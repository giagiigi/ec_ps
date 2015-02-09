package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Planline implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String planCode;

  private String detailType;

  private String detailCode;

  private String detailName;

  private String detailUrl;

  private String detailNameEn;

  private String detailUrlEn;

  private String detailNameJp;

  private String detailUrlJp;

  private String showCommodityCount;

  private String shopCode;

  private String commodityCode;

  private String commodityName;

  private String commodityNameJp;

  private String commodityNameEn;

  private BigDecimal unitPrice;

  private BigDecimal discountPrice;

  private BigDecimal reservationPrice;

  private BigDecimal changeUnitPrice;

  private Date saleStartDatetime;

  private Date saleEndDatetime;

  private Date discountPriceStartDatetime;

  private Date discountPriceEndDatetime;

  private Date reservationStartDatetime;

  private Date reservationEndDatetime;

  private Long commodityTaxType;

  private String commodityDescriptionPc;

  private String commodityDescriptionMobile;

  private String commodityDescriptionMobileJp;

  private String commodityDescriptionMobileEn;

  private Long stockManagementType;

  private BigDecimal retailPrice;

  private Long saleFlg;

  private Long reviewScore;

  private Long reviewCount;

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

  private Long innerQuantity;
  
  private String originalCommodityCode;
  
  private Long combinationAmount;
  
  private Long avaQuantity;
  
  //商品所属优惠活动名称
  private String campaignName;
  

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

  /**
   * @return the planCode
   */
  public String getPlanCode() {
    return planCode;
  }

  /**
   * @param planCode
   *          the planCode to set
   */
  public void setPlanCode(String planCode) {
    this.planCode = planCode;
  }

  /**
   * @return the detailType
   */
  public String getDetailType() {
    return detailType;
  }

  /**
   * @param detailType
   *          the detailType to set
   */
  public void setDetailType(String detailType) {
    this.detailType = detailType;
  }

  /**
   * @return the detailCode
   */
  public String getDetailCode() {
    return detailCode;
  }

  /**
   * @param detailCode
   *          the detailCode to set
   */
  public void setDetailCode(String detailCode) {
    this.detailCode = detailCode;
  }

  /**
   * @return the detailName
   */
  public String getDetailName() {
    return detailName;
  }

  /**
   * @param detailName
   *          the detailName to set
   */
  public void setDetailName(String detailName) {
    this.detailName = detailName;
  }

  /**
   * @return the detailUrl
   */
  public String getDetailUrl() {
    return detailUrl;
  }

  /**
   * @param detailUrl
   *          the detailUrl to set
   */
  public void setDetailUrl(String detailUrl) {
    this.detailUrl = detailUrl;
  }

  /**
   * @return the showCommodityCount
   */
  public String getShowCommodityCount() {
    return showCommodityCount;
  }

  /**
   * @param showCommodityCount
   *          the showCommodityCount to set
   */
  public void setShowCommodityCount(String showCommodityCount) {
    this.showCommodityCount = showCommodityCount;
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
  }

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * @param commodityName
   *          the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * @return the unitPrice
   */
  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  /**
   * @param unitPrice
   *          the unitPrice to set
   */
  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  /**
   * @return the discountPrice
   */
  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  /**
   * @param discountPrice
   *          the discountPrice to set
   */
  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
  }

  /**
   * @return the reservationPrice
   */
  public BigDecimal getReservationPrice() {
    return reservationPrice;
  }

  /**
   * @param reservationPrice
   *          the reservationPrice to set
   */
  public void setReservationPrice(BigDecimal reservationPrice) {
    this.reservationPrice = reservationPrice;
  }

  /**
   * @return the changeUnitPrice
   */
  public BigDecimal getChangeUnitPrice() {
    return changeUnitPrice;
  }

  /**
   * @param changeUnitPrice
   *          the changeUnitPrice to set
   */
  public void setChangeUnitPrice(BigDecimal changeUnitPrice) {
    this.changeUnitPrice = changeUnitPrice;
  }

  /**
   * @return the saleStartDatetime
   */
  public Date getSaleStartDatetime() {
    return saleStartDatetime;
  }

  /**
   * @param saleStartDatetime
   *          the saleStartDatetime to set
   */
  public void setSaleStartDatetime(Date saleStartDatetime) {
    this.saleStartDatetime = saleStartDatetime;
  }

  /**
   * @return the saleEndDatetime
   */
  public Date getSaleEndDatetime() {
    return saleEndDatetime;
  }

  /**
   * @param saleEndDatetime
   *          the saleEndDatetime to set
   */
  public void setSaleEndDatetime(Date saleEndDatetime) {
    this.saleEndDatetime = saleEndDatetime;
  }

  /**
   * @return the discountPriceStartDatetime
   */
  public Date getDiscountPriceStartDatetime() {
    return discountPriceStartDatetime;
  }

  /**
   * @param discountPriceStartDatetime
   *          the discountPriceStartDatetime to set
   */
  public void setDiscountPriceStartDatetime(Date discountPriceStartDatetime) {
    this.discountPriceStartDatetime = discountPriceStartDatetime;
  }

  /**
   * @return the discountPriceEndDatetime
   */
  public Date getDiscountPriceEndDatetime() {
    return discountPriceEndDatetime;
  }

  /**
   * @param discountPriceEndDatetime
   *          the discountPriceEndDatetime to set
   */
  public void setDiscountPriceEndDatetime(Date discountPriceEndDatetime) {
    this.discountPriceEndDatetime = discountPriceEndDatetime;
  }

  /**
   * @return the reservationStartDatetime
   */
  public Date getReservationStartDatetime() {
    return reservationStartDatetime;
  }

  /**
   * @param reservationStartDatetime
   *          the reservationStartDatetime to set
   */
  public void setReservationStartDatetime(Date reservationStartDatetime) {
    this.reservationStartDatetime = reservationStartDatetime;
  }

  /**
   * @return the reservationEndDatetime
   */
  public Date getReservationEndDatetime() {
    return reservationEndDatetime;
  }

  /**
   * @param reservationEndDatetime
   *          the reservationEndDatetime to set
   */
  public void setReservationEndDatetime(Date reservationEndDatetime) {
    this.reservationEndDatetime = reservationEndDatetime;
  }

  /**
   * @return the commodityTaxType
   */
  public Long getCommodityTaxType() {
    return commodityTaxType;
  }

  /**
   * @param commodityTaxType
   *          the commodityTaxType to set
   */
  public void setCommodityTaxType(Long commodityTaxType) {
    this.commodityTaxType = commodityTaxType;
  }

  /**
   * @return the commodityDescriptionPc
   */
  public String getCommodityDescriptionPc() {
    return commodityDescriptionPc;
  }

  /**
   * @param commodityDescriptionPc
   *          the commodityDescriptionPc to set
   */
  public void setCommodityDescriptionPc(String commodityDescriptionPc) {
    this.commodityDescriptionPc = commodityDescriptionPc;
  }

  /**
   * @return the stockManagementType
   */
  public Long getStockManagementType() {
    return stockManagementType;
  }

  /**
   * @param stockManagementType
   *          the stockManagementType to set
   */
  public void setStockManagementType(Long stockManagementType) {
    this.stockManagementType = stockManagementType;
  }

  /**
   * @return the retailPrice
   */
  public BigDecimal getRetailPrice() {
    return retailPrice;
  }

  /**
   * @param retailPrice
   *          the retailPrice to set
   */
  public void setRetailPrice(BigDecimal retailPrice) {
    this.retailPrice = retailPrice;
  }

  /**
   * @return the saleFlg
   */
  public Long getSaleFlg() {
    return saleFlg;
  }

  /**
   * @param saleFlg
   *          the saleFlg to set
   */
  public void setSaleFlg(Long saleFlg) {
    this.saleFlg = saleFlg;
  }

  /**
   * @return the reviewScore
   */
  public Long getReviewScore() {
    return reviewScore;
  }

  /**
   * @param reviewScore
   *          the reviewScore to set
   */
  public void setReviewScore(Long reviewScore) {
    this.reviewScore = reviewScore;
  }

  /**
   * @return the reviewCount
   */
  public Long getReviewCount() {
    return reviewCount;
  }

  /**
   * @param reviewCount
   *          the reviewCount to set
   */
  public void setReviewCount(Long reviewCount) {
    this.reviewCount = reviewCount;
  }

  /**
   * @return the commodityDescriptionMobile
   */
  public String getCommodityDescriptionMobile() {
    return commodityDescriptionMobile;
  }

  /**
   * @param commodityDescriptionMobile
   *          the commodityDescriptionMobile to set
   */
  public void setCommodityDescriptionMobile(String commodityDescriptionMobile) {
    this.commodityDescriptionMobile = commodityDescriptionMobile;
  }

  /**
   * @return the detailNameEn
   */
  public String getDetailNameEn() {
    return detailNameEn;
  }

  /**
   * @param detailNameEn
   *          the detailNameEn to set
   */
  public void setDetailNameEn(String detailNameEn) {
    this.detailNameEn = detailNameEn;
  }

  /**
   * @return the detailUrlEn
   */
  public String getDetailUrlEn() {
    return detailUrlEn;
  }

  /**
   * @param detailUrlEn
   *          the detailUrlEn to set
   */
  public void setDetailUrlEn(String detailUrlEn) {
    this.detailUrlEn = detailUrlEn;
  }

  /**
   * @return the detailNameJp
   */
  public String getDetailNameJp() {
    return detailNameJp;
  }

  /**
   * @param detailNameJp
   *          the detailNameJp to set
   */
  public void setDetailNameJp(String detailNameJp) {
    this.detailNameJp = detailNameJp;
  }

  /**
   * @return the detailUrlJp
   */
  public String getDetailUrlJp() {
    return detailUrlJp;
  }

  /**
   * @param detailUrlJp
   *          the detailUrlJp to set
   */
  public void setDetailUrlJp(String detailUrlJp) {
    this.detailUrlJp = detailUrlJp;
  }

  public String getCommodityNameJp() {
    return commodityNameJp;
  }

  public void setCommodityNameJp(String commodityNameJp) {
    this.commodityNameJp = commodityNameJp;
  }

  public String getCommodityNameEn() {
    return commodityNameEn;
  }

  public void setCommodityNameEn(String commodityNameEn) {
    this.commodityNameEn = commodityNameEn;
  }

  public String getCommodityDescriptionMobileJp() {
    return commodityDescriptionMobileJp;
  }

  public void setCommodityDescriptionMobileJp(String commodityDescriptionMobileJp) {
    this.commodityDescriptionMobileJp = commodityDescriptionMobileJp;
  }

  public String getCommodityDescriptionMobileEn() {
    return commodityDescriptionMobileEn;
  }

  public void setCommodityDescriptionMobileEn(String commodityDescriptionMobileEn) {
    this.commodityDescriptionMobileEn = commodityDescriptionMobileEn;
  }

  
  /**
   * @return the originalCommodityCode
   */
  public String getOriginalCommodityCode() {
    return originalCommodityCode;
  }

  
  /**
   * @param originalCommodityCode the originalCommodityCode to set
   */
  public void setOriginalCommodityCode(String originalCommodityCode) {
    this.originalCommodityCode = originalCommodityCode;
  }

  
  /**
   * @return the combinationAmount
   */
  public Long getCombinationAmount() {
    return combinationAmount;
  }

  
  /**
   * @param combinationAmount the combinationAmount to set
   */
  public void setCombinationAmount(Long combinationAmount) {
    this.combinationAmount = combinationAmount;
  }

  
  /**
   * @return the avaQuantity
   */
  public Long getAvaQuantity() {
    return avaQuantity;
  }

  
  /**
   * @param avaQuantity the avaQuantity to set
   */
  public void setAvaQuantity(Long avaQuantity) {
    this.avaQuantity = avaQuantity;
  }

  
  /**
   * @return the campaignName
   */
  public String getCampaignName() {
    return campaignName;
  }

  
  /**
   * @param campaignName the campaignName to set
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

}
