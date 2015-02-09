package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class CustomerDMCsvSchema extends CsvSchemaImpl {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public CustomerDMCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl(
        "ADDRESS_LAST_NAME", Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.0"),
        CsvDataType.STRING));
    //  delete by V10-CH 170 start
//    columns.add(new CsvColumnImpl(
//        "ADDRESS_FIRST_NAME", Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.1"),
//        CsvDataType.STRING));
    //  delete by V10-CH 170 end
    columns.add(new CsvColumnImpl("POSTAL_CODE",
        Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.4"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ADDRESS1",
        Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.5"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ADDRESS2",
        Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.6"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ADDRESS3",
        Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.7"), CsvDataType.STRING));
//  delete by V10-CH 170 start
    columns.add(new CsvColumnImpl("ADDRESS4",
        Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.8"), CsvDataType.STRING));
//  delete by V10-CH 170 end
    columns
        .add(new CsvColumnImpl("PHONE_NUMBER",
            Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.9"), CsvDataType.STRING));
    columns
    .add(new CsvColumnImpl("MOBILE_NUMBER",
        Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.15"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ORM_ROWID",
        Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.10"), CsvDataType.NUMBER));
    columns
        .add(new CsvColumnImpl("CREATED_USER",
            Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.11"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl(
        "CREATED_DATETIME",
        Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.12"), CsvDataType.DATETIME));
    columns
        .add(new CsvColumnImpl("UPDATED_USER",
            Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.13"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl(
        "UPDATED_DATETIME",
        Messages.getCsvKey("service.data.csv.CustomerDMCsvSchema.14"), CsvDataType.DATETIME));
    setColumns(columns);
  }

  public String getExportConfigureId() {
    return "CustomerDMExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
