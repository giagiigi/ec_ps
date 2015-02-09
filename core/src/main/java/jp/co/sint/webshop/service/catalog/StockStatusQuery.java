package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

public final class StockStatusQuery implements Serializable {
  
  /**
   * default constructor
   */
  private StockStatusQuery() {
  }
  
  /**
   * serial version UID
   */
  private static final long serialVersionUID = 1L;
  
  private static final String BASE_QUERY = " SELECT a.SHOP_CODE, "
    + " a.STOCK_STATUS_NO, "
    + " a.STOCK_STATUS_NAME, "
    + " a.STOCK_SUFFICIENT_MESSAGE, "
    + " a.STOCK_LITTLE_MESSAGE, "
    + " a.OUT_OF_STOCK_MESSAGE, "
    + " a.STOCK_SUFFICIENT_THRESHOLD, "
    + " a.ORM_ROWID, "
    + " a.CREATED_USER, "
    + " a.CREATED_DATETIME,"
    + " a.UPDATED_USER, "
    + " a.UPDATED_DATETIME, "
    + " b.RELATED_COUNT "
    + " FROM   STOCK_STATUS a LEFT OUTER JOIN "
    + "        (SELECT SHOP_CODE, STOCK_STATUS_NO, COUNT(STOCK_STATUS_NO) AS RELATED_COUNT "
    + "         FROM COMMODITY_HEADER"
    + " GROUP BY SHOP_CODE, STOCK_STATUS_NO) b "
    + " ON b.SHOP_CODE = a.SHOP_CODE AND "
    + " b.STOCK_STATUS_NO = a.STOCK_STATUS_NO "
    + " WHERE a.SHOP_CODE = ? ";
  
  private static final String SORT_CONDITION = " ORDER BY a.ORM_ROWID ";
  

  public static String getStockStatusListQuery() {
    return BASE_QUERY + SORT_CONDITION;
  }
  
  public static String getStockStatusQuery() {
    return BASE_QUERY + " AND a.STOCK_STATUS_NO = ? " + SORT_CONDITION;
  }
  
  
}
