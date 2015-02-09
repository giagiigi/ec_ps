package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class CustomerMessageSearchCondition extends SearchCondition {

  /** uid */
  private static final long serialVersionUID = 1L;

  private String searchCustomerCode;
  
  private String searchMessageStartDatetimeFrom;

  private String searchMessageEndDatetimeTo;



  
  /**
   * @return the searchCustomerCode
   */
  public String getSearchCustomerCode() {
    return searchCustomerCode;
  }



  
  /**
   * @param searchCustomerCode the searchCustomerCode to set
   */
  public void setSearchCustomerCode(String searchCustomerCode) {
    this.searchCustomerCode = searchCustomerCode;
  }



  
  /**
   * @return the searchMessageStartDatetimeFrom
   */
  public String getSearchMessageStartDatetimeFrom() {
    return searchMessageStartDatetimeFrom;
  }



  
  /**
   * @param searchMessageStartDatetimeFrom the searchMessageStartDatetimeFrom to set
   */
  public void setSearchMessageStartDatetimeFrom(String searchMessageStartDatetimeFrom) {
    this.searchMessageStartDatetimeFrom = searchMessageStartDatetimeFrom;
  }



  
  /**
   * @return the searchMessageEndDatetimeTo
   */
  public String getSearchMessageEndDatetimeTo() {
    return searchMessageEndDatetimeTo;
  }



  
  /**
   * @param searchMessageEndDatetimeTo the searchMessageEndDatetimeTo to set
   */
  public void setSearchMessageEndDatetimeTo(String searchMessageEndDatetimeTo) {
    this.searchMessageEndDatetimeTo = searchMessageEndDatetimeTo;
  }



  // 活动期间检查
  public boolean isValid() {

    boolean result = true;

    String startDateFrom = getSearchMessageStartDatetimeFrom();

    String endDateTo = getSearchMessageEndDatetimeTo();

    if (StringUtil.hasValueAllOf(startDateFrom, endDateTo)) {
      result &= StringUtil.isCorrectRange(startDateFrom, endDateTo);
    }
    return result;

  }

}
