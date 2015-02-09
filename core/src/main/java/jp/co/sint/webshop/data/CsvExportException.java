package jp.co.sint.webshop.data;

/**
 * CSV出力例外
 * 
 * @author System Integrator Corp.
 */
public class CsvExportException extends DataAccessException {

  /** uid */
  private static final long serialVersionUID = 1L;

  public CsvExportException() {
    super();
  }

  public CsvExportException(Throwable cause) {
    super(cause);
  }

  public CsvExportException(String message, Throwable cause) {
    super(message, cause);
  }

  public CsvExportException(String message) {
    super(message);
  }
}
