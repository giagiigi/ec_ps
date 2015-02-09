package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;


public class ZsIfSOShipWmsImportCsvSchema  extends CsvSchemaImpl {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public ZsIfSOShipWmsImportCsvSchema() {
    getColumns().add(new CsvColumnImpl("order_no",
        Messages.getCsvKey("service.data.csv.ZsIfSOShipErpImportCsvSchema.0"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("shipping_date",
        Messages.getCsvKey("service.data.csv.ZsIfSOShipErpImportCsvSchema.1"), CsvDataType.SHORT_DATE)); 
    getColumns().add(new CsvColumnImpl("line_no",
        Messages.getCsvKey("service.data.csv.ZsIfSOShipErpImportCsvSchema.2"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("sku_code",
        Messages.getCsvKey("service.data.csv.ZsIfSOShipErpImportCsvSchema.3"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("cancel_flg",
        Messages.getCsvKey("service.data.csv.ZsIfSOShipErpImportCsvSchema.4"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("shipping_amount",
        Messages.getCsvKey("service.data.csv.ZsIfSOShipErpImportCsvSchema.5"), CsvDataType.NUMBER)); 
    getColumns().add(new CsvColumnImpl("site",
        Messages.getCsvKey("service.data.csv.ZsIfSOShipErpImportCsvSchema.6"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("location",
        Messages.getCsvKey("service.data.csv.ZsIfSOShipErpImportCsvSchema.7"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("lot_number",
        Messages.getCsvKey("service.data.csv.ZsIfSOShipErpImportCsvSchema.8"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("reference",
        Messages.getCsvKey("service.data.csv.ZsIfSOShipErpImportCsvSchema.9"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("cancel_order_no",
        Messages.getCsvKey("service.data.csv.ZsIfSOShipErpImportCsvSchema.10"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("shipment_status",
        Messages.getCsvKey("service.data.csv.ZsIfSOShipErpImportCsvSchema.11"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("delivery_slip_no",
        Messages.getCsvKey("service.data.csv.ZsIfSOShipErpImportCsvSchema.12"), CsvDataType.STRING)); 
  }
  public String getExportConfigureId() {
    return "";
  }

  public String getImportConfigureId() {
    return "ZsIfSOShipWmsImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

}
