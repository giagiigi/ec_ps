package jp.co.sint.webshop.data.dto;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil; //10.1.4 10151 追加

/**
 * JSON形式の文字列をユーティリティクラスです。
 * 
 * @author System Integrator Corp.
 */
public final class JsonUtil {

  private JsonUtil() {
  }

  /**
   * プロパティの名称と値を受け取って、JSON形式のプロパティ文字列を返します。 ダブルクォート(")およびバックスラッシュ(\)はエスケープされます。
   * 
   * @param name
   *          プロパティの名前を指定します。
   * @param value
   *          プロパティの値を指定します。
   * @return "name":"value"の形式でプロパティを返します。
   */
  public static String getPair(String name, String value) {
    StringBuilder builder = new StringBuilder();
    builder.append("\"" + escape(name) + "\"");
    builder.append(":");
    builder.append("\"" + escape(value) + "\"");
    return builder.toString();
  }

  private static String escape(String str) {
    String result = str;
    if (StringUtil.hasValue(result)) {
      result = result.replace("\\", "\\\\");
      result = result.replace("\"", "\\\\\"");
    }
    return result;
  }

  //10.1.4 10151 追加 ここから
  /**
   * 文字列をURLエンコードします。エンコーディングにはUTF-8が使われます。
   * 
   * JavaScriptのdecodeURIComponentが"+"を半角スペースに戻さないための対策として、
   * WebUtil.urlEncodeの処理後、"+"から"%20"への置換が行われます。
   * 
   * @param str
   *          エンコード対象文字列
   * @return エンコーディングしたURLを返します。
   */
  public static String urlEncode(String str) {
    return WebUtil.urlEncode(str).replace("+", "%20");
  }
  //10.1.4 10151 追加 ここまで

}
