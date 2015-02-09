package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

public class JdStockInfo implements Serializable {

  /**
   * Tmall库存分配
   */
  private static final long serialVersionUID = 1L;

  private String commodityCode;

  private String commodityName;

  private Long combinationAmount;

  private Long jdUseFlg;

  private Long stockQuantity;

  private Long allocatedQuantity;

  private Long scaleValue;

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
   * @return the combinationAmount
   */
  public Long getCombinationAmount() {
    return combinationAmount;
  }

  /**
   * @param combinationAmount
   *          the combinationAmount to set
   */
  public void setCombinationAmount(Long combinationAmount) {
    this.combinationAmount = combinationAmount;
  }

  /**
   * @return the tmallUseFlg
   */
  public Long getJdUseFlg() {
    return jdUseFlg;
  }

  /**
   * @param tmallUseFlg
   *          the tmallUseFlg to set
   */
  public void setJdUseFlg(Long jdUseFlg) {
    this.jdUseFlg = jdUseFlg;
  }

  /**
   * @return the stockQuantity
   */
  public Long getStockQuantity() {
    return stockQuantity;
  }

  /**
   * @param stockQuantity
   *          the stockQuantity to set
   */
  public void setStockQuantity(Long stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  /**
   * @return the allocatedQuantity
   */
  public Long getAllocatedQuantity() {
    return allocatedQuantity;
  }

  /**
   * @param allocatedQuantity
   *          the allocatedQuantity to set
   */
  public void setAllocatedQuantity(Long allocatedQuantity) {
    this.allocatedQuantity = allocatedQuantity;
  }

  /**
   * @return the scaleValue
   */
  public Long getScaleValue() {
    return scaleValue;
  }

  /**
   * @param scaleValue
   *          the scaleValue to set
   */
  public void setScaleValue(Long scaleValue) {
    this.scaleValue = scaleValue;
  }

}
