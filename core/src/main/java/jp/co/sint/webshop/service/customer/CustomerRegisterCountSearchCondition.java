package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.service.SearchCondition;

public class CustomerRegisterCountSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String customerStatus;

  private String registerDate;

  /**
   * customerStatusを取得します。
   * 
   * @return customerStatus
   */
  public String getCustomerStatus() {
    return customerStatus;
  }

  /**
   * customerStatusを設定します。
   * 
   * @param customerStatus
   *          customerStatus
   */
  public void setCustomerStatus(String customerStatus) {
    this.customerStatus = customerStatus;
  }

  /**
   * registerDateを取得します。
   * 
   * @return registerDate
   */
  public String getRegisterDate() {
    return registerDate;
  }

  /**
   * registerDateを設定します。
   * 
   * @param registerDate
   *          registerDate
   */
  public void setRegisterDate(String registerDate) {
    this.registerDate = registerDate;
  }

}
