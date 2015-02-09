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
 * 配送希望日指定区分
 *
 * @author ylp.
 *
 */
public enum DeliveryDateType implements CodeAttribute {

  /** 「指定不可」を表す値です。 */
	
  UNUSABLE("不可指定日期", "0"),
  
  /** 「指定可」を表す値です。 */
  ASSIGNABLE("可指定日期", "1"),
  
  /** 「平日」を表す値です。 */
  FERIAL("平日", "2"),
  
  /** 「休息日」を表す値です。 */
  HOLIDAY("休息日", "3");

  private String name;

  private String value;

  private DeliveryDateType(String name, String value) {
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
   * 指定されたコード名を持つIB/OB区分を返します。
   *
   * @param name コード名
   * @return 区分
   */
  public static DeliveryDateType fromName(String name) {
    for (DeliveryDateType p : DeliveryDateType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つIB/OB区分を返します。
   *
   * @param value コード値
   * @return IB/OB区分
   */
  public static DeliveryDateType fromValue(String value) {
    for (DeliveryDateType p : DeliveryDateType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つIB/OB区分を返します。
   *
   * @param value コード値
   * @return IB/OB区分
   */
  public static DeliveryDateType fromValue(Long value) {
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
      for (DeliveryDateType p : DeliveryDateType.values()) {
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
