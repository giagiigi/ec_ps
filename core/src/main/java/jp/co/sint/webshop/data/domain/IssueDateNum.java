package jp.co.sint.webshop.data.domain;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum IssueDateNum implements CodeAttribute {
  /** 1月 */
  JANUARY("一月", "0"),
  /** 2月 */
  FEBRUARY("二月", "1"),
  /** 3月 */
  MARCH("三月", "2"),
  /** 4月 */
  APRIL("四月", "3"),
  /** 5月 */
  MAY("五月", "4"),
  /** 6月 */
  JUNE("六月", "5"),
  /** 7月 */
  JULY("七月", "6"),
  /** 8月 */
  AUGUST("八月", "7"),
  /** 9月 */
  SEPTEMBER("九月", "8"),
  /** 10月 */
  OCTOBER("十月", "9"),
  /** 11月 */
  NOVEMBER("十一月", "10"),
  /** 12月 */
  DECEMBER("十二月", "11");

  private String name;

  private String value;

  private IssueDateNum(String name, String value) {
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
   * 指定されたコード名を持つ都道府県コードを返します。
   * 
   * @param name
   *          コード名
   * @return 都道府県コード
   */
  public static PrefectureCode fromName(String name) {
    for (PrefectureCode p : PrefectureCode.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ都道府県コードを返します。
   * 
   * @param value
   *          コード値
   * @return 都道府県コード
   */
  public static PrefectureCode fromValue(String value) {
    for (PrefectureCode p : PrefectureCode.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ都道府県コードを返します。
   * 
   * @param value
   *          コード値
   * @return 都道府県コード
   */
  public static PrefectureCode fromValue(Long value) {
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
      for (PrefectureCode p : PrefectureCode.values()) {
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
