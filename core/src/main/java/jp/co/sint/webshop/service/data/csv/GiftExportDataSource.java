package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.utility.StringUtil;

public class GiftExportDataSource extends SqlExportDataSource<GiftCsvSchema, GiftExportCondition> {

  @Override
  public Query getExportQuery() {
    String shopCode = getCondition().getShopCode();
    String sql = //DatabaseUtil.getSelectAllQuery(Gift.class);
    " SELECT "
    + " shop_code, "
    + " gift_code, "
    + " gift_name, "
    + " gift_description, "
    + " gift_price, "
    + " display_order, "
    + " display_flg, "
    + " orm_rowid, "
    + " created_user, "
    + " created_datetime, "
    + " updated_user, "
    + " updated_datetime "
    + " FROM GIFT ";
    Query q = null;

    if (StringUtil.hasValue(shopCode)) {
      sql += " WHERE SHOP_CODE = ? ORDER BY SHOP_CODE, GIFT_CODE ";
      q = new SimpleQuery(sql, shopCode);
    } else {
      sql += " ORDER BY SHOP_CODE, GIFT_CODE ";
      q = new SimpleQuery(sql);
    }
    return q;
  }
}
