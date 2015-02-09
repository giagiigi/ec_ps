package jp.co.sint.webshop.data.csv;

/**
 * CSV取り込み時のコミットモードを表します。
 * 
 * @author System Integrator Corp.
 */
public enum CommitMode {
  /** 1件ごとのコミットを表します。 */
  FOR_EACH,
  /** 全件一括コミットを表します。 */
  BULK_COMMIT
}
