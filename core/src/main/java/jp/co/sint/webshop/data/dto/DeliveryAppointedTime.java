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
 * 「配送指定時間(DELIVERY_APPOINTED_TIME)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class DeliveryAppointedTime implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** 配送種別番号 */
  @PrimaryKey(2)
  @Required
  @Length(8)
  @AlphaNum2
  @Metadata(name = "配送種別番号", order = 2)
  private Long deliveryTypeNo;

  /** 配送指定時間コード */
  @PrimaryKey(3)
  @Length(16)
  @AlphaNum2
  @Metadata(name = "配送指定時間コード", order = 3)
  private String deliveryAppointedTimeCode;

  /** 配送指定時間名称 */
  @Required
  @Length(15)
  @Metadata(name = "配送指定時間名称", order = 4)
  private String deliveryAppointedTimeName;

  /** 配送指定時間開始 */
  @Length(2)
  @Metadata(name = "配送指定時間開始", order = 5)
  private Long deliveryAppointedTimeStart;

  /** 配送指定時間終了 */
  @Length(2)
  @Metadata(name = "配送指定時間終了", order = 6)
  private Long deliveryAppointedTimeEnd;

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
   * ショップコードを取得します
   *
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * 配送種別番号を取得します
   *
   * @return 配送種別番号
   */
  public Long getDeliveryTypeNo() {
    return this.deliveryTypeNo;
  }

  /**
   * 配送指定時間コードを取得します
   *
   * @return 配送指定時間コード
   */
  public String getDeliveryAppointedTimeCode() {
    return this.deliveryAppointedTimeCode;
  }

  /**
   * 配送指定時間名称を取得します
   *
   * @return 配送指定時間名称
   */
  public String getDeliveryAppointedTimeName() {
    return this.deliveryAppointedTimeName;
  }

  /**
   * 配送指定時間開始を取得します
   *
   * @return 配送指定時間開始
   */
  public Long getDeliveryAppointedTimeStart() {
    return this.deliveryAppointedTimeStart;
  }

  /**
   * 配送指定時間終了を取得します
   *
   * @return 配送指定時間終了
   */
  public Long getDeliveryAppointedTimeEnd() {
    return this.deliveryAppointedTimeEnd;
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
   * 配送種別番号を設定します
   *
   * @param  val 配送種別番号
   */
  public void setDeliveryTypeNo(Long val) {
    this.deliveryTypeNo = val;
  }

  /**
   * 配送指定時間コードを設定します
   *
   * @param  val 配送指定時間コード
   */
  public void setDeliveryAppointedTimeCode(String val) {
    this.deliveryAppointedTimeCode = val;
  }

  /**
   * 配送指定時間名称を設定します
   *
   * @param  val 配送指定時間名称
   */
  public void setDeliveryAppointedTimeName(String val) {
    this.deliveryAppointedTimeName = val;
  }

  /**
   * 配送指定時間開始を設定します
   *
   * @param  val 配送指定時間開始
   */
  public void setDeliveryAppointedTimeStart(Long val) {
    this.deliveryAppointedTimeStart = val;
  }

  /**
   * 配送指定時間終了を設定します
   *
   * @param  val 配送指定時間終了
   */
  public void setDeliveryAppointedTimeEnd(Long val) {
    this.deliveryAppointedTimeEnd = val;
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
