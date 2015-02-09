package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class BrandListExportCondition extends CsvConditionImpl<BrandListCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private BrandListExportCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public BrandListExportCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(BrandListExportCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
