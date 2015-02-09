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
public class CCommodityCynchro implements Serializable, WebshopEntity {

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
  @Required
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

  @Length(500)
  @Metadata(name = "商品説明2", order = 8)
  private String commodityDescription2;

  @Length(500)
  @Metadata(name = "商品説明2", order = 9)
  private String commodityDescription3;

  @Length(500)
  @Metadata(name = "商品説明(一覧用)", order = 10)
  private String commodityDescriptionShort;

  @Length(500)
  @Metadata(name = "商品検索ワード", order = 11)
  private String commoditySearchWords;

  @Metadata(name = "販売開始日時", order = 12)
  private String saleStartDatetime;

  @Metadata(name = "販売終了日時", order = 13)
  private String saleEndDatetime;

  @Metadata(name = "特別価格開始日時", order = 14)
  private Date discountPriceStartDatetime;

  @Metadata(name = "特別価格終了日時", order = 15)
  private Date discountPriceEndDatetime;

  @Length(20)
  @Metadata(name = "規格1名称ID(TMALLの属性ID)", order = 16)
  private String standard1Id;

  @Length(20)
  @Metadata(name = "規格1名称", order = 17)
  private String standard1Name;

  @Length(20)
  @Metadata(name = "規格2名称ID(TMALLの属性ID)", order = 18)
  private String standard2Id;

  @Length(20)
  @Metadata(name = "規格2名称", order = 19)
  private String standard2Name;

  @Required
  @Length(1)
  @Metadata(name = "EC販売フラグ", order = 20)
  private String saleFlgEc;

  @Required
  @Length(1)
  @Metadata(name = "返品不可フラグ", order = 21)
  private Long returnFlg;

  @Length(1)
  @Metadata(name = "ワーニング区分", order = 22)
  private String warningFlag;

  @Length(1)
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
  @Metadata(name = "TMALL商品ID(APIの戻り値)", order = 27)
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

  @Length(4)
  @Metadata(name = "税コード", order = 32)
  private String taxCode;

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

  // add by zhangzhengtao 20130527 end
  
  @Required
  @Length(1)
  @Metadata(name = "商品期限管理フラグ", order = 80)
  private Long shelfLifeFlag;
  
  /**
   * @return the keywordCn1
   */
  public String getKeywordCn1() {
    return keywordCn1;
  }

  
  /**
   * @param keywordCn1 the keywordCn1 to set
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
   * @param keywordJp1 the keywordJp1 to set
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
   * @param keywordEn1 the keywordEn1 to set
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

  @Length(6)
  @Metadata(name = "保管日数", order = 81)
  private Long shelfLifeDays;

  @Length(8)
  @Metadata(name = "入り数", order = 82)
  private Long quantityPerUnit;

  @Required
  @Length(1)
  @Metadata(name = "大物フラグ", order = 83)
  private Long bigFlag;

  @Metadata(name = "ECへの同期時間", order = 84)
  private Date syncTimeEc;

  @Metadata(name = "TMALLへの同期時間", order = 85)
  private Date syncTimeTmall;

  @Required
  @Length(1)
  @Metadata(name = "ECへの同期フラグ(0:同期不可、1同期可能、2同期済み)", order = 86)
  private Long syncFlagEc;

  @Required
  @Length(1)
  @Metadata(name = "TMALLへの同期フラグ(0:同期不可、1同期可能、2同期済み)" )
  private Long syncFlagTmall;

  @Length(100)
  @Metadata(name = "ECへの同期ユーザー" )
  private String syncUserEc;

  @Length(100)
  @Metadata(name = "TMALLへの同期ユーザー" )
  private String syncUserTmall;

  @Required
  @Length(1)
  @Metadata(name = "ERP取込用データ対象フラグ(0：対象外、1：対象)" )
  private Long exportFlagErp;

  @Required
  @Length(1)
  @Metadata(name = "WMS取込用データ対象フラグ(0：対象外、1：対象)" )
  private Long exportFlagWms;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID" )
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ" )
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時" )
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ" )
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時" )
  private String updatedTime;

  /** 検索用カテゴリパス */
  @Length(256)
  @Metadata(name = "検索用カテゴリパス" )
  private String categorySearchPath;
  
  /** 商品の分類属性値 */
  @Length(256)
  @Metadata(name = "商品の分類属性値" )
  private String categoryAttributeValue;
  
  /** TMALL检索关键字 */
  @Length(500)
  @Metadata(name = "TMALL检索关键字", order = 156)
  private String tmallCommoditySearchWords;
  
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

  private BigDecimal purchasePrice;
  
  
  private BigDecimal suggestePrice;
  
  private BigDecimal minPrice; 
  
  private String fixedPriceFlag;
  
  private BigDecimal unitPrice;
  
  private BigDecimal tmallUnitPrice;
  
  private BigDecimal discountPrice;
  
  private BigDecimal tmallDiscountPrice;
   
  private String cynchroTime;
  private String useFlg;
  private String tmallUseFlg;
  
  // 2014/05/03 京东WBS对应 ob_姚 add start
  private String jdUseFlg;
  
  private String jdCategoryId;
  
  private Long syncFlagJd;
  
  private Date syncTimeJd;
  
  private String syncUserJd;
  
  private String jdCommodityId;
  // 2014/05/03 京东WBS对应 ob_姚 add end
  
  /**
   * @return the categorySearchPath
   */
  public String getCategorySearchPath() {
    return categorySearchPath;
  }
  
  /**
   * @param categorySearchPath the categorySearchPath to set
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
   * @param categoryAttributeValue the categoryAttributeValue to set
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

 
  
  /**
   * @return the tmallRepresentSkuPrice
   */
  public BigDecimal getTmallRepresentSkuPrice() {
    return tmallRepresentSkuPrice;
  }

  
  /**
   * @param tmallRepresentSkuPrice the tmallRepresentSkuPrice to set
   */
  public void setTmallRepresentSkuPrice(BigDecimal tmallRepresentSkuPrice) {
    this.tmallRepresentSkuPrice = tmallRepresentSkuPrice;
  }

   

  public Date getDiscountPriceStartDatetime() {
    return discountPriceStartDatetime;
  }

  public void setDiscountPriceStartDatetime(Date discountPriceStartDatetime) {
    this.discountPriceStartDatetime = discountPriceStartDatetime;
  }

  public Date getDiscountPriceEndDatetime() {
    return discountPriceEndDatetime;
  }

  public void setDiscountPriceEndDatetime(Date discountPriceEndDatetime) {
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

  public String getTaxCode() {
    return taxCode;
  }

  public void setTaxCode(String taxCode) {
    this.taxCode = taxCode;
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

  public Long getQuantityPerUnit() {
    return quantityPerUnit;
  }

  public void setQuantityPerUnit(Long quantityPerUnit) {
    this.quantityPerUnit = quantityPerUnit;
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
   * @param purchasePrice the purchasePrice to set
   */
  public void setPurchasePrice(BigDecimal purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  /**
   * @return the purchasePrice
   */
  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  /**
   * @param suggestePrice the suggestePrice to set
   */
  public void setSuggestePrice(BigDecimal suggestePrice) {
    this.suggestePrice = suggestePrice;
  }

  /**
   * @return the suggestePrice
   */
  public BigDecimal getSuggestePrice() {
    return suggestePrice;
  }

  /**
   * @param minPrice the minPrice to set
   */
  public void setMinPrice(BigDecimal minPrice) {
    this.minPrice = minPrice;
  }

  /**
   * @return the minPrice
   */
  public BigDecimal getMinPrice() {
    return minPrice;
  }

  
  /**
   * @param unitPrice the unitPrice to set
   */
  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  /**
   * @return the unitPrice
   */
  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  /**
   * @param tmallUnitPrice the tmallUnitPrice to set
   */
  public void setTmallUnitPrice(BigDecimal tmallUnitPrice) {
    this.tmallUnitPrice = tmallUnitPrice;
  }

  /**
   * @return the tmallUnitPrice
   */
  public BigDecimal getTmallUnitPrice() {
    return tmallUnitPrice;
  }

  /**
   * @param discountPrice the discountPrice to set
   */
  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
  }

  /**
   * @return the discountPrice
   */
  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  /**
   * @param tmallDiscountPrice the tmallDiscountPrice to set
   */
  public void setTmallDiscountPrice(BigDecimal tmallDiscountPrice) {
    this.tmallDiscountPrice = tmallDiscountPrice;
  }

  /**
   * @return the tmallDiscountPrice
   */
  public BigDecimal getTmallDiscountPrice() {
    return tmallDiscountPrice;
  }

  /**
   * @param cynchroTime the cynchroTime to set
   */
  public void setCynchroTime(String cynchroTime) {
    this.cynchroTime = cynchroTime;
  }

  /**
   * @return the cynchroTime
   */
  public String getCynchroTime() {
    return cynchroTime;
  }

  /**
   * @param saleStartDatetime the saleStartDatetime to set
   */
  public void setSaleStartDatetime(String saleStartDatetime) {
    this.saleStartDatetime = saleStartDatetime;
  }

  /**
   * @return the saleStartDatetime
   */
  public String getSaleStartDatetime() {
    return saleStartDatetime;
  }

  /**
   * @param saleEndDatetime the saleEndDatetime to set
   */
  public void setSaleEndDatetime(String saleEndDatetime) {
    this.saleEndDatetime = saleEndDatetime;
  }

  /**
   * @return the saleEndDatetime
   */
  public String getSaleEndDatetime() {
    return saleEndDatetime;
  }

  /**
   * @param saleFlgEc the saleFlgEc to set
   */
  public void setSaleFlgEc(String saleFlgEc) {
    this.saleFlgEc = saleFlgEc;
  }

  /**
   * @return the saleFlgEc
   */
  public String getSaleFlgEc() {
    return saleFlgEc;
  }

  /**
   * @param fixedPriceFlag the fixedPriceFlag to set
   */
  public void setFixedPriceFlag(String fixedPriceFlag) {
    this.fixedPriceFlag = fixedPriceFlag;
  }

  /**
   * @return the fixedPriceFlag
   */
  public String getFixedPriceFlag() {
    return fixedPriceFlag;
  }

  /**
   * @param updatedTime the updatedTime to set
   */
  public void setUpdatedTime(String updatedTime) {
    this.updatedTime = updatedTime;
  }

  /**
   * @return the updatedTime
   */
  public String getUpdatedTime() {
    return updatedTime;
  }

  @Override
  public Date getUpdatedDatetime() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setUpdatedDatetime(Date val) {
    // TODO Auto-generated method stub
    
  }

  /**
   * @param useFlg the useFlg to set
   */
  public void setUseFlg(String useFlg) {
    this.useFlg = useFlg;
  }

  /**
   * @return the useFlg
   */
  public String getUseFlg() {
    return useFlg;
  }

  /**
   * @param tmallUseFlg the tmallUseFlg to set
   */
  public void setTmallUseFlg(String tmallUseFlg) {
    this.tmallUseFlg = tmallUseFlg;
  }

  /**
   * @return the tmallUseFlg
   */
  public String getTmallUseFlg() {
    return tmallUseFlg;
  }


  /**
   * @return the jdUseFlg
   */
  public String getJdUseFlg() {
    return jdUseFlg;
  }


  /**
   * @param jdUseFlg the jdUseFlg to set
   */
  public void setJdUseFlg(String jdUseFlg) {
    this.jdUseFlg = jdUseFlg;
  }


  /**
   * @return the jdCategoryId
   */
  public String getJdCategoryId() {
    return jdCategoryId;
  }


  /**
   * @param jdCategoryId the jdCategoryId to set
   */
  public void setJdCategoryId(String jdCategoryId) {
    this.jdCategoryId = jdCategoryId;
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
   * @return the syncTimeJd
   */
  public Date getSyncTimeJd() {
    return syncTimeJd;
  }


  /**
   * @param syncTimeJd the syncTimeJd to set
   */
  public void setSyncTimeJd(Date syncTimeJd) {
    this.syncTimeJd = syncTimeJd;
  }


  /**
   * @return the syncUserJd
   */
  public String getSyncUserJd() {
    return syncUserJd;
  }


  /**
   * @param syncUserJd the syncUserJd to set
   */
  public void setSyncUserJd(String syncUserJd) {
    this.syncUserJd = syncUserJd;
  }


  /**
   * @return the jdCommodityId
   */
  public String getJdCommodityId() {
    return jdCommodityId;
  }


  /**
   * @param jdCommodityId the jdCommodityId to set
   */
  public void setJdCommodityId(String jdCommodityId) {
    this.jdCommodityId = jdCommodityId;
  }
 
 

}
