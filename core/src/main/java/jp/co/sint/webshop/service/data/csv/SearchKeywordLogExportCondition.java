package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.analysis.SearchKeywordLogSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class SearchKeywordLogExportCondition extends CsvConditionImpl<SearchKeywordLogCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private SearchKeywordLogSearchCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public SearchKeywordLogSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(SearchKeywordLogSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
