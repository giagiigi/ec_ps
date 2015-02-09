package jp.co.sint.webshop.service.order;

import jp.co.sint.webshop.service.SearchCondition;
public class UntrueOrderWordSearchCondition extends SearchCondition {

  /**
   * 虚假订单关键词管理 检索条件
   * */
  private static final long serialVersionUID = 1L;

  private String orderWordName;   //关键词内容

  /**
   * @return the orderWordName
   */
  public String getOrderWordName() {
    return orderWordName;
  }
  /**
   * @param orderWordName the orderWordName to set
   */
  public void setOrderWordName(String orderWordName) {
    this.orderWordName = orderWordName;
  }
  
}
