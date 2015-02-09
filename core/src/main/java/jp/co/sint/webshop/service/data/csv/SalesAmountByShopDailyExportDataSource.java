package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.SalesAmountByShopQuery;

public class SalesAmountByShopDailyExportDataSource extends
    SqlExportDataSource<SalesAmountByShopDailyCsvSchema, SalesAmountByShopDailyExportCondition> {

  @Override
  public Query getExportQuery() {
    return new SalesAmountByShopQuery(getCondition().getSearchCondition());
  }

}
