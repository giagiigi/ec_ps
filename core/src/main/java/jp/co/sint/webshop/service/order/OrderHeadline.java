package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.utility.DateUtil;

public class OrderHeadline implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String orderNo;

  private String shopCode;
  
  private String addressLastName;

  private String guestFlg;

  private String customerName;

  private String caution;

  private String message;

  private String phoneNumber;

  // Add by V10-CH start
  private String mobileNumber;

  // Add by V10-CH end

  private String orderDatetime;

  private String paymentDate;

  private Long paymentMethodNo;

  private String paymentMethodName;

  private String paymentMethodType;

  private String shippingStatusSummary;

  private String returnStatusSummary;

  private String totalAmount;

  private String orderStatus;

  private Date updatedDatetime;

  private BigDecimal usedPoint;

  private BigDecimal paymentCommission;

  private Date paymentLimitDate;

  // 20120120 ysy add start
  private String discountPrice;

  // 20120120 ysy add end
  // soukai add 2011/12/29 ob start
  // 受注タイプ
  private String orderType;

  private String mobileComputerType;

  private String tmallTid;

  // 订单检查
  private String orderFlg;

  private String languageCode;

  private String tmallBuyerMessage;
  
  private String jdBuyerMessage;
  
  private String BuyerMessage;
  
  private String BuyId;

  private String deliverySlipNo;
  
  private BigDecimal giftCardUsePrice;
  
  private BigDecimal outerCardUsePrice;

  // soukai add 2011/12/29 ob end

  /**
   * paymentLimitDateを取得します。
   * 
   * @return paymentLimitDate
   */
  public Date getPaymentLimitDate() {
    return DateUtil.immutableCopy(paymentLimitDate);
  }

  public String getMobileComputerType() {
    return mobileComputerType;
  }

  public void setMobileComputerType(String mobileComputerType) {
    this.mobileComputerType = mobileComputerType;
  }

  public String getTmallTid() {
    return tmallTid;
  }

  public void setTmallTid(String tmallTid) {
    this.tmallTid = tmallTid;
  }

  public String getDeliverySlipNo() {
    return deliverySlipNo;
  }

  public void setDeliverySlipNo(String deliverySlipNo) {
    this.deliverySlipNo = deliverySlipNo;
  }

  /**
   * @return the discountPrice
   */
  public String getDiscountPrice() {
    return discountPrice;
  }

  /**
   * @param discountPrice
   *          the discountPrice to set
   */
  public void setDiscountPrice(String discountPrice) {
    this.discountPrice = discountPrice;
  }

  /**
   * paymentLimitDateを設定します。
   * 
   * @param paymentLimitDate
   *          paymentLimitDate
   */
  public void setPaymentLimitDate(Date paymentLimitDate) {
    this.paymentLimitDate = DateUtil.immutableCopy(paymentLimitDate);
  }

  /**
   * usedPointを返します。
   * 
   * @return the usedPoint
   */
  public BigDecimal getUsedPoint() {
    return usedPoint;
  }

  /**
   * usedPointを設定します。
   * 
   * @param usedPoint
   *          設定する usedPoint
   */
  public void setUsedPoint(BigDecimal usedPoint) {
    this.usedPoint = usedPoint;
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
   * @return the shippingStatusSummary
   */
  public String getShippingStatusSummary() {
    return shippingStatusSummary;
  }

  /**
   * @param shippingStatusSummary
   *          the shippingStatusSummary to set
   */
  public void setShippingStatusSummary(String shippingStatusSummary) {
    this.shippingStatusSummary = shippingStatusSummary;
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
   * @return the caution
   */
  public String getCaution() {
    return caution;
  }

  /**
   * @param caution
   *          the caution to set
   */
  public void setCaution(String caution) {
    this.caution = caution;
  }

  /**
   * @return the customerName
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * @param customerName
   *          the customerName to set
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
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
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @param phoneNumber
   *          the phoneNumber to set
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * @return the paymentMethodNo
   */
  public Long getPaymentMethodNo() {
    return paymentMethodNo;
  }

  /**
   * @param paymentMethodNo
   *          the paymentMethodNo to set
   */
  public void setPaymentMethodNo(Long paymentMethodNo) {
    this.paymentMethodNo = paymentMethodNo;
  }

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message
   *          the message to set
   */
  public void setMessage(String message) {
    this.message = message;
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
   * @return the returnStatusSummary
   */
  public String getReturnStatusSummary() {
    return returnStatusSummary;
  }

  /**
   * @param returnStatusSummary
   *          the returnStatusSummary to set
   */
  public void setReturnStatusSummary(String returnStatusSummary) {
    this.returnStatusSummary = returnStatusSummary;
  }

  /**
   * paymentCommissionを返します。
   * 
   * @return the paymentCommission
   */
  public BigDecimal getPaymentCommission() {
    return paymentCommission;
  }

  /**
   * paymentCommissionを設定します。
   * 
   * @param paymentCommission
   *          設定する paymentCommission
   */
  public void setPaymentCommission(BigDecimal paymentCommission) {
    this.paymentCommission = paymentCommission;
  }

  /**
   * guestFlgを取得します。
   * 
   * @return guestFlg
   */
  public String getGuestFlg() {
    return guestFlg;
  }

  /**
   * guestFlgを設定します。
   * 
   * @param guestFlg
   *          guestFlg
   */
  public void setGuestFlg(String guestFlg) {
    this.guestFlg = guestFlg;
  }

  /**
   * mobileNumberを取得します。
   * 
   * @return mobileNumber mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  /**
   * mobileNumberを設定します。
   * 
   * @param mobileNumber
   *          mobileNumber
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  // soukai add 2011/12/29 ob start
  /**
   * 受注タイプを取得する
   * 
   * @return the orderType
   */
  public String getOrderType() {
    return orderType;
  }

  /**
   * 受注タイプを設定する
   * 
   * @param orderType
   *          the orderType to set
   */
  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  // soukai add 2011/12/29 ob end

  /**
   * @return the orderFlg
   */
  public String getOrderFlg() {
    return orderFlg;
  }

  /**
   * @param orderFlg
   *          the orderFlg to set
   */
  public void setOrderFlg(String orderFlg) {
    this.orderFlg = orderFlg;
  }

  /**
   * @return the tmallBuyerMessage
   */
  public String getTmallBuyerMessage() {
    return tmallBuyerMessage;
  }

  /**
   * @param tmallBuyerMessage
   *          the tmallBuyerMessage to set
   */
  public void setTmallBuyerMessage(String tmallBuyerMessage) {
    this.tmallBuyerMessage = tmallBuyerMessage;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  
  /**
   * @return the giftCardUsePrice
   */
  public BigDecimal getGiftCardUsePrice() {
    return giftCardUsePrice;
  }

  
  /**
   * @param giftCardUsePrice the giftCardUsePrice to set
   */
  public void setGiftCardUsePrice(BigDecimal giftCardUsePrice) {
    this.giftCardUsePrice = giftCardUsePrice;
  }

  
  /**
   * @return the outerCardUsePrice
   */
  public BigDecimal getOuterCardUsePrice() {
    return outerCardUsePrice;
  }

  
  /**
   * @param outerCardUsePrice the outerCardUsePrice to set
   */
  public void setOuterCardUsePrice(BigDecimal outerCardUsePrice) {
    this.outerCardUsePrice = outerCardUsePrice;
  }

  
  /**
   * @return the buyerMessage
   */
  public String getBuyerMessage() {
    return BuyerMessage;
  }

  
  /**
   * @return the buyId
   */
  public String getBuyId() {
    return BuyId;
  }

  
  /**
   * @param buyerMessage the buyerMessage to set
   */
  public void setBuyerMessage(String buyerMessage) {
    BuyerMessage = buyerMessage;
  }

  
  /**
   * @param buyId the buyId to set
   */
  public void setBuyId(String buyId) {
    BuyId = buyId;
  }

  
  /**
   * @return the jdBuyerMessage
   */
  public String getJdBuyerMessage() {
    return jdBuyerMessage;
  }

  
  /**
   * @param jdBuyerMessage the jdBuyerMessage to set
   */
  public void setJdBuyerMessage(String jdBuyerMessage) {
    this.jdBuyerMessage = jdBuyerMessage;
  }

  
  /**
   * @return the addressLastName
   */
  public String getAddressLastName() {
    return addressLastName;
  }

  
  /**
   * @param addressLastName the addressLastName to set
   */
  public void setAddressLastName(String addressLastName) {
    this.addressLastName = addressLastName;
  }

}
