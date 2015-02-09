package jp.co.sint.webshop.service.shop;

import java.io.Serializable;

public class CouponResearchInfo implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /* 发行金额 */
  private String issueTotalPrice;

  /* 使用金额 */
  private String usedTotalPrice;

  /* 未使用金额 */
  private String enableTotalPrice;

  /* 废弃金额 */
  private String disableTotalPrice;

  /* 过期金额 */
  private String overdueTotalPrice;

  /* 未激活金额 */
  private String phantomTotalPrice;

  public String getPhantomTotalPrice() {
    return phantomTotalPrice;
  }

  public void setPhantomTotalPrice(String phantomTotalPrice) {
    this.phantomTotalPrice = phantomTotalPrice;
  }

  public String getDisableTotalPrice() {
    return disableTotalPrice;
  }

  public void setDisableTotalPrice(String disableTotalPrice) {
    this.disableTotalPrice = disableTotalPrice;
  }

  public String getEnableTotalPrice() {
    return enableTotalPrice;
  }

  public void setEnableTotalPrice(String enableTotalPrice) {
    this.enableTotalPrice = enableTotalPrice;
  }

  public String getOverdueTotalPrice() {
    return overdueTotalPrice;
  }

  public void setOverdueTotalPrice(String overdueTotalPrice) {
    this.overdueTotalPrice = overdueTotalPrice;
  }

  public String getIssueTotalPrice() {
    return issueTotalPrice;
  }

  public void setIssueTotalPrice(String issueTotalPrice) {
    this.issueTotalPrice = issueTotalPrice;
  }

  public String getUsedTotalPrice() {
    return usedTotalPrice;
  }

  public void setUsedTotalPrice(String usedTotalPrice) {
    this.usedTotalPrice = usedTotalPrice;
  }

}
