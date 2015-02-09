package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

public class OrderCountLogGraphQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public OrderCountLogGraphQuery() {
  }

  public OrderCountLogGraphQuery(String shopCode) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT ");
    builder.append(SqlDialect.getDefault().toCharFromDate("ORDER_DATETIME") + " AS COUNT_DATE,");
    builder.append("CLIENT_GROUP, COUNT(*) AS COUNT_RESULT FROM ORDER_HEADER ");
    builder.append("WHERE ORDER_STATUS = ? ");
    params.add(OrderStatus.ORDERED.getValue());

    if (StringUtil.hasValue(shopCode)) {
      builder.append("AND SHOP_CODE = ? ");
      params.add(shopCode);
    }

    builder.append("AND TO_CHAR(ORDER_DATETIME, 'YYYY/MM/DD') >= SYSDATE - 7 ");
    builder.append("GROUP BY TO_CHAR(ORDER_DATETIME, 'YYYY/MM/DD'), CLIENT_GROUP ");

    setSqlString(builder.toString());
    setParameters(params.toArray());
  }
}
