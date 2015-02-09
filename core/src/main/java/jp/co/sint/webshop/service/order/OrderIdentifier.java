package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.utility.DateUtil;

public class OrderIdentifier implements Serializable {

  private static final long serialVersionUID = 1L;

  private String orderNo;

  private Date updatedDatetime;

  private String paymentReceiptNo;

  private BigDecimal paidPrice;

  public OrderIdentifier() {
  }

  public OrderIdentifier(String orderNo, Date updatedDatetime) {
    setOrderNo(orderNo);
    setUpdatedDatetime(updatedDatetime);
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
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * @param updatedDatetime
   *          the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * @return the paymentReceiptNo
   */
  public String getPaymentReceiptNo() {
    return paymentReceiptNo;
  }

  /**
   * @param paymentReceiptNo
   *          the paymentReceiptNo to set
   */
  public void setPaymentReceiptNo(String paymentReceiptNo) {
    this.paymentReceiptNo = paymentReceiptNo;
  }

  /**
   * @return the paidPrice
   */
  public BigDecimal getPaidPrice() {
    return paidPrice;
  }

  /**
   * @param paidPrice
   *          the paidPrice to set
   */
  public void setPaidPrice(BigDecimal paidPrice) {
    this.paidPrice = paidPrice;
  }

}
