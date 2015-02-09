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
 * 「返品状況サマリー」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum ReturnStatusSummary implements CodeAttribute {

  /** 「返品なし」を表す値です。 */
  NOT_RETURNED("返品なし", "0"),

  /** 「一部返品」を表す値です。 */
  PARTIAL_RETURNED("一部返品", "1"),

  /** 「全て返品」を表す値です。 */
  RETURNED_ALL("全て返品", "2");

  private String name;

  private String value;

  private ReturnStatusSummary(String name, String value) {
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
   * 指定されたコード名を持つ返品状況サマリーを返します。
   *
   * @param name コード名
   * @return 返品状況サマリー
   */
  public static ReturnStatusSummary fromName(String name) {
    for (ReturnStatusSummary p : ReturnStatusSummary.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ返品状況サマリーを返します。
   *
   * @param value コード値
   * @return 返品状況サマリー
   */
  public static ReturnStatusSummary fromValue(String value) {
    for (ReturnStatusSummary p : ReturnStatusSummary.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ返品状況サマリーを返します。
   *
   * @param value コード値
   * @return 返品状況サマリー
   */
  public static ReturnStatusSummary fromValue(Long value) {
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
      for (ReturnStatusSummary p : ReturnStatusSummary.values()) {
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
