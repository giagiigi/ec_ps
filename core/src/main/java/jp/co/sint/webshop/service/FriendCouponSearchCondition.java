package jp.co.sint.webshop.service;


import jp.co.sint.webshop.service.SearchCondition;


public class FriendCouponSearchCondition extends SearchCondition {
	
	 /** Serial Version UID */
  private static final long serialVersionUID = 1L;

   /**
    * 搜索用数据
    */
	private String customerCode;

  
  public String getCustomerCode() {
    return customerCode;
  }

  
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

}
