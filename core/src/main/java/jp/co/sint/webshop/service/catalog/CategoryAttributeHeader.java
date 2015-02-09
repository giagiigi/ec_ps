package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

public class CategoryAttributeHeader implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String categoryCode;

  private String categoryNamePc;

  private String shopCode;

  private String commodityCode;

  private String commodityName;

  /**
   * @return categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * @param categoryCode
   *          設定する categoryCode
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  /**
   * @return categoryNamePc
   */
  public String getCategoryNamePc() {
    return categoryNamePc;
  }

  /**
   * @param categoryNamePc
   *          設定する categoryNamePc
   */
  public void setCategoryNamePc(String categoryNamePc) {
    this.categoryNamePc = categoryNamePc;
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
   * @param commodityName 設定する commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * @return commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

}
