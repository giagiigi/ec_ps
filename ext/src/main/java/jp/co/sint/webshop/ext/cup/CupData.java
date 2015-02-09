package jp.co.sint.webshop.ext.cup;

import java.io.Serializable;

/**
 * @author System Integrator Corp.
 */
public class CupData implements Serializable {

  /**  */
  private static final long serialVersionUID = 1L;

  private String purchaseRequestUrl;

  private String merchantId;

  private String orderId;

  private String gateId;

  private String transactionDate;

  private String transactionType;

  private String private1;

  private String private2;

  private String checkValue;

  private String plainData;
//20111206 lirong add start
  private String Resv;
//20111206 lirong add end
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
   * merchantIdを返します。
   * 
   * @return merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }

  /**
   * merchantIdを設定します。
   * 
   * @param merchantId
   *          merchantId
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * orderIdを返します。
   * 
   * @return orderId
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * orderIdを設定します。
   * 
   * @param orderId
   *          orderId
   */
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  /**
   * gateIdを返します。
   * 
   * @return gateId
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

  /**
   * transactionDateを返します。
   * 
   * @return transactionDate
   */
  public String getTransactionDate() {
    return transactionDate;
  }

  /**
   * transactionDateを設定します。
   * 
   * @param transactionDate
   *          transactionDate
   */
  public void setTransactionDate(String transactionDate) {
    this.transactionDate = transactionDate;
  }

  /**
   * transactionTypeを返します。
   * 
   * @return transactionType
   */
  public String getTransactionType() {
    return transactionType;
  }

  /**
   * transactionTypeを設定します。
   * 
   * @param transactionType
   *          transactionType
   */
  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  /**
   * private1を返します。
   * 
   * @return private1
   */
  public String getPrivate1() {
    return private1;
  }

  /**
   * private1を設定します。
   * 
   * @param private1
   *          private1
   */
  public void setPrivate1(String private1) {
    this.private1 = private1;
  }

  /**
   * private2を返します。
   * 
   * @return private2
   */
  public String getPrivate2() {
    return private2;
  }

  /**
   * private2を設定します。
   * 
   * @param private2
   *          private2
   */
  public void setPrivate2(String private2) {
    this.private2 = private2;
  }

  /**
   * checkValueを返します。
   * 
   * @return checkValue
   */
  public String getCheckValue() {
    return checkValue;
  }

  /**
   * checkValueを設定します。
   * 
   * @param checkValue
   *          checkValue
   */
  public void setCheckValue(String checkValue) {
    this.checkValue = checkValue;
  }

  /**
   * plainDataを返します。
   * 
   * @return plainData
   */
  public String getPlainData() {
    return plainData;
  }

  /**
   * plainDataを設定します。
   * 
   * @param plainData
   *          plainData
   */
  public void setPlainData(String plainData) {
    this.plainData = plainData;
  }
//20111206 lirong add start
  /**
   * @return the resv
   */
  public String getResv() {
    return Resv;
  }
  
  /**
   * @param resv the resv to set
   */
  public void setResv(String resv) {
    Resv = resv;
  }
//20111206 lirong add end
}
