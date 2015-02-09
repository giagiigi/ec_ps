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
 * 免邮促销Bean对象。
 * 
 * @author Kousen.
 */
public class FreePostageListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<FreePostageListBeanDetail> list = new ArrayList<FreePostageListBeanDetail>();

  @AlphaNum2
  @Length(16)
  @Metadata(name = "免邮促销编号", order = 1)
  private String searchFreePostageCode;

  @Length(50)
  @Metadata(name = "免邮促销名称", order = 2)
  private String searchFreePostageName;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "免邮促销开始时间(From)", order = 3)
  private String searchFreeStartDateFrom;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "免邮促销结束时间(To)", order = 4)
  private String searchFreeStartDateTo;

  private String searchStatus;

  private boolean displayUpdateButton;

  private boolean displayDeleteButton;

  private PagerValue pagerValue;

  public static class FreePostageListBeanDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String freePostageCode;

    private String freePostageName;

    private String freeStartDate;

    private String freeEndDate;

    /**
     * @return the freePostageCode
     */
    public String getFreePostageCode() {
      return freePostageCode;
    }

    /**
     * @return the freePostageName
     */
    public String getFreePostageName() {
      return freePostageName;
    }

    /**
     * @return the freeStartDate
     */
    public String getFreeStartDate() {
      return freeStartDate;
    }

    /**
     * @return the freeEndDate
     */
    public String getFreeEndDate() {
      return freeEndDate;
    }

    /**
     * @param freePostageCode
     *          the freePostageCode to set
     */
    public void setFreePostageCode(String freePostageCode) {
      this.freePostageCode = freePostageCode;
    }

    /**
     * @param freePostageName
     *          the freePostageName to set
     */
    public void setFreePostageName(String freePostageName) {
      this.freePostageName = freePostageName;
    }

    /**
     * @param freeStartDate
     *          the freeStartDate to set
     */
    public void setFreeStartDate(String freeStartDate) {
      this.freeStartDate = freeStartDate;
    }

    /**
     * @param freeEndDate
     *          the freeEndDate to set
     */
    public void setFreeEndDate(String freeEndDate) {
      this.freeEndDate = freeEndDate;
    }

  }

  public List<FreePostageListBeanDetail> getList() {
    return list;
  }

  public void setList(List<FreePostageListBeanDetail> list) {
    this.list = list;
  }

  /**
   * @return the searchFreePostageCode
   */
  public String getSearchFreePostageCode() {
    return searchFreePostageCode;
  }

  /**
   * @return the searchFreePostageName
   */
  public String getSearchFreePostageName() {
    return searchFreePostageName;
  }

  /**
   * @return the searchFreeStartDateFrom
   */
  public String getSearchFreeStartDateFrom() {
    return searchFreeStartDateFrom;
  }

  /**
   * @return the searchFreeStartDateTo
   */
  public String getSearchFreeStartDateTo() {
    return searchFreeStartDateTo;
  }

  /**
   * @param searchFreePostageCode
   *          the searchFreePostageCode to set
   */
  public void setSearchFreePostageCode(String searchFreePostageCode) {
    this.searchFreePostageCode = searchFreePostageCode;
  }

  /**
   * @param searchFreePostageName
   *          the searchFreePostageName to set
   */
  public void setSearchFreePostageName(String searchFreePostageName) {
    this.searchFreePostageName = searchFreePostageName;
  }

  /**
   * @param searchFreeStartDateFrom
   *          the searchFreeStartDateFrom to set
   */
  public void setSearchFreeStartDateFrom(String searchFreeStartDateFrom) {
    this.searchFreeStartDateFrom = searchFreeStartDateFrom;
  }

  /**
   * @param searchFreeStartDateTo
   *          the searchFreeStartDateTo to set
   */
  public void setSearchFreeStartDateTo(String searchFreeStartDateTo) {
    this.searchFreeStartDateTo = searchFreeStartDateTo;
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
    setSearchFreeStartDateFrom(reqparam.getDateTimeString("searchFreeStartDateFrom"));
    setSearchFreeStartDateTo(reqparam.getDateTimeString("searchFreeStartDateTo"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1061310";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.FreePostageListBean.0");
  }

}
