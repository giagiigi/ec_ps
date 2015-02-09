package jp.co.sint.webshop.web.bean.back.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class InquiryListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue = new PagerValue();

  private List<CodeAttribute> largeCategoryList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> inquiryStatusList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> inquiryWayList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> ibObTypeList = new ArrayList<CodeAttribute>();

  private List<InquirySearchedBean> inquiryList = new ArrayList<InquirySearchedBean>();

  private List<String> checkedInquiryNoList = new ArrayList<String>();

  @Length(11)
  @MobileNumber
  @Metadata(name = "手机号码", order = 1)
  private String searchMobile;

  @Length(40)
  @Metadata(name = "会员姓名", order = 2)
  private String searchCustomerName;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "会员编号", order = 3)
  private String searchCustomerCode;

  @Length(20)
  @Metadata(name = "担当者名", order = 4)
  private String searchPersonInChargeName;

  @Length(20)
  @AlphaNum2
  @Metadata(name = "担当者编号", order = 5)
  private String searchPersonInChargeNo;

  @Datetime
  @Metadata(name = "受理日期(From)", order = 6)
  private String searchAcceptDateFrom;

  @Datetime
  @Metadata(name = "受理日期(To)", order = 7)
  private String searchAcceptDateTo;

  @Datetime
  @Metadata(name = "更新日期(From)", order = 8)
  private String searchAcceptUpdateFrom;

  @Datetime
  @Metadata(name = "更新日期(To)", order = 9)
  private String searchAcceptUpdateTo;

  private String searchInquiryStatus;

  private String searchLargeCategory;

  private String searchSmallCategory;

  private String searchInquiryWay;

  private String searchIbObType;

  private String searchInquirySubject;

  private String categoryArrayForJs;

  private String countMessage;

  private boolean displayMemberButton = false;

  private boolean displayExportButton = false;

  private boolean displayDeleteButton = false;

  public static class InquirySearchedBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 咨询编号 */
    private String inquiryHeaderNo;

    /** 受理日期(年月日) */
    private String acceptDate;

    /** 受理日期(时分秒) */
    private String acceptDateTime;

    /** 会员名 */
    private String customerName;

    /** 会员编号 */
    private String customerCode;

    /** 手机号码 */
    private String mobile;

    /** 大分类 */
    private String largeCategory;

    /** 关联小分类 */
    private String smallCategory;

    /** 咨询途径 */
    private String inquiryWay;

    /** 咨询主题 */
    private String inquirySubject;

    /** 最终担当者 */
    private String personInChargeName;

    /** 担当者编号 */
    private String personInChargeNo;

    /** 更新时间(年月日) */
    private String acceptUpdate;

    /** 更新时间(时分秒) */
    private String acceptUpdateTime;

    /** 咨询状态 */
    private String inquiryStatus;

    /** IB/OB区分 */
    private String ibObType;

    /**
     * @return the ibObType
     */
    public String getIbObType() {
      return ibObType;
    }

    /**
     * @param ibObType
     *          the ibObType to set
     */
    public void setIbObType(String ibObType) {
      this.ibObType = ibObType;
    }

    /**
     * @return the inquiryHeaderNo
     */
    public String getInquiryHeaderNo() {
      return inquiryHeaderNo;
    }

    /**
     * @param inquiryHeaderNo
     *          the inquiryHeaderNo to set
     */
    public void setInquiryHeaderNo(String inquiryHeaderNo) {
      this.inquiryHeaderNo = inquiryHeaderNo;
    }

    /**
     * @return the acceptDate
     */
    public String getAcceptDate() {
      return acceptDate;
    }

    /**
     * @param acceptDate
     *          the acceptDate to set
     */
    public void setAcceptDate(String acceptDate) {
      this.acceptDate = acceptDate;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
      return customerName;
    }

    /**
     * @param customerName
     *          the customerName to set
     */
    public void setCustomerName(String customerName) {
      this.customerName = customerName;
    }

    /**
     * @return the customerCode
     */
    public String getCustomerCode() {
      return customerCode;
    }

    /**
     * @param customerCode
     *          the customerCode to set
     */
    public void setCustomerCode(String customerCode) {
      this.customerCode = customerCode;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
      return mobile;
    }

    /**
     * @param mobile
     *          the mobile to set
     */
    public void setMobile(String mobile) {
      this.mobile = mobile;
    }

    /**
     * @return the largeCategory
     */
    public String getLargeCategory() {
      return largeCategory;
    }

    /**
     * @param largeCategory
     *          the largeCategory to set
     */
    public void setLargeCategory(String largeCategory) {
      this.largeCategory = largeCategory;
    }

    /**
     * @return the smallCategory
     */
    public String getSmallCategory() {
      return smallCategory;
    }

    /**
     * @param smallCategory
     *          the smallCategory to set
     */
    public void setSmallCategory(String smallCategory) {
      this.smallCategory = smallCategory;
    }

    /**
     * @return the inquiryWay
     */
    public String getInquiryWay() {
      return inquiryWay;
    }

    /**
     * @param inquiryWay
     *          the inquiryWay to set
     */
    public void setInquiryWay(String inquiryWay) {
      this.inquiryWay = inquiryWay;
    }

    /**
     * @return the inquirySubject
     */
    public String getInquirySubject() {
      return inquirySubject;
    }

    /**
     * @param inquirySubject
     *          the inquirySubject to set
     */
    public void setInquirySubject(String inquirySubject) {
      this.inquirySubject = inquirySubject;
    }

    /**
     * @return the personInChargeName
     */
    public String getPersonInChargeName() {
      return personInChargeName;
    }

    /**
     * @param personInChargeName
     *          the personInChargeName to set
     */
    public void setPersonInChargeName(String personInChargeName) {
      this.personInChargeName = personInChargeName;
    }

    /**
     * @return the personInChargeNo
     */
    public String getPersonInChargeNo() {
      return personInChargeNo;
    }

    /**
     * @param personInChargeNo
     *          the personInChargeNo to set
     */
    public void setPersonInChargeNo(String personInChargeNo) {
      this.personInChargeNo = personInChargeNo;
    }

    /**
     * @return the acceptUpdate
     */
    public String getAcceptUpdate() {
      return acceptUpdate;
    }

    /**
     * @param acceptUpdate
     *          the acceptUpdate to set
     */
    public void setAcceptUpdate(String acceptUpdate) {
      this.acceptUpdate = acceptUpdate;
    }

    /**
     * @return the inquiryStatus
     */
    public String getInquiryStatus() {
      return inquiryStatus;
    }

    /**
     * @param inquiryStatus
     *          the inquiryStatus to set
     */
    public void setInquiryStatus(String inquiryStatus) {
      this.inquiryStatus = inquiryStatus;
    }

    /**
     * @return the acceptDateTime
     */
    public String getAcceptDateTime() {
      return acceptDateTime;
    }

    /**
     * @param acceptDateTime
     *          the acceptDateTime to set
     */
    public void setAcceptDateTime(String acceptDateTime) {
      this.acceptDateTime = acceptDateTime;
    }

    /**
     * @return the acceptUpdateTime
     */
    public String getAcceptUpdateTime() {
      return acceptUpdateTime;
    }

    /**
     * @param acceptUpdateTime
     *          the acceptUpdateTime to set
     */
    public void setAcceptUpdateTime(String acceptUpdateTime) {
      this.acceptUpdateTime = acceptUpdateTime;
    }

  }

  /**
   * @return the countMessage
   */
  public String getCountMessage() {
    return countMessage;
  }

  /**
   * @param countMessage
   *          the countMessage to set
   */
  public void setCountMessage(String countMessage) {
    this.countMessage = countMessage;
  }

  /**
   * @return the largeCategoryList
   */
  public List<CodeAttribute> getLargeCategoryList() {
    return largeCategoryList;
  }

  /**
   * @param largeCategoryList
   *          the largeCategoryList to set
   */
  public void setLargeCategoryList(List<CodeAttribute> largeCategoryList) {
    this.largeCategoryList = largeCategoryList;
  }

  /**
   * @return the inquiryStatusList
   */
  public List<CodeAttribute> getInquiryStatusList() {
    return inquiryStatusList;
  }

  /**
   * @param inquiryStatusList
   *          the inquiryStatusList to set
   */
  public void setInquiryStatusList(List<CodeAttribute> inquiryStatusList) {
    this.inquiryStatusList = inquiryStatusList;
  }

  /**
   * @return the inquiryWayList
   */
  public List<CodeAttribute> getInquiryWayList() {
    return inquiryWayList;
  }

  /**
   * @param inquiryWayList
   *          the inquiryWayList to set
   */
  public void setInquiryWayList(List<CodeAttribute> inquiryWayList) {
    this.inquiryWayList = inquiryWayList;
  }

  /**
   * @return the ibObTypeList
   */
  public List<CodeAttribute> getIbObTypeList() {
    return ibObTypeList;
  }

  /**
   * @param ibObTypeList
   *          the ibObTypeList to set
   */
  public void setIbObTypeList(List<CodeAttribute> ibObTypeList) {
    this.ibObTypeList = ibObTypeList;
  }

  /**
   * @return the inquiryList
   */
  public List<InquirySearchedBean> getInquiryList() {
    return inquiryList;
  }

  /**
   * @param inquiryList
   *          the inquiryList to set
   */
  public void setInquiryList(List<InquirySearchedBean> inquiryList) {
    this.inquiryList = inquiryList;
  }

  /**
   * @return the categoryArrayForJs
   */
  public String getCategoryArrayForJs() {
    return categoryArrayForJs;
  }

  /**
   * @param categoryArrayForJs
   *          the categoryArrayForJs to set
   */
  public void setCategoryArrayForJs(String categoryArrayForJs) {
    this.categoryArrayForJs = categoryArrayForJs;
  }

  /**
   * @return the pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * @param pagerValue
   *          the pagerValue to set
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * @return the searchMobile
   */
  public String getSearchMobile() {
    return searchMobile;
  }

  /**
   * @param searchMobile
   *          the searchMobile to set
   */
  public void setSearchMobile(String searchMobile) {
    this.searchMobile = searchMobile;
  }

  /**
   * @return the searchCustomerName
   */
  public String getSearchCustomerName() {
    return searchCustomerName;
  }

  /**
   * @param searchCustomerName
   *          the searchCustomerName to set
   */
  public void setSearchCustomerName(String searchCustomerName) {
    this.searchCustomerName = searchCustomerName;
  }

  /**
   * @return the searchCustomerCode
   */
  public String getSearchCustomerCode() {
    return searchCustomerCode;
  }

  /**
   * @param searchCustomerCode
   *          the searchCustomerCode to set
   */
  public void setSearchCustomerCode(String searchCustomerCode) {
    this.searchCustomerCode = searchCustomerCode;
  }

  /**
   * @return the searchPersonInChargeName
   */
  public String getSearchPersonInChargeName() {
    return searchPersonInChargeName;
  }

  /**
   * @param searchPersonInChargeName
   *          the searchPersonInChargeName to set
   */
  public void setSearchPersonInChargeName(String searchPersonInChargeName) {
    this.searchPersonInChargeName = searchPersonInChargeName;
  }

  /**
   * @return the searchPersonInChargeNo
   */
  public String getSearchPersonInChargeNo() {
    return searchPersonInChargeNo;
  }

  /**
   * @param searchPersonInChargeNo
   *          the searchPersonInChargeNo to set
   */
  public void setSearchPersonInChargeNo(String searchPersonInChargeNo) {
    this.searchPersonInChargeNo = searchPersonInChargeNo;
  }

  /**
   * @return the searchAcceptDateFrom
   */
  public String getSearchAcceptDateFrom() {
    return searchAcceptDateFrom;
  }

  /**
   * @param searchAcceptDateFrom
   *          the searchAcceptDateFrom to set
   */
  public void setSearchAcceptDateFrom(String searchAcceptDateFrom) {
    this.searchAcceptDateFrom = searchAcceptDateFrom;
  }

  /**
   * @return the searchAcceptDateTo
   */
  public String getSearchAcceptDateTo() {
    return searchAcceptDateTo;
  }

  /**
   * @param searchAcceptDateTo
   *          the searchAcceptDateTo to set
   */
  public void setSearchAcceptDateTo(String searchAcceptDateTo) {
    this.searchAcceptDateTo = searchAcceptDateTo;
  }

  /**
   * @return the searchAcceptUpdateFrom
   */
  public String getSearchAcceptUpdateFrom() {
    return searchAcceptUpdateFrom;
  }

  /**
   * @param searchAcceptUpdateFrom
   *          the searchAcceptUpdateFrom to set
   */
  public void setSearchAcceptUpdateFrom(String searchAcceptUpdateFrom) {
    this.searchAcceptUpdateFrom = searchAcceptUpdateFrom;
  }

  /**
   * @return the searchAcceptUpdateTo
   */
  public String getSearchAcceptUpdateTo() {
    return searchAcceptUpdateTo;
  }

  /**
   * @param searchAcceptUpdateTo
   *          the searchAcceptUpdateTo to set
   */
  public void setSearchAcceptUpdateTo(String searchAcceptUpdateTo) {
    this.searchAcceptUpdateTo = searchAcceptUpdateTo;
  }

  /**
   * @return the searchInquiryStatus
   */
  public String getSearchInquiryStatus() {
    return searchInquiryStatus;
  }

  /**
   * @param searchInquiryStatus
   *          the searchInquiryStatus to set
   */
  public void setSearchInquiryStatus(String searchInquiryStatus) {
    this.searchInquiryStatus = searchInquiryStatus;
  }

  /**
   * @return the searchLargeCategory
   */
  public String getSearchLargeCategory() {
    return searchLargeCategory;
  }

  /**
   * @param searchLargeCategory
   *          the searchLargeCategory to set
   */
  public void setSearchLargeCategory(String searchLargeCategory) {
    this.searchLargeCategory = searchLargeCategory;
  }

  /**
   * @return the searchSmallCategory
   */
  public String getSearchSmallCategory() {
    return searchSmallCategory;
  }

  /**
   * @param searchSmallCategory
   *          the searchSmallCategory to set
   */
  public void setSearchSmallCategory(String searchSmallCategory) {
    this.searchSmallCategory = searchSmallCategory;
  }

  /**
   * @return the searchInquiryWay
   */
  public String getSearchInquiryWay() {
    return searchInquiryWay;
  }

  /**
   * @param searchInquiryWay
   *          the searchInquiryWay to set
   */
  public void setSearchInquiryWay(String searchInquiryWay) {
    this.searchInquiryWay = searchInquiryWay;
  }

  /**
   * @return the checkedInquiryNoList
   */
  public List<String> getCheckedInquiryNoList() {
    return checkedInquiryNoList;
  }

  /**
   * @param checkedInquiryNoList
   *          the checkedInquiryNoList to set
   */
  public void setCheckedInquiryNoList(List<String> checkedInquiryNoList) {
    this.checkedInquiryNoList = checkedInquiryNoList;
  }

  /**
   * @return the displayMemberButton
   */
  public boolean isDisplayMemberButton() {
    return displayMemberButton;
  }

  /**
   * @param displayMemberButton
   *          the displayMemberButton to set
   */
  public void setDisplayMemberButton(boolean displayMemberButton) {
    this.displayMemberButton = displayMemberButton;
  }

  /**
   * @return the displayExportButton
   */
  public boolean isDisplayExportButton() {
    return displayExportButton;
  }

  /**
   * @param displayExportButton
   *          the displayExportButton to set
   */
  public void setDisplayExportButton(boolean displayExportButton) {
    this.displayExportButton = displayExportButton;
  }

  /**
   * @return the searchIbObType
   */
  public String getSearchIbObType() {
    return searchIbObType;
  }

  /**
   * @param searchIbObType
   *          the searchIbObType to set
   */
  public void setSearchIbObType(String searchIbObType) {
    this.searchIbObType = searchIbObType;
  }

  /**
   * @return the displayDeleteButton
   */
  public boolean isDisplayDeleteButton() {
    return displayDeleteButton;
  }

  /**
   * @param displayDeleteButton
   *          the displayDeleteButton to set
   */
  public void setDisplayDeleteButton(boolean displayDeleteButton) {
    this.displayDeleteButton = displayDeleteButton;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    setCheckedInquiryNoList(Arrays.asList(reqparam.getAll("inquiryNo")));
    setSearchAcceptDateFrom(reqparam.getDateString("searchAcceptDateFrom"));
    setSearchAcceptDateTo(reqparam.getDateString("searchAcceptDateTo"));
    setSearchAcceptUpdateFrom(reqparam.getDateString("searchAcceptUpdateFrom"));
    setSearchAcceptUpdateTo(reqparam.getDateString("searchAcceptUpdateTo"));
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1090130";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.service.InquiryListBean.0");
  }

  
  /**
   * @return the searchInquirySubject
   */
  public String getSearchInquirySubject() {
    return searchInquirySubject;
  }

  
  /**
   * @param searchInquirySubject the searchInquirySubject to set
   */
  public void setSearchInquirySubject(String searchInquirySubject) {
    this.searchInquirySubject = searchInquirySubject;
  }
}
