package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;


/**
 * 入荷完了导入
 * 
 * @author OS011
 *
 */
public class ZsIfPoWmsImportCsvSchema extends CsvSchemaImpl {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public ZsIfPoWmsImportCsvSchema() {
    getColumns().add(new CsvColumnImpl("po_number",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.0"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("effective_date",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.1"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("packing_slip",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.2"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("receiver",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.3"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("line",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.4"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("sku_code",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.5"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("stock_total",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.6"), CsvDataType.NUMBER)); 
    getColumns().add(new CsvColumnImpl("um",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.7"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("site",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.8"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("location",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.9"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("lot",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.10"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("reference",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.11"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("expire_date",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.12"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("detail_comment",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.13"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("po_result_flag",
        Messages.getCsvKey("service.data.csv.ZsIfPoWmsImportCsvSchema.14"), CsvDataType.STRING)); 
  }
  public String getExportConfigureId() {
    return "";
  }

  public String getImportConfigureId() {
    return "ZsIfPoWmsImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

}

