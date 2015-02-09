package jp.co.sint.webshop.web.bean.front.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2060410:お問い合わせ入力のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class InquiryEditBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Required
  @Metadata(name = "お問い合わせ件名")
  private String customerInquirySubject;

  @AlphaNum2
  @Metadata(name = "注文番号")
  private String orderNo;

  @AlphaNum2
  @Metadata(name = "商品コード")
  private String commodityCode;

  @Metadata(name = "商品名")
  private String commodityName;

  @Metadata(name = "ショップコード")
  private String shopCode;

  @Metadata(name = "ショップ名")
  private String shopName;

  @AlphaNum2
  @Metadata(name = "顧客コード")
  private String customerCode;

  @Required
  @Length(40)
  @Metadata(name = "お名前")
  private String customerName;

  @Required
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス")
  private String email;

  @Required
  @Length(10000)
  @Metadata(name = "内容")
  private String inquiryDetail;

  /** 編集モード */
  private String editMode;

  private List<CodeAttribute> customerInquirySubjectList = new ArrayList<CodeAttribute>();

  private boolean commodityInquiryMode;

  private boolean orderInquiryMode;

  private String readonlyMode;

  private boolean confirmButtonDisplay;

  private boolean completeButtonDisplay;

  private boolean backButtonDisplay;

  /**
   * backButtonDisplayを返します。
   * 
   * @return the backButtonDisplay
   */
  public boolean isBackButtonDisplay() {
    return backButtonDisplay;
  }

  /**
   * backButtonDisplayを設定します。
   * 
   * @param backButtonDisplay
   *          設定する backButtonDisplay
   */
  public void setBackButtonDisplay(boolean backButtonDisplay) {
    this.backButtonDisplay = backButtonDisplay;
  }

  /**
   * customerNameを取得します。
   * 
   * @return customerName
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * customerNameを設定します。
   * 
   * @param customerName
   *          customerName
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  /**
   * commodityInquiryModeを取得します。
   * 
   * @return commodityInquiryMode
   */
  public boolean isCommodityInquiryMode() {
    return commodityInquiryMode;
  }

  /**
   * commodityInquiryModeを設定します。
   * 
   * @param commodityInquiryMode
   *          commodityInquiryMode
   */
  public void setCommodityInquiryMode(boolean commodityInquiryMode) {
    this.commodityInquiryMode = commodityInquiryMode;
  }

  /**
   * orderInquiryModeを取得します。
   * 
   * @return orderInquiryMode
   */
  public boolean isOrderInquiryMode() {
    return orderInquiryMode;
  }

  /**
   * orderInquiryModeを設定します。
   * 
   * @param orderInquiryMode
   *          orderInquiryMode
   */
  public void setOrderInquiryMode(boolean orderInquiryMode) {
    this.orderInquiryMode = orderInquiryMode;
  }

  /**
   * customerInquirySubjectListを取得します。
   * 
   * @return customerInquirySubjectList
   */
  public List<CodeAttribute> getCustomerInquirySubjectList() {
    return customerInquirySubjectList;
  }

  /**
   * customerInquirySubjectListを設定します。
   * 
   * @param customerInquirySubjectList
   *          設定する customerInquirySubjectList
   */
  public void setCustomerInquirySubjectList(List<CodeAttribute> customerInquirySubjectList) {
    this.customerInquirySubjectList = customerInquirySubjectList;
  }

  /**
   * completeButtonDisplayを取得します。
   * 
   * @return completeButtonDisplay
   */
  public boolean isCompleteButtonDisplay() {
    return completeButtonDisplay;
  }

  /**
   * completeButtonDisplayを設定します。
   * 
   * @param completeButtonDisplay
   *          completeButtonDisplay
   */
  public void setCompleteButtonDisplay(boolean completeButtonDisplay) {
    this.completeButtonDisplay = completeButtonDisplay;
  }

  /**
   * confirmButtonDisplayを取得します。
   * 
   * @return confirmButtonDisplay
   */
  public boolean isConfirmButtonDisplay() {
    return confirmButtonDisplay;
  }

  /**
   * confirmButtonDisplayを設定します。
   * 
   * @param confirmButtonDisplay
   *          confirmButtonDisplay
   */
  public void setConfirmButtonDisplay(boolean confirmButtonDisplay) {
    this.confirmButtonDisplay = confirmButtonDisplay;
  }

  /**
   * readonlyModeを取得します。
   * 
   * @return readonlyMode
   */
  public String getReadonlyMode() {
    return readonlyMode;
  }

  /**
   * readonlyModeを設定します。
   * 
   * @param readonlyMode
   *          readonlyMode
   */
  public void setReadonlyMode(String readonlyMode) {
    this.readonlyMode = readonlyMode;
  }

  /**
   * commodityCodeを取得します。
   * 
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * commodityNameを取得します。
   * 
   * @return commodityName
   */
  public String getCommodityName() {
    return commodityName;
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
   * customerInquirySubjectを取得します。
   * 
   * @return customerInquirySubject
   */
  public String getCustomerInquirySubject() {
    return customerInquirySubject;
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
   * inquiryDetailを取得します。
   * 
   * @return inquiryDetail
   */
  public String getInquiryDetail() {
    return inquiryDetail;
  }

  /**
   * orderNoを取得します。
   * 
   * @return orderNo
   */
  public String getOrderNo() {
    return orderNo;
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
   * shopNameを取得します。
   * 
   * @return shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * commodityNameを設定します。
   * 
   * @param commodityName
   *          commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
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
   * customerInquirySubjectを設定します。
   * 
   * @param customerInquirySubject
   *          customerInquirySubject
   */
  public void setCustomerInquirySubject(String customerInquirySubject) {
    this.customerInquirySubject = customerInquirySubject;
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
   * inquiryDetailを設定します。
   * 
   * @param inquiryDetail
   *          inquiryDetail
   */
  public void setInquiryDetail(String inquiryDetail) {
    this.inquiryDetail = inquiryDetail;
  }

  /**
   * orderNoを設定します。
   * 
   * @param orderNo
   *          orderNo
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
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
   * shopNameを設定します。
   * 
   * @param shopName
   *          shopName
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
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
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2060410";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.customer.InquiryEditBean.0");
  }

  /**
   * editModeを取得します。
   * 
   * @return the editMode
   */
  public String getEditMode() {
    return editMode;
  }

  /**
   * editModeを設定します。
   * 
   * @param editMode
   *          editMode
   */
  public void setEditMode(String editMode) {
    this.editMode = editMode;
  }

}
