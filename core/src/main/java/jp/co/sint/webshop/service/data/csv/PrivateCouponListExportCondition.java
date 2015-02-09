package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.communication.PrivateCouponListSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class PrivateCouponListExportCondition extends CsvConditionImpl<PrivateCouponAnalysisCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private PrivateCouponListSearchCondition searchCondition;

  /**
   * @return the searchCondition
   */
  public PrivateCouponListSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * @param searchCondition
   *          the searchCondition to set
   */
  public void setSearchCondition(PrivateCouponListSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
