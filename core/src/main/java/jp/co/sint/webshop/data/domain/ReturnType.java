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
 * 「退换货类型」のコード定義を表す列挙クラスです。
 *
 * @author Swj.
 *
 */
public enum ReturnType implements CodeAttribute {

  /** 「无退货退款」を表す値です。 */
  NOT_RETURN_COMMODITY_PRICE("无退货退款", "0"),

  /** 「退货退款」を表す値です。 */
  RETURN_COMMODITY_PRICE("退货退款", "1"),

  /** 「破损品交换」を表す値です。 */
  COMMODITY_EXCHANGE("破损品交换", "2");

  private String name;

  private String value;

  private ReturnType(String name, String value) {
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
   * 指定されたコード名を持つ退换货类型を返します。
   *
   * @param name コード名
   * @return 退换货类型
   */
  public static ReturnType fromName(String name) {
    for (ReturnType p : ReturnType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ退换货类型を返します。
   *
   * @param value コード値
   * @return 退换货类型
   */
  public static ReturnType fromValue(String value) {
    for (ReturnType p : ReturnType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ退换货类型を返します。
   *
   * @param value コード値
   * @return 退换货类型
   */
  public static ReturnType fromValue(Long value) {
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
      for (ReturnType p : ReturnType.values()) {
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
