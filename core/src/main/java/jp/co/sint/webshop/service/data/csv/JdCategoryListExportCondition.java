package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;
/**
 * 2014/04/25 京东WBS对应 ob_卢 add 
 */
public class JdCategoryListExportCondition extends CsvConditionImpl<JdCategoryListCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private JdCategoryListExportCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public JdCategoryListExportCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(JdCategoryListExportCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
