package jp.co.sint.webshop.service.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kousen.
 */
public class CashierDelivery implements Serializable {

  private static final long serialVersionUID = 1L;

  // 已选择配送公司编号
  private String deliveryCompanyCode;

  // 已选择配送公司名称
  private String deliveryCompanyName;


  private List<DeliveryCompany> deliveryCompanyList = new ArrayList<DeliveryCompany>();
  

  public static class DeliveryCompany implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String deliveryCompanyCode;
    
    private String deliveryCompanyName;

    
    /**
     * @return the deliveryCompanyName
     */
    public String getDeliveryCompanyName() {
      return deliveryCompanyName;
    }

    
    /**
     * @param deliveryCompanyName the deliveryCompanyName to set
     */
    public void setDeliveryCompanyName(String deliveryCompanyName) {
      this.deliveryCompanyName = deliveryCompanyName;
    }


    
    /**
     * @return the deliveryCompanyCode
     */
    public String getDeliveryCompanyCode() {
      return deliveryCompanyCode;
    }


    
    /**
     * @param deliveryCompanyCode the deliveryCompanyCode to set
     */
    public void setDeliveryCompanyCode(String deliveryCompanyCode) {
      this.deliveryCompanyCode = deliveryCompanyCode;
    }
  }


  
  /**
   * @return the deliveryCompanyCode
   */
  public String getDeliveryCompanyCode() {
    return deliveryCompanyCode;
  }


  
  /**
   * @param deliveryCompanyCode the deliveryCompanyCode to set
   */
  public void setDeliveryCompanyCode(String deliveryCompanyCode) {
    this.deliveryCompanyCode = deliveryCompanyCode;
  }


  
  /**
   * @return the deliveryCompanyName
   */
  public String getDeliveryCompanyName() {
    return deliveryCompanyName;
  }


  
  /**
   * @param deliveryCompanyName the deliveryCompanyName to set
   */
  public void setDeliveryCompanyName(String deliveryCompanyName) {
    this.deliveryCompanyName = deliveryCompanyName;
  }


  
  /**
   * @return the deliveryCompanyList
   */
  public List<DeliveryCompany> getDeliveryCompanyList() {
    return deliveryCompanyList;
  }


  
  /**
   * @param deliveryCompanyList the deliveryCompanyList to set
   */
  public void setDeliveryCompanyList(List<DeliveryCompany> deliveryCompanyList) {
    this.deliveryCompanyList = deliveryCompanyList;
  }
}
