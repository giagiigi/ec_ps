package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class CategoryAttributeHeaderQuery extends AbstractQuery<CategoryAttributeHeader> {

  /**
   */
  private static final long serialVersionUID = 1L;

  /**
   */
  public CategoryAttributeHeaderQuery() {

  }

  /**
   */
  public CategoryAttributeHeaderQuery createCategoryAttributeHeaderQuery(RelatedCategorySearchCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("" 
        + " SELECT A.CATEGORY_CODE "
        + "       ,A.CATEGORY_NAME_PC "
        + "       ,D.COMMODITY_CODE "
        + "       ,D.SHOP_CODE "
        + "       ,D.COMMODITY_NAME "
        + " FROM CATEGORY A  "
        + " LEFT OUTER JOIN CATEGORY_ATTRIBUTE B "
        + " ON B.CATEGORY_CODE = A.CATEGORY_CODE "
        + " INNER JOIN CATEGORY_COMMODITY C  "
        + " ON C.CATEGORY_CODE = A.CATEGORY_CODE  "
        + " INNER JOIN COMMODITY_HEADER D  "
        + " ON D.SHOP_CODE = C.SHOP_CODE "
        + " AND D.COMMODITY_CODE = C.COMMODITY_CODE "
        + " WHERE A.CATEGORY_CODE = ? ");
    params.add(condition.getCategoryCode());
    
    // ショップコード一致検索
    if (StringUtil.hasValue(condition.getShopCode())) {
      builder.append(" AND D.SHOP_CODE = ? ");
      params.add(condition.getShopCode());
    }
    
    // 商品コード範囲検索
    if (StringUtil.hasValue(condition.getSearchCommodityCodeStart()) 
        && StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND D.COMMODITY_CODE BETWEEN ? AND ?");
      params.add(condition.getSearchCommodityCodeStart());
      params.add(condition.getSearchCommodityCodeEnd());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeStart())) {
      builder.append(" AND D.COMMODITY_CODE >= ?");
      params.add(condition.getSearchCommodityCodeStart());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND D.COMMODITY_CODE <= ?");
      params.add(condition.getSearchCommodityCodeEnd());
    }

    // 商品名部分一致検索
    if (StringUtil.hasValue(condition.getSearchCommodityName())) {
      SqlFragment fragment = SqlDialect.getDefault().createLikeClause("D.COMMODITY_NAME", condition.getSearchCommodityName(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      for (Object o : fragment.getParameters()) {
        params.add(o);
      }
    }
    builder.append(""
        + " GROUP BY A.CATEGORY_CODE," 
        + "          A.CATEGORY_NAME_PC," 
        + "          D.SHOP_CODE,"
        + "          D.COMMODITY_CODE," 
        + "          D.COMMODITY_NAME");

    builder.append(" ORDER BY D.COMMODITY_CODE");

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
    return this;
  }

  public Class<CategoryAttributeHeader> getRowType() {
    return CategoryAttributeHeader.class;
  }
}
