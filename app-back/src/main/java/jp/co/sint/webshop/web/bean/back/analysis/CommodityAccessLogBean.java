package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1070210:商品別アクセスログ集計のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CommodityAccessLogBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 検索するショップコード */
  @Metadata(name = "ショップコード", order = 1)
  private String shopCodeCondition;

  /** 検索開始日 */
  @Required
  @Datetime
  @Metadata(name = "期間(From)", order = 2)
  private String searchStartDate;

  /** 検索終了日 */
  @Required
  @Datetime
  @Metadata(name = "期間(To)", order = 3)
  private String searchEndDate;

  /** クライアントグループリスト */
  private List<CodeAttribute> clientGroupList = new ArrayList<CodeAttribute>();

  /** クライアントグループ選択 */
  @Metadata(name = "クライアント", order = 4)
  private String clientGroupCondition;

  /** グラフ表示単位 */
  @Required
  @Digit
  @Metadata(name = "表示スケール", order = 5)
  private String scaleCondition;

  /** ショップコード一覧 */
  private List<CodeAttribute> shopCodeList = new ArrayList<CodeAttribute>();

  /** 検索開始商品コード */
  @AlphaNum2
  @Length(16)
  @Metadata(name = "商品コード(From)", order = 6)
  private String searchCommodityCodeStart;

  /** 検索終了商品コード */
  @AlphaNum2
  @Length(16)
  @Metadata(name = "商品コード(To)", order = 7)
  private String searchCommodityCodeEnd;

  /** 検索商品名 */
  @Length(50)
  @Metadata(name = "商品名", order = 8)
  private String commodityName;

  /** ページング制御 */
  private PagerValue pagerValue = new PagerValue();

  /** CSV出力権限有無 true:CSV出力ボタン表示 false:CSV出力ボタン非表示 */
  private boolean exportAuthority = false;

  /** 検索結果 */
  private List<CommodityAccessLogBeanDetail> searchResultList = new ArrayList<CommodityAccessLogBeanDetail>();

  /** ショップコードの表示モード */
  private String shopCodeDisplayMode;

  /**
   * U1070210:商品別アクセスログ集計のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CommodityAccessLogBeanDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 商品名 */
    private String commodityName;

    /** ショップ名 */
    private String shopName;

    /** 商品アクセスログ集計 */
    private String commodityAccessLogCount;

    /** グラフ表示目盛り数 */
    private String graphCount;

    /** 端数があるかどうか */
    private boolean fraction;

    /**
     * fractionを取得します。
     * 
     * @return fraction
     */

    public boolean isFraction() {
      return fraction;
    }

    /**
     * fractionを設定します。
     * 
     * @param fraction
     *          fraction
     */
    public void setFraction(boolean fraction) {
      this.fraction = fraction;
    }

    /**
     * graphCountを取得します。
     * 
     * @return graphCount
     */
    public String getGraphCount() {
      return graphCount;
    }

    /**
     * graphCountを設定します。
     * 
     * @param graphCount
     *          graphCount
     */
    public void setGraphCount(String graphCount) {
      this.graphCount = graphCount;
    }

    /**
     * commodityAccessLogCountを取得します。
     * 
     * @return commodityAccessLogCount
     */
    public String getCommodityAccessLogCount() {
      return commodityAccessLogCount;
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
     * shopNameを取得します。
     * 
     * @return shopName
     */
    public String getShopName() {
      return shopName;
    }

    /**
     * commodityAccessLogCountを設定します。
     * 
     * @param commodityAccessLogCount
     *          commodityAccessLogCount
     */
    public void setCommodityAccessLogCount(String commodityAccessLogCount) {
      this.commodityAccessLogCount = commodityAccessLogCount;
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
     * shopNameを設定します。
     * 
     * @param shopName
     *          shopName
     */
    public void setShopName(String shopName) {
      this.shopName = shopName;
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
    this.setSearchStartDate(reqparam.getDateString("searchStartDate"));
    this.setSearchEndDate(reqparam.getDateString("searchEndDate"));
    this.setShopCodeCondition(reqparam.get("shopCode"));
    this.setClientGroupCondition(reqparam.get("clientGroup"));
    this.setSearchCommodityCodeStart(reqparam.get("searchCommodityCodeStart"));
    this.setSearchCommodityCodeEnd(reqparam.get("searchCommodityCodeEnd"));
    this.setCommodityName(reqparam.get("commodityName"));
    this.setScaleCondition(reqparam.get("scale"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1070210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.analysis.CommodityAccessLogBean.0");
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
   * searchResultListを取得します。
   * 
   * @return searchResultList
   */
  public List<CommodityAccessLogBeanDetail> getSearchResultList() {
    return searchResultList;
  }

  /**
   * searchCommodityCodeEndを取得します。
   * 
   * @return searchCommodityCodeEnd
   */
  public String getSearchCommodityCodeEnd() {
    return searchCommodityCodeEnd;
  }

  /**
   * searchCommodityCodeStartを取得します。
   * 
   * @return searchCommodityCodeStart
   */
  public String getSearchCommodityCodeStart() {
    return searchCommodityCodeStart;
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
   * searchStartDateを取得します。
   * 
   * @return searchStartDate
   */
  public String getSearchStartDate() {
    return searchStartDate;
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
   * shopCodeListを取得します。
   * 
   * @return shopCodeList
   */
  public List<CodeAttribute> getShopCodeList() {
    return shopCodeList;
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
   * searchResultListを設定します。
   * 
   * @param searchResultList
   *          searchResultList
   */
  public void setSearchResultList(List<CommodityAccessLogBeanDetail> searchResultList) {
    this.searchResultList = searchResultList;
  }

  /**
   * searchCommodityCodeEndを設定します。
   * 
   * @param searchCommodityCodeEnd
   *          searchCommodityCodeEnd
   */
  public void setSearchCommodityCodeEnd(String searchCommodityCodeEnd) {
    this.searchCommodityCodeEnd = searchCommodityCodeEnd;
  }

  /**
   * searchCommodityCodeStartを設定します。
   * 
   * @param searchCommodityCodeStart
   *          searchCommodityCodeStart
   */
  public void setSearchCommodityCodeStart(String searchCommodityCodeStart) {
    this.searchCommodityCodeStart = searchCommodityCodeStart;
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
   * searchStartDateを設定します。
   * 
   * @param searchStartDate
   *          searchStartDate
   */
  public void setSearchStartDate(String searchStartDate) {
    this.searchStartDate = searchStartDate;
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
   * shopCodeListを設定します。
   * 
   * @param shopCodeList
   *          shopCodeList
   */
  public void setShopCodeList(List<CodeAttribute> shopCodeList) {
    this.shopCodeList = shopCodeList;
  }

  /**
   * scaleConditionを取得します。
   * 
   * @return scaleCondition
   */
  public String getScaleCondition() {
    return scaleCondition;
  }

  /**
   * scaleConditionを設定します。
   * 
   * @param scaleCondition
   *          scaleCondition
   */
  public void setScaleCondition(String scaleCondition) {
    this.scaleCondition = scaleCondition;
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
   * clientGroupConditionを取得します。
   * 
   * @return clientGroupCondition
   */
  public String getClientGroupCondition() {
    return clientGroupCondition;
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
   * clientGroupConditionを設定します。
   * 
   * @param clientGroupCondition
   *          clientGroupCondition
   */
  public void setClientGroupCondition(String clientGroupCondition) {
    this.clientGroupCondition = clientGroupCondition;
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
