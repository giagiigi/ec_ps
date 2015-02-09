package jp.co.sint.webshop.web.action.front.catalog;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.campain.CampainFilter;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.cart.CartUtil;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.CompositionItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.NumberLimitPolicy;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.cart.CartBean;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean;
import jp.co.sint.webshop.web.bean.front.catalog.DetailCartBean;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean.DisplayPartsCode;
import jp.co.sint.webshop.web.bean.front.catalog.DetailCartBean.DetailCartComposition;
import jp.co.sint.webshop.web.bean.front.catalog.DetailCartBean.DetailCartDetail;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2040510:商品詳細のアクションクラスです。

 * 
 * @author System Integrator Corp.
 */
public class DetailAddcartAction extends DetailBaseAction {
  
  private boolean isSet = false;


/**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommodityDetailBean reqBean = getBean();
    String shopCode = reqBean.getShopCode();
    String skuCode = reqBean.getSkuCode();
    String giftCode = reqBean.getGiftCode();

    // 2012/11/20 促销对应 ob add start
    DetailCartBean cartDetailBean = (DetailCartBean) reqBean.getSubBeanMap().get("/catalog/" + DisplayPartsCode.CART.getName());
    if (isSet) {
      //skuCode = getSetSkuCode(cartDetailBean, skuCode);
    }
    // 2012/11/20 促销对应 ob add end

    if (isNotSelectedSku(reqBean)) {
      return FrontActionResult.RESULT_SUCCESS;
    }

    if (existSku(reqBean)) {
      Long quantity = NumUtil.toLong(reqBean.getQuantity());

      CartBean nextBean = new CartBean();
      List<String> errorMessageList = new ArrayList<String>();

      int cartQuantity = quantity.intValue();
      CartItem cartItem = getCartItem(shopCode, skuCode);

      if (cartItem != null) {
        cartQuantity = cartItem.getQuantity() + quantity.intValue();
      }

      if (StringUtil.hasValue(giftCode)) {
        // 20111215 shen update start
              // 2012/12/04 促销对应 ob update start
        if (!getCart().set(shopCode, skuCode, giftCode, cartQuantity, getLoginInfo().getCustomerCode(), isSet)) {
        // 2012/12/04 促销对应 ob update end
        
        // 20111215 shen update end
          errorMessageList.add(WebMessage.get(OrderErrorMessage.ADD_CART));
        }
        // 20111214 shen update start
      } else if (!getCart().set(shopCode, skuCode, "",cartQuantity, getLoginInfo().getCustomerCode(), isSet)) {
        // 20111214 shen update end
        errorMessageList.add(WebMessage.get(OrderErrorMessage.ADD_CART));
      }
      
      // 2012/11/20 促销对应 ob add start
      if (isSet) {
        setComposition(cartDetailBean, shopCode, skuCode, reqBean.getCommodityCode());
      }
      // 2012/11/20 促销对应 ob add end
      
      nextBean.setErrorMessageList(errorMessageList);
      setRequestBean(nextBean);

      setNextUrl("/app/cart/cart/init");
    }
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。

   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    CommodityDetailBean reqBean = getBean();
    
    // 10.1.4 10193 追加 ここから
    Map<String, String> target = new LinkedHashMap<String, String>();
    target.put(Messages.getString("web.action.front.catalog.DetailAddcartAction.2"), reqBean.getShopCode());
    target.put(Messages.getString("web.action.front.catalog.DetailAddcartAction.3"), reqBean.getSkuCode());
    target.put(Messages.getString("web.action.front.catalog.DetailAddcartAction.4"), reqBean.getQuantity());
    if (!checkRequiredValue(target)) {
      // 10.1.6 10259 修正 ここから
      // return false;
      throw new URLNotFoundException();
      // 10.1.6 10259 修正 ここまで
    }
    
    Boolean valid = validateBean(reqBean);
    if(!valid){
      return false;
    }
    
    
    
    // 10.1.4 10193 追加 ここまで

    // // 10.1.5 10232 追加 ここから
    // if (!validateItems(reqBean, "quantity")) {
    // // 10.1.6 10259 修正 ここから
    // // return false;
    // throw new URLNotFoundException();
    // // 10.1.6 10259 修正 ここまで
    // }
    // // 10.1.5 10232 追加 ここまで

    String shopCode = reqBean.getShopCode();
    String skuCode = reqBean.getSkuCode();
    // // 10.1.15 10232 修正 ここから
    // // int quantity = Integer.parseInt(reqBean.getQuantity());
    // int quantity = NumUtil.toLong(reqBean.getQuantity()).intValue();
    // // 10.1.15 10232 修正 ここまで
    
    // 2012/12/04 促销对应 ob add start
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo info = catalogService.getSkuInfo(shopCode, skuCode);
    CommodityHeader commodityHeader = info.getHeader();
    String cartSkuCode = skuCode;
    DetailCartBean cartDetailBean = new DetailCartBean();
    if (commodityHeader.getSetCommodityFlg() != null
        && commodityHeader.getSetCommodityFlg().equals(SetCommodityFlg.OBJECTIN.longValue())) {
      isSet = true;
      cartDetailBean = (DetailCartBean) reqBean.getSubBeanMap().get("/catalog/" + DisplayPartsCode.CART.getName());
      if (cartDetailBean == null) {
        return false;
      }
      cartDetailBean.createAttributes(getRequestParameter());
      //cartSkuCode = getSetSkuCode(cartDetailBean, skuCode);
    }
    int quantityWithoutSet = 0;
    // 2012/12/04 促销对应 ob add end
    
    int quantity = Integer.parseInt(reqBean.getQuantity());
    
    // 2012/12/04 促销对应 ob update start
    //CartItem cartItem = getCartItem(shopCode, skuCode);
    CartItem cartItem;
    if (isSet) {
      cartItem = getCartItem(shopCode, cartSkuCode);
    } else {
      cartItem = getCartItem(shopCode, skuCode);
    }
    // 2012/12/04 促销对应 ob update end

   
    if (cartItem != null) {
      quantity = cartItem.getQuantity() + quantity;
    }


    boolean isReserve = CartUtil.isReserve(getCart(), shopCode, skuCode);
    
    // 2012/11/20 促销对应 ob add start
    quantityWithoutSet = quantity;
    Cart cart = getCart();
    // 赠品取得
    List<GiftItem> giftItems = new ArrayList<GiftItem>();
    for (CartItem item : cart.get()) {
      if (item.getCommodityInfo().getGiftList() != null && item.getCommodityInfo().getGiftList().size() > 0) {

        String mySkuCode = item.getCommodityInfo().getSkuCode();
        if (mySkuCode.contains(":")) {
          mySkuCode = mySkuCode.split(":")[0];
        }
        
        if (!mySkuCode.equals(reqBean.getSkuCode())) {
          for (GiftItem giftItem : item.getCommodityInfo().getGiftList()) {
            for (int i = 0; i < item.getQuantity(); i++) {
              giftItems.add(giftItem);
            }
          }
        }
      }
    }
    
    // 套餐明细取得
    List<CompositionItem> comItems = new ArrayList<CompositionItem>();
    for (CartItem item : cart.get()) {
      if (item.getCommodityInfo().getCompositionList() != null && item.getCommodityInfo().getCompositionList().size() > 0) {
        String mySkuCode = item.getCommodityInfo().getSkuCode();
        if (mySkuCode.contains(":")) {
          mySkuCode = mySkuCode.split(":")[0];
        }
        if (!mySkuCode.equals(reqBean.getSkuCode())) {
          for (CompositionItem comItem : item.getCommodityInfo().getCompositionList()) {
            for (int i = 0; i < item.getQuantity(); i++) {
              comItems.add(comItem);
            }
          }
        }
      }
    }
    Long campaignAvailability = catalogService.campaignAvailability(reqBean.getCommodityCode(), reqBean.getShopCode(),
        giftItems);

    Long minStock = 0L;
    boolean itemFlg = false;
    boolean noSelect = false;
    if (campaignAvailability != null) {
      minStock = campaignAvailability;
    }

    // 套餐子商品验证
    if (commodityHeader.getSetCommodityFlg() != null && commodityHeader.getSetCommodityFlg().equals(SetCommodityFlg.OBJECTIN.longValue())) {
      isSet = true;
      Long min = null;
      NumberLimitPolicy numberPolicy = DIContainer.getNumberLimitPolicy();
      Long availableStock = numberPolicy.getMaxTotalAmountNum();
      reqBean.addSubBean("/catalog/" + DisplayPartsCode.CART.getName(), cartDetailBean);
      if (cartDetailBean.getCompositionList() != null && cartDetailBean.getCompositionList().size() > 0) {
        for (DetailCartComposition child : cartDetailBean.getCompositionList()) {
          Long itemQuantitys = 0L;
          if (StringUtil.isNullOrEmpty(child.getSelectedSkuCode())) {
            child.setSelectedSkuCode(child.getRepresentSkuCode());
          }
          if (StringUtil.isNullOrEmpty(child.getSelectedSkuCode())) {
            noSelect = true;
          }
          CommodityAvailability availability = catalogService.isAvailable(shopCode, child.getSelectedSkuCode(), 1,
              false);
          if (availability != CommodityAvailability.AVAILABLE) {
            min = 0L;
            break;
          }
          // 子商品有效库存验证
          Long num = catalogService.getAvailableStock(shopCode, child.getSelectedSkuCode());
          if (cart.get(shopCode, child.getSelectedSkuCode()) != null) {
            itemFlg = true;
            itemQuantitys = itemQuantitys + new Long(cart.get(shopCode, child.getSelectedSkuCode()).getQuantity());
          }
          for (CompositionItem item : comItems) {
            if (item.getSkuCode().equals(child.getSelectedSkuCode())) {
              itemFlg = true;
              itemQuantitys++;
            }
          }
          if (num < 0L) {
            num = availableStock;
          }
          if (num >= itemQuantitys) {
            num = num - itemQuantitys;
          }
          if (min == null) {
            min = num;
          }
          if (num < min && num >= 0) {
            min = num;
          }
        }
      }
      if (minStock > min) {
        minStock = min;
      }
    }
    for (CompositionItem item : comItems) {
      if (item.getSkuCode().equals(skuCode)) {
        quantity++;
      }
    }
    // 2012/11/20 促销对应 ob add end
    
    CommodityAvailability commodityAvailability = catalogService.isAvailable(shopCode, skuCode, quantity, isReserve);
    
    //组合商品库存判断时候  要将购物车里面的所有商品都进行循环判断  add  by twh  start
    if (cart.get().size() > 0) {
      Long allCount = 0L;
      Long allStock = 0L;
      if (StringUtil.hasValue(commodityHeader.getOriginalCommodityCode())
          && commodityHeader.getCombinationAmount() != null) {
        allStock = catalogService.getAvailableStock(commodityHeader.getShopCode(),commodityHeader.getOriginalCommodityCode());
        allCount = quantity  * commodityHeader.getCombinationAmount();
      } else {
        allStock = catalogService.getAvailableStock(commodityHeader.getShopCode(), skuCode);
        allCount = quantity  * 1L;
      }

      for (CartItem item : cart.get()) {
        if (!item.getCommodityCode().equals(skuCode)) {
          boolean rel = false;
          if (StringUtil.hasValue(commodityHeader.getOriginalCommodityCode())) {
            rel = (commodityHeader.getOriginalCommodityCode().equals(item.getCommodityCode()) || (StringUtil.hasValue(item
                .getOriginalCommodityCode()) && commodityHeader.getOriginalCommodityCode().equals(item.getOriginalCommodityCode())));
          } else {
            rel = (StringUtil.hasValue(item.getOriginalCommodityCode()) && commodityHeader.getCommodityCode().equals(
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
        commodityAvailability =  CommodityAvailability.STOCK_SHORTAGE;
      }
    }
    //组合商品库存判断时候  要将购物车里面的所有商品都进行循环判断  add  by twh end
    
    // 2012/11/20 促销对应 ob add start  
    if (isSet && minStock >= quantity
        && (CommodityAvailability.OUT_OF_STOCK.equals(commodityAvailability) || CommodityAvailability.STOCK_SHORTAGE.equals(commodityAvailability))) {
      commodityAvailability = CommodityAvailability.AVAILABLE;
    } else if (isSet && minStock < quantity && CommodityAvailability.OUT_OF_STOCK.equals(commodityAvailability)) {
      commodityAvailability = CommodityAvailability.STOCK_SHORTAGE;
    }
    if (campaignAvailability == null || campaignAvailability == 0L || (minStock < 1 && itemFlg) || noSelect) {
      commodityAvailability = CommodityAvailability.OUT_OF_STOCK;
    }
    // 2012/11/20 促销对应 ob add end
    
    if (CommodityAvailability.NOT_EXIST_SKU.equals(commodityAvailability)) {
      // 10.1.6 10259 修正 ここから
      // addErrorMessage(WebMessage.get(ActionErrorMessage.NO_DATA_ERROR,
      // "商品"));
      throw new URLNotFoundException();
      // 10.1.6 10259 修正 ここまで
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD_WITH_NAME, Messages
          .getString("web.action.front.catalog.DetailAddcartAction.1")));
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, Messages
          .getString("web.action.front.catalog.DetailAddcartAction.1")));
    } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
      // 2012/11/20 促销对应 ob add start
      if (!isSet) {
        
        //如果是组合商品 则提示的个数为可销售的组合商品数量
        String origiSkuCode = "";
        Long shortStock  = 0L;
        if(StringUtil.hasValue(commodityHeader.getOriginalCommodityCode())){
          origiSkuCode = commodityHeader.getOriginalCommodityCode();
          shortStock = catalogService.getAvailableStock(shopCode, origiSkuCode);
          if (shortStock < minStock) {
            minStock = shortStock/commodityHeader.getCombinationAmount();
          }
        } else {
          origiSkuCode = skuCode;
          shortStock = catalogService.getAvailableStock(shopCode, origiSkuCode);
          if (shortStock < minStock) {
            minStock = shortStock;
          }
        }

      }
      
      addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, 
          getDisplayCommodityName(info), String.valueOf(minStock)));
      // 2012/11/20 促销对应 ob add end
      
      // 2012/11/20 促销对应 ob delete start
      /*addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, Messages
          .getString("web.action.front.catalog.DetailAddcartAction.1"), String.valueOf(catalogService
          .getAvailableStock(shopCode, skuCode))));*/
      // 2012/11/20 促销对应 ob delete end
      
    } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_RESERVATION_OVER, Messages
          .getString("web.action.front.catalog.DetailAddcartAction.1"), NumUtil.toString(catalogService
          .getReservationAvailableStock(shopCode, skuCode))));
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, Messages
          .getString("web.action.front.catalog.DetailAddcartAction.1")));
    } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
    
      // 2012/11/17 促销对应 ob add start
      if (quantity > minStock) {
      if (!isSet) {
         if (quantityWithoutSet>minStock.intValue()) {
           addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, 
                  getDisplayCommodityName(info), String.valueOf(minStock)));
                if (cartDetailBean.getRecommendSets() != null && cartDetailBean.getRecommendSets().size() > 0) {
                  cartDetailBean.setCompositionList(new ArrayList<DetailCartComposition>());
                }
                return false;
         }
      } else {
         addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, 
                  getDisplayCommodityName(info), String.valueOf(minStock)));
                if (cartDetailBean.getRecommendSets() != null && cartDetailBean.getRecommendSets().size() > 0) {
                  cartDetailBean.setCompositionList(new ArrayList<DetailCartComposition>());
                }
                return false;
      }
       
      }
      
      return true;
    }
    if (cartDetailBean.getRecommendSets() != null && cartDetailBean.getRecommendSets().size() > 0) {
      cartDetailBean.setCompositionList(new ArrayList<DetailCartComposition>());
    }
    // 2012/11/17 促销对应 ob add end
    
    return false;
  }

  /**
   * カート内の商品情報を取得
   * 
   * @param shopCode
   * @param skuCode
   * @return CartItem
   */
  private CartItem getCartItem(String shopCode, String skuCode) {
    CartItem cartItem = getCart().get(shopCode, skuCode);
    if (cartItem == null) {
      cartItem = getCart().getReserve(shopCode, skuCode);
    }

    return cartItem;
  }
  
  // 2012/11/17 促销对应 ob add start
  /**
   *套餐子商品设置
   * 
   * @param DetailCartBean
   * @param 店铺编号
   * @param SKU编号
   */
  private void setComposition(DetailCartBean cartDetailBean, String shopCode, String skuCode, String commodityCode) {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    TaxUtil u = DIContainer.get("TaxUtil");
    Long taxRate = u.getTaxRate();
    CartItem cartItem = getCart().get(shopCode, skuCode);
    BigDecimal weight = new BigDecimal(0);
    List<SetCommodityComposition> setCommodityCompositions = catalogService.getSetCommodityCompositipon(commodityCode,
        shopCode);
    for (DetailCartComposition detailCom : cartDetailBean.getCompositionList()) {
      for (DetailCartDetail detail : detailCom.getCompositionSkuList()) {
        if (detail.getSkuCode().equals(detailCom.getSelectedSkuCode())) {
          CompositionItem item = new CompositionItem();
          CommodityInfo info = catalogService.getCommodityInfoBySkuCode(shopCode, detailCom.getCommodityCode(), detail
              .getSkuCode());
          if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
            item.setCommodityName(info.getHeader().getCommodityName());
            item.setStandardDetail1Name(info.getDetail().getStandardDetail1Name());
            item.setStandardDetail2Name(info.getDetail().getStandardDetail2Name());
          } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
            item.setCommodityName(info.getHeader().getCommodityNameJp());
            item.setStandardDetail1Name(info.getDetail().getStandardDetail1NameJp());
            item.setStandardDetail2Name(info.getDetail().getStandardDetail2NameJp());
          } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
            item.setCommodityName(info.getHeader().getCommodityNameEn());
            item.setStandardDetail1Name(info.getDetail().getStandardDetail1NameEn());
            item.setStandardDetail2Name(info.getDetail().getStandardDetail2NameEn());
          }
          item.setShopCode(shopCode);
          item.setCommodityCode(info.getHeader().getCommodityCode());
          item.setSkuCode(info.getDetail().getSkuCode());
          Price price = new Price(info.getHeader(), info.getDetail(), null, taxRate);
          for (SetCommodityComposition set : setCommodityCompositions) {
            if (info.getHeader().getCommodityCode().equals(set.getChildCommodityCode())) {
              item.setRetailPrice(set.getRetailPrice());
            }
          }
          item.setCommodityTaxType(info.getHeader().getCommodityTaxType());
          item.setRepresentFlg(detailCom.getRepresentSkuCode().equals(info.getDetail().getSkuCode()));
          item.setUnitPrice(info.getDetail().getUnitPrice());
          item.setRetailTax(price.retailTaxCharge().longValue());
          item.setCommodityTaxRate(taxRate);
          item.setWeight(info.getDetail().getWeight());
          item.setCommodityTax(price.getUnitTaxCharge());
          weight = weight.add(info.getDetail().getWeight());
          cartItem.getCommodityInfo().getCompositionList().add(item);
        }
      }
    }
    CommodityInfo info = catalogService.getCommodityInfo(shopCode, commodityCode);
    cartItem.getCommodityInfo().setCompositionWeight(info.getDetail().getWeight());
    cartItem.getCommodityInfo().setWeight(weight);
  }

  /**
   *套餐skucode取得
   * 
   * @param DetailCartBean
   * @param SKU编号
   * @return SETSKU编号
   */
  private String getSetSkuCode(DetailCartBean cartDetailBean, String skuCode) {
    StringBuffer setSkuCode = new StringBuffer();
    setSkuCode.append(skuCode);
    for (DetailCartComposition detailCom : cartDetailBean.getCompositionList()) {
      setSkuCode.append(":");
      setSkuCode.append(detailCom.getSelectedSkuCode());
    }
    return setSkuCode.toString();
  }
  
  /**
   * 促销礼品设置
   * 
   * @param 店铺编号
   * @param SKU编号
   * @param 数量
   */
  private void setOtherGift(String shopCode, String skuCode, String setSkuCode, String quantitys) {
    CampainFilter campainFilter = new CampainFilter();
    CartItem cartItem = getCart().get(shopCode, setSkuCode);
    int quantity = Integer.parseInt(quantitys);
    List<GiftItem> giftItems = campainFilter.getOtherGiftCodeBySku(cartItem.getCommodityCode(), shopCode);
    if (cartItem != null) {
      quantity = cartItem.getQuantity();
    }
    
    if (giftItems != null) {
      for (int i = 0; i < giftItems.size(); i++) {
        giftItems.get(i).setQuantity(new Integer(quantity).intValue());

      }
    }
    
    cartItem.getCommodityInfo().setGiftList(giftItems);
  }
  // 2012/11/17 促销对应 ob add end
  
  // 获得商品名称
  private String getDisplayCommodityName(CommodityInfo info){
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    String display = "";
    if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
      display += info.getHeader().getCommodityName();
      if (StringUtil.hasValue(info.getDetail().getStandardDetail1Name()) && StringUtil.hasValue(info.getDetail().getStandardDetail2Name())) {
        display += "("
            + MessageFormat.format(Messages.getString("web.action.front.order.OrderAction.0"), info.getDetail().getStandardDetail1Name(), info.getDetail().getStandardDetail2Name()) + ")";
      } else if (StringUtil.hasValue(info.getDetail().getStandardDetail1Name())) {
        display += "(" + info.getDetail().getStandardDetail1Name() + ")";
      } else if (StringUtil.hasValue(info.getDetail().getStandardDetail2Name())) {
        display += "(" + info.getDetail().getStandardDetail2Name() + ")";
      }
    } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
      display += info.getHeader().getCommodityNameJp();
      if (StringUtil.hasValue(info.getDetail().getStandardDetail1NameJp()) && StringUtil.hasValue(info.getDetail().getStandardDetail2NameJp())) {
        display += "("
            + MessageFormat.format(Messages.getString("web.action.front.order.OrderAction.0"), info.getDetail().getStandardDetail1NameJp(), info.getDetail().getStandardDetail2NameJp()) + ")";
      } else if (StringUtil.hasValue(info.getDetail().getStandardDetail1NameJp())) {
        display += "(" + info.getDetail().getStandardDetail1NameJp() + ")";
      } else if (StringUtil.hasValue(info.getDetail().getStandardDetail2NameJp())) {
        display += "(" + info.getDetail().getStandardDetail2NameJp() + ")";
      }
    } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
      display += info.getHeader().getCommodityNameEn();
      if (StringUtil.hasValue(info.getDetail().getStandardDetail1NameEn()) && StringUtil.hasValue(info.getDetail().getStandardDetail2NameEn())) {
        display += "("
            + MessageFormat.format(Messages.getString("web.action.front.order.OrderAction.0"), info.getDetail().getStandardDetail1NameEn(), info.getDetail().getStandardDetail2NameEn()) + ")";
      } else if (StringUtil.hasValue(info.getDetail().getStandardDetail1NameEn())) {
        display += "(" + info.getDetail().getStandardDetail1NameEn() + ")";
      } else if (StringUtil.hasValue(info.getDetail().getStandardDetail2NameEn())) {
        display += "(" + info.getDetail().getStandardDetail2NameEn() + ")";
      }
    }
    return display;
  }
}
