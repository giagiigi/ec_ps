package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

public class Related implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String commodityCode;

  private String commodityName;

  private String appliedCampaign;
  
  private String displayOrder;

  /**
   * @return appliedCampaign
   */
  public String getAppliedCampaign() {
    return appliedCampaign;
  }

  /**
   * @param appliedCampaign
   *          設定する appliedCampaign
   */
  public void setAppliedCampaign(String appliedCampaign) {
    this.appliedCampaign = appliedCampaign;
  }

  /**
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          設定する commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * @param commodityName
   *          設定する commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
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
   * @param displayOrder 設定する displayOrder
   */
  public void setDisplayOrder(String displayOrder) {
    this.displayOrder = displayOrder;
  }

  /**
   * @return displayOrder
   */
  public String getDisplayOrder() {
    return displayOrder;
  }
}
