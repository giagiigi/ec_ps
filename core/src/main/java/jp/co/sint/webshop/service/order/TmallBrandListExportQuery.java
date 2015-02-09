package jp.co.sint.webshop.service.order;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.service.data.csv.TmallBrandListExportCondition;

public class TmallBrandListExportQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public TmallBrandListExportQuery(TmallBrandListExportCondition condition) {
    StringBuilder builder = new StringBuilder();
    builder.append(" SELECT ");
    builder.append("   TB.TMALL_BRAND_CODE, ");
    builder.append("   TB.TMALL_BRAND_NAME ");
    builder.append(" FROM ");
    builder.append("   TMALL_BRAND TB");

    setSqlString(builder.toString());
  }

}
