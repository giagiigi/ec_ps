package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.service.SearchCondition;

public class CouponStatusListSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String searchShopCode;

  private String searchCouponStatus;

  private String searchCustomerCode;

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

  public boolean isValid() {
    boolean result = true;
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

  public String getSearchCouponStatus() {
    return searchCouponStatus;
  }

  public void setSearchCouponStatus(String searchCouponStatus) {
    this.searchCouponStatus = searchCouponStatus;
  }

}
