package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.order.BrandListExportQuery;

public class BrandListExportDataSource extends SqlExportDataSource<BrandListCsvSchema, BrandListExportCondition> {

  @Override
  public Query getExportQuery() {
    return new BrandListExportQuery(getCondition().getSearchCondition());
  }

}
