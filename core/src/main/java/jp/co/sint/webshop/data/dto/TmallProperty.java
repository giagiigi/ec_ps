//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class TmallProperty implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  @Required
  @Length(16)
  @Metadata(name = "属性id", order = 1)
  private String propertyId;

  @Required
  @Length(50)
  @Metadata(name = "属性名称", order = 2)
  private String propertyName;

  @Required
  @Length(16)
  @Metadata(name = "上级属性ID", order = 3)
  private String parentPid;

  @Required
  @Length(1)
  @Metadata(name = "是否必须属性", order = 4)
  private Long isMust;

  @Required
  @Length(1)
  @Metadata(name = "是否销售属性", order = 5)
  private Long isSale;

  @Required
  @Length(1)
  @Metadata(name = "输入类型 0：自由输入  1：选择", order = 6)
  private Long isEnum;

  @Required
  @Length(1)
  @Metadata(name = "多属性值标志 0：不是  1：是", order = 7)
  private Long isMulti;

  @Required
  @Length(16)
  @Metadata(name = "所属类目ID", order = 8)
  private String categoryId;

  @Required
  @Length(16)
  @Metadata(name = "上级属性值ID", order = 9)
  private String parentVid;

  @Required
  @Length(1)
  @Metadata(name = "是否关键属性", order = 10)
  private Long isKey;

  @Length(200)
  @Metadata(name = "属性英文名", order = 11)
  private String propertyNameEn;

  @Length(200)
  @Metadata(name = "属性日文名", order = 12)
  private String propertyNameJp;

  private List<TmallPropertyValue> propertyValueContain = new ArrayList<TmallPropertyValue>();

  /**
   * @return the propertyValueContain
   */
  public List<TmallPropertyValue> getPropertyValueContain() {
    return propertyValueContain;
  }

  /**
   * @param propertyValueContain
   *          the propertyValueContain to set
   */
  public void setPropertyValueContain(List<TmallPropertyValue> propertyValueContain) {
    this.propertyValueContain = propertyValueContain;
  }

  public boolean isSelect() {
    return this.isMulti == 0 && this.isEnum == 1;
  }

  public boolean isCheckbox() {
    return this.isMulti == 1 && this.isEnum == 1;
  }

  public boolean getIsSelectCanManual() {
    return this.isEnum == 2 && this.isMulti == 0;
  }

  public boolean getIsCheckedCanManual() {
    return this.isEnum == 2 && this.isMulti == 1;
  }

  public boolean getIsSelect() {
    return this.isSelect();

  }

  public boolean getIsCheckbox() {
    return this.isCheckbox();
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

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  /**
   * @return the propertyName
   */
  public String getPropertyName() {
    return propertyName;
  }

  /**
   * @param propertyName
   *          the propertyName to set
   */
  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  /**
   * @return the parentPid
   */
  public String getParentPid() {
    return parentPid;
  }

  /**
   * @param parentPid
   *          the parentPid to set
   */
  public void setParentPid(String parentPid) {
    this.parentPid = parentPid;
  }

  /**
   * @return the isMust
   */
  public Long getIsMust() {
    return isMust;
  }

  /**
   * @param isMust
   *          the isMust to set
   */
  public void setIsMust(Long isMust) {
    this.isMust = isMust;
  }

  /**
   * @return the isSale
   */
  public Long getIsSale() {
    return isSale;
  }

  /**
   * @param isSale
   *          the isSale to set
   */
  public void setIsSale(Long isSale) {
    this.isSale = isSale;
  }

  /**
   * @return the isEnum
   */
  public Long getIsEnum() {
    return isEnum;
  }

  /**
   * @param isEnum
   *          the isEnum to set
   */
  public void setIsEnum(Long isEnum) {
    this.isEnum = isEnum;
  }

  public Long getIsKey() {
    return isKey;
  }

  public void setIsKey(Long isKey) {
    this.isKey = isKey;
  }

  /**
   * @return the isMulti
   */
  public Long getIsMulti() {
    return isMulti;
  }

  /**
   * @param isMulti
   *          the isMulti to set
   */
  public void setIsMulti(Long isMulti) {
    this.isMulti = isMulti;
  }

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 10)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 11)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 12)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 13)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 14)
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

  public String getParentVid() {
    return parentVid;
  }

  public void setParentVid(String parentVid) {
    this.parentVid = parentVid;
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

  public String getPropertyNameEn() {
    return propertyNameEn;
  }

  public void setPropertyNameEn(String propertyNameEn) {
    this.propertyNameEn = propertyNameEn;
  }

  public String getPropertyNameJp() {
    return propertyNameJp;
  }

  public void setPropertyNameJp(String propertyNameJp) {
    this.propertyNameJp = propertyNameJp;
  }

  /**
   * 清除属性中属性值名称或者ID为空的元素
   */
  public void cleanEmptyPropertyValueElement() {
    List<TmallPropertyValue> contain = new ArrayList<TmallPropertyValue>();
    for (TmallPropertyValue value : contain) {
      if ((value.getValueId() != null && !value.getValueId().equals(""))
          && (value.getValueName() != null && !"".equals(value.getValueName()))) {
        contain.add(value);
      }
    }
    this.propertyValueContain = contain;
  }
}
