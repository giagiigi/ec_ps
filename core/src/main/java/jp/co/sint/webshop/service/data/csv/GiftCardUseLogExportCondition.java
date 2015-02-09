package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.analysis.GiftCardUseLogListSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class GiftCardUseLogExportCondition extends CsvConditionImpl<GiftCardUseLogCsvSchema> {

  private static final long serialVersionUID = 1L;

  private GiftCardUseLogListSearchCondition searchCondition;

  /**
   * @return the searchCondition
   */
  public GiftCardUseLogListSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * @param searchCondition
   *          the searchCondition to set
   */
  public void setSearchCondition(GiftCardUseLogListSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
