package jp.co.sint.webshop.service.customer;

import java.io.Serializable;
import java.util.Date;

public class CustomerCouponInfo implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private Long customerCouponId;

  private String customerCode;

  private Long couponIssueNo;

  private String useFlg;

  private String couponName;

  private String useCouponStartDate;

  private Date useCouponEndDate;

  private String couponPrice;

  private String useDate;

  private String issueDate;

  private String orderNo;

  public Long getCouponIssueNo() {
    return couponIssueNo;
  }

  public void setCouponIssueNo(Long couponIssueNo) {
    this.couponIssueNo = couponIssueNo;
  }

  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public Date getUseCouponEndDate() {
    return useCouponEndDate;
  }

  public void setUseCouponEndDate(Date useCouponEndDate) {
    this.useCouponEndDate = useCouponEndDate;
  }

  public String getUseCouponStartDate() {
    return useCouponStartDate;
  }

  public void setUseCouponStartDate(String useCouponStartDate) {
    this.useCouponStartDate = useCouponStartDate;
  }

  public String getCouponPrice() {
    return couponPrice;
  }

  public void setCouponPrice(String couponPrice) {
    this.couponPrice = couponPrice;
  }

  public String getUseFlg() {
    return useFlg;
  }

  public void setUseFlg(String useFlg) {
    this.useFlg = useFlg;
  }

  public Long getCustomerCouponId() {
    return customerCouponId;
  }

  public void setCustomerCouponId(Long customerCouponId) {
    this.customerCouponId = customerCouponId;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getUseDate() {
    return useDate;
  }

  public void setUseDate(String useDate) {
    this.useDate = useDate;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(String issueDate) {
    this.issueDate = issueDate;
  }

}
