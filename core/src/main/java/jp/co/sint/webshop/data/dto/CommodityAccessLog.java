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
 * 「商品別アクセスログ(COMMODITY_ACCESS_LOG)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class CommodityAccessLog implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 商品別アクセスログID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "商品別アクセスログID", order = 1)
  private Long commodityAccessLogId;

  /** アクセス日 */
  @Required
  @Metadata(name = "アクセス日", order = 2)
  private Date accessDate;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 3)
  private String shopCode;

  /** 商品コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 4)
  private String commodityCode;

  /** クライアントグループ */
  @Required
  @Length(2)
  @Metadata(name = "クライアントグループ", order = 5)
  private String clientGroup;

  /** ショップ名称 */
  @Required
  @Length(30)
  @Metadata(name = "ショップ名称", order = 6)
  private String shopName;

  /** 商品名称 */
  @Required
  @Length(50)
  @Metadata(name = "商品名称", order = 7)
  private String commodityName;

  /** アクセス件数 */
  @Required
  @Length(8)
  @Metadata(name = "アクセス件数", order = 8)
  private Long accessCount;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 9)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 10)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 11)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 12)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 13)
  private Date updatedDatetime;

  /**
   * 商品別アクセスログIDを取得します
   *
   * @return 商品別アクセスログID
   */
  public Long getCommodityAccessLogId() {
    return this.commodityAccessLogId;
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
   * ショップコードを取得します
   *
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * 商品コードを取得します
   *
   * @return 商品コード
   */
  public String getCommodityCode() {
    return this.commodityCode;
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
   * ショップ名称を取得します
   *
   * @return ショップ名称
   */
  public String getShopName() {
    return this.shopName;
  }

  /**
   * 商品名称を取得します
   *
   * @return 商品名称
   */
  public String getCommodityName() {
    return this.commodityName;
  }

  /**
   * アクセス件数を取得します
   *
   * @return アクセス件数
   */
  public Long getAccessCount() {
    return this.accessCount;
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
   * 商品別アクセスログIDを設定します
   *
   * @param  val 商品別アクセスログID
   */
  public void setCommodityAccessLogId(Long val) {
    this.commodityAccessLogId = val;
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
   * ショップコードを設定します
   *
   * @param  val ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * 商品コードを設定します
   *
   * @param  val 商品コード
   */
  public void setCommodityCode(String val) {
    this.commodityCode = val;
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
   * ショップ名称を設定します
   *
   * @param  val ショップ名称
   */
  public void setShopName(String val) {
    this.shopName = val;
  }

  /**
   * 商品名称を設定します
   *
   * @param  val 商品名称
   */
  public void setCommodityName(String val) {
    this.commodityName = val;
  }

  /**
   * アクセス件数を設定します
   *
   * @param  val アクセス件数
   */
  public void setAccessCount(Long val) {
    this.accessCount = val;
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
