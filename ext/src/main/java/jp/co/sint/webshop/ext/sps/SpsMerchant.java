package jp.co.sint.webshop.ext.sps;

import java.io.Serializable;

/**
 * @author System Integrator Corp.
 */
public class SpsMerchant implements Serializable {

  private static final long serialVersionUID = 1L;

  private String merchantId;

  private String serviceId;

  private String hashKey;

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
   * serviceIdを返します。
   * 
   * @return serviceId
   */
  public String getServiceId() {
    return serviceId;
  }

  /**
   * serviceIdを設定します。
   * 
   * @param serviceId
   *          serviceId
   */
  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  /**
   * hashKeyを返します。
   * 
   * @return hashKey
   */
  public String getHashKey() {
    return hashKey;
  }

  /**
   * hashKeyを設定します。
   * 
   * @param hashKey
   *          hashKey
   */
  public void setHashKey(String hashKey) {
    this.hashKey = hashKey;
  }

}
