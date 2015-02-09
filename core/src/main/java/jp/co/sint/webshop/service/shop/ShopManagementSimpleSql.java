package jp.co.sint.webshop.service.shop;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.domain.DeleteFlg;
import jp.co.sint.webshop.data.domain.ShopType;
import jp.co.sint.webshop.data.dto.Bank;
import jp.co.sint.webshop.data.dto.Commission;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CouponIssue;
import jp.co.sint.webshop.data.dto.DeliveryAppointedTime;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.Holiday;
import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.data.dto.MailTemplateDetailJp;
import jp.co.sint.webshop.data.dto.MailTemplateDetailUs;
import jp.co.sint.webshop.data.dto.MailTemplateHeader;
import jp.co.sint.webshop.data.dto.MailTemplateHeaderJp;
import jp.co.sint.webshop.data.dto.MailTemplateHeaderUs;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.PostPayment;
import jp.co.sint.webshop.data.dto.RegionBlock;
import jp.co.sint.webshop.data.dto.RegionBlockLocation;
import jp.co.sint.webshop.data.dto.ShippingCharge;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.SmsTemplateDetail;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;

public final class ShopManagementSimpleSql {

	public static final String LOAD_BANK_LIST = DatabaseUtil
			.getSelectAllQuery(Bank.class)
			+ " WHERE SHOP_CODE = ? AND PAYMENT_METHOD_NO = ? ORDER BY BANK_CODE, BANK_BRANCH_CODE, ACCOUNT_NO";

	// 05-20 Add start
	public static final String LOAD_POST_LIST = DatabaseUtil
			.getSelectAllQuery(PostPayment.class)
			+ " WHERE SHOP_CODE = ? AND PAYMENT_METHOD_NO = ?";

	// 05-20 Add end

	public static final String LOAD_REGION_BLOCK_LOCATION_LIST = DatabaseUtil
			.getSelectAllQuery(RegionBlockLocation.class)
			+ " WHERE SHOP_CODE = ? ORDER BY PREFECTURE_CODE";

	public static final String LOAD_REGION_BLOCK_LIST = DatabaseUtil
			.getSelectAllQuery(RegionBlock.class)
			+ " WHERE SHOP_CODE = ?";

	// modified by ytw start 2010-09-13
	public static final String LOAD_HOLIDAY = DatabaseUtil
			.getSelectAllQuery(Holiday.class)
			+ " WHERE SHOP_CODE = ? AND HOLIDAY >= "
			+ SqlDialect.getDefault().toDatetime()
			+ " AND HOLIDAY <="
			+ SqlDialect.getDefault().toDatetime();
	// modified by ytw end 2010-09-13

	public static final String LOAD_DELIVERY_TYPE_LIST = DatabaseUtil
			.getSelectAllQuery(DeliveryType.class)
			+ " WHERE SHOP_CODE = ? ORDER BY DELIVERY_TYPE_NO";

	public static final String LOAD_DELIVERY_APPOINTED_TIME_LIST = DatabaseUtil
			.getSelectAllQuery(DeliveryAppointedTime.class)
			+ " WHERE SHOP_CODE = ? AND DELIVERY_TYPE_NO = ?";

	public static final String LOAD_DELIVERY_APPOINTED_TIME_LIST_ADDITION_CODE = " AND DELIVERY_APPOINTED_TIME_CODE = ?";

	public static final String LOAD_DELIVERY_APPOINTED_TIME_LIST_ADDITION_TIME = " AND DELIVERY_APPOINTED_TIME_START = ? "
			+ "AND DELIVERY_APPOINTED_TIME_END = ?";

	public static final String LOAD_SHIPPING_CHARGE = DatabaseUtil
			.getSelectAllQuery(ShippingCharge.class)
			+ " WHERE SHOP_CODE = ? AND DELIVERY_TYPE_NO = ?";

	public static final String LOAD_COMMISSION_LIST = DatabaseUtil
			.getSelectAllQuery(Commission.class)
			+ " WHERE SHOP_CODE = ? AND PAYMENT_METHOD_NO = ? ORDER BY PAYMENT_PRICE_THRESHOLD";

	public static final String LOAD_COMMISSION = DatabaseUtil
			.getSelectAllQuery(Commission.class)
			+ " WHERE SHOP_CODE = ? AND PAYMENT_METHOD_NO = ? "
			+ "AND PAYMENT_PRICE_THRESHOLD = ( SELECT MIN(PAYMENT_PRICE_THRESHOLD) FROM COMMISSION "
			+ "WHERE SHOP_CODE = ? AND PAYMENT_METHOD_NO = ? AND PAYMENT_PRICE_THRESHOLD >= ?)";

	public static final String LOAD_MAIL_HEADER = DatabaseUtil
			.getSelectAllQuery(MailTemplateHeader.class)
			+ " WHERE SHOP_CODE = ? AND MAIL_TYPE = ?";

	public static final String LOAD_INFORMATION = DatabaseUtil
			.getSelectAllQuery(MailTemplateDetail.class)
			+ " WHERE MAIL_TYPE = '00' AND MAIL_TEMPLATE_BRANCH_NO = '1' ORDER BY MAIL_TEMPLATE_NO ";

	public static final String LOAD_MAIL_TEMPLATE_DETAIL = DatabaseUtil
			.getSelectAllQuery(MailTemplateDetail.class)
			+ " WHERE SHOP_CODE = ? AND MAIL_TYPE = ? AND MAIL_TEMPLATE_NO = ? ORDER BY MAIL_TEMPLATE_BRANCH_NO";


  public static final String LOAD_MAIL_TEMPLATE_HEARDER_US = DatabaseUtil
      .getSelectAllQuery(MailTemplateHeaderUs.class)
      + " WHERE SHOP_CODE = ? AND MAIL_TYPE = ? AND MAIL_TEMPLATE_NO = ?";
  
  public static final String LOAD_MAIL_TEMPLATE_DETAIL_US = DatabaseUtil
  .getSelectAllQuery(MailTemplateDetailUs.class)
  + " WHERE SHOP_CODE = ? AND MAIL_TYPE = ? AND MAIL_TEMPLATE_NO = ? ORDER BY MAIL_TEMPLATE_BRANCH_NO";

  public static final String LOAD_MAIL_TEMPLATE_HEARDER_JP = DatabaseUtil
  .getSelectAllQuery(MailTemplateHeaderJp.class)
  + " WHERE SHOP_CODE = ? AND MAIL_TYPE = ? AND MAIL_TEMPLATE_NO = ?";

  public static final String LOAD_MAIL_TEMPLATE_DETAIL_JP = DatabaseUtil
  .getSelectAllQuery(MailTemplateDetailJp.class)
  + " WHERE SHOP_CODE = ? AND MAIL_TYPE = ? AND MAIL_TEMPLATE_NO = ? ORDER BY MAIL_TEMPLATE_BRANCH_NO";

	// Add by V10-CH start
	public static final String LOAD_SMS_TEMPLATE_DETAIL = DatabaseUtil
			.getSelectAllQuery(SmsTemplateDetail.class)
			+ " WHERE SMS_TYPE = ?";

	// Add by V10-CH end
  //2013/05/05 优惠券对应 ob add start
	//取得中文短信模板
	public static final String LOAD_SMS_TEMPLATE_DETAIL_CN = 
	  "SELECT " +
	  " SHOP_CODE," +
	  " SMS_TYPE," +
	  " SMS_TEMPLATE_NO," +
	  " SMS_TEXT," +
	  " ORM_ROWID," +
	  " CREATED_USER," +
	  " UPDATED_USER," +
	  " UPDATED_DATETIME," +
	  " SMS_USE_FLG," +
	  " CREATED_DATETIME " +
	  " FROM SMS_TEMPLATE_DETAIL " +
	  " WHERE SMS_TYPE = ? ";
	
	 //取得日文短信模板
	 public static final String LOAD_SMS_TEMPLATE_DETAIL_JP = 
	    "SELECT " +
	    " SHOP_CODE," +
	    " SMS_TYPE," +
	    " SMS_TEMPLATE_NO," +
	    " SMS_TEXT_JP AS SMS_TEXT," +
	    " ORM_ROWID," +
	    " CREATED_USER," +
	    " UPDATED_USER," +
	    " UPDATED_DATETIME," +
	    " SMS_USE_FLG," +
	    " CREATED_DATETIME " +
	    " FROM SMS_TEMPLATE_DETAIL " +
	    " WHERE SMS_TYPE = ? ";
	
	 //取得英文短信模板
   public static final String LOAD_SMS_TEMPLATE_DETAIL_EN = 
     "SELECT " +
     " SHOP_CODE," +
     " SMS_TYPE," +
     " SMS_TEMPLATE_NO," +
     " SMS_TEXT_EN AS SMS_TEXT," +
     " ORM_ROWID," +
     " CREATED_USER," +
     " UPDATED_USER," +
     " UPDATED_DATETIME," +
     " SMS_USE_FLG," +
     " CREATED_DATETIME " +
     " FROM SMS_TEMPLATE_DETAIL " +
     " WHERE SMS_TYPE = ? ";
  //2013/05/05 优惠券对应 ob add end
	

	// postgreSQL start
	// public static final String LOAD_OPEN_SHOP_COUNT = "SELECT COUNT(*) FROM
	// SHOP " + "WHERE TRUNC(SYSDATE) "
	public static final String LOAD_OPEN_SHOP_COUNT = "SELECT COUNT(*) FROM SHOP "
			+ "WHERE "
			+ SqlDialect.getDefault().getTruncSysdate()
			+ " "
			// postgreSQL end
			+ "BETWEEN COALESCE (OPEN_DATETIME , TO_DATE('"
			+ DateUtil.toDateString(DateUtil.getMin())
			+ "', 'yyyy/MM/dd')) "
			+ "AND COALESCE (CLOSE_DATETIME , TO_DATE('"
			+ DateUtil.toDateString(DateUtil.getMax())
			+ "', 'yyyy/MM/dd'))"
			+ "AND SHOP_TYPE = " + ShopType.SHOP.getValue();

	public static final String LOAD_PAYMENT_METHOD_LIST = DatabaseUtil
			.getSelectAllQuery(PaymentMethod.class)
			+ " WHERE SHOP_CODE = ? AND ( DELETE_FLG IS NULL OR DELETE_FLG = "
			+ DeleteFlg.NOT_DELETED.getValue() + ") ORDER BY PAYMENT_METHOD_NO";

	public static final String LOAD_ALL_PAYMENT_METHOD_LIST = DatabaseUtil
			.getSelectAllQuery(PaymentMethod.class)
			+ " WHERE SHOP_CODE = ? ORDER BY PAYMENT_METHOD_NO";

	public static final String LOAD_SHOP_LIST = DatabaseUtil
			.getSelectAllQuery(Shop.class)
			+ " WHERE SHOP_TYPE = 1 ORDER BY SHOP_CODE";

	// 10.1.2 10098 削除 ここから
	// public static final String UPDATE_PAYMENT_TO_DELETE = "UPDATE
	// PAYMENT_METHOD SET DELETE_FLG = 1,"
	// + " UPDATED_USER = ? , UPDATED_DATETIME = ? WHERE SHOP_CODE = ?";
	// 10.1.2 10098 削除 ここまで

	public static final String DELETE_HOLIDAY = "DELETE FROM HOLIDAY WHERE SHOP_CODE = ? AND HOLIDAY >= "
			+ SqlDialect.getDefault().toDatetime()
			+ " AND HOLIDAY <= "
			+ SqlDialect.getDefault().toDatetime();

	public static final String DELETE_ALL_REGION_BLOCK_LOCATION = "DELETE FROM REGION_BLOCK_LOCATION WHERE SHOP_CODE = ?";

	public static final String DELETE_SHIPPING_CHARGE = "DELETE FROM SHIPPING_CHARGE "
			+ "WHERE SHOP_CODE = ? AND DELIVERY_TYPE_NO = ?";

	public static final String DELETE_DELIVERY_APPOINTED = "DELETE FROM DELIVERY_APPOINTED_TIME "
			+ "WHERE SHOP_CODE = ? AND DELIVERY_TYPE_NO = ?";

	public static final String COUNT_UNDELETABLE_ORDERS = "SELECT COUNT(*) FROM ORDER_HEADER OH "
			+ " INNER JOIN SHIPPING_HEADER SH ON OH.ORDER_NO = SH.ORDER_NO AND SH.SHOP_CODE = ? "
			+ " WHERE (SH.FIXED_SALES_STATUS = ? OR OH.PAYMENT_STATUS = ?) AND OH.ORDER_STATUS <> ? ";

	public static final String COUNT_COMMODITY_WITH_DELIVERY_TYPE_NO = DatabaseUtil
			.getSelectAllQuery(CommodityHeader.class)
			+ " WHERE SHOP_CODE = ? AND DELIVERY_TYPE_NO = ?";

	public static final String COUNT_SHIPPING_WITH_DELIVERY_TYPE_NO = DatabaseUtil
			.getSelectAllQuery(ShippingHeader.class)
			+ " WHERE SHOP_CODE = ? AND DELIVERY_TYPE_NO = ?";
	
	public static final String DELETE_DELIVERY_COMPANY = "DELETE FROM SHIPPING_CHARGE "
		+ "WHERE SHOP_CODE = ? AND DELIVERY_TYPE_NO = ?";

	// 10.1.7 10317 削除 ここから
	// public static final String LOAD_MODIFIABLE_ORDER_COUNT =
	// "SELECT COUNT(*) FROM ORDER_HEADER OH "
	// +
	// "LEFT OUTER JOIN ORDER_SUMMARY_VIEW OSV ON OH.ORDER_NO = OSV.ORDER_NO WHERE OH.SHOP_CODE = ? "
	// +
	// "AND OH.PAYMENT_METHOD_NO = ? AND ( OH.PAYMENT_STATUS = ? OR OSV.FIXED_SALES_STATUS = ? ) ";
	// 10.1.7 10317 削除 ここまで

	public static final String LOAD_COUPON_ISSUE_LIST = DatabaseUtil
			.getSelectAllQuery(CouponIssue.class)
			+ " WHERE SHOP_CODE = ? ORDER BY COUPON_ISSUE_NO";

	// 10.1.6 10275 追加 ここから
	public static String getDeliveryTypeFindQuery(String tableName) {
		return "SELECT 1 FROM DUAL WHERE EXISTS (SELECT * FROM " + tableName
				+ " WHERE SHOP_CODE = ? AND DELIVERY_TYPE_NO = ?)";
	}

	// 10.1.6 10275 追加 ここまで

	// 10.1.7 10317 追加 ここから
	public static final String EXISTS_DELETABLE_PAYMENT_QUERY = "select count(*) from (SELECT 1 FROM DUAL WHERE EXISTS (  SELECT * FROM ORDER_HEADER OH WHERE OH.SHOP_CODE = ? "
			+ "  AND OH.PAYMENT_METHOD_NO = ?  AND (OH.PAYMENT_STATUS = ? OR EXISTS ( "
			+ "    SELECT * FROM SHIPPING_HEADER SH WHERE OH.ORDER_NO = SH.ORDER_NO AND SH.FIXED_SALES_STATUS = ? "
			+ "  )))) aa  ";

	// 10.1.7 10317 追加 ここまで
	private ShopManagementSimpleSql() {

	}

	// 配送公司明细集合
	public static final String LOAD_DELIVERY_COMPANY_LIST = DatabaseUtil.getSelectAllQuery(DeliveryCompany.class) + " WHERE SHOP_CODE = ? ORDER BY DELIVERY_COMPANY_NO DESC";

	public static final String LOAD_SHIPPING_COMPANY_CHARGE = DatabaseUtil.getSelectAllQuery(DeliveryCompany.class) + " WHERE SHOP_CODE = ? AND DELIVERY_COMPANY_NO = ?";
	
	
	//配送地域别运费
	public static final String LOAD_DELIVERY_REGION_CHARGE_BY_COMPANYCODE="SELECT * FROM DELIVERY_REGION_CHARGE WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=? AND REGION_BLOCK_ID =?";
	
	//配送地域别运费集合
	public static final String LOAD_DELIVERY_REGION_CHARGE_LIST_BY_COMPANYCODE="SELECT * FROM DELIVERY_REGION_CHARGE WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=?";

	//查询同一个店铺和一个地域的配送时间信息
	public static final String LOAD_COMPANY_TIME_ALL_BY_COD_TYPE="SELECT * FROM DELIVERY_RELATED_INFO WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=? AND PREFECTURE_CODE=? AND COD_TYPE=? AND DELIVERY_DATE_TYPE=? ";
	//查询同一个店铺、地域、cod希望日区分和一个地域的配送时间信息
	public static final String LOAD_COMPANY_TIME_ALL_BY_TIME_CODE="SELECT * FROM DELIVERY_RELATED_INFO WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=? AND PREFECTURE_CODE=? AND COD_TYPE=? AND DELIVERY_DATE_TYPE=? AND DELIVERY_APPOINTED_TIME_CODE=?";
	//取得配送公司关联情报组别
	public static final String LOAD_DELIVERY_RELATED_TIME="SELECT SHOP_CODE,DELIVERY_COMPANY_NO,PREFECTURE_CODE,COD_TYPE,DELIVERY_DATE_TYPE,MAX(DELIVERY_APPOINTED_TIME_CODE) AS DELIVERY_APPOINTED_TIME_CODE, MAX(ORM_ROWID) AS ORM_ROWID,MAX(CREATED_USER) AS CREATED_USER,MAX(CREATED_DATETIME) AS CREATED_DATETIME," +
			"MAX(UPDATED_USER) AS UPDATED_USER,MAX(UPDATED_DATETIME) AS UPDATED_DATETIME " +
			"FROM DELIVERY_RELATED_INFO WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=? AND PREFECTURE_CODE=? " +
			"GROUP BY SHOP_CODE,DELIVERY_COMPANY_NO,PREFECTURE_CODE,COD_TYPE,DELIVERY_DATE_TYPE";
	
	//soukai add 2011/12/19 ob start
	public static final String[] DELETE_DELIVERY_COMPANY_INFO = {"DELETE FROM DELIVERY_COMPANY WHERE DELIVERY_COMPANY_NO = ?"
		,"DELETE FROM DELIVERY_REGION WHERE DELIVERY_COMPANY_NO = ?"
		,"DELETE FROM DELIVERY_RELATED_INFO WHERE DELIVERY_COMPANY_NO = ?"
	};
	//soukai add 2011/12/19 ob end
	
  //soukai add 2011/12/20 ob shb begin
	public static final String LOAD_DELIVERY_REGION_LIST_BY_DELIVERY_COMPANYNO = "SELECT * FROM DELIVERY_REGION WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=? ORDER BY PREFECTURE_CODE";
	
	public static final String LOAD_DELIVERY_REGION ="SELECT * FROM DELIVERY_REGION WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=? AND PREFECTURE_CODE = ? ";
	
	public static final String LOAD_DELIVERY_REGION_JD ="SELECT * FROM JD_DELIVERY_REGION WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=? AND PREFECTURE_CODE = ? ";
	
	public static final String LOAD_DELIVERY_REGION_LIST_BY_DELIVERY_COMPANYNO_JD = "SELECT * FROM JD_DELIVERY_REGION WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=? ORDER BY PREFECTURE_CODE";
	
	public static final String LOAD_DELIVERY_REGION_TMALL ="SELECT * FROM TMALL_DELIVERY_REGION WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=? AND PREFECTURE_CODE = ? ";
  //soukai add 2011/12/20 ob shb end
  //2013/04/15 配送公司设定对应 ob add start
	public static final String LOAD_COMPANYNO = "SELECT * FROM DELIVERY_LOCATION WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=?";
  //2013/04/15 配送公司设定对应 ob add end
	public static final String LOAD_DELIVERY_REGION_LIST_BY_DELIVERY_COMPANYNO_TMALL = "SELECT * FROM TMALL_DELIVERY_REGION WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=? ORDER BY PREFECTURE_CODE";
  
	public static final String LOAD_COMPANYNO_TMALL = "SELECT * FROM TMALL_DELIVERY_LOCATION WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=?";
	
	public static final String LOAD_COMPANYNO_JD = "SELECT * FROM JD_DELIVERY_LOCATION WHERE SHOP_CODE=? AND DELIVERY_COMPANY_NO=?";
  
}
