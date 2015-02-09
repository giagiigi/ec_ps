package jp.co.sint.webshop.web.bean.front.catalog;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.PriceList;
import jp.co.sint.webshop.data.domain.ReviewScore;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition.SearchDetailAttributeList;
import jp.co.sint.webshop.service.communication.CampaignHeadLine;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.CollectionUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040410:商品一覧のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CommodityListBean extends UIFrontBean implements UISearchBean {

  private static final String IMAGE_MODE = "image";

  private static final String TEXT_MODE = "text";

  private static final String HALF_SPACE = " ";

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CodeAttribute> commoditySort;

  private List<CommodityListDetailBean> list = new ArrayList<CommodityListDetailBean>();

  private List<SearchDetailAttributeList> searchCategoryAttributeList = new ArrayList<SearchDetailAttributeList>();

  private HashMap<Long, CampaignHeadLine> campaignMap = new HashMap<Long, CampaignHeadLine>();

  private String searchCampaignId;

  private List<CodeAttribute> campaignIdList;

  private String mode;
  
  private String searchSelected;
  
  private boolean selectedFlg;

  // used for detect if a customer already left 5 or more message.
  private boolean fiveMessageFlag;
  
  private String searchMethod;

  private String searchWord;

  private String encoded_searchWord;

  private String searchCommodityCode;

  private String searchPriceStart;

  private String searchPriceEnd;

  private String searchCategoryCode;

  private String searchShopCode;

  private String searchCampaignCode;

  private String searchTagCode;

  private String searchReviewScore;

  private String alignmentSequence;

  private PagerValue pagerValue;

  private String searchBrandCode;

  private String searchAttribute1;

  private String searchAttribute2;

  private String searchAttribute3;

  private String searchPrice;

  private boolean sessionRead;

  private String metaKeyword;

  private String metaDescription;

  private String priceStart;

  private String priceEnd;

  private String categoryName;

  private boolean categoryFlag;

  private boolean brandFlag;

  private String url;

  // 20121213 txw add start
  private boolean displayCategoryFlg;

  // 20121213 txw add end

  private String selectCommodityCode;

  private String importCommodityType;

  private String clearCommodityType;

  private String reserveCommodityType1;

  private String reserveCommodityType2;

  private String reserveCommodityType3;

  private String newReserveCommodityType1;

  private String newReserveCommodityType2;

  private String newReserveCommodityType3;

  private String newReserveCommodityType4;

  private String newReserveCommodityType5;

  
  // 20130809 txw add start
  private String title;

  private String description;

  private String keyword;

  // 20130809 txw add end

  /**
   * U2040410:商品一覧のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CommodityListDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;
    
    private boolean setCommodityFlg = false;

    private String categoryCode;

    private String commodityCode;

    private String commodityImage;

    private String commodityName;

    private String unitPrice;

    private String discountPrice;

    private String reservationPrice;

    private String commodityTaxType;

    private String campaignDiscountRate;

    private String commodityPointRate;

    private String reviewScore;

    private String reviewCount;

    private String commodityDescription;

    private String stockStatusImage;

    private String campaignImage;

    private String campaignCode;

    private String campaignName;

    private String searchWord;

    private String shopCode;

    private String shopName;

    private String priceMode;

    private boolean campaignPeriod;

    private boolean discountPeriod;

    private boolean reservationPeriod;

    private boolean commodityPointPeriod;

    private boolean hasStock;
    
    private boolean useFlag;

    private boolean displayStockStatus;

    private boolean descpritionLengthOver;

    private String discountRate;

    private String discountPrices;

    private boolean displayDiscountRate;

    private boolean displayFlg;

    private long importCommodityType;

    private long clearCommodityType;

    private long reserveCommodityType1;

    private long reserveCommodityType2;

    private long reserveCommodityType3;

    private long newReserveCommodityType1;

    private long newReserveCommodityType2;

    private long newReserveCommodityType3;

    private long newReserveCommodityType4;

    private long newReserveCommodityType5;

    private long innerQuantity;
    
    private boolean discountCommodityFlg;

    private String originalCommodityCode;

    private long combinationAmount;
    
    //商品现属的优惠活动名称
    private String curcampaignName;

    /**
     * @return the reserveCommodityType1
     */
    public long getReserveCommodityType1() {
      return reserveCommodityType1;
    }

    /**
     * @param reserveCommodityType1
     *          the reserveCommodityType1 to set
     */
    public void setReserveCommodityType1(long reserveCommodityType1) {
      this.reserveCommodityType1 = reserveCommodityType1;
    }

    /**
     * @return the reserveCommodityType2
     */
    public long getReserveCommodityType2() {
      return reserveCommodityType2;
    }

    /**
     * @param reserveCommodityType2
     *          the reserveCommodityType2 to set
     */
    public void setReserveCommodityType2(long reserveCommodityType2) {
      this.reserveCommodityType2 = reserveCommodityType2;
    }

    /**
     * @return the reserveCommodityType3
     */
    public long getReserveCommodityType3() {
      return reserveCommodityType3;
    }

    /**
     * @param reserveCommodityType3
     *          the reserveCommodityType3 to set
     */
    public void setReserveCommodityType3(long reserveCommodityType3) {
      this.reserveCommodityType3 = reserveCommodityType3;
    }

    /**
     * @return the newReserveCommodityType1
     */
    public long getNewReserveCommodityType1() {
      return newReserveCommodityType1;
    }

    /**
     * @param newReserveCommodityType1
     *          the newReserveCommodityType1 to set
     */
    public void setNewReserveCommodityType1(long newReserveCommodityType1) {
      this.newReserveCommodityType1 = newReserveCommodityType1;
    }

    /**
     * @return the newReserveCommodityType2
     */
    public long getNewReserveCommodityType2() {
      return newReserveCommodityType2;
    }

    /**
     * @param newReserveCommodityType2
     *          the newReserveCommodityType2 to set
     */
    public void setNewReserveCommodityType2(long newReserveCommodityType2) {
      this.newReserveCommodityType2 = newReserveCommodityType2;
    }

    /**
     * @return the newReserveCommodityType3
     */
    public long getNewReserveCommodityType3() {
      return newReserveCommodityType3;
    }

    /**
     * @param newReserveCommodityType3
     *          the newReserveCommodityType3 to set
     */
    public void setNewReserveCommodityType3(long newReserveCommodityType3) {
      this.newReserveCommodityType3 = newReserveCommodityType3;
    }

    /**
     * @return the newReserveCommodityType4
     */
    public long getNewReserveCommodityType4() {
      return newReserveCommodityType4;
    }

    /**
     * @param newReserveCommodityType4
     *          the newReserveCommodityType4 to set
     */
    public void setNewReserveCommodityType4(long newReserveCommodityType4) {
      this.newReserveCommodityType4 = newReserveCommodityType4;
    }

    public long getNewReserveCommodityType5() {
      return newReserveCommodityType5;
    }

    /**
     * @param newReserveCommodityType5
     *          the newReserveCommodityType5 to set
     */
    public void setNewReserveCommodityType5(long newReserveCommodityType5) {
      this.newReserveCommodityType5 = newReserveCommodityType5;
    }

    /**
     * @return the innerQuantity
     */
    public long getInnerQuantity() {
      return innerQuantity;
    }

    /**
     * @param innerQuantity
     *          the innerQuantity to set
     */
    public void setInnerQuantity(long innerQuantity) {
      this.innerQuantity = innerQuantity;
    }

    
    /**
     * @return the discountCommodity
     */
    public boolean isDiscountCommodityFlg() {
      return discountCommodityFlg;
    }

    
    /**
     * @param discountCommodity the discountCommodity to set
     */
    public void setDiscountCommodityFlg(boolean discountCommodityFlg) {
      this.discountCommodityFlg = discountCommodityFlg;
    }

    /**
     * @return the importCommodityType
     */
    public long getImportCommodityType() {
      return importCommodityType;
    }

    /**
     * @param importCommodityType
     *          the importCommodityType to set
     */
    public void setImportCommodityType(long importCommodityType) {
      this.importCommodityType = importCommodityType;
    }

    /**
     * @return the clearCommodityType
     */
    public long getClearCommodityType() {
      return clearCommodityType;
    }

    /**
     * @param clearCommodityType
     *          the clearCommodityType to set
     */
    public void setClearCommodityType(long clearCommodityType) {
      this.clearCommodityType = clearCommodityType;
    }

    /**
     * @return the displayFlg
     */
    public boolean isDisplayFlg() {
      return displayFlg;
    }

    /**
     * @param displayFlg
     *          the displayFlg to set
     */
    public void setDisplayFlg(boolean displayFlg) {
      this.displayFlg = displayFlg;
    }

    /**
     * hasStockを取得します。
     * 
     * @return hasStock
     */
    public boolean isHasStock() {
      return hasStock;
    }

    /**
     * hasStockを設定します。
     * 
     * @param hasStock
     *          hasStock
     */
    public void setHasStock(boolean hasStock) {
      this.hasStock = hasStock;
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
     * discountPeriodを取得します。
     * 
     * @return discountPeriod
     */
    public boolean isDiscountPeriod() {
      return discountPeriod;
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
     * campaignImageを取得します。
     * 
     * @return campaignImage
     */
    public String getCampaignImage() {
      return campaignImage;
    }

    /**
     * categoryCodeを取得します。
     * 
     * @return categoryCode
     */
    public String getCategoryCode() {
      return categoryCode;
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
     * commodityDescriptionを取得します。
     * 
     * @return commodityDescription
     */
    public String getCommodityDescription() {
      return commodityDescription;
    }

    /**
     * commodityImageを取得します。
     * 
     * @return commodityImage
     */
    public String getCommodityImage() {
      return commodityImage;
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
     * commodityPointRateを取得します。
     * 
     * @return commodityPointRate
     */
    public String getCommodityPointRate() {
      return commodityPointRate;
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
     * reviewScoreを取得します。
     * 
     * @return reviewScore
     */
    public String getReviewScore() {
      return reviewScore;
    }

    /**
     * reviewCountを取得します。
     * 
     * @return reviewCount
     */
    public String getReviewCount() {
      return reviewCount;
    }

    /**
     * searchWordを取得します。
     * 
     * @return searchWord
     */
    public String getSearchWord() {
      return searchWord;
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
     * shopNameを取得します。
     * 
     * @return shopName
     */
    public String getShopName() {
      return shopName;
    }

    /**
     * stockStatusImageを取得します。
     * 
     * @return stockStatusImage
     */
    public String getStockStatusImage() {
      return stockStatusImage;
    }

    /**
     * campaignImageを設定します。
     * 
     * @param campaignImage
     *          campaignImage
     */
    public void setCampaignImage(String campaignImage) {
      this.campaignImage = campaignImage;
    }

    /**
     * categoryCodeを設定します。
     * 
     * @param categoryCode
     *          categoryCode
     */
    public void setCategoryCode(String categoryCode) {
      this.categoryCode = categoryCode;
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
     * commodityDescriptionを設定します。
     * 
     * @param commodityDescription
     *          commodityDescription
     */
    public void setCommodityDescription(String commodityDescription) {
      this.commodityDescription = commodityDescription;
    }

    /**
     * commodityImageを設定します。
     * 
     * @param commodityImage
     *          commodityImage
     */
    public void setCommodityImage(String commodityImage) {
      this.commodityImage = commodityImage;
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
     * commodityPointRateを設定します。
     * 
     * @param commodityPointRate
     *          commodityPointRate
     */
    public void setCommodityPointRate(String commodityPointRate) {
      this.commodityPointRate = commodityPointRate;
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
     * commodityTaxTypeを設定します。
     * 
     * @param commodityTaxType
     *          commodityTaxType
     */
    public void setCommodityTaxType(String commodityTaxType) {
      this.commodityTaxType = commodityTaxType;
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
     * reviewScoreを設定します。
     * 
     * @param reviewScore
     *          reviewScore
     */
    public void setReviewScore(String reviewScore) {
      this.reviewScore = reviewScore;
    }

    /**
     * reviewCountを設定します。
     * 
     * @param reviewCount
     *          reviewCount
     */
    public void setReviewCount(String reviewCount) {
      this.reviewCount = reviewCount;
    }

    /**
     * searchWordを設定します。
     * 
     * @param searchWord
     *          searchWord
     */
    public void setSearchWord(String searchWord) {
      this.searchWord = searchWord;
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
     * shopNameを設定します。
     * 
     * @param shopName
     *          shopName
     */
    public void setShopName(String shopName) {
      this.shopName = shopName;
    }

    /**
     * stockStatusImageを設定します。
     * 
     * @param stockStatusImage
     *          stockStatusImage
     */
    public void setStockStatusImage(String stockStatusImage) {
      this.stockStatusImage = stockStatusImage;
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
     * descpritionLengthOverを取得します。
     * 
     * @return descpritionLengthOver
     */
    public boolean isDescpritionLengthOver() {
      return descpritionLengthOver;
    }

    /**
     * descpritionLengthOverを設定します。
     * 
     * @param descpritionLengthOver
     *          descpritionLengthOver
     */
    public void setDescpritionLengthOver(boolean descpritionLengthOver) {
      this.descpritionLengthOver = descpritionLengthOver;
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
     * displayStockStatusを取得します。
     * 
     * @return displayStockStatus
     */
    public boolean isDisplayStockStatus() {
      return displayStockStatus;
    }

    /**
     * displayStockStatusを設定します。
     * 
     * @param displayStockStatus
     *          displayStockStatus
     */
    public void setDisplayStockStatus(boolean displayStockStatus) {
      this.displayStockStatus = displayStockStatus;
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
     * @return the useFlag
     */
    public boolean isUseFlag() {
      return useFlag;
    }

    
    /**
     * @param useFlag the useFlag to set
     */
    public void setUseFlag(boolean useFlag) {
      this.useFlag = useFlag;
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

    
    /**
     * @return the curcampaignName
     */
    public String getCurcampaignName() {
      return curcampaignName;
    }

    
    /**
     * @param curcampaignName the curcampaignName to set
     */
    public void setCurcampaignName(String curcampaignName) {
      this.curcampaignName = curcampaignName;
    }

  }

  /**
   * @return the selectCommodityCode
   */
  public String getSelectCommodityCode() {
    return selectCommodityCode;
  }

  /**
   * @param selectCommodityCode
   *          the selectCommodityCode to set
   */
  public void setSelectCommodityCode(String selectCommodityCode) {
    this.selectCommodityCode = selectCommodityCode;
  }

  /**
   * 商品詳細一覧を取得します。
   * 
   * @return list
   */
  public List<CommodityListDetailBean> getList() {
    return list;
  }

  /**
   * 商品詳細一覧を設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<CommodityListDetailBean> list) {
    this.list = list;
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * searchCategoryCodeを取得します。
   * 
   * @return searchCategoryCode
   */
  public String getSearchCategoryCode() {
    return searchCategoryCode;
  }

  /**
   * searchCategoryCodeを設定します。
   * 
   * @param searchCategoryCode
   *          searchCategoryCode
   */
  public void setSearchCategoryCode(String searchCategoryCode) {
    this.searchCategoryCode = searchCategoryCode;
  }

  /**
   * searchCommodityCodeを取得します。
   * 
   * @return searchCommodityCode
   */
  public String getSearchCommodityCode() {
    return searchCommodityCode;
  }

  /**
   * searchCommodityCodeを設定します。
   * 
   * @param searchCommodityCode
   *          searchCommodityCode
   */
  public void setSearchCommodityCode(String searchCommodityCode) {
    this.searchCommodityCode = searchCommodityCode;
  }

  /**
   * searchPriceEndを取得します。
   * 
   * @return searchPriceEnd
   */
  public String getSearchPriceEnd() {
    return searchPriceEnd;
  }

  /**
   * searchPriceEndを設定します。
   * 
   * @param searchPriceEnd
   *          searchPriceEnd
   */
  public void setSearchPriceEnd(String searchPriceEnd) {
    this.searchPriceEnd = searchPriceEnd;
  }

  /**
   * searchPriceStartを取得します。
   * 
   * @return searchPriceStart
   */
  public String getSearchPriceStart() {
    return searchPriceStart;
  }

  /**
   * searchPriceStartを設定します。
   * 
   * @param searchPriceStart
   *          searchPriceStart
   */
  public void setSearchPriceStart(String searchPriceStart) {
    this.searchPriceStart = searchPriceStart;
  }

  /**
   * searchReviewScoreを取得します。
   * 
   * @return searchReviewScore
   */
  public String getSearchReviewScore() {
    return searchReviewScore;
  }

  /**
   * searchReviewScoreを設定します。
   * 
   * @param searchReviewScore
   *          searchReviewScore
   */
  public void setSearchReviewScore(String searchReviewScore) {
    this.searchReviewScore = searchReviewScore;
  }

  /**
   * searchShopCodeを取得します。
   * 
   * @return searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
  }

  /**
   * searchShopCodeを設定します。
   * 
   * @param searchShopCode
   *          searchShopCode
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  /**
   * searchWordを取得します。
   * 
   * @return searchWord
   */
  public String getSearchWord() {
    return searchWord;
  }

  /**
   * searchWordを設定します。
   * 
   * @param searchWord
   *          searchWord
   */
  public void setSearchWord(String searchWord) {
    this.searchWord = searchWord;
  }

  /**
   * searchMethodを取得します。
   * 
   * @return searchMethod
   */
  public String getSearchMethod() {
    return searchMethod;
  }

  /**
   * searchMethodを設定します。
   * 
   * @param searchMethod
   *          searchMethod
   */
  public void setSearchMethod(String searchMethod) {
    this.searchMethod = searchMethod;
  }

  /**
   * commoditySortを取得します。
   * 
   * @return commoditySort
   */
  public List<CodeAttribute> getCommoditySort() {
    return commoditySort;
  }

  /**
   * commoditySortを設定します。
   * 
   * @param commoditySort
   *          commoditySort
   */
  public void setCommoditySort(List<CodeAttribute> commoditySort) {
    this.commoditySort = commoditySort;
  }

  /**
   * alignmentSequenceを取得します。
   * 
   * @return alignmentSequence
   */
  public String getAlignmentSequence() {
    return alignmentSequence;
  }

  /**
   * alignmentSequenceを設定します。
   * 
   * @param alignmentSequence
   *          alignmentSequence
   */
  public void setAlignmentSequence(String alignmentSequence) {
    this.alignmentSequence = alignmentSequence;
  }

  /**
   * modeを設定します。
   * 
   * @param mode
   *          mode
   */
  public void setMode(String mode) {
    this.mode = mode;
  }

  /**
   * modeを取得します。
   * 
   * @return mode
   */
  public String getMode() {
    return mode;
  }

  /**
   * searchCampaignCodeを取得します。
   * 
   * @return searchCampaignCode
   */
  public String getSearchCampaignCode() {
    return searchCampaignCode;
  }

  /**
   * searchCampaignCodeを設定します。
   * 
   * @param searchCampaignCode
   *          searchCampaignCode
   */
  public void setSearchCampaignCode(String searchCampaignCode) {
    this.searchCampaignCode = searchCampaignCode;
  }

  public List<List<Map<String, String>>> getPathList() {
    String str = StringUtil.replaceKeyword(encoded_searchWord);
    int num = DIContainer.getWebshopConfig().getFreeSearchCategoryListNum();
    if (str.equals(HALF_SPACE)) {
      return null;
    }
    if (StringUtil.hasValue(str)) {
      LoginInfo loginInfo = WebLoginManager.createFrontNotLoginInfo();
      CatalogService service = ServiceLocator.getCatalogService(loginInfo);
      List<Category> categoryList = service.getSearchPathList(encoded_searchWord,
          StringUtil.hasValue(searchCategoryCode) ? searchCategoryCode : "0", num);
      String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
      List<List<Map<String, String>>> categoryListPath = new ArrayList<List<Map<String, String>>>();

      if (categoryList != null && categoryList.size() > 0) {

        for (Category cat : categoryList) {
          List<Map<String, String>> tmpList = new ArrayList<Map<String, String>>();
          String[] strPath = cat.getPath().split("~");
          for (int i = 0; i < strPath.length; i++) {
            if (!(strPath[i].equals("/")) && !(strPath[i].equals("0"))) {
              Category categoryPath = service.getCategoryListForPath(strPath[i]);
              Map<String, String> catMap = new HashMap<String, String>();
              catMap.put("code", categoryPath.getCategoryCode());
              if (currentLanguageCode.equals("zh-cn")) {
                catMap.put("name", categoryPath.getCategoryNamePc());
              } else if (currentLanguageCode.equals("ja-jp")) {
                catMap.put("name", categoryPath.getCategoryNamePcJp());
              } else {
                catMap.put("name", categoryPath.getCategoryNamePcEn());
              }
              tmpList.add(catMap);
            }
          }
          Map<String, String> catMap = new HashMap<String, String>();
          catMap.put("code", cat.getCategoryCode());
          if (currentLanguageCode.equals("zh-cn")) {
            catMap.put("name", cat.getCategoryNamePc());
          } else if (currentLanguageCode.equals("ja-jp")) {
            catMap.put("name", cat.getCategoryNamePcJp());
          } else {
            catMap.put("name", cat.getCategoryNamePcEn());
          }
          tmpList.add(catMap);

          categoryListPath.add(tmpList);
        }
      }
      return categoryListPath;
    }
    return null;
  }

  public int getFreeSearchCategoryListNum() {
    return DIContainer.getWebshopConfig().getFreeSearchCategoryListNum();
  }

  public List<Brand> getBrandList() {
    int num = DIContainer.getWebshopConfig().getFreeSearchCategoryListNum();
    String str = StringUtil.replaceKeyword(encoded_searchWord);
    if (str.equals(HALF_SPACE)) {
      return null;
    }
    if (StringUtil.hasValue(str)) {
      LoginInfo loginInfo = WebLoginManager.createFrontNotLoginInfo();
      CatalogService service = ServiceLocator.getCatalogService(loginInfo);
      List<Brand> brandList = service.getBrandList(encoded_searchWord, num);
      return brandList;
    }
    return null;

  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    if (StringUtil.hasValue(this.selectCommodityCode)) {
      if (this.getSubJspIdList() != null && this.getSubJspIdList().size() > 0) {
        this.getSubJspIdList().clear();
      }
      return;
    }
    addSubJspId("/common/header");
    // addSubJspId("/catalog/category_tree");
    addSubJspId("/catalog/campaign_info");
    addSubJspId("/catalog/shop_list");
    addSubJspId("/catalog/topic_path");
    addSubJspId("/catalog/brand_path");
    
    // addSubJspId("/catalog/brand_tree");
    // addSubJspId("/catalog/review_summary");
    // addSubJspId("/catalog/price_list");
    // addSubJspId("/catalog/attribute_list");
    addSubJspId("/catalog/left_menu");
    addSubJspId("/catalog/param_path");
    // 20130904 txw add start
    addSubJspId("/catalog/sales_star");
    // 20130904 txw add end
    
    //20140902 hdh add start
    if(this.getList().size()>5){
      addSubJspId("/catalog/new_sales_recommend");
      this.getSubJspIdList().remove("/catalog/sales_recommend");
    }else{
      addSubJspId("/catalog/sales_recommend");
      this.getSubJspIdList().remove("/catalog/new_sales_recommend");
    }
    // 20140902 hdh add end
    
  }

  public void createAttributes(RequestParameter reqparam) {
    this.setSessionRead(false);
    if (StringUtil.hasValue(reqparam.get("sessionRead")) && reqparam.get("sessionRead").equals("1")) {
      this.setSessionRead(true);
    }
    // 检索按钮查找
    searchCategoryCode = reqparam.get("searchCategoryCode"); // 商品目录
    searchWord = reqparam.get("searchWord");
    encoded_searchWord = reqparam.get("searchWord");
    searchTagCode = reqparam.get("searchTagCode");
    importCommodityType = reqparam.get("importCommodityType");
    clearCommodityType = reqparam.get("clearCommodityType");
    reserveCommodityType1 = reqparam.get("reserveCommodityType1");
    reserveCommodityType2 = reqparam.get("reserveCommodityType2");
    reserveCommodityType3 = reqparam.get("reserveCommodityType3");
    newReserveCommodityType1 = reqparam.get("newReserveCommodityType1");
    newReserveCommodityType2 = reqparam.get("newReserveCommodityType2");
    newReserveCommodityType3 = reqparam.get("newReserveCommodityType3");
    newReserveCommodityType4 = reqparam.get("newReserveCommodityType4");
    newReserveCommodityType5 = reqparam.get("newReserveCommodityType5");

    // this.setSearchCategoryCode("");
    this.setSelectedFlg(false);
    this.setSearchBrandCode("");
    this.setSearchSelected("");
    this.setSearchReviewScore("");
    this.setSearchPriceStart("");
    this.setSearchPriceEnd("");
    this.setSearchPrice("");
    this.setSearchAttribute1("");
    this.setSearchAttribute2("");
    this.setSearchAttribute3("");
    this.setPriceStart("");
    this.setPriceEnd("");
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes_backup(RequestParameter reqparam) {
    // ../关键字/商品目录/品牌/评价/价格/属性1/属性2/属性3/排序/件数/页数

    this.setSessionRead(false);
    if (StringUtil.hasValue(reqparam.get("sessionRead")) && reqparam.get("sessionRead").equals("1")) {
      this.setSessionRead(true);
    }

    searchCategoryCode = reqparam.get("searchCategoryCode"); // 商品目录
    searchBrandCode = reqparam.get("searchBrandCode"); // 品牌
    searchReviewScore = reqparam.get("searchReviewScore"); // 评价
    searchPrice = reqparam.get("searchPrice"); // 价格
    alignmentSequence = reqparam.get("alignmentSequence"); // 排序

    searchCommodityCode = reqparam.get("searchCommodityCode");
    searchWord = reqparam.get("searchWord");
    searchShopCode = reqparam.get("searchShopCode");
    searchCampaignCode = reqparam.get("searchCampaignCode");
    searchCampaignId = reqparam.get("searchCampaignId");

    if (StringUtil.hasValue(searchPrice)) {
      CodeAttribute price = PriceList.fromValue(searchPrice);
      String[] prices = price.getName().split(",");
      if (prices.length == 2) {
        searchPriceStart = NumUtil.parse(prices[0]).abs().toString();
        searchPriceEnd = NumUtil.parse(prices[1]).abs().toString();
      } else {
        searchPriceStart = NumUtil.parse(prices[0]).abs().toString();
        searchPriceEnd = "9999999999";
      }
    } else {
      String priceStart = reqparam.get("searchPriceStart");
      if (StringUtil.hasValue(priceStart) && NumUtil.isDecimal(priceStart)) {
        priceStart = NumUtil.parse(priceStart).abs().toString();
        if (NumUtil.parse(priceStart).scale() > 2) {
          searchPriceStart = NumUtil.parse(priceStart).setScale(2, RoundingMode.FLOOR).toString();
        } else {
          searchPriceStart = priceStart;
        }
      } else {
        searchPriceStart = "";
      }

      String priceEnd = reqparam.get("searchPriceEnd");
      if (StringUtil.hasValue(priceEnd) && NumUtil.isDecimal(priceEnd)) {
        priceEnd = NumUtil.parse(priceEnd).abs().toString();
        if (NumUtil.parse(priceEnd).scale() > 2) {
          searchPriceEnd = NumUtil.parse(priceEnd).setScale(2, RoundingMode.FLOOR).toString();
        } else {
          searchPriceEnd = priceEnd;
        }
        if (StringUtil.hasValue(priceStart) && NumUtil.isDecimal(priceStart)
            && BigDecimalUtil.isAbove(NumUtil.parse(priceStart), NumUtil.parse(priceEnd))) {
          String tempSearchPriceStart = searchPriceStart;
          searchPriceStart = NumUtil.parse(searchPriceEnd).toString();
          searchPriceEnd = NumUtil.parse(tempSearchPriceStart).toString();
        }
      } else {
        searchPriceEnd = "";
      }
    }

    searchTagCode = reqparam.get("searchTagCode");

    String sm = reqparam.get("searchMethod");
    if (sm.equals("0") || sm.equals("1")) {
      searchMethod = sm;
    } else {
      searchMethod = "0";
    }

    mode = reqparam.get("mode");
    if (StringUtil.isNullOrEmpty(mode)) {
      mode = IMAGE_MODE;
    } else if (!mode.equals(IMAGE_MODE) && !mode.equals(TEXT_MODE)) {
      mode = IMAGE_MODE;
    }

    List<SearchDetailAttributeList> attributeList = new ArrayList<SearchDetailAttributeList>();
    String[] categoryAttributeNo = reqparam.getAll("categoryAttributeNo");
    String[] categoryAttributeValue = reqparam.getAll("categoryAttributeValue");
    for (int i = 0; i < categoryAttributeNo.length; i++) {
      SearchDetailAttributeList attribute = new SearchDetailAttributeList();
      attribute.setCategoryAttributeNo(categoryAttributeNo[i]);
      attribute.setCategoryAttributeValue(categoryAttributeValue[i]);
      attributeList.add(attribute);
    }
    setSearchCategoryAttributeList(attributeList);

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2040410";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.catalog.CommodityListBean.0");
  }

  /**
   * 検索条件をクエリストリングにして返します。
   * 
   * @return クエリストリングの文字列
   */
  public String toQueryString() {
    List<String> params = new ArrayList<String>();

    appendParameter(params, "searchCategoryCode", getSearchCategoryCode());
    appendParameter(params, "alignmentSequence", getAlignmentSequence());
    appendParameter(params, "searchCommodityCode", getSearchCommodityCode());
    appendParameter(params, "searchWord", getSearchWord());
    // 検索語がない場合はAND/OR条件不要
    if (StringUtil.hasValue(getSearchWord())) {
      appendParameter(params, "searchMethod", getSearchMethod());
    }
    appendParameter(params, "searchShopCode", getSearchShopCode());
    appendParameter(params, "searchCampaignCode", getSearchCampaignCode());
    appendParameter(params, "searchTagCode", getSearchTagCode());
    appendParameter(params, "searchReviewScore", getSearchReviewScore());
    appendParameter(params, "searchPriceStart", getSearchPriceStart());
    appendParameter(params, "searchPriceEnd", getSearchPriceEnd());
    for (SearchDetailAttributeList sal : getSearchCategoryAttributeList()) {
      if (StringUtil.hasValue(sal.getCategoryAttributeValue())) {
        appendParameter(params, "categoryAttributeNo", sal.getCategoryAttributeNo());
        appendParameter(params, "categoryAttributeValue", sal.getCategoryAttributeValue());
      }
    }
    appendParameter(params, "mode", getMode());
    if (getPagerValue() != null) {
      appendParameter(params, "currentPage", "" + getPagerValue().getCurrentPage());
      appendParameter(params, "pageSize", "" + getPagerValue().getPageSize());
    }

    StringBuilder builder = new StringBuilder();
    String delimiter = "?";
    for (String p : params) {
      builder.append(delimiter + p);
      delimiter = "&";
    }
    return builder.toString();
  }

  private void appendParameter(List<String> params, String name, String value) {
    if (StringUtil.hasValue(value)) {
      params.add(name + "=" + WebUtil.urlEncode(value));
    }
    return;
  }

  /**
   * searchCategoryAttributeListを取得します。
   * 
   * @return searchCategoryAttributeList
   */
  public List<SearchDetailAttributeList> getSearchCategoryAttributeList() {
    return searchCategoryAttributeList;
  }

  /**
   * searchCategoryAttributeListを設定します。
   * 
   * @param searchCategoryAttributeList
   *          searchCategoryAttributeList
   */
  public void setSearchCategoryAttributeList(List<SearchDetailAttributeList> searchCategoryAttributeList) {
    this.searchCategoryAttributeList = searchCategoryAttributeList;
  }

  /**
   * campaignIdListを取得します。
   * 
   * @return campaignIdList
   */

  public List<CodeAttribute> getCampaignIdList() {
    return campaignIdList;
  }

  /**
   * campaignMapを取得します。
   * 
   * @return campaignMap
   */

  public Map<Long, CampaignHeadLine> getCampaignMap() {
    return campaignMap;
  }

  /**
   * campaignIdListを設定します。
   * 
   * @param campaignIdList
   *          campaignIdList
   */
  public void setCampaignIdList(List<CodeAttribute> campaignIdList) {
    this.campaignIdList = campaignIdList;
  }

  /**
   * campaignMapを設定します。
   * 
   * @param campaignMap
   *          campaignMap
   */
  public void setCampaignMap(Map<Long, CampaignHeadLine> campaignMap) {
    CollectionUtil.copyAll(this.campaignMap, campaignMap);
  }

  /**
   * searchCampaignIdを取得します。
   * 
   * @return searchCampaignId
   */

  public String getSearchCampaignId() {
    return searchCampaignId;
  }

  /**
   * searchCampaignIdを設定します。
   * 
   * @param searchCampaignId
   *          searchCampaignId
   */
  public void setSearchCampaignId(String searchCampaignId) {
    this.searchCampaignId = searchCampaignId;
  }

  /**
   * searchTagCodeを取得します。
   * 
   * @return searchTagCode
   */

  public String getSearchTagCode() {
    return searchTagCode;
  }

  /**
   * searchTagCodeを設定します。
   * 
   * @param searchTagCode
   *          searchTagCode
   */
  public void setSearchTagCode(String searchTagCode) {
    this.searchTagCode = searchTagCode;
  }

  /**
   * @return the searchBrandCode
   */
  public String getSearchBrandCode() {
    return searchBrandCode;
  }

  /**
   * @param searchBrandCode
   *          the searchBrandCode to set
   */
  public void setSearchBrandCode(String searchBrandCode) {
    this.searchBrandCode = searchBrandCode;
  }

  /**
   * @return the searchCategoryAttribute1
   */
  public String getSearchAttribute1() {
    return searchAttribute1;
  }

  /**
   * @param searchCategoryAttribute1
   *          the searchCategoryAttribute1 to set
   */
  public void setSearchAttribute1(String searchAttribute1) {
    this.searchAttribute1 = searchAttribute1;
  }

  /**
   * @return the searchPrice
   */
  public String getSearchPrice() {
    return searchPrice;
  }

  /**
   * @param searchPrice
   *          the searchPrice to set
   */
  public void setSearchPrice(String searchPrice) {
    this.searchPrice = searchPrice;
  }

  /**
   * @return the sessionRead
   */
  public boolean isSessionRead() {
    return sessionRead;
  }

  /**
   * @param sessionRead
   *          the sessionRead to set
   */
  public void setSessionRead(boolean sessionRead) {
    this.sessionRead = sessionRead;
  }
  
  
  
  
  /**
   * 商品目录对应
   * 
   * @return
   * @throws UnsupportedEncodingException
   */
  public String getUrlSelected() {
    String url = "";
    
    if (StringUtil.hasValue(this.getSearchCategoryCode())) {
      url += "C" + WebUtil.escapeXml(this.getSearchCategoryCode()) + "-";
    }
    if (StringUtil.hasValue(this.getSearchBrandCode())) {
      url += "B" + this.getSearchBrandCode() + "-";
    }
    if (StringUtil.hasValue(this.getSearchReviewScore())) {
      url += "K" + this.getSearchReviewScore() + "-";
    }
    if (StringUtil.hasValue(this.getSearchPrice())) {
      url += "D" + this.getSearchPrice() + "-";
    }
    if (StringUtil.hasValueAllOf(this.getPriceStart(), this.getPriceEnd())) {
      url += "N" + this.getPriceStart() + "~" + this.getPriceEnd() + "-";
    } else if (StringUtil.hasValue(this.getPriceStart())) {
      url += "N" + this.getPriceStart() + "~" + "-";
    } else if (StringUtil.hasValue(this.getPriceEnd())) {
      url += "N" + "~" + this.getPriceEnd() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute1())) {
      url += "T1" + this.getSearchAttribute1() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute2())) {
      url += "T2" + this.getSearchAttribute2() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute3())) {
      url += "T3" + this.getSearchAttribute3() + "-";
    }
    if (StringUtil.hasValue(this.getAlignmentSequence())) {
      if (!this.getAlignmentSequence().equals("1")) {
        url += "Z" + this.getAlignmentSequence() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getPageSize() != 20) {
        url += "S" + this.getPagerValue().getPageSize() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getCurrentPage() != 1) {
        url += "P" + this.getPagerValue().getCurrentPage() + "-";
      }
    }
    if (StringUtil.hasValue(this.getSearchWord())) {
      url += "searchWord_" + WebUtil.urlEncode(this.getEncoded_searchWord(), "UTF-8") + "-";
    }
    if (StringUtil.hasValue(this.getImportCommodityType())) {
      url += "importFlg_" + WebUtil.escapeXml(this.getImportCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getClearCommodityType())) {
      url += "clearFlag_" + WebUtil.escapeXml(this.getClearCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType1())) {
      url += "asahiFlag_" + WebUtil.escapeXml(this.getReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType2())) {
      url += "hotCoFlag_" + WebUtil.escapeXml(this.getReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType3())) {
      url += "comZsFlag_" + WebUtil.escapeXml(this.getReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType1())) {
      url += "newRe1Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType2())) {
      url += "newRe2Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType3())) {
      url += "newRe3Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType4())) {
      url += "newRe4Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType4()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType5())) {
      url += "newRe5Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType5()) + "-";
    }

    if (StringUtil.hasValue(url)) {
      if (url.endsWith("-")) {
        url = url.substring(0, url.length() - 1);
      }
    }

    return url;
  }
  
  /**
   * 商品目录对应
   * 
   * @return
   * @throws UnsupportedEncodingException
   */
  public String getUrlCategoryPath() {
    String url = "";
    
    if (StringUtil.hasValue(this.getSearchSelected())) {
      url += "E" + this.getSearchSelected() + "-";
    }
    if (StringUtil.hasValue(this.getSearchBrandCode())) {
      url += "B" + this.getSearchBrandCode() + "-";
    }
    if (StringUtil.hasValue(this.getSearchReviewScore())) {
      url += "K" + this.getSearchReviewScore() + "-";
    }
    if (StringUtil.hasValue(this.getSearchPrice())) {
      url += "D" + this.getSearchPrice() + "-";
    }
    if (StringUtil.hasValueAllOf(this.getPriceStart(), this.getPriceEnd())) {
      url += "N" + this.getPriceStart() + "~" + this.getPriceEnd() + "-";
    } else if (StringUtil.hasValue(this.getPriceStart())) {
      url += "N" + this.getPriceStart() + "~" + "-";
    } else if (StringUtil.hasValue(this.getPriceEnd())) {
      url += "N" + "~" + this.getPriceEnd() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute1())) {
      url += "T1" + this.getSearchAttribute1() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute2())) {
      url += "T2" + this.getSearchAttribute2() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute3())) {
      url += "T3" + this.getSearchAttribute3() + "-";
    }
    if (StringUtil.hasValue(this.getAlignmentSequence())) {
      if (!this.getAlignmentSequence().equals("1")) {
        url += "Z" + this.getAlignmentSequence() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getPageSize() != 20) {
        url += "S" + this.getPagerValue().getPageSize() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getCurrentPage() != 1) {
        url += "P" + this.getPagerValue().getCurrentPage() + "-";
      }
    }
    if (StringUtil.hasValue(this.getSearchWord())) {
      url += "searchWord_" + WebUtil.urlEncode(this.getEncoded_searchWord(), "UTF-8") + "-";
    }
    if (StringUtil.hasValue(this.getImportCommodityType())) {
      url += "importFlg_" + WebUtil.escapeXml(this.getImportCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getClearCommodityType())) {
      url += "clearFlag_" + WebUtil.escapeXml(this.getClearCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType1())) {
      url += "asahiFlag_" + WebUtil.escapeXml(this.getReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType2())) {
      url += "hotCoFlag_" + WebUtil.escapeXml(this.getReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType3())) {
      url += "comZsFlag_" + WebUtil.escapeXml(this.getReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType1())) {
      url += "newRe1Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType2())) {
      url += "newRe2Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType3())) {
      url += "newRe3Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType4())) {
      url += "newRe4Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType4()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType5())) {
      url += "newRe5Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType5()) + "-";
    }

    if (StringUtil.hasValue(url)) {
      if (url.endsWith("-")) {
        url = url.substring(0, url.length() - 1);
      }
    }

    return url;
  }

  /**
   * 品牌对应
   * 
   * @return
   */
  public String getBrandUrlStr() {
    String url = "";
    if (StringUtil.hasValue(this.getSearchSelected())) {
      url += "E" + this.getSearchSelected() + "-";
    }
    if (StringUtil.hasValue(this.getSearchCategoryCode())) {
      url += "C" + WebUtil.escapeXml(this.getSearchCategoryCode()) + "-";
    }
    if (StringUtil.hasValue(this.getSearchReviewScore())) {
      url += "K" + this.getSearchReviewScore() + "-";
    }
    if (StringUtil.hasValue(this.getSearchPrice())) {
      url += "D" + this.getSearchPrice() + "-";
    }
    if (StringUtil.hasValueAllOf(this.getPriceStart(), this.getPriceEnd())) {
      url += "N" + this.getPriceStart() + "~" + this.getPriceEnd() + "-";
    } else if (StringUtil.hasValue(this.getPriceStart())) {
      url += "N" + this.getPriceStart() + "~" + "-";
    } else if (StringUtil.hasValue(this.getPriceEnd())) {
      url += "N" + "~" + this.getPriceEnd() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute1())) {
      url += "T1" + this.getSearchAttribute1() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute2())) {
      url += "T2" + this.getSearchAttribute2() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute3())) {
      url += "T3" + this.getSearchAttribute3() + "-";
    }
    if (StringUtil.hasValue(this.getAlignmentSequence())) {
      if (!this.getAlignmentSequence().equals("1")) {
        url += "Z" + this.getAlignmentSequence() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getPageSize() != 20) {
        url += "S" + this.getPagerValue().getPageSize() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getCurrentPage() != 1) {
        url += "P" + this.getPagerValue().getCurrentPage() + "-";
      }
    }
    if (StringUtil.hasValue(this.getSearchWord())) {
      url += "searchWord_" + WebUtil.urlEncode(this.getEncoded_searchWord(), "UTF-8") + "-";
    }
    if (StringUtil.hasValue(this.getImportCommodityType())) {
      url += "importFlg_" + WebUtil.escapeXml(this.getImportCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getClearCommodityType())) {
      url += "clearFlag_" + WebUtil.escapeXml(this.getClearCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType1())) {
      url += "asahiFlag_" + WebUtil.escapeXml(this.getReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType2())) {
      url += "hotCoFlag_" + WebUtil.escapeXml(this.getReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType3())) {
      url += "comZsFlag_" + WebUtil.escapeXml(this.getReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType1())) {
      url += "newRe1Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType2())) {
      url += "newRe2Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType3())) {
      url += "newRe3Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType4())) {
      url += "newRe4Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType4()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType5())) {
      url += "newRe5Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType5()) + "-";
    }
    if (StringUtil.hasValue(url)) {
      if (url.endsWith("-")) {
        url = url.substring(0, url.length() - 1);
      }
    }
    return url;
  }

  /**
   * 评论对应
   * 
   * @return
   */
  public String getReviewUrlStr() {
    String url = "";
    if (StringUtil.hasValue(this.getSearchSelected())) {
      url += "E" + this.getSearchSelected() + "-";
    }
    if (StringUtil.hasValue(this.getSearchCategoryCode())) {
      url += "C" + WebUtil.escapeXml(this.getSearchCategoryCode()) + "-";
    }
    if (StringUtil.hasValue(this.getSearchBrandCode())) {
      url += "B" + this.getSearchBrandCode() + "-";
    }
    if (StringUtil.hasValue(this.getSearchPrice())) {
      url += "D" + this.getSearchPrice() + "-";
    }
    if (StringUtil.hasValueAllOf(this.getPriceStart(), this.getPriceEnd())) {
      url += "N" + this.getPriceStart() + "~" + this.getPriceEnd() + "-";
    } else if (StringUtil.hasValue(this.getPriceStart())) {
      url += "N" + this.getPriceStart() + "~" + "-";
    } else if (StringUtil.hasValue(this.getPriceEnd())) {
      url += "N" + "~" + this.getPriceEnd() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute1())) {
      url += "T1" + this.getSearchAttribute1() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute2())) {
      url += "T2" + this.getSearchAttribute2() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute3())) {
      url += "T3" + this.getSearchAttribute3() + "-";
    }
    if (StringUtil.hasValue(this.getAlignmentSequence())) {
      if (!this.getAlignmentSequence().equals("1")) {
        url += "Z" + this.getAlignmentSequence() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getPageSize() != 20) {
        url += "S" + this.getPagerValue().getPageSize() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getCurrentPage() != 1) {
        url += "P" + this.getPagerValue().getCurrentPage() + "-";
      }
    }
    if (StringUtil.hasValue(this.getSearchWord())) {
      url += "searchWord_" + WebUtil.urlEncode(this.getEncoded_searchWord(), "UTF-8") + "-";
    }
    if (StringUtil.hasValue(this.getImportCommodityType())) {
      url += "importFlg_" + WebUtil.escapeXml(this.getImportCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getClearCommodityType())) {
      url += "clearFlag_" + WebUtil.escapeXml(this.getClearCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType1())) {
      url += "asahiFlag_" + WebUtil.escapeXml(this.getReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType2())) {
      url += "hotCoFlag_" + WebUtil.escapeXml(this.getReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType3())) {
      url += "comZsFlag_" + WebUtil.escapeXml(this.getReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType1())) {
      url += "newRe1Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType2())) {
      url += "newRe2Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType3())) {
      url += "newRe3Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType4())) {
      url += "newRe4Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType4()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType5())) {
      url += "newRe5Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType5()) + "-";
    }
    if (StringUtil.hasValue(url)) {
      if (url.endsWith("-")) {
        url = url.substring(0, url.length() - 1);
      }
    }
    return url;
  }

  /**
   * 价格对应
   * 
   * @return
   */
  public String getPriceUrlStr() {
    String url = "";
    if (StringUtil.hasValue(this.getSearchSelected())) {
      url += "E" + this.getSearchSelected() + "-";
    }
    if (StringUtil.hasValue(this.getSearchCategoryCode())) {
      url += "C" + WebUtil.escapeXml(this.getSearchCategoryCode()) + "-";
    }
    if (StringUtil.hasValue(this.getSearchBrandCode())) {
      url += "B" + this.getSearchBrandCode() + "-";
    }
    if (StringUtil.hasValue(this.getSearchReviewScore())) {
      url += "K" + this.getSearchReviewScore() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute1())) {
      url += "T1" + this.getSearchAttribute1() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute2())) {
      url += "T2" + this.getSearchAttribute2() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute3())) {
      url += "T3" + this.getSearchAttribute3() + "-";
    }
    if (StringUtil.hasValue(this.getAlignmentSequence())) {
      if (!this.getAlignmentSequence().equals("1")) {
        url += "Z" + this.getAlignmentSequence() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getPageSize() != 20) {
        url += "S" + this.getPagerValue().getPageSize() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getCurrentPage() != 1) {
        url += "P" + this.getPagerValue().getCurrentPage() + "-";
      }
    }
    if (StringUtil.hasValue(this.getSearchWord())) {
      url += "searchWord_" + WebUtil.urlEncode(this.getEncoded_searchWord(), "UTF-8") + "-";
    }
    if (StringUtil.hasValue(this.getImportCommodityType())) {
      url += "importFlg_" + WebUtil.escapeXml(this.getImportCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getClearCommodityType())) {
      url += "clearFlag_" + WebUtil.escapeXml(this.getClearCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType1())) {
      url += "asahiFlag_" + WebUtil.escapeXml(this.getReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType2())) {
      url += "hotCoFlag_" + WebUtil.escapeXml(this.getReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType3())) {
      url += "comZsFlag_" + WebUtil.escapeXml(this.getReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType1())) {
      url += "newRe1Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType2())) {
      url += "newRe2Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType3())) {
      url += "newRe3Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType4())) {
      url += "newRe4Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType4()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType5())) {
      url += "newRe5Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType5()) + "-";
    }
    if (StringUtil.hasValue(url)) {
      if (url.endsWith("-")) {
        url = url.substring(0, url.length() - 1);
      }
    }
    return url;
  }

  /**
   * 属性1对应
   * 
   * @return
   */
  public String getAttributeUrlStr1() {
    String url = "";
    if (StringUtil.hasValue(this.getSearchSelected())) {
      url += "E" + this.getSearchSelected() + "-";
    }
    if (StringUtil.hasValue(this.getSearchCategoryCode())) {
      url += "C" + WebUtil.escapeXml(this.getSearchCategoryCode()) + "-";
    }
    if (StringUtil.hasValue(this.getSearchBrandCode())) {
      url += "B" + this.getSearchBrandCode() + "-";
    }
    if (StringUtil.hasValue(this.getSearchReviewScore())) {
      url += "K" + this.getSearchReviewScore() + "-";
    }
    if (StringUtil.hasValue(this.getSearchPrice())) {
      url += "D" + this.getSearchPrice() + "-";
    }
    if (StringUtil.hasValueAllOf(this.getPriceStart(), this.getPriceEnd())) {
      url += "N" + this.getPriceStart() + "~" + this.getPriceEnd() + "-";
    } else if (StringUtil.hasValue(this.getPriceStart())) {
      url += "N" + this.getPriceStart() + "~" + "-";
    } else if (StringUtil.hasValue(this.getPriceEnd())) {
      url += "N" + "~" + this.getPriceEnd() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute2())) {
      url += "T2" + this.getSearchAttribute2() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute3())) {
      url += "T3" + this.getSearchAttribute3() + "-";
    }
    if (StringUtil.hasValue(this.getAlignmentSequence())) {
      if (!this.getAlignmentSequence().equals("1")) {
        url += "Z" + this.getAlignmentSequence() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getPageSize() != 20) {
        url += "S" + this.getPagerValue().getPageSize() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getCurrentPage() != 1) {
        url += "P" + this.getPagerValue().getCurrentPage() + "-";
      }
    }
    if (StringUtil.hasValue(this.getSearchWord())) {
      url += "searchWord_" + WebUtil.urlEncode(this.getEncoded_searchWord(), "UTF-8") + "-";
    }
    if (StringUtil.hasValue(this.getImportCommodityType())) {
      url += "importFlg_" + WebUtil.escapeXml(this.getImportCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getClearCommodityType())) {
      url += "clearFlag_" + WebUtil.escapeXml(this.getClearCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType1())) {
      url += "asahiFlag_" + WebUtil.escapeXml(this.getReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType2())) {
      url += "hotCoFlag_" + WebUtil.escapeXml(this.getReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType3())) {
      url += "comZsFlag_" + WebUtil.escapeXml(this.getReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType1())) {
      url += "newRe1Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType2())) {
      url += "newRe2Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType3())) {
      url += "newRe3Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType4())) {
      url += "newRe4Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType4()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType5())) {
      url += "newRe5Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType5()) + "-";
    }
    if (StringUtil.hasValue(url)) {
      if (url.endsWith("-")) {
        url = url.substring(0, url.length() - 1);
      }
    }
    return url;
  }

  /**
   * 属性2对应
   * 
   * @return
   */
  public String getAttributeUrlStr2() {
    String url = "";
    if (StringUtil.hasValue(this.getSearchSelected())) {
      url += "E" + this.getSearchSelected() + "-";
    }
    if (StringUtil.hasValue(this.getSearchCategoryCode())) {
      url += "C" + WebUtil.escapeXml(this.getSearchCategoryCode()) + "-";
    }
    if (StringUtil.hasValue(this.getSearchBrandCode())) {
      url += "B" + this.getSearchBrandCode() + "-";
    }
    if (StringUtil.hasValue(this.getSearchReviewScore())) {
      url += "K" + this.getSearchReviewScore() + "-";
    }
    if (StringUtil.hasValue(this.getSearchPrice())) {
      url += "D" + this.getSearchPrice() + "-";
    }
    if (StringUtil.hasValueAllOf(this.getPriceStart(), this.getPriceEnd())) {
      url += "N" + this.getPriceStart() + "~" + this.getPriceEnd() + "-";
    } else if (StringUtil.hasValue(this.getPriceStart())) {
      url += "N" + this.getPriceStart() + "~" + "-";
    } else if (StringUtil.hasValue(this.getPriceEnd())) {
      url += "N" + "~" + this.getPriceEnd() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute1())) {
      url += "T1" + this.getSearchAttribute1() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute3())) {
      url += "T3" + this.getSearchAttribute3() + "-";
    }
    if (StringUtil.hasValue(this.getAlignmentSequence())) {
      if (!this.getAlignmentSequence().equals("1")) {
        url += "Z" + this.getAlignmentSequence() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getPageSize() != 20) {
        url += "S" + this.getPagerValue().getPageSize() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getCurrentPage() != 1) {
        url += "P" + this.getPagerValue().getCurrentPage() + "-";
      }
    }
    if (StringUtil.hasValue(this.getSearchWord())) {
      url += "searchWord_" + WebUtil.urlEncode(this.getEncoded_searchWord(), "UTF-8") + "-";
    }
    if (StringUtil.hasValue(this.getImportCommodityType())) {
      url += "importFlg_" + WebUtil.escapeXml(this.getImportCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getClearCommodityType())) {
      url += "clearFlag_" + WebUtil.escapeXml(this.getClearCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType1())) {
      url += "asahiFlag_" + WebUtil.escapeXml(this.getReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType2())) {
      url += "hotCoFlag_" + WebUtil.escapeXml(this.getReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType3())) {
      url += "comZsFlag_" + WebUtil.escapeXml(this.getReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType1())) {
      url += "newRe1Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType2())) {
      url += "newRe2Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType3())) {
      url += "newRe3Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType4())) {
      url += "newRe4Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType4()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType5())) {
      url += "newRe5Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType5()) + "-";
    }
    if (StringUtil.hasValue(url)) {
      if (url.endsWith("-")) {
        url = url.substring(0, url.length() - 1);
      }
    }
    return url;
  }

  /**
   * 属性3对应
   * 
   * @return
   */
  public String getAttributeUrlStr3() {
    String url = "";
    if (StringUtil.hasValue(this.getSearchSelected())) {
      url += "E" + this.getSearchSelected() + "-";
    }
    if (StringUtil.hasValue(this.getSearchCategoryCode())) {
      url += "C" + WebUtil.escapeXml(this.getSearchCategoryCode()) + "-";
    }
    if (StringUtil.hasValue(this.getSearchBrandCode())) {
      url += "B" + this.getSearchBrandCode() + "-";
    }
    if (StringUtil.hasValue(this.getSearchReviewScore())) {
      url += "K" + this.getSearchReviewScore() + "-";
    }
    if (StringUtil.hasValue(this.getSearchPrice())) {
      url += "D" + this.getSearchPrice() + "-";
    }
    if (StringUtil.hasValueAllOf(this.getPriceStart(), this.getPriceEnd())) {
      url += "N" + this.getPriceStart() + "~" + this.getPriceEnd() + "-";
    } else if (StringUtil.hasValue(this.getPriceStart())) {
      url += "N" + this.getPriceStart() + "~" + "-";
    } else if (StringUtil.hasValue(this.getPriceEnd())) {
      url += "N" + "~" + this.getPriceEnd() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute1())) {
      url += "T1" + this.getSearchAttribute1() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute2())) {
      url += "T2" + this.getSearchAttribute2() + "-";
    }
    if (StringUtil.hasValue(this.getAlignmentSequence())) {
      if (!this.getAlignmentSequence().equals("1")) {
        url += "Z" + this.getAlignmentSequence() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getPageSize() != 20) {
        url += "S" + this.getPagerValue().getPageSize() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getCurrentPage() != 1) {
        url += "P" + this.getPagerValue().getCurrentPage() + "-";
      }
    }
    if (StringUtil.hasValue(this.getSearchWord())) {
      url += "searchWord_" + WebUtil.urlEncode(this.getEncoded_searchWord(), "UTF-8") + "-";
    }
    if (StringUtil.hasValue(this.getImportCommodityType())) {
      url += "importFlg_" + WebUtil.escapeXml(this.getImportCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getClearCommodityType())) {
      url += "clearFlag_" + WebUtil.escapeXml(this.getClearCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType1())) {
      url += "asahiFlag_" + WebUtil.escapeXml(this.getReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType2())) {
      url += "hotCoFlag_" + WebUtil.escapeXml(this.getReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType3())) {
      url += "comZsFlag_" + WebUtil.escapeXml(this.getReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType1())) {
      url += "newRe1Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType2())) {
      url += "newRe2Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType3())) {
      url += "newRe3Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType4())) {
      url += "newRe4Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType4()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType5())) {
      url += "newRe5Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType5()) + "-";
    }
    if (StringUtil.hasValue(url)) {
      if (url.endsWith("-")) {
        url = url.substring(0, url.length() - 1);
      }
    }
    return url;
  }

  /**
   * 排序对应
   * 
   * @return
   */
  public String getAlignUrlStr() {
    String url = "";
    if (StringUtil.hasValue(this.getSearchSelected())) {
      url += "E" + this.getSearchSelected() + "-";
    }
    if (StringUtil.hasValue(this.getSearchCategoryCode())) {
      url += "C" + WebUtil.escapeXml(this.getSearchCategoryCode()) + "-";
    }
    if (StringUtil.hasValue(this.getSearchBrandCode())) {
      url += "B" + this.getSearchBrandCode() + "-";
    }
    if (StringUtil.hasValue(this.getSearchReviewScore())) {
      url += "K" + this.getSearchReviewScore() + "-";
    }
    if (StringUtil.hasValue(this.getSearchPrice())) {
      url += "D" + this.getSearchPrice() + "-";
    }
    if (StringUtil.hasValueAllOf(this.getPriceStart(), this.getPriceEnd())) {
      url += "N" + this.getPriceStart() + "~" + this.getPriceEnd() + "-";
    } else if (StringUtil.hasValue(this.getPriceStart())) {
      url += "N" + this.getPriceStart() + "~" + "-";
    } else if (StringUtil.hasValue(this.getPriceEnd())) {
      url += "N" + "~" + this.getPriceEnd() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute1())) {
      url += "T1" + this.getSearchAttribute1() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute2())) {
      url += "T2" + this.getSearchAttribute2() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute3())) {
      url += "T3" + this.getSearchAttribute3() + "-";
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getPageSize() != 20) {
        url += "S" + this.getPagerValue().getPageSize() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getCurrentPage() != 1) {
        url += "P" + this.getPagerValue().getCurrentPage() + "-";
      }
    }
    if (StringUtil.hasValue(this.getSearchWord())) {
      url += "searchWord_" + WebUtil.urlEncode(this.getEncoded_searchWord(), "UTF-8") + "-";
    }
    if (StringUtil.hasValue(this.getImportCommodityType())) {
      url += "importFlg_" + WebUtil.escapeXml(this.getImportCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getClearCommodityType())) {
      url += "clearFlag_" + WebUtil.escapeXml(this.getClearCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType1())) {
      url += "asahiFlag_" + WebUtil.escapeXml(this.getReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType2())) {
      url += "hotCoFlag_" + WebUtil.escapeXml(this.getReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType3())) {
      url += "comZsFlag_" + WebUtil.escapeXml(this.getReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType1())) {
      url += "newRe1Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType2())) {
      url += "newRe2Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType3())) {
      url += "newRe3Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType4())) {
      url += "newRe4Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType4()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType5())) {
      url += "newRe5Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType5()) + "-";
    }
    if (StringUtil.hasValue(url)) {
      if (url.endsWith("-")) {
        url = url.substring(0, url.length() - 1);
      }
    }
    return url;
  }

  /**
   * 件数对应
   * 
   * @return
   */
  public String getPageSizeUrlStr() {
    String url = "";
    if (StringUtil.hasValue(this.getSearchSelected())) {
      url += "E" + this.getSearchSelected() + "-";
    }
    if (StringUtil.hasValue(this.getSearchCategoryCode())) {
      url += "C" + WebUtil.escapeXml(this.getSearchCategoryCode()) + "-";
    }
    if (StringUtil.hasValue(this.getSearchBrandCode())) {
      url += "B" + this.getSearchBrandCode() + "-";
    }
    if (StringUtil.hasValue(this.getSearchReviewScore())) {
      url += "K" + this.getSearchReviewScore() + "-";
    }
    if (StringUtil.hasValue(this.getSearchPrice())) {
      url += "D" + this.getSearchPrice() + "-";
    }
    if (StringUtil.hasValueAllOf(this.getPriceStart(), this.getPriceEnd())) {
      url += "N" + this.getPriceStart() + "~" + this.getPriceEnd() + "-";
    } else if (StringUtil.hasValue(this.getPriceStart())) {
      url += "N" + this.getPriceStart() + "~" + "-";
    } else if (StringUtil.hasValue(this.getPriceEnd())) {
      url += "N" + "~" + this.getPriceEnd() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute1())) {
      url += "T1" + this.getSearchAttribute1() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute2())) {
      url += "T2" + this.getSearchAttribute2() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute3())) {
      url += "T3" + this.getSearchAttribute3() + "-";
    }
    if (StringUtil.hasValue(this.getAlignmentSequence())) {
      if (!this.getAlignmentSequence().equals("1")) {
        url += "Z" + this.getAlignmentSequence() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      if (this.getPagerValue().getCurrentPage() != 1) {
        url += "P" + this.getPagerValue().getCurrentPage() + "-";
      }
    }
    if (StringUtil.hasValue(this.getSearchWord())) {
      url += "searchWord_" + WebUtil.urlEncode(this.getEncoded_searchWord(), "UTF-8") + "-";
    }
    if (StringUtil.hasValue(this.getImportCommodityType())) {
      url += "importFlg_" + WebUtil.escapeXml(this.getImportCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getClearCommodityType())) {
      url += "clearFlag_" + WebUtil.escapeXml(this.getClearCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType1())) {
      url += "asahiFlag_" + WebUtil.escapeXml(this.getReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType2())) {
      url += "hotCoFlag_" + WebUtil.escapeXml(this.getReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType3())) {
      url += "comZsFlag_" + WebUtil.escapeXml(this.getReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType1())) {
      url += "newRe1Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType2())) {
      url += "newRe2Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType3())) {
      url += "newRe3Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType4())) {
      url += "newRe4Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType4()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType5())) {
      url += "newRe5Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType5()) + "-";
    }
    if (StringUtil.hasValue(url)) {
      if (url.endsWith("-")) {
        url = url.substring(0, url.length() - 1);
      }
    }
    return url;
  }

  /**
   * 页数对应
   * 
   * @return
   */
  public String getCurrentPageUrlStr() {
    String url = "";
    if (StringUtil.hasValue(this.getSearchSelected())) {
      url += "E" + this.getSearchSelected() + "-";
    }
    if (StringUtil.hasValue(this.getSearchCategoryCode())) {
      url += "C" + WebUtil.escapeXml(this.getSearchCategoryCode()) + "-";
    }
    if (StringUtil.hasValue(this.getSearchBrandCode())) {
      url += "B" + this.getSearchBrandCode() + "-";
    }
    if (StringUtil.hasValue(this.getSearchReviewScore())) {
      url += "K" + this.getSearchReviewScore() + "-";
    }
    if (StringUtil.hasValue(this.getSearchPrice())) {
      url += "D" + this.getSearchPrice() + "-";
    }
    if (StringUtil.hasValueAllOf(this.getPriceStart(), this.getPriceEnd())) {
      url += "N" + this.getPriceStart() + "~" + this.getPriceEnd() + "-";
    } else if (StringUtil.hasValue(this.getPriceStart())) {
      url += "N" + this.getPriceStart() + "~" + "-";
    } else if (StringUtil.hasValue(this.getPriceEnd())) {
      url += "N" + "~" + this.getPriceEnd() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute1())) {
      url += "T1" + this.getSearchAttribute1() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute2())) {
      url += "T2" + this.getSearchAttribute2() + "-";
    }
    if (StringUtil.hasValue(this.getSearchAttribute3())) {
      url += "T3" + this.getSearchAttribute3() + "-";
    }
    if (StringUtil.hasValue(this.getAlignmentSequence())) {
      if (!this.getAlignmentSequence().equals("1")) {
        url += "Z" + this.getAlignmentSequence() + "-";
      }
    }
    if (this.getPagerValue() != null) {
      // if (this.getPagerValue().getPageSize() != 20) {
      url += "S" + this.getPagerValue().getPageSize() + "-";
      // }
    }
    if (StringUtil.hasValue(this.getSearchWord())) {
      url += "searchWord_" + WebUtil.urlEncode(this.getEncoded_searchWord(), "UTF-8") + "-";
    }
    if (StringUtil.hasValue(this.getImportCommodityType())) {
      url += "importFlg_" + WebUtil.escapeXml(this.getImportCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getClearCommodityType())) {
      url += "clearFlag_" + WebUtil.escapeXml(this.getClearCommodityType()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType1())) {
      url += "asahiFlag_" + WebUtil.escapeXml(this.getReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType2())) {
      url += "hotCoFlag_" + WebUtil.escapeXml(this.getReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getReserveCommodityType3())) {
      url += "comZsFlag_" + WebUtil.escapeXml(this.getReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType1())) {
      url += "newRe1Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType1()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType2())) {
      url += "newRe2Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType2()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType3())) {
      url += "newRe3Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType3()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType4())) {
      url += "newRe4Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType4()) + "-";
    }
    if (StringUtil.hasValue(this.getNewReserveCommodityType5())) {
      url += "newRe5Flg_" + WebUtil.escapeXml(this.getNewReserveCommodityType5()) + "-";
    }
    if (StringUtil.hasValue(url)) {
      if (url.endsWith("-")) {
        url = url.substring(0, url.length() - 1);
      }
    }
    return url;
  }

  // ../关键字/商品目录/品牌/评价/价格/属性1/属性2/属性3/排序/件数/页数/品店精选
  // searchWord_ C B K D T1 T2 T3 Z S P  E

  /**
   * @return the searchAttribute2
   */
  public String getSearchAttribute2() {
    return searchAttribute2;
  }

  /**
   * @param searchAttribute2
   *          the searchAttribute2 to set
   */
  public void setSearchAttribute2(String searchAttribute2) {
    this.searchAttribute2 = searchAttribute2;
  }

  /**
   * @return the searchAttribute3
   */
  public String getSearchAttribute3() {
    return searchAttribute3;
  }

  /**
   * @param searchAttribute3
   *          the searchAttribute3 to set
   */
  public void setSearchAttribute3(String searchAttribute3) {
    this.searchAttribute3 = searchAttribute3;
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
   * @return the priceStart
   */
  public String getPriceStart() {
    return priceStart;
  }

  /**
   * @param priceStart
   *          the priceStart to set
   */
  public void setPriceStart(String priceStart) {
    this.priceStart = priceStart;
  }

  /**
   * @return the priceEnd
   */
  public String getPriceEnd() {
    return priceEnd;
  }

  /**
   * @param priceEnd
   *          the priceEnd to set
   */
  public void setPriceEnd(String priceEnd) {
    this.priceEnd = priceEnd;
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
   * @return the categoryFlag
   */
  public boolean isCategoryFlag() {
    return categoryFlag;
  }

  /**
   * @param categoryFlag
   *          the categoryFlag to set
   */
  public void setCategoryFlag(boolean categoryFlag) {
    this.categoryFlag = categoryFlag;
  }

  /**
   * @return the brandFlag
   */
  public boolean isBrandFlag() {
    return brandFlag;
  }

  /**
   * @param brandFlag
   *          the brandFlag to set
   */
  public void setBrandFlag(boolean brandFlag) {
    this.brandFlag = brandFlag;
  }

  /**
   * @return the displayCategoryFlg
   */
  public boolean isDisplayCategoryFlg() {
    return displayCategoryFlg;
  }

  /**
   * @param displayCategoryFlg
   *          the displayCategoryFlg to set
   */
  public void setDisplayCategoryFlg(boolean displayCategoryFlg) {
    this.displayCategoryFlg = displayCategoryFlg;
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url
   *          the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  public String getImportCommodityType() {
    return importCommodityType;
  }

  public void setImportCommodityType(String importCommodityType) {
    this.importCommodityType = importCommodityType;
  }

  /**
   * @return the clearCommodityType
   */
  public String getClearCommodityType() {
    return clearCommodityType;
  }

  /**
   * @param clearCommodityType
   *          the clearCommodityType to set
   */
  public void setClearCommodityType(String clearCommodityType) {
    this.clearCommodityType = clearCommodityType;
  }

  /**
   * @return the reserveCommodityType1
   */
  public String getReserveCommodityType1() {
    return reserveCommodityType1;
  }

  /**
   * @param reserveCommodityType1
   *          the reserveCommodityType1 to set
   */
  public void setReserveCommodityType1(String reserveCommodityType1) {
    this.reserveCommodityType1 = reserveCommodityType1;
  }

  /**
   * @return the reserveCommodityType2
   */
  public String getReserveCommodityType2() {
    return reserveCommodityType2;
  }

  /**
   * @param reserveCommodityType2
   *          the reserveCommodityType2 to set
   */
  public void setReserveCommodityType2(String reserveCommodityType2) {
    this.reserveCommodityType2 = reserveCommodityType2;
  }

  /**
   * @return the reserveCommodityType3
   */
  public String getReserveCommodityType3() {
    return reserveCommodityType3;
  }

  /**
   * @param reserveCommodityType3
   *          the reserveCommodityType3 to set
   */
  public void setReserveCommodityType3(String reserveCommodityType3) {
    this.reserveCommodityType3 = reserveCommodityType3;
  }

  /**
   * @return the newReserveCommodityType1
   */
  public String getNewReserveCommodityType1() {
    return newReserveCommodityType1;
  }

  /**
   * @param newReserveCommodityType1
   *          the newReserveCommodityType1 to set
   */
  public void setNewReserveCommodityType1(String newReserveCommodityType1) {
    this.newReserveCommodityType1 = newReserveCommodityType1;
  }

  /**
   * @return the newReserveCommodityType2
   */
  public String getNewReserveCommodityType2() {
    return newReserveCommodityType2;
  }

  /**
   * @param newReserveCommodityType2
   *          the newReserveCommodityType2 to set
   */
  public void setNewReserveCommodityType2(String newReserveCommodityType2) {
    this.newReserveCommodityType2 = newReserveCommodityType2;
  }

  /**
   * @return the newReserveCommodityType3
   */
  public String getNewReserveCommodityType3() {
    return newReserveCommodityType3;
  }

  /**
   * @param newReserveCommodityType3
   *          the newReserveCommodityType3 to set
   */
  public void setNewReserveCommodityType3(String newReserveCommodityType3) {
    this.newReserveCommodityType3 = newReserveCommodityType3;
  }

  /**
   * @return the newReserveCommodityType4
   */
  public String getNewReserveCommodityType4() {
    return newReserveCommodityType4;
  }

  /**
   * @param newReserveCommodityType4
   *          the newReserveCommodityType4 to set
   */
  public void setNewReserveCommodityType4(String newReserveCommodityType4) {
    this.newReserveCommodityType4 = newReserveCommodityType4;
  }

  /**
   * @return the newReserveCommodityType5
   */
  public String getNewReserveCommodityType5() {
    return newReserveCommodityType5;
  }

  /**
   * @param newReserveCommodityType5
   *          the newReserveCommodityType5 to set
   */
  public void setNewReserveCommodityType5(String newReserveCommodityType5) {
    this.newReserveCommodityType5 = newReserveCommodityType5;
  }



  /**
   * @return the encoded_searchWord
   */
  public String getEncoded_searchWord() {
    return encoded_searchWord;
  }

  /**
   * @param encoded_searchWord
   *          the encoded_searchWord to set
   */
  public void setEncoded_searchWord(String encoded_searchWord) {
    this.encoded_searchWord = encoded_searchWord;
  }

  /**
   * @return the iMAGE_MODE
   */
  public static String getIMAGE_MODE() {
    return IMAGE_MODE;
  }

  /**
   * @return the tEXT_MODE
   */
  public static String getTEXT_MODE() {
    return TEXT_MODE;
  }

  /**
   * @return the hALF_SPACE
   */
  public static String getHALF_SPACE() {
    return HALF_SPACE;
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
   * @param campaignMap
   *          the campaignMap to set
   */
  public void setCampaignMap(HashMap<Long, CampaignHeadLine> campaignMap) {
    this.campaignMap = campaignMap;
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

  
  /**
   * @return the searchSelected
   */
  public String getSearchSelected() {
    return searchSelected;
  }

  
  /**
   * @param searchSelected the searchSelected to set
   */
  public void setSearchSelected(String searchSelected) {
    this.searchSelected = searchSelected;
  }

  
  /**
   * @return the selectedFlg
   */
  public boolean isSelectedFlg() {
    return selectedFlg;
  }

  
  /**
   * @param selectedFlg the selectedFlg to set
   */
  public void setSelectedFlg(boolean selectedFlg) {
    this.selectedFlg = selectedFlg;
  }

  
  /**
   * @return the fiveMessageFlag
   */
  public boolean isFiveMessageFlag() {
    return fiveMessageFlag;
  }

  
  /**
   * @param fiveMessageFlag the fiveMessageFlag to set
   */
  public void setFiveMessageFlag(boolean fiveMessageFlag) {
    this.fiveMessageFlag = fiveMessageFlag;
  }

}
