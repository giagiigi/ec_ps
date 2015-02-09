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
 * 「商品検索表示順」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum MobileCommodityDisplayOrder implements CodeAttribute {

  /** 「人気順」を表す値です。 */
  BY_POPULAR_RANKING("デフォルト", "1"),

  /** 「価格の安い順」を表す値です。 */
  BY_PRICE_ASCENDING("価格", "2"),

  /** 「価格の高い順」を表す値です。 */
  BY_PRICE_DESCENDING("価格", "3"),

  /** 「商品名順」を表す値です。 */
  BY_COMMODITY_NAME("評価", "4"),

  /** 「おすすめ順」を表す値です。 */
  BY_RECOMMEND_SCORE("点数", "5");

  private String name;

  private String value;

  private MobileCommodityDisplayOrder(String name, String value) {
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
   * 指定されたコード名を持つ商品検索表示順を返します。
   *
   * @param name コード名
   * @return 商品検索表示順
   */
  public static MobileCommodityDisplayOrder fromName(String name) {
    for (MobileCommodityDisplayOrder p : MobileCommodityDisplayOrder.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ商品検索表示順を返します。
   *
   * @param value コード値
   * @return 商品検索表示順
   */
  public static MobileCommodityDisplayOrder fromValue(String value) {
    for (MobileCommodityDisplayOrder p : MobileCommodityDisplayOrder.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ商品検索表示順を返します。
   *
   * @param value コード値
   * @return 商品検索表示順
   */
  public static MobileCommodityDisplayOrder fromValue(Long value) {
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
      for (MobileCommodityDisplayOrder p : MobileCommodityDisplayOrder.values()) {
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
