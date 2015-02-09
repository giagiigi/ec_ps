package jp.co.sint.webshop.web.log.back;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * アクセスログ区分とサブシステムIDを保持するEnum
 * 
 * @author System Integrator Corp.
 */
public enum AccessLogDiv implements CodeAttribute {

  /** 「共通」を表す値です。 */
  COMMON("共通", "0", "common"),

  /** 「受注管理」を表す値です。 */
  ORDER("受注管理", "1", "order"),

  /** 「顧客管理」を表す値です。 */
  CUSTOMER("顧客管理", "2", "customer"),

  /** 「商品管理」を表す値です。 */
  CATALOG("商品管理", "3", "catalog"),

  /** 「ショップ管理」を表す値です。 */
  SHOP("ショップ管理", "4", "shop"),

  /** 「コミュニケーション」を表す値です。 */
  COMMUNICATION("コミュニケーション", "5", "communication"),

  /** 「分析」を表す値です。 */
  ANALYSIS("分析", "6", "analysis"),

  /** 「データ入出力」を表す値です。 */
  DATA_IO("データ入出力", "7", "data"),
  
  /** 「内容管理」を表す値です。 */
  CONTENT("内容管理", "8", "content");

  private String name;

  private String value;

  private String subSystemId;

  private AccessLogDiv(String name, String value, String subSystemId) {
    this.name = name;
    this.value = value;
    this.subSystemId = subSystemId;
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
   * 指定されたコード値を持つアクセスログ区分を返します。
   * 
   * @param value
   *          コード値
   * @return アクセスログ区分
   */
  public static AccessLogDiv fromValue(String value) {
    for (AccessLogDiv p : AccessLogDiv.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたサブシステムIDを持つアクセスログ区分を返します。
   * 
   * @param subSystemId
   *          サブシステムID
   * @return アクセスログ区分
   */
  public static AccessLogDiv fromSubSystemId(String subSystemId) {
    for (AccessLogDiv p : AccessLogDiv.values()) {
      if (p.getSubSystemId().equals(subSystemId)) {
        return p;
      }
    }
    return null;
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
      for (AccessLogDiv p : AccessLogDiv.values()) {
        if (p.getValue().equals(value)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * サブシステムIDを取得します。。
   * 
   * @return サブシステムID
   */
  public String getSubSystemId() {
    return subSystemId;
  }
}
