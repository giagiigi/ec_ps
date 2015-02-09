package jp.co.sint.webshop.ext.alipayaddress;

import java.io.Serializable;

/**
 * @author kousen
 */
public class AlipayAddressConfig implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String signType = "MD5";

  private String inputCharset = "utf-8";

  /** 支付宝支付URL */
  private String requestUrl;

  /** 通知URL */
  private String notifyUrl;

  /** 返回URL */
  private String returnUrl;

  /** 错误信息返回URL */
  private String errorNotifyUrl;

  /** 超时时间 */
  private String itBPay;


  private String showUrl;

  
  private String address ;//收货地址
  
  private String receiveName ;
  
  private String receiveZip ;
  
  private String receivePhone ;
  
  private String receiveMobile ;
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
   * @return the errorNotifyUrl
   */
  public String getErrorNotifyUrl() {
    return errorNotifyUrl;
  }

  /**
   * @param errorNotifyUrl
   *          the errorNotifyUrl to set
   */
  public void setErrorNotifyUrl(String errorNotifyUrl) {
    this.errorNotifyUrl = errorNotifyUrl;
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
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param receiveName the receiveName to set
   */
  public void setReceiveName(String receiveName) {
    this.receiveName = receiveName;
  }

  /**
   * @return the receiveName
   */
  public String getReceiveName() {
    return receiveName;
  }

  /**
   * @param receiveZip the receiveZip to set
   */
  public void setReceiveZip(String receiveZip) {
    this.receiveZip = receiveZip;
  }

  /**
   * @return the receiveZip
   */
  public String getReceiveZip() {
    return receiveZip;
  }

  /**
   * @param receivePhone the receivePhone to set
   */
  public void setReceivePhone(String receivePhone) {
    this.receivePhone = receivePhone;
  }

  /**
   * @return the receivePhone
   */
  public String getReceivePhone() {
    return receivePhone;
  }

  /**
   * @param receiveMobile the receiveMobile to set
   */
  public void setReceiveMobile(String receiveMobile) {
    this.receiveMobile = receiveMobile;
  }

  /**
   * @return the receiveMobile
   */
  public String getReceiveMobile() {
    return receiveMobile;
  }

}
