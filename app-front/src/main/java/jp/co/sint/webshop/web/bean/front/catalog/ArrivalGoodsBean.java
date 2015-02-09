package jp.co.sint.webshop.web.bean.front.catalog;

import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040710:入荷お知らせ登録のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ArrivalGoodsBean extends UIFrontBean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String skuCode;

  private String commodityCode;

  @Required
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス")
  private String email;

  /**
   * emailを取得します。
   * 
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * emailを設定します。
   * 
   * @param email
   *          email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    this.email = reqparam.get("email");
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2040710";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.catalog.ArrivalGoodsBean.0");
  }

  /**
   * commodityCodeを取得します。
   * 
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
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
   * skuCodeを取得します。
   * 
   * @return skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
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
   * skuCodeを設定します。
   * 
   * @param skuCode
   *          skuCode
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

}
