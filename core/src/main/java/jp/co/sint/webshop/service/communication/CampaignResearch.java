package jp.co.sint.webshop.service.communication;

import java.io.Serializable;

public class CampaignResearch implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 商品コード */
  private String commodityCode;

  /** 商品名 */
  private String commodityName;

  /** 商品別売上合計金額 */
  private String commoditySalesAmount;

  /** 商品別受注合計数 */
  private String commodityOrderAmount;

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
   * commodityOrderAmountを取得します。
   * 
   * @return commodityOrderAmount
   */
  public String getCommodityOrderAmount() {
    return commodityOrderAmount;
  }

  /**
   * commodityOrderAmountを設定します。
   * 
   * @param commodityOrderAmount
   *          commodityOrderAmount
   */
  public void setCommodityOrderAmount(String commodityOrderAmount) {
    this.commodityOrderAmount = commodityOrderAmount;
  }

  /**
   * commoditySalesAmountを取得します。
   * 
   * @return commoditySalesAmount
   */
  public String getCommoditySalesAmount() {
    return commoditySalesAmount;
  }

  /**
   * commoditySalesAmountを設定します。
   * 
   * @param commoditySalesAmount
   *          commoditySalesAmount
   */
  public void setCommoditySalesAmount(String commoditySalesAmount) {
    this.commoditySalesAmount = commoditySalesAmount;
  }
}
