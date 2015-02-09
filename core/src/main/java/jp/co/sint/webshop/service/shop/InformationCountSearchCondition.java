package jp.co.sint.webshop.service.shop;

import jp.co.sint.webshop.service.SearchCondition;

public class InformationCountSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String informationType;

  private String informationStartDatetime;

  private String informationEndDatetime;

  /**
   * informationTypeを取得します。
   * 
   * @return informationType
   */
  public String getInformationType() {
    return informationType;
  }

  /**
   * informationTypeを設定します。
   * 
   * @param informationType
   *          informationType
   */
  public void setInformationType(String informationType) {
    this.informationType = informationType;
  }

  /**
   * informationEndDatetimeを取得します。
   * 
   * @return informationEndDatetime
   */
  public String getInformationEndDatetime() {
    return informationEndDatetime;
  }

  /**
   * informationEndDatetimeを設定します。
   * 
   * @param informationEndDatetime
   *          informationEndDatetime
   */
  public void setInformationEndDatetime(String informationEndDatetime) {
    this.informationEndDatetime = informationEndDatetime;
  }

  /**
   * informationStartDatetimeを取得します。
   * 
   * @return informationStartDatetime
   */
  public String getInformationStartDatetime() {
    return informationStartDatetime;
  }

  /**
   * informationStartDatetimeを設定します。
   * 
   * @param informationStartDatetime
   *          informationStartDatetime
   */
  public void setInformationStartDatetime(String informationStartDatetime) {
    this.informationStartDatetime = informationStartDatetime;
  }

}
