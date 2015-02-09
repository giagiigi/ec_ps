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
 * 「電子マネー決済利用区分」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum DigitalCashEnableType implements CodeAttribute {

  /** 「すべて」を表す値です。 */
  ALL("すべて", "0"),

  /** 「Suicaのみ」を表す値です。 */
  SUICA("Suicaのみ", "1"),

  /** 「Edyのみ」を表す値です。 */
  EDY("Edyのみ", "2");

  private String name;

  private String value;

  private DigitalCashEnableType(String name, String value) {
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
   * 指定されたコード名を持つ電子マネー決済利用区分を返します。
   *
   * @param name コード名
   * @return 電子マネー決済利用区分
   */
  public static DigitalCashEnableType fromName(String name) {
    for (DigitalCashEnableType p : DigitalCashEnableType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ電子マネー決済利用区分を返します。
   *
   * @param value コード値
   * @return 電子マネー決済利用区分
   */
  public static DigitalCashEnableType fromValue(String value) {
    for (DigitalCashEnableType p : DigitalCashEnableType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ電子マネー決済利用区分を返します。
   *
   * @param value コード値
   * @return 電子マネー決済利用区分
   */
  public static DigitalCashEnableType fromValue(Long value) {
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
      for (DigitalCashEnableType p : DigitalCashEnableType.values()) {
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
