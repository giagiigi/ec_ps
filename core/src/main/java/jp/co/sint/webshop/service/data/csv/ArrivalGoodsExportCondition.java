package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.catalog.ArrivalGoodsSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class ArrivalGoodsExportCondition extends CsvConditionImpl<ArrivalGoodsCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private ArrivalGoodsSearchCondition condition;

  /**
   * @return the condition
   */
  public ArrivalGoodsSearchCondition getCondition() {
    return condition;
  }

  /**
   * @param condition
   *          the condition to set
   */
  public void setCondition(ArrivalGoodsSearchCondition condition) {
    this.condition = condition;
  }
}
