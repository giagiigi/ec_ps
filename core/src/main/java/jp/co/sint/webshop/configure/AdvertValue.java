package jp.co.sint.webshop.configure;

import java.io.Serializable;

/**
 * 広告に対応する
 * 
 * @author System Integrator Corp.
 */
public class AdvertValue implements Serializable {

  private static final long serialVersionUID = 1L;

  private String advertUrl1;
  
  private String advertUrl2;
  
  private String advertUrl3;
  
  private String advertKey;
  
  
  
  /**
   * advertKeyを取得します。
   *
   * @return advertKey advertKey
   */
  public String getAdvertKey() {
    return advertKey;
  }

  
  /**
   * advertKeyを設定します。
   *
   * @param advertKey 
   *          advertKey
   */
  public void setAdvertKey(String advertKey) {
    this.advertKey = advertKey;
  }

  /**
   * advertUrl2を取得します。
   *
   * @return advertUrl2 advertUrl2
   */
  public String getAdvertUrl2() {
     return advertUrl2;
  }

  /**
   * advertUrl2を設定します。
   *
   * @param advertUrl2 
   *          advertUrl2
   */
  public void setAdvertUrl2(String advertUrl2) {
    this.advertUrl2 = advertUrl2;
  }

  /**
   * advertUrl3を取得します。
   *
   * @return advertUrl3 advertUrl3
   */
  public String getAdvertUrl3() {
    return advertUrl3;
  }

  /**
   * advertUrl3を設定します。
   *
   * @param advertUrl3 
   *          advertUrl3
   */
  public void setAdvertUrl3(String advertUrl3) {
    this.advertUrl3 = advertUrl3;
  }

  /**
   * advertUrl1を取得します。
   *
   * @return advertUrl1 advertUrl1
   */
  public String getAdvertUrl1() {
    return advertUrl1;
  }
  
  /**
   * advertUrl1を設定します。
   *
   * @param advertUrl1 
   *          advertUrl1
   */
  public void setAdvertUrl1(String advertUrl1) {
    this.advertUrl1 = advertUrl1;
  }

  }

