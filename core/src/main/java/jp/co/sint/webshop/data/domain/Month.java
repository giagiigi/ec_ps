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
 * 「月」のコード定義を表す列挙クラスです。
 *
 * @author System OB.
 *
 */
public enum Month implements CodeAttribute {

  MONTH_01("k", "01"),
  
  MONTH_02("p", "02"),
  
  MONTH_03("d", "03"),
  
  MONTH_04("t", "04"),
  
  MONTH_05("w", "05"),
  
  MONTH_06("v", "06"),
  
  MONTH_07("u", "07"),
  
  MONTH_08("s", "08"),
  
  MONTH_09("i", "09"),
  
  MONTH_10("a", "10"),
  
  MONTH_11("x", "11"),

  MONTH_12("b", "12");

  private String name;

  private String value;

  private Month(String name, String value) {
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
   * 指定されたコード名を持つ口座区分を返します。
   *
   * @param name コード名
   * @return 口座区分
   */
  public static Month fromName(String name) {
    for (Month p : Month.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ口座区分を返します。
   *
   * @param value コード値
   * @return 口座区分
   */
  public static Month fromValue(String value) {
    for (Month p : Month.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ口座区分を返します。
   *
   * @param value コード値
   * @return 口座区分
   */
  public static Month fromValue(Long value) {
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
      for (Month p : Month.values()) {
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
