package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.service.SearchCondition;


public class ArrivalGoodsSearchCondition extends SearchCondition {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String searchSkuCode;

  private String searchCommodityName;

  private String searchShopCode;

  
  public String getSearchCommodityName() {
    return searchCommodityName;
  }

  
  public String getSearchShopCode() {
    return searchShopCode;
  }

  
  public String getSearchSkuCode() {
    return searchSkuCode;
  }

  
  public void setSearchCommodityName(String searchCommodityName) {
    this.searchCommodityName = searchCommodityName;
  }

  
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  
  public void setSearchSkuCode(String searchSkuCode) {
    this.searchSkuCode = searchSkuCode;
  }

}
