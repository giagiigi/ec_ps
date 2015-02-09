package jp.co.sint.webshop.service.chinapay;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentChinapayParameter implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** 订单编号 */
  private String orderId;

  /** 支付金额 */
  private BigDecimal amount;

  /** 订单日期属性 */
  private String transDate;

  /** 订单内容属性 */
  private String orderContent;
  
  /**
   * 取得 orderId
   * @return the orderId
   */
  public String getOrderId() {
    return orderId;
  }

  
  /**
   * 设定 orderId
   * @param orderId the orderId to set
   */
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  
  /**
   * 取得 amount
   * @return the amount
   */
  public BigDecimal getAmount() {
    return amount;
  }

  
  /**
   * 设定 amount
   * @param amount the amount to set
   */
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  /**
   * @return the transDate
   */
  public String getTransDate() {
    return transDate;
  }

  /**
   * @param transDate the transDate to set
   */
  public void setTransDate(String transDate) {
    this.transDate = transDate;
  }


/**
 * @return the orderContent
 */
public String getOrderContent() {
	return orderContent;
}


/**
 * @param orderContent the orderContent to set
 */
public void setOrderContent(String orderContent) {
	this.orderContent = orderContent;
}

}
