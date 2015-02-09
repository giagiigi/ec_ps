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
 * @author System Integrator Corp.
 */
public class RelatedSiblingCategory implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 商店编号 */
  @Required
  @Length(16)
  @Metadata(name = "商店编号", order = 1)
  private String shopCode;

  /** 商店编号 */
  @Required
  @Length(16)
  @Metadata(name = "商品编号", order = 2)
  private String commodityCode;

  /** 品牌编号 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "类目编号", order = 3)
  private String categoryCode;

  /** 类目名称 */
  @Required
  @Length(50)
  @Metadata(name = "类目名称(中文)", order = 4)
  private String categoryName;

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

  /** 类目英文名称 */
  @Length(50)
  @Metadata(name = "类目名称(英文)", order = 11)
  private String categoryNameEn;

  /** 淘宝类目ID */
  @Length(16)
  @Metadata(name = "类目名称(日文)", order = 12)
  private String categoryNameJp;

  /** 淘宝类目名 */
  @Length(50)
  @Metadata(name = "显示标志", order = 13)
  private Long displayFlag;

  @Length(2)
  @Metadata(name = "显示顺序", order = 14)
  private Long displayOrder;

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return the categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * @param categoryCode
   *          the categoryCode to set
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  /**
   * @return the categoryName
   */
  public String getCategoryName() {
    return categoryName;
  }

  /**
   * @param categoryName
   *          the categoryName to set
   */
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * @param ormRowid
   *          the ormRowid to set
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @return the createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * @param createdUser
   *          the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @return the createdDatetime
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  /**
   * @param createdDatetime
   *          the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * @param updatedUser
   *          the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  /**
   * @param updatedDatetime
   *          the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  /**
   * @return the categoryNameJp
   */
  public String getCategoryNameJp() {
    return categoryNameJp;
  }

  /**
   * @param categoryNameJp
   *          the categoryNameJp to set
   */
  public void setCategoryNameJp(String categoryNameJp) {
    this.categoryNameJp = categoryNameJp;
  }

  /**
   * @return the displayFlag
   */
  public Long getDisplayFlag() {
    return displayFlag;
  }

  /**
   * @param displayFlag
   *          the displayFlag to set
   */
  public void setDisplayFlag(Long displayFlag) {
    this.displayFlag = displayFlag;
  }

  /**
   * @return the displayOrder
   */
  public Long getDisplayOrder() {
    return displayOrder;
  }

  /**
   * @param displayOrder
   *          the displayOrder to set
   */
  public void setDisplayOrder(Long displayOrder) {
    this.displayOrder = displayOrder;
  }

  /**
   * @return the categoryNameEn
   */
  public String getCategoryNameEn() {
    return categoryNameEn;
  }

  /**
   * @param categoryNameEn
   *          the categoryNameEn to set
   */
  public void setCategoryNameEn(String categoryNameEn) {
    this.categoryNameEn = categoryNameEn;
  }

}
