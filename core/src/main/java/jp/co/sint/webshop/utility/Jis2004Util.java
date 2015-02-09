package jp.co.sint.webshop.utility;

/**
 * JIS2004関連処理をまとめたユーティリティクラスです。このクラスをインスタンス化することはできません。
 * 
 * @author System Integrator Corp.
 */
public final class Jis2004Util {

  /** 文字コードを添え字に取り、その文字コードがcp932で定義されている場合はtrue */
  private static boolean[] cp932Table = new boolean[0x10000];

  /** cp932で定義されていない文字は一律にこの文字に変換 */
  private static final char REPLACE_CHARACTER = '〓';

  /**
   * 新しいJis2004Utilを生成します。
   */
  private Jis2004Util() {
  }

  static {
    Jis2004Dictionary1.initLatinAlphabet(cp932Table);
    Jis2004Dictionary1.initHalfWidthKana(cp932Table);
    Jis2004Dictionary1.initNECSpecialCharacters(cp932Table);
    Jis2004Dictionary1.initNECSelectedIBMExtendedCharacters1(cp932Table);
    Jis2004Dictionary1.initNECSelectedIBMExtendedCharacters2(cp932Table);
    Jis2004Dictionary1.initNECSelectedIBMExtendedCharacters3(cp932Table);
    Jis2004Dictionary1.initNECSelectedIBMExtendedCharacters4(cp932Table);
    Jis2004Dictionary1.initIBMExtendedCharacters1(cp932Table);
    Jis2004Dictionary1.initIBMExtendedCharacters2(cp932Table);
    Jis2004Dictionary1.initIBMExtendedCharacters3(cp932Table);
    Jis2004Dictionary1.initIBMExtendedCharacters4(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters01(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters02(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters03(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters04(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters05(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters06(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters07(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters08(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters09(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters10(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters11(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters12(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters13(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters14(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters15(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters16(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters17(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters18(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters19(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters20(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters21(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters22(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters23(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters24(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters25(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters26(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters27(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters28(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters29(cp932Table);
    Jis2004Dictionary1.initMultiByteCharacters30(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters31(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters32(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters33(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters34(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters35(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters36(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters37(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters38(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters39(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters40(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters41(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters42(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters43(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters44(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters45(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters46(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters47(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters48(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters49(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters50(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters51(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters52(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters53(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters54(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters55(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters56(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters57(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters58(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters59(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters60(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters61(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters62(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters63(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters64(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters65(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters66(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters67(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters68(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters69(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters70(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters71(cp932Table);
    Jis2004Dictionary2.initMultiByteCharacters72(cp932Table);

  }

  /**
   * 文字を変換します。
   * 
   * @param c
   * @return 変換後の文字
   */
  public static char convertChar(char c) {
    if (cp932Table[c]) {
      return c;
    } else {
      return REPLACE_CHARACTER;
    }
  }

  /**
   * 文字列を変換します。
   * 
   * @param value
   *          変換対象の文字列
   * @return 変換後の文字列
   */
  public static String convertString(String value) {
    if (StringUtil.isNullOrEmpty(value)) {
      return value;
    }
    StringBuilder builder = new StringBuilder();
    char[] val = value.toCharArray();
    for (int i = 0; i < val.length; i++) {
      if (i < val.length - 1 && Character.isSurrogatePair(val[i], val[i + 1])) {
        // サロゲート・ペアで記述された文字は1文字にする
        i++;
        builder.append(REPLACE_CHARACTER);
        continue;
      }
      builder.append(convertChar(val[i]));
    }
    return builder.toString();
  }


}
