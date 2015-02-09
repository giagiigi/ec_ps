package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SalesChartsData implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String categoryCode;

  private String categoryNamePc;

  private String commodityCode;

  private String commodityName;

  // 20120522 tuxinwei add start
  private String categoryNamePcJp;

  private String categoryNamePcEn;

  private String commodityNameJp;

  private String commodityNameEn;

  // 20120522 tuxinwei add end

  private BigDecimal unitPrice;

  private BigDecimal discountPrice;

  private BigDecimal reservationPrice;

  private String commodityTaxType;

  private String campaignDiscountRate;

  private Date saleStartDatetime;

  private Date saleEndDatetime;

  private Date discountPriceStartDatetime;

  private Date discountPriceEndDatetime;

  // 20130321 add by yyq start 新增区分
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

  // 20130321 add by yyq end 新增区分

  /**
   * @return the importCommodityType
   */
  public Long getImportCommodityType() {
    return importCommodityType;
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

  public Date getSaleStartDatetime() {
    return saleStartDatetime;
  }

  public void setSaleStartDatetime(Date saleStartDatetime) {
    this.saleStartDatetime = saleStartDatetime;
  }

  public Date getSaleEndDatetime() {
    return saleEndDatetime;
  }

  public void setSaleEndDatetime(Date saleEndDatetime) {
    this.saleEndDatetime = saleEndDatetime;
  }

  public Date getDiscountPriceStartDatetime() {
    return discountPriceStartDatetime;
  }

  public void setDiscountPriceStartDatetime(Date discountPriceStartDatetime) {
    this.discountPriceStartDatetime = discountPriceStartDatetime;
  }

  public Date getDiscountPriceEndDatetime() {
    return discountPriceEndDatetime;
  }

  public void setDiscountPriceEndDatetime(Date discountPriceEndDatetime) {
    this.discountPriceEndDatetime = discountPriceEndDatetime;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
  }

  public BigDecimal getReservationPrice() {
    return reservationPrice;
  }

  public void setReservationPrice(BigDecimal reservationPrice) {
    this.reservationPrice = reservationPrice;
  }

  public String getCommodityTaxType() {
    return commodityTaxType;
  }

  public void setCommodityTaxType(String commodityTaxType) {
    this.commodityTaxType = commodityTaxType;
  }

  public String getCampaignDiscountRate() {
    return campaignDiscountRate;
  }

  public void setCampaignDiscountRate(String campaignDiscountRate) {
    this.campaignDiscountRate = campaignDiscountRate;
  }

  /**
   * @return the categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * @param categoryCode
   *          the categoryCode to set
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  /**
   * @return the categoryNamePc
   */
  public String getCategoryNamePc() {
    return categoryNamePc;
  }

  /**
   * @param categoryNamePc
   *          the categoryNamePc to set
   */
  public void setCategoryNamePc(String categoryNamePc) {
    this.categoryNamePc = categoryNamePc;
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
   * @return the categoryNamePcJp
   */
  public String getCategoryNamePcJp() {
    return categoryNamePcJp;
  }

  /**
   * @param categoryNamePcJp
   *          the categoryNamePcJp to set
   */
  public void setCategoryNamePcJp(String categoryNamePcJp) {
    this.categoryNamePcJp = categoryNamePcJp;
  }

  /**
   * @return the commodityNameJp
   */
  public String getCommodityNameJp() {
    return commodityNameJp;
  }

  /**
   * @param commodityNameJp
   *          the commodityNameJp to set
   */
  public void setCommodityNameJp(String commodityNameJp) {
    this.commodityNameJp = commodityNameJp;
  }

  /**
   * @return the commodityNameEn
   */
  public String getCommodityNameEn() {
    return commodityNameEn;
  }

  /**
   * @param commodityNameEn
   *          the commodityNameEn to set
   */
  public void setCommodityNameEn(String commodityNameEn) {
    this.commodityNameEn = commodityNameEn;
  }

  /**
   * @param categoryNamePcEn
   *          the categoryNamePcEn to set
   */
  public void setCategoryNamePcEn(String categoryNamePcEn) {
    this.categoryNamePcEn = categoryNamePcEn;
  }

  /**
   * @return the categoryNamePcEn
   */
  public String getCategoryNamePcEn() {
    return categoryNamePcEn;
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

}
