package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.customer.CouponStatusSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class CouponStatusAllExportCondition extends CsvConditionImpl<CouponStatusAllCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private CouponStatusSearchCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public CouponStatusSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(CouponStatusSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
