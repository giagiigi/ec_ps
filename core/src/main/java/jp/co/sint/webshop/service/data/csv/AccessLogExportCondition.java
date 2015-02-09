package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.analysis.CountType;
import jp.co.sint.webshop.service.data.CsvConditionImpl;
import jp.co.sint.webshop.utility.DateRange;

public class AccessLogExportCondition extends CsvConditionImpl<AccessLogCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private CountType type;

  private String clientGroup;

  private DateRange range;

  /**
   * rangeを取得します。
   * 
   * @return range
   */

  public DateRange getRange() {
    return range;
  }

  /**
   * rangeを設定します。
   * 
   * @param range
   *          range
   */
  public void setRange(DateRange range) {
    this.range = range;
  }

  /**
   * typeを取得します。
   * 
   * @return type
   */

  public CountType getType() {
    return type;
  }

  /**
   * typeを設定します。
   * 
   * @param type
   *          type
   */
  public void setType(CountType type) {
    this.type = type;
  }

  /**
   * clientGroupを取得します。
   * 
   * @return clientGroup
   */

  public String getClientGroup() {
    return clientGroup;
  }

  /**
   * clientGroupを設定します。
   * 
   * @param clientGroup
   *          clientGroup
   */
  public void setClientGroup(String clientGroup) {
    this.clientGroup = clientGroup;
  }

}
