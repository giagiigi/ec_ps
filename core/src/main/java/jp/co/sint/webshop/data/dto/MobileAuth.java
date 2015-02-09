//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
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
 * 「手机验证(MOBILE_AUTH)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class MobileAuth implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;
  
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "手机验证ID", order = 1)
  private String mobileAuthId;

  @Required
  @Length(11)
  @Metadata(name = "手机号码", order = 2)
  private String mobileNumber;
  
  @Required
  @Metadata(name = "开始时间", order = 3)
  private Date startDatetime;
  
  @Required
  @Metadata(name = "结束时间", order = 4)
  private Date endDatetime;
  
  @Required
  @Length(10)
  @Metadata(name = "验证码", order = 5)
  private String authCode;
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 6)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 7)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 8)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 9)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 10)
  private Date updatedDatetime;
  
  /**
   * 取得手机验证ID
   * @return
   */
  public String getMobileAuthId() {
    return mobileAuthId;
  }

  /**
   * 设定手机验证ID
   * @param mobileAuthId
   */
  public void setMobileAuthId(String mobileAuthId) {
    this.mobileAuthId = mobileAuthId;
  }

  /**
   * 取得开始时间
   * @return
   */
  public Date getStartDatetime() {
    return startDatetime;
  }

  /**
   * 设定开始时间
   * @param startDatetime
   */
  public void setStartDatetime(Date startDatetime) {
    this.startDatetime = startDatetime;
  }

  /**
   * 取得结束时间
   * @return
   */
  public Date getEndDatetime() {
    return endDatetime;
  }

  /**
   * 设定结束时间
   * @param endDatetime
   */
  public void setEndDatetime(Date endDatetime) {
    this.endDatetime = endDatetime;
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

  /** 20111224 os013 add start */

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
  
  /**
   * 取得手机号码
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  /**
   * 设定手机号码
   * @param mobileNumber
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  /**
   * 取得验证码
   * @return
   */
  public String getAuthCode() {
    return authCode;
  }

  /**
   * 设定验证码
   * @param authCode
   */
  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }
  //2013/04/01 优惠券对应 add end
}
