package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.CartUtil;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.AddAddressBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.AddCommodityBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;

/**
 * U2020120:お届け先設定のアクションクラスです
 *
 * @author System Integrator Corp.
 */
public class ShippingAddCommodityAction extends ShippingBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   *
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = validateBean(getBean());

    List<CashierShipping> cashierShippingList = getBean().getCashier().getShippingList();
    List<AddAddressBean> selectAddressList = getSelectAddressList();

    int selectAddressListSize = selectAddressList.size();
    if (selectAddressListSize == 0) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.WRONG_ADDITIONAL_DELIVERY));
      return false;
    }

    AddCommodityBean selectCommodity = getSelectAddCommodity(getBean().getAddCommodityKey());
    String shopCode = selectCommodity.getShopCode();
    String skuCode = selectCommodity.getSkuCode();
    boolean isReserve = CartUtil.isReserve(getCart(), shopCode, skuCode);

    // 予約商品複数配送先追加エラー
    if ((cashierShippingList.size() > 1 || selectAddressListSize > 1 || !selectAddressList.get(0).getAddressNo().equals(
        NumUtil.toString(cashierShippingList.get(0).getAddress().getAddressNo())))
        && isReserve) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.ADD_RESERVE_PLURAL_SHIPPING_ERROR));
      return false;
    }

    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    // 現在配送先・商品追加時は商品が追加配送先分追加される
    int amount = 0;
    for (CashierShipping cashier : cashierShippingList) {
      for (CartCommodityInfo commodity : cashier.getCommodityInfoList()) {
        if (commodity.getShopCode().equals(shopCode) && commodity.getSkuCode().equals(skuCode)) {
          amount = amount + commodity.getQuantity();
        }
      }
    }
    amount = amount + selectAddressListSize;

    CommodityAvailability commodityAvailability = catalogService.isAvailable(shopCode, skuCode, amount, isReserve);

    if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, selectCommodity.getDisplayName()));
      valid &= false;
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD_WITH_NAME, selectCommodity.getDisplayName()));
      valid &= false;
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, selectCommodity.getDisplayName()));
      valid &= false;
    } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE_ERROR, selectCommodity.getDisplayName(), String
          .valueOf(catalogService.getAvailableStock(shopCode, skuCode))));
      valid &= false;
    } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, selectCommodity.getDisplayName(), NumUtil
          .toString(catalogService.getReservationAvailableStock(shopCode, skuCode))));
      valid &= false;
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, selectCommodity.getDisplayName()));
      valid &= false;
    } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
      valid &= true;
    }

    addShippingList();
    setBean(createShippingBeanFromCashier());
    valid &= numberLimitValidation();

    return valid;
  }

  /**
   * アクションを実行します。
   *
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    if (isCashOnDeliveryOnly()) {
      ShippingBean shippingBean = getBean();
      shippingBean.setAvailableAddDelivery(false);
      setRequestBean(shippingBean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    setRequestBean(createShippingBeanFromCashier());

    recastCart();

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 選択したアドレス一覧を取得します。
   *
   * @return addAddress
   */
  public List<AddAddressBean> getSelectAddressList() {
    List<AddAddressBean> addAddress = new ArrayList<AddAddressBean>();
    for (AddAddressBean usableAddress : getBean().getAddAddressCheckList()) {
      for (String selectKey : getBean().getAddAddressNoList()) {
        if (usableAddress.getAddressNo().equals(selectKey)) {
          addAddress.add(usableAddress);
        }
      }
    }
    return addAddress;
  }

  /**
   * 選択した商品情報を取得します。
   *
   * @return addCommodity
   */
  public AddCommodityBean getSelectAddCommodity(String commodityKey) {
    AddCommodityBean addCommodity = new AddCommodityBean();
    for (AddCommodityBean commodity : getBean().getAddCommodityList()) {
      if (commodity.getCommodityKey().equals(commodityKey)) {
        addCommodity.setShopCode(commodity.getShopCode());
        addCommodity.setSkuCode(commodity.getSkuCode());
        addCommodity.setDisplayName(commodity.getDisplayName());
      }
    }
    return addCommodity;
  }

  private void addShippingList() {
    AddCommodityBean selectCommodity = getSelectAddCommodity(getBean().getAddCommodityKey());
    List<AddAddressBean> selectAddressList = getSelectAddressList();
    Cashier cashier = getBean().getCashier();
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    List<CustomerAddress> addressList = new ArrayList<CustomerAddress>();
    for (AddAddressBean addAddress : selectAddressList) {
      addressList.add(copyCustomerAddress(cashier, addAddress));
    }

    CatalogService catalogService = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());

    for (CustomerAddress addAddress : addressList) {

      String shopCode = selectCommodity.getShopCode();
      String skuCode = selectCommodity.getSkuCode();
      Long deliveryTypeNo = null;
      CartCommodityInfo addCartCommodity = null;
      for (CartCommodityInfo item : cashier.getUsableCommodity()) {
        if (item.getShopCode().equals(shopCode) && item.getSkuCode().equals(skuCode)) {
          addCartCommodity = new CartCommodityInfo();
          addCartCommodity.setCommodityCode(item.getCommodityCode());
          addCartCommodity.setCommodityName(item.getCommodityName());
          addCartCommodity.setCommodityTaxCharge(item.getCommodityTaxCharge());
          addCartCommodity.setCommodityTaxType(item.getCommodityTaxType());
          addCartCommodity.setRetailPrice(item.getRetailPrice());
          addCartCommodity.setSkuCode(item.getSkuCode());
          addCartCommodity.setStandardDetail1Name(item.getStandardDetail1Name());
          addCartCommodity.setStandardDetail2Name(item.getStandardDetail2Name());
          addCartCommodity.setUnitPrice(item.getUnitPrice());
          addCartCommodity.setShopCode(item.getShopCode());
          addCartCommodity.setCampaignCode(item.getCampaignCode());
          addCartCommodity.setCampaignDiscountRate(item.getCampaignDiscountRate());
          addCartCommodity.setCampaignName(utilService.getNameByLanguage(item.getCampaignName(),item.getCampaignNameEn(),item.getCampaignNameJp()));
          addCartCommodity.setGiftCode("");
          addCartCommodity.setGiftName("");
          addCartCommodity.setGiftPrice(BigDecimal.ZERO);
          addCartCommodity.setGiftTaxCharge(BigDecimal.ZERO);
          addCartCommodity.setQuantity(1);
          addCartCommodity.setCommodityPointRate(item.getCommodityPointRate());
          addCartCommodity.setUseCommodityPoint(item.isUseCommodityPoint());

          CommodityInfo commodityInfo = catalogService.getSkuInfo(item.getShopCode(), item.getSkuCode());
          deliveryTypeNo = commodityInfo.getHeader().getDeliveryTypeNo();
        }
      }
      if (addCartCommodity != null && deliveryTypeNo != null) {
        ShopManagementService shopSvc = ServiceLocator.getShopManagementService(getLoginInfo());
        DeliveryType deliveryType = shopSvc.getDeliveryType(shopCode, deliveryTypeNo);
        cashier.addCashierItem(addAddress, deliveryType, addCartCommodity);
      }
    }
  }
}
