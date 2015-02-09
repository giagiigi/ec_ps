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
 * 「アクセスログ(ACCESS_LOG)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class AccessLog implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** アクセスログID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "アクセスログID", order = 1)
  private Long accessLogId;

  /** アクセス日 */
  @Required
  @Metadata(name = "アクセス日", order = 2)
  private Date accessDate;

  /** アクセス時間 */
  @Required
  @Length(2)
  @Metadata(name = "アクセス時間", order = 3)
  private Long accessTime;

  /** クライアントグループ */
  @Required
  @Length(2)
  @Metadata(name = "クライアントグループ", order = 4)
  private String clientGroup;

  /** ページビュー件数 */
  @Length(8)
  @Metadata(name = "ページビュー件数", order = 5)
  private Long pageViewCount;

  /** ビジター数 */
  @Length(8)
  @Metadata(name = "ビジター数", order = 6)
  private Long visitorCount;

  /** 購入者数 */
  @Length(8)
  @Metadata(name = "購入者数", order = 7)
  private Long purchaserCount;

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
   * アクセスログIDを取得します
   *
   * @return アクセスログID
   */
  public Long getAccessLogId() {
    return this.accessLogId;
  }

  /**
   * アクセス日を取得します
   *
   * @return アクセス日
   */
  public Date getAccessDate() {
    return DateUtil.immutableCopy(this.accessDate);
  }

  /**
   * アクセス時間を取得します
   *
   * @return アクセス時間
   */
  public Long getAccessTime() {
    return this.accessTime;
  }

  /**
   * クライアントグループを取得します
   *
   * @return クライアントグループ
   */
  public String getClientGroup() {
    return this.clientGroup;
  }

  /**
   * ページビュー件数を取得します
   *
   * @return ページビュー件数
   */
  public Long getPageViewCount() {
    return this.pageViewCount;
  }

  /**
   * ビジター数を取得します
   *
   * @return ビジター数
   */
  public Long getVisitorCount() {
    return this.visitorCount;
  }

  /**
   * 購入者数を取得します
   *
   * @return 購入者数
   */
  public Long getPurchaserCount() {
    return this.purchaserCount;
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
   * アクセスログIDを設定します
   *
   * @param  val アクセスログID
   */
  public void setAccessLogId(Long val) {
    this.accessLogId = val;
  }

  /**
   * アクセス日を設定します
   *
   * @param  val アクセス日
   */
  public void setAccessDate(Date val) {
    this.accessDate = DateUtil.immutableCopy(val);
  }

  /**
   * アクセス時間を設定します
   *
   * @param  val アクセス時間
   */
  public void setAccessTime(Long val) {
    this.accessTime = val;
  }

  /**
   * クライアントグループを設定します
   *
   * @param  val クライアントグループ
   */
  public void setClientGroup(String val) {
    this.clientGroup = val;
  }

  /**
   * ページビュー件数を設定します
   *
   * @param  val ページビュー件数
   */
  public void setPageViewCount(Long val) {
    this.pageViewCount = val;
  }

  /**
   * ビジター数を設定します
   *
   * @param  val ビジター数
   */
  public void setVisitorCount(Long val) {
    this.visitorCount = val;
  }

  /**
   * 購入者数を設定します
   *
   * @param  val 購入者数
   */
  public void setPurchaserCount(Long val) {
    this.purchaserCount = val;
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
