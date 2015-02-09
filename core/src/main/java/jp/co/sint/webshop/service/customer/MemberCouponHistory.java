package jp.co.sint.webshop.service.customer;

import java.io.Serializable;

public class MemberCouponHistory implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 代金券发行订单编号 */
  private String couponOrderNo;

  /** 代金券规则编号 */
  private String couponRuleNo;
  
  // soukai add 2012/02/01 ob start
  /** 优惠券编号 */
  private String couponIssueNo;
  
  /** 优惠券名称 */
  private String couponRuleName;
  // soukai add 2012/02/01 ob end

  /** 代金券明细编号 */
  private String couponIssueDetailNo;

  /** 代金券使用开始日期 */
  private String couponUseStartDate;

  /** 代金券使用结束日期 */
  private String couponUseEndDate;

  /** 代金券发行日期 */
  private String couponIssueDate;

  /** 代金券金额 */
  private String couponPrice;

  /** 代金券使用最低购买金额 */
  private String couponInvestPurchasePrice;

  /** 代金券使用区分 */
  private String couponUse;

  /** 代金券使用订单编号 */
  private String couponUseOrderNo;

  // 20111107 yuyongqiang add start
  /** 代金券使用订单编号 */
  private String couponIssueReason;
  // 20111107 yuyongqiang add end

  
  /**
   * @return the couponOrderNo
   */
  public String getCouponOrderNo() {
    return couponOrderNo;
  }

  
  /**
   * @return the couponIssueReason
   */
  public String getCouponIssueReason() {
    return couponIssueReason;
  }

  
  /**
   * @param couponIssueReason the couponIssueReason to set
   */
  public void setCouponIssueReason(String couponIssueReason) {
    this.couponIssueReason = couponIssueReason;
  }

  /**
   * @param couponOrderNo
   *          the couponOrderNo to set
   */
  public void setCouponOrderNo(String couponOrderNo) {
    this.couponOrderNo = couponOrderNo;
  }

  /**
   * @return the couponRuleNo
   */
  public String getCouponRuleNo() {
    return couponRuleNo;
  }

  /**
   * @param couponRuleNo
   *          the couponRuleNo to set
   */
  public void setCouponRuleNo(String couponRuleNo) {
    this.couponRuleNo = couponRuleNo;
  }

  /**
   * @return the couponIssueDetailNo
   */
  public String getCouponIssueDetailNo() {
    return couponIssueDetailNo;
  }

  /**
   * @param couponIssueDetailNo
   *          the couponIssueDetailNo to set
   */
  public void setCouponIssueDetailNo(String couponIssueDetailNo) {
    this.couponIssueDetailNo = couponIssueDetailNo;
  }

  /**
   * @return the couponUseStartDate
   */
  public String getCouponUseStartDate() {
    return couponUseStartDate;
  }

  /**
   * @param couponUseStartDate
   *          the couponUseStartDate to set
   */
  public void setCouponUseStartDate(String couponUseStartDate) {
    this.couponUseStartDate = couponUseStartDate;
  }

  /**
   * @return the couponUseEndDate
   */
  public String getCouponUseEndDate() {
    return couponUseEndDate;
  }

  /**
   * @param couponUseEndDate
   *          the couponUseEndDate to set
   */
  public void setCouponUseEndDate(String couponUseEndDate) {
    this.couponUseEndDate = couponUseEndDate;
  }

  /**
   * @return the couponIssueDate
   */
  public String getCouponIssueDate() {
    return couponIssueDate;
  }

  /**
   * @param couponIssueDate
   *          the couponIssueDate to set
   */
  public void setCouponIssueDate(String couponIssueDate) {
    this.couponIssueDate = couponIssueDate;
  }

  /**
   * @return the couponPrice
   */
  public String getCouponPrice() {
    return couponPrice;
  }

  /**
   * @param couponPrice
   *          the couponPrice to set
   */
  public void setCouponPrice(String couponPrice) {
    this.couponPrice = couponPrice;
  }

  /**
   * @return the couponInvestPurchasePrice
   */
  public String getCouponInvestPurchasePrice() {
    return couponInvestPurchasePrice;
  }

  /**
   * @param couponInvestPurchasePrice
   *          the couponInvestPurchasePrice to set
   */
  public void setCouponInvestPurchasePrice(String couponInvestPurchasePrice) {
    this.couponInvestPurchasePrice = couponInvestPurchasePrice;
  }

  /**
   * @return the couponUse
   */
  public String getCouponUse() {
    return couponUse;
  }

  /**
   * @param couponUse
   *          the couponUse to set
   */
  public void setCouponUse(String couponUse) {
    this.couponUse = couponUse;
  }

  /**
   * @return the couponUseOrderNo
   */
  public String getCouponUseOrderNo() {
    return couponUseOrderNo;
  }

  /**
   * @param couponUseOrderNo
   *          the couponUseOrderNo to set
   */
  public void setCouponUseOrderNo(String couponUseOrderNo) {
    this.couponUseOrderNo = couponUseOrderNo;
  }


/**
 * @return the couponRuleName
 */
public String getCouponRuleName() {
	return couponRuleName;
}


/**
 * @param couponRuleName the couponRuleName to set
 */
public void setCouponRuleName(String couponRuleName) {
	this.couponRuleName = couponRuleName;
}


/**
 * @return the couponIssueNo
 */
public String getCouponIssueNo() {
	return couponIssueNo;
}


/**
 * @param couponIssueNo the couponIssueNo to set
 */
public void setCouponIssueNo(String couponIssueNo) {
	this.couponIssueNo = couponIssueNo;
}

}
