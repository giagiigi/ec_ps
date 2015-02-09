package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

public class CustomerRegisterCountSearchQuery extends SimpleQuery {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public CustomerRegisterCountSearchQuery() {
  }

  public CustomerRegisterCountSearchQuery(CustomerRegisterCountSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT COUNT(CUSTOMER_CODE) AS COUNT_RESULT FROM CUSTOMER ");
    builder.append("WHERE 1=1 ");

    if (StringUtil.hasValue(condition.getRegisterDate())) {
      builder.append("AND CREATED_DATETIME = " + SqlDialect.getDefault().toDate());
      params.add(condition.getRegisterDate());
    }

    if (StringUtil.hasValue(condition.getCustomerStatus())) {
      builder.append(" AND CUSTOMER_STATUS = ? ");
      params.add(condition.getCustomerStatus());
    }

    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

}
