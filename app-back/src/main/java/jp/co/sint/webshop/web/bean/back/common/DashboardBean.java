package jp.co.sint.webshop.web.bean.back.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.CollectionUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.menu.back.TabMenu;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1010210:ダッシュボードのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class DashboardBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** ToDoリスト */
  private List<DashboardBeanDetail> todoSituation = new ArrayList<DashboardBeanDetail>();

  /** ToDoリスト表示有無 true:表示 false:非表示 */
  private boolean todoDisplay = false;

  /** サイト状況 */
  private List<String> siteSituation = new ArrayList<String>();

  /** サイト状況表示有無 true:表示 false:非表示 */
  private boolean siteDisplay = false;

  /** システムお知らせ一覧 */
  private List<MessageDetail> systemInformationMessages = new ArrayList<MessageDetail>();

  /** システムお知らせ表示有無 */
  private boolean informationDisplay = false;

  /** キャンペーン状況 */
  private List<DashboardBeanDetail> campaignSituation = new ArrayList<DashboardBeanDetail>();

  /** キャンペーン状況表示有無 true:表示 false:非表示 */
  private boolean campaignDisplay = false;

  /** 管理メニュー */
  private HashMap<String, List<MenuDetail>> usableFunction = new HashMap<String, List<MenuDetail>>();

  /**
   * U1010210:ダッシュボードのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class MessageDetail implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    private String startDatetime;

    private String endDatetime;

    private String message;

    /**
     * endDatetimeを取得します。
     * 
     * @return endDatetime
     */

    public String getEndDatetime() {
      return endDatetime;
    }

    /**
     * endDatetimeを設定します。
     * 
     * @param endDatetime
     *          endDatetime
     */
    public void setEndDatetime(String endDatetime) {
      this.endDatetime = endDatetime;
    }

    /**
     * messageを取得します。
     * 
     * @return message
     */

    public String getMessage() {
      return message;
    }

    /**
     * messageを設定します。
     * 
     * @param message
     *          message
     */
    public void setMessage(String message) {
      this.message = message;
    }

    /**
     * startDatetimeを取得します。
     * 
     * @return startDatetime
     */

    public String getStartDatetime() {
      return startDatetime;
    }

    /**
     * startDatetimeを設定します。
     * 
     * @param startDatetime
     *          startDatetime
     */
    public void setStartDatetime(String startDatetime) {
      this.startDatetime = startDatetime;
    }

  }

  /**
   * U1010210:ダッシュボードのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class DashboardBeanDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    // 表示メッセージ
    private String message = "";

    // 遷移先画面action名
    private String url = "";

    // 検索条件
    private HashMap<String, String[]> requestParameter = new HashMap<String, String[]>();

    /**
     * requestParameterを取得します。
     * 
     * @return requestParameter
     */

    public Map<String, String[]> getRequestParameter() {
      return requestParameter;
    }

    /**
     * requestParameterを設定します。
     * 
     * @param requestParameter
     *          requestParameter
     */
    public void setRequestParameter(Map<String, String[]> requestParameter) {
      CollectionUtil.copyAll(this.requestParameter, requestParameter);
    }

    /**
     * messageを取得します。
     * 
     * @return message
     */
    public String getMessage() {
      return message;
    }

    /**
     * messageを設定します。
     * 
     * @param message
     *          message
     */
    public void setMessage(String message) {
      this.message = message;
    }

    /**
     * urlを取得します。
     * 
     * @return url
     */
    public String getUrl() {
      return url;
    }

    /**
     * urlを設定します。
     * 
     * @param url
     *          url
     */
    public void setUrl(String url) {
      this.url = url;
    }
  }

  /**
   * U1010210:ダッシュボードのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class MenuDetail implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = -1L;

    private String name;

    private String url;

    /**
     * nameを取得します。
     * 
     * @return name
     */

    public String getName() {
      return name;
    }

    /**
     * nameを設定します。
     * 
     * @param name
     *          name
     */
    public void setName(String name) {
      this.name = name;
    }

    /**
     * urlを取得します。
     * 
     * @return url
     */

    public String getUrl() {
      return url;
    }

    /**
     * urlを設定します。
     * 
     * @param url
     *          url
     */
    public void setUrl(String url) {
      this.url = url;
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
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1010210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.common.DashboardBean.0");
  }

  /**
   * siteSituationを取得します。
   * 
   * @return siteSituation
   */
  public List<String> getSiteSituation() {
    return siteSituation;
  }

  /**
   * siteSituationを設定します。
   * 
   * @param siteSituation
   *          siteSituation
   */
  public void setSiteSituation(List<String> siteSituation) {
    this.siteSituation = siteSituation;
  }

  /**
   * todoSituationを取得します。
   * 
   * @return todoSituation
   */
  public List<DashboardBeanDetail> getTodoSituation() {
    return todoSituation;
  }

  /**
   * todoSituationを設定します。
   * 
   * @param todoSituation
   *          todoSituation
   */
  public void setTodoSituation(List<DashboardBeanDetail> todoSituation) {
    this.todoSituation = todoSituation;
  }

  /**
   * siteDisplayを取得します。
   * 
   * @return siteDisplay
   */
  public boolean isSiteDisplay() {
    return siteDisplay;
  }

  /**
   * siteDisplayを設定します。
   * 
   * @param siteDisplay
   *          siteDisplay
   */
  public void setSiteDisplay(boolean siteDisplay) {
    this.siteDisplay = siteDisplay;
  }

  /**
   * todoDisplayを取得します。
   * 
   * @return todoDisplay
   */
  public boolean isTodoDisplay() {
    return todoDisplay;
  }

  /**
   * todoDisplayを設定します。
   * 
   * @param todoDisplay
   *          todoDisplay
   */
  public void setTodoDisplay(boolean todoDisplay) {
    this.todoDisplay = todoDisplay;
  }

  /**
   * campaignDisplayを取得します。
   * 
   * @return campaignDisplay
   */
  public boolean isCampaignDisplay() {
    return campaignDisplay;
  }

  /**
   * campaignDisplayを設定します。
   * 
   * @param campaignDisplay
   *          campaignDisplay
   */
  public void setCampaignDisplay(boolean campaignDisplay) {
    this.campaignDisplay = campaignDisplay;
  }

  /**
   * campaignSituationを取得します。
   * 
   * @return campaignSituation
   */
  public List<DashboardBeanDetail> getCampaignSituation() {
    return campaignSituation;
  }

  /**
   * campaignSituationを設定します。
   * 
   * @param campaignSituation
   *          campaignSituation
   */
  public void setCampaignSituation(List<DashboardBeanDetail> campaignSituation) {
    this.campaignSituation = campaignSituation;
  }

  /**
   * informationDisplayを取得します。
   * 
   * @return informationDisplay
   */

  public boolean isInformationDisplay() {
    return informationDisplay;
  }

  /**
   * informationDisplayを設定します。
   * 
   * @param informationDisplay
   *          informationDisplay
   */
  public void setInformationDisplay(boolean informationDisplay) {
    this.informationDisplay = informationDisplay;
  }

  /**
   * systemInformationMessagesを取得します。
   * 
   * @return systemInformationMessages
   */

  public List<MessageDetail> getSystemInformationMessages() {
    return systemInformationMessages;
  }

  /**
   * systemInformationMessagesを設定します。
   * 
   * @param systemInformationMessages
   *          systemInformationMessages
   */
  public void setSystemInformationMessages(List<MessageDetail> systemInformationMessages) {
    this.systemInformationMessages = systemInformationMessages;
  }

  /**
   * usableFunctionを取得します。
   * 
   * @return usableFunction
   */

  public Map<String, List<MenuDetail>> getUsableFunction() {
    return usableFunction;
  }

  /**
   * usableFunctionを設定します。
   * 
   * @param usableFunction
   *          usableFunction
   */
  public void setUsableFunction(Map<String, List<MenuDetail>> usableFunction) {
    CollectionUtil.copyAll(this.usableFunction, usableFunction);
  }

  public TabMenu[] getTabMenu() {
    return ArrayUtil.immutableCopy(TabMenu.values());
  }
}
