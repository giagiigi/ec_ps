package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.service.SearchCondition;

public class DeliveryHistorySearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String searchCustomerCode;

  private String searchAddressNo;

  /**
   * searchAddressNoを取得します。
   * 
   * @return searchAddressNo
   */
  public String getSearchAddressNo() {
    return searchAddressNo;
  }

  /**
   * searchAddressNoを設定します。
   * 
   * @param searchAddressNo
   *          設定する searchAddressNo
   */
  public void setSearchAddressNo(String searchAddressNo) {
    this.searchAddressNo = searchAddressNo;
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
}
