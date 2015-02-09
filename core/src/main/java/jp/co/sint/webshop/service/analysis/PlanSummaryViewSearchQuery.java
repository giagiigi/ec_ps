package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class PlanSummaryViewSearchQuery extends AbstractQuery<PlanSummaryViewInfo> {

	/** uid */
	private static final long serialVersionUID = 1L;

	private static final String PLAN_ANALYSIS_LIST = "SELECT PL.PLAN_CODE," +
			"PL.PLAN_NAME,PL.PLAN_NAME_EN,PL.PLAN_NAME_JP," +
			"PL.PLAN_TYPE,PL.PLAN_DETAIL_TYPE," +
			"PL.PLAN_DESCRIPTION,PL.PLAN_DESCRIPTION_EN,PL.PLAN_DESCRIPTION_JP," +
			"PSV.ORDER_TYPE," +
			"CASE WHEN PSV.SUMMARY_TYPE=1 THEN (SELECT CATEGORY_NAME_PC FROM CATEGORY WHERE CATEGORY_CODE=PSV.DETAIL_CODE)" +
			"WHEN PSV.SUMMARY_TYPE=2 THEN (SELECT BRAND_NAME FROM BRAND WHERE BRAND_CODE=PSV.DETAIL_CODE)" +
			"WHEN PSV.SUMMARY_TYPE=0 THEN '合计' END AS DETAIL_NAME,PSV.ORDER_TOTAL_PRICE,PSV.ORDER_TOTAL_COUNT,PSV.CAMPAIGN_TOTAL_PRICE," +
			"PSV.CANCEL_TOTAL_COUNT,PSV.CANCEL_TOTAL_PRICE,PSV.CANCEL_CAMPAIGN_PRICE,PSV.SUMMARY_TYPE " +
			"FROM PLAN PL INNER JOIN PLAN_SUMMARY_VIEW PSV ON PSV.PLAN_CODE=PL.PLAN_CODE WHERE 1 = 1 ";
	private static final String PLAN_ANALYSIS_COUNT = "SELECT PL.PLAN_CODE,PL.PLAN_NAME,PL.PLAN_TYPE,PL.PLAN_DETAIL_TYPE," +
	"PL.PLAN_DESCRIPTION,PSV.ORDER_TYPE," +
	"CASE WHEN PSV.SUMMARY_TYPE=0 THEN '合计' END AS DETAIL_NAME,PSV.ORDER_TOTAL_PRICE,PSV.ORDER_TOTAL_COUNT,PSV.CAMPAIGN_TOTAL_PRICE," +
	"PSV.CANCEL_TOTAL_COUNT,PSV.CANCEL_TOTAL_PRICE,PSV.CANCEL_CAMPAIGN_PRICE,PSV.SUMMARY_TYPE " +
	"FROM PLAN PL INNER JOIN PLAN_SUMMARY_VIEW PSV ON PSV.PLAN_CODE=PL.PLAN_CODE WHERE 1 = 1 ";
	public PlanSummaryViewSearchQuery() {
	}
	
	public PlanSummaryViewSearchQuery(PlanSummaryViewSearchCondition condition) {

		SqlDialect dialect = SqlDialect.getDefault();
		StringBuilder builder = new StringBuilder();
		//如果为0全明细查找summary_code为1，2的数据为1合计查找
		if(condition.getSearchStatisticsType().equals("1"))
		{
			builder.append(PLAN_ANALYSIS_LIST);
			builder.append("AND SUMMARY_TYPE IN (1,2)");
		}else
		{
			builder.append(PLAN_ANALYSIS_COUNT);
			builder.append("AND SUMMARY_TYPE IN (0)");
		}
		List<Object> params = new ArrayList<Object>();

		// 検索条件:企划编号

		if (StringUtil.hasValue(condition.getSearchPlanCode())) {
			 SqlFragment fragment = dialect.createLikeClause("PL.PLAN_CODE", condition.getSearchPlanCode()
		    		  , LikeClauseOption.PARTIAL_MATCH);
		      builder.append(" AND " + fragment.getFragment());
		      params.addAll(Arrays.asList(fragment.getParameters()));
		}

		// 検索条件:模糊查询企划名称

		if (StringUtil.hasValue(condition.getSearchPlanName())) {
			      SqlFragment fragment = dialect.createLikeClause("PL.PLAN_NAME", condition.getSearchPlanName()
			    		  , LikeClauseOption.PARTIAL_MATCH);
			      builder.append(" AND " + fragment.getFragment());
			      params.addAll(Arrays.asList(fragment.getParameters()));
		}
		//企划开始时间
		if (StringUtil.hasValueAnyOf(condition.getSearchStartDateFrom(),
				condition.getSearchStartDateTo())) {
			if (StringUtil.hasValue(condition.getSearchStartDateTo())) {
				condition.setSearchStartDateTo(condition.getSearchStartDateTo().substring(0, 17)+ "59");
			}
			builder.append(" AND ");
			SqlFragment fragment = dialect.createDateRangeClause("PL.PLAN_START_DATETIME", 
					DateUtil.fromString(condition.getSearchStartDateFrom(), true),
					DateUtil.fromString(condition.getSearchStartDateTo(),true));
			builder.append(fragment.getFragment());
			params.addAll(Arrays.asList(fragment.getParameters()));
		}
		//企划结束时间
		if (StringUtil.hasValueAnyOf(condition.getSearchEndDateFrom(),condition.getSearchEndDateTo())) {
			if (StringUtil.hasValue(condition.getSearchEndDateTo())) {
				condition.setSearchEndDateTo(condition.getSearchEndDateTo().substring(0, 17) + "59");
			}
			builder.append(" AND ");
			SqlFragment fragment = dialect.createDateRangeClause("PL.PLAN_END_DATETIME",
					DateUtil.fromString(condition.getSearchEndDateFrom(), true), 
					DateUtil.fromString(condition.getSearchEndDateTo(), true));
			builder.append(fragment.getFragment());
			params.addAll(Arrays.asList(fragment.getParameters()));
		}

		// 企划分类
		if (StringUtil.hasValue(condition.getSearchPlanDetailType())) {
			builder.append(" AND PL.PLAN_DETAIL_TYPE = ? ");
			params.add(condition.getSearchPlanDetailType());
		}

		// 索条件:企划类型
		if (StringUtil.hasValueAnyOf(condition.getPlanTypeMode())) {
			builder.append(" AND PL.PLAN_TYPE =? ");
			params.add(condition.getPlanTypeMode());
		}

		// 统计类别
		if (StringUtil.hasValue(condition.getSearchOrderType())) {
			builder.append(" AND ");
			builder.append(" PSV.ORDER_TYPE = ? ");
			params.add(condition.getSearchOrderType());
		}
		  //排序
	    builder.append(" ORDER BY CASE"
	    + " WHEN PL.PLAN_START_DATETIME <= " + SqlDialect.getDefault().getCurrentDatetime()
	    + " AND PL.PLAN_END_DATETIME >= " + SqlDialect.getDefault().getCurrentDatetime() + " THEN 1 "
	    + " WHEN PL.PLAN_START_DATETIME > " + SqlDialect.getDefault().getCurrentDatetime() + " THEN 2 "
	    + " ELSE 3 END,"
	    + " PL.PLAN_START_DATETIME ASC");

		setSqlString(builder.toString());
		setParameters(params.toArray());
		setPageNumber(condition.getCurrentPage());
		setPageSize(condition.getPageSize());
	}
	

	public Class<PlanSummaryViewInfo> getRowType() {
		return PlanSummaryViewInfo.class;
	}

}
