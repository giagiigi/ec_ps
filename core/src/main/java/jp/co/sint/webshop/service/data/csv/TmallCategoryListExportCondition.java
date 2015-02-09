package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class TmallCategoryListExportCondition extends CsvConditionImpl<TmallCategoryListCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private TmallCategoryListExportCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public TmallCategoryListExportCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(TmallCategoryListExportCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
