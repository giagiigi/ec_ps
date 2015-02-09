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
 * 「顧客属性区分」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum CustomerAttributeType implements CodeAttribute {

  /** 「単一選択」を表す値です。 */
  RADIO("単一選択", "0"),

  /** 「複数選択」を表す値です。 */
  CHECK("複数選択", "1");

  private String name;

  private String value;

  private CustomerAttributeType(String name, String value) {
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
   * 指定されたコード名を持つ顧客属性区分を返します。
   *
   * @param name コード名
   * @return 顧客属性区分
   */
  public static CustomerAttributeType fromName(String name) {
    for (CustomerAttributeType p : CustomerAttributeType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ顧客属性区分を返します。
   *
   * @param value コード値
   * @return 顧客属性区分
   */
  public static CustomerAttributeType fromValue(String value) {
    for (CustomerAttributeType p : CustomerAttributeType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ顧客属性区分を返します。
   *
   * @param value コード値
   * @return 顧客属性区分
   */
  public static CustomerAttributeType fromValue(Long value) {
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
      for (CustomerAttributeType p : CustomerAttributeType.values()) {
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
