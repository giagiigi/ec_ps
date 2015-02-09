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
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「支払手数料(COMMISSION)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class Commission implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** 支払方法番号 */
  @PrimaryKey(2)
  @Required
  @Length(8)
  @AlphaNum2
  @Metadata(name = "支払方法番号", order = 2)
  private Long paymentMethodNo;

  /** 支払金額閾値 */
  @PrimaryKey(3)
  @Required
  @Length(11)
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "支払金額閾値", order = 3)
  private BigDecimal paymentPriceThreshold;

  /** 支払手数料 */
  @Required
  @Length(11)
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "支払手数料", order = 4)
  private BigDecimal paymentCommission;

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
   * ショップコードを取得します
   *
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * 支払方法番号を取得します
   *
   * @return 支払方法番号
   */
  public Long getPaymentMethodNo() {
    return this.paymentMethodNo;
  }

  /**
   * 支払金額閾値を取得します
   *
   * @return 支払金額閾値
   */
  public BigDecimal getPaymentPriceThreshold() {
    return this.paymentPriceThreshold;
  }

  /**
   * 支払手数料を取得します
   *
   * @return 支払手数料
   */
  public BigDecimal getPaymentCommission() {
    return this.paymentCommission;
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
   * 支払方法番号を設定します
   *
   * @param  val 支払方法番号
   */
  public void setPaymentMethodNo(Long val) {
    this.paymentMethodNo = val;
  }

  /**
   * 支払金額閾値を設定します
   *
   * @param  val 支払金額閾値
   */
  public void setPaymentPriceThreshold(BigDecimal val) {
    this.paymentPriceThreshold = val;
  }

  /**
   * 支払手数料を設定します
   *
   * @param  val 支払手数料
   */
  public void setPaymentCommission(BigDecimal val) {
    this.paymentCommission = val;
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
