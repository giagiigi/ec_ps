package jp.co.sint.webshop.service.communication;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;

public class FriendCouponList implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** 发行规则编号 */
  private String friendCouponRuleNo;

  /** 发行规则编号 */
  private String friendCouponRuleCn;

  /** 发行规则编号 */
  private String friendCouponRuleEn;

  /** 发行规则编号 */
  private String friendCouponRuleJp;

  /** 发行可能日期区分 */
  private Long issueDateType;

  /** 月份值 */
  private Long issueDateNum;

  /** 发行可能开始日期 */
  private Date issueStartDate;

  /** 发行可能结束日期 */
  private Date issueEndDate;

  /** 订购记录 */
  private Long orderHistory;

  /** 利用优惠券金额 */
  private BigDecimal couponAmount;

  /** 利用个人回数 */
  private BigDecimal personalUseLimit;

  /** 利用SITE回数 */
  private BigDecimal siteUseLimit;

  /** 利用优惠券最小购买金额 */
  private BigDecimal minUseOrderAmount;

  /** 利用有效期类别 */
  private Long useValidType;

  /** 利用有效期值 */
  private Long useValidNum;

  /** 利用限定 */
  private Long applicableObjects;

  /** 换取积分 */
  private BigDecimal obtainPoint;

  /** 公共优惠券固定字符 */
  private String fixChar;

  /** 优惠券规则编号（公共优惠券） */
  private String couponCode;

  /** 顾客编号 */
  private String customerCode;

  /** 发行日期 */
  private Date couponIssueDate;

  /** 优惠券规则名称 */
  private String couponName;

  /** 优惠券类别（0:购买发行；1:特别会员发行；2:公共发行） */
  private Long couponType;

  /** 优惠券发行类别（0:比例；1:固定金额） */
  private Long couponIssueType;

  /** 优惠券发行最小购买金额 */
  private BigDecimal minIssueOrderAmount;

  /** 优惠券发行开始日时 */
  private Date minIssueStartDatetime;

  /** 优惠券发行结束日时 */
  private Date minIssueEndDatetime;

  /** 优惠券比例 */
  private Long couponProportion;

  /** 优惠券利用开始日时 */
  private Date minUseStartDatetime;

  /** 优惠券利用结束日时 */
  private Date minUseEndDatetime;

  /** 发行理由 */
  private String issueReason;

  /** 备注 */
  private String memo;

  /** 优惠券规则英文名称 */
  private String couponNameEn;

  /** 优惠券规则日文名称 */
  private String couponNameJp;

  /** 适用地域 */
  private String applicableArea;

  /** 对象商品集合 */
  private String objectCommodities;

  /** 优惠券利用最大购买金额 */
  private BigDecimal maxUseOrderAmount;

  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  private String createdUser;

  private Date createdDatetime;

  private String updatedUser;

  private Date updatedDatetime;
  
  /** 利用优惠券最小购买金额 */
  private BigDecimal minUseOrderAmountIssue;
  
  /** 优惠券金额 */
  private BigDecimal couponAmountIssue;

  /**
   * @return the friendCouponRuleNo
   */
  public String getFriendCouponRuleNo() {
    return friendCouponRuleNo;
  }

  /**
   * @param friendCouponRuleNo the friendCouponRuleNo to set
   */
  public void setFriendCouponRuleNo(String friendCouponRuleNo) {
    this.friendCouponRuleNo = friendCouponRuleNo;
  }

  /**
   * @return the friendCouponRuleCn
   */
  public String getFriendCouponRuleCn() {
    return friendCouponRuleCn;
  }

  /**
   * @param friendCouponRuleCn the friendCouponRuleCn to set
   */
  public void setFriendCouponRuleCn(String friendCouponRuleCn) {
    this.friendCouponRuleCn = friendCouponRuleCn;
  }

  /**
   * @return the friendCouponRuleEn
   */
  public String getFriendCouponRuleEn() {
    return friendCouponRuleEn;
  }

  /**
   * @param friendCouponRuleEn the friendCouponRuleEn to set
   */
  public void setFriendCouponRuleEn(String friendCouponRuleEn) {
    this.friendCouponRuleEn = friendCouponRuleEn;
  }

  /**
   * @return the friendCouponRuleJp
   */
  public String getFriendCouponRuleJp() {
    return friendCouponRuleJp;
  }

  /**
   * @param friendCouponRuleJp the friendCouponRuleJp to set
   */
  public void setFriendCouponRuleJp(String friendCouponRuleJp) {
    this.friendCouponRuleJp = friendCouponRuleJp;
  }

  /**
   * @return the issueDateType
   */
  public Long getIssueDateType() {
    return issueDateType;
  }

  /**
   * @param issueDateType the issueDateType to set
   */
  public void setIssueDateType(Long issueDateType) {
    this.issueDateType = issueDateType;
  }

  /**
   * @return the issueDateNum
   */
  public Long getIssueDateNum() {
    return issueDateNum;
  }

  /**
   * @param issueDateNum the issueDateNum to set
   */
  public void setIssueDateNum(Long issueDateNum) {
    this.issueDateNum = issueDateNum;
  }

  /**
   * @return the issueStartDate
   */
  public Date getIssueStartDate() {
    return issueStartDate;
  }

  /**
   * @param issueStartDate the issueStartDate to set
   */
  public void setIssueStartDate(Date issueStartDate) {
    this.issueStartDate = issueStartDate;
  }

  /**
   * @return the issueEndDate
   */
  public Date getIssueEndDate() {
    return issueEndDate;
  }

  /**
   * @param issueEndDate the issueEndDate to set
   */
  public void setIssueEndDate(Date issueEndDate) {
    this.issueEndDate = issueEndDate;
  }

  /**
   * @return the orderHistory
   */
  public Long getOrderHistory() {
    return orderHistory;
  }

  /**
   * @param orderHistory the orderHistory to set
   */
  public void setOrderHistory(Long orderHistory) {
    this.orderHistory = orderHistory;
  }

  /**
   * @return the couponAmount
   */
  public BigDecimal getCouponAmount() {
    return couponAmount;
  }

  /**
   * @param couponAmount the couponAmount to set
   */
  public void setCouponAmount(BigDecimal couponAmount) {
    this.couponAmount = couponAmount;
  }

  /**
   * @return the personalUseLimit
   */
  public BigDecimal getPersonalUseLimit() {
    return personalUseLimit;
  }

  /**
   * @param personalUseLimit the personalUseLimit to set
   */
  public void setPersonalUseLimit(BigDecimal personalUseLimit) {
    this.personalUseLimit = personalUseLimit;
  }

  /**
   * @return the siteUseLimit
   */
  public BigDecimal getSiteUseLimit() {
    return siteUseLimit;
  }

  /**
   * @param siteUseLimit the siteUseLimit to set
   */
  public void setSiteUseLimit(BigDecimal siteUseLimit) {
    this.siteUseLimit = siteUseLimit;
  }

  /**
   * @return the minUseOrderAmount
   */
  public BigDecimal getMinUseOrderAmount() {
    return minUseOrderAmount;
  }

  /**
   * @param minUseOrderAmount the minUseOrderAmount to set
   */
  public void setMinUseOrderAmount(BigDecimal minUseOrderAmount) {
    this.minUseOrderAmount = minUseOrderAmount;
  }

  /**
   * @return the useValidType
   */
  public Long getUseValidType() {
    return useValidType;
  }

  /**
   * @param useValidType the useValidType to set
   */
  public void setUseValidType(Long useValidType) {
    this.useValidType = useValidType;
  }

  /**
   * @return the useValidNum
   */
  public Long getUseValidNum() {
    return useValidNum;
  }

  /**
   * @param useValidNum the useValidNum to set
   */
  public void setUseValidNum(Long useValidNum) {
    this.useValidNum = useValidNum;
  }

  /**
   * @return the applicableObjects
   */
  public Long getApplicableObjects() {
    return applicableObjects;
  }

  /**
   * @param applicableObjects the applicableObjects to set
   */
  public void setApplicableObjects(Long applicableObjects) {
    this.applicableObjects = applicableObjects;
  }

  /**
   * @return the obtainPoint
   */
  public BigDecimal getObtainPoint() {
    return obtainPoint;
  }

  /**
   * @param obtainPoint the obtainPoint to set
   */
  public void setObtainPoint(BigDecimal obtainPoint) {
    this.obtainPoint = obtainPoint;
  }

  /**
   * @return the fixChar
   */
  public String getFixChar() {
    return fixChar;
  }

  /**
   * @param fixChar the fixChar to set
   */
  public void setFixChar(String fixChar) {
    this.fixChar = fixChar;
  }

  /**
   * @return the couponCode
   */
  public String getCouponCode() {
    return couponCode;
  }

  /**
   * @param couponCode the couponCode to set
   */
  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }

  /**
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @param customerCode the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * @return the couponIssueDate
   */
  public Date getCouponIssueDate() {
    return couponIssueDate;
  }

  /**
   * @param couponIssueDate the couponIssueDate to set
   */
  public void setCouponIssueDate(Date couponIssueDate) {
    this.couponIssueDate = couponIssueDate;
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
   * @return the couponType
   */
  public Long getCouponType() {
    return couponType;
  }

  /**
   * @param couponType the couponType to set
   */
  public void setCouponType(Long couponType) {
    this.couponType = couponType;
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
   * @return the minIssueOrderAmount
   */
  public BigDecimal getMinIssueOrderAmount() {
    return minIssueOrderAmount;
  }

  /**
   * @param minIssueOrderAmount the minIssueOrderAmount to set
   */
  public void setMinIssueOrderAmount(BigDecimal minIssueOrderAmount) {
    this.minIssueOrderAmount = minIssueOrderAmount;
  }

  /**
   * @return the minIssueStartDatetime
   */
  public Date getMinIssueStartDatetime() {
    return minIssueStartDatetime;
  }

  /**
   * @param minIssueStartDatetime the minIssueStartDatetime to set
   */
  public void setMinIssueStartDatetime(Date minIssueStartDatetime) {
    this.minIssueStartDatetime = minIssueStartDatetime;
  }

  /**
   * @return the minIssueEndDatetime
   */
  public Date getMinIssueEndDatetime() {
    return minIssueEndDatetime;
  }

  /**
   * @param minIssueEndDatetime the minIssueEndDatetime to set
   */
  public void setMinIssueEndDatetime(Date minIssueEndDatetime) {
    this.minIssueEndDatetime = minIssueEndDatetime;
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
   * @return the minUseStartDatetime
   */
  public Date getMinUseStartDatetime() {
    return minUseStartDatetime;
  }

  /**
   * @param minUseStartDatetime the minUseStartDatetime to set
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
   * @param minUseEndDatetime the minUseEndDatetime to set
   */
  public void setMinUseEndDatetime(Date minUseEndDatetime) {
    this.minUseEndDatetime = minUseEndDatetime;
  }

  /**
   * @return the issueReason
   */
  public String getIssueReason() {
    return issueReason;
  }

  /**
   * @param issueReason the issueReason to set
   */
  public void setIssueReason(String issueReason) {
    this.issueReason = issueReason;
  }

  /**
   * @return the memo
   */
  public String getMemo() {
    return memo;
  }

  /**
   * @param memo the memo to set
   */
  public void setMemo(String memo) {
    this.memo = memo;
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
   * @return the applicableArea
   */
  public String getApplicableArea() {
    return applicableArea;
  }

  /**
   * @param applicableArea the applicableArea to set
   */
  public void setApplicableArea(String applicableArea) {
    this.applicableArea = applicableArea;
  }

  /**
   * @return the objectCommodities
   */
  public String getObjectCommodities() {
    return objectCommodities;
  }

  /**
   * @param objectCommodities the objectCommodities to set
   */
  public void setObjectCommodities(String objectCommodities) {
    this.objectCommodities = objectCommodities;
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

  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * @param ormRowid the ormRowid to set
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @return the createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * @param createdUser the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @return the createdDatetime
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  /**
   * @param createdDatetime the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * @param updatedUser the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  /**
   * @param updatedDatetime the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  /**
   * @return the minUseOrderAmountIssue
   */
  public BigDecimal getMinUseOrderAmountIssue() {
    return minUseOrderAmountIssue;
  }

  /**
   * @param minUseOrderAmountIssue the minUseOrderAmountIssue to set
   */
  public void setMinUseOrderAmountIssue(BigDecimal minUseOrderAmountIssue) {
    this.minUseOrderAmountIssue = minUseOrderAmountIssue;
  }

  /**
   * @return the couponAmountIssue
   */
  public BigDecimal getCouponAmountIssue() {
    return couponAmountIssue;
  }

  /**
   * @param couponAmountIssue the couponAmountIssue to set
   */
  public void setCouponAmountIssue(BigDecimal couponAmountIssue) {
    this.couponAmountIssue = couponAmountIssue;
  }


}
