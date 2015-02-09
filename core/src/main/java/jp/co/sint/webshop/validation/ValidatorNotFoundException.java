package jp.co.sint.webshop.validation;

public class ValidatorNotFoundException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public ValidatorNotFoundException() {
    super();
  }

  public ValidatorNotFoundException(Throwable cause) {
    super(cause);
  }

  public ValidatorNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ValidatorNotFoundException(String message) {
    super(message);
  }
}
