package jp.co.sint.webshop.code;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * CodeAttributeインタフェースの簡易実装クラスです。
 * 
 * @author System Integrator Corp.
 */
public class NameValue implements CodeAttribute, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String name;

  private String value;

  /**
   * 新しいNameValueのインスタンスを作成します。
   */
  public NameValue() {
    this.setName("");
    this.setValue("");
  }

  /**
   * 名称、値を指定して新しいNameValueのインスタンスを作成します。
   * 
   * @param name
   *          コード名称
   * @param value
   *          コード値
   */
  public NameValue(String name, String value) {
    this.setName(name);
    this.setValue(value);
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
   * コード名称を設定します
   * 
   * @param name
   *          コード名称
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * コード値を設定します
   * 
   * @param value
   *          コード値
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * パターン文字列からNameValueのリストを生成します。
   * パターンは名前と値の区切りが「:」、ペアの区切りが「/」となる「name1:value1/name2:value2/...」のような文字列です。
   * 
   * @param source
   *          パターン文字列
   * @return NameValueのリスト
   */
  public static List<NameValue> asList(String source) {
    Logger logger = Logger.getLogger(NameValue.class);
    List<NameValue> list = new ArrayList<NameValue>();
    try {
      String[] elements = source.split("/");
      for (String s : elements) {
        String[] nv = s.split(":");
        String name = "";
        String value = "";
        if (nv.length > 1) {
          name = nv[1];
        }
        if (nv.length > 0) {
          value = nv[0];
        }
        // list.add(new NameValue(nv[1], nv[0]));
        list.add(new NameValue(name, value));
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return list;
  }

  /**
   * パターン文字列から文字列(java.lang.String)のリストを生成します。
   * パターンは値の区切りが「/」となる「value1/value2/...」のような文字列です。
   * 
   * @param source
   *          パターン文字列
   * @return Stringのリスト
   */
  public static List<String> asListValue(String source) {
    Logger logger = Logger.getLogger(NameValue.class);
    List<String> list = new ArrayList<String>();
    try {
      String[] elements = source.split("/");
      for (int i = 0; i < elements.length; i++) {
        list.add(elements[i]);
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return list;
  }

}
