package jp.co.sint.webshop.utility;

/**
 * 設定された文字セットのルールに従って文字変換、文字列変換を担当するクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CharacterConverter {

  private WebshopCharSet charSet = new Ucs2CharSet();

  public char convertChar(char ch) {
    if (charSet.contains(ch)) {
      return ch;
    } else {
      return charSet.getReplaceCharacter();
    }
  }

  public String convertString(String value) {

    if (StringUtil.isNullOrEmpty(value)) {
      return value;
    }

    StringBuilder builder = new StringBuilder();
    char[] val = value.toCharArray();
    for (int i = 0; i < val.length; i++) {
      if (i < val.length - 1 && Character.isSurrogatePair(val[i], val[i + 1])) {
        // サロゲート・ペアで記述された文字は1文字にする
        i++;
        builder.append(charSet.getReplaceCharacter());
        continue;
      }
      builder.append(convertChar(val[i]));
    }
    return builder.toString();
  }

  /**
   * charSetを取得します。
   * 
   * @return charSet charSet
   */
  public WebshopCharSet getCharSet() {
    return charSet;
  }

  /**
   * charSetを設定します。
   * 
   * @param charSet
   *          charSet
   */
  public void setCharSet(WebshopCharSet charSet) {
    this.charSet = charSet;
  }

}
