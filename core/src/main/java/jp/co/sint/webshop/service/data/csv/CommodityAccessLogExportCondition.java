package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.analysis.CommodityAccessLogSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class CommodityAccessLogExportCondition extends CsvConditionImpl<CommodityAccessLogCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private CommodityAccessLogSearchCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public CommodityAccessLogSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(CommodityAccessLogSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
