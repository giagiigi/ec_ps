package jp.co.sint.webshop.service.alipayaddress;

import java.io.Serializable;


public class AlipayAddressResultBean implements Serializable{

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
   
  private String address;
  
  private String receiveName;
  
  private String receiveZip;
  
  private String receivePhone;
  
  private String receiveMobile;
 
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
