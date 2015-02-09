package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.service.data.csv.JdCategoryListExportCondition;
/**
 * 2014/04/25 京东WBS对应 ob_卢 add 
 */
public class JdCategoryListExportQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public JdCategoryListExportQuery(JdCategoryListExportCondition condition) {
    StringBuilder builder = new StringBuilder();
       
      
    builder.append(" SELECT ");
    builder.append("  JC.CATEGORY_ID, ");
    builder.append("  JC.CATEGORY_NAME, ");
    builder.append("  GET_JD_PARENT_NAME_FUNC(CATEGORY_ID) AS PARENT_NAME ");
    builder.append(" FROM ");
    builder.append(" JD_CATEGORY JC");
    builder.append("  WHERE JC.IS_PARENT = '0'");

    setSqlString(builder.toString());
  }

}
