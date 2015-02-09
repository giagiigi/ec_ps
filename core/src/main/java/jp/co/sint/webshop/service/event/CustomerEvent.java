package jp.co.sint.webshop.service.event;

import jp.co.sint.webshop.data.dto.Customer;

/**
 * コンポーネント内でCustomerServiceのイベントが発生したことを示すイベントです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerEvent implements ServiceEvent {

  private static final long serialVersionUID = 1L;

  private Customer customer;

  public CustomerEvent() {
  }

  public CustomerEvent(Customer customer) {
    setCustomer(customer);
  }

  /**
   * customerを返します。
   * 
   * @return the customer
   */
  public Customer getCustomer() {
    return customer;
  }

  /**
   * customerを設定します。
   * 
   * @param customer
   *          設定する customer
   */
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

}
