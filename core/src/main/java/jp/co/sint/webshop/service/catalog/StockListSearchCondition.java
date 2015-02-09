package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.SearchCondition;

public class StockListSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String searchShopCode;

  // private String searchSkuCode;

  private List<String> searchSkuCode = new ArrayList<String>();

  private List<String> searchCommodityCode = new ArrayList<String>();

  private String commodityLink;

  public String getCommodityLink() {
    return commodityLink;
  }

  public void setCommodityLink(String commodityLink) {
    this.commodityLink = commodityLink;
  }

  /**
   * @return searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
  }

  /**
   * @param searchShopCode
   *          設定する searchShopCode
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  public List<String> getSearchSkuCode() {
    return searchSkuCode;
  }

  public void setSearchSkuCode(List<String> searchSkuCode) {
    this.searchSkuCode = searchSkuCode;
  }

  public List<String> getSearchCommodityCode() {
    return searchCommodityCode;
  }

  public void setSearchCommodityCode(List<String> searchCommodityCode) {
    this.searchCommodityCode = searchCommodityCode;
  }

  // /**
  // * @param searchSkuCode
  // * 設定する searchSkuCode
  // */
  // public void setSearchSkuCode(String searchSkuCode) {
  // this.searchSkuCode = searchSkuCode;
  // }
  //
  // /**
  // * @return searchSkuCode
  // */
  // public String getSearchSkuCode() {
  // return searchSkuCode;
  // }

}
