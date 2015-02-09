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
 * 「在库比例区分」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum RatioType implements CodeAttribute {

  /** 「EC」を表す値です。 */
  EC("EC在库比例", "0"),

  /** 「天猫」を表す値です。 */
  TMALL("天猫在库比例", "1"),
  
  /** 「京东」を表す値です。 */
  JD("京东在库比例", "2");

  private String name;

  private String value;

  private RatioType(String name, String value) {
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
   * 指定されたコード名を持つ入荷お知らせ可能フラグを返します。
   *
   * @param name コード名
   * @return 入荷お知らせ可能フラグ
   */
  public static RatioType fromName(String name) {
    for (RatioType p : RatioType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ入荷お知らせ可能フラグを返します。
   *
   * @param value コード値
   * @return 入荷お知らせ可能フラグ
   */
  public static RatioType fromValue(String value) {
    for (RatioType p : RatioType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ入荷お知らせ可能フラグを返します。
   *
   * @param value コード値
   * @return 入荷お知らせ可能フラグ
   */
  public static RatioType fromValue(Long value) {
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
      for (RatioType p : RatioType.values()) {
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
