package jp.co.sint.webshop.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV取込処理の結果を表すクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CsvResult implements Serializable {

  /** uid */
  private static final long serialVersionUID = 1L;

  private String message;

  private int correctCount;

  private int errorCount;

  private int exportCount;

  private boolean aborted;

  private List<CsvResultDetail> details = new ArrayList<CsvResultDetail>();

  /**
   * @return correctCount
   */
  public int getCorrectCount() {
    return correctCount;
  }

  /**
   * @param correctCount
   *          設定する correctCount
   */
  public void setCorrectCount(int correctCount) {
    this.correctCount = correctCount;
  }

  /**
   * @return errorCount
   */
  public int getErrorCount() {
    return errorCount;
  }

  /**
   * @param errorCount
   *          設定する errorCount
   */
  public void setErrorCount(int errorCount) {
    this.errorCount = errorCount;
  }

  /**
   * @return message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return details
   */
  public List<CsvResultDetail> getDetails() {
    return details;
  }

  /**
   * @param message
   *          設定する message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return aborted
   */
  public boolean isAborted() {
    return aborted;
  }

  /**
   * @param aborted
   *          設定する aborted
   */
  public void setAborted(boolean aborted) {
    this.aborted = aborted;
  }

  public void addDetail(boolean error, int lineNo, String msg) {
    details.add(new CsvResultDetail(error, lineNo, msg));
  }

  public void addDetail(boolean error, int lineNo, List<String> msgList) {
    details.add(new CsvResultDetail(error, lineNo, msgList));
  }

  public static class CsvResultDetail implements Serializable {

    /** uid */
    private static final long serialVersionUID = 1L;

    private List<String> messages = new ArrayList<String>();

    private int lineNo;

    private boolean error;

    public CsvResultDetail() {
    }

    public CsvResultDetail(boolean error, int lineNo, String msg) {
      init(error, lineNo);
      messages.add(msg);
    }

    public CsvResultDetail(boolean error, int lineNo, List<String> msgList) {
      init(error, lineNo);
      messages.addAll(msgList);
    }

    private void init(boolean b, int i) {
      this.error = b;
      this.lineNo = i;
    }

    /**
     * @return lineNo
     */
    public int getLineNo() {
      return lineNo;
    }

    /**
     * @return messages
     */
    public List<String> getMessages() {
      return messages;
    }

    /**
     * @return error
     */
    public boolean isError() {
      return error;
    }

    /**
     * @param error
     *          設定する error
     */
    public void setError(boolean error) {
      this.error = error;
    }

  }

  /**
   * @return the exportCount
   */
  public int getExportCount() {
    return exportCount;
  }

  /**
   * @param exportCount
   *          the exportCount to set
   */
  public void setExportCount(int exportCount) {
    this.exportCount = exportCount;
  }

}
