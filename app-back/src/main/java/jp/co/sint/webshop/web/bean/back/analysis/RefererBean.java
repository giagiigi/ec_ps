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
 * U1070130:リファラー集計のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class RefererBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 検索開始日 */
  @Datetime
  @Required
  @Metadata(name = "集計期間(From)", order = 1)
  private String searchStartDate;

  /** 検索終了日 */
  @Datetime
  @Required
  @Metadata(name = "集計期間(To)", order = 2)
  private String searchEndDate;

  /** クライアントグループ */
  @Metadata(name = "クライアント", order = 3)
  private String clientGroupCondition;

  /** クライアントグループリスト */
  private List<CodeAttribute> clientGroupList = new ArrayList<CodeAttribute>();

  /** グラフ表示スケール */
  @Digit
  @Required
  @Metadata(name = "表示スケール", order = 4)
  private String scaleCondition;

  /** ページング情報 */
  private PagerValue pagerValue = new PagerValue();

  /** CSV出力権限有無 true:CSV出力ボタン表示 false:CSV出力ボタン非表示 */
  private Boolean exportAuthority = false;

  /** 検索結果 */
  private List<RefererBeanDetail> searchResultList = new ArrayList<RefererBeanDetail>();

  /**
   * U1070130:リファラー集計のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RefererBeanDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** リファラーURL */
    private String refererUrl;

    /** グラフ表示数 */
    private String graphCount;

    /** 端数の有無 */
    private boolean fraction;

    /** リファラー数 */
    private String refererCount;

    /**
     * refererCountを取得します。
     * 
     * @return refererCount
     */
    public String getRefererCount() {
      return refererCount;
    }

    /**
     * refererCountを設定します。
     * 
     * @param refererCount
     *          refererCount
     */
    public void setRefererCount(String refererCount) {
      this.refererCount = refererCount;
    }

    /**
     * refererUrlを取得します。
     * 
     * @return refererUrl
     */
    public String getRefererUrl() {
      return refererUrl;
    }

    /**
     * refererUrlを設定します。
     * 
     * @param refererUrl
     *          refererUrl
     */
    public void setRefererUrl(String refererUrl) {
      this.refererUrl = refererUrl;
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
   * @param req
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter req) {
    this.setSearchStartDate(req.getDateString("searchStartDate"));
    this.setSearchEndDate(req.getDateString("searchEndDate"));
    this.setClientGroupCondition(req.get("clientGroup"));
    this.setScaleCondition(req.get("scale"));
    this.setSearchResultList(new ArrayList<RefererBeanDetail>());

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1070130";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.analysis.RefererBean.0");
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
   * searchResultListを取得します。
   * 
   * @return searchResultList
   */
  public List<RefererBeanDetail> getSearchResultList() {
    return searchResultList;
  }

  /**
   * searchResultListを設定します。
   * 
   * @param searchResultList
   *          searchResultList
   */
  public void setSearchResultList(List<RefererBeanDetail> searchResultList) {
    this.searchResultList = searchResultList;
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
  public Boolean getExportAuthority() {
    return exportAuthority;
  }

  /**
   * exportAuthorityを設定します。
   * 
   * @param exportAuthority
   *          exportAuthority
   */
  public void setExportAuthority(Boolean exportAuthority) {
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

}
