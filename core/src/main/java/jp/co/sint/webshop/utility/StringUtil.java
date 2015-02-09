package jp.co.sint.webshop.utility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.sint.webshop.configure.JdSendMailConfig;
import jp.co.sint.webshop.configure.TmallSendMailConfig;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.mail.MailSender;

/**
 * 文字列のユーティリティクラスです。 このクラスをインスタンス化することはできません。
 * 
 * @author System Integrator Corp.
 */
public final class StringUtil {

  /** 空文字を表す定数です。 */
  public static final String EMPTY = "";

  /** 全角シングルクォート(開始) */
  private static final char FULLWIDTH_SINGLE_QUOTE_START = (char) 0x2018;

  /** 全角シングルクォート(終了) */
  private static final char FULLWIDTH_SINGLE_QUOTE_END = (char) 0x2019;

  /** 全角ダブルクォート(開始) */
  private static final char FULLWIDTH_DOUBLE_QUOTE_START = (char) 0x201c;

  /** 全角ダブルクォート(終了) */
  private static final char FULLWIDTH_DOUBLE_QUOTE_END = (char) 0x201d;

  private static final String HALF_SPACE = " ";

  private static final String FULL_SPACE = "　";

  /** 半角かな配列 */
  private static final String[] HANKAKU = {
      "｡", "｢", "｣", "､", "･", "ｦ", "ｧ", "ｨ", "ｩ", "ｪ", "ｫ", "ｬ", "ｭ", "ｮ", "ｯ", "ｰ", "ｱ", "ｲ", "ｳ", "ｴ", "ｵ", "ｶ", "ｷ", "ｸ", "ｹ",
      "ｺ", "ｻ", "ｼ", "ｽ", "ｾ", "ｿ", "ﾀ", "ﾁ", "ﾂ", "ﾃ", "ﾄ", "ﾅ", "ﾆ", "ﾇ", "ﾈ", "ﾉ", "ﾊ", "ﾋ", "ﾌ", "ﾍ", "ﾎ", "ﾏ", "ﾐ", "ﾑ", "ﾒ",
      "ﾓ", "ﾔ", "ﾕ", "ﾖ", "ﾗ", "ﾘ", "ﾙ", "ﾚ", "ﾛ", "ﾜ", "ﾝ", "ｶﾞ", "ｷﾞ", "ｸﾞ", "ｹﾞ", "ｺﾞ", "ｻﾞ", "ｼﾞ", "ｽﾞ", "ｾﾞ", "ｿﾞ", "ﾀﾞ",
      "ﾁﾞ", "ﾂﾞ", "ﾃﾞ", "ﾄﾞ", "ﾊﾞ", "ﾋﾞ", "ﾌﾞ", "ﾍﾞ", "ﾎﾞ", "ﾊﾟ", "ﾋﾟ", "ﾌﾟ", "ﾍﾟ", "ﾎﾟ", "ｳﾞ", "ﾞ", "ﾟ"
  };

  private static final char[] HANKAKU_CHAR = {
      '｡', '｢', '｣', '､', '･', 'ｦ', 'ｧ', 'ｨ', 'ｩ', 'ｪ', 'ｫ', 'ｬ', 'ｭ', 'ｮ', 'ｯ', 'ｰ', 'ｱ', 'ｲ', 'ｳ', 'ｴ', 'ｵ', 'ｶ', 'ｷ', 'ｸ', 'ｹ',
      'ｺ', 'ｻ', 'ｼ', 'ｽ', 'ｾ', 'ｿ', 'ﾀ', 'ﾁ', 'ﾂ', 'ﾃ', 'ﾄ', 'ﾅ', 'ﾆ', 'ﾇ', 'ﾈ', 'ﾉ', 'ﾊ', 'ﾋ', 'ﾌ', 'ﾍ', 'ﾎ', 'ﾏ', 'ﾐ', 'ﾑ', 'ﾒ',
      'ﾓ', 'ﾔ', 'ﾕ', 'ﾖ', 'ﾗ', 'ﾘ', 'ﾙ', 'ﾚ', 'ﾛ', 'ﾜ', 'ﾝ', 'ﾞ', 'ﾟ'
  };

  /** 全角かな配列 */
  private static final char[] ZENKAKU = {
      '。', '「', '」', '、', '・', 'ヲ', 'ァ', 'ィ', 'ゥ', 'ェ', 'ォ', 'ャ', 'ュ', 'ョ', 'ッ', 'ー', 'ア', 'イ', 'ウ', 'エ', 'オ', 'カ', 'キ', 'ク', 'ケ',
      'コ', 'サ', 'シ', 'ス', 'セ', 'ソ', 'タ', 'チ', 'ツ', 'テ', 'ト', 'ナ', 'ニ', 'ヌ', 'ネ', 'ノ', 'ハ', 'ヒ', 'フ', 'ヘ', 'ホ', 'マ', 'ミ', 'ム', 'メ',
      'モ', 'ヤ', 'ユ', 'ヨ', 'ラ', 'リ', 'ル', 'レ', 'ロ', 'ワ', 'ン', 'ガ', 'ギ', 'グ', 'ゲ', 'ゴ', 'ザ', 'ジ', 'ズ', 'ゼ', 'ゾ', 'ダ', 'ヂ', 'ヅ', 'デ',
      'ド', 'バ', 'ビ', 'ブ', 'ベ', 'ボ', 'パ', 'ピ', 'プ', 'ペ', 'ポ', 'ヴ', '゛', '゜'
  };

  // 英文字母
  private static final String ENGCHAR = "AAAAAAAAAAAABBBCCCCCCDDDDDDDEEEEEEEEEEEFFGGGGGGGGHHHHIIIIIIIIIIIJJKKKLLLLMMNNNNNNOOOOOOOOOOOPPRRRRSSSSSSSTTTTTTTTtUUUUUUUUUUUUUWWWWXYYYYYYZZZZZZJaaaaaaaaaaaabbbccccccdddddddeeeeeeeeeeeffgggggggghhhhiiiiiiiiiiijjkkkllllmmnnnnnnooooooooooopprrrrssssssstttttttttuuuuuuuuuuuuuwwwwxyyyyyyzzzzzzj";

  // 拉丁衍生字母
  private static final String YSWCHAR = "ÀÁÂÃÄÅĀĂĄǞȦḀƁɃḂÇĆĈĊČƇḌĎĐƉƊḊḐÈÉÊËĒĔĖĘĚẸẼƑḞĜĞĠĢƓǤǦǴĤĦȞḪÌÍÎÏĨĪĬĮİƗỊĴǰĶƘǨĹĻĽŁḾṀÑŃŅŇƝǸÒÓÔÕÖŌŎŐƠǪỌƤṖŔŖŘⱤŚŜŞŠȘṠṢṬŢŤŦƬƮȚṪẗÙÚÛÜŨŪŬŮŰŲƯɄỤŴẀẂẄẊÝŶŸƳỲỸŹŻŽƵǮẐɈàáâãäåāăąǟȧḁɓƀḃçćĉċčƈḍďđɖɗḋḑèéêëēĕėęěẹẽƒḟĝğġģɠǥǧǵĥħȟḫìíîïĩīĭįİɨịĵǰķƙǩĺļľłḿṁñńņňɲǹòóôõöōŏőơǫọƥṗŕŗřɽśŝşšșṡṣṭţťŧƭʈțṫẗùúûüũūŭůűųưʉụŵẁẃẅẋýŷÿƴỳỹźżžƶǯẑɉ";

  /**
   * 新しいStringUtilを生成します。
   */
  private StringUtil() {
  }

  /**
   * 与えられた文字列リストを先頭から走査し、nullでない最初の要素を返します。
   * 
   * @param values
   *          文字列のリスト
   * @return valuesに与えられた各要素のうち、最初のnullでない要素。valuesが要素なし、
   *         またはvaluesそのものがnullのときはnullを返します。
   */
  public static String coalesce(String... values) {
    if (values != null && values.length > 0) {
      for (int i = 0; i < values.length; i++) {
        if (isNotNull(values[i])) {
          return values[i];
        }
      }
    }
    return null;
  }

  /**
   * 与えられた文字列リストを先頭から走査し、nullかつ空でない最初の要素を返します。
   * 
   * @param values
   *          文字列のリスト
   * @return valuesに与えられた各要素のうち、最初のnullかつ空でない要素。valuesが要素なし、
   *         またはvaluesそのものがnullのときはnullを返します。
   */
  public static String coalesceEmptyValue(String... values) {
    if (values != null && values.length > 0) {
      for (int i = 0; i < values.length; i++) {
        if (hasValue(values[i])) {
          return values[i];
        }
      }
    }
    return null;
  }

  /**
   * 文字列がnullかどうかを返します。
   * 
   * @param value
   *          対象文字列
   * @return 文字列がnullならtrueを返します。<br>
   *         null以外(空文字列含む)の場合はfalseを返します。
   */
  public static boolean isNull(String value) {
    return value == null;
  }

  /**
   * 文字列がnull以外どうかを返します。
   * 
   * @param value
   *          対象文字列
   * @return 文字列がnullでない(空文字列含む)ならtrueを返します。<br>
   *         nullの場合はfalseを返します。
   */
  public static boolean isNotNull(String value) {
    return value != null;
  }

  /**
   * 文字列がnullまたは空文字かどうかを返します。
   * 
   * @param value
   *          対象となる文字列
   * @return 文字列がnull、または文字列の長さが0の場合、trueを返します。<br>
   *         文字列の長さが1以上の場合、falseを返します。
   */
  public static boolean isNullOrEmpty(String value) {
    return value == null || value.length() == 0;
  }

  /**
   * 判斷obj是否為空
   * 
   * @param obj
   * @return
   */
  public static boolean isNullOrEmpty(Object obj) {
    return obj == null || "".equals(obj);
  }

  /**
   * 受け取った各文字列のいずれかにnullまたは空文字が存在すればtrue、それ以外ならfalseを返します。
   * 
   * @param strings
   *          空文字判定をするための文字列
   * @return 受け取った各文字列のいずれかにnullまたは空文字が存在する場合、trueを返します。<br>
   *         受け取った各文字列のいずれかにnullまたは空文字が存在しない場合、falseを返します。
   */
  public static boolean isNullOrEmptyAnyOf(String... strings) {
    for (String str : strings) {
      if (isNullOrEmpty(str)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判斷一組對象中是否有空值
   * 
   * @param objs
   * @return
   */
  public static boolean isNullOrEmptyAnyOf(Object... objs) {
    for (Object obj : objs) {
      if (isNullOrEmpty(obj)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 文字列が値(1以上の長さ)をもっているかどうかを返します。
   * 
   * @param str
   *          対象となる文字列
   * @return 文字列の長さが1以上である場合、trueを返します。<br>
   *         文字列がnull、または文字列の長さが0である場合、falseを返します。
   */
  public static boolean hasValue(String str) {
    return !isNullOrEmpty(str);
  }

  /**
   * 受け取った各文字列のすべてが値(1以上の長さ)をもっているかどうかを返します。
   * 
   * @param strings
   *          対象となる文字列
   * @return すべての文字列の長さが1以上である場合、trueを返します。<br>
   *         いずれかが文字列がnull、または文字列の長さが0である場合、falseを返します。
   */
  public static boolean hasValueAllOf(String... strings) {
    if (strings == null) {
      return false;
    }
    boolean result = true;
    for (String s : strings) {
      result &= hasValue(s);
    }
    return result;
  }

  /**
   * 受け取った各文字列のいずれかが値(1以上の長さ)をもっているかどうかを返します。
   * 
   * @param strings
   *          対象となる文字列
   * @return いずれかが文字列の長さが1以上である場合、trueを返します。<br>
   *         すべての文字列がnull、または文字列の長さが0である場合、falseを返します。
   */
  public static boolean hasValueAnyOf(String... strings) {
    if (strings == null) {
      return false;
    }
    boolean result = false;
    for (String s : strings) {
      result |= hasValue(s);
    }
    return result;
  }

  /**
   * 2つの文字列を比較し、fromの値がtoより小さい、あるいはtoと等しい場合にtrueを返します。
   * 
   * @param from
   *          比較元
   * @param to
   *          比較先
   * @return 2つの文字列を比較し、fromの値がtoより小さい、あるいはtoと等しい場合、trueを返します。<br>
   *         fromまたはtrueのいずれかが空(nullまたは長さゼロの文字列)である場合、常にfalseを返します。
   */
  public static boolean isCorrectRange(String from, String to) {
    if (isNullOrEmpty(from) || isNullOrEmpty(to)) {
      return false;
    }
    return from.compareTo(to) <= 0;
  }

  /**
   * 全角ひらがなを全角カタカナに変換します。
   * 
   * @param str
   *          対象となる文字列
   * @return 変換結果を返します。
   */
  public static String toKatakana(String str) {
    if (isNullOrEmpty(str)) {
      return str;
    }
    str = toFullWidth(str);
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c >= '\u3041' && c <= '\u3096') {
        c += (char) 0x60;
      }
      builder.append(c);
    }
    return builder.toString();
  }

  /**
   * 全角カタカナを全角ひらがなに変換します。
   * 
   * @param str
   *          対象となる文字列
   * @return 変換結果を返します。
   */
  public static String toHiragana(String str) {
    if (isNullOrEmpty(str)) {
      return str;
    }
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c >= '\u30A1' && c <= '\u30F6') {
        c -= (char) 0x60;
      }
      builder.append(c);
    }
    return builder.toString();
  }

  /**
   * 全角カタカナを半角カタカナに変換します。
   * 
   * @param str
   *          対象となる文字列
   * @return 変換結果を返します。
   */
  public static String toHankaku(String str) {
    if (isNullOrEmpty(str)) {
      return str;
    }
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      builder.append(forHalfWidth(c));
    }
    return builder.toString();
  }

  /**
   * forHalfWidth 全角カタカナ1文字を半角カタカナへ変換します。 変換できない場合は、変換前の文字を返します。
   * 
   * @param c
   *          変換前の文字
   * @return 変換後の文字を返します。
   */
  private static String forHalfWidth(char c) {
    String lStr = new String(ZENKAKU);
    if (lStr.indexOf(c) != -1) {
      return HANKAKU[lStr.indexOf(c)];
    } else {
      return String.valueOf(c);
    }
  }

  /**
   * 全角文字列を半角文字列に変換します。 このメソッドでは全角英数→半角英数、全角カタカナ→半角カタカナへの変換の両方が実行されます。
   * 
   * @param str
   *          対象となる文字列
   * @return 変換結果を返します。
   */
  public static String toHalfWidth(String str) {
    if (isNullOrEmpty(str)) {
      return str;
    } else {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < str.length(); i++) {
        char originalChar = str.charAt(i);
        if (originalChar == '”' || originalChar == '’' || originalChar == '‘') {
          sb.append(toHalfwidthDumbQuote(originalChar));
        } else if (originalChar >= '\uff01' && originalChar <= '\uff5e') {
          sb.append((char) (originalChar - '\ufee0'));
        } else {
          String convertedChar = forHalfWidth(originalChar);
          sb.append(convertedChar);
        }
      }
      return sb.toString();
    }
  }

  /**
   * 全角ダブルクォート、または全角シングルクォートを半角に変換します。
   * 
   * @param quoteChar
   *          対象となる文字列
   * @return quoteCharが'”'または、'”'の場合、'"'を返します。<br>
   *         quoteCharが'’'または、'’'の場合、'\''を返します。<br>
   *         上記以外の場合、値を変更せずそのまま返します。
   */
  private static char toHalfwidthDumbQuote(char quoteChar) {
    if (quoteChar == FULLWIDTH_DOUBLE_QUOTE_START || quoteChar == FULLWIDTH_DOUBLE_QUOTE_END) {
      return '"';
    } else if (quoteChar == FULLWIDTH_SINGLE_QUOTE_START || quoteChar == FULLWIDTH_SINGLE_QUOTE_END) {
      return '\'';
    } else {
      return quoteChar;
    }
  }

  /**
   * forFullWidth 半角カタカナ1文字を全角カタカナへ変換します。 変換できない場合は、変換前の文字を返します。
   * 
   * @param c
   *          対象となる文字
   * @return 変換結果を返します。
   */
  private static char forFullWidth(char c) {
    String lStr = new String(HANKAKU_CHAR);
    if (lStr.indexOf(c) != -1) {
      if (c == 'ﾞ') {
        return '゛';
      } else if (c == 'ﾟ') {
        return '゜';
      }
      return ZENKAKU[lStr.indexOf(c)];
    } else {
      return c;
    }
  }

  /**
   * 半角文字列を全角文字列に変換します。
   * 
   * @param str
   *          対象となる文字列
   * @return 変換結果を返します。
   */
  public static String toFullWidth(String str) {
    if (isNullOrEmpty(str)) {
      return str;
    } else if (str.length() == 1) {
      return forFullWidth(str.charAt(0)) + "";
    } else {
      StringBuilder lBui = new StringBuilder(str);
      int i = 0;
      for (i = 0; i < lBui.length() - 1; i++) {
        char originalChar1 = lBui.charAt(i);
        char originalChar2 = lBui.charAt(i + 1);
        char margedChar = mergeChar(originalChar1, originalChar2);
        if (margedChar != originalChar1) {
          lBui.setCharAt(i, margedChar);
          lBui.deleteCharAt(i + 1);
        } else {
          char convertedChar = forFullWidth(originalChar1);
          if (convertedChar != originalChar1) {
            lBui.setCharAt(i, convertedChar);
          }
        }
      }
      if (i < lBui.length()) {
        char originalChar1 = lBui.charAt(i);
        char convertedChar = forFullWidth(originalChar1);
        if (convertedChar != originalChar1) {
          lBui.setCharAt(i, convertedChar);
        }
      }
      return lBui.toString();
    }
  }

  /**
   * mergeChar 半角カナ⇒全角カナ 濁点・半濁点対応
   * 
   * @param c1
   *          対象となる文字
   * @param c2
   *          対象となる文字(濁点・半濁点)
   * @return 変換結果を返します。
   */
  public static char mergeChar(char c1, char c2) {
    if (c2 == 'ﾞ') {
      if ("ｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾊﾋﾌﾍﾎｳ".indexOf(c1) != -1) {
        switch (c1) {
          case 'ｶ':
            return 'ガ';
          case 'ｷ':
            return 'ギ';
          case 'ｸ':
            return 'グ';
          case 'ｹ':
            return 'ゲ';
          case 'ｺ':
            return 'ゴ';
          case 'ｻ':
            return 'ザ';
          case 'ｼ':
            return 'ジ';
          case 'ｽ':
            return 'ズ';
          case 'ｾ':
            return 'ゼ';
          case 'ｿ':
            return 'ゾ';
          case 'ﾀ':
            return 'ダ';
          case 'ﾁ':
            return 'ヂ';
          case 'ﾂ':
            return 'ヅ';
          case 'ﾃ':
            return 'デ';
          case 'ﾄ':
            return 'ド';
          case 'ﾊ':
            return 'バ';
          case 'ﾋ':
            return 'ビ';
          case 'ﾌ':
            return 'ブ';
          case 'ﾍ':
            return 'ベ';
          case 'ﾎ':
            return 'ボ';
          case 'ｳ':
            return 'ヴ';
          default:
            break;
        }
      }
    } else if (c2 == 'ﾟ') {
      if ("ﾊﾋﾌﾍﾎ".indexOf(c1) != -1) {
        switch (c1) {
          case 'ﾊ':
            return 'パ';
          case 'ﾋ':
            return 'ピ';
          case 'ﾌ':
            return 'プ';
          case 'ﾍ':
            return 'ペ';
          case 'ﾎ':
            return 'ポ';
          default:
            break;
        }
      }
    }
    return c1;
  }

  /**
   * 指定文字と文字列の連結結果を返します。
   * 
   * @param delim
   *          指定文字
   * @param args
   *          文字列
   * @return 連結結果を返します。<br>
   *         文字列の長さが0の場合、空文字を返します。
   */
  public static String joint(char delim, String... args) {
    if (args.length > 1) {
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < args.length; i++) {
        if (i > 0) {
          builder.append(delim);
        }
        builder.append(args[i]);
      }
      return builder.toString();
    } else if (args.length == 1) {
      return args[0];
    }
    return EMPTY;
  }

  /**
   * 文字列をPascal記法に変換して返します。
   * 
   * @param str
   *          対象となる文字列
   * @return Pascal記法に変換された文字列を返します。
   */
  public static String toPascalFormat(String str) {
    return toPascalOrCamel(str, false);
  }

  /**
   * 文字列をCamel記法に変換して返します。
   * 
   * @param str
   *          対象となる文字列
   * @return Camel記法に変換された文字列を返します。
   */
  public static String toCamelFormat(String str) {
    return toPascalOrCamel(str, true);
  }

  /**
   * Pascal記法あるいはCamel記法の文字列をアンダースコア区切りの文字列に変換します。
   * 
   * @param str
   *          対象となる文字列
   * @return 変換結果を返します。
   */
  public static String toUnderScoreDelimitedFormat(String str) {
    // StringBuilder builder = new StringBuilder();
    // for (int i = 0; i < str.length(); i++) {
    // char ch = str.charAt(i);
    //
    // if (Character.isUpperCase(ch) && builder.length() != 0) {
    // builder.append("_");
    // }
    // builder.append(Character.toUpperCase(ch));
    // }
    //
    // return builder.toString();
    if (StringUtil.isNullOrEmpty(str)) {
      return str;
    }

    StringBuffer buffer = new StringBuffer();
    Matcher m = Pattern.compile("(IO|[A-Z])").matcher(str);
    while (m.find()) {
      m.appendReplacement(buffer, "_" + m.group().toLowerCase(Locale.getDefault()));
    }
    m.appendTail(buffer);
    if (buffer.toString().startsWith("_")) {
      buffer.deleteCharAt(0);
    }
    return buffer.toString().toUpperCase(Locale.getDefault());
  }

  /**
   * 文字列をPascalまたは、Camel記法に変換して返します。
   * 
   * @param str
   *          対象となる文字列
   * @param isCamel
   *          true:Camel記法 false:Pascal記法
   * @return Pascalまたは、Camel記法に変換された文字列を返します。
   */
  private static String toPascalOrCamel(String str, boolean isCamel) {
    if (hasValue(str)) {
      String[] tokens = str.split("_");
      StringBuilder builder = new StringBuilder();
      for (String t : tokens) {
        if (isNullOrEmpty(t)) {
          continue;
        } else if (t.equalsIgnoreCase("io")) {
          builder.append(t.toUpperCase(Locale.getDefault()));
        } else if (isCamel) {
          builder.append(t.substring(0, 1).toLowerCase(Locale.getDefault()));
          builder.append(t.substring(1).toLowerCase(Locale.getDefault()));
        } else {
          builder.append(t.substring(0, 1).toUpperCase(Locale.getDefault()));
          builder.append(t.substring(1).toLowerCase(Locale.getDefault()));
        }
        isCamel = false;
      }
      return builder.toString();
    } else {
      return str;
    }
  }

  private static final String HALFWIDTH_SPACE = " ";

  private static final String FULLWIDTH_SPACE = "　";

  /**
   * 検索文字列を、半角スペース、全角スペースを区切りで分解して、List < String > にして返す。
   * 
   * @param searchWord
   *          分割前の検索文字列
   * @return 分割した検索文字列を返します。
   */
  public static List<String> getSearchWordStringList(String searchWord) {
    if (hasValue(searchWord)) {
      String str = searchWord.replace(FULLWIDTH_SPACE, HALFWIDTH_SPACE).trim();
      if (hasValue(str)) {
        return Arrays.asList(str.split(HALFWIDTH_SPACE));
      }
    }
    return Collections.emptyList();
  }

  // 20121128 add by yyq 将字符串之间的空格变成相邻一个空格
  public static String getWordAndWordOnlyOneSpace(String str) {

    String resStr = "";

    for (int i = 0; i < str.length() - 1; i++) {
      // 空格转成int型代表数字是32
      if ((int) str.charAt(i) == 32 && (int) str.charAt(i + 1) == 32) {
        continue;
      }
      resStr += str.charAt(i);
    }

    if ((int) str.charAt(str.length() - 1) != 32) {
      resStr += str.charAt(str.length() - 1);
    }
    return resStr;

  }

  /**
   * 検索文字列に有効桁数を指定して、検索文字列を、半角スペース、全角スペースを区切りで分解して、List < String > にして返します。
   * 検索文字列の長さが有効桁数を超えた場合、検索文字列にはsubstring(0, maxLength)によって切り捨てられた値が使われます。
   * （つまり、場合によっては検索ワードリストの最後の文字列は中途半端なものになることがあります）
   * 
   * @param searchWord
   *          分割前の検索文字列
   * @param maxLength
   *          検索文字列の有効桁数
   * @return 分割した検索文字列を返します。
   */
  public static List<String> getSearchWordStringList(String searchWord, int maxLength) {
    if (hasValue(searchWord) && searchWord.length() > maxLength) {
      searchWord = searchWord.substring(0, maxLength);
    }
    return getSearchWordStringList(searchWord);
  }

  /**
   * addZero 文字列をゼロ詰めにして返す。
   * 
   * @param no
   *          ゼロ詰めにするための文字列
   * @param cnt
   *          全体の桁数
   * @return ゼロ詰め結果を返します。
   */
  public static String addZero(String no, int cnt) {
    if (no == null) {
      return EMPTY;
    }

    String result = no;
    int setCnt = cnt - no.length();
    if (hasValue(no)) {
      for (int i = 0; i < setCnt; i++) {
        result = "0" + result;
      }
      return result;
    } else {
      return EMPTY;
    }
  }

  /**
   * 指定した文字列を、指定したエンコードの文字列に変換します。
   * 
   * @param value
   *          対象となる文字列
   * @param srcEncoding
   *          変換されたエンコード
   * @param destEncoding
   *          変換後のエンコード
   * @return 変換結果を返します。<br>
   *         valueがnullの場合、そのままvalueを返します。<br>
   *         Stringの生成に失敗した場合、空文字を返します。
   */
  public static String convertEncoding(String value, String srcEncoding, String destEncoding) {
    String newString = EMPTY;
    if (hasValue(value)) {
      try {
        newString = new String(value.getBytes(srcEncoding), destEncoding);
      } catch (UnsupportedEncodingException ueEx) {
        newString = EMPTY;
      }
    } else {
      newString = value;
    }
    return newString;

  }

  /**
   * 文字列の先頭がアルファベットの場合、先頭の文字を大文字に変換します。 2文字目以降は小文字に変換されます。
   * 
   * @param value
   *          入力値
   * @return 変換結果を返します。<br>
   *         valueがnullまたは空文字の場合は、入力値をそのまま返します。
   */
  public static String capitalize(String value) {
    if (hasValue(value)) {
      char[] chars = value.toLowerCase(Locale.getDefault()).toCharArray();

      chars[0] = Character.toUpperCase(chars[0]);
      return new String(chars);
    }
    return value;
  }

  /**
   * デフォルトの単語パターン([A-Za-z0-9])で文字列を走査し、マッチした各単語の
   * 先頭を大文字化します。各単語の2文字目以降は小文字に変換されます。
   * 
   * @param input
   *          入力値
   * @return 変換結果を返します。<br>
   *         valueがnullまたは空文字の場合は、入力値をそのまま返します。
   */
  public static String initCaps(String input) {
    return initCaps(input, "[A-Za-z0-9]+");
  }

  /**
   * 単語パターンを指定して文字列を走査し、マッチした各単語の先頭を大文字化します。 各単語の2文字目以降は小文字に変換されます。
   * 
   * @param input
   *          入力値
   * @param pattern
   *          検索パターン
   * @return 変換結果を返します。<br>
   *         valueがnullまたは空文字の場合は、入力値をそのまま返します。
   */
  public static String initCaps(String input, String pattern) {
    if (hasValue(input)) {
      Matcher m = Pattern.compile(pattern).matcher(input);
      StringBuffer sb = new StringBuffer();
      while (m.find()) {
        String x = m.group();
        m.appendReplacement(sb, capitalize(x));
      }
      m.appendTail(sb);
      return sb.toString();
    }
    return input;
  }

  /**
   * 指定した長さの部分文字列を返します。
   * 
   * @param str
   *          対象文字列
   * @param size
   *          取得する文字数
   * @return 指定した長さの部分文字列に「...」を付与したものを返します。<br>
   *         文字列が指定した長さに満たない場合は引数の値をそのまま返します。
   */
  public static String getHeadline(String str, int size) {
    return getHeadline(str, size, "...");
  }

  /**
   * 指定した長さの部分文字列を返します。
   * 
   * @param str
   *          対象文字列
   * @param size
   *          取得する文字数
   * @param tail
   *          末尾に付与する文字列
   * @return 指定した長さの部分文字列に、引数tailで指定した文字列を付与したものを返します。<br>
   *         文字列が指定した長さに満たない場合は引数の値をそのまま返します。
   */
  public static String getHeadline(String str, int size, String tail) {
    if (hasValue(str) && str.length() > size) {
      return str.substring(0, size) + tail;
    } else {
      return str;
    }
  }

  /**
   * Windows-31JでマッピングしているUnicode文字をメールで利用できる文字に置き換えます。
   * 
   * @param str
   *          入力文字列
   * @return 変換結果を返します。
   */
  public static String convertForEmailString(String str) {
    if (hasValue(str)) {
      StringBuilder builder = new StringBuilder();
      for (int cc : str.toCharArray()) {
        switch (cc) {
          case 0xff5e: // FULLWIDTH TILDE
            cc = 0x301c; // WAVE DASH
            break;
          case 0x2225: // PARALLEL TO
            cc = 0x2016; // DOUBLE VERTICAL LINE
            break;
          case 0xff0d: // FULLWIDTH HYPHEN-MINUS
            cc = 0x2212; // MINUS SIGN
            break;
          case 0xffe0: // FULLWIDTH CENT SIGN
            cc = 0x00a2; // CENT SIGN
            break;
          case 0xffe1: // FULLWIDTH POUND SIGN
            cc = 0x00a3; // POUND SIGN
            break;
          case 0xffe2: // FULLWIDTH NOT SIGN
            cc = 0x00ac; // NOT SIGN
            break;
          case 0x2015: // EM DASH
            cc = 0x2014; // HORIZONTAL BAR
            break;
          default:
            break;
        }
        builder.append((char) cc);
      }
      return builder.toString();
    }
    return str;
  }

  /**
   * 受け取った文字列をデータ検索用文字列に変換します。
   * 変換内容は(全角ひらがな→全角カタカナ,半角カタカナ→全角カタカナ,全角英数→半角英数,英小文字→英大文字)です。
   * 
   * @param value
   *          入力文字列
   */
  public static String toSearchKeywords(String value) {
    String result = value;
    if (hasValue(value)) {
      result = toHalfWidth(result);
      result = toKatakana(result);
      result = result.toUpperCase(Locale.getDefault());
    }
    return result;
  }

  /**
   * 文字列のリストを1つずつ改行して連結したものを返します。
   * 
   * @param values
   *          文字列のリスト
   * @return 連結結果。受け取った値がnullの場合は空の文字列を返します。
   */
  public static String toMultiLine(List<String> values) {
    if (values == null) {
      return EMPTY;
    }
    StringWriter sw = new StringWriter();
    PrintWriter out = new ExPrintWriter(sw);
    for (String s : values) {
      out.println(s);
    }
    return sw.toString();
  }

  /**
   * 受け取った値を指定された回数連結した文字列を返します。
   * 
   * @param value
   *          対象文字列
   * @param times
   *          繰り返し回数
   * @return 連結結果。繰り返し回数が1未満の場合は、受け取った値をそのまま返します。
   */
  public static String times(String value, int times) {
    StringBuilder builder = new StringBuilder();
    if (times < 1) {
      return value;
    }
    while (--times > 0) {
      builder.append(value);
    }
    return builder.toString();
  }

  /** 全銀ファイルフォーマット対応文字に変換するための正規表現 */
  private static final String REGEX_PATTERN_BANK_KANA = "[0-9ｦ-ｯｱ-ﾟA-Z\\￥｢｣,.()ｰ‐―/　]*";

  /**
   * 受け取った文字列を、全銀ファイルフォーマットに対応した形式に変換して返します。<br>
   * 受け取る文字列は、全銀ファイルフォーマットに対応している文字の、全角文字を想定します。
   * 
   * @param str
   *          変換対象文字列
   * @return 全銀ファイルフォーマットに対応した文字列を返します。<br>
   *         全銀ファイルフォーマット非対応の文字が含まれていた場合、空文字に置換して返します。
   */
  public static String toBankKana(String str) {
    return toBankKana(str, EMPTY);
  }

  /**
   * 受け取った文字列を、全銀ファイルフォーマットに対応した形式に変換して返します。<br>
   * 受け取る文字列は、全銀ファイルフォーマットに対応している文字の、全角文字を想定します。
   * 
   * @param str
   *          変換対象文字列
   * @param replacement
   *          全銀ファイルフォーマット非対応文字を置換する文字列
   * @return 全銀ファイルフォーマットに対応した文字列を返します。<br>
   *         全銀ファイルフォーマット非対応の文字が含まれていた場合、replacementで指定した文字列に置換して返します。
   */
  public static String toBankKana(String str, String replacement) {
    if (isNullOrEmpty(str)) {
      return str;
    }

    StringBuilder builder = new StringBuilder();
    char[] charStr = toHalfWidth(str).toCharArray();
    for (char c : charStr) {
      builder.append(toBankKana(c, replacement));
    }
    return builder.toString();
  }

  /**
   * 受け取った文字を、全銀ファイルフォーマットに対応した形式に変換して返します。<br>
   * 受け取った文字が全銀ファイルフォーマットに非対応である場合、別に指定された置換文字列に変換して返します。
   * 
   * @param halfWidthChar
   *          変換対象文字
   * @param replacement
   *          全品ファイルフォーマット非対応文字を置換する文字列
   * @return 全銀ファイルフォーマットに対応した文字を返します。<br>
   *         全銀ファイルフォーマット非対応の文字が含まれていた場合、replacementで指定した文字列に置換して返します。
   */
  private static String toBankKana(char halfWidthChar, String replacement) {
    String result = "";
    if (Pattern.matches(REGEX_PATTERN_BANK_KANA, Character.toString(halfWidthChar))) {
      char replaceChar;
      switch (halfWidthChar) {
        case 'ｧ':
          replaceChar = 'ｱ';
          break;
        case 'ｨ':
          replaceChar = 'ｲ';
          break;
        case 'ｩ':
          replaceChar = 'ｳ';
          break;
        case 'ｪ':
          replaceChar = 'ｴ';
          break;
        case 'ｫ':
          replaceChar = 'ｵ';
          break;
        case 'ｬ':
          replaceChar = 'ﾔ';
          break;
        case 'ｭ':
          replaceChar = 'ﾕ';
          break;
        case 'ｮ':
          replaceChar = 'ﾖ';
          break;
        case 'ｯ':
          replaceChar = 'ﾂ';
          break;
        case '￥':
          replaceChar = '\\';
          break;
        case 'ｰ':
        case '‐':
        case '―':
          replaceChar = '-';
          break;
        case '　':
          replaceChar = ' ';
          break;
        default:
          replaceChar = halfWidthChar;
      }
      result = Character.toString(replaceChar);
    } else {
      result = replacement;
    }
    return result.toUpperCase(Locale.getDefault());
  }

  // soukai add ob 2011/12/20 start
  /**
   * 受け取った文字を、指定サイズ形式に変換して返します。<br>
   * 
   * @param strValue
   *          変換対象文字
   * @param length
   *          文字のサイズ
   * @param formatFlg
   *          　　　　 true(文字右にフォーマット文字追加)　false(文字左にフォーマット文字追加)
   * @param formtaValue
   *          　フォーマット文字
   * @return フォーマット後の文字<br>
   */

  public static String toFormatString(String strValue, int strLength, boolean formatFlg, String formtaValue) {

    int i = 0;

    int intSize = 0;

    String newStrvalue = "";

    // 文字コード
    String charSet = DIContainer.getWebshopConfig().getCsvCharset();

    // 受け取った文字<>null && サイズ>指定したサイズの場合
    try {
      if (strValue != null && strValue.getBytes(charSet).length > strLength) {

        for (i = 0; i < strValue.toCharArray().length; i++) {

          // 全半角混雑の場合
          if ((newStrvalue + strValue.toCharArray()[i]).toString().getBytes(charSet).length > strLength) {

            newStrvalue = newStrvalue + " ";

          } else {

            newStrvalue = newStrvalue + strValue.toCharArray()[i];

          }

          if (newStrvalue.getBytes(charSet).length == strLength) {

            break;
          }
        }

        strValue = newStrvalue;

        // 受け取った文字<>null && サイズ＝指定したサイズの場合
      } else if (strValue != null && strValue.getBytes(charSet).length == strLength) {

        return strValue;

        // 受け取った文字<>null && サイズ＜指定したサイズの場合
      } else if (strValue != null && strValue.getBytes(charSet).length < strLength) {

        intSize = strValue.getBytes(charSet).length;

        for (i = 0; i < strLength - intSize; i++) {

          // 文字右にフォーマット文字追加
          if (formatFlg == true) {
            strValue = strValue + formtaValue;

            // 文字左にフォーマット文字追加
          } else {

            strValue = formtaValue + strValue;
          }
        }

        // 受け取った文字==nullの場合
      } else {

        for (i = 0; i < strLength; i++) {

          // 文字右にフォーマット文字追加
          if (formatFlg == true) {

            newStrvalue = newStrvalue + formtaValue;

            // 文字左にフォーマット文字追加
          } else {

            newStrvalue = formtaValue + newStrvalue;
          }
        }

        strValue = newStrvalue;
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return strValue;
  }

  // soukai add ob 2011/12/20 end

  // 2012-02-07 yyq add start desc:字符串按字节截取
  public static boolean isChineseChar(char c) {
    // 如果字节数大于1，是汉字
    try {
      return String.valueOf(c).getBytes("GBK").length > 1;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static String subStringByByte(String orignal, int count) {
    // 原始字符不为null，也不是空字符串
    if (null != orignal && !"".equals(orignal)) {
      // 将原始字符串转换为GBK编码格式
      try {
        orignal = new String(orignal.getBytes("GBK"), "GBK");
        if (count > 0 && count < orignal.getBytes("GBK").length) {
          StringBuffer buff = new StringBuffer();
          char c;
          for (int i = 0; i < count; i++) {
            // charAt(int index)也是按照字符来分解字符串的
            c = orignal.charAt(i);

            if (isChineseChar(c) && i + 1 == count) {
            } else {
              buff.append(c);
            }
            if (isChineseChar(c)) {
              // 遇到中文汉字，截取字节总数减2
              count -= 1; // 一般汉字在utf-8中为3个字节长度
            }
          }
          return buff.toString();
        }
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      // 要截取的字节数大于0，且小于原始字符串的字节数
    }
    return orignal;
  }

  // 2012-02-07 yyq add end desc:字符串按字节截取
  // 2012-02-23 电话号码截取 add start
  // 电话号码截取
  public static String formatPhone(String phone) {

    int lineNum = 0;
    int siteNum = 0;

    if (phone != null && !phone.equals("")) {
      // 判断有几个"-"
      char ch[] = phone.toCharArray();
      for (int i = 0; i < ch.length; i++) {
        if (ch[i] == '-') {
          lineNum++;
        }
      }
      // 有1个"-"时
      if (lineNum == 1) {
        // 判断长度不大于16位时
        if (phone.length() <= 16) {
          return phone;
        } else {
          // 大于16位时取得"-"
          return phone.replace("-", "");
        }
        // 有2个"-"时
      } else if (lineNum == 2) {
        siteNum = phone.lastIndexOf("-");
        // 获取带一个"-"的电话号码
        phone = phone.substring(0, siteNum);
        // 判断长度不大于16位时
        if (phone.length() <= 16) {
          return phone;
        }
        // 大于16位时取得"-"
        return phone.replace("-", "");
      }
    }
    return phone;
  }

  // 截取分机号
  public static String formatPhone_Ext(String phone) {
    int siteNum = 0;
    int lineNum = 0;
    // 判断有几个"-"
    char ch[] = phone.toCharArray();
    for (int i = 0; i < ch.length; i++) {
      if (ch[i] == '-') {
        lineNum++;
      }
    }
    // 有2个"-"时
    if (lineNum == 2) {
      siteNum = phone.lastIndexOf("-");
      // 获取分机号
      phone = phone.substring(siteNum + 1, phone.length());
      // 分机号只能为4位
      if (phone.length() <= 6) {
        return phone;
      } else {
        return phone.substring(0, 6);
      }
    } else {
      return "";
    }
  }

  // 发票地址截取
  public static String formatComments(String comments) {
    // 空时返回
    if (comments == null || comments.equals("")) {
      return ";;;;;;;;;;;;";
    }
    String addr1 = "";
    String addr2 = "";
    String addr3 = "";
    String addr2Str2 = "";
    // 截取";"
    String comment[] = comments.split(";");

    if (comment.length > 8) {
      String address = comment[4];
      if (!address.equals("")) {
        addr1 = subStringByByte(address, 70);// 地址1
        addr2Str2 = address.substring(addr1.length());
        if (!addr2Str2.equals("")) {
          addr2 = subStringByByte(addr2Str2, 70);// 地址2
          addr3 = addr2Str2.substring(addr2.length());// 地址3
        }
      }
      // 重新拼接
      comments = comment[0] + ";" + comment[1] + ";" + comment[2] + ";" + comment[3] + ";" + addr1 + ";" + addr2 + ";" + addr3
          + ";" + comment[5] + ";" + comment[6] + ";" + comment[7] + ";" + comment[8] + ";" + comment[9] + ";";
    } else {
      comments = comments + ";;";
    }
    return comments;
  }

  // CONTENT长度截取
  public static String formatComment(String comment) {

    String comment1 = "";
    String comment2 = "";
    String commentStr[] = comment.split(";");
    if (!StringUtil.isNullOrEmpty(commentStr[0])) {
      comment1 = subStringByByte(commentStr[0], 76);
    }
    if (!StringUtil.isNullOrEmpty(commentStr[1])) {
      comment2 = subStringByByte(commentStr[1], 76);
    }

    return comment1 + ";" + comment2;
  }

  // CONTENT长度截取new
  public static String formatCommentNew(String comment) {

    String comment1 = "";
    String comment2 = "";

    if (!StringUtil.isNullOrEmpty(comment)) {
      comment1 = subStringByByte(comment, 76);
      comment2 = comment.substring(comment1.length());
    }
    if (!StringUtil.isNullOrEmpty(comment2)) {
      comment2 = subStringByByte(comment2, 76);
    }

    return comment1 + ";" + comment2;
  }

  public static String getLocalEnMonth(int month) {
    String monthStr = "Jan.";
    switch (month) {
      case 1:
        monthStr = "Jan.";
        break;
      case 2:
        monthStr = "Feb.";
        break;
      case 3:
        monthStr = "Mar.";
        break;
      case 4:
        monthStr = "Apr.";
        break;
      case 5:
        monthStr = "May.";
        break;
      case 6:
        monthStr = "June.";
        break;
      case 7:
        monthStr = "July.";
        break;
      case 8:
        monthStr = "Aug.";
        break;
      case 9:
        monthStr = "Sept.";
        break;
      case 10:
        monthStr = "Oct.";
        break;
      case 11:
        monthStr = "Nov.";
        break;
      case 12:
        monthStr = "Dec.";
        break;
      default:
        break;
    }
    return monthStr;
  }

  // 2012-02-23 电话号码截取 add end
  /**
   * add by os014 2012-03-06 特殊字符转换： （单引号变换为 ‘）、（双引号变换为　“）、（百分号变换为　％）、（ 逗号变换为
   * 　，）、（分号变换为　；）
   */
  public static String parse(String resouce) {
    if (resouce == null) {
      return null;
    }
    String result = "";
    // 百分号变换为　％
    result = resouce.replaceAll("%", "％");
    // 逗号变换为 　，
    result = result.replaceAll(",", "，");
    // 双引号变换为　“
    result = result.replaceAll("\"", "“");
    // 单引号变换为 ‘
    result = result.replaceAll("'", "‘");
    // 分号变换为 ；
    result = result.replaceAll(";", "；");
    // // 竖线变换为 ｜
    result = result.replaceAll("\\|", "｜");
    // // and变换为 ＆
    result = result.replaceAll("&", "＆");
    // // ・变换为空格
    result = result.replaceAll("・", " ");
    // 拉丁衍生文替换成英文字母
    result = StringUtil.convertToSimplized(result);
    
    return StringUtil.toFullWidth(result);
  }

  // add by wjh start 2012-03-08
  public static int getLength(String strValue) {
    if (!hasValue(strValue)) {
      return 0;
    }
    int intLength = 0;
    for (int i = 0; i < strValue.length(); i++) {
      int intCode = strValue.charAt(i);
      if (intCode < 256 || (intCode >= 65382 && intCode <= 65439)) {
        intLength += 1;
      } else {
        intLength += 2;
      }
    }
    return intLength;
  }

  // add by wjh end 2012-03-08
  // add by OS011淘宝订单下载失败 start 2012-04-12
  public static void sendTmallErrMail(String tmallOrderNo, String msg) {
    // Logger logger = Logger.getLogger("StringUtil");
    MailSender sender = DIContainer.get("MailSender");
    MailInfo info = new MailInfo();
    info.setSubject("【品店】淘宝订单下载失败邮件");
    info.setSendDate(DateUtil.getSysdate());
    String mailText = "";
    mailText = "下载失败时间：" + DateUtil.getSysdate();
    if (!tmallOrderNo.equals("")) {
      mailText += "<br><br>订单交易号：" + tmallOrderNo;
    }
    if (!msg.equals("")) {
      mailText += "<br><br>错误信息：" + msg;
    }
    info.setText("以下为淘宝订单下载失败信息<br><br>" + mailText + "<br><br>");
    TmallSendMailConfig tmllMailSend = DIContainer.get(TmallSendMailConfig.class.getSimpleName());

    info.setFromInfo(tmllMailSend.getMailFromAddr(), tmllMailSend.getMailFromName());
    String[] mailToAddrArray = tmllMailSend.getMailToAddr().split(";");
    String[] mailToNameArray = tmllMailSend.getMailToName().split(";");
    for (int i = 0; i < mailToAddrArray.length; i++) {
      if (i >= mailToNameArray.length) {
        info.addToList(mailToAddrArray[i], mailToAddrArray[i]);
      } else {
        info.addToList(mailToAddrArray[i], mailToNameArray[i]);
      }
    }
    info.setContentType(MailInfo.CONTENT_TYPE_HTML);

    for (int i = 0; i < Integer.parseInt(tmllMailSend.getRetryTime()); i++) {
      if (Integer.parseInt(tmllMailSend.getRetryTime()) - i == 1) {
//        tmllMailSend.setMailFromAddr("");
//        tmllMailSend.setMailFromName("");
//        tmllMailSend.setMailToAddr("");
//        tmllMailSend.setMailToName("");
      }
      try {
        sender.sendMail(info);
        // logger.debug("淘宝订单下载失败邮件发送成功。");
//        tmllMailSend.setMailFromAddr("");
//        tmllMailSend.setMailFromName("");
//        tmllMailSend.setMailToAddr("");
//        tmllMailSend.setMailToName("");
        break;
      } catch (Exception e) {
        // logger.debug("淘宝订单下载失败邮件发送失败。");
        e.printStackTrace();
      }
    }
  }

  // add by OS011淘宝订单下载失败 end 2012-04-12

  // add by yyq 订单下载成功发送邮件 start
  public static void sendTmallSuccMail(String startTime, String endTime) {
    MailSender sender = DIContainer.get("MailSender");
    MailInfo info = new MailInfo();
    info.setSubject("【品店】淘宝订单下载成功");
    info.setSendDate(DateUtil.getSysdate());
    String mailText = "以下时间段订单下载成功：<br>" + startTime + "~" + endTime;
    info.setText(mailText);
    TmallSendMailConfig tmllMailSend = DIContainer.get(TmallSendMailConfig.class.getSimpleName());

    info.setFromInfo(tmllMailSend.getMailFromAddr(), tmllMailSend.getMailFromName());
    String[] mailToAddrArray = tmllMailSend.getMailToAddr().split(";");
    String[] mailToNameArray = tmllMailSend.getMailToName().split(";");
    for (int i = 0; i < mailToAddrArray.length; i++) {
      if (i >= mailToNameArray.length) {
        info.addToList(mailToAddrArray[i], mailToAddrArray[i]);
      } else {
        info.addToList(mailToAddrArray[i], mailToNameArray[i]);
      }
    }
    info.setContentType(MailInfo.CONTENT_TYPE_HTML);

    for (int i = 0; i < Integer.parseInt(tmllMailSend.getRetryTime()); i++) {
      if (Integer.parseInt(tmllMailSend.getRetryTime()) - i == 1) {
//        tmllMailSend.setMailFromAddr("");
//        tmllMailSend.setMailFromName("");
//        tmllMailSend.setMailToAddr("");
//        tmllMailSend.setMailToName("");
      }
      try {
        sender.sendMail(info);
//        tmllMailSend.setMailFromAddr("");
//        tmllMailSend.setMailFromName("");
//        tmllMailSend.setMailToAddr("");
//        tmllMailSend.setMailToName("");
        break;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // add by yyq 订单下载成功发送邮件 end
  
  
  // 20140513 hdh add  京东订单下载发送邮件  start  
  public static void sendJdErrMail(String jdOrderNo, String msg) {
    MailSender sender = DIContainer.get("MailSender");
    MailInfo info = new MailInfo();
    info.setSubject("【品店】京东订单下载失败邮件");
    info.setSendDate(DateUtil.getSysdate());
    String mailText = "";
    mailText = "下载失败时间：" + DateUtil.getSysdate();
    if (StringUtil.hasValue(jdOrderNo)) {
      mailText += "<br><br>订单号：" + jdOrderNo;
      mailText += "<br><br>错误信息：" + msg;
    }else{
      mailText += "<br><br>错误信息：" + msg;
    }
    info.setText("以下为京东单下载失败信息<br><br>" + mailText + "<br><br>");
    JdSendMailConfig mailSend = DIContainer.get(JdSendMailConfig.class.getSimpleName());

    info.setFromInfo(mailSend.getMailFromAddr(), mailSend.getMailFromName());
    String[] mailToAddrArray = mailSend.getMailToAddr().split(";");
    String[] mailToNameArray = mailSend.getMailToName().split(";");
    for (int i = 0; i < mailToAddrArray.length; i++) {
      if (i >= mailToNameArray.length) {
        info.addToList(mailToAddrArray[i], mailToAddrArray[i]);
      } else {
        info.addToList(mailToAddrArray[i], mailToNameArray[i]);
      }
    }
    info.setContentType(MailInfo.CONTENT_TYPE_HTML);

    for (int i = 0; i < Integer.parseInt(mailSend.getRetryTime()); i++) {
      if (Integer.parseInt(mailSend.getRetryTime()) - i == 1) {
//        mailSend.setMailFromAddr("");
//        mailSend.setMailFromName("");
//        mailSend.setMailToAddr("");
//        mailSend.setMailToName("");
      }
      try {
        sender.sendMail(info);
//        mailSend.setMailFromAddr("");
//        mailSend.setMailFromName("");
//        mailSend.setMailToAddr("");
//        mailSend.setMailToName("");
        break;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void sendJdSuccMail(String startTime, String endTime) {
    MailSender sender = DIContainer.get("MailSender");
    MailInfo info = new MailInfo();
    info.setSubject("【品店】京东订单下载成功");
    info.setSendDate(DateUtil.getSysdate());
    String mailText = "以下时间段订单下载成功：<br>" + startTime + "~" + endTime;
    info.setText(mailText);
    JdSendMailConfig mailSend = DIContainer.get(JdSendMailConfig.class.getSimpleName());

    info.setFromInfo(mailSend.getMailFromAddr(), mailSend.getMailFromName());
    String[] mailToAddrArray = mailSend.getMailToAddr().split(";");
    String[] mailToNameArray = mailSend.getMailToName().split(";");
    for (int i = 0; i < mailToAddrArray.length; i++) {
      if (i >= mailToNameArray.length) {
        info.addToList(mailToAddrArray[i], mailToAddrArray[i]);
      } else {
        info.addToList(mailToAddrArray[i], mailToNameArray[i]);
      }
    }
    info.setContentType(MailInfo.CONTENT_TYPE_HTML);

    for (int i = 0; i < Integer.parseInt(mailSend.getRetryTime()); i++) {
      if (Integer.parseInt(mailSend.getRetryTime()) - i == 1) {
//        mailSend.setMailFromAddr("");
//        mailSend.setMailFromName("");
//        mailSend.setMailToAddr("");
//        mailSend.setMailToName("");
      }
      try {
        sender.sendMail(info);
//        mailSend.setMailFromAddr("");
//        mailSend.setMailFromName("");
//        mailSend.setMailToAddr("");
//        mailSend.setMailToName("");
        break;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  // 20140513 hdh add 京东订单下载发送邮件 end
  

  public static String replaceCRLF(String value) {
    // WebShopping内で使用している改行コードを、SuperCsvの改行コードに変換する。
    if (StringUtil.hasValue(value) && (value.contains("\r\n") || value.contains("\n"))) {
      String retString = value.replace("\r\n", "");
      retString = retString.replace("\n", "");
      return retString;
    }
    return value;
  }

  // add by yyq Start 去掉半角或者全角空格或者其他空白字符
  public static String replaceEmpty(String value) {
    if (isNullOrEmpty(value)) {
      return "";
    } else {
      value = value.replaceAll("\\s*", "");
      value = value.replace("　", "");
      return value;
    }
  }

  // add by yyq end 去掉半角或者全角空格或者其他空白字符

  // 2013/4/7 优惠券对应 ob add start
  /**
   * 获得一个随即码
   * 
   * @param codeLen
   *          随机码长度
   * @return
   */
  public static String getRandom(int codeLen) {
    Random random = new Random();

    // 构建随机码的字符串
    String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < codeLen; i++) {
      int number = random.nextInt(base.length());
      sb.append(base.charAt(number));
    }
    return sb.toString();
  }

  // 2013/4/7 优惠券对应 ob add end

  public static String replaceKeyword(String str) {

    if (!StringUtil.isNullOrEmpty(str)) {
      str = str.replace("\t", " ");
      str = str.replace("\r\n", " ");
      str = str.replace("\n", " ");
      str = str.replace(FULL_SPACE, HALF_SPACE);
      str = StringUtil.getWordAndWordOnlyOneSpace(str);
      str = str.trim();
      return str;
    }
    return "";
  }

  // add by yyq start 20130822 拉丁衍生文替换成英文字母
  public static String convertToSimplized(String cc) {
    String str = "";
    for (int i = 0; i < cc.length(); i++) {
      if (YSWCHAR.indexOf(cc.charAt(i)) != -1)
        str += ENGCHAR.charAt(YSWCHAR.indexOf(cc.charAt(i)));
      else {
        str += cc.charAt(i);
      }
    }
    return str;
  }
  // add by yyq end 20130822 拉丁衍生文替换成英文字母
  
  public static String signByMd5(String sourceStr) {
    String result = "";
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(sourceStr.getBytes());
      byte b[] = md.digest();
      int i;
      StringBuffer buf = new StringBuffer("");
      for (int offset = 0; offset < b.length; offset++) {
        i = b[offset];
        if (i < 0)
          i += 256;
        if (i < 16)
          buf.append("0");
        buf.append(Integer.toHexString(i));
      }
      result = buf.toString();
      // System.out.println("MD5(" + sourceStr + ",32) = " + result);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return result;
  }
}
