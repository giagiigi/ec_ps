package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.analysis.RfmAnalysisExportSearchCondition;
import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class RmAnalysisExportCondition extends CsvConditionImpl<RmAnalysisCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private RfmAnalysisExportSearchCondition searchCondition;

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */

  public RfmAnalysisExportSearchCondition getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(RfmAnalysisExportSearchCondition searchCondition) {
    this.searchCondition = searchCondition;
  }

}
