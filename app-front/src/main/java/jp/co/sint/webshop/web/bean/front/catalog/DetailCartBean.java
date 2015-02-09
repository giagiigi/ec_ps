package jp.co.sint.webshop.web.bean.front.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.ReviewScore;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040510:商品詳細のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class DetailCartBean extends UISubBean {

  private String commodityCode;

  private String commodityName;

  private String reviewScore;

  private String reviewCount;

  private String shopCode;

  private String shopName;

  private String commodityStandard1Name;

  private String commodityStandard2Name;

  private String campaignCode;

  private String campaignDiscountRate;

  private String campaignName;

  private boolean campaignPeriod;

  private boolean commodityPointPeriod;

  private boolean discountPeriod;

  private boolean reservationPeriod;

  private String selectedSkuCode;

  private String commodityPointRate;

  private String commodityTaxType;

  private String commodityDescription;

  private String priceMode;

  private String stockManagementType;

  private String deliveryTypeCode;

  private String recommendCommodityRank;

  private String outOfStockMessage;

  private String stockLittleMessage;

  private String stockSufficientMessage;

  private Long stockSufficientThreshold;

  private List<String> topicPathList;

  private String linkUrl;

  private String representSkuCode;

  private List<CodeAttribute> tagList = new ArrayList<CodeAttribute>();

  private List<DetailCartDetail> cartDetailList = new ArrayList<DetailCartDetail>();

  private List<DetailCartDetail> skuImageList = new ArrayList<DetailCartDetail>();

  private List<DetailCartGift> giftImageList = new ArrayList<DetailCartGift>();

  private List<CodeAttribute> giftList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> standardList = new ArrayList<CodeAttribute>();

  private List<String> standardName1List = new ArrayList<String>();

  private List<String> standardName2List = new ArrayList<String>();

  // 2012/11/17 促销对应 ob add start
  private List<RecommendSet> recommendSets = new ArrayList<RecommendSet>();

  private List<DetailCartComposition> compositionList;

  // 2012/11/17 促销对应 ob add end

  private String standardAssortment;

  private Long standardSize;

  private boolean commodityImageExists;

  private boolean displayReview;

  private boolean displayGift;

  private String previewDigest;

  private boolean preview = false;

  private String discountRate;

  private String discountPrices;

  private boolean displayDiscountRate;

  private String commodityMessage;

  private String commodityNameEn;

  private String brandCode;

  private String brandName;

  private String commodityDescriptionShort;

  private String brandDescription;

  private String discountPriceEndDatetime;

  private String originalPlace;

  private String originalPlaceEn;

  private String originalCode;

  private Long shelfLifeDays;

  private Long shelfLifeFlag;

  // 20130809 txw add start
  private String title;

  private String description;

  private String keyword;

  // 20130809 txw add end
  // 20130913 yyq add start
  private Long customerMaxTotalNum;

  // 20130913 yyq add end

  // 20140227 txw add start desc:临近效期提醒
  private String nearMsg;

  // 20140227 txw add end desc:临近效期提醒

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

  /**
   * @return the originalPlaceEn
   */
  public String getOriginalPlaceEn() {
    return originalPlaceEn;
  }

  /**
   * @param originalPlaceEn
   *          the originalPlaceEn to set
   */
  public void setOriginalPlaceEn(String originalPlaceEn) {
    this.originalPlaceEn = originalPlaceEn;
  }

  public List<RecommendSet> getRecommendSets() {
    return recommendSets;
  }

  public void setRecommendSets(List<RecommendSet> recommendSets) {
    this.recommendSets = recommendSets;
  }

  public List<DetailCartComposition> getCompositionList() {
    return compositionList;
  }

  public void setCompositionList(List<DetailCartComposition> compositionList) {
    this.compositionList = compositionList;
  }

  public Long getShelfLifeFlag() {
    return shelfLifeFlag;
  }

  public void setShelfLifeFlag(Long shelfLifeFlag) {
    this.shelfLifeFlag = shelfLifeFlag;
  }

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  public String getOriginalPlace() {
    return originalPlace;
  }

  public void setOriginalPlace(String originalPlace) {
    this.originalPlace = originalPlace;
  }

  public Long getShelfLifeDays() {
    return shelfLifeDays;
  }

  public void setShelfLifeDays(Long shelfLifeDays) {
    this.shelfLifeDays = shelfLifeDays;
  }

  /**
   * @return the commodityMessage
   */
  public String getCommodityMessage() {
    return commodityMessage;
  }

  /**
   * @param commodityMessage
   *          the commodityMessage to set
   */
  public void setCommodityMessage(String commodityMessage) {
    this.commodityMessage = commodityMessage;
  }

  /**
   * U2040510:商品詳細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class DetailCartGift implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String giftName;

    private String giftCode;

    /**
     * giftNameを取得します。
     * 
     * @return giftName
     */
    public String getGiftName() {
      return giftName;
    }

    /**
     * giftNameを設定します。
     * 
     * @param giftName
     *          giftName
     */
    public void setGiftName(String giftName) {
      this.giftName = giftName;
    }

    /**
     * giftCodeを取得します。
     * 
     * @return giftCode
     */
    public String getGiftCode() {
      return giftCode;
    }

    /**
     * giftCodeを設定します。
     * 
     * @param giftCode
     *          giftCode
     */
    public void setGiftCode(String giftCode) {
      this.giftCode = giftCode;
    }

  }

  /**
   * U2040510:商品詳細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class DetailCartDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String skuCode;

    private String standardDetail1Name;

    private String standardDetail2Name;

    private String standardDetailName;

    private String unitPrice;

    private String discountPrice;
    
    private boolean setCommodityFlg;

    private String reservationPrice;

    private String availableStockQuantity;

    private String stockStatusMessage;

    private boolean arrivalGoodsFlg;

    private boolean displayCartButton;

    private String discountRate;

    private String discountPrices;

    private boolean displayDiscountRate;

    private boolean useFlg;

    private String discountPriceStartDatetime;

    private String discountPriceEndDatetime;

    private String weight;

    private String originalCommodityCode;

    private long combinationAmount;

    public String getDiscountPriceStartDatetime() {
      return discountPriceStartDatetime;
    }

    public void setDiscountPriceStartDatetime(String discountPriceStartDatetime) {
      this.discountPriceStartDatetime = discountPriceStartDatetime;
    }

    /**
     * @return the useFlg
     */
    public boolean isUseFlg() {
      return useFlg;
    }

    /**
     * @param useFlg
     *          the useFlg to set
     */
    public void setUseFlg(boolean useFlg) {
      this.useFlg = useFlg;
    }

    /**
     * displayCartButtonを取得します。
     * 
     * @return displayCartButton
     */
    public boolean isDisplayCartButton() {
      return displayCartButton;
    }

    /**
     * displayCartButtonを設定します。
     * 
     * @param displayCartButton
     *          displayCartButton
     */
    public void setDisplayCartButton(boolean displayCartButton) {
      this.displayCartButton = displayCartButton;
    }

    /**
     * availableStockQuantityを取得します。
     * 
     * @return availableStockQuantity
     */
    public String getAvailableStockQuantity() {
      return availableStockQuantity;
    }

    /**
     * availableStockQuantityを設定します。
     * 
     * @param availableStockQuantity
     *          availableStockQuantity
     */
    public void setAvailableStockQuantity(String availableStockQuantity) {
      this.availableStockQuantity = availableStockQuantity;
    }

    /**
     * discountPriceを取得します。
     * 
     * @return discountPrice
     */
    public String getDiscountPrice() {
      return discountPrice;
    }

    /**
     * discountPriceを設定します。
     * 
     * @param discountPrice
     *          discountPrice
     */
    public void setDiscountPrice(String discountPrice) {
      this.discountPrice = discountPrice;
    }

    /**
     * reservationPriceを取得します。
     * 
     * @return reservationPrice
     */
    public String getReservationPrice() {
      return reservationPrice;
    }

    /**
     * reservationPriceを設定します。
     * 
     * @param reservationPrice
     *          reservationPrice
     */
    public void setReservationPrice(String reservationPrice) {
      this.reservationPrice = reservationPrice;
    }

    /**
     * skuCodeを取得します。
     * 
     * @return skuCode
     */
    public String getSkuCode() {
      return skuCode;
    }

    /**
     * skuCodeを設定します。
     * 
     * @param skuCode
     *          skuCode
     */
    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    /**
     * standardDetail1Nameを取得します。
     * 
     * @return standardDetail1Name
     */
    public String getStandardDetail1Name() {
      return standardDetail1Name;
    }

    /**
     * standardDetail1Nameを設定します。
     * 
     * @param standardDetail1Name
     *          standardDetail1Name
     */
    public void setStandardDetail1Name(String standardDetail1Name) {
      this.standardDetail1Name = standardDetail1Name;
    }

    /**
     * standardDetail2Nameを取得します。
     * 
     * @return standardDetail2Name
     */
    public String getStandardDetail2Name() {
      return standardDetail2Name;
    }

    /**
     * standardDetail2Nameを設定します。
     * 
     * @param standardDetail2Name
     *          standardDetail2Name
     */
    public void setStandardDetail2Name(String standardDetail2Name) {
      this.standardDetail2Name = standardDetail2Name;
    }

    /**
     * unitPriceを取得します。
     * 
     * @return unitPrice
     */
    public String getUnitPrice() {
      return unitPrice;
    }

    /**
     * unitPriceを設定します。
     * 
     * @param unitPrice
     *          unitPrice
     */
    public void setUnitPrice(String unitPrice) {
      this.unitPrice = unitPrice;
    }

    /**
     * standardDetailNameを取得します。
     * 
     * @return standardDetailName
     */
    public String getStandardDetailName() {
      return standardDetailName;
    }

    /**
     * standardDetailNameを設定します。
     * 
     * @param standardDetailName
     *          standardDetailName
     */
    public void setStandardDetailName(String standardDetailName) {
      this.standardDetailName = standardDetailName;
    }

    /**
     * arrivalGoodsFlgを取得します。
     * 
     * @return arrivalGoodsFlg
     */
    public boolean isArrivalGoodsFlg() {
      return arrivalGoodsFlg;
    }

    /**
     * arrivalGoodsFlgを設定します。
     * 
     * @param arrivalGoodsFlg
     *          arrivalGoodsFlg
     */
    public void setArrivalGoodsFlg(boolean arrivalGoodsFlg) {
      this.arrivalGoodsFlg = arrivalGoodsFlg;
    }

    /**
     * stockStatusMessageを取得します。
     * 
     * @return stockStatusMessage
     */
    public String getStockStatusMessage() {
      return stockStatusMessage;
    }

    /**
     * stockStatusMessageを設定します。
     * 
     * @param stockStatusMessage
     *          stockStatusMessage
     */
    public void setStockStatusMessage(String stockStatusMessage) {
      this.stockStatusMessage = stockStatusMessage;
    }

    /**
     * @return the discountRate
     */
    public String getDiscountRate() {
      return discountRate;
    }

    /**
     * @param discountRate
     *          the discountRate to set
     */
    public void setDiscountRate(String discountRate) {
      this.discountRate = discountRate;
    }

    /**
     * @return the discountPrices
     */
    public String getDiscountPrices() {
      return discountPrices;
    }

    /**
     * @param discountPrices
     *          the discountPrices to set
     */
    public void setDiscountPrices(String discountPrices) {
      this.discountPrices = discountPrices;
    }

    /**
     * @return the displayDiscountRate
     */
    public boolean isDisplayDiscountRate() {
      return displayDiscountRate;
    }

    /**
     * @param displayDiscountRate
     *          the displayDiscountRate to set
     */
    public void setDisplayDiscountRate(boolean displayDiscountRate) {
      this.displayDiscountRate = displayDiscountRate;
    }

    /**
     * @return the discountPriceEndDatetime
     */
    public String getDiscountPriceEndDatetime() {
      return discountPriceEndDatetime;
    }

    /**
     * @param discountPriceEndDatetime
     *          the discountPriceEndDatetime to set
     */
    public void setDiscountPriceEndDatetime(String discountPriceEndDatetime) {
      this.discountPriceEndDatetime = discountPriceEndDatetime;
    }

    /**
     * @return the weight
     */
    public String getWeight() {
      return weight;
    }

    /**
     * @param weight
     *          the weight to set
     */
    public void setWeight(String weight) {
      this.weight = weight;
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
    public long getCombinationAmount() {
      return combinationAmount;
    }

    /**
     * @param combinationAmount
     *          the combinationAmount to set
     */
    public void setCombinationAmount(long combinationAmount) {
      this.combinationAmount = combinationAmount;
    }

    
    /**
     * @return the setCommodityFlg
     */
    public boolean isSetCommodityFlg() {
      return setCommodityFlg;
    }

    
    /**
     * @param setCommodityFlg the setCommodityFlg to set
     */
    public void setSetCommodityFlg(boolean setCommodityFlg) {
      this.setCommodityFlg = setCommodityFlg;
    }

  }

  /**
   * campaignCodeを取得します。
   * 
   * @return campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
  }

  /**
   * campaignCodeを設定します。
   * 
   * @param campaignCode
   *          campaignCode
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  /**
   * campaignDiscountRateを取得します。
   * 
   * @return campaignDiscountRate
   */
  public String getCampaignDiscountRate() {
    return campaignDiscountRate;
  }

  /**
   * campaignDiscountRateを設定します。
   * 
   * @param campaignDiscountRate
   *          campaignDiscountRate
   */
  public void setCampaignDiscountRate(String campaignDiscountRate) {
    this.campaignDiscountRate = campaignDiscountRate;
  }

  /**
   * campaignNameを取得します。
   * 
   * @return campaignName
   */
  public String getCampaignName() {
    return campaignName;
  }

  /**
   * campaignNameを設定します。
   * 
   * @param campaignName
   *          campaignName
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  /**
   * campaignPeriodを取得します。
   * 
   * @return campaignPeriod
   */
  public boolean isCampaignPeriod() {
    return campaignPeriod;
  }

  /**
   * campaignPeriodを設定します。
   * 
   * @param campaignPeriod
   *          campaignPeriod
   */
  public void setCampaignPeriod(boolean campaignPeriod) {
    this.campaignPeriod = campaignPeriod;
  }

  /**
   * commodityCodeを取得します。
   * 
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * commodityDescriptionを取得します。
   * 
   * @return commodityDescription
   */
  public String getCommodityDescription() {
    String staticUrl = DIContainer.getImageUploadConfig().getStaticUrl();
    String localImgUrl1 = DIContainer.getImageUploadConfig().getLocalImgUrl1Cn();
    localImgUrl1 = localImgUrl1.split("/")[2];
    if (StringUtil.hasValue(commodityDescription)) {
      return commodityDescription.replace(localImgUrl1, staticUrl);
    } else {
      return commodityDescription;
    }
  }

  /**
   * commodityDescriptionを設定します。
   * 
   * @param commodityDescription
   *          commodityDescription
   */
  public void setCommodityDescription(String commodityDescription) {
    this.commodityDescription = commodityDescription;
  }

  /**
   * commodityNameを取得します。
   * 
   * @return commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * commodityNameを設定します。
   * 
   * @param commodityName
   *          commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * commodityPointPeriodを取得します。
   * 
   * @return commodityPointPeriod
   */
  public boolean isCommodityPointPeriod() {
    return commodityPointPeriod;
  }

  /**
   * commodityPointPeriodを設定します。
   * 
   * @param commodityPointPeriod
   *          commodityPointPeriod
   */
  public void setCommodityPointPeriod(boolean commodityPointPeriod) {
    this.commodityPointPeriod = commodityPointPeriod;
  }

  /**
   * commodityPointRateを取得します。
   * 
   * @return commodityPointRate
   */
  public String getCommodityPointRate() {
    return commodityPointRate;
  }

  /**
   * commodityPointRateを設定します。
   * 
   * @param commodityPointRate
   *          commodityPointRate
   */
  public void setCommodityPointRate(String commodityPointRate) {
    this.commodityPointRate = commodityPointRate;
  }

  /**
   * commodityTaxTypeを取得します。
   * 
   * @return commodityTaxType
   */
  public String getCommodityTaxType() {
    return commodityTaxType;
  }

  /**
   * commodityTaxTypeを設定します。
   * 
   * @param commodityTaxType
   *          commodityTaxType
   */
  public void setCommodityTaxType(String commodityTaxType) {
    this.commodityTaxType = commodityTaxType;
  }

  /**
   * reviewScoreを取得します。
   * 
   * @return reviewScore
   */
  public String getReviewScore() {
    return reviewScore;
  }

  /**
   * reviewScoreを設定します。
   * 
   * @param reviewScore
   *          reviewScore
   */
  public void setReviewScore(String reviewScore) {
    this.reviewScore = reviewScore;
  }

  /**
   * reviewCountを返します。
   * 
   * @return the reviewCount
   */
  public String getReviewCount() {
    return reviewCount;
  }

  /**
   * reviewCountを設定します。
   * 
   * @param reviewCount
   *          設定する reviewCount
   */
  public void setReviewCount(String reviewCount) {
    this.reviewCount = reviewCount;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * shopNameを取得します。
   * 
   * @return shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * shopNameを設定します。
   * 
   * @param shopName
   *          shopName
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  /**
   * priceModeを取得します。
   * 
   * @return priceMode
   */
  public String getPriceMode() {
    return priceMode;
  }

  /**
   * priceModeを設定します。
   * 
   * @param priceMode
   *          priceMode
   */
  public void setPriceMode(String priceMode) {
    this.priceMode = priceMode;
  }

  /**
   * giftListを取得します。
   * 
   * @return giftList
   */
  public List<CodeAttribute> getGiftList() {
    return giftList;
  }

  /**
   * giftListを設定します。
   * 
   * @param giftList
   *          giftList
   */
  public void setGiftList(List<CodeAttribute> giftList) {
    this.giftList = giftList;
  }

  /**
   * commodityStandard1Nameを取得します。
   * 
   * @return commodityStandard1Name
   */
  public String getCommodityStandard1Name() {
    return commodityStandard1Name;
  }

  /**
   * commodityStandard1Nameを設定します。
   * 
   * @param commodityStandard1Name
   *          commodityStandard1Name
   */
  public void setCommodityStandard1Name(String commodityStandard1Name) {
    this.commodityStandard1Name = commodityStandard1Name;
  }

  /**
   * commodityStandard2Nameを取得します。
   * 
   * @return commodityStandard2Name
   */
  public String getCommodityStandard2Name() {
    return commodityStandard2Name;
  }

  /**
   * commodityStandard2Nameを設定します。
   * 
   * @param commodityStandard2Name
   *          commodityStandard2Name
   */
  public void setCommodityStandard2Name(String commodityStandard2Name) {
    this.commodityStandard2Name = commodityStandard2Name;
  }

  /**
   * discountPeriodを設定します。
   * 
   * @param discountPeriod
   *          discountPeriod
   */
  public void setDiscountPeriod(boolean discountPeriod) {
    this.discountPeriod = discountPeriod;
  }

  /**
   * discountPeriodを取得します。
   * 
   * @return discountPeriod
   */
  public boolean isDiscountPeriod() {
    return discountPeriod;
  }

  /**
   * standardListを取得します。
   * 
   * @return standardList
   */
  public List<CodeAttribute> getStandardList() {
    return standardList;
  }

  /**
   * standardListを設定します。
   * 
   * @param standardList
   *          standardList
   */
  public void setStandardList(List<CodeAttribute> standardList) {
    this.standardList = standardList;
  }

  /**
   * standardName1Listを取得します。
   * 
   * @return standardName1List
   */
  public List<String> getStandardName1List() {
    return standardName1List;
  }

  /**
   * standardName1Listを設定します。
   * 
   * @param standardName1List
   *          standardName1List
   */
  public void setStandardName1List(List<String> standardName1List) {
    this.standardName1List = standardName1List;
  }

  /**
   * standardName2Listを取得します。
   * 
   * @return standardName2List
   */
  public List<String> getStandardName2List() {
    return standardName2List;
  }

  /**
   * standardName2Listを設定します。
   * 
   * @param standardName2List
   *          standardName2List
   */
  public void setStandardName2List(List<String> standardName2List) {
    this.standardName2List = standardName2List;
  }

  // 10.1.1 10054 追加 ここから
  /**
   * 受け取ったリストの文字列に含まれる置換対象文字をXML文字参照に置換します。
   * 
   * @param src
   *          String型のリスト
   * @return　リストに含まれる置換文字をXML文字参照に変換したリストを返します。
   *         　　　　　　　リストが空、nullの場合は、空のリストを返します。
   */
  private List<String> getEscaped(List<String> src) {
    if (src != null && src.size() > 1) {
      List<String> dest = new ArrayList<String>();
      for (String s : src) {
        dest.add(WebUtil.escapeXml(s));
      }
      return dest;
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * escapedStandardName1Listを取得します。
   * 
   * @return getEscaped(getStandardName1List())
   */
  public List<String> getEscapedStandardName1List() {
    return getEscaped(getStandardName1List());
  }

  /**
   * escapedStandardName2Listを取得します。
   * 
   * @return getEscaped(getStandardName2List())
   */
  public List<String> getEscapedStandardName2List() {
    return getEscaped(getStandardName2List());
  }

  // 10.1.1 10054 追加 ここまで

  /**
   * stockManagementTypeを取得します。
   * 
   * @return stockManagementType
   */
  public String getStockManagementType() {
    return stockManagementType;
  }

  /**
   * stockManagementTypeを設定します。
   * 
   * @param stockManagementType
   *          stockManagementType
   */
  public void setStockManagementType(String stockManagementType) {
    this.stockManagementType = stockManagementType;
  }

  /**
   * cartDetailListを取得します。
   * 
   * @return cartDetailList
   */
  public List<DetailCartDetail> getCartDetailList() {
    return cartDetailList;
  }

  /**
   * cartDetailListを設定します。
   * 
   * @param cartDetailList
   *          cartDetailList
   */
  public void setCartDetailList(List<DetailCartDetail> cartDetailList) {
    this.cartDetailList = cartDetailList;
  }

  /**
   * giftImageListを取得します。
   * 
   * @return giftImageList
   */
  public List<DetailCartGift> getGiftImageList() {
    return giftImageList;
  }

  /**
   * giftImageListを設定します。
   * 
   * @param giftImageList
   *          giftImageList
   */
  public void setGiftImageList(List<DetailCartGift> giftImageList) {
    this.giftImageList = giftImageList;
  }

  /**
   * standardAssortmentを取得します。
   * 
   * @return standardAssortment
   */
  public String getStandardAssortment() {
    return standardAssortment;
  }

  /**
   * standardAssortmentを設定します。
   * 
   * @param standardAssortment
   *          standardAssortment
   */
  public void setStandardAssortment(String standardAssortment) {
    this.standardAssortment = standardAssortment;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    String size = reqparam.get("compositionListSize");
    if (!StringUtil.isNullOrEmpty(size) && compositionList == null) {
      compositionList = new ArrayList<DetailCartComposition>();
      for (int i = 1; i <= new Integer(size).intValue(); i++) {
        DetailCartDetail detail = new DetailCartDetail();
        detail.setSkuCode(reqparam.get("comSelectedSkuCode_" + i));
        DetailCartComposition detailcom = new DetailCartComposition();
        detailcom.setRepresentSkuCode(reqparam.get("comSelectedSkuCode_" + i));
        detailcom.setSelectedSkuCode(reqparam.get("comSelectedSkuCode_" + i));
        detailcom.setCommodityCode(reqparam.get("comCommodityCode_" + i));
        detailcom.setCommodityName(reqparam.get("comCommodityName_" + i));
        detailcom.setShopCode(reqparam.get("comShopCode_" + i));
        detailcom.setTax(reqparam.get("comTax_" + i));
        detailcom.setRepresentPrice(reqparam.get("comRepresentPrice_" + i));
        detailcom.setPriceMode(reqparam.get("comPriceMode_" + i));
        for (int j = 1; j < 100; j++) {
          String Name = reqparam.get("comStanderName_" + i + "_" + j);
          String Value = reqparam.get("comStanderValue_" + i + "_" + j);
          if (!StringUtil.isNullOrEmpty(Name) && !StringUtil.isNullOrEmpty(Value)) {
            detailcom.getStander().add(new NameValue(Name, Value));
          } else {
            break;
          }
        }
        detailcom.getCompositionSkuList().add(detail);
        compositionList.add(detailcom);
      }
    }
    if (compositionList != null && compositionList.size() > 0) {
      for (int i = 0; i < compositionList.size(); i++) {
        String select = reqparam.get(compositionList.get(i).getSelectedSkuCode());
        compositionList.get(i).setSelectedSkuCode(select);
      }
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2040510";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.catalog.DetailCartBean.0");
  }

  /**
   * deliveryTypeCodeを取得します。
   * 
   * @return deliveryTypeCode
   */
  public String getDeliveryTypeCode() {
    return deliveryTypeCode;
  }

  /**
   * deliveryTypeCodeを設定します。
   * 
   * @param deliveryTypeCode
   *          deliveryTypeCode
   */
  public void setDeliveryTypeCode(String deliveryTypeCode) {
    this.deliveryTypeCode = deliveryTypeCode;
  }

  /**
   * recommendCommodityRankを取得します。
   * 
   * @return recommendCommodityRank
   */
  public String getRecommendCommodityRank() {
    return recommendCommodityRank;
  }

  /**
   * recommendCommodityRankを設定します。
   * 
   * @param recommendCommodityRank
   *          recommendCommodityRank
   */
  public void setRecommendCommodityRank(String recommendCommodityRank) {
    this.recommendCommodityRank = recommendCommodityRank;
  }

  /**
   * linkUrlを取得します。
   * 
   * @return linkUrl
   */
  public String getLinkUrl() {
    return linkUrl;
  }

  /**
   * linkUrlを設定します。
   * 
   * @param linkUrl
   *          linkUrl
   */
  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
  }

  /**
   * tagListを取得します。
   * 
   * @return tagList
   */
  public List<CodeAttribute> getTagList() {
    return tagList;
  }

  /**
   * tagListを設定します。
   * 
   * @param tagList
   *          tagList
   */
  public void setTagList(List<CodeAttribute> tagList) {
    this.tagList = tagList;
  }

  /**
   * topicPathListを取得します。
   * 
   * @return topicPathList
   */
  public List<String> getTopicPathList() {
    return topicPathList;
  }

  /**
   * topicPathListを設定します。
   * 
   * @param topicPathList
   *          topicPathList
   */
  public void setTopicPathList(List<String> topicPathList) {
    this.topicPathList = topicPathList;
  }

  /**
   * reservationPeriodを取得します。
   * 
   * @return reservationPeriod
   */
  public boolean isReservationPeriod() {
    return reservationPeriod;
  }

  /**
   * reservationPeriodを設定します。
   * 
   * @param reservationPeriod
   *          reservationPeriod
   */
  public void setReservationPeriod(boolean reservationPeriod) {
    this.reservationPeriod = reservationPeriod;
  }

  /**
   * skuImageListを取得します。
   * 
   * @return skuImageList
   */
  public List<DetailCartDetail> getSkuImageList() {
    return skuImageList;
  }

  /**
   * skuImageListを設定します。
   * 
   * @param skuImageList
   *          skuImageList
   */
  public void setSkuImageList(List<DetailCartDetail> skuImageList) {
    this.skuImageList = skuImageList;
  }

  /**
   * standardSizeを取得します。
   * 
   * @return standardSize
   */
  public Long getStandardSize() {
    return standardSize;
  }

  /**
   * standardSizeを設定します。
   * 
   * @param standardSize
   *          standardSize
   */
  public void setStandardSize(Long standardSize) {
    this.standardSize = standardSize;
  }

  /**
   * outOfStockMessageを取得します。
   * 
   * @return outOfStockMessage
   */
  public String getOutOfStockMessage() {
    return outOfStockMessage;
  }

  /**
   * outOfStockMessageを設定します。
   * 
   * @param outOfStockMessage
   *          outOfStockMessage
   */
  public void setOutOfStockMessage(String outOfStockMessage) {
    this.outOfStockMessage = outOfStockMessage;
  }

  /**
   * stockLittleMessageを取得します。
   * 
   * @return stockLittleMessage
   */
  public String getStockLittleMessage() {
    return stockLittleMessage;
  }

  /**
   * stockLittleMessageを設定します。
   * 
   * @param stockLittleMessage
   *          stockLittleMessage
   */
  public void setStockLittleMessage(String stockLittleMessage) {
    this.stockLittleMessage = stockLittleMessage;
  }

  /**
   * stockSufficientMessageを取得します。
   * 
   * @return stockSufficientMessage
   */
  public String getStockSufficientMessage() {
    return stockSufficientMessage;
  }

  /**
   * stockSufficientMessageを設定します。
   * 
   * @param stockSufficientMessage
   *          stockSufficientMessage
   */
  public void setStockSufficientMessage(String stockSufficientMessage) {
    this.stockSufficientMessage = stockSufficientMessage;
  }

  /**
   * stockSufficientThresholdを取得します。
   * 
   * @return stockSufficientThreshold
   */
  public Long getStockSufficientThreshold() {
    return stockSufficientThreshold;
  }

  /**
   * stockSufficientThresholdを設定します。
   * 
   * @param stockSufficientThreshold
   *          stockSufficientThreshold
   */
  public void setStockSufficientThreshold(Long stockSufficientThreshold) {
    this.stockSufficientThreshold = stockSufficientThreshold;
  }

  /**
   * displayReviewを取得します。
   * 
   * @return displayReview
   */
  public boolean isDisplayReview() {
    return displayReview;
  }

  /**
   * displayReviewを設定します。
   * 
   * @param displayReview
   *          displayReview
   */
  public void setDisplayReview(boolean displayReview) {
    this.displayReview = displayReview;
  }

  /**
   * displayGiftを取得します。
   * 
   * @return displayGift
   */

  public boolean isDisplayGift() {
    return displayGift;
  }

  /**
   * displayGiftを設定します。
   * 
   * @param displayGift
   *          displayGift
   */
  public void setDisplayGift(boolean displayGift) {
    this.displayGift = displayGift;
  }

  /**
   * reviewScoreの丸め結果を取得します。
   * 
   * @return reviewScoreの丸め結果
   */
  public Long getRoundedReviewScore() {
    Long score = NumUtil.toLong(getReviewScore(), 0L);

    if (score <= 20) {
      return ReviewScore.ONE_STAR.longValue();
    } else if (score <= 40) {
      return ReviewScore.TWO_STARS.longValue();
    } else if (score <= 60) {
      return ReviewScore.THREE_STARS.longValue();
    } else if (score <= 80) {
      return ReviewScore.FOUR_STARS.longValue();
    } else {
      return ReviewScore.FIVE_STARS.longValue();
    }
  }

  /**
   * commodityImageExistsを取得します。
   * 
   * @return commodityImageExists
   */

  public boolean isCommodityImageExists() {
    return commodityImageExists;
  }

  /**
   * commodityImageExistsを設定します。
   * 
   * @param commodityImageExists
   *          commodityImageExists
   */
  public void setCommodityImageExists(boolean commodityImageExists) {
    this.commodityImageExists = commodityImageExists;
  }

  public String getRepresentSkuCode() {
    return representSkuCode;
  }

  public void setRepresentSkuCode(String representSkuCode) {
    this.representSkuCode = representSkuCode;
  }

  /**
   * previewDigestを取得します。
   * 
   * @return previewDigest
   */
  public String getPreviewDigest() {
    return previewDigest;
  }

  /**
   * previewDigestを設定します。
   * 
   * @param previewDigest
   *          previewDigest
   */
  public void setPreviewDigest(String previewDigest) {
    this.previewDigest = previewDigest;
  }

  /**
   * previewを取得します。
   * 
   * @return preview
   */
  public boolean isPreview() {
    return preview;
  }

  /**
   * previewを設定します。
   * 
   * @param preview
   *          preview
   */
  public void setPreview(boolean preview) {
    this.preview = preview;
  }

  /**
   * selectedSkuCodeを取得します。
   * 
   * @return selectedSkuCode
   */
  public String getSelectedSkuCode() {
    return selectedSkuCode;
  }

  /**
   * selectedSkuCodeを設定します。
   * 
   * @param selectedSkuCode
   *          selectedSkuCode
   */
  public void setSelectedSkuCode(String selectedSkuCode) {
    this.selectedSkuCode = selectedSkuCode;
  }

  /**
   * @return the discountRate
   */
  public String getDiscountRate() {
    return discountRate;
  }

  /**
   * @param discountRate
   *          the discountRate to set
   */
  public void setDiscountRate(String discountRate) {
    this.discountRate = discountRate;
  }

  /**
   * @return the discountPrices
   */
  public String getDiscountPrices() {
    return discountPrices;
  }

  /**
   * @param discountPrices
   *          the discountPrices to set
   */
  public void setDiscountPrices(String discountPrices) {
    this.discountPrices = discountPrices;
  }

  /**
   * @return the displayDiscountRate
   */
  public boolean isDisplayDiscountRate() {
    return displayDiscountRate;
  }

  /**
   * @param displayDiscountRate
   *          the displayDiscountRate to set
   */
  public void setDisplayDiscountRate(boolean displayDiscountRate) {
    this.displayDiscountRate = displayDiscountRate;
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
   * @return the commodityDescriptionShort
   */
  public String getCommodityDescriptionShort() {
    return commodityDescriptionShort;
  }

  /**
   * @param commodityDescriptionShort
   *          the commodityDescriptionShort to set
   */
  public void setCommodityDescriptionShort(String commodityDescriptionShort) {
    this.commodityDescriptionShort = commodityDescriptionShort;
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
   * @return the discountPriceEndDatetime
   */
  public String getDiscountPriceEndDatetime() {
    return discountPriceEndDatetime;
  }

  /**
   * @param discountPriceEndDatetime
   *          the discountPriceEndDatetime to set
   */
  public void setDiscountPriceEndDatetime(String discountPriceEndDatetime) {
    this.discountPriceEndDatetime = discountPriceEndDatetime;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the keyword
   */
  public String getKeyword() {
    return keyword;
  }

  /**
   * @param title
   *          the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @param keyword
   *          the keyword to set
   */
  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public static class RecommendSet implements Serializable {

    private static final long serialVersionUID = 1L;

    private String setCommodityName;

    private String setCommodityCode;

    private List<DetailRecommendBaseBean> childCommodity = new ArrayList<DetailRecommendBaseBean>();

    private String unitPrice;

    private String setPrice;

    private Long taxType;

    private String priceMode;

    private String setSkuCode;
    
    private String cheapPrice;

    private boolean displayCartButton = true;

    public String getSetSkuCode() {
      return setSkuCode;
    }

    public void setSetSkuCode(String setSkuCode) {
      this.setSkuCode = setSkuCode;
    }

    public boolean isDisplayCartButton() {
      return displayCartButton;
    }

    public void setDisplayCartButton(boolean displayCartButton) {
      this.displayCartButton = displayCartButton;
    }

    public String getPriceMode() {
      return priceMode;
    }

    public void setPriceMode(String priceMode) {
      this.priceMode = priceMode;
    }

    public Long getTaxType() {
      return taxType;
    }

    public void setTaxType(Long taxType) {
      this.taxType = taxType;
    }

    public String getSetCommodityName() {
      return setCommodityName;
    }

    public void setSetCommodityName(String setCommodityName) {
      this.setCommodityName = setCommodityName;
    }

    public String getSetCommodityCode() {
      return setCommodityCode;
    }

    public void setSetCommodityCode(String setCommodityCode) {
      this.setCommodityCode = setCommodityCode;
    }

    public List<DetailRecommendBaseBean> getChildCommodity() {
      return childCommodity;
    }

    public void setChildCommodity(List<DetailRecommendBaseBean> childCommodity) {
      this.childCommodity = childCommodity;
    }

    public String getUnitPrice() {
      return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
      this.unitPrice = unitPrice;
    }

    public String getSetPrice() {
      return setPrice;
    }

    public void setSetPrice(String setPrice) {
      this.setPrice = setPrice;
    }

    
    /**
     * @return the cheapPrice
     */
    public String getCheapPrice() {
      return cheapPrice;
    }

    
    /**
     * @param cheapPrice the cheapPrice to set
     */
    public void setCheapPrice(String cheapPrice) {
      this.cheapPrice = cheapPrice;
    }

  }

  public static class DetailCartComposition implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tax;

    private String priceMode;

    private String representPrice;

    private String commodityCode;

    private String commodityName;

    private String shopCode;

    private String commodityStandard1Name;

    private String commodityStandard2Name;

    private String selectedSkuCode;

    private String stockManagementType;

    private String representSkuCode;

    private String standardAssortment;

    private Long standardSize;

    private String stockStatusNo;

    private String oneshotOrderLimit;

    private List<CodeAttribute> stander = new ArrayList<CodeAttribute>();

    private List<DetailCartDetail> compositionSkuList = new ArrayList<DetailCartDetail>();

    private boolean salableComposition;

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

    public String getShopCode() {
      return shopCode;
    }

    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    public String getCommodityStandard1Name() {
      return commodityStandard1Name;
    }

    public void setCommodityStandard1Name(String commodityStandard1Name) {
      this.commodityStandard1Name = commodityStandard1Name;
    }

    public String getCommodityStandard2Name() {
      return commodityStandard2Name;
    }

    public void setCommodityStandard2Name(String commodityStandard2Name) {
      this.commodityStandard2Name = commodityStandard2Name;
    }

    public String getSelectedSkuCode() {
      return selectedSkuCode;
    }

    public void setSelectedSkuCode(String selectedSkuCode) {
      this.selectedSkuCode = selectedSkuCode;
    }

    public String getStockManagementType() {
      return stockManagementType;
    }

    public void setStockManagementType(String stockManagementType) {
      this.stockManagementType = stockManagementType;
    }

    public String getRepresentSkuCode() {
      return representSkuCode;
    }

    public void setRepresentSkuCode(String representSkuCode) {
      this.representSkuCode = representSkuCode;
    }

    public String getStandardAssortment() {
      return standardAssortment;
    }

    public void setStandardAssortment(String standardAssortment) {
      this.standardAssortment = standardAssortment;
    }

    public Long getStandardSize() {
      return standardSize;
    }

    public void setStandardSize(Long standardSize) {
      this.standardSize = standardSize;
    }

    public String getStockStatusNo() {
      return stockStatusNo;
    }

    public void setStockStatusNo(String stockStatusNo) {
      this.stockStatusNo = stockStatusNo;
    }

    public String getOneshotOrderLimit() {
      return oneshotOrderLimit;
    }

    public void setOneshotOrderLimit(String oneshotOrderLimit) {
      this.oneshotOrderLimit = oneshotOrderLimit;
    }

    public List<DetailCartDetail> getCompositionSkuList() {
      return compositionSkuList;
    }

    public void setCompositionSkuList(List<DetailCartDetail> compositionSkuList) {
      this.compositionSkuList = compositionSkuList;
    }

    public boolean isSalableComposition() {
      return salableComposition;
    }

    public void setSalableComposition(boolean salableComposition) {
      this.salableComposition = salableComposition;
    }

    public String getRepresentPrice() {
      return representPrice;
    }

    public void setRepresentPrice(String representPrice) {
      this.representPrice = representPrice;
    }

    public String getPriceMode() {
      return priceMode;
    }

    public void setPriceMode(String priceMode) {
      this.priceMode = priceMode;
    }

    public String getTax() {
      return tax;
    }

    public void setTax(String tax) {
      this.tax = tax;
    }

    public List<CodeAttribute> getStander() {
      return stander;
    }

    public void setStander(List<CodeAttribute> stander) {
      this.stander = stander;
    }

  }

  /**
   * @return the customerMaxTotalNum
   */
  public Long getCustomerMaxTotalNum() {
    return customerMaxTotalNum;
  }

  /**
   * @param customerMaxTotalNum
   *          the customerMaxTotalNum to set
   */
  public void setCustomerMaxTotalNum(Long customerMaxTotalNum) {
    this.customerMaxTotalNum = customerMaxTotalNum;
  }

  /**
   * @return the nearMsg
   */
  public String getNearMsg() {
    return nearMsg;
  }

  /**
   * @param nearMsg
   *          the nearMsg to set
   */
  public void setNearMsg(String nearMsg) {
    this.nearMsg = nearMsg;
  }

}
