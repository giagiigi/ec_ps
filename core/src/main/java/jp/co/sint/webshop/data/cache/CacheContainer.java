// 10.4.0 X40001 新規作成
package jp.co.sint.webshop.data.cache;

/**
 * 一時的なデータキャッシュを管理するコンテナのインタフェースです。
 * 
 * @author System Integrator Corp.
 */
public interface CacheContainer {

  /**
   * コンテナからデータを取得します。
   * 
   * @param key
   *          データを指定するキー
   * @return キャッシュデータ。データが存在しない、あるいは期限切れ(expired)の場合はnullを返します。
   */
  <V>V get(CacheKey key);

  /**
   * コンテナからデータを取得し、キャッシュが見つからない場合はコールバックを実行してデータをコンテナに登録します。
   * 
   * @param key
   *          データを指定するキー
   * @param callback
   *          コールバック処理インタフェース
   * @return キャッシュデータ。データが存在しない、あるいは期限切れ(expired)の場合は
   *         コールバック処理インタフェースの実行結果を返します。コールバック処理が実行された場合、
   *         コールバック処理によって戻されたデータをキャッシュとして登録します。
   *         キャッシュの取得結果とコールバック処理の結果がいずれもnullである場合、nullを返します。
   */
  <V>V get(CacheKey key, CacheRetriever<V> callback);

  /**
   * コンテナにデータを登録します。
   * 
   * @param key
   *          データを指定するキー
   * @param value
   *          キャッシュに登録するデータ
   */
  <V>V put(CacheKey key, V value);

}
