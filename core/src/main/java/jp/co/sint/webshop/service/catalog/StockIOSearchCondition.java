package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.ArrayUtil;

public class StockIOSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String searchShopCode;

  private String searchSkuCode;

  private String searchCommodityName;

  private String searchStockIODateStart;

  private String searchStockIODateEnd;

  private String[] stockIOType = new String[0];

  /**
   * @return searchCommodityName
   */
  public String getSearchCommodityName() {
    return searchCommodityName;
  }

  /**
   * @param searchCommodityName
   *          設定する searchCommodityName
   */
  public void setSearchCommodityName(String searchCommodityName) {
    this.searchCommodityName = searchCommodityName;
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

  /**
   * @return searchStockIODateEnd
   */
  public String getSearchStockIODateEnd() {
    return searchStockIODateEnd;
  }

  /**
   * @param searchStockIODateEnd
   *          設定する searchStockIODateEnd
   */
  public void setSearchStockIODateEnd(String searchStockIODateEnd) {
    this.searchStockIODateEnd = searchStockIODateEnd;
  }

  /**
   * @return searchStockIODateStart
   */
  public String getSearchStockIODateStart() {
    return searchStockIODateStart;
  }

  /**
   * @param searchStockIODateStart
   *          設定する searchStockIODateStart
   */
  public void setSearchStockIODateStart(String searchStockIODateStart) {
    this.searchStockIODateStart = searchStockIODateStart;
  }

  /**
   * @param searchSkuCode
   *          設定する searchSkuCode
   */
  public void setSearchSkuCode(String searchSkuCode) {
    this.searchSkuCode = searchSkuCode;
  }

  /**
   * @return searchSkuCode
   */
  public String getSearchSkuCode() {
    return searchSkuCode;
  }

  /**
   * @param stockIOType
   *          設定する stockIOType
   */
  public void setStockIOType(String... stockIOType) {
    this.stockIOType = ArrayUtil.immutableCopy(stockIOType);
  }

  /**
   * @return stockIOType
   */
  public String[] getStockIOType() {
    return ArrayUtil.immutableCopy(stockIOType);
  }

}
