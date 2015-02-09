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
import jp.co.sint.webshop.data.attribute.CategoryCode;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「カテゴリ(CATEGORY)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class TmallCategory implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** カテゴリコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @CategoryCode
  @Metadata(name = "カテゴリコード", order = 1)
  private String categoryCode;

  /** PC用カテゴリ名称 */
  @Required
  @Length(50)
  @Metadata(name = "类目名称", order = 2)
  private String categoryName;

  @Length(16)
  @Metadata(name = "父类编号", order = 3)
  private String parentCode;

  /** 是否是父类目ID */
  @Required
  @Length(1)
  @Metadata(name = "是否是父类目ID", order = 4)
  private Long isParent;

  /** 是否是店铺自定义类目 */
  @Required
  @Length(1)
  @Metadata(name = "是否是店铺自定义类目", order = 5)
  private Long isShopCategory;

  /**
   * 类别包含的所有属性的集合
   */
  private List<TmallProperty> propertyContain = new ArrayList<TmallProperty>();

  /**
   * @return the propertyContain
   */
  public List<TmallProperty> getPropertyContain() {
    return propertyContain;
  }

  /**
   * @param propertyContain
   *          the propertyContain to set
   */
  public void setPropertyContain(List<TmallProperty> propertyContain) {
    this.propertyContain = propertyContain;
  }

  /**
   * @param isParent
   *          the isParent to set
   */
  public void setIsParent(Long isParent) {
    this.isParent = isParent;
  }

  /**
   * @param isShopCategory
   *          the isShopCategory to set
   */
  public void setIsShopCategory(Long isShopCategory) {
    this.isShopCategory = isShopCategory;
  }

  public boolean isParentBoolean() {
    return this.isParent == 1;
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
   * @return the parentCode
   */
  public String getParentCode() {
    return parentCode;
  }

  /**
   * @param parentCode
   *          the parentCode to set
   */
  public void setParentCode(String parentCode) {
    this.parentCode = parentCode;
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
   * カテゴリコードを取得します
   * 
   * @return カテゴリコード
   */
  public String getCategoryCode() {
    return this.categoryCode;
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
   * カテゴリコードを設定します
   * 
   * @param val
   *          カテゴリコード
   */
  public void setCategoryCode(String val) {
    this.categoryCode = val;
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

  public Long getIsParent() {
    return isParent;
  }

  public Long getIsShopCategory() {
    return isShopCategory;
  }

}
