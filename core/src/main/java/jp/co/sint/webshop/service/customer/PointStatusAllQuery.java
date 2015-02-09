package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

public class PointStatusAllQuery extends AbstractQuery<PointStatusAllSearchInfo> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private int pageSize = 10;

  private int pageNumber = 1;

  // ポイント利用状況
  public static final String LOAD_POINT_STATUS_QUERY = "SELECT PH.SHOP_CODE, S.SHOP_NAME, "
      + "PH.CUSTOMER_CODE, C.LAST_NAME, C.FIRST_NAME, PH.POINT_ISSUE_TYPE, "
      + "PH.ISSUED_POINT, PH.POINT_ISSUE_STATUS, PH.ORDER_NO, TO_CHAR(O.ORDER_DATETIME, 'YYYY/MM/DD HH24:MI:SS') ORDER_DATETIME, "
      + "TO_CHAR(O.PAYMENT_DATE, 'YYYY/MM/DD') PAYMENT_DATE, PH.REVIEW_ID, PH.ENQUETE_CODE, "
      + "PH.DESCRIPTION, TO_CHAR(PH.POINT_ISSUE_DATETIME, 'YYYY/MM/DD HH24:MI:SS') POINT_ISSUE_DATETIME FROM POINT_HISTORY PH "
      + "INNER JOIN CUSTOMER C ON PH.CUSTOMER_CODE = C.CUSTOMER_CODE " + "LEFT OUTER JOIN SHOP S ON PH.SHOP_CODE = S.SHOP_CODE "
      + "LEFT OUTER JOIN ORDER_HEADER O ON PH.ORDER_NO = O.ORDER_NO " + " WHERE 1 = 1";

  public static final String LOAD_POINT_STATUS_CUSTOMER_QUERY = "SELECT PH.SHOP_CODE, "
      + "S.SHOP_NAME, PH.CUSTOMER_CODE, C.LAST_NAME, PH.POINT_ISSUE_TYPE, "
      + "PH.ISSUED_POINT, PH.POINT_ISSUE_STATUS, PH.ORDER_NO, TO_CHAR(O.ORDER_DATETIME, 'YYYY/MM/DD HH24:MI:SS') ORDER_DATETIME, "
      + "TO_CHAR(O.PAYMENT_DATE, 'YYYY/MM/DD') PAYMENT_DATE,  PH.REVIEW_ID, PH.ENQUETE_CODE, "
      + "PH.DESCRIPTION, PH.POINT_USED_DATETIME, TO_CHAR(PH.POINT_ISSUE_DATETIME, 'YYYY/MM/DD HH24:MI:SS') POINT_ISSUE_DATETIME "
      + "FROM POINT_HISTORY PH INNER JOIN CUSTOMER C ON PH.CUSTOMER_CODE = C.CUSTOMER_CODE "
      + "LEFT OUTER JOIN SHOP S ON PH.SHOP_CODE = S.SHOP_CODE " + "LEFT OUTER JOIN ORDER_HEADER O ON PH.ORDER_NO = O.ORDER_NO "
      + " WHERE 1 = 1";

  public PointStatusAllQuery() {

  }

  public PointStatusAllQuery(PointStatusListSearchCondition condition, String query) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    final String sql = query;

    if (condition != null) { // 検索条件設定
      
      if (StringUtil.hasValue(condition.getSearchShopCode())) {
        builder.append(" AND PH.SHOP_CODE = ?");
        params.add(condition.getSearchShopCode());
      }

      if (StringUtil.hasValue(condition.getSearchIssueType())) {
        builder.append(" AND PH.POINT_ISSUE_TYPE = ?");
        params.add(condition.getSearchIssueType());
      }

      if (StringUtil.hasValue(condition.getSearchPointIssueStatus())) {
        builder.append(" AND PH.POINT_ISSUE_STATUS = ?");
        params.add(condition.getSearchPointIssueStatus());
      }

      if (DateUtil.isCorrect(condition.getSearchPointIssueStartDate())
          && DateUtil.isCorrect(condition.getSearchPointIssueEndDate())) {
        builder.append(" AND PH.POINT_ISSUE_DATETIME BETWEEN TO_DATE(?, 'YYYY/MM/DD HH24:MI:SS') "
            + "AND TO_DATE(?, 'YYYY/MM/DD HH24:MI:SS')");
        params.add(condition.getSearchPointIssueStartDate() + " 00:00:00");
        params.add(condition.getSearchPointIssueEndDate() + " 23:59:59");
      } else if (DateUtil.isCorrect(condition.getSearchPointIssueStartDate())) {
        builder.append(" AND PH.POINT_ISSUE_DATETIME >= TO_DATE(?, 'YYYY/MM/DD HH24:MI:SS')");
        params.add(condition.getSearchPointIssueStartDate() + " 00:00:00");
      } else if (DateUtil.isCorrect(condition.getSearchPointIssueEndDate())) {
        builder.append(" AND PH.POINT_ISSUE_DATETIME <= TO_DATE(?, 'YYYY/MM/DD HH24:MI:SS')");
        params.add(condition.getSearchPointIssueEndDate() + " 23:59:59");
      }

      if (StringUtil.hasValue(condition.getSearchCustomerCode())) {
        builder.append(" AND PH.CUSTOMER_CODE = ?");
        params.add(condition.getSearchCustomerCode());
      }

      builder.append(" ORDER BY PH.POINT_ISSUE_DATETIME DESC");

      this.pageNumber = condition.getCurrentPage();
      this.pageSize = condition.getPageSize();
    }

    setSqlString(sql + builder.toString());
    setParameters(params.toArray());

    Logger logger = Logger.getLogger(this.getClass());
    logger.info("sql:" + sql + builder.toString());

  }

  public Class<PointStatusAllSearchInfo> getRowType() {
    return PointStatusAllSearchInfo.class;
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
