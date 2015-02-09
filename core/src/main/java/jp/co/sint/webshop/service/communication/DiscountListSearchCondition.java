package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class DiscountListSearchCondition extends SearchCondition {

  /** uid */
  private static final long serialVersionUID = 1L;

  private String searchDiscountCode;

  private String searchDiscountName;

  private String searchDiscountStartDatetimeFrom;

  private String searchDiscountEndDatetimeTo;

  private String searchDiscountStatus;

  /**
   * @return the searchDiscountCode
   */
  public String getSearchDiscountCode() {
    return searchDiscountCode;
  }

  /**
   * @return the searchDiscountName
   */
  public String getSearchDiscountName() {
    return searchDiscountName;
  }

  /**
   * @return the searchDiscountStartDatetimeFrom
   */
  public String getSearchDiscountStartDatetimeFrom() {
    return searchDiscountStartDatetimeFrom;
  }

  /**
   * @return the searchDiscountEndDatetimeTo
   */
  public String getSearchDiscountEndDatetimeTo() {
    return searchDiscountEndDatetimeTo;
  }

  /**
   * @return the searchDiscountStatus
   */
  public String getSearchDiscountStatus() {
    return searchDiscountStatus;
  }

  /**
   * @param searchDiscountCode
   *          the searchDiscountCode to set
   */
  public void setSearchDiscountCode(String searchDiscountCode) {
    this.searchDiscountCode = searchDiscountCode;
  }

  /**
   * @param searchDiscountName
   *          the searchDiscountName to set
   */
  public void setSearchDiscountName(String searchDiscountName) {
    this.searchDiscountName = searchDiscountName;
  }

  /**
   * @param searchDiscountStartDatetimeFrom
   *          the searchDiscountStartDatetimeFrom to set
   */
  public void setSearchDiscountStartDatetimeFrom(String searchDiscountStartDatetimeFrom) {
    this.searchDiscountStartDatetimeFrom = searchDiscountStartDatetimeFrom;
  }

  /**
   * @param searchDiscountEndDatetimeTo
   *          the searchDiscountEndDatetimeTo to set
   */
  public void setSearchDiscountEndDatetimeTo(String searchDiscountEndDatetimeTo) {
    this.searchDiscountEndDatetimeTo = searchDiscountEndDatetimeTo;
  }

  /**
   * @param searchDiscountStatus
   *          the searchDiscountStatus to set
   */
  public void setSearchDiscountStatus(String searchDiscountStatus) {
    this.searchDiscountStatus = searchDiscountStatus;
  }

  // 活动期间检查
  public boolean isValid() {

    boolean result = true;

    String startDateFrom = getSearchDiscountStartDatetimeFrom();

    String endDateTo = getSearchDiscountEndDatetimeTo();

    if (StringUtil.hasValueAllOf(startDateFrom, endDateTo)) {
      result &= StringUtil.isCorrectRange(startDateFrom, endDateTo);
    }
    return result;

  }

}
