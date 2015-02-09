package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * 宣传品活动Bean对象。
 * 
 * @author Kousen.
 */
public class PropagandaActivityRuleListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<PropagandaActivityRuleDetail> list = new ArrayList<PropagandaActivityRuleDetail>();

  @AlphaNum2
  @Length(16)
  @Metadata(name = "活动编号", order = 1)
  private String searchActivityCode;

  @Length(100)
  @Metadata(name = "活动名称", order = 2)
  private String searchActivityName;

  private String searchActivityType;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "宣传品活动开始时间(From)", order = 3)
  private String searchActivityStartDateFrom;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "宣传品活动结束时间(To)", order = 4)
  private String searchActivityStartDateTo;

  private String searchStatus;

  private boolean displayUpdateButton;

  private boolean displayDeleteButton;

  private PagerValue pagerValue;

  public static class PropagandaActivityRuleDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String activityCode;

    private String activityName;

    private String activityStartDate;

    private String activityEndDate;

    private String activityType;

    private String languageType;

    /**
     * @return the activityCode
     */
    public String getActivityCode() {
      return activityCode;
    }

    /**
     * @return the activityName
     */
    public String getActivityName() {
      return activityName;
    }

    /**
     * @return the activityStartDate
     */
    public String getActivityStartDate() {
      return activityStartDate;
    }

    /**
     * @return the activityEndDate
     */
    public String getActivityEndDate() {
      return activityEndDate;
    }

    /**
     * @param activityCode
     *          the activityCode to set
     */
    public void setActivityCode(String activityCode) {
      this.activityCode = activityCode;
    }

    /**
     * @param activityName
     *          the activityName to set
     */
    public void setActivityName(String activityName) {
      this.activityName = activityName;
    }

    /**
     * @param activityStartDate
     *          the activityStartDate to set
     */
    public void setActivityStartDate(String activityStartDate) {
      this.activityStartDate = activityStartDate;
    }

    /**
     * @param activityEndDate
     *          the activityEndDate to set
     */
    public void setActivityEndDate(String activityEndDate) {
      this.activityEndDate = activityEndDate;
    }

    /**
     * @return the activityType
     */
    public String getActivityType() {
      return activityType;
    }

    /**
     * @param activityType
     *          the activityType to set
     */
    public void setActivityType(String activityType) {
      this.activityType = activityType;
    }

    /**
     * @return the languageType
     */
    public String getLanguageType() {
      return languageType;
    }

    /**
     * @param languageType
     *          the languageType to set
     */
    public void setLanguageType(String languageType) {
      this.languageType = languageType;
    }

  }

  /**
   * @return the list
   */
  public List<PropagandaActivityRuleDetail> getList() {
    return list;
  }

  /**
   * @return the searchActivityCode
   */
  public String getSearchActivityCode() {
    return searchActivityCode;
  }

  /**
   * @return the searchActivityName
   */
  public String getSearchActivityName() {
    return searchActivityName;
  }

  /**
   * @return the searchActivityType
   */
  public String getSearchActivityType() {
    return searchActivityType;
  }

  /**
   * @return the searchActivityStartDateFrom
   */
  public String getSearchActivityStartDateFrom() {
    return searchActivityStartDateFrom;
  }

  /**
   * @return the searchActivityStartDateTo
   */
  public String getSearchActivityStartDateTo() {
    return searchActivityStartDateTo;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<PropagandaActivityRuleDetail> list) {
    this.list = list;
  }

  /**
   * @param searchActivityCode
   *          the searchActivityCode to set
   */
  public void setSearchActivityCode(String searchActivityCode) {
    this.searchActivityCode = searchActivityCode;
  }

  /**
   * @param searchActivityName
   *          the searchActivityName to set
   */
  public void setSearchActivityName(String searchActivityName) {
    this.searchActivityName = searchActivityName;
  }

  /**
   * @param searchActivityType
   *          the searchActivityType to set
   */
  public void setSearchActivityType(String searchActivityType) {
    this.searchActivityType = searchActivityType;
  }

  /**
   * @param searchActivityStartDateFrom
   *          the searchActivityStartDateFrom to set
   */
  public void setSearchActivityStartDateFrom(String searchActivityStartDateFrom) {
    this.searchActivityStartDateFrom = searchActivityStartDateFrom;
  }

  /**
   * @param searchActivityStartDateTo
   *          the searchActivityStartDateTo to set
   */
  public void setSearchActivityStartDateTo(String searchActivityStartDateTo) {
    this.searchActivityStartDateTo = searchActivityStartDateTo;
  }

  public String getSearchStatus() {
    return searchStatus;
  }

  public void setSearchStatus(String searchStatus) {
    this.searchStatus = searchStatus;
  }

  public boolean isDisplayUpdateButton() {
    return displayUpdateButton;
  }

  public void setDisplayUpdateButton(boolean displayUpdateButton) {
    this.displayUpdateButton = displayUpdateButton;
  }

  public boolean isDisplayDeleteButton() {
    return displayDeleteButton;
  }

  public void setDisplayDeleteButton(boolean displayDeleteButton) {
    this.displayDeleteButton = displayDeleteButton;
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue pagerValue
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
    reqparam.copy(this);
    setSearchActivityStartDateFrom(reqparam.getDateTimeString("searchActivityStartDateFrom"));
    setSearchActivityStartDateTo(reqparam.getDateTimeString("searchActivityStartDateTo"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1061510";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.PropagandaActivityRuleListBean.0");
  }

}
