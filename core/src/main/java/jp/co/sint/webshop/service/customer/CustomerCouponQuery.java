package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.NewCouponRuleUseInfo;
import jp.co.sint.webshop.utility.SqlDialect;

public final class CustomerCouponQuery {

  /**
   * default constructor
   */
  private CustomerCouponQuery() {
  }

  public static final String LOAD_CUSTOMER_COUPON_QUERY = "SELECT CC.customer_coupon_id, CC.coupon_issue_no,CC.use_flg, CC.coupon_name,"
    + " CC.use_coupon_start_date,CC.use_coupon_end_date, "
    + " CC.coupon_price,CC.use_date,CC.order_no FROM CUSTOMER_COUPON CC "
    + " WHERE CUSTOMER_CODE = ? AND USE_FLG IN (?, ?, ?)ORDER BY customer_coupon_id desc";

  public static final String LOAD_CUSTOMER_COUPON_BACK_QUERY = "SELECT CC.customer_coupon_id, CC.coupon_issue_no,CC.use_flg, CC.coupon_name,"
    + " CC.use_coupon_start_date,CC.use_coupon_end_date, "
    + " CC.coupon_price,CC.use_date,CC.order_no FROM CUSTOMER_COUPON CC "
    + " WHERE CUSTOMER_CODE = ?  ORDER BY customer_coupon_id desc";
  
  public static final String LOAD_GIFT_CARD_ISSUE_DETAIL_BY_PASSWORD = "SELECT * FROM GIFT_CARD_ISSUE_DETAIL WHERE upper(PASS_WORD)  = ? ";
  
  public static final String LOAD_CUSTOMER_CARD_TOTAL_AMOUNT = "SELECT CASE WHEN SUM(DENOMINATION) IS NULL THEN 0 ELSE SUM(DENOMINATION) END AS DENOMINATION FROM CUSTOMER_CARD_INFO WHERE CUSTOMER_CODE = ?";

  public static final String LOAD_CUSTOMER_CARD_TOTAL_AMOUNT_UNABLE = "SELECT CASE WHEN SUM(DENOMINATION) IS NULL THEN 0 ELSE SUM(DENOMINATION) END AS DENOMINATION FROM CUSTOMER_CARD_INFO WHERE CUSTOMER_CODE = ? AND CARD_END_DATE < NOW()";
  
  public static final String LOAD_GIFT_CARD_RETURN_AMOUNT = "SELECT CASE WHEN SUM(RETURN_AMOUNT) IS NULL THEN 0 ELSE SUM(RETURN_AMOUNT) END AS RETURN_AMOUNT  FROM GIFT_CARD_RETURN_CONFIRM GCRC INNER JOIN CUSTOMER_CARD_INFO CCI ON CCI.CARD_ID = GCRC.CARD_ID AND CARD_END_DATE > NOW() WHERE GCRC.CUSTOMER_CODE = ?";
  
  public static final String LOAD_CUSTOMER_CARD_DETAIL = "  SELECT CCI.CARD_ID,CCI.DENOMINATION,CCI.RECHARGE_DATE , CCI.CARD_STATUS ,CCI.CARD_END_DATE, " 
  	+ " ( CCI.DENOMINATION +(SELECT  CASE WHEN SUM(RETURN_AMOUNT) IS NULL THEN 0 ELSE SUM(RETURN_AMOUNT) END AS RETURN_AMOUNT  FROM GIFT_CARD_RETURN_CONFIRM GCRC WHERE GCRC.CARD_ID = CCI.CARD_ID ) - (  SELECT CASE WHEN SUM(USE_AMOUNT) IS NULL THEN 0 ELSE SUM(USE_AMOUNT) END FROM  CUSTOMER_CARD_USE_INFO WHERE CARD_ID = CCI.CARD_ID AND USE_STATUS = 0)  ) AS AVALIBLE_AMOUNT "
  	+ " FROM CUSTOMER_CARD_INFO CCI WHERE CCI.CUSTOMER_CODE = ? ORDER BY CCI.CARD_ID";
  
  public static final String LOAD_CUSTOMER_CARD_TOTAL_USE_AMOUNT = "SELECT CASE WHEN SUM(CCUI.USE_AMOUNT) IS NULL THEN 0 ELSE SUM(USE_AMOUNT) END AS USE_AMOUNT "
    + " FROM CUSTOMER_CARD_USE_INFO CCUI INNER JOIN CUSTOMER_CARD_INFO CCI ON CCI.CARD_ID = CCUI.CARD_ID AND CCI.CARD_END_DATE > NOW() "
    +	" WHERE CCUI.CUSTOMER_CODE = ? AND CCUI.USE_STATUS = 0  ";

  public static final String LOAD_CUSTOMER_CARD_INFO_LIST = " SELECT * FROM ( " 
  	+	" SELECT INFO.CARD_ID, ( INFO.DENOMINATION + (select case when  sum(gcrc.return_amount) is null then 0 else  sum(gcrc.return_amount) end from gift_card_return_confirm gcrc where gcrc.card_id = INFO.CARD_ID ) -  (CASE WHEN USEINFO.USE_AMOUNT IS NULL THEN 0 ELSE USEINFO.USE_AMOUNT END) ) AS  DENOMINATION " 
  	+	" FROM CUSTOMER_CARD_INFO INFO LEFT JOIN " 
  	+	" (SELECT CARD_ID, SUM(USE_AMOUNT) AS USE_AMOUNT  FROM CUSTOMER_CARD_USE_INFO WHERE USE_STATUS = 0 GROUP BY CARD_ID) USEINFO " 
  	+	" ON INFO.CARD_ID = USEINFO.CARD_ID WHERE INFO.CARD_END_DATE > NOW() AND CUSTOMER_CODE = ? " 
  	+	" ORDER BY CASE WHEN CARD_STATUS=1 THEN 1 ELSE 0  END  DESC , CARD_END_DATE ASC ,DENOMINATION ASC " 
  	+	" )  A  WHERE DENOMINATION > 0 ";
  
  // 20111226 shen add start
  public static final String LOAD_UNUSED_PERSONAL_COUPON_LIST_QUERY = DatabaseUtil.getSelectAllQuery(NewCouponHistory.class)
      + " WHERE NOW() BETWEEN USE_START_DATETIME AND USE_END_DATETIME AND USE_STATUS = ? AND CUSTOMER_CODE = ?";
  
  public static final String LOAD_UNUSED_PERSONAL_COUPON_QUERY = DatabaseUtil.getSelectAllQuery(NewCouponHistory.class)
      + " WHERE NOW() BETWEEN USE_START_DATETIME AND USE_END_DATETIME AND USE_STATUS = ? AND CUSTOMER_CODE = ? AND COUPON_ISSUE_NO = ?";
  
  public static final String LOAD_PUBLIC_COUPON_QUERY = DatabaseUtil.getSelectAllQuery(NewCouponRule.class)
      + " WHERE NOW() BETWEEN MIN_USE_START_DATETIME AND MIN_USE_END_DATETIME AND COUPON_TYPE = ? AND COUPON_CODE = ?";
  
  public static final String LOAD_PERSONNAL_COUPON_QUERY = DatabaseUtil.getSelectAllQuery(NewCouponRule.class)
  + " WHERE COUPON_TYPE = ? AND COUPON_CODE = ?";
  
  // 20111226 shen add end
  //20120111 os013 add start   20120131 ysy update start
  //根据CUSTOMER_CODE查询出所有的优惠券信息
  public static final String LOAD_COUPON_HISTORY_LIST_QUERY = "SELECT *  FROM NEW_COUPON_HISTORY " 
	  + " WHERE CUSTOMER_CODE = ? "
	  + " ORDER BY COUPON_ISSUE_DATETIME DESC";
  //20120111 os013 add end    20120131 ysy update end

  public static final String LOAD_COUPON_HISTORY_LIST = DatabaseUtil.getSelectAllQuery(NewCouponHistory.class)
  + " WHERE CUSTOMER_CODE = ? AND USE_STATUS = 0 AND USE_END_DATETIME  >= "+SqlDialect.getDefault().getCurrentDatetime();
  
  public static final String GET_COUPON_TYPE = "SELECT coupon_type from new_coupon_rule where coupon_code = ?";
  
  public static final String GET_COUPON_CODE_BY_COUPON_ISSUE_NO = "SELECT coupon_code FROM new_coupon_history WHERE coupon_issue_no = ?";
  
  public static final String GET_NEW_COUPON_RULE_USE_INFO = DatabaseUtil.getSelectAllQuery(NewCouponRuleUseInfo.class) + " WHERE coupon_code = ?";
  
  
  public static final String GET_AVALIABLE_GIFT_CARD_COUNT = "  SELECT COUNT(*) FROM CUSTOMER_CARD_INFO CCI " +
    		" WHERE CCI.CUSTOMER_CODE = ? AND CCI.CARD_END_DATE > NOW() AND CCI.CARD_STATUS = 0  AND " +
    		" (CCI.DENOMINATION +(SELECT  CASE WHEN SUM(RETURN_AMOUNT) IS NULL THEN 0 ELSE SUM(RETURN_AMOUNT) END AS RETURN_AMOUNT  FROM GIFT_CARD_RETURN_CONFIRM GCRC WHERE GCRC.CARD_ID = CCI.CARD_ID ) - (  SELECT CASE WHEN SUM(USE_AMOUNT) IS NULL THEN 0 ELSE SUM(USE_AMOUNT) END FROM  CUSTOMER_CARD_USE_INFO WHERE CARD_ID = CCI.CARD_ID AND USE_STATUS = 0)) >0 ";
}
