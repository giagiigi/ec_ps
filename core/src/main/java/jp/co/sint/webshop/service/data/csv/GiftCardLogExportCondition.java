package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.analysis.GiftCardLogSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;


public class GiftCardLogExportCondition extends CsvConditionImpl<GiftCardLogCsvSchema>{

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private GiftCardLogSearchCondition searchCondition;

  
  /**
   * @return the searchCondition
   */
  public GiftCardLogSearchCondition getSearchCondition() {
    return searchCondition;
  }

  
  /**
   * @param searchCondition the searchCondition to set
   */
  public void setSearchCondition(GiftCardLogSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }
  

}
