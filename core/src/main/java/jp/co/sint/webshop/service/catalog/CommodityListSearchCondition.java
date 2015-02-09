package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.ArrayUtil;

public class CommodityListSearchCondition extends SearchCondition {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String searchShopCode;

  private String searchCommodityCodeStart;

  private String searchCommodityCodeEnd;

  private String searchSkuCode;

  private String searchCommodityName;

  private String searchCommoditySearchWords;

  private String searchSaleStartDateRangeFrom;

  private String searchSaleStartDateRangeTo;

  private String searchSaleEndDateRangeFrom;

  private String searchSaleEndDateRangeTo;

  private String searchStockQuantityStart;

  private String searchStockQuantityEnd;

  // add by tangweihui 2012-11-16 start
  // 商品区分
  private String searchCommodityType;

  // 套装商品区分
  private String searchSetcommodityflg;

  // add by tangweihui 2012-11-16 end

  // tmall满就送赠品区分
  private String searchTmallMjsCommodityflg;
  
  private String combination;

  private String[] searchStockStatus = new String[0];

  private String searchSaleType;

  private String[] searchSaleStatus = new String[0];

  private String[] searchDisplayClientType = new String[0];

  private String searchArrivalGoods;

  private boolean notSafetyStock;
  
  // 20140526 hdh add start
  //品店精选
  private String choseType;
  
  // 20140526 hdh add end
  
  /**
   * @return the combination
   */
  public String getCombination() {
    return combination;
  }

  
  /**
   * @param combination the combination to set
   */
  public void setCombination(String combination) {
    this.combination = combination;
  }

  public String getSearchTmallMjsCommodityflg() {
    return searchTmallMjsCommodityflg;
  }

  public void setSearchTmallMjsCommodityflg(String searchTmallMjsCommodityflg) {
    this.searchTmallMjsCommodityflg = searchTmallMjsCommodityflg;
  }

  public String getSearchCommodityCodeStart() {
    return searchCommodityCodeStart;
  }

  public String getSearchCommodityName() {
    return searchCommodityName;
  }

  public String getSearchCommoditySearchWords() {
    return searchCommoditySearchWords;
  }

  public String getSearchSaleEndDateRangeTo() {
    return searchSaleEndDateRangeTo;
  }

  public String getSearchSaleStartDateRangeFrom() {
    return searchSaleStartDateRangeFrom;
  }

  public String getSearchSaleStartDateRangeTo() {
    return searchSaleStartDateRangeTo;
  }

  public String getSearchSkuCode() {
    return searchSkuCode;
  }

  public String getSearchStockQuantityEnd() {
    return searchStockQuantityEnd;
  }

  public String getSearchStockQuantityStart() {
    return searchStockQuantityStart;
  }

  public String getSearchCommodityType() {
    return searchCommodityType;
  }

  public void setSearchCommodityType(String searchCommodityType) {
    this.searchCommodityType = searchCommodityType;
  }

  public String getSearchSetcommodityflg() {
    return searchSetcommodityflg;
  }

  public void setSearchSetcommodityflg(String searchSetcommodityflg) {
    this.searchSetcommodityflg = searchSetcommodityflg;
  }

  public void setSearchCommodityCodeEnd(String searchCommodityCodeEnd) {
    this.searchCommodityCodeEnd = searchCommodityCodeEnd;
  }

  public void setSearchCommodityCodeStart(String searchCommodityCodeStart) {
    this.searchCommodityCodeStart = searchCommodityCodeStart;
  }

  public void setSearchCommodityName(String searchCommodityName) {
    this.searchCommodityName = searchCommodityName;
  }

  public void setSearchCommoditySearchWords(String searchCommoditySearchWords) {
    this.searchCommoditySearchWords = searchCommoditySearchWords;
  }

  public void setSearchSaleEndDateRangeFrom(String searchSaleEndDateRangeFrom) {
    this.searchSaleEndDateRangeFrom = searchSaleEndDateRangeFrom;
  }

  public void setSearchSaleEndDateRangeTo(String searchSaleEndDateRangeTo) {
    this.searchSaleEndDateRangeTo = searchSaleEndDateRangeTo;
  }

  public void setSearchSaleStartDateRangeFrom(String searchSaleStartDateRangeFrom) {
    this.searchSaleStartDateRangeFrom = searchSaleStartDateRangeFrom;
  }

  public void setSearchSaleStartDateRangeTo(String searchSaleStartDateRangeTo) {
    this.searchSaleStartDateRangeTo = searchSaleStartDateRangeTo;
  }

  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  public void setSearchSkuCode(String searchSkuCode) {
    this.searchSkuCode = searchSkuCode;
  }

  public void setSearchStockQuantityEnd(String searchStockQuantityEnd) {
    this.searchStockQuantityEnd = searchStockQuantityEnd;
  }

  public void setSearchStockQuantityStart(String searchStockQuantityStart) {
    this.searchStockQuantityStart = searchStockQuantityStart;
  }

  /**
   * searchCommodityCodeEndを取得します。
   * 
   * @return searchCommodityCodeEnd
   */
  public String getSearchCommodityCodeEnd() {
    return searchCommodityCodeEnd;
  }

  /**
   * searchDisplayClientTypeを取得します。
   * 
   * @return searchDisplayClientType
   */
  public String[] getSearchDisplayClientType() {
    return ArrayUtil.immutableCopy(searchDisplayClientType);
  }

  /**
   * searchSaleEndDateRangeFromを取得します。
   * 
   * @return searchSaleEndDateRangeFrom
   */
  public String getSearchSaleEndDateRangeFrom() {
    return searchSaleEndDateRangeFrom;
  }

  /**
   * searchShopCodeを取得します。
   * 
   * @return searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
  }

  /**
   * searchStockStatusを取得します。
   * 
   * @return searchStockStatus
   */
  public String[] getSearchStockStatus() {
    return ArrayUtil.immutableCopy(searchStockStatus);
  }

  /**
   * searchDisplayClientTypeを設定します。
   * 
   * @param searchDisplayClientType
   *          searchDisplayClientType
   */
  public void setSearchDisplayClientType(String[] searchDisplayClientType) {
    this.searchDisplayClientType = ArrayUtil.immutableCopy(searchDisplayClientType);
  }

  /**
   * searchStockStatusを設定します。
   * 
   * @param searchStockStatus
   *          searchStockStatus
   */
  public void setSearchStockStatus(String... searchStockStatus) {
    this.searchStockStatus = ArrayUtil.immutableCopy(searchStockStatus);
  }

  /**
   * searchArrivalGoodsを取得します。
   * 
   * @return searchArrivalGoods
   */
  public String getSearchArrivalGoods() {
    return this.searchArrivalGoods;
  }

  /**
   * searchArrivalGoodsを設定します。
   * 
   * @param searchArrivalGoods
   *          searchArrivalGoods
   */
  public void setSearchArrivalGoods(String searchArrivalGoods) {
    this.searchArrivalGoods = searchArrivalGoods;
  }

  /**
   * searchSaleStatusを取得します。
   * 
   * @return searchSaleStatus
   */

  public String[] getSearchSaleStatus() {
    return ArrayUtil.immutableCopy(searchSaleStatus);
  }

  /**
   * searchSaleStatusを設定します。
   * 
   * @param searchSaleStatus
   *          searchSaleStatus
   */
  public void setSearchSaleStatus(String[] searchSaleStatus) {
    this.searchSaleStatus = ArrayUtil.immutableCopy(searchSaleStatus);
  }

  /**
   * searchSaleTypeを取得します。
   * 
   * @return searchSaleType
   */

  public String getSearchSaleType() {
    return searchSaleType;
  }

  /**
   * searchSaleTypeを設定します。
   * 
   * @param searchSaleType
   *          searchSaleType
   */
  public void setSearchSaleType(String searchSaleType) {
    this.searchSaleType = searchSaleType;
  }

  /**
   * notSafetyStockを取得します。
   * 
   * @return notSafetyStock
   */
  public boolean isNotSafetyStock() {
    return notSafetyStock;
  }

  /**
   * notSafetyStockを設定します。
   * 
   * @param notSafetyStock
   *          notSafetyStock
   */
  public void setNotSafetyStock(boolean notSafetyStock) {
    this.notSafetyStock = notSafetyStock;
  }


  
  /**
   * @return the choseType
   */
  public String getChoseType() {
    return choseType;
  }


  
  /**
   * @param choseType the choseType to set
   */
  public void setChoseType(String choseType) {
    this.choseType = choseType;
  }

}
