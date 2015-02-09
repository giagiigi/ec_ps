package jp.co.sint.webshop.service.communication;

import java.io.Serializable;

public class PropagandaActivityCommodityInfo implements Serializable {

  /**
   * 宣传品关联商品信息
   */
  private static final long serialVersionUID = 1L;

  private String commodityCode;

  private String commodityName;

  private Long purchasingAmount;

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

}
