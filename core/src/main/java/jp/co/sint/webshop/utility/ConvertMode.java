package jp.co.sint.webshop.utility;

import jp.co.sint.webshop.text.Messages;

import org.apache.log4j.Logger;

public enum ConvertMode {
  /** Windows-31Jを表す値です。 */
  WINDOWS_31J("windows-31j", "windows_31j"),
  /** EUC-JPを表す値です。 */
  EUC_JP("euc-jp", "euc_jp"),
  /** UTF-8を表す値です。 */
  UTF_8("utf-8", "utf8"),
  /** その他を表す値です。 */
  EMPTY(new String[0]);

  private ConvertMode(String... aliases) {
    this.aliases = ArrayUtil.immutableCopy(aliases);
  }

  private String[] aliases;

  public static ConvertMode getInstance(String encode) {
    for (ConvertMode mode : ConvertMode.values()) {
      for (String alias : mode.aliases) {
        if (alias.equalsIgnoreCase(encode)) {
          return mode;
        }
      }
    }
    Logger logger = Logger.getLogger(ConvertMode.class);
    logger.error(Messages.log("utility.ConvertMode.6"));
    return EMPTY;
  }

  public String convertFromUnicode(String source) {
    StringBuilder builder = new StringBuilder();
    for (char ch : source.toCharArray()) {
      builder.append(convertFromUnicode(ch));
    }
    return builder.toString();
  }

  public String convertToUnicode(String source) {
    StringBuilder builder = new StringBuilder();
    for (char ch : source.toCharArray()) {
      builder.append(convertToUnicode(ch));
    }
    return builder.toString();
  }

  /** DBに登録されたUnicode文字から出力モードに添った文字に変換します。 */
  public char convertFromUnicode(char source) {
    char ch = source;
    switch (this) {
      case WINDOWS_31J:
      case UTF_8:
        ch = convertToPositiveDirection(ch);
        break;
      case EUC_JP:
        ch = convertToNagativeDirection(ch);
        break;
      default:
        break;
    }
    return ch;
  }

  /** 入力された文字をDBに登録可能な文字に変換します。 */
  public char convertToUnicode(char source) {
    return convertToPositiveDirection(source);
  }

  /** 文字を順方向(MS932の範囲へマッピング)に変換します。 */
  private char convertToPositiveDirection(char source) {
    char ch = source;
    switch (ch) {
      case 0x301c: // WAVE DASH
        ch = 0xff5e; // FULLWIDTH TILDE
        break;
      case 0x2016: // DOUBLE VERTICAL LINE
        ch = 0x2225; // PARALLEL TO
        break;
      case 0x2212: // MINUS SIGN
        ch = 0xff0d; // FULLWIDTH HYPHEN-MINUS
        break;
      case 0x00a2: // CENT SIGN
        ch = 0xffe0; // FULLWIDTH CENT SIGN
        break;
      case 0x00a3: // POUND SIGN
        ch = 0xffe1; // FULLWIDTH POUND SIGN
        break;
      case 0x00ac: // NOT SIGN
        ch = 0xffe2; // FULLWIDTH NOT SIGN
        break;
      case 0x2014: // HORIZONTAL BAR
        ch = 0x2015; // EM DASH
        break;
      default:
        break;
    }
    return ch;
  }

  /** 文字を逆方向(MS932の範囲外へマッピング)に変換します。 */
  private char convertToNagativeDirection(char source) {
    char ch = source;
    switch (ch) {
      case 0xff5e: // FULLWIDTH TILDE
        ch = 0x301c; // WAVE DASH
        break;
      case 0x2225: // PARALLEL TO
        ch = 0x2016; // DOUBLE VERTICAL LINE
        break;
      case 0xff0d: // FULLWIDTH HYPHEN-MINUS
        ch = 0x2212; // MINUS SIGN
        break;
      case 0xffe0: // FULLWIDTH CENT SIGN
        ch = 0x00a2; // CENT SIGN
        break;
      case 0xffe1: // FULLWIDTH POUND SIGN
        ch = 0x00a3; // POUND SIGN
        break;
      case 0xffe2: // FULLWIDTH NOT SIGN
        ch = 0x00ac; // NOT SIGN
        break;
      case 0x2015: // EM DASH
        ch = 0x2014; // HORIZONTAL BAR
        break;
      default:
        break;
    }
    return ch;
  }
}
