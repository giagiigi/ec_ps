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
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「RFM(RFM)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class Rfm implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 受注番号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "受注番号", order = 1)
  private String orderNo;

  /** 購入商品数 */
  @Required
  @Length(8)
  @Metadata(name = "購入商品数", order = 2)
  private Long purchasingAmount;

  /** 受注日 */
  @Required
  @Metadata(name = "受注日", order = 3)
  private Date orderDate;

  /** 合計購入金額 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "合計購入金額", order = 4)
  private BigDecimal totalPurchasedAmount;

  /** 顧客コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顧客コード", order = 5)
  private String customerCode;

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
   * 受注番号を取得します
   *
   * @return 受注番号
   */
  public String getOrderNo() {
    return this.orderNo;
  }

  /**
   * 購入商品数を取得します
   *
   * @return 購入商品数
   */
  public Long getPurchasingAmount() {
    return this.purchasingAmount;
  }

  /**
   * 受注日を取得します
   *
   * @return 受注日
   */
  public Date getOrderDate() {
    return DateUtil.immutableCopy(this.orderDate);
  }

  /**
   * 合計購入金額を取得します
   *
   * @return 合計購入金額
   */
  public BigDecimal getTotalPurchasedAmount() {
    return this.totalPurchasedAmount;
  }

  /**
   * 顧客コードを取得します
   *
   * @return 顧客コード
   */
  public String getCustomerCode() {
    return this.customerCode;
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
   * 受注番号を設定します
   *
   * @param  val 受注番号
   */
  public void setOrderNo(String val) {
    this.orderNo = val;
  }

  /**
   * 購入商品数を設定します
   *
   * @param  val 購入商品数
   */
  public void setPurchasingAmount(Long val) {
    this.purchasingAmount = val;
  }

  /**
   * 受注日を設定します
   *
   * @param  val 受注日
   */
  public void setOrderDate(Date val) {
    this.orderDate = DateUtil.immutableCopy(val);
  }

  /**
   * 合計購入金額を設定します
   *
   * @param  val 合計購入金額
   */
  public void setTotalPurchasedAmount(BigDecimal val) {
    this.totalPurchasedAmount = val;
  }

  /**
   * 顧客コードを設定します
   *
   * @param  val 顧客コード
   */
  public void setCustomerCode(String val) {
    this.customerCode = val;
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
