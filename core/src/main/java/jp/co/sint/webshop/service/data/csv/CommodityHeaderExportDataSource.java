package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.utility.StringUtil;

public class CommodityHeaderExportDataSource extends SqlExportDataSource<CommodityHeaderCsvSchema, CommodityHeaderExportCondition> {

  public Query getExportQuery() {

    String shopCode = getCondition().getShopCode();
    String sql = ""
      + " SELECT CH.SHOP_CODE "
      + " , CH.COMMODITY_CODE "
      + " , CH.COMMODITY_NAME "
      + " , CH.REPRESENT_SKU_CODE "
      + " , CH.STOCK_STATUS_NO "
      + " , CH.STOCK_MANAGEMENT_TYPE "
//    delete by V10-CH 170 start
//      + " , CH.COMMODITY_TAX_TYPE "
//    delete by V10-CH 170 end
      + " , CH.COMMODITY_DESCRIPTION_PC "
      + " , CH.COMMODITY_DESCRIPTION_MOBILE "
      + " , CH.COMMODITY_SEARCH_WORDS "
      + " , CH.SHADOW_SEARCH_WORDS "
      + " , CH.SALE_START_DATETIME "
      + " , CH.SALE_END_DATETIME "
      + " , CH.CHANGE_UNIT_PRICE_DATETIME "
      + " , CH.DISCOUNT_PRICE_START_DATETIME "
      + " , CH.DISCOUNT_PRICE_END_DATETIME "
      + " , CH.RESERVATION_START_DATETIME "
      + " , CH.RESERVATION_END_DATETIME "
      + " , CH.DELIVERY_TYPE_NO "
      + " , CH.LINK_URL "
      + " , CASE "
      + "     WHEN 9999 < CH.RECOMMEND_COMMODITY_RANK THEN NULL "
      + "     ELSE CH.RECOMMEND_COMMODITY_RANK "
      + "   END AS RECOMMEND_COMMODITY_RANK "
      + " , CH.COMMODITY_STANDARD1_NAME "
      + " , CH.COMMODITY_STANDARD2_NAME "
      + " , CH.COMMODITY_POINT_RATE "
      + " , CH.COMMODITY_POINT_START_DATETIME "
      + " , CH.COMMODITY_POINT_END_DATETIME "
      + " , CH.SALE_FLG "
      + " , CH.DISPLAY_CLIENT_TYPE "
      + " , CH.ARRIVAL_GOODS_FLG "
      + " , CD.UNIT_PRICE "
      + " , CD.DISCOUNT_PRICE "
      + " , CD.RESERVATION_PRICE "
      + " , CD.CHANGE_UNIT_PRICE "
      + " , CD.JAN_CODE "
      + " , ST.RESERVATION_LIMIT "
      + " , ST.ONESHOT_RESERVATION_LIMIT "
      + " , ST.STOCK_THRESHOLD "
      + " , CH.ORM_ROWID "
      + " , CH.CREATED_USER "
      + " , CH.CREATED_DATETIME "
      + " , CH.UPDATED_USER "
      + " , CH.UPDATED_DATETIME "
      + " FROM COMMODITY_HEADER CH "
      + " INNER JOIN COMMODITY_DETAIL CD "
      + " ON CD.SHOP_CODE = CH.SHOP_CODE "
      + " AND CD.COMMODITY_CODE = CH.COMMODITY_CODE "
      + " AND CD.SKU_CODE = CH.REPRESENT_SKU_CODE "
      + " INNER JOIN STOCK ST "
      + " ON ST.SHOP_CODE = CD.SHOP_CODE "
      + " AND ST.SKU_CODE = CD.SKU_CODE ";

    Query q = null;

    if (StringUtil.hasValue(shopCode)) {
      sql += " WHERE CH.SHOP_CODE = ? ORDER BY SHOP_CODE, COMMODITY_CODE";
      q = new SimpleQuery(sql, shopCode);
    } else {
      sql += " ORDER BY SHOP_CODE, COMMODITY_CODE";
      q = new SimpleQuery(sql);
    }
    return q;

  }
}
