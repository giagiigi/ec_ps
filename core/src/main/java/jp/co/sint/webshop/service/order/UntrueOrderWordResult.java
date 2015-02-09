package jp.co.sint.webshop.service.order;

import java.io.Serializable;

public class UntrueOrderWordResult implements Serializable {

  /**
   * 虚假订单关键词辅助类
   */
  private static final long serialVersionUID = 1L;
    private String orderWordCode;  //关键词编号
    private String orderWordName;  //关键词内容
    
    /**
     * @return the orderWordCode
     */
    public String getOrderWordCode() {
      return orderWordCode;
    }
    
    /**
     * @param orderWordCode the orderWordCode to set
     */
    public void setOrderWordCode(String orderWordCode) {
      this.orderWordCode = orderWordCode;
    }
    
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
