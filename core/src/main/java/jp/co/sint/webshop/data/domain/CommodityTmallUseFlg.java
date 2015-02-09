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
 *tm子商品使用标志
 * @author System Integrator Corp.
 *
 */
public enum CommodityTmallUseFlg implements CodeAttribute {
  ONTMUSEFLG("否","0"),
  ISTMUSEFLG("是","1");
  private String name;

  private String value;

  private CommodityTmallUseFlg(String name, String value) {
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
   * 指定されたコード名を持つ表示フラグを返します。
   *
   * @param name コード名
   * @return 表示フラグ
   */
  public static CommodityTmallUseFlg fromName(String name) {
    for (CommodityTmallUseFlg p : CommodityTmallUseFlg.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ表示フラグを返します。
   *
   * @param value コード値
   * @return 表示フラグ
   */
  public static CommodityTmallUseFlg fromValue(String value) {
    for (CommodityTmallUseFlg p : CommodityTmallUseFlg.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ表示フラグを返します。
   *
   * @param value コード値
   * @return 表示フラグ
   */
  public static CommodityTmallUseFlg fromValue(Long value) {
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
      for (CommodityTmallUseFlg p : CommodityTmallUseFlg.values()) {
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
