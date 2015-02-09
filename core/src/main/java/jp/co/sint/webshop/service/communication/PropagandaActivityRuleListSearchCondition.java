package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class PropagandaActivityRuleListSearchCondition extends SearchCondition {

  /** uid */
  private static final long serialVersionUID = 1L;

  private String searchActivityCode;

  private String searchActivityName;

  private String searchActivityType;

  private String searchActivityStartDateFrom;

  private String searchActivityStartDateTo;

  private String searchStatus;

  /**
   * @return the searchActivityCode
   */
  public String getSearchActivityCode() {
    return searchActivityCode;
  }

  /**
   * @return the searchActivityName
   */
  public String getSearchActivityName() {
    return searchActivityName;
  }

  /**
   * @return the searchActivityType
   */
  public String getSearchActivityType() {
    return searchActivityType;
  }

  /**
   * @return the searchActivityStartDateFrom
   */
  public String getSearchActivityStartDateFrom() {
    return searchActivityStartDateFrom;
  }

  /**
   * @return the searchActivityStartDateTo
   */
  public String getSearchActivityStartDateTo() {
    return searchActivityStartDateTo;
  }

  /**
   * @return the searchStatus
   */
  public String getSearchStatus() {
    return searchStatus;
  }

  /**
   * @param searchActivityCode
   *          the searchActivityCode to set
   */
  public void setSearchActivityCode(String searchActivityCode) {
    this.searchActivityCode = searchActivityCode;
  }

  /**
   * @param searchActivityName
   *          the searchActivityName to set
   */
  public void setSearchActivityName(String searchActivityName) {
    this.searchActivityName = searchActivityName;
  }

  /**
   * @param searchActivityType
   *          the searchActivityType to set
   */
  public void setSearchActivityType(String searchActivityType) {
    this.searchActivityType = searchActivityType;
  }

  /**
   * @param searchActivityStartDateFrom
   *          the searchActivityStartDateFrom to set
   */
  public void setSearchActivityStartDateFrom(String searchActivityStartDateFrom) {
    this.searchActivityStartDateFrom = searchActivityStartDateFrom;
  }

  /**
   * @param searchActivityStartDateTo
   *          the searchActivityStartDateTo to set
   */
  public void setSearchActivityStartDateTo(String searchActivityStartDateTo) {
    this.searchActivityStartDateTo = searchActivityStartDateTo;
  }

  /**
   * @param searchStatus
   *          the searchStatus to set
   */
  public void setSearchStatus(String searchStatus) {
    this.searchStatus = searchStatus;
  }

  // 活动期间检查
  public boolean isValid() {

    boolean result = true;

    String startDateFrom = getSearchActivityStartDateFrom();

    String endDateTo = getSearchActivityStartDateTo();

    if (StringUtil.hasValueAllOf(startDateFrom, endDateTo)) {
      result &= StringUtil.isCorrectRange(startDateFrom, endDateTo);
    }
    return result;

  }

}
