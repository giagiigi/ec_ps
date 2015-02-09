package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.order.TmallBrandListExportQuery;

public class TmallBrandListExportDataSource extends SqlExportDataSource<TmallBrandListCsvSchema, TmallBrandListExportCondition> {

  @Override
  public Query getExportQuery() {
    return new TmallBrandListExportQuery(getCondition().getSearchCondition());
  }

}
