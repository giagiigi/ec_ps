package jp.co.sint.webshop.ext.cup;


/**
 * @author System Integrator Corp.
 */
public class CupPaymentRequest extends CupRequest {

  /**  */
  private static final long serialVersionUID = 1L;

  private String currencyId;

  private String countryId;

  private String transactionAmount;

  private String transactionTime;

  private String backReturnUrl;

  private String pageReturnUrl;

  private String timeZone;

  private String dstFlag;

  private String extFlag;
//20111206 lirong add start
  private String requestURL;
//20111206 lirong add end
  /**
   * transactionAmountを返します。
   * 
   * @return transactionAmount
   */
  public String getTransactionAmount() {
    return transactionAmount;
  }

  /**
   * transactionAmountを設定します。
   * 
   * @param transactionAmount
   *          transactionAmount
   */
  public void setTransactionAmount(String transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  /**
   * currencyIdを返します。
   * 
   * @return currencyId
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
   * countryIdを返します。
   * 
   * @return countryId
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
   * transactionTimeを返します。
   * 
   * @return transactionTime
   */
  public String getTransactionTime() {
    return transactionTime;
  }

  /**
   * transactionTimeを設定します。
   * 
   * @param transactionTime
   *          transactionTime
   */
  public void setTransactionTime(String transactionTime) {
    this.transactionTime = transactionTime;
  }


  /**
   * backReturnUrlを返します。
   * 
   * @return backReturnUrl
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
   * timeZoneを返します。
   * 
   * @return timeZone
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
   * dstFlagを返します。
   * 
   * @return dstFlag
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
   * extFlagを返します。
   * 
   * @return extFlag
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
//20111206 lirong add end
}
