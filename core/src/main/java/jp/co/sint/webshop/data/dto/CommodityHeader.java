//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Percentage;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.data.domain.ArrivalGoodsFlg;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「商品ヘッダ(COMMODITY_HEADER)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */
public class CommodityHeader implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** 商品コード */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 2)
  private String commodityCode;

  /** 商品名称 */
  @Required
  @Length(50)
  @Metadata(name = "商品名称", order = 3)
  private String commodityName;

  /** 代表SKUコード */
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "代表SKUコード", order = 4)
  private String representSkuCode;

  /** 代表SKU単価 */
  @Required
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "代表SKU単価", order = 5)
  private BigDecimal representSkuUnitPrice;

  /** 在庫状況番号 */
  @Length(8)
  @Digit
  @Metadata(name = "在庫状況番号", order = 6)
  private Long stockStatusNo;

  /** 在庫管理区分 */
  @Required
  @Length(1)
  @Domain(StockManagementType.class)
  @Metadata(name = "在庫管理区分", order = 7)
  private Long stockManagementType;

  /** 商品消費税区分 */
  @Required
  @Length(1)
  @Domain(TaxType.class)
  @Metadata(name = "商品消費税区分", order = 8)
  private Long commodityTaxType;

  /** PC商品説明 */
  // 10.1.3 10140 修正 ここから
  // @Length(2000)
  @Length(25000)
  // 10.1.3 10140 修正 ここまで
  @Metadata(name = "PC商品説明", order = 9)
  private String commodityDescriptionPc;

  /** 携帯商品説明 */
  @Length(500)
  @Metadata(name = "携帯商品説明", order = 10)
  private String commodityDescriptionMobile;

  /** 商品検索ワード */
  @Length(500)
  @Metadata(name = "商品検索ワード", order = 11)
  private String commoditySearchWords;

  /** シャドウ検索ワード */
  @Length(600)
  @Metadata(name = "シャドウ検索ワード", order = 12)
  private String shadowSearchWords;

  /** 販売開始日時 */
  @Required
  @Metadata(name = "販売開始日時", order = 13)
  private Date saleStartDatetime;

  /** 販売終了日時 */
  @Required
  @Metadata(name = "販売終了日時", order = 14)
  private Date saleEndDatetime;

  /** 価格改定日時 */
  @Metadata(name = "価格改定日時", order = 15)
  private Date changeUnitPriceDatetime;

  /** 特別価格開始日時 */
  @Metadata(name = "特別価格開始日時", order = 16)
  private Date discountPriceStartDatetime;

  /** 特別価格終了日時 */
  @Metadata(name = "特別価格終了日時", order = 17)
  private Date discountPriceEndDatetime;

  /** 予約開始日時 */
  @Required
  @Metadata(name = "予約開始日時", order = 18)
  private Date reservationStartDatetime;

  /** 予約終了日時 */
  @Required
  @Metadata(name = "予約終了日時", order = 19)
  private Date reservationEndDatetime;

  /** 配送種別番号 */
  @Required
  @Length(8)
  @AlphaNum2
  @Metadata(name = "配送種別番号", order = 20)
  private Long deliveryTypeNo;

  /** リンクURL */
  @Length(256)
  @Url
  @Metadata(name = "リンクURL", order = 21)
  private String linkUrl;

  /** おすすめ商品順位 */
  @Required
  @Length(8)
  @Metadata(name = "おすすめ商品順位", order = 22)
  private Long recommendCommodityRank;

  /** 人気順位 */
  @Required
  @Length(8)
  @Metadata(name = "人気順位", order = 23)
  private Long commodityPopularRank;

  /** 規格名称1 */
  @Length(20)
  @Metadata(name = "規格名称1", order = 24)
  private String commodityStandard1Name;

  /** 規格名称2 */
  @Length(20)
  @Metadata(name = "規格名称2", order = 25)
  private String commodityStandard2Name;

  /** 商品別ポイント付与率 */
  @Length(3)
  @Percentage
  @Metadata(name = "商品別ポイント付与率", order = 26)
  private Long commodityPointRate;

  /** 商品別ポイント付与開始日時 */
  @Metadata(name = "商品別ポイント付与開始日時", order = 27)
  private Date commodityPointStartDatetime;

  /** 商品別ポイント付与終了日時 */
  @Metadata(name = "商品別ポイント付与終了日時", order = 28)
  private Date commodityPointEndDatetime;

  /** 販売フラグ */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "販売フラグ", order = 29)
  private Long saleFlg;

  /** 表示クライアント区分 */
  @Required
  @Length(1)
  @Metadata(name = "表示クライアント区分", order = 30)
  private Long displayClientType;

  // add by tangweihui 2012-11-16 start
  @Required
  @Length(1)
  @Metadata(name = "商品区分", order = 30)
  private Long commodityType;

  @Required
  @Length(1)
  @Metadata(name = "套装商品区分", order = 30)
  private Long setCommodityFlg;

  // add by tangweihui 2012-11-16 end
  /** 入荷お知らせ可能フラグ */
  @Required
  @Length(1)
  @Domain(ArrivalGoodsFlg.class)
  @Metadata(name = "入荷お知らせ可能フラグ", order = 31)
  private Long arrivalGoodsFlg;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 32)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 33)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 34)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 35)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 36)
  private Date updatedDatetime;

  // add by os014 begin
  @Required
  @Length(1)
  @Metadata(name = "返品不可フラグ", order = 37)
  private Long returnFlg;

  @Length(1)
  @Metadata(name = "ワーニング区分", order = 38)
  private String warningFlag;

  @Length(2)
  @Metadata(name = "リードタイム", order = 39)
  private Long leadTime;

  @Length(5)
  @Metadata(name = "セール区分", order = 40)
  private String saleFlag;

  @Length(5)
  @Metadata(name = "特集区分", order = 41)
  private String specFlag;

  @Length(16)
  @Metadata(name = "ブランドコード", order = 42)
  private String brandCode;

  private String brand;

  // add by os014 begin

  // add by wjw begin
  @Length(200)
  @Metadata(name = "商品名称英字", order = 43)
  private String commodityNameEn;

  @Length(256)
  @Metadata(name = "商品目录路径", order = 44)
  private String categoryPath;

  @Length(1000)
  @Metadata(name = "商品属性", order = 45)
  private String categoryAttribute1;

  // add by wjw end
  // add by cs_yuli 20120514 start
  /**** 商品名称日文 ****/
  @Length(200)
  @Metadata(name = "商品名称日文", order = 46)
  private String commodityNameJp;

  /** PC商品説明日文 */
  @Length(25000)
  @Metadata(name = "PC商品説明日文", order = 47)
  private String commodityDescriptionPcJp;

  /** 携帯商品説明日文 */
  @Length(500)
  @Metadata(name = "携帯商品説明日文", order = 48)
  private String commodityDescriptionMobileJp;

  /** PC商品説明英文 */
  @Length(25000)
  @Metadata(name = "PC商品説明英文", order = 49)
  private String commodityDescriptionPcEn;

  /** 携帯商品説明英文 */
  @Length(500)
  @Metadata(name = "携帯商品説明英文", order = 50)
  private String commodityDescriptionMobileEn;

  /** 規格名称1英文 */
  @Length(20)
  @Metadata(name = "規格名称1", order = 51)
  private String commodityStandard1NameEn;

  /** 規格名称2英文 */
  @Length(20)
  @Metadata(name = "規格名称2英文", order = 52)
  private String commodityStandard2NameEn;

  /** 規格名称1日文 */
  @Length(20)
  @Metadata(name = "規格名称1日文", order = 53)
  private String commodityStandard1NameJp;

  /** 規格名称2日文 */
  @Length(20)
  @Metadata(name = "規格名称2英文", order = 54)
  private String commodityStandard2NameJp;

  // add by cs_yuli 20120514 end

  // add by cs_yuli 20120615 start
  @Length(1000)
  @Metadata(name = "商品属性", order = 55)
  private String categoryAttribute1En;

  @Length(1000)
  @Metadata(name = "商品属性", order = 56)
  private String categoryAttribute1Jp;

  // add by cs_yuli 20120615 end

  @Length(20)
  @Metadata(name = "产地中文", order = 57)
  private String originalPlace;

  @Length(50)
  @Metadata(name = "产地英文", order = 58)
  private String originalPlaceEn;

  @Length(50)
  @Metadata(name = "产地日文", order = 59)
  private String originalPlaceJp;

  @Length(6)
  @Metadata(name = "保管日数", order = 69)
  private Long shelfLifeDays;

  @Length(1)
  @Metadata(name = "商品期限管理フラグ", order = 70)
  private Long shelfLifeFlag;

  @Length(1)
  @Metadata(name = "进口商品区分", order = 71)
  private Long importCommodityType;

  @Length(1)
  @Metadata(name = "清仓商品区分", order = 72)
  private Long clearCommodityType;

  @Length(1)
  @Metadata(name = "Asahi商品区分", order = 73)
  private Long reserveCommodityType1;

  @Length(1)
  @Metadata(name = "hot商品区分", order = 74)
  private Long reserveCommodityType2;

  @Length(1)
  @Metadata(name = "商品展示区分", order = 75)
  private Long reserveCommodityType3;

  @Length(1)
  @Metadata(name = "预留区分1*", order = 76)
  private Long newReserveCommodityType1;

  @Length(1)
  @Metadata(name = "预留区分2*", order = 77)
  private Long newReserveCommodityType2;

  @Length(1)
  @Metadata(name = "预留区分3*", order = 78)
  private Long newReserveCommodityType3;

  @Length(1)
  @Metadata(name = "预留区分4*", order = 79)
  private Long newReserveCommodityType4;

  @Length(1)
  @Metadata(name = "预留区分5*", order = 80)
  private Long newReserveCommodityType5;

  @Length(16)
  @Metadata(name = "産地编号", order = 81)
  private String originalCode;

  // add by zhangzhengtao 20130527 start
  /** 検索Keyword（中文）1 */
  @Length(30)
  @Metadata(name = " 検索Keyword（中文）1", order = 148)
  private String keywordCn1;

  /** 検索Keyword（日文）2 */
  @Length(30)
  @Metadata(name = " 検索Keyword（日文）1", order = 149)
  private String keywordJp1;

  /** 検索Keyword（英文）2 */
  @Length(30)
  @Metadata(name = " 検索Keyword（英文）1", order = 150)
  private String keywordEn1;

  /** 検索Keyword（中文）2 */
  @Length(30)
  @Metadata(name = " 検索Keyword（中文）2", order = 151)
  private String keywordCn2;

  /** 検索Keyword（日文）2 */
  @Length(30)
  @Metadata(name = " 検索Keyword（日文）2", order = 152)
  private String keywordJp2;

  /** 検索Keyword（英文）2 */
  @Length(30)
  @Metadata(name = " 検索Keyword（英文）2", order = 153)
  private String keywordEn2;

  /** 原商品编号 */
  @Length(16)
  @Metadata(name = "原商品编号", order = 154)
  private String originalCommodityCode;

  /** 组合数量 */
  @Length(8)
  @Metadata(name = "组合数量", order = 155)
  private Long combinationAmount;

  // add by zhangzhengtao 20130527 end

  @Length(1)
  @Metadata(name = "english热卖区分", order = 156)
  private Long hotFlgEn;

  @Length(1)
  @Metadata(name = "japan热卖区分", order = 157)
  private Long hotFlgJp;

  // 20130808 txw add start
  /*** TITLE ***/
  @Length(60)
  @Metadata(name = "TITLE", order = 158)
  private String title;

  /*** TITLE(英文) ***/
  @Length(60)
  @Metadata(name = "TITLE(英文)", order = 159)
  private String titleEn;

  /*** TITLE(日文) ***/
  @Length(60)
  @Metadata(name = "TITLE(日文)", order = 160)
  private String titleJp;

  /*** DESCRIPTION ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION", order = 161)
  private String description;

  /*** DESCRIPTION(英文) ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION(英文)", order = 162)
  private String descriptionEn;

  /*** DESCRIPTION(日文) ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION(日文)", order = 163)
  private String descriptionJp;

  /*** KEYWORD ***/
  @Length(100)
  @Metadata(name = "KEYWORD", order = 164)
  private String keyword;

  /*** KEYWORD(英文) ***/
  @Length(100)
  @Metadata(name = "KEYWORD(英文)", order = 165)
  private String keywordEn;

  /*** KEYWORD(日文) ***/
  @Length(100)
  @Metadata(name = "KEYWORD(日文)", order = 166)
  private String keywordJp;

  // 20130808 txw add end

  /*** 品店精选排序字段 ***/
  @Length(5)
  @Metadata(name = "品店精选排序字段", order = 167)
  private Long chosenSortRank;
  
  /**
   * @return the originalCode
   */
  public String getOriginalCode() {
    return originalCode;
  }

  /**
   * @return the originalCommodityCode
   */
  public String getOriginalCommodityCode() {
    return originalCommodityCode;
  }

  /**
   * @param originalCommodityCode
   *          the originalCommodityCode to set
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
   * @param combinationAmount
   *          the combinationAmount to set
   */
  public void setCombinationAmount(Long combinationAmount) {
    this.combinationAmount = combinationAmount;
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
   * @param originalCode
   *          the originalCode to set
   */
  public void setOriginalCode(String originalCode) {
    this.originalCode = originalCode;
  }

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

  public Long getShelfLifeFlag() {
    return shelfLifeFlag;
  }

  public void setShelfLifeFlag(Long shelfLifeFlag) {
    this.shelfLifeFlag = shelfLifeFlag;
  }

  public String getOriginalPlace() {
    return originalPlace;
  }

  public void setOriginalPlace(String originalPlace) {
    this.originalPlace = originalPlace;
  }

  public String getOriginalPlaceEn() {
    return originalPlaceEn;
  }

  public void setOriginalPlaceEn(String originalPlaceEn) {
    this.originalPlaceEn = originalPlaceEn;
  }

  public String getOriginalPlaceJp() {
    return originalPlaceJp;
  }

  public void setOriginalPlaceJp(String originalPlaceJp) {
    this.originalPlaceJp = originalPlaceJp;
  }

  public Long getShelfLifeDays() {
    return shelfLifeDays;
  }

  public void setShelfLifeDays(Long shelfLifeDays) {
    this.shelfLifeDays = shelfLifeDays;
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
   * @return the brand
   */
  public String getBrand() {
    return brand;
  }

  /**
   * @param brand
   *          the brand to set
   */
  public void setBrand(String brand) {
    this.brand = brand;
  }

  /**
   * 商品コードを取得します
   * 
   * @return 商品コード
   */
  public String getCommodityCode() {
    return this.commodityCode;
  }

  /**
   * 商品名称を取得します
   * 
   * @return 商品名称
   */
  public String getCommodityName() {
    return this.commodityName;
  }

  /**
   * 代表SKUコードを取得します
   * 
   * @return 代表SKUコード
   */
  public String getRepresentSkuCode() {
    return this.representSkuCode;
  }

  /**
   * 代表SKU単価を取得します
   * 
   * @return 代表SKU単価
   */
  public BigDecimal getRepresentSkuUnitPrice() {
    return this.representSkuUnitPrice;
  }

  /**
   * 在庫状況番号を取得します
   * 
   * @return 在庫状況番号
   */
  public Long getStockStatusNo() {
    return this.stockStatusNo;
  }

  /**
   * 在庫管理区分を取得します
   * 
   * @return 在庫管理区分
   */
  public Long getStockManagementType() {
    return this.stockManagementType;
  }

  /**
   * 商品消費税区分を取得します
   * 
   * @return 商品消費税区分
   */
  public Long getCommodityTaxType() {
    return this.commodityTaxType;
  }

  /**
   * PC商品説明を取得します
   * 
   * @return PC商品説明
   */
  public String getCommodityDescriptionPc() {
    return this.commodityDescriptionPc;
  }

  /**
   * 携帯商品説明を取得します
   * 
   * @return 携帯商品説明
   */
  public String getCommodityDescriptionMobile() {
    return this.commodityDescriptionMobile;
  }

  /**
   * 商品検索ワードを取得します
   * 
   * @return 商品検索ワード
   */
  public String getCommoditySearchWords() {
    return this.commoditySearchWords;
  }

  /**
   * シャドウ検索ワードを取得します
   * 
   * @return シャドウ検索ワード
   */
  public String getShadowSearchWords() {
    return this.shadowSearchWords;
  }

  /**
   * 販売開始日時を取得します
   * 
   * @return 販売開始日時
   */
  public Date getSaleStartDatetime() {
    return DateUtil.immutableCopy(this.saleStartDatetime);
  }

  /**
   * 販売終了日時を取得します
   * 
   * @return 販売終了日時
   */
  public Date getSaleEndDatetime() {
    return DateUtil.immutableCopy(this.saleEndDatetime);
  }

  /**
   * 価格改定日時を取得します
   * 
   * @return 価格改定日時
   */
  public Date getChangeUnitPriceDatetime() {
    return DateUtil.immutableCopy(this.changeUnitPriceDatetime);
  }

  /**
   * 特別価格開始日時を取得します
   * 
   * @return 特別価格開始日時
   */
  public Date getDiscountPriceStartDatetime() {
    return DateUtil.immutableCopy(this.discountPriceStartDatetime);
  }

  /**
   * 特別価格終了日時を取得します
   * 
   * @return 特別価格終了日時
   */
  public Date getDiscountPriceEndDatetime() {
    return DateUtil.immutableCopy(this.discountPriceEndDatetime);
  }

  /**
   * 予約開始日時を取得します
   * 
   * @return 予約開始日時
   */
  public Date getReservationStartDatetime() {
    return DateUtil.immutableCopy(this.reservationStartDatetime);
  }

  /**
   * 予約終了日時を取得します
   * 
   * @return 予約終了日時
   */
  public Date getReservationEndDatetime() {
    return DateUtil.immutableCopy(this.reservationEndDatetime);
  }

  /**
   * 配送種別番号を取得します
   * 
   * @return 配送種別番号
   */
  public Long getDeliveryTypeNo() {
    return this.deliveryTypeNo;
  }

  /**
   * リンクURLを取得します
   * 
   * @return リンクURL
   */
  public String getLinkUrl() {
    return this.linkUrl;
  }

  /**
   * おすすめ商品順位を取得します
   * 
   * @return おすすめ商品順位
   */
  public Long getRecommendCommodityRank() {
    return this.recommendCommodityRank;
  }

  /**
   * 人気順位を取得します
   * 
   * @return 人気順位
   */
  public Long getCommodityPopularRank() {
    return this.commodityPopularRank;
  }

  /**
   * 規格名称1を取得します
   * 
   * @return 規格名称1
   */
  public String getCommodityStandard1Name() {
    return this.commodityStandard1Name;
  }

  /**
   * 規格名称2を取得します
   * 
   * @return 規格名称2
   */
  public String getCommodityStandard2Name() {
    return this.commodityStandard2Name;
  }

  /**
   * 商品別ポイント付与率を取得します
   * 
   * @return 商品別ポイント付与率
   */
  public Long getCommodityPointRate() {
    return this.commodityPointRate;
  }

  /**
   * 商品別ポイント付与開始日時を取得します
   * 
   * @return 商品別ポイント付与開始日時
   */
  public Date getCommodityPointStartDatetime() {
    return DateUtil.immutableCopy(this.commodityPointStartDatetime);
  }

  /**
   * 商品別ポイント付与終了日時を取得します
   * 
   * @return 商品別ポイント付与終了日時
   */
  public Date getCommodityPointEndDatetime() {
    return DateUtil.immutableCopy(this.commodityPointEndDatetime);
  }

  /**
   * 販売フラグを取得します
   * 
   * @return 販売フラグ
   */
  public Long getSaleFlg() {
    return this.saleFlg;
  }

  /**
   * 表示クライアント区分を取得します
   * 
   * @return 表示クライアント区分
   */
  public Long getDisplayClientType() {
    return this.displayClientType;
  }

  /**
   * 入荷お知らせ可能フラグを取得します
   * 
   * @return 入荷お知らせ可能フラグ
   */
  public Long getArrivalGoodsFlg() {
    return this.arrivalGoodsFlg;
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
   * 商品コードを設定します
   * 
   * @param val
   *          商品コード
   */
  public void setCommodityCode(String val) {
    this.commodityCode = val;
  }

  /**
   * 商品名称を設定します
   * 
   * @param val
   *          商品名称
   */
  public void setCommodityName(String val) {
    this.commodityName = val;
  }

  /**
   * 代表SKUコードを設定します
   * 
   * @param val
   *          代表SKUコード
   */
  public void setRepresentSkuCode(String val) {
    this.representSkuCode = val;
  }

  /**
   * 代表SKU単価を設定します
   * 
   * @param val
   *          代表SKU単価
   */
  public void setRepresentSkuUnitPrice(BigDecimal val) {
    this.representSkuUnitPrice = val;
  }

  /**
   * 在庫状況番号を設定します
   * 
   * @param val
   *          在庫状況番号
   */
  public void setStockStatusNo(Long val) {
    this.stockStatusNo = val;
  }

  /**
   * 在庫管理区分を設定します
   * 
   * @param val
   *          在庫管理区分
   */
  public void setStockManagementType(Long val) {
    this.stockManagementType = val;
  }

  /**
   * 商品消費税区分を設定します
   * 
   * @param val
   *          商品消費税区分
   */
  public void setCommodityTaxType(Long val) {
    this.commodityTaxType = val;
  }

  /**
   * PC商品説明を設定します
   * 
   * @param val
   *          PC商品説明
   */
  public void setCommodityDescriptionPc(String val) {
    this.commodityDescriptionPc = val;
  }

  /**
   * 携帯商品説明を設定します
   * 
   * @param val
   *          携帯商品説明
   */
  public void setCommodityDescriptionMobile(String val) {
    this.commodityDescriptionMobile = val;
  }

  /**
   * 商品検索ワードを設定します
   * 
   * @param val
   *          商品検索ワード
   */
  public void setCommoditySearchWords(String val) {
    this.commoditySearchWords = val;
  }

  /**
   * シャドウ検索ワードを設定します
   * 
   * @param val
   *          シャドウ検索ワード
   */
  public void setShadowSearchWords(String val) {
    this.shadowSearchWords = val;
  }

  /**
   * 販売開始日時を設定します
   * 
   * @param val
   *          販売開始日時
   */
  public void setSaleStartDatetime(Date val) {
    this.saleStartDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 販売終了日時を設定します
   * 
   * @param val
   *          販売終了日時
   */
  public void setSaleEndDatetime(Date val) {
    this.saleEndDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 価格改定日時を設定します
   * 
   * @param val
   *          価格改定日時
   */
  public void setChangeUnitPriceDatetime(Date val) {
    this.changeUnitPriceDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 特別価格開始日時を設定します
   * 
   * @param val
   *          特別価格開始日時
   */
  public void setDiscountPriceStartDatetime(Date val) {
    this.discountPriceStartDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 特別価格終了日時を設定します
   * 
   * @param val
   *          特別価格終了日時
   */
  public void setDiscountPriceEndDatetime(Date val) {
    this.discountPriceEndDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 予約開始日時を設定します
   * 
   * @param val
   *          予約開始日時
   */
  public void setReservationStartDatetime(Date val) {
    this.reservationStartDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 予約終了日時を設定します
   * 
   * @param val
   *          予約終了日時
   */
  public void setReservationEndDatetime(Date val) {
    this.reservationEndDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 配送種別番号を設定します
   * 
   * @param val
   *          配送種別番号
   */
  public void setDeliveryTypeNo(Long val) {
    this.deliveryTypeNo = val;
  }

  /**
   * リンクURLを設定します
   * 
   * @param val
   *          リンクURL
   */
  public void setLinkUrl(String val) {
    this.linkUrl = val;
  }

  /**
   * おすすめ商品順位を設定します
   * 
   * @param val
   *          おすすめ商品順位
   */
  public void setRecommendCommodityRank(Long val) {
    this.recommendCommodityRank = val;
  }

  /**
   * 人気順位を設定します
   * 
   * @param val
   *          人気順位
   */
  public void setCommodityPopularRank(Long val) {
    this.commodityPopularRank = val;
  }

  /**
   * 規格名称1を設定します
   * 
   * @param val
   *          規格名称1
   */
  public void setCommodityStandard1Name(String val) {
    this.commodityStandard1Name = val;
  }

  /**
   * 規格名称2を設定します
   * 
   * @param val
   *          規格名称2
   */
  public void setCommodityStandard2Name(String val) {
    this.commodityStandard2Name = val;
  }

  /**
   * 商品別ポイント付与率を設定します
   * 
   * @param val
   *          商品別ポイント付与率
   */
  public void setCommodityPointRate(Long val) {
    this.commodityPointRate = val;
  }

  /**
   * 商品別ポイント付与開始日時を設定します
   * 
   * @param val
   *          商品別ポイント付与開始日時
   */
  public void setCommodityPointStartDatetime(Date val) {
    this.commodityPointStartDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 商品別ポイント付与終了日時を設定します
   * 
   * @param val
   *          商品別ポイント付与終了日時
   */
  public void setCommodityPointEndDatetime(Date val) {
    this.commodityPointEndDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 販売フラグを設定します
   * 
   * @param val
   *          販売フラグ
   */
  public void setSaleFlg(Long val) {
    this.saleFlg = val;
  }

  /**
   * 表示クライアント区分を設定します
   * 
   * @param val
   *          表示クライアント区分
   */
  public void setDisplayClientType(Long val) {
    this.displayClientType = val;
  }

  public Long getCommodityType() {
    return commodityType;
  }

  public void setCommodityType(Long commodityType) {
    this.commodityType = commodityType;
  }

  public Long getSetCommodityFlg() {
    return setCommodityFlg;
  }

  public void setSetCommodityFlg(Long setCommodityFlg) {
    this.setCommodityFlg = setCommodityFlg;
  }

  /**
   * 入荷お知らせ可能フラグを設定します
   * 
   * @param val
   *          入荷お知らせ可能フラグ
   */
  public void setArrivalGoodsFlg(Long val) {
    this.arrivalGoodsFlg = val;
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
   * @return the returnFlg
   */
  public Long getReturnFlg() {
    return returnFlg;
  }

  /**
   * @param returnFlg
   *          the returnFlg to set
   */
  public void setReturnFlg(Long returnFlg) {
    this.returnFlg = returnFlg;
  }

  /**
   * @return the warningFlag
   */
  public String getWarningFlag() {
    return warningFlag;
  }

  /**
   * @param warningFlag
   *          the warningFlag to set
   */
  public void setWarningFlag(String warningFlag) {
    this.warningFlag = warningFlag;
  }

  /**
   * @return the leadTime
   */
  public Long getLeadTime() {
    return leadTime;
  }

  /**
   * @param leadTime
   *          the leadTime to set
   */
  public void setLeadTime(Long leadTime) {
    this.leadTime = leadTime;
  }

  /**
   * @return the saleFlag
   */
  public String getSaleFlag() {
    return saleFlag;
  }

  /**
   * @param saleFlag
   *          the saleFlag to set
   */
  public void setSaleFlag(String saleFlag) {
    this.saleFlag = saleFlag;
  }

  /**
   * @return the specFlag
   */
  public String getSpecFlag() {
    return specFlag;
  }

  /**
   * @param specFlag
   *          the specFlag to set
   */
  public void setSpecFlag(String specFlag) {
    this.specFlag = specFlag;
  }

  /**
   * @return the brandCode
   */
  public String getBrandCode() {
    return brandCode;
  }

  /**
   * @param brandCode
   *          the brandCode to set
   */
  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  /**
   * @return the commodityNameEn
   */
  public String getCommodityNameEn() {
    return commodityNameEn;
  }

  /**
   * @param commodityNameEn
   *          the commodityNameEn to set
   */
  public void setCommodityNameEn(String commodityNameEn) {
    this.commodityNameEn = commodityNameEn;
  }

  /**
   * @return the categoryPath
   */
  public String getCategoryPath() {
    return categoryPath;
  }

  /**
   * @param categoryPath
   *          the categoryPath to set
   */
  public void setCategoryPath(String categoryPath) {
    this.categoryPath = categoryPath;
  }

  /**
   * @return the categoryAttribute1
   */
  public String getCategoryAttribute1() {
    return categoryAttribute1;
  }

  /**
   * @param categoryAttribute1
   *          the categoryAttribute1 to set
   */
  public void setCategoryAttribute1(String categoryAttribute1) {
    this.categoryAttribute1 = categoryAttribute1;
  }

  /**
   * @param commodityNameJp
   *          the commodityNameJp to set
   */
  public void setCommodityNameJp(String commodityNameJp) {
    this.commodityNameJp = commodityNameJp;
  }

  /**
   * @return the commodityNameJp
   */
  public String getCommodityNameJp() {
    return commodityNameJp;
  }

  /**
   * @param commodityDescriptionPcJp
   *          the commodityDescriptionPcJp to set
   */
  public void setCommodityDescriptionPcJp(String commodityDescriptionPcJp) {
    this.commodityDescriptionPcJp = commodityDescriptionPcJp;
  }

  /**
   * @return the commodityDescriptionPcJp
   */
  public String getCommodityDescriptionPcJp() {
    return commodityDescriptionPcJp;
  }

  /**
   * @param commodityDescriptionMobileJp
   *          the commodityDescriptionMobileJp to set
   */
  public void setCommodityDescriptionMobileJp(String commodityDescriptionMobileJp) {
    this.commodityDescriptionMobileJp = commodityDescriptionMobileJp;
  }

  /**
   * @return the commodityDescriptionMobileJp
   */
  public String getCommodityDescriptionMobileJp() {
    return commodityDescriptionMobileJp;
  }

  /**
   * @param commodityDescriptionPcEn
   *          the commodityDescriptionPcEn to set
   */
  public void setCommodityDescriptionPcEn(String commodityDescriptionPcEn) {
    this.commodityDescriptionPcEn = commodityDescriptionPcEn;
  }

  /**
   * @return the commodityDescriptionPcEn
   */
  public String getCommodityDescriptionPcEn() {
    return commodityDescriptionPcEn;
  }

  /**
   * @param commodityDescriptionMobileEn
   *          the commodityDescriptionMobileEn to set
   */
  public void setCommodityDescriptionMobileEn(String commodityDescriptionMobileEn) {
    this.commodityDescriptionMobileEn = commodityDescriptionMobileEn;
  }

  /**
   * @return the commodityDescriptionMobileEn
   */
  public String getCommodityDescriptionMobileEn() {
    return commodityDescriptionMobileEn;
  }

  /**
   * @param commodityStandard1NameEn
   *          the commodityStandard1NameEn to set
   */
  public void setCommodityStandard1NameEn(String commodityStandard1NameEn) {
    this.commodityStandard1NameEn = commodityStandard1NameEn;
  }

  /**
   * @return the commodityStandard1NameEn
   */
  public String getCommodityStandard1NameEn() {
    return commodityStandard1NameEn;
  }

  /**
   * @param commodityStandard2NameEn
   *          the commodityStandard2NameEn to set
   */
  public void setCommodityStandard2NameEn(String commodityStandard2NameEn) {
    this.commodityStandard2NameEn = commodityStandard2NameEn;
  }

  /**
   * @return the commodityStandard2NameEn
   */
  public String getCommodityStandard2NameEn() {
    return commodityStandard2NameEn;
  }

  /**
   * @param commodityStandard1NameJp
   *          the commodityStandard1NameJp to set
   */
  public void setCommodityStandard1NameJp(String commodityStandard1NameJp) {
    this.commodityStandard1NameJp = commodityStandard1NameJp;
  }

  /**
   * @return the commodityStandard1NameJp
   */
  public String getCommodityStandard1NameJp() {
    return commodityStandard1NameJp;
  }

  /**
   * @param commodityStandard2NameJp
   *          the commodityStandard2NameJp to set
   */
  public void setCommodityStandard2NameJp(String commodityStandard2NameJp) {
    this.commodityStandard2NameJp = commodityStandard2NameJp;
  }

  /**
   * @return the commodityStandard2NameJp
   */
  public String getCommodityStandard2NameJp() {
    return commodityStandard2NameJp;
  }

  /**
   * @param categoryAttribute1En
   *          the categoryAttribute1En to set
   */
  public void setCategoryAttribute1En(String categoryAttribute1En) {
    this.categoryAttribute1En = categoryAttribute1En;
  }

  /**
   * @return the categoryAttribute1En
   */
  public String getCategoryAttribute1En() {
    return categoryAttribute1En;
  }

  /**
   * @param categoryAttribute1Jp
   *          the categoryAttribute1Jp to set
   */
  public void setCategoryAttribute1Jp(String categoryAttribute1Jp) {
    this.categoryAttribute1Jp = categoryAttribute1Jp;
  }

  /**
   * @return the categoryAttribute1Jp
   */
  public String getCategoryAttribute1Jp() {
    return categoryAttribute1Jp;
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
   * @return the hotFlgjp
   */
  public Long getHotFlgJp() {
    return hotFlgJp;
  }

  /**
   * @param hotFlgjp
   *          the hotFlgjp to set
   */
  public void setHotFlgJp(Long hotFlgJp) {
    this.hotFlgJp = hotFlgJp;
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
   * @return the chosenSortRank
   */
  public Long getChosenSortRank() {
    return chosenSortRank;
  }

  
  /**
   * @param chosenSortRank the chosenSortRank to set
   */
  public void setChosenSortRank(Long chosenSortRank) {
    this.chosenSortRank = chosenSortRank;
  }

}
