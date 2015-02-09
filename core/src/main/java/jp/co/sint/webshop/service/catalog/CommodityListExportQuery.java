package jp.co.sint.webshop.service.catalog;


public class CommodityListExportQuery extends CommodityListSearchQuery {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static final String COMMODITY_BASE_COLUMNS = ""
    + " SELECT    CH.SHOP_CODE " 
    + "   ,CH.COMMODITY_CODE " 
    + "   ,CH.COMMODITY_NAME "
    + "   ,CH.REPRESENT_SKU_CODE " 
    + "   ,CH.STOCK_STATUS_NO "
    + "   ,CH.STOCK_MANAGEMENT_TYPE "
//  delete by V10-CH 170 start
//    + "   ,CH.COMMODITY_TAX_TYPE " 
//  delete by V10-CH 170 start
    + "   ,CH.COMMODITY_DESCRIPTION_PC "
    + "   ,CH.COMMODITY_DESCRIPTION_MOBILE"
    + "   ,CH.COMMODITY_SEARCH_WORDS " 
    + "   ,CH.SALE_START_DATETIME " 
    + "   ,CH.SALE_END_DATETIME "
    + "   ,CH.CHANGE_UNIT_PRICE_DATETIME "
    + "   ,CH.DISCOUNT_PRICE_START_DATETIME " 
    + "   ,CH.DISCOUNT_PRICE_END_DATETIME " 
    + "   ,CH.RESERVATION_START_DATETIME " 
    + "   ,CH.RESERVATION_END_DATETIME " 
    + "   ,CH.DELIVERY_TYPE_NO "
    + "   ,CH.LINK_URL " 
    // 10.1.4 10148 修正 ここから
    // + "   ,CH.RECOMMEND_COMMODITY_RANK "
    + "   ,CASE "
    + "      WHEN 9999 < CH.RECOMMEND_COMMODITY_RANK THEN NULL "
    + "      ELSE CH.RECOMMEND_COMMODITY_RANK "
    + "    END AS RECOMMEND_COMMODITY_RANK "
    // 10.1.4 10148 修正 ここまで
    + "   ,CH.COMMODITY_STANDARD1_NAME "
    + "   ,CH.COMMODITY_STANDARD2_NAME "
    + "   ,CH.COMMODITY_POINT_RATE " 
    + "   ,CH.COMMODITY_POINT_START_DATETIME "
    + "   ,CH.COMMODITY_POINT_END_DATETIME "
    + "   ,CH.SALE_FLG " 
    + "   ,CH.DISPLAY_CLIENT_TYPE " 
    + "   ,CH.ARRIVAL_GOODS_FLG  ";

  private static final String EXPORT_SELECT_CLAUSE = ""
      + COMMODITY_BASE_COLUMNS
      + "   ,CD.SKU_CODE"
      + "   ,CD.UNIT_PRICE"
      + "   ,CD.DISCOUNT_PRICE"
      + "   ,CD.RESERVATION_PRICE"
      + "   ,CD.CHANGE_UNIT_PRICE"
      + "   ,CD.JAN_CODE"
      + "   ,CD.DISPLAY_ORDER"
      + "   ,CD.STANDARD_DETAIL1_NAME"
      + "   ,CD.STANDARD_DETAIL2_NAME"
      + "   ,ST.STOCK_QUANTITY"
      + "   ,ST.ALLOCATED_QUANTITY"
      + "   ,ST.RESERVED_QUANTITY"
      + "   ,ST.RESERVATION_LIMIT"
      + "   ,ST.ONESHOT_RESERVATION_LIMIT"
      + "   ,ST.STOCK_THRESHOLD";
      
  private static final String QUERY_PART_CLAUSE = ""
    + " INNER JOIN COMMODITY_DETAIL CD "
    + " ON CD.SHOP_CODE = CH.SHOP_CODE "
    + " AND CD.COMMODITY_CODE = CH.COMMODITY_CODE "
    + " INNER JOIN STOCK ST "
    + " ON ST.SHOP_CODE = CD.SHOP_CODE "
    + " AND ST.SKU_CODE = CD.SKU_CODE ";

  public CommodityListExportQuery(CommodityListSearchCondition condition) {
    super(condition, COMMODITY_BASE_COLUMNS);
    StringBuilder builder = new StringBuilder();
    builder.append(EXPORT_SELECT_CLAUSE);
    builder.append(" FROM ( ");
    builder.append(getSqlString());
    builder.append("      ) CH ");
    builder.append(QUERY_PART_CLAUSE);
    this.setSqlString(builder.toString());
  }

}
