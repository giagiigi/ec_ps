package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class EnqueteListSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String searchEnqueteCodeFrom;

  private String searchEnqueteCodeTo;

  private String searchEnqueteName;

  private String searchEnqueteStartDateFrom;

  private String searchEnqueteStartDateTo;

  private String searchEnqueteEndDateFrom;

  private String searchEnqueteEndDateTo;

  public String getSearchEnqueteCodeFrom() {
    return searchEnqueteCodeFrom;
  }

  public void setSearchEnqueteCodeFrom(String searchEnqueteCodeFrom) {
    this.searchEnqueteCodeFrom = searchEnqueteCodeFrom;
  }

  public String getSearchEnqueteCodeTo() {
    return searchEnqueteCodeTo;
  }

  public void setSearchEnqueteCodeTo(String searchEnqueteCodeTo) {
    this.searchEnqueteCodeTo = searchEnqueteCodeTo;
  }

  public String getSearchEnqueteEndDateFrom() {
    return searchEnqueteEndDateFrom;
  }

  public void setSearchEnqueteEndDateFrom(String searchEnqueteEndDateFrom) {
    this.searchEnqueteEndDateFrom = searchEnqueteEndDateFrom;
  }

  public String getSearchEnqueteName() {
    return searchEnqueteName;
  }

  public void setSearchEnqueteName(String searchEnqueteName) {
    this.searchEnqueteName = searchEnqueteName;
  }

  public String getSearchEnqueteStartDateFrom() {
    return searchEnqueteStartDateFrom;
  }

  public void setSearchEnqueteStartDateFrom(String searchEnqueteStartDateFrom) {
    this.searchEnqueteStartDateFrom = searchEnqueteStartDateFrom;
  }

  public String getSearchEnqueteEndDateTo() {
    return searchEnqueteEndDateTo;
  }

  public void setSearchEnqueteEndDateTo(String searchEnqueteEndDateTo) {
    this.searchEnqueteEndDateTo = searchEnqueteEndDateTo;
  }

  public String getSearchEnqueteStartDateTo() {
    return searchEnqueteStartDateTo;
  }

  public void setSearchEnqueteStartDateTo(String searchEnqueteStartDateTo) {
    this.searchEnqueteStartDateTo = searchEnqueteStartDateTo;
  }

  public boolean isValid() {

    boolean result = true;

    String startDateFrom = getSearchEnqueteStartDateFrom();
    String startDateTo = getSearchEnqueteStartDateTo();
    String endDateFrom = getSearchEnqueteEndDateFrom();
    String endDateTo = getSearchEnqueteEndDateTo();

    if (StringUtil.hasValueAllOf(startDateFrom, startDateTo)) {
      result &= StringUtil.isCorrectRange(startDateFrom, startDateTo);
    }
    if (StringUtil.hasValueAllOf(endDateFrom, endDateTo)) {
      result &= StringUtil.isCorrectRange(endDateFrom, endDateTo);
    }
    return result;

  }

}
