package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.dao.CampaignDoingsDao;
import jp.co.sint.webshop.data.dao.CampaignMainDao;
import jp.co.sint.webshop.data.dao.SetCommodityCompositionDao;
import jp.co.sint.webshop.data.domain.CampaignConditionFlg;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.NewCampaignConditionType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignDoings;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.campain.CampaignMain;
import jp.co.sint.webshop.service.campain.CampainFilter;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.cart.CartUtil;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.CompositionItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.CommodityCompositionContainer;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.communication.CampaignLine;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SetCommodityUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.NumberLimitPolicy;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityBean.CartCommodityDetailListBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityBean.CartGiftCommodityDetailListBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityBean.CommodityListDetailBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityCompositionBean.CommodityCompositionDetailBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityGiftBean.GiftCommodityDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020110:新規受注(商品選択)の基底クラスです。
 * 
 * @author System Integrator Corp.
 */
public abstract class NeworderCommodityBaseAction extends NeworderBaseAction<NeworderCommodityBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return validateCommon(true, true);
  }

  /**
   * @param skuTotalChkFlg
   *          sku编号合计验证区分
   * @param multipleGiftSetFlg
   *          多关联商品重新设定区分
   * @return
   */
  public boolean validateCommon(boolean skuTotalChkFlg, boolean multipleGiftSetFlg) {
    boolean valid = true;
    CommodityAvailability commodityAvailability = null;
    String shopCode = null;
    String skuCode = null;
    BigDecimal totalCurrency = BigDecimal.ZERO;

    int totalQuantity = 0;
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    List<CartCommodityDetailListBean> cartList = getBean().getCartCommodityList();
    for (CartCommodityDetailListBean cart : cartList) {
      List<String> errorMessageList = BeanValidator.validate(cart).getErrorMessages();
      if (errorMessageList.size() > 0) {
        for (String s : errorMessageList) {
          addErrorMessage(MessageFormat.format(Messages.getString("web.action.back.order.NeworderCommodityBaseAction.0"), cart
              .getCommodityName(), s));
        }
        valid &= false;
        continue;
      }

      BigDecimal giftPrice = BigDecimal.ZERO;
      String commodityName = cart.getCommodityName();
      commodityName += createStandardDetailName(cart.getStandard1Name(), cart.getStandard2Name());
      shopCode = cart.getShopCode();
      skuCode = cart.getSkuCode();

      if (StringUtil.hasValue(cart.getGiftCode())) {
        Gift gift = service.getGift(cart.getShopCode(), cart.getGiftCode());
        if (gift != null) {
          giftPrice = gift.getGiftPrice();
        }
      }

      int quantity = NumUtil.toLong(cart.getPurchasingAmount()).intValue();

      totalCurrency = totalCurrency.add(BigDecimalUtil.multiply(NumUtil
          .parse(NumUtil.toLong(cart.getPurchasingAmount()).toString()), NumUtil.parse(cart.getRetailPrice()).add(giftPrice)));
      totalQuantity = totalQuantity + quantity;

      // 最小购买数check
      if (!CommodityType.GIFT.longValue().equals(cart.getCommodityType())) {
        if (SetCommodityFlg.OBJECTIN.longValue().equals(cart.getCommodityFlg())) {
          // 套餐商品
          List<String> compositionSkuCodeList = new ArrayList<String>();
          List<String> giftSkuCodeList = new ArrayList<String>();
          for (CompositionItem composition : cart.getCompositionItemList()) {
            if (StringUtil.hasValue(composition.getSkuCode())) {
              compositionSkuCodeList.add(composition.getSkuCode());
            }
          }

          if (cart.getGiftCommodityList() != null && cart.getGiftCommodityList().size() > 0) {
            for (CartGiftCommodityDetailListBean gift : cart.getGiftCommodityList()) {
              if (StringUtil.hasValue(gift.getSkuCode())) {
                giftSkuCodeList.add(gift.getSkuCode());
              }
            }
          }

          Long availableStock = service.getAvailableStock(shopCode, skuCode, true, compositionSkuCodeList, giftSkuCodeList);

          if (availableStock < NumUtil.toLong(cart.getPurchasingAmount(), 0L)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock.toString()));
            valid &= false;
          }
        } else {
          // 通常商品
          List<String> giftSkuCodeList = new ArrayList<String>();

          if (cart.getGiftCommodityList() != null && cart.getGiftCommodityList().size() > 0) {
            for (CartGiftCommodityDetailListBean gift : cart.getGiftCommodityList()) {
              if (StringUtil.hasValue(gift.getSkuCode())) {
                giftSkuCodeList.add(gift.getSkuCode());
              }
            }
          }
          // 20130625 txw update start
          Long availableStock = 0L;
          if (StringUtil.hasValue(cart.getOriginalCommodityCode()) && cart.getCombinationAmount() != null) {
            availableStock = service.getAvailableStock(shopCode, cart.getOriginalCommodityCode());
            availableStock = new Double(Math.floor(availableStock / cart.getCombinationAmount())).longValue();
          } else {
            availableStock = service.getAvailableStock(shopCode, skuCode);
          }
          // 20130625 txw update end
          if (availableStock < NumUtil.toLong(cart.getPurchasingAmount(), 0L)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock.toString()));
            valid &= false;
          }
        }

        // 套餐商品的可购买性check
        if (valid && SetCommodityFlg.OBJECTIN.longValue().equals(cart.getCommodityFlg())) {
          boolean isReserve = CartUtil.isReserve(getCart(), shopCode, skuCode);
          commodityAvailability = service.isAvailable(shopCode, skuCode, quantity, isReserve, true);

          if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, skuCode));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
            valid &= true;
          }
        }
      }
    }
    // 20130626 txw add start
    if (valid) {
      if (cartList.size() > 0) {
        for (CartCommodityDetailListBean cart : cartList) {
          Long allCount = 0L;
          Long allStock = 0L;
          Long quantity = NumUtil.toLong(cart.getPurchasingAmount());
          if (StringUtil.hasValue(cart.getOriginalCommodityCode()) && cart.getCombinationAmount() != null) {
            allStock = service.getAvailableStock(cart.getShopCode(), cart.getOriginalCommodityCode());
            allCount = quantity * cart.getCombinationAmount();
          } else {
            allStock = service.getAvailableStock(cart.getShopCode(), cart.getSkuCode());
            allCount = quantity;
          }

          for (CartCommodityDetailListBean item : cartList) {
            if (!item.getCommodityCode().equals(cart.getCommodityCode())) {
              boolean rel = false;
              if (StringUtil.hasValue(cart.getOriginalCommodityCode())) {
                rel = (cart.getOriginalCommodityCode().equals(item.getCommodityCode()) || (StringUtil.hasValue(item
                    .getOriginalCommodityCode()) && cart.getOriginalCommodityCode().equals(item.getOriginalCommodityCode())));
              } else {
                rel = (StringUtil.hasValue(item.getOriginalCommodityCode()) && cart.getCommodityCode().equals(
                    item.getOriginalCommodityCode()));
              }

              if (rel) {
                if (StringUtil.hasValue(item.getOriginalCommodityCode()) && item.getCombinationAmount() != null) {
                  allCount = allCount + NumUtil.toLong(item.getPurchasingAmount()) * item.getCombinationAmount();
                } else {
                  allCount = allCount + NumUtil.toLong(item.getPurchasingAmount());
                }
              }
            }
          }

          if (allCount > allStock) {
            addErrorMessage("可用库存不足,商品编号：" + cart.getCommodityCode());
            valid &= false;
          }
        }
      }
    }
    // 20130626 txw add end

    if (multipleGiftSetFlg) {
      // 设置可领取商品List
      multipleGiftProcess(getBean(), false, true);
    }

    // sku单位合计在库数验证
    if (skuTotalChkFlg) {
      // 各通常商品可购买性check
      Map<String, String> cartCommodityName = new HashMap<String, String>(); // 购物车中各通常商品的名称信息
      if (valid) {
        Map<String, Long> commodityAmountMap = getCartCommodityBeanTotalAmount(cartCommodityName);
        for (Object key : commodityAmountMap.keySet()) {
          commodityAvailability = service.isAvailable(shopCode, key.toString(), commodityAmountMap.get(key).intValue(), false);
          String commodityName = cartCommodityName.get(key);
          if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, key.toString()));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, key.toString()));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, commodityName));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE, key.toString(), String.valueOf(service
                .getAvailableStock(shopCode, key.toString()))));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, key.toString(), NumUtil.toString(service
                .getReservationAvailableStock(shopCode, key.toString()))));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, key.toString()));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
            valid &= true;
          }
        }
      }

      // 各赠品可购买性check
      if (valid) {
        cartCommodityName = new HashMap<String, String>(); // 购物车中各通常商品的名称信息
        Map<String, Long> cartGiftTotalAmount = getCartGiftBeanTotalAmount(cartCommodityName);
        for (Object key : cartGiftTotalAmount.keySet()) {
          commodityAvailability = service.isAvailableGift(shopCode, key.toString(), cartGiftTotalAmount.get(key).intValue());
          String commodityName = cartCommodityName.get(key);
          if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, commodityName));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE, key.toString(), String.valueOf(service
                .getAvailableStock(shopCode, key.toString()))));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
            valid &= true;
          }
        }
      }
    }

    // 限界値チェック
    NumberLimitPolicy numberPolicy = DIContainer.getNumberLimitPolicy();
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
   * 统计购物车中各通常商品的数量
   * 
   * @return 购物车中各通常商品的数量信息
   */
  public Map<String, Long> getCartCommodityBeanTotalAmount(Map<String, String> cartCommodityName) {
    Map<String, Long> cartCartCommodityTotalAmount = new HashMap<String, Long>(); // 购物车中各通常商品的数量信息

    for (CartCommodityDetailListBean cartCommodityDetailListBean : getBean().getCartCommodityList()) {

      // 套餐商品
      if (cartCommodityDetailListBean.getCompositionItemList() != null
          && cartCommodityDetailListBean.getCompositionItemList().size() > 0) {
        for (CompositionItem composition : cartCommodityDetailListBean.getCompositionItemList()) {
          if (cartCartCommodityTotalAmount.containsKey(composition.getSkuCode())) {
            cartCartCommodityTotalAmount.put(composition.getSkuCode(), cartCartCommodityTotalAmount.get(composition.getSkuCode())
                + NumUtil.toLong(cartCommodityDetailListBean.getPurchasingAmount()));
          } else {
            cartCartCommodityTotalAmount.put(composition.getSkuCode(), NumUtil.toLong(cartCommodityDetailListBean
                .getPurchasingAmount()));
          }
          String cmdtyName = composition.getCommodityName();
          cmdtyName += createStandardDetailName(composition.getStandardDetail1Name(), composition.getStandardDetail2Name());
          cartCommodityName.put(composition.getSkuCode(), cmdtyName);
        }
      } else {
        // 普通商品
        if (CommodityType.GENERALGOODS.longValue().equals(cartCommodityDetailListBean.getCommodityType())) {
          if (cartCartCommodityTotalAmount.containsKey(cartCommodityDetailListBean.getSkuCode())) {
            cartCartCommodityTotalAmount.put(cartCommodityDetailListBean.getSkuCode(), cartCartCommodityTotalAmount
                .get(cartCommodityDetailListBean.getSkuCode())
                + NumUtil.toLong(cartCommodityDetailListBean.getPurchasingAmount()));
          } else {
            cartCartCommodityTotalAmount.put(cartCommodityDetailListBean.getSkuCode(), NumUtil.toLong(cartCommodityDetailListBean
                .getPurchasingAmount()));
          }
        }
        String cmdtyName = cartCommodityDetailListBean.getCommodityName();
        cmdtyName += createStandardDetailName(cartCommodityDetailListBean.getStandard1Name(), cartCommodityDetailListBean
            .getStandard2Name());
        cartCommodityName.put(cartCommodityDetailListBean.getSkuCode(), cmdtyName);
      }
    }
    return cartCartCommodityTotalAmount;
  }

  /**
   * 统计购物车中各赠品的数量
   * 
   * @return 购物车中各赠品的数量信息
   */
  public Map<String, Long> getCartGiftBeanTotalAmount(Map<String, String> cartGiftName) {

    return getCartGiftBeanTotalAmount(cartGiftName, null);
  }

  /**
   * 统计购物车中各赠品的数量
   * 
   * @return 购物车中各赠品的数量信息
   */
  public Map<String, Long> getCartGiftBeanTotalAmount(Map<String, String> cartGiftName,
      List<CartCommodityDetailListBean> giftBeanList) {
    Map<String, Long> cartGiftTotalAmount = new HashMap<String, Long>(); // 购物车中各通常商品的数量信息

    for (CartCommodityDetailListBean cartCommodityDetailListBean : getBean().getCartCommodityList()) {

      // 普通赠品sku合计
      if (cartCommodityDetailListBean.getGiftCommodityList() != null
          && cartCommodityDetailListBean.getGiftCommodityList().size() > 0) {
        for (CartGiftCommodityDetailListBean cartGiftCommodityDetailListBean : cartCommodityDetailListBean.getGiftCommodityList()) {
          CampaignMainDao camDao = DIContainer.getDao(CampaignMainDao.class);
          jp.co.sint.webshop.data.dto.CampaignMain camMain = camDao.load(cartGiftCommodityDetailListBean.getCampaignCode());
          Long giftAmount = 1L;
          if (camMain.getGiftAmount() != null ) {
            giftAmount = camMain.getGiftAmount();
          }
          if (cartGiftTotalAmount.containsKey(cartGiftCommodityDetailListBean.getSkuCode())) {
            cartGiftTotalAmount.put(cartGiftCommodityDetailListBean.getSkuCode(), cartGiftTotalAmount
                .get(cartGiftCommodityDetailListBean.getSkuCode()) + giftAmount);
          } else {
            cartGiftTotalAmount.put(cartGiftCommodityDetailListBean.getSkuCode(), giftAmount);
          }
          String cmdtyName = cartGiftCommodityDetailListBean.getCommodityName();
          cmdtyName += createStandardDetailName(cartGiftCommodityDetailListBean.getStandard1Name(), cartGiftCommodityDetailListBean
              .getStandard2Name());
          cartGiftName.put(cartGiftCommodityDetailListBean.getSkuCode(), cmdtyName);
        }
      }
    }

    // giftBeanList为空时去Bean中的可领取赠品list
    if (giftBeanList == null || giftBeanList.size() == 0) {
      giftBeanList = getBean().getCartOtherGiftCommodityList();
    }

    // 多关联赠品sku合计
    if (giftBeanList != null && giftBeanList.size() > 0) {
      for (CartCommodityDetailListBean cartCommodityDetailListBean : giftBeanList) {
        if (cartGiftTotalAmount.containsKey(cartCommodityDetailListBean.getSkuCode())) {
          cartGiftTotalAmount.put(cartCommodityDetailListBean.getSkuCode(), cartGiftTotalAmount.get(cartCommodityDetailListBean
              .getSkuCode()) + 1L);
        } else {
          cartGiftTotalAmount.put(cartCommodityDetailListBean.getSkuCode(), 1L);
        }
        String cmdtyName = cartCommodityDetailListBean.getCommodityName();
        cmdtyName += createStandardDetailName(cartCommodityDetailListBean.getStandard1Name(), cartCommodityDetailListBean
            .getStandard2Name());
        cartGiftName.put(cartCommodityDetailListBean.getSkuCode(), cmdtyName);
      }
    }

    return cartGiftTotalAmount;
  }

  /**
   * 统计购物车中各通常商品的数量
   * 
   * @return 购物车中各通常商品的数量信息
   */
  public Map<String, Long> getCartCommodityTotalAmount(Map<String, String> cartCommodityName) {

    Map<String, Long> cartCartCommodityTotalAmount = new HashMap<String, Long>(); // 购物车中各通常商品的数量信息

    for (CartItem item : getCart().get()) {
      // 套餐商品
      if (item.getCommodityInfo().getCompositionList() != null && item.getCommodityInfo().getCompositionList().size() > 0) {
        for (CompositionItem composition : item.getCommodityInfo().getCompositionList()) {
          if (cartCartCommodityTotalAmount.containsKey(composition.getSkuCode())) {
            cartCartCommodityTotalAmount.put(composition.getSkuCode(), cartCartCommodityTotalAmount.get(composition.getSkuCode())
                + item.getQuantity());
          } else {
            cartCartCommodityTotalAmount.put(composition.getSkuCode(), (long) item.getQuantity());
          }
          String cmdtyName = composition.getCommodityName();
          cmdtyName += createStandardDetailName(composition.getStandardDetail1Name(), composition.getStandardDetail2Name());
          cartCommodityName.put(composition.getSkuCode(), cmdtyName);
        }
      } else {
        // 普通商品
        if (CommodityType.GENERALGOODS.longValue().equals(item.getCommodityType())) {
          if (cartCartCommodityTotalAmount.containsKey(item.getSkuCode())) {
            cartCartCommodityTotalAmount.put(item.getSkuCode(), cartCartCommodityTotalAmount.get(item.getSkuCode())
                + item.getQuantity());
          } else {
            cartCartCommodityTotalAmount.put(item.getSkuCode(), (long) item.getQuantity());
          }
        }
        String cmdtyName = item.getCommodityName();
        cmdtyName += createStandardDetailName(item.getStandardDetail1Name(), item.getStandardDetail2Name());
        cartCommodityName.put(item.getSkuCode(), cmdtyName);
      }
    }
    return cartCartCommodityTotalAmount;
  }

  /**
   * 统计购物车中各赠品的数量
   * 
   * @return 购物车中各赠品的数量信息
   */
  public Map<String, Long> getCartGiftTotalAmount(Map<String, String> cartGiftName) {
    Map<String, Long> cartGiftTotalAmount = new HashMap<String, Long>(); // 购物车中各通常商品的数量信息

    // 统计购物车中各赠品的数量
    for (CartItem item : getCart().get()) {
      // 多关联赠品sku合计
      if (CommodityType.GIFT.longValue().equals((item.getCommodityInfo().getCommodityType()))) {
        String skuCode = item.getSkuCode();
        if (item.getSkuCode().contains(WebConstantCode.SKU_CODE_SEPARATOR)
            && item.getSkuCode().split(WebConstantCode.SKU_CODE_SEPARATOR).length > 1) {
          skuCode = item.getSkuCode().split(WebConstantCode.SKU_CODE_SEPARATOR)[1];
        }
        if (cartGiftTotalAmount.containsKey(skuCode)) {
          cartGiftTotalAmount.put(skuCode, cartGiftTotalAmount.get(skuCode) + item.getQuantity());
        } else {
          cartGiftTotalAmount.put(skuCode, (long) item.getQuantity());
        }

        cartGiftName.put(skuCode, item.getCommodityName());
      } else {
        // 普通赠品sku合计
        if (item.getCommodityInfo().getGiftList() != null && item.getCommodityInfo().getGiftList().size() > 0) {
          for (GiftItem giftItem : item.getCommodityInfo().getGiftList()) {
            if (cartGiftTotalAmount.containsKey(giftItem.getGiftSkuCode())) {
              cartGiftTotalAmount.put(giftItem.getGiftSkuCode(), cartGiftTotalAmount.get(giftItem.getGiftSkuCode())
                  + giftItem.getQuantity());
            } else {
              cartGiftTotalAmount.put(giftItem.getGiftSkuCode(), (long) giftItem.getQuantity());
            }

            cartGiftName.put(giftItem.getGiftSkuCode(), giftItem.getGiftName());
          }
        }
      }
    }

    return cartGiftTotalAmount;
  }

  /**
   * 套餐可购买验证
   * 
   * @param shopCode
   * @param commodityInfo
   * @param skuCode
   * @return
   */
  public boolean compositionCommodityCheck(String shopCode, CommodityInfo commodityInfo, String skuCode) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityListDetailBean row = new CommodityListDetailBean();
    boolean valid = true;

    if (commodityInfo != null) {
      String errMessage = commodityInfo.getHeader().getCommodityName();
      errMessage += createStandardDetailName(commodityInfo.getDetail().getStandardDetail1Name(), commodityInfo.getDetail()
          .getStandardDetail2Name());

      // 套餐商品时,存在验证
      if (!service.isExistsSetCommodityComposition(shopCode, commodityInfo.getHeader().getCommodityCode())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.SET_COMMODITY_COMPOSITION_NOT_EXIST_ERROR, errMessage));
        return false;
      }

      // 套餐商品可购买性验证
      CommodityAvailability availability = service.isAvailable(shopCode, skuCode, 1, false, true);
      if (availability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
        return false;
      } else if (availability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, skuCode));
        return false;
      }

      String provisionalSkuCode = SetCommodityUtil.createProvisionalSkuCode(skuCode, getBean().getSelectedCompositionList());
      // 多规格时同一套餐的数量合计
      if (provisionalSkuCode.contains(SetCommodityUtil.SET_COMMODITY_DELIMITER)) {
        Cart cart = getCart();
        int quantityTotal = 1;
        for (CartItem item : cart.get()) {
          if (item.getCommodityInfo().getSkuCode().contains(SetCommodityUtil.SET_COMMODITY_DELIMITER)) {
            if (item.getCommodityInfo().getSkuCode().equals(provisionalSkuCode)) {
              quantityTotal += item.getQuantity();
            }
          }
        }

        // 在库数验证
        if (!minAvailableStockCheck(shopCode, provisionalSkuCode.split(SetCommodityUtil.SET_COMMODITY_DELIMITER)[0], row,
            provisionalSkuCode)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NO_AVAILABLE_STOCK_SKU, skuCode));
          return false;
        } else if (row.getStockQuantity().intValue() < quantityTotal) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, errMessage, String.valueOf(row
              .getStockQuantity().intValue())));
          return false;
        }
      }

      // 套餐详细商品可购买性验证
      for (CommodityCompositionDetailBean compositionBean : getBean().getCommodityCompositionBean()
          .getCommodityCompositionDetailBeanList()) {
        if (StringUtil.hasValue(compositionBean.getSelectedSkuCode())) {

          availability = service.isAvailable(getBean().getCommodityCompositionBean().getShopCode(), compositionBean
              .getSelectedSkuCode(), 1, false);

          if (availability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
            valid &= false;
          } else if (availability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, skuCode));
            valid &= false;
          } else if (availability.equals(CommodityAvailability.OUT_OF_STOCK)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, errMessage));
            valid &= false;
          } else if (availability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, errMessage, String.valueOf(service
                .getAvailableStock(shopCode, compositionBean.getSelectedSkuCode()))));
            valid &= false;
          } else if (availability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, skuCode, NumUtil.toString(service
                .getReservationAvailableStock(shopCode, compositionBean.getSelectedSkuCode()))));
            valid &= false;
          } else if (availability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, skuCode));
            valid &= false;
          }
        }
      }
    }
    return valid;
  }

  /**
   * 普通商品可购买验证
   * 
   * @param shopCode
   * @param commodityInfo
   * @param skuCode
   * @return
   */
  public boolean commonCommodityCheck(String shopCode, String skuCode, CommodityInfo commodityInfo) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    boolean valid = true;
    String commodityNameMessage = "";

    if (commodityInfo != null) {

      Map<String, String> cartCommodityName = new HashMap<String, String>(); // 购物车中各通常商品的名称信息
      Map<String, Long> commodityAmountMap = getCartCommodityTotalAmount(cartCommodityName);
      commodityNameMessage = commodityInfo.getHeader().getCommodityName();
      commodityNameMessage += createStandardDetailName(commodityInfo.getDetail().getStandardDetail1Name(), commodityInfo
          .getDetail().getStandardDetail2Name());
      boolean validTemp = false;

      for (Object key : commodityAmountMap.keySet()) {

        // 购物车中存在的场合
        if (key.toString().equals(skuCode)) {
          CommodityAvailability availability = service.isAvailable(shopCode, skuCode, commodityAmountMap.get(key).intValue() + 1,
              false);
          if (availability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
            valid &= false;
          } else if (availability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, skuCode));
            valid &= false;
          } else if (availability.equals(CommodityAvailability.OUT_OF_STOCK)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, commodityNameMessage));
            valid &= false;
          } else if (availability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
            Long quanntity = service.getAvailableStock(shopCode, skuCode);
            if (quanntity > commodityAmountMap.get(key).intValue()) {
              quanntity = quanntity - commodityAmountMap.get(key).intValue();
            }
            addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE, skuCode, String.valueOf(quanntity)));
            valid &= false;
          } else if (availability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
            Long quanntity = service.getReservationAvailableStock(shopCode, skuCode);
            if (quanntity > commodityAmountMap.get(key).intValue()) {
              quanntity = quanntity - commodityAmountMap.get(key).intValue();
            }
            addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, skuCode, NumUtil.toString(quanntity)));
            valid &= false;
          } else if (availability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, skuCode));
            valid &= false;
          }
          validTemp = true;
          break;
        }
      }

      // 购物车中不存在的场合
      if (!validTemp) {
        CommodityAvailability availability = service.isAvailable(shopCode, skuCode, 1, false);

        if (availability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
          valid &= false;
        } else if (availability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD, skuCode));
          valid &= false;
        } else if (availability.equals(CommodityAvailability.OUT_OF_STOCK)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, commodityNameMessage));
          valid &= false;
        } else if (availability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityNameMessage, String.valueOf(service
              .getAvailableStock(shopCode, skuCode))));
          valid &= false;
        } else if (availability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, skuCode, NumUtil.toString(service
              .getReservationAvailableStock(shopCode, skuCode))));
          valid &= false;
        } else if (availability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, skuCode));
          valid &= false;
        }
      }
    }
    return valid;
  }

  /**
   * 赠品可购买验证
   * 
   * @param shopCode
   * @param commodityInfo
   * @param skuCode
   * @return
   */
  public boolean giftCommodityCheck(String shopCode, String skuCode, CommodityInfo commodityInfo) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CampainFilter campainFilter = new CampainFilter();
    boolean valid = true;
    String commodityNameMessage = "";

    if (commodityInfo != null) {
      Map<String, String> cartCommodityName = new HashMap<String, String>(); // 购物车中各通常商品的名称信息
      Map<String, Long> commodityAmountMap = getCartGiftTotalAmount(cartCommodityName);
      commodityNameMessage = commodityInfo.getHeader().getCommodityName();
      commodityNameMessage += createStandardDetailName(commodityInfo.getDetail().getStandardDetail1Name(), commodityInfo
          .getDetail().getStandardDetail2Name());

      // 对象商品所在期间内特定商品活动的赠品
      List<GiftItem> giftItems = campainFilter.getOtherGiftCodeBySku(commodityInfo.getHeader().getCommodityCode(), shopCode);

      if (giftItems != null) {
        for (GiftItem giftItem : giftItems) {
          boolean validTemp = false;
          for (Object key : commodityAmountMap.keySet()) {

            // 购物车中存在的场合
            if (key.toString().equals(giftItem.getGiftSkuCode())) {
              CommodityAvailability availability = service.isAvailableGift(shopCode, giftItem.getGiftSkuCode(), commodityAmountMap
                  .get(key).intValue() + 1);
              if (availability.equals(CommodityAvailability.OUT_OF_STOCK)) {
                addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, commodityInfo.getHeader()
                    .getCommodityName()));
                valid &= false;
              } else if (availability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
                Long quanntity = service.getAvailableStock(shopCode, giftItem.getGiftSkuCode());
                if (quanntity > commodityAmountMap.get(key).intValue()) {
                  quanntity = quanntity - commodityAmountMap.get(key).intValue();
                }
                addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE, skuCode, String.valueOf(quanntity)));
                valid &= false;
              }
              validTemp = true;
            }
          }

          // 购物车中不存在的场合
          if (!validTemp) {
            CommodityAvailability availability = service.isAvailableGift(shopCode, giftItem.getGiftSkuCode(), 1);
            if (availability.equals(CommodityAvailability.OUT_OF_STOCK)) {
              addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, commodityNameMessage));
              valid &= false;
            } else if (availability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
              addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE, skuCode, String.valueOf(service.getAvailableStock(
                  shopCode, giftItem.getGiftSkuCode()))));
              valid &= false;
            }
          }
        }
      }
    }
    return valid;
  }

  // 2012/11/26 促销对应 ob update end

  /**
   * セッションカートから情報を取得し、画面beanに設定する
   * 
   * @return cartList
   */
  public List<CartCommodityDetailListBean> createBeanFromCart() {
    List<CartItem> cartItemList = new ArrayList<CartItem>();
    cartItemList.addAll(getCart().get());
    cartItemList.addAll(getCart().getReserve());

    List<CartCommodityDetailListBean> cartList = new ArrayList<CartCommodityDetailListBean>();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    for (CartItem item : cartItemList) {
      // 2012/11/22 促销对应 新建订单_商品选择 ob add start
      if (!CommodityType.GIFT.longValue().equals(item.getCommodityInfo().getCommodityType())) {
        // 2012/11/22 促销对应 新建订单_商品选择 ob add end
        CommodityInfo commodity = service.getSkuInfo(item.getShopCode(), item.getSkuCode());
        // 2012/12/03 促销对应 新建订单_商品选择 ob add start
        // 套餐时
        if (item.getSkuCode().contains(SetCommodityUtil.SET_COMMODITY_DELIMITER)) {
          commodity = service.getSkuInfo(item.getShopCode(), item.getSkuCode().split(SetCommodityUtil.SET_COMMODITY_DELIMITER)[0]);
        }
        // 2012/12/03 促销对应 新建订单_商品选择 ob add end
        String shopCode = item.getShopCode();
        String commodityCode = item.getCommodityCode();
        Campaign campaign = service.getAppliedCampaignInfo(shopCode, commodityCode);
        Price price = new Price(commodity.getHeader(), commodity.getDetail(), campaign, taxRate);
        CartCommodityDetailListBean cartDetail = new CartCommodityDetailListBean();
        DiscountCommodity dCommodity = service.getDiscountCommodityByCommodityCode(item.getCommodityCode());
        //历史所有客户购买总数
        Long siteTotalAmount = 0L;
        if (dCommodity != null){
          siteTotalAmount= service.getHistoryBuyAmountTotal(item.getCommodityCode());
          if (siteTotalAmount == null){
            siteTotalAmount = 0L;
          }
        }
        
        Long historyNum = 0L;
        if (dCommodity != null && dCommodity.getSiteMaxTotalNum() > siteTotalAmount){
          Long quantity = 0L;
          //限购商品剩余可购买数量
          Long avalibleAmount = dCommodity.getSiteMaxTotalNum() - siteTotalAmount;
          if(avalibleAmount < 0L ){
            avalibleAmount = 0L;
          }
          if (dCommodity.getCustomerMaxTotalNum()!=null){
            //当前客户可购买限购商品数
            quantity = dCommodity.getCustomerMaxTotalNum() - historyNum;
            if(quantity < 0L ){
              quantity = 0L;
            }
            if(quantity > avalibleAmount){
              quantity = avalibleAmount;
            }
          }
          int totalNum = item.getQuantity();
          if (totalNum <= quantity){
            cartDetail = createCartCommodity(item,price,service,utilService,taxRate);
            cartDetail.setIsDiscountCommodity("true");
            cartDetail.setRetailPrice(dCommodity.getDiscountPrice().toString());
            cartDetail.setSamaryPrice(createPrice(item,dCommodity.getDiscountPrice()));
            cartList.add(cartDetail);
          } else {
            if(quantity != 0L){
              item.setQuantity(Integer.parseInt(quantity.toString()));
              cartDetail = createCartCommodity(item,price,service,utilService,taxRate);
              cartDetail.setIsDiscountCommodity("true");
              cartDetail.setRetailPrice(dCommodity.getDiscountPrice().toString());
              cartDetail.setSamaryPrice(createPrice(item,dCommodity.getDiscountPrice()));
              cartList.add(cartDetail);
            }
            
            item.setQuantity(totalNum - Integer.parseInt(quantity.toString()));
            cartDetail = createCartCommodity(item,price,service,utilService,taxRate);
            cartDetail.setRetailPrice(item.getRetailPrice().toString());
            cartDetail.setSamaryPrice(createPrice(item,item.getRetailPrice()));
            cartList.add(cartDetail);
          }
          item.getCommodityInfo().setIsDiscountCommodity("true");
          item.setQuantity(totalNum);
        } else {
          cartDetail = createCartCommodity(item,price,service,utilService,taxRate);
          cartList.add(cartDetail);
        }
      }
    }
    return cartList;
  }

  /**
   * 検索条件のカテゴリツリーを設定する。
   * 
   * @return カテゴリツリー
   */
  public String createCategoryTree() {
    CatalogService catalogSvc = ServiceLocator.getCatalogService(getLoginInfo());
    List<CategoryInfo> categoryList = catalogSvc.getAllCategory();

    // カテゴリツリー構築用データの生成
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < categoryList.size(); i++) {
      stringBuilder.append(categoryList.get(i).getCategoryCode() + ":" + categoryList.get(i).getDepth() + ":"
          + categoryList.get(i).getCategoryNamePc() + ":" + categoryList.get(i).getCommodityCountPc() + "-"
          + categoryList.get(i).getCommodityCountMobile() + "/");
    }

    return stringBuilder.toString();
  }

  /**
   * 再計算
   */
  public void calculate() {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    List<CartCommodityDetailListBean> cartList = getBean().getCartCommodityList();
    List<CartCommodityDetailListBean> cartOtherGiftList = getBean().getCartOtherGiftCommodityList();
    getCart().clear();
    //sku相同的商品数量相加（限购时可能有俩相同SKU的商品）
    for (int i = 0 ;i < cartList.size()-1; i++) {
      for (int j = i+1  ;j < cartList.size(); j++) {
        if (cartList.get(i).getSkuCode().equals(cartList.get(j).getSkuCode())){
          Long oldAmountI = Long.parseLong(cartList.get(i).getPurchasingAmount());
          Long oldAmountJ = Long.parseLong(cartList.get(j).getPurchasingAmount());
          oldAmountI = oldAmountI + oldAmountJ;
          cartList.get(i).setPurchasingAmount(oldAmountI.toString());
          cartList.remove(j);
          j--;
        }
      }
    }
    for (CartCommodityDetailListBean cart : cartList) {
      String shopCode = cart.getShopCode();
      String skuCode = cart.getSkuCode();
      String giftCode = cart.getGiftCode();
      int quantity = Integer.valueOf(cart.getPurchasingAmount());
      boolean isReserve = CartUtil.isReserve(getCart(), shopCode, skuCode);
      // 2012/11/24 促销对应 新建订单_商品选择 ob update start
      TaxUtil u = DIContainer.get("TaxUtil");
      Long taxRate = u.getTaxRate();
      CommodityInfo commodityObj = service.getSkuInfo(shopCode, skuCode);
      CommodityAvailability commodityAvailability = CommodityAvailability.AVAILABLE;

      if (!SetCommodityFlg.OBJECTIN.longValue().equals(commodityObj.getHeader().getSetCommodityFlg())) {
        if (CommodityType.GENERALGOODS.longValue().equals(commodityObj.getHeader().getCommodityType())) {
          commodityAvailability = service.isAvailable(shopCode, skuCode, quantity, isReserve);
        } else if (CommodityType.GIFT.longValue().equals(commodityObj.getHeader().getCommodityType())) {
          commodityAvailability = service.isAvailableGift(shopCode, skuCode, quantity);
        }
      } else {
        commodityAvailability = service.isAvailable(shopCode, skuCode, quantity, isReserve, true);
        // 循环Bean中套餐详细信息
        List<Sku> addCompositionList = new ArrayList<Sku>();
        for (CompositionItem detailCom : cart.getCompositionItemList()) {
          if (StringUtil.hasValue(detailCom.getSkuCode())) {
            addCompositionList.add(new Sku(detailCom.getShopCode(), detailCom.getSkuCode()));
          }
        }
        skuCode = SetCommodityUtil.createProvisionalSkuCode(skuCode, addCompositionList);
      }

      // 2012/11/24 促销对应 新建订单_商品选择 ob update start

      if (quantity > 0 && commodityAvailability == CommodityAvailability.AVAILABLE) {
        getCart().set(shopCode, skuCode, giftCode, quantity, "");
        // 2012/11/24 促销对应 新建订单_商品选择 ob add start
        // 特定商品的贈品
        CartItem cartItem = getCart().get(shopCode, skuCode);
        List<GiftItem> items = new ArrayList<GiftItem>();
        for (CartGiftCommodityDetailListBean giftBean : cart.getGiftCommodityList()) {

          GiftItem item = new GiftItem();
          CommodityInfo commodityInfo = service.getCommodityInfo(giftBean.getShopCode(), giftBean.getCommodityCode());
          if (commodityInfo != null && commodityInfo.getHeader() != null) {

            item.setGiftName(commodityInfo.getHeader().getCommodityName());
            item.setStandardDetail1Name(commodityInfo.getDetail().getStandardDetail1Name());
            item.setStandardDetail2Name(commodityInfo.getDetail().getStandardDetail2Name());
            item.setCampaignName(giftBean.getCampaignName());
            Price price = new Price(commodityInfo.getHeader(), commodityInfo.getDetail(), null, taxRate);
            item.setUnitTaxCharge(price.getUnitTaxCharge());
            item.setUnitPrice(price.getUnitPrice());
            item.setRetailPrice(price.getRetailPrice());
            item.setDiscount(price.isDiscount());
            item.setWeight(commodityInfo.getDetail().getWeight());
            item.setTaxType(commodityInfo.getHeader().getCommodityTaxType());
            item.setGiftCode(giftBean.getCommodityCode());
            item.setShopCode(shopCode);
            item.setGiftSkuCode(commodityInfo.getHeader().getRepresentSkuCode());
            item.setCampaignCode(giftBean.getCampaignCode());
            item.setQuantity(Integer.parseInt(giftBean.getPurchasingAmount()));
            items.add(item);
          }
        }
        cartItem.getCommodityInfo().setGiftList(items);

        // 套餐不重新取得
        if (SetCommodityFlg.OBJECTIN.longValue().equals(cartItem.getCommodityInfo().getSetCommodityFlg())) {
          cartItem.getCommodityInfo().setCompositionList(cart.getCompositionItemList());

          BigDecimal weightAll = BigDecimal.ZERO;
          if (cart.getCompositionItemList() != null && cart.getCompositionItemList().size() > 0) {
            for (CompositionItem compositionItem : cart.getCompositionItemList()) {
              weightAll = weightAll.add(compositionItem.getWeight());
            }
          }
          cartItem.getCommodityInfo().setCompositionWeight(cartItem.getWeight());
          cartItem.getCommodityInfo().setWeight(weightAll);
        }
        // 2012/11/24 促销对应 新建订单_商品选择 ob add end
      }
    }

    // 2012/11/24 促销对应 新建订单_商品选择 ob add start
    // 多关联赠品
    for (CartCommodityDetailListBean otherGiftBean : cartOtherGiftList) {
      String shopCode = otherGiftBean.getShopCode();
      String skuCode = otherGiftBean.getSkuCode();
      String giftCode = otherGiftBean.getGiftCode();
      int quantity = Integer.valueOf(otherGiftBean.getPurchasingAmount());
      CommodityAvailability commodityAvailability = service.isAvailableGift(shopCode, skuCode, quantity);
      skuCode = otherGiftBean.getCampaignCode() + WebConstantCode.SKU_CODE_SEPARATOR + skuCode;
      if (quantity > 0 && commodityAvailability == CommodityAvailability.AVAILABLE) {
        getCart().set(shopCode, skuCode, giftCode, quantity, "");
      }
    }
    // 2012/11/24 促销对应 新建订单_商品选择 ob add end
  }

  /**
   * 予約商品の在庫・注文毎予約上限数と、予約以外の商品の在庫チェックを行います。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @param quantity
   *          数量
   * @return 入力値にエラーがなければtrueを返します。
   */
  public boolean commodityValidation(String shopCode, String skuCode, int quantity) {
    boolean valid = true;

    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    boolean isReserve = CartUtil.isReserve(getCart(), shopCode, skuCode);
    CommodityAvailability commodityAvailability = catalogService.isAvailable(shopCode, skuCode, quantity, isReserve);
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
      addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE, skuCode, NumUtil.toString(CartUtil.getAvailableQuantity(
          shopCode, skuCode, isReserve))));
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

    return valid;
  }

  // 2012/11/20 促销对应 新建订单_商品选择 ob add start
  /**
   * 验证领取的商品
   * 
   * @param bean
   */
  public boolean checkMultipleGiftProcess(NeworderCommodityBean bean, String shopCode, String campaignCode, String skuCode,
      boolean delFlg, CommodityInfo commodityInfo) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    String skuName = "";

    if (commodityInfo != null) {
      skuName = commodityInfo.getHeader().getCommodityName();
      skuName += createStandardDetailName(commodityInfo.getDetail().getStandardDetail1Name(), commodityInfo.getDetail()
          .getStandardDetail2Name());

    }

    // 取得可领取商品
    if (multipleGiftProcess(bean, true, delFlg)) {
      boolean vaild = false;

      // 购买的商品不在取得的商品中
      List<GiftCommodityDetailBean> giftCommodityBeanList = bean.getGiftCommodityBean().getGiftCommodityBeanCheckList();
      if (giftCommodityBeanList != null) {
        for (GiftCommodityDetailBean giftCommodityDetailBean : giftCommodityBeanList) {
          if (giftCommodityDetailBean.getCampainCode().equals(campaignCode) && giftCommodityDetailBean.getSkuCode().equals(skuCode)) {
            vaild = true;
            break;
          }
        }
      }

      if (!vaild) {
        multipleGiftProcess(bean, true, true, false);
        addErrorMessage(WebMessage.get(OrderErrorMessage.MULTIPLE_GIFT_BUY_ERROR));
        return false;
      }

      // 购物车中该活动的该商品已领取时
      List<CartCommodityDetailListBean> giftCommodityListList = bean.getCartOtherGiftCommodityList();
      // 追加多关联可领取赠品
      for (CartCommodityDetailListBean cartCommodityDetailListBean : giftCommodityListList) {
        // 单次购买同一活动的同一商品只可领取一次
        if (cartCommodityDetailListBean.getCampaignCode().equals(campaignCode)
            && cartCommodityDetailListBean.getSkuCode().equals(skuCode)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.SAME_CAMPAIGN_MULTIPLE_GIFT_BUY_ERROR));
          return false;
        }
      }

      // 商品的有效在库数验证
      int stockAvailability = 0;
      for (CartCommodityDetailListBean cartCommodityDetailListBean : giftCommodityListList) {
        // 取得购物车中的数量合计
        if (cartCommodityDetailListBean.getSkuCode().equals(skuCode)) {
          stockAvailability += NumUtil.toLong(cartCommodityDetailListBean.getPurchasingAmount()).intValue();
        }
      }

      for (CartCommodityDetailListBean cartCommodityDetailListBean : bean.getCartCommodityList()) {
        for (CartGiftCommodityDetailListBean cartGiftCommodityDetailListBean : cartCommodityDetailListBean.getGiftCommodityList()) {
          // 取得购物车中的数量合计
          if (cartGiftCommodityDetailListBean.getSkuCode().equals(skuCode)) {
            stockAvailability += NumUtil.toLong(cartCommodityDetailListBean.getPurchasingAmount()).intValue();
          }
        }
      }

      // 购物车sku编号合计在库数验证
      CommodityAvailability commodityAvailability = service.isAvailableGift(shopCode, skuCode, stockAvailability + 1);
      Long availableStock = service.getAvailableStock(shopCode, skuCode);
      if (!CommodityAvailability.AVAILABLE.equals(commodityAvailability)) {
        if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, skuName));
        } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE, skuCode, NumUtil.toString(availableStock)));
        }
        return false;
      }
      return true;
    } else {
      multipleGiftProcess(bean, true, true, false);
      addErrorMessage(WebMessage.get(OrderErrorMessage.MULTIPLE_GIFT_BUY_ERROR));
      return false;
    }
  }

  /**
   * 获得可领取商品
   * 
   * @param bean
   * @param hashTable
   *          促销活动信息
   * @return
   */
  public List<String> getMultipleGiftList(NeworderCommodityBean bean) {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommunicationService commuicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    List<String> resultList = new ArrayList<String>(); // 可领取赠品List（活动编号+赠品编号）
    List<String> cartCommodityCodeList = new ArrayList<String>(); // 购物车普通商品编号List(商品编号)
    List<String> commodityCodeList = new ArrayList<String>(); // 关联商品List(商品编号)
    BigDecimal totalCurrency = BigDecimal.ZERO; // 商品合计金额
    Hashtable<String, jp.co.sint.webshop.data.dto.CampaignMain> campaignHash = new Hashtable<String, jp.co.sint.webshop.data.dto.CampaignMain>(); // 促销活动信息

    // 店编号
    String shopCode = "";

    // 广告媒体编号
    String advertCode = getSessionContainer().getMedia();

    // 可领取商品不显示
    bean.setGiftCommodityDisplayFlg(false);

    // 取得Bean中的购物车
    List<CartCommodityDetailListBean> commodityListDetailBeanList = bean.getCartCommodityList();

    for (CartCommodityDetailListBean cartCommodityDetailListBean : commodityListDetailBeanList) {
      shopCode = cartCommodityDetailListBean.getShopCode();

      // 商品合计金额
      totalCurrency = totalCurrency.add(new BigDecimal(cartCommodityDetailListBean.getSamaryPrice()));
      // 普通商品list
      cartCommodityCodeList.add(cartCommodityDetailListBean.getCommodityCode());

      // 追加特定商品的赠品
      for (CartGiftCommodityDetailListBean giftCartCommodityDetailListBean : cartCommodityDetailListBean.getGiftCommodityList()) {
        if (StringUtil.hasValue(giftCartCommodityDetailListBean.getSamaryPrice())) {
          // 商品合计金额
          totalCurrency = totalCurrency.add(new BigDecimal(giftCartCommodityDetailListBean.getSamaryPrice()));
        }
      }
    }

    // 创建多重促销活动List
    List<CampaignInfo> campaignInfoList = commuicationService.getMultipleGiftCampaignInfo(DateUtil.getSysdate(), totalCurrency,
        advertCode);
    if (campaignInfoList != null && campaignInfoList.size() > 0) {

      for (CampaignInfo campaignInfo : campaignInfoList) {
        campaignHash.put(campaignInfo.getCampaignMain().getCampaignCode(), campaignInfo.getCampaignMain());
        commodityCodeList = new ArrayList<String>();

        CampaignCondition campaignCondition = new CampaignCondition();
        List<CampaignCondition> campaignConditionList = campaignInfo.getConditionList();
        if (campaignConditionList != null && campaignConditionList.size() > 0) {
          campaignCondition = campaignConditionList.get(0);
        }

        // 对象商品编号
        String attributrValue = "";
        if (StringUtil.hasValue(campaignCondition.getAttributrValue())) {
          attributrValue = campaignCondition.getAttributrValue();
        }

        if (StringUtil.hasValue(attributrValue.trim())) {
          String[] attributrArray = attributrValue.split(WebConstantCode.COMMODITY_COMMA);
          for (int i = 0; i < attributrArray.length; i++) {
            // 关联商品
            // 保存商品编号
            commodityCodeList.add(attributrArray[i]);
          }
        }

        // 获得能否添加赠品(可领取商品)区分
        if (commodityCodeList.size() == 0
            || isCanAddGiftCodeToList(campaignCondition.getCampaignConditionFlg(), cartCommodityCodeList, commodityCodeList)) {
          // 取得赠品信息
          CampaignDoingsDao doingsDao = DIContainer.getDao(CampaignDoingsDao.class);
          CampaignDoings campaignDoings = doingsDao.load(campaignInfo.getCampaignMain().getCampaignCode(),
              CampaignMainType.MULTIPLE_GIFT.longValue());
          if (campaignDoings != null) {
            // 赠品编号
            String commodityCodeValue = "";
            if (StringUtil.hasValue(campaignDoings.getAttributrValue())) {
              commodityCodeValue = campaignDoings.getAttributrValue();
            }
            if (StringUtil.hasValue(commodityCodeValue.trim())) {
              String[] commodityCodeArray = commodityCodeValue.split(WebConstantCode.COMMODITY_COMMA);

              for (int i = 0; i < commodityCodeArray.length; i++) {
                CommodityInfo commoditySkuInfo = catalogService.getCommodityInfo(shopCode, commodityCodeArray[i]);
                if (commoditySkuInfo != null) {
                  // 有库存
                  Long availableStock = catalogService.getAvailableStock(shopCode, commoditySkuInfo.getDetail().getSkuCode());
                  if (availableStock != null && (availableStock == -1L || availableStock > 0L)) {
                    resultList.add(campaignCondition.getCampaignCode() + WebConstantCode.SKU_CODE_SEPARATOR
                        + commoditySkuInfo.getDetail().getSkuCode());
                  }
                }
              }
            }
          }
        }
      }
    }
    bean.setMultipleGiftListAll(resultList);
    bean.setCampaignHashTable(campaignHash);
    return resultList;
  }

  /**
   * 设置可领取商品
   * 
   * @param bean
   * @param checkFlg
   *          验证区分
   * @param delFlg
   *          删除区分
   * @return
   */
  public boolean multipleGiftProcess(NeworderCommodityBean bean, boolean checkFlg, boolean delFlg) {
    return multipleGiftProcess(bean, checkFlg, delFlg, true);
  }

  /**
   * 设置可领取商品
   * 
   * @param bean
   * @param checkFlg
   *          验证区分
   * @param delFlg
   *          删除区分
   * @param queryAgainFlg
   *          重新查询多关联赠品的区分
   * @return
   */
  public boolean multipleGiftProcess(NeworderCommodityBean bean, boolean checkFlg, boolean delFlg, boolean queryAgainFlg) {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    List<GiftCommodityDetailBean> giftCommodityBeanList = new ArrayList<GiftCommodityDetailBean>();
    List<String> resultList = new ArrayList<String>(); // 可领取赠品List（活动编号+赠品编号）
    List<String> resultListTemp = new ArrayList<String>(); // 可领取赠品List（活动编号+赠品编号）
    List<Integer> resultDelList = new ArrayList<Integer>(); // 赠品编号List删除
    List<String> cartGiftSkuCodeList = new ArrayList<String>(); // 购物车赠品商品编号List
    Map<String, String> cartCommodityName = new HashMap<String, String>(); // 购物车中各通常商品的名称信息

    Hashtable<String, jp.co.sint.webshop.data.dto.CampaignMain> campaignHash = new Hashtable<String, jp.co.sint.webshop.data.dto.CampaignMain>();

    String shopCode = ""; // 店编号

    // 获得购物车赠品数量
    Map<String, Long> commodityAmountMap = getCartGiftBeanTotalAmount(cartCommodityName);

    // 店编号
    if (bean.getCartCommodityList() != null && bean.getCartCommodityList().size() > 0) {
      shopCode = bean.getCartCommodityList().get(0).getShopCode();
    }

    List<CartCommodityDetailListBean> giftCommodityListList = bean.getCartOtherGiftCommodityList(); // 多关联赠品list
    for (CartCommodityDetailListBean cartCommodityDetailListBean : giftCommodityListList) {
      // 可领取商品list
      cartGiftSkuCodeList.add(cartCommodityDetailListBean.getCampaignCode() + WebConstantCode.SKU_CODE_SEPARATOR
          + cartCommodityDetailListBean.getSkuCode());
    }

    // 取得期间内的多关联活动，注文金额内的赠品list
    if (queryAgainFlg) { // 重新查询多关联赠品list
      List<String> giftList = getMultipleGiftList(bean);
      for (String multipleGift : giftList) {
        String multipleGiftCode = (String) multipleGift;
        resultList.add(multipleGiftCode);
      }
    } else {
      List<String> giftList = bean.getMultipleGiftListAll();
      for (String multipleGift : giftList) {
        String multipleGiftCode = (String) multipleGift;
        resultList.add(multipleGiftCode);
      }
    }
    campaignHash = bean.getCampaignHashTable();

    // 对取得的赠品进行筛选
    if (resultList.size() > 0) {

      // 取得购物车中已存在的赠品
      for (int i = 0; i < resultList.size(); i++) {
        for (int j = 0; j < cartGiftSkuCodeList.size(); j++) {
          if (cartGiftSkuCodeList.get(j).toString().equals(resultList.get(i).toString())) {
            resultDelList.add(i);
            break;
          }
        }
      }

      for (int i = 0; i < resultList.size(); i++) {
        resultListTemp.add(resultList.get(i)); // 暂时保存可领取赠品，判断用
      }

      // 若购物车中已领取的赠品编号在赠品编号List中不
      // 存在时，则将该赠品从购物车的其他已领取赠品中删除
      List<String> giftCommodityCodeListDelete = new ArrayList<String>();

      for (int i = 0; i < cartGiftSkuCodeList.size(); i++) {
        boolean sameFlg = false;
        for (int j = 0; j < resultList.size(); j++) {
          if (resultList.get(j).toString().equals(cartGiftSkuCodeList.get(i).toString())) {
            sameFlg = true;
            break;
          }
        }
        if (!sameFlg) {
          giftCommodityCodeListDelete.add(cartGiftSkuCodeList.get(i).toString());
        }
      }

      // giftCommodityCodeList中删除购物车中已存在的赠品
      for (int i = 0; i < resultDelList.size(); i++) {
        resultList.remove(resultListTemp.get(resultDelList.get(i).intValue()));
      }

      if (resultList.size() > 0) {

        for (int i = 0; i < resultList.size(); i++) {
          GiftCommodityDetailBean cmdtyBean = new GiftCommodityDetailBean();
          String skuCodeTemp = resultList.get(i).split(WebConstantCode.SKU_CODE_SEPARATOR)[1];
          // 取得促销活动编号及名称
          jp.co.sint.webshop.data.dto.CampaignMain campaignMain = campaignHash.get(resultList.get(i).split(
              WebConstantCode.SKU_CODE_SEPARATOR)[0]);
          // 活动信息
          if (campaignMain != null) {
            cmdtyBean.setCampainCode(campaignMain.getCampaignCode());
            cmdtyBean.setCampainName(campaignMain.getCampaignName());
          }
          CommodityInfo commodity = catalogService.getSkuInfo(shopCode, skuCodeTemp);
          if (commodity != null && commodity.getHeader() != null) {
            // 商品信息
            cmdtyBean.setShopCode(commodity.getHeader().getShopCode());
            cmdtyBean.setSkuCode(skuCodeTemp);
            cmdtyBean.setCommodityCode(commodity.getHeader().getCommodityCode());
            cmdtyBean.setCommodityName(commodity.getHeader().getCommodityName());
          }

          // 购物车中该sku的合计数量
          int quantity = 0;
          int dataRow = 0;
          for (Object key : commodityAmountMap.keySet()) {
            if (key.toString().equals(skuCodeTemp)) {
              quantity += commodityAmountMap.get(key).intValue();
            }
            dataRow++;
          }

          CommodityAvailability availability = catalogService.isAvailableGift(shopCode, skuCodeTemp, quantity);

          // 有库存（有效库存 >购物车中该sku的合计数量）
          if (CommodityAvailability.AVAILABLE.equals(availability)) {
            Long availableStock = catalogService.getAvailableStock(shopCode, skuCodeTemp);
            if (availableStock == -1L || availableStock.intValue() > quantity) {
              giftCommodityBeanList.add(cmdtyBean);
            }
          }
        }

        // 非验证的场合
        if (!checkFlg) {
          bean.getGiftCommodityBean().setGiftCommodityBeanList(giftCommodityBeanList);
        } else {
          // 验证的场合
          bean.getGiftCommodityBean().setGiftCommodityBeanCheckList(giftCommodityBeanList);
          if (delFlg) {
            bean.getGiftCommodityBean().setGiftCommodityBeanList(giftCommodityBeanList);
          }
        }

        if (giftCommodityBeanList.size() > 0) {
          // 显示可领取赠品信息
          bean.setGiftCommodityDisplayFlg(true);
        }

      } else {
        if (!checkFlg) {
          bean.getGiftCommodityBean().setGiftCommodityBeanList(new ArrayList<GiftCommodityDetailBean>());
        } else {
          bean.getGiftCommodityBean().setGiftCommodityBeanCheckList(new ArrayList<GiftCommodityDetailBean>());
          if (delFlg) {
            bean.getGiftCommodityBean().setGiftCommodityBeanList(new ArrayList<GiftCommodityDetailBean>());
          }
        }
      }

      if (delFlg) {
        // 从购物车的其他已领取赠品中删除
        int listNum = 0;
        for (int i = 0; i < giftCommodityCodeListDelete.size(); i++) {
          List<CartCommodityDetailListBean> otherGiftCmdtyList = bean.getCartOtherGiftCommodityList();
          for (int j = 0; j < otherGiftCmdtyList.size(); j++) {
            CartCommodityDetailListBean otherGiftBean = otherGiftCmdtyList.get(j);
            String skuCodeTemp = otherGiftBean.getSkuCode();
            if ((otherGiftBean.getCampaignCode() + WebConstantCode.SKU_CODE_SEPARATOR + skuCodeTemp)
                .equals(giftCommodityCodeListDelete.get(i))) {
              bean.getCartOtherGiftCommodityList().remove(otherGiftBean);
            }
          }
          for (CartItem cartItem : getCart().get()) {
            if (cartItem.getSkuCode().equals(giftCommodityCodeListDelete.get(i))) {
              getCart().remove(cartItem.getShopCode(), giftCommodityCodeListDelete.get(i), "");
            }
          }
          listNum++;
        }

        // 可领取商品再计算
        if (listNum > 0) {
          multipleGiftProcess(bean, checkFlg, delFlg, false);
        }
      }
    } else {
      if (delFlg) {
        // 从购物车中删除所有已领取赠品
        clearAcceptedGiftOfCartOrBean(bean, checkFlg, delFlg);
      }
      return false;
    }
    return true;
  }

  /**
   * 清空购物车及Bean中的多重优惠（多关联）活动的赠品
   * 
   * @param bean
   */
  private boolean clearAcceptedGiftOfCartOrBean(NeworderCommodityBean bean, boolean checkFlg, boolean delFlg) {
    // 从购物车中删除所有已领取赠品
    List<CartCommodityDetailListBean> cartCommodityDetailListBeanList = bean.getCartOtherGiftCommodityList();
    int intNum = cartCommodityDetailListBeanList.size();

    bean.setCartOtherGiftCommodityList(new ArrayList<CartCommodityDetailListBean>());

    getCart().clearAcceptedGift();
    bean.getGiftCommodityBean().setGiftCommodityBeanList(new ArrayList<GiftCommodityDetailBean>());
    bean.getGiftCommodityBean().setGiftCommodityBeanCheckList(new ArrayList<GiftCommodityDetailBean>());

    if (intNum > 0) {
      // 可领取商品再计算
      multipleGiftProcess(bean, checkFlg, delFlg, false);
    }
    return true;
  }

  /**
   * 获得能否添加赠品(可领取商品)区分
   * 
   * @param 对象商品区分
   *          campaignConditionFlg
   * @param 购物车中的普通商品
   *          cartCommodityCodeList
   * @param 多关联促销活动的关联商品
   *          commodityCodeList
   */
  private boolean isCanAddGiftCodeToList(Long campaignConditionFlg, List<String> cartCommodityCodeList,
      List<String> commodityCodeList) {
    boolean addFlg = false;
    // 条件.对象商品：1：包含
    if (CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue().equals(campaignConditionFlg)) {
      for (int i = 0; i < cartCommodityCodeList.size(); i++) {
        for (int j = 0; j < commodityCodeList.size(); j++) {
          if (commodityCodeList.get(j).toString().equals(cartCommodityCodeList.get(i).toString())) {
            addFlg = true;
            break;
          }
        }
        if (addFlg) {
          break;
        }
      }

    } else if (CampaignConditionFlg.CAMPAIGN_CONDITION_ONLY.longValue().equals(campaignConditionFlg)) {
      // 仅有
      for (int i = 0; i < cartCommodityCodeList.size(); i++) {
        addFlg = false;
        for (int j = 0; j < commodityCodeList.size(); j++) {
          if (commodityCodeList.get(j).toString().equals(cartCommodityCodeList.get(i).toString())) {
            addFlg = true;
            break;
          }
        }
        if (!addFlg) {
          break;
        } else {
          continue;
        }
      }
    }
    return addFlg;
  }

  /**
   * 取得普通商品及套餐商品最小在库数(查询表示购买套餐在库数时)
   * 
   * @param shopCode
   * @param skuCodes
   * @param row
   * @return
   */
  public boolean getAvailableStock(String shopCode, String skuCode, CommodityListDetailBean row) {
    return getAvailableStock(shopCode, skuCode, row, true, "");
  }

  /**
   * 取得普通商品及套餐商品最小在库数(共通)
   * 
   * @param shopCode
   *          店编号
   * @param skuCode
   *          sku编号
   * @param row
   * @param queryFlg
   *          是查询显示时：true,其他场合只取得在库数：false
   * @param skuCodes
   *          套餐sku编号 套餐商品的sku编号 + ":" + 套餐详细商品的sku编号
   * @return
   */
  public boolean getAvailableStock(String shopCode, String skuCode, CommodityListDetailBean row, boolean queryFlg, String skuCodes) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CampainFilter cf = new CampainFilter();
    List<String> giftCampaignMainList = new ArrayList<String>();
    List<String> compositionSkuCodeList = new ArrayList<String>(); // 套餐商品的详细商品(SKU编号)
    List<String> giftSkuCodeList = new ArrayList<String>(); // 促销活动的赠品(SKU编号)
    List<String> giftCommodityCodeList = new ArrayList<String>(); // 促销活动的赠品(商品编号)
    Long minStockQuantity = 0L; // 最小有效在库数
    boolean returnValid = true;
    String setCommodityFlg = ""; // 对象商品区分
    boolean noStockManageFlg = true; // 不管理库存区分

    // 取得商品信息
    CommodityInfo commodityInfo = service.getSkuInfo(shopCode, skuCode);

    if (commodityInfo != null) {
      // 在库不管理
      if (queryFlg) {
        if (!SetCommodityFlg.OBJECTIN.longValue().equals(commodityInfo.getHeader().getSetCommodityFlg())) {
          if (!(StockManagementType.NONE.longValue().equals(commodityInfo.getHeader().getStockManagementType()) || StockManagementType.NOSTOCK
              .longValue().equals(commodityInfo.getHeader().getStockManagementType()))) {
            noStockManageFlg = false;
          }
        }
      }

      // 不是套餐商品的时候
      // 期间内的特别商品活动存在验证

      // 取得当前进行中的活动列表
      List<CampaignMain> campaignMainList = cf.getSkuCampaigns(commodityInfo.getHeader().getCommodityCode());
      if (campaignMainList != null && campaignMainList.size() > 0) {
        for (CampaignMain campaignMain : campaignMainList) {
          // 赠品促销/特定商品
          if (CampaignMainType.GIFT.longValue().equals(campaignMain.getCampaignType())) {
            giftCampaignMainList.add(campaignMain.getCampaignCode());
          }
        }
      }

      setCommodityFlg = NumUtil.toString(commodityInfo.getHeader().getSetCommodityFlg());

      // 有特定商品的促销活动时
      giftCommodityCodeList = giftCommodityCodeList(giftCampaignMainList);

      if (giftCommodityCodeList != null) {
        for (String commodityCode : giftCommodityCodeList) {
          CommodityInfo commodityObj = service.getCommodityInfo(shopCode, commodityCode);
          if (commodityObj != null) {
            // 在库不管理
            if (queryFlg) {
              if (!(StockManagementType.NONE.longValue().equals(commodityObj.getHeader().getStockManagementType()) || StockManagementType.NOSTOCK
                  .longValue().equals(commodityObj.getHeader().getStockManagementType()))) {
                noStockManageFlg = false;
              }
            }
            giftSkuCodeList.add(commodityObj.getHeader().getRepresentSkuCode()); // 赠品取代表sku编号
          }
        }

      }
      // 取得最小有效在库数
      // 20130625 txw add start
      if (StringUtil.hasValue(commodityInfo.getHeader().getOriginalCommodityCode())
          && commodityInfo.getHeader().getCombinationAmount() != null) {
        minStockQuantity = service.getAvailableStock(shopCode, commodityInfo.getHeader().getOriginalCommodityCode(), false,
            compositionSkuCodeList, giftSkuCodeList);
        minStockQuantity = new Double(Math.floor(minStockQuantity / commodityInfo.getHeader().getCombinationAmount())).longValue();
      } else {
        minStockQuantity = service.getAvailableStock(shopCode, skuCode, false, compositionSkuCodeList, giftSkuCodeList);
      }
      // 20130625 txw add end

      // 套餐商品存在验证
      if (StringUtil.hasValue(setCommodityFlg) && SetCommodityFlg.OBJECTIN.getValue().equals(setCommodityFlg)) {
        // 套餐商品的时候

        if (queryFlg) {
          // 套餐商品不管理在库数时，画面显示时，把在库管理区分设设置为2：在库管理
          if (StockManagementType.NONE.longValue().equals(commodityInfo.getHeader().getStockManagementType())
              || StockManagementType.NOSTOCK.longValue().equals(commodityInfo.getHeader().getStockManagementType())) {
            row.setStockManagementType(StockManagementType.WITH_QUANTITY.getValue());
          }
          // 套餐的销售价格设置为套餐商品的商品单价
          row.setRetailPrice(commodityInfo.getDetail().getUnitPrice());
        }

        boolean canBuyFlg = true;
        if (StringUtil.hasValue(skuCodes)) {
          // 取得套餐详细商品
          List<String> skuCodeList = Arrays.asList(skuCodes.split(SetCommodityUtil.SET_COMMODITY_DELIMITER));
          canBuyFlg = true;
          if (skuCodeList != null && skuCodeList.size() > 0) {
            int rowInt = 1;
            for (String skuCodeFor : skuCodeList) {
              if (rowInt > 1) {
                CommodityAvailability availability = service.isAvailable(shopCode, skuCodeFor, 1, false);
                if (CommodityAvailability.AVAILABLE.equals(availability)) {
                  compositionSkuCodeList.add(skuCodeFor);
                } else {
                  canBuyFlg = false;
                  break;
                }
              }
              rowInt++;
            }
          }
        } else {
          // 取得套餐详细商品
          List<SetCommodityComposition> setCompositions = service.getSetCommodityCompositipon(commodityInfo.getHeader()
              .getCommodityCode(), shopCode);
          canBuyFlg = true;
          if (setCompositions != null && setCompositions.size() > 0) {

            if (queryFlg) {
              // 套餐明细商品的商品单价合计
              BigDecimal unitPriceTotal = BigDecimal.ZERO;
              for (SetCommodityComposition setCommodityComposition : setCompositions) {
                CommodityInfo commodity = service.getCommodityInfo(setCommodityComposition.getShopCode(), setCommodityComposition
                    .getChildCommodityCode());
                if (commodity != null && commodity.getHeader() != null) {
                  unitPriceTotal = unitPriceTotal.add(commodity.getHeader().getRepresentSkuUnitPrice());
                }
              }
              // 设置单价
              row.setUnitPrice(unitPriceTotal);
            }

            // 库存判断
            for (SetCommodityComposition setCommodityComposition : setCompositions) {
              CommodityInfo commodity = service.getCommodityInfo(setCommodityComposition.getShopCode(), setCommodityComposition
                  .getChildCommodityCode());
              if (commodity != null && commodity.getHeader() != null) {

                if (queryFlg) {
                  // 在库不管理
                  if (!(StockManagementType.NONE.longValue().equals(commodity.getHeader().getStockManagementType()) || StockManagementType.NOSTOCK
                      .longValue().equals(commodity.getHeader().getStockManagementType()))) {
                    noStockManageFlg = false;
                  }
                }

                // 取得详细商品的库存数最大的sku编号
                String skuCodeOfMaxStock = service.getCompositionDetailSkuOfMaxAvailableStock(
                    setCommodityComposition.getShopCode(), setCommodityComposition.getChildCommodityCode());
                CommodityAvailability availability = service.isAvailable(commodity.getHeader().getShopCode(), skuCodeOfMaxStock, 1,
                    false);
                if (CommodityAvailability.AVAILABLE.equals(availability)) {
                  compositionSkuCodeList.add(skuCodeOfMaxStock);
                } else {
                  canBuyFlg = false;
                  break;
                }
              }
            }
          }
        }

        // 取得最小有效在库数
        minStockQuantity = service.getAvailableStock(shopCode, skuCode, true, compositionSkuCodeList, giftSkuCodeList);
        if (!canBuyFlg) {
          minStockQuantity = 0L;
        }
      }

      row.setStockQuantity(minStockQuantity);

      if (row.getStockQuantity() <= 0L) {
        if (queryFlg) {
          row.setDisplayCartButton(false);
        }
        returnValid = false;
      } else {
        if (queryFlg) {
          row.setDisplayCartButton(true);
        }
      }

      if (queryFlg) {
        // 商品本身，赠品，套餐详细商品都不管理库存时
        if (noStockManageFlg) {
          row.setStockQuantity(-1L);
        }
      }
    }
    return returnValid;
  }

  /**
   * 普通商品及套餐商品最小在库数验证(显示购买套详细餐时)
   * 
   * @param shopCode
   * @param skuCodes
   * @param row
   * @return
   */
  public boolean minAvailableStockCheck(String shopCode, String skuCode, CommodityListDetailBean row) {
    return getAvailableStock(shopCode, skuCode, row, false, "");
  }

  /**
   * 普通商品及套餐商品最小在库数验证(购买套餐时)
   * 
   * @param shopCode
   * @param skuCode
   * @param row
   * @param skuCodes
   * @return
   */
  public boolean minAvailableStockCheck(String shopCode, String skuCode, CommodityListDetailBean row, String skuCodes) {
    return getAvailableStock(shopCode, skuCode, row, false, skuCodes);
  }

  /**
   * 取得促销活动的赠品
   * 
   * @param giftCampaignMainList
   * @return 促销活动的赠品List
   */
  public List<String> giftCommodityCodeList(List<String> giftCampaignMainList) {
    List<String> giftCommodityCodeList = new ArrayList<String>();
    CampaignDoingsDao doingsDao = DIContainer.getDao(CampaignDoingsDao.class);

    for (String campainCode : giftCampaignMainList) {
      CampaignDoings campaignDoings = doingsDao.load(campainCode);
      if (campaignDoings != null) {
        String commodityCode = campaignDoings.getAttributrValue();
        if (StringUtil.hasValue(commodityCode) && StringUtil.hasValue(commodityCode.trim())) {
          String[] commodityCodeArray = commodityCode.split(WebConstantCode.COMMODITY_COMMA);
          for (int i = 0; i < commodityCodeArray.length; i++) {
            giftCommodityCodeList.add(commodityCodeArray[i]);
          }
        }
      }
    }

    return giftCommodityCodeList;
  }

  /**
   * 从cart中取值设置多关联活动的赠品到Bean中
   * 
   * @return
   */
  public List<CartCommodityDetailListBean> createBeanFromCartOfGift() {
    List<CartItem> cartItemList = new ArrayList<CartItem>();
    cartItemList.addAll(getCart().get());
    cartItemList.addAll(getCart().getReserve());

    List<CartCommodityDetailListBean> cartList = new ArrayList<CartCommodityDetailListBean>();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();

    for (CartItem item : cartItemList) {
      // 赠品
      if (CommodityType.GIFT.longValue().equals(item.getCommodityInfo().getCommodityType())) {
        String skuCode = item.getSkuCode();
        if (item.getSkuCode().contains(WebConstantCode.SKU_CODE_SEPARATOR)) {
          skuCode = item.getSkuCode().split(WebConstantCode.SKU_CODE_SEPARATOR)[1];
        }
        CommodityInfo commodity = service.getSkuInfo(item.getShopCode(), skuCode);
        String shopCode = item.getShopCode();
        String commodityCode = item.getCommodityCode();
        Campaign campaign = service.getAppliedCampaignInfo(shopCode, commodityCode);
        Price price = new Price(commodity.getHeader(), commodity.getDetail(), campaign, taxRate);

        CartCommodityDetailListBean cartDetail = new CartCommodityDetailListBean();
        cartDetail.setShopCode(item.getShopCode());
        cartDetail.setShopName(item.getShopName());
        cartDetail.setCommodityCode(item.getCommodityCode());
        cartDetail.setCommodityName(item.getCommodityName());
        cartDetail.setStandard1Name(item.getStandardDetail1Name());
        cartDetail.setStandard2Name(item.getStandardDetail2Name());
        cartDetail.setSkuCode(skuCode);
        cartDetail.setControlSkuCode(item.getCommodityInfo().getMultipleCampaignCode() + WebConstantCode.SKU_CODE_SEPARATOR
            + skuCode);

        // 数量
        cartDetail.setPurchasingAmount("1");

        cartDetail.setGiftList(utilService.getGiftList(item.getShopCode(), item.getCommodityCode()));
        cartDetail.setGiftCode(item.getGiftCode());

        // 销售价格
        cartDetail.setRetailPrice(String.valueOf(price.getRetailPrice()));

        // 商品重量
        cartDetail.setWeight(item.getWeight());
        if (item.getWeight() != null) {
          cartDetail.setSummaryWeight(item.getWeight().multiply(new BigDecimal(item.getQuantity())));
        } else {
          cartDetail.setSummaryWeight(null);
        }

        BigDecimal giftPrice;
        if (item.getGiftPrice() != null) {
          giftPrice = item.getGiftPrice();
        } else {
          giftPrice = BigDecimal.ZERO;
        }

        // 商品合计金额
        BigDecimal samaryPrice = BigDecimalUtil.multiply(BigDecimalUtil.add(price.getRetailPrice(), giftPrice), 1);
        cartDetail.setSamaryPrice(String.valueOf(samaryPrice));

        // 活动
        cartDetail.setCampaignCode(item.getCommodityInfo().getMultipleCampaignCode());
        cartDetail.setCampaignName(item.getCommodityInfo().getMultipleCampaignName());

        cartList.add(cartDetail);
      }
    }
    return cartList;
  }

  /**
   * 设置套餐信息及详细商品一览信息
   * 
   * @param shopCode
   *          店铺编号
   * @param skuCode
   *          SKU编号
   * @param bean
   *          NeworderCommodityBean
   */
  public void setSetCommodityToBean(String shopCode, String skuCode, NeworderCommodityBean bean) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    BigDecimal retailPriceTotal = BigDecimal.ZERO; // 套餐详细商品的代表SKU代价的合计

    // 取得商品情报
    CommodityInfo commodityInfo = service.getSkuInfo(shopCode, skuCode);

    // 套餐商品SKU编号
    bean.getCommodityCompositionBean().setSkuCode(skuCode);
    // 店编号
    bean.getCommodityCompositionBean().setShopCode(shopCode);

    if (commodityInfo != null) {
      if (commodityInfo.getHeader() != null) {
        // 套餐商品编号
        bean.getCommodityCompositionBean().setCommodityCode(commodityInfo.getHeader().getCommodityCode());

        // 套餐名称 套餐商品名称
        bean.getCommodityCompositionBean().setCommodityCompositionName(commodityInfo.getHeader().getCommodityName());
      }
      if (commodityInfo.getDetail() != null) {
        // 套餐价格
        bean.getCommodityCompositionBean().setCommodityCopositionPrice(commodityInfo.getDetail().getUnitPrice());
      }
    }

    // 取得套餐构成商品信息
    List<CommodityCompositionContainer> commodityCompositionContainerList = service.getCommodityCompositionContainerList(shopCode,
        commodityInfo.getHeader().getCommodityCode());
    List<CommodityCompositionDetailBean> setCommodityCompositionDetailBeanList = new ArrayList<CommodityCompositionDetailBean>();

    // 取得套餐详细商品
    if (commodityCompositionContainerList != null) {

      for (CommodityCompositionContainer commodityCompositionContainer : commodityCompositionContainerList) {

        CommodityCompositionDetailBean detailBean = new CommodityCompositionDetailBean();

        // 设置商品编号
        detailBean.setChildCommodityCode(commodityCompositionContainer.getCommodityCode());

        // 代表SKU编号
        detailBean.setChildRepresentSkuCode(commodityCompositionContainer.getRepresentSkuCode());

        detailBean.setSelectedSkuCode(commodityCompositionContainer.getRepresentSkuCode());

        // 商品名称
        detailBean.setChildCommodityName(commodityCompositionContainer.getCommodityName());

        // 价格
        retailPriceTotal = retailPriceTotal.add(new BigDecimal(commodityCompositionContainer.getRepresentSkuUnitPrice()));
        // 销售价格
        detailBean.setChildRepresentSkuUnitPrice(new BigDecimal(commodityCompositionContainer.getRepresentSkuUnitPrice())
            .toString());

        if (commodityCompositionContainer.getCommodityDetailList() != null) {
          for (CommodityDetail commodityDetail : commodityCompositionContainer.getCommodityDetailList()) {

            // 规格
            NameValue standardNameNameValue = getSkuNameValue(commodityDetail.getShopCode(), commodityDetail.getSkuCode());
            detailBean.getStandardNameList().add(standardNameNameValue);
          }
        }
        setCommodityCompositionDetailBeanList.add(detailBean);
      }
    }
    // 优惠价格
    bean.getCommodityCompositionBean().setCommodityCopositionPreferentialPrice(
        retailPriceTotal.subtract(bean.getCommodityCompositionBean().getCommodityCopositionPrice()));

    bean.getCommodityCompositionBean().setCommodityCompositionDetailBeanList(setCommodityCompositionDetailBeanList);
  }

  /**
   * 显示商品名称+规格名称
   * 
   * @param shopCode
   * @param skuCode
   * @param isOnlyOne
   * @return
   */
  public NameValue getSkuNameValue(String shopCode, String skuCode) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    String name = "";

    CommodityInfo skuDetail = service.getSkuInfo(shopCode, skuCode);
    if (skuDetail != null && skuDetail.getDetail() != null) {
      name = skuCode
          + createStandardDetailName(skuDetail.getDetail().getStandardDetail1Name(), skuDetail.getDetail().getStandardDetail2Name());
    }
    return new NameValue(name, skuCode);
  }

  /**
   * 作成规格详细名称<BR>
   * 规格详细1名称と规格详细2名称都存在的场合<BR>
   * (规格详细1名称/规格详细2名称)<BR>
   * 仅仅规格详细1名称存在的场合<BR>
   * (规格详细1名称)<BR>
   * 其他场合返回空
   * 
   * @param detail1Name
   * @param detail2Name
   * @return 规格详细名称
   */
  public String createStandardDetailName(String detail1Name, String detail2Name) {
    String detail = "";
    if (StringUtil.hasValue(detail1Name) && StringUtil.hasValue(detail2Name)) {
      detail = "(" + detail1Name + "/" + detail2Name + ")";
    } else if (StringUtil.hasValue(detail1Name) && StringUtil.isNullOrEmpty(detail2Name)) {
      detail = "(" + detail1Name + ")";
    } else if (StringUtil.hasValue(detail2Name) && StringUtil.isNullOrEmpty(detail1Name)) {
      detail = "(" + detail2Name + ")";
    } else {
      detail = "";
    }
    return detail;
  }

  /**
   * 向购物车中设置套餐详细商品
   * 
   * @param neworderCommodityBean
   * @param shopCode
   * @param commodityCode
   */
  public void setCompositionToCart(NeworderCommodityBean bean, String shopCode, String skuCode) {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    List<CompositionItem> compositionItemList = new ArrayList<CompositionItem>();
    SetCommodityCompositionDao setCommodityCompositionDao = DIContainer.getDao(SetCommodityCompositionDao.class);
    TaxUtil u = DIContainer.get("TaxUtil");
    Long taxRate = u.getTaxRate();
    // 获取购物车中对象套餐商品的信息
    CartItem cartItem = getCart().get(shopCode, skuCode);

    // 循环Bean中套餐详细信息
    for (CommodityCompositionDetailBean detailCom : bean.getCommodityCompositionBean().getCommodityCompositionDetailBeanList()) {

      CompositionItem item = new CompositionItem();
      // 取得商品信息
      CommodityInfo compositionDetailInfo = catalogService.getSkuInfo(shopCode, detailCom.getSelectedSkuCode());
      // 取得套餐明细信息
      SetCommodityComposition composition = setCommodityCompositionDao.load(shopCode, bean.getCommodityCompositionBean()
          .getCommodityCode(), compositionDetailInfo.getHeader().getCommodityCode());

      if (compositionDetailInfo != null) {
        item.setCommodityName(compositionDetailInfo.getHeader().getCommodityName());
        item.setStandardDetail1Name(compositionDetailInfo.getDetail().getStandardDetail1Name());
        item.setStandardDetail2Name(compositionDetailInfo.getDetail().getStandardDetail2Name());
        item.setShopCode(compositionDetailInfo.getHeader().getShopCode());
        item.setCommodityCode(compositionDetailInfo.getHeader().getCommodityCode());
        item.setSkuCode(detailCom.getSelectedSkuCode());
        Price price = new Price(compositionDetailInfo.getHeader(), compositionDetailInfo.getDetail(), null, taxRate);
        item.setUnitPrice(price.getUnitPrice());
        item.setCommodityTaxType(compositionDetailInfo.getHeader().getCommodityTaxType());
        item.setRepresentFlg(compositionDetailInfo.getHeader().getRepresentSkuCode().equals(detailCom.getSelectedSkuCode()));
        if (composition != null) {
          item.setRetailPrice(composition.getRetailPrice());
        }
        item.setRetailTax(price.retailTaxCharge().longValue());
        item.setCommodityTaxRate(taxRate);
        item.setWeight(compositionDetailInfo.getDetail().getWeight());
        item.setCommodityTax(price.getUnitTaxCharge());
        item.setWeight(compositionDetailInfo.getDetail().getWeight());

        compositionItemList.add(item);
      }
    }
    cartItem.getCommodityInfo().setCompositionList(compositionItemList);
  }

  /**
   * 向购物车中设置特定商品促销活动的赠品
   * 
   * @param 店铺编号
   * @param SKU编号
   * @param 数量
   */
  public void setGiftCommodityToCart(String shopCode, String skuCode) {
    CampainFilter campainFilter = new CampainFilter();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodity = service.getSkuInfo(shopCode, skuCode);
    // 套餐商品时
    if (skuCode.contains(SetCommodityUtil.SET_COMMODITY_DELIMITER)) {
      commodity = service.getSkuInfo(shopCode, skuCode.split(SetCommodityUtil.SET_COMMODITY_DELIMITER)[0]);
    }
    CartItem cartItem = getCart().get(shopCode, skuCode);
    int quantity = 0;
    if (cartItem != null) {
      quantity += cartItem.getQuantity();
    }
    // 对象商品所在期间内特定商品活动的赠品
    List<GiftItem> giftItems = campainFilter.getOtherGiftCodeBySku(commodity.getHeader().getCommodityCode(), shopCode);
    if (giftItems != null) {
      CampaignMainDao camDao = DIContainer.getDao(CampaignMainDao.class);
      for (int i = 0; i < giftItems.size(); i++) {
        jp.co.sint.webshop.data.dto.CampaignMain camMain = camDao.load(giftItems.get(i).getCampaignCode());
        Long giftAmount = 1L;
        if (camMain.getGiftAmount() != null ) {
          giftAmount = camMain.getGiftAmount();
        }
        giftItems.get(i).setQuantity(giftAmount.intValue());
      }
    }
    cartItem.getCommodityInfo().setGiftList(giftItems);
  }
  // 2012/11/20 促销对应 新建订单_商品选择 ob add end
  
  private  CartCommodityDetailListBean createCartCommodity( CartItem item,Price price ,CatalogService service,UtilService utilService,Long taxRate ){
    CartCommodityDetailListBean cartDetail = new CartCommodityDetailListBean();
    cartDetail.setShopCode(item.getShopCode());
    cartDetail.setShopName(item.getShopName());
    cartDetail.setCommodityCode(item.getCommodityCode());
    cartDetail.setCommodityName(item.getCommodityName());
    cartDetail.setStandard1Name(item.getStandardDetail1Name());
    cartDetail.setStandard2Name(item.getStandardDetail2Name());
    cartDetail.setSkuCode(item.getSkuCode());
    cartDetail.setPurchasingAmount(String.valueOf(item.getQuantity()));
    cartDetail.setGiftList(utilService.getGiftList(item.getShopCode(), item.getCommodityCode()));
    cartDetail.setGiftCode(item.getGiftCode());
    if (StringUtil.hasValue(item.getOriginalCommodityCode()) && item.getCombinationAmount() != null) {
      cartDetail.setRetailPrice(NumUtil.toString(price.getRetailPrice().multiply(new BigDecimal(item.getCombinationAmount()))));
    } else {
      cartDetail.setRetailPrice(String.valueOf(price.getRetailPrice()));
    }
    if (StringUtil.hasValue(item.getOriginalCommodityCode())) {
      cartDetail.setOriginalCommodityCode(item.getOriginalCommodityCode());
      cartDetail.setCombinationAmount(item.getCombinationAmount());
    }
    cartDetail.setWeight(item.getWeight());
    if (item.getWeight() != null) {
      cartDetail.setSummaryWeight(item.getWeight().multiply(new BigDecimal(item.getQuantity())));
    } else {
      cartDetail.setSummaryWeight(null);
    }
    cartDetail.setSamaryPrice(createPrice(item,price.getRetailPrice()));

    // 2012/11/22 促销对应 新建订单_商品选择 ob add start
    cartDetail.setCommodityType(item.getCommodityInfo().getCommodityType());
    cartDetail.setCommodityFlg(item.getCommodityInfo().getSetCommodityFlg());
    cartDetail.setControlSkuCode(item.getSkuCode());
    if (item.getSkuCode().contains(SetCommodityUtil.SET_COMMODITY_DELIMITER)) {
      cartDetail.setSkuCode(item.getSkuCode().split(SetCommodityUtil.SET_COMMODITY_DELIMITER)[0]);
    }
    // 套餐详细商品
    List<CompositionItem> compositionItemList = item.getCommodityInfo().getCompositionList();
    String commodityCompositionName = ""; // 套餐内容
    BigDecimal weightAll = BigDecimal.ZERO; // 套餐详细商品的重量合计
    if (SetCommodityFlg.OBJECTIN.longValue().equals(item.getCommodityInfo().getSetCommodityFlg())) {
      // 套餐的价格重新设置
      BigDecimal giftPrice;
      if (item.getGiftPrice() != null) {
        giftPrice = item.getGiftPrice();
      } else {
        giftPrice = BigDecimal.ZERO;
      }
      BigDecimal samaryPrice = null;
      cartDetail.setRetailPrice(item.getCommodityInfo().getRetailPrice().toString());
      samaryPrice = BigDecimalUtil.multiply(BigDecimalUtil.add(item.getCommodityInfo().getRetailPrice(), giftPrice), item
          .getQuantity());
      cartDetail.setSamaryPrice(String.valueOf(samaryPrice));

      // 套餐明细商品list的长度大于0的场合
      if (compositionItemList != null && compositionItemList.size() > 0) {
        for (CompositionItem compositionItem : compositionItemList) {
          if (StringUtil.hasValue(compositionItem.getStandardDetail1Name())
              && StringUtil.hasValue(compositionItem.getStandardDetail2Name())) {
            commodityCompositionName += compositionItem.getCommodityName() + "(" + compositionItem.getStandardDetail1Name()
                + "/" + compositionItem.getStandardDetail2Name() + ")+";
          } else if (StringUtil.hasValue(compositionItem.getStandardDetail1Name())
              && !StringUtil.hasValue(compositionItem.getStandardDetail2Name())) {
            commodityCompositionName += compositionItem.getCommodityName() + "(" + compositionItem.getStandardDetail1Name()
                + ")+";
          } else if (!StringUtil.hasValue(compositionItem.getStandardDetail1Name())
              && StringUtil.hasValue(compositionItem.getStandardDetail2Name())) {
            commodityCompositionName += compositionItem.getCommodityName() + "(" + compositionItem.getStandardDetail2Name()
                + ")+";
          } else {
            commodityCompositionName += compositionItem.getCommodityName() + "+";
          }
          weightAll = weightAll.add(compositionItem.getWeight());
        }
        if (StringUtil.hasValue(commodityCompositionName)
            && commodityCompositionName.lastIndexOf("+") == (commodityCompositionName.length() - 1)) {
          commodityCompositionName = commodityCompositionName.substring(0, commodityCompositionName.length() - 1);
        }
      }
      cartDetail.setCommodityCompositionName(commodityCompositionName);
      cartDetail.setCompositionItemList(compositionItemList);
      cartDetail.setWeight(weightAll);
      cartDetail.setSummaryWeight(weightAll.multiply(new BigDecimal(item.getQuantity())));
    }

    // 赠品
    UtilService utilSvc = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
    List<GiftItem> itemList = item.getCommodityInfo().getGiftList();
    List<CartGiftCommodityDetailListBean> giftCommodityList = new ArrayList<CartGiftCommodityDetailListBean>();
    if (itemList != null) {
      for (GiftItem giftItem : itemList) {
        CartGiftCommodityDetailListBean giftBean = new CartGiftCommodityDetailListBean();
        CommodityInfo commodityInfo = service.getSkuInfo(giftItem.getShopCode(), giftItem.getGiftSkuCode());
        if (commodityInfo != null) {
          Price priceGift = new Price(commodityInfo.getHeader(), commodityInfo.getDetail(), null, taxRate);
          giftBean.setShopCode(giftItem.getShopCode());
          giftBean.setShopName(utilSvc.getShopName(giftItem.getShopCode()));
          giftBean.setCommodityCode(giftItem.getGiftCode());
          giftBean.setCommodityName(giftItem.getGiftName());
          giftBean.setSkuCode(giftItem.getGiftSkuCode());
          giftBean.setStandard1Name(giftItem.getStandardDetail1Name());
          giftBean.setStandard2Name(giftItem.getStandardDetail2Name());

          // 普通商品的数量
          giftBean.setPurchasingAmount(giftItem.getQuantity()+"");
          // 商品销售价合格
          giftBean.setRetailPrice(String.valueOf(priceGift.getRetailPrice()));

          // 商品合计重量
          giftBean.setWeight(giftItem.getWeight());
          if (giftItem.getWeight() != null) {
            giftBean.setSummaryWeight(giftItem.getWeight().multiply(new BigDecimal(giftItem.getQuantity())));
          } else {
            giftBean.setSummaryWeight(null);
          }

          // 商品合计金额
          BigDecimal samaryPriceBig = BigDecimalUtil.multiply(BigDecimalUtil.add(priceGift.getRetailPrice(), BigDecimal.ZERO),
              giftItem.getQuantity());
          giftBean.setSamaryPrice(String.valueOf(samaryPriceBig));

          // 活动
          giftBean.setCampaignCode(giftItem.getCampaignCode());
          giftBean.setCampaignName(giftItem.getCampaignName());

          giftCommodityList.add(giftBean);
        }
      }
    }
    cartDetail.setGiftCommodityList(giftCommodityList);
    // 2012/11/22 促销对应 新建订单_商品选择 ob add end
    return cartDetail;
  }
  
  private String createPrice( CartItem item,BigDecimal retailPrice ){
    BigDecimal giftPrice;
    if (item.getGiftPrice() != null) {
      giftPrice = item.getGiftPrice();
    } else {
      giftPrice = BigDecimal.ZERO;
    }
    BigDecimal samaryPrice = null;
    if (StringUtil.hasValue(item.getOriginalCommodityCode()) && item.getCombinationAmount() != null) {
      samaryPrice = BigDecimalUtil.multiply(BigDecimalUtil.add(retailPrice.multiply(
          new BigDecimal(item.getCombinationAmount())), giftPrice), item.getQuantity());
    } else {
      samaryPrice = BigDecimalUtil.multiply(BigDecimalUtil.add(retailPrice, giftPrice), item.getQuantity());
    }
    return samaryPrice.toString();
  }
  
  
  
  /** 赠品重新计算 **/
  public void giftReset(NeworderCommodityBean bean) {
    
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    
    List<CartCommodityDetailListBean> items = bean.getCartCommodityList();
    
    // 购物车中商品符合赠品促销的活动编号
    List<String> compaignCodes = new ArrayList<String>();
    for (CartCommodityDetailListBean item : items) {
      if (item.getGiftCommodityList() != null && item.getGiftCommodityList().size() > 0) {
        String compaignCode = item.getGiftCommodityList().get(0).getCampaignCode();
        if (StringUtil.hasValue(compaignCode) && !compaignCodes.contains(compaignCode)) {
          compaignCodes.add(compaignCode);
        }
      }
    }
    
    for (String code : compaignCodes) {
      // 赠品活动中的关联商品
      CampaignLine campaignLine = communicationService.loadCampaignLine(code);
      if (campaignLine == null) {
        continue;
      }
      List<CampaignCondition> cList = campaignLine.getConditionList();
      String commodityStr = ""; // 关联商品编号接连串
      if (cList != null && cList.size() > 0) {
        for (int i = 0; i < cList.size(); i++) {
          CampaignCondition condition = cList.get(i);
          if (NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(condition.getCampaignConditionType())) {
            commodityStr = cList.get(i).getAttributrValue();
            break;
          }
          if (NewCampaignConditionType.COMMODITY_RELATED.longValue().equals(condition.getCampaignConditionType())) {
            commodityStr = cList.get(i).getAttributrValue();
            break;
          }
        }
      }
      if (StringUtil.isNullOrEmpty(commodityStr)) {
        continue;
      }
      
      String firstCommdityCode = null; //取符合活动的第一个商品编号
      int totalCommodityNum = 0;   //符合当前活动的商品总数量
      for (CartCommodityDetailListBean item : items) {
        if (item.getGiftCommodityList() == null || item.getGiftCommodityList().size() == 0
            || !code.equals(item.getGiftCommodityList().get(0).getCampaignCode())) {
          continue;
        }
        //只取第一个商品
        String commodiyCode = item.getCommodityCode();
        String []commodityCodes = commodityStr.split(",");
        for(String curCode:commodityCodes){
          if(StringUtil.isNullOrEmpty(curCode)){
            continue;
          }
          if(curCode.equals(commodiyCode)){
            totalCommodityNum += NumUtil.toLong(item.getPurchasingAmount()) ;
            
            if(firstCommdityCode == null){
              firstCommdityCode = item.getCommodityCode();
            }else{
              //清空后面的商品赠品信息
//              item.setGiftCommodityList(null);
              item.setGiftCommodityList(new ArrayList<CartGiftCommodityDetailListBean>());
//              item.setCampaignName(null);
//              item.setCampaignCode(null);
              //item.setGiftName(null);
              //item.setGiftOptionCode("");
            }
          }
        }
      }
      Long giftNum   = 0L;
      //如果最小购买数为0则赠品最多只送活动中的赠品数量
      if("0".equals(NumUtil.toString(campaignLine.getCampaignMain().getMinCommodityNum()))){
        giftNum = campaignLine.getCampaignMain().getGiftAmount();
      }else{
        giftNum = (totalCommodityNum / campaignLine.getCampaignMain().getMinCommodityNum())
        * campaignLine.getCampaignMain().getGiftAmount();
      }
      //重置赠品数量
      for(CartCommodityDetailListBean item : items){
        if(StringUtil.hasValue(firstCommdityCode) && firstCommdityCode.equals(item.getCommodityCode())){
          
          if(giftNum.intValue() !=0 && item.getGiftCommodityList()!=null){
            for(CartGiftCommodityDetailListBean gift:item.getGiftCommodityList()){
              gift.setPurchasingAmount(NumUtil.toString(giftNum));
            }           
          }else{ //如果赠品为0，清空第一个商品中的赠品
//            item.setGiftCommodityList(null);
            item.setGiftCommodityList(new ArrayList<CartGiftCommodityDetailListBean>());
//            item.setCampaignName(null);
//            item.setCampaignCode(null);
          }
          break;
        }
 
      }
      

    }
    
  }
}
