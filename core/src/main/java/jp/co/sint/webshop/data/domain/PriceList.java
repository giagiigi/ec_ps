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
 * 「レビュースコア」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum PriceList implements CodeAttribute {

  /** 「50」を表す値です。 */
  ONE("0,50", "1"),

  /** 「100」を表す値です。 */
  TWO("51,100", "2"),

  /** 「150」を表す値です。 */
  THREE("101,150", "3"),

  /** 「200」を表す値です。 */
  FOUR("151,200", "4"),

  /** 「200以上」を表す値です。 */
  FIVE("200", "5");

  private String name;

  private String value;

  private PriceList(String name, String value) {
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
   * 指定されたコード名を持つレビュースコアを返します。
   *
   * @param name コード名
   * @return レビュースコア
   */
  public static PriceList fromName(String name) {
    for (PriceList p : PriceList.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つレビュースコアを返します。
   *
   * @param value コード値
   * @return レビュースコア
   */
  public static PriceList fromValue(String value) {
    for (PriceList p : PriceList.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つレビュースコアを返します。
   *
   * @param value コード値
   * @return レビュースコア
   */
  public static PriceList fromValue(Long value) {
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
      for (PriceList p : PriceList.values()) {
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
