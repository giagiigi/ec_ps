//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはコード定義ドキュメントから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.code;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「メニューグループ」のコード定義を表す列挙クラスです。
 * 
 * @author System Integrator Corp.
 */
public enum MenuGroup implements CodeAttribute {

  /** 「受注管理」を表す値です。 */
  ORDER("受注管理", "1"),

  /** 「顧客管理」を表す値です。 */
  CUSTOMER("顧客管理", "2"),

  /** 「商品管理」を表す値です。 */
  CATALOG("商品管理", "3"),

  /** 「ショップ管理」を表す値です。 */
  SHOP("ショップ管理", "4"),

  /** 「コミュニケーション」を表す値です。 */
  COMMUNICATION("コミュニケーション", "5"),

  /** 「分析」を表す値です。 */
  ANALYSIS("分析", "6"),

  /** 「データ入出力」を表す値です。 */
  DATA_IO("データ入出力", "7"),

  /** 「电话中心管理」を表す値です。 */
  SERVICE("客服系统", "8"),

  /** 「内容管理」を表す値です。 */
  CONTENT("内容管理", "9");

  private String name;

  private String value;

  private MenuGroup(String name, String value) {
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
   * 指定されたコード名を持つメニューグループを返します。
   * 
   * @param name
   *          コード名
   * @return メニューグループ
   */
  public static MenuGroup fromName(String name) {
    for (MenuGroup p : MenuGroup.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つメニューグループを返します。
   * 
   * @param value
   *          コード値
   * @return メニューグループ
   */
  public static MenuGroup fromValue(String value) {
    for (MenuGroup p : MenuGroup.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つメニューグループを返します。
   * 
   * @param value
   *          コード値
   * @return メニューグループ
   */
  public static MenuGroup fromValue(Long value) {
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
      for (MenuGroup p : MenuGroup.values()) {
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
