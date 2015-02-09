package jp.co.sint.webshop.web.bean.back.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.data.attribute.Point;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1030140:ポイント履歴のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class PointHistoryBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PointHistoryEdit edit = new PointHistoryEdit();

  private List<PointHistoryDetailList> list = new ArrayList<PointHistoryDetailList>();

  private String customerCode;

  private String email;

  private String lastName;

  private String firstName;

  private String lastNameKana;

  private String firstNameKana;

  @Phone
  private String phoneNumber;
  
  //Add by V10-CH start
  @MobileNumber
  private String mobileNumber;
  //Add by V10-CH end

  private String restPoint;

  private String temporaryPoint;

  private String expiredPointDate;

  private boolean updateMode;

  private boolean deleteMode;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード")
  private String searchShopCode;

  private List<CodeAttribute> shopList;

  private String searchIssueType;

  private PagerValue pagerValue;

  private Date updatedDatetime;

  private Boolean usablePointSystem;

  /**
   * U1030140:ポイント履歴のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PointHistoryDetailList implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String publisher;

    private String publisherId;

    private String shopCode;

    private String shopName;

    private String description;

    private String pointIssueStatus;

    private String issuedPoint;

    private String pointIssueDatetime;

    /**
     * pointIssueDatetimeを取得します。
     * 
     * @return pointIssueDatetime
     */
    public String getPointIssueDatetime() {
      return pointIssueDatetime;
    }

    /**
     * descriptionを取得します。
     * 
     * @return description
     */
    public String getDescription() {
      return description;
    }

    /**
     * pointIssueStatusを取得します。
     * 
     * @return pointIssueStatus
     */
    public String getPointIssueStatus() {
      return pointIssueStatus;
    }

    /**
     * publisherを取得します。
     * 
     * @return publisher
     */
    public String getPublisher() {
      return publisher;
    }

    /**
     * publisherIdを取得します。
     * 
     * @return publisherId
     */
    public String getPublisherId() {
      return publisherId;
    }

    /**
     * pointIssueDatetimeを設定します。
     * 
     * @param pointIssueDatetime
     *          pointIssueDatetime
     */
    public void setPointIssueDatetime(String pointIssueDatetime) {
      this.pointIssueDatetime = pointIssueDatetime;
    }

    /**
     * descriptionを設定します。
     * 
     * @param description
     *          description
     */
    public void setDescription(String description) {
      this.description = description;
    }

    /**
     * issuedPointを取得します。
     * 
     * @return issuedPoint
     */
    public String getIssuedPoint() {
      return issuedPoint;
    }

    /**
     * issuedPointを設定します。
     * 
     * @param issuedPoint
     *          issuedPoint
     */
    public void setIssuedPoint(String issuedPoint) {
      this.issuedPoint = issuedPoint;
    }

    /**
     * pointIssueStatusを設定します。
     * 
     * @param pointIssueStatus
     *          pointIssueStatus
     */
    public void setPointIssueStatus(String pointIssueStatus) {
      this.pointIssueStatus = pointIssueStatus;
    }

    /**
     * publisherを設定します。
     * 
     * @param publisher
     *          publisher
     */
    public void setPublisher(String publisher) {
      this.publisher = publisher;
    }

    /**
     * publisherIdを設定します。
     * 
     * @param publisherId
     *          publisherId
     */
    public void setPublisherId(String publisherId) {
      this.publisherId = publisherId;
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
     * shopCodeを設定します。
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
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
   * U1030140:ポイント履歴のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PointHistoryEdit implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Length(100)
    @Metadata(name = "ポイント付与行使理由", order = 1)
    private String description;

    @Required
    //@Precision(precision = 10, scale = 2)
    @Point
    @Metadata(name = "ポイント", order = 2)
    private String issuedPoint;

    /**
     * descriptionを取得します。
     * 
     * @return description
     */
    public String getDescription() {
      return description;
    }

    /**
     * descriptionを設定します。
     * 
     * @param description
     *          description
     */
    public void setDescription(String description) {
      this.description = description;
    }

    /**
     * issuedPointを取得します。
     * 
     * @return issuedPoint
     */
    public String getIssuedPoint() {
      return issuedPoint;
    }

    /**
     * issuedPointを設定します。
     * 
     * @param issuedPoint
     *          issuedPoint
     */
    public void setIssuedPoint(String issuedPoint) {
      this.issuedPoint = issuedPoint;
    }

  }

  /**
   * currentlyPointを取得します。
   * 
   * @return restPoint
   */
  public String getRestPoint() {
    return restPoint;
  }

  /**
   * customerCodeを取得します。
   * 
   * @return customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * editを取得します。
   * 
   * @return edit
   */
  public PointHistoryEdit getEdit() {
    return edit;
  }

  /**
   * emailを取得します。
   * 
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * firstNameを取得します。
   * 
   * @return firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * firstNameKanaを取得します。
   * 
   * @return firstNameKana
   */
  public String getFirstNameKana() {
    return firstNameKana;
  }

  /**
   * lastNameを取得します。
   * 
   * @return lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * lastNameKanaを取得します。
   * 
   * @return lastNameKana
   */
  public String getLastNameKana() {
    return lastNameKana;
  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<PointHistoryDetailList> getList() {
    return list;
  }

  /**
   * telを取得します。
   * 
   * @return phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * temporaryPointを取得します。
   * 
   * @return temporaryPoint
   */
  public String getTemporaryPoint() {
    return temporaryPoint;
  }

  /**
   * currentlyPointを設定します。
   * 
   * @param restPoint
   *          restPoint
   */
  public void setRestPoint(String restPoint) {
    this.restPoint = restPoint;
  }

  /**
   * customerCodeを設定します。
   * 
   * @param customerCode
   *          customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * editを設定します。
   * 
   * @param edit
   *          edit
   */
  public void setEdit(PointHistoryEdit edit) {
    this.edit = edit;
  }

  /**
   * emailを設定します。
   * 
   * @param email
   *          email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * firstNameを設定します。
   * 
   * @param firstName
   *          firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * firstNameKanaを設定します。
   * 
   * @param firstNameKana
   *          firstNameKana
   */
  public void setFirstNameKana(String firstNameKana) {
    this.firstNameKana = firstNameKana;
  }

  /**
   * lastNameを設定します。
   * 
   * @param lastName
   *          lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * lastNameKanaを設定します。
   * 
   * @param lastNameKana
   *          lastNameKana
   */
  public void setLastNameKana(String lastNameKana) {
    this.lastNameKana = lastNameKana;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<PointHistoryDetailList> list) {
    this.list = list;
  }

  /**
   * telを設定します。
   * 
   * @param phoneNumber
   *          phoneNumber
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * temporaryPointを設定します。
   * 
   * @param temporaryPoint
   *          temporaryPoint
   */
  public void setTemporaryPoint(String temporaryPoint) {
    this.temporaryPoint = temporaryPoint;
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
    edit.setDescription(reqparam.get("description"));
    edit.setIssuedPoint(reqparam.get("issuedPoint"));
    this.setSearchShopCode(reqparam.get("searchShopCode"));
    this.setSearchIssueType(reqparam.get("searchIssueType"));

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1030140";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.customer.PointHistoryBean.0");
  }

  /**
   * expiredPointDateを取得します。
   * 
   * @return expiredPointDate
   */
  public String getExpiredPointDate() {
    return expiredPointDate;
  }

  /**
   * expiredPointDateを設定します。
   * 
   * @param expiredPointDate
   *          expiredPointDate
   */
  public void setExpiredPointDate(String expiredPointDate) {
    this.expiredPointDate = expiredPointDate;
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
   * deleteModeを取得します。
   * 
   * @return deleteMode
   */
  public boolean isDeleteMode() {
    return deleteMode;
  }

  /**
   * deleteModeを設定します。
   * 
   * @param deleteMode
   *          deleteMode
   */
  public void setDeleteMode(boolean deleteMode) {
    this.deleteMode = deleteMode;
  }

  /**
   * updateModeを取得します。
   * 
   * @return updateMode
   */
  public boolean isUpdateMode() {
    return updateMode;
  }

  /**
   * updateModeを設定します。
   * 
   * @param updateMode
   *          updateMode
   */
  public void setUpdateMode(boolean updateMode) {
    this.updateMode = updateMode;
  }

  /**
   * searchIssueTypeを取得します。
   * 
   * @return searchIssueType
   */
  public String getSearchIssueType() {
    return searchIssueType;
  }

  /**
   * searchIssueTypeを設定します。
   * 
   * @param searchIssueType
   *          searchIssueType
   */
  public void setSearchIssueType(String searchIssueType) {
    this.searchIssueType = searchIssueType;
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
   * updatedDatetimeを取得します。
   * 
   * @return updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * updatedDatetimeを設定します。
   * 
   * @param updatedDatetime
   *          updatedDatetime
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
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
   * usablePointSystemを取得します。
   * 
   * @return usablePointSystem
   */
  public Boolean getUsablePointSystem() {
    return usablePointSystem;
  }

  /**
   * usablePointSystemを設定します。
   * 
   * @param usablePointSystem
   *          usablePointSystem
   */
  public void setUsablePointSystem(Boolean usablePointSystem) {
    this.usablePointSystem = usablePointSystem;
  }

  
  /**
   * mobileNumberを取得します。
   *
   * @return mobileNumber mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  
  /**
   * mobileNumberを設定します。
   *
   * @param mobileNumber 
   *          mobileNumber
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

}
