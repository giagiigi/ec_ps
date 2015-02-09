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
 * 「タグ(Brand)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class TmallBrand implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 商店编号 */
  @Required
  @Length(16)
  @Metadata(name = "商店编号", order = 1)
  private String shopCode;

  /** 淘宝品牌vid */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "淘宝品牌vid", order = 2)
  private String tmallBrandCode;

  /** 淘宝品牌名称 */
  @Required
  @Length(50)
  @Metadata(name = "淘宝品牌名称", order = 3)
  private String tmallBrandName;

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

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public String getTmallBrandCode() {
    return tmallBrandCode;
  }

  public void setTmallBrandCode(String tmallBrandCode) {
    this.tmallBrandCode = tmallBrandCode;
  }

  public String getTmallBrandName() {
    return tmallBrandName;
  }

  public void setTmallBrandName(String tmallBrandName) {
    this.tmallBrandName = tmallBrandName;
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
