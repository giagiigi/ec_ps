package jp.co.sint.webshop.service.communication;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.utility.DateUtil;

public class DiscountHeadLine implements Serializable {

  /** uid */
  private static final long serialVersionUID = 1L;

  private String discountCode;

  private String discountName;

  private Date discountStartDatetime;

  private Date discountEndDatetime;

  private Long commodityCount;

  /**
   * @return the discountCode
   */
  public String getDiscountCode() {
    return discountCode;
  }

  /**
   * @return the discountName
   */
  public String getDiscountName() {
    return discountName;
  }

  /**
   * @return the discountStartDatetime
   */
  public Date getDiscountStartDatetime() {
    return DateUtil.immutableCopy(discountStartDatetime);
  }

  /**
   * @return the discountEndDatetime
   */
  public Date getDiscountEndDatetime() {
    return DateUtil.immutableCopy(discountEndDatetime);
  }

  /**
   * @return the commodityCount
   */
  public Long getCommodityCount() {
    return commodityCount;
  }

  /**
   * @param discountCode
   *          the discountCode to set
   */
  public void setDiscountCode(String discountCode) {
    this.discountCode = discountCode;
  }

  /**
   * @param discountName
   *          the discountName to set
   */
  public void setDiscountName(String discountName) {
    this.discountName = discountName;
  }

  /**
   * @param discountStartDatetime
   *          the discountStartDatetime to set
   */
  public void setDiscountStartDatetime(Date discountStartDatetime) {
    this.discountStartDatetime = DateUtil.immutableCopy(discountStartDatetime);
  }

  /**
   * @param discountEndDatetime
   *          the discountEndDatetime to set
   */
  public void setDiscountEndDatetime(Date discountEndDatetime) {
    this.discountEndDatetime = DateUtil.immutableCopy(discountEndDatetime);
  }

  /**
   * @param commodityCount
   *          the commodityCount to set
   */
  public void setCommodityCount(Long commodityCount) {
    this.commodityCount = commodityCount;
  }

}
