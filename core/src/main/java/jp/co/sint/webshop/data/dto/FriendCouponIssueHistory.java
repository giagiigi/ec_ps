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
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * 「朋友介绍发行履历(friend_coupon_issue_history)」テーブルの1行分のレコードを表すDTO(Data Transfer
 * Object)です。
 * 
 * @author System Integrator Corp.
 */
public class FriendCouponIssueHistory implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 优惠券规则编号（公共优惠券） */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "优惠券规则编号", order = 1)
  private String couponCode;

  /** 发行规则编号 */
  @Required
  @Length(8)
  @Metadata(name = "发行规则编号", order = 2)
  private String friendCouponRuleNo;

  /** 顾客编号 */
  @Required
  @Length(16)
  @Metadata(name = "顾客编号", order = 3)
  private String customerCode;

  /** 发行日期 */
  @Required
  @Metadata(name = "发行日期", order = 4)
  private Date couponIssueDate;

  /** 发行金额 */
  @Metadata(name = "发行金额", order = 4)
  private BigDecimal couponAmount;

  /** 获取记分 */
  @Required
  @Length(8)
  @Metadata(name = "临界后获得积分", order = 5)
  private Long obtainPoint;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 5)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 6)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 7)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 8)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 9)
  private Date updatedDatetime;

  @Required
  @Length(8)
  @Metadata(name = "发行获得积分", order = 10)
  private Long issueObtainPoint;

  @Required
  @Length(8)
  @Metadata(name = "临界前获得积分", order = 11)
  private Long formerUsePoint;

  @Required
  @Length(8)
  @Metadata(name = "优惠券类别", order = 12)
  private Long couponIssueType;

  @Length(8)
  @Metadata(name = "优惠券比例", order = 13)
  private Long couponProportion;
  
  @Required
  @Length(8)
  @Metadata(name = "优惠临界人数", order = 14)
  private Long couponUseNum;
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
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @param customerCode
   *          the customerCode to set
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
   * @param couponIssueDate
   *          the couponIssueDate to set
   */
  public void setCouponIssueDate(Date couponIssueDate) {
    this.couponIssueDate = couponIssueDate;
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
   * @return the couponUseNum
   */
  public Long getCouponUseNum() {
    return couponUseNum;
  }

  
  /**
   * @param couponUseNum the couponUseNum to set
   */
  public void setCouponUseNum(Long couponUseNum) {
    this.couponUseNum = couponUseNum;
  }

}
