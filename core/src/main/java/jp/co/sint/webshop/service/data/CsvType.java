package jp.co.sint.webshop.service.data;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * CSVのコード定義を表す列挙クラスです。<br>
 * 
 * @author System Integrator Corp.
 */

public enum CsvType implements CodeAttribute {

  /** 「CSVタイプ」を表す値です。 */
  CSV_CUSTOMER("customer", "顧客マスタ"),

  CSV_CUSTOMER_ADDRESS("customer_address", "顧客アドレス"),

  CSV_CATEGORY("category", "カテゴリ"),

  CSV_CATEGORY_ATTRIBUTE("category_attribute", "カテゴリ属性名称"),

  CSV_CATEGORY_ATTRIBUTE_VALUE("category_attribute_value", "カテゴリ属性値"),

  CSV_COMMODITY_HEADER("commodity_header", "商品ヘッダ"),

  CSV_COMMODITY_DETAIL("commodity_detail", "商品明細"),

  CSV_CATEGORY_COMMODITY("category_commodity", "カテゴリ陳列商品"),

  CSV_CAMPAIGN_COMMODITY("campaign_commodity", "商品キャンペーン"),

  CSV_TAG_COMMODITY("tag_commodity", "商品タグ"),

  CSV_TAG("tag", "タグ"),

  CSV_RELATED_COMMODITY_A("related_commodity_a", "関連商品A"),

  CSV_GIFT("gift", "ギフト"),

  CSV_STOCK("stock", "在庫"),

  CSV_ORDER_SITE("ordersite", "受注"),

  CSV_ORDER_SHOP("ordershop", "受注"),

  CSV_ARRIVAL_GOODS("arrival_goods", "入荷お知らせ"),

  CSV_HOLIDAY("holiday", "休日"),

  CSV_SHIPPING_REPORT("shipping_report", "出荷実績登録"),

  CSV_CAMPAIGN("campaign", "キャンペーン"),

  CSV_CAMPAIGN_RESEARCH("campaign_research", "キャンペーン分析"),

  CSV_ENQUETE_SUMMARY("enquete_summary", "アンケート結果"),

  CSV_MAIL_MAGAZINE("mail_magazine", "メールマガジン");

  private String code;

  private String name;

  private CsvType(String code, String name) {
    this.code = code;
    this.name = name;
  }

  /**
   * コード名称を返します。
   * 
   * @return コード名称
   */
  public String getName() {
    return name;
  }

  /**
   * コード値を返します。
   * 
   * @return コード値
   */
  public String getValue() {
    return code;
  }

  /**
   * 指定されたコード名を持つファイル種別を返します。
   * 
   * @param name
   *          コード名
   * @return ファイル種別
   */
  public static CsvType fromName(String name) {
    for (CsvType p : CsvType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つファイル種別を返します。
   * 
   * @param code
   *          コード値
   * @return ファイル種別
   */
  public static CsvType fromValue(String code) {
    for (CsvType p : CsvType.values()) {
      if (p.getValue().equals(code)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値が有効かどうかを返します。
   * 
   * @param code
   *          コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(String code) {
    if (StringUtil.hasValue(code)) {
      for (CsvType p : CsvType.values()) {
        if (p.getValue().equals(code)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * codeを取得します。
   * 
   * @return code
   */
  public String getCode() {
    return code;
  }

}
