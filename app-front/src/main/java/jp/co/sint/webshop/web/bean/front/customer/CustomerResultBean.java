package jp.co.sint.webshop.web.bean.front.customer;

import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030240:お客様情報登録完了のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerResultBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String customerCode;

  private boolean hasCustomerAttributeFlg;

  private boolean updateFlg = false;

  private boolean hasNextUrl;

  private String escapeNextUrl;

  private boolean hasCookieFlg;

  private String advertUrl;

  private String emali;

  public String getEmali() {
    return emali;
  }

  public void setEmali(String emali) {
    this.emali = emali;
  }

  /**
   * advertUrlを取得します。
   * 
   * @return advertUrl advertUrl
   */
  public String getAdvertUrl() {
    return advertUrl;
  }

  /**
   * advertUrlを設定します。
   * 
   * @param advertUrl
   *          advertUrl
   */
  public void setAdvertUrl(String advertUrl) {
    this.advertUrl = advertUrl;
  }

  /**
   * hasCookieFlgを取得します。
   * 
   * @return hasCookieFlg hasCookieFlg
   */
  public boolean isHasCookieFlg() {
    return hasCookieFlg;
  }

  /**
   * hasCookieFlgを設定します。
   * 
   * @param hasCookieFlg
   *          hasCookieFlg
   */
  public void setHasCookieFlg(boolean hasCookieFlg) {
    this.hasCookieFlg = hasCookieFlg;
  }

  /**
   * hasNextUrlを取得します。
   * 
   * @return the hasNextUrl
   */
  public boolean isHasNextUrl() {
    return hasNextUrl;
  }

  /**
   * hasNextUrlを設定します。
   * 
   * @param hasNextUrl
   *          hasNextUrl
   */
  public void setHasNextUrl(boolean hasNextUrl) {
    this.hasNextUrl = hasNextUrl;
  }

  /**
   * customerCodeを取得します。
   * 
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
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
    return "U2030240";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.customer.CustomerResultBean.0");
  }

  /**
   * hasCustomerAttributeFlgを取得します。
   * 
   * @return the hasCustomerAttributeFlg
   */
  public boolean isHasCustomerAttributeFlg() {
    return hasCustomerAttributeFlg;
  }

  /**
   * hasCustomerAttributeFlgを設定します。
   * 
   * @param hasCustomerAttributeFlg
   *          hasCustomerAttributeFlg
   */
  public void setHasCustomerAttributeFlg(boolean hasCustomerAttributeFlg) {
    this.hasCustomerAttributeFlg = hasCustomerAttributeFlg;
  }

  /**
   * escapeNextUrlを取得します。
   * 
   * @return the escapeNextUrl
   */
  public String getEscapeNextUrl() {
    return escapeNextUrl;
  }

  /**
   * escapeNextUrlを設定します。
   * 
   * @param escapeNextUrl
   *          escapeNextUrl
   */
  public void setEscapeNextUrl(String escapeNextUrl) {
    this.escapeNextUrl = escapeNextUrl;
  }

  /**
   * updateFlgを取得します。
   * 
   * @return updateFlg
   */
  public boolean isUpdateFlg() {
    return updateFlg;
  }

  /**
   * updateFlgを設定します。
   * 
   * @param updateFlg
   *          updateFlg
   */
  public void setUpdateFlg(boolean updateFlg) {
    this.updateFlg = updateFlg;
  }

}
