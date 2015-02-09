package jp.co.sint.webshop.web.action.front;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.campain.CampainFilter;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.CompositionItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.CommodityCompositionContainer;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.NumberLimitPolicy;
import jp.co.sint.webshop.web.action.WebMainAction;
import jp.co.sint.webshop.web.bean.UIMainBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * フロント用アクションの基底クラスです。
 * 
 * @author System Integrator Corp.
 * @param <T>
 *          UIMainBean
 */
public abstract class WebFrontAction<T extends UIMainBean> extends WebMainAction {

  /**
   * ログイン情報を取得します。
   */
  @Override
  public FrontLoginInfo getLoginInfo() {
    LoginInfo loginInfo = getSessionContainer().getLoginInfo();
    FrontLoginInfo frontLoginInfo = null;

    if (loginInfo == null) {
      frontLoginInfo = WebLoginManager.createFrontNotLoginInfo();
    } else {
      if (loginInfo instanceof FrontLoginInfo) {
        frontLoginInfo = (FrontLoginInfo) loginInfo;
      } else {
        frontLoginInfo = WebLoginManager.createFrontNotLoginInfo();
      }
    }
    return frontLoginInfo;
  }

  /**
   * 呼び出し元JSPのBeanを取得します。
   * 
   * @return 呼び出し元JSPのBean
   */
  @SuppressWarnings("unchecked")
  public T getBean() {
    T bean = (T) super.getBean();
    return bean;
  }

  /**
   * 認証処理
   */
  @Override
  public boolean authorize() {
    return true;
  }
  
  // 2012/12/19 促销对应 ob add start
  
  public List<CommodityCompositionContainer> getCompositionContainerList(String shopCode, String commodityCode) {
    CatalogService catalogSvc = ServiceLocator.getCatalogService(getLoginInfo());
    
    // 取得子商品
    List<CommodityCompositionContainer> commodityCompositionContainerList = catalogSvc
        .getCommodityCompositionContainerList(shopCode, commodityCode);

    return commodityCompositionContainerList;
  }
  
  /**
   * 判断是否显示查看套餐按钮
   */
  public boolean displaySetButton(String shopCode, String commodityCode, Long setCommodityFlg) {
    boolean display = false;
    CatalogService catalogSvc = ServiceLocator.getCatalogService(getLoginInfo());
    
    List<CommodityCompositionContainer> commodityCompositionContainerList = getCompositionContainerList(shopCode, commodityCode);
    CommodityHeader ch = catalogSvc.getCommodityHeader(shopCode, commodityCode);
    if (SetCommodityFlg.OBJECTIN.longValue().equals(setCommodityFlg) && StringUtil.isNullOrEmpty(ch.getOriginalCommodityCode())) {

      NumberLimitPolicy numberPolicy = DIContainer.getNumberLimitPolicy();
      if (commodityCompositionContainerList == null || commodityCompositionContainerList.size() < numberPolicy.getMinSetNum().intValue()) {
        return false;
      }

      for (CommodityCompositionContainer child : commodityCompositionContainerList) {

        if (!child.isSalableComposition()) {
          return false;
        }
        
        int skuNumber = 0;
        
        for (CommodityDetail detail : child.getCommodityDetailList()) {
          CommodityAvailability availability = catalogSvc.isAvailable(shopCode, detail.getSkuCode(), 1, false);
          if (CommodityAvailability.AVAILABLE.equals(availability)) {
            skuNumber = skuNumber + 1;
          }
          
          if (skuNumber > 1) {
            return true;
          }
        }
        
      }
    }
    
    
    return display;
  }
  
  /**
   * 检证商品是否可购买
   * @param shopCode 店铺编号
   * @param commodityCode 商品编号
   * @param skuCode Sku编号
   * @param commodityType 商品区分
   * @param setCommodityFlg 套餐Flag
   * @param diplayCommodityName 显示错误时商品名的显示内容
   * @param isAddCart 是否显示错误信息
   * @return
   */
  public boolean checkAvailable(String shopCode, String commodityCode, String skuCode, Long commodityType,
      Long setCommodityFlg, Long quantity, String diplayCommodityName, boolean isAddCart) {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    boolean valid = true;
    CatalogService catalogSvc = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityAvailability commodityAvailability = null;
    if (quantity == null) {
      quantity = 0L;
    }

    // 套餐子商品
    List<CommodityCompositionContainer> commodityCompositionContainerList = new ArrayList<CommodityCompositionContainer>();
    List<GiftItem> giftItems = new ArrayList<GiftItem>();
    // 套餐商品
    List<String> compositionSkuCodeList = new ArrayList<String>();
    // 赠品
    List<String> giftSkuCodeList = new ArrayList<String>();
    String skuCodeTemp = skuCode;
    // 最小购买数check
    if (CommodityType.GENERALGOODS.longValue().equals(commodityType)) {
      
      giftItems = getOtherGift(shopCode, commodityCode, 1);
      
      if (StringUtil.hasValue(skuCode) && skuCode.contains(":")) {
        skuCode = skuCode.split(":")[0];
      }
      //组合品库存可用对应
      CommodityHeaderDao chDao = DIContainer.getDao(CommodityHeaderDao.class);
      CommodityHeader ch = chDao.load(shopCode, skuCode);
      if (SetCommodityFlg.OBJECTIN.longValue().equals(setCommodityFlg) && StringUtil.isNullOrEmpty(ch.getOriginalCommodityCode())) {
        

        // 取得子商品
        commodityCompositionContainerList = catalogSvc.getCommodityCompositionContainerList(shopCode, commodityCode);

        for (CommodityCompositionContainer child : commodityCompositionContainerList) {
          String compositionSkuCode = getStockOfAllSku(shopCode, child.getCommodityDetailList(), catalogSvc);
          if (StringUtil.hasValue(compositionSkuCode)) {
            compositionSkuCodeList.add(compositionSkuCode);
          }

        }

        if (giftItems != null && giftItems.size() > 0) {
          for (GiftItem gift : giftItems) {
            if (StringUtil.hasValue(gift.getGiftSkuCode())) {
              giftSkuCodeList.add(gift.getGiftSkuCode());
            }
          }
        }

        Long availableStock = catalogSvc.getAvailableStock(shopCode, skuCode, true, compositionSkuCodeList, giftSkuCodeList);
        if (availableStock < quantity) {
          if (isAddCart) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, diplayCommodityName, availableStock.toString()));
          }

          valid &= false;
        }
      } else {
        // 通常商品

        if (giftItems != null && giftItems.size() > 0) {
          for (GiftItem gift : giftItems) {
            if (StringUtil.hasValue(gift.getGiftSkuCode())) {
              giftSkuCodeList.add(gift.getGiftSkuCode());
            }
          }
        }
        //组合商品库存CHECK
        Long availableStock = 0L;
        if(StringUtil.hasValue(ch.getOriginalCommodityCode())){
          availableStock = catalogService.getAvailableStock(shopCode, ch.getOriginalCommodityCode(), false, null, giftSkuCodeList);
          availableStock = availableStock/ch.getCombinationAmount();
        }else{
          availableStock = catalogService.getAvailableStock(shopCode, skuCode, false, null, giftSkuCodeList);
        }
        //Long availableStock = catalogSvc.getAvailableStock(shopCode, skuCode, false, null, giftSkuCodeList);
        if (availableStock < quantity) {
          if (isAddCart) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, diplayCommodityName, availableStock.toString()));
          }

          valid &= false;
        }
      }
    } else {
      valid &= false;
    }

    // 各套餐的可购买性check
    if (valid && SetCommodityFlg.OBJECTIN.longValue().equals(setCommodityFlg)) {
      commodityAvailability = catalogSvc.isAvailable(shopCode, skuCode, quantity.intValue(), false, true);

      if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
        if (isAddCart) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, diplayCommodityName));
        }

        valid &= false;
      } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
        if (isAddCart) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD_WITH_NAME, diplayCommodityName));
        }

        valid &= false;
      } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
        valid &= true;
      }
    }

 
    if (isAddCart) {
      
      // 各通常商品可购买性check
      if (valid) {
        if (SetCommodityFlg.OBJECTIN.longValue().equals(setCommodityFlg) && compositionSkuCodeList != null && compositionSkuCodeList.size() > 0) {
          for (CommodityCompositionContainer child : commodityCompositionContainerList) {
            for (CommodityDetail detail : child.getCommodityDetailList()) {
              CommodityAvailability availability = catalogSvc.isAvailable(shopCode, detail.getSkuCode(), 1, false);
              if (CommodityAvailability.AVAILABLE.equals(availability)) {
                int commodityAmount = getCommodityAmountFromCart(detail.getSkuCode());
                valid &= checkAvailableSub(catalogSvc, shopCode, detail.getSkuCode(), skuCodeTemp, diplayCommodityName, commodityAmount);
              }
            }
          }
        } else {
          int commodityAmount = getCommodityAmountFromCart(skuCode);
          valid &= checkAvailableSub(catalogSvc, shopCode, skuCode, skuCodeTemp, diplayCommodityName, commodityAmount);
        }
      }
      
      // 各赠品可购买性check
      if (valid && giftItems != null && giftItems.size() > 0) {
        
        for (GiftItem gift : giftItems) {
          int giftAmount = getGiftAmountFromCart(gift.getGiftSkuCode());
          
          commodityAvailability = catalogSvc.isAvailableGift(shopCode, gift.getGiftSkuCode(), giftAmount + 1, false);
          int amount = 0;
          if (getCart().get(shopCode, skuCodeTemp) != null) {
            amount = getCart().get(shopCode, skuCodeTemp).getQuantity();
          }
          if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, diplayCommodityName));
            valid &= false;
            break;
          } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, diplayCommodityName, String.valueOf(amount)));
            valid &= false;
            break;
          } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
            valid &= true;
          }
        }
      }
    }
    
    return valid;

  }
  
  private boolean checkAvailableSub(CatalogService catalogSvc, String shopCode, String skuCode, String skuCodeTemp, String commodityName, int commodityAmount) {
    CommodityAvailability commodityAvailability = catalogSvc.isAvailable(shopCode, skuCode, commodityAmount + 1 , false);
    boolean valid = true;
   
    if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, commodityName));
      valid &= false;
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD_WITH_NAME, commodityName));
      valid &= false;
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, commodityName));
      valid &= false;
    } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE_ERROR, commodityName, String.valueOf(catalogSvc
          .getAvailableStock(shopCode, skuCode))));
      valid &= false;
    } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, commodityName, String.valueOf(catalogSvc
          .getAvailableStock(shopCode, skuCode))));
      valid &= false;
    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, commodityName));
      valid &= false;
    } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
      valid &= true;
    }
    
    return valid;
  }
  /**
   * 统计购物车中指定商品的数量
   * @return 购物车中指定商品的数量信息
   */
  private int getCommodityAmountFromCart(String skuCode) {
    int quantityOfCart = 0; // 购物车中指定商品的数量信息

    for (CartItem item : getCart().get()) {
      if (SetCommodityFlg.OBJECTIN.longValue().equals(item.getCommodityInfo().getSetCommodityFlg())) {
        for (CompositionItem composition : item.getCommodityInfo().getCompositionList()) {
          if (skuCode.equals(composition.getSkuCode())) {
            quantityOfCart = item.getQuantity() + quantityOfCart;
          }
        }
      } else {
        if (skuCode.equals(item.getSkuCode()) && CommodityType.GENERALGOODS.longValue().equals(item.getCommodityType())) {
          quantityOfCart = item.getQuantity() + quantityOfCart;
        }
      }
    }

    return quantityOfCart;
  }
  
  /**
   * 统计购物车指定赠品的数量
   * @return 购物车指定赠品的数量信息
   */
  private int getGiftAmountFromCart(String skuCode) {
    int cartGiftAmount = 0; // 购物车指定各赠品的数量信息

    // 统计购物车中指定赠品的数量
    for (CartItem item : getCart().get()) {
      if (CommodityType.GIFT.longValue().equals((item.getCommodityInfo().getCommodityType())) && StringUtil.hasValue(item.getSkuCode())) {
        
        if (skuCode.equals(item.getSkuCode().split("~")[1])) {
          cartGiftAmount = item.getQuantity() + cartGiftAmount;
        }
        
      } else {
        if (item.getCommodityInfo().getGiftList() != null && item.getCommodityInfo().getGiftList().size() > 0) {
          for (GiftItem giftItem : item.getCommodityInfo().getGiftList()) {
            if (skuCode.equals(giftItem.getGiftSkuCode())) {
              cartGiftAmount = item.getQuantity() + cartGiftAmount;
            }
          }
        }
      }
    }
    
    return cartGiftAmount;
  }
  
  public String getDisplayName(String commodityName, String detail1Name, String detail2Name) {
    String display = commodityName;

    if (StringUtil.hasValue(detail1Name) && StringUtil.hasValue(detail2Name)) {
      display += "("
          + MessageFormat.format(Messages.getString("web.action.front.order.OrderAction.0"), detail1Name, detail2Name) + ")";
    } else if (StringUtil.hasValue(detail1Name)) {
      display += "(" + detail1Name + ")";
    } else if (StringUtil.hasValue(detail2Name)) {
      display += "(" + detail2Name + ")";
    }
    return display;
  }

  /**
   * 取得套餐明細商品中庫存量最多的商品的Sku編號
   */
  public String getStockOfAllSku(String shopCode, List<CommodityDetail> commodityDetailList, CatalogService catalogSvc) {
    Long availableStock = 0L;
    String skuCode= ""; 

    for (CommodityDetail detail : commodityDetailList) {
      CommodityAvailability availability = catalogSvc.isAvailable(shopCode, detail.getSkuCode(), 1, false);
      if (StringUtil.hasValue(detail.getSkuCode()) && CommodityAvailability.AVAILABLE.equals(availability)) {
        Long stockTemp = catalogSvc.getAvailableStock(shopCode, detail.getSkuCode());
        if (stockTemp == null ) {
          
          continue;
          
        } else if (stockTemp == -1L){
          
          skuCode = detail.getSkuCode();
          break;
 
        } else if (stockTemp >= availableStock) {
          
          skuCode = detail.getSkuCode();
          availableStock = stockTemp;
          
        }
      }
    }
 

    return skuCode;
  }
  
  /**
   *套餐子商品设置
   * 
   * @param DetailCartBean
   * @param 店铺编号
   * @param SKU编号
   */
  public void setComposition(String shopCode, String skuCode, String commodityCode) {

    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    TaxUtil u = DIContainer.get("TaxUtil");
    Long taxRate = u.getTaxRate();
    CartItem cartItem = getCart().get(shopCode, skuCode);
    BigDecimal weight = new BigDecimal(0);
    List<SetCommodityComposition> setCommodityCompositions = catalogService.getSetCommodityCompositipon(commodityCode,
        shopCode);
    List<CommodityCompositionContainer> compositionContainerList = getCompositionContainerList(shopCode, commodityCode);

    for (CommodityCompositionContainer detail : compositionContainerList) {
      CompositionItem item = new CompositionItem();
      String compositionSkuCode = getStockOfAllSku(shopCode, detail.getCommodityDetailList(), catalogService);
      CommodityInfo info = catalogService.getCommodityInfoBySkuCode(shopCode, detail.getCommodityCode(),
          compositionSkuCode);
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
      item.setRepresentFlg(info.getHeader().getRepresentSkuCode().equals(info.getDetail().getSkuCode()));
      item.setUnitPrice(info.getDetail().getUnitPrice());
      item.setRetailTax(price.retailTaxCharge().longValue());
      item.setCommodityTaxRate(taxRate);
      item.setWeight(info.getDetail().getWeight());
      item.setCommodityTax(price.getUnitTaxCharge());
      weight = weight.add(info.getDetail().getWeight());
      cartItem.getCommodityInfo().getCompositionList().add(item);
    }

    CommodityInfo info = catalogService.getCommodityInfo(shopCode, commodityCode);
    cartItem.getCommodityInfo().setCompositionWeight(info.getDetail().getWeight());
    cartItem.getCommodityInfo().setWeight(weight);
  }

  public void setCampaignGiftInfo(String shopCode, String commodityCode, String skuCode, String quantitys) {

    CartItem cartItem = getCart().get(shopCode, skuCode);
    int quantity = Integer.parseInt(quantitys);
    
    
    if (cartItem != null) {
      quantity = cartItem.getQuantity();
    }
    
    List<GiftItem> giftItems = getOtherGift(shopCode, commodityCode, quantity);
    
    cartItem.getCommodityInfo().setGiftList(giftItems);
  }
  /**
   * 取得赠品促销活动的赠品一览
   * 
   * @param 店铺编号
   * @param SKU编号
   */
  public List<GiftItem> getOtherGift(String shopCode, String commodityCode, int quantitys) {
    CampainFilter campainFilter = new CampainFilter();
    List<GiftItem> giftItems = campainFilter.getOtherGiftCodeBySku(commodityCode, shopCode);
 
    if (giftItems != null) {
      for (int i = 0; i < giftItems.size(); i++) {
        giftItems.get(i).setQuantity(quantitys);

      }
    }
    
    return giftItems;
  }
  // 2012/12/19 促销对应 ob add end
  

}
