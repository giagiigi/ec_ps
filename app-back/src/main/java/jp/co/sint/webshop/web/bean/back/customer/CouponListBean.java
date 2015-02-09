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
public class CouponListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CouponDetailBean> list = new ArrayList<CouponDetailBean>();

  private String customerCode;

  private String email;

  private String name;

  @Phone
  private String phoneNumber;

  @MobileNumber
  private String mobileNumber;

  private boolean usableCouponSystem;

  private String searchCouponStatus;

  private boolean updateMode;

  private boolean deleteMode;

  private PointHistoryEdit edit = new PointHistoryEdit();

  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード")
  private String searchShopCode;

  private List<CodeAttribute> shopList;

  private String searchIssueType;

  private PagerValue pagerValue;

  private Date updatedDatetime;

  public static class PointHistoryEdit implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 電子クーポンID */
    @Required
    @Length(38)
    @Metadata(name = "電子クーポンID", order = 1)
    private String couponIssueNo;

    @Length(50)
    @Metadata(name = "ポイント付与行使理由", order = 2)
    private String description;

    public String getCouponIssueNo() {
      return couponIssueNo;
    }

    public void setCouponIssueNo(String couponIssueNo) {
      this.couponIssueNo = couponIssueNo;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

  }

  /**
   * U1030140:ポイント履歴のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CouponDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String customerCouponId;

    private String couponIssueId;

    private String couponName;

    private String couponPrice;

    private String useCouponStartDate;

    private String useCouponEndDate;

    private String couponStatus;

    private String useDate;

    private String issueDate;

    private String getCouponOrderNo;

    private String useCouponOrderNo;

    public String getCouponIssueId() {
      return couponIssueId;
    }

    public void setCouponIssueId(String couponIssueId) {
      this.couponIssueId = couponIssueId;
    }

    public String getCouponName() {
      return couponName;
    }

    public void setCouponName(String couponName) {
      this.couponName = couponName;
    }

    public String getCouponPrice() {
      return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
      this.couponPrice = couponPrice;
    }

    public String getCouponStatus() {
      return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
      this.couponStatus = couponStatus;
    }

    public String getCustomerCouponId() {
      return customerCouponId;
    }

    public void setCustomerCouponId(String customerCouponId) {
      this.customerCouponId = customerCouponId;
    }

    public String getIssueDate() {
      return issueDate;
    }

    public void setIssueDate(String issueDate) {
      this.issueDate = issueDate;
    }

    public String getUseCouponEndDate() {
      return useCouponEndDate;
    }

    public void setUseCouponEndDate(String useCouponEndDate) {
      this.useCouponEndDate = useCouponEndDate;
    }

    public String getUseCouponStartDate() {
      return useCouponStartDate;
    }

    public void setUseCouponStartDate(String useCouponStartDate) {
      this.useCouponStartDate = useCouponStartDate;
    }

    public String getUseDate() {
      return useDate;
    }

    public void setUseDate(String useDate) {
      this.useDate = useDate;
    }

    public String getGetCouponOrderNo() {
      return getCouponOrderNo;
    }

    public void setGetCouponOrderNo(String getCouponOrderNo) {
      this.getCouponOrderNo = getCouponOrderNo;
    }

    public String getUseCouponOrderNo() {
      return useCouponOrderNo;
    }

    public void setUseCouponOrderNo(String useCouponOrderNo) {
      this.useCouponOrderNo = useCouponOrderNo;
    }

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
   * emailを取得します。
   * 
   * @return email
   */
  public String getEmail() {
    return email;
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
   * customerCodeを設定します。
   * 
   * @param customerCode
   *          customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
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
   * telを設定します。
   * 
   * @param phoneNumber
   *          phoneNumber
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
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
    edit.setCouponIssueNo(reqparam.get("couponIssueNo"));
    this.setSearchShopCode(reqparam.get("searchShopCode"));
    this.setSearchCouponStatus(reqparam.get("searchCouponStatus"));

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
    return Messages.getString("web.bean.back.customer.CouponListBean.0");
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

  public List<CouponDetailBean> getList() {
    return list;
  }

  public void setList(List<CouponDetailBean> list) {
    this.list = list;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getUsableCouponSystem() {
    return usableCouponSystem;
  }

  public void setUsableCouponSystem(boolean usableCouponSystem) {
    this.usableCouponSystem = usableCouponSystem;
  }

  public String getSearchCouponStatus() {
    return searchCouponStatus;
  }

  public void setSearchCouponStatus(String searchCouponStatus) {
    this.searchCouponStatus = searchCouponStatus;
  }

  public PointHistoryEdit getEdit() {
    return edit;
  }

  public void setEdit(PointHistoryEdit edit) {
    this.edit = edit;
  }

}
