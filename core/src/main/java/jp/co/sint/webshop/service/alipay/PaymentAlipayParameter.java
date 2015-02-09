package jp.co.sint.webshop.service.alipay;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentAlipayParameter implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** 店铺编号 */
  private String shopCode;

  /** 订单编号 */
  private String orderId;

  /** 支付金额 */
  private BigDecimal amount;

  /** 店主ID */
  private String merchantId;

  /** 密钥 */
  private String secretKey;

  /** 服务ID */
  private String serviceId;

  /** 是否成功 */
  private String isSuccess;

  /** 交易状态 */
  private String tradeStatus;
  
  /** 付款时间 */
  private String gmtPayment;
  
  /** 错误信息 */
  private String error;

  /** 商品描述 */
  private String commodityNames;
  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return the orderId
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * @param orderId
   *          the orderId to set
   */
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  /**
   * @return the amount
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * @param amount
   *          the amount to set
   */
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  /**
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }

  /**
   * @param merchantId
   *          the merchantId to set
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * @return the secretKey
   */
  public String getSecretKey() {
    return secretKey;
  }

  /**
   * @param secretKey
   *          the secretKey to set
   */
  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  /**
   * @return the serviceId
   */
  public String getServiceId() {
    return serviceId;
  }

  /**
   * @param serviceId
   *          the serviceId to set
   */
  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  /**
   * @return the isSuccess
   */
  public String getIsSuccess() {
    return isSuccess;
  }

  /**
   * @param isSuccess
   *          the isSuccess to set
   */
  public void setIsSuccess(String isSuccess) {
    this.isSuccess = isSuccess;
  }

  /**
   * @return the tradeStatus
   */
  public String getTradeStatus() {
    return tradeStatus;
  }

  /**
   * @param tradeStatus
   *          the tradeStatus to set
   */
  public void setTradeStatus(String tradeStatus) {
    this.tradeStatus = tradeStatus;
  }

  /**
   * @return the gmtPayment
   */
  public String getGmtPayment() {
    return gmtPayment;
  }

  /**
   * @param gmtPayment the gmtPayment to set
   */
  public void setGmtPayment(String gmtPayment) {
    this.gmtPayment = gmtPayment;
  }
  
  /**
   * @return the error
   */
  public String getError() {
    return error;
  }
  
  /**
   * @param error the error to set
   */
  public void setError(String error) {
    this.error = error;
  }

  
  /**
   * 取得 commodityNames
   * @return the commodityNames
   */
  public String getCommodityNames() {
    return commodityNames;
  }

  
  /**
   * 设定 commodityNames
   * @param commodityNames the commodityNames to set
   */
  public void setCommodityNames(String commodityNames) {
    this.commodityNames = commodityNames;
  }

}
