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
 * 「RDBMS」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum RdbmsType implements CodeAttribute {

  /** 「Oracle10g」を表す値です。 */
  ORACLE_10G("Oracle10g", "1"),
  //postgreSQL start
  /** 「postgreSQL」を表す値です。 */
  POSTGRESQL("postgreSQL", "2");
  //postgreSQL end
  private String name;

  private String value;

  private RdbmsType(String name, String value) {
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
   * 指定されたコード名を持つRDBMSを返します。
   *
   * @param name コード名
   * @return RDBMS
   */
  public static RdbmsType fromName(String name) {
    for (RdbmsType p : RdbmsType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つRDBMSを返します。
   *
   * @param value コード値
   * @return RDBMS
   */
  public static RdbmsType fromValue(String value) {
    for (RdbmsType p : RdbmsType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つRDBMSを返します。
   *
   * @param value コード値
   * @return RDBMS
   */
  public static RdbmsType fromValue(Long value) {
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
      for (RdbmsType p : RdbmsType.values()) {
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
