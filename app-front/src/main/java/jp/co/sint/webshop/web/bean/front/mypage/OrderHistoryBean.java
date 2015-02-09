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
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030610:注文履歴のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class OrderHistoryBean extends UIFrontBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<OrdersHistoryDetail> list = new ArrayList<OrdersHistoryDetail>();

  private PagerValue pagerValue;

  private Date updatedDatetime;

  /** 模式 */
  private String pattern = "order_history";

  /** ソート条件 */
  public static final String ORDER_DATETIME_DESC = "orderDatetime";

  private String selectOrderStatusValue;

  @Length(16)
  @Digit
  @Metadata(name = "受注番号")
  private String orderNo;

  //近期订单,全部订单
  private String searchOrderDateType;
  
  

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/catalog/sales_recommend");
    addSubJspId("/common/header");
    
  }
  
  /**
   * U2030610:注文履歴のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class OrdersHistoryDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String orderNo;

    private String orderDatetime;

    private String totalPrice;

    private List<OrdersHistoryCommodityDetail> commodityList = new ArrayList<OrdersHistoryCommodityDetail>();

    // 20111220 os013 add start
    private List<CommodityAndReviewBean> commodityAndReviewBeanList = new ArrayList<CommodityAndReviewBean>();

    private String shopCode;

    // 20111220 os013 add end

    private String orderStatus;

    private String shippingStatus;

    private String paymentDate;

    // add by lc 2012-02-01 start
    private String paymentMethodName;

    // add by lc 2012-02-01 end
    private boolean cancelStatus;

    private Date updatedDatetime;

    private boolean isReserve;

    private boolean hasReturn;

    private Object paymentFormObject;

    // 银联支付信息表示Flg
    private boolean displayChinapayInfo = false;

    // 支付宝支付信息表示Flg
    private boolean displayAlipayInfo = false;

    // M17N 10361 追加 ここから
    // alipayと銀聯支払区分
    private boolean paymentFlg;

    // M17N 10361 追加 ここまで

    // 20111220 os013 add start
    /**
     * 获得 commodityAndReviewBeanList
     * 
     * @return commodityAndReviewBeanList
     */

    public List<CommodityAndReviewBean> getCommodityAndReviewBeanList() {
      return commodityAndReviewBeanList;
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

    public Object getPaymentFormObject() {
      return paymentFormObject;
    }

    public void setPaymentFormObject(Object paymentFormObject) {
      this.paymentFormObject = paymentFormObject;
    }

    /**
     * 设定 commodityAndReviewBeanList
     * 
     * @param commodityAndReviewBeanList
     */
    public void setCommodityAndReviewBeanList(List<CommodityAndReviewBean> commodityAndReviewBeanList) {
      this.commodityAndReviewBeanList = commodityAndReviewBeanList;
    }

    public String getShopCode() {
      return shopCode;
    }

    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    // 20111220 os013 add end
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
     * orderDatetimeを取得します。
     * 
     * @return orderDatetime
     */
    public String getOrderDatetime() {
      return orderDatetime;
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
     * orderStatusを取得します。
     * 
     * @return orderStatus
     */
    public String getOrderStatus() {
      return orderStatus;
    }

    /**
     * totalPriceを取得します。
     * 
     * @return totalPrice
     */
    public String getTotalPrice() {
      return totalPrice;
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
     * orderStatusを設定します。
     * 
     * @param orderStatus
     *          orderStatus
     */
    public void setOrderStatus(String orderStatus) {
      this.orderStatus = orderStatus;
    }

    /**
     * totalPriceを設定します。
     * 
     * @param totalPrice
     *          totalPrice
     */
    public void setTotalPrice(String totalPrice) {
      this.totalPrice = totalPrice;
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

    /**
     * paymentDateを返します。
     * 
     * @return the paymentDate
     */
    public String getPaymentDate() {
      return paymentDate;
    }

    /**
     * paymentDateを設定します。
     * 
     * @param paymentDate
     *          設定する paymentDate
     */
    public void setPaymentDate(String paymentDate) {
      this.paymentDate = paymentDate;
    }

    /**
     * cancelStatusを取得します。
     * 
     * @return cancelStatus
     */
    public boolean isCancelStatus() {
      return cancelStatus;
    }

    /**
     * cancelStatusを設定します。
     * 
     * @param cancelStatus
     *          cancelStatus
     */
    public void setCancelStatus(boolean cancelStatus) {
      this.cancelStatus = cancelStatus;
    }

    /**
     * commodityListを取得します。
     * 
     * @return commodityList
     */
    public List<OrdersHistoryCommodityDetail> getCommodityList() {
      return commodityList;
    }

    /**
     * commodityListを設定します。
     * 
     * @param commodityList
     *          commodityList
     */
    public void setCommodityList(List<OrdersHistoryCommodityDetail> commodityList) {
      this.commodityList = commodityList;
    }

    /**
     * updatedDatetimeを取得します。
     * 
     * @return updatedDatetime
     */
    public Date getUpdatedDatetime() {
      return DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * updatedDatetimeを設定します。
     * 
     * @param updatedDatetime
     *          updatedDatetime
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
    }

    public boolean isPaymentFlg() {
      return paymentFlg;
    }

    public void setPaymentFlg(boolean paymentFlg) {
      this.paymentFlg = paymentFlg;
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

  }

  /**
   * U2030610:注文履歴のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class OrdersHistoryCommodityDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String commodityName;

    private String commodityCode;

    private String shopCode;

    private String standardDetail1Name;

    private String standardDetail2Name;

    private String salePrice;

    private String commodityAmount;

    // 商品评论标志
    private boolean displayReviewButtonFlg;

    // sku编号
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

    public boolean isDisplayReviewButtonFlg() {
      return displayReviewButtonFlg;
    }

    public void setDisplayReviewButtonFlg(boolean displayReviewButtonFlg) {
      this.displayReviewButtonFlg = displayReviewButtonFlg;
    }

    /**
     * @return the commoditySet
     */
    public boolean isCommoditySet() {
      return commoditySet;
    }

    /**
     * @param commoditySet the commoditySet to set
     */
    public void setCommoditySet(boolean commoditySet) {
      this.commoditySet = commoditySet;
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
     * salePriceを取得します。
     * 
     * @return salePriceを取得します。
     */
    public String getSalePrice() {
      return salePrice;
    }

    /**
     * salePriceを設定します。
     * 
     * @param salePrice
     *          salePrice
     */
    public void setSalePrice(String salePrice) {
      this.salePrice = salePrice;
    }

    /**
     * commodityAmountを取得します。
     * 
     * @return commodityAmount
     */
    public String getCommodityAmount() {
      return commodityAmount;
    }

    /**
     * commodityAmountを設定します。
     * 
     * @param commodityAmount
     *          commodityAmount
     */
    public void setCommodityAmount(String commodityAmount) {
      this.commodityAmount = commodityAmount;
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

  }

  /**
   * orderNoを取得します。
   * 
   * @return the orderNo
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
   * 受注履歴を取得します。
   * 
   * @return list
   */
  public List<OrdersHistoryDetail> getList() {
    return list;
  }

  /**
   * 受注履歴を設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<OrdersHistoryDetail> list) {
    this.list = list;
  }

  // /**
  // * サブJSPを設定します。
  // */
  // @Override
  // public void setSubJspId() {
  // }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2030610";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.OrderHistoryBean.0");
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
   * updatedDatetimeを取得します。
   * 
   * @return updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * updatedDatetimeを設定します。
   * 
   * @param updatedDatetime
   *          updatedDatetime
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * selectOrderStatusValueを取得します。
   * 
   * @return the selectOrderStatusValue
   */
  public String getSelectOrderStatusValue() {
    return selectOrderStatusValue;
  }

  /**
   * selectOrderStatusValueを設定します。
   * 
   * @param selectOrderStatusValue
   *          selectOrderStatusValue
   */
  public void setSelectOrderStatusValue(String selectOrderStatusValue) {
    this.selectOrderStatusValue = selectOrderStatusValue;
  }

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString(
    "web.bean.front.mypage.OrderHistoryBean.1"), "/app/mypage/mypage")); //$NON-NLS-1$ //$NON-NLS-2$
    if(!StringUtil.isNullOrEmpty(getClient()) && !getClient().equals(ClientType.OTHER)){
      
      topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.OrderHistoryBean.0"), "/app/mypage/order_history/init")); //$NON-NLS-1$ //$NON-NLS-2$
    }else{
      topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.OrderHistoryBean.0"), "/app/mypage/order_history/init")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    return topicPath;
  }

  // 20111220 os013 add start
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

    // 20120113 ysy add start
    // sku编号
    private String skuCode;

    // 20120113 ysy add end

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
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  
  /**
   * @return the searchOrderDateType
   */
  public String getSearchOrderDateType() {
    return searchOrderDateType;
  }

  
  /**
   * @param searchOrderDateType the searchOrderDateType to set
   */
  public void setSearchOrderDateType(String searchOrderDateType) {
    this.searchOrderDateType = searchOrderDateType;
  }

  // 20111220 os013 add end

}
