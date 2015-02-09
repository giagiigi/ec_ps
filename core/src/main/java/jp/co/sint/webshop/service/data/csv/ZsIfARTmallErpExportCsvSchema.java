package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class ZsIfARTmallErpExportCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public ZsIfARTmallErpExportCsvSchema() {
    getColumns().add(
        new CsvColumnImpl("SALES_ORDER", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.0"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("CHECK", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.1"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("BILL_TO", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.2"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("CURRENCY", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.3"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("CHECK_CONTROL", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.4"),CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("RECEIVED_DATE", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.5"),CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("EFFECTIVE_DATE", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.6"),CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("BANK", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.7"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("REMARK", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.8"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("ACCOUNT", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.9"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("SUB_ACCOUNT", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.10"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("COST_CENTER", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.11"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("DISCOUNT_ACCOUNT", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.12"),CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("DISCOUNT_SUB_ACCOUNT", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.13"),CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("DISCOUNT_COST_CENTER", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.14"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("ENTITY", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.15"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("EXCHANGE_RATE", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.16"),CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("EXCHANGE_RATE2", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.17"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("AUTO_APPLY", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.18"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("AR_REFERENCE", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.19"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("RECEIVED_AMOUNT", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.20"),CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("CASH_AMOUNT", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.21"), CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("DISCOUNT", Messages.getCsvKey("service.data.csv.ZsIfARTmallErpExportCsvSchema.22"), CsvDataType.STRING));
  }

  public String getExportConfigureId() {
    return "ZsIfARTmallErpExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
