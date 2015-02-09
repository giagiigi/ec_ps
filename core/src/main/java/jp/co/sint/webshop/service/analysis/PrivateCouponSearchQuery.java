package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.service.communication.PrivateCouponListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class PrivateCouponSearchQuery extends AbstractQuery<PrivateCouponListSummary> {

  /**
   * uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * SQL语句查询方法
   * @param condition 查询条件实体
   * @param flag true:顾客别查询;       false:公共优惠劵查询
   * 
   * */
  public PrivateCouponSearchQuery(PrivateCouponListSearchCondition condition, boolean flag) {
	  buildQuery(ANALYSIS_QUERY, condition, flag,false);
  }


  /**
   * CSVSQL语句查询方法
   * @param condition 查询条件实体
   * @param flag true:顾客别查询;       false:公共优惠劵查询
   * @param flg true 顾客别CSV查询语句
   * */
  public PrivateCouponSearchQuery(PrivateCouponListSearchCondition condition, boolean flag,boolean flg) {
	  buildQuery(ANALYSIS_CSV_QUERY, condition, flag,flg);
  }
  
  private static final String ANALYSIS_CSV_QUERY = "SELECT NCR.COUPON_CODE,"
		+ " NCR.COUPON_NAME, "
		+ " NCH.COUPON_ISSUE_NO, "
		
		//优惠券种别
		+ "CASE WHEN NCR.COUPON_ISSUE_TYPE = 0 THEN '比例' WHEN  '1' THEN '固定金额'  ELSE '' END , "
		
		//优惠比例
		+ " NCH.COUPON_PROPORTION, "
		//优惠金额
		+ " NCH.COUPON_AMOUNT, "
		//利用开始日时
		+ " NCH.USE_START_DATETIME, "
		//使用结束日时
		+ " NCH.USE_END_DATETIME, "
		//顾客编号 
		+ " NCH.CUSTOMER_CODE, "
		//顾客名称
		+ " C.LAST_NAME, "
        //利用状态		
		+ " CASE WHEN NCH.USE_STATUS = 0 THEN '未使用' WHEN  NCH.USE_STATUS = 1 THEN '已使用'  ELSE '' END , "
		//发行原订单编号
		+ " NCH.ISSUE_ORDER_NO, "
		//使用订单编号
		+ " NCH.USE_ORDER_NO"
		
		+ " FROM NEW_COUPON_RULE NCR "
		+ " LEFT JOIN "
		+ " NEW_COUPON_HISTORY NCH "
		+ " ON NCR.COUPON_CODE = NCH.COUPON_CODE "
		+ " LEFT JOIN " 
		+ " CUSTOMER C "
		+ " ON C.CUSTOMER_CODE = NCH.CUSTOMER_CODE WHERE ";
  
  private static final String ANALYSIS_QUERY = "SELECT NCR.COUPON_CODE,"
		+ " NCR.COUPON_NAME, "
		+ " NCR.COUPON_TYPE, "
		+ " CV.ISSUE_TOTAL_COUNT, "
		+ " CV.FIRST_ORDER_COUNT, "
		+ " CV.ORDER_TOTAL_PRICE, "
		+ " CV.ORDER_TOTAL_COUNT, "
		+ " CV.CAMPAIGN_TOTAL_PRICE, "
		+ " CV.ORDER_UNIT_PRICE, "
		+ " CV.CANCEL_TOTAL_PRICE, "
		+ " CV.CANCEL_TOTAL_COUNT, "
		+ " CV.CANCEL_CAMPAIGN_PRICE, "
		+ " CV.CANCEL_UNIT_PRICE"
		+ " FROM NEW_COUPON_RULE NCR INNER JOIN COUPON_SUMMARY_VIEW CV ON CV.COUPON_CODE = NCR.COUPON_CODE WHERE ";


  /**
   * SQL语句查询条件
   * @param query 查询语句
   * @param condition 查询条件实体
   * @param flag true:顾客别查询; false :公共优惠劵查询     
   * @param flg  true:顾客别CSV查询 false:公共优惠劵查询
   * 
   * */
  private void buildQuery(String query, PrivateCouponListSearchCondition condition, boolean flag,boolean flg) {
    StringBuilder builder = new StringBuilder(query);
    //根据查询条件取得顾客别优惠券发行规则信息
    if (flag) {
		builder.append(" NCR.COUPON_TYPE != " + CouponType.COMMON_DISTRIBUTION.longValue());
	//根据查询条件取得顾客组别优惠规则信息
    } else {
		builder.append(" 1 = 1 ");
	}
    List<Object> params = new ArrayList<Object>();
    // 检索条件：优惠券规则编号
    if (StringUtil.hasValue(condition.getSearchCouponCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("NCR.COUPON_CODE", condition.getSearchCouponCode(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 检索条件：优惠券规则名称
    if (StringUtil.hasValue(condition.getSearchCouponName())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("NCR.COUPON_NAME", condition
          .getSearchCouponName(), LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 检索条件：优惠券类别
    if (StringUtil.hasValue(condition.getSearchCouponType())) {
      builder.append(" AND NCR.COUPON_TYPE = ? ");
      params.add(condition.getSearchCouponType());
    }
    
    // 检索条件：优惠券发行类别
    if (StringUtil.hasValue(condition.getSearchCampaignType())) {
      builder.append(" AND NCR.COUPON_ISSUE_TYPE = ? ");
      params.add(condition.getSearchCampaignType());
    }
    
    // 检索条件：优惠券利用开始日时
    if (StringUtil.hasValueAnyOf(condition.getSearchMinUseStartDatetimeFrom(), condition.getSearchMinUseStartDatetimeTo())) {
      SqlDialect dialect = SqlDialect.getDefault();
      String endDate = condition.getSearchMinUseStartDatetimeTo();
      if(!StringUtil.isNullOrEmpty(endDate)){
    	  endDate =condition.getSearchMinUseStartDatetimeTo().substring(0,17) + "59";
      }
      SqlFragment fragment = dialect.createRangeClause("NCR.MIN_USE_START_DATETIME", DateUtil.fromString(condition.getSearchMinUseStartDatetimeFrom()
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
      SqlFragment fragment = dialect.createRangeClause("NCR.MIN_USE_END_DATETIME", DateUtil.fromString(condition.getSearchMinUseEndDatetimeFrom()
          , true), DateUtil.fromString(endDate, true));
      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    
    //检索条件：发行状态
    if (StringUtil.hasValue(condition.getSearchCouponActivityStatus())) {
    	
    	//发行中
		if (ActivityStatus.IN_PROGRESS.getValue().equals(condition.getSearchCouponActivityStatus())) {
			builder.append(" AND NCR.MIN_USE_START_DATETIME <= " + SqlDialect.getDefault().getCurrentDatetime() + " AND NCR.MIN_USE_END_DATETIME >= " + SqlDialect.getDefault().getCurrentDatetime());
    		
    	//未发行
    	}else if(ActivityStatus.NOT_PROGRESS.getValue().equals(condition.getSearchCouponActivityStatus())){
    		builder.append(" AND NCR.MIN_USE_START_DATETIME > " + SqlDialect.getDefault().getCurrentDatetime());
    		
    	//已发行
    	}else if(ActivityStatus.OUT_PROGRESS.getValue().equals(condition.getSearchCouponActivityStatus())){
    		builder.append(" AND NCR.MIN_USE_END_DATETIME < " + SqlDialect.getDefault().getCurrentDatetime());
    	}
    }

    //排序
    builder.append(" ORDER BY CASE"
    + " WHEN NCR.MIN_USE_START_DATETIME <= " + SqlDialect.getDefault().getCurrentDatetime()
    + " AND NCR.MIN_USE_END_DATETIME >= " + SqlDialect.getDefault().getCurrentDatetime() + " THEN 1 "
    + " WHEN NCR.MIN_USE_START_DATETIME > " + SqlDialect.getDefault().getCurrentDatetime() + " THEN 2 "
    + " ELSE 3 END,"
    + " NCR.MIN_USE_START_DATETIME ASC ");
    
    if(flg){
    	//builder.append(" , cast(coupon_issue_detail_no as numeric(1,0)) desc");
    	builder.append(" , coupon_issue_detail_no desc");

    }
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  public Class<PrivateCouponListSummary> getRowType() {
    return PrivateCouponListSummary.class;
  }

}
