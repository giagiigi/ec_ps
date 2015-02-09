package jp.co.sint.webshop.service.shop;

import jp.co.sint.webshop.service.SearchCondition;

public class DeliveryRegionChargeCondition extends SearchCondition {
	/** Serial Version UID */
	private static final long serialVersionUID = 1L;
	
	private String deliveryCompanyNo;

  
  /**
   * @return the deliveryCompanyNo
   */
  public String getDeliveryCompanyNo() {
    return deliveryCompanyNo;
  }

  
  /**
   * @param deliveryCompanyNo the deliveryCompanyNo to set
   */
  public void setDeliveryCompanyNo(String deliveryCompanyNo) {
    this.deliveryCompanyNo = deliveryCompanyNo;
  }
	
}
