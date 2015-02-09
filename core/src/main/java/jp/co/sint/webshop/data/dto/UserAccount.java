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
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「管理ユーザ(USER_ACCOUNT)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class UserAccount implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ユーザコード */
  @PrimaryKey(1)
  @Length(38)
  @AlphaNum2
  @Metadata(name = "ユーザコード", order = 1)
  private Long userCode;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 2)
  private String shopCode;

  /** ユーザログインID */
  @Required
  @Length(20)
  @Metadata(name = "ユーザログインID", order = 3)
  private String userLoginId;

  /** パスワード */
  @Required
  @Length(50)
  @Metadata(name = "パスワード", order = 4)
  private String password;

  /** ユーザ名 */
  @Required
  @Length(20)
  @Metadata(name = "ユーザ名", order = 5)
  private String userName;

  /** メールアドレス */
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス", order = 6)
  private String email;

  /** ログイン失敗回数 */
  @Required
  @Length(2)
  @Metadata(name = "ログイン失敗回数", order = 7)
  private Long loginErrorCount;

  /** ログインロックフラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "ログインロックフラグ", order = 8)
  private Long loginLockedFlg;

  /** ログイン日時 */
  @Metadata(name = "ログイン日時", order = 9)
  private Date loginDatetime;

  /** メモ */
  @Length(200)
  @Metadata(name = "メモ", order = 10)
  private String memo;

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
   * ユーザログインIDを取得します
   *
   * @return ユーザログインID
   */
  public String getUserLoginId() {
    return this.userLoginId;
  }

  /**
   * パスワードを取得します
   *
   * @return パスワード
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * ユーザ名を取得します
   *
   * @return ユーザ名
   */
  public String getUserName() {
    return this.userName;
  }

  /**
   * メールアドレスを取得します
   *
   * @return メールアドレス
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * ログイン失敗回数を取得します
   *
   * @return ログイン失敗回数
   */
  public Long getLoginErrorCount() {
    return this.loginErrorCount;
  }

  /**
   * ログインロックフラグを取得します
   *
   * @return ログインロックフラグ
   */
  public Long getLoginLockedFlg() {
    return this.loginLockedFlg;
  }

  /**
   * ログイン日時を取得します
   *
   * @return ログイン日時
   */
  public Date getLoginDatetime() {
    return DateUtil.immutableCopy(this.loginDatetime);
  }

  /**
   * メモを取得します
   *
   * @return メモ
   */
  public String getMemo() {
    return this.memo;
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
   * ユーザログインIDを設定します
   *
   * @param  val ユーザログインID
   */
  public void setUserLoginId(String val) {
    this.userLoginId = val;
  }

  /**
   * パスワードを設定します
   *
   * @param  val パスワード
   */
  public void setPassword(String val) {
    this.password = val;
  }

  /**
   * ユーザ名を設定します
   *
   * @param  val ユーザ名
   */
  public void setUserName(String val) {
    this.userName = val;
  }

  /**
   * メールアドレスを設定します
   *
   * @param  val メールアドレス
   */
  public void setEmail(String val) {
    this.email = val;
  }

  /**
   * ログイン失敗回数を設定します
   *
   * @param  val ログイン失敗回数
   */
  public void setLoginErrorCount(Long val) {
    this.loginErrorCount = val;
  }

  /**
   * ログインロックフラグを設定します
   *
   * @param  val ログインロックフラグ
   */
  public void setLoginLockedFlg(Long val) {
    this.loginLockedFlg = val;
  }

  /**
   * ログイン日時を設定します
   *
   * @param  val ログイン日時
   */
  public void setLoginDatetime(Date val) {
    this.loginDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * メモを設定します
   *
   * @param  val メモ
   */
  public void setMemo(String val) {
    this.memo = val;
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
