package jp.co.sint.webshop.data.csv;

/**
 * CSV出力例外です。
 * 
 * @author System Integrator Corp.
 */
public class CsvExportException extends RuntimeException {

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
