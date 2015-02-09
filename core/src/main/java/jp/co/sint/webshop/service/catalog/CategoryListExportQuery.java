package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.service.data.csv.CategoryListExportCondition;

public class CategoryListExportQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public CategoryListExportQuery(CategoryListExportCondition condition) {
    StringBuilder builder = new StringBuilder();
      
    builder.append("SELECT ");
    builder.append("  C.CATEGORY_CODE, ");
    builder.append("  C.CATEGORY_NAME_PC, ");
    builder.append("  GET_PARENT_NAME_EC_FUNC(C.CATEGORY_CODE) AS PARENT_NAME ");
    builder.append(" FROM ");
    builder.append(" CATEGORY C");
    builder.append("  WHERE DEPTH > 2");

    setSqlString(builder.toString());
  }

}
