package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1070810:売上集計のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Digit
  @Length(4)
  @Required
  @Metadata(name = "集計対象年", order = 1)
  private String searchYear;

  @Digit
  @Length(2)
  @Required
  @Metadata(name = "集計対象月", order = 2)
  private String searchMonth;

  /** CSV出力権限有無 true:CSV出力ボタン表示 false:CSV出力ボタン非表示 */
  private boolean exportAuthority = false;

  private SalesAmountBeanDetail searchSiteResult = new SalesAmountBeanDetail();

  private List<SalesAmountBeanDetail> searchShopResult = new ArrayList<SalesAmountBeanDetail>();

  /**
   * U1070810:売上集計のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class SalesAmountBeanDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** ショップコード */
    private String shopCode;

    /** ショップ名 */
    private String shopName;

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
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1070810";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.analysis.SalesAmountBean.0");
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
   * searchShopResultを取得します。
   * 
   * @return searchShopResult
   */
  public List<SalesAmountBeanDetail> getSearchShopResult() {
    return searchShopResult;
  }

  /**
   * searchShopResultを設定します。
   * 
   * @param searchShopResult
   *          searchShopResult
   */
  public void setSearchShopResult(List<SalesAmountBeanDetail> searchShopResult) {
    this.searchShopResult = searchShopResult;
  }

  /**
   * searchSiteResultを取得します。
   * 
   * @return searchSiteResult
   */
  public SalesAmountBeanDetail getSearchSiteResult() {
    return searchSiteResult;
  }

  /**
   * searchSiteResultを設定します。
   * 
   * @param searchSiteResult
   *          searchSiteResult
   */
  public void setSearchSiteResult(SalesAmountBeanDetail searchSiteResult) {
    this.searchSiteResult = searchSiteResult;
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

}
