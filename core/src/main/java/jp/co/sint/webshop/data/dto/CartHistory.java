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

/**
 * 「购物车履历(CART_HISTORY)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author Kousen.
 */
public class CartHistory implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 顾客编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顾客编号", order = 1)
  private String customerCode;

  /** 店铺编号 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "店铺编号", order = 2)
  private String shopCode;

  /** 商品编号 */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品编号", order = 3)
  private String commodityCode;

  /** SKU编号 */
  @PrimaryKey(3)
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKU编号", order = 4)
  private String skuCode;

  /** 购入数量 */
  @Required
  @Length(8)
  @Metadata(name = "购入数量", order = 5)
  private Long purchasingAmount;

  /** 购入时间 */
  @Required
  @Metadata(name = "购入时间", order = 6)
  private Date cartDatetime;

  /** 数据行ID */
  @Required
  @Length(38)
  @Metadata(name = "数据行ID", order = 7)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成者ID */
  @Required
  @Length(100)
  @Metadata(name = "作成者ID", order = 8)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 9)
  private Date createdDatetime;

  /** 更新者ID */
  @Required
  @Length(100)
  @Metadata(name = "更新者ID", order = 10)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 11)
  private Date updatedDatetime;

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public String getCommodityCode() {
    return commodityCode;
  }

  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  public String getSkuCode() {
    return skuCode;
  }

  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  public Long getPurchasingAmount() {
    return purchasingAmount;
  }

  public void setPurchasingAmount(Long purchasingAmount) {
    this.purchasingAmount = purchasingAmount;
  }

  public Date getCartDatetime() {
    return cartDatetime;
  }

  public void setCartDatetime(Date cartDatetime) {
    this.cartDatetime = cartDatetime;
  }

  public Long getOrmRowid() {
    return ormRowid;
  }

  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  public String getCreatedUser() {
    return createdUser;
  }

  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  public String getUpdatedUser() {
    return updatedUser;
  }

  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

}
