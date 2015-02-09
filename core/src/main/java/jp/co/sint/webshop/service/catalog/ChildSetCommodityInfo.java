package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ChildSetCommodityInfo implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private String commodityCode;

  private String commodityName;

  private String commodityNameEn;

  private String commodityNameJp;

  private String representSkuCode;

  private Long commodityTaxType;

  private Date saleStartDatetime;

  private Date saleEndDatetime;

  private Long saleFlg;

  private Date discountPriceStartDatetime;

  private Date discountPriceEndDatetime;

  private BigDecimal unitPrice;

  private BigDecimal representSkuUnitPrice;

  private BigDecimal discountPrice;

  public BigDecimal getRepresentSkuUnitPrice() {
    return representSkuUnitPrice;
  }

  public void setRepresentSkuUnitPrice(BigDecimal representSkuUnitPrice) {
    this.representSkuUnitPrice = representSkuUnitPrice;
  }

  public String getCommodityCode() {
    return commodityCode;
  }

  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  public String getCommodityName() {
    return commodityName;
  }

  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  public String getCommodityNameEn() {
    return commodityNameEn;
  }

  public void setCommodityNameEn(String commodityNameEn) {
    this.commodityNameEn = commodityNameEn;
  }

  public String getCommodityNameJp() {
    return commodityNameJp;
  }

  public void setCommodityNameJp(String commodityNameJp) {
    this.commodityNameJp = commodityNameJp;
  }

  public String getRepresentSkuCode() {
    return representSkuCode;
  }

  public void setRepresentSkuCode(String representSkuCode) {
    this.representSkuCode = representSkuCode;
  }

  public Long getCommodityTaxType() {
    return commodityTaxType;
  }

  public void setCommodityTaxType(Long commodityTaxType) {
    this.commodityTaxType = commodityTaxType;
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

  public Long getSaleFlg() {
    return saleFlg;
  }

  public void setSaleFlg(Long saleFlg) {
    this.saleFlg = saleFlg;
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

}
