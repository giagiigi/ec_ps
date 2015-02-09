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
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.MailContentType;
import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「メールテンプレートヘッダ(MAIL_TEMPLATE_HEADER)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class MailTemplateHeaderUs implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** メールタイプ */
  @PrimaryKey(2)
  @Required
  @Length(2)
  @Domain(MailType.class)
  @Metadata(name = "メールタイプ", order = 2)
  private String mailType;

  /** メールテンプレート番号 */
  @PrimaryKey(3)
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "メールテンプレート番号", order = 3)
  private Long mailTemplateNo;

  /** メールコンテントタイプ */
  @Required
  @Length(1)
  @Domain(MailContentType.class)
  @Metadata(name = "メールコンテントタイプ", order = 4)
  private Long mailContentType;

  /** FROMアドレス */
  @Required
  @Length(256)
  @Email
  @Metadata(name = "FROMアドレス", order = 5)
  private String fromAddress;

  /** BCCアドレス */
  @Length(256)
  @Email
  @Metadata(name = "BCCアドレス", order = 6)
  private String bccAddress;

  /** 差出人名 */
  @Length(50)
  @Metadata(name = "差出人名", order = 7)
  private String mailSenderName;

  /** メール件名 */
  @Length(100)
  @Metadata(name = "メール件名", order = 8)
  private String mailSubject;

  /** メール構成 */
  @Length(150)
  @Metadata(name = "メール構成", order = 9)
  private String mailComposition;

  /** 表示順 */
  @Length(8)
  @Metadata(name = "表示順", order = 10)
  private Long displayOrder;

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
   * ショップコードを取得します
   *
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * メールタイプを取得します
   *
   * @return メールタイプ
   */
  public String getMailType() {
    return this.mailType;
  }

  /**
   * メールテンプレート番号を取得します
   *
   * @return メールテンプレート番号
   */
  public Long getMailTemplateNo() {
    return this.mailTemplateNo;
  }

  /**
   * メールコンテントタイプを取得します
   *
   * @return メールコンテントタイプ
   */
  public Long getMailContentType() {
    return this.mailContentType;
  }

  /**
   * FROMアドレスを取得します
   *
   * @return FROMアドレス
   */
  public String getFromAddress() {
    return this.fromAddress;
  }

  /**
   * BCCアドレスを取得します
   *
   * @return BCCアドレス
   */
  public String getBccAddress() {
    return this.bccAddress;
  }

  /**
   * 差出人名を取得します
   *
   * @return 差出人名
   */
  public String getMailSenderName() {
    return this.mailSenderName;
  }

  /**
   * メール件名を取得します
   *
   * @return メール件名
   */
  public String getMailSubject() {
    return this.mailSubject;
  }

  /**
   * メール構成を取得します
   *
   * @return メール構成
   */
  public String getMailComposition() {
    return this.mailComposition;
  }

  /**
   * 表示順を取得します
   *
   * @return 表示順
   */
  public Long getDisplayOrder() {
    return this.displayOrder;
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
   * ショップコードを設定します
   *
   * @param  val ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * メールタイプを設定します
   *
   * @param  val メールタイプ
   */
  public void setMailType(String val) {
    this.mailType = val;
  }

  /**
   * メールテンプレート番号を設定します
   *
   * @param  val メールテンプレート番号
   */
  public void setMailTemplateNo(Long val) {
    this.mailTemplateNo = val;
  }

  /**
   * メールコンテントタイプを設定します
   *
   * @param  val メールコンテントタイプ
   */
  public void setMailContentType(Long val) {
    this.mailContentType = val;
  }

  /**
   * FROMアドレスを設定します
   *
   * @param  val FROMアドレス
   */
  public void setFromAddress(String val) {
    this.fromAddress = val;
  }

  /**
   * BCCアドレスを設定します
   *
   * @param  val BCCアドレス
   */
  public void setBccAddress(String val) {
    this.bccAddress = val;
  }

  /**
   * 差出人名を設定します
   *
   * @param  val 差出人名
   */
  public void setMailSenderName(String val) {
    this.mailSenderName = val;
  }

  /**
   * メール件名を設定します
   *
   * @param  val メール件名
   */
  public void setMailSubject(String val) {
    this.mailSubject = val;
  }

  /**
   * メール構成を設定します
   *
   * @param  val メール構成
   */
  public void setMailComposition(String val) {
    this.mailComposition = val;
  }

  /**
   * 表示順を設定します
   *
   * @param  val 表示順
   */
  public void setDisplayOrder(Long val) {
    this.displayOrder = val;
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
