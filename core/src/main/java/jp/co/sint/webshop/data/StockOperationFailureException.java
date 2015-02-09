package jp.co.sint.webshop.data;

public class StockOperationFailureException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public StockOperationFailureException() {
    super();
  }

  public StockOperationFailureException(Throwable cause) {
    super(cause);
  }

  public StockOperationFailureException(String message, Throwable cause) {
    super(message, cause);
  }

  public StockOperationFailureException(String message) {
    super(message);
  }
}
