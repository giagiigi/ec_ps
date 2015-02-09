package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.CommodityAccessLogSearchQuery;

public class CommodityAccessLogExportDataSource extends
    SqlExportDataSource<CommodityAccessLogCsvSchema, CommodityAccessLogExportCondition> {

  @Override
  public Query getExportQuery() {
    return new CommodityAccessLogSearchQuery(getCondition().getSearchCondition());
  }

}
