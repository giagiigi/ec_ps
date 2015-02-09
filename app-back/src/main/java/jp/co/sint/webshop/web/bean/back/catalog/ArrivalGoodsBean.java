package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040710:入荷お知らせのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ArrivalGoodsBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<ArrivalGoodsDetailBean> list = new ArrayList<ArrivalGoodsDetailBean>();

  /** SKUコード */
  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKUコード", order = 1)
  private String searchSkuCode;

  /** 商品名 */
  @Length(50)
  @Metadata(name = "商品名", order = 2)
  private String searchCommodityName;

  private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップ", order = 3)
  private String searchShopCode;

  private boolean displayShopList = false;

  private boolean displayDeleteButton = false;

  private List<String> checkedCode = new ArrayList<String>();

  private PagerValue pagerValue;

  /**
   * U1040710:入荷お知らせのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class ArrivalGoodsDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String checkBox;

    private String shopCode;

    private String skuCode;

    private String commodityName;

    private String subscriptionCount;

    private boolean displayCsvExportButton = false;

    /**
     * checkBoxを取得します。
     * 
     * @return checkBox
     */
    public String getCheckBox() {
      return checkBox;
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
     * skuCodeを取得します。
     * 
     * @return skuCode
     */
    public String getSkuCode() {
      return skuCode;
    }

    /**
     * checkBoxを設定します。
     * 
     * @param checkBox
     *          checkBox
     */
    public void setCheckBox(String checkBox) {
      this.checkBox = checkBox;
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
     * skuCodeを設定します。
     * 
     * @param skuCode
     *          skuCode
     */
    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    /**
     * displayCsvExportButtonを取得します。
     * 
     * @return displayCsvExportButton
     */
    public boolean isDisplayCsvExportButton() {
      return displayCsvExportButton;
    }

    /**
     * displayCsvExportButtonを設定します。
     * 
     * @param displayCsvExportButton
     *          displayCsvExportButton
     */
    public void setDisplayCsvExportButton(boolean displayCsvExportButton) {
      this.displayCsvExportButton = displayCsvExportButton;
    }

    /**
     * subscriptionCountを取得します。
     * 
     * @return subscriptionCount
     */
    public String getSubscriptionCount() {
      return subscriptionCount;
    }

    /**
     * subscriptionCountを設定します。
     * 
     * @param subscriptionCount
     *          subscriptionCount
     */
    public void setSubscriptionCount(String subscriptionCount) {
      this.subscriptionCount = subscriptionCount;
    }

  }

  /**
   * shopListを取得します。
   * 
   * @return shopList
   */
  public List<CodeAttribute> getShopList() {
    return shopList;
  }

  /**
   * shopListを設定します。
   * 
   * @param shopList
   *          shopList
   */
  public void setShopList(List<CodeAttribute> shopList) {
    this.shopList = shopList;
  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<ArrivalGoodsDetailBean> getList() {
    return list;
  }

  /**
   * searchCommodityNameを取得します。
   * 
   * @return searchCommodityName
   */
  public String getSearchCommodityName() {
    return searchCommodityName;
  }

  /**
   * searchSkuCodeを取得します。
   * 
   * @return searchSkuCode
   */
  public String getSearchSkuCode() {
    return searchSkuCode;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<ArrivalGoodsDetailBean> list) {
    this.list = list;
  }

  /**
   * searchCommodityNameを設定します。
   * 
   * @param searchCommodityName
   *          searchCommodityName
   */
  public void setSearchCommodityName(String searchCommodityName) {
    this.searchCommodityName = searchCommodityName;
  }

  /**
   * searchSkuCodeを設定します。
   * 
   * @param searchSkuCode
   *          searchSkuCode
   */
  public void setSearchSkuCode(String searchSkuCode) {
    this.searchSkuCode = searchSkuCode;
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
   * displayDeleteButtonを取得します。
   * 
   * @return displayDeleteButton
   */
  public boolean isDisplayDeleteButton() {
    return displayDeleteButton;
  }

  /**
   * displayShopListを取得します。
   * 
   * @return displayShopList
   */
  public boolean isDisplayShopList() {
    return displayShopList;
  }

  /**
   * displayDeleteButtonを設定します。
   * 
   * @param displayDeleteButton
   *          displayDeleteButton
   */
  public void setDisplayDeleteButton(boolean displayDeleteButton) {
    this.displayDeleteButton = displayDeleteButton;
  }

  /**
   * displayShopListを設定します。
   * 
   * @param displayShopList
   *          displayShopList
   */
  public void setDisplayShopList(boolean displayShopList) {
    this.displayShopList = displayShopList;
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
    this.setSearchShopCode(reqparam.get("searchShopCode"));
    this.setSearchCommodityName(reqparam.get("searchCommodityName"));
    this.setSearchSkuCode(reqparam.get("searchSkuCode"));
    this.setCheckedCode(Arrays.asList(reqparam.getAll("checkBox")));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040710";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.ArrivalGoodsBean.0");
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
   * checkedCodeを取得します。
   * 
   * @return checkedCode
   */
  public List<String> getCheckedCode() {
    return checkedCode;
  }

  /**
   * checkedCodeを設定します。
   * 
   * @param checkedCode
   *          checkedCode
   */
  public void setCheckedCode(List<String> checkedCode) {
    this.checkedCode = checkedCode;
  }

}
