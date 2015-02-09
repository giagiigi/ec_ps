//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
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
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 *
 *
 * @author System Integrator Corp.
 *
 */
public class FriendCouponUseHistory implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 使用履历id */
  @PrimaryKey(1)
  @Length(38)
  @Required
  @Metadata(name = "使用履历id", order = 1)
  private String useHistoryId;

  /** 优惠劵规则编号 */
  @Required
  @Length(16)
  @Metadata(name = "优惠劵规则编号", order = 2)
  private String couponCode;

  /** 发行规则编号 */
  @Required
  @Length(8)
  @Metadata(name = "发行规则编号", order = 3)
  private String friendCouponRuleNo;

  /** 订单编号 */
  @Required
  @Metadata(name = "订单编号", order = 4)
  @Length(16)
  private String orderNo;
  
  /** 顾客编号 */
  @Required
  @Metadata(name = "顾客编号", order = 5)
  @Length(16)
  private String customerCode;
  
  /** 顾客名称 */
  @Metadata(name = "顾客名称", order = 6)
  @Length(16)
  private String customerName;
  
  /** 使用日期 */
  @Required
  @Metadata(name = "使用日期", order = 7)
  private Date issueDate;
  
  /** 优惠券金额 */
  @Metadata(name = "优惠券金额", order = 8)
  private BigDecimal couponAmount;
  
  /** 积分数 */
  @Metadata(name = "临界后获得积分", order = 9)
  private Long point;
  
  /** 状态（0：临时  1：有效  2：无效） */
  @Required
  @Metadata(name = "状态", order = 10)
  private Long pointStatus;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 11)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 12)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 13)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 14)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 15)
  private Date updatedDatetime;

  /** 更新日時 */
  @Required
  @Metadata(name = "优惠临界人数", order = 16)
  private Long couponUseNum;
  
  /** 更新日時 */
  @Required
  @Metadata(name = "临界前获得积分", order = 17)
  private Long formerUsePoint;
  
  
  /**
   * @return the useHistoryId
   */
  public String getUseHistoryId() {
    return useHistoryId;
  }

  /**
   * @param useHistoryId the useHistoryId to set
   */
  public void setUseHistoryId(String useHistoryId) {
    this.useHistoryId = useHistoryId;
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
   * @return the customerName
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * @param customerName the customerName to set
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  /**
   * @return the issueDate
   */
  public Date getIssueDate() {
    return issueDate;
  }

  /**
   * @param issueDate the issueDate to set
   */
  public void setIssueDate(Date issueDate) {
    this.issueDate = issueDate;
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
   * @return the point
   */
  public Long getPoint() {
    return point;
  }

  /**
   * @param point the point to set
   */
  public void setPoint(Long point) {
    this.point = point;
  }

  /**
   * @return the pointStatus
   */
  public Long getPointStatus() {
    return pointStatus;
  }

  /**
   * @param pointStatus the pointStatus to set
   */
  public void setPointStatus(Long pointStatus) {
    this.pointStatus = pointStatus;
  }

  /**
   * データ行IDを取得します
   *
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return this.ormRowid;
  }

  /**
   * 作成ユーザを取得します
   *
   * @return 作成ユーザ
   */
  public String getCreatedUser() {
    return this.createdUser;
  }

  /**
   * 作成日時を取得します
   *
   * @return 作成日時
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(this.createdDatetime);
  }

  /**
   * 更新ユーザを取得します
   *
   * @return 更新ユーザ
   */
  public String getUpdatedUser() {
    return this.updatedUser;
  }

  /**
   * 更新日時を取得します
   *
   * @return 更新日時
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(this.updatedDatetime);
  }

  /**
   * データ行IDを設定します
   *
   * @param  val データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   *
   * @param  val 作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   *
   * @param  val 作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   *
   * @param  val 更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   *
   * @param  val 更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
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

  
  /**
   * @return the formerUsePoint
   */
  public Long getFormerUsePoint() {
    return formerUsePoint;
  }

  
  /**
   * @param formerUsePoint the formerUsePoint to set
   */
  public void setFormerUsePoint(Long formerUsePoint) {
    this.formerUsePoint = formerUsePoint;
  }

}
