package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.catalog.CommodityListSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class CommodityDataExportCondition extends CsvConditionImpl<CommodityDataCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private CommodityListSearchCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public CommodityListSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(CommodityListSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
