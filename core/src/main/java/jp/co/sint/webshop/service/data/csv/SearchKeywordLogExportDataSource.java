package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.SearchKeywordLogSearchQuery;

public class SearchKeywordLogExportDataSource extends
    SqlExportDataSource<SearchKeywordLogCsvSchema, SearchKeywordLogExportCondition> {

  @Override
  public Query getExportQuery() {
    return new SearchKeywordLogSearchQuery(getCondition().getSearchCondition());
  }

}
