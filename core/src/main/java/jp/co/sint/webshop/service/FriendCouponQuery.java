package jp.co.sint.webshop.service;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.CouponStatus;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * サイト管理サービスで使用するクエリを集約したクラス
 * 
 * @author OB
 */
public class FriendCouponQuery extends AbstractQuery<FriendCouponLine> {
  private static final long serialVersionUID = 1L;
  
  public static final String GET_FRIEND_COUPON_USE = "SELECT COUPON_CODE,EXCHANGE_POINT_AMOUNT,COUPON_NAME,COUPON_NAME_JP,COUPON_NAME_EN,COUPON_AMOUNT,MIN_USE_ORDER_AMOUNT, "
                           + " TO_CHAR(MIN_USE_START_DATETIME,'YYYY/MM/DD') AS MIN_USE_START_DATETIME, "
                           + " TO_CHAR(MIN_USE_END_DATETIME,'YYYY/MM/DD') AS MIN_USE_END_DATETIME, "
                           + " MIN_USE_END_NUM AS MIN_USE_END_NUM, "
                           + " COUPON_ISSUE_TYPE AS COUPON_ISSUE_TYPE, "
                           + " COUPON_PROPORTION AS COUPON_PROPORTION, "
                           + " MAX_USE_ORDER_AMOUNT AS MAX_USE_ORDER_AMOUNT "
                           + " FROM NEW_COUPON_RULE " 
                           + " WHERE EXCHANGE_POINT_AMOUNT > 0 "
                           + " AND ? BETWEEN MIN_ISSUE_START_DATETIME AND MIN_ISSUE_END_DATETIME "
                           + " AND COUPON_TYPE = " + "'" + CouponType.SPECIAL_MEMBER_DISTRIBUTION.getValue() + "'"
                           + " ORDER BY DATE(MIN_USE_START_DATETIME) DESC,DATE(MIN_USE_END_DATETIME) DESC "; 

  /** default constructor */
  public FriendCouponQuery(FriendCouponSearchCondition condition) {
    List<Object> params = new ArrayList<Object>();
    StringBuilder b = new StringBuilder(" SELECT ");
    b.append("   B.COUPON_CODE AS COUPON_CODE,");
    b.append("   TO_CHAR(C.ORDER_DATETIME,'YYYY/MM/DD') AS COUPON_ISSUE_DATE ,");
    b.append("   B.COUPON_AMOUNT AS COUPON_AMOUNT,");
    b.append("   B.CUSTOMER_CODE AS CUSTOMER_CODE, ");
    b.append("   B.CUSTOMER_NAME AS CUSTOMER_NAME, ");
    b.append("   C.ORDER_NO AS ORDER_NO, ");
    
    b.append("CASE WHEN B.USE_HISTORY_ID IN ("
    +"     SELECT USE_HISTORY_ID FROM FRIEND_COUPON_USE_HISTORY WHERE COUPON_CODE = B.COUPON_CODE AND POINT_STATUS = '1' " 
    +" ORDER BY CREATED_DATETIME ASC LIMIT ("
    +"       SELECT COUPON_USE_NUM FROM FRIEND_COUPON_USE_HISTORY WHERE COUPON_CODE = B.COUPON_CODE LIMIT 1"
    +"     )"
    +"   )  THEN B.FORMER_USE_POINT ELSE POINT END AS ALL_POINT");
    b.append(" FROM FRIEND_COUPON_ISSUE_HISTORY A ");
    b.append(" INNER JOIN FRIEND_COUPON_USE_HISTORY B ON A.COUPON_CODE = B.COUPON_CODE ");
    b.append(" INNER JOIN ORDER_HEADER C ON C.ORDER_NO = B.ORDER_NO ");
    b.append(" WHERE B.POINT_STATUS = " + "'" + CouponStatus.USED.getValue() + "'" );

    if (StringUtil.hasValue(condition.getCustomerCode())) {
      b.append(" AND A.CUSTOMER_CODE = ? ");
      params.add(condition.getCustomerCode());
    }
    
    //b.append("GROUP BY B.COUPON_CODE, C.ORDER_DATETIME, B.COUPON_AMOUNT, B.CUSTOMER_CODE, B.CUSTOMER_NAME ");
    b.append("ORDER BY C.ORDER_DATETIME DESC  ");

    setSqlString(b.toString());
    setParameters(params.toArray());
    setMaxFetchSize(condition.getMaxFetchSize());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  public Class<FriendCouponLine> getRowType() {
    return FriendCouponLine.class;
  }

}
