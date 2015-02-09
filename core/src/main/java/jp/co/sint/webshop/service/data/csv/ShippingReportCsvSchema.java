package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class ShippingReportCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public ShippingReportCsvSchema() {
    getColumns().add(new CsvColumnImpl("shipping_no",
        Messages.getCsvKey("service.data.csv.ShippingReportCsvSchema.0"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("shipping_direct_date",
        Messages.getCsvKey("service.data.csv.ShippingReportCsvSchema.1"), CsvDataType.DATE)); 
    getColumns().add(new CsvColumnImpl("shipping_date",
        Messages.getCsvKey("service.data.csv.ShippingReportCsvSchema.2"), CsvDataType.DATE)); 
    getColumns().add(new CsvColumnImpl("arrival_date",
        Messages.getCsvKey("service.data.csv.ShippingReportCsvSchema.3"), CsvDataType.DATE)); 
    getColumns().add(new CsvColumnImpl("arrival_time_start",
        Messages.getCsvKey("service.data.csv.ShippingReportCsvSchema.4"), CsvDataType.NUMBER)); 
    getColumns().add(new CsvColumnImpl("arrival_time_end",
        Messages.getCsvKey("service.data.csv.ShippingReportCsvSchema.5"), CsvDataType.NUMBER)); 
    getColumns().add(new CsvColumnImpl("delivery_slip_no",
        Messages.getCsvKey("service.data.csv.ShippingReportCsvSchema.6"), CsvDataType.STRING)); 
  }

  public String getExportConfigureId() {
    return "ShippingReportExportDataSource";
  }

  public String getImportConfigureId() {
    return "ShippingReportImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

}
