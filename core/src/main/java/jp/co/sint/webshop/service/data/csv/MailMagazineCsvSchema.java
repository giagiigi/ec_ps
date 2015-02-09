package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class MailMagazineCsvSchema extends CsvSchemaImpl {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public MailMagazineCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("MAIL_MAGAZINE_CODE",
        Messages.getCsvKey("service.data.csv.MailMagazineCsvSchema.0"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("EMAIL",
        Messages.getCsvKey("service.data.csv.MailMagazineCsvSchema.1"),
        CsvDataType.STRING));

    setColumns(columns);
  }

  public String getExportConfigureId() {
    return "MailMagazineExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
