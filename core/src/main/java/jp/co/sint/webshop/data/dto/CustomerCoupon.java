// Copyright(C) 2007-2008 System Integrator Corp.
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「クーポン発行(POINT_ISSUE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class CustomerCoupon implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** アクセスログID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "アクセスログID", order = 1)
  private Long customerCouponId;

  /** 顧客コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード", order = 1)
  private String customerCode;

  /** クーポンルール番号 */
  @Length(8)
  @Digit
  @Metadata(name = "クーポン番号", order = 2)
  private Long couponIssueNo;

  /** クーポン機能使用フラグ */
  @Required
  @Length(1)
  @Metadata(name = "クーポン機能使用フラグ", order = 2)
  private Long useFlg;

  /** クーポンフラント金額 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "クーポンフラント金額", order = 5)
  private BigDecimal couponPrice;

  /** クーポン使用開始日 */
  // @Required
  @Metadata(name = "クーポン使用日", order = 8)
  private Date useDate;

  /** クーポン使用開始日 */
  @Required
  @Metadata(name = "クーポン発行日", order = 8)
  private Date issueDate;

  /** クーポン使用開始日 */
  @Required
  @Metadata(name = "クーポン使用開始日", order = 8)
  private Date useCouponStartDate;

  /** クーポン使用終了日 */
  @Required
  @Metadata(name = "クーポン使用終了日", order = 9)
  private Date useCouponEndDate;

  /** クーポン管理側名 */
  @Required
  @Length(40)
  @Metadata(name = "クーポン名", order = 3)
  private String couponName;

  /** 受注番号 */
  @Length(16)
  @Digit
  @Metadata(name = "受注番号", order = 1)
  private String orderNo;

  /** 受注番号 */
  @Length(16)
  @Digit
  @Metadata(name = "受注番号", order = 1)
  private String getCouponOrderNo;

  /** 受注番号 */
  @Length(50)
  @Metadata(name = "クーポン付与理由", order = 1)
  private String description;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 13)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 14)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 15)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 16)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 17)
  private Date updatedDatetime;

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
   * @param val
   *          データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   * 
   * @param val
   *          作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   * 
   * @param val
   *          作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   * 
   * @param val
   *          更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   * 
   * @param val
   *          更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

  public Long getCouponIssueNo() {
    return couponIssueNo;
  }

  public void setCouponIssueNo(Long couponIssueNo) {
    this.couponIssueNo = couponIssueNo;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public Long getUseFlg() {
    return useFlg;
  }

  public void setUseFlg(Long useFlg) {
    this.useFlg = useFlg;
  }

  public Long getCustomerCouponId() {
    return customerCouponId;
  }

  public void setCustomerCouponId(Long customerCouponId) {
    this.customerCouponId = customerCouponId;
  }

  public BigDecimal getCouponPrice() {
    return couponPrice;
  }

  public void setCouponPrice(BigDecimal couponPrice) {
    this.couponPrice = couponPrice;
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
    return  DateUtil.immutableCopy(useCouponStartDate);
  }

  public void setUseCouponStartDate(Date useCouponStartDate) {
    this.useCouponStartDate = DateUtil.immutableCopy(useCouponStartDate);
  }

  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public Date getIssueDate() {
    return DateUtil.immutableCopy(issueDate);
  }

  public void setIssueDate(Date issueDate) {
    this.issueDate = DateUtil.immutableCopy(issueDate);
  }

  public Date getUseDate() {
    return DateUtil.immutableCopy(useDate);
  }

  public void setUseDate(Date useDate) {
    this.useDate = DateUtil.immutableCopy(useDate);
  }

  public String getGetCouponOrderNo() {
    return getCouponOrderNo;
  }

  public void setGetCouponOrderNo(String getCouponOrderNo) {
    this.getCouponOrderNo = getCouponOrderNo;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
