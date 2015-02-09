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
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「受注メール送信履歴(ORDER_MAIL_HISTORY)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class OrderSmsHistory implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 受注メール送信履歴ID */
  @PrimaryKey(1)
  @Length(38)
  @Metadata(name = "受注メール送信履歴ID", order = 1)
  private Long orderSmsHistoryId;

  /** 受注番号 */
  @Required
  @Length(16)
  //@Digit
  @Metadata(name = "受注番号", order = 2)
  private String orderNo;

  /** 出荷番号 */
  @Length(16)
  @Digit
  @Metadata(name = "出荷番号", order = 3)
  private String shippingNo;

  /** メールキューID */
  @Required
  @Length(38)
  @Metadata(name = "メールキューID", order = 4)
  private Long smsQueueId;

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
   * 受注メール送信履歴IDを取得します
   *
   * @return 受注メール送信履歴ID
   */
  public Long getOrderSmsHistoryId() {
    return this.orderSmsHistoryId;
  }

  /**
   * 受注番号を取得します
   *
   * @return 受注番号
   */
  public String getOrderNo() {
    return this.orderNo;
  }

  /**
   * 出荷番号を取得します
   *
   * @return 出荷番号
   */
  public String getShippingNo() {
    return this.shippingNo;
  }

  /**
   * メールキューIDを取得します
   *
   * @return メールキューID
   */
  public Long getSmsQueueId() {
    return this.smsQueueId;
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
   * 受注メール送信履歴IDを設定します
   *
   * @param  val 受注メール送信履歴ID
   */
  public void setOrderSmsHistoryId(Long val) {
    this.orderSmsHistoryId = val;
  }

  /**
   * 受注番号を設定します
   *
   * @param  val 受注番号
   */
  public void setOrderNo(String val) {
    this.orderNo = val;
  }

  /**
   * 出荷番号を設定します
   *
   * @param  val 出荷番号
   */
  public void setShippingNo(String val) {
    this.shippingNo = val;
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
