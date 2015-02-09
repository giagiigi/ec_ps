
package jp.co.sint.webshop.data.domain;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「优惠券类别」のコード定義を表す列挙クラスです。
 *
 * @author OB.
 *
 */
public enum CouponType implements CodeAttribute {

  /** 「购买发行」を表す値です。 */
  PURCHASE_DISTRIBUTION("购买发行", "0"),

  /** 「特别会员发行」を表す値です。 */
  SPECIAL_MEMBER_DISTRIBUTION("特别会员发行", "1"),
  
  /** 「公共发行」を表す値です。 */
  COMMON_DISTRIBUTION("公共发行", "2"),
  
  /** 「誕生日発行」を表す値です。 */
  BIRTHDAY_DISTRIBUTION("誕生日発行", "3"),
  
  /** 「顾客组别」を表す値です。 */
  CUSTOMER_GROUP("顾客组别", "9");

  private String name;

  private String value;

  private CouponType(String name, String value) {
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
   * 指定されたコード名を持つキャンペーンタイプを返します。
   *
   * @param name コード名
   * @return キャンペーンタイプ
   */
  public static CouponType fromName(String name) {
    for (CouponType p : CouponType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つキャンペーンタイプを返します。
   *
   * @param value コード値
   * @return キャンペーンタイプ
   */
  public static CouponType fromValue(String value) {
    for (CouponType p : CouponType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つキャンペーンタイプを返します。
   *
   * @param value コード値
   * @return キャンペーンタイプ
   */
  public static CouponType fromValue(Long value) {
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
      for (CouponType p : CouponType.values()) {
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
