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
public enum JdOrderStatus implements CodeAttribute {

  WAIT_SELLER_STOCK_OUT("WAIT_SELLER_STOCK_OUT", "1"),

  WAIT_GOODS_RECEIVE_CONFIRM("WAIT_GOODS_RECEIVE_CONFIRM", "1"),

  FINISHED_L ("FINISHED_L", "1"),
  
  LOCKED ("LOCKED", "1"),

  TRADE_CANCELED("TRADE_CANCELED", "2");

  private String name;

  private String value;

  private JdOrderStatus(String name, String value) {
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
  public static JdOrderStatus fromName(String name) {
    for (JdOrderStatus p : JdOrderStatus.values()) {
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
  public static JdOrderStatus fromValue(String value) {
    for (JdOrderStatus p : JdOrderStatus.values()) {
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
  public static JdOrderStatus fromValue(Long value) {
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
      for (JdOrderStatus p : JdOrderStatus.values()) {
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
