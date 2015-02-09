package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.SmsSendStatus;
import jp.co.sint.webshop.data.domain.SmsType;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「個別配信メールキュー(RESPECTIVE_SMSQUEUE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class RespectiveSmsqueue implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** メールキューID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "メールキューID", order = 1)
  private Long smsQueueId;

  /** メールタイプ */
  @Required
  @Length(2)
  @Domain(SmsType.class)
  @Metadata(name = "メールタイプ", order = 2)
  private String smsType;

  /** TOアドレス */
  @Length(256)
  @Metadata(name = "SMS手机号码", order = 7)
  private String toMobile;

  /** メール本文 */
  @Metadata(name = " SMS本文", order = 9)
  private String smsText;

  /** メール送信ステータス */
  @Required
  @Length(1)
  @Domain(SmsSendStatus.class)
  @Metadata(name = "SMS发送状态", order = 10)
  private Long smsSendStatus;

  /** メール送信日時 */
  @Metadata(name = "SMS发送日期", order = 11)
  private Date smsSentDatetime;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "数据行ID", order = 12)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "制作用户", order = 13)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "制作日期", order = 14)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新用户", order = 15)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日期", order = 16)
  private Date updatedDatetime;

  /**
   * メールキューIDを取得します
   *
   * @return メールキューID
   */
  public Long getSmsQueueId() {
    return this.smsQueueId;
  }

  /**
   * メールタイプを取得します
   *
   * @return メールタイプ
   */
  public String getSmsType() {
    return this.smsType;
  }

  /**
   * メール本文を取得します
   *
   * @return メール本文
   */
  public String getSmsText() {
    return this.smsText;
  }

  /**
   * メール送信ステータスを取得します
   *
   * @return メール送信ステータス
   */
  public Long getSmsSendStatus() {
    return this.smsSendStatus;
  }

  /**
   * メール送信日時を取得します
   *
   * @return メール送信日時
   */
  public Date getSmsSentDatetime() {
    return DateUtil.immutableCopy(this.smsSentDatetime);
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
  public void setSmsQueueId(Long val) {
    this.smsQueueId = val;
  }

  /**
   * メールタイプを設定します
   *
   * @param  val メールタイプ
   */
  public void setSmsType(String val) {
    this.smsType = val;
  }

  /**
   * メール本文を設定します
   *
   * @param  val メール本文
   */
  public void setSmsText(String val) {
    this.smsText = val;
  }

  /**
   * メール送信ステータスを設定します
   *
   * @param  val メール送信ステータス
   */
  public void setSmsSendStatus(Long val) {
    this.smsSendStatus = val;
  }

  /**
   * メール送信日時を設定します
   *
   * @param  val メール送信日時
   */
  public void setSmsSentDatetime(Date val) {
    this.smsSentDatetime = DateUtil.immutableCopy(val);
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

  /**
   * toMobileを取得します。
   *
   * @return toMobile toMobile
   */
  public String getToMobile() {
    return toMobile;
  }
  
  /**
   * toMobileを設定します。
   *
   * @param toMobile 
   *          toMobile
   */
  public void setToMobile(String toMobile) {
    this.toMobile = toMobile;
  }

}
