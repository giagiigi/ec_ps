package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.service.SearchCondition;

public class CategoryAttributeValueSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String categoryCode;

  private String searchCommodityCodeStart;

  private String searchCommodityCodeEnd;

  private String searchCommodityName;

  
  /**
   * @return categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  
  /**
   * @param categoryCode 設定する categoryCode
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  
  /**
   * @return searchCommodityCodeEnd
   */
  public String getSearchCommodityCodeEnd() {
    return searchCommodityCodeEnd;
  }

  
  /**
   * @param searchCommodityCodeEnd 設定する searchCommodityCodeEnd
   */
  public void setSearchCommodityCodeEnd(String searchCommodityCodeEnd) {
    this.searchCommodityCodeEnd = searchCommodityCodeEnd;
  }

  
  /**
   * @return searchCommodityCodeStart
   */
  public String getSearchCommodityCodeStart() {
    return searchCommodityCodeStart;
  }

  
  /**
   * @param searchCommodityCodeStart 設定する searchCommodityCodeStart
   */
  public void setSearchCommodityCodeStart(String searchCommodityCodeStart) {
    this.searchCommodityCodeStart = searchCommodityCodeStart;
  }

  
  /**
   * @return searchCommodityName
   */
  public String getSearchCommodityName() {
    return searchCommodityName;
  }

  
  /**
   * @param searchCommodityName 設定する searchCommodityName
   */
  public void setSearchCommodityName(String searchCommodityName) {
    this.searchCommodityName = searchCommodityName;
  }

  
  /**
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  
  /**
   * @param shopCode 設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

}
