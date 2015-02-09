package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.CouponStatus;

public class MyCouponHistorySearchQuery extends AbstractQuery<NewCouponHistoryInfo> {

  /**
   * uid
   */
  private static final long serialVersionUID = 1L;

  /** クーポン発行履歴を取得する */
  public static final String NEW_COUPON_HISTORY = "SELECT * FROM NEW COUPON_HISTORY WHERE 1=1";

  public static final String BASE_QUERY = "select A.* from (SELECT " 
    + "NCH.COUPON_NAME," 
    + "NCH.COUPON_NAME_EN," 
    + "NCH.COUPON_NAME_JP,"
    + "NCH.COUPON_AMOUNT," 
    + "NCH.MIN_USE_ORDER_AMOUNT," 
    + "NCH.USE_START_DATETIME," 
    + "NCH.USE_END_DATETIME, "
    + "NCH.COUPON_STATUS, " 
    + "NCH.USE_STATUS, " 
    + "NCH.COUPON_ISSUE_TYPE, " 
    + "NCH.coupon_issue_datetime, " 
    + "NCH.COUPON_PROPORTION ," 
    + "NCH.COUPON_CODE "   // 20140905 hdh add
    + "FROM NEW_COUPON_HISTORY NCH " ;

  public MyCouponHistorySearchQuery(MyCouponHistorySearchCondition condition) {
    buildQuery(BASE_QUERY, condition);
  }

  public static String getNewCouponHistoryByStatus() {
    StringBuilder builder = new StringBuilder(NEW_COUPON_HISTORY);
    builder.append(" AND ((COUPON_STATUS = '"  + CouponStatus.TEMPORARY.getValue() + "' AND COUPON_ISSUE_DATETIME - ? < 1)");
    builder.append(" OR (COUPON_STATUS = '" + CouponStatus.USED.getValue() + "' AND USE_END_DATETIME  - ? <= 15))");
    return builder.toString();
  }

  private void buildQuery(String query, MyCouponHistorySearchCondition condition) {
    StringBuilder builder = new StringBuilder(query);
    List<Object> params = new ArrayList<Object>();
    builder.append(" WHERE NCH.CUSTOMER_CODE = ? ");
    params.add(condition.getCustomerCode());

    // 排序
    /* 从前往后依次为：
     * 1，未使用，未过期的正式优惠券
     * 2，已使用，未过期的正式优惠券
     * 3，未使用，未过期的临时优惠券
     * 4，已使用，未过期的临时优惠券
     * 5，取消的优惠券
     * 6，已过期的优惠券
     * 
     * 其中在1,2,3,4,5中按使用截止日期升序排列
     * */
    builder.append(" ORDER BY CASE WHEN NCH.COUPON_STATUS= '" + CouponStatus.USED.getValue() + "' THEN 0");
    builder.append(" WHEN NCH.COUPON_STATUS= '" + CouponStatus.TEMPORARY.getValue() + "' THEN 1 ");
    builder.append(" ELSE 2 END, ");
    builder.append(" CASE WHEN NCH.use_status= '0' THEN 0 ELSE 1 END,");
    builder.append(" CASE WHEN NCH.COUPON_STATUS = '1' THEN NCH.use_start_datetime END) as A ");
    builder.append(" ORDER BY CASE WHEN NOW() > A.USE_END_DATETIME THEN 9 ");
    builder.append(" WHEN (A.COUPON_STATUS = '1' AND A.USE_STATUS = '0') THEN 0 WHEN (A.COUPON_STATUS = '1' AND A.USE_STATUS = '1') THEN 1 ");
    builder.append(" WHEN (A.COUPON_STATUS = '0' AND A.USE_STATUS = '0') THEN 2 WHEN (A.COUPON_STATUS = '0' AND A.USE_STATUS = '1') THEN 3 ELSE 4 END, ");
    //builder.append(" CASE WHEN A.COUPON_STATUS ='1' THEN '1900/01/01' ELSE A.coupon_issue_datetime END desc ");
    builder.append("USE_END_DATETIME");
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }
  
  /**优惠券发行履历*/
  public static final String GET_NEW_COUPON_HISTORY = "SELECT * FROM NEW_COUPON_HISTORY WHERE COUPON_ISSUE_NO = ?";
  
  /**优惠券规则_发行关联信息商品信息取得*/
  public static final String GET_COMMODITY_CODE_BY_LSSUE = 
    "SELECT "
    + "  NCRL.* "
    + "FROM NEW_COUPON_RULE_LSSUE_INFO NCRL "
    + "WHERE NCRL.COUPON_CODE = ?";
  
  public Class<NewCouponHistoryInfo> getRowType() {
    return NewCouponHistoryInfo.class;
  }

}
