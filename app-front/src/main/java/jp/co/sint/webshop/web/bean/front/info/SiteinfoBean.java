package jp.co.sint.webshop.web.bean.front.info;

import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2050110:サイト情報のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class SiteinfoBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private Shop siteInfo = new Shop();

  /**
   * siteInfoを取得します。
   * 
   * @return siteInfo
   */
  public Shop getSiteInfo() {
    return siteInfo;
  }

  /**
   * siteInfoを設定します。
   * 
   * @param siteInfo
   *          siteInfo
   */
  public void setSiteInfo(Shop siteInfo) {
    this.siteInfo = siteInfo;
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
    return "U2050110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.info.SiteinfoBean.0");
  }

}
