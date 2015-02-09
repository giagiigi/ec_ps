//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「アクセスログ(ACCESS_LOG)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class CCommodityHeaderImport implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "商品コード", order = 2)
  private String commodityCode;

  /** アクセス時間 */
  @Required
  @Length(50)
  @Metadata(name = "商品名称", order = 3)
  private String commodityName;

  /** クライアントグループ */
  @Length(50)
  @Metadata(name = "商品名称英字", order = 4)
  private String commodityNameEn;

  /** ページビュー件数 */
  @Length(24)
  @Metadata(name = "代表SKUコード", order = 5)
  private String representSkuCode;

  /** ビジター数 */
  @Length(13)
  @Metadata(name = "代表SKU単価", order = 6)
  private BigDecimal representSkuUnitPrice;

  /** 購入者数 */
  @Length(1000)
  @Metadata(name = "商品説明1", order = 7)
  private String commodityDescription1;

  @Length(2000)
  @Metadata(name = "商品説明2", order = 8)
  private String commodityDescription2;

  @Length(1000)
  @Metadata(name = "商品説明3", order = 9)
  private String commodityDescription3;

  @Length(500)
  @Metadata(name = "商品説明(一覧用）", order = 10)
  private String commodityDescriptionShort;

  @Length(500)
  @Metadata(name = "商品検索ワード", order = 11)
  private String commoditySearchWords;

  @Metadata(name = "販売開始日時", order = 12)
  private String saleStartDatetime;

  @Metadata(name = "販売終了日時", order = 13)
  private String saleEndDatetime;

  @Metadata(name = "特別価格開始日時", order = 14)
  private String discountPriceStartDatetime;

  @Metadata(name = "特別価格終了日時", order = 15)
  private String discountPriceEndDatetime;

  @Length(20)
  @Metadata(name = "規格1名称ID(TMALLの属性ID）", order = 16)
  private String standard1Id;

  @Length(20)
  @Metadata(name = "規格1名称", order = 17)
  private String standard1Name;

  @Length(20)
  @Metadata(name = "規格2名称ID(TMALLの属性ID）", order = 18)
  private String standard2Id;

  @Length(20)
  @Metadata(name = "規格2名称", order = 19)
  private String standard2Name;

  @Required
  @Length(1)
  @Metadata(name = "販売フラグ", order = 20)
  private Long saleFlgEc;

  @Required
  @Length(1)
  @Metadata(name = "返品不可フラグ", order = 21)
  private Long returnFlg;

  @Length(1)
  @Metadata(name = "ワーニング区分", order = 22)
  private String warningFlag;

  @Length(2)
  @Metadata(name = "リードタイム", order = 23)
  private Long leadTime;

  @Length(5)
  @Metadata(name = "セール区分", order = 24)
  private String saleFlag;

  @Length(5)
  @Metadata(name = "特集区分", order = 25)
  private String specFlag;

  @Length(16)
  @Metadata(name = "ブランドコード", order = 26)
  private String brandCode;

  @Length(16)
  @Metadata(name = "TMALL商品ID", order = 27)
  private Long tmallCommodityId;

  @Length(10)
  @Metadata(name = "TMALL代表SKU単価", order = 28)
  private BigDecimal tmallRepresentSkuPrice;

  @Length(16)
  @Metadata(name = "TMALLのカテゴリID", order = 29)
  private Long tmallCategoryId;

  @Length(8)
  @Metadata(name = "取引先コード", order = 30)
  private String supplierCode;

  @Length(8)
  @Metadata(name = "仕入担当者コード", order = 31)
  private String buyerCode;

  @Length(20)
  @Metadata(name = "産地", order = 33)
  private String originalPlace;

  @Length(20)
  @Metadata(name = "成分名1", order = 34)
  private String ingredientName1;

  @Length(10)
  @Metadata(name = "成分量1", order = 35)
  private String ingredientVal1;

  @Length(20)
  @Metadata(name = "成分名2", order = 36)
  private String ingredientName2;

  @Length(10)
  @Metadata(name = "成分量2", order = 37)
  private String ingredientVal2;

  @Length(20)
  @Metadata(name = "成分名3", order = 38)
  private String ingredientName3;

  @Length(10)
  @Metadata(name = "成分量3", order = 39)
  private String ingredientVal3;

  @Length(20)
  @Metadata(name = "成分名4", order = 40)
  private String ingredientName4;

  @Length(10)
  @Metadata(name = "成分量4", order = 41)
  private String ingredientVal4;

  @Length(20)
  @Metadata(name = "成分名5", order = 42)
  private String ingredientName5;

  @Length(10)
  @Metadata(name = "成分量5", order = 43)
  private String ingredientVal5;

  @Length(20)
  @Metadata(name = "成分名6", order = 44)
  private String ingredientName6;

  @Length(10)
  @Metadata(name = "成分量6", order = 45)
  private String ingredientVal6;

  @Length(20)
  @Metadata(name = "成分名7", order = 46)
  private String ingredientName7;

  @Length(10)
  @Metadata(name = "成分量7", order = 47)
  private String ingredientVal7;

  @Length(20)
  @Metadata(name = "成分名8", order = 48)
  private String ingredientName8;

  @Length(10)
  @Metadata(name = "成分量8", order = 49)
  private String ingredientVal8;

  @Length(20)
  @Metadata(name = "成分名9", order = 50)
  private String ingredientName9;

  @Length(10)
  @Metadata(name = "成分量9", order = 51)
  private String ingredientVal9;

  @Length(20)
  @Metadata(name = "成分名10", order = 52)
  private String ingredientName10;

  @Length(10)
  @Metadata(name = "成分量10", order = 53)
  private String ingredientVal10;

  @Length(20)
  @Metadata(name = "成分名11", order = 55)
  private String ingredientName11;

  @Length(10)
  @Metadata(name = "成分量11", order = 56)
  private String ingredientVal11;

  @Length(20)
  @Metadata(name = "成分名12", order = 57)
  private String ingredientName12;

  @Length(10)
  @Metadata(name = "成分量12", order = 58)
  private String ingredientVal12;

  @Length(20)
  @Metadata(name = "成分名13", order = 59)
  private String ingredientName13;

  @Length(10)
  @Metadata(name = "成分量13", order = 60)
  private String ingredientVal13;

  @Length(20)
  @Metadata(name = "成分名14", order = 61)
  private String ingredientName14;

  @Length(10)
  @Metadata(name = "成分量14", order = 62)
  private String ingredientVal14;

  @Length(20)
  @Metadata(name = "成分名15", order = 63)
  private String ingredientName15;

  @Length(10)
  @Metadata(name = "成分量15", order = 64)
  private String ingredientVal15;

  @Length(20)
  @Metadata(name = "原材料1", order = 65)
  private String material1;

  @Length(20)
  @Metadata(name = "原材料2", order = 66)
  private String material2;

  @Length(20)
  @Metadata(name = "原材料3", order = 67)
  private String material3;

  @Length(20)
  @Metadata(name = "原材料4", order = 68)
  private String material4;

  @Length(20)
  @Metadata(name = "原材料5", order = 69)
  private String material5;

  @Length(20)
  @Metadata(name = "原材料6", order = 70)
  private String material6;

  @Length(20)
  @Metadata(name = "原材料7", order = 71)
  private String material7;

  @Length(20)
  @Metadata(name = "原材料8", order = 72)
  private String material8;

  @Length(20)
  @Metadata(name = "原材料9", order = 73)
  private String material9;

  @Length(20)
  @Metadata(name = "原材料10", order = 74)
  private String material10;

  @Length(20)
  @Metadata(name = "原材料11", order = 75)
  private String material11;

  @Length(20)
  @Metadata(name = "原材料12", order = 76)
  private String material12;

  @Length(20)
  @Metadata(name = "原材料13", order = 77)
  private String material13;

  @Length(20)
  @Metadata(name = "原材料14", order = 78)
  private String material14;

  @Length(20)
  @Metadata(name = "原材料15", order = 79)
  private String material15;

  @Required
  @Length(1)
  @Metadata(name = "商品期限管理フラグ", order = 80)
  private Long shelfLifeFlag;

  @Length(6)
  @Metadata(name = "保管日数", order = 81)
  private Long shelfLifeDays;

  @Length(4)
  @Metadata(name = "入库效期", order = 82)
  private Long inBoundLifeDays;

  @Length(4)
  @Metadata(name = "出库效期", order = 83)
  private Long outBoundLifeDays;

  @Length(4)
  @Metadata(name = "失效预警", order = 84)
  private Long shelfLifeAlertDays;

  @Length(16)
  @Metadata(name = "生产许可证号", order = 119)
  private String foodSecurityPrdLicenseNo;

  @Length(25)
  @Metadata(name = "产品标准号", order = 120)
  private String foodSecurityDesignCode;

  @Length(50)
  @Metadata(name = "厂名", order = 121)
  private String foodSecurityFactory;

  @Length(50)
  @Metadata(name = "厂址", order = 122)
  private String foodSecurityFactorySite;

  @Length(25)
  @Metadata(name = "厂家联系方式", order = 123)
  private String foodSecurityContact;

  @Length(100)
  @Metadata(name = "配料表", order = 124)
  private String foodSecurityMix;

  @Length(25)
  @Metadata(name = "储藏方法", order = 125)
  private String foodSecurityPlanStorage;

  @Length(15)
  @Metadata(name = "保质期", order = 126)
  private String foodSecurityPeriod;

  @Length(50)
  @Metadata(name = "食品添加剂", order = 127)
  private String foodSecurityFoodAdditive;

  @Length(25)
  @Metadata(name = "供货商", order = 128)
  private String foodSecuritySupplier;

  @Metadata(name = "生产开始日期", order = 129)
  private String foodSecurityProductDateStart;

  @Metadata(name = "生产结束日期", order = 130)
  private String foodSecurityProductDateEnd;

  @Metadata(name = "进货开始日期", order = 131)
  private String foodSecurityStockDateStart;

  @Metadata(name = "进货结束日期", order = 132)
  private String foodSecurityStockDateEnd;

  @Required
  @Length(1)
  @Metadata(name = "大物フラグ", order = 85)
  private Long bigFlag;

  /** 検索用カテゴリパス */
  @Length(256)
  @Metadata(name = "検索用カテゴリパス", order = 86)
  private String categorySearchPath;

  /** 商品の分類属性値 */
  @Length(256)
  @Metadata(name = "商品の分類属性値", order = 87)
  private String categoryAttributeValue;

  @Metadata(name = "ECへの同期時間", order = 88)
  private Date syncTimeEc;

  @Metadata(name = "TMALLへの同期時間", order = 89)
  private Date syncTimeTmall;

  @Required
  @Length(1)
  @Metadata(name = "ECへの同期フラグ(0:同期不可、1同期可能、2同期済み)", order = 90)
  private Long syncFlagEc;

  @Required
  @Length(1)
  @Metadata(name = "TMALLへの同期フラグ(0:同期不可、1同期可能、2同期済み)", order = 91)
  private Long syncFlagTmall;

  @Length(100)
  @Metadata(name = "ECへの同期ユーザー", order = 92)
  private String syncUserEc;

  @Length(100)
  @Metadata(name = "TMALLへの同期ユーザー", order = 93)
  private String syncUserTmall;

  @Required
  @Length(1)
  @Metadata(name = "ERP取込用データ対象フラグ(0：対象外、1：対象）", order = 94)
  private Long exportFlagErp;

  @Required
  @Length(1)
  @Metadata(name = "WMS取込用データ対象フラグ(0：対象外、1：対象）", order = 95)
  private Long exportFlagWms;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 96)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 97)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 98)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 99)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 100)
  private Date updatedDatetime;

  // 20120305 os013 add start
  @Length(1)
  @Metadata(name = "供货商换货", order = 101)
  private String returnFlgCust;

  @Length(1)
  @Metadata(name = "供货商退货", order = 102)
  private String returnFlgSupp;

  @Length(1)
  @Metadata(name = "顾客退货", order = 103)
  private String changeFlgSupp;

  @Length(50)
  @Metadata(name = "商品名称日文", order = 104)
  private String commodityNameJp;

  @Length(1000)
  @Metadata(name = "商品説明1英文", order = 105)
  private String commodityDescription1En;

  @Length(1000)
  @Metadata(name = "商品説明1日文", order = 106)
  private String commodityDescription1Jp;

  @Length(2000)
  @Metadata(name = "商品説明2英文", order = 107)
  private String commodityDescription2En;

  @Length(2000)
  @Metadata(name = "商品説明2日文", order = 108)
  private String commodityDescription2Jp;

  @Length(1000)
  @Metadata(name = "商品説明3英文", order = 109)
  private String commodityDescription3En;

  @Length(1000)
  @Metadata(name = "商品説明3日文", order = 110)
  private String commodityDescription3Jp;

  @Length(500)
  @Metadata(name = "商品説明(一覧用）英文", order = 111)
  private String commodityDescriptionShortEn;

  @Length(500)
  @Metadata(name = "商品説明(一覧用）日文", order = 112)
  private String commodityDescriptionShortJp;

  @Length(20)
  @Metadata(name = "規格1名称英文", order = 113)
  private String standard1NameEn;

  @Length(20)
  @Metadata(name = "規格1名称日文", order = 114)
  private String standard1NameJp;

  @Length(20)
  @Metadata(name = "規格2名称英文", order = 115)
  private String standard2NameEn;

  @Length(20)
  @Metadata(name = "規格2名称日文", order = 116)
  private String standard2NameJp;

  // add by cs_yuli 20120608 start
  @Length(50)
  @Metadata(name = "産地英文", order = 117)
  private String originalPlaceEn;

  @Length(50)
  @Metadata(name = "産地日文", order = 118)
  private String originalPlaceJp;

  // add by cs_yuli 20120608 end

  @Length(1)
  @Metadata(name = "商品区分", order = 119)
  private Long commodityType;

  @Length(1)
  @Metadata(name = "tmall满就送(赠品标志)", order = 120)
  private Long tmallMjsFlg;

  @Length(1)
  @Metadata(name = "进口商品区分", order = 121)
  private Long importCommodityType;

  @Length(1)
  @Metadata(name = "清仓商品区分", order = 122)
  private Long clearCommodityType;

  @Length(1)
  @Metadata(name = "Asahi商品区分", order = 123)
  private Long reserveCommodityType1;

  @Length(1)
  @Metadata(name = "hot区分", order = 124)
  private Long reserveCommodityType2;

  @Length(1)
  @Metadata(name = "商品展示区分", order = 125)
  private Long reserveCommodityType3;

  @Length(16)
  @Metadata(name = "産地编号", order = 126)
  private String originalCode;

  @Length(1)
  @Metadata(name = "预留商品区分1", order = 127)
  private Long newReserveCommodityType1;

  @Length(1)
  @Metadata(name = "预留商品区分2", order = 128)
  private Long newReserveCommodityType2;

  @Length(1)
  @Metadata(name = "预留商品区分3", order = 129)
  private Long newReserveCommodityType3;

  @Length(1)
  @Metadata(name = "预留商品区分4", order = 130)
  private Long newReserveCommodityType4;

  @Length(1)
  @Metadata(name = "预留商品区分5", order = 131)
  private Long newReserveCommodityType5;

  @Length(1)
  @Metadata(name = "English热卖标志", order = 132)
  private Long hotFlgEn;

  @Length(1)
  @Metadata(name = "Japan热卖标志", order = 133)
  private Long hotFlgJp;
  
  @Length(16)
  @Metadata(name = "原商品编号", order = 134)
  private String originalCommodityCode;
  
  @Digit
  @Metadata(name = "组合数", order = 135)
  private Long combinationAmount;
  
  @Length(500)
  @Metadata(name = "TMALL搜索关键字", order = 136)
  private String tmallCommoditySearchWords;

  // 20130808 txw add start
  /*** TITLE ***/
  @Length(60)
  @Metadata(name = "TITLE", order = 134)
  private String title;

  /*** TITLE(英文) ***/
  @Length(60)
  @Metadata(name = "TITLE(英文)", order = 135)
  private String titleEn;

  /*** TITLE(日文) ***/
  @Length(60)
  @Metadata(name = "TITLE(日文)", order = 136)
  private String titleJp;

  /*** DESCRIPTION ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION", order = 137)
  private String description;

  /*** DESCRIPTION(英文) ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION(英文)", order = 138)
  private String descriptionEn;

  /*** DESCRIPTION(日文) ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION(日文)", order = 139)
  private String descriptionJp;

  /*** KEYWORD ***/
  @Length(100)
  @Metadata(name = "KEYWORD", order = 140)
  private String keyword;

  /*** KEYWORD(英文) ***/
  @Length(100)
  @Metadata(name = "KEYWORD(英文)", order = 141)
  private String keywordEn;

  /*** KEYWORD(日文) ***/
  @Length(100)
  @Metadata(name = "KEYWORD(日文)", order = 142)
  private String keywordJp;

  // 20130808 txw add end
  
  @Metadata(name = "検索Keyword中文", order = 143)
  private String keywordCn2;
  
  @Metadata(name = "検索Keyword日文", order = 144)
  private String keywordJp2;
  
  @Metadata(name = "検索Keyword英文", order = 145)
  private String keywordEn2;
  
  @Length(1)
  @Metadata(name = "套装商品区分", order = 146)
  private Long setCommodityFlg;

  // 2014/04/28 京东WBS对应 ob_卢 add start
  @Length(45)
  @Metadata(name = "广告词", order = 147)
  private String advertContent;
  
  @Length(1)
  @Metadata(name = "京东同期FLG", order = 148)
  private Long syncFlagJd;
  
  @Length(16)
  @Metadata(name = "京东类目ID", order = 149)
  private Long jdCategoryId;
  // 2014/04/28 京东WBS对应 ob_卢 add end
  /**
   * @return the newReserveCommodityType1
   */
  public Long getNewReserveCommodityType1() {
    return newReserveCommodityType1;
  }

  /**
   * @param newReserveCommodityType1
   *          the newReserveCommodityType1 to set
   */
  public void setNewReserveCommodityType1(Long newReserveCommodityType1) {
    this.newReserveCommodityType1 = newReserveCommodityType1;
  }

  /**
   * @return the newReserveCommodityType2
   */
  public Long getNewReserveCommodityType2() {
    return newReserveCommodityType2;
  }

  /**
   * @param newReserveCommodityType2
   *          the newReserveCommodityType2 to set
   */
  public void setNewReserveCommodityType2(Long newReserveCommodityType2) {
    this.newReserveCommodityType2 = newReserveCommodityType2;
  }

  /**
   * @return the newReserveCommodityType3
   */
  public Long getNewReserveCommodityType3() {
    return newReserveCommodityType3;
  }

  /**
   * @param newReserveCommodityType3
   *          the newReserveCommodityType3 to set
   */
  public void setNewReserveCommodityType3(Long newReserveCommodityType3) {
    this.newReserveCommodityType3 = newReserveCommodityType3;
  }

  /**
   * @return the newReserveCommodityType4
   */
  public Long getNewReserveCommodityType4() {
    return newReserveCommodityType4;
  }

  /**
   * @param newReserveCommodityType4
   *          the newReserveCommodityType4 to set
   */
  public void setNewReserveCommodityType4(Long newReserveCommodityType4) {
    this.newReserveCommodityType4 = newReserveCommodityType4;
  }

  /**
   * @return the newReserveCommodityType5
   */
  public Long getNewReserveCommodityType5() {
    return newReserveCommodityType5;
  }

  /**
   * @param newReserveCommodityType5
   *          the newReserveCommodityType5 to set
   */
  public void setNewReserveCommodityType5(Long newReserveCommodityType5) {
    this.newReserveCommodityType5 = newReserveCommodityType5;
  }

  /**
   * @return the originalCode
   */
  public String getOriginalCode() {
    return originalCode;
  }

  /**
   * @param originalCode
   *          the originalCode to set
   */
  public void setOriginalCode(String originalCode) {
    this.originalCode = originalCode;
  }

  public Long getTmallMjsFlg() {
    return tmallMjsFlg;
  }

  public void setTmallMjsFlg(Long tmallMjsFlg) {
    this.tmallMjsFlg = tmallMjsFlg;
  }

  public Long getCommodityType() {
    return commodityType;
  }

  public void setCommodityType(Long commodityType) {
    this.commodityType = commodityType;
  }

  public String getStandard1NameEn() {
    return standard1NameEn;
  }

  public String getFoodSecurityPrdLicenseNo() {
    return foodSecurityPrdLicenseNo;
  }

  public void setFoodSecurityPrdLicenseNo(String foodSecurityPrdLicenseNo) {
    this.foodSecurityPrdLicenseNo = foodSecurityPrdLicenseNo;
  }

  public String getFoodSecurityDesignCode() {
    return foodSecurityDesignCode;
  }

  public void setFoodSecurityDesignCode(String foodSecurityDesignCode) {
    this.foodSecurityDesignCode = foodSecurityDesignCode;
  }

  public String getFoodSecurityFactory() {
    return foodSecurityFactory;
  }

  public void setFoodSecurityFactory(String foodSecurityFactory) {
    this.foodSecurityFactory = foodSecurityFactory;
  }

  public String getFoodSecurityFactorySite() {
    return foodSecurityFactorySite;
  }

  public void setFoodSecurityFactorySite(String foodSecurityFactorySite) {
    this.foodSecurityFactorySite = foodSecurityFactorySite;
  }

  public String getFoodSecurityContact() {
    return foodSecurityContact;
  }

  public void setFoodSecurityContact(String foodSecurityContact) {
    this.foodSecurityContact = foodSecurityContact;
  }

  public String getFoodSecurityMix() {
    return foodSecurityMix;
  }

  public void setFoodSecurityMix(String foodSecurityMix) {
    this.foodSecurityMix = foodSecurityMix;
  }

  public String getFoodSecurityPlanStorage() {
    return foodSecurityPlanStorage;
  }

  public void setFoodSecurityPlanStorage(String foodSecurityPlanStorage) {
    this.foodSecurityPlanStorage = foodSecurityPlanStorage;
  }

  public String getFoodSecurityPeriod() {
    return foodSecurityPeriod;
  }

  public void setFoodSecurityPeriod(String foodSecurityPeriod) {
    this.foodSecurityPeriod = foodSecurityPeriod;
  }

  public String getFoodSecurityFoodAdditive() {
    return foodSecurityFoodAdditive;
  }

  public void setFoodSecurityFoodAdditive(String foodSecurityFoodAdditive) {
    this.foodSecurityFoodAdditive = foodSecurityFoodAdditive;
  }

  public String getFoodSecuritySupplier() {
    return foodSecuritySupplier;
  }

  public void setFoodSecuritySupplier(String foodSecuritySupplier) {
    this.foodSecuritySupplier = foodSecuritySupplier;
  }

  public String getFoodSecurityProductDateStart() {
    return foodSecurityProductDateStart;
  }

  public void setFoodSecurityProductDateStart(String foodSecurityProductDateStart) {
    this.foodSecurityProductDateStart = foodSecurityProductDateStart;
  }

  public String getFoodSecurityProductDateEnd() {
    return foodSecurityProductDateEnd;
  }

  public void setFoodSecurityProductDateEnd(String foodSecurityProductDateEnd) {
    this.foodSecurityProductDateEnd = foodSecurityProductDateEnd;
  }

  public String getFoodSecurityStockDateStart() {
    return foodSecurityStockDateStart;
  }

  public void setFoodSecurityStockDateStart(String foodSecurityStockDateStart) {
    this.foodSecurityStockDateStart = foodSecurityStockDateStart;
  }

  public String getFoodSecurityStockDateEnd() {
    return foodSecurityStockDateEnd;
  }

  public void setFoodSecurityStockDateEnd(String foodSecurityStockDateEnd) {
    this.foodSecurityStockDateEnd = foodSecurityStockDateEnd;
  }

  public void setStandard1NameEn(String standard1NameEn) {
    this.standard1NameEn = standard1NameEn;
  }

  public String getStandard1NameJp() {
    return standard1NameJp;
  }

  public void setStandard1NameJp(String standard1NameJp) {
    this.standard1NameJp = standard1NameJp;
  }

  public String getStandard2NameEn() {
    return standard2NameEn;
  }

  public void setStandard2NameEn(String standard2NameEn) {
    this.standard2NameEn = standard2NameEn;
  }

  public String getStandard2NameJp() {
    return standard2NameJp;
  }

  public void setStandard2NameJp(String standard2NameJp) {
    this.standard2NameJp = standard2NameJp;
  }

  public String getCommodityNameJp() {
    return commodityNameJp;
  }

  public void setCommodityNameJp(String commodityNameJp) {
    this.commodityNameJp = commodityNameJp;
  }

  public String getCommodityDescription1En() {
    return commodityDescription1En;
  }

  public void setCommodityDescription1En(String commodityDescription1En) {
    this.commodityDescription1En = commodityDescription1En;
  }

  public String getCommodityDescription1Jp() {
    return commodityDescription1Jp;
  }

  public void setCommodityDescription1Jp(String commodityDescription1Jp) {
    this.commodityDescription1Jp = commodityDescription1Jp;
  }

  public String getCommodityDescription2En() {
    return commodityDescription2En;
  }

  public void setCommodityDescription2En(String commodityDescription2En) {
    this.commodityDescription2En = commodityDescription2En;
  }

  public String getCommodityDescription2Jp() {
    return commodityDescription2Jp;
  }

  public void setCommodityDescription2Jp(String commodityDescription2Jp) {
    this.commodityDescription2Jp = commodityDescription2Jp;
  }

  public String getCommodityDescription3En() {
    return commodityDescription3En;
  }

  public void setCommodityDescription3En(String commodityDescription3En) {
    this.commodityDescription3En = commodityDescription3En;
  }

  public String getCommodityDescription3Jp() {
    return commodityDescription3Jp;
  }

  public void setCommodityDescription3Jp(String commodityDescription3Jp) {
    this.commodityDescription3Jp = commodityDescription3Jp;
  }

  public String getCommodityDescriptionShortEn() {
    return commodityDescriptionShortEn;
  }

  public void setCommodityDescriptionShortEn(String commodityDescriptionShortEn) {
    this.commodityDescriptionShortEn = commodityDescriptionShortEn;
  }

  public String getCommodityDescriptionShortJp() {
    return commodityDescriptionShortJp;
  }

  public void setCommodityDescriptionShortJp(String commodityDescriptionShortJp) {
    this.commodityDescriptionShortJp = commodityDescriptionShortJp;
  }

  /**
   * @return the returnFlgCust
   */
  public String getReturnFlgCust() {
    return returnFlgCust;
  }

  /**
   * @param returnFlgCust
   *          the returnFlgCust to set
   */
  public void setReturnFlgCust(String returnFlgCust) {
    this.returnFlgCust = returnFlgCust;
  }

  /**
   * @return the returnFlgSupp
   */
  public String getReturnFlgSupp() {
    return returnFlgSupp;
  }

  /**
   * @param returnFlgSupp
   *          the returnFlgSupp to set
   */
  public void setReturnFlgSupp(String returnFlgSupp) {
    this.returnFlgSupp = returnFlgSupp;
  }

  /**
   * @return the changeFlgSupp
   */
  public String getChangeFlgSupp() {
    return changeFlgSupp;
  }

  /**
   * @param changeFlgSupp
   *          the changeFlgSupp to set
   */
  public void setChangeFlgSupp(String changeFlgSupp) {
    this.changeFlgSupp = changeFlgSupp;
  }

  // 20120305 os013 add end
  /**
   * @return the categorySearchPath
   */
  public String getCategorySearchPath() {
    return categorySearchPath;
  }

  /**
   * @param categorySearchPath
   *          the categorySearchPath to set
   */
  public void setCategorySearchPath(String categorySearchPath) {
    this.categorySearchPath = categorySearchPath;
  }

  /**
   * @return the categoryAttributeValue
   */
  public String getCategoryAttributeValue() {
    return categoryAttributeValue;
  }

  /**
   * @param categoryAttributeValue
   *          the categoryAttributeValue to set
   */
  public void setCategoryAttributeValue(String categoryAttributeValue) {
    this.categoryAttributeValue = categoryAttributeValue;
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

  public String getCommodityName() {
    return commodityName;
  }

  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  public String getCommodityNameEn() {
    return commodityNameEn;
  }

  public void setCommodityNameEn(String commodityNameEn) {
    this.commodityNameEn = commodityNameEn;
  }

  public String getRepresentSkuCode() {
    return representSkuCode;
  }

  public void setRepresentSkuCode(String representSkuCode) {
    this.representSkuCode = representSkuCode;
  }

  public BigDecimal getRepresentSkuUnitPrice() {
    return representSkuUnitPrice;
  }

  public void setRepresentSkuUnitPrice(BigDecimal representSkuUnitPrice) {
    this.representSkuUnitPrice = representSkuUnitPrice;
  }

  public String getCommodityDescription1() {
    return commodityDescription1;
  }

  public void setCommodityDescription1(String commodityDescription1) {
    this.commodityDescription1 = commodityDescription1;
  }

  public String getCommodityDescription2() {
    return commodityDescription2;
  }

  public void setCommodityDescription2(String commodityDescription2) {
    this.commodityDescription2 = commodityDescription2;
  }

  public String getCommodityDescription3() {
    return commodityDescription3;
  }

  public void setCommodityDescription3(String commodityDescription3) {
    this.commodityDescription3 = commodityDescription3;
  }

  public String getCommodityDescriptionShort() {
    return commodityDescriptionShort;
  }

  public void setCommodityDescriptionShort(String commodityDescriptionShort) {
    this.commodityDescriptionShort = commodityDescriptionShort;
  }

  public String getCommoditySearchWords() {
    return commoditySearchWords;
  }

  public void setCommoditySearchWords(String commoditySearchWords) {
    this.commoditySearchWords = commoditySearchWords;
  }

  public Date getSaleStartDatetime() {
    if (saleStartDatetime == null) {
      return DateUtil.getMin();
    } else {
      saleStartDatetime = saleStartDatetime.replace("'", "").replaceAll("-", "/");
      return DateUtil.truncateHour(DateUtil.fromString(saleStartDatetime, true));
    }
  }

  public String getSaleStartDatetimeStr() {
    return saleStartDatetime;
  }

  public void setSaleStartDatetime(String saleStartDatetime) {
    this.saleStartDatetime = saleStartDatetime;
  }

  public Date getSaleEndDatetime() {
    if (saleEndDatetime == null) {
      return DateUtil.getMax();
    } else {
      saleEndDatetime = saleEndDatetime.replace("'", "").replaceAll("-", "/");
      return DateUtil.truncateHour(DateUtil.fromString(saleEndDatetime, true));
    }
  }

  public String getSaleEndDatetimeStr() {
    return saleEndDatetime;
  }

  /**
   * @return the tmallRepresentSkuPrice
   */
  public BigDecimal getTmallRepresentSkuPrice() {
    return tmallRepresentSkuPrice;
  }

  /**
   * @param tmallRepresentSkuPrice
   *          the tmallRepresentSkuPrice to set
   */
  public void setTmallRepresentSkuPrice(BigDecimal tmallRepresentSkuPrice) {
    this.tmallRepresentSkuPrice = tmallRepresentSkuPrice;
  }

  public void setSaleEndDatetime(String saleEndDatetime) {
    this.saleEndDatetime = saleEndDatetime;
  }

  public Date getDiscountPriceStartDatetime() {
    if (discountPriceStartDatetime == null) {
      return null;
    } else {
      discountPriceStartDatetime = discountPriceStartDatetime.replace("'", "").replaceAll("-", "/");
      return DateUtil.truncateHour(DateUtil.fromString(discountPriceStartDatetime, true));
    }
  }

  public String getDiscountPriceStartDatetimeStr() {
    return discountPriceStartDatetime;
  }

  public void setDiscountPriceStartDatetime(String discountPriceStartDatetime) {
    this.discountPriceStartDatetime = discountPriceStartDatetime;
  }

  public Date getDiscountPriceEndDatetime() {
    if (discountPriceEndDatetime == null) {
      return null;
    } else {
      discountPriceEndDatetime = discountPriceEndDatetime.replace("'", "").replaceAll("-", "/");
      return DateUtil.truncateHour(DateUtil.fromString(discountPriceEndDatetime, true));
    }
  }

  public String getDiscountPriceEndDatetimeStr() {
    return discountPriceEndDatetime;
  }

  public void setDiscountPriceEndDatetime(String discountPriceEndDatetime) {
    this.discountPriceEndDatetime = discountPriceEndDatetime;
  }

  public String getStandard1Id() {
    return standard1Id;
  }

  public void setStandard1Id(String standard1Id) {
    this.standard1Id = standard1Id;
  }

  public String getStandard1Name() {
    return standard1Name;
  }

  public void setStandard1Name(String standard1Name) {
    this.standard1Name = standard1Name;
  }

  public String getStandard2Id() {
    return standard2Id;
  }

  public void setStandard2Id(String standard2Id) {
    this.standard2Id = standard2Id;
  }

  public String getStandard2Name() {
    return standard2Name;
  }

  public void setStandard2Name(String standard2Name) {
    this.standard2Name = standard2Name;
  }

  public Long getSaleFlgEc() {
    return saleFlgEc;
  }

  public void setSaleFlgEc(Long saleFlgEc) {
    this.saleFlgEc = saleFlgEc;
  }

  public Long getReturnFlg() {
    return returnFlg;
  }

  public void setReturnFlg(Long returnFlg) {
    this.returnFlg = returnFlg;
  }

  public String getWarningFlag() {
    return warningFlag;
  }

  public void setWarningFlag(String warningFlag) {
    this.warningFlag = warningFlag;
  }

  public Long getLeadTime() {
    return leadTime;
  }

  public void setLeadTime(Long leadTime) {
    this.leadTime = leadTime;
  }

  public String getSaleFlag() {
    return saleFlag;
  }

  public void setSaleFlag(String saleFlag) {
    this.saleFlag = saleFlag;
  }

  public String getSpecFlag() {
    return specFlag;
  }

  public void setSpecFlag(String specFlag) {
    this.specFlag = specFlag;
  }

  public String getBrandCode() {
    return brandCode;
  }

  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  public Long getTmallCommodityId() {
    return tmallCommodityId;
  }

  public void setTmallCommodityId(Long tmallCommodityId) {
    this.tmallCommodityId = tmallCommodityId;
  }

  public Long getTmallCategoryId() {
    return tmallCategoryId;
  }

  public void setTmallCategoryId(Long tmallCategoryId) {
    this.tmallCategoryId = tmallCategoryId;
  }

  public String getSupplierCode() {
    return supplierCode;
  }

  public void setSupplierCode(String supplierCode) {
    this.supplierCode = supplierCode;
  }

  public String getBuyerCode() {
    return buyerCode;
  }

  public void setBuyerCode(String buyerCode) {
    this.buyerCode = buyerCode;
  }

  public String getOriginalPlace() {
    return originalPlace;
  }

  public void setOriginalPlace(String originalPlace) {
    this.originalPlace = originalPlace;
  }

  public String getIngredientName1() {
    return ingredientName1;
  }

  public void setIngredientName1(String ingredientName1) {
    this.ingredientName1 = ingredientName1;
  }

  public String getIngredientVal1() {
    return ingredientVal1;
  }

  public void setIngredientVal1(String ingredientVal1) {
    this.ingredientVal1 = ingredientVal1;
  }

  public String getIngredientName2() {
    return ingredientName2;
  }

  public void setIngredientName2(String ingredientName2) {
    this.ingredientName2 = ingredientName2;
  }

  public String getIngredientVal2() {
    return ingredientVal2;
  }

  public void setIngredientVal2(String ingredientVal2) {
    this.ingredientVal2 = ingredientVal2;
  }

  public String getIngredientName3() {
    return ingredientName3;
  }

  public void setIngredientName3(String ingredientName3) {
    this.ingredientName3 = ingredientName3;
  }

  public String getIngredientVal3() {
    return ingredientVal3;
  }

  public void setIngredientVal3(String ingredientVal3) {
    this.ingredientVal3 = ingredientVal3;
  }

  public String getIngredientName4() {
    return ingredientName4;
  }

  public void setIngredientName4(String ingredientName4) {
    this.ingredientName4 = ingredientName4;
  }

  public String getIngredientVal4() {
    return ingredientVal4;
  }

  public void setIngredientVal4(String ingredientVal4) {
    this.ingredientVal4 = ingredientVal4;
  }

  public String getIngredientName5() {
    return ingredientName5;
  }

  public void setIngredientName5(String ingredientName5) {
    this.ingredientName5 = ingredientName5;
  }

  public String getIngredientVal5() {
    return ingredientVal5;
  }

  public void setIngredientVal5(String ingredientVal5) {
    this.ingredientVal5 = ingredientVal5;
  }

  public String getIngredientName6() {
    return ingredientName6;
  }

  public void setIngredientName6(String ingredientName6) {
    this.ingredientName6 = ingredientName6;
  }

  public String getIngredientVal6() {
    return ingredientVal6;
  }

  public void setIngredientVal6(String ingredientVal6) {
    this.ingredientVal6 = ingredientVal6;
  }

  public String getIngredientName7() {
    return ingredientName7;
  }

  public void setIngredientName7(String ingredientName7) {
    this.ingredientName7 = ingredientName7;
  }

  public String getIngredientVal7() {
    return ingredientVal7;
  }

  public void setIngredientVal7(String ingredientVal7) {
    this.ingredientVal7 = ingredientVal7;
  }

  public String getIngredientName8() {
    return ingredientName8;
  }

  public void setIngredientName8(String ingredientName8) {
    this.ingredientName8 = ingredientName8;
  }

  public String getIngredientVal8() {
    return ingredientVal8;
  }

  public void setIngredientVal8(String ingredientVal8) {
    this.ingredientVal8 = ingredientVal8;
  }

  public String getIngredientName9() {
    return ingredientName9;
  }

  public void setIngredientName9(String ingredientName9) {
    this.ingredientName9 = ingredientName9;
  }

  public String getIngredientVal9() {
    return ingredientVal9;
  }

  public void setIngredientVal9(String ingredientVal9) {
    this.ingredientVal9 = ingredientVal9;
  }

  public String getIngredientName10() {
    return ingredientName10;
  }

  public void setIngredientName10(String ingredientName10) {
    this.ingredientName10 = ingredientName10;
  }

  public String getIngredientVal10() {
    return ingredientVal10;
  }

  public void setIngredientVal10(String ingredientVal10) {
    this.ingredientVal10 = ingredientVal10;
  }

  public String getIngredientName11() {
    return ingredientName11;
  }

  public void setIngredientName11(String ingredientName11) {
    this.ingredientName11 = ingredientName11;
  }

  public String getIngredientVal11() {
    return ingredientVal11;
  }

  public void setIngredientVal11(String ingredientVal11) {
    this.ingredientVal11 = ingredientVal11;
  }

  public String getIngredientName12() {
    return ingredientName12;
  }

  public void setIngredientName12(String ingredientName12) {
    this.ingredientName12 = ingredientName12;
  }

  public String getIngredientVal12() {
    return ingredientVal12;
  }

  public void setIngredientVal12(String ingredientVal12) {
    this.ingredientVal12 = ingredientVal12;
  }

  public String getIngredientName13() {
    return ingredientName13;
  }

  public void setIngredientName13(String ingredientName13) {
    this.ingredientName13 = ingredientName13;
  }

  public String getIngredientVal13() {
    return ingredientVal13;
  }

  public void setIngredientVal13(String ingredientVal13) {
    this.ingredientVal13 = ingredientVal13;
  }

  public String getIngredientName14() {
    return ingredientName14;
  }

  public void setIngredientName14(String ingredientName14) {
    this.ingredientName14 = ingredientName14;
  }

  public String getIngredientVal14() {
    return ingredientVal14;
  }

  public void setIngredientVal14(String ingredientVal14) {
    this.ingredientVal14 = ingredientVal14;
  }

  public String getIngredientName15() {
    return ingredientName15;
  }

  public void setIngredientName15(String ingredientName15) {
    this.ingredientName15 = ingredientName15;
  }

  public String getIngredientVal15() {
    return ingredientVal15;
  }

  public void setIngredientVal15(String ingredientVal15) {
    this.ingredientVal15 = ingredientVal15;
  }

  public String getMaterial1() {
    return material1;
  }

  public void setMaterial1(String material1) {
    this.material1 = material1;
  }

  public String getMaterial2() {
    return material2;
  }

  public void setMaterial2(String material2) {
    this.material2 = material2;
  }

  public String getMaterial3() {
    return material3;
  }

  public void setMaterial3(String material3) {
    this.material3 = material3;
  }

  public String getMaterial4() {
    return material4;
  }

  public void setMaterial4(String material4) {
    this.material4 = material4;
  }

  public String getMaterial5() {
    return material5;
  }

  public void setMaterial5(String material5) {
    this.material5 = material5;
  }

  public String getMaterial6() {
    return material6;
  }

  public void setMaterial6(String material6) {
    this.material6 = material6;
  }

  public String getMaterial7() {
    return material7;
  }

  public void setMaterial7(String material7) {
    this.material7 = material7;
  }

  public String getMaterial8() {
    return material8;
  }

  public void setMaterial8(String material8) {
    this.material8 = material8;
  }

  public String getMaterial9() {
    return material9;
  }

  public void setMaterial9(String material9) {
    this.material9 = material9;
  }

  public String getMaterial10() {
    return material10;
  }

  public void setMaterial10(String material10) {
    this.material10 = material10;
  }

  public String getMaterial11() {
    return material11;
  }

  public void setMaterial11(String material11) {
    this.material11 = material11;
  }

  public String getMaterial12() {
    return material12;
  }

  public void setMaterial12(String material12) {
    this.material12 = material12;
  }

  public String getMaterial13() {
    return material13;
  }

  public void setMaterial13(String material13) {
    this.material13 = material13;
  }

  public String getMaterial14() {
    return material14;
  }

  public void setMaterial14(String material14) {
    this.material14 = material14;
  }

  public String getMaterial15() {
    return material15;
  }

  public void setMaterial15(String material15) {
    this.material15 = material15;
  }

  public Long getShelfLifeFlag() {
    return shelfLifeFlag;
  }

  public void setShelfLifeFlag(Long shelfLifeFlag) {
    this.shelfLifeFlag = shelfLifeFlag;
  }

  public Long getShelfLifeDays() {
    return shelfLifeDays;
  }

  public void setShelfLifeDays(Long shelfLifeDays) {
    this.shelfLifeDays = shelfLifeDays;
  }

  public Long getBigFlag() {
    return bigFlag;
  }

  public void setBigFlag(Long bigFlag) {
    this.bigFlag = bigFlag;
  }

  public Date getSyncTimeEc() {
    return syncTimeEc;
  }

  public void setSyncTimeEc(Date syncTimeEc) {
    this.syncTimeEc = syncTimeEc;
  }

  public Date getSyncTimeTmall() {
    return syncTimeTmall;
  }

  public void setSyncTimeTmall(Date syncTimeTmall) {
    this.syncTimeTmall = syncTimeTmall;
  }

  public Long getSyncFlagEc() {
    return syncFlagEc;
  }

  public void setSyncFlagEc(Long syncFlagEc) {
    this.syncFlagEc = syncFlagEc;
  }

  public Long getSyncFlagTmall() {
    return syncFlagTmall;
  }

  public void setSyncFlagTmall(Long syncFlagTmall) {
    this.syncFlagTmall = syncFlagTmall;
  }

  public String getSyncUserEc() {
    return syncUserEc;
  }

  public void setSyncUserEc(String syncUserEc) {
    this.syncUserEc = syncUserEc;
  }

  public String getSyncUserTmall() {
    return syncUserTmall;
  }

  public void setSyncUserTmall(String syncUserTmall) {
    this.syncUserTmall = syncUserTmall;
  }

  public Long getExportFlagErp() {
    return exportFlagErp;
  }

  public void setExportFlagErp(Long exportFlagErp) {
    this.exportFlagErp = exportFlagErp;
  }

  public Long getExportFlagWms() {
    return exportFlagWms;
  }

  public void setExportFlagWms(Long exportFlagWms) {
    this.exportFlagWms = exportFlagWms;
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

  /**
   * @param shelfLifeAlertDays
   *          the shelfLifeAlertDays to set
   */
  public void setShelfLifeAlertDays(Long shelfLifeAlertDays) {
    this.shelfLifeAlertDays = shelfLifeAlertDays;
  }

  /**
   * @return the shelfLifeAlertDays
   */
  public Long getShelfLifeAlertDays() {
    return shelfLifeAlertDays;
  }

  /**
   * @param inBoundLifeDays
   *          the inBoundLifeDays to set
   */
  public void setInBoundLifeDays(Long inBoundLifeDays) {
    this.inBoundLifeDays = inBoundLifeDays;
  }

  /**
   * @return the inBoundLifeDays
   */
  public Long getInBoundLifeDays() {
    return inBoundLifeDays;
  }

  /**
   * @param outBoundLifeDays
   *          the outBoundLifeDays to set
   */
  public void setOutBoundLifeDays(Long outBoundLifeDays) {
    this.outBoundLifeDays = outBoundLifeDays;
  }

  /**
   * @return the outBoundLifeDays
   */
  public Long getOutBoundLifeDays() {
    return outBoundLifeDays;
  }

  /**
   * @param originalPlaceEn
   *          the originalPlaceEn to set
   */
  public void setOriginalPlaceEn(String originalPlaceEn) {
    this.originalPlaceEn = originalPlaceEn;
  }

  /**
   * @return the originalPlaceEn
   */
  public String getOriginalPlaceEn() {
    return originalPlaceEn;
  }

  /**
   * @param originalPlaceJp
   *          the originalPlaceJp to set
   */
  public void setOriginalPlaceJp(String originalPlaceJp) {
    this.originalPlaceJp = originalPlaceJp;
  }

  /**
   * @return the originalPlaceJp
   */
  public String getOriginalPlaceJp() {
    return originalPlaceJp;
  }

  /**
   * @return the importCommodityType
   */
  public Long getImportCommodityType() {
    return importCommodityType;
  }

  /**
   * @param importCommodityType
   *          the importCommodityType to set
   */
  public void setImportCommodityType(Long importCommodityType) {
    this.importCommodityType = importCommodityType;
  }

  /**
   * @return the clearCommodityType
   */
  public Long getClearCommodityType() {
    return clearCommodityType;
  }

  /**
   * @param clearCommodityType
   *          the clearCommodityType to set
   */
  public void setClearCommodityType(Long clearCommodityType) {
    this.clearCommodityType = clearCommodityType;
  }

  /**
   * @return the reserveCommodityType1
   */
  public Long getReserveCommodityType1() {
    return reserveCommodityType1;
  }

  /**
   * @param reserveCommodityType1
   *          the reserveCommodityType1 to set
   */
  public void setReserveCommodityType1(Long reserveCommodityType1) {
    this.reserveCommodityType1 = reserveCommodityType1;
  }

  /**
   * @return the reserveCommodityType2
   */
  public Long getReserveCommodityType2() {
    return reserveCommodityType2;
  }

  /**
   * @param reserveCommodityType2
   *          the reserveCommodityType2 to set
   */
  public void setReserveCommodityType2(Long reserveCommodityType2) {
    this.reserveCommodityType2 = reserveCommodityType2;
  }

  /**
   * @return the reserveCommodityType3
   */
  public Long getReserveCommodityType3() {
    return reserveCommodityType3;
  }

  /**
   * @param reserveCommodityType3
   *          the reserveCommodityType3 to set
   */
  public void setReserveCommodityType3(Long reserveCommodityType3) {
    this.reserveCommodityType3 = reserveCommodityType3;
  }

  /**
   * @return the hotFlgEn
   */
  public Long getHotFlgEn() {
    return hotFlgEn;
  }

  /**
   * @param hotFlgEn
   *          the hotFlgEn to set
   */
  public void setHotFlgEn(Long hotFlgEn) {
    this.hotFlgEn = hotFlgEn;
  }

  /**
   * @return the hotFlgJp
   */
  public Long getHotFlgJp() {
    return hotFlgJp;
  }

  /**
   * @param hotFlgJp
   *          the hotFlgJp to set
   */
  public void setHotFlgJp(Long hotFlgJp) {
    this.hotFlgJp = hotFlgJp;
  }

  
  /**
   * @return the originalCommodityCode
   */
  public String getOriginalCommodityCode() {
    return originalCommodityCode;
  }

  
  /**
   * @param originalCommodityCode the originalCommodityCode to set
   */
  public void setOriginalCommodityCode(String originalCommodityCode) {
    this.originalCommodityCode = originalCommodityCode;
  }

  
  /**
   * @return the combinationAmount
   */
  public Long getCombinationAmount() {
    return combinationAmount;
  }

  
  /**
   * @param combinationAmount the combinationAmount to set
   */
  public void setCombinationAmount(Long combinationAmount) {
    this.combinationAmount = combinationAmount;
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
   * @return the tmallCommoditySearchWords
   */
  public String getTmallCommoditySearchWords() {
    return tmallCommoditySearchWords;
  }

  
  /**
   * @param tmallCommoditySearchWords the tmallCommoditySearchWords to set
   */
  public void setTmallCommoditySearchWords(String tmallCommoditySearchWords) {
    this.tmallCommoditySearchWords = tmallCommoditySearchWords;
  }

  
  /**
   * @return the keywordCn2
   */
  public String getKeywordCn2() {
    return keywordCn2;
  }

  
  /**
   * @param keywordCn2 the keywordCn2 to set
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
   * @param keywordJp2 the keywordJp2 to set
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
   * @param keywordEn2 the keywordEn2 to set
   */
  public void setKeywordEn2(String keywordEn2) {
    this.keywordEn2 = keywordEn2;
  }

  
  /**
   * @return the setCommodityFlg
   */
  public Long getSetCommodityFlg() {
    return setCommodityFlg;
  }

  
  /**
   * @param setCommodityFlg the setCommodityFlg to set
   */
  public void setSetCommodityFlg(Long setCommodityFlg) {
    this.setCommodityFlg = setCommodityFlg;
  }

  /**
   * @return the advertContent
   */
  public String getAdvertContent() {
    return advertContent;
  }

  /**
   * @param advertContent the advertContent to set
   */
  public void setAdvertContent(String advertContent) {
    this.advertContent = advertContent;
  }

  /**
   * @return the syncFlagJd
   */
  public Long getSyncFlagJd() {
    return syncFlagJd;
  }

  /**
   * @param syncFlagJd the syncFlagJd to set
   */
  public void setSyncFlagJd(Long syncFlagJd) {
    this.syncFlagJd = syncFlagJd;
  }

  /**
   * @return the jdCategoryId
   */
  public Long getJdCategoryId() {
    return jdCategoryId;
  }

  /**
   * @param jdCategoryId the jdCategoryId to set
   */
  public void setJdCategoryId(Long jdCategoryId) {
    this.jdCategoryId = jdCategoryId;
  }
  

}
