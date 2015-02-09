package jp.co.sint.webshop.service.analysis;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum RearrangeType {
  /** 受注件数順 */
  ORDER_BY_ORDER_COUNT("受注件数順", "orderCount"),
  /** 受注数量順 */
  ORDER_BY_PURCHASING_AMOUNT("受注数量順", "purchasingAmount"),
  /** 商品コード順 */
  ORDER_BY_COMMODITY_CODE("商品コード順", "commodityCode");

  private String name;

  private String value;
  
  private RearrangeType() {
  }

  private RearrangeType(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public static RearrangeType fromName(String searchName) {
    for (RearrangeType r : RearrangeType.values()) {
      if (r.getName().equals(searchName)) {
        return r;
      }
    }
    return null;
  }

  public static RearrangeType fromValue(String searchValue) {
    for (RearrangeType r : RearrangeType.values()) {
      if (r.getValue().equals(searchValue)) {
        return r;
      }
    }
    return null;
  }

  /**
   * nameを返します。
   * 
   * @return the name
   */
  public String getName() {
    return StringUtil.coalesce(CodeUtil.getName(this), this.name);
  }

  /**
   * valueを返します。
   * 
   * @return the value
   */
  public String getValue() {
    return StringUtil.coalesce(CodeUtil.getValue(this), this.value);
  }
}
