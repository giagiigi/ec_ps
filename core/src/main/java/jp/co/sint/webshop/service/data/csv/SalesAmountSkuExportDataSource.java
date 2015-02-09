package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.SalesAmountBySkuSearchQuery;

public class SalesAmountSkuExportDataSource extends SqlExportDataSource<SalesAmountSkuCsvSchema, SalesAmountSkuExportCondition> {

  @Override
  public Query getExportQuery() {
    return new SalesAmountBySkuSearchQuery(getCondition().getSearchCondition());
  }

}
