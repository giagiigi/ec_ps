package jp.co.sint.webshop.utility;

/**
 * SI Web Shoppingが入出力可能な文字セットの範囲を定義するインタフェースです。
 * 
 * @author System Integrator Corp.
 */
public interface WebshopCharSet {

  /**
   * システムで利用可能な文字かどうかを判断します。
   * 
   * @param ch
   *          チェック対象文字
   * @return システムで利用可能な文字であればtrueを返します。
   */
   boolean contains(char ch);

  /**
   * システムで利用可能でない文字を置換するための文字を返します。。
   * 
   * @return 置換文字
   */
  char getReplaceCharacter();

}
