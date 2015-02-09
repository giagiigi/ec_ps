package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1070820:ショップ別売上集計のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountShopBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 日別集計 */
  public static final String DISPLAY_MODE_DAY = "day";

  /** 月別集計 */
  public static final String DISPLAY_MODE_MONTH = "month";

  @Digit
  @Length(4)
  @Metadata(name = "集計対象年", order = 1)
  private String searchYear;

  @Digit
  @Length(2)
  @Metadata(name = "集計対象月", order = 2)
  private String searchMonth;

  @Digit
  @Length(2)
  @Metadata(name = "クライアント", order = 3)
  private String clientGroupCondition;

  /** クライアントグループリスト */
  private List<CodeAttribute> clientGroupList = new ArrayList<CodeAttribute>();

  @Length(8)
  @Metadata(name = "支払方法", order = 4)
  private String paymentMethodCondition;

  /** 支払方法リスト */
  private List<CodeAttribute> paymentMethodList = new ArrayList<CodeAttribute>();

  /** ショップコード */
  @Required
  @AlphaNum2
  @Length(16)
  @Metadata(name = "ショップコード", order = 5)
  private String shopCode;

  /** ショップ名 */
  @Length(50)
  @Metadata(name = "ショップ名", order = 6)
  private String shopName;

  /** CSV出力権限有無 true:CSV出力ボタン表示 false:CSV出力ボタン非表示 */
  private boolean exportAuthority = false;

  /** 検索結果表示有無 true:CSV出力ボタン表示 false:CSV出力ボタン非表示 */
  private boolean searchResultDisplay = false;

  /** 表示モード day:日別集計 month:月別集計 */
  private String displayMode;

  private List<SalesAmountShopBeanDetail> searchResult = new ArrayList<SalesAmountShopBeanDetail>();

  /**
   * U1070820:ショップ別売上集計のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class SalesAmountShopBeanDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 集計日 */
    private String countedDate;

    /** 商品金額累計 */
    private String totalSalesPrice;

    /** 商品消費税額累計 */
    private String totalSalesPriceTax;

    /** 返金額累計 */
    private String totalRefund;

    /** 返品損金額累計 */
    private String totalReturnItemLossMoney;

    /** 値引額累計 */
    private String totalDiscountAmount;

    /** 送料累計 */
    private String totalShippingCharge;

    /** 送料消費税額累計 */
    private String totalShippingChargeTax;

    /** ギフト金額累計 */
    private String totalGiftPrice;

    /** ギフト消費税額累計 */
    private String totalGiftTax;

    /**
     * countedDateを取得します。
     * 
     * @return countedDate
     */
    public String getCountedDate() {
      return countedDate;
    }

    /**
     * countedDateを設定します。
     * 
     * @param countedDate
     *          countedDate
     */
    public void setCountedDate(String countedDate) {
      this.countedDate = countedDate;
    }

    /**
     * totalDiscountAmountを取得します。
     * 
     * @return totalDiscountAmount
     */
    public String getTotalDiscountAmount() {
      return totalDiscountAmount;
    }

    /**
     * totalDiscountAmountを設定します。
     * 
     * @param totalDiscountAmount
     *          totalDiscountAmount
     */
    public void setTotalDiscountAmount(String totalDiscountAmount) {
      this.totalDiscountAmount = totalDiscountAmount;
    }

    /**
     * totalGiftPriceを取得します。
     * 
     * @return totalGiftPrice
     */
    public String getTotalGiftPrice() {
      return totalGiftPrice;
    }

    /**
     * totalGiftPriceを設定します。
     * 
     * @param totalGiftPrice
     *          totalGiftPrice
     */
    public void setTotalGiftPrice(String totalGiftPrice) {
      this.totalGiftPrice = totalGiftPrice;
    }

    /**
     * totalRefundを取得します。
     * 
     * @return totalRefund
     */
    public String getTotalRefund() {
      return totalRefund;
    }

    /**
     * totalRefundを設定します。
     * 
     * @param totalRefund
     *          totalRefund
     */
    public void setTotalRefund(String totalRefund) {
      this.totalRefund = totalRefund;
    }

    /**
     * totalReturnItemLossMoneyを取得します。
     * 
     * @return totalReturnItemLossMoney
     */
    public String getTotalReturnItemLossMoney() {
      return totalReturnItemLossMoney;
    }

    /**
     * totalReturnItemLossMoneyを設定します。
     * 
     * @param totalReturnItemLossMoney
     *          totalReturnItemLossMoney
     */
    public void setTotalReturnItemLossMoney(String totalReturnItemLossMoney) {
      this.totalReturnItemLossMoney = totalReturnItemLossMoney;
    }

    /**
     * totalSalesPriceを取得します。
     * 
     * @return totalSalesPrice
     */
    public String getTotalSalesPrice() {
      return totalSalesPrice;
    }

    /**
     * totalSalesPriceを設定します。
     * 
     * @param totalSalesPrice
     *          totalSalesPrice
     */
    public void setTotalSalesPrice(String totalSalesPrice) {
      this.totalSalesPrice = totalSalesPrice;
    }

    /**
     * totalShippingChargeを取得します。
     * 
     * @return totalShippingCharge
     */
    public String getTotalShippingCharge() {
      return totalShippingCharge;
    }

    /**
     * totalShippingChargeを設定します。
     * 
     * @param totalShippingCharge
     *          totalShippingCharge
     */
    public void setTotalShippingCharge(String totalShippingCharge) {
      this.totalShippingCharge = totalShippingCharge;
    }

    /**
     * totalGiftTaxを取得します。
     * 
     * @return totalGiftTax
     */
    public String getTotalGiftTax() {
      return totalGiftTax;
    }

    /**
     * totalGiftTaxを設定します。
     * 
     * @param totalGiftTax
     *          totalGiftTax
     */
    public void setTotalGiftTax(String totalGiftTax) {
      this.totalGiftTax = totalGiftTax;
    }

    /**
     * totalSalesPriceTaxを取得します。
     * 
     * @return totalSalesPriceTax
     */
    public String getTotalSalesPriceTax() {
      return totalSalesPriceTax;
    }

    /**
     * totalSalesPriceTaxを設定します。
     * 
     * @param totalSalesPriceTax
     *          totalSalesPriceTax
     */
    public void setTotalSalesPriceTax(String totalSalesPriceTax) {
      this.totalSalesPriceTax = totalSalesPriceTax;
    }

    /**
     * totalShippingChargeTaxを取得します。
     * 
     * @return totalShippingChargeTax
     */
    public String getTotalShippingChargeTax() {
      return totalShippingChargeTax;
    }

    /**
     * totalShippingChargeTaxを設定します。
     * 
     * @param totalShippingChargeTax
     *          totalShippingChargeTax
     */
    public void setTotalShippingChargeTax(String totalShippingChargeTax) {
      this.totalShippingChargeTax = totalShippingChargeTax;
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
    setSearchYear(reqparam.get("searchYear"));
    setSearchMonth(reqparam.get("searchMonth"));
    setClientGroupCondition(reqparam.get("clientGroupCondition"));
    setPaymentMethodCondition(reqparam.get("paymentMethodCondition"));
    setDisplayMode(reqparam.get("displayMode"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1070820";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.analysis.SalesAmountShopBean.0");
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
   * clientGroupListを取得します。
   * 
   * @return clientGroupList
   */
  public List<CodeAttribute> getClientGroupList() {
    return clientGroupList;
  }

  /**
   * clientGroupListを設定します。
   * 
   * @param clientGroupList
   *          clientGroupList
   */
  public void setClientGroupList(List<CodeAttribute> clientGroupList) {
    this.clientGroupList = clientGroupList;
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
   * paymentMethodConditionを取得します。
   * 
   * @return paymentMethodCondition
   */
  public String getPaymentMethodCondition() {
    return paymentMethodCondition;
  }

  /**
   * paymentMethodConditionを設定します。
   * 
   * @param paymentMethodCondition
   *          paymentMethodCondition
   */
  public void setPaymentMethodCondition(String paymentMethodCondition) {
    this.paymentMethodCondition = paymentMethodCondition;
  }

  /**
   * paymentMethodListを取得します。
   * 
   * @return paymentMethodList
   */
  public List<CodeAttribute> getPaymentMethodList() {
    return paymentMethodList;
  }

  /**
   * paymentMethodListを設定します。
   * 
   * @param paymentMethodList
   *          paymentMethodList
   */
  public void setPaymentMethodList(List<CodeAttribute> paymentMethodList) {
    this.paymentMethodList = paymentMethodList;
  }

  /**
   * searchMonthを取得します。
   * 
   * @return searchMonth
   */
  public String getSearchMonth() {
    return searchMonth;
  }

  /**
   * searchMonthを設定します。
   * 
   * @param searchMonth
   *          searchMonth
   */
  public void setSearchMonth(String searchMonth) {
    this.searchMonth = searchMonth;
  }

  /**
   * searchResultを取得します。
   * 
   * @return searchResult
   */
  public List<SalesAmountShopBeanDetail> getSearchResult() {
    return searchResult;
  }

  /**
   * searchResultを設定します。
   * 
   * @param searchResult
   *          searchResult
   */
  public void setSearchResult(List<SalesAmountShopBeanDetail> searchResult) {
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
   * searchYearを取得します。
   * 
   * @return searchYear
   */
  public String getSearchYear() {
    return searchYear;
  }

  /**
   * searchYearを設定します。
   * 
   * @param searchYear
   *          searchYear
   */
  public void setSearchYear(String searchYear) {
    this.searchYear = searchYear;
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

}
