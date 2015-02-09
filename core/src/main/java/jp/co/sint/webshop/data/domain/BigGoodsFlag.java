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
 * 表示大件货物标志
 *
 * @author System Integrator Corp.
 *
 */
public enum BigGoodsFlag implements CodeAttribute {

  /** 普通货物 */
  Big("普通", "0"),

  /** 大件货物 */
  NotBig("大件", "1");

  private String name;

  private String value;

  private BigGoodsFlag(String name, String value) {
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
   * 指定されたコード名を持つ表示クライアント区分を返します。
   *
   * @param name コード名
   * @return 表示クライアント区分
   */
  public static BigGoodsFlag fromName(String name) {
    for (BigGoodsFlag p : BigGoodsFlag.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ表示クライアント区分を返します。
   *
   * @param value コード値
   * @return 表示クライアント区分
   */
  public static BigGoodsFlag fromValue(String value) {
    for (BigGoodsFlag p : BigGoodsFlag.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ表示クライアント区分を返します。
   *
   * @param value コード値
   * @return 表示クライアント区分
   */
  public static BigGoodsFlag fromValue(Long value) {
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
      for (BigGoodsFlag p : BigGoodsFlag.values()) {
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
