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
 * 「同期フラグ」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum SyncFlagJd implements CodeAttribute {

  /** 「同期不可」を表す値です。 */
  SYNCHIDDEN("同期不可", "0"),

  /** 「同期可能」を表す値です。 */
  SYNCVISIBLE("同期可能", "1"),
  
  /** 「已同期」を表す値です。 */
  SYNCALREADY("已同期", "2");

  private String name;

  private String value;

  private SyncFlagJd(String name, String value) {
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
   * 指定されたコード名を持つ表示フラグを返します。
   *
   * @param name コード名
   * @return 表示フラグ
   */
  public static SyncFlagJd fromName(String name) {
    for (SyncFlagJd p : SyncFlagJd.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ表示フラグを返します。
   *
   * @param value コード値
   * @return 表示フラグ
   */
  public static SyncFlagJd fromValue(String value) {
    for (SyncFlagJd p : SyncFlagJd.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ表示フラグを返します。
   *
   * @param value コード値
   * @return 表示フラグ
   */
  public static SyncFlagJd fromValue(Long value) {
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
      for (SyncFlagJd p : SyncFlagJd.values()) {
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
