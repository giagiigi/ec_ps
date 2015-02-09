package jp.co.sint.webshop.service.data;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「コンテンツ区分」のコード定義を表す列挙クラスです。
 * 
 * @author System Integrator Corp.
 */
public enum ContentsDivision implements CodeAttribute {

  /** 「コンテンツ」を表す値です。 */
  CONTENTS("PCコンテンツ", "1"),

  /** 「画像データ」を表す値です。 */
  IMAGE_DATA("画像データ", "2"),

  /** 「テキストファイル」を表す値です。 */
  TEXT_DATA("画像データ", "2");

  private String name;

  private String value;

  private ContentsDivision(String name, String value) {
    this.name = name;
    this.value = value;
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
    return value;
  }

  /**
   * 指定されたコード名を持つコンテンツ区分を返します。
   * 
   * @param name
   *          コード名
   * @return コンテンツ区分
   */
  public static ContentsDivision fromName(String name) {
    for (ContentsDivision p : ContentsDivision.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つコンテンツ区分を返します。
   * 
   * @param value
   *          コード値
   * @return コンテンツ区分
   */
  public static ContentsDivision fromValue(String value) {
    for (ContentsDivision p : ContentsDivision.values()) {
      if (p.getValue().equals(value)) {
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
      for (ContentsDivision p : ContentsDivision.values()) {
        if (p.getValue().equals(value)) {
          return true;
        }
      }
    }
    return false;
  }
}
