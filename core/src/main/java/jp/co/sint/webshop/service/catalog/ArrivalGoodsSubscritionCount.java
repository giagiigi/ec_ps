package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;


public class ArrivalGoodsSubscritionCount implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** ショップコード */
  private String shopCode;

  /** SKUコード */
  private String skuCode;

  /** 商品名 */
  private String commodityName;
  
  /** 規格詳細1名称 */
  private String standardDetail1Name;

  /** 規格詳細2名称 */
  private String standardDetail2Name;

  /** 申し込み数 */
  private String subscriptionCount;

  
  /**
   * commodityNameを返します。
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  
  /**
   * shopCodeを返します。
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  
  /**
   * skuCodeを返します。
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  
  /**
   * standardDetail1Nameを返します。
   * @return the standardDetail1Name
   */
  public String getStandardDetail1Name() {
    return standardDetail1Name;
  }

  
  /**
   * standardDetail2Nameを返します。
   * @return the standardDetail2Name
   */
  public String getStandardDetail2Name() {
    return standardDetail2Name;
  }

  
  /**
   * subscriptionCountを返します。
   * @return the subscriptionCount
   */
  public String getSubscriptionCount() {
    return subscriptionCount;
  }

  
  /**
   * commodityNameを設定します。
   * @param commodityName 設定する commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  
  /**
   * shopCodeを設定します。
   * @param shopCode 設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  
  /**
   * skuCodeを設定します。
   * @param skuCode 設定する skuCode
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  
  /**
   * standardDetail1Nameを設定します。
   * @param standardDetail1Name 設定する standardDetail1Name
   */
  public void setStandardDetail1Name(String standardDetail1Name) {
    this.standardDetail1Name = standardDetail1Name;
  }

  
  /**
   * standardDetail2Nameを設定します。
   * @param standardDetail2Name 設定する standardDetail2Name
   */
  public void setStandardDetail2Name(String standardDetail2Name) {
    this.standardDetail2Name = standardDetail2Name;
  }

  
  /**
   * subscriptionCountを設定します。
   * @param subscriptionCount 設定する subscriptionCount
   */
  public void setSubscriptionCount(String subscriptionCount) {
    this.subscriptionCount = subscriptionCount;
  }

  

}
