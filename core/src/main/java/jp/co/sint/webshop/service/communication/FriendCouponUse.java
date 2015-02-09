package jp.co.sint.webshop.service.communication;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FriendCouponUse implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // 顾客名
  private String lastName;

  // 优惠券金额
  private BigDecimal couponAmount;

  // 优惠券编号
  private String couponCode;

  // 优惠券使用最小购买金额
  private BigDecimal minUseOrderAmount;

  // 优惠券使用开始日时
  private Date minUseStartDatetime;

  // 优惠券使用结束日时
  private Date minUseEndDatetime;
  
  // 优惠券名称
  private String couponName;
  
  // 优惠券名称(英文)
  private String couponNameEn;
  
  // 优惠券名称(日语)
  private String couponNameJp;
  
  /**利用限定（0:无限制 1:初次购买）*/
  private String applicableObjects;
  
  // 优惠券比例
  private Long couponProportion;
  
  // 优惠券类型
  private Long couponIssueType;
  
  // 优惠券使用最大购买金额
  private BigDecimal maxUseOrderAmount;

  /**
   * @return the couponCode
   */
  public String getCouponCode() {
    return couponCode;
  }

  /**
   * @param couponCode
   *          the couponCode to set
   */
  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }

  /**
   * @return the couponAmount
   */
  public BigDecimal getCouponAmount() {
    return couponAmount;
  }

  /**
   * @param couponAmount
   *          the couponAmount to set
   */
  public void setCouponAmount(BigDecimal couponAmount) {
    this.couponAmount = couponAmount;
  }

  /**
   * @return the minUseOrderAmount
   */
  public BigDecimal getMinUseOrderAmount() {
    return minUseOrderAmount;
  }

  /**
   * @param minUseOrderAmount
   *          the minUseOrderAmount to set
   */
  public void setMinUseOrderAmount(BigDecimal minUseOrderAmount) {
    this.minUseOrderAmount = minUseOrderAmount;
  }

  /**
   * @return the minUseStartDatetime
   */
  public Date getMinUseStartDatetime() {
    return minUseStartDatetime;
  }

  /**
   * @param minUseStartDatetime
   *          the minUseStartDatetime to set
   */
  public void setMinUseStartDatetime(Date minUseStartDatetime) {
    this.minUseStartDatetime = minUseStartDatetime;
  }

  /**
   * @return the minUseEndDatetime
   */
  public Date getMinUseEndDatetime() {
    return minUseEndDatetime;
  }

  /**
   * @param minUseEndDatetime
   *          the minUseEndDatetime to set
   */
  public void setMinUseEndDatetime(Date minUseEndDatetime) {
    this.minUseEndDatetime = minUseEndDatetime;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName
   *          the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  
  /**
   * @return the couponName
   */
  public String getCouponName() {
    return couponName;
  }

  
  /**
   * @param couponName the couponName to set
   */
  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  
  /**
   * @return the couponNameEn
   */
  public String getCouponNameEn() {
    return couponNameEn;
  }

  
  /**
   * @param couponNameEn the couponNameEn to set
   */
  public void setCouponNameEn(String couponNameEn) {
    this.couponNameEn = couponNameEn;
  }

  
  /**
   * @return the couponNameJp
   */
  public String getCouponNameJp() {
    return couponNameJp;
  }

  
  /**
   * @param couponNameJp the couponNameJp to set
   */
  public void setCouponNameJp(String couponNameJp) {
    this.couponNameJp = couponNameJp;
  }

  /**
   * 取得利用限定（0:无限制 1:初次购买）
   * @return
   */
  public String getApplicableObjects() {
    return applicableObjects;
  }

  /**
   * 设定利用限定（0:无限制 1:初次购买）
   * @param applicableObjects
   */
  public void setApplicableObjects(String applicableObjects) {
    this.applicableObjects = applicableObjects;
  }

  
  /**
   * @return the couponProportion
   */
  public Long getCouponProportion() {
    return couponProportion;
  }

  
  /**
   * @param couponProportion the couponProportion to set
   */
  public void setCouponProportion(Long couponProportion) {
    this.couponProportion = couponProportion;
  }

  
  /**
   * @return the couponIssueType
   */
  public Long getCouponIssueType() {
    return couponIssueType;
  }

  
  /**
   * @param couponIssueType the couponIssueType to set
   */
  public void setCouponIssueType(Long couponIssueType) {
    this.couponIssueType = couponIssueType;
  }

  
  /**
   * @return the maxUseOrderAmount
   */
  public BigDecimal getMaxUseOrderAmount() {
    return maxUseOrderAmount;
  }

  
  /**
   * @param maxUseOrderAmount the maxUseOrderAmount to set
   */
  public void setMaxUseOrderAmount(BigDecimal maxUseOrderAmount) {
    this.maxUseOrderAmount = maxUseOrderAmount;
  }
  
}
