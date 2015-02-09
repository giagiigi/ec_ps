package jp.co.sint.webshop.service.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

public class OrderCountSearchQuery extends SimpleQuery {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public OrderCountSearchQuery() {
  }

  public OrderCountSearchQuery(OrderCountSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT COUNT(ORDER_NO) FROM ORDER_HEADER WHERE 1=1 ");
    
    if (StringUtil.hasValue(condition.getPaymentStatus())) {
      builder.append("AND PAYMENT_STATUS = ? ");
      params.add(condition.getPaymentStatus());
    }
    
    if (StringUtil.hasValue(condition.getOrderStatus())) {
      builder.append("AND ORDER_STATUS = ? ");
      params.add(condition.getOrderStatus());
    }
    
    if (StringUtil.hasValue(condition.getOrderDatetime())) {
      builder.append("AND ORDER_DATETIME = " + SqlDialect.getDefault().toDate() + " ");
      params.add(condition.getOrderDatetime());
    }

    if (StringUtil.hasValue(condition.getPaymentLimitDate())) {
      if (condition.getPaymentLimitDate().equals(DateUtil.getSysdateString())) {
        builder.append("AND " + SqlDialect.getDefault().toCharFromDate("PAYMENT_LIMIT_DATE") + " < ? ");
        params.add(condition.getPaymentLimitDate());
      } else {
        builder.append("AND " + SqlDialect.getDefault().toCharFromDate("PAYMENT_LIMIT_DATE") + " >= SYSDATE ");
        builder.append("AND " + SqlDialect.getDefault().toCharFromDate("PAYMENT_LIMIT_DATE") + " <= ? ");
        params.add(condition.getPaymentLimitDate());
      }
    }

    if (StringUtil.hasValue(condition.getShopCode())) {
      builder.append("AND SHOP_CODE = ? ");
      params.add(condition.getShopCode());
    }

    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

}
