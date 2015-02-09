package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CampaignCommodity;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

public class RelatedSearchQueryCampaign extends AbstractQuery <RelatedCampaign> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 該当の商品コードに関連付いているキャンペーンの一覧を取得する
   */
  public RelatedSearchQueryCampaign createRelatedCampaignSearchQuery(RelatedSearchConditionBaseCommodity condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append(" SELECT A.SHOP_CODE, "
        + "                 A.CAMPAIGN_CODE, "
        + "                 A.CAMPAIGN_NAME, "
        + "                 A.CAMPAIGN_DISCOUNT_RATE, "
        + "                 A.CAMPAIGN_START_DATE, "
        + "                 A.CAMPAIGN_END_DATE, "
        + "                 B.COMMODITY_CODE "
        + "          FROM (" + DatabaseUtil.getSelectAllQuery(Campaign.class)
        + "                WHERE  SHOP_CODE = ?) A "
        + "                  LEFT OUTER JOIN "
        + "                    (" + DatabaseUtil.getSelectAllQuery(CampaignCommodity.class)
        + "                     WHERE  SHOP_CODE = ? AND  "
        + "                            COMMODITY_CODE = ?) B "
        + "                    ON A.SHOP_CODE    = B.SHOP_CODE AND "
        + "                       A.CAMPAIGN_CODE = B.CAMPAIGN_CODE ");
    params.add(condition.getShopCode());
    params.add(condition.getShopCode());
    params.add(condition.getCommodityCode());
    
    builder.append(" WHERE 1 = 1 ");
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
      builder.append(SqlDialect.getDefault().getLikeClausePattern("A.CAMPAIGN_NAME"));
      String commodityName = SqlDialect.getDefault().escape(condition.getSearchEffectualName());
      commodityName = "%" + commodityName + "%";
      params.add(commodityName);
    }

    // キャンペーン開始日
    if (DateUtil.isCorrect(condition.getSearchStartDateFrom())
        && DateUtil.isCorrect(condition.getSearchStartDateTo())) {
      builder.append(" AND A.CAMPAIGN_START_DATE BETWEEN ? AND ?");
      params.add(condition.getSearchStartDateFrom());
      params.add(condition.getSearchStartDateTo());
    } else if (DateUtil.isCorrect(condition.getSearchStartDateFrom())) {
      builder.append(" AND A.CAMPAIGN_START_DATE >= ?");
      params.add(condition.getSearchStartDateFrom());
    } else if (DateUtil.isCorrect(condition.getSearchStartDateTo())) {
      builder.append(" AND A.CAMPAIGN_START_DATE <= ?");
      params.add(condition.getSearchStartDateTo());
    }

    // キャンペーン終了日
    if (DateUtil.isCorrect(condition.getSearchEndDateFrom()) && DateUtil.isCorrect(condition.getSearchEndDateTo())) {
      builder.append(" AND A.CAMPAIGN_END_DATE BETWEEN ? AND ?");
      params.add(condition.getSearchEndDateFrom());
      params.add(condition.getSearchEndDateTo());
    } else if (DateUtil.isCorrect(condition.getSearchEndDateFrom())) {
      builder.append(" AND A.CAMPAIGN_END_DATE >= ?");
      params.add(condition.getSearchEndDateFrom());
    } else if (DateUtil.isCorrect(condition.getSearchEndDateTo())) {
      builder.append(" AND A.CAMPAIGN_END_DATE <= ?");
      params.add(condition.getSearchEndDateTo());
    }
    
    builder.append(" ORDER BY A.CAMPAIGN_CODE");

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

    return this;
  }
  
  /**
   * 
   */
  public Class <RelatedCampaign> getRowType() {
    return RelatedCampaign.class;
  }
}
