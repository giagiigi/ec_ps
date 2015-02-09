package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class RefererCsvSchema extends CsvSchemaImpl {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public RefererCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("REFERER_URL",
        Messages.getCsvKey("service.data.csv.RefererCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("REFERER_COUNT",
        Messages.getCsvKey("service.data.csv.RefererCsvSchema.1"), CsvDataType.NUMBER));

    setColumns(columns);
  }

  public String getExportConfigureId() {
    return "RefererExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
