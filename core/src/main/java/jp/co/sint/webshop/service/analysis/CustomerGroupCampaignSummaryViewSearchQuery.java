package jp.co.sint.webshop.service.analysis;

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

public class CustomerGroupCampaignSummaryViewSearchQuery extends AbstractQuery<CustomerGroupCampaignSummaryViewList> {

	/** uid */
	private static final long serialVersionUID = 1L;

	// 查询顾客组别优惠分析信息
	private static final String CAMPAIGN_ORDER_LIST = "SELECT "
			+ "CGC.SHOP_CODE, " + "CGC.CAMPAIGN_CODE, " + "CGC.CAMPAIGN_NAME, "
			+ "CGC.CAMPAIGN_NAME_EN,CGC.CAMPAIGN_NAME_JP, "
			+ "CGC.CAMPAIGN_START_DATETIME, " + "CGC.CAMPAIGN_END_DATETIME, "
			+ "CG.CUSTOMER_GROUP_CODE, " + "CG.CUSTOMER_GROUP_NAME, "
			+ "CG.CUSTOMER_GROUP_NAME_EN, "+ "CG.CUSTOMER_GROUP_NAME_JP, "
			+ "SV.ORDER_TOTAL_PRICE, " + "SV.ORDER_TOTAL_COUNT, "
			+ "SV.ORDER_UNIT_PRICE, " + "SV.CAMPAIGN_TOTAL_PRICE, "
			+ "SV.CANCEL_TOTAL_COUNT, " + "SV.CANCEL_TOTAL_PRICE, "
			+ "SV.CANCEL_UNIT_PRICE, " + "SV.CANCEL_CAMPAIGN_PRICE "
			+ "FROM CUSTOMER_GROUP_CAMPAIGN_SUMMARY_VIEW SV "
			+ " INNER JOIN CUSTOMER_GROUP_CAMPAIGN CGC ON (CGC.CAMPAIGN_CODE = SV.CAMPAIGN_CODE) "
			+ " INNER JOIN CUSTOMER_GROUP CG ON (SV.CUSTOMER_GROUP_CODE = CG.CUSTOMER_GROUP_CODE) ";

	// 比较是否在优惠活动期间内
	private static final String DATA_SORT_CONDITION = " ORDER BY "
			+ "SV.CAMPAIGN_STATUS,CGC.CAMPAIGN_START_DATETIME DESC";

	public CustomerGroupCampaignSummaryViewSearchQuery() {
	}

	public CustomerGroupCampaignSummaryViewSearchQuery(CustomerGroupCampaignSummaryViewSearchCondition condition) {

		StringBuilder builder = new StringBuilder(CAMPAIGN_ORDER_LIST);
		builder.append(" WHERE 1 = 1 ");
		List<Object> params = new ArrayList<Object>();

		// 検索条件:顾客群组

		if (StringUtil.hasValue(condition.getCustomerGroupCode())) {
			builder.append(" AND ");
			builder.append(" CG.CUSTOMER_GROUP_CODE = ? ");
			params.add(condition.getCustomerGroupCode());
		}

		// 検索条件:优惠种别

		if (StringUtil.hasValue(condition.getCampaignType())) {
			builder.append(" AND ");
			builder.append(" CGC.CAMPAIGN_TYPE = ? ");
			params.add(condition.getCampaignType());
		}

		// 検索条件:キャンペーンコード
		if (StringUtil.hasValue(condition.getCampaignCode())) {
			SqlDialect dialect = SqlDialect.getDefault();
			builder.append(" AND ");
			SqlFragment fragment = dialect.createLikeClause("CGC.CAMPAIGN_CODE", condition.getCampaignCode(), LikeClauseOption.PARTIAL_MATCH);
			builder.append(fragment.getFragment());
			params.addAll(Arrays.asList(fragment.getParameters()));
		}

		// 検索条件:キャンペーン名
		if (StringUtil.hasValue(condition.getCampaignName())) {
			SqlDialect dialect = SqlDialect.getDefault();
			builder.append(" AND ");
			SqlFragment fragment = dialect.createLikeClause("CGC.CAMPAIGN_NAME", condition.getCampaignName(), LikeClauseOption.PARTIAL_MATCH);
			builder.append(fragment.getFragment());
			params.addAll(Arrays.asList(fragment.getParameters()));
		}
		if (StringUtil.hasValue(condition.getCampaignNameEn())) {
			SqlDialect dialect = SqlDialect.getDefault();
			builder.append(" AND ");
			SqlFragment fragment = dialect.createLikeClause("CGC.CAMPAIGN_NAME_EN", condition.getCampaignNameEn(), LikeClauseOption.PARTIAL_MATCH);
			builder.append(fragment.getFragment());
			params.addAll(Arrays.asList(fragment.getParameters()));
			
		}if (StringUtil.hasValue(condition.getCampaignNameJp())) {
			SqlDialect dialect = SqlDialect.getDefault();
			builder.append(" AND ");
			SqlFragment fragment = dialect.createLikeClause("CGC.CAMPAIGN_NAME_JP", condition.getCampaignNameJp(), LikeClauseOption.PARTIAL_MATCH);
			builder.append(fragment.getFragment());
			params.addAll(Arrays.asList(fragment.getParameters()));
		}
		if (StringUtil.hasValueAnyOf(condition.getCampaignStartDateFrom(),
				condition.getCampaignStartDateTo())) {
			if (StringUtil.hasValue(condition.getCampaignStartDateTo())) {
				condition.setCampaignStartDateTo(condition.getCampaignStartDateTo().substring(0, 17) + "59");
			}
			SqlDialect dialect = SqlDialect.getDefault();
			builder.append(" AND ");
			SqlFragment fragment = dialect.createDateRangeClause("CGC.CAMPAIGN_START_DATETIME", DateUtil.fromString(condition.getCampaignStartDateFrom(), true), DateUtil.fromString(condition.getCampaignStartDateTo(), true));
			builder.append(fragment.getFragment());
			params.addAll(Arrays.asList(fragment.getParameters()));
		}
		if (StringUtil.hasValueAnyOf(condition.getCampaignEndDateFrom(),
				condition.getCampaignEndDateTo())) {
			if (StringUtil.hasValue(condition.getCampaignEndDateTo())) {
				condition.setCampaignEndDateTo(condition.getCampaignEndDateTo().substring(0, 17) + "59");
			}
			SqlDialect dialect = SqlDialect.getDefault();
			builder.append(" AND ");
			SqlFragment fragment = dialect.createDateRangeClause("CGC.CAMPAIGN_END_DATETIME", DateUtil.fromString(condition.getCampaignEndDateFrom(), true), DateUtil.fromString(condition.getCampaignEndDateTo(), true));
			builder.append(fragment.getFragment());
			params.addAll(Arrays.asList(fragment.getParameters()));
		}

		// 活动状态
		if (StringUtil.hasValue(condition.getCampaignStatus())) {
			if (ActivityStatus.IN_PROGRESS.getValue().equals(condition.getCampaignStatus())) {
				builder.append(" AND ");
				builder.append(" CGC.CAMPAIGN_START_DATETIME <= " + SqlDialect.getDefault().getCurrentDate() + "  AND CGC.CAMPAIGN_END_DATETIME >= " + SqlDialect.getDefault().getCurrentDate());
			} else if (ActivityStatus.NOT_PROGRESS.getValue().equals(condition.getCampaignStatus())) {
				builder.append(" AND ");
				builder.append(" CGC.CAMPAIGN_START_DATETIME > " + SqlDialect.getDefault().getCurrentDate());
			} else if (ActivityStatus.OUT_PROGRESS.getValue().equals(condition.getCampaignStatus())) {
				builder.append(" AND ");
				builder.append(" CGC.CAMPAIGN_END_DATETIME < " + SqlDialect.getDefault().getCurrentDate());
			}
		}

		// 並べ替え条件(実施中⇒実施前⇒実施終了, 各期間中では開始日の降順)
	    builder.append(DATA_SORT_CONDITION);

		setSqlString(builder.toString());
		setParameters(params.toArray());

		setPageNumber(condition.getCurrentPage());
		setPageSize(condition.getPageSize());
	}

	public Class<CustomerGroupCampaignSummaryViewList> getRowType() {
		return CustomerGroupCampaignSummaryViewList.class;
	}

}
