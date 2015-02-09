package jp.co.sint.webshop.service.alipay;

import java.io.Serializable;

public class PaymentAlipayResultBean implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** 支付参数 */
  // 支付宝请求URL
  private String requestUrl;

  // 接口名称
  private String service;

  // 合作伙伴ID
  private String partner;

  // 卖家ID
  private String sellerId;

  // 参数编码字符集
  private String inputCharset;

  // 通知URL
  private String notifyUrl;

  // 签名
  private String sign;

  // 签名方式
  private String signType;

  // 返回URL
  private String returnUrl;

  // 外部交易号
  private String outTradeNo;

  // 支付类型
  private String paymentType;

  // 商品展示URL
  private String showUrl;

  // 商品描述
  private String body;

  // 交易金额
  private String totalFee;

  // 商品名称
  private String subject;

  // 超时时间
  private String itBPay;
  
  // 是否成功
  private String isSuccess;

  // 交易状态
  private String tradeStatus;

  // 付款时间
  private String gmtPayment;
  
  // 错误信息
  private String error;
  
  /**
   * @return the sellerId
   */
  public String getSellerId() {
    return sellerId;
  }

  /**
   * @param sellerId
   *          the sellerId to set
   */
  public void setSellerId(String sellerId) {
    this.sellerId = sellerId;
  }

  /**
   * @return the requestUrl
   */
  public String getRequestUrl() {
    return requestUrl;
  }

  /**
   * @param requestUrl
   *          the requestUrl to set
   */
  public void setRequestUrl(String requestUrl) {
    this.requestUrl = requestUrl;
  }

  /**
   * @return the service
   */
  public String getService() {
    return service;
  }

  /**
   * @param service
   *          the service to set
   */
  public void setService(String service) {
    this.service = service;
  }

  /**
   * @return the partner
   */
  public String getPartner() {
    return partner;
  }

  /**
   * @param partner
   *          the partner to set
   */
  public void setPartner(String partner) {
    this.partner = partner;
  }

  /**
   * @return the inputCharset
   */
  public String getInputCharset() {
    return inputCharset;
  }

  /**
   * @param inputCharset
   *          the inputCharset to set
   */
  public void setInputCharset(String inputCharset) {
    this.inputCharset = inputCharset;
  }

  /**
   * @return the notifyUrl
   */
  public String getNotifyUrl() {
    return notifyUrl;
  }

  /**
   * @param notifyUrl
   *          the notifyUrl to set
   */
  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }

  /**
   * @return the sign
   */
  public String getSign() {
    return sign;
  }

  /**
   * @param sign
   *          the sign to set
   */
  public void setSign(String sign) {
    this.sign = sign;
  }

  /**
   * @return the signType
   */
  public String getSignType() {
    return signType;
  }

  /**
   * @param signType
   *          the signType to set
   */
  public void setSignType(String signType) {
    this.signType = signType;
  }

  /**
   * @return the returnUrl
   */
  public String getReturnUrl() {
    return returnUrl;
  }

  /**
   * @param returnUrl
   *          the returnUrl to set
   */
  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }

  /**
   * @return the outTradeNo
   */
  public String getOutTradeNo() {
    return outTradeNo;
  }

  /**
   * @param outTradeNo
   *          the outTradeNo to set
   */
  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }

  /**
   * @return the paymentType
   */
  public String getPaymentType() {
    return paymentType;
  }

  /**
   * @param paymentType
   *          the paymentType to set
   */
  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  /**
   * @return the showUrl
   */
  public String getShowUrl() {
    return showUrl;
  }

  /**
   * @param showUrl
   *          the showUrl to set
   */
  public void setShowUrl(String showUrl) {
    this.showUrl = showUrl;
  }

  /**
   * @return the body
   */
  public String getBody() {
    return body;
  }

  /**
   * @param body
   *          the body to set
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * @return the totalFee
   */
  public String getTotalFee() {
    return totalFee;
  }

  /**
   * @param totalFee
   *          the totalFee to set
   */
  public void setTotalFee(String totalFee) {
    this.totalFee = totalFee;
  }

  /**
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * @param subject
   *          the subject to set
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * @return the itBPay
   */
  public String getItBPay() {
    return itBPay;
  }

  /**
   * @param itBPay
   *          the itBPay to set
   */
  public void setItBPay(String itBPay) {
    this.itBPay = itBPay;
  }

  /**
   * @return the isSuccess
   */
  public String getIsSuccess() {
    return isSuccess;
  }
  
  /**
   * @param isSuccess the isSuccess to set
   */
  public void setIsSuccess(String isSuccess) {
    this.isSuccess = isSuccess;
  }

  /**
   * @return the tradeStatus
   */
  public String getTradeStatus() {
    return tradeStatus;
  }
  
  /**
   * @param tradeStatus the tradeStatus to set
   */
  public void setTradeStatus(String tradeStatus) {
    this.tradeStatus = tradeStatus;
  }
  
  /**
   * @return the gmtPayment
   */
  public String getGmtPayment() {
    return gmtPayment;
  }
  
  /**
   * @param gmtPayment the gmtPayment to set
   */
  public void setGmtPayment(String gmtPayment) {
    this.gmtPayment = gmtPayment;
  }

  /**
   * @return the error
   */
  public String getError() {
    return error;
  }

  /**
   * @param error the error to set
   */
  public void setError(String error) {
    this.error = error;
  }
  
}
