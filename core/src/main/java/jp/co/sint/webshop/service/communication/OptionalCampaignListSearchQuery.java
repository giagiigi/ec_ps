package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.OptionalCampaign;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class OptionalCampaignListSearchQuery extends AbstractQuery<OptionalCampaign> {

  /**
   * uid
   */
  private static final long serialVersionUID = 1L;

  public OptionalCampaignListSearchQuery(OptionalCampaignListSearchCondition condition) {
	  buildQuery(BASE_QUERY, condition);
  }
  
  //优惠劵利用开始通知
  public static final String FIND_NEW_COUPON_USE_START_FOR_BATCH = "SELECT * FROM OPTIONAL_CAMPAIGN WHERE "
      + " TO_CHAR(USE_START_DATETIME, 'yyyy/MM/dd') = ? ";

  //优惠劵利用结束通知
  public static final String FIND_NEW_COUPON_USE_END_FOR_BATCH = "SELECT * FROM OPTIONAL_CAMPAIGN WHERE "
      + " TO_CHAR(USE_END_DATETIME, 'yyyy/MM/dd') = ? ";
  // 2013/04/12 优惠券对应 ob modify end
  
  //当优惠券的发行最小购买金额相等时判断优惠券利用其间是否重复
  public static final String CHECK_DUPLICATED_REGISTER = "SELECT COUNT(COUPON_CODE) FROM OPTIONAL_CAMPAIGN " 
	  + " WHERE MIN_ISSUE_ORDER_AMOUNT = ? AND ? <= MIN_ISSUE_END_DATETIME AND ? >= MIN_ISSUE_START_DATETIME AND COUPON_CODE <> ? AND COUPON_TYPE =?";
  
  //根据规则编号和类型得到订单信息
  //public static final String SELECT_ORDER_HEADE_BY_DISCOUNT_CODE = "SELECT COUNT(DISCOUNT_CODE) FROM ORDER_HEADER WHERE DISCOUNT_CODE = ? AND DISCOUNT_TYPE = ? ";
  
  
  //判断优惠券信息是否已经被使用
  //public static final String CHECK_NEWCOUPONRULE_USE = "SELECT COUNT(COUPON_CODE) FROM OPTIONAL_CAMPAIGN WHERE COUPON_CODE = ? ";
  
  private static final String BASE_QUERY = "SELECT NCR.* "
//	+ " NCR.COUPON_NAME, "
//	+ " NCR.COUPON_NAME_EN, "
//	+ " NCR.COUPON_NAME_JP, "
//	+ " NCR.COUPON_TYPE, "
//	+ " NCR.PERSONAL_USE_LIMIT, " 
//	+ " NCR.SITE_USE_LIMIT, "
//	+ " NCR.COUPON_AMOUNT,"
//	+ " NCR.COUPON_PROPORTION,"
//	+ " NCR.COUPON_ISSUE_TYPE, "
//	+ " NCR.MIN_ISSUE_ORDER_AMOUNT, "
//	+ " NCR.MIN_ISSUE_START_DATETIME, "
//	+ " NCR.MIN_ISSUE_END_DATETIME, "
//	+ " NCR.MIN_USE_ORDER_AMOUNT, "
//	+ " NCR.MIN_USE_START_DATETIME, "
//	+ " NCR.MIN_USE_END_DATETIME, "
//  + " NCR.MIN_USE_END_NUM "
	+ " FROM OPTIONAL_CAMPAIGN NCR";
  
  // 20120113 shen add start
  // 发行期间内的购买发行金券
  public static final String LOAD_NEW_COUPON_RULE_QUERY = DatabaseUtil.getSelectAllQuery(NewCouponRule.class)
      + " WHERE NOW() BETWEEN MIN_ISSUE_START_DATETIME AND MIN_ISSUE_END_DATETIME AND COUPON_TYPE = ?"
      + " ORDER BY MIN_USE_ORDER_AMOUNT";
  // 20120113 shen add end
  
  // 20120716 shen add start
  public static final String LOAD_PUBLIC_COUPON_LIST_QUERY = DatabaseUtil.getSelectAllQuery(NewCouponRule.class)
      + " WHERE NOW() BETWEEN MIN_USE_START_DATETIME AND MIN_USE_END_DATETIME AND COUPON_TYPE = ?";
  // 20120716 shen add end

  private void buildQuery(String query, OptionalCampaignListSearchCondition condition) {
    StringBuilder builder = new StringBuilder(query);

    List<Object> params = new ArrayList<Object>();
    
    builder.append(" where 1=1 ");
    
    // 检索条件：优惠券规则编号
    if (StringUtil.hasValue(condition.getSearchCouponCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("NCR.CAMPAIGN_CODE", condition.getSearchCouponCode(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 检索条件：优惠券规则名称
    if (StringUtil.hasValue(condition.getSearchCouponName())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("NCR.CAMPAIGN_NAME", condition
          .getSearchCouponName(), LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    if (StringUtil.hasValue(condition.getSearchCouponNameEn())) {
        SqlDialect dialect = SqlDialect.getDefault();
        SqlFragment fragment = dialect.createLikeClause("NCR.CAMPAIGN_NAME_EN", condition
            .getSearchCouponNameEn(), LikeClauseOption.PARTIAL_MATCH);
        builder.append(" AND " + fragment.getFragment());
        params.addAll(Arrays.asList(fragment.getParameters()));
      }
    if (StringUtil.hasValue(condition.getSearchCouponNameJp())) {
        SqlDialect dialect = SqlDialect.getDefault();
        SqlFragment fragment = dialect.createLikeClause("NCR.CAMPAIGN_NAME_JP", condition
            .getSearchCouponNameJp(), LikeClauseOption.PARTIAL_MATCH);
        builder.append(" AND " + fragment.getFragment());
        params.addAll(Arrays.asList(fragment.getParameters()));
      }
    
    
    // 检索条件：优惠券利用开始日时
    if (StringUtil.hasValueAnyOf(condition.getSearchMinUseStartDatetimeFrom(), condition.getSearchMinUseStartDatetimeTo())) {
      SqlDialect dialect = SqlDialect.getDefault();
      String endDate = condition.getSearchMinUseStartDatetimeTo();
      if(!StringUtil.isNullOrEmpty(endDate)){
    	  endDate =condition.getSearchMinUseStartDatetimeTo().substring(0,17) + "59";
      }
      SqlFragment fragment = dialect.createRangeClause("NCR.CAMPAIGN_START_DATE", DateUtil.fromString(condition.getSearchMinUseStartDatetimeFrom()
          , true), DateUtil.fromString(endDate, true));
      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    
    // 检索条件：优惠券利用结束日时
    if (StringUtil.hasValueAnyOf(condition.getSearchMinUseEndDatetimeFrom(), condition.getSearchMinUseEndDatetimeTo())) {
      SqlDialect dialect = SqlDialect.getDefault();
      String endDate = condition.getSearchMinUseEndDatetimeTo();
      if(!StringUtil.isNullOrEmpty(endDate)){
    	  endDate =condition.getSearchMinUseEndDatetimeTo().substring(0,17) + "59";
      }
      SqlFragment fragment = dialect.createRangeClause("NCR.CAMPAIGN_END_DATE", DateUtil.fromString(condition.getSearchMinUseEndDatetimeFrom()
          , true), DateUtil.fromString(endDate, true));
      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    

    //排序
    builder.append(" ORDER BY CASE"
    + " WHEN NCR.campaign_start_date <= " + SqlDialect.getDefault().getCurrentDatetime()
    + " AND NCR.campaign_end_date >= " + SqlDialect.getDefault().getCurrentDatetime() + " THEN 1 "
    + " WHEN NCR.campaign_start_date > " + SqlDialect.getDefault().getCurrentDatetime() + " THEN 2 "
    + " ELSE 3 END,"
    + " NCR.campaign_start_date DESC");
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  public Class<OptionalCampaign> getRowType() {
    return OptionalCampaign.class;
  }

}
