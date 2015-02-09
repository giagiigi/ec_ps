package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

public class TagSearchQuery extends AbstractQuery<TagCount> {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   *
   */
  public TagSearchQuery createTagSearchQuery(TagSearchCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append(" SELECT A.SHOP_CODE,"
        + "        A.TAG_CODE,"
        + "        A.TAG_NAME,"
        //20120514 tuxinwei add start
        + "        A.TAG_NAME_EN,"
        + "        A.TAG_NAME_JP,"
        //20120514 tuxinwei add end
        + "        A.DISPLAY_ORDER,"
        + "        A.ORM_ROWID,"
        + "        A.CREATED_USER,"
        + "        A.CREATED_DATETIME,"
        + "        A.UPDATED_USER,"
        + "        A.UPDATED_DATETIME,"
        + "        B.RELATED_COUNT"
        + " FROM   TAG A LEFT OUTER JOIN"
        + "        (SELECT SHOP_CODE, TAG_CODE, COUNT(TAG_CODE) AS RELATED_COUNT"
        + "         FROM TAG_COMMODITY"
        + "         WHERE SHOP_CODE = ? "
        + "         GROUP BY SHOP_CODE, TAG_CODE) B"
        + "           ON A.SHOP_CODE = B.SHOP_CODE AND"
        + "          A.TAG_CODE = B.TAG_CODE"
        + " WHERE A.SHOP_CODE = ? ");
    params.add(condition.getShopCode());
    params.add(condition.getShopCode());

    if (StringUtil.hasValue(condition.getSearchTagCodeStart()) && StringUtil.hasValue(condition.getSearchTagCodeEnd())) {
      builder.append(" AND A.TAG_CODE BETWEEN ? AND ?");
      params.add(condition.getSearchTagCodeStart());
      params.add(condition.getSearchTagCodeEnd());
    } else if (StringUtil.hasValue(condition.getSearchTagCodeStart())) {
      builder.append(" AND A.TAG_CODE >= ?");
      params.add(condition.getSearchTagCodeStart());
    } else if (StringUtil.hasValue(condition.getSearchTagCodeEnd())) {
      builder.append(" AND A.TAG_CODE <= ?");
      params.add(condition.getSearchTagCodeEnd());
    }
    if (StringUtil.hasValue(condition.getSearchTagName())) {
      builder.append(" AND ");
      builder.append(SqlDialect.getDefault().getLikeClausePattern("A.TAG_NAME"));
      String commodityName = SqlDialect.getDefault().escape(condition.getSearchTagName());
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

  public Class<TagCount> getRowType() {
    return TagCount.class;
  }
}
