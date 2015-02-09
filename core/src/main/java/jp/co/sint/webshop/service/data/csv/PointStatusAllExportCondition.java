package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.customer.PointStatusListSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class PointStatusAllExportCondition extends CsvConditionImpl<PointStatusAllCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private PointStatusListSearchCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public PointStatusListSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(PointStatusListSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
