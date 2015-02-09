package jp.co.sint.webshop.utility;

/**
 * 郵便番号検索によって返される住所情報クラス
 * 
 * @author System Integrator Corp.
 */
public interface PostalAddress {

  /**
   * 郵便番号を取得します。
   * 
   * @return 郵便番号
   */
  String getPostalCode();

  /**
   * 都道府県を取得します。
   * 
   * @return 都道府県
   */
  String getPrefecture();

  /**
   * 市区町村を取得します。
   * 
   * @return 市区町村
   */
  String getCity();

  /**
   * 町名・番地を取得します。
   * 
   * @return 町名・番地
   */
  String getAddress();

}
