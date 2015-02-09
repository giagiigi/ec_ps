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
 * 「代金券使用区分」のコード定義を表す列挙クラスです。
 *
 * @author Swj.
 *
 */
public enum CouponUse implements CodeAttribute {

  /** 「未使用」を表す値です。 */
  UNUSED("未使用", "0"),

  /** 「已使用」を表す値です。 */
  USE("已使用", "1"),
  
  // 20111026 yuyongqiang add start
  OVERDATE("已过期", "2");
  // 20111026 yuyongqiang add end

  private String name;

  private String value;

  private CouponUse(String name, String value) {
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
   * 指定されたコード名を持つ代金券使用区分を返します。
   *
   * @param name コード名
   * @return 代金券使用区分
   */
  public static CouponUse fromName(String name) {
    for (CouponUse p : CouponUse.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ代金券使用区分を返します。
   *
   * @param value コード値
   * @return 代金券使用区分
   */
  public static CouponUse fromValue(String value) {
    for (CouponUse p : CouponUse.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ代金券使用区分を返します。
   *
   * @param value コード値
   * @return 代金券使用区分
   */
  public static CouponUse fromValue(Long value) {
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
      for (CouponUse p : CouponUse.values()) {
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
