package jp.co.sint.webshop.data;

import java.io.Serializable;

/**
 * 繰り返し更新の実行結果を表すオブジェクトです。
 * 
 * @author System Integrator Corp.
 */
public class UpdateResult implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private boolean error;

  private int updateCount;

  /**
   * 更新件数を返します。
   * 
   * @return 更新件数
   */
  public int getUpdateCount() {
    return updateCount;
  }

  /**
   * 更新件数を取得します。
   * 
   * @param updateCount
   *          更新件数
   */
  public void setUpdateCount(int updateCount) {
    this.updateCount = updateCount;
  }

  /**
   * 更新実行時にエラーがあったかどうかを返します。
   * 
   * @return エラーがあればtrue
   */
  public boolean hasError() {
    return error;
  }

  /**
   * 更新実行時にエラーがあったかどうかを設定します。
   * 
   * @param error
   *          更新実行時にエラーがあったかどうか
   */
  public void setError(boolean error) {
    this.error = error;
  }

}
