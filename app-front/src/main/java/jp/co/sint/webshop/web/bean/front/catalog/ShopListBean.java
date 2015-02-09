package jp.co.sint.webshop.web.bean.front.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * のデータモデルです
 * 
 * @author System Integrator Corp.
 */
public class ShopListBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

  private String shopCode;

  public void createAttributes(RequestParameter reqparam) {

  }

  /**
   * shopListを取得します。
   * 
   * @return shopList
   */
  public List<CodeAttribute> getShopList() {
    return shopList;
  }

  /**
   * shopListを設定します。
   * 
   * @param shopList
   *          shopList
   */
  public void setShopList(List<CodeAttribute> shopList) {
    this.shopList = shopList;
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

}
