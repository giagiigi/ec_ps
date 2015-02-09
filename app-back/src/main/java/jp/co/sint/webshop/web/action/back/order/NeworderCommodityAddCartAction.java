package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.message.MessageType;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.SetCommodityUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.NumberLimitPolicy;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityBean.CommodityListDetailBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityBean.InputCartBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityCompositionBean.CommodityCompositionDetailBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020110:新規受注(商品選択)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCommodityAddCartAction extends NeworderCommodityBaseAction {

  /**
   * 初期処理を実行します。
   */
  @Override
  public void init() {
    if (getConfig().isOne()) {
      NeworderCommodityBean bean = getBean();
      bean.getInputCartList().setInputShopCode(getLoginInfo().getShopCode());

      setBean(bean);
    }
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // 2012/11/20 促销对应 新建订单_商品选择 ob upd start
    String shopCode = "";
    String skuCode = "";
    String campaignCode = "";
    String actionFlg = "";
    boolean valid = true;
    String[] tmp = getRequestParameter().getPathArgs();

    if (tmp.length > 2) {
      actionFlg = tmp[2];
    }

    // 可领取赠品时不进行验证
    if (!StringUtil.hasValue(actionFlg)) {
      valid = validateBean(getBean().getInputCartList());
    }

    // ショップ個別決済で同一決済にならない場合はエラーとする
    shopCode = getBean().getInputCartList().getInputShopCode();
    skuCode = getBean().getInputCartList().getInputSkuCode();
    if (tmp.length > 1) {
      shopCode = tmp[0];
      skuCode = tmp[1];
    }

    if (tmp.length > 3) {
      campaignCode = tmp[3];
    }
    // 2012/11/24 促销对应 新建订单_商品选择 ob upd end

    // 10.1.4 10145 追加 ここから
    if (StringUtil.isNullOrEmpty(shopCode)) {
      return false;
    }
    // 10.1.4 10145 追加 ここまで

    // ショップ存在チェック
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    Shop shop = shopService.getShop(shopCode);
    if (shop == null) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.SHOP_NOT_FOUND));
      valid = false;
    }

    if (getConfig().getOperatingMode() == OperatingMode.SHOP && valid) {
      Cart cart = getCart();
      // 既にカートに商品が入っていて、別ショップの商品だった場合エラー
      // 予約の場合、getでは取得できないので、２商品予約不可となる
      CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
      boolean reserve = catalogService.isReserve(shopCode, skuCode);
      if (cart.getItemCount() > 0 && cart.get(shopCode).size() < 1 && cart.getReserve(shopCode, skuCode) == null) {
        if (reserve) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.ADD_RESERVE_PAYMENT_NOT_FOUND_SHOP));
        } else {
          addErrorMessage(WebMessage.get(OrderErrorMessage.ADD_SALES_PAYMENT_NOT_FOUND_SHOP));
        }
        valid = false;
      }
    }
    if (!valid) {
      return valid;
    }

    // 在庫数チェック
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    // 2012/12/03 促销对应 新建订单_商品选择 ob add start
    CommodityInfo commodityInfo = catalogService.getSkuInfo(shopCode, skuCode);
    if (commodityInfo == null) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
      return false;
    }
    // 2012/12/03 促销对应 新建订单_商品选择 ob add end

    boolean reserve = catalogService.isReserve(shopCode, skuCode);
    CartItem cartItem;
    if (reserve) {
      cartItem = getCart().getReserve(shopCode, skuCode);
    } else {
      cartItem = getCart().get(shopCode, skuCode);
    }
    int quantity = 0;
    if (cartItem != null) {
      quantity = cartItem.getQuantity();
    }

    // 2012/11/23 促销对应 新建订单_商品选择 ob upd start
    if (commodityInfo != null) {
      // 不是套餐商品或多关联赠品的时候
      if (!WebFrameworkConstants.ADD_CART_COMMODITY_GIFT.equals(actionFlg)
          && !(WebFrameworkConstants.ADD_CART_COMMODITY_COMPOSITION.equals(actionFlg) || SetCommodityFlg.OBJECTIN.longValue()
              .equals(commodityInfo.getHeader().getSetCommodityFlg()))) {
        valid &= commodityValidation(shopCode, skuCode, quantity + 1);
      }
    }

    List<Sku> addCompositionList = new ArrayList<Sku>();
    for (CommodityCompositionDetailBean compositionBean : getBean().getCommodityCompositionBean()
        .getCommodityCompositionDetailBeanList()) {
      if (StringUtil.hasValue(compositionBean.getSelectedSkuCode())) {
        addCompositionList.add(new Sku(compositionBean.getChildShopCode(), compositionBean.getSelectedSkuCode()));
      }
    }
    getBean().setSelectedCompositionList(addCompositionList);
    CommodityListDetailBean row = new CommodityListDetailBean();

    if (commodityInfo != null && valid) {
      String commodityName = commodityInfo.getHeader().getCommodityName();
      commodityName += createStandardDetailName(commodityInfo.getDetail().getStandardDetail1Name(), commodityInfo.getDetail()
          .getStandardDetail2Name());

      // 设置套餐详细信息显示区分
      if (SetCommodityFlg.OBJECTIN.longValue().equals(commodityInfo.getHeader().getSetCommodityFlg())) {
        getBean().setCommodityComposition(true);
      } else {
        getBean().setCommodityComposition(false);
      }

      // 普通商品时
      if (!StringUtil.hasValue(actionFlg)
          && !SetCommodityFlg.OBJECTIN.longValue().equals(commodityInfo.getHeader().getSetCommodityFlg())) {
        int checkStockQuantity = quantity + 1;

        // 在库数验证
        if (!minAvailableStockCheck(shopCode, skuCode, row)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NO_AVAILABLE_STOCK_SKU, skuCode));
          return false;
        } else if (row.getStockQuantity().intValue() < checkStockQuantity) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, String.valueOf(row
              .getStockQuantity().intValue())));
          return false;
          // 20130625 txw add start
        } else {
          if (getCart().get().size() > 0) {
            Long allCount = 0L;
            Long allStock = 0L;
            if (StringUtil.hasValue(commodityInfo.getHeader().getOriginalCommodityCode())
                && commodityInfo.getHeader().getCombinationAmount() != null) {
              allStock = catalogService.getAvailableStock(commodityInfo.getHeader().getShopCode(), commodityInfo.getHeader()
                  .getOriginalCommodityCode());
              allCount = (quantity + 1) * commodityInfo.getHeader().getCombinationAmount();
            } else {
              allStock = catalogService.getAvailableStock(commodityInfo.getHeader().getShopCode(), skuCode);
              allCount = quantity + 1L;
            }

            for (CartItem item : getCart().get()) {
              if (!item.getCommodityCode().equals(skuCode)) {
                boolean rel = false;
                if (StringUtil.hasValue(commodityInfo.getHeader().getOriginalCommodityCode())) {
                  rel = (commodityInfo.getHeader().getOriginalCommodityCode().equals(item.getCommodityCode()) || (StringUtil.hasValue(item
                      .getOriginalCommodityCode()) && commodityInfo.getHeader().getOriginalCommodityCode().equals(item.getOriginalCommodityCode())));
                } else {
                  rel = (StringUtil.hasValue(item.getOriginalCommodityCode()) && commodityInfo.getHeader().getCommodityCode().equals(
                      item.getOriginalCommodityCode()));
                }
                
                if (rel) {
                  if (StringUtil.hasValue(item.getOriginalCommodityCode()) && item.getCombinationAmount() != null) {
                    allCount = allCount + item.getQuantity() * item.getCombinationAmount();
                  } else {
                    allCount = allCount + item.getQuantity();
                  }
                }
              }
            }

            if (allCount > allStock) {
              addErrorMessage("可用库存不足。");
              return false;
            }
          }
        }
        // 20130625 txw add end
      } else if (!StringUtil.hasValue(actionFlg)
          && SetCommodityFlg.OBJECTIN.longValue().equals(commodityInfo.getHeader().getSetCommodityFlg())) {
        // 不放入购物车（套餐详细信息显示）时
        // 套餐商品时,存在验证
        if (!catalogService.isExistsSetCommodityComposition(shopCode, commodityInfo.getHeader().getCommodityCode())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.SET_COMMODITY_COMPOSITION_NOT_EXIST_ERROR, commodityName));
          return false;
        }
        row = new CommodityListDetailBean();
        int checkStockQuantity = 1;

        // 在库数验证
        if (!minAvailableStockCheck(shopCode, skuCode, row) || row.getStockQuantity().intValue() < checkStockQuantity) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NO_AVAILABLE_STOCK_SKU, skuCode));
          return false;
        }
        return true;
      } else if (StringUtil.hasValue(actionFlg)
          && (WebFrameworkConstants.ADD_CART_COMMODITY_COMPOSITION.equals(actionFlg) || SetCommodityFlg.OBJECTIN.longValue()
              .equals(commodityInfo.getHeader().getSetCommodityFlg()))) {
        // 把套餐放入购物车时

        // 套餐可购买验证
        valid = compositionCommodityCheck(shopCode, commodityInfo, skuCode);

        if (!valid) {
          return false;
        }

      } else if (StringUtil.hasValue(actionFlg) && WebFrameworkConstants.ADD_CART_COMMODITY_GIFT.equals(actionFlg)) {
        // 验证可领取商品
        if (!checkMultipleGiftProcess(getBean(), shopCode, campaignCode, skuCode, false, commodityInfo)) {
          return false;
        }
      }

      // 普通商品sku单位可购买性验证
      if (!StringUtil.hasValue(actionFlg)
          && !SetCommodityFlg.OBJECTIN.longValue().equals(commodityInfo.getHeader().getSetCommodityFlg())) {
        valid = commonCommodityCheck(shopCode, skuCode, commodityInfo);
        if (!valid) {
          return false;
        }
      }

    }
    // 2012/11/23 促销对应 新建订单_商品选择 ob upd end

    // 限界値チェック
    Cart cart = getCart();
    NumberLimitPolicy numberPolicy = DIContainer.getNumberLimitPolicy();
    int totalQuantity = 0;
    BigDecimal totalCurrency = BigDecimal.ZERO;
    if (reserve) {
      if (cartItem != null) {
        totalCurrency = BigDecimalUtil.multiply(cartItem.getRetailPrice().add(cartItem.getGiftPrice()), cartItem.getQuantity());
        totalQuantity = cartItem.getQuantity();
      }
    } else {
      for (CartItem item : cart.get()) {
        totalCurrency = BigDecimalUtil.multiply(item.getRetailPrice().add(item.getGiftPrice()), item.getQuantity());
        totalQuantity = totalQuantity + item.getQuantity();
      }
    }

    totalQuantity = totalQuantity + 1;
    CommodityInfo skuInfo = catalogService.getSkuInfo(shopCode, skuCode);
    if (skuInfo == null) {
      return false;
    }
    totalCurrency = totalCurrency.add(skuInfo.getDetail().getUnitPrice());
    ValidationResult resultCurrency = numberPolicy.validTotalCurrency(totalCurrency);
    if (resultCurrency.isError()) {
      addErrorMessage(resultCurrency.getFormedMessage());
      valid = false;
    }
    ValidationResult resultAmount = numberPolicy.validTotalAmount(Long.valueOf(totalQuantity));
    if (resultAmount.isError()) {
      addErrorMessage(resultAmount.getFormedMessage());
      valid = false;
    }

    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    NeworderCommodityBean bean = (NeworderCommodityBean) getBean();

    String shopCode = bean.getInputCartList().getInputShopCode();
    String skuCode = bean.getInputCartList().getInputSkuCode();

    // 2012/11/20 促销对应 新建订单_商品选择 ob add start
    String campaignCode = "";
    String actionFlg = "";
    String[] tmp = getRequestParameter().getPathArgs();

    if (tmp.length > 1) {
      shopCode = tmp[0];
      skuCode = tmp[1];
    }

    if (tmp.length > 2) {
      actionFlg = tmp[2];
    }

    if (tmp.length > 3) {
      campaignCode = tmp[3];
    }

    // 套餐商品时
    if (bean.isCommodityComposition()) {
      if (!StringUtil.hasValue(actionFlg)) {
        // 设置套餐信息及详细商品一览信息
        setSetCommodityToBean(shopCode, skuCode, bean);
        setBean(bean);
        getBean().setCartCommodityList(createBeanFromCart());
        // 把购物车的可领取赠品显示到画面购物车list中
        getBean().setCartOtherGiftCommodityList(createBeanFromCartOfGift());
        // 设置可领取商品List
        multipleGiftProcess(bean, false, true);

        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      } else if (StringUtil.hasValue(actionFlg) && WebFrameworkConstants.ADD_CART_COMMODITY_COMPOSITION.equals(actionFlg)) {
        // 添加商品详细
        int totalQuantity = 0;
        String provisionalSkuCode = SetCommodityUtil.createProvisionalSkuCode(skuCode, bean.getSelectedCompositionList());
        for (CartItem item : getCart().get()) {
          if (item.getShopCode().equals(shopCode) && item.getSkuCode().equals(provisionalSkuCode)) {
            totalQuantity += item.getQuantity();
          }
        }
        getCart().set(shopCode, provisionalSkuCode, totalQuantity + 1, "");
        // 向购物车中添加套餐详细商品
        setCompositionToCart(bean, shopCode, provisionalSkuCode);
      }
    }
    // 2012/11/20 促销对应 新建订单_商品选择 ob add end
    // 2012/11/20 促销对应 新建订单_商品选择 ob upd start
    String skuCampaignCode = skuCode;
    // 可领取商品时
    if (StringUtil.hasValue(actionFlg) && WebFrameworkConstants.ADD_CART_COMMODITY_GIFT.equals(actionFlg)) {
      if (StringUtil.hasValue(campaignCode)) {
        skuCampaignCode = campaignCode + WebConstantCode.SKU_CODE_SEPARATOR + skuCode;
      }
    } else if (StringUtil.hasValue(actionFlg) && WebFrameworkConstants.ADD_CART_COMMODITY_COMPOSITION.equals(actionFlg)) {
      String provisionalSkuCode = SetCommodityUtil.createProvisionalSkuCode(skuCode, bean.getSelectedCompositionList());
      skuCode = provisionalSkuCode;
    }

    // 不是套餐商品时
    if (!StringUtil.hasValue(actionFlg) || !WebFrameworkConstants.ADD_CART_COMMODITY_COMPOSITION.equals(actionFlg)) {
      if (!getCart().add(shopCode, skuCampaignCode, 1)) {
        // 在庫引当エラー
        addErrorMessage(WebMessage.get(OrderErrorMessage.NO_AVAILABLE_STOCK_SKU, skuCode));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }
    }

    // 特定商品促销活动的赠品时
    if (!StringUtil.hasValue(actionFlg) || !WebFrameworkConstants.ADD_CART_COMMODITY_GIFT.equals(actionFlg)) {
      // 向购物车中设置特定商品促销活动的赠品
      setGiftCommodityToCart(shopCode, skuCode);
    }

    // 2012/11/20 促销对应 新建订单_商品选择 ob upd end
    // 予約が２つ以上もしくは、予約＋受注となった場合エラーにする
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    boolean reserve = service.isReserve(shopCode, skuCode);
    if (getCart().getReserve().size() > 1 || (getCart().getReserve().size() > 0 && getCart().get().size() > 0)) {
      MessageType addOrderError;
      if (getConfig().getOperatingMode() == OperatingMode.SHOP) {
        if (reserve) {
          addOrderError = OrderErrorMessage.ADD_RESERVE_PAYMENT_NOT_FOUND_SHOP;
        } else {
          addOrderError = OrderErrorMessage.ADD_SALES_PAYMENT_NOT_FOUND_SHOP;
        }
      } else {
        if (reserve) {
          addOrderError = OrderErrorMessage.ADD_RESERVE_PAYMENT_NOT_FOUND;
        } else {
          addOrderError = OrderErrorMessage.ADD_SALES_PAYMENT_NOT_FOUND;
        }
      }
      addErrorMessage(WebMessage.get(addOrderError));
      getCart().remove(shopCode, skuCode, "");
      // 20111215 shen update end
    }

    getBean().setCartCommodityList(createBeanFromCart());
    // 2012/11/22 促销对应 新建订单_商品选择 ob add start
    // 把购物车的可领取赠品显示到画面购物车list中
    getBean().setCartOtherGiftCommodityList(createBeanFromCartOfGift());
    // 设置可领取商品List
    multipleGiftProcess(getBean(), false, true);
    // 2012/11/22 促销对应 新建订单_商品选择 ob add end
    getBean().setInputCartList(new InputCartBean());
    setRequestBean(getBean());

    // 20140728 hdh 
    giftReset(getBean());
    
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderCommodityAddCartAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102011001";
  }

}
