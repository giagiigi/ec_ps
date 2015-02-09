package jp.co.sint.webshop.utility;

/**
 * UCS-2の範囲を許可するWebshopCharSetの実装クラスです。
 * 
 * @author System Integrator Corp.
 */
public class Ucs2CharSet implements WebshopCharSet {

  private char replaceCharacter = '?';

  public boolean contains(char ch) {
    return !isSurrogate(ch);
  }

  private boolean isSurrogate(char ch) {
    return Character.isHighSurrogate(ch) || Character.isLowSurrogate(ch);
  }

  /**
   * replaceCharacterを取得します。
   * 
   * @return replaceCharacter replaceCharacter
   */
  public char getReplaceCharacter() {
    return replaceCharacter;
  }

  /**
   * replaceCharacterを設定します。
   * 
   * @param replaceCharacter
   *          replaceCharacter
   */
  public void setReplaceCharacter(char replaceCharacter) {
    this.replaceCharacter = replaceCharacter;
  }

}
