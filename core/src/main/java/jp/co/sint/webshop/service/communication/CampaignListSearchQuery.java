package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.DateSearchAccuracy;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;


public class CampaignListSearchQuery extends AbstractQuery<CampaignHeadLine> {

  /** uid */
  private static final long serialVersionUID = 1L;
  
  private static final String BASE_SQL
    = "SELECT "
    + "SH.SHOP_CODE, "
    + "SH.SHOP_NAME, "
    + "CM.CAMPAIGN_CODE, "
    + "CM.CAMPAIGN_NAME, "
    + "CM.CAMPAIGN_NAME_EN, "
    + "CM.CAMPAIGN_NAME_JP, "
    + SqlDialect.getDefault().toCharFromDate("CM.CAMPAIGN_START_DATE") + " CAMPAIGN_START_DATE, "
    + SqlDialect.getDefault().toCharFromDate("CM.CAMPAIGN_END_DATE") + " CAMPAIGN_END_DATE, "
    + "CM.CAMPAIGN_DISCOUNT_RATE "
    + "FROM CAMPAIGN CM "
    + "INNER JOIN SHOP SH ON CM.SHOP_CODE = SH.SHOP_CODE "
//  postgreSQL start    
    //+ "AND TRUNC(SYSDATE) "
    + "AND "+SqlDialect.getDefault().getTruncSysdate()+" "
//  postgreSQL end    
    + "BETWEEN COALESCE (SH.OPEN_DATETIME , TO_DATE('" + DateUtil.toDateString(DateUtil.getMin()) + "', 'yyyy/MM/dd')) "
    + "AND COALESCE (SH.CLOSE_DATETIME , TO_DATE('" + DateUtil.toDateString(DateUtil.getMax()) + "', 'yyyy/MM/dd')) ";
  
  private static final String SORT_CONDITION
    = " ORDER BY CASE"
//    10.1.6 10260 修正 ここから
      // + " WHEN TRUNC(CM.CAMPAIGN_START_DATE) <= " + SqlDialect.getDefault().getCurrentDatetime()
      // + "  AND TRUNC(CM.CAMPAIGN_END_DATE) >= " + SqlDialect.getDefault().getCurrentDatetime() + " THEN 1 "
      // + " WHEN TRUNC(CM.CAMPAIGN_START_DATE) > " + SqlDialect.getDefault().getCurrentDatetime() + " THEN 2"
      // + " WHEN TRUNC(CM.CAMPAIGN_END_DATE) < " + SqlDialect.getDefault().getCurrentDatetime() + " THEN 3 END,"
      + " WHEN CM.CAMPAIGN_START_DATE <= " + SqlDialect.getDefault().getCurrentDate()
      + "  AND CM.CAMPAIGN_END_DATE >= " + SqlDialect.getDefault().getCurrentDate() + " THEN 1 "
      + " WHEN CM.CAMPAIGN_START_DATE > " + SqlDialect.getDefault().getCurrentDate() + " THEN 2"
      + " WHEN CM.CAMPAIGN_END_DATE < " + SqlDialect.getDefault().getCurrentDate() + " THEN 3 END,"
      + " CM.CAMPAIGN_START_DATE DESC";
      // 10.1.5 10230 修正 ここまで

  public CampaignListSearchQuery() {
  }

  public CampaignListSearchQuery(CampaignListSearchCondition condition) {
    
    StringBuilder builder = new StringBuilder(BASE_SQL);
    builder.append(" WHERE 1 = 1 ");
    List<Object> params = new ArrayList<Object>();

    //検索条件:ショップコード
    if (StringUtil.hasValue(condition.getShopCode())) {
      builder.append(" AND ");
      builder.append(" SH.SHOP_CODE = ? ");
      params.add(condition.getShopCode());
    }

    //検索条件:キャンペーンコード
    if (StringUtil.hasValue(condition.getCampaignCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause(
          "CM.CAMPAIGN_CODE",
          condition.getCampaignCode(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    //検索条件:キャンペーン名
    if (StringUtil.hasValue(condition.getCampaignName())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause(
          "CM.CAMPAIGN_NAME",
          condition.getCampaignName(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    if (StringUtil.hasValueAnyOf(condition.getCampaignStartDateFrom(), condition.getCampaignStartDateTo())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createDateRangeClause("CM.CAMPAIGN_START_DATE", DateUtil.fromString(condition
          .getCampaignStartDateFrom()), DateUtil.fromString(condition.getCampaignStartDateTo()), DateSearchAccuracy.DATE);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    if (StringUtil.hasValueAnyOf(condition.getCampaignEndDateFrom(), condition.getCampaignEndDateTo())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createDateRangeClause("CM.CAMPAIGN_END_DATE", DateUtil.fromString(condition
          .getCampaignEndDateFrom()), DateUtil.fromString(condition.getCampaignEndDateTo()), DateSearchAccuracy.DATE);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 並べ替え条件(実施中⇒実施前⇒実施終了, 各期間中では開始日の降順)
    builder.append(SORT_CONDITION);
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
    
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }
  
  public Class<CampaignHeadLine> getRowType() {
    return CampaignHeadLine.class;
  }

}
