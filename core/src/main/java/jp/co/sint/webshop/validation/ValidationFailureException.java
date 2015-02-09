package jp.co.sint.webshop.validation;

/**
 * データ検証時に発生する実行時例外です。
 * 
 * @author System Integrator Corp.
 */
public class ValidationFailureException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public ValidationFailureException() {
    super();
  }

  public ValidationFailureException(Throwable cause) {
    super(cause);
  }

  public ValidationFailureException(String message, Throwable cause) {
    super(message, cause);
  }

  public ValidationFailureException(String message) {
    super(message);
  }
}
