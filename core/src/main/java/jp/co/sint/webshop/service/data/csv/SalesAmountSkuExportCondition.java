package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.analysis.SalesAmountBySkuSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class SalesAmountSkuExportCondition extends CsvConditionImpl<SalesAmountSkuCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private SalesAmountBySkuSearchCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public SalesAmountBySkuSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(SalesAmountBySkuSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }
}
