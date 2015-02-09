package jp.co.sint.webshop.service.analysis;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;

public class CustomerRegisterLogGraphQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public CustomerRegisterLogGraphQuery() {
    String sql = "SELECT " + SqlDialect.getDefault().toCharFromDate("CREATED_DATETIME") + " AS COUNT_DATE, "
        + "COUNT(*) AS COUNT_RESULT FROM CUSTOMER WHERE CUSTOMER_STATUS = ? AND CREATED_DATETIME >= ?" + " GROUP BY "
        + SqlDialect.getDefault().toCharFromDate("CREATED_DATETIME");
    setSqlString(sql);

    setParameters(new Object[] {
        CustomerStatus.MEMBER.getValue(), DateUtil.truncateDate(DateUtil.addDate(DateUtil.getSysdate(), -7))
    });
  }
}
