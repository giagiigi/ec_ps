//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはコード定義ドキュメントから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.domain;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「商品価格タイプ」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum CommodityPriceType implements CodeAttribute {

  /** 「商品単価」を表す値です。 */
  UNIT_PRICE("商品単価", "0"),

  /** 「特別価格」を表す値です。 */
  DISCOUNT_PRICE("特別価格", "1"),

  /** 「予約価格」を表す値です。 */
  RESERVATION_PRICE("予約価格", "2"),

  /** 「改定価格」を表す値です。 */
  CHANGE_UNIT_PRICE("改定価格", "3"),
  
  /**希望售价 */
  SUGGESTE_PRICE("希望售价","4"),
  
  /**采购价格 */
  PURCHASE_PRICE("采购价格","5");

  private String name;

  private String value;

  private CommodityPriceType(String name, String value) {
    this.name = name;
    this.value = value;
  }
  /**
   * コード名称を返します。
   * @return コード名称
   */
  public String getName() {
    return StringUtil.coalesce(CodeUtil.getName(this), this.name);
  }

  /**
   * コード値を返します。
   * @return コード値
   */
  public String getValue() {
    return StringUtil.coalesce(CodeUtil.getValue(this), this.value);
  }

  /**
   * Long型のコード値を返します。
   * @return コード値
   */
  public Long longValue() {
    return Long.valueOf(this.value);
  }

  /**
   * 指定されたコード名を持つ商品価格タイプを返します。
   *
   * @param name コード名
   * @return 商品価格タイプ
   */
  public static CommodityPriceType fromName(String name) {
    for (CommodityPriceType p : CommodityPriceType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ商品価格タイプを返します。
   *
   * @param value コード値
   * @return 商品価格タイプ
   */
  public static CommodityPriceType fromValue(String value) {
    for (CommodityPriceType p : CommodityPriceType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ商品価格タイプを返します。
   *
   * @param value コード値
   * @return 商品価格タイプ
   */
  public static CommodityPriceType fromValue(Long value) {
    return fromValue(Long.toString(value));
  }

  /**
   * 指定されたコード値が有効かどうかを返します。
   *
   * @param value コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(String value) {
    if (StringUtil.hasValue(value)) {
      for (CommodityPriceType p : CommodityPriceType.values()) {
        if (p.getValue().equals(value)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 指定されたコード値が有効かどうかを返します。
   *
   * @param value コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(Long value) {
    return isValid(Long.toString(value));
  }
}
