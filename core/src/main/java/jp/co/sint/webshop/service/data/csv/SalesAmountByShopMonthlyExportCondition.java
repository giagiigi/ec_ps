package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.analysis.SalesAmountByShopSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class SalesAmountByShopMonthlyExportCondition extends CsvConditionImpl<SalesAmountByShopMonthlyCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private SalesAmountByShopSearchCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public SalesAmountByShopSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(SalesAmountByShopSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }
}
