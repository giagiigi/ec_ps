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
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.MailContentType;
import jp.co.sint.webshop.data.domain.MailSendStatus;
import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「個別配信メールキュー(RESPECTIVE_MAILQUEUE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class RespectiveMailqueue implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** メールキューID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "メールキューID", order = 1)
  private Long mailQueueId;

  /** メールタイプ */
  @Required
  @Length(2)
  @Domain(MailType.class)
  @Metadata(name = "メールタイプ", order = 2)
  private String mailType;

  /** メールコンテントタイプ */
  @Required
  @Length(1)
  @Domain(MailContentType.class)
  @Metadata(name = "メールコンテントタイプ", order = 3)
  private Long mailContentType;

  /** メール件名 */
  @Required
  @Length(100)
  @Metadata(name = "メール件名", order = 4)
  private String mailSubject;

  /** 差出人名 */
  @Length(50)
  @Metadata(name = "差出人名", order = 5)
  private String mailSenderName;

  /** FROMアドレス */
  @Required
  @Length(256)
  @Email
  @Metadata(name = "FROMアドレス", order = 6)
  private String fromAddress;

  /** TOアドレス */
  @Length(256)
  @Email
  @Metadata(name = "TOアドレス", order = 7)
  private String toAddress;

  /** BCCアドレス */
  @Length(256)
  @Email
  @Metadata(name = "BCCアドレス", order = 8)
  private String bccAddress;

  /** メール本文 */
  @Metadata(name = "メール本文", order = 9)
  private String mailText;

  /** メール送信ステータス */
  @Required
  @Length(1)
  @Domain(MailSendStatus.class)
  @Metadata(name = "メール送信ステータス", order = 10)
  private Long mailSendStatus;

  /** メール送信日時 */
  @Metadata(name = "メール送信日時", order = 11)
  private Date mailSentDatetime;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 12)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 13)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 14)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 15)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 16)
  private Date updatedDatetime;

  /**
   * メールキューIDを取得します
   *
   * @return メールキューID
   */
  public Long getMailQueueId() {
    return this.mailQueueId;
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
   * メールコンテントタイプを取得します
   *
   * @return メールコンテントタイプ
   */
  public Long getMailContentType() {
    return this.mailContentType;
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
   * 差出人名を取得します
   *
   * @return 差出人名
   */
  public String getMailSenderName() {
    return this.mailSenderName;
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
   * TOアドレスを取得します
   *
   * @return TOアドレス
   */
  public String getToAddress() {
    return this.toAddress;
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
   * メール本文を取得します
   *
   * @return メール本文
   */
  public String getMailText() {
    return this.mailText;
  }

  /**
   * メール送信ステータスを取得します
   *
   * @return メール送信ステータス
   */
  public Long getMailSendStatus() {
    return this.mailSendStatus;
  }

  /**
   * メール送信日時を取得します
   *
   * @return メール送信日時
   */
  public Date getMailSentDatetime() {
    return DateUtil.immutableCopy(this.mailSentDatetime);
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
   * メールキューIDを設定します
   *
   * @param  val メールキューID
   */
  public void setMailQueueId(Long val) {
    this.mailQueueId = val;
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
   * メールコンテントタイプを設定します
   *
   * @param  val メールコンテントタイプ
   */
  public void setMailContentType(Long val) {
    this.mailContentType = val;
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
   * 差出人名を設定します
   *
   * @param  val 差出人名
   */
  public void setMailSenderName(String val) {
    this.mailSenderName = val;
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
   * TOアドレスを設定します
   *
   * @param  val TOアドレス
   */
  public void setToAddress(String val) {
    this.toAddress = val;
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
   * メール本文を設定します
   *
   * @param  val メール本文
   */
  public void setMailText(String val) {
    this.mailText = val;
  }

  /**
   * メール送信ステータスを設定します
   *
   * @param  val メール送信ステータス
   */
  public void setMailSendStatus(Long val) {
    this.mailSendStatus = val;
  }

  /**
   * メール送信日時を設定します
   *
   * @param  val メール送信日時
   */
  public void setMailSentDatetime(Date val) {
    this.mailSentDatetime = DateUtil.immutableCopy(val);
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
