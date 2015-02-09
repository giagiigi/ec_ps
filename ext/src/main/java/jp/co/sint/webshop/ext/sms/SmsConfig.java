package jp.co.sint.webshop.ext.sms;

import java.io.Serializable;

public class SmsConfig implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** 帐号 */
  private String account;

  /** 密码 */
  private String password;

  /** 发送短信地址 */
  private String sendUrl;
  
  /** 请求报告地址 */
  private String reportUrl;
  
  /**
   * @return the sendUrl
   */
  public String getSendUrl() {
    return sendUrl;
  }

  /**
   * @param sendUrl
   *          the sendUrl to set
   */
  public void setSendUrl(String sendUrl) {
    this.sendUrl = sendUrl;
  }

  /**
   * @return the account
   */
  public String getAccount() {
    return account;
  }

  /**
   * @param account
   *          the account to set
   */
  public void setAccount(String account) {
    this.account = account;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password
   *          the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the reportUrl
   */
  public String getReportUrl() {
    return reportUrl;
  }

  /**
   * @param reportUrl the reportUrl to set
   */
  public void setReportUrl(String reportUrl) {
    this.reportUrl = reportUrl;
  }

}
