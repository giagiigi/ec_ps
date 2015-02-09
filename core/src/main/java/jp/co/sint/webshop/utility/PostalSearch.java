package jp.co.sint.webshop.utility;

import java.util.List;

/**
 * 郵便番号検索インタフェース
 * 
 * @author System Integrator Corp.
 */
public interface PostalSearch {

  /**
   * 郵便番号に対応する住所を返します。
   * 
   * @param postalCode
   *          郵便番号
   * @return 郵便番号に対応する住所を返します。<br>
   *         該当する住所が存在しない場合はnullを返します。
   */
  PostalAddress get(String postalCode);

  /**
   * 郵便番号に対応する住所のリストを返します。
   * 
   * @param postalCode
   *          郵便番号
   * @return 郵便番号に対応する住所のリストを返します。
   */
  List<PostalAddress> getAll(String postalCode);

  /**
   * 郵便番号検索が利用可能かどうかを返します。
   * 
   * @return 郵便番号検索が利用可能な状態であればtrue。
   */
  boolean isEnabled();

}
