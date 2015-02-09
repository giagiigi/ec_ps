package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.service.SearchCondition;

public class OrderHistorySearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String searchCustomerCode;

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
}
