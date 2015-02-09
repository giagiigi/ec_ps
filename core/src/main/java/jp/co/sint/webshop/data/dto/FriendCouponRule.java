//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * 「朋友介绍(friend_coupon_rule)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class FriendCouponRule implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 发行规则编号 */
  @PrimaryKey(1)
  @Required
  @Length(8)
  @Metadata(name = "发行规则编号", order = 1)
  private String friendCouponRuleNo;

  /** 发行可能日期区分 */
  @Required
  @Length(1)
  @Metadata(name = "发行可能日期区分", order = 2)
  private Long issueDateType;

  /** 月份值 */
  @Length(2)
  @Metadata(name = "月份值", order = 3)
  private Long issueDateNum;

  /** 发行可能开始日期 */
  @Metadata(name = "发行可能开始日期", order = 4)
  private Date issueStartDate;

  /** 发行可能结束日期 */
  @Metadata(name = "发行可能结束日期", order = 5)
  private Date issueEndDate;

  /** 订购记录 */
  @Required
  @Length(8)
  @Metadata(name = "订购记录", order = 6)
  private Long orderHistory;

  /** 利用优惠券金额 */
  @Metadata(name = "利用优惠券金额", order = 7)
  private BigDecimal couponAmount;

  /** 利用个人回数 */
  @Length(8)
  @Metadata(name = "利用个人回数", order = 8)
  private Long personalUseLimit;

  /** 利用SITE回数 */
  @Length(8)
  @Metadata(name = "利用SITE回数", order = 9)
  private Long siteUseLimit;

  /** 利用优惠券最小购买金额 */
  @Required
  @Length(10)
  @Metadata(name = "利用优惠券最小购买金额", order = 10)
  private BigDecimal minUseOrderAmount;

  /** 利用有效期类别 */
  @Required
  @Length(1)
  @Metadata(name = "利用有效期类别", order = 11)
  private Long useValidType;

  /** 利用有效期值 */
  @Required
  @Length(8)
  @Metadata(name = "利用有效期值", order = 12)
  private Long useValidNum;

  /** 利用限定 */
  @Required
  @Metadata(name = "利用限定", order = 13)
  private Long applicableObjects;

  /** 换取积分 */
  @Required
  @Length(8)
  @Metadata(name = "临界前获得积分", order = 14)
  private Long obtainPoint;

  /** 公共优惠券固定字符 */
  @Length(2)
  @Metadata(name = "公共优惠券固定字符", order = 15)
  private String fixChar;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 16)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 17)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 18)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 19)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 20)
  private Date updatedDatetime;

  /** 优惠券名称 */
  @Required
  @Length(40)
  @Metadata(name = "优惠券名称", order = 21)
  private String friendCouponRuleCn;

  /** 优惠券名称英文 */
  @Required
  @Length(40)
  @Metadata(name = "优惠券名称英文", order = 22)
  private String friendCouponRuleEn;

  /** 优惠券名称日语 */
  @Required
  @Length(40)
  @Metadata(name = "优惠券名称日语", order = 23)
  private String friendCouponRuleJp;

  /** 优惠券发行类别（0:比例；1:固定金额） */
  @Digit
  @Metadata(name = "优惠券发行类别", order = 24)
  private Long couponIssueType;

  /** 优惠券比例 */
  @Digit
  @Range(min = 0, max = 100)
  @Metadata(name = "优惠券比例", order = 25)
  private Long couponProportion;

  /** 发行获得积分 */
  @Length(8)
  @Metadata(name = "发行获得积分", order = 26)
  private Long issueObtainPoint;

  /** 前使用积分 */

  @Length(8)
  @Metadata(name = "临界前获得积分", order = 27)
  private Long formerUsePoint;

  /** 使用位数 */
  @Length(8)
  @Metadata(name = "优惠临界订单数", order = 29)
  private Long couponUseNum;

  @Length(10)
  @Metadata(name = "优惠券使用最大购买金额", order = 30)
  private BigDecimal maxUseOrderAmount;

  /** 对象商品集合 */
  @Metadata(name = "对象商品集合", order = 31)
  private String objectCommodities;

  /** 对象品牌集合字段集合 */
  @Metadata(name = "对象品牌集合字段", order = 34)
  private String objectBrand;

  // 20130927 txw add start
  /** 对象分类集合字段集合 */
  @Metadata(name = "对象分类集合字段", order = 35)
  private String objectCategory;

  /** 优惠关联对象使用类型类别（0：可使用 1：不可使用 2:部分使用(限) 3:部分使用） */
  @Digit
  @Metadata(name = "关联对象使用类型", order = 33)
  private Long useType;

  /**
   * @return the friendCouponRuleNo
   */
  public String getFriendCouponRuleNo() {
    return friendCouponRuleNo;
  }

  /**
   * @param friendCouponRuleNo
   *          the friendCouponRuleNo to set
   */
  public void setFriendCouponRuleNo(String friendCouponRuleNo) {
    this.friendCouponRuleNo = friendCouponRuleNo;
  }

  /**
   * @return the issueDateType
   */
  public Long getIssueDateType() {
    return issueDateType;
  }

  /**
   * @param issueDateType
   *          the issueDateType to set
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
   * @param issueDateNum
   *          the issueDateNum to set
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
   * @param issueStartDate
   *          the issueStartDate to set
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
   * @param issueEndDate
   *          the issueEndDate to set
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
   * @param orderHistory
   *          the orderHistory to set
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
   * @param couponAmount
   *          the couponAmount to set
   */
  public void setCouponAmount(BigDecimal couponAmount) {
    this.couponAmount = couponAmount;
  }

  /**
   * @return the personalUseLimit
   */
  public Long getPersonalUseLimit() {
    return personalUseLimit;
  }

  /**
   * @param personalUseLimit
   *          the personalUseLimit to set
   */
  public void setPersonalUseLimit(Long personalUseLimit) {
    this.personalUseLimit = personalUseLimit;
  }

  /**
   * @return the siteUseLimit
   */
  public Long getSiteUseLimit() {
    return siteUseLimit;
  }

  /**
   * @param siteUseLimit
   *          the siteUseLimit to set
   */
  public void setSiteUseLimit(Long siteUseLimit) {
    this.siteUseLimit = siteUseLimit;
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
   * @return the useValidType
   */
  public Long getUseValidType() {
    return useValidType;
  }

  /**
   * @param useValidType
   *          the useValidType to set
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
   * @param useValidNum
   *          the useValidNum to set
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
   * @param applicableObjects
   *          the applicableObjects to set
   */
  public void setApplicableObjects(Long applicableObjects) {
    this.applicableObjects = applicableObjects;
  }

  /**
   * @return the obtainPoint
   */
  public Long getObtainPoint() {
    return obtainPoint;
  }

  /**
   * @param obtainPoint
   *          the obtainPoint to set
   */
  public void setObtainPoint(Long obtainPoint) {
    this.obtainPoint = obtainPoint;
  }

  /**
   * @return the fixChar
   */
  public String getFixChar() {
    return fixChar;
  }

  /**
   * @param fixChar
   *          the fixChar to set
   */
  public void setFixChar(String fixChar) {
    this.fixChar = fixChar;
  }

  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * @param ormRowid
   *          the ormRowid to set
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
   * @param createdUser
   *          the createdUser to set
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
   * @param createdDatetime
   *          the createdDatetime to set
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
   * @param updatedUser
   *          the updatedUser to set
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
   * @param updatedDatetime
   *          the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  /**
   * @return the friendCouponRuleCn
   */
  public String getFriendCouponRuleCn() {
    return friendCouponRuleCn;
  }

  /**
   * @param friendCouponRuleCn
   *          the friendCouponRuleCn to set
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
   * @param friendCouponRuleEn
   *          the friendCouponRuleEn to set
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
   * @param friendCouponRuleJp
   *          the friendCouponRuleJp to set
   */
  public void setFriendCouponRuleJp(String friendCouponRuleJp) {
    this.friendCouponRuleJp = friendCouponRuleJp;
  }

  /**
   * @return the couponIssueType
   */
  public Long getCouponIssueType() {
    return couponIssueType;
  }

  /**
   * @param couponIssueType
   *          the couponIssueType to set
   */
  public void setCouponIssueType(Long couponIssueType) {
    this.couponIssueType = couponIssueType;
  }

  /**
   * @return the couponProportion
   */
  public Long getCouponProportion() {
    return couponProportion;
  }

  /**
   * @param couponProportion
   *          the couponProportion to set
   */
  public void setCouponProportion(Long couponProportion) {
    this.couponProportion = couponProportion;
  }

  /**
   * @return the issueObtainPoint
   */
  public Long getIssueObtainPoint() {
    return issueObtainPoint;
  }

  /**
   * @param issueObtainPoint
   *          the issueObtainPoint to set
   */
  public void setIssueObtainPoint(Long issueObtainPoint) {
    this.issueObtainPoint = issueObtainPoint;
  }

  /**
   * @return the formerUsePoint
   */
  public Long getFormerUsePoint() {
    return formerUsePoint;
  }

  /**
   * @param formerUsePoint
   *          the formerUsePoint to set
   */
  public void setFormerUsePoint(Long formerUsePoint) {
    this.formerUsePoint = formerUsePoint;
  }

  /**
   * @return the couponUseNum
   */
  public Long getCouponUseNum() {
    return couponUseNum;
  }

  /**
   * @param couponUseNum
   *          the couponUseNum to set
   */
  public void setCouponUseNum(Long couponUseNum) {
    this.couponUseNum = couponUseNum;
  }

  /**
   * @return the maxUseOrderAmount
   */
  public BigDecimal getMaxUseOrderAmount() {
    return maxUseOrderAmount;
  }

  /**
   * @param maxUseOrderAmount
   *          the maxUseOrderAmount to set
   */
  public void setMaxUseOrderAmount(BigDecimal maxUseOrderAmount) {
    this.maxUseOrderAmount = maxUseOrderAmount;
  }

  /**
   * @return the objectCommodities
   */
  public String getObjectCommodities() {
    return objectCommodities;
  }

  /**
   * @param objectCommodities
   *          the objectCommodities to set
   */
  public void setObjectCommodities(String objectCommodities) {
    this.objectCommodities = objectCommodities;
  }

  /**
   * @return the objectBrand
   */
  public String getObjectBrand() {
    return objectBrand;
  }

  /**
   * @param objectBrand
   *          the objectBrand to set
   */
  public void setObjectBrand(String objectBrand) {
    this.objectBrand = objectBrand;
  }

  /**
   * @return the objectCategory
   */
  public String getObjectCategory() {
    return objectCategory;
  }

  /**
   * @param objectCategory
   *          the objectCategory to set
   */
  public void setObjectCategory(String objectCategory) {
    this.objectCategory = objectCategory;
  }

  /**
   * @return the useType
   */
  public Long getUseType() {
    return useType;
  }

  /**
   * @param useType
   *          the useType to set
   */
  public void setUseType(Long useType) {
    this.useType = useType;
  }

}
