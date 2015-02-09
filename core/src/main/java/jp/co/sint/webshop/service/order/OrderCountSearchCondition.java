package jp.co.sint.webshop.service.order;

import jp.co.sint.webshop.service.SearchCondition;

public class OrderCountSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String paymentStatus;

  private String orderStatus;

  private String paymentLimitDate;

  private String shopCode;

  private String orderDatetime;

  /**
   * orderStatusを取得します。
   * 
   * @return orderStatus
   */
  public String getOrderStatus() {
    return orderStatus;
  }

  /**
   * orderStatusを設定します。
   * 
   * @param orderStatus
   *          orderStatus
   */
  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  /**
   * paymentStatusを取得します。
   * 
   * @return paymentStatus
   */
  public String getPaymentStatus() {
    return paymentStatus;
  }

  /**
   * paymentStatusを設定します。
   * 
   * @param paymentStatus
   *          paymentStatus
   */
  public void setPaymentStatus(String paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  /**
   * paymentLimitDateを取得します。
   * 
   * @return paymentLimitDate
   */
  public String getPaymentLimitDate() {
    return paymentLimitDate;
  }

  /**
   * paymentLimitDateを設定します。
   * 
   * @param paymentLimitDate
   *          paymentLimitDate
   */
  public void setPaymentLimitDate(String paymentLimitDate) {
    this.paymentLimitDate = paymentLimitDate;
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
   * orderDatetimeを取得します。
   * 
   * @return orderDatetime
   */
  public String getOrderDatetime() {
    return orderDatetime;
  }

  /**
   * orderDatetimeを設定します。
   * 
   * @param orderDatetime
   *          orderDatetime
   */
  public void setOrderDatetime(String orderDatetime) {
    this.orderDatetime = orderDatetime;
  }

}
