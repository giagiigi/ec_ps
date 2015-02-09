package jp.co.sint.webshop.utility;

public class LoadingObjectFailureException extends RuntimeException {

  /** uid */
  private static final long serialVersionUID = 1L;

  /**
   * 詳細メッセージに null を使用して、新しい例外を構築します。
   */
  public LoadingObjectFailureException() {
    super();
  }

  /**
   * 指定された詳細メッセージを使用して、新しい例外を構築します。
   * 
   * @param cause
   *          例外
   */
  public LoadingObjectFailureException(Throwable cause) {
    super(cause);
  }

  /**
   * 指定された詳細メッセージおよび原因を使用して新しい例外を構築します。
   * 
   * @param message
   *          詳細メッセージ
   * @param cause
   *          例外
   */
  public LoadingObjectFailureException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * (cause==null ? null : cause.toString()) の指定された原因および詳細メッセージを使用して新しい例外を構築します。
   * 
   * @param message
   *          詳細メッセージ
   */
  public LoadingObjectFailureException(String message) {
    super(message);
  }
}
