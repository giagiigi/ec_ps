package jp.co.sint.webshop.web.bean.back.service;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class InquiryEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CodeAttribute> largeCategoryList = new ArrayList<CodeAttribute>();

  private String categoryArrayForJs;

  @Required
  @AlphaNum2
  @Metadata(name = "会员编号")
  private String customerCode;

  @Required
  @Metadata(name = "会员名")
  private String customerName;

  @Required
  @Metadata(name = "咨询状态")
  private String inquiryStatus;

  @Required
  @Metadata(name = "大分类")
  private String largeCategory;

  @Required
  @Metadata(name = "关联小分类")
  private String smallCategory;

  @Required
  @Metadata(name = "咨询途径")
  private String inquiryWay;

  @Required
  @Length(200)
  @Metadata(name = "咨询主题")
  private String inquirySubject;

  @Required
  @Length(2000)
  @Metadata(name = "咨询内容")
  private String inquiryContents;

  @Required
  @Metadata(name = "IB/OB区分")
  private String ibObType;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品编号")
  private String commodityCode;

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
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
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
    return "U1090120";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.service.InquiryEditBean.0");
  }
}
