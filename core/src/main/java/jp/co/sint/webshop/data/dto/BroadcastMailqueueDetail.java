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
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.MailSendStatus;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「同報配信メールキュー明細(BROADCAST_MAILQUEUE_DETAIL)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class BroadcastMailqueueDetail implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** メールキューID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "メールキューID", order = 1)
  private Long mailQueueId;

  /** 顧客コード */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード", order = 2)
  private String customerCode;

  /** TOアドレス */
  @Required
  @Length(256)
  @Email
  @Metadata(name = "TOアドレス", order = 3)
  private String toAddress;

  /** BCCアドレス */
  @Length(256)
  @Email
  @Metadata(name = "BCCアドレス", order = 4)
  private String bccAddress;

  /** メール送信日時 */
  @Metadata(name = "メール送信日時", order = 5)
  private Date mailSentDatetime;

  /** メール送信ステータス */
  @Required
  @Length(1)
  @Domain(MailSendStatus.class)
  @Metadata(name = "メール送信ステータス", order = 6)
  private Long mailSendStatus;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 7)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 8)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 9)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 10)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 11)
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
   * 顧客コードを取得します
   *
   * @return 顧客コード
   */
  public String getCustomerCode() {
    return this.customerCode;
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
   * メール送信日時を取得します
   *
   * @return メール送信日時
   */
  public Date getMailSentDatetime() {
    return DateUtil.immutableCopy(this.mailSentDatetime);
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
   * 顧客コードを設定します
   *
   * @param  val 顧客コード
   */
  public void setCustomerCode(String val) {
    this.customerCode = val;
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
   * メール送信日時を設定します
   *
   * @param  val メール送信日時
   */
  public void setMailSentDatetime(Date val) {
    this.mailSentDatetime = DateUtil.immutableCopy(val);
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
