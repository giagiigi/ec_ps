package jp.co.sint.webshop.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.utility.DateUtil;

public class CommodityHeadline implements Serializable,Cloneable {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** ショップコード */
  private String shopCode;

  /** 商品コード */
  private String commodityCode;

  /** 商品名称 */
  private String commodityName;

  private String commodityNameCn;

  private String commodityNameEn;

  private String commodityNameJp;

  /** 代表SKUコード */
  private String representSkuCode;

  /** 在庫管理区分 */
  private Long stockManagementType;

  // add by tangweihui 2012-11-16 start
  // 商品区分
  private Long commodityType;

  // add by tangweihui 2012-11-16 end

  private Long importCommodityType;

  private Long clearCommodityType;

  private Long reserveCommodityType1;

  private Long reserveCommodityType2;

  private Long reserveCommodityType3;

  private Long newReserveCommodityType1;

  private Long newReserveCommodityType2;

  private Long newReserveCommodityType3;

  private Long newReserveCommodityType4;

  private Long newReserveCommodityType5;

  private Long innerQuantity;
  
  private boolean discountCommodityFlg;

  /** 商品消費税区分 */
  private Long commodityTaxType;

  /** PC商品説明 */
  private String commodityDescriptionPc;

  private String commodityDescriptionPcEn;

  private String commodityDescriptionPcJp;

  /** 携帯商品説明 */
  private String commodityDescriptionMobile;

  private String commodityDescriptionMobileEn;

  private String commodityDescriptionMobileJp;

  /** 商品検索ワード */
  private String commoditySearchWords;

  /** シャドウ検索ワード */
  private String shadowSearchWords;

  /** 販売開始日時 */
  private Date saleStartDatetime;

  /** 販売終了日時 */
  private Date saleEndDatetime;

  /** 価格改定日時 */
  private Date changeUnitPriceDatetime;

  /** 特別価格開始日時 */
  private Date discountPriceStartDatetime;

  /** 特別価格終了日時 */
  private Date discountPriceEndDatetime;

  /** 予約開始日時 */
  private Date reservationStartDatetime;

  /** 予約終了日時 */
  private Date reservationEndDatetime;

  /** 配送種別番号 */
  private Long deliveryTypeNo;

  /** リンクURL */
  private String linkUrl;

  /** おすすめ商品順位 */
  private Long recommendCommodityRank;

  /** 人気順位 */
  private Long commodityPopularRank;

  /** 規格名称1 */
  private String commodityStandard1Name;

  private String commodityStandard1NameEn;

  private String commodityStandard1NameJp;

  /** 規格名称2 */
  private String commodityStandard2Name;

  private String commodityStandard2NameEn;

  private String commodityStandard2NameJp;

  /** 商品別ポイント付与率 */
  private Long commodityPointRate;

  /** 商品別ポイント付与開始日時 */
  private Date commodityPointStartDatetime;

  /** 商品別ポイント付与終了日時 */
  private Date commodityPointEndDatetime;

  /** 販売フラグ */
  private Long saleFlg;

  /** 表示クライアント区分 */
  private Long displayClientType;

  /** 入荷お知らせ可能フラグ */
  private Long arrivalGoodsFlg;

  /** 商品単価 */
  private BigDecimal unitPrice;

  /** 特別価格 */
  private BigDecimal discountPrice;

  /** 予約価格 */
  private BigDecimal reservationPrice;

  /** 改定価格 */
  private BigDecimal changeUnitPrice;

  /** JANコード */
  private String janCode;

  /** 規格詳細1名称 */
  private String standardDetail1Name;

  private String standardDetail1NameEn;

  private String standardDetail1NameJp;

  /** 規格詳細2名称 */
  private String standardDetail2Name;

  private String standardDetail2NameEn;

  private String standardDetail2NameJp;

  /** 在庫数量 */
  private Long stockQuantity;

  /** 引当数量 */
  private Long allocatedQuantity;

  /** 予約数量 */
  private Long reservedQuantity;

  /** 予約上限数 */
  private Long reservationLimit;

  /** 注文毎予約上限数 */
  private Long oneshotReservationLimit;

  /** 在庫閾値 * */
  private Long stockThreshold;

  /** カテゴリコード */
  private String categoryCode;

  /** PC用カテゴリ名称 */
  private String categoryNamePc;

  /** 携帯用カテゴリ名称 */
  private String categoryNameMobile;

  /** 親カテゴリコード */
  private String parentCategoryCode;

  /** パス */
  private String path;

  /** 階層 */
  private Long depth;

  /** PC商品件数 */
  private Long commodityCountPc;

  /** 携帯商品件数 */
  private Long commodityCountMobile;

  /** リンク商品コード */
  private String linkCommodityCode;

  /** リンクショップコード */
  private String linkShopCode;

  /** キャンペーンコード */
  private String campaignCode;

  /** キャンペーン名称 */
  private String campaignName;

  /** キャンペーン開始日 */
  private Date campaignStartDate;

  /** キャンペーン終了日 */
  private Date campaignEndDate;

  /** キャンペーン値引率 */
  private Long campaignDiscountRate;

  /** メモ */
  private String memo;

  /** ギフトコード */
  private String giftCode;

  /** ギフト名称 */
  private String giftName;

  /** ギフト説明 */
  private String giftDescription;

  /** ギフト価格 */
  private BigDecimal giftPrice;

  /** ギフト消費税区分 */
  private Long giftTaxType;

  /** 表示フラグ */
  private Long displayFlg;

  /** タグコード */
  private String tagCode;

  /** タグ名称 */
  private String tagName;

  /** 表示順 */
  private Long displayOrder;

  /** 在庫状況番号 */
  private Long stockStatusNo;

  /** 在庫状況分類名 */
  private String stockStatusName;

  /** 在庫多メッセージ */
  private String stockSufficientMessage;

  /** 在庫少メッセージ */
  private String stockLittleMessage;

  /** 在庫切メッセージ */
  private String outOfStockMessage;

  /** 在庫多閾値 */
  private Long stockSufficientThreshold;

  /** レビューID */
  private Long reviewId;

  /** レビュータイトル */
  private String reviewTitle;

  /** ニックネーム */
  private String nickname;

  /** レビュー投稿日時 */
  private Date reviewContributedDatetime;

  /** レビュー内容 */
  private String reviewDescription;

  /** 商品レビュー表示区分 */
  private Long reviewDisplayType;

  /** レビューポイント割当ステータス */
  private Long reviewPointAllocatedStatus;

  /** レビュー点数集計ID */
  private Long reviewSummaryId;

  /** 集計期間開始日 */
  private Date countTermStartDate;

  /** 集計期間終了日 */
  private Date countTermEndDate;

  /** レビュー点数 */
  private Long reviewScore;

  /** レビュー件数 */
  private Long reviewCount;

  /** 人気ランキング集計ID */
  private Long popularRankingCountId;

  /** 受注金額ランキング */
  private Long orderRanking;

  /** 前回受注金額ランキング */
  private Long lasttimeOrderRanking;

  /** 購入数量ランキング */
  private Long countRanking;

  /** 前回購入数量ランキング */
  private Long lasttimeCountRanking;

  /** SKUコード */
  private String skuCode;

  /** メールアドレス */
  private String email;

  /** 顧客コード */
  private String customerCode;

  /** ショップ開店日時 */
  private Date openDatetime;

  /** ショップ閉店日時 */
  private Date closeDatetime;

  /** ショップ名称 */
  private String shopName;

  /** ショップ紹介URL */
  private String shopIntroducedUrl;

  /** 郵便番号 */
  private String postalCode;

  /** 都道府県コード */
  private String prefectureCode;

  /** 住所1 */
  private String address1;

  /** 住所2 */
  private String address2;

  /** 住所3 */
  private String address3;

  /** 住所4 */
  private String address4;

  /** 電話番号 */
  private String phoneNumber;

  /** 手机号码 */
  private String mobileNumber;

  /** 担当者 */
  private String personInCharge;

  /** SSLページ */
  private String sslPage;

  /** ショップ区分 */
  private Long shopType;

  // 以下、DTO以外の項目
  /** 利用可能な在庫 */
  private Long availableStockQuantity;

  /** 販売価格 */
  private BigDecimal retailPrice;

  /** カテゴリ関連付き件数 */
  private Long relatedCategoryCount;

  /** キャンペーン関連付き件数 */
  private Long relatedCampaignCount;

  /** ギフト関連付き件数 */
  private Long relatedGiftCount;

  /** タグ関連付き件数 */
  private Long relatedTagCount;

  // 2012/11/17 促销对应 ob add start
  /** 套餐商品flg */
  private Long setCommodityFlg;

  /** 商品明细件数 */
  private Long relatedCompositionCount;

  // 2012/11/17 促销对应 ob add end

  /** 関連商品A関連付き件数 */
  private Long relatedCommodityACount;

  private Long relatedCommodityBCount;

  private Long stockStatus;

  private Long saleType;

  private Long saleStatus;

  // add by wjw 20120103 start
  private String infinityFlagEc;

  private String discountmode;

  private String actStock;

  // modified by cs_yuli 20120619 start
  // private String rankingScore;
  private Long rankingScore;

  // add by wjw 20120103 end
  // modified by cs_yuli 20120619 end
  private String warningFlag;

  private Long useFlg;

  private String brandCode;

  private String categoryPath;

  private String categoryAttribute1;

  private String brandName;

  private String brandDescription;

  private BigDecimal weight;

  // ADD BY CS_YULI 20120524 START
  private String brandEnglishName;

  private String brandJapaneseName;

  private String brandDescriptionEn;

  private String brandDescriptionJp;

  // ADD BY CS_YULI 20120524 END

  private String originalCommodityCode;

  private Long combinationAmount;

  // 20130808 txw add start

  private String title;

  private String titleEn;

  private String titleJp;

  private String description;

  private String descriptionEn;

  private String descriptionJp;

  private String keyword;

  private String keywordEn;

  private String keywordJp;

  private String priceMode;

  // 20130808 txw add end

  private String prefectureCodes;

  //品店精选排序顺序
  private Long chosenSortRank;
  /**
   * @return the saleType
   */
  public Long getSaleType() {
    return saleType;
  }

  /**
   * @param saleType
   *          the saleType to set
   */
  public void setSaleType(Long saleType) {
    this.saleType = saleType;
  }

  /**
   * @return the stockStatus
   */
  public Long getStockStatus() {
    return stockStatus;
  }

  /**
   * @param stockStatus
   *          the stockStatus to set
   */
  public void setStockStatus(Long stockStatus) {
    this.stockStatus = stockStatus;
  }

  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }

  /**
   * @param address1
   *          the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  /**
   * @return the address2
   */
  public String getAddress2() {
    return address2;
  }

  /**
   * @param address2
   *          the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  /**
   * @return the address3
   */
  public String getAddress3() {
    return address3;
  }

  /**
   * @param address3
   *          the address3 to set
   */
  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  /**
   * @return the address4
   */
  public String getAddress4() {
    return address4;
  }

  /**
   * @param address4
   *          the address4 to set
   */
  public void setAddress4(String address4) {
    this.address4 = address4;
  }

  /**
   * @return the allocatedQuantity
   */
  public Long getAllocatedQuantity() {
    return allocatedQuantity;
  }

  /**
   * @param allocatedQuantity
   *          the allocatedQuantity to set
   */
  public void setAllocatedQuantity(Long allocatedQuantity) {
    this.allocatedQuantity = allocatedQuantity;
  }

  /**
   * @return the arrivalGoodsFlg
   */
  public Long getArrivalGoodsFlg() {
    return arrivalGoodsFlg;
  }

  /**
   * @param arrivalGoodsFlg
   *          the arrivalGoodsFlg to set
   */
  public void setArrivalGoodsFlg(Long arrivalGoodsFlg) {
    this.arrivalGoodsFlg = arrivalGoodsFlg;
  }

  /**
   * @return the availableStockQuantity
   */
  public Long getAvailableStockQuantity() {
    return availableStockQuantity;
  }

  /**
   * @param availableStockQuantity
   *          the availableStockQuantity to set
   */
  public void setAvailableStockQuantity(Long availableStockQuantity) {
    this.availableStockQuantity = availableStockQuantity;
  }

  /**
   * @return the campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
  }

  /**
   * @param campaignCode
   *          the campaignCode to set
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  /**
   * @return the campaignDiscountRate
   */
  public Long getCampaignDiscountRate() {
    return campaignDiscountRate;
  }

  /**
   * @param campaignDiscountRate
   *          the campaignDiscountRate to set
   */
  public void setCampaignDiscountRate(Long campaignDiscountRate) {
    this.campaignDiscountRate = campaignDiscountRate;
  }

  /**
   * @return the campaignEndDate
   */
  public Date getCampaignEndDate() {
    return DateUtil.immutableCopy(campaignEndDate);
  }

  /**
   * @param campaignEndDate
   *          the campaignEndDate to set
   */
  public void setCampaignEndDate(Date campaignEndDate) {
    this.campaignEndDate = DateUtil.immutableCopy(campaignEndDate);
  }

  /**
   * @return the campaignName
   */
  public String getCampaignName() {
    return campaignName;
  }

  /**
   * @param campaignName
   *          the campaignName to set
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  /**
   * @return the campaignStartDate
   */
  public Date getCampaignStartDate() {
    return DateUtil.immutableCopy(campaignStartDate);
  }

  /**
   * @param campaignStartDate
   *          the campaignStartDate to set
   */
  public void setCampaignStartDate(Date campaignStartDate) {
    this.campaignStartDate = DateUtil.immutableCopy(campaignStartDate);
  }

  /**
   * @return the categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * @param categoryCode
   *          the categoryCode to set
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  /**
   * @return the categoryNameMobile
   */
  public String getCategoryNameMobile() {
    return categoryNameMobile;
  }

  /**
   * @param categoryNameMobile
   *          the categoryNameMobile to set
   */
  public void setCategoryNameMobile(String categoryNameMobile) {
    this.categoryNameMobile = categoryNameMobile;
  }

  /**
   * @return the categoryNamePc
   */
  public String getCategoryNamePc() {
    return categoryNamePc;
  }

  /**
   * @param categoryNamePc
   *          the categoryNamePc to set
   */
  public void setCategoryNamePc(String categoryNamePc) {
    this.categoryNamePc = categoryNamePc;
  }

  /**
   * @return the changeUnitPrice
   */
  public BigDecimal getChangeUnitPrice() {
    return changeUnitPrice;
  }

  /**
   * @param changeUnitPrice
   *          the changeUnitPrice to set
   */
  public void setChangeUnitPrice(BigDecimal changeUnitPrice) {
    this.changeUnitPrice = changeUnitPrice;
  }

  /**
   * @return the changeUnitPriceDatetime
   */
  public Date getChangeUnitPriceDatetime() {
    return DateUtil.immutableCopy(changeUnitPriceDatetime);
  }

  /**
   * @param changeUnitPriceDatetime
   *          the changeUnitPriceDatetime to set
   */
  public void setChangeUnitPriceDatetime(Date changeUnitPriceDatetime) {
    this.changeUnitPriceDatetime = DateUtil.immutableCopy(changeUnitPriceDatetime);
  }

  /**
   * @return the closeDatetime
   */
  public Date getCloseDatetime() {
    return DateUtil.immutableCopy(closeDatetime);
  }

  /**
   * @param closeDatetime
   *          the closeDatetime to set
   */
  public void setCloseDatetime(Date closeDatetime) {
    this.closeDatetime = DateUtil.immutableCopy(closeDatetime);
  }

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return the commodityCountMobile
   */
  public Long getCommodityCountMobile() {
    return commodityCountMobile;
  }

  /**
   * @param commodityCountMobile
   *          the commodityCountMobile to set
   */
  public void setCommodityCountMobile(Long commodityCountMobile) {
    this.commodityCountMobile = commodityCountMobile;
  }

  /**
   * @return the commodityCountPc
   */
  public Long getCommodityCountPc() {
    return commodityCountPc;
  }

  /**
   * @param commodityCountPc
   *          the commodityCountPc to set
   */
  public void setCommodityCountPc(Long commodityCountPc) {
    this.commodityCountPc = commodityCountPc;
  }

  /**
   * @return the commodityDescriptionMobile
   */
  public String getCommodityDescriptionMobile() {
    return commodityDescriptionMobile;
  }

  /**
   * @param commodityDescriptionMobile
   *          the commodityDescriptionMobile to set
   */
  public void setCommodityDescriptionMobile(String commodityDescriptionMobile) {
    this.commodityDescriptionMobile = commodityDescriptionMobile;
  }

  /**
   * @return the commodityDescriptionPc
   */
  public String getCommodityDescriptionPc() {
    return commodityDescriptionPc;
  }

  /**
   * @param commodityDescriptionPc
   *          the commodityDescriptionPc to set
   */
  public void setCommodityDescriptionPc(String commodityDescriptionPc) {
    this.commodityDescriptionPc = commodityDescriptionPc;
  }

  /**
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * @param commodityName
   *          the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * @return the commodityPointEndDatetime
   */
  public Date getCommodityPointEndDatetime() {
    return DateUtil.immutableCopy(commodityPointEndDatetime);
  }

  /**
   * @param commodityPointEndDatetime
   *          the commodityPointEndDatetime to set
   */
  public void setCommodityPointEndDatetime(Date commodityPointEndDatetime) {
    this.commodityPointEndDatetime = DateUtil.immutableCopy(commodityPointEndDatetime);
  }

  /**
   * @return the commodityPointRate
   */
  public Long getCommodityPointRate() {
    return commodityPointRate;
  }

  /**
   * @param commodityPointRate
   *          the commodityPointRate to set
   */
  public void setCommodityPointRate(Long commodityPointRate) {
    this.commodityPointRate = commodityPointRate;
  }

  /**
   * @return the commodityPointStartDatetime
   */
  public Date getCommodityPointStartDatetime() {
    return DateUtil.immutableCopy(commodityPointStartDatetime);
  }

  /**
   * @param commodityPointStartDatetime
   *          the commodityPointStartDatetime to set
   */
  public void setCommodityPointStartDatetime(Date commodityPointStartDatetime) {
    this.commodityPointStartDatetime = DateUtil.immutableCopy(commodityPointStartDatetime);
  }

  /**
   * @return the commoditySearchWords
   */
  public String getCommoditySearchWords() {
    return commoditySearchWords;
  }

  /**
   * @param commoditySearchWords
   *          the commoditySearchWords to set
   */
  public void setCommoditySearchWords(String commoditySearchWords) {
    this.commoditySearchWords = commoditySearchWords;
  }

  /**
   * @return the commodityStandard1Name
   */
  public String getCommodityStandard1Name() {
    return commodityStandard1Name;
  }

  /**
   * @param commodityStandard1Name
   *          the commodityStandard1Name to set
   */
  public void setCommodityStandard1Name(String commodityStandard1Name) {
    this.commodityStandard1Name = commodityStandard1Name;
  }

  /**
   * @return the commodityStandard2Name
   */
  public String getCommodityStandard2Name() {
    return commodityStandard2Name;
  }

  /**
   * @param commodityStandard2Name
   *          the commodityStandard2Name to set
   */
  public void setCommodityStandard2Name(String commodityStandard2Name) {
    this.commodityStandard2Name = commodityStandard2Name;
  }

  /**
   * @return the commodityTaxType
   */
  public Long getCommodityTaxType() {
    return commodityTaxType;
  }

  /**
   * @param commodityTaxType
   *          the commodityTaxType to set
   */
  public void setCommodityTaxType(Long commodityTaxType) {
    this.commodityTaxType = commodityTaxType;
  }

  /**
   * @return the countRanking
   */
  public Long getCountRanking() {
    return countRanking;
  }

  /**
   * @param countRanking
   *          the countRanking to set
   */
  public void setCountRanking(Long countRanking) {
    this.countRanking = countRanking;
  }

  /**
   * @return the countTermEndDate
   */
  public Date getCountTermEndDate() {
    return DateUtil.immutableCopy(countTermEndDate);
  }

  /**
   * @param countTermEndDate
   *          the countTermEndDate to set
   */
  public void setCountTermEndDate(Date countTermEndDate) {
    this.countTermEndDate = DateUtil.immutableCopy(countTermEndDate);
  }

  /**
   * @return the countTermStartDate
   */
  public Date getCountTermStartDate() {
    return DateUtil.immutableCopy(countTermStartDate);
  }

  /**
   * @param countTermStartDate
   *          the countTermStartDate to set
   */
  public void setCountTermStartDate(Date countTermStartDate) {
    this.countTermStartDate = DateUtil.immutableCopy(countTermStartDate);
  }

  /**
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @param customerCode
   *          the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * @return the deliveryTypeNo
   */
  public Long getDeliveryTypeNo() {
    return deliveryTypeNo;
  }

  /**
   * @param deliveryTypeNo
   *          the deliveryTypeNo to set
   */
  public void setDeliveryTypeNo(Long deliveryTypeNo) {
    this.deliveryTypeNo = deliveryTypeNo;
  }

  /**
   * @return the depth
   */
  public Long getDepth() {
    return depth;
  }

  /**
   * @param depth
   *          the depth to set
   */
  public void setDepth(Long depth) {
    this.depth = depth;
  }

  /**
   * @return the discountPrice
   */
  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  /**
   * @param discountPrice
   *          the discountPrice to set
   */
  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
  }

  /**
   * @return the discountPriceEndDatetime
   */
  public Date getDiscountPriceEndDatetime() {
    return DateUtil.immutableCopy(discountPriceEndDatetime);
  }

  /**
   * @param discountPriceEndDatetime
   *          the discountPriceEndDatetime to set
   */
  public void setDiscountPriceEndDatetime(Date discountPriceEndDatetime) {
    this.discountPriceEndDatetime = DateUtil.immutableCopy(discountPriceEndDatetime);
  }

  /**
   * @return the discountPriceStartDatetime
   */
  public Date getDiscountPriceStartDatetime() {
    return DateUtil.immutableCopy(discountPriceStartDatetime);
  }

  /**
   * @param discountPriceStartDatetime
   *          the discountPriceStartDatetime to set
   */
  public void setDiscountPriceStartDatetime(Date discountPriceStartDatetime) {
    this.discountPriceStartDatetime = DateUtil.immutableCopy(discountPriceStartDatetime);
  }

  /**
   * @return the displayClientType
   */
  public Long getDisplayClientType() {
    return displayClientType;
  }

  /**
   * @param displayClientType
   *          the displayClientType to set
   */
  public void setDisplayClientType(Long displayClientType) {
    this.displayClientType = displayClientType;
  }

  /**
   * @return the displayFlg
   */
  public Long getDisplayFlg() {
    return displayFlg;
  }

  /**
   * @param displayFlg
   *          the displayFlg to set
   */
  public void setDisplayFlg(Long displayFlg) {
    this.displayFlg = displayFlg;
  }

  /**
   * @return the displayOrder
   */
  public Long getDisplayOrder() {
    return displayOrder;
  }

  /**
   * @param displayOrder
   *          the displayOrder to set
   */
  public void setDisplayOrder(Long displayOrder) {
    this.displayOrder = displayOrder;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the giftCode
   */
  public String getGiftCode() {
    return giftCode;
  }

  /**
   * @param giftCode
   *          the giftCode to set
   */
  public void setGiftCode(String giftCode) {
    this.giftCode = giftCode;
  }

  /**
   * @return the giftDescription
   */
  public String getGiftDescription() {
    return giftDescription;
  }

  /**
   * @param giftDescription
   *          the giftDescription to set
   */
  public void setGiftDescription(String giftDescription) {
    this.giftDescription = giftDescription;
  }

  /**
   * @return the giftName
   */
  public String getGiftName() {
    return giftName;
  }

  /**
   * @param giftName
   *          the giftName to set
   */
  public void setGiftName(String giftName) {
    this.giftName = giftName;
  }

  /**
   * @return the giftPrice
   */
  public BigDecimal getGiftPrice() {
    return giftPrice;
  }

  /**
   * @param giftPrice
   *          the giftPrice to set
   */
  public void setGiftPrice(BigDecimal giftPrice) {
    this.giftPrice = giftPrice;
  }

  /**
   * @return the giftTaxType
   */
  public Long getGiftTaxType() {
    return giftTaxType;
  }

  /**
   * @param giftTaxType
   *          the giftTaxType to set
   */
  public void setGiftTaxType(Long giftTaxType) {
    this.giftTaxType = giftTaxType;
  }

  /**
   * @return the janCode
   */
  public String getJanCode() {
    return janCode;
  }

  /**
   * @param janCode
   *          the janCode to set
   */
  public void setJanCode(String janCode) {
    this.janCode = janCode;
  }

  /**
   * @return the lasttimeCountRanking
   */
  public Long getLasttimeCountRanking() {
    return lasttimeCountRanking;
  }

  /**
   * @param lasttimeCountRanking
   *          the lasttimeCountRanking to set
   */
  public void setLasttimeCountRanking(Long lasttimeCountRanking) {
    this.lasttimeCountRanking = lasttimeCountRanking;
  }

  /**
   * @return the lasttimeOrderRanking
   */
  public Long getLasttimeOrderRanking() {
    return lasttimeOrderRanking;
  }

  /**
   * @param lasttimeOrderRanking
   *          the lasttimeOrderRanking to set
   */
  public void setLasttimeOrderRanking(Long lasttimeOrderRanking) {
    this.lasttimeOrderRanking = lasttimeOrderRanking;
  }

  /**
   * @return the linkCommodityCode
   */
  public String getLinkCommodityCode() {
    return linkCommodityCode;
  }

  /**
   * @param linkCommodityCode
   *          the linkCommodityCode to set
   */
  public void setLinkCommodityCode(String linkCommodityCode) {
    this.linkCommodityCode = linkCommodityCode;
  }

  /**
   * @return the linkShopCode
   */
  public String getLinkShopCode() {
    return linkShopCode;
  }

  /**
   * @param linkShopCode
   *          the linkShopCode to set
   */
  public void setLinkShopCode(String linkShopCode) {
    this.linkShopCode = linkShopCode;
  }

  /**
   * @return the linkUrl
   */
  public String getLinkUrl() {
    return linkUrl;
  }

  /**
   * @param linkUrl
   *          the linkUrl to set
   */
  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
  }

  /**
   * @return the memo
   */
  public String getMemo() {
    return memo;
  }

  /**
   * @param memo
   *          the memo to set
   */
  public void setMemo(String memo) {
    this.memo = memo;
  }

  /**
   * @return the nickname
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * @param nickname
   *          the nickname to set
   */
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  /**
   * @return the oneshotReservationLimit
   */
  public Long getOneshotReservationLimit() {
    return oneshotReservationLimit;
  }

  /**
   * @param oneshotReservationLimit
   *          the oneshotReservationLimit to set
   */
  public void setOneshotReservationLimit(Long oneshotReservationLimit) {
    this.oneshotReservationLimit = oneshotReservationLimit;
  }

  /**
   * stockThresholdを取得します。
   * 
   * @return stockThreshold
   */
  public Long getStockThreshold() {
    return stockThreshold;
  }

  /**
   * stockThresholdを設定します。
   * 
   * @param stockThreshold
   *          stockThreshold
   */
  public void setStockThreshold(Long stockThreshold) {
    this.stockThreshold = stockThreshold;
  }

  /**
   * @return the openDatetime
   */
  public Date getOpenDatetime() {
    return DateUtil.immutableCopy(openDatetime);
  }

  /**
   * @param openDatetime
   *          the openDatetime to set
   */
  public void setOpenDatetime(Date openDatetime) {
    this.openDatetime = DateUtil.immutableCopy(openDatetime);
  }

  /**
   * @return the orderRanking
   */
  public Long getOrderRanking() {
    return orderRanking;
  }

  /**
   * @param orderRanking
   *          the orderRanking to set
   */
  public void setOrderRanking(Long orderRanking) {
    this.orderRanking = orderRanking;
  }

  /**
   * @return the outOfStockMessage
   */
  public String getOutOfStockMessage() {
    return outOfStockMessage;
  }

  /**
   * @param outOfStockMessage
   *          the outOfStockMessage to set
   */
  public void setOutOfStockMessage(String outOfStockMessage) {
    this.outOfStockMessage = outOfStockMessage;
  }

  /**
   * @return the parentCategoryCode
   */
  public String getParentCategoryCode() {
    return parentCategoryCode;
  }

  /**
   * @param parentCategoryCode
   *          the parentCategoryCode to set
   */
  public void setParentCategoryCode(String parentCategoryCode) {
    this.parentCategoryCode = parentCategoryCode;
  }

  /**
   * @return the path
   */
  public String getPath() {
    return path;
  }

  /**
   * @param path
   *          the path to set
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * @return the personInCharge
   */
  public String getPersonInCharge() {
    return personInCharge;
  }

  /**
   * @param personInCharge
   *          the personInCharge to set
   */
  public void setPersonInCharge(String personInCharge) {
    this.personInCharge = personInCharge;
  }

  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @param phoneNumber
   *          the phoneNumber to set
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * @return the popularRankingCountId
   */
  public Long getPopularRankingCountId() {
    return popularRankingCountId;
  }

  /**
   * @param popularRankingCountId
   *          the popularRankingCountId to set
   */
  public void setPopularRankingCountId(Long popularRankingCountId) {
    this.popularRankingCountId = popularRankingCountId;
  }

  /**
   * postalCodeを返します。
   * 
   * @return the postalCode
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * postalCodeを設定します。
   * 
   * @param postalCode
   *          設定する postalCode
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * @return the prefectureCode
   */
  public String getPrefectureCode() {
    return prefectureCode;
  }

  /**
   * @param prefectureCode
   *          the prefectureCode to set
   */
  public void setPrefectureCode(String prefectureCode) {
    this.prefectureCode = prefectureCode;
  }

  /**
   * @return the recommendCommodityRank
   */
  public Long getRecommendCommodityRank() {
    return recommendCommodityRank;
  }

  /**
   * @param recommendCommodityRank
   *          the recommendCommodityRank to set
   */
  public void setRecommendCommodityRank(Long recommendCommodityRank) {
    this.recommendCommodityRank = recommendCommodityRank;
  }

  /**
   * @return the relatedCampaignCount
   */
  public Long getRelatedCampaignCount() {
    return relatedCampaignCount;
  }

  /**
   * @param relatedCampaignCount
   *          the relatedCampaignCount to set
   */
  public void setRelatedCampaignCount(Long relatedCampaignCount) {
    this.relatedCampaignCount = relatedCampaignCount;
  }

  /**
   * @return the relatedCategoryCount
   */
  public Long getRelatedCategoryCount() {
    return relatedCategoryCount;
  }

  /**
   * @param relatedCategoryCount
   *          the relatedCategoryCount to set
   */
  public void setRelatedCategoryCount(Long relatedCategoryCount) {
    this.relatedCategoryCount = relatedCategoryCount;
  }

  /**
   * @return the relatedCommodityACount
   */
  public Long getRelatedCommodityACount() {
    return relatedCommodityACount;
  }

  /**
   * @param relatedCommodityCount
   *          the relatedCommodityACount to set
   */
  public void setRelatedCommodityACount(Long relatedCommodityCount) {
    this.relatedCommodityACount = relatedCommodityCount;
  }

  /**
   * @return the relatedGiftCount
   */
  public Long getRelatedGiftCount() {
    return relatedGiftCount;
  }

  /**
   * @param relatedGiftCount
   *          the relatedGiftCount to set
   */
  public void setRelatedGiftCount(Long relatedGiftCount) {
    this.relatedGiftCount = relatedGiftCount;
  }

  /**
   * @return the relatedTagCount
   */
  public Long getRelatedTagCount() {
    return relatedTagCount;
  }

  /**
   * @param relatedTagCount
   *          the relatedTagCount to set
   */
  public void setRelatedTagCount(Long relatedTagCount) {
    this.relatedTagCount = relatedTagCount;
  }

  /**
   * @return the representSkuCode
   */
  public String getRepresentSkuCode() {
    return representSkuCode;
  }

  /**
   * @param representSkuCode
   *          the representSkuCode to set
   */
  public void setRepresentSkuCode(String representSkuCode) {
    this.representSkuCode = representSkuCode;
  }

  /**
   * @return the reservationEndDatetime
   */
  public Date getReservationEndDatetime() {
    return DateUtil.immutableCopy(reservationEndDatetime);
  }

  /**
   * @param reservationEndDatetime
   *          the reservationEndDatetime to set
   */
  public void setReservationEndDatetime(Date reservationEndDatetime) {
    this.reservationEndDatetime = DateUtil.immutableCopy(reservationEndDatetime);
  }

  /**
   * @return the reservationLimit
   */
  public Long getReservationLimit() {
    return reservationLimit;
  }

  /**
   * @param reservationLimit
   *          the reservationLimit to set
   */
  public void setReservationLimit(Long reservationLimit) {
    this.reservationLimit = reservationLimit;
  }

  /**
   * @return the reservationPrice
   */
  public BigDecimal getReservationPrice() {
    return reservationPrice;
  }

  /**
   * @param reservationPrice
   *          the reservationPrice to set
   */
  public void setReservationPrice(BigDecimal reservationPrice) {
    this.reservationPrice = reservationPrice;
  }

  /**
   * @return the reservationStartDatetime
   */
  public Date getReservationStartDatetime() {
    return DateUtil.immutableCopy(reservationStartDatetime);
  }

  /**
   * @param reservationStartDatetime
   *          the reservationStartDatetime to set
   */
  public void setReservationStartDatetime(Date reservationStartDatetime) {
    this.reservationStartDatetime = DateUtil.immutableCopy(reservationStartDatetime);
  }

  /**
   * @return the reservedQuantity
   */
  public Long getReservedQuantity() {
    return reservedQuantity;
  }

  /**
   * @param reservedQuantity
   *          the reservedQuantity to set
   */
  public void setReservedQuantity(Long reservedQuantity) {
    this.reservedQuantity = reservedQuantity;
  }

  /**
   * @return the retailPrice
   */
  public BigDecimal getRetailPrice() {
    return retailPrice;
  }

  /**
   * @param retailPrice
   *          the retailPrice to set
   */
  public void setRetailPrice(BigDecimal retailPrice) {
    this.retailPrice = retailPrice;
  }

  /**
   * @return the reviewContributedDatetime
   */
  public Date getReviewContributedDatetime() {
    return DateUtil.immutableCopy(reviewContributedDatetime);
  }

  /**
   * @param reviewContributedDatetime
   *          the reviewContributedDatetime to set
   */
  public void setReviewContributedDatetime(Date reviewContributedDatetime) {
    this.reviewContributedDatetime = DateUtil.immutableCopy(reviewContributedDatetime);
  }

  /**
   * @return the reviewDescription
   */
  public String getReviewDescription() {
    return reviewDescription;
  }

  /**
   * @param reviewDescription
   *          the reviewDescription to set
   */
  public void setReviewDescription(String reviewDescription) {
    this.reviewDescription = reviewDescription;
  }

  /**
   * @return the reviewDisplayType
   */
  public Long getReviewDisplayType() {
    return reviewDisplayType;
  }

  /**
   * @param reviewDisplayType
   *          the reviewDisplayType to set
   */
  public void setReviewDisplayType(Long reviewDisplayType) {
    this.reviewDisplayType = reviewDisplayType;
  }

  /**
   * @return the reviewId
   */
  public Long getReviewId() {
    return reviewId;
  }

  /**
   * @param reviewId
   *          the reviewId to set
   */
  public void setReviewId(Long reviewId) {
    this.reviewId = reviewId;
  }

  /**
   * @return the reviewPointAllocatedStatus
   */
  public Long getReviewPointAllocatedStatus() {
    return reviewPointAllocatedStatus;
  }

  /**
   * @param reviewPointAllocatedStatus
   *          the reviewPointAllocatedStatus to set
   */
  public void setReviewPointAllocatedStatus(Long reviewPointAllocatedStatus) {
    this.reviewPointAllocatedStatus = reviewPointAllocatedStatus;
  }

  /**
   * @return the reviewScore
   */
  public Long getReviewScore() {
    return reviewScore;
  }

  /**
   * @param reviewScore
   *          the reviewScore to set
   */
  public void setReviewScore(Long reviewScore) {
    this.reviewScore = reviewScore;
  }

  /**
   * @return the reviewCount
   */
  public Long getReviewCount() {
    return reviewCount;
  }

  /**
   * @param reviewCount
   *          the reviewCount to set
   */
  public void setReviewCount(Long reviewCount) {
    this.reviewCount = reviewCount;
  }

  /**
   * @return the reviewSummaryId
   */
  public Long getReviewSummaryId() {
    return reviewSummaryId;
  }

  /**
   * @param reviewSummaryId
   *          the reviewSummaryId to set
   */
  public void setReviewSummaryId(Long reviewSummaryId) {
    this.reviewSummaryId = reviewSummaryId;
  }

  /**
   * @return the reviewTitle
   */
  public String getReviewTitle() {
    return reviewTitle;
  }

  /**
   * @param reviewTitle
   *          the reviewTitle to set
   */
  public void setReviewTitle(String reviewTitle) {
    this.reviewTitle = reviewTitle;
  }

  /**
   * @return the saleEndDatetime
   */
  public Date getSaleEndDatetime() {
    return DateUtil.immutableCopy(saleEndDatetime);
  }

  /**
   * @param saleEndDatetime
   *          the saleEndDatetime to set
   */
  public void setSaleEndDatetime(Date saleEndDatetime) {
    this.saleEndDatetime = DateUtil.immutableCopy(saleEndDatetime);
  }

  /**
   * @return the saleFlg
   */
  public Long getSaleFlg() {
    return saleFlg;
  }

  /**
   * @param saleFlg
   *          the saleFlg to set
   */
  public void setSaleFlg(Long saleFlg) {
    this.saleFlg = saleFlg;
  }

  /**
   * @return the saleStartDatetime
   */
  public Date getSaleStartDatetime() {
    return DateUtil.immutableCopy(saleStartDatetime);
  }

  /**
   * @param saleStartDatetime
   *          the saleStartDatetime to set
   */
  public void setSaleStartDatetime(Date saleStartDatetime) {
    this.saleStartDatetime = DateUtil.immutableCopy(saleStartDatetime);
  }

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return the shopIntroducedUrl
   */
  public String getShopIntroducedUrl() {
    return shopIntroducedUrl;
  }

  /**
   * @param shopIntroducedUrl
   *          the shopIntroducedUrl to set
   */
  public void setShopIntroducedUrl(String shopIntroducedUrl) {
    this.shopIntroducedUrl = shopIntroducedUrl;
  }

  /**
   * @return the shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * @param shopName
   *          the shopName to set
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  /**
   * @return the shopType
   */
  public Long getShopType() {
    return shopType;
  }

  /**
   * @param shopType
   *          the shopType to set
   */
  public void setShopType(Long shopType) {
    this.shopType = shopType;
  }

  /**
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * @param skuCode
   *          the skuCode to set
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  /**
   * @return the sslPage
   */
  public String getSslPage() {
    return sslPage;
  }

  /**
   * @param sslPage
   *          the sslPage to set
   */
  public void setSslPage(String sslPage) {
    this.sslPage = sslPage;
  }

  /**
   * @return the standardDetail1Name
   */
  public String getStandardDetail1Name() {
    return standardDetail1Name;
  }

  /**
   * @param standardDetail1Name
   *          the standardDetail1Name to set
   */
  public void setStandardDetail1Name(String standardDetail1Name) {
    this.standardDetail1Name = standardDetail1Name;
  }

  /**
   * @return the standardDetail2Name
   */
  public String getStandardDetail2Name() {
    return standardDetail2Name;
  }

  /**
   * @param standardDetail2Name
   *          the standardDetail2Name to set
   */
  public void setStandardDetail2Name(String standardDetail2Name) {
    this.standardDetail2Name = standardDetail2Name;
  }

  /**
   * @return the stockLittleMessage
   */
  public String getStockLittleMessage() {
    return stockLittleMessage;
  }

  /**
   * @param stockLittleMessage
   *          the stockLittleMessage to set
   */
  public void setStockLittleMessage(String stockLittleMessage) {
    this.stockLittleMessage = stockLittleMessage;
  }

  /**
   * @return the stockManagementType
   */
  public Long getStockManagementType() {
    return stockManagementType;
  }

  /**
   * @param stockManagementType
   *          the stockManagementType to set
   */
  public void setStockManagementType(Long stockManagementType) {
    this.stockManagementType = stockManagementType;
  }

  public Long getCommodityType() {
    return commodityType;
  }

  public void setCommodityType(Long commodityType) {
    this.commodityType = commodityType;
  }

  /**
   * @return the stockQuantity
   */
  public Long getStockQuantity() {
    return stockQuantity;
  }

  /**
   * @param stockQuantity
   *          the stockQuantity to set
   */
  public void setStockQuantity(Long stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  /**
   * @return the stockStatusName
   */
  public String getStockStatusName() {
    return stockStatusName;
  }

  /**
   * @param stockStatusName
   *          the stockStatusName to set
   */
  public void setStockStatusName(String stockStatusName) {
    this.stockStatusName = stockStatusName;
  }

  /**
   * @return the stockStatusNo
   */
  public Long getStockStatusNo() {
    return stockStatusNo;
  }

  /**
   * @param stockStatusNo
   *          the stockStatusNo to set
   */
  public void setStockStatusNo(Long stockStatusNo) {
    this.stockStatusNo = stockStatusNo;
  }

  /**
   * @return the stockSufficientMessage
   */
  public String getStockSufficientMessage() {
    return stockSufficientMessage;
  }

  /**
   * @param stockSufficientMessage
   *          the stockSufficientMessage to set
   */
  public void setStockSufficientMessage(String stockSufficientMessage) {
    this.stockSufficientMessage = stockSufficientMessage;
  }

  /**
   * @return the stockSufficientThreshold
   */
  public Long getStockSufficientThreshold() {
    return stockSufficientThreshold;
  }

  /**
   * @param stockSufficientThreshold
   *          the stockSufficientThreshold to set
   */
  public void setStockSufficientThreshold(Long stockSufficientThreshold) {
    this.stockSufficientThreshold = stockSufficientThreshold;
  }

  /**
   * @return the tagCode
   */
  public String getTagCode() {
    return tagCode;
  }

  /**
   * @param tagCode
   *          the tagCode to set
   */
  public void setTagCode(String tagCode) {
    this.tagCode = tagCode;
  }

  /**
   * @return the tagName
   */
  public String getTagName() {
    return tagName;
  }

  /**
   * @param tagName
   *          the tagName to set
   */
  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  /**
   * @return the unitPrice
   */
  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  /**
   * @param unitPrice
   *          the unitPrice to set
   */
  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  /**
   * relatedCommodityBCountを返します。
   * 
   * @return the relatedCommodityBCount
   */
  public Long getRelatedCommodityBCount() {
    return relatedCommodityBCount;
  }

  /**
   * relatedCommodityBCountを設定します。
   * 
   * @param relatedCommodityBCount
   *          設定する relatedCommodityBCount
   */
  public void setRelatedCommodityBCount(Long relatedCommodityBCount) {
    this.relatedCommodityBCount = relatedCommodityBCount;
  }

  /**
   * saleStatusを取得します。
   * 
   * @return saleStatus
   */

  public Long getSaleStatus() {
    return saleStatus;
  }

  /**
   * saleStatusを設定します。
   * 
   * @param saleStatus
   *          saleStatus
   */
  public void setSaleStatus(Long saleStatus) {
    this.saleStatus = saleStatus;
  }

  /**
   * shadowSearchWordsを取得します。
   * 
   * @return shadowSearchWords
   */
  public String getShadowSearchWords() {
    return shadowSearchWords;
  }

  /**
   * shadowSearchWordsを設定します。
   * 
   * @param shadowSearchWords
   *          shadowSearchWords
   */
  public void setShadowSearchWords(String shadowSearchWords) {
    this.shadowSearchWords = shadowSearchWords;
  }

  /**
   * commodityPopularRankを取得します。
   * 
   * @return commodityPopularRank
   */
  public Long getCommodityPopularRank() {
    return commodityPopularRank;
  }

  /**
   * commodityPopularRankを設定します。
   * 
   * @param commodityPopularRank
   *          commodityPopularRank
   */
  public void setCommodityPopularRank(Long commodityPopularRank) {
    this.commodityPopularRank = commodityPopularRank;
  }

  /**
   * mobileNumberを取得します。
   * 
   * @return mobileNumber mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  /**
   * mobileNumberを設定します。
   * 
   * @param mobileNumber
   *          mobileNumber
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  /**
   * @return the infinityFlagEc
   */
  public String getInfinityFlagEc() {
    return infinityFlagEc;
  }

  /**
   * @param infinityFlagEc
   *          the infinityFlagEc to set
   */
  public void setInfinityFlagEc(String infinityFlagEc) {
    this.infinityFlagEc = infinityFlagEc;
  }

  /**
   * @return the actStock
   */
  public String getActStock() {
    return actStock;
  }

  /**
   * @param actStock
   *          the actStock to set
   */
  public void setActStock(String actStock) {
    this.actStock = actStock;
  }

  /**
   * @return the discountmode
   */
  public String getDiscountmode() {
    return discountmode;
  }

  /**
   * @param discountmode
   *          the discountmode to set
   */
  public void setDiscountmode(String discountmode) {
    this.discountmode = discountmode;
  }

  /**
   * @return the rankingScore
   */
  public Long getRankingScore() {
    return rankingScore;
  }

  /**
   * @param rankingScore
   *          the rankingScore to set
   */
  public void setRankingScore(Long rankingScore) {
    this.rankingScore = rankingScore;
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
   * @return the useFlg
   */
  public Long getUseFlg() {
    return useFlg;
  }

  /**
   * @param useFlg
   *          the useFlg to set
   */
  public void setUseFlg(Long useFlg) {
    this.useFlg = useFlg;
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
   * @return the brandName
   */
  public String getBrandName() {
    return brandName;
  }

  /**
   * @param brandName
   *          the brandName to set
   */
  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  /**
   * @return the brandDescription
   */
  public String getBrandDescription() {
    return brandDescription;
  }

  /**
   * @param brandDescription
   *          the brandDescription to set
   */
  public void setBrandDescription(String brandDescription) {
    this.brandDescription = brandDescription;
  }

  /**
   * @return the weight
   */
  public BigDecimal getWeight() {
    return weight;
  }

  /**
   * @param weight
   *          the weight to set
   */
  public void setWeight(BigDecimal weight) {
    this.weight = weight;
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
   * @param standardDetail1NameJp
   *          the standardDetail1NameJp to set
   */
  public void setStandardDetail1NameJp(String standardDetail1NameJp) {
    this.standardDetail1NameJp = standardDetail1NameJp;
  }

  /**
   * @return the standardDetail1NameJp
   */
  public String getStandardDetail1NameJp() {
    return standardDetail1NameJp;
  }

  /**
   * @param standardDetail2NameEn
   *          the standardDetail2NameEn to set
   */
  public void setStandardDetail2NameEn(String standardDetail2NameEn) {
    this.standardDetail2NameEn = standardDetail2NameEn;
  }

  /**
   * @return the standardDetail2NameEn
   */
  public String getStandardDetail2NameEn() {
    return standardDetail2NameEn;
  }

  /**
   * @param standardDetail1NameEn
   *          the standardDetail1NameEn to set
   */
  public void setStandardDetail1NameEn(String standardDetail1NameEn) {
    this.standardDetail1NameEn = standardDetail1NameEn;
  }

  /**
   * @return the standardDetail1NameEn
   */
  public String getStandardDetail1NameEn() {
    return standardDetail1NameEn;
  }

  /**
   * @param standardDetail2NameJp
   *          the standardDetail2NameJp to set
   */
  public void setStandardDetail2NameJp(String standardDetail2NameJp) {
    this.standardDetail2NameJp = standardDetail2NameJp;
  }

  /**
   * @return the standardDetail2NameJp
   */
  public String getStandardDetail2NameJp() {
    return standardDetail2NameJp;
  }

  /**
   * @param brandDescriptionEn
   *          the brandDescriptionEn to set
   */
  public void setBrandDescriptionEn(String brandDescriptionEn) {
    this.brandDescriptionEn = brandDescriptionEn;
  }

  /**
   * @return the brandDescriptionEn
   */
  public String getBrandDescriptionEn() {
    return brandDescriptionEn;
  }

  /**
   * @param brandDescriptionJp
   *          the brandDescriptionJp to set
   */
  public void setBrandDescriptionJp(String brandDescriptionJp) {
    this.brandDescriptionJp = brandDescriptionJp;
  }

  /**
   * @return the brandDescriptionJp
   */
  public String getBrandDescriptionJp() {
    return brandDescriptionJp;
  }

  /**
   * @param brandJapaneseName
   *          the brandJapaneseName to set
   */
  public void setBrandJapaneseName(String brandJapaneseName) {
    this.brandJapaneseName = brandJapaneseName;
  }

  /**
   * @return the brandJapaneseName
   */
  public String getBrandJapaneseName() {
    return brandJapaneseName;
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

  // 2012/11/17 促销对应 ob add start
  /**
   * @return the setCommodityFlg
   */
  public Long getSetCommodityFlg() {
    return setCommodityFlg;
  }

  /**
   * @param setCommodityFlg
   *          the setCommodityFlg to set
   */
  public void setSetCommodityFlg(Long setCommodityFlg) {
    this.setCommodityFlg = setCommodityFlg;
  }

  // 2012/11/17 促销对应 ob add end

  public Long getRelatedCompositionCount() {
    return relatedCompositionCount;
  }

  public void setRelatedCompositionCount(Long relatedCompositionCount) {
    this.relatedCompositionCount = relatedCompositionCount;
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
   * @return the innerQuantity
   */
  public Long getInnerQuantity() {
    return innerQuantity;
  }

  /**
   * @param innerQuantity
   *          the innerQuantity to set
   */
  public void setInnerQuantity(Long innerQuantity) {
    this.innerQuantity = innerQuantity;
  }

  
  /**
   * @return the discountCommodityFlg
   */
  public boolean isDiscountCommodityFlg() {
    return discountCommodityFlg;
  }

  
  /**
   * @param discountCommodityFlg the discountCommodityFlg to set
   */
  public void setDiscountCommodityFlg(boolean discountCommodityFlg) {
    this.discountCommodityFlg = discountCommodityFlg;
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
   * @return the priceMode
   */
  public String getPriceMode() {
    return priceMode;
  }

  /**
   * @param priceMode
   *          the priceMode to set
   */
  public void setPriceMode(String priceMode) {
    this.priceMode = priceMode;
  }

  /**
   * @return the prefectureCodes
   */
  public String getPrefectureCodes() {
    return prefectureCodes;
  }

  /**
   * @param prefectureCodes
   *          the prefectureCodes to set
   */
  public void setPrefectureCodes(String prefectureCodes) {
    this.prefectureCodes = prefectureCodes;
  }

  /**
   * @return the commodityNameCn
   */
  public String getCommodityNameCn() {
    return commodityNameCn;
  }

  /**
   * @param commodityNameCn
   *          the commodityNameCn to set
   */
  public void setCommodityNameCn(String commodityNameCn) {
    this.commodityNameCn = commodityNameCn;
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
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }
}
