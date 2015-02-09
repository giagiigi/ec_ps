package jp.co.sint.webshop.service.customer;

import java.io.Serializable;
import java.math.BigDecimal;

public class MemberShippingHistory implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 订单编号 */
  private String orderNo;

  /** 发货编号 */
  private String shippingNo;

  /** 订单日 */
  private String orderDatetime;

  /** 付款日 */
  private String paymentDate;

  /** 支付区分 */
  private String paymentMethodType;

  /** 支付方式 */
  private String paymentMethodName;

  /** 订单金额 */
  private String totalAmount;
  
  private String giftCardUsePrice;

  /** 发货状况 */
  private String shippingStatus;

  /** 配送方式 */
  private String deliveryTypeName;

  /** 运单号 */
  private String deliverySlipNo;

  /** 发货指示日 */
  private String shippingDirectDate;

  /** 发货日 */
  private String shippingDate;

  /** 预计送达日 */
  private String arrivalDate;

  /** 区分 */
  private String orderStatus;

  /** 退货的订单编号 */
  private String returnOrderNo;

  /** 销售确定状态 */
  private String fixedSalesStatus;

  /** 配送公司 */
  private String deliveryCompanyName;
  
  private BigDecimal paymentCommission;

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
   * @return the shippingNo
   */
  public String getShippingNo() {
    return shippingNo;
  }

  /**
   * @param shippingNo
   *          the shippingNo to set
   */
  public void setShippingNo(String shippingNo) {
    this.shippingNo = shippingNo;
  }

  /**
   * @return the orderDatetime
   */
  public String getOrderDatetime() {
    return orderDatetime;
  }

  /**
   * @param orderDatetime
   *          the orderDatetime to set
   */
  public void setOrderDatetime(String orderDatetime) {
    this.orderDatetime = orderDatetime;
  }

  /**
   * @return the paymentDate
   */
  public String getPaymentDate() {
    return paymentDate;
  }

  /**
   * @param paymentDate
   *          the paymentDate to set
   */
  public void setPaymentDate(String paymentDate) {
    this.paymentDate = paymentDate;
  }

  /**
   * @return the paymentMethodName
   */
  public String getPaymentMethodName() {
    return paymentMethodName;
  }

  /**
   * @param paymentMethodName
   *          the paymentMethodName to set
   */
  public void setPaymentMethodName(String paymentMethodName) {
    this.paymentMethodName = paymentMethodName;
  }

  /**
   * @return the totalAmount
   */
  public String getTotalAmount() {
    return totalAmount;
  }

  /**
   * @param totalAmount
   *          the totalAmount to set
   */
  public void setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
  }

  /**
   * @return the shippingStatus
   */
  public String getShippingStatus() {
    return shippingStatus;
  }

  /**
   * @param shippingStatus
   *          the shippingStatus to set
   */
  public void setShippingStatus(String shippingStatus) {
    this.shippingStatus = shippingStatus;
  }

  /**
   * @return the deliveryTypeName
   */
  public String getDeliveryTypeName() {
    return deliveryTypeName;
  }

  /**
   * @param deliveryTypeName
   *          the deliveryTypeName to set
   */
  public void setDeliveryTypeName(String deliveryTypeName) {
    this.deliveryTypeName = deliveryTypeName;
  }

  /**
   * @return the deliverySlipNo
   */
  public String getDeliverySlipNo() {
    return deliverySlipNo;
  }

  /**
   * @param deliverySlipNo
   *          the deliverySlipNo to set
   */
  public void setDeliverySlipNo(String deliverySlipNo) {
    this.deliverySlipNo = deliverySlipNo;
  }

  /**
   * @return the shippingDirectDate
   */
  public String getShippingDirectDate() {
    return shippingDirectDate;
  }

  /**
   * @param shippingDirectDate
   *          the shippingDirectDate to set
   */
  public void setShippingDirectDate(String shippingDirectDate) {
    this.shippingDirectDate = shippingDirectDate;
  }

  /**
   * @return the shippingDate
   */
  public String getShippingDate() {
    return shippingDate;
  }

  /**
   * @param shippingDate
   *          the shippingDate to set
   */
  public void setShippingDate(String shippingDate) {
    this.shippingDate = shippingDate;
  }

  /**
   * @return the arrivalDate
   */
  public String getArrivalDate() {
    return arrivalDate;
  }

  /**
   * @param arrivalDate
   *          the arrivalDate to set
   */
  public void setArrivalDate(String arrivalDate) {
    this.arrivalDate = arrivalDate;
  }

  /**
   * @return the orderStatus
   */
  public String getOrderStatus() {
    return orderStatus;
  }

  /**
   * @param orderStatus
   *          the orderStatus to set
   */
  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  /**
   * @return the paymentMethodType
   */
  public String getPaymentMethodType() {
    return paymentMethodType;
  }

  /**
   * @param paymentMethodType
   *          the paymentMethodType to set
   */
  public void setPaymentMethodType(String paymentMethodType) {
    this.paymentMethodType = paymentMethodType;
  }

  /**
   * @return the returnOrderNo
   */
  public String getReturnOrderNo() {
    return returnOrderNo;
  }

  /**
   * @param returnOrderNo
   *          the returnOrderNo to set
   */
  public void setReturnOrderNo(String returnOrderNo) {
    this.returnOrderNo = returnOrderNo;
  }

  /**
   * @return the fixedSalesStatus
   */
  public String getFixedSalesStatus() {
    return fixedSalesStatus;
  }

  /**
   * @param fixedSalesStatus
   *          the fixedSalesStatus to set
   */
  public void setFixedSalesStatus(String fixedSalesStatus) {
    this.fixedSalesStatus = fixedSalesStatus;
  }

  public String getDeliveryCompanyName() {
    return deliveryCompanyName;
  }

  public void setDeliveryCompanyName(String deliveryCompanyName) {
    this.deliveryCompanyName = deliveryCompanyName;
  }

  
  /**
   * @return the giftCardUsePrice
   */
  public String getGiftCardUsePrice() {
    return giftCardUsePrice;
  }

  
  /**
   * @param giftCardUsePrice the giftCardUsePrice to set
   */
  public void setGiftCardUsePrice(String giftCardUsePrice) {
    this.giftCardUsePrice = giftCardUsePrice;
  }

  
  /**
   * @return the paymentCommission
   */
  public BigDecimal getPaymentCommission() {
    return paymentCommission;
  }

  
  /**
   * @param paymentCommission the paymentCommission to set
   */
  public void setPaymentCommission(BigDecimal paymentCommission) {
    this.paymentCommission = paymentCommission;
  }

}
