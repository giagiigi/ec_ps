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
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「メールマガジン(MAIL_MAGAZINE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class MailMagazine implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** メールマガジンコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "メールマガジンコード", order = 1)
  private String mailMagazineCode;

  /** メールマガジンタイトル */
  @Length(20)
  @Metadata(name = "メールマガジンタイトル", order = 2)
  private String mailMagazineTitle;

  /** メールマガジン説明 */
  @Length(300)
  @Metadata(name = "メールマガジン説明", order = 3)
  private String mailMagazineDescription;

  /** 表示フラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "表示フラグ", order = 4)
  private Long displayFlg;

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

  /**
   * メールマガジンコードを取得します
   *
   * @return メールマガジンコード
   */
  public String getMailMagazineCode() {
    return this.mailMagazineCode;
  }

  /**
   * メールマガジンタイトルを取得します
   *
   * @return メールマガジンタイトル
   */
  public String getMailMagazineTitle() {
    return this.mailMagazineTitle;
  }

  /**
   * メールマガジン説明を取得します
   *
   * @return メールマガジン説明
   */
  public String getMailMagazineDescription() {
    return this.mailMagazineDescription;
  }

  /**
   * 表示フラグを取得します
   *
   * @return 表示フラグ
   */
  public Long getDisplayFlg() {
    return this.displayFlg;
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
   * メールマガジンコードを設定します
   *
   * @param  val メールマガジンコード
   */
  public void setMailMagazineCode(String val) {
    this.mailMagazineCode = val;
  }

  /**
   * メールマガジンタイトルを設定します
   *
   * @param  val メールマガジンタイトル
   */
  public void setMailMagazineTitle(String val) {
    this.mailMagazineTitle = val;
  }

  /**
   * メールマガジン説明を設定します
   *
   * @param  val メールマガジン説明
   */
  public void setMailMagazineDescription(String val) {
    this.mailMagazineDescription = val;
  }

  /**
   * 表示フラグを設定します
   *
   * @param  val 表示フラグ
   */
  public void setDisplayFlg(Long val) {
    this.displayFlg = val;
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
