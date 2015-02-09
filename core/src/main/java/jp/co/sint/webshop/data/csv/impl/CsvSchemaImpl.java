package jp.co.sint.webshop.data.csv.impl;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

public abstract class CsvSchemaImpl implements CsvSchema {

  private static final long serialVersionUID = 1L;

  private List<CsvColumn> columns = new ArrayList<CsvColumn>();

  private boolean header;

  private String csvId;

  public CsvSchemaImpl() {
  }

  public CsvSchemaImpl(boolean header, List<CsvColumn> columns) {
    setHeader(header);
    setColumns(columns);
  }

  /**
   * fileNameを返します。
   * 
   * @return the fileName
   */
  public String getFileName() {
    return CsvUtil.getCsvFileName(getCsvId());
  }

  public ValidationSummary checkColumns(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    for (int i = 0; i < getColumns().size(); i++) {
      CsvColumn column = getColumns().get(i);
      String value = csvLine.get(i);

      if (StringUtil.isNullOrEmpty(value)) {
        continue;
      }

      if (column.getDataType().equals(CsvDataType.NUMBER)) {
        if (!NumUtil.isNum(value)) {
          summary.getErrors().add(new ValidationResult(column.getLogicalName(), null, Message.get(CsvMessage.DIGIT)));
        }
      } else if (column.getDataType().equals(CsvDataType.DATETIME) || column.getDataType().equals(CsvDataType.DATE)) {
        if (!DateUtil.isCorrect(value)) {
          summary.getErrors().add(new ValidationResult(column.getLogicalName(), null, Message.get(CsvMessage.DATETIME)));
        }
      }
    }
    return summary;
  }

  /**
   * columnsを返します。
   * 
   * @return the columns
   */
  public List<CsvColumn> getColumns() {
    return columns;
  }

  /**
   * columnsを設定します。
   * 
   * @param columns
   *          設定する columns
   */
  public void setColumns(List<CsvColumn> columns) {
    this.columns = columns;
  }

  /**
   * headerを返します。
   * 
   * @return the header
   */
  public boolean hasHeader() {
    return header;
  }

  /**
   * headerを設定します。
   * 
   * @param header
   *          設定する header
   */
  public void setHeader(boolean header) {
    this.header = header;
  }

  /**
   * csvIdを取得します。
   * 
   * @return csvId
   */

  public String getCsvId() {
    return csvId;
  }

  /**
   * csvIdを設定します。
   * 
   * @param csvId
   *          csvId
   */
  public void setCsvId(String csvId) {
    this.csvId = csvId;
  }

}
