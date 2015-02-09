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
 * 「ポイント発行区分」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum PointIssueType implements CodeAttribute {

  /** 「注文時」を表す値です。 */
  ORDER("注文時", "0"),

  /** 「レビュー」を表す値です。 */
  REVIEW("レビュー", "1"),

  /** 「アンケート」を表す値です。 */
  ENQUETE("アンケート", "2"),

  /** 「調整」を表す値です。 */
  ADJUSTMENT("調整", "3"),

  /** 「会員登録」を表す値です。 */
  MEMBER("会員登録", "4");

  private String name;

  private String value;

  private PointIssueType(String name, String value) {
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
   * 指定されたコード名を持つポイント発行区分を返します。
   *
   * @param name コード名
   * @return ポイント発行区分
   */
  public static PointIssueType fromName(String name) {
    for (PointIssueType p : PointIssueType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つポイント発行区分を返します。
   *
   * @param value コード値
   * @return ポイント発行区分
   */
  public static PointIssueType fromValue(String value) {
    for (PointIssueType p : PointIssueType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つポイント発行区分を返します。
   *
   * @param value コード値
   * @return ポイント発行区分
   */
  public static PointIssueType fromValue(Long value) {
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
      for (PointIssueType p : PointIssueType.values()) {
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
