package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.CollectionUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

/**
 * 返品特約の有無を調べるクエリクラスです。
 * 10.1.4 K00171 新規追加
 * 
 * @author System Integrator Corp.
 */
public class ReturnPolicySetQuery extends SimpleQuery {

  private static final long serialVersionUID = 1L;

  private HashSet<Sku> skuSet = new HashSet<Sku>();

  private boolean isMobile = false;

  public ReturnPolicySetQuery() {
    initialize();
  }

  public ReturnPolicySetQuery(Set<Sku> skuSet, boolean isMobile) {
    CollectionUtil.copyAll(this.skuSet, skuSet);
    this.isMobile = isMobile;
    initialize();
  }

  private void initialize() {
    SqlDialect dialect = SqlDialect.getDefault();
    String columnName = "COMMODITY_DESCRIPTION_PC";
    if (isMobile) {
      columnName = "COMMODITY_DESCRIPTION_MOBILE";
    }
    SqlFragment fragment = dialect.createLikeClause(columnName,
        "<div class=\"returnPolicy\">", LikeClauseOption.PARTIAL_MATCH);

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    builder
        .append("SELECT CD.SHOP_CODE, CD.SKU_CODE FROM COMMODITY_DETAIL CD ");
    builder.append("INNER JOIN COMMODITY_HEADER CH ");
    builder
        .append("ON CH.SHOP_CODE = CD.SHOP_CODE AND CH.COMMODITY_CODE = CD.COMMODITY_CODE ");
    builder.append("WHERE (");
    for (Sku s : skuSet) {
      builder.append(" (CD.SHOP_CODE = ? AND CD.SKU_CODE = ?) ");
      params.add(s.getShopCode());
      params.add(s.getSkuCode());
      builder.append("OR ");
    }
    builder.append(" 1 = 0 )");
//    builder.append("AND ");
//    builder.append(fragment.getFragment());
//    params.addAll(Arrays.asList(fragment.getParameters()));

    this.setSqlString(builder.toString());
    this.setParameters(params.toArray());
  }
}
