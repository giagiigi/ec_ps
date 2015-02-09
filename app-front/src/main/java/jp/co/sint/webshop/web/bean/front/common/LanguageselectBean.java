package jp.co.sint.webshop.web.bean.front.common;

import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040110:トップページのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class LanguageselectBean extends UIFrontBean {
  private List<NameValue> displayLanguageTypeList = NameValue.asList(LanguageCode.Zh_Cn.getValue()+":"
      + "中文" + "/" + LanguageCode.Ja_Jp.getValue() + ":"
      + "日文" + "/" + LanguageCode.En_Us.getValue() + ":"
      + "英文");
  
  private String searchLanguageType;
  
  
  public List<NameValue> getDisplayLanguageTypeList() {
    return displayLanguageTypeList;
  }

  
  public void setDisplayLanguageTypeList(List<NameValue> displayLanguageTypeList) {
    this.displayLanguageTypeList = displayLanguageTypeList;
  }

  
  public String getSearchLanguageType() {
    return searchLanguageType;
  }

  
  public void setSearchLanguageType(String searchLanguageType) {
    this.searchLanguageType = searchLanguageType;
  }

  @Override
  public void setSubJspId() {
  }

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2040110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return "语言选择";
  }

}
