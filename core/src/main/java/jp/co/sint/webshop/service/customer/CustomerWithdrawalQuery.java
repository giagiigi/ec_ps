package jp.co.sint.webshop.service.customer;

public final class CustomerWithdrawalQuery {

  private static final long serialVersionUID = 1L;

  /**
   * default constructor
   */
  private CustomerWithdrawalQuery() {
  }

  public static final String ADDRESS_WITHDRAWAL_QUERY = "UPDATE CUSTOMER_ADDRESS SET"
      + " ADDRESS_ALIAS = '" + CustomerConstant.MASK_DATA + "', "
      + " ADDRESS_LAST_NAME = '" + CustomerConstant.MASK_DATA + "', "
      + " ADDRESS_FIRST_NAME = '" + CustomerConstant.MASK_DATA + "', "
      + " ADDRESS_LAST_NAME_KANA = '" + CustomerConstant.MASK_DATA + "', "
      + " ADDRESS_FIRST_NAME_KANA = '" + CustomerConstant.MASK_DATA + "', "
      + " POSTAL_CODE = '" + CustomerConstant.MASK_DATA + "', "
      + " ADDRESS1 = '" + CustomerConstant.MASK_DATA + "', "
      + " ADDRESS2 = '" + CustomerConstant.MASK_DATA  + "', "
      + " ADDRESS3 = '" + CustomerConstant.MASK_DATA + "', "
      + " ADDRESS4 = '" + CustomerConstant.MASK_DATA + "', "
      + " PHONE_NUMBER = '" + CustomerConstant.MASK_DATA + "', "
      + " MOBILE_NUMBER = '" + CustomerConstant.MASK_DATA + "',"
      + " UPDATED_DATETIME = ? WHERE CUSTOMER_CODE = ?";

  public static final String POINT_WITHDRAWAL_QUERY = "UPDATE POINT_HISTORY SET POINT_ISSUE_STATUS = ?,"
      + " UPDATED_DATETIME = ? WHERE CUSTOMER_CODE = ?";

  public static final String ORDER_WITHDRAWAL_QUERY = "UPDATE ORDER_HEADER SET "
     + " LAST_NAME = '" + CustomerConstant.MASK_DATA + "', "
     + " FIRST_NAME = '" + CustomerConstant.MASK_DATA + "', "
     + " LAST_NAME_KANA = '" + CustomerConstant.MASK_DATA + "', "
     + " FIRST_NAME_KANA = '" + CustomerConstant.MASK_DATA + "', "
     + " EMAIL = ?, "
     + " POSTAL_CODE = '" + CustomerConstant.MASK_DATA + "', "
     + " ADDRESS1 = '" + CustomerConstant.MASK_DATA + "', "
     + " ADDRESS2 = '" + CustomerConstant.MASK_DATA  + "', "
     + " ADDRESS3 = '" + CustomerConstant.MASK_DATA + "', "
     + " ADDRESS4 = '" + CustomerConstant.MASK_DATA + "', "
     + " PHONE_NUMBER = '" + CustomerConstant.MASK_DATA + "', "
     + " MOBILE_NUMBER = '" + CustomerConstant.MASK_DATA + "',"
     + " UPDATED_DATETIME = ? WHERE CUSTOMER_CODE = ?";

  public static final String SHIPPING_WITHDRAWAL_QUERY = "UPDATE SHIPPING_HEADER SET "
     + " ADDRESS_LAST_NAME = '" + CustomerConstant.MASK_DATA + "', "
     + " ADDRESS_FIRST_NAME = '" + CustomerConstant.MASK_DATA + "', "
     + " ADDRESS_LAST_NAME_KANA = '" + CustomerConstant.MASK_DATA + "', "
     + " ADDRESS_FIRST_NAME_KANA = '" + CustomerConstant.MASK_DATA + "', "
     + " POSTAL_CODE = '" + CustomerConstant.MASK_DATA + "', "
     + " ADDRESS1 = '" + CustomerConstant.MASK_DATA + "', "
     + " ADDRESS2 = '" + CustomerConstant.MASK_DATA  + "', "
     + " ADDRESS3 = '" + CustomerConstant.MASK_DATA + "', "
     + " ADDRESS4 = '" + CustomerConstant.MASK_DATA + "', "
     + " PHONE_NUMBER = '" + CustomerConstant.MASK_DATA + "', "
     + " MOBILE_NUMBER = '" + CustomerConstant.MASK_DATA + "',"
     + " UPDATED_DATETIME = ? WHERE CUSTOMER_CODE = ?";

  public static final String FAVORITE_WITHDRAWAL_QUERY = "DELETE FROM FAVORITE_COMMODITY"
     + " WHERE CUSTOMER_CODE = ?";

  public static final String RECOMMENDED_WITHDRAWAL_QUERY = "DELETE  FROM RECOMMENDED_COMMODITY"
     + " WHERE CUSTOMER_CODE = ?";

  public static final String REMINDER_WITHDRAWAL_QUERY = "DELETE  FROM REMINDER"
     + " WHERE CUSTOMER_CODE = ?";

  public static final String ARRIVAL_GOODS_WITHDRAWAL_QUERY = "DELETE  FROM ARRIVAL_GOODS"
     + " WHERE CUSTOMER_CODE = ?";

  public static final String ACTIVE_ORDER_QUERY = "SELECT OH.ORDER_NO "
    + "   FROM ORDER_HEADER OH INNER JOIN ORDER_SUMMARY_VIEW OSV ON OH.ORDER_NO = OSV.ORDER_NO "
    + "   LEFT OUTER JOIN CUSTOMER CU ON CU.CUSTOMER_CODE = OH.CUSTOMER_CODE  "
    + "   WHERE OH.ORDER_STATUS <> ? "
    // 20120320 shen update start
    // + "   AND (OSV.PAYMENT_STATUS = ? "
    // + "   OR OSV.FIXED_SALES_STATUS = ?) "
    + "   AND OSV.SHIPPING_STATUS_SUMMARY <> ?"
    + "   AND OSV.SHIPPING_STATUS_SUMMARY <> ?"
    // 20120320 shen update end
    + "   AND CU.CUSTOMER_CODE = ?";

  public static final String NOT_PAYMENT_ORDER_QUERY = "SELECT OH.ORDER_NO "
    // 10.1.6 10272 修正 ここから
    // + "   FROM ORDER_HEADER OH INNER JOIN ORDER_SUMMARY_VIEW OSV ON OH.ORDER_NO = OSV.ORDER_NO "
    // + "   LEFT OUTER JOIN CUSTOMER CU ON CU.CUSTOMER_CODE = OH.CUSTOMER_CODE  "
    + "   FROM ORDER_HEADER OH "
    + "   INNER JOIN CUSTOMER CU ON CU.CUSTOMER_CODE = OH.CUSTOMER_CODE  "
    // 10.1.6 10272 修正 ここまで
    + "   WHERE OH.ORDER_STATUS <> ? "
    // 10.1.6 10272 修正 ここから
    // + "   AND OSV.PAYMENT_STATUS = ?"
    + "   AND OH.PAYMENT_STATUS = ?"
    // 10.1.6 10272 修正 ここまで
    + "   AND CU.CUSTOMER_CODE = ?";
  
  public static final String DAILY_CUSTOMER_MESSAGE = "SELECT CM.* FROM  CUSTOMER_MESSAGE CM "
    + " WHERE CM.CREATED_DATETIME > ? AND CM.CREATED_DATETIME < ? AND CUSTOMER_CODE = ?";
    
  
}
