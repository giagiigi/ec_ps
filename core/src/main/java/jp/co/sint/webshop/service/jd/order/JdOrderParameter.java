package jp.co.sint.webshop.service.jd.order;

import java.io.Serializable;

/**
 * 调用京东API的参数
 * 
 * @author kousen
 */
public class JdOrderParameter implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // 开始时间
  private String startDate;

  // 结束时间
  private String endDate;

  // 订单状态
  private String orderStatus;

  /**
   * @return the startDate
   */
  public String getStartDate() {
    return startDate;
  }

  /**
   * @param startDate
   *          the startDate to set
   */
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * @param endDate
   *          the endDate to set
   */
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  /**
   * @return the orderStatus
   */
  public String getOrderStatus() {
    return orderStatus;
  }

  /**
   * @param orderStatus
   *          the orderStatus to set
   */
  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

}
