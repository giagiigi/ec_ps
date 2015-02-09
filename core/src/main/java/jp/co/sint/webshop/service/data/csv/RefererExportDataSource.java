package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.RefererSearchQuery;

public class RefererExportDataSource extends SqlExportDataSource<RefererCsvSchema, RefererExportCondition> {

  @Override
  public Query getExportQuery() {
    return new RefererSearchQuery(getCondition().getSearchCondition());
  }

}
