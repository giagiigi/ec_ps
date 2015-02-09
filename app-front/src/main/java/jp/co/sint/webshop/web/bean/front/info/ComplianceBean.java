package jp.co.sint.webshop.web.bean.front.info;

import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2050710:特定商取引法に基づく表記のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ComplianceBean extends UIFrontBean {

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
    return "U2050710";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.info.ComplianceBean.0");
  }

}
