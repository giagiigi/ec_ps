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
 * 「受注ステータス」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum OrderStatus implements CodeAttribute {

  /** 「预约」を表す値です。 */
  RESERVED("预约", "0"),

  /** 「订单」を表す値です。 */
  ORDERED("订单", "1"),

  /** 「キャンセル」を表す値です。 */
  CANCELLED("取消", "2"),
  
  // M17N 10361 修正 ここから
  // /** 「假订单」を表す値です。 */
  // PHANTOM_ORDER("真订单", "3"); //$NON-NLS-1$ //$NON-NLS-2$
  /** 「假订单」を表す値です。 */
  PHANTOM_ORDER("假订单", "3"),

  /** 「假预约」を表す値です。 */
  PHANTOM_RESERVATION("假预约", "4");
  // M17N 10361 修正 ここまで

  private String name;

  private String value;

  private OrderStatus(String name, String value) {
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
   * 指定されたコード名を持つ订单ステータスを返します。
   *
   * @param name コード名
   * @return 订单ステータス
   */
  public static OrderStatus fromName(String name) {
    for (OrderStatus p : OrderStatus.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ订单ステータスを返します。
   *
   * @param value コード値
   * @return 订单ステータス
   */
  public static OrderStatus fromValue(String value) {
    for (OrderStatus p : OrderStatus.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ订单ステータスを返します。
   *
   * @param value コード値
   * @return 订单ステータス
   */
  public static OrderStatus fromValue(Long value) {
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
      for (OrderStatus p : OrderStatus.values()) {
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
