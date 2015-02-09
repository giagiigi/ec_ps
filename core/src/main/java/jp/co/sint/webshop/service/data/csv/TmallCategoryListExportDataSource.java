package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.catalog.TmallCategoryListExportQuery;

public class TmallCategoryListExportDataSource extends SqlExportDataSource<TmallCategoryListCsvSchema, TmallCategoryListExportCondition> {

  @Override
  public Query getExportQuery() {
    return new TmallCategoryListExportQuery(getCondition().getSearchCondition());
  }
}
