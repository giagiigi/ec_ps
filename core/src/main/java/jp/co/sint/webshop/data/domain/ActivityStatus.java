package jp.co.sint.webshop.data.domain;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「活动状态」のコード定義を表す列挙クラスです。
 *
 * @author OB.
 *
 */
public enum ActivityStatus implements CodeAttribute {

  /** 「进行中」を表す値です。 */
  IN_PROGRESS("进行中", "1"),

  /** 「未进行」を表す値です。 */
  NOT_PROGRESS("未进行", "2"),

  /** 「已结束」を表す値です。 */
  OUT_PROGRESS("已结束", "3");

  private String name;

  private String value;

  private ActivityStatus(String name, String value) {
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
   * 指定されたコード名を持つ活动状态を返します。
   *
   * @param name コード名
   * @return 活动状态
   */
  public static ReturnItemType fromName(String name) {
    for (ReturnItemType p : ReturnItemType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ活动状态を返します。
   *
   * @param value コード値
   * @return 活动状态
   */
  public static ReturnItemType fromValue(String value) {
    for (ReturnItemType p : ReturnItemType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ活动状态を返します。
   *
   * @param value コード値
   * @return 活动状态
   */
  public static ReturnItemType fromValue(Long value) {
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
      for (ReturnItemType p : ReturnItemType.values()) {
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
