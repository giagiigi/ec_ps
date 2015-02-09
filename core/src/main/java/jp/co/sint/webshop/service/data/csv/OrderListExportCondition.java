package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;
import jp.co.sint.webshop.service.order.OrderListSearchCondition;

public class OrderListExportCondition extends CsvConditionImpl<OrderListCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private OrderListSearchCondition condition = new OrderListSearchCondition();

  /**
   * @return the condition
   */
  public OrderListSearchCondition getCondition() {
    return condition;
  }

  /**
   * @param condition
   *          the condition to set
   */
  public void setCondition(OrderListSearchCondition condition) {
    this.condition = condition;
  }

}
