package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 
 * 「検索キー」のコード定義を表す列挙クラスです。
 * 
 * @author System Integrator Corp.
 *  
 */
public enum SearchKey {
  /** */
  KEYWORD("キーワード", "0"),
  /** */
  COMMODITY_CODE("商品コード", "1"),
  /** */
  CATEGORY("商品カテゴリ", "2"),
  /** */
  SHOP_CODE("ショップコード", "3"),
  /** */
  REVIEW_SCORE("レビュースコア", "4"),
  /** */
  PRICE("価格", "5");

  private String name;

  private String value;

  private SearchKey(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return StringUtil.coalesce(CodeUtil.getName(this), this.name);
  }

  public String getValue() {
    return StringUtil.coalesce(CodeUtil.getValue(this), this.value);
  }
}