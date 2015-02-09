package jp.co.sint.webshop.web.bean.front.mypage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030810:お気に入り商品のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class FavoriteListBean extends UIFrontBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<FavoriteDetail> list = new ArrayList<FavoriteDetail>();

  private PagerValue pagerValue;

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/catalog/sales_recommend");
    addSubJspId("/common/header");
  }
  
  /**
   * U2030810:お気に入り商品のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class FavoriteDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String commodityCode;

    private String skuCode;

    private String commodityName;

    private String shopCode;

    private String shopName;

    private String standardDetail1Name;

    private String standardDetail2Name;

    private String unitPrice;

    private String stockQuantity;

    private String discountPrice;

    private String reservedPrice;

    private String commodityTaxType;

    private String commodityPointRate;

    private String commodityDescription;

    private String reviewScore;

    private String reviewCount;

    private boolean campaignPeriod;

    private boolean discountPeriod;

    private String campaignDiscountRate;

    private String campaignCode;

    private String campaignName;

    private String priceMode;

    private String stockManagementType;

    private Long availableStockQuantity;

    private String outOfStockMessage;

    private String stockLittleMessage;

    private String stockSufficientMessage;

    private Long stockSufficientThreshold;

    private boolean isSale;

    private boolean displayCartButton;
    
    // 2012/12/19 促销对应 ob add start
    private boolean giftFlg;
    // 2012/12/19 促销对应 ob add end
    
    // 20120731 ysy add start
    private String discountRate;
        
    private boolean displayDiscountRate;
    
    private String discountPrices;
    // 20120731 ysy add end

    /** 有効在庫有無フラグ */
    private boolean hasNoStock;

    private String favoriteRegisterDate;
    

    
    /**
     * hasNoStockを取得します。
     * 
     * @return the hasNoStock
     */
    public boolean isHasNoStock() {
      return hasNoStock;
    }

    /**
     * hasNoStockを設定します。
     * 
     * @param hasNoStock
     *          hasNoStock
     */
    public void setHasNoStock(boolean hasNoStock) {
      this.hasNoStock = hasNoStock;
    }

    /**
     * isSaleを取得します。
     * 
     * @return the isSale
     */
    public boolean isSale() {
      return isSale;
    }

    /**
     * isSaleを設定します。
     * 
     * @param sale
     *          isSale
     */
    public void setSale(boolean sale) {
      this.isSale = sale;
    }

    /**
     * availableStockQuantityを取得します。
     * 
     * @return availableStockQuantity
     */
    public Long getAvailableStockQuantity() {
      return availableStockQuantity;
    }

    /**
     * availableStockQuantityを設定します。
     * 
     * @param availableStockQuantity
     *          availableStockQuantity
     */
    public void setAvailableStockQuantity(Long availableStockQuantity) {
      this.availableStockQuantity = availableStockQuantity;
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
     * commodityDescriptionを取得します。
     * 
     * @return commodityDescription
     */
    public String getCommodityDescription() {
      return commodityDescription;
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
     * reservedPriceを取得します。
     * 
     * @return reservedPrice
     */
    public String getReservedPrice() {
      return reservedPrice;
    }

    /**
     * reservedPriceを設定します。
     * 
     * @param reservedPrice
     *          reservedPrice
     */
    public void setReservedPrice(String reservedPrice) {
      this.reservedPrice = reservedPrice;
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
     * commodityNameを取得します。
     * 
     * @return commodityName
     */
    public String getCommodityName() {
      return commodityName;
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
     * skuCodeを取得します。
     * 
     * @return skuCode
     */
    public String getSkuCode() {
      return skuCode;
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
     * standardDetail2Nameを取得します。
     * 
     * @return standardDetail2Name
     */
    public String getStandardDetail2Name() {
      return standardDetail2Name;
    }

    /**
     * stockQuantityを取得します。
     * 
     * @return stockQuantity
     */
    public String getStockQuantity() {
      return stockQuantity;
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
     * commodityNameを設定します。
     * 
     * @param commodityName
     *          commodityName
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
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
     * skuCodeを設定します。
     * 
     * @param skuCode
     *          skuCode
     */
    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
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
     * standardDetail2Nameを設定します。
     * 
     * @param standardDetail2Name
     *          standardDetail2Name
     */
    public void setStandardDetail2Name(String standardDetail2Name) {
      this.standardDetail2Name = standardDetail2Name;
    }

    /**
     * stockQuantityを設定します。
     * 
     * @param stockQuantity
     *          stockQuantity
     */
    public void setStockQuantity(String stockQuantity) {
      this.stockQuantity = stockQuantity;
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
     * commodityCodeを取得します。
     * 
     * @return the commodityCode
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
     * displayCartButtonを取得します。
     * 
     * @return the displayCartButton
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
     * @return the giftFlg
     */
    public boolean isGiftFlg() {
      return giftFlg;
    }

    /**
     * @param giftFlg the giftFlg to set
     */
    public void setGiftFlg(boolean giftFlg) {
      this.giftFlg = giftFlg;
    }

  /**
   * @return the favoriteRegisterDate
   */
  public String getFavoriteRegisterDate() {
    return favoriteRegisterDate;
  }

  /**
   * @param favoriteRegisterDate the favoriteRegisterDate to set
   */
  public void setFavoriteRegisterDate(String favoriteRegisterDate) {
    this.favoriteRegisterDate = favoriteRegisterDate;
  }

  
  /**
   * @return the reviewCount
   */
  public String getReviewCount() {
    return reviewCount;
  }

  
  /**
   * @param reviewCount the reviewCount to set
   */
  public void setReviewCount(String reviewCount) {
    this.reviewCount = reviewCount;
  }

  
  /**
   * @return the discountRate
   */
  public String getDiscountRate() {
    return discountRate;
  }

  
  /**
   * @param discountRate the discountRate to set
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
   * @param discountPrices the discountPrices to set
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
   * @param displayDiscountRate the displayDiscountRate to set
   */
  public void setDisplayDiscountRate(boolean displayDiscountRate) {
    this.displayDiscountRate = displayDiscountRate;
  }

  }

  /**
   * お気に入り一覧を取得します。
   * 
   * @return list
   */
  public List<FavoriteDetail> getList() {
    return list;
  }

  /**
   * お気に入り一覧を設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<FavoriteDetail> list) {
    this.list = list;
  }

//  /**
//   * サブJSPを設定します。
//   */
//  @Override
//  public void setSubJspId() {
//  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2030810";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.FavoriteListBean.0");
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
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString(
    "web.bean.front.mypage.FavoriteListBean.1"), "/app/mypage/mypage"));
    if(!StringUtil.isNullOrEmpty(getClient()) && !this.getClient().equals(ClientType.OTHER)){
    topicPath.add(new NameValue(Messages.getString(
        "web.bean.front.mypage.FavoriteListBean.0"), "/app/mypage/favorite_list/init"));
    }else{
      topicPath.add(new NameValue(Messages.getString(
      "web.bean.front.mypage.FavoriteListBean.0"), "/app/mypage/favorite_list/init"));
    }
    
    return topicPath;
  }
}
