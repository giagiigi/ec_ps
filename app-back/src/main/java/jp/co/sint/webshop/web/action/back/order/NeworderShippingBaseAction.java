package jp.co.sint.webshop.web.action.back.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.dao.CampaignConditionDao;
import jp.co.sint.webshop.data.dao.CampaignMainDao;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.domain.AppointedTimeType;
import jp.co.sint.webshop.data.domain.CampaignConditionFlg;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CodType;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.DiscountType;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.domain.LoginType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.data.dto.CustomerVatInvoice;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.NewCouponHistoryUseInfo;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.MyCouponInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierDelivery;
import jp.co.sint.webshop.service.cart.CashierDiscount;
import jp.co.sint.webshop.service.cart.CashierInvoice;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.CompositionItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.cart.CashierInvoice.CashierInvoiceBase;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.NumberLimitPolicy;
import jp.co.sint.webshop.web.bean.PaymentMethodBean;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeBase;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderBaseBean.InvoiceBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.AddAddressBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.AddCommodityBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryCompanyBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryBean.GiftItemBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryBean.OrderCommodityBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PaymentSupporter;

import org.apache.log4j.Logger;

/**
 * U1020130:新規受注(配送先設定)の基底クラスです。
 * 
 * @author System Integrator Corp.
 */
public abstract class NeworderShippingBaseAction extends NeworderBaseAction<NeworderShippingBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = super.authorize();
    if (getBean().getCashier() == null) {
      auth = false;
    }
    return auth;
  }
  
  public void createOutCardPrice() {
    BigDecimal outerCardRate = DIContainer.getWebshopConfig().getOuterCardRate();
    NeworderShippingBean bean = getBean();
    bean.getCashier().setOuterCardUseAmount(BigDecimal.ZERO);
    if (bean.getCashier().getPayment() != null && bean.getCashier().getPayment().getSelectPayment() != null ) {
      if (PaymentMethodType.OUTER_CARD.getValue().equals(bean.getCashier().getPayment().getSelectPayment().getPaymentMethodType())) {
        BigDecimal outPrice = bean.getCashier().getPaymentTotalPrice().divide(outerCardRate,5);
        outPrice = BigDecimalUtil.subtract(outPrice, bean.getCashier().getPaymentTotalPrice());
        bean.getCashier().setOuterCardUseAmount(outPrice);
      }
    }
  }

  /**
   * 新規受注(配送先設定)のbeanを作成します。
   * 
   * @return 新規受注(配送先設定)のbean
   */
  public NeworderShippingBean createBeanFromCashier() {
    NeworderShippingBean bean = getBean();
    Cashier cashier = bean.getCashier();
    Customer cust = cashier.getCustomer();
    bean.setCustomerCode(cust.getCustomerCode());
    bean.setCustomerName(cust.getLastName());

    ShopManagementService shopSvc = ServiceLocator.getShopManagementService(getLoginInfo());
    CatalogService catalogSvc = ServiceLocator.getCatalogService(getLoginInfo());

    // 商品追加部分
    bean.setAddCommodityList(createAddCommodityList(cashier));
    bean.setAddSelectCommodityList(createAddSelectCommodityList(bean.getAddCommodityList()));
    recreateOtherGiftList();

    // 金額計算
    BigDecimal cartTotalPrice = BigDecimal.ZERO;

    // アドレス、配送種別ごとにDeliveryBeanを設定する。
    List<DeliveryBean> deliveryList = new ArrayList<DeliveryBean>();
    for (CashierShipping cashierItem : cashier.getShippingList()) {
      DeliveryBean deliveryBean = new DeliveryBean();

      // 配送種別情報
      DeliveryType deliveryType = cashierItem.getDeliveryType();
      deliveryBean.setShopCode(deliveryType.getShopCode());
      deliveryBean.setDeliveryCode(NumUtil.toString(deliveryType.getDeliveryTypeNo()));
      deliveryBean.setDeliveryName(deliveryType.getDeliveryTypeName());

      // お届け情報
      deliveryBean.setShippingCharge(String.valueOf(cashierItem.getShippingCharge()));
      deliveryBean.setShippingChargeTax(String.valueOf(cashierItem.getShippingChargeTax()));

      UtilService utilSvc = ServiceLocator.getUtilService(getLoginInfo());

      deliveryBean.setDeliveryRemark(cashierItem.getDeliveryRemark());

      // アドレス情報
      CustomerAddress address = cashierItem.getAddress();
      deliveryBean.setDeliveryAlias(address.getAddressAlias());
      deliveryBean.setAddressNo(address.getAddressNo());
      deliveryBean.setLastName(address.getAddressLastName());
      deliveryBean.setFirstName(address.getAddressFirstName());
      deliveryBean.setPostalCode(address.getPostalCode());
      deliveryBean.setPrefectureCode(address.getPrefectureCode());
      deliveryBean.setCityCode(address.getCityCode());
      deliveryBean.setAreaCode(address.getAreaCode());
      deliveryBean.setAddress1(address.getAddress1());
      deliveryBean.setAddress2(address.getAddress2());
      deliveryBean.setAddress3(address.getAddress3());
      deliveryBean.setAddress4(address.getAddress4());
      deliveryBean.setPhoneNumber(address.getPhoneNumber());
      deliveryBean.setMobileNumber(address.getMobileNumber());

      deliveryBean.setDeliveryAppointedDate(cashierItem.getDeliveryAppointedDate());
      if (StringUtil.hasValueAllOf(cashierItem.getDeliveryAppointedStartTime(), cashierItem.getDeliveryAppointedTimeEnd())) {
        deliveryBean.setDeliveryAppointedTime(cashierItem.getDeliveryAppointedStartTime() + "-"
            + cashierItem.getDeliveryAppointedTimeEnd());
      } else if (StringUtil.hasValue(cashierItem.getDeliveryAppointedStartTime())) {
        deliveryBean.setDeliveryAppointedTime(cashierItem.getDeliveryAppointedStartTime() + "-");
      } else if (StringUtil.hasValue(cashierItem.getDeliveryAppointedTimeEnd())) {
        deliveryBean.setDeliveryAppointedTime("-" + cashierItem.getDeliveryAppointedTimeEnd());
      }
      if (StringUtil.hasValue(cashier.getPayment().getPaymentMethodCode())) {
        if (cashier.getPayment().getSelectPayment().isCashOnDelivery()) {
          deliveryBean.setCodType(CodType.OHTER.getValue());
        } else {
          deliveryBean.setCodType(CodType.COD.getValue());
        }
        String dcCode = "";
        if (cashier.getDelivery() == null) {
          // 获得配送公司
          UtilService utilService = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
          List<DeliveryCompany> dc = utilService.getDeliveryCompanys(deliveryBean.getShopCode(), deliveryBean.getPrefectureCode(),
              deliveryBean.getCityCode(), deliveryBean.getAreaCode(), cashier.getPayment().getSelectPayment().isCashOnDelivery(),
              cashier.getTotalWeight().toString());

          // 封装List<DeliveryCompanyBean>
          List<DeliveryCompanyBean> lsDcb = new ArrayList<DeliveryCompanyBean>();
          DeliveryCompanyBean cacheDCBean = new DeliveryCompanyBean();
          cacheDCBean.setDeliveryCompanyNo("D000");
          for (DeliveryCompany deliveryCompany : dc) {
            if (!cacheDCBean.getDeliveryCompanyNo().equals(deliveryCompany.getDeliveryCompanyNo())) {
              DeliveryCompanyBean dcb = new DeliveryCompanyBean();
              dcb.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
              dcb.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
              lsDcb.add(dcb);
            }
            cacheDCBean.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
          }
          if (lsDcb.size() == 1) {
            dcCode = dc.get(0).getDeliveryCompanyNo();
          }
        } else {
          dcCode = cashier.getDelivery().getDeliveryCompanyCode();
        }
        List<CodeAttribute> deliveryDateList = utilSvc.getDeliveryDateList(deliveryBean.getShopCode(), deliveryBean
            .getPrefectureCode(), cashier.getPayment().getSelectPayment().isCashOnDelivery(), cashierItem.getCommodityInfoList(),
            cashier.getTotalWeight().toString(), dcCode);

        deliveryBean.setDeliveryAppointedDateList(deliveryDateList);

        if (deliveryDateList.size() > 0) {
          String deliveryAppointedDate = deliveryBean.getDeliveryAppointedDate();
          boolean flg = false;
          for (CodeAttribute info : deliveryDateList) {
            if (info.getValue().equals(deliveryAppointedDate)) {
              flg = true;
            }
          }
          if (!flg || StringUtil.isNullOrEmpty(deliveryAppointedDate)) {
            deliveryAppointedDate = deliveryDateList.get(0).getValue();
          }
          List<CodeAttribute> deliveryTimeZoneList = utilSvc.getDeliveryTimeList(deliveryBean.getShopCode(), deliveryBean
              .getPrefectureCode(), cashier.getPayment().getSelectPayment().isCashOnDelivery(), deliveryAppointedDate, cashier
              .getTotalWeight().toString(), dcCode);

          deliveryBean.setDeliveryAppointedTimeList(deliveryTimeZoneList);
        }
      }

      // 商品情報
      List<OrderCommodityBean> deliveryItemList = new ArrayList<OrderCommodityBean>();
      CommunicationService communService = ServiceLocator.getCommunicationService(getLoginInfo());

      UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
      for (CartCommodityInfo commodityInfo : cashierItem.getCommodityInfoList()) {
        OrderCommodityBean deliveryItem = new OrderCommodityBean();
        if (StringUtil.hasValue(commodityInfo.getOriginalCommodityCode())) {
          deliveryItem.setOriginalCommodityCode(commodityInfo.getOriginalCommodityCode());
          deliveryItem.setCombinationAmount(commodityInfo.getCombinationAmount());
        }
        deliveryItem.setIsDiscountCommodity(commodityInfo.getIsDiscountCommodity());
        deliveryItem.setShopCode(commodityInfo.getShopCode());
        deliveryItem.setShopName(shopSvc.getShop(commodityInfo.getShopCode()).getShopName());
        deliveryItem.setCommodityCode(commodityInfo.getCommodityCode());
        deliveryItem.setCommodityName(commodityInfo.getCommodityName());
        deliveryItem.setStandardName1(commodityInfo.getStandardDetail1Name());
        deliveryItem.setStandardName2(commodityInfo.getStandardDetail2Name());
        deliveryItem.setRetailPrice(commodityInfo.getRetailPrice());
        deliveryItem.setCommodityTaxType(commodityInfo.getCommodityTaxType());
        deliveryItem.setGiftCode(commodityInfo.getGiftCode());

        deliveryItem.setGiftList(utilService.getGiftList(commodityInfo.getShopCode(), commodityInfo.getCommodityCode()));

        CommodityHeader commodityHeader = catalogSvc.getCommodityHeader(commodityInfo.getShopCode(), commodityInfo
            .getCommodityCode());
        // 如果是套餐商品,先不管它本身的在库区分,直接设置成2
        if (SetCommodityFlg.OBJECTIN.longValue().equals(commodityInfo.getSetCommodityFlg())) {
          deliveryItem.setStockManagementType(StockManagementType.WITH_QUANTITY.longValue());
        } else {
          deliveryItem.setStockManagementType(commodityHeader.getStockManagementType());
        }

        deliveryItem.setPurchasingAmount(String.valueOf(commodityInfo.getQuantity()));

        BigDecimal giftPrice;

        if (StringUtil.isNullOrEmpty(commodityInfo.getGiftCode())) {
          giftPrice = BigDecimal.ZERO;
        } else {
          giftPrice = commodityInfo.getGiftPrice();
        }
        BigDecimal subTotal = BigDecimalUtil.multiply(commodityInfo.getRetailPrice().add(giftPrice), commodityInfo.getQuantity());
        deliveryItem.setSubTotalPrice(String.valueOf(subTotal));
        cartTotalPrice = cartTotalPrice.add(subTotal);
        // 不是赠品的情况下,查看能不能表示折扣券
        if (!CommodityType.GIFT.longValue().equals(commodityInfo.getCommodityType())) {
          // 取出商品的最小库存数并判断是否为库存不管理,如果是,则重新设置库存类型为不管理.
          NumberLimitPolicy numberPolicy = DIContainer.getNumberLimitPolicy();
          Long availableStock = numberPolicy.getMaxTotalAmountNum();

          Long stockQuantity = catalogSvc
              .getAvailableStock(commodityInfo.getShopCode(), commodityInfo.getSkuCode(), SetCommodityFlg.OBJECTIN.longValue()
                  .equals(commodityInfo.getSetCommodityFlg()), getCompositionSkuCodeList(commodityInfo.getCompositionList()),
                  getCashierGiftSkuCodeList(commodityInfo.getGiftList()));
          if (availableStock.equals(stockQuantity)) {
            deliveryItem.setStockManagementType(StockManagementType.NONE.longValue());
          }
          deliveryItem.setStockQuantity(stockQuantity);
          // 判断该商品是否符合折扣券显示条件
          List<CampaignCondition> camConditionList = communService.getSaleOffCampaignByCommodityCode(commodityInfo
              .getCommodityCode());
          if (camConditionList != null && camConditionList.size() > 0) {
            deliveryItem.setDiscountCouponDisplayFlg(false);
            for (CampaignCondition condition : camConditionList) {
              if (NumUtil.isNull(condition.getMaxCommodityNum())) {
                deliveryItem.setDiscountCouponDisplayFlg(true);
                break;
              }
              if (!NumUtil.isNull(condition.getMaxCommodityNum()) && condition.getMaxCommodityNum() <= commodityInfo.getQuantity()) {
                deliveryItem.setDiscountCouponDisplayFlg(true);
                break;
              }
            }
          }

          if (StringUtil.hasValue(commodityInfo.getCampaignCouponCode())) {
            if (deliveryItem.isDiscountCouponDisplayFlg()) {
              // 如果初始时,casher里的商品有折扣券信息,则表示是从订单确认画面过来的cashier,要在bean的map里记上,并设置好bean的折扣券信息
              Map<String, Long> discountCouponUsedMap = bean.getDiscountCouponUsedMap();
              if (!discountCouponUsedMap.containsKey(commodityInfo.getCampaignCouponCode())) {
                discountCouponUsedMap.put(commodityInfo.getCampaignCouponCode(), 1L);
              }
              deliveryItem.setDiscountCouponUsedFlg(true);
              deliveryItem.setDiscountCouponCode(commodityInfo.getCampaignCouponCode());
              deliveryItem.setDiscountPrice(commodityInfo.getCampaignCouponPrice());
              deliveryItem.setTotalDiscountPrice(BigDecimalUtil.multiply(commodityInfo.getCampaignCouponPrice(), new BigDecimal(
                  commodityInfo.getQuantity())));
              bean.setDiscountCouponUsedFlg(true);
            }
          }

          // 设置对象商品的赠品
          if (commodityInfo.getGiftList() != null && commodityInfo.getGiftList().size() > 0) {
            List<GiftItemBean> giftItemBeanList = new ArrayList<GiftItemBean>();
            for (GiftItem gift : commodityInfo.getGiftList()) {
              GiftItemBean itemBean = new GiftItemBean();
              itemBean.setShopCode(gift.getShopCode());
              itemBean.setGiftCode(gift.getGiftCode());
              itemBean.setGiftName(gift.getGiftName());
              itemBean.setGiftSkuCode(gift.getGiftSkuCode());
              itemBean.setStandardDetail1Name(gift.getStandardDetail1Name());
              itemBean.setStandardDetail2Name(gift.getStandardDetail2Name());
              CommodityHeader cmdHeader = catalogSvc.getCommodityHeader(itemBean.getShopCode(), itemBean.getGiftCode());
              if (cmdHeader != null) {
                itemBean.setStockManagementType(cmdHeader.getStockManagementType());
                itemBean.setStockQuantity(catalogSvc.getAvailableStock(itemBean.getShopCode(), itemBean.getGiftSkuCode()));
              }
              itemBean.setRetailPrice(gift.getRetailPrice());
              itemBean.setQuantity(gift.getQuantity());
              itemBean.setSubTotalPrice(BigDecimalUtil.multiply(itemBean.getRetailPrice(), gift.getQuantity()));

              cartTotalPrice = cartTotalPrice.add(itemBean.getSubTotalPrice());
              itemBean.setCampaignName(gift.getCampaignName());

              giftItemBeanList.add(itemBean);
            }
            deliveryItem.setCommodityGiftList(giftItemBeanList);
          }

          // 如果是套餐商品,设定套餐内容
          if (SetCommodityFlg.OBJECTIN.longValue().equals(commodityInfo.getSetCommodityFlg())) {
            String setCommodityInfo = "";
            int nSize = commodityInfo.getCompositionList().size();
            for (int i = 0; i < nSize; ++i) {
              setCommodityInfo += commodityInfo.getCompositionList().get(i).getCommodityName();
              if (StringUtil.hasValue(commodityInfo.getCompositionList().get(i).getStandardDetail1Name())
                  || StringUtil.hasValue(commodityInfo.getCompositionList().get(i).getStandardDetail2Name())) {
                setCommodityInfo += "(";
                if (StringUtil.hasValue(commodityInfo.getCompositionList().get(i).getStandardDetail1Name())) {
                  setCommodityInfo += commodityInfo.getCompositionList().get(i).getStandardDetail1Name();
                }
                if (StringUtil.hasValue(commodityInfo.getCompositionList().get(i).getStandardDetail2Name())) {
                  setCommodityInfo += " / " + commodityInfo.getCompositionList().get(i).getStandardDetail2Name();
                }

                setCommodityInfo += ")";
              }

              if (i < nSize - 1) {
                setCommodityInfo += "+";
              }
            }

            deliveryItem.setCommodityAttributes(setCommodityInfo);
            deliveryItem.setSetCommodityFlg(true);

            deliveryItem.setCompositionList(commodityInfo.getCompositionList());
            // 套餐商品不可使折扣券
            deliveryItem.setDiscountCouponDisplayFlg(false);
          } else {
            deliveryItem.setSetCommodityFlg(false);
          }

          deliveryItem.setSkuCode(commodityInfo.getSkuCode());
          // 如果折扣券不表示,则清空折扣券的信息及折扣金额
          if (!deliveryItem.isDiscountCouponDisplayFlg()) {
            deliveryItem.setDiscountCouponCode("");
            deliveryItem.setDiscountPrice(BigDecimal.ZERO);
            deliveryItem.setTotalDiscountPrice(BigDecimal.ZERO);
          }
          deliveryItemList.add(deliveryItem);
        }
      }

      deliveryBean.setOrderCommodityList(deliveryItemList);

      deliveryBean.setDeliveryTotalCommodityPrice(cashierItem.getTotalPrice());

      deliveryList.add(deliveryBean);
    }
    // 多关联商品的赠品List
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    List<OrderCommodityBean> otherGiftList = new ArrayList<OrderCommodityBean>();
    for (CartCommodityInfo otherGiftItem : cashier.getOtherGiftList()) {
      OrderCommodityBean otherGiftBeanItem = new OrderCommodityBean();
      // 如果商品类型为GIFT,表示存的是多重优惠活动的赠品 skuCode实际存的是(促销活动编号~skuCode)
      if (StringUtil.hasValue(otherGiftItem.getSkuCode())) {
        String[] tmp = otherGiftItem.getSkuCode().split("~");
        if (tmp.length > 1) {
          otherGiftBeanItem.setMultipleCampaignCode(tmp[0]);
          otherGiftBeanItem.setSkuCode(tmp[1]);
          otherGiftBeanItem.setStockQuantity(catalogSvc.getAvailableStock(otherGiftItem.getShopCode(), otherGiftBeanItem
              .getSkuCode()));
        } else {
          otherGiftBeanItem.setMultipleCampaignCode(otherGiftItem.getMultipleCampaignCode());
          otherGiftBeanItem.setSkuCode(otherGiftItem.getSkuCode());
          otherGiftBeanItem.setStockQuantity(catalogSvc.getAvailableStock(otherGiftItem.getShopCode(), otherGiftItem.getSkuCode()));
        }
      }
      otherGiftBeanItem.setShopCode(otherGiftItem.getShopCode());
      otherGiftBeanItem.setShopName(shopSvc.getShop(otherGiftItem.getShopCode()).getShopName());
      otherGiftBeanItem.setCommodityName(otherGiftItem.getCommodityName());
      otherGiftBeanItem.setStandardName1(otherGiftItem.getStandardDetail1Name());
      otherGiftBeanItem.setStandardName2(otherGiftItem.getStandardDetail2Name());
      otherGiftBeanItem.setRetailPrice(otherGiftItem.getRetailPrice());
      otherGiftBeanItem.setCommodityTaxType(otherGiftItem.getCommodityTaxType());
      otherGiftBeanItem.setGiftCode(otherGiftItem.getGiftCode());

      otherGiftBeanItem.setGiftList(utilService.getGiftList(otherGiftItem.getShopCode(), otherGiftItem.getCommodityCode()));

      CommodityHeader commodityHeader = catalogSvc
          .getCommodityHeader(otherGiftItem.getShopCode(), otherGiftItem.getCommodityCode());
      otherGiftBeanItem.setPurchasingAmount(String.valueOf(otherGiftItem.getQuantity()));

      BigDecimal giftPrice;

      if (StringUtil.isNullOrEmpty(otherGiftItem.getGiftCode())) {
        giftPrice = BigDecimal.ZERO;
      } else {
        giftPrice = otherGiftItem.getGiftPrice();
      }
      BigDecimal subTotal = BigDecimalUtil.multiply(otherGiftItem.getRetailPrice().add(giftPrice), otherGiftItem.getQuantity());
      otherGiftBeanItem.setSubTotalPrice(String.valueOf(subTotal));
      cartTotalPrice = cartTotalPrice.add(subTotal);

      otherGiftBeanItem.setStockManagementType(commodityHeader.getStockManagementType());
      otherGiftBeanItem.setMultipleCampaignName(otherGiftItem.getMultipleCampaignName());

      otherGiftList.add(otherGiftBeanItem);
    }
    bean.setOtherGiftList(otherGiftList);
    // 指定したショップが持つ表示可能な支払方法を取得
    BigDecimal totalAllPrice = BigDecimalUtil.add(cashier.getTotalCommodityPrice(), cashier.getTotalShippingCharge());
    BigDecimal couponPrice = BigDecimal.ZERO;

    // お支払い方法関連情報の取得
    bean.setOrderPayment(PaymentSupporterFactory.createPaymentSuppoerter().createPaymentMethodBean(cashier.getPayment(),
        totalAllPrice, BigDecimal.ZERO, false, couponPrice, ""));

    PaymentSupporter paymentSupporter = new PaymentSupporter(LoginType.BACK);
    PaymentMethodBean paymentMethodBean = paymentSupporter.createPaymentMethodBean(bean.getCashier().getPayment().getShopCode());

    // 表示可能な支払方法が代金引換だけかどうかを検証
    boolean isCashOnDeliveryOnly = paymentMethodBean.getDisplayPaymentList().size() == 1
        && paymentMethodBean.getDisplayPaymentList().get(0).getPaymentMethodType().equals(
            PaymentMethodType.CASH_ON_DELIVERY.getValue());
    boolean hasNotPoint = true;
    // 全額ポイント払いが可能かどうかを検証
    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    PointRule pointRule = service.getPointRule();

    if (pointRule.getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.ENABLED.longValue())) {
      if (BigDecimalUtil.isAboveOrEquals(getBean().getCashier().getCustomer().getRestPoint(), getBean().getCashier()
          .getGrandTotalPrice())) {
        hasNotPoint = false;
      }
    }
    if (BigDecimalUtil.isAbove(getBean().getCashier().getGrandTotalPrice(), BigDecimal.ZERO) && hasNotPoint && isCashOnDeliveryOnly) {
      bean.setAvailableAddDelivery(false);
    } else {
      bean.setAvailableAddDelivery(true);
    }

    if (bean.getAddAddressCheckList().size() == 0) {
      bean.setAddAddressCheckList(createAddressCheckList(cashier));
    }
    if (bean.getDispAddressList().size() == 0) {
      // アドレスリスト
      bean.setDispAddressList(createAddressList(cashier));
    }

    bean.setCartTotalCommodityPrice(NumUtil.toString(cartTotalPrice));
    bean.setDeliveryList(deliveryList);

    bean.setDiscountTypeList(createDiscountTypeList(cashier));
    bean.setCustomerGroupCampaign(createCustomerDiscount(cashier));
    // 个人优惠券
    bean.setPersonalCouponList(createPersonalCouponList(cashier));
    bean.setOrderInvoice(this.createInvoice(cashier));
    createDeliveryToBean(bean);
    // 判断是否使用了优惠券
    if (StringUtil.hasValue(cashier.getDiscount().getDiscountCode())) {
      bean.setDiscountUsedFlg(true);
    }

    return bean;
  }

  /**
   * 商品追加情報生成
   * 
   * @param cashier
   * @return 商品のリスト
   */
  private List<AddCommodityBean> createAddCommodityList(Cashier cashier) {
    List<AddCommodityBean> commodityList = new ArrayList<AddCommodityBean>();

    Long i = 0L;
    for (CartCommodityInfo useableCommodity : cashier.getUsableCommodity()) {
      AddCommodityBean commodity = new AddCommodityBean();
      commodity.setCommodityKey(NumUtil.toString(i));
      commodity.setShopCode(useableCommodity.getShopCode());
      commodity.setCommodityCode(useableCommodity.getCommodityCode());
      commodity.setSkuCode(useableCommodity.getSkuCode());
      String display = useableCommodity.getCommodityName();
      String detail1Name = useableCommodity.getStandardDetail1Name();
      String detail2Name = useableCommodity.getStandardDetail2Name();
      if (StringUtil.hasValue(detail1Name) && StringUtil.hasValue(detail2Name)) {
        display += "("
            + MessageFormat.format(Messages.getString("web.action.back.order.NeworderShippingBaseAction.0"), detail1Name,
                detail2Name) + ")";
      } else if (StringUtil.hasValue(detail1Name)) {
        display += "(" + detail1Name + ")";
      } else if (StringUtil.hasValue(detail2Name)) {
        display += "(" + detail2Name + ")";
      }
      commodity.setDisplayName(display);
      commodityList.add(commodity);
      i++;
    }

    return commodityList;
  }

  /**
   * アドレス一覧生成
   * 
   * @param customerCode
   * @return アドレス帳のリスト
   */
  // 10.1.2 10073 修正 ここから
  public List<CodeAttribute> createAddressList(Cashier cashier) {
    // 10.1.2 10073 修正 ここまで
    List<CodeAttribute> addressList = new ArrayList<CodeAttribute>();

    String customerCode = cashier.getCustomer().getCustomerCode();
    if (StringUtil.hasValue(customerCode)) {
      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
      for (CustomerAddress address : service.getCustomerAddressList(customerCode)) {
        NameValue value = new NameValue(address.getAddressLastName() + "("
            + address.getAddressAlias() + ")", NumUtil.toString(address.getAddressNo()));
        addressList.add(value);
      }
    } else {
      // ゲストの場合
      CustomerAddress address = cashier.getShippingList().get(0).getAddress();

      NameValue value = new NameValue(MessageFormat.format(
          Messages.getString("web.action.back.order.NeworderShippingBaseAction.1"),
          address.getAddressLastName(), ""), NumUtil.toString(address
          .getAddressNo()));
      addressList.add(value);
    }
    return addressList;
  }

  /**
   * 新規受注(配送先設定)のBeanからCashierへコピーします。
   */
  public void copyBeanToCashier() {
    NeworderShippingBean bean = getBean();
    Cashier cashier = bean.getCashier();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    TaxUtil taxUtil = DIContainer.get("TaxUtil");

    for (DeliveryBean shipping : bean.getDeliveryList()) {
      CashierShipping cashierShipping = cashier.getShipping(shipping.getShopCode(), NumUtil.toLong(shipping.getDeliveryCode()),
          shipping.getAddressNo());
      cashierShipping.setDeliveryRemark(shipping.getDeliveryRemark());
      cashierShipping.setDeliveryAppointedDate(shipping.getDeliveryAppointedDate());
      if (StringUtil.isNullOrEmpty(shipping.getDeliveryAppointedTime())
          || shipping.getDeliveryAppointedTime().equals(AppointedTimeType.NOT.getValue())) {
        cashierShipping.setDeliveryAppointedStartTime("");
        cashierShipping.setDeliveryAppointedTimeEnd("");
      } else {
        String[] timeZone = shipping.getDeliveryAppointedTime().split("-");
        if (timeZone.length > 1) {
          cashierShipping.setDeliveryAppointedStartTime(timeZone[0]);
          cashierShipping.setDeliveryAppointedTimeEnd(timeZone[1]);
        } else if (shipping.getDeliveryAppointedTime().endsWith("-")) {
          cashierShipping.setDeliveryAppointedStartTime(shipping.getDeliveryAppointedTime().replace("-", ""));
          cashierShipping.setDeliveryAppointedTimeEnd("");
        } else {
          cashierShipping.setDeliveryAppointedStartTime("");
          cashierShipping.setDeliveryAppointedTimeEnd(shipping.getDeliveryAppointedTime().replace("-", ""));
        }
      }
      // 商品情報コピー
      for (OrderCommodityBean commodity : shipping.getOrderCommodityList()) {
        List<CartCommodityInfo> cashierCommodityList = cashierShipping.getCommodityInfoList();
        for (CartCommodityInfo cashierCommodity : cashierCommodityList) {
          if (commodity.getSkuCode().equals(cashierCommodity.getSkuCode())
              && commodity.getIsDiscountCommodity().equals(cashierCommodity.getIsDiscountCommodity())) {
            // ギフト更新
            Gift gift = catalogService.getGift(shipping.getShopCode(), commodity.getGiftCode());
            if (gift != null) {
              cashierCommodity.setGiftCode(gift.getGiftCode());
              cashierCommodity.setGiftName(gift.getGiftName());
              cashierCommodity.setGiftPrice(Price.getPriceIncludingTax(gift.getGiftPrice(), taxUtil.getTaxRate(), NumUtil
                  .toString(gift.getGiftTaxType())));
              cashierCommodity.setGiftTaxCharge(Price.getPriceTaxCharge(gift.getGiftPrice(), taxUtil.getTaxRate(), NumUtil
                  .toString(gift.getGiftTaxType())));
              cashierCommodity.setGiftTaxType(gift.getGiftTaxType());
            } else {
              // ギフトが存在しないときはリセットする
              cashierCommodity.setGiftCode("");
              cashierCommodity.setGiftName("");
              cashierCommodity.setGiftPrice(BigDecimal.ZERO);
              cashierCommodity.setGiftTaxCharge(BigDecimal.ZERO);
              cashierCommodity.setGiftTaxType(null);
            }
            // 数量更新
            if (NumUtil.isNum(commodity.getPurchasingAmount())) {
              cashierCommodity.setQuantity(Integer.parseInt(commodity.getPurchasingAmount()));
            } else {
              Logger logger = Logger.getLogger(this.getClass());
              logger.warn(Messages.getString("web.action.back.order.NeworderShippingBaseAction.2"));
            }
            // 折扣券信息的更新
            if (commodity.isDiscountCouponUsedFlg()) {
              CampaignMainDao mainDao = DIContainer.getDao(CampaignMainDao.class);
              CampaignMain main = mainDao.load(commodity.getDiscountCouponCode());
              if (main != null) {
                cashierCommodity.setCampaignCouponName(main.getCampaignName());
              }
              cashierCommodity.setCampaignCouponCode(commodity.getDiscountCouponCode());
              cashierCommodity.setCampaignCouponPrice(commodity.getDiscountPrice());
              CampaignConditionDao conditionDao = DIContainer.getDao(CampaignConditionDao.class);
              CampaignCondition condition = conditionDao.load(commodity.getDiscountCouponCode());
              if (condition != null) {
                cashierCommodity.setCampaignCouponType(condition.getDiscountType());
              }
            } else {
              cashierCommodity.setCampaignCouponName("");
              cashierCommodity.setCampaignCouponCode("");
              cashierCommodity.setCampaignCouponPrice(BigDecimal.ZERO);
            }

            if (SetCommodityFlg.OBJECTIN.longValue().equals(cashierCommodity.getSetCommodityFlg())) {
              cashierCommodity.setCompositionAttribute(commodity.getCommodityAttributes());
            }
            for (GiftItemBean giftBean : commodity.getCommodityGiftList()) {
              for (GiftItem giftItem : cashierCommodity.getGiftList()) {
                if (giftItem.getShopCode().equals(giftBean.getShopCode())
                    && giftItem.getGiftSkuCode().equals(giftBean.getGiftSkuCode())) {
                  giftItem.setQuantity(giftItem.getQuantity());
                }
              }
            }
          }
        }
      }
      // 2012-11-29 促销对应 ob add start
      recreateOtherGiftList();

      for (CartCommodityInfo cashierCommodity : cashierShipping.getCommodityInfoList()) {
        if (CommodityType.GIFT.longValue().equals(cashierCommodity.getCommodityType())) {
          if (!StringUtil.isNullOrEmpty(cashierCommodity.getSkuCode())) {
            String[] tmp = cashierCommodity.getSkuCode().split("~");
            if (tmp.length > 1) {
              cashierCommodity.setSkuCode(tmp[1]);
            }
          }
        }
      }
      // 2012-11-29 促销对应 ob add end
    }
    // 2012-11-29 促销对应 ob add start
    for (CartCommodityInfo cashierCommodity : cashier.getOtherGiftList()) {
      if (!StringUtil.isNullOrEmpty(cashierCommodity.getSkuCode())) {
        String[] tmp = cashierCommodity.getSkuCode().split("~");
        if (tmp.length > 1) {
          cashierCommodity.setSkuCode(tmp[1]);
        }
      }
    }
    // 2012-11-29 促销对应 ob add end

    if (!StringUtil.isNullOrEmpty(getBean().getSelectedDeliveryCompany().getDeliveryCompanyNo())) {
      CashierDelivery cdBean = new CashierDelivery();
      cdBean.setDeliveryCompanyCode(getBean().getSelectedDeliveryCompany().getDeliveryCompanyNo());
      cashier.setDelivery(cdBean);
      cashier.recomputeShippingCharge();
    }

  }

  /**
   * 購入商品数合計を集計して返します。
   * 
   * @param shippingList
   * @param shopCode
   * @param skuCode
   * @return 購入商品数合計
   */
  public Long getAllPurchasingAmount(List<DeliveryBean> shippingList, String shopCode, String skuCode) {
    Long allPurchasingAmount = 0L;
    for (DeliveryBean shipping : shippingList) {
      for (OrderCommodityBean commodity : shipping.getOrderCommodityList()) {
        if (commodity.getShopCode().equals(shopCode) && commodity.getSkuCode().equals(skuCode)) {
          allPurchasingAmount += NumUtil.toLong(commodity.getPurchasingAmount());
        }
      }
    }
    return allPurchasingAmount;
  }

  /**
   * 在庫・予約上限数チェック
   * 
   * @return boolean
   */
  public boolean shippingValidation() {
    boolean valid = true;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    for (AddCommodityBean commodity : getBean().getAddCommodityList()) {
      String shopCode = commodity.getShopCode();
      String skuCode = commodity.getSkuCode();

      CommodityHeaderDao cmdHeaderDao = DIContainer.getDao(CommodityHeaderDao.class);
      CommodityHeader cmdHeader = cmdHeaderDao.load(shopCode, commodity.getCommodityCode());
      if (SetCommodityFlg.OBJECTIN.longValue().equals(cmdHeader.getSetCommodityFlg())) {
        continue;
      }

      Long amount = getAllPurchasingAmount(getBean().getDeliveryList(), shopCode, skuCode);

      boolean isReserve = getBean().getCashier().isReserve();
      CommodityAvailability commodityAvailability = catalogService.isAvailable(shopCode, skuCode, amount.intValue(), isReserve);
      if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
      } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, skuCode));
      } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NO_AVAILABLE_STOCK_SKU, skuCode));
      } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE, skuCode, NumUtil.toString(catalogService
            .getAvailableStock(shopCode, skuCode))));
      } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, skuCode, NumUtil.toString(catalogService
            .getReservationAvailableStock(shopCode, skuCode))));
      } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
        valid &= false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, skuCode));
      } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
        valid &= true;
      }
    }

    return valid;
  }

  // 2012-11-23 促销对应 ob add start
  // 折扣券使用限定check
  public boolean discountCouponUseValidation(String commodityCode) {
    boolean valid = true;
    List<DeliveryBean> deliveryList = getBean().getDeliveryList();
    for (DeliveryBean delivery : deliveryList) {
      List<OrderCommodityBean> orderCommodityList = delivery.getOrderCommodityList();
      for (OrderCommodityBean orderCommodity : orderCommodityList) {
        if (commodityCode.equals(orderCommodity.getSkuCode())) {
          String campaignCode = orderCommodity.getDiscountCouponCode();
          if (StringUtil.isNullOrEmpty(campaignCode)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_CODE_NULL_ERROR, Messages
                .getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
            return false;
          }
          CampaignMainDao mainDao = DIContainer.getDao(CampaignMainDao.class);
          CampaignMain main = mainDao.load(campaignCode);
          // 促销活动不存在
          if (null == main) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_NOT_EXIST, Messages
                .getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
            return false;
          }
          // 促销活动期间不是进行中
          if (!DateUtil.isPeriodDate(main.getCampaignStartDate(), main.getCampaignEndDate())) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_NOT_EXIST, Messages
                .getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
            return false;
          }
          // 促销行为类型不是商品优惠券
          if (!CampaignMainType.SALE_OFF.longValue().equals(main.getCampaignType())) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_NOT_EXIST, Messages
                .getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
            return false;
          }

          CampaignConditionDao conditionDao = DIContainer.getDao(CampaignConditionDao.class);
          CampaignCondition condition = conditionDao.load(campaignCode);
          if (null == condition) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_NOT_EXIST, Messages
                .getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
            return false;
          }
          // 判断商品在不在折扣券的使用范围内
          CommunicationService cmuSvr = ServiceLocator.getCommunicationService(getLoginInfo());
          if (!cmuSvr.isCampaignExistCommodit(orderCommodity.getShopCode(), orderCommodity.getCommodityCode(), campaignCode)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_CANNT_USED_ERROR, Messages
                .getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
            return false;
          }

          // 判断折扣券的对象商品限定件数
          if (condition.getMaxCommodityNum() != null) {
            if (condition.getMaxCommodityNum() > Long.parseLong(orderCommodity.getPurchasingAmount())) {
              addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_MAX_COMMODITY_ERROR, condition.getMaxCommodityNum()
                  .toString(), campaignCode));
              return false;
            }
          }

          // 判断折扣券的最大使用回数
          Long count = 0L;
          Map<String, Long> discountCouponUsedMap = getBean().getDiscountCouponUsedMap();
          if (!discountCouponUsedMap.isEmpty()) {
            if (discountCouponUsedMap.containsKey(campaignCode)) {
              count = discountCouponUsedMap.get(campaignCode);
            }
          }

          OrderService orderSvr = ServiceLocator.getOrderService(getLoginInfo());
          count += orderSvr.getCampaignDiscountUsedCount(campaignCode, "");
          if (condition.getUseLimit() != null) {
            if (count >= condition.getUseLimit()) {
              addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_MAX_USED_ERROR, campaignCode, Messages
                  .getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
              return false;
            }
          }
        }
      }
    }
    return valid;
  }

  // 重新创建多关联商品List
  public void recreateOtherGiftList() {
    Cashier cashier = getBean().getCashier();
    BigDecimal totalOrderPrice = cashier.getTotalCommodityPrice();

    List<CartCommodityInfo> otherGiftList = cashier.getOtherGiftList();

    Iterator<CartCommodityInfo> iter = otherGiftList.iterator();
    while (iter.hasNext()) {
      CartCommodityInfo otherGift = iter.next();
      String campaignCode = otherGift.getMultipleCampaignCode();
      CampaignMainDao mainDao = DIContainer.getDao(CampaignMainDao.class);
      CampaignMain main = mainDao.load(campaignCode);
      // 促销活动不存在
      if (null == main) {
        iter.remove();
        continue;
      }
      // 促销活动期间不是进行中
      if (!DateUtil.isPeriodDate(main.getCampaignStartDate(), main.getCampaignEndDate())) {
        iter.remove();
        continue;
      }

      // 促销行为类型不是多重关联促销活动
      if (!CampaignMainType.MULTIPLE_GIFT.longValue().equals(main.getCampaignType())) {
        iter.remove();
        continue;
      }

      CampaignConditionDao conditionDao = DIContainer.getDao(CampaignConditionDao.class);
      CampaignCondition condition = conditionDao.load(campaignCode);
      if (null == condition) {
        iter.remove();
        continue;
      }

      // 订单金额check
      if (!NumUtil.isNull(condition.getOrderAmount())) {
        if (BigDecimalUtil.isAbove(condition.getOrderAmount(), totalOrderPrice)) {
          iter.remove();
          continue;
        }
      }

      // 对象商品类型check
      if (StringUtil.hasValue(condition.getAttributrValue())) {
        List<String> mutipleCommodityList = Arrays.asList(condition.getAttributrValue().split(","));
        if (!conditionFlgValidation(condition.getCampaignConditionFlg(), getCommodityCodeList(), mutipleCommodityList)) {
          iter.remove();
        }
      }
    }
  }

  // 将购物车内的所有商品以SKU为单位进行合计,再次进行有效在库数验证
  public boolean allCommoditysStockValidation() {
    boolean valid = true;
    Cashier cashier = getBean().getCashier();

    List<Sku> useSkuList = new ArrayList<Sku>();
    Map<Sku, Integer> skuMap = new HashMap<Sku, Integer>();
    Map<Sku, CartCommodityInfo> mainCommodityDetailMap = new HashMap<Sku, CartCommodityInfo>();
    Map<Sku, GiftItem> GiftMap = new HashMap<Sku, GiftItem>();
    Map<Sku, CompositionItem> compositionMap = new HashMap<Sku, CompositionItem>();
    for (CashierShipping cashierShipping : cashier.getShippingList()) {
      for (CartCommodityInfo commodity : cashierShipping.getCommodityInfoList()) {
        if (SetCommodityFlg.OBJECTIN.longValue().equals(commodity.getSetCommodityFlg())) {
          for (CompositionItem comItem : commodity.getCompositionList()) {
            Sku sku = new Sku(comItem.getShopCode(), comItem.getSkuCode());
            if (skuMap.containsKey(sku)) {
              Integer amount = skuMap.get(sku) + commodity.getQuantity();
              skuMap.remove(sku);
              skuMap.put(sku, amount);
            } else {
              useSkuList.add(sku);
              skuMap.put(sku, commodity.getQuantity());
              compositionMap.put(sku, comItem);
            }
          }
        } else {
          Sku sku = new Sku(commodity.getShopCode(), commodity.getSkuCode());
          if (skuMap.containsKey(sku)) {
            Integer amount = skuMap.get(sku) + commodity.getQuantity();
            skuMap.remove(sku);
            skuMap.put(sku, amount);
          } else {
            useSkuList.add(sku);
            skuMap.put(sku, commodity.getQuantity());
            mainCommodityDetailMap.put(sku, commodity);
          }
        }

        for (GiftItem gift : commodity.getGiftList()) {
          Sku skuGift = new Sku(gift.getShopCode(), gift.getGiftSkuCode());
          if (skuMap.containsKey(skuGift)) {
            Integer amount = skuMap.get(skuGift) + gift.getQuantity();
            skuMap.remove(skuGift);
            skuMap.put(skuGift, amount);
          } else {
            useSkuList.add(skuGift);
            skuMap.put(skuGift, gift.getQuantity());
            GiftMap.put(skuGift, gift);
          }
        }

      }
    }

    for (Sku sku : useSkuList) {
      String shopCode = sku.getShopCode();
      String skuCode = sku.getSkuCode();
      CommodityAvailability commodityAvailability = CommodityAvailability.AVAILABLE;
      CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
      if (mainCommodityDetailMap.containsKey(sku)) {
        CartCommodityInfo commodity = mainCommodityDetailMap.get(sku);

        // 如果是普通商品,check普通商品的在库;如果是赠品,check赠品的在库
        if (CommodityType.GIFT.longValue().equals(commodity.getCommodityType())) {
          commodityAvailability = catalogService.isAvailableGift(shopCode, skuCode, skuMap.get(sku));
        } else {
          commodityAvailability = catalogService.isAvailable(shopCode, skuCode, skuMap.get(sku), cashier.isReserve());
        }
      } else if (GiftMap.containsKey(sku)) {
        // 如果GiftMap不为空,check giftList
        GiftItem gift = GiftMap.get(sku);
        commodityAvailability = catalogService.isAvailableGift(gift.getShopCode(), gift.getGiftSkuCode(), skuMap.get(sku));
      } else if (compositionMap.containsKey(sku)) {
        CompositionItem composition = compositionMap.get(sku);
        commodityAvailability = catalogService.isAvailable(composition.getShopCode(), composition.getSkuCode(), skuMap.get(sku),
            cashier.isReserve());
      }

      if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
        valid = false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
        break;
      } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
        valid = false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, skuCode));
        break;
      } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
        valid = false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NO_AVAILABLE_STOCK_SKU, skuCode));
        break;
      } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
        valid = false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE, skuCode, NumUtil.toString(catalogService
            .getAvailableStock(shopCode, skuCode))));
        break;
      } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
        valid = false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, skuCode, NumUtil.toString(catalogService
            .getReservationAvailableStock(shopCode, skuCode))));
        break;
      } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
        valid = false;
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, skuCode));
        break;
      } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
        valid = true;
      }
    }
    return valid;
  }

  // 对象商品类型(包含,仅有check)
  public boolean conditionFlgValidation(Long conditionFlg, List<String> orderCommodityList, List<String> mutipleCommodityList) {
    boolean valid = false;
    if (CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue().equals(conditionFlg)) {
      for (String orderCommodityCode : orderCommodityList) {
        for (String mutipleCommodityCode : mutipleCommodityList) {
          if (orderCommodityCode.equals(mutipleCommodityCode)) {
            valid = true;
            break;
          }
        }
        if (valid) {
          break;
        }
      }
    } else if (CampaignConditionFlg.CAMPAIGN_CONDITION_ONLY.longValue().equals(conditionFlg)) {
      valid = false;
      for (String orderCommodityCode : orderCommodityList) {
        for (String mutipleCommodityCode : mutipleCommodityList) {
          if (orderCommodityCode.equals(mutipleCommodityCode)) {
            valid = true;
            break;
          }
        }
        if (!valid) {
          break;
        }
      }
    }
    return valid;
  }

  // 最小在库数check
  public boolean minStockValidation() {
    boolean valid = true;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    List<DeliveryBean> deliveryList = getBean().getDeliveryList();
    for (DeliveryBean delivery : deliveryList) {
      List<OrderCommodityBean> orderCommodityList = delivery.getOrderCommodityList();
      for (OrderCommodityBean orderCommodity : orderCommodityList) {
        Long amount = getAllPurchasingAmount(getBean().getDeliveryList(), orderCommodity.getShopCode(), orderCommodity.getSkuCode());
        Long minStockCount = amount;
        if (orderCommodity.isSetCommodityFlg()) {
          if (StringUtil.hasValue(orderCommodity.getOriginalCommodityCode()) && orderCommodity.getCombinationAmount() != null) {
            minStockCount = catalogService.getAvailableStock(orderCommodity.getShopCode(), orderCommodity
                .getOriginalCommodityCode(), true, getCompositionSkuCodeList(orderCommodity.getCompositionList()),
                getGiftSkuCodeList(orderCommodity.getCommodityGiftList()));
            minStockCount = new Double(Math.floor(minStockCount / orderCommodity.getCombinationAmount())).longValue();
          } else {
            minStockCount = catalogService.getAvailableStock(orderCommodity.getShopCode(), orderCommodity.getSkuCode(), true,
                getCompositionSkuCodeList(orderCommodity.getCompositionList()), getGiftSkuCodeList(orderCommodity
                    .getCommodityGiftList()));
          }
        } else {
          if (StringUtil.hasValue(orderCommodity.getOriginalCommodityCode()) && orderCommodity.getCombinationAmount() != null) {
            minStockCount = catalogService.getAvailableStock(orderCommodity.getShopCode(), orderCommodity
                .getOriginalCommodityCode());
            minStockCount = new Double(Math.floor(minStockCount / orderCommodity.getCombinationAmount())).longValue();
          } else {
            minStockCount = catalogService.getAvailableStock(orderCommodity.getShopCode(), orderCommodity.getSkuCode());
          }
        }
        if (amount > minStockCount) {
          valid &= false;
          String skuCode = "";
          if (orderCommodity.isSetCommodityFlg()) {
            String[] tmp = orderCommodity.getSkuCode().split(":");
            if (tmp.length > 0) {
              skuCode = tmp[0];
            }
          } else {
            skuCode = orderCommodity.getSkuCode();
          }
          if (minStockCount.equals(0L)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.NO_AVAILABLE_STOCK_SKU, skuCode, NumUtil.toString(minStockCount)));
          } else {
            addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, skuCode, NumUtil.toString(minStockCount)));
          }
        }
      }
    }
    return valid;
  }

  // 折扣券使用限制check
  public boolean discountCouponLimtedValidation() {
    boolean valid = true;
    List<DeliveryBean> deliveryList = getBean().getDeliveryList();
    for (DeliveryBean delivery : deliveryList) {
      List<OrderCommodityBean> orderCommodityList = delivery.getOrderCommodityList();
      for (OrderCommodityBean orderCommodity : orderCommodityList) {
        String campaignCode = orderCommodity.getDiscountCouponCode();
        if (StringUtil.isNullOrEmpty(campaignCode)) {
          continue;
        }
        CampaignMainDao mainDao = DIContainer.getDao(CampaignMainDao.class);
        CampaignMain main = mainDao.load(campaignCode);
        // 促销活动不存在
        if (null == main) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_NOT_EXIST, Messages
              .getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
          return false;
        }
        // 促销活动期间不是进行中
        if (!DateUtil.isPeriodDate(main.getCampaignStartDate(), main.getCampaignEndDate())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_NOT_EXIST, Messages
              .getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
          return false;
        }
        // 促销行为类型不是商品优惠券
        if (!CampaignMainType.SALE_OFF.longValue().equals(main.getCampaignType())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_NOT_EXIST, Messages
              .getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
          return false;
        }

        CampaignConditionDao conditionDao = DIContainer.getDao(CampaignConditionDao.class);
        CampaignCondition condition = conditionDao.load(campaignCode);
        if (null == condition) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_NOT_EXIST, Messages
              .getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
          return false;
        }
        // 判断折扣券的对象商品限定件数
        if (condition.getMaxCommodityNum() != null) {
          if (condition.getMaxCommodityNum() > Long.parseLong(orderCommodity.getPurchasingAmount())) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_MAX_COMMODITY_ERROR, condition.getMaxCommodityNum()
                .toString(), campaignCode));
            return false;
          }
        }

        // 判断折扣券的最大使用回数
        Long count = 0L;
        Map<String, Long> discountCouponUsedMap = getBean().getDiscountCouponUsedMap();
        if (!discountCouponUsedMap.isEmpty()) {
          if (discountCouponUsedMap.containsKey(campaignCode)) {
            count = discountCouponUsedMap.get(campaignCode);
          }
        }

        OrderService orderSvr = ServiceLocator.getOrderService(getLoginInfo());
        count += orderSvr.getCampaignDiscountUsedCount(campaignCode, "");
        if (condition.getUseLimit() != null) {
          if (count > condition.getUseLimit()) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.DISCOUNT_MAX_USED_ERROR, campaignCode, Messages
                .getString("web.action.back.order.NeworderShippingDiscountCouponAction.0")));
            return false;
          }
        }
      }
    }
    return valid;
  }

  public List<String> getCompositionSkuCodeList(List<CompositionItem> compositionList) {
    List<String> compositionSkuCodeList = new ArrayList<String>();
    for (CompositionItem setItem : compositionList) {
      String skuCode = setItem.getSkuCode();
      compositionSkuCodeList.add(skuCode);
    }
    return compositionSkuCodeList;
  }

  public List<String> getGiftSkuCodeList(List<GiftItemBean> giftList) {
    List<String> giftSkuCodeList = new ArrayList<String>();
    for (GiftItemBean item : giftList) {
      String skuCode = item.getGiftSkuCode();
      giftSkuCodeList.add(skuCode);
    }
    return giftSkuCodeList;
  }

  public List<String> getCashierGiftSkuCodeList(List<GiftItem> giftList) {
    List<String> giftSkuCodeList = new ArrayList<String>();
    for (GiftItem item : giftList) {
      String skuCode = item.getGiftSkuCode();
      giftSkuCodeList.add(skuCode);
    }
    return giftSkuCodeList;
  }

  public List<String> getCommodityCodeList() {
    List<String> commodityCodeList = new ArrayList<String>();
    for (AddCommodityBean commodity : getBean().getAddCommodityList()) {
      commodityCodeList.add(commodity.getCommodityCode());
    }

    return commodityCodeList;
  }

  // 2012-11-23 促销对应 ob add end

  /**
   * 商品追加情報生成(選択用)
   * 
   * @param addCommodityBean
   * @return commoditySelectList
   */
  private List<CodeAttribute> createAddSelectCommodityList(List<AddCommodityBean> addCommodityBean) {
    List<CodeAttribute> commoditySelectList = new ArrayList<CodeAttribute>();

    for (AddCommodityBean useableCommodity : addCommodityBean) {
      NameValue value = new NameValue(useableCommodity.getDisplayName(), useableCommodity.getCommodityKey());
      commoditySelectList.add(value);
    }

    return commoditySelectList;
  }

  /**
   * アドレス一覧生成(チェック用)
   * 
   * @param cashier
   * @return addressList
   */
  public List<AddAddressBean> createAddressCheckList(Cashier cashier) {
    List<AddAddressBean> addressList = new ArrayList<AddAddressBean>();

    String customerCode = cashier.getCustomer().getCustomerCode();
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    for (CustomerAddress address : service.getCustomerAddressList(customerCode)) {
      AddAddressBean addAddress = new AddAddressBean();
      addAddress.setAddressNo(Long.toString(address.getAddressNo()));
      addAddress.setAddressAlias(address.getAddressAlias());
      addAddress.setAddressFirstName(address.getAddressFirstName());
      addAddress.setAddressLastName(address.getAddressLastName());
      addAddress.setAddressFirstNameKana(address.getAddressFirstNameKana());
      addAddress.setAddressLastNameKana(address.getAddressLastNameKana());
      addAddress.setPostalCode1(address.getPostalCode());
      addAddress.setPostalCode2(address.getPostalCode());
      if (StringUtil.hasValue(address.getPostalCode())) {
        if (address.getPostalCode().length() < 3) {
          addAddress.setPostalCode1(address.getPostalCode());
          addAddress.setPostalCode2("");
        } else {
          addAddress.setPostalCode1(address.getPostalCode().substring(0, 3));
          addAddress.setPostalCode2(address.getPostalCode().substring(3));
        }
      }
      addAddress.setPrefectureCode(address.getPrefectureCode());
      addAddress.setAddress1(address.getAddress1());
      addAddress.setAddress2(address.getAddress2());
      addAddress.setAddress3(address.getAddress3());
      addAddress.setAddress4(address.getAddress4());
      addAddress.setCityCode(address.getCityCode());
      addAddress.setPhoneNumber(address.getPhoneNumber());
      addAddress.setMobileNumber(address.getMobileNumber());
      addAddress.setAreaCode(address.getAreaCode());
      addressList.add(addAddress);
    }

    return addressList;

  }

  /**
   * 支払方法が代金引換のみの場合に、Cashier内の配送先が複数あるかどうかを検証します。
   * 
   * @return 合計金額が0円、または残ポイントが合計金額を上回る:false<br>
   *         配送先が複数存在する:true
   */
  public boolean isCashOnDeliveryOnly() {
    if (BigDecimalUtil.isBelow(getBean().getCashier().getGrandTotalPrice(), BigDecimal.ONE)) {
      return false;
    }

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    PointRule pointRule = service.getPointRule();
    boolean hasNoPoint = false;
    if (pointRule.getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.ENABLED.longValue())) {
      if (BigDecimalUtil.isAboveOrEquals(getBean().getCashier().getCustomer().getRestPoint(), getBean().getCashier()
          .getGrandTotalPrice())) {
        return false;
      } else {
        hasNoPoint = true;
      }
    }

    PaymentSupporter paymentSupporter = new PaymentSupporter(LoginType.BACK);
    PaymentMethodBean paymentMethodBean = paymentSupporter.createPaymentMethodBean(getBean().getCashier().getPayment()
        .getShopCode());

    if (paymentMethodBean.getDisplayPaymentList().size() == 1
        && paymentMethodBean.getDisplayPaymentList().get(0).getPaymentMethodType().equals(
            PaymentMethodType.CASH_ON_DELIVERY.getValue())) {

      if (getBean().getCashier().getShippingList().size() > 1) {
        if (hasNoPoint) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.CASH_ON_DELIVERY_SELECT_AND_NO_POINT_ERROR));
        } else {
          addErrorMessage(WebMessage.get(OrderErrorMessage.CASH_ON_DELIVERY_SELECT_ERROR));
        }
        return true;
      }

      for (CashierShipping cs : getBean().getCashier().getShippingList()) {
        if (!cs.getAddress().getAddressNo().equals(0L)) {
          if (hasNoPoint) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.CASH_ON_DELIVERY_SELECT_AND_NO_POINT_ERROR));
          } else {
            addErrorMessage(WebMessage.get(OrderErrorMessage.CASH_ON_DELIVERY_SELECT_ERROR));
          }
          return true;
        }
      }
    }
    return false;
  }

  /***
   * Cshier情報とAddAddressBeanからCustomerAddressを生成します。
   * 
   * @param cashier
   * @param addAddress
   * @return
   */
  public CustomerAddress copyCustomerAddress(Cashier cashier, AddAddressBean addAddress) {
    CustomerAddress address = new CustomerAddress();
    address.setCustomerCode(cashier.getCustomer().getCustomerCode());
    address.setAddressNo(NumUtil.toLong(addAddress.getAddressNo()));
    address.setAddressAlias(addAddress.getAddressAlias());
    address.setAddressLastName(addAddress.getAddressLastName());
    address.setAddressFirstName(addAddress.getAddressFirstName());
    address.setAddressLastNameKana(addAddress.getAddressLastNameKana());
    address.setAddressFirstNameKana(addAddress.getAddressFirstNameKana());
    address.setPostalCode(addAddress.getPostalCode1() + addAddress.getPostalCode2());
    address.setPrefectureCode(addAddress.getPrefectureCode());
    address.setAddress1(addAddress.getAddress1());
    address.setAddress2(addAddress.getAddress2());
    address.setAddress3(addAddress.getAddress3());
    address.setAddress4(addAddress.getAddress4());
    address.setPhoneNumber(addAddress.getPhoneNumber());
    address.setCityCode(addAddress.getCityCode());
    address.setMobileNumber(addAddress.getMobileNumber());
    address.setAreaCode(addAddress.getAreaCode());
    return address;
  }

  public static class ShippingComparator implements Comparator<CashierShipping>, Serializable {

    private static final long serialVersionUID = 1L;

    public int compare(CashierShipping o1, CashierShipping o2) {
      // アドレスNo・ショップコード・配送種別の順でソート
      if (o1.getAddress().getAddressNo().equals(o2.getAddress().getAddressNo())) {
        if (o1.getShopCode().equals(o2.getShopCode())) {
          return o1.getDeliveryType().getDeliveryTypeNo().compareTo(o2.getDeliveryType().getDeliveryTypeNo());
        } else {
          return o1.getShopCode().compareTo(o2.getShopCode());
        }
      } else {
        return o1.getAddress().getAddressNo().compareTo(o2.getAddress().getAddressNo());
      }
    }

  }

  // 发票信息
  /**
   * cashierから画面情報を設定します
   */
  public void createCashierFromDisplay() {
    NeworderShippingBean bean = getBean();

    Cashier cashier = bean.getCashier();
    // 发票信息设定
    CashierInvoice invoice = new CashierInvoice();
    CashierInvoiceBase invoiceInfo = new CashierInvoiceBase();
    invoiceInfo.setCommodityName(bean.getOrderInvoice().getInvoiceCommodityName());
    invoiceInfo.setInvoiceType(bean.getOrderInvoice().getInvoiceType());
    invoiceInfo.setCompanyName(bean.getOrderInvoice().getInvoiceCompanyName());
    invoiceInfo.setTaxpayerCode(bean.getOrderInvoice().getInvoiceTaxpayerCode());
    invoiceInfo.setAddress(bean.getOrderInvoice().getInvoiceAddress());
    invoiceInfo.setTel(bean.getOrderInvoice().getInvoiceTel());
    invoiceInfo.setBankName(bean.getOrderInvoice().getInvoiceBankName());
    invoiceInfo.setBankNo(bean.getOrderInvoice().getInvoiceBankNo());
    invoiceInfo.setInvoiceSaveFlg(bean.getOrderInvoice().getInvoiceSaveFlg());
    invoiceInfo.setCustomerName(bean.getOrderInvoice().getInvoiceCustomerName());
    if (StringUtil.hasValue(bean.getOrderInvoice().getInvoiceFlg())
        && bean.getOrderInvoice().getInvoiceFlg().equals(InvoiceFlg.NEED.getValue())) {
      invoice.setInvoiceFlg(InvoiceFlg.NEED.getValue());
      if (bean.getOrderInvoice().getInvoiceType().equals(InvoiceType.VAT.getValue())) {
        invoiceInfo.setInvoiceType(InvoiceType.VAT.getValue());
      } else {
        invoiceInfo.setInvoiceType(InvoiceType.USUAL.getValue());
      }
    } else {
      invoice.setInvoiceFlg(InvoiceFlg.NO_NEED.getValue());
    }
    invoice.setInvoiceInfo(invoiceInfo);
    cashier.setInvoice(invoice);
  }

  public InvoiceBean createInvoice(Cashier cashier) {
    InvoiceBean invoiceBean = new InvoiceBean();
    CashierInvoice cashierInvoice = cashier.getInvoice();
    if (cashierInvoice != null) {
      invoiceBean.setInvoiceFlg(cashierInvoice.getInvoiceFlg());
      invoiceBean.setInvoiceCommodityName(cashierInvoice.getInvoiceInfo().getCommodityName());
      invoiceBean.setInvoiceType(cashierInvoice.getInvoiceInfo().getInvoiceType());
      invoiceBean.setInvoiceCustomerName(cashierInvoice.getInvoiceInfo().getCustomerName());
      invoiceBean.setInvoiceCompanyName(cashierInvoice.getInvoiceInfo().getCompanyName());
      invoiceBean.setInvoiceTaxpayerCode(cashierInvoice.getInvoiceInfo().getTaxpayerCode());
      invoiceBean.setInvoiceAddress(cashierInvoice.getInvoiceInfo().getAddress());
      invoiceBean.setInvoiceTel(cashierInvoice.getInvoiceInfo().getTel());
      invoiceBean.setInvoiceBankName(cashierInvoice.getInvoiceInfo().getBankName());
      invoiceBean.setInvoiceBankNo(cashierInvoice.getInvoiceInfo().getBankNo());
      invoiceBean.setInvoiceSaveFlg(cashierInvoice.getInvoiceInfo().getInvoiceSaveFlg());
      if (!cashierInvoice.getInvoiceFlg().equals(InvoiceFlg.NEED.getValue())) {
        invoiceBean.setInvoiceFlg(InvoiceFlg.NO_NEED.getValue());
        invoiceBean.setInvoiceType(InvoiceType.USUAL.getValue());
      }
    } else {
      OrderService service = ServiceLocator.getOrderService(getLoginInfo());
      CustomerVatInvoice customerVatInvoice = service.getCustomerVatInvoice(cashier.getCustomer().getCustomerCode());
      if (customerVatInvoice != null) {
        invoiceBean.setInvoiceCompanyName(customerVatInvoice.getCompanyName());
        invoiceBean.setInvoiceTaxpayerCode(customerVatInvoice.getTaxpayerCode());
        invoiceBean.setInvoiceAddress(customerVatInvoice.getAddress());
        invoiceBean.setInvoiceTel(customerVatInvoice.getTel());
        invoiceBean.setInvoiceBankName(customerVatInvoice.getBankName());
        invoiceBean.setInvoiceBankNo(customerVatInvoice.getBankNo());
      }
      invoiceBean.setInvoiceFlg(InvoiceFlg.NO_NEED.getValue());
      invoiceBean.setInvoiceType(InvoiceType.USUAL.getValue());
    }

    return invoiceBean;
  }

  // 优惠种别取得
  public List<CodeAttribute> createDiscountTypeList(Cashier cashier) {
    List<CodeAttribute> discountTypeList = new ArrayList<CodeAttribute>();
    CustomerGroupCampaign customerGroupCampaign = getCustomerGroupCampaign(cashier);
    if (customerGroupCampaign != null) {
      discountTypeList.add(new NameValue(DiscountType.CUSTOMER.getName(), DiscountType.CUSTOMER.getValue()));
    }
    discountTypeList.add(new NameValue(DiscountType.COUPON.getName(), DiscountType.COUPON.getValue()));
    return discountTypeList;
  }
  // 会员折扣取得
  public CustomerGroupCampaign createCustomerDiscount(Cashier cashier){
    CustomerGroupCampaign customerGroupCampaign = getCustomerGroupCampaign(cashier);
    return customerGroupCampaign;
  }

  // 顾客种别优惠获得
  public CustomerGroupCampaign getCustomerGroupCampaign(Cashier cashier) {
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    String shopCode = cashier.getPayment().getShopCode();
    String customerGroupCode = cashier.getCustomer().getCustomerGroupCode();
    CustomerGroupCampaign customerGroupCampaign = customerService.getCustomerGroupCampaign(shopCode, customerGroupCode);
    if (customerGroupCampaign != null) {
      if (BigDecimalUtil.isBelow(cashier.getTotalCommodityPrice(), customerGroupCampaign.getMinOrderAmount())) {
        customerGroupCampaign = null;
      }
    }
    return customerGroupCampaign;
  }

  // 根据Cashier设置优惠信息
  public void createDiscount(Cashier cashier) {
    NeworderShippingBean bean = getBean();
    CashierDiscount cashierDiscount = cashier.getDiscount();
    if (StringUtil.hasValue(cashierDiscount.getDiscountType())) {
      if (cashierDiscount.getDiscountType().equals(DiscountType.CUSTOMER.getValue())) {
        bean.setSelDiscountType(DiscountType.CUSTOMER.getValue());
      } else if (cashierDiscount.getDiscountType().equals(DiscountType.COUPON.getValue())) {
        bean.setSelDiscountType(DiscountType.COUPON.getValue());
        if (cashierDiscount.getCouponType().equals(CouponType.COMMON_DISTRIBUTION.getValue())) {
          bean.setPublicCouponCode(cashierDiscount.getDiscountCode());
        } else {
          bean.setSelPersonalCouponCode(cashierDiscount.getDiscountDetailCode());
        }
      }
    }
  }

  // 个人优惠券信息获得
  public List<CodeAttribute> createPersonalCouponList(Cashier cashier) {
    List<CodeAttribute> personalCouponList = new ArrayList<CodeAttribute>();
    personalCouponList.add(new NameValue("请选择", ""));

    List<MyCouponInfo> aviableCouponList = getAviableCouponList(cashier);
    for (MyCouponInfo newCouponHistory : aviableCouponList) {
      if (BigDecimalUtil.isAboveOrEquals(cashier.getTotalCommodityPrice(), newCouponHistory.getMinUseOrderAmount())) {
        personalCouponList.add(new NameValue(newCouponHistory.getCouponIssueNo(), newCouponHistory.getCouponIssueNo()));
      }
    }
    return personalCouponList;
  }

  public MyCouponInfo getAviableCouponSelected() {
    MyCouponInfo aviableCouponSelected = new MyCouponInfo();
    List<MyCouponInfo> aviableCouponList = getAviableCouponList(getBean().getCashier());

    if (aviableCouponList == null || aviableCouponList.size() < 1) {
      return new MyCouponInfo();
    }
    for (MyCouponInfo aviableCoupon : aviableCouponList) {
      if (aviableCoupon.getCouponIssueNo().equals(getBean().getSelPersonalCouponCode())) {
        aviableCouponSelected = aviableCoupon;
        break;
      }
    }

    return aviableCouponSelected;

  }

  /**
   * 可以使用的优惠券一览取得
   * 
   * @param shopCode
   * @return 可以使用的优惠券一览
   */
  public List<MyCouponInfo> getAviableCouponList(Cashier cashier) {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());

    List<MyCouponInfo> newCouponHistoryList = service.getMyCoupon(cashier.getCustomer().getCustomerCode());
    List<MyCouponInfo> result = new ArrayList<MyCouponInfo>();

    List<String> objectCommodity = new ArrayList<String>();

    for (MyCouponInfo newCouponHistory : newCouponHistoryList) {

      BigDecimal objectTotalPrice = BigDecimal.ZERO;
      BigDecimal objectTotalPriceTemp = BigDecimal.ZERO;

      // 朋友推荐 优惠券时没有USE_TYPE字段默认可使用
      if (StringUtil.isNullOrEmpty(newCouponHistory.getUseType())) {
        newCouponHistory.setUseType(0L);
      }

      List<NewCouponHistoryUseInfo> useCommodityList = service.getUseCommodityList(newCouponHistory.getCouponIssueNo());
      List<NewCouponHistoryUseInfo> brandList = service.getBrandCodeList(newCouponHistory.getCouponIssueNo());
      List<NewCouponHistoryUseInfo> importCommodityTypeList = service.getImportCommodityTypeList(newCouponHistory
          .getCouponIssueNo());
      List<NewCouponHistoryUseInfo> categoryList = service.getCategoryCodeList(newCouponHistory.getCouponIssueNo());
      boolean objectFlg = false;
      if (useCommodityList != null && useCommodityList.size() > 0) {
        objectFlg = true;
      }
      if (brandList != null && brandList.size() > 0) {
        objectFlg = true;
      }
      if (importCommodityTypeList != null && importCommodityTypeList.size() > 0) {
        objectFlg = true;
      }
      if (categoryList != null && categoryList.size() > 0) {
        objectFlg = true;
      }
      if (objectFlg) {
        objectCommodity = getObjectCommodity(cashier.getPayment().getShopCode(), newCouponHistory.getCouponIssueNo(),
            useCommodityList, brandList, importCommodityTypeList, categoryList);
      }

      // 指定商品的购买金额取得
      if (objectCommodity.size() > 0) {
        for (String commodityCode : objectCommodity) {
          for (CashierShipping cashierShipping : cashier.getShippingList()) {
            for (CartCommodityInfo commodity : cashierShipping.getCommodityInfoList()) {
              if (commodityCode.equals(commodity.getCommodityCode())) {
                objectTotalPrice = BigDecimalUtil.add(objectTotalPrice, BigDecimalUtil.multiply(commodity.getRetailPrice(),
                    commodity.getQuantity()));
              }
            }
          }
        }
      } else {
       // continue;
      }
      // 不使用
      if (newCouponHistory.getUseType().equals(1L)) {
        if (objectFlg) {
          objectTotalPriceTemp = getBean().getCashier().getTotalCommodityPrice().subtract(objectTotalPrice);
        } else {
          objectTotalPriceTemp = getBean().getCashier().getTotalCommodityPrice();
        }
        // 使用
      } else {
        if (objectFlg) {
          objectTotalPriceTemp = objectTotalPrice;
        } else {
          objectTotalPriceTemp = getBean().getCashier().getTotalCommodityPrice();
        }
      }

      // 优惠券利用最小购买金额
      if (BigDecimalUtil.isAboveOrEquals(objectTotalPriceTemp, newCouponHistory.getMinUseOrderAmount())) {
        newCouponHistory.setObjectTotalPrice(objectTotalPriceTemp);
        result.add(newCouponHistory);
      }
    }
    return result;
  }

  // 指定商品的购买金额取得
  public BigDecimal getTotalRelatePrice(NewCouponRule newCouponRule) {

    BigDecimal objectTotalPrice = BigDecimal.ZERO;
    BigDecimal objectTotalPriceTemp = BigDecimal.ZERO;
    
    if (newCouponRule.getObjectBrand() == null) {
      newCouponRule.setObjectBrand("");
    }
    String[] brands = newCouponRule.getObjectBrand().split(";");

    if (newCouponRule.getObjectCategory() == null) {
      newCouponRule.setObjectCategory("");
    }
    String[] categorys = newCouponRule.getObjectCategory().split(";");

    boolean partailFlg = false;
    if (brands != null && brands.length > 0 && StringUtil.hasValue(brands[0])) {
      partailFlg = true;
    }
    if (categorys != null && categorys.length > 0 && StringUtil.hasValue(categorys[0])) {
      partailFlg = true;
    }
    
    List<String> objectCommodity = new ArrayList<String>();
    if ( partailFlg &&  (newCouponRule.getUseType().equals(2L) || newCouponRule.getUseType().equals(3L))) {
      objectTotalPrice = getCommodityLimitObjectPrice(newCouponRule, brands, categorys);
      return objectTotalPrice;
    } else {
      objectCommodity =   getPublicObjectCommodity("00000000", newCouponRule);
    }
    
    // 指定商品的购买金额取得
    if (objectCommodity.size() > 0) {
      for (String commodityCode : objectCommodity) {
        for (CashierShipping cashierShipping : getBean().getCashier().getShippingList()) {
          for (CartCommodityInfo commodity : cashierShipping.getCommodityInfoList()) {
            if (commodityCode.equals(commodity.getCommodityCode())) {
                objectTotalPrice = BigDecimalUtil.add(objectTotalPrice, BigDecimalUtil.multiply(commodity.getRetailPrice(), NumUtil
                    .toLong(commodity.getQuantity()+"")));
            }
          }
        }
      }
    }
    if (StringUtil.isNullOrEmpty(newCouponRule.getUseType())) {
      newCouponRule.setUseType(0L);
    }
    if (newCouponRule.getUseType().equals(1L)) {
      objectTotalPriceTemp = getBean().getCashier().getTotalCommodityPrice().subtract(objectTotalPrice);
    } else {
      objectTotalPriceTemp = objectTotalPrice;
    }

    return objectTotalPriceTemp;
  }

  
  public BigDecimal getCommodityLimitObjectPrice(NewCouponRule rule,String[] brands,String[] categorys) {
    
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    List<String> objectCommodity = new ArrayList<String>();
    
    Map<String,List<CartCommodityInfo>> objectMap = new HashMap<String,List<CartCommodityInfo>>();
    for (CashierShipping cashierItem : getBean().getCashier().getShippingList()) {
      for (CartCommodityInfo commodity : cashierItem.getCommodityInfoList()) {
          CommodityHeader ch = cs.getCommodityHeader("00000000", commodity.getCommodityCode());
          
          //品牌对应
          for (int i = 0; i < brands.length; i++) {
            if (!objectCommodity.contains(ch.getCommodityCode()) && brands[i].split(":")[0].equals(ch.getBrandCode())) {
              objectCommodity.add(ch.getCommodityCode());
              if (objectMap.get(brands[i]) == null) {
                List<CartCommodityInfo> list = new ArrayList<CartCommodityInfo>();
                list.add(commodity);
                objectMap.put(brands[i], list);
              } else {
                objectMap.get(brands[i]).add(commodity);
              }
              break;
            }
          }
      
          // 分类对应
          String categoryPath = ch.getCategoryPath();
          categoryPath = categoryPath.replaceAll("/", "");
          categoryPath = categoryPath.replaceAll("#", "");
          String[] categoryDb = categoryPath.split("~");
          String keyCategory = "";
          boolean existCategory = false;
          if (StringUtil.hasValue(categoryPath)) {
            for (int i = 0; i < categorys.length; i++) {
              for (int j = 0; j < categoryDb.length; j++) {
                if (StringUtil.hasValue(categoryDb[j]) &&  categorys[i].split(":")[0].equals(categoryDb[j]) ) {
                  existCategory = true;
                  keyCategory = categorys[i];
                  break;
                }
              }
              if (existCategory) {
                break;
              }
            }
          } else {
            existCategory = false;
          }
    
          if (!objectCommodity.contains(ch.getCommodityCode()) && existCategory) {
            objectCommodity.add(ch.getCommodityCode());
            if (objectMap.get(keyCategory) == null) {
              List<CartCommodityInfo> list = new ArrayList<CartCommodityInfo>();
              list.add(commodity);
              objectMap.put(keyCategory, list);
            } else {
              objectMap.get(keyCategory).add(commodity);
            }
          }
      }
    }
    
    BigDecimal partialObjectPrice = BigDecimal.ZERO;
    BigDecimal allObjectPrice = BigDecimal.ZERO; 
    //拆开
    for (String s : objectMap.keySet()) {
      List<CartCommodityInfo> changeOneList = new ArrayList<CartCommodityInfo>();
      //把商品拆成单个数量加入集合
      for (CartCommodityInfo commodity : objectMap.get(s)) {
        for (int i = 0 ; i< commodity.getQuantity() ;i++) {
          allObjectPrice = BigDecimalUtil.add(allObjectPrice, commodity.getRetailPrice());
          changeOneList.add(commodity);
        }
      }
      //按照价格排序
      CartCommodityInfo scb = new CartCommodityInfo();
      for (int i=0 ; i < changeOneList.size() ; i++) {
        for (int j=i+1 ; j < changeOneList.size() ; j++) {
          if (BigDecimalUtil.isBelow(changeOneList.get(i).getRetailPrice(), changeOneList.get(j).getRetailPrice())){
            scb = changeOneList.get(i);
            changeOneList.set(i, changeOneList.get(j)) ;
            changeOneList.set(j, scb);
          }
        }
      }
      //计算符合条件部分商品总金额
      int objectNum = 0;
      if(StringUtil.hasValue(s.split(":")[1])) {
        objectNum = Integer.parseInt(s.split(":")[1]);
      } else {
        objectNum = 9999;
      }

      int bigNum = objectNum > changeOneList.size() ? changeOneList.size() : objectNum;
      
      for (int i = 0 ; i < bigNum ; i++) {
        partialObjectPrice = BigDecimalUtil.add(partialObjectPrice, changeOneList.get(i).getRetailPrice());
      }
      //objectMap.put(s, changeOneList);
    }
    
    if (rule.getUseType().equals(3L)) {
      partialObjectPrice = BigDecimalUtil.subtract(getBean().getCashier().getTotalCommodityPrice(), allObjectPrice.subtract(partialObjectPrice));
    }
    return partialObjectPrice;
  }
  
  
  
  
  
  public List<String> getPublicObjectCommodity(String shopCode, NewCouponRule useCommodity) {
    List<CommodityHeader> commodityListOfCart = new ArrayList<CommodityHeader>();
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    List<String> objectCommodity = new ArrayList<String>();

    for (CashierShipping cashierItem : getBean().getCashier().getShippingList()) {
      for (CartCommodityInfo commodity : cashierItem.getCommodityInfoList()) {
        commodityListOfCart.add(cs.getCommodityHeader(shopCode, commodity.getCommodityCode()));
      }
    }

    if (!StringUtil.hasValueAnyOf(useCommodity.getObjectCommodities(), useCommodity.getObjectBrand(), useCommodity
        .getObjectCategory())) {
      for (CommodityHeader ch : commodityListOfCart) {
        objectCommodity.add(ch.getCommodityCode());
      }
      return objectCommodity;
    }
    if (useCommodity.getObjectCommodities() == null) {
      useCommodity.setObjectCommodities("");
    }
    String[] commoditys = useCommodity.getObjectCommodities().split(";");

    if (useCommodity.getObjectBrand() == null) {
      useCommodity.setObjectBrand("");
    }
    String[] brands = useCommodity.getObjectBrand().split(";");

    if (useCommodity.getObjectCategory() == null) {
      useCommodity.setObjectCategory("");
    }
    String[] categorys = useCommodity.getObjectCategory().split(";");

    for (CommodityHeader ch : commodityListOfCart) {

      if (commoditys != null && commoditys[0] != null) {
        // 使用关联信息(商品编号)
        for (int i = 0; i < commoditys.length; i++) {
          if (!objectCommodity.contains(ch.getCommodityCode()) && commoditys[i].equals(ch.getCommodityCode())) {
            objectCommodity.add(ch.getCommodityCode());
            break;
          }
        }

        // 使用关联信息(品牌编号)
        for (int i = 0; i < brands.length; i++) {
          if (!objectCommodity.contains(ch.getCommodityCode()) && brands[i].split(":")[0].equals(ch.getBrandCode())) {
            objectCommodity.add(ch.getCommodityCode());
            break;
          }
        }

        // 使用关联信息(分类编号)
        String categoryPath = ch.getCategoryPath();
        categoryPath = categoryPath.replaceAll("/", "");
        categoryPath = categoryPath.replaceAll("#", "");
        String[] categoryDb = categoryPath.split("~");
        boolean existCategory = false;
        if (StringUtil.hasValue(categoryPath)) {
          for (int i = 0; i < categorys.length; i++) {
            for (int j = 0; j < categoryDb.length; j++) {
              if (categorys[i].split(":")[0].equals(categoryDb[j]) && StringUtil.hasValue(categoryDb[j])) {
                existCategory = true;
                break;
              }
            }
            if (existCategory) {
              break;
            }
          }
        } else {
          existCategory = true;
        }

        if (!objectCommodity.contains(ch.getCommodityCode()) && existCategory) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }
    }

    return objectCommodity;
  }
  

  /**
   * 购物车中满足优惠券使用规则（商品编号或品牌编号或商品区分品）的商品一览设定
   * 
   * @param shopCode
   * @param newCouponHistory
   *          优惠券发行履历信息
   * @param objectCommodity
   *          购物车中满足优惠券使用规则的商品一览
   */
  private List<String> getObjectCommodity(String shopCode, String couponIssueCode, List<NewCouponHistoryUseInfo> useCommodityList,
      List<NewCouponHistoryUseInfo> brandList, List<NewCouponHistoryUseInfo> importCommodityTypeList,
      List<NewCouponHistoryUseInfo> categoryList) {
    List<CommodityHeader> commodityListOfCart = new ArrayList<CommodityHeader>();
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    List<String> objectCommodity = new ArrayList<String>();

    for (CashierShipping cashierItem : getBean().getCashier().getShippingList()) {
      for (CartCommodityInfo commodity : cashierItem.getCommodityInfoList()) {
        commodityListOfCart.add(cs.getCommodityHeader(shopCode, commodity.getCommodityCode()));
      }
    }
    List<String> useCommodityCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo useCommodity : useCommodityList) {
      useCommodityCodeList.add(useCommodity.getCommodityCode());
    }

    List<String> brandCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo brand : brandList) {
      brandCodeList.add(brand.getBrandCode());
    }

    List<Long> importCommodityTypeCodeList = new ArrayList<Long>();
    for (NewCouponHistoryUseInfo importCommodityType : importCommodityTypeList) {
      importCommodityTypeCodeList.add(importCommodityType.getImportCommodityType());
    }

    List<String> caregoryCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo category : categoryList) {
      caregoryCodeList.add(category.getCategoryCode());
    }

    for (CommodityHeader ch : commodityListOfCart) {

      // 使用关联信息(商品编号)
      if (useCommodityCodeList != null && useCommodityCodeList.size() > 0 && useCommodityCodeList.contains(ch.getCommodityCode())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      // 使用关联信息(品牌编号)
      if (brandCodeList != null && brandCodeList.size() > 0 && brandCodeList.contains(ch.getBrandCode())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      // 使用关联信息(商品区分品)
      if (importCommodityTypeCodeList != null && importCommodityTypeCodeList.size() > 0
          && importCommodityTypeCodeList.contains(ch.getImportCommodityType())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      // 使用关联信息(分类编号)
      if (caregoryCodeList != null && caregoryCodeList.size() > 0) {
        String categoryPath = ch.getCategoryPath();
        categoryPath = categoryPath.replaceAll("/", "");
        categoryPath = categoryPath.replaceAll("#", "");
        String[] categorys = categoryPath.split("~");
        boolean existCategory = false;
        if (StringUtil.hasValue(categoryPath)) {
          existCategory = false;
        } else {
          existCategory = true;
        }
        for (String categoryCode : categorys) {
          if (caregoryCodeList.contains(categoryCode)) {
            existCategory = true;
            break;
          }
        }
        if (!objectCommodity.contains(ch.getCommodityCode()) && existCategory) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }
    }
    return objectCommodity;
  }

  // 2013/04/17 优惠券对应 ob add end

  //
  public NewCouponHistory getPersonalCoupon(String customerCode, String personalCouponCode, BigDecimal totalCommodityPrice) {
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    NewCouponHistory newCouponHistory = customerService.getUnusedPersonalCoupon(customerCode, personalCouponCode);
    if (newCouponHistory != null) {
      if (BigDecimalUtil.isBelow(totalCommodityPrice, newCouponHistory.getMinUseOrderAmount())) {
        newCouponHistory = null;
      }
    }

    return newCouponHistory;
  }

  public NewCouponRule getPublicCoupon(String publicCouponCode, BigDecimal totalCommodityPrice) {
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    NewCouponRule newCouponRule = customerService.getPublicCoupon(publicCouponCode);
    if (newCouponRule != null) {
      if (BigDecimalUtil.isBelow(totalCommodityPrice, newCouponRule.getMinUseOrderAmount())) {
        newCouponRule = null;
      }
    }

    return newCouponRule;
  }

  public NewCouponRule getPublicCoupon(String publicCouponCode) {
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    NewCouponRule newCouponRule = customerService.getPublicCoupon(publicCouponCode);

    return newCouponRule;
  }


  public void createDeliveryToBean(NeworderShippingBean bean) {
    ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
    UtilService utilService = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
    String shopCode = bean.getDeliveryList().get(0).getShopCode();
    String prefectureCode = bean.getDeliveryList().get(0).getPrefectureCode();
    // 2013/04/21 优惠券对应 ob add start
    String cityCode = bean.getDeliveryList().get(0).getCityCode();
    String areaCode = bean.getDeliveryList().get(0).getAreaCode();
    // 2013/04/21 优惠券对应 ob add end
    // 获得支付类型
    String paymentMethodType = "";
    if (StringUtil.isNullOrEmpty(bean.getOrderPayment().getPaymentMethodCode())) {
      // 如果支付方式未选择，则不表示配送公司列表，退出本方法
      return;
    } else {
      for (PaymentTypeBase ptb : bean.getOrderPayment().getDisplayPaymentList()) {
        if (ptb.getPaymentMethodCode().equals(bean.getOrderPayment().getPaymentMethodCode())) {
          paymentMethodType = ptb.getPaymentMethodType();
          break;
        }
      }
    }
    // 是否货到付款
    Boolean codFlg = paymentMethodType.equals(PaymentMethodType.CASH_ON_DELIVERY.getValue()) ? true : false;
    // 订单重量计算
    BigDecimal subTotalWeight = bean.getCashier().getTotalWeight();
    // 获得配送公司
    List<DeliveryCompany> dc = utilService.getDeliveryCompanys(shopCode, prefectureCode, cityCode, areaCode, codFlg, subTotalWeight
        .toString());
    // 2013/04/21 优惠券对应 ob update end

    // 封装List<DeliveryCompanyBean>
    List<DeliveryCompanyBean> lsDcb = new ArrayList<DeliveryCompanyBean>();
    DeliveryCompanyBean cacheDCBean = new DeliveryCompanyBean();
    cacheDCBean.setDeliveryCompanyNo("D000");
    for (DeliveryCompany deliveryCompany : dc) {
      if (!cacheDCBean.getDeliveryCompanyNo().equals(deliveryCompany.getDeliveryCompanyNo())) {
        DeliveryCompanyBean dcb = new DeliveryCompanyBean();
        dcb.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
        dcb.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
        lsDcb.add(dcb);
      }
      cacheDCBean.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
    }
    // 设置Bean可以指定的配送公司列表
    bean.setDeliveryCompanyList(lsDcb);
    BigDecimal subTotalPrice = bean.getCashier().getTotalCommodityPrice();
    DeliveryCompanyBean dcBean = new DeliveryCompanyBean();
    if (lsDcb.size() > 1) {
      String strDeliveryCompanyNo = "";
      BigDecimal deliveryCharge = BigDecimal.ZERO;
      for (DeliveryCompanyBean dcb : lsDcb) {
        BigDecimal cacheDeliveryCharge = BigDecimal.ZERO;
        cacheDeliveryCharge = shopService.getShippingCharge(prefectureCode, subTotalPrice, subTotalWeight, dcb
            .getDeliveryCompanyNo());
        // 循环第一轮时，为运费deliveryCharge赋值
        if (BigDecimalUtil.equals(deliveryCharge, BigDecimal.ZERO)) {
          deliveryCharge = cacheDeliveryCharge;
        }
        // 两条以上的配送公司，默认选择运费最低的
        if (BigDecimalUtil.isBelowOrEquals(cacheDeliveryCharge, deliveryCharge)) {
          deliveryCharge = cacheDeliveryCharge;
          strDeliveryCompanyNo = dcb.getDeliveryCompanyNo();
        }
      }
      dcBean.setDeliveryCompanyNo(strDeliveryCompanyNo);
      bean.setSelectedDeliveryCompany(dcBean);
    }

    if (bean.getCashier().getDelivery() != null) {
      dcBean.setDeliveryCompanyNo(bean.getCashier().getDelivery().getDeliveryCompanyCode());
      bean.setSelectedDeliveryCompany(dcBean);
    }

  }

}
