package jp.co.sint.webshop.web.bean.back.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050110:サイト設定のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class AdvertEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String advertNo;

  @Required
  @Metadata(name = "広告システム")
  private String enableFlg;

  @Required
  @Length(200)
  @Metadata(name = "広告内容")
  private String advertText;

  // @Required
  @Length(200)
  @Metadata(name = "広告内容")
  private String advertOrderText;
  
  private String modeDiv;

  private String advertType;

  private List<CodeAttribute> advertTypeList = new ArrayList<CodeAttribute>();

  private String customerAdvertType;

  private List<CodeAttribute> customerAdvertList = new ArrayList<CodeAttribute>();

  private String orderAdvertType;

  private List<CodeAttribute> orderAdvertList = new ArrayList<CodeAttribute>();

  private Date updateDatetime;

  private String updateDatetimeStr;

  private String readonlyMode;

  private boolean confirmButtonDisplay;

  private boolean registerButtonDisplay;

  private boolean backButtonDisplay;

  /**
   * backButtonDisplayを取得します。
   * 
   * @return backButtonDisplay
   */
  public boolean isBackButtonDisplay() {
    return backButtonDisplay;
  }

  /**
   * backButtonDisplayを設定します。
   * 
   * @param backButtonDisplay
   *          backButtonDisplay
   */
  public void setBackButtonDisplay(boolean backButtonDisplay) {
    this.backButtonDisplay = backButtonDisplay;
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
   * @param param
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter param) {
    param.copy(this);
    this.setCustomerAdvertType(param.get("customerAdvertType"));
    this.setOrderAdvertType(param.get("orderAdvertType"));
    this.setAdvertType(param.get("advertType"));
    this.setAdvertText(param.get("advertText"));
    this.setEnableFlg(param.get("enableFlg"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050840";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.AdvertEditBean.0");
  }

  public String getAdvertText() {
    return advertText;
  }

  public void setAdvertText(String advertText) {
    this.advertText = advertText;
  }

  public boolean isConfirmButtonDisplay() {
    return confirmButtonDisplay;
  }

  public void setConfirmButtonDisplay(boolean confirmButtonDisplay) {
    this.confirmButtonDisplay = confirmButtonDisplay;
  }

  public String getEnableFlg() {
    return enableFlg;
  }

  public void setEnableFlg(String enableFlg) {
    this.enableFlg = enableFlg;
  }

  public String getReadonlyMode() {
    return readonlyMode;
  }

  public void setReadonlyMode(String readonlyMode) {
    this.readonlyMode = readonlyMode;
  }

  public boolean isRegisterButtonDisplay() {
    return registerButtonDisplay;
  }

  public void setRegisterButtonDisplay(boolean registerButtonDisplay) {
    this.registerButtonDisplay = registerButtonDisplay;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public Date getUpdateDatetime() {
    return updateDatetime;
  }

  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = updateDatetime;
  }

  public String getUpdateDatetimeStr() {
    return updateDatetimeStr;
  }

  public void setUpdateDatetimeStr(String updateDatetimeStr) {
    this.updateDatetimeStr = updateDatetimeStr;
  }

  public String getAdvertNo() {
    return advertNo;
  }

  public void setAdvertNo(String advertNo) {
    this.advertNo = advertNo;
  }

  public String getAdvertOrderText() {
    return advertOrderText;
  }

  public void setAdvertOrderText(String advertOrderText) {
    this.advertOrderText = advertOrderText;
  }

  public List<CodeAttribute> getAdvertTypeList() {
    return advertTypeList;
  }

  public void setAdvertTypeList(List<CodeAttribute> advertTypeList) {
    this.advertTypeList = advertTypeList;
  }

  public List<CodeAttribute> getCustomerAdvertList() {
    return customerAdvertList;
  }

  public void setCustomerAdvertList(List<CodeAttribute> customerAdvertList) {
    this.customerAdvertList = customerAdvertList;
  }

  public String getCustomerAdvertType() {
    return customerAdvertType;
  }

  public void setCustomerAdvertType(String customerAdvertType) {
    this.customerAdvertType = customerAdvertType;
  }

  public List<CodeAttribute> getOrderAdvertList() {
    return orderAdvertList;
  }

  public void setOrderAdvertList(List<CodeAttribute> orderAdvertList) {
    this.orderAdvertList = orderAdvertList;
  }

  public String getOrderAdvertType() {
    return orderAdvertType;
  }

  public void setOrderAdvertType(String orderAdvertType) {
    this.orderAdvertType = orderAdvertType;
  }

  public String getAdvertType() {
    return advertType;
  }

  public void setAdvertType(String advertType) {
    this.advertType = advertType;
  }

  
  public String getModeDiv() {
    return modeDiv;
  }

  
  public void setModeDiv(String modeDiv) {
    this.modeDiv = modeDiv;
  }

}
