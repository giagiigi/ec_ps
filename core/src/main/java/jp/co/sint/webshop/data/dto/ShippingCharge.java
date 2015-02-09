//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「送料(SHIPPING_CHARGE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class ShippingCharge implements Serializable, WebshopEntity {

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

  /** 地域ブロックID */
  @PrimaryKey(3)
  @Required
  @Length(38)
  @AlphaNum2
  @Metadata(name = "地域ブロックID", order = 3)
  private Long regionBlockId;

  /** リードタイム */
  @Required
  @Length(2)
  @Metadata(name = "リードタイム", order = 4)
  private Long leadTime;

  /** 送料 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "送料", order = 5)
  private BigDecimal shippingCharge;

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
   * 地域ブロックIDを取得します
   *
   * @return 地域ブロックID
   */
  public Long getRegionBlockId() {
    return this.regionBlockId;
  }

  /**
   * リードタイムを取得します
   *
   * @return リードタイム
   */
  public Long getLeadTime() {
    return this.leadTime;
  }

  /**
   * 送料を取得します
   *
   * @return 送料
   */
  public BigDecimal getShippingCharge() {
    return this.shippingCharge;
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
   * 地域ブロックIDを設定します
   *
   * @param  val 地域ブロックID
   */
  public void setRegionBlockId(Long val) {
    this.regionBlockId = val;
  }

  /**
   * リードタイムを設定します
   *
   * @param  val リードタイム
   */
  public void setLeadTime(Long val) {
    this.leadTime = val;
  }

  /**
   * 送料を設定します
   *
   * @param  val 送料
   */
  public void setShippingCharge(BigDecimal val) {
    this.shippingCharge = val;
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
