package jp.co.sint.webshop.data;

/**
 * 繰り返し更新のためのクエリオブジェクトです。
 * 
 * @author System Integrator Corp.
 * @param <T>
 */
public interface UpdateQuery<T> extends Query {

  /**
   * 更新用パラメータの配列を返します。
   * 
   * @return 更新用パラメータの配列
   */
  Object[][] getUpdateParameters();

  /**
   * 更新を1つのトランザクションとして扱うかどうかを返します。
   * 
   * @return 更新が1つのトランザクションであればtrue
   */
  boolean isTransacion();
}
