package jp.co.sint.webshop.service.order;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.service.data.csv.JdBrandListExportCondition;
/**
 * 2014/04/25 京东WBS对应 ob_卢 add 
 */
public class JdBrandListExportQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public JdBrandListExportQuery(JdBrandListExportCondition condition) {
    StringBuilder builder = new StringBuilder();
    builder.append(" SELECT ");
    builder.append("   JB.JD_BRAND_CODE, ");
    builder.append("   JB.JD_BRAND_NAME ");
    builder.append(" FROM ");
    builder.append("   JD_BRAND JB");

    setSqlString(builder.toString());
  }

}
