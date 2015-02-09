package jp.co.sint.webshop.web.bean.front.order;

import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2060120:アンケート完了のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class EnqueteResultBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** アンケートポイント */
  private String enqueteInvestPoint;

  private String advertiseLinkUrl;

  private String advertiseImageUrl;

  private boolean enqueteInvestPointDisplayFlg;

  public boolean isEnqueteInvestPointDisplayFlg() {
    return enqueteInvestPointDisplayFlg;
  }

  public void setEnqueteInvestPointDisplayFlg(boolean enqueteInvestPointDisplayFlg) {
    this.enqueteInvestPointDisplayFlg = enqueteInvestPointDisplayFlg;
  }

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
   * enqueteInvestPointを取得します。
   * 
   * @return enqueteInvestPoint
   */
  public String getEnqueteInvestPoint() {
    return enqueteInvestPoint;
  }

  /**
   * enqueteInvestPointを設定します。
   * 
   * @param enqueteInvestPoint
   *          enqueteInvestPoint
   */
  public void setEnqueteInvestPoint(String enqueteInvestPoint) {
    this.enqueteInvestPoint = enqueteInvestPoint;
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
    return "U2060120";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.order.EnqueteResultBean.0");
  }

}
