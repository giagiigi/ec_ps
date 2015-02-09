package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.analysis.RefererSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class RefererExportCondition extends CsvConditionImpl<RefererCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private RefererSearchCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public RefererSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(RefererSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
