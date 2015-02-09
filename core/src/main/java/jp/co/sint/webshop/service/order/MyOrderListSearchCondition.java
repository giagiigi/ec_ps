package jp.co.sint.webshop.service.order;

import jp.co.sint.webshop.service.SearchCondition;

public class MyOrderListSearchCondition extends SearchCondition {

  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String customerCode;

  private String[] orderStatus = new String[0];

  // 发货状态
  private String[] shippingStatus = new String[0];

  // 全部订单,最期订单
  private String orderDateType;

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @param customerCode
   *          the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * @return the orderStatus
   */
  public String[] getOrderStatus() {
    return orderStatus;
  }

  /**
   * @param orderStatus
   *          the orderStatus to set
   */
  public void setOrderStatus(String[] orderStatus) {
    this.orderStatus = orderStatus;
  }

  /**
   * @return the shippingStatus
   */
  public String[] getShippingStatus() {
    return shippingStatus;
  }

  /**
   * @param shippingStatus
   *          the shippingStatus to set
   */
  public void setShippingStatus(String[] shippingStatus) {
    this.shippingStatus = shippingStatus;
  }

  /**
   * @return the orderDateType
   */
  public String getOrderDateType() {
    return orderDateType;
  }

  /**
   * @param orderDateType
   *          the orderDateType to set
   */
  public void setOrderDateType(String orderDateType) {
    this.orderDateType = orderDateType;
  }

}
