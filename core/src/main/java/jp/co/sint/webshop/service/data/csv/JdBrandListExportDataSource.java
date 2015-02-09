package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.order.JdBrandListExportQuery;

/**
 * 2014/04/25 京东WBS对应 ob_卢 add 
 */
public class JdBrandListExportDataSource extends SqlExportDataSource<JdBrandListCsvSchema, JdBrandListExportCondition> {

  @Override
  public Query getExportQuery() {
    return new JdBrandListExportQuery(getCondition().getSearchCondition());
  }

}
