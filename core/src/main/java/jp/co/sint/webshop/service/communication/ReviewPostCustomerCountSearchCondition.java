package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.service.SearchCondition;

public class ReviewPostCustomerCountSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String customerCode;
  
  private String commodityCode;
  
  //20111220 os013 add start
  //受注履歴ID
  private String orderNo;
  //20111220 os013 add end
  
  /**
   * commodityCodeを取得します。
   * 
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * customerCodeを取得します。
   * 
   * @return customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * customerCodeを設定します。
   * 
   * @param customerCode
   *          customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }
  
  //20111220 os013 add start
  /**
   *  受注履歴IDを取得します。
   * 
   * @return  受注履歴ID
   */
  public String getOrderNo() {
    return orderNo;
  }
  
  /**
   * 受注履歴IDを設定します。
   * 
   * @param 受注履歴ID
   *          受注履歴ID
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }
  //20111220 os013 add end
}
