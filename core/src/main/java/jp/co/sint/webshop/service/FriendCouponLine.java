package jp.co.sint.webshop.service;

import java.io.Serializable;

public class FriendCouponLine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//顾客名称
  private String customerName;
  
  //顾客代码
  private String customerCode;
  
  //发行日期
  private String couponIssueDate;
  
  //优惠劵编号
  private String couponCode;
  
  //优惠劵金额
  private String couponAmount;
  
  //所得积分
  private Long allPoint;
  
  //优惠劵条数统计
  private Long couponNum;

  //订单号
  private String orderNo;
  
  public String getCustomerName() {
    return customerName;
  }
  
  
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  
  public String getCouponCode() {
    return couponCode;
  }

  
  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }

  
  public String getCouponAmount() {
    return couponAmount;
  }

  
  public void setCouponAmount(String couponAmount) {
    this.couponAmount = couponAmount;
  }

  
  public Long getAllPoint() {
    return allPoint;
  }

  
  public void setAllPoint(Long allPoint) {
    this.allPoint = allPoint;
  }


  
  public String getCouponIssueDate() {
    return couponIssueDate;
  }


  
  public void setCouponIssueDate(String couponIssueDate) {
    this.couponIssueDate = couponIssueDate;
  }


  
  public Long getCouponNum() {
    return couponNum;
  }


  
  public void setCouponNum(Long couponNum) {
    this.couponNum = couponNum;
  }




  
  public String getCustomerCode() {
    return customerCode;
  }




  
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }


  
  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }


  
  /**
   * @param orderNo the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }
	
}
