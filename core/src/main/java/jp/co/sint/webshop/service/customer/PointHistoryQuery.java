package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.SqlDialect;

import org.apache.log4j.Logger;

public class PointHistoryQuery extends AbstractQuery<PointHistoryList> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private int pageSize = 10;

  private int pageNumber = 1;

  public static final String LOAD_REVIEWPOINT_HISTORY_QUERY = "SELECT POINT_HISTORY_ID, POINT_ISSUE_DATETIME, CUSTOMER_CODE, "
      + "POINT_ISSUE_STATUS, POINT_ISSUE_TYPE, ORDER_NO, REVIEW_ID, ENQUETE_CODE, DESCRIPTION, ISSUED_POINT, SHOP_CODE, "
      + "POINT_USED_DATETIME, ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME FROM POINT_HISTORY "
      + "WHERE REVIEW_ID = ? AND POINT_ISSUE_STATUS = 0";

  public static final String LOAD_CUSTOMER_POINT_QUERY = "SELECT A.CUSTOMER_CODE, A.LAST_NAME, A.FIRST_NAME, "
      + "A.LAST_NAME_KANA, A.FIRST_NAME_KANA, A.EMAIL, A.UPDATED_DATETIME, B.PHONE_NUMBER, B.MOBILE_NUMBER, "
      + "COALESCE(A.REST_POINT, 0) AS REST_POINT, COALESCE(A.TEMPORARY_POINT, 0) AS TEMPORARY_POINT, "
//    postgreSQL start
      //+ "ADD_MONTHS(A.LATEST_POINT_ACQUIRED_DATE, C.POINT_PERIOD) AS EXPIRED_POINT_DATE " + "FROM CUSTOMER A "
      + SqlDialect.getDefault().getAddMonth("A.LATEST_POINT_ACQUIRED_DATE", "C.POINT_PERIOD") +" AS EXPIRED_POINT_DATE " + "FROM CUSTOMER A "
//    postgreSQL end      
      + "INNER JOIN (SELECT CUSTOMER_CODE, PHONE_NUMBER, MOBILE_NUMBER FROM CUSTOMER_ADDRESS WHERE ADDRESS_NO = 0) B "
      + "ON A.CUSTOMER_CODE = B.CUSTOMER_CODE INNER JOIN (SELECT POINT_PERIOD FROM POINT_RULE) C ON 1 = 1 "
      + "WHERE A.CUSTOMER_CODE = ?";

  public static final String LOAD_POINT_HISTORY_QUERY = "SELECT CUSTOMER_CODE, POINT_ISSUE_STATUS, "
      + "ORDER_NO, REVIEW_ID, ENQUETE_CODE, "
      + "DESCRIPTION, ISSUED_POINT, SHOP_CODE, POINT_USED_DATETIME, CREATED_DATETIME FROM POINT_HISTORY "
      + "WHERE CUSTOMER_CODE = ? ORDER BY CREATED_DATETIME DESC";

  public static final String DELETE_POINT_HISTORY_QUERY = "DELETE FROM POINT_HISTORY WHERE CUSTOMER_CODE = ? AND POINT_ISSUE_STATUS = 2";

  public static final String UPDATE_CUSTOMER_POINTINFO_QUERY = "UPDATE CUSTOMER SET REST_POINT = COALESCE(REST_POINT,0) + ? , "
      + "LATEST_POINT_ACQUIRED_DATE = ?, UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE CUSTOMER_CODE = ?";

  // 10.1.3 10174 追加 ここから
  public static final String LOAD_ISSUED_POINT_SUMMARY = "SELECT SUM(ISSUED_POINT) FROM POINT_HISTORY "
      + "WHERE CUSTOMER_CODE = ? AND POINT_ISSUE_STATUS = ?";
  // 10.1.3 10174 追加 ここまで

  public PointHistoryQuery() {

  }

  public PointHistoryQuery(CustomerSearchCondition condition) {

    List<Object> params = new ArrayList<Object>();

    params.add(condition.getCustomerCode());

    setSqlString(LOAD_POINT_HISTORY_QUERY);
    setParameters(params.toArray());

    this.pageNumber = condition.getCurrentPage();
    this.pageSize = condition.getPageSize();

    Logger logger = Logger.getLogger(this.getClass());
    logger.info("sql:" + LOAD_POINT_HISTORY_QUERY);

  }

  public Class<PointHistoryList> getRowType() {
    return PointHistoryList.class;
  }

  /**
   * pageNumberを取得します。
   * 
   * @return pageNumber
   */
  public int getPageNumber() {
    return pageNumber;
  }

  /**
   * pageSizeを取得します。
   * 
   * @return pageSize
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * pageNumberを設定します。
   * 
   * @param pageNumber
   *          pageNumber
   */
  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  /**
   * pageSizeを設定します。
   * 
   * @param pageSize
   *          pageSize
   */
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

}
