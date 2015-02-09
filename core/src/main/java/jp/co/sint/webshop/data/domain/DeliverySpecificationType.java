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
 * 「配送詳細区分」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum DeliverySpecificationType implements CodeAttribute {

  /** 「指定なし」を表す値です。 */
  NONE("指定なし", "0"),

  /** 「日のみ」を表す値です。 */
  DATE_ONLY("日のみ", "1"),

  /** 「時間のみ」を表す値です。 */
  TIME_ONLY("時間のみ", "2"),

  /** 「日時両方」を表す値です。 */
  DATE_AND_TIME("日時両方", "3");

  private String name;

  private String value;

  private DeliverySpecificationType(String name, String value) {
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
   * 指定されたコード名を持つ配送詳細区分を返します。
   *
   * @param name コード名
   * @return 配送詳細区分
   */
  public static DeliverySpecificationType fromName(String name) {
    for (DeliverySpecificationType p : DeliverySpecificationType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ配送詳細区分を返します。
   *
   * @param value コード値
   * @return 配送詳細区分
   */
  public static DeliverySpecificationType fromValue(String value) {
    for (DeliverySpecificationType p : DeliverySpecificationType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ配送詳細区分を返します。
   *
   * @param value コード値
   * @return 配送詳細区分
   */
  public static DeliverySpecificationType fromValue(Long value) {
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
      for (DeliverySpecificationType p : DeliverySpecificationType.values()) {
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
