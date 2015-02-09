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
 * 「ショップ別売上集計(SALES_AMOUNT_BY_SHOP)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class SalesAmountByShop implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップ別売上集計ID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "ショップ別売上集計ID", order = 1)
  private Long salesAmountByShopId;

  /** 集計日 */
  @Required
  @Metadata(name = "集計日", order = 2)
  private Date countedDate;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 3)
  private String shopCode;

  /** クライアントグループ */
  @Required
  @Length(2)
  @Metadata(name = "クライアントグループ", order = 4)
  private String clientGroup;

  /** 支払方法番号 */
  @Required
  @Length(8)
  @AlphaNum2
  @Metadata(name = "支払方法番号", order = 5)
  private Long paymentMethodNo;

  /** ショップ名称 */
  @Length(30)
  @Metadata(name = "ショップ名称", order = 6)
  private String shopName;

  /** 商品金額累計 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "商品金額累計", order = 7)
  private BigDecimal totalSalesPrice;

  /** 商品消費税額累計 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "商品消費税額累計", order = 8)
  private BigDecimal totalSalesPriceTax;

  /** ギフト金額累計 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "ギフト金額累計", order = 9)
  private BigDecimal totalGiftPrice;

  /** ギフト消費税額累計 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "ギフト消費税額累計", order = 10)
  private BigDecimal totalGiftTax;

  /** 送料累計 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "送料累計", order = 11)
  private BigDecimal totalShippingCharge;

  /** 送料消費税額累計 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "送料消費税額累計", order = 12)
  private BigDecimal totalShippingChargeTax;

  /** 値引額累計 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "値引額累計", order = 13)
  private BigDecimal totalDiscountAmount;

  /** 返金額累計 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "返金額累計", order = 14)
  private BigDecimal totalRefund;

  /** 返品損金額累計 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "返品損金額累計", order = 15)
  private BigDecimal totalReturnItemLossMoney;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 16)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 17)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 18)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 19)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 20)
  private Date updatedDatetime;

  /**
   * ショップ別売上集計IDを取得します
   *
   * @return ショップ別売上集計ID
   */
  public Long getSalesAmountByShopId() {
    return this.salesAmountByShopId;
  }

  /**
   * 集計日を取得します
   *
   * @return 集計日
   */
  public Date getCountedDate() {
    return DateUtil.immutableCopy(this.countedDate);
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
   * クライアントグループを取得します
   *
   * @return クライアントグループ
   */
  public String getClientGroup() {
    return this.clientGroup;
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
   * ショップ名称を取得します
   *
   * @return ショップ名称
   */
  public String getShopName() {
    return this.shopName;
  }

  /**
   * 商品金額累計を取得します
   *
   * @return 商品金額累計
   */
  public BigDecimal getTotalSalesPrice() {
    return this.totalSalesPrice;
  }

  /**
   * 商品消費税額累計を取得します
   *
   * @return 商品消費税額累計
   */
  public BigDecimal getTotalSalesPriceTax() {
    return this.totalSalesPriceTax;
  }

  /**
   * ギフト金額累計を取得します
   *
   * @return ギフト金額累計
   */
  public BigDecimal getTotalGiftPrice() {
    return this.totalGiftPrice;
  }

  /**
   * ギフト消費税額累計を取得します
   *
   * @return ギフト消費税額累計
   */
  public BigDecimal getTotalGiftTax() {
    return this.totalGiftTax;
  }

  /**
   * 送料累計を取得します
   *
   * @return 送料累計
   */
  public BigDecimal getTotalShippingCharge() {
    return this.totalShippingCharge;
  }

  /**
   * 送料消費税額累計を取得します
   *
   * @return 送料消費税額累計
   */
  public BigDecimal getTotalShippingChargeTax() {
    return this.totalShippingChargeTax;
  }

  /**
   * 値引額累計を取得します
   *
   * @return 値引額累計
   */
  public BigDecimal getTotalDiscountAmount() {
    return this.totalDiscountAmount;
  }

  /**
   * 返金額累計を取得します
   *
   * @return 返金額累計
   */
  public BigDecimal getTotalRefund() {
    return this.totalRefund;
  }

  /**
   * 返品損金額累計を取得します
   *
   * @return 返品損金額累計
   */
  public BigDecimal getTotalReturnItemLossMoney() {
    return this.totalReturnItemLossMoney;
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
   * ショップ別売上集計IDを設定します
   *
   * @param  val ショップ別売上集計ID
   */
  public void setSalesAmountByShopId(Long val) {
    this.salesAmountByShopId = val;
  }

  /**
   * 集計日を設定します
   *
   * @param  val 集計日
   */
  public void setCountedDate(Date val) {
    this.countedDate = DateUtil.immutableCopy(val);
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
   * クライアントグループを設定します
   *
   * @param  val クライアントグループ
   */
  public void setClientGroup(String val) {
    this.clientGroup = val;
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
   * ショップ名称を設定します
   *
   * @param  val ショップ名称
   */
  public void setShopName(String val) {
    this.shopName = val;
  }

  /**
   * 商品金額累計を設定します
   *
   * @param  val 商品金額累計
   */
  public void setTotalSalesPrice(BigDecimal val) {
    this.totalSalesPrice = val;
  }

  /**
   * 商品消費税額累計を設定します
   *
   * @param  val 商品消費税額累計
   */
  public void setTotalSalesPriceTax(BigDecimal val) {
    this.totalSalesPriceTax = val;
  }

  /**
   * ギフト金額累計を設定します
   *
   * @param  val ギフト金額累計
   */
  public void setTotalGiftPrice(BigDecimal val) {
    this.totalGiftPrice = val;
  }

  /**
   * ギフト消費税額累計を設定します
   *
   * @param  val ギフト消費税額累計
   */
  public void setTotalGiftTax(BigDecimal val) {
    this.totalGiftTax = val;
  }

  /**
   * 送料累計を設定します
   *
   * @param  val 送料累計
   */
  public void setTotalShippingCharge(BigDecimal val) {
    this.totalShippingCharge = val;
  }

  /**
   * 送料消費税額累計を設定します
   *
   * @param  val 送料消費税額累計
   */
  public void setTotalShippingChargeTax(BigDecimal val) {
    this.totalShippingChargeTax = val;
  }

  /**
   * 値引額累計を設定します
   *
   * @param  val 値引額累計
   */
  public void setTotalDiscountAmount(BigDecimal val) {
    this.totalDiscountAmount = val;
  }

  /**
   * 返金額累計を設定します
   *
   * @param  val 返金額累計
   */
  public void setTotalRefund(BigDecimal val) {
    this.totalRefund = val;
  }

  /**
   * 返品損金額累計を設定します
   *
   * @param  val 返品損金額累計
   */
  public void setTotalReturnItemLossMoney(BigDecimal val) {
    this.totalReturnItemLossMoney = val;
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
