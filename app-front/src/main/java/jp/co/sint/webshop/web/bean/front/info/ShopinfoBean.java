package jp.co.sint.webshop.web.bean.front.info;

import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2050210:ショップ情報のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ShopinfoBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private Shop shopInfo = new Shop();

  /**
   * shopInfoを取得します。
   * 
   * @return shopInfo
   */
  public Shop getShopInfo() {
    return shopInfo;
  }

  /**
   * shopInfoを設定します。
   * 
   * @param shopInfo
   *          shopInfo
   */
  public void setShopInfo(Shop shopInfo) {
    this.shopInfo = shopInfo;
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
    return "U2050210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.info.ShopinfoBean.0");
  }

}
