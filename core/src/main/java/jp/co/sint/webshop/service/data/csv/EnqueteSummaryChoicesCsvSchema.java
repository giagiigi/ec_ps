package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class EnqueteSummaryChoicesCsvSchema extends CsvSchemaImpl {

  public EnqueteSummaryChoicesCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("ENQUETE_CHOICES",
        Messages.getCsvKey("service.data.csv.EnqueteSummaryChoicesCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("NUM_OF_ANSWERS",
        Messages.getCsvKey("service.data.csv.EnqueteSummaryChoicesCsvSchema.1"), CsvDataType.NUMBER));
    setColumns(columns);
  }

  /** serial versin UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "EnqueteSummaryChoicesExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
