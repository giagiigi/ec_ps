package jp.co.sint.webshop.data;

public class DuplicatedInsertException extends DataAccessException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public DuplicatedInsertException() {
    super();
  }

  public DuplicatedInsertException(Throwable cause) {
    super(cause);
  }

  public DuplicatedInsertException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicatedInsertException(String message) {
    super(message);
  }
}
