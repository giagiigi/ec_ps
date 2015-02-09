package jp.co.sint.webshop.web.bean.front.mypage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030110:マイページのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class MypageBean extends UIFrontBean {

  private String shopCode;

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<MypageDetailBean> list = new ArrayList<MypageDetailBean>();

  private List<RecommendDetail> rdList = new ArrayList<RecommendDetail>();
  
  private List<RecommendDetail> favList = new ArrayList<RecommendDetail>();

  private List<CouponHistoryListDetail> chldList = new ArrayList<CouponHistoryListDetail>();

  private List<CouponHistoryListDetail> chldFutureList = new ArrayList<CouponHistoryListDetail>();

  private Long listSize;

  private Long chldListSize;

  private String customerCode;

  private String lastName;

  private String firstName;

  private String email;

  private String restPoint;

  private String pointPeriod;

  private String pointExpirationDate;

  private String favoriteSum;

  private String reserveSum;

  private String cancelableSum;

  private String couponSum;
  
  //可用优惠券数量
  private String couponNum;
  
  
  //可用礼品卡  20141017 hdh update start
  private String avaliableGiftCardNum;
  // 20141017 hdh udpate end
  
  private String goodPoint;

  private boolean displayReceivedWithdrawalNotice;

  // 20111214 lirong add start
  // 顾客所在组
  private String customerGroupName;

  // 20111214 lirong add end

  // 20120217 yyq add start
  /** 模式 */
  private String pattern = "mypage";

  // 20120217 yyq add end
  @Length(16)
  @Digit
  @Metadata(name = "受注番号")
  private String orderNo;

  // 20111227 os013 add start
  // 顾客存在评论
  private boolean reviewFlg;

  private String customerGroupMessages;
  
  private String sex;
  
  @Override
  public void setSubJspId() {
      addSubJspId("/common/header");
      addSubJspId("/catalog/topic_path");
      addSubJspId("/catalog/sales_recommend");
  }
  /**
   * 推荐商品Bean
   * 
   * @author lichuang
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

    /**
     * @return the newReserveCommodityType5
     */
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
   * 优惠券
   * 
   * @return
   */
  public static class CouponHistoryListDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 优惠券规则编号 */
    private String couponName;

    /** 优惠券发行类别（0:比例；1:固定金额） */
    private String couponIssueType;

    /** 优惠券比例 */
    private Long couponProportion;

    /** 优惠金额 */
    private String couponAmount;

    /** 优惠券利用开始日时 */
    private String useStartDatetime;

    /** 优惠券利用结束日时 */
    private String useEndDatetime;

    /**
     * @return the couponName
     */
    public String getCouponName() {
      return couponName;
    }

    /**
     * @param couponName
     *          the couponName to set
     */
    public void setCouponName(String couponName) {
      this.couponName = couponName;
    }

    /**
     * @return the couponIssueType
     */
    public String getCouponIssueType() {
      return couponIssueType;
    }

    /**
     * @param couponIssueType
     *          the couponIssueType to set
     */
    public void setCouponIssueType(String couponIssueType) {
      this.couponIssueType = couponIssueType;
    }

    /**
     * @return the couponProportion
     */
    public Long getCouponProportion() {
      return couponProportion;
    }

    /**
     * @param couponProportion
     *          the couponProportion to set
     */
    public void setCouponProportion(Long couponProportion) {
      this.couponProportion = couponProportion;
    }

    /**
     * @return the couponAmount
     */
    public String getCouponAmount() {
      return couponAmount;
    }

    /**
     * @param couponAmount
     *          the couponAmount to set
     */
    public void setCouponAmount(String couponAmount) {
      this.couponAmount = couponAmount;
    }

    /**
     * @return the useStartDatetime
     */
    public String getUseStartDatetime() {
      return useStartDatetime;
    }

    /**
     * @param useStartDatetime
     *          the useStartDatetime to set
     */
    public void setUseStartDatetime(String useStartDatetime) {
      this.useStartDatetime = useStartDatetime;
    }

    /**
     * @return the useEndDatetime
     */
    public String getUseEndDatetime() {
      return useEndDatetime;
    }

    /**
     * @param useEndDatetime
     *          the useEndDatetime to set
     */
    public void setUseEndDatetime(String useEndDatetime) {
      this.useEndDatetime = useEndDatetime;
    }

  }

  public boolean isReviewFlg() {
    return reviewFlg;
  }

  public void setReviewFlg(boolean reviewFlg) {
    this.reviewFlg = reviewFlg;
  }

  // 20111227 os013 add end
  /**
   * U2030110:マイページのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class MypageDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String orderDatetime;

    private String orderNo;

    // 20111216 lirong add start
    private List<CommodityAndReviewBean> commodityAndReviewBeanList = new ArrayList<CommodityAndReviewBean>();

    // 20111216 lirong add end
    private List<String> commodityName = new ArrayList<String>();

    private List<String> shopCode = new ArrayList<String>();

    private List<String> commodityCode = new ArrayList<String>();

    private List<String> standardDetail1Name = new ArrayList<String>();

    private List<String> standardDetail2Name = new ArrayList<String>();

    private List<String> salePrice = new ArrayList<String>();

    private String shippingStatus;

    private String totalAmount;
    
    private String addressLastName;

    private boolean isReserve;

    /** 配送单号 */
    private String slipNo;

    private boolean hasReturn;

    private String paymentDate;

    private Object paymentFormObject;

    // 银联支付信息表示Flg
    private boolean displayChinapayInfo = false;

    // 支付宝支付信息表示Flg
    private boolean displayAlipayInfo = false;

    /** 支払方法名称 */
    private String paymentMethodName;

    private boolean cancelStatus;

    // M17N 10361 追加 ここから
    // alipayと銀聯支払区分
    private boolean paymentFlg;

    // M17N 10361 追加 ここまで

    // 20111216 lirong add start
    private Date updatedDatetime;

    public Object getPaymentFormObject() {
      return paymentFormObject;
    }

    public void setPaymentFormObject(Object paymentFormObject) {
      this.paymentFormObject = paymentFormObject;
    }

    public boolean isDisplayChinapayInfo() {
      return displayChinapayInfo;
    }

    public void setDisplayChinapayInfo(boolean displayChinapayInfo) {
      this.displayChinapayInfo = displayChinapayInfo;
    }

    public boolean isDisplayAlipayInfo() {
      return displayAlipayInfo;
    }

    public void setDisplayAlipayInfo(boolean displayAlipayInfo) {
      this.displayAlipayInfo = displayAlipayInfo;
    }

    /**
     * @return the updatedDatetime
     */
    public Date getUpdatedDatetime() {
      return updatedDatetime;
    }

    /**
     * @param updatedDatetime
     *          the updatedDatetime to set
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = updatedDatetime;
    }

    /**
     * 获得 commodityAndReviewBeanList
     * 
     * @return commodityAndReviewBeanList
     */

    public List<CommodityAndReviewBean> getCommodityAndReviewBeanList() {
      return commodityAndReviewBeanList;
    }

    /**
     * 设定 commodityAndReviewBeanList
     * 
     * @param commodityAndReviewBeanList
     */
    public void setCommodityAndReviewBeanList(List<CommodityAndReviewBean> commodityAndReviewBeanList) {
      this.commodityAndReviewBeanList = commodityAndReviewBeanList;
    }

    // 20111216 lirong add end
    /**
     * isReserveを返します。
     * 
     * @return the isReserve
     */
    public boolean isReserve() {
      return isReserve;
    }

    /**
     * isReserveを設定します。
     * 
     * @param reserve
     *          設定する reserve
     */
    public void setReserve(boolean reserve) {
      this.isReserve = reserve;
    }

    public String getSlipNo() {
      return slipNo;
    }

    public void setSlipNo(String slipNo) {
      this.slipNo = slipNo;
    }

    /**
     * hasReturnを返します。
     * 
     * @return the hasReturn
     */
    public boolean isHasReturn() {
      return hasReturn;
    }

    /**
     * hasReturnを設定します。
     * 
     * @param hasReturn
     *          設定する hasReturn
     */
    public void setHasReturn(boolean hasReturn) {
      this.hasReturn = hasReturn;
    }

    /**
     * commodityNameを取得します。
     * 
     * @return commodityName
     */
    public List<String> getCommodityName() {
      return commodityName;
    }

    /**
     * orderDatetimeを取得します。
     * 
     * @return orderDatetime
     */
    public String getOrderDatetime() {
      return orderDatetime;
    }

    /**
     * orderDatetimeの日付部分のみを取得します。
     * 
     * @return orderDate
     */
    public String getOrderDate() {
      String orderDate = getOrderDatetime();
      if (StringUtil.isNullOrEmpty(orderDate)) {
        return "";
      }
      orderDate = orderDate.substring(0, 10);
      return orderDate;
    }

    /**
     * orderNoを取得します。
     * 
     * @return orderNo
     */
    public String getOrderNo() {
      return orderNo;
    }

    /**
     * standardDetail1Nameを取得します。
     * 
     * @return standardDetail1Name
     */
    public List<String> getStandardDetail1Name() {
      return standardDetail1Name;
    }

    /**
     * standardDetail2Nameを取得します。
     * 
     * @return standardDetail2Name
     */
    public List<String> getStandardDetail2Name() {
      return standardDetail2Name;
    }

    /**
     * totalAmountを取得します。
     * 
     * @return totalAmount
     */
    public String getTotalAmount() {
      return totalAmount;
    }

    /**
     * orderDatetimeを設定します。
     * 
     * @param orderDatetime
     *          orderDatetime
     */
    public void setOrderDatetime(String orderDatetime) {
      this.orderDatetime = orderDatetime;
    }

    /**
     * orderNoを設定します。
     * 
     * @param orderNo
     *          orderNo
     */
    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

    /**
     * commodityNameを設定します。
     * 
     * @param commodityName
     *          commodityName
     */
    public void setCommodityName(List<String> commodityName) {
      this.commodityName = commodityName;
    }

    /**
     * standardDetail1Nameを設定します。
     * 
     * @param standardDetail1Name
     *          standardDetail1Name
     */
    public void setStandardDetail1Name(List<String> standardDetail1Name) {
      this.standardDetail1Name = standardDetail1Name;
    }

    /**
     * standardDetail2Nameを設定します。
     * 
     * @param standardDetail2Name
     *          standardDetail2Name
     */
    public void setStandardDetail2Name(List<String> standardDetail2Name) {
      this.standardDetail2Name = standardDetail2Name;
    }

    /**
     * totalAmountを設定します。
     * 
     * @param totalAmount
     *          totalAmount
     */
    public void setTotalAmount(String totalAmount) {
      this.totalAmount = totalAmount;
    }

    /**
     * salePriceを取得します。
     * 
     * @return salePrice
     */
    public List<String> getSalePrice() {
      return salePrice;
    }

    /**
     * salePriceを設定します。
     * 
     * @param salePrice
     *          salePrice
     */
    public void setSalePrice(List<String> salePrice) {
      this.salePrice = salePrice;
    }

    /**
     * shippingStatusを取得します。
     * 
     * @return shippingStatus
     */
    public String getShippingStatus() {
      return shippingStatus;
    }

    /**
     * shippingStatusを設定します。
     * 
     * @param shippingStatus
     *          shippingStatus
     */
    public void setShippingStatus(String shippingStatus) {
      this.shippingStatus = shippingStatus;
    }

    public boolean isPaymentFlg() {
      return paymentFlg;
    }

    public void setPaymentFlg(boolean paymentFlg) {
      this.paymentFlg = paymentFlg;
    }

    /**
     * @return the shopCode
     */
    public List<String> getShopCode() {
      return shopCode;
    }

    /**
     * @param shopCode
     *          the shopCode to set
     */
    public void setShopCode(List<String> shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * @return the commodityCode
     */
    public List<String> getCommodityCode() {
      return commodityCode;
    }

    /**
     * @param commodityCode
     *          the commodityCode to set
     */
    public void setCommodityCode(List<String> commodityCode) {
      this.commodityCode = commodityCode;
    }

    /**
     * @return the paymentDate
     */
    public String getPaymentDate() {
      return paymentDate;
    }

    /**
     * @param paymentDate
     *          the paymentDate to set
     */
    public void setPaymentDate(String paymentDate) {
      this.paymentDate = paymentDate;
    }

    /**
     * @return the paymentMethodName
     */
    public String getPaymentMethodName() {
      return paymentMethodName;
    }

    /**
     * @param paymentMethodName
     *          the paymentMethodName to set
     */
    public void setPaymentMethodName(String paymentMethodName) {
      this.paymentMethodName = paymentMethodName;
    }

    /**
     * @return the cancelStatus
     */
    public boolean isCancelStatus() {
      return cancelStatus;
    }

    /**
     * @param cancelStatus
     *          the cancelStatus to set
     */
    public void setCancelStatus(boolean cancelStatus) {
      this.cancelStatus = cancelStatus;
    }

    
    /**
     * @return the addressLastName
     */
    public String getAddressLastName() {
      return addressLastName;
    }

    
    /**
     * @param addressLastName the addressLastName to set
     */
    public void setAddressLastName(String addressLastName) {
      this.addressLastName = addressLastName;
    }

  }

  /**
   * cancelableSumを取得します。
   * 
   * @return cancelableSum
   */
  public String getCancelableSum() {
    return cancelableSum;
  }

  /**
   * cancelableSumを設定します。
   * 
   * @param cancelableSum
   *          cancelableSum
   */
  public void setCancelableSum(String cancelableSum) {
    this.cancelableSum = cancelableSum;
  }

  /**
   * reserveSumを取得します。
   * 
   * @return reserveSum
   */
  public String getReserveSum() {
    return reserveSum;
  }

  /**
   * reserveSumを設定します。
   * 
   * @param reserveSum
   *          reserveSum
   */
  public void setReserveSum(String reserveSum) {
    this.reserveSum = reserveSum;
  }

  /**
   * customerCodeを取得します。
   * 
   * @return customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * customerCodeを設定します。
   * 
   * @param customerCode
   *          customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * emailを取得します。
   * 
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * favoriteSumを取得します。
   * 
   * @return favoriteSum
   */
  public String getFavoriteSum() {
    return favoriteSum;
  }

  /**
   * firstNameを取得します。
   * 
   * @return firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * lastNameを取得します。
   * 
   * @return lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * マイページ一覧を取得します。
   * 
   * @return list
   */
  public List<MypageDetailBean> getList() {
    return list;
  }

  /**
   * pointExpirationDateを取得します。
   * 
   * @return pointExpirationDate
   */
  public String getPointExpirationDate() {
    return pointExpirationDate;
  }

  /**
   * restPointを取得します。
   * 
   * @return restPoint
   */
  public String getRestPoint() {
    return restPoint;
  }

  /**
   * pointPeriodを取得します。
   * 
   * @return pointPeriod
   */
  public String getPointPeriod() {
    return pointPeriod;
  }

  /**
   * emailを設定します。
   * 
   * @param email
   *          email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * favoriteSumを設定します。
   * 
   * @param favoriteSum
   *          favoriteSum
   */
  public void setFavoriteSum(String favoriteSum) {
    this.favoriteSum = favoriteSum;
  }

  /**
   * firstNameを設定します。
   * 
   * @param firstName
   *          firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * lastNameを設定します。
   * 
   * @param lastName
   *          lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * マイページ一覧を設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<MypageDetailBean> list) {
    this.list = list;
  }

  /**
   * pointExpirationDateを設定します。
   * 
   * @param pointExpirationDate
   *          pointExpirationDate
   */
  public void setPointExpirationDate(String pointExpirationDate) {
    this.pointExpirationDate = pointExpirationDate;
  }

  /**
   * restPointを設定します。
   * 
   * @param restPoint
   *          restPoint
   */
  public void setRestPoint(String restPoint) {
    this.restPoint = restPoint;
  }

  /**
   * pointPeriodを設定します。
   * 
   * @param pointPeriod
   *          pointPeriod
   */
  public void setPointPeriod(String pointPeriod) {
    this.pointPeriod = pointPeriod;
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
    return "U2030110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.MypageBean.0");
  }

  /**
   * listSizeを取得します。
   * 
   * @return listSize listSize
   */
  public Long getListSize() {
    return listSize;
  }

  /**
   * listSizeを設定します。
   * 
   * @param listSize
   *          listSize
   */
  public void setListSize(Long listSize) {
    this.listSize = listSize;
  }

  /**
   * orderNoを取得します。
   * 
   * @return orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * orderNoを設定します。
   * 
   * @param orderNo
   *          orderNo
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.OrderDetailBean.1"), "/app/mypage/mypage"));
    return topicPath;
  }

  /**
   * displayReceivedWithdrawalNoticeを返します。
   * 
   * @return the displayReceivedWithdrawalNotice
   */
  public boolean isDisplayReceivedWithdrawalNotice() {
    return displayReceivedWithdrawalNotice;
  }

  /**
   * displayReceivedWithdrawalNoticeを設定します。
   * 
   * @param displayReceivedWithdrawalNotice
   *          設定する displayReceivedWithdrawalNotice
   */
  public void setDisplayReceivedWithdrawalNotice(boolean displayReceivedWithdrawalNotice) {
    this.displayReceivedWithdrawalNotice = displayReceivedWithdrawalNotice;
  }

  public String getCouponSum() {
    return couponSum;
  }

  public void setCouponSum(String couponSum) {
    this.couponSum = couponSum;
  }

  // 20111214 lirong add start
  /**
   * customerGroupNameを取得します。
   * 
   * @return customerGroupName
   */
  public String getCustomerGroupName() {
    return customerGroupName;
  }

  /**
   * customerGroupNameを設定します。
   * 
   * @param customerGroupName
   *          customerGroupName
   */
  public void setCustomerGroupName(String customerGroupName) {
    this.customerGroupName = customerGroupName;
  }

  // 20111214 lirong add end

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  // 20111216 lirong add start
  /**
   * マイページのサブモデルです。 商品评论条件
   */
  public static class CommodityAndReviewBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // 商品名称
    private String commodityName;

    //
    private boolean displayReviewButtonFlg;

    // 商品code
    private String commodityCode;

    // 店铺code
    private String shopCode;

    // 商品数量
    private Long purchasingAmount;

    // 规格1
    private String standardDetail1Name;

    // 规格2
    private String standardDetail2Name;

    // skuCode
    private String skuCode;

    private boolean isSale;

    private boolean displayCartButton;

    // 2012/12/19 促销对应 ob add start
    private boolean commoditySet;

    private boolean giftFlg;

    // 2012/12/19 促销对应 ob add end

    public boolean isSale() {
      return isSale;
    }

    public void setSale(boolean isSale) {
      this.isSale = isSale;
    }

    public boolean isDisplayCartButton() {
      return displayCartButton;
    }

    public void setDisplayCartButton(boolean displayCartButton) {
      this.displayCartButton = displayCartButton;
    }

    public String getSkuCode() {
      return skuCode;
    }

    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    public String getStandardDetail1Name() {
      return standardDetail1Name;
    }

    public void setStandardDetail1Name(String standardDetail1Name) {
      this.standardDetail1Name = standardDetail1Name;
    }

    public String getStandardDetail2Name() {
      return standardDetail2Name;
    }

    public void setStandardDetail2Name(String standardDetail2Name) {
      this.standardDetail2Name = standardDetail2Name;
    }

    public Long getPurchasingAmount() {
      return purchasingAmount;
    }

    public void setPurchasingAmount(Long purchasingAmount) {
      this.purchasingAmount = purchasingAmount;
    }

    public String getShopCode() {
      return shopCode;
    }

    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * 获得 commodityName
     * 
     * @return commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    /**
     * 设定 commodityName
     * 
     * @param commodityName
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    /**
     * 获得 displayReviewButtonFlg
     * 
     * @return displayReviewButtonFlg
     */
    public boolean isDisplayReviewButtonFlg() {
      return displayReviewButtonFlg;
    }

    /**
     * 设定 displayReviewButtonFlg
     * 
     * @param displayReviewButtonFlg
     */
    public void setDisplayReviewButtonFlg(boolean displayReviewButtonFlg) {
      this.displayReviewButtonFlg = displayReviewButtonFlg;
    }

    /**
     * 获得 commodityCode
     * 
     * @return commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
    }

    /**
     * 设定 commodityCode
     * 
     * @param commodityCode
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
    }

    /**
     * @return the commoditySet
     */
    public boolean isCommoditySet() {
      return commoditySet;
    }

    /**
     * @param commoditySet
     *          the commoditySet to set
     */
    public void setCommoditySet(boolean commoditySet) {
      this.commoditySet = commoditySet;
    }

    /**
     * @return the giftFlg
     */
    public boolean isGiftFlg() {
      return giftFlg;
    }

    /**
     * @param giftFlg
     *          the giftFlg to set
     */
    public void setGiftFlg(boolean giftFlg) {
      this.giftFlg = giftFlg;
    }
  }

  // 20111216 lirong add end

  /**
   * @return the customerGroupMessages
   */
  public String getCustomerGroupMessages() {
    return customerGroupMessages;
  }

  /**
   * @param customerGroupMessages
   *          the customerGroupMessages to set
   */
  public void setCustomerGroupMessages(String customerGroupMessages) {
    this.customerGroupMessages = customerGroupMessages;
  }

  public List<RecommendDetail> getRdList() {
    return rdList;
  }

  public void setRdList(List<RecommendDetail> rdList) {
    this.rdList = rdList;
  }

  /**
   * @return the chldList
   */
  public List<CouponHistoryListDetail> getChldList() {
    return chldList;
  }

  /**
   * @param chldList
   *          the chldList to set
   */
  public void setChldList(List<CouponHistoryListDetail> chldList) {
    this.chldList = chldList;
  }

  /**
   * @return the chldListSize
   */
  public Long getChldListSize() {
    return chldListSize;
  }

  /**
   * @param chldListSize
   *          the chldListSize to set
   */
  public void setChldListSize(Long chldListSize) {
    this.chldListSize = chldListSize;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  /**
   * @return the chldFutureList
   */
  public List<CouponHistoryListDetail> getChldFutureList() {
    return chldFutureList;
  }

  /**
   * @param chldFutureList
   *          the chldFutureList to set
   */
  public void setChldFutureList(List<CouponHistoryListDetail> chldFutureList) {
    this.chldFutureList = chldFutureList;
  }

  
  /**
   * @return the couponNum
   */
  public String getCouponNum() {
    return couponNum;
  }

  
  /**
   * @param couponNum the couponNum to set
   */
  public void setCouponNum(String couponNum) {
    this.couponNum = couponNum;
  }
  
  /**
   * @return the goodPoint
   */
  public String getGoodPoint() {
    return goodPoint;
  }

  
  /**
   * @param goodPoint the goodPoint to set
   */
  public void setGoodPoint(String goodPoint) {
    this.goodPoint = goodPoint;
  }

  
  /**
   * @return the favList
   */
  public List<RecommendDetail> getFavList() {
    return favList;
  }

  
  /**
   * @param favList the favList to set
   */
  public void setFavList(List<RecommendDetail> favList) {
    this.favList = favList;
  }

  
  /**
   * @return the sex
   */
  public String getSex() {
    return sex;
  }

  
  /**
   * @param sex the sex to set
   */
  public void setSex(String sex) {
    this.sex = sex;
  }

  
  /**
   * @return the avaliableGiftCardNum
   */
  public String getAvaliableGiftCardNum() {
    return avaliableGiftCardNum;
  }

  
  /**
   * @param avaliableGiftCardNum the avaliableGiftCardNum to set
   */
  public void setAvaliableGiftCardNum(String avaliableGiftCardNum) {
    this.avaliableGiftCardNum = avaliableGiftCardNum;
  }

}
