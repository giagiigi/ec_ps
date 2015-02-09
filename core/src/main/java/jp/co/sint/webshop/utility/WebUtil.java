package jp.co.sint.webshop.utility;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress; //10.1.3 10172 追加
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.service.data.ContentsPath;

import org.apache.log4j.Logger;

/**
 * XML/HTMLなどの文字列処理を担当するユーティリティクラスです。
 * 
 * @author System Integrator Corp.
 */
public final class WebUtil {

  private static final String WEBUTIL_DEFAULT_ENCODING = "UTF-8";

  private static final int OCTET = 8;  //10.1.3 10172 追加

  private WebUtil() {
  }

  /**
   * 引数で受取った郵便番号を、ハイフン区切りだった場合はハイフン無し、<br />
   * ハイフン無しだった場合はハイフン区切りへと変換します。
   * 
   * @param postalCode
   *          郵便番号文字列
   * @return 変換した郵便番号文字列を返します。
   */
  public static String convertPostalCode(String postalCode) {
    if (postalCode == null) {
      return null;
    }
    String convertedPostalCode = postalCode;

    if (convertedPostalCode.length() == 7) {
      convertedPostalCode = convertedPostalCode.substring(0, 3) + "-" + convertedPostalCode.substring(3);
    } else if (convertedPostalCode.length() == 8) {
      convertedPostalCode = convertedPostalCode.replace("-", "");
      convertedPostalCode = convertedPostalCode.replace("－", "");
    }

    return convertedPostalCode;

  }

  /**
   * 文字列をURLエンコードします。エンコーディングにはUTF-8が使われます。
   * 
   * @param str
   *          エンコード対象文字列
   * @return エンコーディングしたURLを返します。
   */
  public static String urlEncode(String str) {
    return urlEncode(str, WEBUTIL_DEFAULT_ENCODING);
  }

  /**
   * エンコーディングを指定して、文字列をURLエンコードします。
   * 
   * @param str
   *          エンコード対象文字列
   * @param encoding
   *          エンコーディング
   * @return エンコードしたURLを返します。
   */
  public static String urlEncode(String str, String encoding) {
    if (str == null || str.length() == 0) {
      return str;
    }
    String result = "";
    try {
      result = URLEncoder.encode(str, encoding);
    } catch (UnsupportedEncodingException e) {
      Logger logger = Logger.getLogger(WebUtil.class);
      logger.error(e);
      result = "";
    }
    return result;
  }

  /**
   * 文字列をURLデコードします。デコーディングにはUTF-8が使われます。
   * 
   * @param str
   *          デコード対象文字列
   * @return デコードしたURLを返します。
   */
  public static String urlDecode(String str) {
    return urlDecode(str, WEBUTIL_DEFAULT_ENCODING);
  }

  /**
   * エンコーディングを指定して、文字列をURLデコードします。
   * 
   * @param str
   *          デコード対象文字列
   * @param encoding
   *          エンコーディング
   * @return デコードしたURLを返します。
   */
  public static String urlDecode(String str, String encoding) {
    if (str == null || str.length() == 0) {
      return str;
    }
    String result = "";
    try {
      result = URLDecoder.decode(str, encoding);
    } catch (UnsupportedEncodingException e) {
      Logger logger = Logger.getLogger(WebUtil.class);
      logger.error(e);
      result = "";
    }

    return result;
  }

  /**
   * 日付を取得します。<br>
   * 引数として受取った日付が複数ある場合、最初の日付を返します。
   * 
   * @param parameterMap
   *          requestParameterが格納されたMapインスタンス<br>
   *          _yy、_mm、_ddは自動で検知します。
   * @param baseName
   *          パラメータ(ベース)名
   * @return 日付を返します。
   */
  public static String getDateParameter(Map<String, String[]> parameterMap, String baseName) {
    return getDateParameter(parameterMap, baseName, 0);
  }

  /**
   * 引数として受取った日付を指定して取得します。
   * 
   * @param parameterMap
   *          requestParameterが格納されたMapインスタンス
   * @param baseName
   *          パラメータ(ベース)名
   * @param index
   *          ポインタ
   * @return 日付を返します。
   */
  public static String getDateParameter(Map<String, String[]> parameterMap, String baseName, int index) {
    String result = StringUtil.EMPTY;
    String[] years = parameterMap.get(baseName + "_yy");
    String[] months = parameterMap.get(baseName + "_mm");
    String[] dates = parameterMap.get(baseName + "_dd");
    if (years != null && years.length > 0 && months != null && months.length > 0 && dates != null && dates.length > 0) {
      result = years[index] + "/" + months[index] + "/" + dates[index];
    } else {
      result = StringUtil.EMPTY;
    }
    return result;

  }

  /**
   * 郵便番号を取得します。<br>
   * 引数として受取った郵便番号が複数ある場合、最初の郵便番号を返します。
   * 
   * @param parameterMap
   *          requestParameterが格納されたMapインスタンス<br>
   *          _1、_2は自動で検知します。
   * @param baseName
   *          パラメータ(ベース)名
   * @return 郵便番号を返します。
   */
  public static String getPostalParameter(Map<String, String[]> parameterMap, String baseName) {
    return getPostalParameter(parameterMap, baseName, 0);
  }

  /**
   * 引数として受取った郵便番号を指定して取得します。
   * 
   * @param parameterMap
   *          requestParameterが格納されたMapインスタンス<br>
   *          _1、_2は自動で検知します。
   * @param baseName
   *          パラメータ(ベース)名
   * @param index
   *          ポインタ
   * @return 郵便番号を返します。
   */
  public static String getPostalParameter(Map<String, String[]> parameterMap, String baseName, int index) {
    String result = StringUtil.EMPTY;
    String[] p1 = parameterMap.get(baseName + "_1");
    String[] p2 = parameterMap.get(baseName + "_2");
    if (p1 != null && p1.length > 0 && p2 != null && p2.length > 0) {
      result = p1[index] + "/" + p2[index];
    } else {
      result = StringUtil.EMPTY;
    }
    return result;

  }

  /**
   * 電話番号を取得します。<br>
   * 引数として受取った電話番号が複数ある場合、最初の電話番号を返します。
   * 
   * @param parameterMap
   *          requestParameterが格納されたMapインスタンス<br>
   *          _1、_2、_3は自動で検知します。
   * @param baseName
   *          パラメータ(ベース)名
   * @return 電話番号を返します。
   */
  public static String getPhoneNumberParameter(Map<String, String[]> parameterMap, String baseName) {
    return getPhoneNumberParameter(parameterMap, baseName, 0);
  }

  /**
   * 引数として受取った電話番号を指定して取得します。
   * 
   * @param parameterMap
   *          requestParameterが格納されたMapインスタンス<br>
   *          _1、_2は自動で検知します。
   * @param baseName
   *          パラメータ(ベース)名
   * @param index
   *          ポインタ
   * @return 電話番号を返します。
   */
  public static String getPhoneNumberParameter(Map<String, String[]> parameterMap, String baseName, int index) {
    String result = StringUtil.EMPTY;
    String[] no1 = parameterMap.get(baseName + "_1");
    String[] no2 = parameterMap.get(baseName + "_2");
    String[] no3 = parameterMap.get(baseName + "_3");
    if (hasStringData(no1) && no1[index].length() > 0 && hasStringData(no2) && no2[index].length() > 0 && hasStringData(no3)
        && no3[index].length() > 0) {
      result = no1[index] + "-" + no2[index] + "-" + no3[index];
    } else {
      result = StringUtil.EMPTY;
    }
    return result;

  }

  /**
   * 引数で受け取ったパラメータ・マップを新たに生成した配列のパラメータ・マップに設定します。
   * 
   * @param map
   *          requestParameterが格納されたMapインスタンス
   * @return 配列のパラメータ・マップ
   */
  public static Map<String, String[]> convert(Map<String, String> map) {
    Map<String, String[]> newMap = new HashMap<String, String[]>();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      newMap.put(entry.getKey(), new String[] {
        entry.getValue()
      });
    }
    return newMap;
  }

  /**
   * クエリ文字列を解析して、パラメータ・マップに変換します。エンコーディングにはUTF-8が使われます。
   * 
   * @param queryString
   *          URLエンコードされたクエリ文字列
   * @return パラメータ・マップを返します。
   */
  public static Map<String, String[]> parseQueryString(String queryString) {
    return parseQueryString(queryString, WEBUTIL_DEFAULT_ENCODING);
  }

  /**
   * クエリ文字列を解析して、パラメータ・マップに変換します。
   * 
   * @param queryString
   *          URLエンコードされたクエリ文字列
   * @param characterSet
   *          URLデコードに使用するキャラクタセット
   * @return パラメータ・マップを返します。
   */
  public static Map<String, String[]> parseQueryString(String queryString, String characterSet) {
    Logger logger = Logger.getLogger(WebUtil.class);
    Map<String, String[]> m = new HashMap<String, String[]>();
    try {
      String query = URLDecoder.decode(queryString, characterSet);
      String[] pairs = query.split("&");

      for (String pp : pairs) {
        String[] nv = pp.split("=");
        String key = nv[0];
        String value = "";
        if (nv.length > 1) {
          value = nv[1];
        }

        if (m.containsKey(key)) {
          String[] old = m.get(key);
          String[] next = new String[old.length + 1];
          System.arraycopy(old, 0, next, 0, old.length);
          next[old.length] = value;
        } else {
          m.put(key, new String[] {
            value
          });
        }
      }
    } catch (Exception e) {
      logger.error(e);
    }
    return m;
  }

  /**
   * デフォルト・エンコーディングでパラメータ・マップをクエリ文字列に変換します。エンコーディングにはUTF-8が使われます。
   * 
   * @param map
   *          パラメータ・マップ
   * @return クエリ文字列を返します。
   */
  public static String buildQueryString(Map<String, String[]> map) {
    return buildQueryString(map, WEBUTIL_DEFAULT_ENCODING);
  }

  /**
   * パラメータ・マップをクエリ文字列に変換します。
   * 
   * @param map
   *          パラメータ・マップ
   * @param characterEncoding
   *          エンコーディング
   * @return クエリ文字列を返します。
   */
  public static String buildQueryString(Map<String, String[]> map, String characterEncoding) {
    StringBuilder builder = new StringBuilder();
    for (Map.Entry<String, String[]> e : map.entrySet()) {
      for (String v : e.getValue()) {
        if (builder.length() > 0) {
          builder.append("&");
        }
        builder.append(e.getKey() + "=" + WebUtil.urlEncode(v, characterEncoding));
      }
    }
    return builder.toString();
  }

  /**
   * 文字列がnullまたは空文字かどうかを返します。
   * 
   * @param data
   * @return dataがnullでないかつ、文字列の長さが1以上の場合、trueを返します。<br>
   *         dataがnull、または文字列の長さが0以下の場合、falseを返します。
   */
  private static boolean hasStringData(String[] data) {
    return data != null && data.length > 0;
  }

  /**
   * フィールドのmaxlengthを取得します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>
   * <ol>
   * <li>取得したクラスに指定されたフィールドが存在すれば、maxlengthを返します。</li>
   * <li>フィールドが指定されていなければ、タグ名をフィールド名とします。</li>
   * <li>取得したクラスに指定されたフィールドが存在しない、またはエラーが発生した場合は空文字を返します。</li>
   * </dd>
   * </dl>
   * </ol>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param type
   *          クラス
   * @param attrField
   *          フィールド名
   * @param attrName
   *          タグ名
   * @return フィールドの文字数を返します。
   */
  public static String buildMaxlength(Class<?> type, String attrField, String attrName) {
    String result = "";

    if (type == null) {
      return result;
    }
    try {
      String fieldName = "";
      if (attrField == null) {
        fieldName = attrName;
      } else {
        fieldName = attrField;
      }

      Length len = type.getDeclaredField(fieldName).getAnnotation(Length.class);
      if (len != null) {
        result = Integer.toString(len.value());
      }
    } catch (NoSuchFieldException nsfEx) {
      // Logger.getLogger(WebUtil.class).warn(nsfEx);
      result = "";
    } catch (RuntimeException e) {
      // Logger.getLogger(WebUtil.class).warn(e);
      result = "";
    }
    return result;
  }

  /**
   * 受け取った文字列に含まれる置換対象文字をXML文字参照に置換します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>置換対象の文字は 「&lt;」(less-than sign)「&gt;」(greater-than sign)
   * 「&amp;」(ampersand)「&quot;」(quotation mark)
   * 「&#37;」(percent-sign)「&#39;」(apostrophe)
   * 「&#40;」(left-parenthesis)「&#41;」(right-parenthesis) 「&#59;」(semicolon)です。
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param str
   *          入力値
   * @return 入力値に含まれる置換文字をXML文字参照に変換したものを返します。 入力値がnullの場合はnullを返します。
   */
  public static String escapeXml(String str) {
    if (StringUtil.hasValue(str)) {
      StringBuilder builder = new StringBuilder();
      for (char c : str.toCharArray()) {
        switch (c) {
          case '<':
            builder.append("&lt;");
            break;
          case '>':
            builder.append("&gt;");
            break;
          case '\'':
            builder.append("&#39;");
            break;
          case '"':
            builder.append("&quot;");
            break;
          case '&':
            builder.append("&amp;");
            break;
          case '%':
            builder.append("&#37;");
            break;
          case ';':
            builder.append("&#59;");
            break;
          case '(':
            builder.append("&#40;");
            break;
          case ')':
            builder.append("&#41;");
            break;
          default:
            builder.append(c);
        }
      }
      return builder.toString();
    }
    return str;
  }

  /**
   * 携帯アプリケーションで出力するURLに含まれる、置換対象文字をXML文字参照に置換します。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>置換対象の文字はescapeXmlで変換される9種類の文字から、セミコロン(;→&#59;)を除外したものです。 </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>特にありません。</dd>
   * </dl>
   * </p>
   * 
   * @param str
   *          入力値
   * @return 入力値に含まれる置換文字をXML文字参照に変換したものを返します。 入力値がnullの場合はnullを返します。
   */
  public static String escapeMobileUrl(String str) {
    // ※AU端末でのHTTP POST送信時、URLにセミコロンをエンコードした
    // &#59;が含まれていると正しく動作しない問題に対応するためのメソッド
    if (StringUtil.hasValue(str)) {
      StringBuilder builder = new StringBuilder();
      for (char c : str.toCharArray()) {
        switch (c) {
          case '<':
            builder.append("&lt;");
            break;
          case '>':
            builder.append("&gt;");
            break;
          case '\'':
            builder.append("&#39;");
            break;
          case '"':
            builder.append("&quot;");
            break;
          case '&':
            builder.append("&amp;");
            break;
          case '%':
            builder.append("&#37;");
            break;
          case '(':
            builder.append("&#40;");
            break;
          case ')':
            builder.append("&#41;");
            break;
          default:
            builder.append(c);
        }
      }
      return builder.toString();
    }
    return str;
  }

  /**
   * 受け取った文字列に含まれるHTMLタグを削除します。
   * 
   * @param str
   *          入力値
   * @return 入力値に含まれるHTMLタグを削除したものを返します。 入力値がnullの場合はnullを返します。
   */
  public static String removeHtmlTag(String str) {
    if (StringUtil.hasValue(str)) {
      String[] elements = Pattern.compile("<[^>]*>").split(str);
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < elements.length; i++) {
        builder.append(elements[i]);
      }
      return builder.toString();
    }
    return str;
  }

  /**
   * 改行コード(CR, LF, CRLF)をXHTMLの改行タグ(&lt;br /&gt;)に置換します。
   * 
   * @param str
   *          入力文字列
   * @return 置換結果を返します。
   */
  public static String replaceLineFeeds(String str) {
    return replaceLineFeeds(str, "<br/>");
  }

  /**
   * 改行コード(CR, LF, CRLF)を指定された置換文字列で置換します。
   * 
   * @param str
   *          入力文字列
   * @param replacement
   *          置換文字列
   * @return 置換結果を返します。
   */
  public static String replaceLineFeeds(String str, String replacement) {
    Matcher matcher = Pattern.compile("(\r\n|\r|\n)").matcher(str);
    StringBuffer buffer = new StringBuffer();
    while (matcher.find()) {
      matcher.appendReplacement(buffer, replacement);
    }
    matcher.appendTail(buffer);
    return buffer.toString();
  }

  private static final String HTTPS_PROTOCOL = "https";

  private static final String HTTP_PROTOCOL = "http";

  private static final int HTTP_DEFAULT_PORT = 80;

  private static final int HTTPS_DEFAULT_PORT = 443;

  /**
   * secure = trueの場合はhttps、falseの場合はhttpのURLを生成し返します。
   * 
   * @param hostName
   *          ホスト名
   * @param portNo
   *          ポート番号
   * @param secure
   *          true:SSL保護 false:SSL保護でない
   * @return 指定されたパターン({0}://{1}{2})を使ってフォーマットしたものを返します。
   */
  public static String getHttpUrl(String hostName, int portNo, boolean secure) {
    if (secure) {
      return getSecureUrl(hostName, portNo);
    } else {
      return getNormalUrl(hostName, portNo);
    }
  }

  /**
   * プロトコルを「http」、ポート番号を「80」とし、URLをフォーマットします。
   * 
   * @param hostName
   *          ホスト名
   * @return フォーマットされたURL文字列を返します。
   */
  public static String getNormalUrl(String hostName) {
    return getNormalUrl(hostName, HTTP_DEFAULT_PORT);
  }

  /**
   * プロトコルを「http」、ポート番号を「80」とし、URLをフォーマットします。
   * 
   * @param hostName
   *          ホスト名
   * @param fileName
   *          ファイルのパス
   * @return フォーマットされたURL文字列を返します。
   */
  public static String getNormalUrl(String hostName, String fileName) {
    return getNormalUrl(hostName, HTTP_DEFAULT_PORT, fileName);
  }

  /**
   * プロトコルを「https」、ポート番号を「443」とし、URLをフォーマットします。
   * 
   * @param hostName
   *          ホスト名
   * @return フォーマットされたURL文字列を返します。
   */
  public static String getSecureUrl(String hostName) {
    return getSecureUrl(hostName, HTTPS_DEFAULT_PORT);
  }

  /**
   * プロトコルを「https」、ポート番号を「443」とし、URLをフォーマットします。
   * 
   * @param hostName
   *          ホスト名
   * @param fileName
   *          ファイルのパス
   * @return フォーマットされたURL文字列を返します。
   */
  public static String getSecureUrl(String hostName, String fileName) {
    return getSecureUrl(hostName, HTTPS_DEFAULT_PORT, fileName);
  }

  /**
   * プロトコルを「http」とし、URLをフォーマットします。
   * 
   * @param hostName
   *          ホスト名
   * @param portNo
   *          ポート番号
   * @return フォーマットされたURL文字列を返します。
   */
  public static String getNormalUrl(String hostName, int portNo) {
    return getUrl(HTTP_PROTOCOL, hostName, portNo, "");
  }

  /**
   * プロトコルを「http」とし、URLをフォーマットします。
   * 
   * @param hostName
   *          ホスト名
   * @param portNo
   *          ポート番号
   * @param fileName
   *          ファイルのパス
   * @return フォーマットされたURL文字列を返します。
   */
  public static String getNormalUrl(String hostName, int portNo, String fileName) {
    return getUrl(HTTP_PROTOCOL, hostName, portNo, fileName);
  }

  /**
   * プロトコルを「https」とし、URLをフォーマットします。
   * 
   * @param hostName
   *          ホスト名
   * @param portNo
   *          ポート番号
   * @return フォーマットされたURL文字列を返します。
   */
  public static String getSecureUrl(String hostName, int portNo) {
    return getUrl(HTTPS_PROTOCOL, hostName, portNo, "");
  }

  /**
   * プロトコルを「https」とし、URLをフォーマットします。
   * 
   * @param hostName
   *          ホスト名
   * @param portNo
   *          ポート番号
   * @param fileName
   *          ファイルのパス
   * @return フォーマットされたURL文字列を返します。
   */
  public static String getSecureUrl(String hostName, int portNo, String fileName) {
    return getUrl(HTTPS_PROTOCOL, hostName, portNo, fileName);
  }

  /**
   * パターンを" {0}://{1}{2} "とし、フォーマットをします。
   * 
   * @param protocol
   *          プロトコル
   * @param hostName
   *          ホスト名
   * @param portNo
   *          ポート番号
   * @param fileName
   *          ファイルのパス
   * @return フォーマットされたURL文字列を返します。
   */
  private static String getUrl(String protocol, String hostName, int portNo, String fileName) {
    String portSection = "";
    String fileSection = StringUtil.coalesce(fileName, "");
    if (fileSection.length() > 0 && !fileSection.startsWith("/")) {
      fileSection = "/" + fileSection;
    }
    if (protocol.equals(HTTPS_PROTOCOL) && portNo != HTTPS_DEFAULT_PORT) {
      portSection = ":" + portNo;
    } else if (protocol.equals(HTTP_PROTOCOL) && portNo != HTTP_DEFAULT_PORT) {
      portSection = ":" + portNo;
    }
    return MessageFormat.format("{0}://{1}{2}{3}", protocol, hostName, portSection, fileSection);
  }

  /**
   * maxlengthを元に、適切なwidthのsizeを取得します。
   * 
   * @param maxlength
   *          maxlength
   * @param mode
   *          fullまたはkana:全角<br>
   *          halfまたはpasswordまたはurl:半角
   * @return widthのsize
   */
  public static String getMaxlengthWidth(String maxlength, String mode) {
    String maxlengthWidth = "";
    double length = Integer.parseInt(maxlength);
    if (length < 3) {
      length = length * 1.4;
    } else if (length < 10) {
      length = length * 1.0;
    } else if (length < 20) {
      length = length * 1.0;
    } else if (length < 40) {
      length = length * 0.95;
    } else if (length < 50) {
      length = length * 0.95;
    } else {
      length = 50;
    }
    if (mode.equals("half") || mode.equals("password") || mode.equals("url")) {
      length = length / 2;
    }
    maxlengthWidth = Double.toString(length);

    return maxlengthWidth;
  }

  /**
   * 受取ったURLから、指定したコンテキストパスを除去した文字列を返します。
   * 
   * @param contextPath
   *          除去するコンテキストパス
   * @param uri
   *          コンテキストパスを除去するURL
   * @return コンテキストパスを除去したURL
   */
  public static String getRemovedContextUri(String contextPath, String uri) {
    String removedUri = "";

    if (contextPath != null && uri != null) {
      removedUri = uri.replaceFirst(contextPath, "");
    }

    return removedUri;
  }

  //10.1.3 10172 追加 ここから
  /**
   * 2つのIPアドレスが同一のサブネットに属しているかどうかを返します。 IPアドレスを表現するクラスにはjava.net.Inet4Addressまたは
   * java.net.Inet6Addressを使うことができます。
   * 
   * @param addr1
   *          比較するIPアドレス(1)
   * @param addr2
   *          比較するIPアドレス(2)
   * @param mask
   *          サブネットマスクのビット数
   * @return 2つの配列addr1, addr2が同一のサブネットに属していればtrueを返します。 IPV4とV6の比較はできません。addr1,
   * addr2のクラスが異なる場合はfalseを返します。
   */
  public static boolean isMatch(InetAddress addr1, InetAddress addr2, int mask) {
    return isMatch(addr1.getAddress(), addr2.getAddress(), mask);
  }

  /**
   * 2つのバイト配列を先頭から比較し、先頭から指定した長さだけ前方一致しているかどうかを調べます。
   * 
   * @param addr1
   *          比較するバイト列(1)
   * @param addr2
   *          比較するバイト列(2)
   * @param mask
   *          比較するバイト数
   * @return 2つの配列addr1, addr2のバイト列の0番目から(mask - 1)番目まですべて一致していればtrueを返します。
   *         add1, addr
   */
  public static boolean isMatch(byte[] addr1, byte[] addr2, int mask) {
    // IPV4/V6の違い
    if (addr1.length != addr2.length
        || (mask < 0 && mask > addr1.length * OCTET)) {
      return false;
    }
    boolean result = true;
    for (int i = 0; i < addr1.length && mask > 0; i++) {
      int shift = 0;
      if (mask / OCTET == 0) {
        shift = OCTET - (mask % OCTET);
      }
      result &= (addr1[i] >> shift) == (addr2[i] >> shift);
      mask -= OCTET;
    }
    return result;
  }
  //10.1.3 10172 追加 ここまで

  /**
   * インクルード静的コンテンツ存在するかどうか
   *
   * @param filePath
   *          静的コンテンツパス
   * @param fileName
   *          静的コンテンツファイル名
   * @return true：存在する；false：存在しない
   */
  public static boolean includeStaticExists(String filePath, String fileName) {
    return includeStaticExists(filePath, fileName, false);
  }

  public static boolean includeStaticExists(String filePath, String fileName, boolean bPage) {
    if (StringUtil.isNullOrEmpty(fileName)) {
      return false;
    }
    ContentsPath path = DIContainer.get("contentsPath");
    String contentsPath = path.getContentsSharedPath();
    StringBuilder builder = new StringBuilder();
    builder.append(contentsPath);

//    if (bPage) {
//      if (contentsPath.endsWith("/")) {
//        builder.append(contentsPath + "include/page");
//      } else {
//        builder.append(contentsPath + "/include/page");
//      }
//    } else {
//      if (contentsPath.endsWith("/")) {
//        builder.append(contentsPath + "include");
//      } else {
//        builder.append(contentsPath + "/include");
//      }
//    }

    if (StringUtil.hasValue(filePath)) {
      if (!filePath.startsWith("/")) {
        builder.append("/" + filePath.trim());
      }
    }
    fileName = fileName.replaceAll("/", "");
    if (!builder.toString().endsWith("/")) {
      builder.append("/");
    }
    builder.append(fileName.trim());
    File file = new File(builder.toString());
    return file.exists();
  }

  /**
   * インクルード静的コンテンツURLを取得する
   *
   * @param filePath
   *          静的コンテンツパス
   * @param fileName
   *          静的コンテンツファイル名
   * @return インクルード静的コンテンツURL
   */
  public static String getIncludeStaticUrl(String filePath, String fileName) {
    return getIncludeStaticUrl(filePath, fileName, false);
  }

  public static String getIncludeStaticUrl(String filePath, String fileName, boolean bPage) {
    return getIncludeStaticUrl(filePath, fileName, bPage, false);
  }

  public static String getIncludeStaticUrl(String filePath, String fileName, boolean bPage, boolean context) {
    if (StringUtil.isNullOrEmpty(fileName)) {
      return "";
    }
    ContentsPath path = DIContainer.get("contentsPath");
    String contentsPath = path.getContentsServerPath();
    if (context) {
      if (StringUtil.hasValue(path.getContentsContext())) {
        contentsPath = path.getContentsContext();
      } else {
        contentsPath = "contents";
      }
    }
    StringBuilder builder = new StringBuilder();
    builder.append(contentsPath);
    
//    if (bPage) {
//      if (contentsPath.endsWith("/")) {
//        builder.append(contentsPath + "include/page");
//      } else {
//        builder.append(contentsPath + "/include/page");
//      }
//    } else {
//      if (contentsPath.endsWith("/")) {
//        builder.append(contentsPath + "include");
//      } else {
//        builder.append(contentsPath + "/include");
//      }
//    }

    if (StringUtil.hasValue(filePath)) {
      if (!filePath.startsWith("/")) {
        builder.append("/" + filePath.trim());
      }
    }
    fileName = fileName.replaceAll("/", "");
    if (!builder.toString().endsWith("/")) {
      builder.append("/");
    }
    builder.append(fileName.trim());
    return builder.toString();
  }

}
