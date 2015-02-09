package jp.co.sint.webshop.service.customer;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerAttributeAnswer;

public class CustomerInfo implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private Customer customer;

  private CustomerAddress address;

  private List<CustomerAttributeAnswer> answerList;

  /**
   * @return answerList
   */
  public List<CustomerAttributeAnswer> getAnswerList() {
    return answerList;
  }

  /**
   * @param answerList
   *          設定する answerList
   */
  public void setAnswerList(List<CustomerAttributeAnswer> answerList) {
    this.answerList = answerList;
  }

  /**
   * @return address
   */
  public CustomerAddress getAddress() {
    return address;
  }

  /**
   * @return customer
   */
  public Customer getCustomer() {
    return customer;
  }

  /**
   * @param address
   *          設定する address
   */
  public void setAddress(CustomerAddress address) {
    this.address = address;
  }

  /**
   * @param customer
   *          設定する customer
   */
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

}
