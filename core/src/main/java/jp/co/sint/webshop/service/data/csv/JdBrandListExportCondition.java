package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;
/**
 * 2014/04/25 京东WBS对应 ob_卢 add 
 */
public class JdBrandListExportCondition extends CsvConditionImpl<JdBrandListCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private JdBrandListExportCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public JdBrandListExportCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(JdBrandListExportCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
