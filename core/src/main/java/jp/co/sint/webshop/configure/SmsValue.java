package jp.co.sint.webshop.configure;

import java.io.Serializable;

/**
 * 広告に対応する
 * 
 * @author System Integrator Corp.
 */
public class SmsValue implements Serializable {

  private static final long serialVersionUID = 1L;

  private String host;

  private String port;

  private String accountId;

  private String password;

  private String key;

  private String serviceId;

  /**
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * @param key
   *          the key to set
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * accountIdを取得します。
   * 
   * @return accountId accountId
   */
  public String getAccountId() {
    return accountId;
  }

  /**
   * accountIdを設定します。
   * 
   * @param accountId
   *          accountId
   */
  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  /**
   * hostを取得します。
   * 
   * @return host host
   */
  public String getHost() {
    return host;
  }

  /**
   * hostを設定します。
   * 
   * @param host
   *          host
   */
  public void setHost(String host) {
    this.host = host;
  }

  /**
   * passwordを取得します。
   * 
   * @return password password
   */
  public String getPassword() {
    return password;
  }

  /**
   * passwordを設定します。
   * 
   * @param password
   *          password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * portを取得します。
   * 
   * @return port port
   */
  public String getPort() {
    return port;
  }

  /**
   * portを設定します。
   * 
   * @param port
   *          port
   */
  public void setPort(String port) {
    this.port = port;
  }

  /**
   * serviceIdを取得します。
   * 
   * @return serviceId serviceId
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

}
