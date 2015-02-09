package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.GiftCardLogExportQuery;

public class GiftCardLogExportDateSource extends SqlExportDataSource<GiftCardLogCsvSchema, GiftCardLogExportCondition> {

  public Query getExportQuery() {
    return new GiftCardLogExportQuery(getCondition().getSearchCondition());
  }

}
