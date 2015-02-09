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
import jp.co.sint.webshop.data.attribute.CategoryCode;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「カテゴリ属性名称(CATEGORY_ATTRIBUTE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class CategoryAttribute implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** カテゴリコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @CategoryCode
  @Metadata(name = "カテゴリコード", order = 1)
  private String categoryCode;

  /** カテゴリ属性番号 */
  @PrimaryKey(2)
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "カテゴリ属性番号", order = 2)
  private Long categoryAttributeNo;

  /** カテゴリ属性名称 */
  @Length(15)
  @Metadata(name = "カテゴリ属性名称", order = 3)
  private String categoryAttributeName;

  /** カテゴリ属性名称 */
  @Length(30)
  @Metadata(name = "カテゴリ属性英文名称", order = 4)
  private String categoryAttributeNameEn;

  /** カテゴリ属性名称 */
  @Length(30)
  @Metadata(name = "カテゴリ属性日文名称", order = 5)
  private String categoryAttributeNameJp;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order =6)
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

  /**
   * カテゴリコードを取得します
   *
   * @return カテゴリコード
   */
  public String getCategoryCode() {
    return this.categoryCode;
  }

  /**
   * カテゴリ属性番号を取得します
   *
   * @return カテゴリ属性番号
   */
  public Long getCategoryAttributeNo() {
    return this.categoryAttributeNo;
  }

  /**
   * カテゴリ属性名称を取得します
   *
   * @return カテゴリ属性名称
   */
  public String getCategoryAttributeName() {
    return this.categoryAttributeName;
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
   * @param  val カテゴリコード
   */
  public void setCategoryCode(String val) {
    this.categoryCode = val;
  }

  /**
   * カテゴリ属性番号を設定します
   *
   * @param  val カテゴリ属性番号
   */
  public void setCategoryAttributeNo(Long val) {
    this.categoryAttributeNo = val;
  }

  /**
   * カテゴリ属性名称を設定します
   *
   * @param  val カテゴリ属性名称
   */
  public void setCategoryAttributeName(String val) {
    this.categoryAttributeName = val;
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

/**
 * @param categoryAttributeNameEn the categoryAttributeNameEn to set
 */
public void setCategoryAttributeNameEn(String categoryAttributeNameEn) {
	this.categoryAttributeNameEn = categoryAttributeNameEn;
}

/**
 * @return the categoryAttributeNameEn
 */
public String getCategoryAttributeNameEn() {
	return categoryAttributeNameEn;
}

/**
 * @param categoryAttributeNameJp the categoryAttributeNameJp to set
 */
public void setCategoryAttributeNameJp(String categoryAttributeNameJp) {
	this.categoryAttributeNameJp = categoryAttributeNameJp;
}

/**
 * @return the categoryAttributeNameJp
 */
public String getCategoryAttributeNameJp() {
	return categoryAttributeNameJp;
}

}
