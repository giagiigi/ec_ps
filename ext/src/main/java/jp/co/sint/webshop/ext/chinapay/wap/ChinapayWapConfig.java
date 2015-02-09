package jp.co.sint.webshop.ext.chinapay.wap;

import java.io.File;
import java.io.Serializable;

public class ChinapayWapConfig implements Serializable {

  public static String SUCCESS_CODE = "0";
  
  public static String FAILED_CODE = "111";
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // 购买请求URL
  private String purchaseRequestUrl;

  // 应答电文(背景)的要求发送处
  private String backReturnUrl;

  // 页面返回URL
  private String pageReturnUrl;

  // 货币编码
  private String currencyId;

  // 国家编码
  private String countryId;

  // API版本
  private String version;

  // 时区
  private String timeZone;

  // DST标志
  private String dstFlag;

  // EXT标志
  private String extFlag;

  //
  private String gateId;

  // 查询URL
  private String requestUrl;

  private String resv;

  private String TransType = "0001";

  /** 店主ID */
  private String merchantId;

  /** 密钥 */
  private File secretKey;
  
  /** 公钥 */
  private File publicKey;
  
  /** 公钥文件名 */
  private String publicKeyName;
  
  /** 系统运营方ID */
  private String sysProvider;
  
  /** 终端号 */
  private String terminalId;
  
  /** 服务提供商代码，由商户向银联申请 */
  private String spId;
  
  /**
   * @return the purchaseRequestUrl
   */
  public String getPurchaseRequestUrl() {
    return purchaseRequestUrl;
  }

  /**
   * @param purchaseRequestUrl
   *          the purchaseRequestUrl to set
   */
  public void setPurchaseRequestUrl(String purchaseRequestUrl) {
    this.purchaseRequestUrl = purchaseRequestUrl;
  }

  /**
   * @return the backReturnUrl
   */
  public String getBackReturnUrl() {
    return backReturnUrl;
  }

  /**
   * @param backReturnUrl
   *          the backReturnUrl to set
   */
  public void setBackReturnUrl(String backReturnUrl) {
    this.backReturnUrl = backReturnUrl;
  }

  /**
   * @return the pageReturnUrl
   */
  public String getPageReturnUrl() {
    return pageReturnUrl;
  }

  /**
   * @param pageReturnUrl
   *          the pageReturnUrl to set
   */
  public void setPageReturnUrl(String pageReturnUrl) {
    this.pageReturnUrl = pageReturnUrl;
  }

  /**
   * @return the currencyId
   */
  public String getCurrencyId() {
    return currencyId;
  }

  /**
   * @param currencyId
   *          the currencyId to set
   */
  public void setCurrencyId(String currencyId) {
    this.currencyId = currencyId;
  }

  /**
   * @return the countryId
   */
  public String getCountryId() {
    return countryId;
  }

  /**
   * @param countryId
   *          the countryId to set
   */
  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  /**
   * @return the version
   */
  public String getVersion() {
    return version;
  }

  /**
   * @param version
   *          the version to set
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * @return the timeZone
   */
  public String getTimeZone() {
    return timeZone;
  }

  /**
   * @param timeZone
   *          the timeZone to set
   */
  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  /**
   * @return the dstFlag
   */
  public String getDstFlag() {
    return dstFlag;
  }

  /**
   * @param dstFlag
   *          the dstFlag to set
   */
  public void setDstFlag(String dstFlag) {
    this.dstFlag = dstFlag;
  }

  /**
   * @return the extFlag
   */
  public String getExtFlag() {
    return extFlag;
  }

  /**
   * @param extFlag
   *          the extFlag to set
   */
  public void setExtFlag(String extFlag) {
    this.extFlag = extFlag;
  }

  /**
   * @return the gateId
   */
  public String getGateId() {
    return gateId;
  }

  /**
   * @param gateId
   *          the gateId to set
   */
  public void setGateId(String gateId) {
    this.gateId = gateId;
  }

  /**
   * @return the requestUrl
   */
  public String getRequestUrl() {
    return requestUrl;
  }

  /**
   * @param requestUrl
   *          the requestURL to set
   */
  public void setRequestUrl(String requestUrl) {
    this.requestUrl = requestUrl;
  }

  /**
   * @return the resv
   */
  public String getResv() {
    return resv;
  }

  /**
   * @param resv
   *          the resv to set
   */
  public void setResv(String resv) {
    this.resv = resv;
  }

  /**
   * @return the transType
   */
  public String getTransType() {
    return TransType;
  }

  /**
   * @param transType
   *          the transType to set
   */
  public void setTransType(String transType) {
    TransType = transType;
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
   * 取得 secretKey
   * @return the secretKey
   */
  public File getSecretKey() {
    return secretKey;
  }

  
  /**
   * 设定 secretKey
   * @param secretKey the secretKey to set
   */
  public void setSecretKey(File secretKey) {
    this.secretKey = secretKey;
  }

  
  /**
   * 取得 publicKey
   * @return the publicKey
   */
  public File getPublicKey() {
    return publicKey;
  }

  
  /**
   * 设定 publicKey
   * @param publicKey the publicKey to set
   */
  public void setPublicKey(File publicKey) {
    this.publicKey = publicKey;
  }

  
  /**
   * 取得 publicKeyName
   * @return the publicKeyName
   */
  public String getPublicKeyName() {
    return publicKeyName;
  }

  
  /**
   * 设定 publicKeyName
   * @param publicKeyName the publicKeyName to set
   */
  public void setPublicKeyName(String publicKeyName) {
    this.publicKeyName = publicKeyName;
  }

  
/**
 * @return the sysProvider
 */
public String getSysProvider() {
	return sysProvider;
}

/**
 * @param sysProvider the sysProvider to set
 */
public void setSysProvider(String sysProvider) {
	this.sysProvider = sysProvider;
}

/**
 * @return the spId
 */
public String getSpId() {
	return spId;
}

/**
 * @param spId the spId to set
 */
public void setSpId(String spId) {
	this.spId = spId;
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

}
