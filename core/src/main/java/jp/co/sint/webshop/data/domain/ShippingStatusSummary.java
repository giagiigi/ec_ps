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
 * 「出荷状況サマリー」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum ShippingStatusSummary implements CodeAttribute {

  /** 「未出荷」を表す値です。 */
  NOT_SHIPPED("未出荷", "0"),

  /** 「出荷手配中」を表す値です。 */
  IN_PROCESSING("出荷手配中", "1"),

  /** 「一部出荷」を表す値です。 */
  PARTIAL_SHIPPED("一部出荷", "2"),

  /** 「全て出荷」を表す値です。 */
  SHIPPED_ALL("全て出荷", "3"),

  /** 「キャンセル」を表す値です。 */
  CANCELLED("キャンセル", "4");

  private String name;

  private String value;

  private ShippingStatusSummary(String name, String value) {
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
   * 指定されたコード名を持つ出荷状況サマリーを返します。
   *
   * @param name コード名
   * @return 出荷状況サマリー
   */
  public static ShippingStatusSummary fromName(String name) {
    for (ShippingStatusSummary p : ShippingStatusSummary.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ出荷状況サマリーを返します。
   *
   * @param value コード値
   * @return 出荷状況サマリー
   */
  public static ShippingStatusSummary fromValue(String value) {
    for (ShippingStatusSummary p : ShippingStatusSummary.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ出荷状況サマリーを返します。
   *
   * @param value コード値
   * @return 出荷状況サマリー
   */
  public static ShippingStatusSummary fromValue(Long value) {
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
      for (ShippingStatusSummary p : ShippingStatusSummary.values()) {
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
