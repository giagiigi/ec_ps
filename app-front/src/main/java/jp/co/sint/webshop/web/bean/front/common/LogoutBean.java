package jp.co.sint.webshop.web.bean.front.common;

import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2010210:ログアウトのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class LogoutBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String advertiseLinkUrl;

  private String advertiseImageUrl;

  /**
   * advertiseImageUrlを取得します。
   * 
   * @return advertiseImageUrl
   */
  public String getAdvertiseImageUrl() {
    return advertiseImageUrl;
  }

  /**
   * advertiseImageUrlを設定します。
   * 
   * @param advertiseImageUrl
   *          advertiseImageUrl
   */
  public void setAdvertiseImageUrl(String advertiseImageUrl) {
    this.advertiseImageUrl = advertiseImageUrl;
  }

  /**
   * advertiseLinkUrlを取得します。
   * 
   * @return advertiseLinkUrl
   */
  public String getAdvertiseLinkUrl() {
    return advertiseLinkUrl;
  }

  /**
   * advertiseLinkUrlを設定します。
   * 
   * @param advertiseLinkUrl
   *          advertiseLinkUrl
   */
  public void setAdvertiseLinkUrl(String advertiseLinkUrl) {
    this.advertiseLinkUrl = advertiseLinkUrl;
  }

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
    return "U2010210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.common.LogoutBean.0");
  }

}
