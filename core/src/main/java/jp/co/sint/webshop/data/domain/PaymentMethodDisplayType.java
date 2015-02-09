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
 * 「支払方法表示区分」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum PaymentMethodDisplayType implements CodeAttribute {

  /** 「フロント・管理」を表す値です。 */
  ALL("フロント・管理", "0"),

  /** 「フロントのみ」を表す値です。 */
  FRONT("フロントのみ", "1"),

  /** 「管理のみ」を表す値です。 */
  BACK("管理のみ", "2"),

  /** 「非表示」を表す値です。 */
  HIDDEN("非表示", "9");

  private String name;

  private String value;

  private PaymentMethodDisplayType(String name, String value) {
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
   * 指定されたコード名を持つ支払方法表示区分を返します。
   *
   * @param name コード名
   * @return 支払方法表示区分
   */
  public static PaymentMethodDisplayType fromName(String name) {
    for (PaymentMethodDisplayType p : PaymentMethodDisplayType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ支払方法表示区分を返します。
   *
   * @param value コード値
   * @return 支払方法表示区分
   */
  public static PaymentMethodDisplayType fromValue(String value) {
    for (PaymentMethodDisplayType p : PaymentMethodDisplayType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ支払方法表示区分を返します。
   *
   * @param value コード値
   * @return 支払方法表示区分
   */
  public static PaymentMethodDisplayType fromValue(Long value) {
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
      for (PaymentMethodDisplayType p : PaymentMethodDisplayType.values()) {
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
