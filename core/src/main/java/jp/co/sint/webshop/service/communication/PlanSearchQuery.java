package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;


public class PlanSearchQuery extends AbstractQuery<Plan> {

  /** uid */
  private static final long serialVersionUID = 1L;
  
  public static final String CATEGORY_COMMOIDTY_EXISTS = "SELECT COUNT(*) FROM CATEGORY_COMMODITY " +
  		" WHERE COMMODITY_CODE = ? " +
  		" AND CATEGORY_SEARCH_PATH LIKE ?";
  
  public static final String COUNT_SQL = "SELECT COUNT(PLAN_CODE) FROM PLAN " +
	"WHERE PLAN_TYPE = ? AND " +
	" PLAN_DETAIL_TYPE = ? AND" +
	" ? <= PLAN_END_DATETIME AND " +
	" ? >= PLAN_START_DATETIME AND " +
	"PLAN_CODE <> ?";
  
  public static final String FIND_PLAN_DETAIL = "SELECT" +
  		" PD.DETAIL_CODE, " +
  		" PD.DETAIL_TYPE, " +
  		" PD.DETAIL_NAME, " +
        " PD.DETAIL_URL, " +
        " PD.SHOW_COMMODITY_COUNT, " +
        " PD.DISPLAY_ORDER " +
        " FROM PLAN_DETAIL PD" +
        " LEFT JOIN CATEGORY C ON C.CATEGORY_CODE = PD.DETAIL_CODE " +
        " LEFT JOIN BRAND B ON B.BRAND_CODE = PD.DETAIL_CODE " +
        " WHERE PLAN_CODE = ? ";
  
  public static final String FIND_PLAN_COMMODITY_BY_DETAIL = "SELECT * FROM PLAN_COMMODITY " +
  		" WHERE PLAN_CODE = ?" +
  		" AND DETAIL_TYPE = ?" +
  		" AND DETAIL_CODE = ?";
  
  public static final String PLAN_RELATED_COMMODITY = "SELECT" +
  		" PC.COMMODITY_CODE, " +
  		" CH.COMMODITY_NAME, " +
  		" TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME,'YYYY/MM/DD HH24:MI:SS') AS DISCOUNT_PRICE_START_DATETIME, " +
  		" TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME,'YYYY/MM/DD HH24:MI:SS') AS DISCOUNT_PRICE_END_DATETIME, " +
  		" CD.DISCOUNT_PRICE, " +
  		" PC.DISPLAY_ORDER " +
  		" FROM PLAN_COMMODITY AS PC INNER JOIN COMMODITY_HEADER AS CH" +
  		" ON PC.COMMODITY_CODE = CH.COMMODITY_CODE " +
  		" INNER JOIN COMMODITY_DETAIL AS CD "+
  		" ON CH.REPRESENT_SKU_CODE = CD.SKU_CODE " +
  		" WHERE PC.PLAN_CODE = ? AND " +
  		" PC.DETAIL_TYPE = ? AND " +
  		" PC.DETAIL_CODE = ?"; 
  
  public static final String COUPON_RELATED_COMMODITY = "SELECT" +
  " PC.COMMODITY_CODE, " +
  " CH.COMMODITY_NAME, " +
  " TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME,'YYYY/MM/DD HH24:MI:SS') AS DISCOUNT_PRICE_START_DATETIME, " +
  " TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME,'YYYY/MM/DD HH24:MI:SS') AS DISCOUNT_PRICE_END_DATETIME, " +
  " CD.DISCOUNT_PRICE, " +
  " '' AS DISPLAY_ORDER " +
  " FROM NEW_COUPON_RULE_LSSUE_INFO AS PC INNER JOIN COMMODITY_HEADER AS CH" +
  " ON PC.COMMODITY_CODE = CH.COMMODITY_CODE " +
  " INNER JOIN COMMODITY_DETAIL AS CD "+
  " ON CH.REPRESENT_SKU_CODE = CD.SKU_CODE " +
  " WHERE PC.COUPON_CODE = ? " ;
  
  public static final String USE_RELATED_COMMODITY = "SELECT" +
  " PC.COMMODITY_CODE, " +
  " CH.COMMODITY_NAME, " +
  " TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME,'YYYY/MM/DD HH24:MI:SS') AS DISCOUNT_PRICE_START_DATETIME, " +
  " TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME,'YYYY/MM/DD HH24:MI:SS') AS DISCOUNT_PRICE_END_DATETIME, " +
  " CD.DISCOUNT_PRICE, " +
  " '' AS DISPLAY_ORDER " +
  " FROM NEW_COUPON_RULE_USE_INFO AS PC INNER JOIN COMMODITY_HEADER AS CH" +
  " ON PC.COMMODITY_CODE = CH.COMMODITY_CODE " +
  " INNER JOIN COMMODITY_DETAIL AS CD "+
  " ON CH.REPRESENT_SKU_CODE = CD.SKU_CODE " +
  " WHERE PC.COUPON_CODE = ? " +
  " AND PC.COMMODITY_CODE IS NOT NULL ";
  
  private static final String BASE_SQL
    = "SELECT "
    + "P.PLAN_CODE, "
    + "P.PLAN_NAME, "
    + "P.PLAN_NAME_EN, "
    + "P.PLAN_NAME_JP, "
    + "P.PLAN_TYPE, "
    + "P.PLAN_DESCRIPTION, "
    + "P.PLAN_DESCRIPTION_EN, "
    + "P.PLAN_DESCRIPTION_JP, "
    + "P.PLAN_START_DATETIME, "
    + "P.PLAN_END_DATETIME, "
    + "P.PLAN_DETAIL_TYPE "
    // 20130703 txw add start
    + ",P.TITLE,P.TITLE_EN,P.TITLE_JP "
    + ",P.DESCRIPTION,P.DESCRIPTION_EN,P.DESCRIPTION_JP "
    + ",P.KEYWORD,P.KEYWORD_EN,P.KEYWORD_JP "
    // 20130703 txw add end
    + "FROM PLAN P ";
    
  private static final String SORT_CONDITION
	= " ORDER BY CASE"
	+ " WHEN P.PLAN_START_DATETIME <= " + SqlDialect.getDefault().getCurrentDate()
	+ "  AND P.PLAN_END_DATETIME >= " + SqlDialect.getDefault().getCurrentDate() + " THEN 1 "
	+ " WHEN P.PLAN_START_DATETIME > " + SqlDialect.getDefault().getCurrentDate() + " THEN 2"
	+ " WHEN P.PLAN_END_DATETIME < " + SqlDialect.getDefault().getCurrentDate() + " THEN 3 END,"
	+ "  P.PLAN_START_DATETIME DESC ";

  public PlanSearchQuery() {
  }

  public PlanSearchQuery(PlanSearchCondition condition) {
    
    StringBuilder builder = new StringBuilder(BASE_SQL);
    builder.append(" WHERE 1 = 1 ");
    List<Object> params = new ArrayList<Object>();
    
    //検索条件:企划类型
    if (StringUtil.hasValueAnyOf(condition.getPlanType())) {
      builder.append(" AND ");
      builder.append(" P.PLAN_TYPE = ? ");
      params.add(condition.getPlanType());
    }
    
    //検索条件:企划明细类型
    if (StringUtil.hasValueAnyOf(condition.getPlanDetailType())) {
      builder.append(" AND ");
      builder.append(" P.PLAN_DETAIL_TYPE = ? ");
      params.add(condition.getPlanDetailType());
    }
    
    
    //検索条件:企划编号    if (StringUtil.hasValue(condition.getPlanCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause(
          "P.PLAN_CODE",
          condition.getPlanCode(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    //検索条件:企划名称
    if (StringUtil.hasValue(condition.getPlanName())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause(
          "P.PLAN_NAME",
          condition.getPlanName(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    } 
    if (StringUtil.hasValue(condition.getPlanNameEn())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause(
          "P.PLAN_NAME_EN",
          condition.getPlanNameEn(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    } 
    if (StringUtil.hasValue(condition.getPlanNameJp())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createLikeClause(
          "P.PLAN_NAME_JP",
          condition.getPlanNameJp(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    
    if (StringUtil.hasValueAnyOf(condition.getPlanStartDateFrom(), condition.getPlanStartDateTo())) {
	 if (StringUtil.hasValue(condition.getPlanStartDateTo())) {
	   condition.setPlanStartDateTo(condition.getPlanStartDateTo().substring(0,17) + "59");
	 }
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createDateRangeClause("P.PLAN_START_DATETIME", DateUtil.fromString(condition
          .getPlanStartDateFrom(), true), DateUtil.fromString(condition.getPlanStartDateTo(), true));
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    if (StringUtil.hasValueAnyOf(condition.getPlanEndDateFrom(), condition.getPlanEndDateTo())) {
      if (StringUtil.hasValue(condition.getPlanEndDateTo())) {
		condition.setPlanEndDateTo(condition.getPlanEndDateTo().substring(0,17) + "59");
	  }
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createDateRangeClause("P.PLAN_END_DATETIME", DateUtil.fromString(condition
          .getPlanEndDateFrom(), true), DateUtil.fromString(condition.getPlanEndDateTo(), true));
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    
    // 活动状态
    if (StringUtil.hasValue(condition.getCampaignStatus())) {
	  if (ActivityStatus.IN_PROGRESS.getValue().equals(condition.getCampaignStatus())) {
		builder.append(" AND ");
	    builder.append(" P.PLAN_START_DATETIME <= " + SqlDialect.getDefault().getCurrentDate()
          + "  AND P.PLAN_END_DATETIME >= " + SqlDialect.getDefault().getCurrentDate());
	  } else if (ActivityStatus.NOT_PROGRESS.getValue().equals(condition.getCampaignStatus())) {
	    builder.append(" AND ");
	    builder.append(" P.PLAN_START_DATETIME > " + SqlDialect.getDefault().getCurrentDate());
	  } else if (ActivityStatus.OUT_PROGRESS.getValue().equals(condition.getCampaignStatus())) {
		builder.append(" AND ");
        builder.append(" P.PLAN_END_DATETIME < " + SqlDialect.getDefault().getCurrentDate());
	  }
	}

    
    // 並べ替え条件(実施中⇒実施前⇒実施終了, 各期間中では開始日の降順)
    builder.append(SORT_CONDITION);
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
    
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }
  
  public Class<Plan> getRowType() {
    return Plan.class;
  }

}
