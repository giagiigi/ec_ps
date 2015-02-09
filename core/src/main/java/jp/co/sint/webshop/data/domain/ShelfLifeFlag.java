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
 * 「販売フラグ」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum ShelfLifeFlag implements CodeAttribute {

  /** 不管理。 */
  NOT("不管理", "0"),

  /** 根据保持期管理。 */
  BYENDTIME("根据到期日管理", "1"),
  
  /** 根据生产日期/保质期天数管理。 */
  BYENDTIMEORCREATETIME("根据生产日期/保质期天数管理", "2");

  private String name;

  private String value;

  private ShelfLifeFlag(String name, String value) {
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
   * 指定されたコード名を持つ販売フラグを返します。
   *
   * @param name コード名
   * @return 販売フラグ
   */
  public static ShelfLifeFlag fromName(String name) {
    for (ShelfLifeFlag p : ShelfLifeFlag.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ販売フラグを返します。
   *
   * @param value コード値
   * @return 販売フラグ
   */
  public static ShelfLifeFlag fromValue(String value) {
    for (ShelfLifeFlag p : ShelfLifeFlag.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ販売フラグを返します。
   *
   * @param value コード値
   * @return 販売フラグ
   */
  public static ShelfLifeFlag fromValue(Long value) {
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
      for (ShelfLifeFlag p : ShelfLifeFlag.values()) {
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
