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
 * 「アプリケーションサーバ」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum ApplicationServerType implements CodeAttribute {

  /** 「Apache Tomcat」を表す値です。 */
  TOMCAT("Apache Tomcat", "1"),

  /** 「Oracle AS」を表す値です。 */
  OAS("Oracle AS", "2");

  private String name;

  private String value;

  private ApplicationServerType(String name, String value) {
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
   * 指定されたコード名を持つアプリケーションサーバを返します。
   *
   * @param name コード名
   * @return アプリケーションサーバ
   */
  public static ApplicationServerType fromName(String name) {
    for (ApplicationServerType p : ApplicationServerType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つアプリケーションサーバを返します。
   *
   * @param value コード値
   * @return アプリケーションサーバ
   */
  public static ApplicationServerType fromValue(String value) {
    for (ApplicationServerType p : ApplicationServerType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つアプリケーションサーバを返します。
   *
   * @param value コード値
   * @return アプリケーションサーバ
   */
  public static ApplicationServerType fromValue(Long value) {
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
      for (ApplicationServerType p : ApplicationServerType.values()) {
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
