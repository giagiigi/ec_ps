package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class EnqueteSummaryInputExportCondition extends CsvConditionImpl<EnqueteSummaryInputCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private String enqueteCode;

  private Long enqueteQuestionNo;

  /**
   * enqueteCodeを取得します。
   * 
   * @return enqueteCode
   */

  public String getEnqueteCode() {
    return enqueteCode;
  }

  /**
   * enqueteCodeを設定します。
   * 
   * @param enqueteCode
   *          enqueteCode
   */
  public void setEnqueteCode(String enqueteCode) {
    this.enqueteCode = enqueteCode;
  }

  /**
   * enqueteQuestionNoを取得します。
   * 
   * @return enqueteQuestionNo
   */

  public Long getEnqueteQuestionNo() {
    return enqueteQuestionNo;
  }

  /**
   * enqueteQuestionNoを設定します。
   * 
   * @param enqueteQuestionNo
   *          enqueteQuestionNo
   */
  public void setEnqueteQuestionNo(Long enqueteQuestionNo) {
    this.enqueteQuestionNo = enqueteQuestionNo;
  }

}
