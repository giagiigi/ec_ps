package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.analysis.CustomerPreferenceSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class CustomerPreferenceExportCondition extends CsvConditionImpl<CustomerPreferenceCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private CustomerPreferenceSearchCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public CustomerPreferenceSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(CustomerPreferenceSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }
}
