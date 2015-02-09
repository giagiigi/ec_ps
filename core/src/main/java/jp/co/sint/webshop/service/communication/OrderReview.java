package jp.co.sint.webshop.service.communication;

import java.io.Serializable;

public class OrderReview implements Serializable {

  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String commodityCode;

  private String commodityName;
  
  private String commodityNameEn;
  
  private String commodityNameJp;

  private String retailPrice;

  private String rewardId;

  // 商品类型，分区普通商品与赠品
  private String commodityType;

  /**
   * @return the retailPrice
   */
  public String getRetailPrice() {
    return retailPrice;
  }

  /**
   * @param retailPrice
   *          the retailPrice to set
   */
  public void setRetailPrice(String retailPrice) {
    this.retailPrice = retailPrice;
  }

  /**
   * @return the rewardId
   */
  public String getRewardId() {
    return rewardId;
  }

  /**
   * @param rewardId
   *          the rewardId to set
   */
  public void setRewardId(String rewardId) {
    this.rewardId = rewardId;
  }

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * @param commodityName
   *          the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * @return the commodityType
   */
  public String getCommodityType() {
    return commodityType;
  }

  /**
   * @param commodityType
   *          the commodityType to set
   */
  public void setCommodityType(String commodityType) {
    this.commodityType = commodityType;
  }

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
   * @return the commodityNameEn
   */
  public String getCommodityNameEn() {
    return commodityNameEn;
  }

  
  /**
   * @param commodityNameEn the commodityNameEn to set
   */
  public void setCommodityNameEn(String commodityNameEn) {
    this.commodityNameEn = commodityNameEn;
  }

  
  /**
   * @return the commodityNameJp
   */
  public String getCommodityNameJp() {
    return commodityNameJp;
  }

  
  /**
   * @param commodityNameJp the commodityNameJp to set
   */
  public void setCommodityNameJp(String commodityNameJp) {
    this.commodityNameJp = commodityNameJp;
  }

}
