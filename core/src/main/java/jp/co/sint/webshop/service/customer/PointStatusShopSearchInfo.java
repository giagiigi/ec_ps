package jp.co.sint.webshop.service.customer;

import java.io.Serializable;

/**
 * ポイント利用状況の検索結果
 */
public class PointStatusShopSearchInfo implements Serializable {

  /**
   * uid
   */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String shopName;

  private String ineffectivePoint;

  private String temporaryPoint;

  private String restPoint;

  /**
   * @return ineffectivePoint
   */
  public String getIneffectivePoint() {
    return ineffectivePoint;
  }

  /**
   * @param ineffectivePoint
   *          設定する ineffectivePoint
   */
  public void setIneffectivePoint(String ineffectivePoint) {
    this.ineffectivePoint = ineffectivePoint;
  }

  /**
   * @return restPoint
   */
  public String getRestPoint() {
    return restPoint;
  }

  /**
   * @param restPoint
   *          設定する restPoint
   */
  public void setRestPoint(String restPoint) {
    this.restPoint = restPoint;
  }

  /**
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * @param shopName
   *          設定する shopName
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  /**
   * @return temporaryPoint
   */
  public String getTemporaryPoint() {
    return temporaryPoint;
  }

  /**
   * @param temporaryPoint
   *          設定する temporaryPoint
   */
  public void setTemporaryPoint(String temporaryPoint) {
    this.temporaryPoint = temporaryPoint;
  }

}
