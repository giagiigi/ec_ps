package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

public class RankingSearchResult implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String commodityCode;

  private String commodityName;

  private String orderRanking;

  private String lasttimeOrderRanking;

  private String countRanking;

  private String lasttimeCountRanking;

  /**
   * commodityCodeを取得します。
   * 
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * commodityNameを取得します。
   * 
   * @return commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * commodityNameを設定します。
   * 
   * @param commodityName
   *          commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * countRankingを取得します。
   * 
   * @return countRanking
   */
  public String getCountRanking() {
    return countRanking;
  }

  /**
   * countRankingを設定します。
   * 
   * @param countRanking
   *          countRanking
   */
  public void setCountRanking(String countRanking) {
    this.countRanking = countRanking;
  }

  /**
   * lasttimeCountRankingを取得します。
   * 
   * @return lasttimeCountRanking
   */
  public String getLasttimeCountRanking() {
    return lasttimeCountRanking;
  }

  /**
   * lasttimeCountRankingを設定します。
   * 
   * @param lasttimeCountRanking
   *          lasttimeCountRanking
   */
  public void setLasttimeCountRanking(String lasttimeCountRanking) {
    this.lasttimeCountRanking = lasttimeCountRanking;
  }

  /**
   * lasttimeOrderRankingを取得します。
   * 
   * @return lasttimeOrderRanking
   */
  public String getLasttimeOrderRanking() {
    return lasttimeOrderRanking;
  }

  /**
   * lasttimeOrderRankingを設定します。
   * 
   * @param lasttimeOrderRanking
   *          lasttimeOrderRanking
   */
  public void setLasttimeOrderRanking(String lasttimeOrderRanking) {
    this.lasttimeOrderRanking = lasttimeOrderRanking;
  }

  /**
   * orderRankingを取得します。
   * 
   * @return orderRanking
   */
  public String getOrderRanking() {
    return orderRanking;
  }

  /**
   * orderRankingを設定します。
   * 
   * @param orderRanking
   *          orderRanking
   */
  public void setOrderRanking(String orderRanking) {
    this.orderRanking = orderRanking;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

}
