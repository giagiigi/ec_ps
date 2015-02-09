package jp.co.sint.webshop.data.domain;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 退款方式
 * @author ks
 *
 */
public enum ReturnMethodType implements CodeAttribute {

  /** 「支付宝」を表す値です。 */
  ALIPAY("支付宝", "08"),

  /** 「银联」を表す値です。 */
  CHINA_UNIONPAY("银联", "07"),

  /** 「银行汇款」を表す値です。 */
  BANK("银行汇款", "99");

  
  private String name;

  private String value;

  private ReturnMethodType(String name, String value) {
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
   * 指定されたコード名を持つ販売ステータスを返します。
   *
   * @param name コード名
   * @return 販売ステータス
   */
  public static ReturnMethodType fromName(String name) {
    for (ReturnMethodType p : ReturnMethodType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ販売ステータスを返します。
   *
   * @param value コード値
   * @return 販売ステータス
   */
  public static ReturnMethodType fromValue(String value) {
    for (ReturnMethodType p : ReturnMethodType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ販売ステータスを返します。
   *
   * @param value コード値
   * @return 販売ステータス
   */
  public static ReturnMethodType fromValue(Long value) {
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
      for (ReturnMethodType p : ReturnMethodType.values()) {
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
