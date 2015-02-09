package jp.co.sint.webshop.service.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

public class OrderSumRetailPriceSearchQuery extends SimpleQuery {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  public OrderSumRetailPriceSearchQuery() {
  }

  public OrderSumRetailPriceSearchQuery(OrderListSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT ");
    builder.append("COALESCE(SUM(ORDER_SUMMARY_VIEW.TOTAL_AMOUNT), 0) AS TOTAL_AMOUNT ");
    builder.append("FROM ORDER_HEADER ");
    builder.append("LEFT OUTER JOIN ORDER_SUMMARY_VIEW ON ORDER_HEADER.ORDER_NO = ORDER_SUMMARY_VIEW.ORDER_NO ");
    builder.append("WHERE 1=1 ");

    String orderStartDatetime = DateUtil.toDateString(DateUtil.getMin());
    String orderEndDatetime = DateUtil.toDateString(DateUtil.getMax());

    if (StringUtil.hasValue(condition.getOrderDatetimeStart())) {
      orderStartDatetime = condition.getOrderDatetimeStart();
    }
    if (StringUtil.hasValue(condition.getOrderDatetimeEnd())) {
      orderEndDatetime = condition.getOrderDatetimeEnd();
    }

    if (orderStartDatetime.equals(orderEndDatetime)) {
      builder.append(" AND ORDER_HEADER.ORDER_DATETIME = " + SqlDialect.getDefault().toDate());
      params.add(orderStartDatetime);
    } else {
      builder.append("AND " + SqlDialect.getDefault().toCharFromDate("ORDER_HEADER.ORDER_DATETIME") + " BETWEEN ? AND ? ");
      params.add(orderStartDatetime);
      params.add(orderEndDatetime);
    }

    if (StringUtil.hasValue(condition.getShopCode())) {
      builder.append("AND ORDER_HEADER.SHOP_CODE = ? ");
      params.add(condition.getShopCode());
    }

    if (StringUtil.hasValue(condition.getOrderStatus()[0])) {
      builder.append("AND ORDER_HEADER.ORDER_STATUS = ? ");
      params.add(condition.getOrderStatus()[0]);
    }

    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

}
