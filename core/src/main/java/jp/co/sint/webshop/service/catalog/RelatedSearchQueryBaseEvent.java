package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

public class RelatedSearchQueryBaseEvent extends AbstractQuery <RelatedBaseEvent> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public RelatedSearchQueryBaseEvent() {

  }

  
  private static final String SORT_CONDITION = " ORDER BY A.COMMODITY_CODE";
  
  /**
   * 
   */
  public static String getRelatedTagCommodityList() {
    final String sql = " SELECT  A.SHOP_CODE, "
                      + "         A.COMMODITY_CODE, "
                      + "        B.COMMODITY_NAME"
                      + " FROM   TAG_DISPLAY_COMMODITY A, "
                      + "        COMMODITY_HEADER B"
                      + " WHERE  A.SHOP_CODE          = ? AND"
                      + "        A.TAG_CODE           = ? AND"
                      + "        A.SHOP_CODE          = B.SHOP_CODE AND"
                      + "        A.COMMODITY_CODE     = B.COMMODITY_CODE"
                      + " ORDER BY A.COMMODITY_CODE";
    return sql;
  }
  
  /**
   * 該当のタグコードに関連付いている商品の一覧を取得する
   */
  public RelatedSearchQueryBaseEvent createRelatedTagSearchQueryBaseEvent(RelatedSearchConditionBaseEvent condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("  SELECT   A.SHOP_CODE, "
        + "                    A.COMMODITY_CODE, "
        + "                    B.COMMODITY_NAME "
        // 20130805 txw add start
        + "                    ,A.SORT_NUM, A.SORT_NUM_JP,A.SORT_NUM_EN,"
        + "                    A.LANG "
        // 20130805 txw add end
        + "           FROM     TAG_COMMODITY A, "
        + "                    COMMODITY_HEADER B "
        + "           WHERE    A.SHOP_CODE          = ? AND "
        + "                    A.TAG_CODE = ? AND "
        + "                    A.SHOP_CODE          = B.SHOP_CODE AND "
        + "                    A.COMMODITY_CODE     = B.COMMODITY_CODE ");
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

    builder.append(" ORDER BY LANG,A.COMMODITY_CODE");

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

    return this;
  }


  
  
  /**
   * 
   */
  public RelatedSearchQueryBaseEvent createRelatedGiftSearchQueryBaseEvent(RelatedSearchConditionBaseEvent condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("  SELECT   A.SHOP_CODE, "
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
  public RelatedSearchQueryBaseEvent createRelatedCampaignSearchQueryBaseEvent(RelatedSearchConditionBaseEvent condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("  SELECT   A.SHOP_CODE, "
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
   * 該当の商品コードに関連付いているキャンペーンの一覧を取得する
   */
  public RelatedSearchQueryBaseEvent createRelatedCampaignSearchQuery(RelatedSearchConditionBaseCommodity condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("  SELECT   A.SHOP_CODE, "
        + "                    A.CAMPAIGN_CODE, "
        + "                    B.COMMODITY_NAME "
        + "           FROM     CAMPAIGN_COMMODITY A, "
        + "                    CAMPAIGN B "
        + "           WHERE    A.SHOP_CODE      = ? AND "
        + "                    A.COMMODITY_CODE = ? AND "
        + "                    A.SHOP_CODE      = B.SHOP_CODE AND "
        + "                    A.CAMPAIGN_CODE  = B.CAMPAIGN_CODE ");
    params.add(condition.getShopCode());
    params.add(condition.getCommodityCode());
    if (StringUtil.hasValue(condition.getSearchEffectualCodeStart())
        && StringUtil.hasValue(condition.getSearchEffectualCodeEnd())) {
      builder.append(" AND A.CAMPAIGN_CODE BETWEEN ? AND ?");
      params.add(condition.getSearchEffectualCodeStart());
      params.add(condition.getSearchEffectualCodeEnd());
    } else if (StringUtil.hasValue(condition.getSearchEffectualCodeStart())) {
      builder.append(" AND A.CAMPAIGN_CODE >= ?");
      params.add(condition.getSearchEffectualCodeStart());
    } else if (StringUtil.hasValue(condition.getSearchEffectualCodeEnd())) {
      builder.append(" AND A.CAMPAIGN_CODE <= ?");
      params.add(condition.getSearchEffectualCodeEnd());
    }
    if (StringUtil.hasValue(condition.getSearchEffectualName())) {
      builder.append(" AND ");
      builder.append(SqlDialect.getDefault().getLikeClausePattern("B.COMMODITY_NAME"));
      String commodityName = SqlDialect.getDefault().escape(condition.getSearchEffectualName());
      commodityName = "%" + commodityName + "%";
      params.add(commodityName);
    }

    // キャンペーン開始日
    if (DateUtil.isCorrect(condition.getSearchStartDateFrom())
        && DateUtil.isCorrect(condition.getSearchStartDateTo())) {
      builder.append(" AND E._START_DATE BETWEEN ? AND ?");
      params.add(condition.getSearchStartDateFrom());
      params.add(condition.getSearchStartDateTo());
    } else if (DateUtil.isCorrect(condition.getSearchStartDateFrom())) {
      builder.append(" AND E._START_DATE >= ?");
      params.add(condition.getSearchStartDateFrom());
    } else if (DateUtil.isCorrect(condition.getSearchStartDateTo())) {
      builder.append(" AND E._START_DATE <= ?");
      params.add(condition.getSearchStartDateTo());
    }

    // キャンペーン終了日
    if (DateUtil.isCorrect(condition.getSearchEndDateFrom()) && DateUtil.isCorrect(condition.getSearchEndDateTo())) {
      builder.append(" AND E._END_DATE BETWEEN ? AND ?");
      params.add(condition.getSearchEndDateFrom());
      params.add(condition.getSearchEndDateTo());
    } else if (DateUtil.isCorrect(condition.getSearchEndDateFrom())) {
      builder.append(" AND E._END_DATE >= ?");
      params.add(condition.getSearchEndDateFrom());
    } else if (DateUtil.isCorrect(condition.getSearchEndDateTo())) {
      builder.append(" AND E._END_DATE <= ?");
      params.add(condition.getSearchEndDateTo());
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
  public RelatedSearchQueryBaseEvent createRelatedCommodityASearchQueryBaseEvent(RelatedSearchConditionBaseEvent condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("  SELECT   A.SHOP_CODE, "
        + "                    A.LINK_COMMODITY_CODE AS COMMODITY_CODE, "
        + "                    A.DISPLAY_ORDER, "
        + "                    B.COMMODITY_NAME, "
        + "                    A.UPDATED_DATETIME "
        + "           FROM     RELATED_COMMODITY_A A "
        + "                      INNER JOIN COMMODITY_HEADER B "
        + "                        ON A.SHOP_CODE = B.SHOP_CODE AND "
        + "                           A.LINK_COMMODITY_CODE = B.COMMODITY_CODE  "
        + "           WHERE    A.SHOP_CODE      = ? AND "
        + "                    A.COMMODITY_CODE = ? ");
    params.add(condition.getShopCode());
    params.add(condition.getEffectualCode());
    if (StringUtil.hasValue(condition.getSearchCommodityCodeStart())
        && StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND B.COMMODITY_CODE BETWEEN ? AND ?");
      params.add(condition.getSearchCommodityCodeStart());
      params.add(condition.getSearchCommodityCodeEnd());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeStart())) {
      builder.append(" AND B.COMMODITY_CODE >= ?");
      params.add(condition.getSearchCommodityCodeStart());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND B.COMMODITY_CODE <= ?");
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
  public RelatedSearchQueryBaseEvent createRelatedCommodityBSearchQueryBaseEvent(RelatedSearchConditionBaseEvent condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("  SELECT   A.SHOP_CODE, "
        + "                    A.LINK_COMMODITY_CODE AS COMMODITY_CODE, "
        + "                    A.RANKING_SCORE, "
        + "                    B.COMMODITY_NAME, "
        + "                    A.UPDATED_DATETIME "
        + "           FROM     RELATED_COMMODITY_B A "
        + "                      INNER JOIN COMMODITY_HEADER B "
        + "                        ON A.SHOP_CODE = B.SHOP_CODE AND "
        + "                           A.LINK_COMMODITY_CODE = B.COMMODITY_CODE  "
        + "           WHERE    A.SHOP_CODE      = ? AND "
        + "                    A.LINK_SHOP_CODE = ? AND "
        + "                    A.COMMODITY_CODE = ? ");
    params.add(condition.getShopCode());
    params.add(condition.getShopCode());
    params.add(condition.getEffectualCode());
    
    if (StringUtil.hasValue(condition.getSearchCommodityCodeStart())
        && StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND B.COMMODITY_CODE BETWEEN ? AND ?");
      params.add(condition.getSearchCommodityCodeStart());
      params.add(condition.getSearchCommodityCodeEnd());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeStart())) {
      builder.append(" AND B.COMMODITY_CODE >= ?");
      params.add(condition.getSearchCommodityCodeStart());
    } else if (StringUtil.hasValue(condition.getSearchCommodityCodeEnd())) {
      builder.append(" AND B.COMMODITY_CODE <= ?");
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
  public Class <RelatedBaseEvent> getRowType() {
    return RelatedBaseEvent.class;
  }
}
