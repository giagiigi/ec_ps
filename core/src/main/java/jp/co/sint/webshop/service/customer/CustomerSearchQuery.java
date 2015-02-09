package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class CustomerSearchQuery extends AbstractQuery<CustomerSearchInfo> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 顧客情報
  public static final String LOAD_CUSTOMER_QUERY = "CC.CUSTOMER_CODE, CC.CUSTOMER_GROUP_CODE,"
      + " CC.LAST_NAME, CC.FIRST_NAME, CC.LAST_NAME_KANA, CC.FIRST_NAME_KANA, CC.LOGIN_ID,"
      + " CC.EMAIL, CA.PHONE_NUMBER ,CA.MOBILE_NUMBER , CC.PASSWORD, CC.BIRTH_DATE, CC.SEX, CC.REQUEST_MAIL_TYPE, CC.CAUTION,"
      + " CC.LOGIN_DATETIME, CC.LOGIN_ERROR_COUNT, CC.LOGIN_LOCKED_FLG, CC.CUSTOMER_STATUS,"
      + " CC.CUSTOMER_ATTRIBUTE_REPLY_DATE, CC.LATEST_POINT_ACQUIRED_DATE,"
      + " CC.REST_POINT, CC.TEMPORARY_POINT, CC.WITHDRAWAL_REQUEST_DATE, CC.WITHDRAWAL_DATE,"
      + " CC.ORM_ROWID, CC.CREATED_USER, CC.CREATED_DATETIME, CC.UPDATED_USER, CC.UPDATED_DATETIME";

  // 顧客情報FROM(DEFAULT)
  //modify by os012 20111213 start
//  public static final String LOAD_CUSTOMER_QUERY_FROM_DEFAULT = " FROM CUSTOMER CC"
//      + " INNER JOIN CUSTOMER_ADDRESS CA ON CC.CUSTOMER_CODE = CA.CUSTOMER_CODE AND CA.ADDRESS_NO = ?";
	public static final String LOAD_CUSTOMER_QUERY_FROM_DEFAULT = " FROM CUSTOMER CC"
	+ " LEFT JOIN CUSTOMER_ADDRESS CA ON CC.CUSTOMER_CODE = CA.CUSTOMER_CODE AND CA.ADDRESS_NO = ?";
	//modify by os012 20111213 end
  // 顧客情報FROM(ショップ管理者)
  public static final String LOAD_CUSTOMER_QUERY_FROM_SHOP = " INNER JOIN ORDER_HEADER OH ON CC.CUSTOMER_CODE = OH.CUSTOMER_CODE";

  // 顧客アドレス情報
  public static final String LOAD_ADDRESS_QUERY = "CA.ADDRESS_LAST_NAME, CA.ADDRESS_FIRST_NAME,"
      + " CA.POSTAL_CODE, CA.ADDRESS1, CA.ADDRESS2, CA.ADDRESS3, CA.ADDRESS4, CA.PHONE_NUMBER, CA.MOBILE_NUMBER";

  // ログインIDで顧客情報を取得する
  public static final String LOAD_CUSTOMER_QUERY_BY_LOGIN_ID = DatabaseUtil.getSelectAllQuery(Customer.class)
      + " WHERE LOWER(LOGIN_ID) = ? OR LOWER(MOBILE_NUMBER) = ?";

  // 更新時重複メール判定SQL
  public static final String CHECK_EMAIL_QUERY_UPDATE = " SELECT EMAIL FROM CUSTOMER WHERE CUSTOMER_CODE <> ? AND LOWER(EMAIL) = ?";

  // 新規登録時重複メール判定SQL
  public static final String CHECK_EMAIL_QUERY_INSERT = " SELECT CUSTOMER_CODE FROM CUSTOMER WHERE LOWER(EMAIL) = ?";

  // 更新時重複ログインID判定SQL
  public static final String CHECK_LOGINID_QUERY_UPDATE = " SELECT LOGIN_ID FROM CUSTOMER "
      + " WHERE CUSTOMER_CODE <> ? AND LOGIN_ID = ?";

  // 新規登録時重複ログインID判定SQL
  public static final String CHECK_LOGINID_QUERY_INSERT = " SELECT LOGIN_ID FROM CUSTOMER WHERE LOGIN_ID = ?";

  // ショップ管理者でログインした際、自ショップで受注処理を行っているか判定
  public static final String CHECK_SHOP_CUSTOMER_QUERY = " SELECT CC.CUSTOMER_CODE FROM CUSTOMER CC "
      + " INNER JOIN ORDER_HEADER OH ON CC.CUSTOMER_CODE = OH.CUSTOMER_CODE" + " AND CC.CUSTOMER_CODE = ?"
      + " AND OH.SHOP_CODE = ?" + " GROUP BY CC.CUSTOMER_CODE";

  // 顧客情報(情報メール送信用)
  public static final String LOAD_INFORMATION_SEND_MAIL_QUERY = "CC.CUSTOMER_CODE, CC.EMAIL";
  
  //Add by V10-CH start
  public static final String LOAD_INFORMATION_SEND_SMS_QUERY = "CC.CUSTOMER_CODE, CC.EMAIL";
  //Add by V10-CH end

  // 情報メール送信(INSERT句)
  public static final String INSERT_INFORMATION_SEND_MAIL_INSERT_QUERY = " INSERT INTO BROADCAST_MAILQUEUE_DETAIL"
//	postgreSQL start	  
      //+ " SELECT BMH.MAIL_QUEUE_ID, C.CUSTOMER_CODE, C.EMAIL, ?," + " NULL, ?, BROADCAST_MAILQUEUE_DETAIL_SEQ.NEXTVAL,"
      + " SELECT BMH.MAIL_QUEUE_ID, C.CUSTOMER_CODE, C.EMAIL, ?," + " NULL, ?, "+SqlDialect.getDefault().getNextvalNOprm("BROADCAST_MAILQUEUE_DETAIL_SEQ")+","
//    postgreSQL end
      + " ?, ?, ?, ?";

  //Add by V10-CH start
  public static final String INSERT_INFORMATION_SEND_SMS_INSERT_QUERY = " INSERT INTO BROADCAST_MAILQUEUE_DETAIL"
    + " SELECT BMH.MAIL_QUEUE_ID, C.CUSTOMER_CODE, C.EMAIL, ?," + " NULL, ?, "+SqlDialect.getDefault().getNextvalNOprm("BROADCAST_MAILQUEUE_DETAIL_SEQ")+","
    + " ?, ?, ?, ?";
  //Add by V10-CH end
  
  // 情報メール送信(FROM句)
  public static final String INSERT_INFORMATION_SEND_MAIL_FROM_QUERY = " FROM BROADCAST_MAILQUEUE_HEADER BMH,"
      + " MAIL_TEMPLATE_HEADER MTH, (";

  //Add by V10-CH start
  public static final String INSERT_INFORMATION_SEND_SMS_FROM_QUERY = " FROM BROADCAST_MAILQUEUE_HEADER BMH,"
    + " MAIL_TEMPLATE_HEADER MTH, (";
  //Add by V10-CH end
  
  // 情報メール送信(WHERE句)
  public static final String INSERT_INFORMATION_SEND_MAIL_WHERE_QUERY = ") C WHERE MTH.MAIL_TYPE = ? AND MAIL_TEMPLATE_NO = ?"
      + " AND  BMH.MAIL_QUEUE_ID = ? ";

  //Add by V10-CH start
  public static final String INSERT_INFORMATION_SEND_SMS_WHERE_QUERY = ") C WHERE AND MAIL_TEMPLATE_NO = ?"
    + " AND  BMH.MAIL_QUEUE_ID = ? ";
  //Add by V10-CH end
  //20111225 os013 add start
  //根据支付宝用户编号查出没有退会的顧客コード
  public static final String LOAD_CUSTOMER_TMALLUSERID_QUERY ="SELECT CUSTOMER_CODE FROM CUSTOMER WHERE  TMALL_USER_ID= ? AND CUSTOMER_STATUS <> ?";
  //20111225 os013 add end
  
  // 20120112 ysy add start
  // 根据顾客编号和商品编号查找订单数
  public static final String LOAD_COUNT_ORDER_NO_QUERY = "SELECT COUNT(ORDER_NO) FROM ORDER_HEADER"
	  	+" WHERE CUSTOMER_CODE = ? "
	  	+" AND ORDER_NO IN (SELECT ORDER_NO FROM ORDER_DETAIL WHERE COMMODITY_CODE = ? )"; 
  // 20120112 ysy add end
  
  //2013/04/01 优惠券对应 ob add start
  public static final String GET_MOBILE_AUTH = "SELECT * FROM MOBILE_AUTH WHERE MOBILE_NUMBER = ? AND AUTH_CODE = ? AND END_DATETIME >= ? ";
  //2013/04/01 优惠券对应 ob add end
  
  public CustomerSearchQuery() {

  }

  public CustomerSearchQuery(CustomerSearchCondition condition, String query) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    final String sql = query;

    if (condition != null) { // 検索条件設定

     
      builder.append("SELECT ");
      builder.append(sql);
      builder.append(LOAD_CUSTOMER_QUERY_FROM_DEFAULT);
      // アドレス帳番号（本人）
    // 10.1.3 10150 修正 ここから
    // params.add(CustomerConstant.SELFE_ADDRESS_NO);
    params.add(CustomerConstant.SELF_ADDRESS_NO);
    // 10.1.3 10150 修正 ここまで

      // ショップ管理者
      if (condition.isShop()) {
        builder.append(LOAD_CUSTOMER_QUERY_FROM_SHOP);
        builder.append(" AND OH.SHOP_CODE = ?");
        params.add(condition.getShopCode());
      }
      
     

      builder.append(" WHERE 1 = 1");

      // 顧客コード
      if (StringUtil.hasValue(condition.getSearchCustomerFrom()) && StringUtil.hasValue(condition.getSearchCustomerTo())) {
        SqlFragment fragment = SqlDialect.getDefault().createRangeClause("CC.CUSTOMER_CODE", condition.getSearchCustomerFrom(),
            condition.getSearchCustomerTo());

        builder.append(" AND " + fragment.getFragment());
        for (Object o : fragment.getParameters()) {
          params.add(o);
        }
      } else if (StringUtil.hasValue(condition.getSearchCustomerFrom())) {
        builder.append(" AND CC.CUSTOMER_CODE >= ?");
        params.add(condition.getSearchCustomerFrom());
      } else if (StringUtil.hasValue(condition.getSearchCustomerTo())) {
        builder.append(" AND CC.CUSTOMER_CODE <= ?");
        params.add(condition.getSearchCustomerTo());
      }

      // 顧客グループ
      if (StringUtil.hasValue(condition.getSearchCustomerGroupCode())) {
        builder.append(" AND CC.CUSTOMER_GROUP_CODE = ?");
        params.add(condition.getSearchCustomerGroupCode());
      }

      // 電話番号
      if (StringUtil.hasValue(condition.getSearchTel())) {
        builder.append(" AND REPLACE(CA.PHONE_NUMBER,'-','') = ?");
        params.add(condition.getSearchTel());
      }

      //Add by V10-CH start
      // 手機番号
      if (StringUtil.hasValue(condition.getSearchMobile())) {
        builder.append(" AND CA.MOBILE_NUMBER = ?");
        params.add(condition.getSearchMobile());
      }
      //Add by V10-Ch end
      
      // 会員状態
      if (StringUtil.hasValue(condition.getSearchCustomerStatus())) {
        builder.append(" AND CC.CUSTOMER_STATUS = ?");
        params.add(condition.getSearchCustomerStatus());
      }

      // 顧客名
      if (StringUtil.hasValue(condition.getSearchCustomerName())) {
        builder.append(" AND ");
//      postgreSQL start        
        //builder.append(SqlDialect.getDefault().getLikeClausePattern("CONCAT(CC.LAST_NAME, CC.FIRST_NAME)"));
        builder.append(SqlDialect.getDefault().getLikeClausePattern(SqlDialect.getDefault().getConcat("CC.LAST_NAME", "CC.FIRST_NAME")));
//      postgreSQL end        
        params.add("%" + SqlDialect.getDefault().escape(condition.getSearchCustomerName()) + "%");
      }

      // 顧客名カナ
      if (StringUtil.hasValue(condition.getSearchCustomerNameKana())) {
        builder.append(" AND ");
//      postgreSQL start        
        //builder.append(SqlDialect.getDefault().getLikeClausePattern("CONCAT(CC.LAST_NAME_KANA, CC.FIRST_NAME_KANA)"));
        builder.append(SqlDialect.getDefault().getLikeClausePattern(SqlDialect.getDefault().getConcat("CC.LAST_NAME_KANA", "CC.FIRST_NAME_KANA")));
//      postgreSQL end        
        params.add("%" + SqlDialect.getDefault().escape(condition.getSearchCustomerNameKana()) + "%");
      }

      // メールアドレス
      if (StringUtil.hasValue(condition.getSearchEmail())) {
        SqlFragment fragment = SqlDialect.getDefault().createLikeClause("CC.EMAIL", condition.getSearchEmail(),
            LikeClauseOption.PARTIAL_MATCH);

        builder.append(" AND " + fragment.getFragment());
        for (Object o : fragment.getParameters()) {
          params.add(o);
        }
      }

      // 情報メール
      if (StringUtil.hasValue(condition.getSearchRequestMailType())) {
        if (condition.getSearchRequestMailType().equals(RequestMailType.UNWANTED.getValue())) {
          // メール希望しない
          builder.append(" AND CC.REQUEST_MAIL_TYPE = ?");
          params.add(RequestMailType.UNWANTED.getValue());
        }
      }

      // ログインロック
      if (StringUtil.hasValue(condition.getSearchLoginLockedFlg())) {
        builder.append(" AND CC.LOGIN_LOCKED_FLG = ?");
        params.add(condition.getSearchLoginLockedFlg());
      }

      // メール区分
      if (StringUtil.hasValue(condition.getSearchClientMailType())) {
        builder.append(" AND CC.CLIENT_MAIL_TYPE = ?");
        params.add(condition.getSearchClientMailType());
      }
      //20111224 os013 add start
      //支付宝用户编号
      if (StringUtil.hasValue(condition.getTmallUserId())) {
        builder.append(" AND CC.TMALL_USER_ID = ?");
        params.add(condition.getTmallUserId());
      }
      //会员区分
      if (StringUtil.hasValue(condition.getCustomerKbn())) {
        builder.append(" AND CC.CUSTOMER_KBN = ?");
        params.add(condition.getCustomerKbn());
      }
      //20111224 os013 add end
      // ショップ管理者
      if (condition.isShop()) {
        builder.append(" GROUP BY " + LOAD_CUSTOMER_QUERY);
      }

      // 並び順
      builder.append(" ORDER BY CC.CUSTOMER_CODE");

      setPageNumber(condition.getCurrentPage());
      setPageSize(condition.getPageSize());
      setMaxFetchSize(condition.getMaxFetchSize());

    }

    setSqlString(builder.toString());
    setParameters(params.toArray());

  }

  public Class<CustomerSearchInfo> getRowType() {
    return CustomerSearchInfo.class;
  }
}
