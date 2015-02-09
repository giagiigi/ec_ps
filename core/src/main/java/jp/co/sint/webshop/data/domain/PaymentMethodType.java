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
 * 「支払方法区分」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum PaymentMethodType implements CodeAttribute {

  /** 「支払不要」を表す値です。 */
  NO_PAYMENT("礼品卡支付", "00"),

  /** 「全額ポイント払い」を表す値です。 */
  POINT_IN_FULL("全额积分付款", "01"),

  /** 「代金引換」を表す値です。 */
  CASH_ON_DELIVERY("货到付款", "02"),

  /** 「銀行振込」を表す値です。 */
  BANKING("银行汇款", "03"),

  /** 「クレジット支払い」を表す値です。 */
  CREDITCARD("信用支付", "04"),

  /** 「コンビニ支払い」を表す値です。 */
  CVS_PAYMENT("便利店支付", "05"),

  /** 「電子マネー支払い」を表す値です。 */
  DIGITAL_CASH("电子货币支付", "06"),

  /** 「支付宝(Alipay)支払い」を表す値です。 */
  ALIPAY("支付宝", "11"),

  /** 「銀聯カード支払い」を表す値です。 */
  CHINA_UNIONPAY("银联", "12"),
  
//v10-ch-pg add start
  /** 「邮局支払い」を表す値です。 */
  POSTRAL("邮局汇款", "13"),
//v10-ch-pg add end
  
  OUTER_CARD("外卡支付", "14"),
  
  WAP_ALIPAY("手机支付宝", "15"),
  
  INNER_CARD("内卡支付", "16"),
  
  JD_ONLINEPAYMENT("京东在线支付", "17");


  private String name;

  private String value;

  private PaymentMethodType(String name, String value) {
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
    return Long.valueOf(this.getValue());
  }

  /**
   * 指定されたコード名を持つ支払方法区分を返します。
   *
   * @param name コード名
   * @return 支払方法区分
   */
  public static PaymentMethodType fromName(String name) {
    for (PaymentMethodType p : PaymentMethodType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ支払方法区分を返します。
   *
   * @param value コード値
   * @return 支払方法区分
   */
  public static PaymentMethodType fromValue(String value) {
    for (PaymentMethodType p : PaymentMethodType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ支払方法区分を返します。
   *
   * @param value コード値
   * @return 支払方法区分
   */
  public static PaymentMethodType fromValue(Long value) {
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
      for (PaymentMethodType p : PaymentMethodType.values()) {
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
