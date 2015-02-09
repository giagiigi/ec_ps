package jp.co.sint.webshop.data.domain;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「配送支付方式」のコード定義を表す列挙クラスです。
 *
 * @author cxw
 *
 */
public enum DeliveryPaymentType implements CodeAttribute {

  /** 「不支持」を表す値です。 */
  NONE("不支持", "0"),

  /** 「支持」を表す値です。 */
  DATE_ONLY("支持", "1");

  private String name;

  private String value;

  private DeliveryPaymentType(String name, String value) {
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
   * 指定されたコード名を持つ配送支付方式を返します。
   *
   * @param name コード名
   * @return 配送支付方式
   */
  public static DeliveryPaymentType fromName(String name) {
    for (DeliveryPaymentType p : DeliveryPaymentType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ配送支付方式を返します。
   *
   * @param value コード値
   * @return 配送支付方式
   */
  public static DeliveryPaymentType fromValue(String value) {
    for (DeliveryPaymentType p : DeliveryPaymentType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ配送支付方式を返します。
   *
   * @param value コード値
   * @return 配送支付方式
   */
  public static DeliveryPaymentType fromValue(Long value) {
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
      for (DeliveryPaymentType p : DeliveryPaymentType.values()) {
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
