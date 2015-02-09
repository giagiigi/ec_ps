package jp.co.sint.webshop.service.communication;
 
import jp.co.sint.webshop.service.SearchCondition;

public class MyCouponHistorySearchCondition extends SearchCondition {

	/** uid */
	private static final long serialVersionUID = 1L;
	
	//顧客番号
	private String customerCode;

	/***
	 * 顧客番号を取得する
	 * @return
	 */
  public String getCustomerCode() {
    return customerCode;
  }
  
  /**
   * 顧客番号を設定する
   * @param customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }
}
