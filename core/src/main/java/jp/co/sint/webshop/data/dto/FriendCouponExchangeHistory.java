//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
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
 * @author System Integrator Corp.
 *
 */
public class FriendCouponExchangeHistory implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 优惠劵编号 */
  @PrimaryKey(1)
  @Length(20)
  @Required
  @Metadata(name = "优惠劵编号", order = 1)
  private String couponIssueNo;

  /** 顾客编号 */
  @Required
  @Length(16)
  @Metadata(name = "顾客编号", order = 2)
  private String customerCode;

  /** 兑换使用积分数 */
  @Required
  @Metadata(name = "兑换使用积分数", order = 3)
  private Long exchangePoint;

  /** 兑换日期 */
  @Required
  @Metadata(name = "兑换日期", order = 4)
  private Date exchangeDate;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 9)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 10)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 11)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 12)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 13)
  private Date updatedDatetime;

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
   * @return the exchangePoint
   */
  public Long getExchangePoint() {
    return exchangePoint;
  }

  /**
   * @param exchangePoint the exchangePoint to set
   */
  public void setExchangePoint(Long exchangePoint) {
    this.exchangePoint = exchangePoint;
  }

  /**
   * @return the exchangeDate
   */
  public Date getExchangeDate() {
    return exchangeDate;
  }

  /**
   * @param exchangeDate the exchangeDate to set
   */
  public void setExchangeDate(Date exchangeDate) {
    this.exchangeDate = exchangeDate;
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

}
