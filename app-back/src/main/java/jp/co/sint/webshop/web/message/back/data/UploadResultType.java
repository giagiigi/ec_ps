package jp.co.sint.webshop.web.message.back.data;

/**
 * アップロードを行った結果を表すEnum値です。
 * 
 * @author System Integrator Corp.
 */
public enum UploadResultType {
  /** 成功 */
  SUCCEED,

  /** 失敗 */
  CAUTION,

  /** 致命的な失敗 */
  FAILED
}
