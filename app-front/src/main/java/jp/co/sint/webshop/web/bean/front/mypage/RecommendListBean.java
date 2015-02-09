package jp.co.sint.webshop.web.bean.front.mypage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030820:おすすめ商品のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class RecommendListBean extends UIFrontBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue;

  private List<RecommendDetail> list = new ArrayList<RecommendDetail>();

  /** 在庫状況表示文言(在庫あり) */
  public static final String STOCK_TRUE = Messages.getString("web.bean.front.mypage.RecommendListBean.0");  //$NON-NLS-1$

  /** 在庫状況表示文言(在庫なし) */
  public static final String STOCK_FALSE = Messages.getString("web.bean.front.mypage.RecommendListBean.1");  //$NON-NLS-1$

  /**
   * U2030820:おすすめ商品のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RecommendDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String commodityCode;

    private String commodityName;

    private String skuCode;

    private String shopCode;

    private String shopName;

    private String unitPrice;

    private String stockQuantity;

    private String saleStartDate;

    private String discountPrice;

    private String reservedPrice;

    private String commodityTaxType;

    private String commodityPointRate;

    private String commodityDescription;
    
    // 20120116 ysy add start
    private String commodityDescriptionShort;
    // 20120116 ysy add end

    private String reviewScore;

    private boolean campaignPeriod;

    private boolean pointPeriod;

    private boolean discountPeriod;

    private String campaignDiscountRate;

    private String campaignCode;

    private String campaignName;

    private String priceMode;

    private String stockStatus;

    /**
	 * @return the commodityDescriptionShort
	 */
	public String getCommodityDescriptionShort() {
		return commodityDescriptionShort;
	}

	/**
	 * @param commodityDescriptionShort the commodityDescriptionShort to set
	 */
	public void setCommodityDescriptionShort(String commodityDescriptionShort) {
		this.commodityDescriptionShort = commodityDescriptionShort;
	}

	/**
     * stockStatusを取得します。
     * 
     * @return the stockStatus
     */
    public String getStockStatus() {
      return stockStatus;
    }

    /**
     * stockStatusを設定します。
     * 
     * @param stockStatus
     *          stockStatus
     */
    public void setStockStatus(String stockStatus) {
      this.stockStatus = stockStatus;
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
     * commodityCodeを取得します。
     * 
     * @return commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
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
     * saleStartDateを取得します。
     * 
     * @return saleStartDate
     */
    public String getSaleStartDate() {
      return saleStartDate;
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
     * commodityCodeを設定します。
     * 
     * @param commodityCode
     *          commodityCode
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
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
     * saleStartDateを設定します。
     * 
     * @param saleStartDate
     *          saleStartDate
     */
    public void setSaleStartDate(String saleStartDate) {
      this.saleStartDate = saleStartDate;
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
     * reservePriceを取得します。
     * 
     * @return reservePrice
     */
    public String getReservedPrice() {
      return reservedPrice;
    }

    /**
     * reservePriceを設定します。
     * 
     * @param reservePrice
     *          reservePrice
     */
    public void setReservedPrice(String reservePrice) {
      this.reservedPrice = reservePrice;
    }

    /**
     * pointPeriodを取得します。
     * 
     * @return pointPeriod
     */
    public boolean isPointPeriod() {
      return pointPeriod;
    }

    /**
     * pointPeriodを設定します。
     * 
     * @param pointPeriod
     *          pointPeriod
     */
    public void setPointPeriod(boolean pointPeriod) {
      this.pointPeriod = pointPeriod;
    }

  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<RecommendDetail> getList() {
    return list;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<RecommendDetail> list) {
    this.list = list;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

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
    return "U2030820";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.RecommendListBean.3");
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
    topicPath.add(new NameValue(
        Messages.getString("web.bean.front.mypage.RecommendListBean.2"), "/app/mypage/mypage"));
    topicPath.add(new NameValue(
        Messages.getString("web.bean.front.mypage.RecommendListBean.3"),
        "/app/mypage/recommend_list/init"));
    return topicPath;
  }
}
