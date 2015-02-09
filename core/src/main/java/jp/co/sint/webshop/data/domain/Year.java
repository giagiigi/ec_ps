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
 * 「年」のコード定義を表す列挙クラスです。
 *
 * @author System OB.
 *
 */
public enum Year implements CodeAttribute {

  YEAR_01("A", "2013"),
  
  YEAR_02("G", "2014"),
  
  YEAR_03("H", "2015"),
  
  YEAR_04("I", "2016"),
  
  YEAR_05("E", "2017"),
  
  YEAR_06("O", "2018"),
  
  YEAR_07("Q", "2019"),

  YEAR_08("M", "2020");

  private String name;

  private String value;

  private Year(String name, String value) {
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
  public static Year fromName(String name) {
    for (Year p : Year.values()) {
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
  public static Year fromValue(String value) {
    for (Year p : Year.values()) {
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
  public static Year fromValue(Long value) {
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
      for (Year p : Year.values()) {
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
