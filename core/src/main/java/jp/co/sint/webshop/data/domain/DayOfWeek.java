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
 * 「曜日」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum DayOfWeek implements CodeAttribute {

  /** 「日曜日」を表す値です。 */
  SUNDAY("日曜日", "1"),

  /** 「月曜日」を表す値です。 */
  MONDAY("月曜日", "2"),

  /** 「火曜日」を表す値です。 */
  TUESDAY("火曜日", "3"),

  /** 「水曜日」を表す値です。 */
  WEDNESDAY("水曜日", "4"),

  /** 「木曜日」を表す値です。 */
  THURSDAY("木曜日", "5"),

  /** 「金曜日」を表す値です。 */
  FRIDAY("金曜日", "6"),

  /** 「土曜日」を表す値です。 */
  SATURDAY("土曜日", "7");

  private String name;

  private String value;

  private DayOfWeek(String name, String value) {
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
   * 指定されたコード名を持つ曜日を返します。
   *
   * @param name コード名
   * @return 曜日
   */
  public static DayOfWeek fromName(String name) {
    for (DayOfWeek p : DayOfWeek.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ曜日を返します。
   *
   * @param value コード値
   * @return 曜日
   */
  public static DayOfWeek fromValue(String value) {
    for (DayOfWeek p : DayOfWeek.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ曜日を返します。
   *
   * @param value コード値
   * @return 曜日
   */
  public static DayOfWeek fromValue(Long value) {
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
      for (DayOfWeek p : DayOfWeek.values()) {
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
