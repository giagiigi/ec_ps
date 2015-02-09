package jp.co.sint.webshop.web.action.front.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
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
import jp.co.sint.webshop.service.catalog.SetCommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.cart.CartBean;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean;
import jp.co.sint.webshop.web.bean.front.catalog.DetailCartBean;
import jp.co.sint.webshop.web.bean.front.catalog.DetailRecommendBaseBean;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean.DisplayPartsCode;
import jp.co.sint.webshop.web.bean.front.catalog.DetailCartBean.RecommendSet;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

public class DetailRecommendAddcartAction extends DetailBaseAction {
  String commodityCode;

  String skuCode;

  /**
   * 
   * 
   * 推荐套餐加入购物车
   * 
   */
  @Override
  public WebActionResult callService() {
    String url[] = getRequestParameter().getPathArgs();
    String shopCode = url[0];
    String commodityCode = url[1];
    String skuCode = this.skuCode;
    String setSkuCode = skuCode;
    CommodityDetailBean reqBean = getBean();
    String giftCode = reqBean.getGiftCode();
    DetailCartBean cartDetailBean = (DetailCartBean) reqBean.getSubBeanMap().get("/catalog/" + DisplayPartsCode.CART.getName());
    skuCode = getSetSkuCode(cartDetailBean, skuCode);
    
    // 加入购物车
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
        if (!getCart().set(shopCode, skuCode, giftCode, cartQuantity, getLoginInfo().getCustomerCode())) {
          errorMessageList.add(WebMessage.get(OrderErrorMessage.ADD_CART));
        }
      } else if (!getCart().set(shopCode, skuCode, cartQuantity, getLoginInfo().getCustomerCode())) {

        errorMessageList.add(WebMessage.get(OrderErrorMessage.ADD_CART));
      }
      
      // 添加套餐明细
      boolean setFlg = setComposition(cartDetailBean, shopCode, skuCode, setSkuCode);
      
      nextBean.setErrorMessageList(errorMessageList);
      
      if (!setFlg) {
        getCart().remove(shopCode, setSkuCode, null);
        return FrontActionResult.RESULT_SUCCESS;
      }
      
      setOtherGift(shopCode, skuCode, commodityCode, reqBean.getQuantity());
      setRequestBean(nextBean);
      setNextUrl("/app/cart/cart/init");
    }
    
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    CommodityDetailBean reqBean = getBean();
    boolean returnFlg = true;
    String url[] = getRequestParameter().getPathArgs();
    Map<String, String> target = new LinkedHashMap<String, String>();
    
    target.put(Messages.getString("web.action.front.catalog.DetailAddcartAction.2"), reqBean.getShopCode());
    target.put(Messages.getString("web.action.front.catalog.DetailAddcartAction.3"), reqBean.getSkuCode());
    target.put(Messages.getString("web.action.front.catalog.DetailAddcartAction.4"), reqBean.getQuantity());
    
    if (!checkRequiredValue(target)) {
      throw new URLNotFoundException();

    }
    // URL验证
    if (url.length != 2) {
      throw new URLNotFoundException();
    }
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    String shopCode = url[0];
    String commodityCode = url[1];
    DetailCartBean cartDetailBean = (DetailCartBean) reqBean.getSubBeanMap().get("/catalog/" + DisplayPartsCode.CART.getName());
    if (cartDetailBean == null) {
      cartDetailBean = new DetailCartBean();
      RecommendSet recommendset = getSetComposition(commodityCode, shopCode, catalogService);
      if (recommendset == null) {
        return false;
      }
      cartDetailBean.getRecommendSets().add(recommendset);
      reqBean.getSubBeanMap().put("/catalog/" + DisplayPartsCode.CART.getName(), cartDetailBean);
    }
    cartDetailBean.createAttributes(getRequestParameter());
    CommodityHeader commodityHeader = catalogService.getCommodityHeader(shopCode, commodityCode);
    
    this.commodityCode = commodityCode;
    this.skuCode = commodityHeader.getRepresentSkuCode();
    
    int quantity = 1;
    CartItem cartItem = getCartItem(shopCode, getSetSkuCode(cartDetailBean, this.skuCode));
    if (cartItem != null) {
      quantity = cartItem.getQuantity() + quantity;
    }
    
    // 商品存在验证
    if (commodityHeader == null) {
      return false;
    }

    Cart cart = getCart();
    
    // 购物车赠品取得
    List<GiftItem> giftItems = new ArrayList<GiftItem>();
    for (CartItem item : cart.get()) {
      if (item.getCommodityInfo().getGiftList() != null && item.getCommodityInfo().getGiftList().size() > 0) {

        String mySkuCode = item.getCommodityInfo().getSkuCode();
        if (mySkuCode.contains(":")) {
          mySkuCode = mySkuCode.split(":")[0];
        }
        if (!mySkuCode.equals(this.skuCode)) {
          for (GiftItem giftItem : item.getCommodityInfo().getGiftList()) {
            for (int i = 0; i < item.getQuantity(); i++) {
              giftItems.add(giftItem);
            }
          }
        }
      }
    }
    
    // 购物车商品明细取得
    List<CompositionItem> comItems = new ArrayList<CompositionItem>();
    for (CartItem item : cart.get()) {
      if (item.getCommodityInfo().getCompositionList() != null && item.getCommodityInfo().getCompositionList().size() > 0) {
        String mySkuCode = item.getCommodityInfo().getSkuCode();
        if (mySkuCode.contains(":")) {
          mySkuCode = mySkuCode.split(":")[0];
        }
        if (!mySkuCode.equals(this.skuCode)) {
          for (CompositionItem comItem : item.getCommodityInfo().getCompositionList()) {
            for (int i = 0; i < item.getQuantity(); i++) {
              comItems.add(comItem);
            }
          }
        }
      }
    }

    boolean isReserve = CartUtil.isReserve(getCart(), shopCode, commodityHeader.getRepresentSkuCode());
    // 促销赠品验证
    Long campaignAvailability = catalogService.campaignAvailability(commodityCode, shopCode, giftItems);
    Long minStock = 0L;
    if (campaignAvailability != null) {
      minStock = campaignAvailability;
    }

    if (commodityHeader.getSetCommodityFlg() == null
        || !commodityHeader.getSetCommodityFlg().equals(SetCommodityFlg.OBJECTIN.longValue())) {
      return false;
    }
    
    // 套餐子商品验证
    Long min = null;
    RecommendSet recommendSet = null;

    for (RecommendSet rec : cartDetailBean.getRecommendSets()) {
      if (rec.getSetSkuCode().equals(commodityHeader.getRepresentSkuCode())) {
        recommendSet = rec;
        break;
      }
    }

    if (recommendSet == null) {
      return false;
    }
    
    if (recommendSet != null && recommendSet.getChildCommodity().size() > 0) {
      for (DetailRecommendBaseBean child : recommendSet.getChildCommodity()) {
        CommodityAvailability availability = catalogService.isAvailable(shopCode, child.getSkuCode(), 1, false);
        if (availability != CommodityAvailability.AVAILABLE) {
          min = 0L;
          break;
        }
        
        // 子商品有效库存验证
        Long num = catalogService.getAvailableStock(shopCode, child.getSkuCode());
        Long itemQuantitys = 0L;
        if (cart.get(shopCode, child.getSkuCode()) != null) {
          itemQuantitys = itemQuantitys + new Long(cart.get(shopCode, child.getSkuCode()).getQuantity());
        }
        for (CompositionItem item : comItems) {
          if (item.getSkuCode().equals(child.getSkuCode())) {
            itemQuantitys++;
          }
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
    
    if (min != null && minStock > min) {
      minStock = min;
    }
    
    CommodityAvailability commodityAvailability = catalogService.isAvailable(shopCode, commodityHeader.getRepresentSkuCode(), quantity, isReserve);
    if (minStock >= quantity
        && (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK) || commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE))) {
      commodityAvailability = CommodityAvailability.AVAILABLE;
    } else if (minStock < quantity && commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
      returnFlg = false;
      commodityAvailability = CommodityAvailability.STOCK_SHORTAGE;
    }
    
    if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
      throw new URLNotFoundException();
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
      returnFlg = false;
      addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD_WITH_NAME, Messages
          .getString("web.action.front.catalog.DetailAddcartAction.1")));
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
      returnFlg = false;
      addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, Messages
          .getString("web.action.front.catalog.DetailAddcartAction.1")));
    } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
      returnFlg = false;
      addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, Messages
          .getString("web.action.front.catalog.DetailAddcartAction.1"), String.valueOf(minStock)));
    } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
      returnFlg = false;
      addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_RESERVATION_OVER, Messages
          .getString("web.action.front.catalog.DetailAddcartAction.1"), NumUtil.toString(catalogService
          .getReservationAvailableStock(shopCode, commodityHeader.getRepresentSkuCode()))));
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
      returnFlg = false;
      addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, Messages
          .getString("web.action.front.catalog.DetailAddcartAction.1")));
    } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
      if (minStock == 0L) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, Messages
            .getString("web.action.front.catalog.DetailAddcartAction.1")));
        return false;
      } else if (quantity > minStock) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, Messages
            .getString("web.action.front.catalog.DetailAddcartAction.1"), String.valueOf(minStock)));

        return false;
      }
    }

    return returnFlg;
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

  /**
   *套餐子商品设置
   * 
   * @param DetailCartBean
   * @param 店铺编号
   * @param SKU编号
   */
  private boolean setComposition(DetailCartBean cartDetailBean, String shopCode, String skuCode, String setSkuCode) {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    TaxUtil u = DIContainer.get("TaxUtil");
    Long taxRate = u.getTaxRate();
    CartItem cartItem = getCart().get(shopCode, skuCode);
    BigDecimal weight = new BigDecimal(0);
    RecommendSet set = null;
    
    for (RecommendSet recset : cartDetailBean.getRecommendSets()) {
      if (recset.getSetSkuCode().equals(setSkuCode)) {
        set = recset;
        break;
      }
    }
    
    if (set == null) {
      return false;
    }
    
    List<SetCommodityComposition> setCommodityCompositions = catalogService.getSetCommodityCompositipon(commodityCode, shopCode);
    
    for (DetailRecommendBaseBean detailRec : set.getChildCommodity()) {
      CompositionItem item = new CompositionItem();
      CommodityInfo info = catalogService.getCommodityInfoBySkuCode(shopCode, detailRec.getCommodityCode(), detailRec.getSkuCode());
      
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
      
      for (SetCommodityComposition childset : setCommodityCompositions) {
        if (info.getHeader().getCommodityCode().equals(childset.getChildCommodityCode())) {
          item.setUnitPrice(childset.getRetailPrice());
        }
      }
      
      item.setCommodityTaxType(info.getHeader().getCommodityTaxType());
      item.setRepresentFlg(detailRec.getSkuCode().equals(info.getHeader().getRepresentSkuCode()));
      item.setRetailPrice(price.getRetailPrice());
      item.setRetailTax(price.retailTaxCharge().longValue());
      item.setCommodityTaxRate(taxRate);
      item.setWeight(info.getDetail().getWeight());
      weight = weight.add(info.getDetail().getWeight());
      cartItem.getCommodityInfo().getCompositionList().add(item);
    }
    
    CommodityInfo info = catalogService.getSkuInfo(shopCode, setSkuCode);
    cartItem.getCommodityInfo().setCompositionWeight(info.getDetail().getWeight());
    cartItem.getCommodityInfo().setWeight(weight);
    
    return true;
  }

  /**
   * 促销礼品设置
   * 
   * @param 店铺编号
   * @param SKU编号
   * @param 数量
   */
  private void setOtherGift(String shopCode, String skuCode, String setCommodityCode, String quantitys) {
    CampainFilter campainFilter = new CampainFilter();
    CartItem cartItem = getCart().get(shopCode, skuCode);
    int quantity = Integer.parseInt(quantitys);
    
    List<GiftItem> giftItems = campainFilter.getOtherGiftCodeBySku(setCommodityCode, shopCode);
    
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
    RecommendSet recommendSet = null;
    for (RecommendSet rec : cartDetailBean.getRecommendSets()) {
      if (rec.getSetSkuCode().equals(skuCode)) {
        recommendSet = rec;
        break;
      }
    }
    
    if (recommendSet != null && recommendSet.getChildCommodity().size() > 0) {
      for (DetailRecommendBaseBean child : recommendSet.getChildCommodity()) {
        setSkuCode.append(":");
        setSkuCode.append(child.getSkuCode());
      }
    }
    
    return setSkuCode.toString();
  }
  

  /**
   *根据商品信息创建套餐信息
   * 
   * @param 商品编号
   * @param 店铺编号
   * @param CatalogService
   * @return 推荐套餐
   */
  private RecommendSet getSetComposition(String commodityCode, String shopCode, CatalogService service) {
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    SetCommodityInfo setInfo = service.getSetComposition(commodityCode, shopCode);

    if (setInfo == null) {
      return null;
    }

    Long CampaignAvailability = service.campaignAvailability(commodityCode, shopCode, null);
    if (CampaignAvailability == null || CampaignAvailability == 0) {
      return null;
    }

    // 套餐商品价格获取
    Campaign campaign = service.getAppliedCampaignInfo(shopCode, commodityCode);
    Price price = new Price(setInfo.getSetCommodity().getCommodityHeader(), setInfo.getSetCommodity()
        .getCommodityDetail(), campaign, taxRate);
    RecommendSet recommendSet = new RecommendSet();
    recommendSet.setSetCommodityCode(setInfo.getSetCommodity().getCommodityHeader().getCommodityCode());
    recommendSet.setSetPrice(NumUtil.formatCurrency(price.getRetailPrice()));
    recommendSet.setTaxType(setInfo.getSetCommodity().getCommodityHeader().getCommodityTaxType());
    recommendSet.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();

    // 语言判断
    if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
      recommendSet.setSetCommodityName(setInfo.getSetCommodity().getCommodityHeader().getCommodityName());
    } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
      recommendSet.setSetCommodityName(setInfo.getSetCommodity().getCommodityHeader().getCommodityNameJp());
    } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
      recommendSet.setSetCommodityName(setInfo.getSetCommodity().getCommodityHeader().getCommodityNameEn());
    }

    BigDecimal totalPrice = new BigDecimal(0);
    // 子商品价格设定
    for (CommodityContainer childCommodity : setInfo.getChildCommodity()) {
      DetailRecommendBaseBean child = new DetailRecommendBaseBean();
      child.setCommodityCode(childCommodity.getCommodityHeader().getCommodityCode());
      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        child.setCommodityName(childCommodity.getCommodityHeader().getCommodityName());
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        child.setCommodityName(childCommodity.getCommodityHeader().getCommodityNameJp());
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        child.setCommodityName(childCommodity.getCommodityHeader().getCommodityNameEn());
      }
      child.setSkuCode(childCommodity.getCommodityHeader().getRepresentSkuCode());
      child.setUnitPrice(childCommodity.getCommodityDetail().getUnitPrice().toString());
      child.setShopCode(shopCode);
      child.setCommodityTaxType(childCommodity.getCommodityHeader().getCommodityTaxType().toString());
      child.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
      recommendSet.getChildCommodity().add(child);
      // 合计获取
      totalPrice = totalPrice.add(childCommodity.getCommodityDetail().getUnitPrice());
    }
    totalPrice = totalPrice.add(price.getRetailPrice().negate());

    recommendSet.setSetSkuCode(setInfo.getSetCommodity().getCommodityHeader().getRepresentSkuCode());
    recommendSet.setUnitPrice(NumUtil.formatCurrency(totalPrice.toString()));
    recommendSet.setDisplayCartButton(!setInfo.isSingleSku());

    return recommendSet;
  }
}
