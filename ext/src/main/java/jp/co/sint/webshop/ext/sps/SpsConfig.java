package jp.co.sint.webshop.ext.sps;

import java.io.Serializable;

/**
 * @author System Integrator Corp.
 */
public class SpsConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  private String purchaseRequestUrl;

  private String successUrl;

  private String cancelUrl;

  private String errorUrl;

  private String responseUrl;

  private int limitSecond;
  
  /**
   * purchaseRequestUrlを取得します。
   * 
   * @return purchaseRequestUrl purchaseRequestUrl
   */
  public String getPurchaseRequestUrl() {
    return purchaseRequestUrl;
  }

  /**
   * purchaseRequestUrlを設定します。
   * 
   * @param purchaseRequestUrl
   *          purchaseRequestUrl
   */
  public void setPurchaseRequestUrl(String purchaseRequestUrl) {
    this.purchaseRequestUrl = purchaseRequestUrl;
  }

  /**
   * successUrlを取得します。
   * 
   * @return successUrl successUrl
   */
  public String getSuccessUrl() {
    return successUrl;
  }

  /**
   * successUrlを設定します。
   * 
   * @param successUrl
   *          successUrl
   */
  public void setSuccessUrl(String successUrl) {
    this.successUrl = successUrl;
  }

  /**
   * cancelUrlを取得します。
   * 
   * @return cancelUrl cancelUrl
   */
  public String getCancelUrl() {
    return cancelUrl;
  }

  /**
   * cancelUrlを設定します。
   * 
   * @param cancelUrl
   *          cancelUrl
   */
  public void setCancelUrl(String cancelUrl) {
    this.cancelUrl = cancelUrl;
  }

  /**
   * errorUrlを取得します。
   * 
   * @return errorUrl errorUrl
   */
  public String getErrorUrl() {
    return errorUrl;
  }

  /**
   * errorUrlを設定します。
   * 
   * @param errorUrl
   *          errorUrl
   */
  public void setErrorUrl(String errorUrl) {
    this.errorUrl = errorUrl;
  }

  /**
   * responseUrlを取得します。
   * 
   * @return responseUrl responseUrl
   */
  public String getResponseUrl() {
    return responseUrl;
  }

  /**
   * responseUrlを設定します。
   * 
   * @param responseUrl
   *          responseUrl
   */
  public void setResponseUrl(String responseUrl) {
    this.responseUrl = responseUrl;
  }

  
  /**
   * limitSecondを取得します。
   *
   * @return limitSecond limitSecond
   */
  public int getLimitSecond() {
    return limitSecond;
  }

  
  /**
   * limitSecondを設定します。
   *
   * @param limitSecond 
   *          limitSecond
   */
  public void setLimitSecond(int limitSecond) {
    this.limitSecond = limitSecond;
  }

}
