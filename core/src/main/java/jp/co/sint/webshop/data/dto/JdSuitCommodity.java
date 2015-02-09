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
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「JD库存比率分配表(JdStockAllocation)」テーブルの1行分のレコードを表すDTO(Data Transfer
 * Object)です。
 * 
 * @author System Integrator Corp.
 */
public class JdSuitCommodity implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 商品コード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 1)
  private String commodityCode;

  /** 在庫数量 */
  @Required
  @Length(8)
  @Metadata(name = "在庫数量", order = 2)
  private Long stockQuantity;
  
  /** 引当数 */
  @Required
  @Length(8)
  @Metadata(name = "引当数", order = 3)
  private Long allocatedQuantity;

  /** 比率值 */
  @Required
  @Length(8)
  @Metadata(name = "比率值", order = 4)
  private Long scaleValue;

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
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @return the stockQuantity
   */
  public Long getStockQuantity() {
    return stockQuantity;
  }

  /**
   * @return the scaleValue
   */
  public Long getScaleValue() {
    return scaleValue;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @param stockQuantity
   *          the stockQuantity to set
   */
  public void setStockQuantity(Long stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  /**
   * @param scaleValue
   *          the scaleValue to set
   */
  public void setScaleValue(Long scaleValue) {
    this.scaleValue = scaleValue;
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
   * @return the allocatedQuantity
   */
  public Long getAllocatedQuantity() {
    return allocatedQuantity;
  }

  
  /**
   * @param allocatedQuantity the allocatedQuantity to set
   */
  public void setAllocatedQuantity(Long allocatedQuantity) {
    this.allocatedQuantity = allocatedQuantity;
  }

}
