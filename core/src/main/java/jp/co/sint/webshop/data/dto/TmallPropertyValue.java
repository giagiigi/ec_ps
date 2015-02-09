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
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「カテゴリ(CATEGORY)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class TmallPropertyValue implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  @Required
  @Length(16)
  @Metadata(name = "属性值id", order = 1)
  private String valueId;

  @Required
  @Length(50)
  @Metadata(name = "属性值名称", order = 2)
  private String valueName;

  @Required
  @Length(16)
  @Metadata(name = "所属属性id", order = 3)
  private String propertyId;

  @Required
  @Length(50)
  @Metadata(name = "属性值别名", order = 4)
  private String aliasName;

  @Required
  @Length(1)
  @Metadata(name = "删除标志", order = 5)
  private Long deleteFlag;

  @Required
  @Length(16)
  @Metadata(name = "所属类目ID", order = 6)
  private String categoryId;

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

  @Length(500)
  @Metadata(name = "属性值英文", order = 12)
  private String valueNameEn;

  @Length(500)
  @Metadata(name = "属性值日文", order = 13)
  private String valueNameJp;

  private boolean isSelect;

  public boolean getIsSelect() {
    return isSelect;
  }

  public void setIsSelect(boolean boo) {
    this.isSelect = boo;
  }

  /**
   * @return the valueId
   */
  public String getValueId() {
    return valueId;
  }

  /**
   * @param valueId
   *          the valueId to set
   */
  public void setValueId(String valueId) {
    this.valueId = valueId;
  }

  /**
   * @return the valueName
   */
  public String getValueName() {
    return valueName;
  }

  /**
   * @param valueName
   *          the valueName to set
   */
  public void setValueName(String valueName) {
    this.valueName = valueName;
  }

  /**
   * @return the propertyId
   */
  public String getPropertyId() {
    return propertyId;
  }

  /**
   * @param propertyId
   *          the propertyId to set
   */
  public void setPropertyId(String propertyId) {
    this.propertyId = propertyId;
  }

  /**
   * @return the aliasName
   */
  public String getAliasName() {
    return aliasName;
  }

  /**
   * @param aliasName
   *          the aliasName to set
   */
  public void setAliasName(String aliasName) {
    this.aliasName = aliasName;
  }

  /**
   * @return the deleteFlag
   */
  public Long getDeleteFlag() {
    return deleteFlag;
  }

  /**
   * @param deleteFlag
   *          the deleteFlag to set
   */
  public void setDeleteFlag(Long deleteFlag) {
    this.deleteFlag = deleteFlag;
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

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getValueNameEn() {
    return valueNameEn;
  }

  public void setValueNameEn(String valueNameEn) {
    this.valueNameEn = valueNameEn;
  }

  public String getValueNameJp() {
    return valueNameJp;
  }

  public void setValueNameJp(String valueNameJp) {
    this.valueNameJp = valueNameJp;
  }

}
