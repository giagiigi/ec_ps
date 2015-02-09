package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.catalog.CategoryListExportQuery;

public class CategoryListExportDataSource extends SqlExportDataSource<CategoryListCsvSchema, CategoryListExportCondition> {

  @Override
  public Query getExportQuery() {
    return new CategoryListExportQuery(getCondition().getSearchCondition());
  }
}
