package jp.co.sint.webshop.service.cart.impl;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;

public class CartItemImpl implements CartItem {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String shopName;

  private CartCommodityInfo commodityInfo = new CartCommodityInfo();

  private boolean reserve;

  private BigDecimal subTotal;

  private String isDiscountCommodity = "false";
  
  public void setGift(String commodityShopCode, String giftCode) {
    // 消費税の取得
    TaxUtil u = DIContainer.get("TaxUtil");
    Long taxRate = u.getTaxRate();

    CatalogService catalogSvc = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    Gift gift = catalogSvc.getGift(commodityShopCode, giftCode);
    this.setGiftCode(giftCode);
    this.setGiftName(gift.getGiftName());
    this.setGiftPrice(Price.getPriceIncludingTax(gift.getGiftPrice(), taxRate.longValue(), String.valueOf(gift
        .getGiftTaxType())));
    this.setGiftTaxType(gift.getGiftTaxType());
    this.setGiftTaxCharge(Price.getPriceTaxCharge(gift.getGiftPrice(), taxRate.longValue(), String.valueOf(gift
        .getGiftTaxType())));
  }

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
    commodityInfo.setShopCode(shopCode);
  }

  /**
   * @return the shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * @param shopName
   *          the shopName to set
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityInfo.getCommodityCode();
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    commodityInfo.setCommodityCode(commodityCode);
  }

  /**
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityInfo.getCommodityName();
  }

  /**
   * @param commodityName
   *          the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    commodityInfo.setCommodityName(commodityName);
  }

  /**
   * @return the skuCode
   */
  public String getSkuCode() {
    return commodityInfo.getSkuCode();
  }

  /**
   * @param skuCode
   *          the skuCode to set
   */
  public void setSkuCode(String skuCode) {
    commodityInfo.setSkuCode(skuCode);
  }

  /**
   * @return the commodityTaxType
   */
  public Long getCommodityTaxType() {
    return commodityInfo.getCommodityTaxType();
  }

  /**
   * @param commodityTaxType
   *          the commodityTaxType to set
   */
  public void setCommodityTaxType(Long commodityTaxType) {
    commodityInfo.setCommodityTaxType(commodityTaxType);
  }

  /**
   * @return the commodityTaxCharge
   */
  public BigDecimal getCommodityTaxCharge() {
    return commodityInfo.getCommodityTaxCharge();
  }

  /**
   * @param commodityTaxCharge
   *          the commodityTaxCharge to set
   */
  public void setCommodityTaxCharge(BigDecimal commodityTaxCharge) {
    commodityInfo.setCommodityTaxCharge(commodityTaxCharge);
  }

  /**
   * @return the unitTaxCharge
   */
  public BigDecimal getUnitTaxCharge() {
    return commodityInfo.getUnitTaxCharge();
  }

  /**
   * @param unitTaxCharge
   *          the unitTaxCharge to set
   */
  public void setUnitTaxCharge(BigDecimal unitTaxCharge) {
    commodityInfo.setUnitTaxCharge(unitTaxCharge);
  }

  /**
   * @return the unitPrice
   */
  public BigDecimal getUnitPrice() {
    return commodityInfo.getUnitPrice();
  }

  /**
   * @param unitPrice
   *          the unitPrice to set
   */
  public void setUnitPrice(BigDecimal unitPrice) {
    commodityInfo.setUnitPrice(unitPrice);
  }

  /**
   * @return the retailPrice
   */
  public BigDecimal getRetailPrice() {
    return commodityInfo.getRetailPrice();
  }

  /**
   * @param retailPrice
   *          the retailPrice to set
   */
  public void setRetailPrice(BigDecimal retailPrice) {
    commodityInfo.setRetailPrice(retailPrice);
  }

  /**
   * @return the standardDetail1Name
   */
  public String getStandardDetail1Name() {
    return commodityInfo.getStandardDetail1Name();
  }

  /**
   * @param standardDetail1Name
   *          the standardDetail1Name to set
   */
  public void setStandardDetail1Name(String standardDetail1Name) {
    commodityInfo.setStandardDetail1Name(standardDetail1Name);
  }

  /**
   * @return the standardDetail2Name
   */
  public String getStandardDetail2Name() {
    return commodityInfo.getStandardDetail2Name();
  }

  /**
   * @param standardDetail2Name
   *          the standardDetail2Name to set
   */
  public void setStandardDetail2Name(String standardDetail2Name) {
    commodityInfo.setStandardDetail2Name(standardDetail2Name);
  }

  /**
   * @return the giftCode
   */
  public String getGiftCode() {
    return commodityInfo.getGiftCode();
  }

  /**
   * @param giftCode
   *          the giftCode to set
   */
  public void setGiftCode(String giftCode) {
    commodityInfo.setGiftCode(giftCode);
  }

  /**
   * @return the giftName
   */
  public String getGiftName() {
    return commodityInfo.getGiftName();
  }

  /**
   * @param giftName
   *          the giftName to set
   */
  public void setGiftName(String giftName) {
    commodityInfo.setGiftName(giftName);
  }

  /**
   * @return the giftPrice
   */
  public BigDecimal getGiftPrice() {
    if (commodityInfo.getGiftPrice() == null) {
      commodityInfo.setGiftPrice(BigDecimal.ZERO);
    }
    return commodityInfo.getGiftPrice();
  }

  /**
   * @param giftPrice
   *          the giftPrice to set
   */
  public void setGiftPrice(BigDecimal giftPrice) {
    commodityInfo.setGiftPrice(giftPrice);
  }

  /**
   * @return the giftTaxType
   */
  public Long getGiftTaxType() {
    return commodityInfo.getGiftTaxType();
  }

  /**
   * @param giftTaxType
   *          the giftTaxType to set
   */
  public void setGiftTaxType(Long giftTaxType) {
    commodityInfo.setGiftTaxType(giftTaxType);
  }

  /**
   * @return the giftTaxCharge
   */
  public BigDecimal getGiftTaxCharge() {
    return commodityInfo.getGiftTaxCharge();
  }

  /**
   * @param giftTaxCharge
   *          the giftTaxCharge to set
   */
  public void setGiftTaxCharge(BigDecimal giftTaxCharge) {
    commodityInfo.setGiftTaxCharge(giftTaxCharge);
  }

  /**
   * @return the quantity
   */
  public int getQuantity() {
    return commodityInfo.getQuantity();
  }

  /**
   * @param quantity
   *          the quantity to set
   */
  public void setQuantity(int quantity) {
    commodityInfo.setQuantity(quantity);
  }

  /**
   * @return the reserve
   */
  public boolean isReserve() {
    return reserve;
  }

  /**
   * @param reserve
   *          the reserve to set
   */
  public void setReserve(boolean reserve) {
    this.reserve = reserve;
  }

  /**
   * @return the subTotal
   */
  public BigDecimal getSubTotal() {
    return subTotal;
  }

  /**
   * @param subTotal
   *          the subTotal to set
   */
  public void setSubTotal(BigDecimal subTotal) {
    this.subTotal = subTotal;
  }

  /**
   * @return the campaginCode
   */
  public String getCampaignCode() {
    return commodityInfo.getCampaignCode();
  }

  /**
   * @param campaignCode
   *          the campaignCode to set
   */
  public void setCampaignCode(String campaignCode) {
    commodityInfo.setCampaignCode(campaignCode);
  }

  /**
   * @return the campainName
   */
  public String getCampaignName() {
    return commodityInfo.getCampaignName();
  }

  /**
   * @param campaignName
   *          the campaignName to set
   */
  public void setCampaignName(String campaignName) {
    commodityInfo.setCampaignName(campaignName);
  }

  /**
   * @return the campaignDiscountRate
   */
  public Long getCampaignDiscountRate() {
    return commodityInfo.getCampaignDiscountRate();
  }

  /**
   * @param campaignDiscountRate
   *          the campaignDiscountRate to set
   */
  public void setCampaignDiscountRate(Long campaignDiscountRate) {
    commodityInfo.setCampaignDiscountRate(campaignDiscountRate);
  }

  public boolean isOrder() {
    return !isReserve();
  }

  /**
   * commodityInfoを取得します。
   * 
   * @return the commodityInfo
   */
  public CartCommodityInfo getCommodityInfo() {
    return commodityInfo;
  }

  /**
   * commodityInfoを設定します。
   * 
   * @param commodityInfo
   *          the commodityInfo to set
   */
  public void setCommodityInfo(CartCommodityInfo commodityInfo) {
    this.commodityInfo = commodityInfo;
  }
  
  // 20111230 shen add start
  public BigDecimal getWeight() {
    return commodityInfo.getWeight();
  }
  
  public void setWeight(BigDecimal weight) {
    commodityInfo.setWeight(weight);
  }
  // 20111230 shen add end
  //2012/11/23 促销对应 新建订单_商品选择  ob add start
  public Long getCommodityType() {
    return commodityInfo.getCommodityType();
  }
  
  public void setCommodityType(Long commodityType) {
    commodityInfo.setCommodityType(commodityType);
  }
  //2012/11/23 促销对应 新建订单_商品选择  ob add end
  

  public Long getCombinationAmount() {
    return commodityInfo.getCombinationAmount();
  }

  public void setCombinationAmount(Long combinationAmount) {
    commodityInfo.setCombinationAmount(combinationAmount);
  }
  

  public String getOriginalCommodityCode() {
    return commodityInfo.getOriginalCommodityCode();
  }

  public void setOriginalCommodityCode(String originalCommodityCode) {
    commodityInfo.setOriginalCommodityCode(originalCommodityCode);
  }

  
  /**
   * @return the isDiscountCommodity
   */
  public String getIsDiscountCommodity() {
    return commodityInfo.getIsDiscountCommodity();
  }

  
  /**
   * @param isDiscountCommodity the isDiscountCommodity to set
   */
  public void setIsDiscountCommodity(String isDiscountCommodity) {
    commodityInfo.setIsDiscountCommodity(isDiscountCommodity);
  }
  
}
