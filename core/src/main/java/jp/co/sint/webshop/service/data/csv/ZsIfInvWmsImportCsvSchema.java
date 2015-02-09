package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;


/**
 * 从ERP导入sku有效库存
 *  * 
 * @author OS011
 *
 */
public class ZsIfInvWmsImportCsvSchema extends CsvSchemaImpl {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public ZsIfInvWmsImportCsvSchema() {
    getColumns().add(new CsvColumnImpl("sku_code",
        Messages.getCsvKey("service.data.csv.ZsIfInvWmsImportCsvSchema.0"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("effective_date",
        Messages.getCsvKey("service.data.csv.ZsIfInvWmsImportCsvSchema.1"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("stock_total",
            Messages.getCsvKey("service.data.csv.ZsIfInvWmsImportCsvSchema.2"), CsvDataType.NUMBER)); 
    getColumns().add(new CsvColumnImpl("sales_job",
            Messages.getCsvKey("service.data.csv.ZsIfInvWmsImportCsvSchema.3"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("remark",
            Messages.getCsvKey("service.data.csv.ZsIfInvWmsImportCsvSchema.4"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("from_site",
            Messages.getCsvKey("service.data.csv.ZsIfInvWmsImportCsvSchema.5"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("from_location",
            Messages.getCsvKey("service.data.csv.ZsIfInvWmsImportCsvSchema.6"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("from_lotnumber",
        Messages.getCsvKey("service.data.csv.ZsIfInvWmsImportCsvSchema.7"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("from_reference",
        Messages.getCsvKey("service.data.csv.ZsIfInvWmsImportCsvSchema.8"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("site",
        Messages.getCsvKey("service.data.csv.ZsIfInvWmsImportCsvSchema.9"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("location",
        Messages.getCsvKey("service.data.csv.ZsIfInvWmsImportCsvSchema.10"), CsvDataType.STRING)); 
  }
  public String getExportConfigureId() {
    return "";
  }

  public String getImportConfigureId() {
    return "ZsIfInvWmsImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

}

