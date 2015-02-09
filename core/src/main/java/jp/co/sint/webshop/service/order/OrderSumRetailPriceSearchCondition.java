package jp.co.sint.webshop.service.order;

import jp.co.sint.webshop.service.SearchCondition;

public class OrderSumRetailPriceSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String orderDatetime;

  private String shopCode;

  private String orderStatus;

  /**
   * orderStatusを取得します。
   * 
   * @return orderStatus
   */
  public String getOrderStatus() {
    return orderStatus;
  }

  /**
   * orderStatusを設定します。
   * 
   * @param orderStatus
   *          orderStatus
   */
  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  /**
   * orderDatetimeを取得します。
   * 
   * @return orderDatetime
   */
  public String getOrderDatetime() {
    return orderDatetime;
  }

  /**
   * orderDatetimeを設定します。
   * 
   * @param orderDatetime
   *          orderDatetime
   */
  public void setOrderDatetime(String orderDatetime) {
    this.orderDatetime = orderDatetime;
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

}
