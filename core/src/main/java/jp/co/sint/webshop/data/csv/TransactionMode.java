package jp.co.sint.webshop.data.csv;


/**
 * CSV取り込み時のトランザクションモードを表します。
 * 
 * @author System Integrator Corp.
 */
public enum TransactionMode {
  
  /** 強制コミットを表します。(取り込み中の失敗件数に拘わらず) */
  COMMIT_FORCE,
  /** 強制ロールバックを表します。強制コミットを表します。(取り込み中の成功件数に拘わらず) */
  ROLLBACK_FORCE,
  /** 全件成功の場合はコミット、１件でも失敗の場合はロールバックを表します。 */
  DEPEND_ON_RESULT;
  
}
