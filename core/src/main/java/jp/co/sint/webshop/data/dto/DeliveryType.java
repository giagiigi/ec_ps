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
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.data.domain.DeliverySpecificationType;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「配送種別(DELIVERY_TYPE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class DeliveryType implements Serializable, WebshopEntity {

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

  /** 配送種別名称 */
  @Required
  @Length(40)
  @Metadata(name = "配送種別名称", order = 3)
  private String deliveryTypeName;

  /** 配送詳細区分 */
  @Length(1)
  @Domain(DeliverySpecificationType.class)
  @Metadata(name = "配送詳細区分", order = 4)
  private Long deliverySpecificationType;

  /** 送料消費税区分 */
  @Required
  @Length(1)
  @Domain(TaxType.class)
  @Metadata(name = "送料消費税区分", order = 5)
  private Long shippingChargeTaxType;

  /** 宅配業者URL */
  @Length(128)
  @Url
  @Metadata(name = "宅配業者URL", order = 6)
  private String parcelUrl;

  /** 送料無料フラグ */
  @Length(1)
  @Bool
  @Metadata(name = "送料無料フラグ", order = 7)
  private Long shippingChargeFreeFlg;

  /** 送料無料閾値 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "送料無料閾値", order = 8)
  private BigDecimal shippingChargeFreeThreshold;

  /** 送料課金フラグ */
  @Length(1)
  @Bool
  @Metadata(name = "送料課金フラグ", order = 9)
  private Long shippingChargeFlg;

  /** 送料課金閾値 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "送料課金閾値", order = 10)
  private BigDecimal shippingChargeThreshold;

  /** 表示フラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "表示フラグ", order = 11)
  private Long displayFlg;

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
   * 配送種別名称を取得します
   *
   * @return 配送種別名称
   */
  public String getDeliveryTypeName() {
    return this.deliveryTypeName;
  }

  /**
   * 配送詳細区分を取得します
   *
   * @return 配送詳細区分
   */
  public Long getDeliverySpecificationType() {
    return this.deliverySpecificationType;
  }

  /**
   * 送料消費税区分を取得します
   *
   * @return 送料消費税区分
   */
  public Long getShippingChargeTaxType() {
    return this.shippingChargeTaxType;
  }

  /**
   * 宅配業者URLを取得します
   *
   * @return 宅配業者URL
   */
  public String getParcelUrl() {
    return this.parcelUrl;
  }

  /**
   * 送料無料フラグを取得します
   *
   * @return 送料無料フラグ
   */
  public Long getShippingChargeFreeFlg() {
    return this.shippingChargeFreeFlg;
  }

  /**
   * 送料無料閾値を取得します
   *
   * @return 送料無料閾値
   */
  public BigDecimal getShippingChargeFreeThreshold() {
    return this.shippingChargeFreeThreshold;
  }

  /**
   * 送料課金フラグを取得します
   *
   * @return 送料課金フラグ
   */
  public Long getShippingChargeFlg() {
    return this.shippingChargeFlg;
  }

  /**
   * 送料課金閾値を取得します
   *
   * @return 送料課金閾値
   */
  public BigDecimal getShippingChargeThreshold() {
    return this.shippingChargeThreshold;
  }

  /**
   * 表示フラグを取得します
   *
   * @return 表示フラグ
   */
  public Long getDisplayFlg() {
    return this.displayFlg;
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
   * 配送種別名称を設定します
   *
   * @param  val 配送種別名称
   */
  public void setDeliveryTypeName(String val) {
    this.deliveryTypeName = val;
  }

  /**
   * 配送詳細区分を設定します
   *
   * @param  val 配送詳細区分
   */
  public void setDeliverySpecificationType(Long val) {
    this.deliverySpecificationType = val;
  }

  /**
   * 送料消費税区分を設定します
   *
   * @param  val 送料消費税区分
   */
  public void setShippingChargeTaxType(Long val) {
    this.shippingChargeTaxType = val;
  }

  /**
   * 宅配業者URLを設定します
   *
   * @param  val 宅配業者URL
   */
  public void setParcelUrl(String val) {
    this.parcelUrl = val;
  }

  /**
   * 送料無料フラグを設定します
   *
   * @param  val 送料無料フラグ
   */
  public void setShippingChargeFreeFlg(Long val) {
    this.shippingChargeFreeFlg = val;
  }

  /**
   * 送料無料閾値を設定します
   *
   * @param  val 送料無料閾値
   */
  public void setShippingChargeFreeThreshold(BigDecimal val) {
    this.shippingChargeFreeThreshold = val;
  }

  /**
   * 送料課金フラグを設定します
   *
   * @param  val 送料課金フラグ
   */
  public void setShippingChargeFlg(Long val) {
    this.shippingChargeFlg = val;
  }

  /**
   * 送料課金閾値を設定します
   *
   * @param  val 送料課金閾値
   */
  public void setShippingChargeThreshold(BigDecimal val) {
    this.shippingChargeThreshold = val;
  }

  /**
   * 表示フラグを設定します
   *
   * @param  val 表示フラグ
   */
  public void setDisplayFlg(Long val) {
    this.displayFlg = val;
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
