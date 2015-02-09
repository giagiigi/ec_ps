// 10.4.0 X40001 新規作成
package jp.co.sint.webshop.data.cache;

/**
 * CacheContainer にデータキャッシュがなかった場合、データを取得するためのコールバック処理を表すインタフェースです。
 * 
 * @author System Integrator Corp.
 * @param <D>
 *          コールバックで取得できるデータ型
 */
public interface CacheRetriever<D> {

  /**
   * データを取得します。
   * 
   * @return データ
   */
  D retrieve();

}
