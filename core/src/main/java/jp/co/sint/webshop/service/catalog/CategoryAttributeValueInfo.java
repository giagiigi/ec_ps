package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.dto.CategoryAttributeValue;

public class CategoryAttributeValueInfo extends CategoryAttributeValue {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 商品名 */
  private String commodityName;

  /**
   * @param commodityName
   *          設定する commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * @return commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

}
