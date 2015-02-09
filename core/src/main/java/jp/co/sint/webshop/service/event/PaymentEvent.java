package jp.co.sint.webshop.service.event;

import java.util.Date;

import jp.co.sint.webshop.utility.DateUtil;

public class PaymentEvent implements ServiceEvent {

  private static final long serialVersionUID = 1L;

  private String orderNo;

  private Date paymentDate;

  public PaymentEvent() {
  }

  public PaymentEvent(String orderNo) {
    setOrderNo(orderNo);
  }

  public PaymentEvent(String orderNo, Date paymentDate) {
    setOrderNo(orderNo);
    setPaymentDate(paymentDate);
  }

  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * @param orderNo
   *          the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * @return the paymentDate
   */
  public Date getPaymentDate() {
    return DateUtil.immutableCopy(paymentDate);
  }

  /**
   * @param paymentDate
   *          the paymentDate to set
   */
  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = DateUtil.immutableCopy(paymentDate);
  }

}
