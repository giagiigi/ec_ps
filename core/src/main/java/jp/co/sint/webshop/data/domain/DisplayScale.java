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
 * 「表示スケール」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum DisplayScale implements CodeAttribute {

  /** 「100件」を表す値です。 */
  SCALE_100("100件", "100"),

  /** 「1,000件」を表す値です。 */
  SCALE_1000("1,000件", "1000"),

  /** 「10,000件」を表す値です。 */
  SCALE_10000("10,000件", "10000"),

  /** 「100,000件」を表す値です。 */
  SCALE_100000("100,000件", "100000"),

  /** 「1,000,000件」を表す値です。 */
  SCALE_1000000("1,000,000件", "1000000");

  private String name;

  private String value;

  private DisplayScale(String name, String value) {
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
   * 指定されたコード名を持つ表示スケールを返します。
   *
   * @param name コード名
   * @return 表示スケール
   */
  public static DisplayScale fromName(String name) {
    for (DisplayScale p : DisplayScale.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ表示スケールを返します。
   *
   * @param value コード値
   * @return 表示スケール
   */
  public static DisplayScale fromValue(String value) {
    for (DisplayScale p : DisplayScale.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ表示スケールを返します。
   *
   * @param value コード値
   * @return 表示スケール
   */
  public static DisplayScale fromValue(Long value) {
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
      for (DisplayScale p : DisplayScale.values()) {
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
