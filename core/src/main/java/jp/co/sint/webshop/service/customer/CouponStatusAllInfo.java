package jp.co.sint.webshop.service.customer;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.utility.DateUtil;

public class CouponStatusAllInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  private String customerCouponId;

  private String customerCode;

  private String couponIssueNo;

  private String useFlg;

  private String couponPrice;

  private Date useDate;

  private Date issueDate;

  private Date useCouponStartDate;

  private Date useCouponEndDate;

  private String couponName;

  private String orderNo;

  private String getCouponOrderNo;

  private String description;

  private String customerName;

  public String getCouponIssueNo() {
    return couponIssueNo;
  }

  public void setCouponIssueNo(String couponIssueNo) {
    this.couponIssueNo = couponIssueNo;
  }

  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public String getCouponPrice() {
    return couponPrice;
  }

  public void setCouponPrice(String couponPrice) {
    this.couponPrice = couponPrice;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getCustomerCouponId() {
    return customerCouponId;
  }

  public void setCustomerCouponId(String customerCouponId) {
    this.customerCouponId = customerCouponId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getGetCouponOrderNo() {
    return getCouponOrderNo;
  }

  public void setGetCouponOrderNo(String getCouponOrderNo) {
    this.getCouponOrderNo = getCouponOrderNo;
  }

  public Date getIssueDate() {
    return DateUtil.immutableCopy(issueDate);
  }

  public void setIssueDate(Date issueDate) {
    this.issueDate = DateUtil.immutableCopy(issueDate);
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public Date getUseCouponEndDate() {
    return DateUtil.immutableCopy(useCouponEndDate);
  }

  public void setUseCouponEndDate(Date useCouponEndDate) {
    this.useCouponEndDate = DateUtil.immutableCopy(useCouponEndDate);
  }

  public Date getUseCouponStartDate() {
    return DateUtil.immutableCopy(useCouponStartDate);
  }

  public void setUseCouponStartDate(Date useCouponStartDate) {
    this.useCouponStartDate = useCouponStartDate;
  }

  public Date getUseDate() {
    return DateUtil.immutableCopy(useDate);
  }

  public void setUseDate(Date useDate) {
    this.useDate = DateUtil.immutableCopy(useDate);
  }

  public String getUseFlg() {
    return useFlg;
  }

  public void setUseFlg(String useFlg) {
    this.useFlg = useFlg;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

}
