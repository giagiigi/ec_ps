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
 * 「允许货到付款标志」のコード定義を表す列挙クラスです。
 *
 * @author Kousen.
 *
 */
public enum AllowCodFlg implements CodeAttribute {

  /** 「不允许」を表す値です。 */
  NO_ALLOW("不允许", "0"),

  /** 「允许」を表す値です。 */
  ALLOW("允许", "1");

  private String name;

  private String value;

  private AllowCodFlg(String name, String value) {
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
   * 指定されたコード名を持つ允许货到付款标志を返します。
   *
   * @param name コード名
   * @return 允许货到付款标志
   */
  public static AllowCodFlg fromName(String name) {
    for (AllowCodFlg p : AllowCodFlg.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ允许货到付款标志を返します。
   *
   * @param value コード値
   * @return 允许货到付款标志
   */
  public static AllowCodFlg fromValue(String value) {
    for (AllowCodFlg p : AllowCodFlg.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ允许货到付款标志を返します。
   *
   * @param value コード値
   * @return 允许货到付款标志
   */
  public static AllowCodFlg fromValue(Long value) {
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
      for (AllowCodFlg p : AllowCodFlg.values()) {
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
