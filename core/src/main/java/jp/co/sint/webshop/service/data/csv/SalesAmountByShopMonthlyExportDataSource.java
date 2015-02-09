package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.SalesAmountByShopQuery;

public class SalesAmountByShopMonthlyExportDataSource extends
    SqlExportDataSource<SalesAmountByShopMonthlyCsvSchema, SalesAmountByShopMonthlyExportCondition> {

  @Override
  public Query getExportQuery() {
    return new SalesAmountByShopQuery(getCondition().getSearchCondition());
  }

}
