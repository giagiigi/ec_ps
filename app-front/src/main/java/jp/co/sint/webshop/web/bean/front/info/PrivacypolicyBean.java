package jp.co.sint.webshop.web.bean.front.info;

import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2050310:個人情報保護方針のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class PrivacypolicyBean extends UIFrontBean {

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
    return "U2050310";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.info.PrivacypolicyBean.0");
  }

}
