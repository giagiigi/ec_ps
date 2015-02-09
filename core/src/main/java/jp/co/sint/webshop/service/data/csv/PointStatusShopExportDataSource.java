package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.customer.PointStatusShopQuery;

public class PointStatusShopExportDataSource extends SqlExportDataSource<PointStatusShopCsvSchema, PointStatusShopExportCondition> {

  @Override
  public Query getExportQuery() {
    return new PointStatusShopQuery(getCondition().getSearchCondition(), PointStatusShopQuery.LOAD_POINT_STATUS_SHOP_QUERY);
  }

}
