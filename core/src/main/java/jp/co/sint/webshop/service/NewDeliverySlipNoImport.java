package jp.co.sint.webshop.service;

import jp.co.sint.webshop.data.domain.OrderType;

/**
 * author : huangdahui date : 2014-10-22
 */
public class NewDeliverySlipNoImport {

  private String orderNo;

  private String deliverySlipNo;

  private OrderType orderType;

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
   * @return the deliverySlipNo
   */
  public String getDeliverySlipNo() {
    return deliverySlipNo;
  }

  /**
   * @param deliverySlipNo
   *          the deliverySlipNo to set
   */
  public void setDeliverySlipNo(String deliverySlipNo) {
    this.deliverySlipNo = deliverySlipNo;
  }

  /**
   * @return the orderType
   */
  public OrderType getOrderType() {
    return orderType;
  }

  /**
   * @param orderType
   *          the orderType to set
   */
  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

}
