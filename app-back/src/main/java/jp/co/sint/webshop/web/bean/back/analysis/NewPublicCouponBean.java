package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.MobileComputerType;
import jp.co.sint.webshop.data.domain.OrderClientType;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1070820:ショップ別売上集計のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class NewPublicCouponBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 日別集計 */
  public static final String DISPLAY_MODE_DAY = "day";

  /** 月別集計 */
  public static final String DISPLAY_MODE_MONTH = "month";

  @Datetime
  @Metadata(name = "集計期間(From)", order = 1)
  private String searchStartDate;

  private String searchOrderDate;

  private String searchDiscountCode;

  @Datetime
  @Metadata(name = "集計期間(To)", order = 2)
  private String searchEndDate;

  @Digit
  @Length(4)
  @Metadata(name = "集計対象年", order = 1)
  private String searchStartYear;

  @Digit
  @Length(4)
  @Metadata(name = "集計対象年", order = 1)
  private String searchEndYear;

  @Digit
  @Length(2)
  @Metadata(name = "集計対象月", order = 2)
  private String searchStartMonth;

  @Digit
  @Length(2)
  @Metadata(name = "集計対象月", order = 2)
  private String searchEndMonth;

  @Digit
  @Length(2)
  @Metadata(name = "クライアント", order = 3)
  private String clientGroupCondition;

  /** ショップコード */
  @AlphaNum2
  @Length(16)
  @Metadata(name = "ショップコード", order = 5)
  private String shopCode;

  /** ショップ名 */
  @Length(50)
  @Metadata(name = "ショップ名", order = 6)
  private String shopName;
  
  @Length(1)
  @Metadata(name = "制作订单设备类型", order = 7)
  private String orderClientType;

  /** CSV出力権限有無 true:CSV出力ボタン表示 false:CSV出力ボタン非表示 */
  private boolean exportAuthority = false;

  /** 検索結果表示有無 true:CSV出力ボタン表示 false:CSV出力ボタン非表示 */
  private boolean searchResultDisplay = false;

  /** 表示モード day:日別集計 month:月別集計 */
  private String displayMode;

  private String selectMode;

  private String mobileComputerType;
  
  public String getMobileComputerType() {
    return mobileComputerType;
  }

  public void setMobileComputerType(String mobileComputerType) {
    this.mobileComputerType = mobileComputerType;
  }

  private List<NewPublicCouponBeanDetail> searchResult = new ArrayList<NewPublicCouponBeanDetail>();

  /**
   * U1070820:ショップ別売上集計のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class NewPublicCouponBeanDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 集計日 */
    private String orderDate;

    /** 优惠代码 */
    private String discountCode;

    /** 注文件数累計 */
    private String totalCall;

    /** 全部退货订单数 */
    private String returnCall;

    /** 中文订单数 */
    private String zhCall;

    /** 日文订单数 */
    private String jpCall;

    /** 英文订单数 */
    private String enCall;

    /** 折扣金额 */
    private String discountPrice;

    /** 商品除折扣金额 */
    private String paidPrice;

    /** 运费 */
    private String shippingCharge;

    /**
     * @return the orderDate
     */
    public String getOrderDate() {
      return orderDate;
    }

    /**
     * @param orderDate
     *          the orderDate to set
     */
    public void setOrderDate(String orderDate) {
      this.orderDate = orderDate;
    }

    /**
     * @return the discountCode
     */
    public String getDiscountCode() {
      return discountCode;
    }

    /**
     * @param discountCode
     *          the discountCode to set
     */
    public void setDiscountCode(String discountCode) {
      this.discountCode = discountCode;
    }

    /**
     * @return the totalCall
     */
    public String getTotalCall() {
      return totalCall;
    }

    /**
     * @param totalCall
     *          the totalCall to set
     */
    public void setTotalCall(String totalCall) {
      this.totalCall = totalCall;
    }

    /**
     * @return the zhCall
     */
    public String getZhCall() {
      return zhCall;
    }

    /**
     * @param zhCall
     *          the zhCall to set
     */
    public void setZhCall(String zhCall) {
      this.zhCall = zhCall;
    }

    /**
     * @return the jpCall
     */
    public String getJpCall() {
      return jpCall;
    }

    /**
     * @param jpCall
     *          the jpCall to set
     */
    public void setJpCall(String jpCall) {
      this.jpCall = jpCall;
    }

    /**
     * @return the enCall
     */
    public String getEnCall() {
      return enCall;
    }

    /**
     * @param enCall
     *          the enCall to set
     */
    public void setEnCall(String enCall) {
      this.enCall = enCall;
    }

    /**
     * @return the discountPrice
     */
    public String getDiscountPrice() {
      return discountPrice;
    }

    /**
     * @param discountPrice
     *          the discountPrice to set
     */
    public void setDiscountPrice(String discountPrice) {
      this.discountPrice = discountPrice;
    }

    /**
     * @return the paidPrice
     */
    public String getPaidPrice() {
      return paidPrice;
    }

    /**
     * @param paidPrice
     *          the paidPrice to set
     */
    public void setPaidPrice(String paidPrice) {
      this.paidPrice = paidPrice;
    }

    /**
     * @return the shippingCharge
     */
    public String getShippingCharge() {
      return shippingCharge;
    }

    /**
     * @param shippingCharge
     *          the shippingCharge to set
     */
    public void setShippingCharge(String shippingCharge) {
      this.shippingCharge = shippingCharge;
    }

    /**
     * @return the returnCall
     */
    public String getReturnCall() {
      return returnCall;
    }

    /**
     * @param returnCall
     *          the returnCall to set
     */
    public void setReturnCall(String returnCall) {
      this.returnCall = returnCall;
    }

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
    setSearchStartDate(reqparam.getDateString("searchStartDate"));
    setSearchEndDate(reqparam.getDateString("searchEndDate"));
    setSearchStartYear(reqparam.get("searchStartYear"));
    setSearchEndYear(reqparam.get("searchEndYear"));
    setSearchStartMonth(reqparam.get("searchStartMonth"));
    setSearchEndMonth(reqparam.get("searchEndMonth"));
    setDisplayMode(reqparam.get("displayMode"));
    setSelectMode(reqparam.get("selectMode"));
    setMobileComputerType(reqparam.get("mobileComputerType"));
    setOrderClientType(reqparam.get("orderClientType"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1071510";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.analysis.PublicCouponBean.0");
  }

  /**
   * clientGroupConditionを取得します。
   * 
   * @return clientGroupCondition
   */
  public String getClientGroupCondition() {
    return clientGroupCondition;
  }

  /**
   * clientGroupConditionを設定します。
   * 
   * @param clientGroupCondition
   *          clientGroupCondition
   */
  public void setClientGroupCondition(String clientGroupCondition) {
    this.clientGroupCondition = clientGroupCondition;
  }

  /**
   * exportAuthorityを取得します。
   * 
   * @return exportAuthority
   */
  public boolean isExportAuthority() {
    return exportAuthority;
  }

  /**
   * exportAuthorityを設定します。
   * 
   * @param exportAuthority
   *          exportAuthority
   */
  public void setExportAuthority(boolean exportAuthority) {
    this.exportAuthority = exportAuthority;
  }

  /**
   * searchResultを取得します。
   * 
   * @return searchResult
   */
  public List<NewPublicCouponBeanDetail> getSearchResult() {
    return searchResult;
  }

  /**
   * searchResultを設定します。
   * 
   * @param searchResult
   *          searchResult
   */
  public void setSearchResult(List<NewPublicCouponBeanDetail> searchResult) {
    this.searchResult = searchResult;
  }

  /**
   * searchResultDisplayを取得します。
   * 
   * @return searchResultDisplay
   */
  public boolean isSearchResultDisplay() {
    return searchResultDisplay;
  }

  /**
   * searchResultDisplayを設定します。
   * 
   * @param searchResultDisplay
   *          searchResultDisplay
   */
  public void setSearchResultDisplay(boolean searchResultDisplay) {
    this.searchResultDisplay = searchResultDisplay;
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
   * displayModeを取得します。
   * 
   * @return displayMode
   */
  public String getDisplayMode() {
    return displayMode;
  }

  /**
   * displayModeを設定します。
   * 
   * @param displayMode
   *          displayMode
   */
  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
  }

  /**
   * @return the searchStartDate
   */
  public String getSearchStartDate() {
    return searchStartDate;
  }

  /**
   * @param searchStartDate
   *          the searchStartDate to set
   */
  public void setSearchStartDate(String searchStartDate) {
    this.searchStartDate = searchStartDate;
  }

  /**
   * @return the searchEndDate
   */
  public String getSearchEndDate() {
    return searchEndDate;
  }

  /**
   * @param searchEndDate
   *          the searchEndDate to set
   */
  public void setSearchEndDate(String searchEndDate) {
    this.searchEndDate = searchEndDate;
  }

  /**
   * @return the serialVersionUID
   */
  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  /**
   * @return the searchStartYear
   */
  public String getSearchStartYear() {
    return searchStartYear;
  }

  /**
   * @param searchStartYear
   *          the searchStartYear to set
   */
  public void setSearchStartYear(String searchStartYear) {
    this.searchStartYear = searchStartYear;
  }

  /**
   * @return the searchEndYear
   */
  public String getSearchEndYear() {
    return searchEndYear;
  }

  /**
   * @param searchEndYear
   *          the searchEndYear to set
   */
  public void setSearchEndYear(String searchEndYear) {
    this.searchEndYear = searchEndYear;
  }

  /**
   * @return the searchStartMonth
   */
  public String getSearchStartMonth() {
    return searchStartMonth;
  }

  /**
   * @param searchStartMonth
   *          the searchStartMonth to set
   */
  public void setSearchStartMonth(String searchStartMonth) {
    this.searchStartMonth = searchStartMonth;
  }

  /**
   * @return the searchEndMonth
   */
  public String getSearchEndMonth() {
    return searchEndMonth;
  }

  /**
   * @param searchEndMonth
   *          the searchEndMonth to set
   */
  public void setSearchEndMonth(String searchEndMonth) {
    this.searchEndMonth = searchEndMonth;
  }

  /**
   * @return the searchOrderDate
   */
  public String getSearchOrderDate() {
    return searchOrderDate;
  }

  /**
   * @param searchOrderDate
   *          the searchOrderDate to set
   */
  public void setSearchOrderDate(String searchOrderDate) {
    this.searchOrderDate = searchOrderDate;
  }

  /**
   * @return the searchDiscountCode
   */
  public String getSearchDiscountCode() {
    return searchDiscountCode;
  }

  /**
   * @param searchDiscountCode
   *          the searchDiscountCode to set
   */
  public void setSearchDiscountCode(String searchDiscountCode) {
    this.searchDiscountCode = searchDiscountCode;
  }

  /**
   * @return the selectMode
   */
  public String getSelectMode() {
    return selectMode;
  }

  /**
   * @param selectMode
   *          the selectMode to set
   */
  public void setSelectMode(String selectMode) {
    this.selectMode = selectMode;
  }

  public List<NameValue> getMobileComputerTypeList() {
    return NameValue.asList(":" + MobileComputerType.ALL.getName() + "/" + MobileComputerType.PC.getValue() + ":"
        + MobileComputerType.PC.getName() + "/" + MobileComputerType.Mobile.getValue() + ":" + MobileComputerType.Mobile.getName());
  }
  
  public List<CodeAttribute> getOrderClientTypeList() {
    List<CodeAttribute> orderClientTypeList = new ArrayList<CodeAttribute>();
    orderClientTypeList.addAll(Arrays.asList(OrderClientType.values()));
    return orderClientTypeList;
  }

  
  /**
   * @return the orderClientType
   */
  public String getOrderClientType() {
    return orderClientType;
  }

  
  /**
   * @param orderClientType the orderClientType to set
   */
  public void setOrderClientType(String orderClientType) {
    this.orderClientType = orderClientType;
  }

}
