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
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「在庫状況(STOCK_STATUS)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class StockStatus implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** 在庫状況番号 */
  @PrimaryKey(2)
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "在庫状況番号", order = 2)
  private Long stockStatusNo;

  /** 在庫状況分類名 */
  @Length(40)
  @Metadata(name = "在庫状況分類名", order = 3)
  private String stockStatusName;

  /** 在庫多メッセージ */
  @Length(50)
  @Metadata(name = "在庫多メッセージ", order = 4)
  private String stockSufficientMessage;

  /** 在庫少メッセージ */
  @Length(50)
  @Metadata(name = "在庫少メッセージ", order = 5)
  private String stockLittleMessage;

  /** 在庫切メッセージ */
  @Length(50)
  @Metadata(name = "在庫切メッセージ", order = 6)
  private String outOfStockMessage;

  /** 在庫多閾値 */
  @Length(8)
  @Metadata(name = "在庫多閾値", order = 7)
  private Long stockSufficientThreshold;

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
   * ショップコードを取得します
   *
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * 在庫状況番号を取得します
   *
   * @return 在庫状況番号
   */
  public Long getStockStatusNo() {
    return this.stockStatusNo;
  }

  /**
   * 在庫状況分類名を取得します
   *
   * @return 在庫状況分類名
   */
  public String getStockStatusName() {
    return this.stockStatusName;
  }

  /**
   * 在庫多メッセージを取得します
   *
   * @return 在庫多メッセージ
   */
  public String getStockSufficientMessage() {
    return this.stockSufficientMessage;
  }

  /**
   * 在庫少メッセージを取得します
   *
   * @return 在庫少メッセージ
   */
  public String getStockLittleMessage() {
    return this.stockLittleMessage;
  }

  /**
   * 在庫切メッセージを取得します
   *
   * @return 在庫切メッセージ
   */
  public String getOutOfStockMessage() {
    return this.outOfStockMessage;
  }

  /**
   * 在庫多閾値を取得します
   *
   * @return 在庫多閾値
   */
  public Long getStockSufficientThreshold() {
    return this.stockSufficientThreshold;
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
   * 在庫状況番号を設定します
   *
   * @param  val 在庫状況番号
   */
  public void setStockStatusNo(Long val) {
    this.stockStatusNo = val;
  }

  /**
   * 在庫状況分類名を設定します
   *
   * @param  val 在庫状況分類名
   */
  public void setStockStatusName(String val) {
    this.stockStatusName = val;
  }

  /**
   * 在庫多メッセージを設定します
   *
   * @param  val 在庫多メッセージ
   */
  public void setStockSufficientMessage(String val) {
    this.stockSufficientMessage = val;
  }

  /**
   * 在庫少メッセージを設定します
   *
   * @param  val 在庫少メッセージ
   */
  public void setStockLittleMessage(String val) {
    this.stockLittleMessage = val;
  }

  /**
   * 在庫切メッセージを設定します
   *
   * @param  val 在庫切メッセージ
   */
  public void setOutOfStockMessage(String val) {
    this.outOfStockMessage = val;
  }

  /**
   * 在庫多閾値を設定します
   *
   * @param  val 在庫多閾値
   */
  public void setStockSufficientThreshold(Long val) {
    this.stockSufficientThreshold = val;
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
