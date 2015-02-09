package jp.co.sint.webshop.data;

import java.io.Serializable;

import jp.co.sint.webshop.utility.ArrayUtil;

public class DownloadResource implements Serializable {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private Query query;

  private String[] header = new String[0];

  private String fileName;

  private ExportType exportType = ExportType.CSV;

  /**
   * exportTypeを取得します。
   * 
   * @return exportType
   */
  public ExportType getExportType() {
    return exportType;
  }

  /**
   * headerを取得します。
   * 
   * @return header
   */
  public String[] getHeader() {
    return ArrayUtil.immutableCopy(header);
  }

  /**
   * queryを取得します。
   * 
   * @return query
   */
  public Query getQuery() {
    return query;
  }

  /**
   * headerを設定します。
   * 
   * @param header
   *          header
   */
  public void setHeader(String[] header) {
    if (header == null) {
      this.header = new String[0];
    } else {
      this.header = ArrayUtil.immutableCopy(header);
    }
  }

  /**
   * queryを設定します。
   * 
   * @param query
   *          query
   */
  public void setQuery(Query query) {
    this.query = query;
  }

  public String getContentType() {
    return "text/csv";
  }

  public static enum ExportType {
    CSV, XML;
  }

  /**
   * fileNameを取得します。
   * 
   * @return fileName
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * fileNameを設定します。
   * 
   * @param fileName
   *          fileName
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * exportTypeを設定します。
   * 
   * @param exportType
   *          exportType
   */
  public void setExportType(ExportType exportType) {
    this.exportType = exportType;
  }

}
