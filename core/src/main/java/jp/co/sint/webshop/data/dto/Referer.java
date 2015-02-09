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
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「リファラー(REFERER)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class Referer implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** リファラーID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "リファラーID", order = 1)
  private Long refererId;

  /** アクセス日 */
  @Required
  @Metadata(name = "アクセス日", order = 2)
  private Date accessDate;

  /** クライアントグループ */
  @Required
  @Length(2)
  @Metadata(name = "クライアントグループ", order = 3)
  private String clientGroup;

  /** リファラーURL */
  @Required
  @Length(256)
  @Url
  @Metadata(name = "リファラーURL", order = 4)
  private String refererUrl;

  /** リファラー集計件数 */
  @Required
  @Length(8)
  @Metadata(name = "リファラー集計件数", order = 5)
  private Long refererCount;

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
   * リファラーIDを取得します
   *
   * @return リファラーID
   */
  public Long getRefererId() {
    return this.refererId;
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
   * クライアントグループを取得します
   *
   * @return クライアントグループ
   */
  public String getClientGroup() {
    return this.clientGroup;
  }

  /**
   * リファラーURLを取得します
   *
   * @return リファラーURL
   */
  public String getRefererUrl() {
    return this.refererUrl;
  }

  /**
   * リファラー集計件数を取得します
   *
   * @return リファラー集計件数
   */
  public Long getRefererCount() {
    return this.refererCount;
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
   * リファラーIDを設定します
   *
   * @param  val リファラーID
   */
  public void setRefererId(Long val) {
    this.refererId = val;
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
   * クライアントグループを設定します
   *
   * @param  val クライアントグループ
   */
  public void setClientGroup(String val) {
    this.clientGroup = val;
  }

  /**
   * リファラーURLを設定します
   *
   * @param  val リファラーURL
   */
  public void setRefererUrl(String val) {
    this.refererUrl = val;
  }

  /**
   * リファラー集計件数を設定します
   *
   * @param  val リファラー集計件数
   */
  public void setRefererCount(Long val) {
    this.refererCount = val;
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
