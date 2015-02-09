package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;

public class CommodityAccessLogSummary implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** ショップコード */
  private String shopCode;

  /** 商品コード */
  private String commodityCode;

  /** 商品名 */
  private String commodityName;

  /** ショップ名 */
  private String shopName;

  /** 商品アクセス回数 */
  private Long accessCount;

  /**
   * shopCodeを返します。
   * 
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * commodityCodeを返します。
   * 
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * commodityNameを返します。
   * 
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * shopNameを返します。
   * 
   * @return the shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * accessCountを返します。
   * 
   * @return the accessCount
   */
  public Long getAccessCount() {
    return accessCount;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          設定する commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * commodityNameを設定します。
   * 
   * @param commodityName
   *          設定する commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * shopNameを設定します。
   * 
   * @param shopName
   *          設定する shopName
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  /**
   * accessCountを設定します。
   * 
   * @param accessCount
   *          設定する accessCount
   */
  public void setAccessCount(Long accessCount) {
    this.accessCount = accessCount;
  }
}
