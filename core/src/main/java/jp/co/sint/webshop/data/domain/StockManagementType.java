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
 * 「在庫管理区分」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum StockManagementType implements CodeAttribute {

  /** 「在庫管理しない」を表す値です。 */
  NONE("在庫管理しない", "0"), 

  /** 「在庫なし販売する」を表す値です。 */
  NOSTOCK("在庫なし販売する", "1"),

  /** 「在庫管理する(在庫数表示)」を表す値です。 */
  WITH_QUANTITY("在庫管理する(在庫数表示)", "2"),

  /** 「在庫管理する(状況表示)」を表す値です。 */
  WITH_STATUS("在庫管理する(状況表示)", "3");

  private String name;

  private String value;

  private StockManagementType(String name, String value) {
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
   * 指定されたコード名を持つ在庫管理区分を返します。
   *
   * @param name コード名
   * @return 在庫管理区分
   */
  public static StockManagementType fromName(String name) {
    for (StockManagementType p : StockManagementType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ在庫管理区分を返します。
   *
   * @param value コード値
   * @return 在庫管理区分
   */
  public static StockManagementType fromValue(String value) {
    for (StockManagementType p : StockManagementType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ在庫管理区分を返します。
   *
   * @param value コード値
   * @return 在庫管理区分
   */
  public static StockManagementType fromValue(Long value) {
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
      for (StockManagementType p : StockManagementType.values()) {
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
