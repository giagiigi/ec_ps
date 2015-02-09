package jp.co.sint.webshop.data;

public class TransactionFailureException extends DataAccessException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public TransactionFailureException() {
    super();
  }

  public TransactionFailureException(Throwable cause) {
    super(cause);
  }

  public TransactionFailureException(String message, Throwable cause) {
    super(message, cause);
  }

  public TransactionFailureException(String message) {
    super(message);
  }
}
