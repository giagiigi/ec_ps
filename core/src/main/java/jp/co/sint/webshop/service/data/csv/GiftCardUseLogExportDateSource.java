package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.GiftCardUseLogSearchQuery;

public class GiftCardUseLogExportDateSource extends SqlExportDataSource<GiftCardUseLogCsvSchema, GiftCardUseLogExportCondition> {

  public Query getExportQuery() {
    return new GiftCardUseLogSearchQuery(getCondition().getSearchCondition());
  }
}
