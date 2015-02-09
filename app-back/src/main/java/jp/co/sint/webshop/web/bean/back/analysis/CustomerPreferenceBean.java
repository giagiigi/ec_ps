package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.service.analysis.RearrangeType;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1070310:顧客嗜好分析のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerPreferenceBean extends UIBackBean implements UISearchBean {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** ページング情報 */
  private PagerValue pagerValue = new PagerValue();

  /** 性別 */
  @Digit
  @Metadata(name = "性別", order = 1)
  private String sexCondition;

  /** 検索開始年 */
  @Required
  @Digit
  @Length(4)
  @Metadata(name = "年(From)", order = 2)
  private String searchYearStart;

  /** 検索終了年 */
  @Required
  @Digit
  @Length(4)
  @Metadata(name = "年(To)", order = 3)
  private String searchYearEnd;

  /** 検索開始月 */
  @Required
  @Digit
  @Length(2)
  @Metadata(name = "月(From)", order = 4)
  private String searchMonthStart;

  /** 検索終了月 */
  @Required
  @Digit
  @Length(2)
  @Metadata(name = "月(To)", order = 5)
  private String searchMonthEnd;

  /** 顧客グループリスト */
  private List<CodeAttribute> customerGroupList = new ArrayList<CodeAttribute>();

  /** 顧客グループ選択 */
  @AlphaNum2
  @Length(16)
  private String customerGroupCondition;

  /** 検索開始年齢 */
  @Digit
  @Length(3)
  @Range(min = 0)
  @Metadata(name = "年齢(From)", order = 6)
  private String searchAgeStart;

  /** 検索終了年齢 */
  @Digit
  @Length(3)
  @Range(min = 0)
  @Metadata(name = "年齢(To)", order = 7)
  private String searchAgeEnd;

  /** 並べ替えタイプリスト */
  private List<CodeAttribute> rearrangeTypeList = new ArrayList<CodeAttribute>();

  /** 並べ替えタイプ */
  @Required
  @Metadata(name = "並べ替え", order = 8)
  private String rearrangeTypeCondition = RearrangeType.ORDER_BY_ORDER_COUNT.getValue();

  /** 詳細検索モード(顧客属性での絞り込み検索を行うかどうか) */
  private String advancedSearchMode;

  /** 顧客属性を選択するリスト */
  private List<List<CodeAttribute>> customerAttributeSelectList = new ArrayList<List<CodeAttribute>>();

  /** 選択した顧客属性NO */
  private List<String> customerAttributeNoList = new ArrayList<String>();

  /** 選択した顧客属性 */
  private List<CustomerAttributeListBean> customerAttributeList = new ArrayList<CustomerAttributeListBean>();

  /** 検索結果 */
  private List<CustomerPreferenceDetailBean> searchResult = new ArrayList<CustomerPreferenceDetailBean>();

  /** 受注件数合計 */
  private String totalOrderCount;

  /** 顧客数合計 */
  private String totalCustomerCount;

  /** CSV出力ボタン表示有無 */
  private boolean displayExportButton = false;

  /**
   * U1070310:顧客嗜好分析のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerAttributeListBean implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    /** 顧客属性番号 */
    @Digit
    private String customerAttributeNo;

    /** 顧客属性名 */
    private String customerAttributeName;

    /** 顧客属性選択肢番号 */
    private List<CodeAttribute> attributeChoiceList = new ArrayList<CodeAttribute>();

    /** 顧客属性選択肢回答リスト表示用 */
    private List<String> attributeAnswerItem = new ArrayList<String>();

    /**
     * attributeAnswerItemを取得します。
     * 
     * @return attributeAnswerItem
     */
    public List<String> getAttributeAnswerItem() {
      return attributeAnswerItem;
    }

    /**
     * attributeChoiceListを取得します。
     * 
     * @return attributeChoiceList
     */
    public List<CodeAttribute> getAttributeChoiceList() {
      return attributeChoiceList;
    }

    /**
     * customerAttributeNameを取得します。
     * 
     * @return customerAttributeName
     */
    public String getCustomerAttributeName() {
      return customerAttributeName;
    }

    /**
     * attributeAnswerItemを設定します。
     * 
     * @param attributeAnswerItem
     *          attributeAnswerItem
     */
    public void setAttributeAnswerItem(List<String> attributeAnswerItem) {
      this.attributeAnswerItem = attributeAnswerItem;
    }

    /**
     * attributeChoiceListを設定します。
     * 
     * @param attributeChoiceList
     *          attributeChoiceList
     */
    public void setAttributeChoiceList(List<CodeAttribute> attributeChoiceList) {
      this.attributeChoiceList = attributeChoiceList;
    }

    /**
     * customerAttributeNameを設定します。
     * 
     * @param customerAttributeName
     *          customerAttributeName
     */
    public void setCustomerAttributeName(String customerAttributeName) {
      this.customerAttributeName = customerAttributeName;
    }

    /**
     * customerAttributeNoを取得します。
     * 
     * @return customerAttributeNo
     */
    public String getCustomerAttributeNo() {
      return customerAttributeNo;
    }

    /**
     * customerAttributeNoを設定します。
     * 
     * @param customerAttributeNo
     *          customerAttributeNo
     */
    public void setCustomerAttributeNo(String customerAttributeNo) {
      this.customerAttributeNo = customerAttributeNo;
    }

  }

  /**
   * U1070310:顧客嗜好分析のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerPreferenceDetailBean implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    /** ショップコード */
    private String shopCode;

    /** ショップ名 */
    private String shopName;

    /** 商品コード */
    private String commodityCode;

    /** 商品名 */
    private String commodityName;

    /** 受注件数合計 */
    private String totalOrderCount;

    /** 受注件数構成率 */
    private String totalOrderCountRatio;

    /** 購入数合計 */
    private String purchasingAmount;

    /** グラフ表示数 */
    private String graphCount;

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
     * purchasingAmountを取得します。
     * 
     * @return purchasingAmount
     */
    public String getPurchasingAmount() {
      return purchasingAmount;
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
     * totalOrderCountを取得します。
     * 
     * @return totalOrderCount
     */
    public String getTotalOrderCount() {
      return totalOrderCount;
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
     * purchasingAmountを設定します。
     * 
     * @param purchasingAmount
     *          purchasingAmount
     */
    public void setPurchasingAmount(String purchasingAmount) {
      this.purchasingAmount = purchasingAmount;
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
     * totalOrderCountを設定します。
     * 
     * @param totalOrderCount
     *          totalOrderCount
     */
    public void setTotalOrderCount(String totalOrderCount) {
      this.totalOrderCount = totalOrderCount;
    }

    /**
     * totalOrderCountRatioを取得します。
     * 
     * @return totalOrderCountRatio
     */
    public String getTotalOrderCountRatio() {
      return totalOrderCountRatio;
    }

    /**
     * totalOrderCountRatioを設定します。
     * 
     * @param totalOrderCountRatio
     *          totalOrderCountRatio
     */
    public void setTotalOrderCountRatio(String totalOrderCountRatio) {
      this.totalOrderCountRatio = totalOrderCountRatio;
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
    this.setSexCondition(reqparam.get("sex"));
    this.setSearchAgeStart(reqparam.get("searchAgeStart"));
    this.setSearchAgeEnd(reqparam.get("searchAgeEnd"));
    this.setCustomerGroupCondition(reqparam.get("customerGroup"));
    this.setSearchYearStart(reqparam.get("searchYearStart"));
    this.setSearchMonthStart(reqparam.get("searchMonthStart"));
    this.setSearchYearEnd(reqparam.get("searchYearEnd"));
    this.setSearchMonthEnd(reqparam.get("searchMonthEnd"));
    this.setRearrangeTypeCondition(reqparam.get("rearrange"));
    this.setAdvancedSearchMode(reqparam.get("advancedSearchMode"));

    List<String> customerAttributeNo = new ArrayList<String>();
    for (int i = 0; i < getCustomerAttributeSelectList().size(); i++) {
      customerAttributeNo.add(reqparam.get("customerAttributeNo_" + i));
    }
    this.setCustomerAttributeNoList(customerAttributeNo);

    List<CustomerAttributeListBean> attributeBeanList = new ArrayList<CustomerAttributeListBean>();

    for (int i = 0; i < this.getCustomerAttributeList().size(); i++) {
      CustomerAttributeListBean attributeBean = getCustomerAttributeList().get(i);

      String[] paramList = reqparam.getAll("attributeAnswer_" + i);

      List<String> answerList = new ArrayList<String>();
      if (StringUtil.hasValueAllOf(paramList)) {
        for (String s : paramList) {
          answerList.add(s);
        }
      }
      attributeBean.setAttributeAnswerItem(answerList);
      attributeBeanList.add(attributeBean);
    }
    this.setCustomerAttributeList(attributeBeanList);
  }

  /**
   * customerAttributeListを取得します。
   * 
   * @return customerAttributeList
   */
  public List<CustomerAttributeListBean> getCustomerAttributeList() {
    return customerAttributeList;
  }

  /**
   * customerAttributeNoListを取得します。
   * 
   * @return customerAttributeNoList
   */
  public List<String> getCustomerAttributeNoList() {
    return customerAttributeNoList;
  }

  /**
   * customerAttributeSelectListを取得します。
   * 
   * @return customerAttributeSelectList
   */
  public List<List<CodeAttribute>> getCustomerAttributeSelectList() {
    return customerAttributeSelectList;
  }

  /**
   * customerAttributeListを設定します。
   * 
   * @param customerAttributeList
   *          customerAttributeList
   */
  public void setCustomerAttributeList(List<CustomerAttributeListBean> customerAttributeList) {
    this.customerAttributeList = customerAttributeList;
  }

  /**
   * customerAttributeNoListを設定します。
   * 
   * @param customerAttributeNoList
   *          customerAttributeNoList
   */
  public void setCustomerAttributeNoList(List<String> customerAttributeNoList) {
    this.customerAttributeNoList = customerAttributeNoList;
  }

  /**
   * customerAttributeSelectListを設定します。
   * 
   * @param customerAttributeSelectList
   *          customerAttributeSelectList
   */
  public void setCustomerAttributeSelectList(List<List<CodeAttribute>> customerAttributeSelectList) {
    this.customerAttributeSelectList = customerAttributeSelectList;
  }

  /**
   * totalOrderCountを取得します。
   * 
   * @return totalOrderCount
   */
  public String getTotalOrderCount() {
    return totalOrderCount;
  }

  /**
   * totalPurchasingAmountを取得します。
   * 
   * @return totalPurchasingAmount
   */
  public String getTotalCustomerCount() {
    return totalCustomerCount;
  }

  /**
   * totalOrderCountを設定します。
   * 
   * @param totalOrderCount
   *          totalOrderCount
   */
  public void setTotalOrderCount(String totalOrderCount) {
    this.totalOrderCount = totalOrderCount;
  }

  /**
   * totalPurchasingAmountを設定します。
   * 
   * @param totalCustomerCount
   *          顧客数合計
   */
  public void setTotalCustomerCount(String totalCustomerCount) {
    this.totalCustomerCount = totalCustomerCount;
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1070310";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.analysis.CustomerPreferenceBean.0");
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return this.pagerValue;
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          設定する pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * searchAgeEndを取得します。
   * 
   * @return searchAgeEnd
   */
  public String getSearchAgeEnd() {
    return searchAgeEnd;
  }

  /**
   * searchAgeStartを取得します。
   * 
   * @return searchAgeStart
   */
  public String getSearchAgeStart() {
    return searchAgeStart;
  }

  /**
   * searchMonthEndを取得します。
   * 
   * @return searchMonthEnd
   */
  public String getSearchMonthEnd() {
    return searchMonthEnd;
  }

  /**
   * searchMonthStartを取得します。
   * 
   * @return searchMonthStart
   */
  public String getSearchMonthStart() {
    return searchMonthStart;
  }

  /**
   * searchResultを取得します。
   * 
   * @return searchResult
   */
  public List<CustomerPreferenceDetailBean> getSearchResult() {
    return searchResult;
  }

  /**
   * searchYearEndを取得します。
   * 
   * @return searchYearEnd
   */
  public String getSearchYearEnd() {
    return searchYearEnd;
  }

  /**
   * searchYearStartを取得します。
   * 
   * @return searchYearStart
   */
  public String getSearchYearStart() {
    return searchYearStart;
  }

  /**
   * sexconditionを取得します。
   * 
   * @return sexcondition
   */
  public String getSexCondition() {
    return sexCondition;
  }

  /**
   * searchAgeEndを設定します。
   * 
   * @param searchAgeEnd
   *          searchAgeEnd
   */
  public void setSearchAgeEnd(String searchAgeEnd) {
    this.searchAgeEnd = searchAgeEnd;
  }

  /**
   * searchAgeStartを設定します。
   * 
   * @param searchAgeStart
   *          searchAgeStart
   */
  public void setSearchAgeStart(String searchAgeStart) {
    this.searchAgeStart = searchAgeStart;
  }

  /**
   * searchMonthEndを設定します。
   * 
   * @param searchMonthEnd
   *          searchMonthEnd
   */
  public void setSearchMonthEnd(String searchMonthEnd) {
    this.searchMonthEnd = searchMonthEnd;
  }

  /**
   * searchMonthStartを設定します。
   * 
   * @param searchMonthStart
   *          searchMonthStart
   */
  public void setSearchMonthStart(String searchMonthStart) {
    this.searchMonthStart = searchMonthStart;
  }

  /**
   * searchResultを設定します。
   * 
   * @param searchResult
   *          searchResult
   */
  public void setSearchResult(List<CustomerPreferenceDetailBean> searchResult) {
    this.searchResult = searchResult;
  }

  /**
   * searchYearEndを設定します。
   * 
   * @param searchYearEnd
   *          searchYearEnd
   */
  public void setSearchYearEnd(String searchYearEnd) {
    this.searchYearEnd = searchYearEnd;
  }

  /**
   * searchYearStartを設定します。
   * 
   * @param searchYearStart
   *          searchYearStart
   */
  public void setSearchYearStart(String searchYearStart) {
    this.searchYearStart = searchYearStart;
  }

  /**
   * sexconditionを設定します。
   * 
   * @param sexcondition
   *          sexcondition
   */
  public void setSexCondition(String sexcondition) {
    this.sexCondition = sexcondition;
  }

  /**
   * customerGroupConditionを取得します。
   * 
   * @return customerGroupCondition
   */
  public String getCustomerGroupCondition() {
    return customerGroupCondition;
  }

  /**
   * customerGroupListを取得します。
   * 
   * @return customerGroupList
   */
  public List<CodeAttribute> getCustomerGroupList() {
    return customerGroupList;
  }

  /**
   * customerGroupConditionを設定します。
   * 
   * @param customerGroupCondition
   *          customerGroupCondition
   */
  public void setCustomerGroupCondition(String customerGroupCondition) {
    this.customerGroupCondition = customerGroupCondition;
  }

  /**
   * customerGroupListを設定します。
   * 
   * @param customerGroupList
   *          customerGroupList
   */
  public void setCustomerGroupList(List<CodeAttribute> customerGroupList) {
    this.customerGroupList = customerGroupList;
  }

  /**
   * rearrangeTypeConditionを取得します。
   * 
   * @return orderDivision
   */
  public String getRearrangeTypeCondition() {
    return rearrangeTypeCondition;
  }

  /**
   * rearrangeTypeConditionを設定します。
   * 
   * @param rearrangeTypeCondition
   *          rearrangeTypeCondition
   */
  public void setRearrangeTypeCondition(String rearrangeTypeCondition) {
    this.rearrangeTypeCondition = rearrangeTypeCondition;
  }

  /**
   * rearrangeTypeListを取得します。
   * 
   * @return rearrangeTypeList
   */
  public List<CodeAttribute> getRearrangeTypeList() {
    return rearrangeTypeList;
  }

  /**
   * rearrangeTypeListを設定します。
   * 
   * @param rearrangeTypeList
   *          rearrangeTypeList
   */
  public void setRearrangeTypeList(List<CodeAttribute> rearrangeTypeList) {
    this.rearrangeTypeList = rearrangeTypeList;
  }

  /**
   * advancedSearchModeを取得します。
   * 
   * @return advancedSearchMode
   */
  public String getAdvancedSearchMode() {
    return advancedSearchMode;
  }

  /**
   * advancedSearchModeを設定します。
   * 
   * @param advancedSearchMode
   *          advancedSearchMode
   */
  public void setAdvancedSearchMode(String advancedSearchMode) {
    this.advancedSearchMode = advancedSearchMode;
  }

  /**
   * displayExportButtonを取得します。
   * 
   * @return displayExportButton
   */
  public boolean isDisplayExportButton() {
    return displayExportButton;
  }

  /**
   * displayExportButtonを設定します。
   * 
   * @param displayExportButton
   *          displayExportButton
   */
  public void setDisplayExportButton(boolean displayExportButton) {
    this.displayExportButton = displayExportButton;
  }

}
