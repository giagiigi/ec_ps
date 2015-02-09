package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.data.domain.PointAmplificationRate;
import jp.co.sint.webshop.utility.PointUtil;

/**
 * 顧客サービスで使用するクエリを集約したクラス
 * 
 * @author System Integrator Corp.
 */
public final class CustomerServiceQuery {

  /** default constructor */
  private CustomerServiceQuery() {
  }

  public static final String EXPORT_CUSTOMER_QUERY = ""
      + "SELECT CUSTOMER.CUSTOMER_CODE, CUSTOMER.CUSTOMER_GROUP_CODE, CUSTOMER.LAST_NAME, "
      // modify by V10-CH 170 start
      // + "CUSTOMER.FIRST_NAME, CUSTOMER.LAST_NAME_KANA,
      // CUSTOMER.FIRST_NAME_KANA, CUSTOMER.LOGIN_ID, "
      + "CUSTOMER.LOGIN_ID, "
      // modify by V10-CH 170 end
      + "CUSTOMER.EMAIL, CUSTOMER.PASSWORD, CUSTOMER.BIRTH_DATE, CUSTOMER.SEX, CUSTOMER.REQUEST_MAIL_TYPE, "
      + "CUSTOMER.CLIENT_MAIL_TYPE, CUSTOMER.CAUTION, CUSTOMER.LOGIN_DATETIME, CUSTOMER.LOGIN_ERROR_COUNT, "
      + "CUSTOMER.LOGIN_LOCKED_FLG, CUSTOMER.CUSTOMER_STATUS, CUSTOMER.CUSTOMER_ATTRIBUTE_REPLY_DATE, "
      // modify by V10-CH start
      // + "CUSTOMER.LATEST_POINT_ACQUIRED_DATE, CUSTOMER.REST_POINT/" +
      // DIContainer.getWebshopConfig().getPointMultiple() + ",
      // CUSTOMER.TEMPORARY_POINT/" +
      // DIContainer.getWebshopConfig().getPointMultiple()+ ", "
      + "CUSTOMER.LATEST_POINT_ACQUIRED_DATE, trim(to_char(trunc(CUSTOMER.REST_POINT, "
      + PointUtil.getAcquiredPointScale()
      + " ),'"
      + PointAmplificationRate.fromValue(Integer.toString(PointUtil.getAcquiredPointScale())).getName()
      + "')) AS REST_POINT,"
      + "trim(to_char(trunc(CUSTOMER.TEMPORARY_POINT, "
      + PointUtil.getAcquiredPointScale()
      + "),'" + PointAmplificationRate.fromValue(Integer.toString(PointUtil.getAcquiredPointScale())).getName()
      + "'))AS TEMPORARY_POINT,"
      // modify by V10-CH end
      + "CUSTOMER.WITHDRAWAL_REQUEST_DATE, CUSTOMER.WITHDRAWAL_DATE, CA.ADDRESS_ALIAS, CA.POSTAL_CODE, "
      // modify by V10-CH 170 start
      // + "CA.PREFECTURE_CODE, CA.ADDRESS1, CA.ADDRESS2, CA.ADDRESS3,
      // CA.ADDRESS4, CA.PHONE_NUMBER, CUSTOMER.ORM_ROWID, "
      + "CA.PREFECTURE_CODE, CA.CITY_CODE, CA.ADDRESS1, CA.ADDRESS2, CA.ADDRESS3, CA.PHONE_NUMBER, CA.MOBILE_NUMBER, CUSTOMER.ORM_ROWID, "
      // modify by V10-CH 170 end
      + "CUSTOMER.CREATED_USER, CUSTOMER.CREATED_DATETIME, CUSTOMER.UPDATED_USER, CUSTOMER.UPDATED_DATETIME "
      + "FROM CUSTOMER INNER JOIN CUSTOMER_ADDRESS CA ON CUSTOMER.CUSTOMER_CODE = CA.CUSTOMER_CODE "
      + "WHERE CUSTOMER.CUSTOMER_STATUS = ? AND CA.ADDRESS_NO = ? ";
}
