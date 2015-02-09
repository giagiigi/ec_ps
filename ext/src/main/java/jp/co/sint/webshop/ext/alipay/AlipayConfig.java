package jp.co.sint.webshop.ext.alipay;

import java.io.Serializable;

/**
 * @author kousen
 */
public class AlipayConfig implements Serializable {

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

  private String subject;

  private String showUrl;

  private String body;

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

}
