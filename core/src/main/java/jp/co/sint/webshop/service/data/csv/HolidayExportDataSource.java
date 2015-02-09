package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.dto.Holiday;
import jp.co.sint.webshop.utility.StringUtil;

public class HolidayExportDataSource extends SqlExportDataSource<HolidayCsvSchema, HolidayExportCondition> {

  public Query getExportQuery() {

    String shopCode = getCondition().getShopCode();
    String sql = DatabaseUtil.getSelectAllQuery(Holiday.class);
    Query q = null;

    if (StringUtil.hasValue(shopCode)) {
      sql += " WHERE SHOP_CODE = ? ORDER BY HOLIDAY ";
      q = new SimpleQuery(sql, shopCode);
    } else {
      sql += " ORDER BY HOLIDAY ";
      q = new SimpleQuery(sql);
    }
    return q;

  }
}
