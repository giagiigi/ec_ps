//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity; 
import jp.co.sint.webshop.data.attribute.Metadata; 
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「在庫(StockBatch)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class StockBatch implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */

  @Metadata(name = "ショップコード")
  private String shopCode;

  /** SKUコード */
  @Metadata(name = "SKUコード")
  private String skuCode;

  /** 商品コード */
  @Metadata(name = "商品コード")
  private String commodityCode;

  /** 在庫数量 */
  @Metadata(name = "在庫数量")
  private Long stockQuantity;

  /** 引当数量 */
  @Metadata(name = "引当数量")
  private Long allocatedQuantity;

  /** 予約数量 */
  @Metadata(name = "予約数量")
  private Long reservedQuantity;

  /** 予約上限数 */
  @Metadata(name = "予約上限数")
  private Long reservationLimit;

  /** 注文毎予約上限数 */
  @Metadata(name = "注文毎予約上限数")
  private Long oneshotReservationLimit;

  /** 在庫閾値 */
  @Metadata(name = "在庫閾値")
  private Long stockThreshold;

  /** データ行ID */
  @Metadata(name = "データ行ID")
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Metadata(name = "作成ユーザ")
  private String createdUser;

  /** 作成日時 */
  @Metadata(name = "作成日時")
  private Date createdDatetime;

  /** 更新ユーザ */
  @Metadata(name = "更新ユーザ")
  private String updatedUser;

  /** 更新日時 */

  @Metadata(name = "更新日時")
  private Date updatedDatetime;

  /** TMALL引当数 */
  @Metadata(name = "TMALL引当数")
  private Long allocatedTmall;

  /** EC在库割合(0-100) */
  @Metadata(name = "EC在库割合(0-100)")
  private Long shareRatio;

  /** 総在庫 */
  @Metadata(name = " 総在庫")
  private Long stockTotal;

  /** TMALL在庫数 */
  @Metadata(name = "TMALL在庫数")
  private Long stockTmall;

  /** EC無限在庫フラグ */
  @Metadata(name = "EC無限在庫フラグ")
  private Long infinityFlagEc;

  /** TMALL無限在庫フラグ */
  @Metadata(name = "TMALL無限在庫フラグ")
  private Long infinityFlagTmall;

  /** 在庫リーバランスフラグ */
  @Metadata(name = "在庫リーバランスフラグ")
  private Long shareRecalcFlag;
 /**
  * 更新标识
  */
  private Long  tmallUpFlg;
  /**
   * ショップコードを取得します
   * 
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
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
   * 商品コードを取得します
   * 
   * @return 商品コード
   */
  public String getCommodityCode() {
    return this.commodityCode;
  }

  /**
   * 在庫数量を取得します
   * 
   * @return 在庫数量
   */
  public Long getStockQuantity() {
    return this.stockQuantity;
  }

  /**
   * 引当数量を取得します
   * 
   * @return 引当数量
   */
  public Long getAllocatedQuantity() {
    return this.allocatedQuantity;
  }

  /**
   * 予約数量を取得します
   * 
   * @return 予約数量
   */
  public Long getReservedQuantity() {
    return this.reservedQuantity;
  }

  /**
   * 予約上限数を取得します
   * 
   * @return 予約上限数
   */
  public Long getReservationLimit() {
    return this.reservationLimit;
  }

  /**
   * 注文毎予約上限数を取得します
   * 
   * @return 注文毎予約上限数
   */
  public Long getOneshotReservationLimit() {
    return this.oneshotReservationLimit;
  }

  /**
   * 在庫閾値を取得します
   * 
   * @return 在庫閾値
   */
  public Long getStockThreshold() {
    return this.stockThreshold;
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
   * @param val
   *          ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * SKUコードを設定します
   * 
   * @param val
   *          SKUコード
   */
  public void setSkuCode(String val) {
    this.skuCode = val;
  }

  /**
   * 商品コードを設定します
   * 
   * @param val
   *          商品コード
   */
  public void setCommodityCode(String val) {
    this.commodityCode = val;
  }

  /**
   * 在庫数量を設定します
   * 
   * @param val
   *          在庫数量
   */
  public void setStockQuantity(Long val) {
    this.stockQuantity = val;
  }

  /**
   * 引当数量を設定します
   * 
   * @param val
   *          引当数量
   */
  public void setAllocatedQuantity(Long val) {
    this.allocatedQuantity = val;
  }

  /**
   * 予約数量を設定します
   * 
   * @param val
   *          予約数量
   */
  public void setReservedQuantity(Long val) {
    this.reservedQuantity = val;
  }

  /**
   * 予約上限数を設定します
   * 
   * @param val
   *          予約上限数
   */
  public void setReservationLimit(Long val) {
    this.reservationLimit = val;
  }

  /**
   * 注文毎予約上限数を設定します
   * 
   * @param val
   *          注文毎予約上限数
   */
  public void setOneshotReservationLimit(Long val) {
    this.oneshotReservationLimit = val;
  }

  /**
   * 在庫閾値を設定します
   * 
   * @param val
   *          在庫閾値
   */
  public void setStockThreshold(Long val) {
    this.stockThreshold = val;
  }

  /**
   * データ行IDを設定します
   * 
   * @param val
   *          データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   * 
   * @param val
   *          作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   * 
   * @param val
   *          作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   * 
   * @param val
   *          更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   * 
   * @param val
   *          更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * @param allocatedTmall
   *          the allocatedTmall to set
   */
  public void setAllocatedTmall(Long allocatedTmall) {
    this.allocatedTmall = allocatedTmall;
  }

  /**
   * @return the allocatedTmall
   */
  public Long getAllocatedTmall() {
    return allocatedTmall;
  }

  /**
   * @param shareRatio
   *          the shareRatio to set
   */
  public void setShareRatio(Long shareRatio) {
    this.shareRatio = shareRatio;
  }

  /**
   * @return the shareRatio
   */
  public Long getShareRatio() {
    return shareRatio;
  }

  /**
   * @param stockTmall
   *          the stockTmall to set
   */
  public void setStockTmall(Long stockTmall) {
    this.stockTmall = stockTmall;
  }

  /**
   * @return the stockTmall
   */
  public Long getStockTmall() {
    return stockTmall;
  }

  /**
   * @param infinityFlagEc
   *          the infinityFlagEc to set
   */
  public void setInfinityFlagEc(Long infinityFlagEc) {
    this.infinityFlagEc = infinityFlagEc;
  }

  /**
   * @return the infinityFlagEc
   */
  public Long getInfinityFlagEc() {
    return infinityFlagEc;
  }

  /**
   * @param stockTotal
   *          the stockTotal to set
   */
  public void setStockTotal(Long stockTotal) {
    this.stockTotal = stockTotal;
  }

  /**
   * @return the stockTotal
   */
  public Long getStockTotal() {
    return stockTotal;
  }

  /**
   * @param infinityFlagTmall
   *          the infinityFlagTmall to set
   */
  public void setInfinityFlagTmall(Long infinityFlagTmall) {
    this.infinityFlagTmall = infinityFlagTmall;
  }

  /**
   * @return the infinityFlagTmall
   */
  public Long getInfinityFlagTmall() {
    return infinityFlagTmall;
  }

  /**
   * @param shareRecalcFlag
   *          the shareRecalcFlag to set
   */
  public void setShareRecalcFlag(Long shareRecalcFlag) {
    this.shareRecalcFlag = shareRecalcFlag;
  }

  /**
   * @return the shareRecalcFlag
   */
  public Long getShareRecalcFlag() {
    return shareRecalcFlag;
  }

  /**
   * @param tmallUpFlg the tmallUpFlg to set
   */
  public void setTmallUpFlg(Long tmallUpFlg) {
    this.tmallUpFlg = tmallUpFlg;
  }

  /**
   * @return the tmallUpFlg
   */
  public Long getTmallUpFlg() {
    return tmallUpFlg;
  }

}
