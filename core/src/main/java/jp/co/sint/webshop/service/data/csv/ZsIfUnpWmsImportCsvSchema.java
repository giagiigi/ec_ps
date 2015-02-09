package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;


/**
 * 盘点库存数导入
 * 
 * @author OS011
 *
 */
public class ZsIfUnpWmsImportCsvSchema extends CsvSchemaImpl {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public ZsIfUnpWmsImportCsvSchema() {
    getColumns().add(new CsvColumnImpl("sku_code",
        Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.0"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("stock_total",
        Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.1"), CsvDataType.NUMBER)); 
    getColumns().add(new CsvColumnImpl("um",
            Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.2"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("conversion",
            Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.3"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("site",
            Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.4"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("location",
            Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.5"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("lot_serial",
            Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.6"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("reference",
        Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.7"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("multi_entry",
        Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.8"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("order",
        Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.9"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("line",
        Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.10"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("sales_job",
        Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.11"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("address",
        Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.12"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("remarks",
        Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.13"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("effective_date",
        Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.14"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("reason_code",
        Messages.getCsvKey("service.data.csv.ZsIfUnpWmsImportCsvSchema.15"), CsvDataType.STRING)); 
  }
  public String getExportConfigureId() {
    return "";
  }

  public String getImportConfigureId() {
    return "ZsIfUnpWmsImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

}

