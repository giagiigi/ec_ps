package jp.co.sint.webshop.ext.veritrans;

import jp.co.sint.webshop.code.CodeAttribute;

/**
 * 「ベリトランス電子マネー決済方法区分」のコード定義を表す列挙クラスです。
 * 
 * @author System Integrator Corp.
 */
public enum VeritransDigitalCashType implements CodeAttribute {

  /** Mobile Edy */
  MOBILE_EDY("Mobile Edy", "11"),

  /** Suica(メール決済) */
  MAIL_SUICA("Suicaメール決済", "21");

  private String name;

  private String value;

  private VeritransDigitalCashType(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public static VeritransDigitalCashType fromName(String name) {
    for (VeritransDigitalCashType s : VeritransDigitalCashType.values()) {
      if (s.getName().equals(name)) {
        return s;
      }
    }
    return null;
  }

  public static VeritransDigitalCashType fromValue(String value) {
    for (VeritransDigitalCashType s : VeritransDigitalCashType.values()) {
      if (s.getValue().equals(value)) {
        return s;
      }
    }
    return null;
  }

  public static boolean isValid(String value) {
    for (VeritransDigitalCashType s : VeritransDigitalCashType.values()) {
      if (s.getValue().equals(value)) {
        return true;
      }
    }
    return false;
  }

}
