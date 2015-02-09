package jp.co.sint.webshop.service.catalog;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.Tag;
import jp.co.sint.webshop.data.dto.TagCommodity;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

public class RelatedSearchQueryTag extends AbstractQuery <RelatedTag> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 該当の商品コードに関連付いているタグの一覧を取得する
   */
  public RelatedSearchQueryTag createRelatedTagSearchQuery(RelatedSearchConditionBaseCommodity condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append(" SELECT   A.SHOP_CODE, "
        + "                   A.TAG_CODE, "
        + "                   A.TAG_NAME, "
        + "                   B.COMMODITY_CODE "
        + "          FROM     (" + DatabaseUtil.getSelectAllQuery(Tag.class)
        + "                    WHERE SHOP_CODE = ?) A "
        + "                      LEFT OUTER JOIN (" + DatabaseUtil.getSelectAllQuery(TagCommodity.class)
        + "                                       WHERE SHOP_CODE = ? AND  "
        + "                                             COMMODITY_CODE = ?) B "
        + "                      ON A.SHOP_CODE = B.SHOP_CODE AND "
        + "                         A.TAG_CODE  = B.TAG_CODE ");
    
    params.add(condition.getShopCode());
    params.add(condition.getShopCode());
    params.add(condition.getCommodityCode());
    
    builder.append(" WHERE 1 = 1 ");    
    if (StringUtil.hasValue(condition.getSearchEffectualCodeStart()) 
        && StringUtil.hasValue(condition.getSearchEffectualCodeEnd())) {
      builder.append(" AND A.TAG_CODE BETWEEN ? AND ?");
      params.add(condition.getSearchEffectualCodeStart());
      params.add(condition.getSearchEffectualCodeEnd());
    } else if (StringUtil.hasValue(condition.getSearchEffectualCodeStart())) {
      builder.append(" AND A.TAG_CODE >= ?");
      params.add(condition.getSearchEffectualCodeStart());
    } else if (StringUtil.hasValue(condition.getSearchEffectualCodeEnd())) {
      builder.append(" AND A.TAG_CODE <= ?");
      params.add(condition.getSearchEffectualCodeEnd());
    }
    if (StringUtil.hasValue(condition.getSearchEffectualName())) {
      builder.append(" AND ");
      builder.append(SqlDialect.getDefault().getLikeClausePattern("A.TAG_NAME"));
      String commodityName = SqlDialect.getDefault().escape(condition.getSearchEffectualName());
      commodityName = "%" + commodityName + "%";
      params.add(commodityName);
    }
    
    builder.append(" ORDER BY A.TAG_CODE");

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

    return this;
  }

  /**
   * 
   */
  public Class <RelatedTag> getRowType() {
    return RelatedTag.class;
  }
}
