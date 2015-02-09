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
 * 「库存变更区分」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum StockChangeType implements CodeAttribute {

  /** 「总库存变动」を表す値です。 */
  ALL("总库存变动", "0"),

  /** 「EC引当变动」を表す値です。 */
  EC_ALLOCATED("EC引当变动", "1"),
  
  /** 「TMALL引当变动」を表す値です。 */
  TM_ALLOCATED("TMALL引当变动", "2"),

  /** 「JD引当变动」を表す値です。 */
  JD_ALLOCATED("JD引当变动", "3"),
  
  /** 「安全库存变动」を表す値です。 */
  SAFE_ALLOCATED("安全库存变动", "4"),
  
  /** 「TMALL库存变动」を表す値です。 */
  TM_STOCK_ADD("TMALL库存变动", "5"),
  
  /** 「JD库存变动」を表す値です。 */
  JD_STOCK_ADD("JD库存变动", "6");
  
  private String name;

  private String value;

  private StockChangeType(String name, String value) {
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
   * 指定されたコード名を持つ販売フラグを返します。
   *
   * @param name コード名
   * @return 販売フラグ
   */
  public static SaleFlg fromName(String name) {
    for (SaleFlg p : SaleFlg.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ販売フラグを返します。
   *
   * @param value コード値
   * @return 販売フラグ
   */
  public static SaleFlg fromValue(String value) {
    for (SaleFlg p : SaleFlg.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ販売フラグを返します。
   *
   * @param value コード値
   * @return 販売フラグ
   */
  public static SaleFlg fromValue(Long value) {
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
      for (SaleFlg p : SaleFlg.values()) {
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
