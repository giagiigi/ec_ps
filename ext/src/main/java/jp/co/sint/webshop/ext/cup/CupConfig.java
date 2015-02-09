package jp.co.sint.webshop.ext.cup;

import java.io.Serializable;

/**
 * @author System Integrator Corp.
 */
public class CupConfig implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private CupMerchant chinapayMerchant;

  private String backReturnUrl;

  private String pageReturnUrl;

  private String purchaseRequestUrl;

  private String refundRequestUrl;

  private String currencyId;

  private String countryId;

  private String version;

  private String timeZone;

  private String dstFlag;

  private String extFlag;

  private String gateId;
  //20111206 lirong add start
  private String requestURL;
  
  private String resv;
  //20111206 lirong add end
  /**
   * chinapayMerchantを取得します。
   * 
   * @return chinapayMerchant chinapayMerchant
   */
  public CupMerchant getChinapayMerchant() {
    return chinapayMerchant;
  }

  /**
   * chinapayMerchantを設定します。
   * 
   * @param chinapayMerchant
   *          chinapayMerchant
   */
  public void setChinapayMerchant(CupMerchant chinapayMerchant) {
    this.chinapayMerchant = chinapayMerchant;
  }

  /**
   * backReturnUrlを取得します。
   * 
   * @return backReturnUrl backReturnUrl
   */
  public String getBackReturnUrl() {
    return backReturnUrl;
  }

  /**
   * backReturnUrlを設定します。
   * 
   * @param backReturnUrl
   *          backReturnUrl
   */
  public void setBackReturnUrl(String backReturnUrl) {
    this.backReturnUrl = backReturnUrl;
  }

  /**
   * pageReturnUrlを返します。
   * 
   * @return pageReturnUrl
   */
  public String getPageReturnUrl() {
    return pageReturnUrl;
  }

  /**
   * pageReturnUrlを設定します。
   * 
   * @param pageReturnUrl
   *          pageReturnUrl
   */
  public void setPageReturnUrl(String pageReturnUrl) {
    this.pageReturnUrl = pageReturnUrl;
  }

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
   * refundRequestUrlを取得します。
   * 
   * @return refundRequestUrl refundRequestUrl
   */
  public String getRefundRequestUrl() {
    return refundRequestUrl;
  }

  /**
   * refundRequestUrlを設定します。
   * 
   * @param refundRequestUrl
   *          refundRequestUrl
   */
  public void setRefundRequestUrl(String refundRequestUrl) {
    this.refundRequestUrl = refundRequestUrl;
  }

  /**
   * currencyIdを取得します。
   * 
   * @return currencyId currencyId
   */
  public String getCurrencyId() {
    return currencyId;
  }

  /**
   * currencyIdを設定します。
   * 
   * @param currencyId
   *          currencyId
   */
  public void setCurrencyId(String currencyId) {
    this.currencyId = currencyId;
  }

  /**
   * countryIdを取得します。
   * 
   * @return countryId countryId
   */
  public String getCountryId() {
    return countryId;
  }

  /**
   * countryIdを設定します。
   * 
   * @param countryId
   *          countryId
   */
  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  /**
   * versionを取得します。
   * 
   * @return version version
   */
  public String getVersion() {
    return version;
  }

  /**
   * versionを設定します。
   * 
   * @param version
   *          version
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * timeZoneを取得します。
   * 
   * @return timeZone timeZone
   */
  public String getTimeZone() {
    return timeZone;
  }

  /**
   * timeZoneを設定します。
   * 
   * @param timeZone
   *          timeZone
   */
  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  /**
   * dstFlagを取得します。
   * 
   * @return dstFlag dstFlag
   */
  public String getDstFlag() {
    return dstFlag;
  }

  /**
   * dstFlagを設定します。
   * 
   * @param dstFlag
   *          dstFlag
   */
  public void setDstFlag(String dstFlag) {
    this.dstFlag = dstFlag;
  }

  /**
   * extFlagを取得します。
   * 
   * @return extFlag extFlag
   */
  public String getExtFlag() {
    return extFlag;
  }

  /**
   * extFlagを設定します。
   * 
   * @param extFlag
   *          extFlag
   */
  public void setExtFlag(String extFlag) {
    this.extFlag = extFlag;
  }

  /**
   * gateIdを取得します。
   * 
   * @return gateId gateId
   */
  public String getGateId() {
    return gateId;
  }

  /**
   * gateIdを設定します。
   * 
   * @param gateId
   *          gateId
   */
  public void setGateId(String gateId) {
    this.gateId = gateId;
  }

//20111206 lirong add start
  /**
   * @return the requestURL
   */
  public String getRequestURL() {
    return requestURL;
  }

  /**
   * @param requestURL the requestURL to set
   */
  public void setRequestURL(String requestURL) {
    this.requestURL = requestURL;
  }
  
  /**
   * @return the resv
   */
  public String getResv() {
    return resv;
  }

  /**
   * @param resv the resv to set
   */
  public void setResv(String resv) {
    this.resv = resv;
  }
//20111206 lirong add end
}
