package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.SalesAmountQuery;

public class SalesAmountExportDataSource extends SqlExportDataSource<SalesAmountCsvSchema, SalesAmountExportCondition> {

  @Override
  public Query getExportQuery() {
    return new SalesAmountQuery(getCondition().getRange(), getCondition().isSite());
  }

}
