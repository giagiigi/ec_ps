package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class AccessLogCsvSchema extends CsvSchemaImpl {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public AccessLogCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("LABEL",
        Messages.getCsvKey("service.data.csv.AccessLogCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("PAGE_VIEW_COUNT",
        Messages.getCsvKey("service.data.csv.AccessLogCsvSchema.1"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("CONVERSION_RATE",
        Messages.getCsvKey("service.data.csv.AccessLogCsvSchema.2"), CsvDataType.STRING));

    setColumns(columns);
  }

  public String getExportConfigureId() {
    return "AccessLogExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
