package jp.co.sint.webshop.service.data.csv;
import jp.co.sint.webshop.service.data.CsvConditionImpl;
import jp.co.sint.webshop.service.jd.order.JdOrderSearchCondition;

public class JdOrderListExportConditionTwo extends CsvConditionImpl<JdOrderListCsvTwoSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private JdOrderSearchCondition condition= new JdOrderSearchCondition();

  
  /**
   * @return the condition
   */
  public JdOrderSearchCondition getCondition() {
    return condition;
  }

  
  /**
   * @param condition the condition to set
   */
  public void setCondition(JdOrderSearchCondition condition) {
    this.condition = condition;
  }

 

}
