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
 * 「タグ(Brand)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class Brand implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 商店编号 */
  @Required
  @Length(16)
  @Metadata(name = "商店编号", order = 1)
  private String shopCode;

  /** 品牌编号 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "品牌编号", order = 2)
  private String brandCode;

  /** 品牌名称 */
  @Required
  @Length(50)
  @Metadata(name = "品牌名称", order = 3)
  private String brandName;

  /** 品牌ABBR名 */
  @Length(50)
  @Metadata(name = "品牌名称略称", order = 4)
  private String brandNameAbbr;

  /** 品牌说明 */
  @Length(1000)
  @Metadata(name = "品牌说明", order = 5)
  private String brandDescription;

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

  /** 品牌英文名称 */
  @Length(50)
  @Metadata(name = "品牌英文名称", order = 11)
  private String brandEnglishName;

  /** 淘宝品牌ID */
  @Length(16)
  @Metadata(name = "淘宝品牌ID", order = 12)
  private String tmallBrandCode;

  /** 淘宝品牌名 */
  @Length(50)
  @Metadata(name = "淘宝品牌名", order = 13)
  private String tmallBrandName;

  @Length(2)
  @Metadata(name = "品牌区分", order = 14)
  private Long brandType;

  // 20120515 tuxinwei add start
  /** 品牌日文名称 */
  @Length(50)
  @Metadata(name = "品牌日文名称", order = 15)
  private String brandJapaneseName;

  // 20120515 tuxinwei add end

  // 20120524 tuxinwei add start
  /** 品牌说明(英文) */
  @Length(1000)
  @Metadata(name = "品牌说明(英文)", order = 16)
  private String brandDescriptionEn;

  /** 品牌说明(日文) */
  @Length(1000)
  @Metadata(name = "品牌说明(日文)", order = 17)
  private String brandDescriptionJp;

  // 20120524 tuxinwei add end
  // add by zhangzhengtao 20130527 start
  /** 検索Keyword（中文）1 */
  @Metadata(name = " 検索Keyword（中文）1", order = 18)
  private String keywordCn1;

  /** 検索Keyword（日文）2 */
  @Metadata(name = " 検索Keyword（日文）1", order = 19)
  private String keywordJp1;

  /** 検索Keyword（英文）2 */
  @Metadata(name = " 検索Keyword（英文）1", order = 20)
  private String keywordEn1;

  /** 検索Keyword（中文）2 */
  @Metadata(name = " 検索Keyword（中文）2", order = 21)
  private String keywordCn2;

  /** 検索Keyword（日文）2 */
  @Metadata(name = " 検索Keyword（日文）2", order = 22)
  private String keywordJp2;

  /** 検索Keyword（英文）2 */
  @Metadata(name = " 検索Keyword（英文）2", order = 23)
  private String keywordEn2;

  // add by zhangzhengtao 20130527 end
  // 20130808 txw add start
  /*** TITLE ***/
  @Length(60)
  @Metadata(name = "TITLE", order = 24)
  private String title;

  /*** TITLE(英文) ***/
  @Length(60)
  @Metadata(name = "TITLE(英文)", order = 25)
  private String titleEn;

  /*** TITLE(日文) ***/
  @Length(60)
  @Metadata(name = "TITLE(日文)", order = 26)
  private String titleJp;

  /*** DESCRIPTION ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION", order = 27)
  private String description;

  /*** DESCRIPTION(英文) ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION(英文)", order = 28)
  private String descriptionEn;

  /*** DESCRIPTION(日文) ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION(日文)", order = 29)
  private String descriptionJp;

  /*** KEYWORD ***/
  @Length(100)
  @Metadata(name = "KEYWORD", order = 30)
  private String keyword;

  /*** KEYWORD(英文) ***/
  @Length(100)
  @Metadata(name = "KEYWORD(英文)", order = 31)
  private String keywordEn;

  /*** KEYWORD(日文) ***/
  @Length(100)
  @Metadata(name = "KEYWORD(日文)", order = 32)
  private String keywordJp;

  // 20130808 txw add end

  /**
   * @return the brandType
   */
  public Long getBrandType() {
    return brandType;
  }

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
   * @param brandType
   *          the brandType to set
   */
  public void setBrandType(Long brandType) {
    this.brandType = brandType;
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
   * タグコードを取得します
   * 
   * @return タグコード
   */
  public String getBrandCode() {
    return this.brandCode;
  }

  /**
   * タグ名称を取得します
   * 
   * @return タグ名称
   */
  public String getBrandName() {
    return this.brandName;
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
   * ショップコードを設定します
   * 
   * @param val
   *          ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * タグコードを設定します
   * 
   * @param val
   *          タグコード
   */
  public void setBrandCode(String val) {
    this.brandCode = val;
  }

  /**
   * タグ名称を設定します
   * 
   * @param val
   *          タグ名称
   */
  public void setBrandName(String val) {
    this.brandName = val;
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
   * @param brandNameAbbr
   *          the brandNameAbbr to set
   */
  public void setBrandNameAbbr(String brandNameAbbr) {
    this.brandNameAbbr = brandNameAbbr;
  }

  /**
   * @return the brandNameAbbr
   */
  public String getBrandNameAbbr() {
    return brandNameAbbr;
  }

  /**
   * @param brandDescription
   *          the brandDescription to set
   */
  public void setBrandDescription(String brandDescription) {
    this.brandDescription = brandDescription;
  }

  /**
   * @return the brandDescription
   */
  public String getBrandDescription() {
    return brandDescription;
  }

  /**
   * @param brandEnglishName
   *          the brandEnglishName to set
   */
  public void setBrandEnglishName(String brandEnglishName) {
    this.brandEnglishName = brandEnglishName;
  }

  /**
   * @return the brandEnglishName
   */
  public String getBrandEnglishName() {
    return brandEnglishName;
  }

  /**
   * @param tmallBrandName
   *          the tmallBrandName to set
   */
  public void setTmallBrandName(String tmallBrandName) {
    this.tmallBrandName = tmallBrandName;
  }

  /**
   * @return the tmallBrandName
   */
  public String getTmallBrandName() {
    return tmallBrandName;
  }

  /**
   * @param tmallBrandCode
   *          the tmallBrandCode to set
   */
  public void setTmallBrandCode(String tmallBrandCode) {
    this.tmallBrandCode = tmallBrandCode;
  }

  /**
   * @return the tmallBrandCode
   */
  public String getTmallBrandCode() {
    return tmallBrandCode;
  }

  /**
   * @return the brandJapaneseName
   */
  public String getBrandJapaneseName() {
    return brandJapaneseName;
  }

  /**
   * @param brandJapaneseName
   *          the brandJapaneseName to set
   */
  public void setBrandJapaneseName(String brandJapaneseName) {
    this.brandJapaneseName = brandJapaneseName;
  }

  /**
   * @return the brandDescriptionEn
   */
  public String getBrandDescriptionEn() {
    return brandDescriptionEn;
  }

  /**
   * @param brandDescriptionEn
   *          the brandDescriptionEn to set
   */
  public void setBrandDescriptionEn(String brandDescriptionEn) {
    this.brandDescriptionEn = brandDescriptionEn;
  }

  /**
   * @return the brandDescriptionJp
   */
  public String getBrandDescriptionJp() {
    return brandDescriptionJp;
  }

  /**
   * @param brandDescriptionJp
   *          the brandDescriptionJp to set
   */
  public void setBrandDescriptionJp(String brandDescriptionJp) {
    this.brandDescriptionJp = brandDescriptionJp;
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

}
