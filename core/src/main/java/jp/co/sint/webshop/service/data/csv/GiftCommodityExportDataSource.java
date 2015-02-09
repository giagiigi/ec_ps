package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.dto.GiftCommodity;
import jp.co.sint.webshop.utility.StringUtil;

public class GiftCommodityExportDataSource extends SqlExportDataSource<GiftCommodityCsvSchema, GiftCommodityExportCondition> {

  @Override
  public Query getExportQuery() {
    String shopCode = getCondition().getShopCode();
    String sql = DatabaseUtil.getSelectAllQuery(GiftCommodity.class);
    Query q = null;

    if (StringUtil.hasValue(shopCode)) {
      sql += " WHERE SHOP_CODE = ? ORDER BY SHOP_CODE, GIFT_CODE, COMMODITY_CODE ";
      q = new SimpleQuery(sql, shopCode);
    } else {
      sql += " ORDER BY SHOP_CODE, GIFT_CODE, COMMODITY_CODE ";
      q = new SimpleQuery(sql);
    }
    return q;
  }
}
