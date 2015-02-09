package jp.co.sint.webshop.data;

/**
 * データアクセス例外
 * 
 * @author System Integrator Corp.
 */
public class DataAccessException extends RuntimeException {

  /** uid */
  private static final long serialVersionUID = 1L;

  public DataAccessException() {
    super();
  }

  public DataAccessException(Throwable cause) {
    super(cause);
  }

  public DataAccessException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataAccessException(String message) {
    super(message);
  }

}
