package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class CategoryListExportCondition extends CsvConditionImpl<CategoryListCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private CategoryListExportCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public CategoryListExportCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(CategoryListExportCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
