package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;



public class CampaignSearchQuery extends AbstractQuery<CampaignMain> {

  /** uid */
  private static final long serialVersionUID = 1L;
  
  private static final String CAMPAIGN_MAIN_SQL = "SELECT " + "CAMPAIGN_CODE, " + "CAMPAIGN_NAME, "
      + "CAMPAIGN_NAME_EN, " + "CAMPAIGN_NAME_JP, " + "CAMPAIGN_TYPE, " + "ORDER_LIMIT, " + "CAMPAIGN_START_DATE, "
      + "CAMPAIGN_END_DATE, " + "MEMO, " + "ORM_ROWID, " + "CREATED_USER, " + "CREATED_DATETIME, " + "UPDATED_USER, "
      + "UPDATED_DATETIME " + "FROM CAMPAIGN_MAIN  ";
  
  private static final String CAMPAIGN_SORT_CONDITION = " ORDER BY CASE" 
      + " WHEN NOW() between CAMPAIGN_START_DATE AND CAMPAIGN_END_DATE THEN " + ActivityStatus.IN_PROGRESS.getValue()
      + " WHEN CAMPAIGN_START_DATE > NOW() THEN " + ActivityStatus.NOT_PROGRESS.getValue()
      + " WHEN CAMPAIGN_END_DATE < NOW() THEN " + ActivityStatus.OUT_PROGRESS.getValue() +" END, CAMPAIGN_START_DATE DESC , CAMPAIGN_CODE";

  public CampaignSearchQuery(CampaignSearchCondition condition) {

    StringBuilder builder = new StringBuilder(CAMPAIGN_MAIN_SQL);
    builder.append(" WHERE 1 = 1 ");
    List<Object> params = new ArrayList<Object>();

    // 検索条件:活動类型
    if (StringUtil.hasValueAnyOf(condition.getCampaignType())) {
      builder.append(" AND ");
      builder.append(" CAMPAIGN_TYPE = " + condition.getCampaignType());
    }

    // 検索条件:活動編號
    if (StringUtil.hasValue(condition.getCampaignCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause("CAMPAIGN_CODE", condition.getCampaignCode(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:活動名稱
    if (StringUtil.hasValue(condition.getCampaignName())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause("CAMPAIGN_NAME", condition.getCampaignName(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 
    if (StringUtil.hasValue(condition.getCampaignNameEn())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause("CAMPAIGN_NAME_EN", condition.getCampaignNameEn(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 
    if (StringUtil.hasValue(condition.getCampaignNameJp())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause("CAMPAIGN_NAME_JP", condition.getCampaignNameJp(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:活動時間
    if (StringUtil.hasValueAnyOf(condition.getCampaignStartDateFrom(), condition.getCampaignEndDateTo())) {
      if (StringUtil.hasValue(condition.getCampaignStartDateFrom())) {
        condition.setCampaignStartDateFrom(condition.getCampaignStartDateFrom().substring(0, 17) + "00");
        builder.append(" AND ");
        builder.append(" CAMPAIGN_START_DATE >= ? ");
        params.add(DateUtil.fromString(condition.getCampaignStartDateFrom(), true));
      }
      if (StringUtil.hasValue(condition.getCampaignEndDateTo())) {
        condition.setCampaignEndDateTo(condition.getCampaignEndDateTo().substring(0, 17) + "59");
        builder.append(" AND ");
        builder.append(" CAMPAIGN_END_DATE <= ? ");
        params.add(DateUtil.fromString(condition.getCampaignEndDateTo(), true));
      }
    }

    // 検索条件:活動進行狀態
    if (StringUtil.hasValue(condition.getSearchCampaignStatus())) {
      if (condition.getSearchCampaignStatus().equals("1")) {
        builder.append(" AND ");
        builder.append(" NOW() between CAMPAIGN_START_DATE AND CAMPAIGN_END_DATE ");
      } else if (condition.getSearchCampaignStatus().equals("2")) {
        builder.append(" AND ");
        builder.append(" NOW() < CAMPAIGN_START_DATE ");
      } else if (condition.getSearchCampaignStatus().equals("3")) {
        builder.append(" AND ");
        builder.append(" NOW() > CAMPAIGN_END_DATE ");
      }
    }
    // 並べ替え条件(実施中⇒実施前⇒実施終了, 各期間中では開始日の降順)
    builder.append(CAMPAIGN_SORT_CONDITION);

    setSqlString(builder.toString());
    setParameters(params.toArray());

    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  
  public Class<CampaignMain> getRowType() {
    return CampaignMain.class;
  }

}
