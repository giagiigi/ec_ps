package jp.co.sint.webshop.web.bean.back.catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040710:入荷お知らせのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CommodityCsvImportBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CommodityCsvImportBean> list = new ArrayList<CommodityCsvImportBean>();

  private boolean displayCsvImportButton = true;

  private String searchImportObject;

  private List<NameValue> displayImportObjectList = NameValue.asList("import-c_commodity_header:"
      + Messages.getString("web.bean.back.catalog.ImportObjectList.1") + "/import-c_commodity_detail:"
      + Messages.getString("web.bean.back.catalog.ImportObjectList.2") + "/import-c_commodity_data:"
      + Messages.getString("web.bean.back.catalog.ImportObjectList.3") + "/import-category_attribute_value_data:"
      + Messages.getString("web.bean.back.catalog.ImportObjectList.4") + "/import-c_commodity_initial_stage:"
      + Messages.getString("web.bean.back.catalog.ImportObjectList.5") + "/import-c_commodity_gift:"
      + Messages.getString("web.bean.back.catalog.ImportObjectList.6"));

  // 20130702 shen add start
  private List<CodeAttribute> syncFlagList = new ArrayList<CodeAttribute>();

  private String syncFlagTmall;

  // 20130702 shen add end
  
  // 2014/04/28 京东WBS对应 ob_卢 add start
  // JD同期标志
  private String syncFlagJd;
  // 2014/04/28 京东WBS对应 ob_卢 add end

  /**
   * @return the searchImportObject
   */
  public String getSearchImportObject() {
    return searchImportObject;
  }

  /**
   * @param searchImportObject
   *          the searchImportObject to set
   */
  public void setSearchImportObject(String searchImportObject) {
    this.searchImportObject = searchImportObject;
  }

  /**
   * @return the list
   */
  public List<CommodityCsvImportBean> getList() {
    return list;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<CommodityCsvImportBean> list) {
    this.list = list;
  }

  private boolean displayShopList = false;

  private boolean registerButtonDisplayFlg = true;

  private List<String> checkedCode = new ArrayList<String>();

  private PagerValue pagerValue;

  /**
   * displayShopListを取得します。
   * 
   * @return displayShopList
   */
  public boolean isDisplayShopList() {
    return displayShopList;
  }

  /**
   * displayShopListを設定します。
   * 
   * @param displayShopList
   *          displayShopList
   */
  public void setDisplayShopList(boolean displayShopList) {
    this.displayShopList = displayShopList;
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
    this.setCheckedCode(Arrays.asList(reqparam.getAll("checkBox")));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1800002";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.CommodityCsvImportBean.0");
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
   * checkedCodeを取得します。
   * 
   * @return checkedCode
   */
  public List<String> getCheckedCode() {
    return checkedCode;
  }

  /**
   * checkedCodeを設定します。
   * 
   * @param checkedCode
   *          checkedCode
   */
  public void setCheckedCode(List<String> checkedCode) {
    this.checkedCode = checkedCode;
  }

  public void setRegisterButtonDisplayFlg(boolean registerButtonDisplayFlg) {
    this.registerButtonDisplayFlg = registerButtonDisplayFlg;
  }

  public boolean isRegisterButtonDisplayFlg() {
    return registerButtonDisplayFlg;
  }

  /**
   * @return the displayImportObjectList
   */
  public List<NameValue> getDisplayImportObjectList() {
    return displayImportObjectList;
  }

  /**
   * @param displayImportObjectList
   *          the displayImportObjectList to set
   */
  public void setDisplayImportObjectList(List<NameValue> displayImportObjectList) {
    this.displayImportObjectList = displayImportObjectList;
  }

  /**
   * @return the displayCsvImportButton
   */
  public boolean isDisplayCsvImportButton() {
    return displayCsvImportButton;
  }

  /**
   * @param displayCsvImportButton
   *          the displayCsvImportButton to set
   */
  public void setDisplayCsvImportButton(boolean displayCsvImportButton) {
    this.displayCsvImportButton = displayCsvImportButton;
  }

  /**
   * @return the syncFlagList
   */
  public List<CodeAttribute> getSyncFlagList() {
    return syncFlagList;
  }

  /**
   * @param syncFlagList
   *          the syncFlagList to set
   */
  public void setSyncFlagList(List<CodeAttribute> syncFlagList) {
    this.syncFlagList = syncFlagList;
  }

  /**
   * @return the syncFlagTmall
   */
  public String getSyncFlagTmall() {
    return syncFlagTmall;
  }

  /**
   * @param syncFlagTmall
   *          the syncFlagTmall to set
   */
  public void setSyncFlagTmall(String syncFlagTmall) {
    this.syncFlagTmall = syncFlagTmall;
  }

  /**
   * @return the syncFlagJd
   */
  public String getSyncFlagJd() {
    return syncFlagJd;
  }

  /**
   * @param syncFlagJd the syncFlagJd to set
   */
  public void setSyncFlagJd(String syncFlagJd) {
    this.syncFlagJd = syncFlagJd;
  }

}
