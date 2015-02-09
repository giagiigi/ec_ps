package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class EnqueteSummaryInputCsvSchema extends CsvSchemaImpl {

  public EnqueteSummaryInputCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("ENQUETE_REPLY",
        Messages.getCsvKey("service.data.csv.EnqueteSummaryInputCsvSchema.0"),
        CsvDataType.STRING));
    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "EnqueteSummaryInputExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
