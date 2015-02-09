package jp.co.sint.webshop.ext.veritrans;

import jp.co.sint.webshop.code.CodeAttribute;

/** 
 * 「ベリトランスコンビニ決済コンビニコード」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 */
public enum VeritransCvsCode implements CodeAttribute {

  /** セブンイレブン */
  SEVEN_ELEVEN("セブンイレブン", "01"),

  /** ローソン・セイコーマート */
  LAWSON("ローソン・セイコーマート", "02"),

  /** ファミリーマート */
  FAMILY_MART("ファミリーマート", "03"),

  /** サークルKサンクス、その他 */
  CIRCLE_K("サークルKサンクス、その他", "04"),

  /** ペイジー */
  PAY_EASY("ペイジー", "30");

  private String name;
  
  private String value;

  private VeritransCvsCode(String name, String value) {
    this.name = name;
    this.value = value;
  }
  
  public String getName() {
    return this.name;
  }

  public String getValue() {
    return this.value;
  }
  
  public static VeritransCvsCode fromName(String name) {
    for (VeritransCvsCode c : VeritransCvsCode.values()) {
      if (c.getName().equals(name)) {
        return c;
      }
    }
    return null;
  }
  
  public static VeritransCvsCode fromValue(String value) {
    for (VeritransCvsCode c : VeritransCvsCode.values()) {
      if (c.getValue().equals(value)) {
        return c;
      }
    }
    return null;
  }
  
  public static boolean isValid(String value) {
    for (VeritransCvsCode c : VeritransCvsCode.values()) {
      if (c.getValue().equals(value)) {
        return true;
      }
    }
    return false;
  }
  
}
