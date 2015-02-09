package jp.co.sint.webshop.service.order;

import java.io.Serializable;

import jp.co.sint.webshop.service.cart.CashierPayment;

/**
 * @author System Integrator Corp.
 */
public class BookSalesContainer implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private OrderContainer orderContainer;

  private CashierPayment cashierPayment;

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
   * orderContainerを取得します。
   * 
   * @return orderContainer
   */
  public OrderContainer getOrderContainer() {
    return orderContainer;
  }

  /**
   * orderContainerを設定します。
   * 
   * @param orderContainer
   *          orderContainer
   */
  public void setOrderContainer(OrderContainer orderContainer) {
    this.orderContainer = orderContainer;
  }

}
