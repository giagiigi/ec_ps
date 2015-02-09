package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;

public class CustomerPreferenceSummary implements Serializable {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** ショップコード */
  private String shopCode;

  /** ショップ名 */
  private String shopName;

  /** 商品コード */
  private String commodityCode;

  /** 商品名 */
  private String commodityName;

  /** 顧客数合計 */
  private Long totalCustomerCount;

  /** 受注件数合計 */
  private Long totalOrderCount;

  /** 受注件数構成率 */
  private String totalOrderCountRatio;

  /** 購入商品数合計 */
  private String purchasingAmount;

  /**
   * shopCodeを返します。
   * 
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
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
   * totalCustomerCountを返します。
   * 
   * @return the totalCustomerCount
   */
  public Long getTotalCustomerCount() {
    return totalCustomerCount;
  }

  /**
   * totalOrderCountを返します。
   * 
   * @return the totalOrderCount
   */
  public Long getTotalOrderCount() {
    return totalOrderCount;
  }

  /**
   * totalOrderCountRatioを返します。
   * 
   * @return the totalOrderCountRatio
   */
  public String getTotalOrderCountRatio() {
    return totalOrderCountRatio;
  }

  /**
   * purchasingAmountを返します。
   * 
   * @return the purchasingAmount
   */
  public String getPurchasingAmount() {
    return purchasingAmount;
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
   * shopNameを設定します。
   * 
   * @param shopName
   *          設定する shopName
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
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
   * totalCustomerCountを設定します。
   * 
   * @param totalCustomerCount
   *          設定する totalCustomerCount
   */
  public void setTotalCustomerCount(Long totalCustomerCount) {
    this.totalCustomerCount = totalCustomerCount;
  }

  /**
   * totalOrderCountを設定します。
   * 
   * @param totalOrderCount
   *          設定する totalOrderCount
   */
  public void setTotalOrderCount(Long totalOrderCount) {
    this.totalOrderCount = totalOrderCount;
  }

  /**
   * totalOrderCountRatioを設定します。
   * 
   * @param totalOrderCountRatio
   *          設定する totalOrderCountRatio
   */
  public void setTotalOrderCountRatio(String totalOrderCountRatio) {
    this.totalOrderCountRatio = totalOrderCountRatio;
  }

  /**
   * purchasingAmountを設定します。
   * 
   * @param purchasingAmount
   *          設定する purchasingAmount
   */
  public void setPurchasingAmount(String purchasingAmount) {
    this.purchasingAmount = purchasingAmount;
  }

}
