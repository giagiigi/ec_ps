package jp.co.sint.webshop.data.csv;

import java.io.Writer;
import jp.co.sint.webshop.utility.VoidWriter;

/**
 * 取り込んだCSVデータをフィルタリングして分離するクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CsvFilter {

  private Writer outputWriter;

  private Writer errorWriter;

  private Writer logWriter;

  public CsvFilter() {
    setOutputWriter(new VoidWriter());
    setErrorWriter(new VoidWriter());
    setLogWriter(new VoidWriter());
  }

  public CsvFilter(Writer out, Writer err, Writer log) {
    setOutputWriter(out);
    setErrorWriter(err);
    setLogWriter(log);
  }

  /**
   * outputWriterを返します。
   * 
   * @return the outputWriter
   */
  public Writer getOutputWriter() {
    return outputWriter;
  }

  /**
   * outputWriterを設定します。
   * 
   * @param outputWriter
   *          設定する outputWriter
   */
  public void setOutputWriter(Writer outputWriter) {
    this.outputWriter = outputWriter;
  }

  /**
   * errorWriterを返します。
   * 
   * @return the errorWriter
   */
  public Writer getErrorWriter() {
    return errorWriter;
  }

  /**
   * errorWriterを設定します。
   * 
   * @param errorWriter
   *          設定する errorWriter
   */
  public void setErrorWriter(Writer errorWriter) {
    this.errorWriter = errorWriter;
  }

  /**
   * logWriterを返します。
   * 
   * @return the logWriter
   */
  public Writer getLogWriter() {
    return logWriter;
  }

  /**
   * logWriterを設定します。
   * 
   * @param logWriter
   *          設定する logWriter
   */
  public void setLogWriter(Writer logWriter) {
    this.logWriter = logWriter;
  }

}
