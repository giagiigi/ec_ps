package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;
import jp.co.sint.webshop.service.order.ShippingListSearchCondition;

public class ShippingListExportCondition extends CsvConditionImpl<ShippingListCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private ShippingListSearchCondition condition;

  /**
   * @return the condition
   */
  public ShippingListSearchCondition getCondition() {
    return condition;
  }

  /**
   * @param condition
   *          the condition to set
   */
  public void setCondition(ShippingListSearchCondition condition) {
    this.condition = condition;
  }
}
