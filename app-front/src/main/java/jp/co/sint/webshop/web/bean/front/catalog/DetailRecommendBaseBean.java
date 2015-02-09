package jp.co.sint.webshop.web.bean.front.catalog;

import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040510:商品詳細のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class DetailRecommendBaseBean extends UISubBean {

  private static final long serialVersionUID = 1L;

  private String commodityCode;

  private String commodityName;

  private String commodityImage;

  private String unitPrice;

  private String discountPrice;

  private String reservationPrice;

  private String commodityTaxType;

  private String campaignDiscountRate;

  private String commodityPointRate;

  private String reviewScore;

  private String commodityDescription;

  private String stockStatusImage;

  private String campaignImage;

  private String campaignCode;

  private String campaignName;

  private String shopCode;

  private String shopName;

  private String priceMode;

  private boolean campaignPeriod;

  private boolean discountPeriod;

  private boolean commodityPointPeriod;

  private boolean hasStock;

  private boolean displayStockStatus;

  private Long importCommodityType;

  private Long clearCommodityType;

  private Long reserveCommodityType1;

  private Long reserveCommodityType2;

  private Long reserveCommodityType3;

  private Long newReserveCommodityType1;

  private Long newReserveCommodityType2;

  private Long newReserveCommodityType3;

  private Long newReserveCommodityType4;

  private Long newReserveCommodityType5;

  private boolean discountCommodityFlg;
  
  private Long innerQuantity;

  /**
   * @return the newReserveCommodityType1
   */
  public Long getNewReserveCommodityType1() {
    return newReserveCommodityType1;
  }

  /**
   * @param newReserveCommodityType1
   *          the newReserveCommodityType1 to set
   */
  public void setNewReserveCommodityType1(Long newReserveCommodityType1) {
    this.newReserveCommodityType1 = newReserveCommodityType1;
  }

  /**
   * @return the newReserveCommodityType2
   */
  public Long getNewReserveCommodityType2() {
    return newReserveCommodityType2;
  }

  /**
   * @param newReserveCommodityType2
   *          the newReserveCommodityType2 to set
   */
  public void setNewReserveCommodityType2(Long newReserveCommodityType2) {
    this.newReserveCommodityType2 = newReserveCommodityType2;
  }

  /**
   * @return the newReserveCommodityType3
   */
  public Long getNewReserveCommodityType3() {
    return newReserveCommodityType3;
  }

  /**
   * @param newReserveCommodityType3
   *          the newReserveCommodityType3 to set
   */
  public void setNewReserveCommodityType3(Long newReserveCommodityType3) {
    this.newReserveCommodityType3 = newReserveCommodityType3;
  }

  /**
   * @return the newReserveCommodityType4
   */
  public Long getNewReserveCommodityType4() {
    return newReserveCommodityType4;
  }

  /**
   * @param newReserveCommodityType4
   *          the newReserveCommodityType4 to set
   */
  public void setNewReserveCommodityType4(Long newReserveCommodityType4) {
    this.newReserveCommodityType4 = newReserveCommodityType4;
  }

  /**
   * @return the newReserveCommodityType5
   */
  public Long getNewReserveCommodityType5() {
    return newReserveCommodityType5;
  }

  /**
   * @param newReserveCommodityType5
   *          the newReserveCommodityType5 to set
   */
  public void setNewReserveCommodityType5(Long newReserveCommodityType5) {
    this.newReserveCommodityType5 = newReserveCommodityType5;
  }

  
  /**
   * @return the discountCommodityFlg
   */
  public boolean isDiscountCommodityFlg() {
    return discountCommodityFlg;
  }

  
  /**
   * @param discountCommodityFlg the discountCommodityFlg to set
   */
  public void setDiscountCommodityFlg(boolean discountCommodityFlg) {
    this.discountCommodityFlg = discountCommodityFlg;
  }

  /**
   * @return the innerQuantity
   */
  public Long getInnerQuantity() {
    return innerQuantity;
  }

  /**
   * @param innerQuantity
   *          the innerQuantity to set
   */
  public void setInnerQuantity(Long innerQuantity) {
    this.innerQuantity = innerQuantity;
  }

  // 2012/11/26 促销对应 ob add start
  private String skuCode;

  // 2012/11/26 促销对应 ob add end

  /**
   * displayStockStatusを取得します。
   * 
   * @return displayStockStatus
   */
  public boolean isDisplayStockStatus() {
    return displayStockStatus;
  }

  /**
   * @return the importCommodityType
   */
  public Long getImportCommodityType() {
    return importCommodityType;
  }

  /**
   * @param importCommodityType
   *          the importCommodityType to set
   */
  public void setImportCommodityType(Long importCommodityType) {
    this.importCommodityType = importCommodityType;
  }

  /**
   * @return the clearCommodityType
   */
  public Long getClearCommodityType() {
    return clearCommodityType;
  }

  /**
   * @param clearCommodityType
   *          the clearCommodityType to set
   */
  public void setClearCommodityType(Long clearCommodityType) {
    this.clearCommodityType = clearCommodityType;
  }

  /**
   * @return the reserveCommodityType1
   */
  public Long getReserveCommodityType1() {
    return reserveCommodityType1;
  }

  /**
   * @param reserveCommodityType1
   *          the reserveCommodityType1 to set
   */
  public void setReserveCommodityType1(Long reserveCommodityType1) {
    this.reserveCommodityType1 = reserveCommodityType1;
  }

  /**
   * @return the reserveCommodityType2
   */
  public Long getReserveCommodityType2() {
    return reserveCommodityType2;
  }

  /**
   * @param reserveCommodityType2
   *          the reserveCommodityType2 to set
   */
  public void setReserveCommodityType2(Long reserveCommodityType2) {
    this.reserveCommodityType2 = reserveCommodityType2;
  }

  /**
   * @return the reserveCommodityType3
   */
  public Long getReserveCommodityType3() {
    return reserveCommodityType3;
  }

  /**
   * @param reserveCommodityType3
   *          the reserveCommodityType3 to set
   */
  public void setReserveCommodityType3(Long reserveCommodityType3) {
    this.reserveCommodityType3 = reserveCommodityType3;
  }

  public String getSkuCode() {
    return skuCode;
  }

  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  /**
   * displayStockStatusを設定します。
   * 
   * @param displayStockStatus
   *          displayStockStatus
   */
  public void setDisplayStockStatus(boolean displayStockStatus) {
    this.displayStockStatus = displayStockStatus;
  }

  /**
   * campaignCodeを取得します。
   * 
   * @return campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
  }

  /**
   * campaignCodeを設定します。
   * 
   * @param campaignCode
   *          campaignCode
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  /**
   * campaignDiscountRateを取得します。
   * 
   * @return campaignDiscountRate
   */
  public String getCampaignDiscountRate() {
    return campaignDiscountRate;
  }

  /**
   * campaignDiscountRateを設定します。
   * 
   * @param campaignDiscountRate
   *          campaignDiscountRate
   */
  public void setCampaignDiscountRate(String campaignDiscountRate) {
    this.campaignDiscountRate = campaignDiscountRate;
  }

  /**
   * campaignImageを取得します。
   * 
   * @return campaignImage
   */
  public String getCampaignImage() {
    return campaignImage;
  }

  /**
   * campaignImageを設定します。
   * 
   * @param campaignImage
   *          campaignImage
   */
  public void setCampaignImage(String campaignImage) {
    this.campaignImage = campaignImage;
  }

  /**
   * campaignNameを取得します。
   * 
   * @return campaignName
   */
  public String getCampaignName() {
    return campaignName;
  }

  /**
   * campaignNameを設定します。
   * 
   * @param campaignName
   *          campaignName
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  /**
   * campaignPeriodを取得します。
   * 
   * @return campaignPeriod
   */
  public boolean isCampaignPeriod() {
    return campaignPeriod;
  }

  /**
   * campaignPeriodを設定します。
   * 
   * @param campaignPeriod
   *          campaignPeriod
   */
  public void setCampaignPeriod(boolean campaignPeriod) {
    this.campaignPeriod = campaignPeriod;
  }

  /**
   * commodityCodeを取得します。
   * 
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * commodityDescriptionを取得します。
   * 
   * @return commodityDescription
   */
  public String getCommodityDescription() {
    return commodityDescription;
  }

  /**
   * commodityDescriptionを設定します。
   * 
   * @param commodityDescription
   *          commodityDescription
   */
  public void setCommodityDescription(String commodityDescription) {
    this.commodityDescription = commodityDescription;
  }

  /**
   * commodityImageを取得します。
   * 
   * @return commodityImage
   */
  public String getCommodityImage() {
    return commodityImage;
  }

  /**
   * commodityImageを設定します。
   * 
   * @param commodityImage
   *          commodityImage
   */
  public void setCommodityImage(String commodityImage) {
    this.commodityImage = commodityImage;
  }

  /**
   * commodityNameを取得します。
   * 
   * @return commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * commodityNameを設定します。
   * 
   * @param commodityName
   *          commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * commodityPointPeriodを取得します。
   * 
   * @return commodityPointPeriod
   */
  public boolean isCommodityPointPeriod() {
    return commodityPointPeriod;
  }

  /**
   * commodityPointPeriodを設定します。
   * 
   * @param commodityPointPeriod
   *          commodityPointPeriod
   */
  public void setCommodityPointPeriod(boolean commodityPointPeriod) {
    this.commodityPointPeriod = commodityPointPeriod;
  }

  /**
   * commodityPointRateを取得します。
   * 
   * @return commodityPointRate
   */
  public String getCommodityPointRate() {
    return commodityPointRate;
  }

  /**
   * commodityPointRateを設定します。
   * 
   * @param commodityPointRate
   *          commodityPointRate
   */
  public void setCommodityPointRate(String commodityPointRate) {
    this.commodityPointRate = commodityPointRate;
  }

  /**
   * commodityTaxTypeを取得します。
   * 
   * @return commodityTaxType
   */
  public String getCommodityTaxType() {
    return commodityTaxType;
  }

  /**
   * commodityTaxTypeを設定します。
   * 
   * @param commodityTaxType
   *          commodityTaxType
   */
  public void setCommodityTaxType(String commodityTaxType) {
    this.commodityTaxType = commodityTaxType;
  }

  /**
   * discountPeriodを取得します。
   * 
   * @return discountPeriod
   */
  public boolean isDiscountPeriod() {
    return discountPeriod;
  }

  /**
   * discountPeriodを設定します。
   * 
   * @param discountPeriod
   *          discountPeriod
   */
  public void setDiscountPeriod(boolean discountPeriod) {
    this.discountPeriod = discountPeriod;
  }

  /**
   * discountPriceを取得します。
   * 
   * @return discountPrice
   */
  public String getDiscountPrice() {
    return discountPrice;
  }

  /**
   * discountPriceを設定します。
   * 
   * @param discountPrice
   *          discountPrice
   */
  public void setDiscountPrice(String discountPrice) {
    this.discountPrice = discountPrice;
  }

  /**
   * hasStockを取得します。
   * 
   * @return hasStock
   */
  public boolean isHasStock() {
    return hasStock;
  }

  /**
   * hasStockを設定します。
   * 
   * @param hasStock
   *          hasStock
   */
  public void setHasStock(boolean hasStock) {
    this.hasStock = hasStock;
  }

  /**
   * priceModeを取得します。
   * 
   * @return priceMode
   */
  public String getPriceMode() {
    return priceMode;
  }

  /**
   * priceModeを設定します。
   * 
   * @param priceMode
   *          priceMode
   */
  public void setPriceMode(String priceMode) {
    this.priceMode = priceMode;
  }

  /**
   * reservationPriceを取得します。
   * 
   * @return reservationPrice
   */
  public String getReservationPrice() {
    return reservationPrice;
  }

  /**
   * reservationPriceを設定します。
   * 
   * @param reservationPrice
   *          reservationPrice
   */
  public void setReservationPrice(String reservationPrice) {
    this.reservationPrice = reservationPrice;
  }

  /**
   * reviewScoreを取得します。
   * 
   * @return reviewScore
   */
  public String getReviewScore() {
    return reviewScore;
  }

  /**
   * reviewScoreを設定します。
   * 
   * @param reviewScore
   *          reviewScore
   */
  public void setReviewScore(String reviewScore) {
    this.reviewScore = reviewScore;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * shopNameを取得します。
   * 
   * @return shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * shopNameを設定します。
   * 
   * @param shopName
   *          shopName
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  /**
   * stockStatusImageを取得します。
   * 
   * @return stockStatusImage
   */
  public String getStockStatusImage() {
    return stockStatusImage;
  }

  /**
   * stockStatusImageを設定します。
   * 
   * @param stockStatusImage
   *          stockStatusImage
   */
  public void setStockStatusImage(String stockStatusImage) {
    this.stockStatusImage = stockStatusImage;
  }

  /**
   * unitPriceを取得します。
   * 
   * @return unitPrice
   */
  public String getUnitPrice() {
    return unitPrice;
  }

  /**
   * unitPriceを設定します。
   * 
   * @param unitPrice
   *          unitPrice
   */
  public void setUnitPrice(String unitPrice) {
    this.unitPrice = unitPrice;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2040510";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.catalog.DetailRecommendBaseBean.0");
  }
}
