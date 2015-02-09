package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

public class PointStatusShopQuery extends AbstractQuery<PointStatusShopSearchInfo> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private int pageSize = 10;

  private int pageNumber = 1;

  // ポイント利用状況
  public static final String LOAD_POINT_STATUS_SHOP_QUERY = "SELECT SH.SHOP_CODE, SH.SHOP_NAME, "
      // 10.1.3 10135 修正 ここから
      // + "SUM(CASE PH.POINT_ISSUE_STATUS WHEN 0 THEN PH.ISSUED_POINT ELSE 0 END) INEFFECTIVE_POINT, "
      // + "SUM(CASE PH.POINT_ISSUE_STATUS WHEN 1 THEN PH.ISSUED_POINT ELSE 0 END) TEMPORARY_POINT, "
      // + "SUM(CASE PH.POINT_ISSUE_STATUS WHEN 2 THEN PH.ISSUED_POINT ELSE 0 END) REST_POINT " + "FROM POINT_HISTORY PH "
      + "SUM(CASE PH.POINT_ISSUE_STATUS WHEN 0 THEN PH.ISSUED_POINT ELSE 0 END) TEMPORARY_POINT, "
      + "SUM(CASE PH.POINT_ISSUE_STATUS WHEN 1 THEN PH.ISSUED_POINT ELSE 0 END) REST_POINT, "
      + "SUM(CASE PH.POINT_ISSUE_STATUS WHEN 2 THEN PH.ISSUED_POINT ELSE 0 END) INEFFECTIVE_POINT "
      + "FROM POINT_HISTORY PH "
      // 10.1.3 10135 修正 ここまで
      + "LEFT OUTER JOIN SHOP SH ON PH.SHOP_CODE = SH.SHOP_CODE WHERE 1 = 1 ";

  public PointStatusShopQuery() {

  }

  public PointStatusShopQuery(PointStatusListSearchCondition condition, String query) {

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

      builder.append(" GROUP BY SH.SHOP_CODE, SH.SHOP_NAME ORDER BY SH.SHOP_CODE");

      this.pageNumber = condition.getCurrentPage();
      this.pageSize = condition.getPageSize();
    }

    setSqlString(sql + builder.toString());
    setParameters(params.toArray());

    Logger logger = Logger.getLogger(this.getClass());
    logger.info("sql:" + sql + builder.toString());

  }

  public Class<PointStatusShopSearchInfo> getRowType() {
    return PointStatusShopSearchInfo.class;
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
