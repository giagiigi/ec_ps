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
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.StockIOType;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「入出庫明細(STOCK_IO_DETAIL)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class StockIODetail implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 入出庫行ID */
  @PrimaryKey(1)
  @Required
  @Length(38)
  @Metadata(name = "入出庫行ID", order = 1)
  private Long stockIOId;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 2)
  private String shopCode;

  /** 入出庫日 */
  @Required
  @Metadata(name = "入出庫日", order = 3)
  private Date stockIODate;

  /** SKUコード */
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKUコード", order = 4)
  private String skuCode;

  /** 入出庫数量 */
  @Length(8)
  @Quantity
  @Metadata(name = "入出庫数量", order = 5)
  private Long stockIOQuantity;

  /** 入出庫区分 */
  @Length(1)
  @Domain(StockIOType.class)
  @Metadata(name = "入出庫区分", order = 6)
  private Long stockIOType;

  /** メモ */
  @Length(200)
  @Metadata(name = "メモ", order = 7)
  private String memo;

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
   * 入出庫行IDを取得します
   *
   * @return 入出庫行ID
   */
  public Long getStockIOId() {
    return this.stockIOId;
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
   * 入出庫日を取得します
   *
   * @return 入出庫日
   */
  public Date getStockIODate() {
    return DateUtil.immutableCopy(this.stockIODate);
  }

  /**
   * SKUコードを取得します
   *
   * @return SKUコード
   */
  public String getSkuCode() {
    return this.skuCode;
  }

  /**
   * 入出庫数量を取得します
   *
   * @return 入出庫数量
   */
  public Long getStockIOQuantity() {
    return this.stockIOQuantity;
  }

  /**
   * 入出庫区分を取得します
   *
   * @return 入出庫区分
   */
  public Long getStockIOType() {
    return this.stockIOType;
  }

  /**
   * メモを取得します
   *
   * @return メモ
   */
  public String getMemo() {
    return this.memo;
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
   * 入出庫行IDを設定します
   *
   * @param  val 入出庫行ID
   */
  public void setStockIOId(Long val) {
    this.stockIOId = val;
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
   * 入出庫日を設定します
   *
   * @param  val 入出庫日
   */
  public void setStockIODate(Date val) {
    this.stockIODate = DateUtil.immutableCopy(val);
  }

  /**
   * SKUコードを設定します
   *
   * @param  val SKUコード
   */
  public void setSkuCode(String val) {
    this.skuCode = val;
  }

  /**
   * 入出庫数量を設定します
   *
   * @param  val 入出庫数量
   */
  public void setStockIOQuantity(Long val) {
    this.stockIOQuantity = val;
  }

  /**
   * 入出庫区分を設定します
   *
   * @param  val 入出庫区分
   */
  public void setStockIOType(Long val) {
    this.stockIOType = val;
  }

  /**
   * メモを設定します
   *
   * @param  val メモ
   */
  public void setMemo(String val) {
    this.memo = val;
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
