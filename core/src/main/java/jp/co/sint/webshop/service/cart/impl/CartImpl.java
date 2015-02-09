package jp.co.sint.webshop.service.cart.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.dao.CampaignMainDao;
import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.data.dto.CartHistory;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OptionalCommodity;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.CommodityCompositionContainer;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SetCommodityUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

public class CartImpl implements Cart {

  private List<Sku> history = new ArrayList<Sku>();

  public List<Sku> getHistory() {
    return this.history;
  }

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private List<CartItem> cartItemList = new ArrayList<CartItem>();

  public boolean add(CartItem cartItem) {
    String shopCode = cartItem.getShopCode();
    String skuCode = cartItem.getSkuCode();
    int quantity = cartItem.getQuantity();

    CatalogService catalogSvc = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    //2012/11/23 促销对应 新建订单_商品选择  ob upd start
    CommodityAvailability commodityAvailability;
    //赠品购买时
    if (CommodityType.GIFT.longValue().equals(cartItem.getCommodityType())) {
      if (skuCode != null && skuCode.contains("~")) {
        commodityAvailability = catalogSvc.isAvailableGift(shopCode, skuCode.split("~")[1], quantity);
      } else {
        commodityAvailability = CommodityAvailability.NOT_EXIST_SKU;
      }
      
    } else {
    	//既存
    	commodityAvailability = catalogSvc.isAvailable(shopCode, skuCode, quantity, cartItem.isReserve());
    	if (skuCode != null && skuCode.contains(SetCommodityUtil.SET_COMMODITY_DELIMITER)) {
    		commodityAvailability = catalogSvc.isAvailable(shopCode,
    				skuCode.split(SetCommodityUtil.SET_COMMODITY_DELIMITER)[0], quantity, cartItem.isReserve());
    	}
    }
    //2012/11/23 促销对应 新建订单_商品选择  ob upd end
    if (!CommodityAvailability.AVAILABLE.equals(commodityAvailability)) {
      return false;
    }
    for (CartItem item : this.cartItemList) {
      if (item.getShopCode().equals(shopCode) && item.getSkuCode().equals(skuCode)) {
        this.cartItemList.remove(item);
        break;
      }
    }
    getHistory().add(new Sku(shopCode, skuCode));
    return this.cartItemList.add(cartItem);
  }

  public boolean add(String shopCode, String skuCode, int quantity) {
    return add(shopCode, skuCode, "", quantity);
  }

  public boolean add(String shopCode, String skuCode, String giftCode, int quantity) {
    CartItem cartItem = this.get(shopCode, skuCode);
    if (cartItem == null) {
      cartItem = getReserve(shopCode, skuCode);
    }
    int quantitySummary = 0;
    if (cartItem == null) {
      quantitySummary = quantity;
      // 商品がまだ登録されていない場合は、CartItemを生成する。
      cartItem = createCartItemImpl(shopCode, skuCode, giftCode, quantity,false);
    } else {
      quantitySummary = cartItem.getQuantity() + quantity;
      // 既に商品が登録されている場合は該当商品を削除する
      // 20111215 shen update start
      this.remove(shopCode, skuCode, "");
    }
    cartItem.setQuantity(quantitySummary);

    return add(cartItem);
  }

  public void clear() {
    this.cartItemList.clear();
  }

  // 2012/11/22 促销对应 ob add start
  public void clearAcceptedGift() {
    List<CartItem> cartItemListTemp = new ArrayList<CartItem>();

    for (CartItem item : this.cartItemList) {
      if (!CommodityType.GIFT.longValue().equals(item.getCommodityInfo().getCommodityType())) {
        cartItemListTemp.add(item);
      }
    }
    
    this.cartItemList.clear();
    
    this.cartItemList = cartItemListTemp;
    
  }
  // 2012/11/22 促销对应 ob add end
  
  public BigDecimal getGrandTotal() {
    BigDecimal grandTotal = BigDecimal.ZERO;
    for (CartItem item : getCartItemList()) {
      grandTotal = grandTotal.add(BigDecimalUtil.multiply(item.getRetailPrice().add(item.getGiftPrice()), item.getQuantity()));
    }
    return grandTotal;
  }

  public int getItemCount() {
    int itemCount = 0;
    for (CartItem item : getCartItemList()) {
      itemCount += item.getQuantity();
    }
    return itemCount;
  }
  
  // 2012/11/22 促销对应 ob add start
  public int getItemCountExceptGift() {
    int itemCount = 0;
    for (CartItem item : getCartItemList()) {
      if (CommodityType.GENERALGOODS.longValue().equals(item.getCommodityInfo().getCommodityType())) {
        itemCount += item.getQuantity();
      }
    }
    return itemCount;
  }
  // 2012/11/22 促销对应 ob add end

  public boolean isEmpty() {
    return getItemCount() == 0; // 数量合計がゼロの場合
  }

  // 20111215 shen update start
  // public void remove(String shopCode, String skuCode) {
  //    remove(shopCode, skuCode, "");
  // }
  public void remove(String shopCode, String skuCode, String customerCode) {
    remove(shopCode, skuCode, "", customerCode);
  }
  // 20111215 shen update end

  // 20111214 shen update start
  // public void remove(String shopCode, String skuCode, String giftCode) {
  public void remove(String shopCode, String skuCode, String giftCode, String customerCode) {
  // 20111214 shen update end
    Logger logger = Logger.getLogger(this.getClass());
    List<CartItem> list = this.getCartItemList();
    List<CartItem> newList = new ArrayList<CartItem>();
    for (CartItem item : list) {
      if (item.getShopCode().equals(shopCode) && item.getCommodityCode().equals(skuCode)) {
        logger.debug("clear cart :shopCode = " + shopCode + " / skuCode = " + skuCode + " / giftCode = " + giftCode);
        
        // 20111214 shen add start
        if (StringUtil.hasValue(customerCode)) {
          CustomerService customerService = ServiceLocator.getCustomerService(ServiceLoginInfo.getInstance());
          customerService.deleteCartHistory(customerCode, shopCode, skuCode);
        }
        // 20111214 shen add end
      } else {
        newList.add(item);
      }
    }
    this.setCartItemList(newList);
  }

  // 20111214 shen update start
  // public boolean set(String shopCode, String skuCode, int quantity) {
  //   return set(shopCode, skuCode, "", quantity);
  //
  // }
  public boolean set(String shopCode, String skuCode, int quantity, String customerCode) {
    return set(shopCode, skuCode, "", quantity, customerCode);
  }
  // 20111214 shen update end
  
  
//2012/12/04 促销对应 ob update start
  // 20111214 shen update start
  // public boolean set(String shopCode, String skuCode, String giftCode, int quantity) {
//  public boolean set(String shopCode, String skuCode, String giftCode, int quantity, String customerCode) {
//  // 20111214 shen update end
//    CatalogService catalogSvc = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
//    if (!catalogSvc.isAvailable(shopCode, skuCode, quantity, catalogSvc.isReserve(shopCode, skuCode)).equals(
//        CommodityAvailability.AVAILABLE)) {
//      //return false;
//    }
//    List<CartItem> list = this.getCartItemList();
//    List<CartItem> removeList = new ArrayList<CartItem>();
//    for (CartItem item : list) {
//      // 重複している商品があれば削除
//      if (item.getShopCode().equals(shopCode) && item.getSkuCode().equals(skuCode)) {
//        removeList.add(item);
//      }
//    }
//    for (CartItem item : removeList) {
//      list.remove(item);
//    }
//
//    if (quantity > 0) {
//      CartItem item = createCartItemImpl(shopCode, skuCode, giftCode, quantity);
//      if (item == null) {
//        return false;
//      }
//      list.add(item);
//      
//      // 20111214 shen add start
//      insertCartHistory(item, customerCode);
//      // 20111214 shen add end
//    }
//
//    Collections.sort(list, new CartItemComparator());
//
//    this.setCartItemList(list);
//    getHistory().add(new Sku(shopCode, skuCode));
//    return true;
//  }


  public boolean set(String shopCode, String skuCode, String giftCode, int quantity, String customerCode) {
    
      return set(shopCode, skuCode, giftCode, quantity, customerCode, false);
    }
  
  public boolean set(String shopCode, String skuCode, String giftCode, int quantity, String customerCode, boolean isSet) {
    CatalogService catalogSvc = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    if (!catalogSvc.isAvailable(shopCode, skuCode, quantity, catalogSvc.isReserve(shopCode, skuCode)).equals(
        CommodityAvailability.AVAILABLE)) {
      //return false;
    }
    List<CartItem> list = this.getCartItemList();
    List<CartItem> removeList = new ArrayList<CartItem>();
    for (CartItem item : list) {
      // 重複している商品があれば削除
      if (item.getShopCode().equals(shopCode) && item.getSkuCode().equals(skuCode)) {
        removeList.add(item);
      }
    }
    for (CartItem item : removeList) {
      list.remove(item);
    }

    if (quantity > 0) {
      CartItem item = createCartItemImpl(shopCode, skuCode, giftCode, quantity,isSet);
      if (item == null) {
        return false;
      }
      list.add(item);
      
      // 20111214 shen add start
      insertCartHistory(item, customerCode);
      // 20111214 shen add end
    }

    Collections.sort(list, new CartItemComparator());

    this.setCartItemList(list);
    
    // 2012/12/04 促销对应 ob update start
//    getHistory().add(new Sku(shopCode, skuCode));
    if (isSet) {
      getHistory().add(new Sku(shopCode, skuCode, SetCommodityFlg.OBJECTIN.longValue()));
    } else if (skuCode != null && skuCode.contains(":")) {
      getHistory().add(new Sku(shopCode, skuCode, SetCommodityFlg.OBJECTIN.longValue()));
    } else {
      getHistory().add(new Sku(shopCode, skuCode, SetCommodityFlg.OBJECTOUT.longValue()));
    }
    // 2012/12/04 促销对应 ob update end
    
    return true;
  }
  // 2012/12/04 促销对应 ob update end
  
  private BigDecimal getSuitOldPrice(String suitCommodityCode) {
    CatalogService catalogService = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    List<CommodityCompositionContainer> commodityCompositionContainerList = catalogService.getCommodityCompositionContainerList("00000000", suitCommodityCode);
    BigDecimal totalPrce = new BigDecimal(0);
    for (CommodityCompositionContainer child : commodityCompositionContainerList) {
      for (CommodityDetail detail : child.getCommodityDetailList()) {
          totalPrce = totalPrce.add(NumUtil.parse(detail.getUnitPrice().toString()));
      }
    }
    return totalPrce;
  }
  
  // 20111214 shen add start
  public boolean updateQuantity(String shopCode, String skuCode, boolean isOrder, int quantity) {
    return updateQuantity(shopCode, skuCode, isOrder, quantity, "");
  }
  // 20111214 shen add end
  
  // 20111214 shen update start
  // public boolean updateQuantity(String shopCode, String skuCode, boolean isOrder, int quantity) {
  public boolean updateQuantity(String shopCode, String skuCode, boolean isOrder, int quantity, String customerCode) {
  // 20111214 shen update end
    CartItem cartItem = this.get(shopCode, skuCode, isOrder);
    if (cartItem == null) {
      return false;
    } else {
      // 20111215 shen update start
      // this.remove(shopCode, skuCode);
      this.remove(shopCode, skuCode, customerCode);
      // 20111215 shen update end
      cartItem.setQuantity(quantity);
      
      // 2012/11/23 促销对应 ob add start
      if (cartItem.getCommodityInfo().getGiftList() != null && cartItem.getCommodityInfo().getGiftList().size() > 0) {
        CampaignMainDao camDao = DIContainer.getDao(CampaignMainDao.class);
        for (GiftItem gift : cartItem.getCommodityInfo().getGiftList()) {
          CampaignMain camMain = camDao.load(gift.getCampaignCode());
          Long giftAmount = 1L;
          if (camMain.getGiftAmount() != null ) {
            giftAmount = camMain.getGiftAmount();
          }
          gift.setQuantity(Integer.parseInt(giftAmount.toString()));
        }
      }
      // 2012/11/23 促销对应 ob add end
      
      // 20111214 shen add start
      insertCartHistory(cartItem, customerCode);
      // 20111214 shen add end

      // 現在登録されている商品数との差分を追加する
      return this.cartItemList.add(cartItem);
    }
  }

  /**
   * @return the cartItemList
   */
  private List<CartItem> getCartItemList() {
    return cartItemList;
  }

  /**
   * @param cartItemList
   *          the cartItemList to set
   */
  private void setCartItemList(List<CartItem> cartItemList) {
    this.cartItemList = cartItemList;
  }

  /**
   * @param shopCode
   * @param isOrder
   *          予約か受注か true-受注商品 false-予約商品
   * @return
   */
  private List<CartItem> get(String shopCode, boolean isOrder) {
    if (this.getCartItemList().size() < 1) {
      return new ArrayList<CartItem>();
    }
    List<CartItem> list = new ArrayList<CartItem>();

    for (CartItem item : this.getCartItemList()) {
      if ((StringUtil.isNullOrEmpty(shopCode) || item.getShopCode().equals(shopCode)) && item.isOrder() == isOrder) {
        list.add(item);
      }
    }

    return list;
  }

  public List<CartItem> get() {
    return get("", true);
  }

  public List<CartItem> getReserve() {
    return get("", false);
  }

  public List<CartItem> get(String shopCode) {
    return get(shopCode, true);
  }

  private CartItem get(String shopCode, String skuCode, boolean isOrder) {
    if (this.getCartItemList().size() < 1) {
      return null;
    }

    CartItem result = null;

    for (CartItem item : this.getCartItemList()) {
      if (item.getShopCode().equals(shopCode) && item.getSkuCode().equals(skuCode) && item.isOrder() == isOrder) {
        result = item;
      }
    }
    return result;
  }

  public CartItem get(String shopCode, String skuCode) {
    return get(shopCode, skuCode, true);
  }

  public CartItem getReserve(String shopCode, String skuCode) {
    return get(shopCode, skuCode, false);
  }

  private CartItem createCartItemImpl(String shopCode, String skuCode, String giftCode, int quantity,boolean isSet) {
    CartItemImpl itemImpl = new CartItemImpl();
    itemImpl.setShopCode(shopCode);
    itemImpl.setSkuCode(skuCode);
    itemImpl.setGiftCode(giftCode);
    // 2012/11/23 促销对应 ob add start
    String campaignCode = "";
    if (skuCode.contains("~")) {
    	campaignCode = skuCode.split("~")[0];
    	skuCode = skuCode.split("~")[1];
    }
    if (skuCode.contains(SetCommodityUtil.SET_COMMODITY_DELIMITER)) {
    	skuCode = skuCode.split(SetCommodityUtil.SET_COMMODITY_DELIMITER)[0];
    }
    // 2012/11/23 促销对应 ob add end
    // ショップ名の取得
    UtilService utilSvc = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
    itemImpl.setShopName(utilSvc.getShopName(shopCode));

    // 消費税の取得
    TaxUtil u = DIContainer.get("TaxUtil");
    Long taxRate = u.getTaxRate();

    // 商品情報の取得
    CatalogService catalogSvc = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    CommodityInfo skuInfo = catalogSvc.getSkuInfo(shopCode, skuCode);
    if (skuInfo == null || skuInfo.getHeader() == null || skuInfo.getDetail() == null) {
      return null;
    }

    Campaign campaign = catalogSvc
        .getAppliedCampaignInfo(skuInfo.getHeader().getShopCode(), skuInfo.getHeader().getCommodityCode());
    Price price = new Price(skuInfo.getHeader(), skuInfo.getDetail(), campaign, taxRate);
    
    itemImpl.setCommodityCode(skuInfo.getHeader().getCommodityCode());  
    itemImpl.setCommodityTaxType(skuInfo.getHeader().getCommodityTaxType());
    itemImpl.setCommodityTaxCharge(price.retailTaxCharge());
    itemImpl.setUnitTaxCharge(price.getUnitTaxCharge());
    if (isSet) {
      itemImpl.setUnitPrice(getSuitOldPrice(skuCode));
      itemImpl.setRetailPrice(catalogSvc.getSuitSalePrice(skuCode).getRetailPrice());
    } else {
      itemImpl.setUnitPrice(price.getUnitPrice());
      itemImpl.setRetailPrice(price.getRetailPrice());
    }

    //edit by cs_yuli 20120521 start 
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
      itemImpl.setCommodityName(skuInfo.getHeader().getCommodityName());
      itemImpl.setStandardDetail1Name(skuInfo.getDetail().getStandardDetail1Name());
      itemImpl.setStandardDetail2Name(skuInfo.getDetail().getStandardDetail2Name());
    } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
      itemImpl.setCommodityName(skuInfo.getHeader().getCommodityNameEn());
      itemImpl.setStandardDetail1Name(skuInfo.getDetail().getStandardDetail1NameEn());
      itemImpl.setStandardDetail2Name(skuInfo.getDetail().getStandardDetail2NameEn());
    } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
      itemImpl.setCommodityName(skuInfo.getHeader().getCommodityNameJp());
      itemImpl.setStandardDetail1Name(skuInfo.getDetail().getStandardDetail1NameJp());
      itemImpl.setStandardDetail2Name(skuInfo.getDetail().getStandardDetail2NameJp());
    } 
    //edit by cs_yuli 20120521 end   
	
    if (price.isCampaign()) {
      itemImpl.setCampaignCode(price.getCampaignInfo().getCampaignCode());
      itemImpl.setCampaignName(utilSvc.getNameByLanguage(price.getCampaignInfo().getCampaignName(),price.getCampaignInfo().getCampaignNameEn(),price.getCampaignInfo().getCampaignNameJp()));
      itemImpl.setCampaignDiscountRate(price.getCampaignInfo().getCampaignDiscountRate());
    }

    // 2012/11/17 促销对应 ob add start
    itemImpl.getCommodityInfo().setCommodityType(skuInfo.getHeader().getCommodityType());
    itemImpl.getCommodityInfo().setSetCommodityFlg(skuInfo.getHeader().getSetCommodityFlg());
    //多关联赠品时
    if (CommodityType.GIFT.longValue().equals(skuInfo.getHeader().getCommodityType())) {
      if (StringUtil.hasValue(campaignCode)) {
        CampaignMainDao campaignMainDao = DIContainer.getDao(CampaignMainDao.class);
        CampaignMain campaignMainInfo = campaignMainDao.load(campaignCode);
        if (campaignMainInfo != null) {
          itemImpl.getCommodityInfo().setMultipleCampaignCode(campaignMainInfo.getCampaignCode());
          if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {          
            itemImpl.getCommodityInfo().setMultipleCampaignName(campaignMainInfo.getCampaignName());
          } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
            itemImpl.getCommodityInfo().setMultipleCampaignName(campaignMainInfo.getCampaignNameEn());
          } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
            itemImpl.getCommodityInfo().setMultipleCampaignName(campaignMainInfo.getCampaignNameJp());
          }
        }
      }
    }
//    if (SetCommodityFlg.OBJECTIN.longValue().equals(itemImpl.getCommodityInfo().getSetCommodityFlg())) {
//      itemImpl.setRetailPrice(price.getUnitPrice());
//    }
    // 2012/11/23 促销对应 ob add end
    
    // ギフト情報の取得
    if (StringUtil.isNullOrEmpty(giftCode)) {
      itemImpl.setGiftCode("");
      itemImpl.setGiftName("");
      itemImpl.setGiftPrice(BigDecimal.ZERO);
      itemImpl.setGiftTaxCharge(BigDecimal.ZERO);
    } else {
      Gift gift = catalogSvc.getGift(shopCode, giftCode);
      if (gift == null) {
        return null;
      }
      itemImpl.setGiftName(gift.getGiftName());
      itemImpl.setGiftPrice(Price.getPriceIncludingTax(gift.getGiftPrice(), taxRate.longValue(), String.valueOf(gift.getGiftTaxType())));
      itemImpl.setGiftTaxType(gift.getGiftTaxType());
      itemImpl.setGiftTaxCharge(Price.getPriceTaxCharge(gift.getGiftPrice(), taxRate.longValue(), String.valueOf(gift.getGiftTaxType())));
    }
    itemImpl.setQuantity(quantity);
    itemImpl.setReserve(price.isReserved());

    itemImpl.setSubTotal(BigDecimalUtil.multiply(itemImpl.getRetailPrice().add(itemImpl.getGiftPrice()), quantity));

    if (price.isSale()) {
      itemImpl.getCommodityInfo().setCommodityPriceType(CommodityPriceType.UNIT_PRICE);
    } else if (price.isDiscount()) {
      itemImpl.getCommodityInfo().setCommodityPriceType(CommodityPriceType.DISCOUNT_PRICE);
    } else if (price.isReserved()) {
      itemImpl.getCommodityInfo().setCommodityPriceType(CommodityPriceType.RESERVATION_PRICE);
    }
    
    itemImpl.getCommodityInfo().setDiscount(price.isDiscount());
    itemImpl.getCommodityInfo().setUseCommodityPoint(price.isPoint());
    itemImpl.getCommodityInfo().setCommodityPointRate(skuInfo.getHeader().getCommodityPointRate());
    // 20111230 shen add start
    itemImpl.setWeight(skuInfo.getDetail().getWeight());
    // 20111230 shen add end
    // 20120206 shen add start
    itemImpl.getCommodityInfo().setStockManagementType(skuInfo.getHeader().getStockManagementType().toString());
    // 20120206 shen add end
    itemImpl.getCommodityInfo().setOriginalCommodityCode(skuInfo.getHeader().getOriginalCommodityCode());
    if (skuInfo.getHeader().getCombinationAmount() != null){
      itemImpl.getCommodityInfo().setCombinationAmount(skuInfo.getHeader().getCombinationAmount());
    }
    
    return itemImpl;
  }

  public List<String> getShopCodeList() {
    List<String> shopCodeList = new ArrayList<String>();
    for (CartItem item : get()) {
      String shopCode = item.getShopCode();
      if (!shopCodeList.contains(shopCode)) {
        shopCodeList.add(shopCode);
      }
    }
    return shopCodeList;
  }

  public List<String> getReserveShopCodeList() {
    List<String> shopCodeList = new ArrayList<String>();
    for (CartItem item : getReserve()) {
      String shopCode = item.getShopCode();
      if (!shopCodeList.contains(shopCode)) {
        shopCodeList.add(shopCode);
      }
    }
    return shopCodeList;
  }

  private static class CartItemComparator implements Comparator<CartItem>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * カートに追加する商品のソート用に比較を行います。
     * 
     * @param item1
     * @param item2
     * @return 処理結果
     */
    public int compare(CartItem item1, CartItem item2) {
      String shopCode1 = item1.getShopCode();
      String shopCode2 = item2.getShopCode();

      if (shopCode1.equals(shopCode2)) {
        String commodityCode1 = item1.getCommodityCode();
        String commodityCode2 = item2.getCommodityCode();

        if (commodityCode1.equals(commodityCode2)) {
          return item1.getSkuCode().compareTo(item2.getSkuCode());
        } else {
          return commodityCode1.compareTo(commodityCode2);
        }
      } else {
        return shopCode1.compareTo(shopCode2);
      }
    }
  }

  public boolean checkDeliveryType(String selectShopCode) {
    WebshopConfig config = DIContainer.getWebshopConfig();
    CatalogService catalogService = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    String shopCode = "";
    String deliveryTypeNo = "";
    if (config.getOperatingMode() == OperatingMode.MALL || config.getOperatingMode() == OperatingMode.ONE) {
      if (get().size() > 0) {
        if (catalogService.isListed(get().get(0).getShopCode(), get().get(0).getCommodityCode())) {
          CommodityInfo commodityInfo = catalogService
              .getCommodityInfo(get().get(0).getShopCode(), get().get(0).getCommodityCode());
          shopCode = get().get(0).getShopCode();
          deliveryTypeNo = NumUtil.toString(commodityInfo.getHeader().getDeliveryTypeNo());
        }
      }
      for (CartItem item : get()) {
        if (catalogService.isListed(item.getShopCode(), item.getCommodityCode())) {
          CommodityInfo commodityInfo = catalogService.getCommodityInfo(item.getShopCode(), item.getCommodityCode());
          if (shopCode.equals(commodityInfo.getHeader().getShopCode())) {
            if (!deliveryTypeNo.equals(NumUtil.toString(commodityInfo.getHeader().getDeliveryTypeNo()))) {
              return true;
            }
          } else {
            return true;
          }
        }
      }
    } else {
      if (get(selectShopCode).size() > 0) {
        if (catalogService.isListed(selectShopCode, get(selectShopCode).get(0).getCommodityCode())) {
          CommodityInfo commodityInfo = catalogService.getCommodityInfo(selectShopCode, get(selectShopCode).get(0)
              .getCommodityCode());
          deliveryTypeNo = NumUtil.toString(commodityInfo.getHeader().getDeliveryTypeNo());
        }
      }
      for (CartItem item : get(selectShopCode)) {
        if (catalogService.isListed(selectShopCode, item.getCommodityCode())) {
          CommodityInfo commodityInfo = catalogService.getCommodityInfo(selectShopCode, item.getCommodityCode());
          if (!deliveryTypeNo.equals(NumUtil.toString(commodityInfo.getHeader().getDeliveryTypeNo()))) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  // 20111214 shen add start
  private void insertCartHistory(CartItem item, String customerCode) {
    if (StringUtil.hasValue(customerCode)) {
      CartHistory cartHistory = new CartHistory();
      cartHistory.setCustomerCode(customerCode);
      cartHistory.setShopCode(item.getShopCode());
      cartHistory.setCommodityCode(item.getCommodityCode());
      cartHistory.setSkuCode(item.getSkuCode());
      cartHistory.setPurchasingAmount(new Long(item.getQuantity()));
      cartHistory.setCartDatetime(DateUtil.getSysdate());
      
      CustomerService customerService = ServiceLocator.getCustomerService(ServiceLoginInfo.getInstance());
      customerService.insertCartHistory(cartHistory);
    }
  }
  
  public void setCartItemFromHistory(String customerCode) {
    if (StringUtil.isNullOrEmpty(customerCode)) {
      return;
    }
    
    CustomerService customerService = ServiceLocator.getCustomerService(ServiceLoginInfo.getInstance());
    List<CartHistory> cartHistoryList = customerService.getCartHistoryList(customerCode);
    if (cartHistoryList != null && cartHistoryList.size() > 0) {
      for (CartHistory cartHistory : cartHistoryList) {
        Long quantity = cartHistory.getPurchasingAmount();
        int cartQuantity = quantity.intValue();
        CartItem cartItem = this.get(cartHistory.getShopCode(), cartHistory.getSkuCode());
        if (cartItem == null) {
          cartItem = this.getReserve(cartHistory.getShopCode(), cartHistory.getSkuCode());
        }
        if (cartItem != null) {
          cartQuantity = cartItem.getQuantity() + quantity.intValue();
        }
        
        this.set(cartHistory.getShopCode(), cartHistory.getSkuCode(), cartQuantity, customerCode);
      }
    }
  }
  
  public void insertCartHistory(String customerCode) {
    // 通常商品
    List<CartItem> cartItemList = this.get();
    for (CartItem cartItem : cartItemList) {
      insertCartHistory(cartItem, customerCode);
    }
    // 预约商品
    cartItemList = this.getReserve();
    for (CartItem cartItem : cartItemList) {
      insertCartHistory(cartItem, customerCode);
    }
  }
  
  public void clearCartHistroy(String customerCode) {
    CustomerService customerService = ServiceLocator.getCustomerService(ServiceLoginInfo.getInstance());
    customerService.deleteCartHistory(customerCode);
  }
  // 20111214 shen add end
  
  // 20120221 shen add start
  private String prefectureCode;
  
  /**
   * @return the prefectureCode
   */
  public String getPrefectureCode() {
    return prefectureCode;
  }
  
  /**
   * @param prefectureCode the prefectureCode to set
   */
  public void setPrefectureCode(String prefectureCode) {
    this.prefectureCode = prefectureCode;
  }
  // 20120221 shen add end
  
  private BigDecimal optionalCheapPrice = BigDecimal.ZERO;
  
  private Map<String ,OptionalCommodity> optionalCommodityMap = new HashMap<String ,OptionalCommodity>();
  
  private List<String> opCommodityCodeList = new ArrayList<String>();

  
  /**
   * @return the optionalCheapPrice
   */
  public BigDecimal getOptionalCheapPrice() {
    return optionalCheapPrice;
  }

  
  /**
   * @param optionalCheapPrice the optionalCheapPrice to set
   */
  public void setOptionalCheapPrice(BigDecimal optionalCheapPrice) {
    this.optionalCheapPrice = optionalCheapPrice;
  }

  
  /**
   * @return the optionalCommodityMap
   */
  public Map<String, OptionalCommodity> getOptionalCommodityMap() {
    return optionalCommodityMap;
  }

  
  /**
   * @param optionalCommodityMap the optionalCommodityMap to set
   */
  public void setOptionalCommodityMap(Map<String, OptionalCommodity> optionalCommodityMap) {
    this.optionalCommodityMap = optionalCommodityMap;
  }

  
  /**
   * @return the opCommodityCodeList
   */
  public List<String> getOpCommodityCodeList() {
    return opCommodityCodeList;
  }

  
  /**
   * @param opCommodityCodeList the opCommodityCodeList to set
   */
  public void setOpCommodityCodeList(List<String> opCommodityCodeList) {
    this.opCommodityCodeList = opCommodityCodeList;
  }
  
  
}
