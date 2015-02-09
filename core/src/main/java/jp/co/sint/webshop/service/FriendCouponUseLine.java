package jp.co.sint.webshop.service;

import java.io.Serializable;

public class FriendCouponUseLine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//优惠金额
  private String couponAmount;
  
  //优惠券利用最小购买金额
  private String minUseOrderAmount;
  
  private String maxUseOrderAmount;
  
  //有效期限(开始)
  private String minUseStartDatetime;

  //有效期限(结束)
  private String minUseEndDatetime;
  
  //优惠劵规则代码
  private String couponCode;
  
  //优惠劵规则名称
  private String couponName;
  
  private String couponNameEn;
  
  private String couponNameJp;
  
  //优惠劵兑换金额
  private Long exchangePointAmount;
  
  //优惠券发行类别（0:比例；1:固定金额）
  private String couponIssueType;
  
  //优惠券利用结束日期（金额、特别会员使用）
  private String minUseEndNum;
  
  //优惠券比例
  private String couponProportion;

  
  public String getMinUseEndDatetime() {
    return minUseEndDatetime;
  }
  
  public void setMinUseEndDatetime(String minUseEndDatetime) {
    this.minUseEndDatetime = minUseEndDatetime;
  }

  public String getCouponAmount() {
    return couponAmount;
  }
  
  public void setCouponAmount(String couponAmount) {
    this.couponAmount = couponAmount;
  }

  
  public String getMinUseOrderAmount() {
    return minUseOrderAmount;
  }

  
  public void setMinUseOrderAmount(String minUseOrderAmount) {
    this.minUseOrderAmount = minUseOrderAmount;
  }

  
  public String getMinUseStartDatetime() {
    return minUseStartDatetime;
  }

  
  public void setMinUseStartDatetime(String minUseStartDatetime) {
    this.minUseStartDatetime = minUseStartDatetime;
  }


  
  public String getCouponCode() {
    return couponCode;
  }


  
  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }


  
  public String getCouponName() {
    return couponName;
  }


  
  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }


  
  public Long getExchangePointAmount() {
    return exchangePointAmount;
  }


  
  public void setExchangePointAmount(Long exchangePointAmount) {
    this.exchangePointAmount = exchangePointAmount;
  }

  
  public String getCouponNameEn() {
    return couponNameEn;
  }

  
  public void setCouponNameEn(String couponNameEn) {
    this.couponNameEn = couponNameEn;
  }

  
  public String getCouponNameJp() {
    return couponNameJp;
  }

  
  public void setCouponNameJp(String couponNameJp) {
    this.couponNameJp = couponNameJp;
  }

  
  public String getMinUseEndNum() {
    return minUseEndNum;
  }

  
  public void setMinUseEndNum(String minUseEndNum) {
    this.minUseEndNum = minUseEndNum;
  }

  
  public String getCouponIssueType() {
    return couponIssueType;
  }

  
  public void setCouponIssueType(String couponIssueType) {
    this.couponIssueType = couponIssueType;
  }

  
  public String getCouponProportion() {
    return couponProportion;
  }

  
  public void setCouponProportion(String couponProportion) {
    this.couponProportion = couponProportion;
  }

  
  /**
   * @return the maxUseOrderAmount
   */
  public String getMaxUseOrderAmount() {
    return maxUseOrderAmount;
  }

  
  /**
   * @param maxUseOrderAmount the maxUseOrderAmount to set
   */
  public void setMaxUseOrderAmount(String maxUseOrderAmount) {
    this.maxUseOrderAmount = maxUseOrderAmount;
  }
  
  
}
