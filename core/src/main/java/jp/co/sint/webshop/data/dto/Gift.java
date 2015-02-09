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
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「ギフト(GIFT)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class Gift implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** ギフトコード */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ギフトコード", order = 2)
  private String giftCode;

  /** ギフト名称 */
  @Required
  @Length(40)
  @Metadata(name = "ギフト名称", order = 3)
  private String giftName;

  /** ギフト説明 */
  @Length(200)
  @Metadata(name = "ギフト説明", order = 4)
  private String giftDescription;

  /** ギフト価格 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "ギフト価格", order = 5)
  private BigDecimal giftPrice;

  /** 表示順 */
  @Length(8)
  @Metadata(name = "表示順", order = 6)
  private Long displayOrder;

  /** ギフト消費税区分 */
  @Required
  @Length(1)
  @Domain(TaxType.class)
  @Metadata(name = "ギフト消費税区分", order = 7)
  private Long giftTaxType;

  /** 表示フラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "表示フラグ", order = 8)
  private Long displayFlg;

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
   * ショップコードを取得します
   *
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * ギフトコードを取得します
   *
   * @return ギフトコード
   */
  public String getGiftCode() {
    return this.giftCode;
  }

  /**
   * ギフト名称を取得します
   *
   * @return ギフト名称
   */
  public String getGiftName() {
    return this.giftName;
  }

  /**
   * ギフト説明を取得します
   *
   * @return ギフト説明
   */
  public String getGiftDescription() {
    return this.giftDescription;
  }

  /**
   * ギフト価格を取得します
   *
   * @return ギフト価格
   */
  public BigDecimal getGiftPrice() {
    return this.giftPrice;
  }

  /**
   * 表示順を取得します
   *
   * @return 表示順
   */
  public Long getDisplayOrder() {
    return this.displayOrder;
  }

  /**
   * ギフト消費税区分を取得します
   *
   * @return ギフト消費税区分
   */
  public Long getGiftTaxType() {
    return this.giftTaxType;
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
   * ギフトコードを設定します
   *
   * @param  val ギフトコード
   */
  public void setGiftCode(String val) {
    this.giftCode = val;
  }

  /**
   * ギフト名称を設定します
   *
   * @param  val ギフト名称
   */
  public void setGiftName(String val) {
    this.giftName = val;
  }

  /**
   * ギフト説明を設定します
   *
   * @param  val ギフト説明
   */
  public void setGiftDescription(String val) {
    this.giftDescription = val;
  }

  /**
   * ギフト価格を設定します
   *
   * @param  val ギフト価格
   */
  public void setGiftPrice(BigDecimal val) {
    this.giftPrice = val;
  }

  /**
   * 表示順を設定します
   *
   * @param  val 表示順
   */
  public void setDisplayOrder(Long val) {
    this.displayOrder = val;
  }

  /**
   * ギフト消費税区分を設定します
   *
   * @param  val ギフト消費税区分
   */
  public void setGiftTaxType(Long val) {
    this.giftTaxType = val;
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
