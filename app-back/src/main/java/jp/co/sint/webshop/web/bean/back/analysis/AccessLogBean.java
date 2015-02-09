package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1070110:アクセスログ集計のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class AccessLogBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 検索年 */
  @Required
  @Digit
  @Length(4)
  @Metadata(name = "検索年")
  private String searchYear;

  /** 検索月 */
  @Digit
  @Length(2)
  @Metadata(name = "検索月")
  private String searchMonth;

  /** 検索日 */
  @Digit
  @Length(2)
  @Metadata(name = "検索日")
  private String searchDay;

  /** クライアントグループ */
  private List<CodeAttribute> clientGroupList = new ArrayList<CodeAttribute>();

  /** 検索クライアントグループ */
  @Metadata(name = "クライアント")
  private String clientGroupCondition;

  /** 検索結果 */
  private List<AccessLogBeanDetail> searchResult = new ArrayList<AccessLogBeanDetail>();

  /**
   * アクセスログ棒グラフ表示用データ<BR>
   * [名前]:値1:値2:･･･,[名前]:値1:値2:･･･<BR>
   * の形式
   */
  private String accessLogBarGraphData;

  /**
   * コンバージョン率棒グラフ表示用データ<BR>
   * [名前]:値1:値2:･･･,[名前]:値1:値2:･･･<BR>
   * の形式
   */
  private String conevrsionBarGraphData;

  /**
   * グラフ表示用項目データ<BR>
   * [項目名],[項目名],･･･<BR>
   * の形式
   */
  private String columnData;

  /** CSV出力権限有無 true:CSV出力ボタン表示 false:CSV出力ボタン非表示 */
  private boolean exportAuthority = false;

  /** 検索結果表示有無 true:検索結果表示 false:検索結果非表示 */
  private Boolean searchResultDisplay = false;

  /** 表示モード */
  @Required
  private String displayMode;

  /**
   * U1070110:アクセスログ集計のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class AccessLogBeanDetail implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    /** * ラベル(日付/時間帯/月/曜日) */
    private String label;

    /** アクセス数 */
    private String pageViewCount;

    /** コンバージョン率 */
    private String conversionRate;

    /**
     * accessCountを取得します。
     * 
     * @return accessCount
     */
    public String getAccessCount() {
      return pageViewCount;
    }

    /**
     * conversionRateを取得します。
     * 
     * @return conversionRate
     */
    public String getConversionRate() {
      return conversionRate;
    }

    /**
     * labelを取得します。
     * 
     * @return label
     */
    public String getLabel() {
      return label;
    }

    /**
     * pageViewCountを返します。
     * 
     * @return the pageViewCount
     */
    public String getPageViewCount() {
      return pageViewCount;
    }

    /**
     * pageViewCountを設定します。
     * 
     * @param pageViewCount
     *          設定する pageViewCount
     */
    public void setPageViewCount(String pageViewCount) {
      this.pageViewCount = pageViewCount;
    }

    /**
     * conversionRateを設定します。
     * 
     * @param conversionRate
     *          conversionRate
     */
    public void setConversionRate(String conversionRate) {
      this.conversionRate = conversionRate;
    }

    /**
     * labelを設定します。
     * 
     * @param label
     *          label
     */
    public void setLabel(String label) {
      this.label = label;
    }

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1070110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.analysis.AccessLogBean.0");
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
    setClientGroupCondition(reqparam.get("clientGroup"));
    setSearchYear(reqparam.get("searchYear"));
    setSearchMonth(reqparam.get("searchMonth"));
    setSearchDay(reqparam.get("searchDay"));
    setDisplayMode(reqparam.get("displayMode"));
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
   * exportAuthorityを取得します。
   * 
   * @return exportAuthority
   */
  public boolean getExportAuthority() {
    return exportAuthority;
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
   * exportAuthorityを設定します。
   * 
   * @param exportAuthority
   *          exportAuthority
   */
  public void setExportAuthority(boolean exportAuthority) {
    this.exportAuthority = exportAuthority;
  }

  /**
   * searchDayを取得します。
   * 
   * @return searchDay
   */
  public String getSearchDay() {
    return searchDay;
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
   * searchYearを取得します。
   * 
   * @return searchYear
   */
  public String getSearchYear() {
    return searchYear;
  }

  /**
   * searchDayを設定します。
   * 
   * @param searchDay
   *          searchDay
   */
  public void setSearchDay(String searchDay) {
    this.searchDay = searchDay;
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
   * searchYearを設定します。
   * 
   * @param searchYear
   *          searchYear
   */
  public void setSearchYear(String searchYear) {
    this.searchYear = searchYear;
  }

  /**
   * searchResultDisplayを取得します。
   * 
   * @return searchResultDisplay
   */
  public Boolean getSearchResultDisplay() {
    return searchResultDisplay;
  }

  /**
   * searchResultDisplayを設定します。
   * 
   * @param searchResultDisplay
   *          searchResultDisplay
   */
  public void setSearchResultDisplay(Boolean searchResultDisplay) {
    this.searchResultDisplay = searchResultDisplay;
  }

  /**
   * columnDataを取得します。
   * 
   * @return columnData
   */
  public String getColumnData() {
    return columnData;
  }

  /**
   * graphDataを取得します。
   * 
   * @return graphData
   */
  public String getAccessLogBarGraphData() {
    return accessLogBarGraphData;
  }

  /**
   * columnDataを設定します。
   * 
   * @param columnData
   *          columnData
   */
  public void setColumnData(String columnData) {
    this.columnData = columnData;
  }

  /**
   * graphDataを設定します。
   * 
   * @param accessLogBarGraphData
   *          アクセスログ棒グラフ表示用データ
   */
  public void setAccessLogBarGraphData(String accessLogBarGraphData) {
    this.accessLogBarGraphData = accessLogBarGraphData;
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
   * conevrsionBarGraphDataを取得します。
   * 
   * @return conevrsionBarGraphData
   */
  public String getConevrsionBarGraphData() {
    return conevrsionBarGraphData;
  }

  /**
   * conevrsionBarGraphDataを設定します。
   * 
   * @param conevrsionBarGraphData
   *          conevrsionBarGraphData
   */
  public void setConevrsionBarGraphData(String conevrsionBarGraphData) {
    this.conevrsionBarGraphData = conevrsionBarGraphData;
  }

  /**
   * searchResultを取得します。
   * 
   * @return searchResult
   */
  public List<AccessLogBeanDetail> getSearchResult() {
    return searchResult;
  }

  /**
   * searchResultを設定します。
   * 
   * @param searchResult
   *          searchResult
   */
  public void setSearchResult(List<AccessLogBeanDetail> searchResult) {
    this.searchResult = searchResult;
  }

}
