package jp.co.sint.webshop.data.csv;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidationSummary;

/**
 * CSV取込失敗例例外です。
 * 
 * @author System Integrator Corp.
 */
public class CsvImportException extends RuntimeException {

  private ValidationSummary summary;

  /** uid */
  private static final long serialVersionUID = 1L;

  public CsvImportException() {
    super();
  }

  public CsvImportException(Throwable cause) {
    super(cause);
  }

  public CsvImportException(String message, Throwable cause) {
    super(message, cause);
  }

  public CsvImportException(String message) {
    super(message);
  }

  public CsvImportException(ValidationSummary sumamry, Throwable cause) {
    super(StringUtil.toMultiLine(sumamry.getErrorMessages()), cause);
    this.summary = sumamry;
  }

  public CsvImportException(ValidationSummary sumamry) {
    super(StringUtil.toMultiLine(sumamry.getErrorMessages()));
    this.summary = sumamry;
  }

  /**
   * summaryを返します。
   * 
   * @return the summary
   */
  public ValidationSummary getSummary() {
    return summary;
  }

}
