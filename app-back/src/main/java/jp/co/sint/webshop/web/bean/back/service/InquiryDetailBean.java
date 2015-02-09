package jp.co.sint.webshop.web.bean.back.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class InquiryDetailBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue = new PagerValue();

  private List<DetailSearchedBean> inquiryDetailList = new ArrayList<DetailSearchedBean>();

  /** 咨询编号 */
  private String inquiryHeaderNo;

  /** 受理日期 */
  private String acceptDate;

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

  /** 咨询状态 */
  private String inquiryStatus;

  private String ibObType;

  private String commodityCode;

  @Required
  @Length(2000)
  @Metadata(name = "咨询内容")
  private String replyInquiryContents;

  @Required
  @Metadata(name = "咨询状态")
  private String replyInquiryStatus;

  private boolean replyLastPage = false;

  private boolean displayReplyButton = false;

  private boolean displayEditButton = false;

  private boolean displayDeleteButton = false;

  /**
   * @return the displayReplyButton
   */
  public boolean isDisplayReplyButton() {
    return displayReplyButton;
  }

  /**
   * @param displayReplyButton
   *          the displayReplyButton to set
   */
  public void setDisplayReplyButton(boolean displayReplyButton) {
    this.displayReplyButton = displayReplyButton;
  }

  /**
   * @return the displayEditButton
   */
  public boolean isDisplayEditButton() {
    return displayEditButton;
  }

  /**
   * @param displayEditButton
   *          the displayEditButton to set
   */
  public void setDisplayEditButton(boolean displayEditButton) {
    this.displayEditButton = displayEditButton;
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
   * @return the replyInquiryContents
   */
  public String getReplyInquiryContents() {
    return replyInquiryContents;
  }

  /**
   * @param replyInquiryContents
   *          the replyInquiryContents to set
   */
  public void setReplyInquiryContents(String replyInquiryContents) {
    this.replyInquiryContents = replyInquiryContents;
  }

  /**
   * @return the replyInquiryStatus
   */
  public String getReplyInquiryStatus() {
    return replyInquiryStatus;
  }

  /**
   * @param replyInquiryStatus
   *          the replyInquiryStatus to set
   */
  public void setReplyInquiryStatus(String replyInquiryStatus) {
    this.replyInquiryStatus = replyInquiryStatus;
  }

  /**
   * @return the replyLastPage
   */
  public boolean isReplyLastPage() {
    return replyLastPage;
  }

  /**
   * @param replyLastPage
   *          the replyLastPage to set
   */
  public void setReplyLastPage(boolean replyLastPage) {
    this.replyLastPage = replyLastPage;
  }

  /**
   * @return the inquiryHeaderNo
   */
  public String getInquiryHeaderNo() {
    return inquiryHeaderNo;
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
   * @param inquiryHeaderNo
   *          the inquiryHeaderNo to set
   */
  public void setInquiryHeaderNo(String inquiryHeaderNo) {
    this.inquiryHeaderNo = inquiryHeaderNo;
  }

  /**
   * @return the inquiryDetailList
   */
  public List<DetailSearchedBean> getInquiryDetailList() {
    return inquiryDetailList;
  }

  /**
   * @param inquiryDetailList
   *          the inquiryDetailList to set
   */
  public void setInquiryDetailList(List<DetailSearchedBean> inquiryDetailList) {
    this.inquiryDetailList = inquiryDetailList;
  }

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
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  public static class DetailSearchedBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String inquiryHeaderNo;

    private String inquiryDetailNo;

    private String acceptUpdate;

    private String personInChargeName;

    private String personInChargeNo;

    private String inquiryStatus;

    private String inquiryContents;

    private boolean replyDetail;

    private String rowNo;

    /**
     * @return the rowNo
     */
    public String getRowNo() {
      return rowNo;
    }

    /**
     * @param rowNo
     *          the rowNo to set
     */
    public void setRowNo(String rowNo) {
      this.rowNo = rowNo;
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
     * @return the inquiryDetailNo
     */
    public String getInquiryDetailNo() {
      return inquiryDetailNo;
    }

    /**
     * @param inquiryDetailNo
     *          the inquiryDetailNo to set
     */
    public void setInquiryDetailNo(String inquiryDetailNo) {
      this.inquiryDetailNo = inquiryDetailNo;
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
     * @return the inquiryContents
     */
    public String getInquiryContents() {
      return inquiryContents;
    }

    /**
     * @param inquiryContents
     *          the inquiryContents to set
     */
    public void setInquiryContents(String inquiryContents) {
      this.inquiryContents = inquiryContents;
    }

    /**
     * @return the replyDetail
     */
    public boolean isReplyDetail() {
      return replyDetail;
    }

    /**
     * @param replyDetail
     *          the replyDetail to set
     */
    public void setReplyDetail(boolean replyDetail) {
      this.replyDetail = replyDetail;
    }

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
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    setReplyLastPage(StringUtil.hasValue(reqparam.get("replyLastPage")));
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
    return Messages.getString("web.bean.back.service.InquiryDetailBean.0");
  }
}
