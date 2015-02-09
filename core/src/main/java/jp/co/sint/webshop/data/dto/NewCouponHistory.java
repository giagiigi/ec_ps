package jp.co.sint.webshop.data.dto;

/**
 * 优惠券发行履历
 * 
 * @author ob.
 * @version 1.0.0
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * AbstractNewCouponHistory entity provides the base persistence definition of
 * the NewCouponHistory
 * 
 * @author OB
 */

public class NewCouponHistory implements Serializable, WebshopEntity {

  private static final long serialVersionUID = 1L;

  /** 优惠券编号 */
  @PrimaryKey(1)
  @Required
  @Length(20)
  @Metadata(name = "优惠券编号", order = 1)
  private String couponIssueNo;

  /** 优惠券规则编号 */
  @Required
  @Length(16)
  @Metadata(name = "优惠券规则编号", order = 2)
  private String couponCode;

  /** 优惠券明细编号 */
  @Required
  @Length(16)
  @Metadata(name = "优惠券明细编号", order = 3)
  private String couponIssueDetailNo;

  /** 优惠券名称 */
  @Required
  @Length(40)
  @Metadata(name = "优惠券名称", order = 4)
  private String couponName;

  /** 优惠券发行类别（0:比例；1:固定金额） */
  @Required
  @Digit
  @Metadata(name = "优惠券发行类别", order = 5)
  private Long couponIssueType;

  /** 优惠券发行日时 */
  @Required
  @Metadata(name = "优惠券发行日时", order = 6)
  @Datetime
  private Date couponIssueDatetime;

  /** 优惠券比例 */
  @Digit
  @Range(min = 0, max = 100)
  @Metadata(name = "优惠券比例", order = 7)
  private Long couponProportion;

  /** 优惠金额 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "优惠金额", order = 8)
  private BigDecimal couponAmount;

  /** 优惠券利用最小购买金额 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "优惠券利用最小购买金额", order = 9)
  private BigDecimal minUseOrderAmount;

  /** 优惠券利用开始日时 */
  @Required
  @Datetime
  @Metadata(name = "优惠券利用开始日时", order = 10)
  private Date useStartDatetime;

  /** 优惠券利用结束日时 */
  @Required
  @Datetime
  @Metadata(name = "优惠券利用结束日时", order = 11)
  private Date useEndDatetime;

  /** 发行理由 */
  @Length(200)
  @Metadata(name = "发行理由", order = 12)
  private String issueReason;

  /** 顾客编号 */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顾客编号", order = 13)
  private String customerCode;

  /** 发行原订单编号 */
  @Length(16)
  @Digit
  @Metadata(name = "发行原订单编号", order = 14)
  private String issueOrderNo;

  /** 使用订单编号 */
  @Length(16)
  @Digit
  @Metadata(name = "使用订单编号", order = 15)
  private String useOrderNo;

  /** 使用状态 */
  @Required
  @Length(1)
  @Digit
  @Metadata(name = "使用状态", order = 16)
  private Long useStatus;

  /** データ行ID */
  @Required
  @Digit
  @Metadata(name = "データ行ID", order = 17)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 18)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Datetime
  @Metadata(name = "作成日時", order = 19)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 20)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Datetime
  @Metadata(name = "更新日時", order = 21)
  private Date updatedDatetime;

  // 20120522 tuxinwei add start
  /** 优惠券名称(英文) */
  @Length(40)
  @Metadata(name = "优惠券名称(英文)", order = 22)
  private String couponNameEn;

  /** 优惠券名称(日文) */
  @Length(40)
  @Metadata(name = "优惠券名称(日文)", order = 23)
  private String couponNameJp;

  // 20120522 tuxinwei add end

  // 2013/04/05 优惠券对应 ob add start
  /** 优惠券状态（0：临时 1：有效 2：取消） */
  @Required
  @Length(1)
  @Metadata(name = "优惠券状态", order = 24)
  private Long couponStatus;

  /** 优惠券最大金额优惠值（NULL：99999999） */
  @Required
  @Length(10)
  @Metadata(name = "优惠券最大金额优惠值", order = 25)
  private BigDecimal maxUseOrderAmount;
  
  /** 优惠关联对象使用类型类别（0：可使用   1：不可使用） */
  @Digit
  @Metadata(name = "关联对象使用类型", order = 26)
  private Long useType;

  // 2013/04/05 优惠券对应 ob add end

  /**
   * 优惠券编号を取得します。
   * 
   * @return 优惠券编号
   */
  public String getCouponIssueNo() {
    return couponIssueNo;
  }

  /**
   * 优惠券编号を設定します。
   * 
   * @param couponIssueNo
   *          优惠券编号
   */
  public void setCouponIssueNo(String couponIssueNo) {
    this.couponIssueNo = couponIssueNo;
  }

  /**
   * @return 优惠券规则编号
   */
  public String getCouponCode() {
    return couponCode;
  }

  /**
   * @param CouponCode
   *          设置优惠券规则编号
   */
  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }

  /**
   * 优惠券明细编号を取得します。
   * 
   * @return 优惠券明细编号
   */
  public String getCouponIssueDetailNo() {
    return couponIssueDetailNo;
  }

  /**
   * 优惠券明细编号を設定します。
   * 
   * @param couponIssueDetailNo
   *          优惠券明细编号
   */
  public void setCouponIssueDetailNo(String couponIssueDetailNo) {
    this.couponIssueDetailNo = couponIssueDetailNo;
  }

  /**
   * @return 优惠券发行类别（0:比例；1:固定金额）
   */
  public Long getCouponIssueType() {
    return couponIssueType;
  }

  /**
   * @param couponIssueType
   *          设置优惠券发行类别（0:比例；1:固定金额）
   */
  public void setCouponIssueType(Long couponIssueType) {
    this.couponIssueType = couponIssueType;
  }

  /**
   * @return 优惠券发行日时
   */
  public Date getCouponIssueDatetime() {
    return couponIssueDatetime;
  }

  /**
   * @param couponIssueDatetime
   *          优惠券发行日时
   */
  public void setCouponIssueDatetime(Date couponIssueDatetime) {
    this.couponIssueDatetime = couponIssueDatetime;
  }

  /**
   * @return 优惠券数值
   */
  public BigDecimal getCouponAmount() {
    return couponAmount;
  }

  /**
   * @param couponAmount
   *          设置优惠券数值
   */
  public void setCouponAmount(BigDecimal couponAmount) {
    this.couponAmount = couponAmount;
  }

  /**
   * @return 优惠券比例
   */
  public Long getCouponProportion() {
    return couponProportion;
  }

  /**
   * @param couponProportion
   *          优惠券比例
   */
  public void setCouponProportion(Long couponProportion) {
    this.couponProportion = couponProportion;
  }

  /**
   * @return 优惠券利用最小购买金额
   */
  public BigDecimal getMinUseOrderAmount() {
    return minUseOrderAmount;
  }

  /**
   * @param minUseOrderAmount
   *          设置优惠券利用最小购买金额
   */
  public void setMinUseOrderAmount(BigDecimal minUseOrderAmount) {
    this.minUseOrderAmount = minUseOrderAmount;
  }

  /**
   * @return 发行理由
   */
  public String getIssueReason() {
    return issueReason;
  }

  /**
   * @param issueReason
   *          发行理由
   */
  public void setIssueReason(String issueReason) {
    this.issueReason = issueReason;
  }

  /**
   * 顾客编号を取得します。
   * 
   * @return 顾客编号
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * 顾客编号を設定します。
   * 
   * @param customerCode
   *          顾客编号
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * 发行原订单编号を取得します。
   * 
   * @return 发行原订单编号
   */
  public String getIssueOrderNo() {
    return issueOrderNo;
  }

  /**
   * 发行原订单编号を設定します。
   * 
   * @param issueOrderNo
   *          发行原订单编号
   */
  public void setIssueOrderNo(String issueOrderNo) {
    this.issueOrderNo = issueOrderNo;
  }

  /**
   * 使用订单编号を取得します。
   * 
   * @return 使用订单编号
   */
  public String getUseOrderNo() {
    return useOrderNo;
  }

  /**
   * 使用订单编号を設定します。
   * 
   * @param useOrderNo
   *          使用订单编号
   */
  public void setUseOrderNo(String useOrderNo) {
    this.useOrderNo = useOrderNo;
  }

  /**
   * 使用状态を取得します。
   * 
   * @return 使用状态
   */
  public Long getUseStatus() {
    return useStatus;
  }

  /**
   * 使用状态を設定します。
   * 
   * @param 使用状态
   *          使用状态
   */
  public void setUseStatus(Long useStatus) {
    this.useStatus = useStatus;
  }

  /**
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * @param OrmRowid
   *          设置データ行ID
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @return 作成ユーザ
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * @param CreatedUser
   *          设置作成ユーザ
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @return 作成日時
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  /**
   * @param CreatedDatetime
   *          设置作成日時
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * @return 更新ユーザ
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * @param UpdatedUser
   *          设置更新ユーザ
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @return 更新日時
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  /**
   * @param UpdatedDatetime
   *          设置更新日時
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  /**
   * @return the couponName
   */
  public String getCouponName() {
    return couponName;
  }

  /**
   * @param couponName
   *          the couponName to set
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
   * @param couponNameEn
   *          the couponNameEn to set
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
   * @param couponNameJp
   *          the couponNameJp to set
   */
  public void setCouponNameJp(String couponNameJp) {
    this.couponNameJp = couponNameJp;
  }

  public Long getCouponStatus() {
    return couponStatus;
  }

  public void setCouponStatus(Long couponStatus) {
    this.couponStatus = couponStatus;
  }

  public BigDecimal getMaxUseOrderAmount() {
    return maxUseOrderAmount;
  }

  public void setMaxUseOrderAmount(BigDecimal maxUseOrderAmount) {
    this.maxUseOrderAmount = maxUseOrderAmount;
  }

  
  /**
   * @return the useStartDatetime
   */
  public Date getUseStartDatetime() {
    return useStartDatetime;
  }

  
  /**
   * @param useStartDatetime the useStartDatetime to set
   */
  public void setUseStartDatetime(Date useStartDatetime) {
    this.useStartDatetime = useStartDatetime;
  }

  
  /**
   * @return the useEndDatetime
   */
  public Date getUseEndDatetime() {
    return useEndDatetime;
  }

  
  /**
   * @param useEndDatetime the useEndDatetime to set
   */
  public void setUseEndDatetime(Date useEndDatetime) {
    this.useEndDatetime = useEndDatetime;
  }

  
  /**
   * @return the useType
   */
  public Long getUseType() {
    return useType;
  }

  
  /**
   * @param useType the useType to set
   */
  public void setUseType(Long useType) {
    this.useType = useType;
  }
}
