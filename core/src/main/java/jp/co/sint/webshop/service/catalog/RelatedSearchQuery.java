package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

public class RelatedSearchQuery extends AbstractQuery <Related> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public RelatedSearchQuery() {

  }

  private static final String SORT_CONDITION = " ORDER BY A.COMMODITY_CODE";
  
  /**
   * 
   */
  public static String getRelatedTagCommodityList() {
    final String sql = " SELECT A.SHOP_CODE, "
                      + "        A.COMMODITY_CODE, "
                      + "        B.COMMODITY_NAME"
                      + " FROM   TAG_COMMODITY A, "
                      + "        COMMODITY_HEADER B"
                      + " WHERE  A.SHOP_CODE          = ? AND"
                      + "        A.TAG_CODE = ? AND"
                      + "        A.SHOP_CODE          = B.SHOP_CODE AND"
                      + "        A.COMMODITY_CODE     = B.COMMODITY_CODE"
                      + " ORDER BY A.COMMODITY_CODE";
    return sql;
  }

  /**
   * 
   */
  public RelatedSearchQuery createRelatedTagSearchQuery(RelatedSearchCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT   A.SHOP_CODE, "
        + "                    A.COMMODITY_CODE, "
        + "                    B.COMMODITY_NAME "
        + "           FROM     TAG_COMMODITY A, "
        + "                    COMMODITY_HEADER B "
        + "           WHERE    A.SHOP_CODE      = ? AND "
        + "                    A.TAG_CODE       = ? AND "
        + "                    A.SHOP_CODE      = B.SHOP_CODE AND "
        + "                    A.COMMODITY_CODE = B.COMMODITY_CODE ");
    params.add(condition.getShopCode());
    params.add(condition.getEffectualCode());
    if (StringUtil.hasValue(condition.getSearchCommodityCodeStart())
        && StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND A.COMMODITY_CODE BETWEEN ? AND ?");
      params.add(condition.getSearchCommodityCodeStart());
      params.add(condition.getSearchCommodityCodeEnd());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeStart())) {
      builder.append(" AND A.COMMODITY_CODE >= ?");
      params.add(condition.getSearchCommodityCodeStart());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND A.COMMODITY_CODE <= ?");
      params.add(condition.getSearchCommodityCodeEnd());
    }
    if (StringUtil.hasValue(condition.getSearchCommodityName())) {
      builder.append(" AND ");
      builder.append(SqlDialect.getDefault().getLikeClausePattern("B.COMMODITY_NAME"));
      String commodityName = SqlDialect.getDefault().escape(condition.getSearchCommodityName());
      commodityName = "%" + commodityName + "%";
      params.add(commodityName);
    }

    builder.append(SORT_CONDITION);

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

    return this;
  }

  /**
   * 
   */
  public RelatedSearchQuery createRelatedGiftSearchQuery(RelatedSearchCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT   A.SHOP_CODE, "
        + "                    A.COMMODITY_CODE, "
        + "                    B.COMMODITY_NAME "
        + "           FROM     GIFT_COMMODITY A, "
        + "                    COMMODITY_HEADER B "
        + "           WHERE    A.SHOP_CODE      = ? AND "
        + "                    A.GIFT_CODE      = ? AND "
        + "                    A.SHOP_CODE      = B.SHOP_CODE AND "
        + "                    A.COMMODITY_CODE = B.COMMODITY_CODE ");
    params.add(condition.getShopCode());
    params.add(condition.getEffectualCode());
    if (StringUtil.hasValue(condition.getSearchCommodityCodeStart())
        && StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND A.COMMODITY_CODE BETWEEN ? AND ?");
      params.add(condition.getSearchCommodityCodeStart());
      params.add(condition.getSearchCommodityCodeEnd());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeStart())) {
      builder.append(" AND A.COMMODITY_CODE >= ?");
      params.add(condition.getSearchCommodityCodeStart());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND A.COMMODITY_CODE <= ?");
      params.add(condition.getSearchCommodityCodeEnd());
    }
    if (StringUtil.hasValue(condition.getSearchCommodityName())) {
      builder.append(" AND ");
      builder.append(SqlDialect.getDefault().getLikeClausePattern("B.COMMODITY_NAME"));
      String commodityName = SqlDialect.getDefault().escape(condition.getSearchCommodityName());
      commodityName = "%" + commodityName + "%";
      params.add(commodityName);
    }

    builder.append(SORT_CONDITION);

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

    return this;
  }

  /**
   * 
   */
  public RelatedSearchQuery createRelatedCampaignSearchQuery(RelatedSearchCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT   A.SHOP_CODE, "
        + "                    A.COMMODITY_CODE, "
        + "                    B.COMMODITY_NAME "
        + "           FROM     CAMPAIGN_COMMODITY A, "
        + "                    COMMODITY_HEADER B "
        + "           WHERE    A.SHOP_CODE      = ? AND "
        + "                    A.CAMPAIGN_CODE  = ? AND "
        + "                    A.SHOP_CODE      = B.SHOP_CODE AND "
        + "                    A.COMMODITY_CODE = B.COMMODITY_CODE ");
    params.add(condition.getShopCode());
    params.add(condition.getEffectualCode());
    if (StringUtil.hasValue(condition.getSearchCommodityCodeStart())
        && StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND A.COMMODITY_CODE BETWEEN ? AND ?");
      params.add(condition.getSearchCommodityCodeStart());
      params.add(condition.getSearchCommodityCodeEnd());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeStart())) {
      builder.append(" AND A.COMMODITY_CODE >= ?");
      params.add(condition.getSearchCommodityCodeStart());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND A.COMMODITY_CODE <= ?");
      params.add(condition.getSearchCommodityCodeEnd());
    }
    if (StringUtil.hasValue(condition.getSearchCommodityName())) {
      builder.append(" AND ");
      builder.append(SqlDialect.getDefault().getLikeClausePattern("B.COMMODITY_NAME"));
      String commodityName = SqlDialect.getDefault().escape(condition.getSearchCommodityName());
      commodityName = "%" + commodityName + "%";
      params.add(commodityName);
    }

    builder.append(SORT_CONDITION);

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

    return this;
  }

  /**
   * 
   */
  public RelatedSearchQuery createRelatedCommodityASearchQuery(RelatedSearchCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT   A.SHOP_CODE, "
        + "                    A.LINK_COMMODITY_CODE AS COMMODITY_CODE, "
        + "                    A.DISPLAY_ORDER, "
        + "                    B.COMMODITY_NAME "
        + "           FROM     RELATED_COMMODITY_A A, "
        + "                    COMMODITY_HEADER B "
        + "           WHERE    A.SHOP_CODE      = ? AND "
        + "                    A.COMMODITY_CODE = ? AND "
        + "                    A.SHOP_CODE      = B.SHOP_CODE AND "
        + "                    A.LINK_COMMODITY_CODE = B.COMMODITY_CODE ");
    params.add(condition.getShopCode());
    params.add(condition.getEffectualCode());
    if (StringUtil.hasValue(condition.getSearchCommodityCodeStart())
        && StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND A.COMMODITY_CODE BETWEEN ? AND ?");
      params.add(condition.getSearchCommodityCodeStart());
      params.add(condition.getSearchCommodityCodeEnd());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeStart())) {
      builder.append(" AND A.COMMODITY_CODE >= ?");
      params.add(condition.getSearchCommodityCodeStart());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND A.COMMODITY_CODE <= ?");
      params.add(condition.getSearchCommodityCodeEnd());
    }
    if (StringUtil.hasValue(condition.getSearchCommodityName())) {
      builder.append(" AND ");
      builder.append(SqlDialect.getDefault().getLikeClausePattern("B.COMMODITY_NAME"));
      String commodityName = SqlDialect.getDefault().escape(condition.getSearchCommodityName());
      commodityName = "%" + commodityName + "%";
      params.add(commodityName);
    }

    builder.append(SORT_CONDITION);

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

    return this;
  }

  /**
   * 
   */
  public Class <Related> getRowType() {
    return Related.class;
  }
}
