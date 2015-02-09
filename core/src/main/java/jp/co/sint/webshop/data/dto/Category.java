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
public class Category implements Serializable, WebshopEntity {

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
  @Length(20)
  @Metadata(name = "PC用カテゴリ名称", order = 2)
  private String categoryNamePc;

  /** 携帯用カテゴリ名称 */
  @Required
  @Length(10)
  @Metadata(name = "携帯用カテゴリ名称", order = 3)
  private String categoryNameMobile;

  /** 親カテゴリコード */
  @Required
  @Length(16)
  @CategoryCode
  @Metadata(name = "親カテゴリコード", order = 4)
  private String parentCategoryCode;

  /** パス */
  @Required
  @Length(256)
  @Metadata(name = "パス", order = 5)
  private String path;

  /** 階層 */
  @Required
  @Length(2)
  @Metadata(name = "階層", order = 6)
  private Long depth;

  /** 表示順 */
  @Length(8)
  @Metadata(name = "表示順", order = 7)
  private Long displayOrder;

  /** PC商品件数 */
  @Length(12)
  @Metadata(name = "PC商品件数", order = 8)
  private Long commodityCountPc;

  /** 携帯商品件数 */
  @Length(12)
  @Metadata(name = "携帯商品件数", order = 9)
  private Long commodityCountMobile;

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
   * add by os012 20111221 start 淘宝分类编号
   */
  @Length(16)
  @Metadata(name = "淘宝分类编号", order = 16)
  private String categoryIdTmall;
  
  // add by os012 20111221 end
  @Length(50)
  @Metadata(name = "meta keyword", order = 17)
  private String metaKeyword;

  @Length(50)
  @Metadata(name = "meta description", order = 18)
  private String metaDescription;

  // 20120514 tuxinwei add start
  // add by zhangzhengtao 20130527 start
  /** 検索Keyword（中文）1 */
  @Metadata(name = " 検索Keyword（中文）1", order = 19)
  private String keywordCn1;

  /** 検索Keyword（日文）2 */
  @Metadata(name = " 検索Keyword（日文）1", order = 20)
  private String keywordJp1;

  /** 検索Keyword（英文）2 */
  @Metadata(name = " 検索Keyword（英文）1", order = 21)
  private String keywordEn1;

  /** 検索Keyword（中文）2 */
  @Metadata(name = " 検索Keyword（中文）2", order = 22)
  private String keywordCn2;

  /** 検索Keyword（日文）2 */
  @Metadata(name = " 検索Keyword（日文）2", order = 23)
  private String keywordJp2;

  /** 検索Keyword（英文）2 */
  @Metadata(name = " 検索Keyword（英文）2", order = 24)
  private String keywordEn2;

  // add by zhangzhengtao 20130527 end

  /** PC用カテゴリ名称(英文) */
  @Required
  @Length(100)
  @Metadata(name = "PC用カテゴリ名称(英文)", order = 19)
  private String categoryNamePcEn;

  // 20130808 txw add start
  /*** TITLE ***/
  @Length(60)
  @Metadata(name = "TITLE", order = 26)
  private String title;

  /*** TITLE(英文) ***/
  @Length(60)
  @Metadata(name = "TITLE(英文)", order = 27)
  private String titleEn;

  /*** TITLE(日文) ***/
  @Length(60)
  @Metadata(name = "TITLE(日文)", order = 28)
  private String titleJp;

  /*** DESCRIPTION ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION", order = 29)
  private String description;

  /*** DESCRIPTION(英文) ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION(英文)", order = 30)
  private String descriptionEn;

  /*** DESCRIPTION(日文) ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION(日文)", order = 31)
  private String descriptionJp;

  /*** KEYWORD ***/
  @Length(100)
  @Metadata(name = "KEYWORD", order = 32)
  private String keyword;

  /*** KEYWORD(英文) ***/
  @Length(100)
  @Metadata(name = "KEYWORD(英文)", order = 33)
  private String keywordEn;

  /*** KEYWORD(日文) ***/
  @Length(100)
  @Metadata(name = "KEYWORD(日文)", order = 34)
  private String keywordJp;
  
  // 2014/05/14 京东对应 ob add start
  @Length(100)
  @Metadata(name = "京东分类编号", order = 35)
  private String categoryIdJd;
  // 2014/05/14 京东对应 ob add end

  // 20130808 txw add end
  /**
   * @return the keywordCn1
   */
  public String getKeywordCn1() {
    return keywordCn1;
  }

  /**
   * @param keywordCn1
   *          the keywordCn1 to set
   */
  public void setKeywordCn1(String keywordCn1) {
    this.keywordCn1 = keywordCn1;
  }

  /**
   * @return the keywordJp1
   */
  public String getKeywordJp1() {
    return keywordJp1;
  }

  /**
   * @param keywordJp1
   *          the keywordJp1 to set
   */
  public void setKeywordJp1(String keywordJp1) {
    this.keywordJp1 = keywordJp1;
  }

  /**
   * @return the keywordEn1
   */
  public String getKeywordEn1() {
    return keywordEn1;
  }

  /**
   * @param keywordEn1
   *          the keywordEn1 to set
   */
  public void setKeywordEn1(String keywordEn1) {
    this.keywordEn1 = keywordEn1;
  }

  /** PC用カテゴリ名称(日本语) */
  @Required
  @Length(100)
  @Metadata(name = "PC用カテゴリ名称(日本语)", order = 20)
  private String categoryNamePcJp;

  // 20120514 tuxinwei add end

  /**
   * カテゴリコードを取得します
   * 
   * @return カテゴリコード
   */
  public String getCategoryCode() {
    return this.categoryCode;
  }

  /**
   * @return the keywordCn2
   */
  public String getKeywordCn2() {
    return keywordCn2;
  }

  /**
   * @param keywordCn2
   *          the keywordCn2 to set
   */
  public void setKeywordCn2(String keywordCn2) {
    this.keywordCn2 = keywordCn2;
  }

  /**
   * @return the keywordJp2
   */
  public String getKeywordJp2() {
    return keywordJp2;
  }

  /**
   * @param keywordJp2
   *          the keywordJp2 to set
   */
  public void setKeywordJp2(String keywordJp2) {
    this.keywordJp2 = keywordJp2;
  }

  /**
   * @return the keywordEn2
   */
  public String getKeywordEn2() {
    return keywordEn2;
  }

  /**
   * @param keywordEn2
   *          the keywordEn2 to set
   */
  public void setKeywordEn2(String keywordEn2) {
    this.keywordEn2 = keywordEn2;
  }

  /**
   * PC用カテゴリ名称を取得します
   * 
   * @return PC用カテゴリ名称
   */
  public String getCategoryNamePc() {
    return this.categoryNamePc;
  }

  /**
   * 携帯用カテゴリ名称を取得します
   * 
   * @return 携帯用カテゴリ名称
   */
  public String getCategoryNameMobile() {
    return this.categoryNameMobile;
  }

  /**
   * 親カテゴリコードを取得します
   * 
   * @return 親カテゴリコード
   */
  public String getParentCategoryCode() {
    return this.parentCategoryCode;
  }

  /**
   * パスを取得します
   * 
   * @return パス
   */
  public String getPath() {
    return this.path;
  }

  /**
   * 階層を取得します
   * 
   * @return 階層
   */
  public Long getDepth() {
    return this.depth;
  }

  /**
   * 表示順を取得します
   * 
   * @return 表示順
   */
  public Long getDisplayOrder() {
    return this.displayOrder;
  }

  /**
   * PC商品件数を取得します
   * 
   * @return PC商品件数
   */
  public Long getCommodityCountPc() {
    return this.commodityCountPc;
  }

  /**
   * 携帯商品件数を取得します
   * 
   * @return 携帯商品件数
   */
  public Long getCommodityCountMobile() {
    return this.commodityCountMobile;
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
   * PC用カテゴリ名称を設定します
   * 
   * @param val
   *          PC用カテゴリ名称
   */
  public void setCategoryNamePc(String val) {
    this.categoryNamePc = val;
  }

  /**
   * 携帯用カテゴリ名称を設定します
   * 
   * @param val
   *          携帯用カテゴリ名称
   */
  public void setCategoryNameMobile(String val) {
    this.categoryNameMobile = val;
  }

  /**
   * 親カテゴリコードを設定します
   * 
   * @param val
   *          親カテゴリコード
   */
  public void setParentCategoryCode(String val) {
    this.parentCategoryCode = val;
  }

  /**
   * パスを設定します
   * 
   * @param val
   *          パス
   */
  public void setPath(String val) {
    this.path = val;
  }

  /**
   * 階層を設定します
   * 
   * @param val
   *          階層
   */
  public void setDepth(Long val) {
    this.depth = val;
  }

  /**
   * 表示順を設定します
   * 
   * @param val
   *          表示順
   */
  public void setDisplayOrder(Long val) {
    this.displayOrder = val;
  }

  /**
   * PC商品件数を設定します
   * 
   * @param val
   *          PC商品件数
   */
  public void setCommodityCountPc(Long val) {
    this.commodityCountPc = val;
  }

  /**
   * 携帯商品件数を設定します
   * 
   * @param val
   *          携帯商品件数
   */
  public void setCommodityCountMobile(Long val) {
    this.commodityCountMobile = val;
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
   * @param categoryIdTmall
   *          the categoryIdTmall to set
   */
  public void setCategoryIdTmall(String categoryIdTmall) {
    this.categoryIdTmall = categoryIdTmall;
  }

  /**
   * @return the categoryIdTmall
   */
  public String getCategoryIdTmall() {
    return categoryIdTmall;
  }

  /**
   * @return the metaKeyword
   */
  public String getMetaKeyword() {
    return metaKeyword;
  }

  /**
   * @param metaKeyword
   *          the metaKeyword to set
   */
  public void setMetaKeyword(String metaKeyword) {
    this.metaKeyword = metaKeyword;
  }

  /**
   * @return the metaDescription
   */
  public String getMetaDescription() {
    return metaDescription;
  }

  /**
   * @param metaDescription
   *          the metaDescription to set
   */
  public void setMetaDescription(String metaDescription) {
    this.metaDescription = metaDescription;
  }

  /**
   * @param categoryNamePcJp
   *          the categoryNamePcJp to set
   */
  public void setCategoryNamePcJp(String categoryNamePcJp) {
    this.categoryNamePcJp = categoryNamePcJp;
  }

  /**
   * @return the categoryNamePcJp
   */
  public String getCategoryNamePcJp() {
    return categoryNamePcJp;
  }

  /**
   * @param categoryNamePcEn
   *          the categoryNamePcEn to set
   */
  public void setCategoryNamePcEn(String categoryNamePcEn) {
    this.categoryNamePcEn = categoryNamePcEn;
  }

  /**
   * @return the categoryNamePcEn
   */
  public String getCategoryNamePcEn() {
    return categoryNamePcEn;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @return the titleEn
   */
  public String getTitleEn() {
    return titleEn;
  }

  /**
   * @return the titleJp
   */
  public String getTitleJp() {
    return titleJp;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the descriptionEn
   */
  public String getDescriptionEn() {
    return descriptionEn;
  }

  /**
   * @return the descriptionJp
   */
  public String getDescriptionJp() {
    return descriptionJp;
  }

  /**
   * @return the keyword
   */
  public String getKeyword() {
    return keyword;
  }

  /**
   * @return the keywordEn
   */
  public String getKeywordEn() {
    return keywordEn;
  }

  /**
   * @return the keywordJp
   */
  public String getKeywordJp() {
    return keywordJp;
  }

  /**
   * @param title
   *          the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @param titleEn
   *          the titleEn to set
   */
  public void setTitleEn(String titleEn) {
    this.titleEn = titleEn;
  }

  /**
   * @param titleJp
   *          the titleJp to set
   */
  public void setTitleJp(String titleJp) {
    this.titleJp = titleJp;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @param descriptionEn
   *          the descriptionEn to set
   */
  public void setDescriptionEn(String descriptionEn) {
    this.descriptionEn = descriptionEn;
  }

  /**
   * @param descriptionJp
   *          the descriptionJp to set
   */
  public void setDescriptionJp(String descriptionJp) {
    this.descriptionJp = descriptionJp;
  }

  /**
   * @param keyword
   *          the keyword to set
   */
  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  /**
   * @param keywordEn
   *          the keywordEn to set
   */
  public void setKeywordEn(String keywordEn) {
    this.keywordEn = keywordEn;
  }

  /**
   * @param keywordJp
   *          the keywordJp to set
   */
  public void setKeywordJp(String keywordJp) {
    this.keywordJp = keywordJp;
  }

  /**
   * @return the categoryIdJd
   */
  public String getCategoryIdJd() {
    return categoryIdJd;
  }

  /**
   * @param categoryIdJd the categoryIdJd to set
   */
  public void setCategoryIdJd(String categoryIdJd) {
    this.categoryIdJd = categoryIdJd;
  }

}
