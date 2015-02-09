package jp.co.sint.webshop.service.analysis;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;

public class PageViewLogGraphQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public PageViewLogGraphQuery() {
    String sql = "SELECT " + SqlDialect.getDefault().toCharFromDate("ACCESS_DATE") + " AS COUNT_DATE, "
        + "CLIENT_GROUP, SUM(PAGE_VIEW_COUNT) AS COUNT_RESULT FROM ACCESS_LOG " + "WHERE ACCESS_DATE BETWEEN ? AND ? "
        + "GROUP BY " + SqlDialect.getDefault().toCharFromDate("ACCESS_DATE") + ", CLIENT_GROUP "
        + "ORDER BY COUNT_DATE , CLIENT_GROUP ";

    setSqlString(sql);
    setParameters(new Object[] {
        DateUtil.truncateDate(DateUtil.addDate(DateUtil.getSysdate(), -7)), DateUtil.truncateDate(DateUtil.getSysdate())
    });
  }

}
