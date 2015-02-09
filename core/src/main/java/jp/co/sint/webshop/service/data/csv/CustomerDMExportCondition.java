package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class CustomerDMExportCondition extends CsvConditionImpl<CustomerDMCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private CustomerSearchCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public CustomerSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(CustomerSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
