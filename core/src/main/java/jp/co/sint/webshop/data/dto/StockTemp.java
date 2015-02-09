//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
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
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「在庫临时表(STOCK_TEMP)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class StockTemp implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** SKUコード */
  @PrimaryKey(2)
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKUコード", order = 2)
  private String skuCode;

  /** 库存变更区分 */
  @Required
  @Length(1)
  @Quantity
  @Metadata(name = "库存变更区分", order = 3)
  private Long stockChangeType;
  
  /** 库存变更数量 */
  @Required
  @Length(8)
  @Quantity
  @Metadata(name = "库存变更数量", order = 4)
  private Long stockChangeQuantity;
  
  /** 在庫数量 */
  @Required
  @Length(8)
  @Quantity
  @Metadata(name = "总在庫数量增量", order = 3)
  private Long addStockTotal;

  /** 在庫数量 */
  @Required
  @Length(8)
  @Quantity
  @Metadata(name = "EC在庫数量增量", order = 4)
  private Long addStockEc;

  /** 在庫数量 */
  @Required
  @Length(8)
  @Quantity
  @Metadata(name = "Tmall在庫数量增量", order = 5)
  private Long addStockTmall;

  /** 在庫数量 */
  @Required
  @Length(8)
  @Quantity
  @Metadata(name = "安全在庫数量增量", order = 6)
  private Long addStockThreshold;

  /** 商品コード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 3)
  private String commodityCode;

  /** 在庫数量 */
  @Required
  @Length(8)
  @Quantity
  @Metadata(name = "在庫数量", order = 4)
  private Long stockQuantity;

  /** 引当数量 */
  @Required
  @Length(8)
  @Quantity
  @Metadata(name = "引当数量", order = 5)
  private Long allocatedQuantity;

  /** 予約数量 */
  @Required
  @Length(8)
  @Quantity
  @Metadata(name = "予約数量", order = 6)
  private Long reservedQuantity;

  /** 予約上限数 */
  @Length(8)
  @Quantity
  @Metadata(name = "予約上限数", order = 7)
  private Long reservationLimit;

  /** 注文毎予約上限数 */
  @Length(8)
  @Quantity
  @Metadata(name = "注文毎予約上限数", order = 8)
  private Long oneshotReservationLimit;

  /** 在庫閾値 */
  @Required
  @Length(8)
  @Metadata(name = "在庫閾値", order = 9)
  private Long stockThreshold;

  /** TMALL引当数 */
  @Length(8)
  @Quantity
  @Metadata(name = "TMALL引当数", order = 15)
  private Long allocatedTmall;
  
  /** JD引当数 */
  @Length(8)
  @Quantity
  @Metadata(name = "JD引当数", order = 16)
  private Long allocatedJd;

  /** EC在库割合(0-100) */
  @Length(3)
  @Quantity
  @Metadata(name = "EC在库割合(0-100)", order = 16)
  private Long shareRatio;

  /** 総在庫 */
  @Length(8)
  @Quantity
  @Metadata(name = " 総在庫", order = 17)
  private Long stockTotal;
  
  /** 原始在庫 */
  @Length(8)
  @Quantity
  @Metadata(name = "原始在庫", order = 17)
  private Long originalStock;

  /** TMALL在庫数 */
  @Length(8)
  @Quantity
  @Metadata(name = "TMALL在庫数", order = 18)
  private Long stockTmall;
  
  /** JD在庫数 */
  @Length(8)
  @Quantity
  @Metadata(name = "JD在庫数", order = 19)
  private Long stockJd;

  /** EC無限在庫フラグ */
  @Length(1)
  @Quantity
  @Metadata(name = "EC無限在庫フラグ", order = 19)
  private Long infinityFlagEc;

  /** TMALL無限在庫フラグ */
  @Length(1)
  @Quantity
  @Metadata(name = "TMALL無限在庫フラグ", order = 20)
  private Long infinityFlagTmall;

  /** 在庫リーバランスフラグ */
  @Length(1)
  @Quantity
  @Metadata(name = "在庫リーバランスフラグ", order = 21)
  private Long shareRecalcFlag;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 7)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 8)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 9)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 10)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 11)
  private Date updatedDatetime;
  
  /**在库品区分 **/
  private String stockFlg;
  
  public String getStockFlg() {
    return stockFlg;
  }
  
  public void setStockFlg(String stockFlg) {
    this.stockFlg = stockFlg;
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
   * SKUコードを取得します
   * 
   * @return SKUコード
   */
  public String getSkuCode() {
    return this.skuCode;
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

  public Long getOrmRowid() {
    return ormRowid;
  }

  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  public Long getAddStockTotal() {
    return addStockTotal;
  }

  public void setAddStockTotal(Long addStockTotal) {
    this.addStockTotal = addStockTotal;
  }

  public Long getAddStockEc() {
    return addStockEc;
  }

  public void setAddStockEc(Long addStockEc) {
    this.addStockEc = addStockEc;
  }

  public Long getAddStockTmall() {
    return addStockTmall;
  }

  public void setAddStockTmall(Long addStockTmall) {
    this.addStockTmall = addStockTmall;
  }

  public Long getAddStockThreshold() {
    return addStockThreshold;
  }

  public void setAddStockThreshold(Long addStockThreshold) {
    this.addStockThreshold = addStockThreshold;
  }

  public String getCommodityCode() {
    return commodityCode;
  }

  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  public Long getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(Long stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  public Long getAllocatedQuantity() {
    return allocatedQuantity;
  }

  public void setAllocatedQuantity(Long allocatedQuantity) {
    this.allocatedQuantity = allocatedQuantity;
  }

  public Long getReservedQuantity() {
    return reservedQuantity;
  }

  public void setReservedQuantity(Long reservedQuantity) {
    this.reservedQuantity = reservedQuantity;
  }

  public Long getReservationLimit() {
    return reservationLimit;
  }

  public void setReservationLimit(Long reservationLimit) {
    this.reservationLimit = reservationLimit;
  }

  public Long getOneshotReservationLimit() {
    return oneshotReservationLimit;
  }

  public void setOneshotReservationLimit(Long oneshotReservationLimit) {
    this.oneshotReservationLimit = oneshotReservationLimit;
  }

  public Long getStockThreshold() {
    return stockThreshold;
  }

  public void setStockThreshold(Long stockThreshold) {
    this.stockThreshold = stockThreshold;
  }

  public Long getAllocatedTmall() {
    return allocatedTmall;
  }

  public void setAllocatedTmall(Long allocatedTmall) {
    this.allocatedTmall = allocatedTmall;
  }

  public Long getShareRatio() {
    return shareRatio;
  }

  public void setShareRatio(Long shareRatio) {
    this.shareRatio = shareRatio;
  }

  public Long getStockTotal() {
    return stockTotal;
  }

  public void setStockTotal(Long stockTotal) {
    this.stockTotal = stockTotal;
  }

  public Long getStockTmall() {
    return stockTmall;
  }

  public void setStockTmall(Long stockTmall) {
    this.stockTmall = stockTmall;
  }

  public Long getInfinityFlagEc() {
    return infinityFlagEc;
  }

  public void setInfinityFlagEc(Long infinityFlagEc) {
    this.infinityFlagEc = infinityFlagEc;
  }

  public Long getInfinityFlagTmall() {
    return infinityFlagTmall;
  }

  public void setInfinityFlagTmall(Long infinityFlagTmall) {
    this.infinityFlagTmall = infinityFlagTmall;
  }

  public Long getShareRecalcFlag() {
    return shareRecalcFlag;
  }

  public void setShareRecalcFlag(Long shareRecalcFlag) {
    this.shareRecalcFlag = shareRecalcFlag;
  }

  /**
   * @return the originalStock
   */
  public Long getOriginalStock() {
    return originalStock;
  }

  /**
   * @param originalStock the originalStock to set
   */
  public void setOriginalStock(Long originalStock) {
    this.originalStock = originalStock;
  }

  /**
   * @param stockChangeType the stockChangeType to set
   */
  public void setStockChangeType(Long stockChangeType) {
    this.stockChangeType = stockChangeType;
  }

  /**
   * @return the stockChangeType
   */
  public Long getStockChangeType() {
    return stockChangeType;
  }

  /**
   * @param stockChangeQuantity the stockChangeQuantity to set
   */
  public void setStockChangeQuantity(Long stockChangeQuantity) {
    this.stockChangeQuantity = stockChangeQuantity;
  }

  /**
   * @return the stockChangeQuantity
   */
  public Long getStockChangeQuantity() {
    return stockChangeQuantity;
  }

  /**
   * @param stockJd the stockJd to set
   */
  public void setStockJd(Long stockJd) {
    this.stockJd = stockJd;
  }

  /**
   * @return the stockJd
   */
  public Long getStockJd() {
    return stockJd;
  }

  /**
   * @param allocatedJd the allocatedJd to set
   */
  public void setAllocatedJd(Long allocatedJd) {
    this.allocatedJd = allocatedJd;
  }

  /**
   * @return the allocatedJd
   */
  public Long getAllocatedJd() {
    return allocatedJd;
  }
}
