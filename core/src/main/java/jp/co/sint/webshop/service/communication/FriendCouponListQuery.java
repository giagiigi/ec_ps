package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class FriendCouponListQuery extends AbstractQuery<FriendCouponList> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;
  
  public static final String GET_UNISSUED_FRIEND_COUPON_LIST_QUERY = "SELECT A.* " 
    + " FROM FRIEND_COUPON_RULE A " 
    + " WHERE A.friend_coupon_rule_no NOT IN ( " 
    + " SELECT B.friend_coupon_rule_no FROM FRIEND_COUPON_ISSUE_HISTORY B WHERE B.CUSTOMER_CODE = ?) ";
  
  public static final String GET_ISSUED_FRIEND_COUPON_LIST_QUERY = "SELECT B.* " 
    + " FROM NEW_COUPON_RULE B INNER JOIN FRIEND_COUPON_ISSUE_HISTORY C ON B.COUPON_CODE = C.COUPON_CODE "
    + " WHERE C.CUSTOMER_CODE = ? order by created_datetime desc";
    
  public static final String GET_FRIEND_COUPON_LIST_QUERY = DatabaseUtil.getSelectAllQuery(FriendCouponRule.class);
  
  public static final String FRIEND_COUPON_USE_QUERY = "SELECT A.COUPON_AMOUNT,A.COUPON_CODE,A.MIN_USE_ORDER_AMOUNT," 
    + " A.MIN_USE_START_DATETIME,A.MIN_USE_END_DATETIME,A.coupon_name,A.coupon_name_en,A.coupon_name_jp,D.LAST_NAME,A.applicable_objects, " 
    + " A.COUPON_ISSUE_TYPE,A.COUPON_PROPORTION," 
    // 20140417 hdh add start
    +		"C.MAX_USE_ORDER_AMOUNT "
    // 2040417 hdh add end
    + " FROM NEW_COUPON_RULE A " 
    +	" INNER JOIN FRIEND_COUPON_ISSUE_HISTORY B   ON A.COUPON_CODE = B.COUPON_CODE"
    + " INNER JOIN CUSTOMER D  ON B.CUSTOMER_CODE = D.CUSTOMER_CODE"
    // 20140417 hdh add start
    + " INNER JOIN FRIEND_COUPON_RULE C ON B.FRIEND_COUPON_RULE_NO = C.FRIEND_COUPON_RULE_NO "
    // 2040417 hdh add end
    + " WHERE A.COUPON_CODE=? "; 
  
  


  public Class<FriendCouponList> getRowType() {
    return FriendCouponList.class;
  }
}
