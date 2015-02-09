package jp.co.sint.webshop.service.shop;

import jp.co.sint.webshop.data.dto.ShippingCharge;

public class ShippingChargeSuite {

  private String regionBlockName;

  private ShippingCharge shippingCharge;

  /**
   * 地域ブロック名の取得。
   * 
   * @return 地域ブロック名
   */
  public String getRegionBlockName() {
    return regionBlockName;
  }

  /**
   * 地域ブロック名の設定。
   * 
   * @param regionBlockName
   *          地域ブロック名
   */
  public void setRegionBlockName(String regionBlockName) {
    this.regionBlockName = regionBlockName;
  }

  /**
   * 送料情報の取得。
   * 
   * @return 送料情報
   */
  public ShippingCharge getShippingCharge() {
    return shippingCharge;
  }

  /**
   * 送料情報の設定。
   * 
   * @param shippingCharge
   *          送料情報
   */
  public void setShippingCharge(ShippingCharge shippingCharge) {
    this.shippingCharge = shippingCharge;
  }
}
