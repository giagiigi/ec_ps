package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.analysis.NewPublicCouponSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class NewPublicCouponExportCondition extends CsvConditionImpl<NewPublicCouponCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private NewPublicCouponSearchCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public NewPublicCouponSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(NewPublicCouponSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }
}
