package jp.co.sint.webshop.service.chinapay;

import java.io.Serializable;

import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;

public class PaymentChinapayResultBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1L;

  //银联支付请求URL
  private String purchaseRequestUrl;
  
  //ChinaPay商户编号
  private String merchantId; 

  //交易订单号
  private String orderId; 

  //订单交易金额
  private String transactionAmount; 
  
  //订单交易币种
  private String currencyId; 

  //订单交易日期
  private String transactionDate; 

  //订单交易类型
  private String transactionType; 
  
  //支付接入版本号
  private String version; 
  
  //后台交易接入URL
  private String backReturnUrl; 
  
  //页面交易接入URL
  private String pageReturnUrl; 
  
  //查询请求URL
  private String queryRul;

  //支付网关号
  private String gateId; 
  
  //商户私有域
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "订单编号")
  private String private1; 
  
  //数字签名
  private String checkValue;

  //交易状态
  private String transactionStatus;
  
  //应答编码
  private String responseCode;
  
  //订单内容
  private String orderContent;
  
  // wap订单信息
  private String paydata;
  
  // wap支付结果
  private String result;
  
  /**
   * 取得 purchaseRequestUrl
   * @return the purchaseRequestUrl
   */
  public String getPurchaseRequestUrl() {
    return purchaseRequestUrl;
  }

  
  /**
   * 设定 purchaseRequestUrl
   * @param purchaseRequestUrl the purchaseRequestUrl to set
   */
  public void setPurchaseRequestUrl(String purchaseRequestUrl) {
    this.purchaseRequestUrl = purchaseRequestUrl;
  }

  
  /**
   * 取得 merchantId
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }

  
  /**
   * 设定 merchantId
   * @param merchantId the merchantId to set
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  
  /**
   * 取得 orderId
   * @return the orderId
   */
  public String getOrderId() {
    return orderId;
  }

  
  /**
   * 设定 orderId
   * @param orderId the orderId to set
   */
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  
  /**
   * 取得 transactionAmount
   * @return the transactionAmount
   */
  public String getTransactionAmount() {
    return transactionAmount;
  }

  
  /**
   * 设定 transactionAmount
   * @param transactionAmount the transactionAmount to set
   */
  public void setTransactionAmount(String transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  
  /**
   * 取得 currencyId
   * @return the currencyId
   */
  public String getCurrencyId() {
    return currencyId;
  }

  
  /**
   * 设定 currencyId
   * @param currencyId the currencyId to set
   */
  public void setCurrencyId(String currencyId) {
    this.currencyId = currencyId;
  }

  
  /**
   * 取得 transactionDate
   * @return the transactionDate
   */
  public String getTransactionDate() {
    return transactionDate;
  }

  
  /**
   * 设定 transactionDate
   * @param transactionDate the transactionDate to set
   */
  public void setTransactionDate(String transactionDate) {
    this.transactionDate = transactionDate;
  }

  
  /**
   * 取得 transactionType
   * @return the transactionType
   */
  public String getTransactionType() {
    return transactionType;
  }

  
  /**
   * 设定 transactionType
   * @param transactionType the transactionType to set
   */
  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  
  /**
   * 取得 version
   * @return the version
   */
  public String getVersion() {
    return version;
  }

  
  /**
   * 设定 version
   * @param version the version to set
   */
  public void setVersion(String version) {
    this.version = version;
  }

  
  /**
   * 取得 backReturnUrl
   * @return the backReturnUrl
   */
  public String getBackReturnUrl() {
    return backReturnUrl;
  }

  
  /**
   * 设定 backReturnUrl
   * @param backReturnUrl the backReturnUrl to set
   */
  public void setBackReturnUrl(String backReturnUrl) {
    this.backReturnUrl = backReturnUrl;
  }

  
  /**
   * 取得 pageReturnUrl
   * @return the pageReturnUrl
   */
  public String getPageReturnUrl() {
    return pageReturnUrl;
  }

  
  /**
   * 设定 pageReturnUrl
   * @param pageReturnUrl the pageReturnUrl to set
   */
  public void setPageReturnUrl(String pageReturnUrl) {
    this.pageReturnUrl = pageReturnUrl;
  }

  
  /**
   * 取得 queryRul
   * @return the queryRul
   */
  public String getQueryRul() {
    return queryRul;
  }

  
  /**
   * 设定 queryRul
   * @param queryRul the queryRul to set
   */
  public void setQueryRul(String queryRul) {
    this.queryRul = queryRul;
  }

  
  /**
   * 取得 gateId
   * @return the gateId
   */
  public String getGateId() {
    return gateId;
  }

  
  /**
   * 设定 gateId
   * @param gateId the gateId to set
   */
  public void setGateId(String gateId) {
    this.gateId = gateId;
  }


  /**
   * 取得 private1
   * @return the private1
   */
  public String getPrivate1() {
    return private1;
  }


  
  /**
   * 设定 private1
   * @param private1 the private1 to set
   */
  public void setPrivate1(String private1) {
    this.private1 = private1;
  }


  
  /**
   * 取得 checkValue
   * @return the checkValue
   */
  public String getCheckValue() {
    return checkValue;
  }


  
  /**
   * 设定 checkValue
   * @param checkValue the checkValue to set
   */
  public void setCheckValue(String checkValue) {
    this.checkValue = checkValue;
  }


  
  /**
   * 取得 transactionStatus
   * @return the transactionStatus
   */
  public String getTransactionStatus() {
    return transactionStatus;
  }


  
  /**
   * 设定 transactionStatus
   * @param transactionStatus the transactionStatus to set
   */
  public void setTransactionStatus(String transactionStatus) {
    this.transactionStatus = transactionStatus;
  }


  
  /**
   * 取得 responseCode
   * @return the responseCode
   */
  public String getResponseCode() {
    return responseCode;
  }


  
  /**
   * 设定 responseCode
   * @param responseCode the responseCode to set
   */
  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }


/**
 * @return the paydata
 */
public String getPaydata() {
	return paydata;
}


/**
 * @param paydata the paydata to set
 */
public void setPaydata(String paydata) {
	this.paydata = paydata;
}


/**
 * @return the orderContent
 */
public String getOrderContent() {
	return orderContent;
}


/**
 * @param orderContent the orderContent to set
 */
public void setOrderContent(String orderContent) {
	this.orderContent = orderContent;
}


/**
 * @return the result
 */
public String getResult() {
	return result;
}


/**
 * @param result the result to set
 */
public void setResult(String result) {
	this.result = result;
} 

  
}
