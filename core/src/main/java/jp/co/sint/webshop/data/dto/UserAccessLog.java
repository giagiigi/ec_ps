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
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「管理側アクセスログ(USER_ACCESS_LOG)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class UserAccessLog implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 管理側アクセスログID */
  @PrimaryKey(1)
  @Length(38)
  @Metadata(name = "管理側アクセスログID", order = 1)
  private Long userAccessLogId;

  /** ユーザコード */
  @Required
  @Length(38)
  @AlphaNum2
  @Metadata(name = "ユーザコード", order = 2)
  private Long userCode;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 3)
  private String shopCode;

  /** 管理ユーザ名称 */
  @Length(20)
  @Metadata(name = "管理ユーザ名称", order = 4)
  private String userName;

  /** オペレーションコード */
  @Required
  @Length(10)
  @AlphaNum2
  @Metadata(name = "オペレーションコード", order = 5)
  private String operationCode;

  /** アクセス日時 */
  @Required
  @Metadata(name = "アクセス日時", order = 6)
  private Date accessDatetime;

  /** IPアドレス */
  @Length(40)
  @Metadata(name = "IPアドレス", order = 7)
  private String ipAddress;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 8)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 9)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 10)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 11)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 12)
  private Date updatedDatetime;

  /**
   * 管理側アクセスログIDを取得します
   *
   * @return 管理側アクセスログID
   */
  public Long getUserAccessLogId() {
    return this.userAccessLogId;
  }

  /**
   * ユーザコードを取得します
   *
   * @return ユーザコード
   */
  public Long getUserCode() {
    return this.userCode;
  }

  /**
   * ショップコードを取得します
   *
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * 管理ユーザ名称を取得します
   *
   * @return 管理ユーザ名称
   */
  public String getUserName() {
    return this.userName;
  }

  /**
   * オペレーションコードを取得します
   *
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return this.operationCode;
  }

  /**
   * アクセス日時を取得します
   *
   * @return アクセス日時
   */
  public Date getAccessDatetime() {
    return DateUtil.immutableCopy(this.accessDatetime);
  }

  /**
   * IPアドレスを取得します
   *
   * @return IPアドレス
   */
  public String getIpAddress() {
    return this.ipAddress;
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
   * 管理側アクセスログIDを設定します
   *
   * @param  val 管理側アクセスログID
   */
  public void setUserAccessLogId(Long val) {
    this.userAccessLogId = val;
  }

  /**
   * ユーザコードを設定します
   *
   * @param  val ユーザコード
   */
  public void setUserCode(Long val) {
    this.userCode = val;
  }

  /**
   * ショップコードを設定します
   *
   * @param  val ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * 管理ユーザ名称を設定します
   *
   * @param  val 管理ユーザ名称
   */
  public void setUserName(String val) {
    this.userName = val;
  }

  /**
   * オペレーションコードを設定します
   *
   * @param  val オペレーションコード
   */
  public void setOperationCode(String val) {
    this.operationCode = val;
  }

  /**
   * アクセス日時を設定します
   *
   * @param  val アクセス日時
   */
  public void setAccessDatetime(Date val) {
    this.accessDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * IPアドレスを設定します
   *
   * @param  val IPアドレス
   */
  public void setIpAddress(String val) {
    this.ipAddress = val;
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
