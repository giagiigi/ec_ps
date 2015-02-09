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
 * 「レビューポイント割当ステータス」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum ReviewPointAllocatedStatus implements CodeAttribute {

  /** 「未割当」を表す値です。 */
  NOT_ALLOCATED("未割当", "0"),

  /** 「割当済み」を表す値です。 */
  ALLOCATED("割当済み", "1");

  private String name;

  private String value;

  private ReviewPointAllocatedStatus(String name, String value) {
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
   * 指定されたコード名を持つレビューポイント割当ステータスを返します。
   *
   * @param name コード名
   * @return レビューポイント割当ステータス
   */
  public static ReviewPointAllocatedStatus fromName(String name) {
    for (ReviewPointAllocatedStatus p : ReviewPointAllocatedStatus.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つレビューポイント割当ステータスを返します。
   *
   * @param value コード値
   * @return レビューポイント割当ステータス
   */
  public static ReviewPointAllocatedStatus fromValue(String value) {
    for (ReviewPointAllocatedStatus p : ReviewPointAllocatedStatus.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つレビューポイント割当ステータスを返します。
   *
   * @param value コード値
   * @return レビューポイント割当ステータス
   */
  public static ReviewPointAllocatedStatus fromValue(Long value) {
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
      for (ReviewPointAllocatedStatus p : ReviewPointAllocatedStatus.values()) {
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
