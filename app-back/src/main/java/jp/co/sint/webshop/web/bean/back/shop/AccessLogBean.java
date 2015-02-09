package jp.co.sint.webshop.web.bean.back.shop;

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
 * U1050940:管理側アクセスログのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class AccessLogBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private AccessLogDetail searchCondition = new AccessLogDetail();

  private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> operationList = new ArrayList<CodeAttribute>();

  private List<AccessLogDetail> searchResult = new ArrayList<AccessLogDetail>();

  private PagerValue pagerValue = new PagerValue();

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

    searchCondition.setShopCode(reqparam.get("shopCode"));
    searchCondition.setLoginId(reqparam.get("loginId"));
    searchCondition.setUserName(reqparam.get("userName"));
    searchCondition.setOperation(reqparam.get("operation"));
    searchCondition.setDatetimeFrom(reqparam.getDateTimeString("datetimeFrom"));
    searchCondition.setDatetimeTo(reqparam.getDateTimeString("datetimeTo"));

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050940";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.AccessLogBean.0");
  }

  /**
   * U1050940:管理側アクセスログのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class AccessLogDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    @AlphaNum2
    @Length(20)
    @Metadata(name = "管理者ID", order = 1)
    private String loginId;

    @Length(20)
    @Metadata(name = "管理ユーザ名", order = 2)
    private String userName;

    @Required
    @Datetime(format = "yyyy/MM/dd HH:mm:ss")
    @Metadata(name = "日時(From)", order = 3)
    private String datetimeFrom;

    @Required
    @Datetime(format = "yyyy/MM/dd HH:mm:ss")
    @Metadata(name = "日時(To)", order = 4)
    private String datetimeTo;

    private String date;

    private String operation;

    private String ipAddress;

    /**
     * dateを取得します。
     * 
     * @return date
     */
    public String getDate() {
      return date;
    }

    /**
     * ipAddressを取得します。
     * 
     * @return ipAddress
     */
    public String getIpAddress() {
      return ipAddress;
    }

    /**
     * loginIdを取得します。
     * 
     * @return loginId
     */
    public String getLoginId() {
      return loginId;
    }

    /**
     * operationを取得します。
     * 
     * @return operation
     */
    public String getOperation() {
      return operation;
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
     * userNameを取得します。
     * 
     * @return userName
     */
    public String getUserName() {
      return userName;
    }

    /**
     * dateを設定します。
     * 
     * @param date
     *          date
     */
    public void setDate(String date) {
      this.date = date;
    }

    /**
     * ipAddressを設定します。
     * 
     * @param ipAddress
     *          ipAddress
     */
    public void setIpAddress(String ipAddress) {
      this.ipAddress = ipAddress;
    }

    /**
     * loginIdを設定します。
     * 
     * @param loginId
     *          loginId
     */
    public void setLoginId(String loginId) {
      this.loginId = loginId;
    }

    /**
     * operationを設定します。
     * 
     * @param operation
     *          operation
     */
    public void setOperation(String operation) {
      this.operation = operation;
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
     * userNameを設定します。
     * 
     * @param userName
     *          userName
     */
    public void setUserName(String userName) {
      this.userName = userName;
    }

    /**
     * datetimeFromを取得します。
     *
     * @return datetimeFrom datetimeFrom
     */
    public String getDatetimeFrom() {
      return datetimeFrom;
    }

    /**
     * datetimeFromを設定します。
     *
     * @param datetimeFrom 
     *          datetimeFrom
     */
    public void setDatetimeFrom(String datetimeFrom) {
      this.datetimeFrom = datetimeFrom;
    }

    /**
     * datetimeToを取得します。
     *
     * @return datetimeTo datetimeTo
     */
    public String getDatetimeTo() {
      return datetimeTo;
    }

    /**
     * datetimeToを設定します。
     *
     * @param datetimeTo 
     *          datetimeTo
     */
    public void setDatetimeTo(String datetimeTo) {
      this.datetimeTo = datetimeTo;
    }

  }

  /**
   * searchConditionを取得します。
   * 
   * @return searchCondition
   */
  public AccessLogDetail getSearchCondition() {
    return searchCondition;
  }

  /**
   * searchResultを取得します。
   * 
   * @return searchResult
   */
  public List<AccessLogDetail> getSearchResult() {
    return searchResult;
  }

  /**
   * searchConditionを設定します。
   * 
   * @param searchCondition
   *          searchCondition
   */
  public void setSearchCondition(AccessLogDetail searchCondition) {
    this.searchCondition = searchCondition;
  }

  /**
   * searchResultを設定します。
   * 
   * @param searchResult
   *          searchResult
   */
  public void setSearchResult(List<AccessLogDetail> searchResult) {
    this.searchResult = searchResult;
  }

  /**
   * shopListを取得します。
   * 
   * @return the shopList
   */
  public List<CodeAttribute> getShopList() {
    return shopList;
  }

  /**
   * shopListを設定します。
   * 
   * @param shopList
   *          the shopList to set
   */
  public void setShopList(List<CodeAttribute> shopList) {
    this.shopList = shopList;
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
   * operationListを取得します。
   * 
   * @return the operationList
   */
  public List<CodeAttribute> getOperationList() {
    return operationList;
  }

  /**
   * operationListを設定します。
   * 
   * @param operationList
   *          the operationList to set
   */
  public void setOperationList(List<CodeAttribute> operationList) {
    this.operationList = operationList;
  }

}
