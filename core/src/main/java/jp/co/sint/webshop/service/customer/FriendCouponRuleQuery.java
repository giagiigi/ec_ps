package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.DateDistinguish;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.service.communication.FriendCouponRuleCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class FriendCouponRuleQuery extends AbstractQuery<FriendCouponRule> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public FriendCouponRuleQuery(FriendCouponRuleCondition condition) {
    StringBuilder builder = new StringBuilder(" SELECT * FROM FRIEND_COUPON_RULE AS FCR WHERE 1=1 ");

    List<Object> params = new ArrayList<Object>();
    // 检索条件：优惠券规则编号
    if (StringUtil.hasValue(condition.getSearchCouponCode())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("FCR.FRIEND_COUPON_RULE_NO", condition.getSearchCouponCode(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 检索条件：优惠券规则名称
    if (StringUtil.hasValue(condition.getSearchCouponName())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("FCR.FRIEND_COUPON_RULE_CN", condition
          .getSearchCouponName(), LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    if (StringUtil.hasValue(condition.getSearchCouponNameEn())) {
        SqlDialect dialect = SqlDialect.getDefault();
        SqlFragment fragment = dialect.createLikeClause("FCR.FRIEND_COUPON_RULE_EN", condition
            .getSearchCouponNameEn(), LikeClauseOption.PARTIAL_MATCH);
        builder.append(" AND " + fragment.getFragment());
        params.addAll(Arrays.asList(fragment.getParameters()));
      }
    if (StringUtil.hasValue(condition.getSearchCouponNameJp())) {
        SqlDialect dialect = SqlDialect.getDefault();
        SqlFragment fragment = dialect.createLikeClause("FCR.FRIEND_COUPON_RULE_JP", condition
            .getSearchCouponNameJp(), LikeClauseOption.PARTIAL_MATCH);
        builder.append(" AND " + fragment.getFragment());
        params.addAll(Arrays.asList(fragment.getParameters()));
      }
    
    // 检索条件：优惠券发行类别
    if (StringUtil.hasValue(condition.getSearchCampaignType())) {
      builder.append(" AND FCR.COUPON_ISSUE_TYPE = ? ");
      params.add(condition.getSearchCampaignType());
    }
    
    
    
    // 检索条件：发行可能日期区分
    if (StringUtil.hasValue(condition.getSearchIssueDateType())) {
      builder.append(" AND FCR.ISSUE_DATE_TYPE = ? ");
      params.add(condition.getSearchIssueDateType());
    }
    
    // 检索条件：优惠券发行可能月份类别
    if (DateDistinguish.PERMONTH.getValue().equals(condition.getSearchIssueDateType())
        &&StringUtil.hasValue(condition.getSearchIssueDateNum())) {
      builder.append(" AND FCR.ISSUE_DATE_NUM = ? ");
      params.add(condition.getSearchIssueDateNum());
    }
    
    
    // 检索条件：优惠券发行开始日时
    if (DateDistinguish.SPECIFYINGTHEDATEOF.getValue().equals(condition.getSearchIssueDateType()) &&
        StringUtil.hasValueAnyOf(condition.getSearchMinIssueStartDatetimeFrom(), condition.getSearchMinIssueStartDatetimeTo())) {
      SqlDialect dialect = SqlDialect.getDefault();
      String endDate = condition.getSearchMinIssueStartDatetimeTo();
      if(!StringUtil.isNullOrEmpty(endDate)){
        endDate =condition.getSearchMinIssueStartDatetimeTo().substring(0,17) + "59";
      }
      SqlFragment fragment = dialect.createRangeClause("FCR.ISSUE_START_DATE", DateUtil.fromString(condition.getSearchMinIssueStartDatetimeFrom()
          , true), DateUtil.fromString(endDate, true));
      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 检索条件：优惠券发行结束日时
    if (DateDistinguish.SPECIFYINGTHEDATEOF.getValue().equals(condition.getSearchIssueDateType()) &&
        StringUtil.hasValueAnyOf(condition.getSearchMinIssueEndDatetimeFrom(), condition.getSearchMinIssueEndDatetimeTo())) {
      SqlDialect dialect = SqlDialect.getDefault();
      String endDate = condition.getSearchMinIssueEndDatetimeTo();
      if(!StringUtil.isNullOrEmpty(endDate)){
        endDate =condition.getSearchMinIssueEndDatetimeTo().substring(0,17) + "59";
      }
      SqlFragment fragment = dialect.createRangeClause("FCR.ISSUE_END_DATE", DateUtil.fromString(condition.getSearchMinIssueEndDatetimeFrom()
          , true), DateUtil.fromString(endDate, true));
      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    
    setSqlString(builder.toString());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
    setParameters(params.toArray());
  }

  @Override
  public Class<FriendCouponRule> getRowType() {
    return FriendCouponRule.class;
  }

}
