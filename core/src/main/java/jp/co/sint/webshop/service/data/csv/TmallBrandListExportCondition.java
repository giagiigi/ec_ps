package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class TmallBrandListExportCondition extends CsvConditionImpl<TmallBrandListCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private TmallBrandListExportCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public TmallBrandListExportCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(TmallBrandListExportCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
