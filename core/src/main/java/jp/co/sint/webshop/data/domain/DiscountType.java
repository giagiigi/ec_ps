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
 * 「折扣类型」のコード定義を表す列挙クラスです。
 *
 * @author Kousen.
 *
 */
public enum DiscountType implements CodeAttribute {

  /** 「会员折扣」を表す値です。 */
  CUSTOMER("会员折扣", "0"),

  /** 「优惠券折扣」を表す値です。 */
  COUPON("优惠券折扣", "1"),
  
  DISCOUNT_TIME("限时限量", "2"),
    
  OPTIONAL("满A减B", "3");

  private String name;

  private String value;

  private DiscountType(String name, String value) {
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
   * 指定されたコード名を持つ折扣类型を返します。
   *
   * @param name コード名
   * @return 折扣类型
   */
  public static DiscountType fromName(String name) {
    for (DiscountType p : DiscountType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ折扣类型を返します。
   *
   * @param value コード値
   * @return 折扣类型
   */
  public static DiscountType fromValue(String value) {
    for (DiscountType p : DiscountType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ折扣类型を返します。
   *
   * @param value コード値
   * @return 折扣类型
   */
  public static DiscountType fromValue(Long value) {
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
      for (DiscountType p : DiscountType.values()) {
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
