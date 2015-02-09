package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class CouponStatusSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String searchCustomerCode;

  private String searchIssueFromDate;

  private String searchIssueToDate;

  private String searchCouponStatus;

  private String searchSummaryCondition;

  /**
   * searchIssueFromDateを取得します。
   * 
   * @return searchIssueFromDate searchIssueFromDate
   */
  public String getSearchIssueFromDate() {
    return searchIssueFromDate;
  }

  /**
   * searchIssueFromDateを設定します。
   * 
   * @param searchIssueFromDate
   *          searchIssueFromDate
   */
  public void setSearchIssueFromDate(String searchIssueFromDate) {
    this.searchIssueFromDate = searchIssueFromDate;
  }

  /**
   * searchIssueToDateを取得します。
   * 
   * @return searchIssueToDate searchIssueToDate
   */
  public String getSearchIssueToDate() {
    return searchIssueToDate;
  }

  /**
   * searchIssueToDateを設定します。
   * 
   * @param searchIssueToDate
   *          searchIssueToDate
   */
  public void setSearchIssueToDate(String searchIssueToDate) {
    this.searchIssueToDate = searchIssueToDate;
  }

  /**
   * searchSummaryConditionを取得します。
   * 
   * @return searchSummaryCondition searchSummaryCondition
   */
  public String getSearchSummaryCondition() {
    return searchSummaryCondition;
  }

  /**
   * searchSummaryConditionを設定します。
   * 
   * @param searchSummaryCondition
   *          searchSummaryCondition
   */
  public void setSearchSummaryCondition(String searchSummaryCondition) {
    this.searchSummaryCondition = searchSummaryCondition;
  }

  public String getSearchCouponStatus() {
    return searchCouponStatus;
  }

  public void setSearchCouponStatus(String searchCouponStatus) {
    this.searchCouponStatus = searchCouponStatus;
  }

  public boolean isValid() {
    boolean result = true;
    String fromDate = getSearchIssueFromDate();
    String toDate = getSearchIssueToDate();
    if (StringUtil.hasValueAllOf(fromDate, toDate)) {
      result &= StringUtil.isCorrectRange(fromDate, toDate);
    }
    return result;
  }

  /**
   * searchCustomerCodeを取得します。
   * 
   * @return searchCustomerCode searchCustomerCode
   */
  public String getSearchCustomerCode() {
    return searchCustomerCode;
  }

  /**
   * searchCustomerCodeを設定します。
   * 
   * @param searchCustomerCode
   *          searchCustomerCode
   */
  public void setSearchCustomerCode(String searchCustomerCode) {
    this.searchCustomerCode = searchCustomerCode;
  }
}
