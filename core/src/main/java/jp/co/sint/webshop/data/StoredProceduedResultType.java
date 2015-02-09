//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはコード定義ドキュメントから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「口座区分」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum StoredProceduedResultType implements CodeAttribute {

  /** 「成功」を表す値です。 */
  SUCCESS("成功", "0"),

  /** 「失敗」を表す値です。 */
  FAILED("失敗", "1");

  private String name;

  private String value;

  private StoredProceduedResultType(String name, String value) {
    this.name = name;
    this.value = value;
  }
  /**
   * コード名称を返します。
   * @return コード名称
   */
  public String getName() {
    return name;
  }

  /**
   * コード値を返します。
   * @return コード値
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Long型のコード値を返します。
   * @return コード値
   */
  public Long longValue() {
    return Long.valueOf(this.value);
  }

  /**
   * 指定されたコード名を持つ処理結果を返します。
   *
   * @param name コード名
   * @return 処理結果
   */
  public static StoredProceduedResultType fromName(String name) {
    for (StoredProceduedResultType p : StoredProceduedResultType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ処理結果を返します。
   *
   * @param value コード値
   * @return 処理結果
   */
  public static StoredProceduedResultType fromValue(String value) {
    for (StoredProceduedResultType p : StoredProceduedResultType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ処理結果を返します。
   *
   * @param value コード値
   * @return 処理結果
   */
  public static StoredProceduedResultType fromValue(Long value) {
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
      for (StoredProceduedResultType p : StoredProceduedResultType.values()) {
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
