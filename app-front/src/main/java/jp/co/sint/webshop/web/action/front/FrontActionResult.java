package jp.co.sint.webshop.web.action.front;

import jp.co.sint.webshop.web.action.WebActionResult;

/**
 * アクション処理結果のenumです。
 * 
 * @author System Integrator Corp.
 */
public enum FrontActionResult implements WebActionResult { 
  /** エラーが無かった場合 */
  RESULT_SUCCESS(WebActionResult.RESULT_SUCCESS, ""),
  /** サービス内部エラー */
  SERVICE_ERROR(0, "サービス内部でエラーが発生しました"),
  /** サービス内部Validationエラー */
  SERVICE_VALIDATION_ERROR(1, "サービス内部でValidationエラーが発生しました"),
  /** 未ログインエラー F/Wで使用 */
  LOGIN_FAILED_ERROR(2, "未ログイン");

  /** ログと画面に表示するメッセージ */
  private String message;

  /** 処理結果コード */
  private int resultCode;

  private FrontActionResult(int resultCode, String defaultMessage) {
    this.message = defaultMessage;
    this.resultCode = resultCode;
  }

  /**
   * ログと画面で表示するメッセージを取得します。
   * 
   * @return メッセージ
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * 処理結果コードを取得します。
   * 
   * @return 処理結果コード
   */
  public int getCode() {
    return resultCode;
  }

}
