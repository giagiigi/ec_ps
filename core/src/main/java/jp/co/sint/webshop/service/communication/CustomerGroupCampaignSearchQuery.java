package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;


public class CustomerGroupCampaignSearchQuery extends AbstractQuery<CustomerGroupCampaignHeadLine> {

  /** uid */
  private static final long serialVersionUID = 1L;
  
  public static final String COUNT_SQL = "SELECT COUNT(CAMPAIGN_CODE) FROM CUSTOMER_GROUP_CAMPAIGN " +
  		"WHERE (CUSTOMER_GROUP_CODE = ? OR CUSTOMER_GROUP_CODE IS NULL) AND " +
  		" ? <= CAMPAIGN_END_DATETIME AND " +
  		" ? >= CAMPAIGN_START_DATETIME AND " +
  		"CAMPAIGN_CODE <> ?";
  
  public static final String COUNT_SQL2 = "SELECT COUNT(CAMPAIGN_CODE) FROM CUSTOMER_GROUP_CAMPAIGN " +
	"WHERE  ? <= CAMPAIGN_END_DATETIME AND " +
		" ? >= CAMPAIGN_START_DATETIME AND " +
		"CAMPAIGN_CODE <> ?";
  
  private static final String BASE_SQL
    = "SELECT "
    + "CGC.SHOP_CODE, "
    + "CGC.CAMPAIGN_CODE, "
    + "CGC.CAMPAIGN_NAME, "
    + "CGC.CAMPAIGN_NAME_EN, "
    + "CGC.CAMPAIGN_NAME_JP, "
    + "CGC.CUSTOMER_GROUP_CODE, "
    + "CG.CUSTOMER_GROUP_NAME, "
    //20120522 tuxinwei add start
    + "CG.CUSTOMER_GROUP_NAME_EN, "
    + "CG.CUSTOMER_GROUP_NAME_JP, "
    //20120522 tuxinwei add end
    + "CGC.CAMPAIGN_TYPE, "
    + "CGC.CAMPAIGN_PROPORTION, "
    + "CGC.CAMPAIGN_AMOUNT, "
    + "CGC.CAMPAIGN_START_DATETIME, "
    + "CGC.CAMPAIGN_END_DATETIME, "
    + "CGC.MIN_ORDER_AMOUNT ,"
    //20140312 hdh add start
    + "CGC.PERSONAL_USE_LIMIT "
    //20140312 hdh add end
    + "FROM CUSTOMER_GROUP_CAMPAIGN CGC "
    + "LEFT JOIN CUSTOMER_GROUP CG ON CG.CUSTOMER_GROUP_CODE = CGC.CUSTOMER_GROUP_CODE ";
    
  private static final String SORT_CONDITION
	= " ORDER BY CASE"
	+ " WHEN CGC.CAMPAIGN_START_DATETIME <= " + SqlDialect.getDefault().getCurrentDate()
	+ "  AND CGC.CAMPAIGN_END_DATETIME >= " + SqlDialect.getDefault().getCurrentDate() + " THEN 1 "
	+ " WHEN CGC.CAMPAIGN_START_DATETIME > " + SqlDialect.getDefault().getCurrentDate() + " THEN 2"
	+ " WHEN CGC.CAMPAIGN_END_DATETIME < " + SqlDialect.getDefault().getCurrentDate() + " THEN 3 END,"
	+ "  CGC.CAMPAIGN_START_DATETIME DESC ,CGC.CUSTOMER_GROUP_CODE";
  
  public static final String CAMPAIGN_LIST = BASE_SQL 
    + " WHERE CGC.CAMPAIGN_START_DATETIME <= NOW () AND NOW () <= CGC.CAMPAIGN_END_DATETIME "
    + " ORDER BY CGC.CUSTOMER_GROUP_CODE DESC";

  public CustomerGroupCampaignSearchQuery() {
  }

  public CustomerGroupCampaignSearchQuery(CustomerGroupCampaignSearchCondition condition) {
    
    StringBuilder builder = new StringBuilder(BASE_SQL);
    builder.append(" WHERE 1 = 1 ");
    List<Object> params = new ArrayList<Object>();
    
    
    //検索条件:顾客群组

    if (StringUtil.hasValue(condition.getCustomerGroupCode())) {
      builder.append(" AND ");
      builder.append(" CGC.CUSTOMER_GROUP_CODE = ? ");
      params.add(condition.getCustomerGroupCode());
    }
    
    //検索条件:优惠种别

    if (StringUtil.hasValue(condition.getCampaignType())) {
      builder.append(" AND ");
      builder.append(" CGC.CAMPAIGN_TYPE = ? ");
      params.add(condition.getCampaignType());
    }

    //検索条件:キャンペーンコード
    if (StringUtil.hasValue(condition.getCampaignCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause(
          "CGC.CAMPAIGN_CODE",
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
          "CGC.CAMPAIGN_NAME",
          condition.getCampaignName(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    
    if (StringUtil.hasValueAnyOf(condition.getCampaignStartDateFrom(), condition.getCampaignStartDateTo())) {
	 if (StringUtil.hasValue(condition.getCampaignStartDateTo())) {
	   condition.setCampaignStartDateTo(condition.getCampaignStartDateTo().substring(0,17) + "59");
	 }
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createDateRangeClause("CGC.CAMPAIGN_START_DATETIME", DateUtil.fromString(condition
          .getCampaignStartDateFrom(), true), DateUtil.fromString(condition.getCampaignStartDateTo(), true));
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    if (StringUtil.hasValueAnyOf(condition.getCampaignEndDateFrom(), condition.getCampaignEndDateTo())) {
      if (StringUtil.hasValue(condition.getCampaignEndDateTo())) {
		condition.setCampaignEndDateTo(condition.getCampaignEndDateTo().substring(0,17) + "59");
	  }
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createDateRangeClause("CGC.CAMPAIGN_END_DATETIME", DateUtil.fromString(condition
          .getCampaignEndDateFrom(), true), DateUtil.fromString(condition.getCampaignEndDateTo(), true));
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    
    // 活动状态
    if (StringUtil.hasValue(condition.getCampaignStatus())) {
	  if (ActivityStatus.IN_PROGRESS.getValue().equals(condition.getCampaignStatus())) {
		builder.append(" AND ");
	    builder.append(" CGC.CAMPAIGN_START_DATETIME <= " + SqlDialect.getDefault().getCurrentDate()
          + "  AND CGC.CAMPAIGN_END_DATETIME >= " + SqlDialect.getDefault().getCurrentDate());
	  } else if (ActivityStatus.NOT_PROGRESS.getValue().equals(condition.getCampaignStatus())) {
	    builder.append(" AND ");
	    builder.append(" CGC.CAMPAIGN_START_DATETIME > " + SqlDialect.getDefault().getCurrentDate());
	  } else if (ActivityStatus.OUT_PROGRESS.getValue().equals(condition.getCampaignStatus())) {
		builder.append(" AND ");
        builder.append(" CGC.CAMPAIGN_END_DATETIME < " + SqlDialect.getDefault().getCurrentDate());
	  }
	}

    // 並べ替え条件(実施中⇒実施前⇒実施終了, 各期間中では開始日の降順)
    builder.append(SORT_CONDITION);
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
    
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }
  
  public Class<CustomerGroupCampaignHeadLine> getRowType() {
    return CustomerGroupCampaignHeadLine.class;
  }

}
