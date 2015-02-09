package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.util.Date;

public class MyOrder implements Serializable {

  private static final long serialVersionUID = 1L;

  private String orderNo;

  private Date orderDate;

  private String shopCode;

  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * @param orderNo
   *          the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * @return the orderDate
   */
  public Date getOrderDate() {
    return orderDate;
  }

  /**
   * @param orderDate
   *          the orderDate to set
   */
  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

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

}
