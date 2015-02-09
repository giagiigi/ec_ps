package jp.co.sint.webshop.service.event;

import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.order.OrderMailType;
import jp.co.sint.webshop.service.order.OrderSmsType;

/**
 * コンポーネント内でOrderServiceのイベントが発生したことを示すイベントです。
 * 
 * @author System Integrator Corp.
 */
public class OrderEvent implements ServiceEvent {

  private static final long serialVersionUID = 1L;

  private String orderNo;
  
  private CashierPayment cashierPayment;
  
  private OrderMailType orderMailType;
  
  //Add by V10-CH start
  private OrderSmsType orderSmsType;

  
  /**
   * orderSmsTypeを取得します。
   *
   * @return orderSmsType orderSmsType
   */
  public OrderSmsType getOrderSmsType() {
    return orderSmsType;
  }

  
  /**
   * orderSmsTypeを設定します。
   *
   * @param orderSmsType 
   *          orderSmsType
   */
  public void setOrderSmsType(OrderSmsType orderSmsType) {
    this.orderSmsType = orderSmsType;
  }
  //Add by V10-CH end
  
  public OrderEvent() {
    
  }

  public OrderEvent(String orderNo) {
    setOrderNo(orderNo);
  }
  
  public OrderEvent(String orderNo, CashierPayment cashierPayment) {
    setOrderNo(orderNo);
    setCashierPayment(cashierPayment);
  }

  public OrderEvent(String orderNo, CashierPayment cashierPayment, OrderMailType orderMailType) {
    setOrderNo(orderNo);
    setCashierPayment(cashierPayment);
    setOrderMailType(orderMailType);
  }
  
  //Add by V10-CH start
  public OrderEvent(String orderNo, CashierPayment cashierPayment, OrderSmsType orderSmsType){
    setOrderNo(orderNo);
    setCashierPayment(cashierPayment);
    setOrderSmsType(orderSmsType);
  }
  //Add by V10-CH end
  
  /**
   * orderNoを返します。
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  
  /**
   * orderNoを設定します。
   * @param orderNo 設定する orderNo
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  
  /**
   * cashierPaymentを取得します。
   *
   * @return cashierPayment
   */
  
  public CashierPayment getCashierPayment() {
    return cashierPayment;
  }

  
  /**
   * cashierPaymentを設定します。
   *
   * @param cashierPayment 
   *          cashierPayment
   */
  public void setCashierPayment(CashierPayment cashierPayment) {
    this.cashierPayment = cashierPayment;
  }

  
  /**
   * orderMailTypeを取得します。
   *
   * @return orderMailType
   */
  
  public OrderMailType getOrderMailType() {
    return orderMailType;
  }

  
  /**
   * orderMailTypeを設定します。
   *
   * @param orderMailType 
   *          orderMailType
   */
  public void setOrderMailType(OrderMailType orderMailType) {
    this.orderMailType = orderMailType;
  }

  
}
