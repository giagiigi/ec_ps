package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.utility.StringUtil;

public class CommodityDetailExportDataSource extends SqlExportDataSource<CommodityDetailCsvSchema, CommodityDetailExportCondition> {

  public Query getExportQuery() {

    String shopCode = getCondition().getShopCode();
    String sql = ""
      + " SELECT CD.SHOP_CODE "
      + " , CD.SKU_CODE "
      + " , CD.COMMODITY_CODE "
      + " , CD.UNIT_PRICE "
      + " , CD.DISCOUNT_PRICE "
      + " , CD.RESERVATION_PRICE "
      + " , CD.CHANGE_UNIT_PRICE "
      + " , CD.JAN_CODE "
      + " , CD.DISPLAY_ORDER "
      + " , CD.STANDARD_DETAIL1_NAME "
      + " , CD.STANDARD_DETAIL2_NAME "
      + " , ST.RESERVATION_LIMIT "
      + " , ST.ONESHOT_RESERVATION_LIMIT "
      + " , ST.STOCK_THRESHOLD "
      + " , CD.ORM_ROWID "
      + " , CD.CREATED_USER "
      + " , CD.CREATED_DATETIME "
      + " , CD.UPDATED_USER "
      + " , CD.UPDATED_DATETIME "
      + " FROM COMMODITY_DETAIL CD "
      + " INNER JOIN STOCK ST "
      + " ON ST.SHOP_CODE = CD.SHOP_CODE "
      + " AND ST.SKU_CODE = CD.SKU_CODE ";
    Query q = null;

    if (StringUtil.hasValue(shopCode)) {
      sql += " WHERE CD.SHOP_CODE = ? ORDER BY SHOP_CODE, COMMODITY_CODE, SKU_CODE ";
      q = new SimpleQuery(sql, shopCode);
    } else {
      sql += " ORDER BY SHOP_CODE, COMMODITY_CODE, SKU_CODE ";
      q = new SimpleQuery(sql);
    }
    return q;

  }
}
