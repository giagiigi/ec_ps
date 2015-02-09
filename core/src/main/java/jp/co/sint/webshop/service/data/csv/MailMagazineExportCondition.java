package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class MailMagazineExportCondition extends CsvConditionImpl<MailMagazineCsvSchema> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private String mailMagazineCode;

  /**
   * mailMagazineCodeを取得します。
   * 
   * @return mailMagazineCode
   */

  public String getMailMagazineCode() {
    return mailMagazineCode;
  }

  /**
   * mailMagazineCodeを設定します。
   * 
   * @param mailMagazineCode
   *          mailMagazineCode
   */
  public void setMailMagazineCode(String mailMagazineCode) {
    this.mailMagazineCode = mailMagazineCode;
  }

}
