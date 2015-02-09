package jp.co.sint.webshop.web.bean.front.common;

import jp.co.sint.webshop.web.bean.UIMainBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * UIMainBeanを拡張したクラスです。
 * 
 * @author System Integrator Corp.
 */
public class UtilBean extends UIMainBean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

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
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return "";
  }

}
