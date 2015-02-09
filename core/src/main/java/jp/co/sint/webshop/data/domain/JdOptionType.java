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
 * 京东option_type
 *
 * @author ob
 *
 */
public enum JdOptionType implements CodeAttribute {

  /** 「下架」を表す値です。 */
  OFFSALE("下架", "offsale"),
  
  /** 「上架」を表す値です。 */
  ONSALE("上架", "onsale");

  private String name;

  private String value;

  private JdOptionType(String name, String value) {
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
   * 指定されたコード名を持つ京东option_typeを返します。
   *
   * @param name コード名
   * @return 区分
   */
  public static JdOptionType fromName(String name) {
    for (JdOptionType p : JdOptionType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ京东option_typeを返します。
   *
   * @param value コード値
   * @return 京东option_type
   */
  public static JdOptionType fromValue(String value) {
    for (JdOptionType p : JdOptionType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ京东option_typeを返します。
   *
   * @param value コード値
   * @return 京东option_type
   */
  public static JdOptionType fromValue(Long value) {
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
      for (JdOptionType p : JdOptionType.values()) {
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
