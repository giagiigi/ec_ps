package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.service.data.csv.TmallCategoryListExportCondition;

public class TmallCategoryListExportQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public TmallCategoryListExportQuery(TmallCategoryListExportCondition condition) {
    StringBuilder builder = new StringBuilder();
       
      
    builder.append(" SELECT ");
    builder.append("  TC.CATEGORY_CODE, ");
    builder.append("  TC.CATEGORY_NAME, ");
    builder.append("  GET_PARENT_NAME_FUNC(CATEGORY_CODE) AS PARENT_NAME ");
    builder.append(" FROM ");
    builder.append(" TMALL_CATEGORY TC");
    builder.append("  WHERE TC.IS_PARENT = '0'");

    setSqlString(builder.toString());
  }

}
