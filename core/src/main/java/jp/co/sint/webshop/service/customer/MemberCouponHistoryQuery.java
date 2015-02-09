package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.StringUtil;

public class MemberCouponHistoryQuery extends AbstractQuery<MemberCouponHistory> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 代金券情报
  public static final String LOAD_COUPON_QUERY = "SELECT"
    + " CI.COUPON_ISSUE_DETAIL_NO,"
    + " CI.COUPON_CODE AS COUPON_RULE_NO,"
    // soukai add 2012/02/01 ob start
    + " CI.COUPON_ISSUE_NO,"
    + " CR.COUPON_NAME AS COUPON_RULE_NAME,"
    // soukai add 2012/02/01 ob end
    + " CI.ISSUE_ORDER_NO AS COUPON_ORDER_NO,"
    + " CI.COUPON_AMOUNT AS COUPON_PRICE,"
    + " TO_CHAR(CI.COUPON_ISSUE_DATETIME, 'yyyy/MM/dd') AS COUPON_ISSUE_DATE,"
    + " TO_CHAR(CI.USE_START_DATETIME, 'yyyy/MM/dd') AS COUPON_USE_START_DATE,"
    + " TO_CHAR(CI.USE_END_DATETIME, 'yyyy/MM/dd') AS COUPON_USE_END_DATE,"
    + " CI.MIN_USE_ORDER_AMOUNT AS COUPON_INVEST_PURCHASE_PRICE,"
    + " CI.USE_STATUS AS COUPON_USE,"
    + " CI.USE_ORDER_NO AS COUPON_USE_ORDER_NO,"
    + " CI.ISSUE_REASON AS COUPON_ISSUE_REASON"
    + " FROM NEW_COUPON_HISTORY CI"
    // soukai add 2012/02/01 ob start
    + " INNER JOIN NEW_COUPON_RULE CR"
    + " ON CI.COUPON_CODE = CR.COUPON_CODE";
    // soukai add 2012/02/01 ob end

  /** default constructor */
  public MemberCouponHistoryQuery() {

  }
  
  public MemberCouponHistoryQuery(MemberSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    if (condition != null) { // 検索条件設定
      builder.append(LOAD_COUPON_QUERY);
      builder.append(" WHERE 1 = 1");

      // 会员编号
      if (StringUtil.hasValue(condition.getSearchCustomerCode())) {
        builder.append(" AND CI.CUSTOMER_CODE = ?");
        params.add(condition.getSearchCustomerCode());
      }

      setPageNumber(condition.getCurrentPage());
      setPageSize(condition.getPageSize());
      setMaxFetchSize(condition.getMaxFetchSize());
    }

    // 排序
    builder.append(" ORDER BY CI.COUPON_ISSUE_DATETIME DESC, CI.COUPON_CODE DESC, CI.COUPON_ISSUE_DETAIL_NO DESC");
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

  public Class<MemberCouponHistory> getRowType() {
    return MemberCouponHistory.class;
  }
}
