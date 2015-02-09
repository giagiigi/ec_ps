package jp.co.sint.webshop.ext.chinapay.wap;

import java.io.Serializable;

public class CupMobile implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // 支付应用名称 最大长度32
  private String cupMobileApplication;
  // 版本号 定长4
  private String cupMobileVersion;
  // 交易类型 最大长度64
  // "Purchase"－消费交易
  // "PurchaseAdvice"－消费通知交易
  private String transactionType;
  // 交易提交时间 定长14 YYYYMMDDhhmmss
  private String submitTime;
  // 商户代码 定长15 银联为接入本平台的商户分配的唯一标识号
  private String merchantId;
  // 订单号 最大长度32
  private String orderId;
  // 订单生成时间 定长14 YYYYMMDDhhmmss
  private String orderGenerateTime;
  // 清算日期 定长8位 YYYYMMDD
  private String settleDate;
  // 交易传输时间 定长10 MMDDhhmmss
  private String transmitTime;
  // 扣账金额 定长12 不满12位前补“0”
  private String billAmount;
  // 账户1 最大长度19 银行卡号
  private String accountNumber1;
  // 交易流水号 定长6 银联平台为当前交易生成的识别号
  private String transSerialNumber;
  // 子商户/终端代码 定长8 商户为其子商户分配的唯一标识
  private String terminalId;
  // 交易币种 定长3
  private String transAmountCurrency;
  // 交易金额 定长12 不满12位前补“0”
  private String transAmount;
  // 应答码 定长3
  private String responseCode;
  
	/**
	 * @return the cupMobileApplication
	 */
	public String getCupMobileApplication() {
		return cupMobileApplication;
	}
	/**
	 * @param cupMobileApplication the cupMobileApplication to set
	 */
	public void setCupMobileApplication(String cupMobileApplication) {
		this.cupMobileApplication = cupMobileApplication;
	}
	/**
	 * @return the cupMobileVersion
	 */
	public String getCupMobileVersion() {
		return cupMobileVersion;
	}
	/**
	 * @param cupMobileVersion the cupMobileVersion to set
	 */
	public void setCupMobileVersion(String cupMobileVersion) {
		this.cupMobileVersion = cupMobileVersion;
	}
	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * @return the submitTime
	 */
	public String getSubmitTime() {
		return submitTime;
	}
	/**
	 * @param submitTime the submitTime to set
	 */
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}
	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the orderGenerateTime
	 */
	public String getOrderGenerateTime() {
		return orderGenerateTime;
	}
	/**
	 * @param orderGenerateTime the orderGenerateTime to set
	 */
	public void setOrderGenerateTime(String orderGenerateTime) {
		this.orderGenerateTime = orderGenerateTime;
	}
	/**
	 * @return the settleDate
	 */
	public String getSettleDate() {
		return settleDate;
	}
	/**
	 * @param settleDate the settleDate to set
	 */
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	/**
	 * @return the transmitTime
	 */
	public String getTransmitTime() {
		return transmitTime;
	}
	/**
	 * @param transmitTime the transmitTime to set
	 */
	public void setTransmitTime(String transmitTime) {
		this.transmitTime = transmitTime;
	}
	/**
	 * @return the billAmount
	 */
	public String getBillAmount() {
		return billAmount;
	}
	/**
	 * @param billAmount the billAmount to set
	 */
	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}
	/**
	 * @return the accountNumber1
	 */
	public String getAccountNumber1() {
		return accountNumber1;
	}
	/**
	 * @param accountNumber1 the accountNumber1 to set
	 */
	public void setAccountNumber1(String accountNumber1) {
		this.accountNumber1 = accountNumber1;
	}
	/**
	 * @return the transSerialNumber
	 */
	public String getTransSerialNumber() {
		return transSerialNumber;
	}
	/**
	 * @param transSerialNumber the transSerialNumber to set
	 */
	public void setTransSerialNumber(String transSerialNumber) {
		this.transSerialNumber = transSerialNumber;
	}
	/**
	 * @return the terminalId
	 */
	public String getTerminalId() {
		return terminalId;
	}
	/**
	 * @param terminalId the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	/**
	 * @return the transAmountCurrency
	 */
	public String getTransAmountCurrency() {
		return transAmountCurrency;
	}
	/**
	 * @param transAmountCurrency the transAmountCurrency to set
	 */
	public void setTransAmountCurrency(String transAmountCurrency) {
		this.transAmountCurrency = transAmountCurrency;
	}
	/**
	 * @return the transAmount
	 */
	public String getTransAmount() {
		return transAmount;
	}
	/**
	 * @param transAmount the transAmount to set
	 */
	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}
	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}
	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
  
}
