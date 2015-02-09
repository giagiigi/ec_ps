package jp.co.sint.webshop.data.domain;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「ポイント拡大倍数」のコード定義を表す列挙クラスです。
 * 
 * @author System Integrator Corp.
 */
public enum PointAmplificationRate implements CodeAttribute {

  /** 「整数」を表す値です。 */
  SCALE_1("99999999990", "0"),
  
  /** 「整数」を表す値です。 */
  SCALE_2("99999999990.0", "1"),
  
  /** 「小数一位」を表す値です。 */
  SCALE_3("99999999990.00", "2");

  private String name;

  private String value;

  private PointAmplificationRate(String name, String value) {
    this.name = name;
    this.value = value;
  }

  /**
   * コード名称を返します。
   * 
   * @return コード名称
   */
  public String getName() {
    return StringUtil.coalesce(CodeUtil.getName(this), this.name);
  }

  /**
   * コード値を返します。
   * 
   * @return コード値
   */
  public String getValue() {
    return StringUtil.coalesce(CodeUtil.getValue(this), this.value);
  }

  /**
   * Long型のコード値を返します。
   * 
   * @return コード値
   */
  public Long longValue() {
    return Long.valueOf(this.value);
  }

  /**
   * 指定されたコード名を持つ権限区分を返します。
   * 
   * @param name
   *          コード名
   * @return 権限区分
   */
  public static PointAmplificationRate fromName(String name) {
    for (PointAmplificationRate p : PointAmplificationRate.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ権限区分を返します。
   * 
   * @param value
   *          コード値
   * @return 権限区分
   */
  public static PointAmplificationRate fromValue(String value) {
    for (PointAmplificationRate p : PointAmplificationRate.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ権限区分を返します。
   * 
   * @param value
   *          コード値
   * @return 権限区分
   */
  public static PointAmplificationRate fromValue(Long value) {
    return fromValue(Long.toString(value));
  }

  /**
   * 指定されたコード値が有効かどうかを返します。
   * 
   * @param value
   *          コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(String value) {
    if (StringUtil.hasValue(value)) {
      for (PointAmplificationRate p : PointAmplificationRate.values()) {
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
   * @param value
   *          コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(Long value) {
    return isValid(Long.toString(value));
  }
}
