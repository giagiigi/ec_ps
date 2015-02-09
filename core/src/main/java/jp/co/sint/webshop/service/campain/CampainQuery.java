package jp.co.sint.webshop.service.campain;

import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CouponStatus;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.UseStatus;
import jp.co.sint.webshop.utility.SqlDialect;


public final class CampainQuery {

  private static final String SYSDATE = SqlDialect.getDefault().getCurrentDatetime();
  
	private CampainQuery() {
	}

	/** 查询出当前进行中的活动列表 */
	public static final String GET_CURRENT_CAMPAIN_LIST = ""
	  + " SELECT "
	  +	" CM.CAMPAIGN_CODE," 
	  + " CM.CAMPAIGN_NAME," 
	  + " CM.CAMPAIGN_START_DATE," 
	  + " CM.CAMPAIGN_END_DATE," 
	  + " CM.CAMPAIGN_TYPE, MEMO," 
	  + " CM.ORM_ROWID," 
	  + " CM.CREATED_USER," 
	  + " CM.CREATED_DATETIME," 
	  + " CM.UPDATED_USER,"
	  + " CM.UPDATED_DATETIME," 
	  + " CM.CAMPAIGN_NAME_EN," 
	  + " CM.CAMPAIGN_NAME_JP," 
	  + " CM.ORDER_LIMIT," 
	  + " CC.CAMPAIGN_CONDITION_TYPE," 
	  + " CC.CAMPAIGN_CONDITION_FLG," 
	  + " CC.ATTRIBUTR_VALUE," 
	  + " CC.MAX_COMMODITY_NUM" 
	  +	" FROM CAMPAIGN_MAIN CM"
	  +	" LEFT JOIN CAMPAIGN_CONDITION CC"
	  +	" ON CM.CAMPAIGN_CODE = CC.CAMPAIGN_CODE"
	  + " WHERE CAMPAIGN_START_DATE <= "
    + SYSDATE + "  AND "
    + SYSDATE+ "  <= CAMPAIGN_END_DATE"
    // 2012/11/19 促销活动  ob add start
    + " AND CAMPAIGN_TYPE = " + CampaignMainType.SHIPPING_CHARGE_FREE.getValue()
    // 2012/11/19 促销活动  ob add end
	  + " ORDER BY CM.CAMPAIGN_CODE" ;
    /** 查询出当前进行中的活动列表 */
	public static final String GET_CURRENT_CAMPAIN_LIST_BY_ORDERDATETIME = ""
	  + " SELECT "
	  +	" CM.CAMPAIGN_CODE," 
	  + " CM.CAMPAIGN_NAME," 
	  + " CM.CAMPAIGN_START_DATE," 
	  + " CM.CAMPAIGN_END_DATE," 
	  + " CM.CAMPAIGN_TYPE, MEMO," 
	  + " CM.ORM_ROWID," 
	  + " CM.CREATED_USER," 
	  + " CM.CREATED_DATETIME," 
	  + " CM.UPDATED_USER,"
	  + " CM.UPDATED_DATETIME," 
	  + " CM.CAMPAIGN_NAME_EN," 
	  + " CM.CAMPAIGN_NAME_JP," 
	  + " CM.ORDER_LIMIT," 
	  + " CC.CAMPAIGN_CONDITION_TYPE," 
	  + " CC.CAMPAIGN_CONDITION_FLG," 
	  + " CC.ATTRIBUTR_VALUE," 
	  + " CC.MAX_COMMODITY_NUM" 
	  +	" FROM CAMPAIGN_MAIN CM"
	  +	" LEFT JOIN CAMPAIGN_CONDITION CC"
	  +	" ON CM.CAMPAIGN_CODE = CC.CAMPAIGN_CODE"
	  + " WHERE ? BETWEEN CAMPAIGN_START_DATE  AND CAMPAIGN_END_DATE"
    // 2012/11/19 促销活动  ob add start
    + " AND CAMPAIGN_TYPE = " + CampaignMainType.SHIPPING_CHARGE_FREE.getValue()
    // 2012/11/19 促销活动  ob add end
	  + " ORDER BY CM.CAMPAIGN_CODE" ;;
	public static final String GET_CURRENT_CAMPAIN_LIST_NORMAL = ""
		  + " SELECT "
		  +	" CM.CAMPAIGN_CODE," 
		  + " CM.CAMPAIGN_NAME," 
		  + " CM.CAMPAIGN_START_DATE," 
		  + " CM.CAMPAIGN_END_DATE," 
		  + " CM.CAMPAIGN_TYPE, MEMO," 
		  + " CM.ORM_ROWID," 
		  + " CM.CREATED_USER," 
		  + " CM.CREATED_DATETIME," 
		  + " CM.UPDATED_USER,"
		  + " CM.UPDATED_DATETIME," 
		  + " CM.CAMPAIGN_NAME_EN," 
		  + " CM.CAMPAIGN_NAME_JP," 
		  + " CM.ORDER_LIMIT," 
		  + " CC.CAMPAIGN_CONDITION_TYPE," 
		  + " CC.CAMPAIGN_CONDITION_FLG," 
		  + " CC.ATTRIBUTR_VALUE," 
		  + " CC.MAX_COMMODITY_NUM" 
		  +	" FROM CAMPAIGN_MAIN CM"
		  +	" LEFT JOIN CAMPAIGN_CONDITION CC"
		  +	" ON CM.CAMPAIGN_CODE = CC.CAMPAIGN_CODE"
		  + " WHERE CAMPAIGN_START_DATE <= "
	    + SYSDATE + "  AND "
	    + SYSDATE+ "  <= CAMPAIGN_END_DATE"
	    + " ORDER BY CM.CAMPAIGN_CODE" ;
  /**
   * 查询用户历史活动订单数
   * 
   * 规则与优惠券使用限定规则一致
   * 
   * 固定电话号码相同 或者
   * 手机号码相同 或者
   * 客户名+送货地址相同
   */
	public static final String GET_USER_HISTORY_ORDERS = ""
	  + " SELECT COUNT(0) FROM CAMPAIGN_MAIN CM "
	  + " INNER JOIN ORDER_CAMPAIGN OC ON CM.CAMPAIGN_CODE = OC.CAMPAIGN_CODE"
	  + " INNER JOIN SHIPPING_HEADER SH ON OC.ORDER_NO = SH.ORDER_NO"
	  + " WHERE  "
	  + " CM.CAMPAIGN_CODE = ?"
	  + " AND SH.SHIPPING_STATUS <> " + ShippingStatus.CANCELLED.getValue()
	  + " AND SH.CUSTOMER_CODE = ?" ;
//	  + " AND (SH.MOBILE_NUMBER = ?"
//	  + " OR SH.PHONE_NUMBER = ?"
//	  + " OR REPLACE(SH.ADDRESS_LAST_NAME || SH.ADDRESS1 || SH.ADDRESS2 || SH.ADDRESS3 || SH.ADDRESS4,' ','') = ?)"; 
//	  
//	  + "SELECT"
//	  + " COUNT(0)"
//	  + " FROM SHIPPING_HEADER SH"
//	  + " WHERE"
//	  + " SH.MOBILE_NUMBER = ? "
//	  + " OR SH.PHONE_NUMBER = ? " 
//    + " OR REPLACE(SH.ADDRESS_LAST_NAME || SH.ADDRESS1 || SH.ADDRESS2 ||  SH.ADDRESS3 ||  SH.ADDRESS4,' ','') = ? " ;
	// 2012/11/20 促销活动 ob add start
	public static final String GET_CAMPAIGN_CONDITION_BY_CAMPAIGN_CODE = "" +
			"SELECT * FROM CAMPAIGN_CONDITION WHERE CAMPAIGN_CODE = ?" ;
	public static final String GET_MULTIPLE_GIFT_CAMPAIGN_SQL1 = "SELECT CM.* FROM CAMPAIGN_MAIN CM " +
			" INNER JOIN CAMPAIGN_CONDITION CC ON CM.CAMPAIGN_CODE = CC.CAMPAIGN_CODE" +
			" WHERE ? BETWEEN CM.CAMPAIGN_START_DATE AND CM.CAMPAIGN_END_DATE " +
			" AND (CC.ORDER_AMOUNT IS NULL OR CC.ORDER_AMOUNT <= ?) AND (CC.ADVERT_CODE IS NULL OR CC.ADVERT_CODE = ?)" +
			" AND CM.CAMPAIGN_TYPE = " + CampaignMainType.MULTIPLE_GIFT.getValue() ;
	
	public static final String GET_COUPON_CAMPAIGN_SQL = "SELECT CM.* FROM CAMPAIGN_MAIN CM " +
      " INNER JOIN CAMPAIGN_CONDITION CC ON CM.CAMPAIGN_CODE = CC.CAMPAIGN_CODE" +
      " WHERE ? BETWEEN CM.CAMPAIGN_START_DATE AND CM.CAMPAIGN_END_DATE " +
      " AND CM.CAMPAIGN_TYPE = " + CampaignMainType.SALE_OFF.getValue() ;
	
	public static final String GET_MULTIPLE_GIFT_CAMPAIGN_SQL2 = "SELECT CM.* FROM CAMPAIGN_MAIN CM " +
  " INNER JOIN CAMPAIGN_CONDITION CC ON CM.CAMPAIGN_CODE = CC.CAMPAIGN_CODE" +
  " WHERE ? BETWEEN CM.CAMPAIGN_START_DATE AND CM.CAMPAIGN_END_DATE " +
  " AND (CC.ORDER_AMOUNT IS NULL OR CC.ORDER_AMOUNT <= ?) AND CC.ADVERT_CODE IS NULL" +
  " AND CM.CAMPAIGN_TYPE = " + CampaignMainType.MULTIPLE_GIFT.getValue() ;
	public static final String GET_CAMPAIGN_CODE_SQL = ""
		  + " SELECT "
		  +	" CC.*"
		  +	" FROM CAMPAIGN_CONDITION CC"
		  +	" INNER JOIN CAMPAIGN_MAIN CM"
		  +	" ON CM.CAMPAIGN_CODE = CC.CAMPAIGN_CODE"
		  + " WHERE ? BETWEEN CM.CAMPAIGN_START_DATE AND CM.CAMPAIGN_END_DATE" 
		  + " AND CM.CAMPAIGN_TYPE = " + CampaignMainType.GIFT.getValue();
	public static final String GET_CAMPAIGN_DISCOUNT_SQL = ""
		  + " SELECT "
		  +	" CC.*"
		  +	" FROM CAMPAIGN_CONDITION CC"
		  +	" INNER JOIN CAMPAIGN_MAIN CM"
		  +	" ON CM.CAMPAIGN_CODE = CC.CAMPAIGN_CODE"
		  + " WHERE ? BETWEEN CM.CAMPAIGN_START_DATE AND CM.CAMPAIGN_END_DATE" 
		  + " AND CM.CAMPAIGN_TYPE = " + CampaignMainType.SALE_OFF.getValue();

	public static final String GET_CAMPAIN_DOING_INFO = " SELECT * FROM CAMPAIGN_DOINGS WHERE CAMPAIGN_CODE = ?";
	
	 public static final String GET_COUPON_CAMPAIGN_SQL2 = "SELECT CC.* FROM CAMPAIGN_MAIN CM " +
   " INNER JOIN CAMPAIGN_CONDITION CC ON CM.CAMPAIGN_CODE = CC.CAMPAIGN_CODE" +
   " WHERE " + SYSDATE + " BETWEEN CM.CAMPAIGN_START_DATE AND CM.CAMPAIGN_END_DATE " +
   " AND CM.CAMPAIGN_CODE = ? " +
   " AND CM.CAMPAIGN_TYPE = " + CampaignMainType.SALE_OFF.getValue();
	// 2012/11/20 促销活动 ob add end
	 
	 // 20131009 txw add start
	 public static final String GET_PRIVATE_COUPON_ISSUE_BRAND_SQL = "SELECT * FROM NEW_COUPON_RULE_LSSUE_INFO WHERE COUPON_CODE = ? AND BRAND_CODE IS NOT NULL";
	 
	 public static final String GET_PRIVATE_COUPON_ISSUE_CATEGORY_SQL = "SELECT * FROM NEW_COUPON_RULE_LSSUE_INFO WHERE COUPON_CODE = ? AND CATEGORY_CODE IS NOT NULL";
	 
	 public static final String GET_MAX_COUPON_ISSUE_SQL = "SELECT  * FROM NEW_COUPON_RULE_LSSUE_INFO WHERE COUPON_CODE = ? ORDER BY COUPON_USE_NO DESC LIMIT 1";
	 // 20131009 txw add end
	 // 20131104 wz add start
	 public static final String GET_GIFT_CARD_ISSUE_HISTORY_COUNT = "SELECT  COUNT(*) FROM GIFT_CARD_ISSUE_HISTORY WHERE CARD_CODE =?";
	 
	 public static final String GET_COUPON_HISTORY_COUNT = "SELECT  COUNT(*) FROM NEW_COUPON_HISTORY WHERE CUSTOMER_CODE =?";
	
   public static final String GET_GIFT_CARD_COUNT = "SELECT  COUNT(*) FROM CUSTOMER_CARD_INFO WHERE CUSTOMER_CODE = ? AND CARD_STATUS = 0";
	 
	 public static final String GET_GIFT_CARD_ISSUE_HISTORY = "SELECT  * FROM GIFT_CARD_ISSUE_HISTORY WHERE CARD_CODE =? ORDER BY CARD_HISTORY_NO";
	 
   public static final String GET_GIFT_CARD_ISSUE_HISTORY_NUMBER= "SELECT * FROM GIFT_CARD_ISSUE_HISTORY WHERE CARD_CODE=? AND CARD_HISTORY_NO=?";
	
   public static final String GIFT_CARD_ISSUE_DETAIL_UPDATE="UPDATE GIFT_CARD_ISSUE_DETAIL SET CANCEL_FLG=? WHERE CARD_CODE=? AND CARD_HISTORY_NO=?";
  
   public static final String GIFT_CARD_ISSUE_DETAIL_CARD_STATUS="SELECT CARD_STATUS FROM GIFT_CARD_ISSUE_DETAIL WHERE CARD_CODE=? AND CARD_HISTORY_NO=?";
     
   public static final String GIFT_CARD_ISSUE_DETAIL_UPDATE_STATUS="UPDATE GIFT_CARD_ISSUE_DETAIL SET CANCEL_FLG=? WHERE CARD_CODE=? AND CARD_HISTORY_NO=? AND CARD_STATUS=0";
   
   
   public static final String GET_AVALIABLE_COUPON_COUNT = "SELECT  COUNT(*) FROM NEW_COUPON_HISTORY WHERE " +
   		"CUSTOMER_CODE =? AND USE_END_DATETIME > NOW() AND USE_STATUS="+UseStatus.UNUSED.getValue()+" AND COUPON_STATUS="+CouponStatus.USED.getValue();
   
   
   // 20131104 wz add end
}

