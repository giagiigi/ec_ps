package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.communication.GiftCardRuleListSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class GiftCardRuleExportCondition extends CsvConditionImpl<GiftCardRuleCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private GiftCardRuleListSearchCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public GiftCardRuleListSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(GiftCardRuleListSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
