package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;
import jp.co.sint.webshop.utility.DateRange;

public class SalesAmountExportCondition extends CsvConditionImpl<SalesAmountCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private DateRange range;

  private boolean site;

  /**
   * siteを取得します。
   * 
   * @return site
   */

  public boolean isSite() {
    return site;
  }

  /**
   * siteを設定します。
   * 
   * @param site
   *          site
   */
  public void setSite(boolean site) {
    this.site = site;
  }

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

}
