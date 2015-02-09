package jp.co.sint.webshop.service;

import java.io.Serializable;
import java.math.BigDecimal;

public class OptionalCommodity implements Serializable {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 商品コード */
  private String commodityCode;
  
  private String optionalCode;
  
  private String optionalNameCn;
  
  private String optionalNameJp;
  
  private String optionalNameEn;
  
  private BigDecimal realPrice;
  
  private BigDecimal cheapPrice;
  
  private Long commodityAmount;

  
  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  
  /**
   * @param commodityCode the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  
  /**
   * @return the optionalCode
   */
  public String getOptionalCode() {
    return optionalCode;
  }

  
  /**
   * @param optionalCode the optionalCode to set
   */
  public void setOptionalCode(String optionalCode) {
    this.optionalCode = optionalCode;
  }

  
  /**
   * @return the optionalNameCn
   */
  public String getOptionalNameCn() {
    return optionalNameCn;
  }

  
  /**
   * @param optionalNameCn the optionalNameCn to set
   */
  public void setOptionalNameCn(String optionalNameCn) {
    this.optionalNameCn = optionalNameCn;
  }

  
  /**
   * @return the optionalNameJp
   */
  public String getOptionalNameJp() {
    return optionalNameJp;
  }

  
  /**
   * @param optionalNameJp the optionalNameJp to set
   */
  public void setOptionalNameJp(String optionalNameJp) {
    this.optionalNameJp = optionalNameJp;
  }

  
  /**
   * @return the optionalNameEn
   */
  public String getOptionalNameEn() {
    return optionalNameEn;
  }

  
  /**
   * @param optionalNameEn the optionalNameEn to set
   */
  public void setOptionalNameEn(String optionalNameEn) {
    this.optionalNameEn = optionalNameEn;
  }

  
  /**
   * @return the realPrice
   */
  public BigDecimal getRealPrice() {
    return realPrice;
  }

  
  /**
   * @param realPrice the realPrice to set
   */
  public void setRealPrice(BigDecimal realPrice) {
    this.realPrice = realPrice;
  }

  
  /**
   * @return the cheapPrice
   */
  public BigDecimal getCheapPrice() {
    return cheapPrice;
  }

  
  /**
   * @param cheapPrice the cheapPrice to set
   */
  public void setCheapPrice(BigDecimal cheapPrice) {
    this.cheapPrice = cheapPrice;
  }

  
  /**
   * @return the commodityAmount
   */
  public Long getCommodityAmount() {
    return commodityAmount;
  }

  
  /**
   * @param commodityAmount the commodityAmount to set
   */
  public void setCommodityAmount(Long commodityAmount) {
    this.commodityAmount = commodityAmount;
  }
  
  



}
