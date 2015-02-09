package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class PointStatusListSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String searchShopCode;

  private String searchIssueType;

  private String searchPointIssueStatus;

  private String searchPointIssueStartDate;

  private String searchPointIssueEndDate;

  private String searchSummaryCondition;

  private String searchCustomerCode;

  /**
   * @return searchPointIssueEndDate
   */
  public String getSearchPointIssueEndDate() {
    return searchPointIssueEndDate;
  }

  /**
   * @param searchPointIssueEndDate
   *          設定する searchPointIssueEndDate
   */
  public void setSearchPointIssueEndDate(String searchPointIssueEndDate) {
    this.searchPointIssueEndDate = searchPointIssueEndDate;
  }

  /**
   * @return searchPointIssueStartDate
   */
  public String getSearchPointIssueStartDate() {
    return searchPointIssueStartDate;
  }

  /**
   * @param searchPointIssueStartDate
   *          設定する searchPointIssueStartDate
   */
  public void setSearchPointIssueStartDate(String searchPointIssueStartDate) {
    this.searchPointIssueStartDate = searchPointIssueStartDate;
  }

  /**
   * @return searchPointIssueStatus
   */
  public String getSearchPointIssueStatus() {
    return searchPointIssueStatus;
  }

  /**
   * @param searchPointIssueStatus
   *          設定する searchPointIssueStatus
   */
  public void setSearchPointIssueStatus(String searchPointIssueStatus) {
    this.searchPointIssueStatus = searchPointIssueStatus;
  }

  /**
   * @return searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
  }

  /**
   * @param searchShopCode
   *          設定する searchShopCode
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  /**
   * @return searchSummaryCondition
   */
  public String getSearchSummaryCondition() {
    return searchSummaryCondition;
  }

  /**
   * @param searchSummaryCondition
   *          設定する searchSummaryCondition
   */
  public void setSearchSummaryCondition(String searchSummaryCondition) {
    this.searchSummaryCondition = searchSummaryCondition;
  }

  public boolean isValid() {

    boolean result = true;

    String issueDateFrom = getSearchPointIssueStartDate();
    String issueDateTo = getSearchPointIssueEndDate();

    if (StringUtil.hasValueAllOf(issueDateFrom, issueDateTo)) {
      result &= StringUtil.isCorrectRange(issueDateFrom, issueDateTo);
    }
    return result;

  }

  /**
   * @return searchCustomerCode
   */
  public String getSearchCustomerCode() {
    return searchCustomerCode;
  }

  /**
   * @param searchCustomerCode
   *          設定する searchCustomerCode
   */
  public void setSearchCustomerCode(String searchCustomerCode) {
    this.searchCustomerCode = searchCustomerCode;
  }

  
  /**
   * searchIssueTypeを取得します。
   *
   * @return searchIssueType
   */
  public String getSearchIssueType() {
    return searchIssueType;
  }

  
  /**
   * searchIssueTypeを設定します。
   *
   * @param searchIssueType 設定する searchIssueType
   */
  public void setSearchIssueType(String searchIssueType) {
    this.searchIssueType = searchIssueType;
  }
}
