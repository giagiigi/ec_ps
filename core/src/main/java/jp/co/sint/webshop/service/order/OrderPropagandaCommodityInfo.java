package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderPropagandaCommodityInfo implements Serializable {

  /**
   * 订单宣传品信息
   */
  private static final long serialVersionUID = 1L;

  private String commodityCode;

  private String commodityName;

  private Long purchasingAmount;

  private BigDecimal commodityWeight;

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * @return the purchasingAmount
   */
  public Long getPurchasingAmount() {
    return purchasingAmount;
  }

  /**
   * @return the commodityWeight
   */
  public BigDecimal getCommodityWeight() {
    return commodityWeight;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @param commodityName
   *          the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * @param purchasingAmount
   *          the purchasingAmount to set
   */
  public void setPurchasingAmount(Long purchasingAmount) {
    this.purchasingAmount = purchasingAmount;
  }

  /**
   * @param commodityWeight
   *          the commodityWeight to set
   */
  public void setCommodityWeight(BigDecimal commodityWeight) {
    this.commodityWeight = commodityWeight;
  }

}
