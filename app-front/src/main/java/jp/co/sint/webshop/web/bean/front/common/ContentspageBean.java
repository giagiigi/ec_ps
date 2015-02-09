package jp.co.sint.webshop.web.bean.front.common;

import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2010110:ログインのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ContentspageBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String type;

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
  }

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
    return "U2010110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.common.LoginBean.0");
  }

  
  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  
  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

}
