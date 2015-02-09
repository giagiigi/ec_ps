package jp.co.sint.webshop.service.customer;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.utility.DateUtil;

/**
 * ポイント利用状況の検索結果
 */
public class OrderHistorySearchInfo implements Serializable {

  /**
   * uid
   */
  private static final long serialVersionUID = 1L;

  private String orderNo;

  private Date orderDatetime;

  private Date paymentDate;

  private String paymentMethodNo;

  private String totalPrice;

  private String orderStatus;

  private String customerCode;

  /**
   * @return customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @param customerCode
   *          設定する customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * @return orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * @param orderNo
   *          設定する orderNo
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * @return orderStatus
   */
  public String getOrderStatus() {
    return orderStatus;
  }

  /**
   * @param orderStatus
   *          設定する orderStatus
   */
  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  /**
   * @return orderDatetime
   */
  public Date getOrderDatetime() {
    return DateUtil.immutableCopy(orderDatetime);
  }

  /**
   * @param orderDatetime
   *          設定する orderDatetime
   */
  public void setOrderDatetime(Date orderDatetime) {
    this.orderDatetime = DateUtil.immutableCopy(orderDatetime);
  }

  /**
   * @return paymentDate
   */
  public Date getPaymentDate() {
    return DateUtil.immutableCopy(paymentDate);
  }

  /**
   * @param paymentDate
   *          設定する paymentDate
   */
  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = DateUtil.immutableCopy(paymentDate);
  }

  /**
   * @return paymentMethodNo
   */
  public String getPaymentMethodNo() {
    return paymentMethodNo;
  }

  /**
   * @param paymentMethodNo
   *          設定する paymentMethodNo
   */
  public void setPaymentMethodNo(String paymentMethodNo) {
    this.paymentMethodNo = paymentMethodNo;
  }

  /**
   * @return totalPrice
   */
  public String getTotalPrice() {
    return totalPrice;
  }

  /**
   * @param totalPrice
   *          設定する totalPrice
   */
  public void setTotalPrice(String totalPrice) {
    this.totalPrice = totalPrice;
  }
}
