package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.analysis.NewPublicCouponDetailsCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class NewPublicCouponDetailsExportCondition extends CsvConditionImpl<NewPublicCouponDetailsCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private NewPublicCouponDetailsCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public NewPublicCouponDetailsCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(NewPublicCouponDetailsCondition searchCondition) {
    this.searchCondition = searchCondition;
  }
}
