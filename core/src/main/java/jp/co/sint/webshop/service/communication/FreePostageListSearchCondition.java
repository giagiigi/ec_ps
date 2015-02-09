package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class FreePostageListSearchCondition extends SearchCondition {

  /** uid */
  private static final long serialVersionUID = 1L;

  private String searchFreePostageCode;

  private String searchFreePostageName;

  private String searchFreeStartDateFrom;

  private String searchFreeStartDateTo;

  private String searchStatus;

  /**
   * @return the searchFreePostageCode
   */
  public String getSearchFreePostageCode() {
    return searchFreePostageCode;
  }

  
  /**
   * @return the searchFreePostageName
   */
  public String getSearchFreePostageName() {
    return searchFreePostageName;
  }



  
  /**
   * @return the searchFreeStartDateFrom
   */
  public String getSearchFreeStartDateFrom() {
    return searchFreeStartDateFrom;
  }



  
  /**
   * @return the searchFreeStartDateTo
   */
  public String getSearchFreeStartDateTo() {
    return searchFreeStartDateTo;
  }



  
  /**
   * @return the searchStatus
   */
  public String getSearchStatus() {
    return searchStatus;
  }



  
  /**
   * @param searchFreePostageCode the searchFreePostageCode to set
   */
  public void setSearchFreePostageCode(String searchFreePostageCode) {
    this.searchFreePostageCode = searchFreePostageCode;
  }



  
  /**
   * @param searchFreePostageName the searchFreePostageName to set
   */
  public void setSearchFreePostageName(String searchFreePostageName) {
    this.searchFreePostageName = searchFreePostageName;
  }



  
  /**
   * @param searchFreeStartDateFrom the searchFreeStartDateFrom to set
   */
  public void setSearchFreeStartDateFrom(String searchFreeStartDateFrom) {
    this.searchFreeStartDateFrom = searchFreeStartDateFrom;
  }



  
  /**
   * @param searchFreeStartDateTo the searchFreeStartDateTo to set
   */
  public void setSearchFreeStartDateTo(String searchFreeStartDateTo) {
    this.searchFreeStartDateTo = searchFreeStartDateTo;
  }



  
  /**
   * @param searchStatus the searchStatus to set
   */
  public void setSearchStatus(String searchStatus) {
    this.searchStatus = searchStatus;
  }



  // 活动期间检查
  public boolean isValid() {

    boolean result = true;

    String startDateFrom = getSearchFreeStartDateFrom();

    String endDateTo = getSearchFreeStartDateTo();

    if (StringUtil.hasValueAllOf(startDateFrom, endDateTo)) {
      result &= StringUtil.isCorrectRange(startDateFrom, endDateTo);
    }
    return result;

  }

}
