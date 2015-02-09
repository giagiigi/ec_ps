package jp.co.sint.webshop.service.shop;

import jp.co.sint.webshop.service.SearchCondition;

public class PaymentmethodListSearchCondition extends SearchCondition {

  private static final long serialVersionUID = 1L;

  private boolean operatingMode;

  private boolean sitePermission;

  private String shopCode;

  /**
   * @return operatingMode
   */
  public boolean getOperatingMode() {
    return operatingMode;
  }

  /**
   * @param operatingMode
   *          設定する operatingMode
   */
  public void setOperatingMode(boolean operatingMode) {
    this.operatingMode = operatingMode;
  }

  /**
   * @return sitePermission
   */
  public boolean getSitePermission() {
    return sitePermission;
  }

  /**
   * @param sitePermission
   *          設定する sitePermission
   */
  public void setSitePermission(boolean sitePermission) {
    this.sitePermission = sitePermission;
  }

  /**
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

}
