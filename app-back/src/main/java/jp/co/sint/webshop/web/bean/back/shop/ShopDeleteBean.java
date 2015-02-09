package jp.co.sint.webshop.web.bean.back.shop;

import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050240:ショップマスタ削除確認のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ShopDeleteBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private boolean deleteCompleteFlg;

  /**
   * deleteCompleteFlgを取得します。
   * 
   * @return deleteCompleteFlg
   */
  public boolean getDeleteCompleteFlg() {
    return deleteCompleteFlg;
  }

  /**
   * deleteCompleteFlgを設定します。
   * 
   * @param deleteCompleteFlg
   *          deleteCompleteFlg
   */
  public void setDeleteCompleteFlg(boolean deleteCompleteFlg) {
    this.deleteCompleteFlg = deleteCompleteFlg;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
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
    return "U1050240";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.ShopDeleteBean.0");
  }

}
