package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1070830:商品別売上集計のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountSkuBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Required
  @Datetime
  @Metadata(name = "集計期間(From)", order = 1)
  private String searchStartDate;

  @Required
  @Datetime
  @Metadata(name = "集計期間(To)", order = 2)
  private String searchEndDate;

  @Length(16)
  @Metadata(name = "ショップコード", order = 3)
  private String shopCodeCondition;

  /** ショップ名リスト */
  private List<CodeAttribute> shopNameList = new ArrayList<CodeAttribute>();

  @AlphaNum2
  @Length(16)
  @Metadata(name = "商品コード(From)", order = 4)
  private String commodityCodeStart;

  @AlphaNum2
  @Length(16)
  @Metadata(name = "商品コード(To)", order = 5)
  private String commodityCodeEnd;

  @AlphaNum2
  @Length(16)
  @Metadata(name = "SKUコード(From)", order = 6)
  private String skuCodeStart;

  @AlphaNum2
  @Length(16)
  @Metadata(name = "SKUコード(To)", order = 7)
  private String skuCodeEnd;

  @Length(50)
  @Metadata(name = "規格別商品名称", order = 8)
  private String skuName;

  /** CSV出力権限有無 true:CSV出力ボタン表示 false:CSV出力ボタン非表示 */
  private boolean exportAuthority = false;

  /** 改ページ制御 */
  private PagerValue pagerValue = new PagerValue();

  private List<SalesAmountSkuBeanDetail> searchResult = new ArrayList<SalesAmountSkuBeanDetail>();

  private String shopCodeDisplayMode;

  /**
   * U1070830:商品別売上集計のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class SalesAmountSkuBeanDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** ショップ名 */
    private String shopName;

    /** ショップコード */
    private String shopCode;

    /** 商品コード */
    private String commodityCode;

    /** SKUコード */
    private String skuCode;

    /** 規格別商品名 */
    private String skuName;

    /** 商品金額累計 */
    private String totalSalesPrice;

    /** 商品消費税額累計 */
    private String totalSalesPriceTax;

    /** ギフト金額累計 */
    private String totalGiftPrice;

    /** ギフト消費税額累計 */
    private String totalGiftTax;

    /** 値引額累計 */
    private String totalDiscountAmount;

    /** 返金額累計 */
    private String totalRefund;

    /** 注文個数累計 */
    private String totalOrderQuantity;

    /** 返品個数累計 */
    private String totalReturnItemQuantity;

    /**
     * skuNameを取得します。
     * 
     * @return skuName
     */
    public String getSkuName() {
      return skuName;
    }

    /**
     * skuNameを設定します。
     * 
     * @param skuName
     *          skuName
     */
    public void setSkuName(String skuName) {
      this.skuName = skuName;
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
     * totalOrderQuantityを取得します。
     * 
     * @return totalOrderQuantity
     */
    public String getTotalOrderQuantity() {
      return totalOrderQuantity;
    }

    /**
     * totalOrderQuantityを設定します。
     * 
     * @param totalOrderQuantity
     *          totalOrderQuantity
     */
    public void setTotalOrderQuantity(String totalOrderQuantity) {
      this.totalOrderQuantity = totalOrderQuantity;
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
     * totalReturnItemQuantityを取得します。
     * 
     * @return totalReturnItemQuantity
     */
    public String getTotalReturnItemQuantity() {
      return totalReturnItemQuantity;
    }

    /**
     * totalReturnItemQuantityを設定します。
     * 
     * @param totalReturnItemQuantity
     *          totalReturnItemQuantity
     */
    public void setTotalReturnItemQuantity(String totalReturnItemQuantity) {
      this.totalReturnItemQuantity = totalReturnItemQuantity;
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
    setShopCodeCondition(reqparam.get("shopCodeCondition"));
    setCommodityCodeStart(reqparam.get("commodityCodeStart"));
    setCommodityCodeEnd(reqparam.get("commodityCodeEnd"));
    setSkuName(reqparam.get("skuName"));
    // 10.1.4 10166 削除 ここから
    // setSearchResult(new ArrayList<SalesAmountSkuBeanDetail>());
    // 10.1.4 10166 削除 ここまで
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1070830";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.analysis.SalesAmountSkuBean.0");
  }

  /**
   * commodityCodeEndを取得します。
   * 
   * @return commodityCodeEnd
   */
  public String getCommodityCodeEnd() {
    return commodityCodeEnd;
  }

  /**
   * commodityCodeEndを設定します。
   * 
   * @param commodityCodeEnd
   *          commodityCodeEnd
   */
  public void setCommodityCodeEnd(String commodityCodeEnd) {
    this.commodityCodeEnd = commodityCodeEnd;
  }

  /**
   * commodityCodeStartを取得します。
   * 
   * @return commodityCodeStart
   */
  public String getCommodityCodeStart() {
    return commodityCodeStart;
  }

  /**
   * commodityCodeStartを設定します。
   * 
   * @param commodityCodeStart
   *          commodityCodeStart
   */
  public void setCommodityCodeStart(String commodityCodeStart) {
    this.commodityCodeStart = commodityCodeStart;
  }

  /**
   * skuNameを取得します。
   * 
   * @return skuName
   */
  public String getSkuName() {
    return skuName;
  }

  /**
   * skuNameを設定します。
   * 
   * @param skuName
   *          skuName
   */
  public void setSkuName(String skuName) {
    this.skuName = skuName;
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
   * searchEndDateを取得します。
   * 
   * @return searchEndDate
   */
  public String getSearchEndDate() {
    return searchEndDate;
  }

  /**
   * searchEndDateを設定します。
   * 
   * @param searchEndDate
   *          searchEndDate
   */
  public void setSearchEndDate(String searchEndDate) {
    this.searchEndDate = searchEndDate;
  }

  /**
   * searchResultを取得します。
   * 
   * @return searchResult
   */
  public List<SalesAmountSkuBeanDetail> getSearchResult() {
    return searchResult;
  }

  /**
   * searchResultを設定します。
   * 
   * @param searchResult
   *          searchResult
   */
  public void setSearchResult(List<SalesAmountSkuBeanDetail> searchResult) {
    this.searchResult = searchResult;
  }

  /**
   * searchStartDateを取得します。
   * 
   * @return searchStartDate
   */
  public String getSearchStartDate() {
    return searchStartDate;
  }

  /**
   * searchStartDateを設定します。
   * 
   * @param searchStartDate
   *          searchStartDate
   */
  public void setSearchStartDate(String searchStartDate) {
    this.searchStartDate = searchStartDate;
  }

  /**
   * shopCodeConditionを取得します。
   * 
   * @return shopCodeCondition
   */
  public String getShopCodeCondition() {
    return shopCodeCondition;
  }

  /**
   * shopCodeConditionを設定します。
   * 
   * @param shopCodeCondition
   *          shopCodeCondition
   */
  public void setShopCodeCondition(String shopCodeCondition) {
    this.shopCodeCondition = shopCodeCondition;
  }

  /**
   * shopNameListを取得します。
   * 
   * @return shopNameList
   */
  public List<CodeAttribute> getShopNameList() {
    return shopNameList;
  }

  /**
   * shopNameListを設定します。
   * 
   * @param shopNameList
   *          shopNameList
   */
  public void setShopNameList(List<CodeAttribute> shopNameList) {
    this.shopNameList = shopNameList;
  }

  /**
   * skuCodeEndを取得します。
   * 
   * @return skuCodeEnd
   */
  public String getSkuCodeEnd() {
    return skuCodeEnd;
  }

  /**
   * skuCodeEndを設定します。
   * 
   * @param skuCodeEnd
   *          skuCodeEnd
   */
  public void setSkuCodeEnd(String skuCodeEnd) {
    this.skuCodeEnd = skuCodeEnd;
  }

  /**
   * skuCodeStartを取得します。
   * 
   * @return skuCodeStart
   */
  public String getSkuCodeStart() {
    return skuCodeStart;
  }

  /**
   * skuCodeStartを設定します。
   * 
   * @param skuCodeStart
   *          skuCodeStart
   */
  public void setSkuCodeStart(String skuCodeStart) {
    this.skuCodeStart = skuCodeStart;
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
   * shopCodeDisplayModeを取得します。
   * 
   * @return shopCodeDisplayMode shopCodeDisplayMode
   */
  public String getShopCodeDisplayMode() {
    return shopCodeDisplayMode;
  }

  /**
   * shopCodeDisplayModeを設定します。
   * 
   * @param shopCodeDisplayMode
   *          shopCodeDisplayMode
   */
  public void setShopCodeDisplayMode(String shopCodeDisplayMode) {
    this.shopCodeDisplayMode = shopCodeDisplayMode;
  }

}
