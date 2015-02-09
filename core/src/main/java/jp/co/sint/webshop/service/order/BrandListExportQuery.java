package jp.co.sint.webshop.service.order;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.service.data.csv.BrandListExportCondition;

public class BrandListExportQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public BrandListExportQuery(BrandListExportCondition condition) {
    StringBuilder builder = new StringBuilder();
    builder.append(" SELECT ");
    builder.append("   B.BRAND_CODE, ");
    builder.append("   B.BRAND_NAME, ");
    builder.append("   B.BRAND_ENGLISH_NAME, ");
    builder.append("   B.TMALL_BRAND_CODE ");
    builder.append(" FROM ");
    builder.append("   BRAND B");

    setSqlString(builder.toString());
  }

}
