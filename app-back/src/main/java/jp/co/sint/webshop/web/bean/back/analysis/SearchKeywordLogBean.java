package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1070910:検索キーワード集計のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class SearchKeywordLogBean extends UIBackBean implements UISearchBean {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** 検索開始日 */
  @Datetime
  @Required
  @Metadata(name = "検索期間(From)", order = 1)
  private String searchStartDate;

  /** 検索終了日 */
  @Datetime
  @Required
  @Metadata(name = "検索期間(To)", order = 2)
  private String searchEndDate;

  /** 検索キーリスト */
  private List<CodeAttribute> searchKeyList = new ArrayList<CodeAttribute>();

  /** 検索キー */
  private String searchKeyCondition;

  /** 検索結果 */
  private List<SearchKeywordLogBeanDetail> searchResult = new ArrayList<SearchKeywordLogBeanDetail>();

  /** グラフ表示スケール */
  @Required
  @Digit
  private String scaleCondition;

  /** CSV出力権限有無 true:CSV出力可能 false:CSV出力不可 */
  private boolean exportAuthority;

  /** ページング制御 */
  private PagerValue pagerValue = new PagerValue();

  /**
   * U1070910:検索キーワード集計のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class SearchKeywordLogBeanDetail implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    /** 検索キー */
    private String searchKey;

    /** 検索ワード */
    private String searchWord;

    /** 検索回数 */
    private String searchCount;

    /** グラフ表示数 */
    private String graphCount;

    /** 端数の有無 */
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
     * searchCountを取得します。
     * 
     * @return searchCount
     */
    public String getSearchCount() {
      return searchCount;
    }

    /**
     * searchKeyを取得します。
     * 
     * @return searchKey
     */
    public String getSearchKey() {
      return searchKey;
    }

    /**
     * searchWordを取得します。
     * 
     * @return searchWord
     */
    public String getSearchWord() {
      return searchWord;
    }

    /**
     * searchCountを設定します。
     * 
     * @param searchCount
     *          searchCount
     */
    public void setSearchCount(String searchCount) {
      this.searchCount = searchCount;
    }

    /**
     * searchKeyを設定します。
     * 
     * @param searchKey
     *          searchKey
     */
    public void setSearchKey(String searchKey) {
      this.searchKey = searchKey;
    }

    /**
     * searchWordを設定します。
     * 
     * @param searchWord
     *          searchWord
     */
    public void setSearchWord(String searchWord) {
      this.searchWord = searchWord;
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
    this.setSearchKeyCondition(reqparam.get("searchKey"));
    this.setScaleCondition(reqparam.get("scale"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1070910";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.analysis.SearchKeywordLogBean.0");
  }

  /**
   * scaleを取得します。
   * 
   * @return scale
   */
  public String getScaleCondition() {
    return scaleCondition;
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
   * searchKeyを取得します。
   * 
   * @return searchKey
   */
  public String getSearchKeyCondition() {
    return searchKeyCondition;
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
   * scaleを設定します。
   * 
   * @param scaleCondition
   *          グラフ表示スケール
   */
  public void setScaleCondition(String scaleCondition) {
    this.scaleCondition = scaleCondition;
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
   * searchKeyを設定します。
   * 
   * @param searchKeyCondition
   *          検索キー
   */
  public void setSearchKeyCondition(String searchKeyCondition) {
    this.searchKeyCondition = searchKeyCondition;
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
   * searchKeyListを取得します。
   * 
   * @return searchKeyList
   */
  public List<CodeAttribute> getSearchKeyList() {
    return searchKeyList;
  }

  /**
   * searchKeyListを設定します。
   * 
   * @param searchKeyList
   *          searchKeyList
   */
  public void setSearchKeyList(List<CodeAttribute> searchKeyList) {
    this.searchKeyList = searchKeyList;
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
   * searchResultを取得します。
   * 
   * @return searchResult
   */
  public List<SearchKeywordLogBeanDetail> getSearchResult() {
    return searchResult;
  }

  /**
   * searchResultを設定します。
   * 
   * @param searchResult
   *          searchResult
   */
  public void setSearchResult(List<SearchKeywordLogBeanDetail> searchResult) {
    this.searchResult = searchResult;
  }

  /**
   * hasExportAuthorityを取得します。
   * 
   * @return exportAuthority
   */
  public boolean isExportAuthority() {
    return exportAuthority;
  }

  /**
   * hasExportAuthorityを設定します。
   * 
   * @param exportAuthority
   *          CSV出力権限有無 true:CSV出力可能 false:CSV出力不可
   */
  public void setExportAuthority(boolean exportAuthority) {
    this.exportAuthority = exportAuthority;
  }
}
