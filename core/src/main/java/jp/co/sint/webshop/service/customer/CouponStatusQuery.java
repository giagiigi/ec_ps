package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

public class CouponStatusQuery extends AbstractQuery<CouponStatusAllInfo> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private int pageSize = 10;

  private int pageNumber = 1;

  public static final String LOAD_COUPON_STATUS_QUERY = 
    " SELECT cc.customer_coupon_id,cc.coupon_name,cc.coupon_price,"
  + "  cc.issue_date, cc.use_coupon_end_date, cc.use_flg,  cc.customer_code, cu.last_name AS customer_name,  cc.order_no, cc.use_date"
  + " FROM customer_coupon cc "
  + " LEFT JOIN CUSTOMER cu "
  + " ON CC.CUSTOMER_CODE = CU.CUSTOMER_CODE WHERE 1 = 1";

  public CouponStatusQuery() {

  }

  public CouponStatusQuery(CouponStatusSearchCondition condition, String query) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    final String sql = query;
    
    if (condition != null) {
      if (StringUtil.hasValue(condition.getSearchCouponStatus())) {
        builder.append(" AND cc.use_flg = ? ");
        params.add(condition.getSearchCouponStatus());
      }
      
      if (DateUtil.isCorrect(condition.getSearchIssueFromDate()) && DateUtil.isCorrect(condition.getSearchIssueToDate())) {
        builder.append(" AND cc.issue_date BETWEEN " + SqlDialect.getDefault().toDatetime() + " AND " + SqlDialect.getDefault().toDatetime());
        params.add(condition.getSearchIssueFromDate() + " 00:00:00");
        params.add(condition.getSearchIssueToDate() + " 23:59:59");
      } else if (DateUtil.isCorrect(condition.getSearchIssueFromDate())) {
        builder.append(" AND cc.issue_date >= " + SqlDialect.getDefault().toDatetime());
        params.add(condition.getSearchIssueFromDate() + " 00:00:00");
      } else if (DateUtil.isCorrect(condition.getSearchIssueToDate())) {
        builder.append(" AND cc.issue_date <= " + SqlDialect.getDefault().toDatetime());
        params.add(condition.getSearchIssueToDate() + " 23:59:59");
      }
      
      builder.append(" ORDER BY cc.issue_date DESC ");
      
      this.pageNumber = condition.getCurrentPage();
      this.pageSize = condition.getPageSize();
    }
    
    setSqlString(sql + builder.toString());
    setParameters(params.toArray());

    Logger logger = Logger.getLogger(this.getClass());
    logger.info("sql:" + sql + builder.toString());
  }

  public Class<CouponStatusAllInfo> getRowType() {
    return CouponStatusAllInfo.class;
  }

  /**
   * pageNumberを取得します。

   * 
   * @return pageNumber pageNumber
   */
  public int getPageNumber() {
    return pageNumber;
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
   * pageSizeを取得します。

   * 
   * @return pageSize pageSize
   */
  public int getPageSize() {
    return pageSize;
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
