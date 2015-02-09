
package jp.co.sint.webshop.data.domain;
import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「时间」のコード定義を表す列挙クラスです。
 *
 * @author System  OB.
 *
 */
public enum Day implements CodeAttribute {

  DAY_01("E", "01"),
  
  DAY_02("Q", "02"),
  
  DAY_03("K", "03"),
  
  DAY_04("2", "04"),
  
  DAY_05("i", "05"),
  
  DAY_06("A", "06"),
  
  DAY_07("G", "07"),
  
  DAY_08("l", "08"),
  
  DAY_09("j", "09"),
  
  DAY_10("a", "10"),
  
  DAY_11("6", "11"),
  
  DAY_12("v", "12"),
  
  DAY_13("s", "13"),
  
  DAY_14("O", "14"),
  
  DAY_15("7", "15"),
  
  DAY_16("B", "16"),
  
  DAY_17("p", "17"),
  
  DAY_18("1", "18"),
  
  DAY_19("V", "19"),
  
  DAY_20("c", "20"),
  
  DAY_21("q", "21"),
  
  DAY_22("M", "22"),
  
  DAY_23("3", "23"),
  
  DAY_24("L", "24"),
  
  DAY_25("Y", "25"),
  
  DAY_26("z", "26"),
  
  DAY_27("4", "27"),
  
  DAY_28("T", "28"),
  
  DAY_29("t", "29"),
  
  DAY_30("5", "30"),
  
  DAY_31("k", "31");

  private String name;

  private String value;

  private Day(String name, String value) {
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
   * 指定されたコード名を持つ口座区分を返します。
   *
   * @param name コード名
   * @return 口座区分
   */
  public static Day fromName(String name) {
    for (Day p : Day.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ口座区分を返します。
   *
   * @param value コード値
   * @return 口座区分
   */
  public static Day fromValue(String value) {
    for (Day p : Day.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ口座区分を返します。
   *
   * @param value コード値
   * @return 口座区分
   */
  public static Day fromValue(Long value) {
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
      for (Day p : Day.values()) {
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
