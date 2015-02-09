package jp.co.sint.webshop.data;

public class ConcurrencyFailureException extends DataAccessException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public ConcurrencyFailureException() {
    super();
  }

  public ConcurrencyFailureException(Throwable cause) {
    super(cause);
  }

  public ConcurrencyFailureException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConcurrencyFailureException(String message) {
    super(message);
  }
}
