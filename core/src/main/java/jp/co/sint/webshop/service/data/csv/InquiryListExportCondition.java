package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.customer.InquirySearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class InquiryListExportCondition extends CsvConditionImpl<InquiryListCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private InquirySearchCondition searchCondition;

  /**
   * @return the searchCondition
   */
  public InquirySearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * @param searchCondition
   *          the searchCondition to set
   */
  public void setSearchCondition(InquirySearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
