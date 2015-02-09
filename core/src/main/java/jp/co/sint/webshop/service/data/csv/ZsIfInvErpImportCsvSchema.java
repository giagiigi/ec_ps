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
public class ZsIfInvErpImportCsvSchema extends CsvSchemaImpl {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public ZsIfInvErpImportCsvSchema() {
    getColumns().add(new CsvColumnImpl("sku_code",
        Messages.getCsvKey("service.data.csv.ZsIfInvErpImportCsvSchema.0"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("site",
        Messages.getCsvKey("service.data.csv.ZsIfInvErpImportCsvSchema.1"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("location",
            Messages.getCsvKey("service.data.csv.ZsIfInvErpImportCsvSchema.2"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("lot_number",
            Messages.getCsvKey("service.data.csv.ZsIfInvErpImportCsvSchema.3"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("reference_number",
            Messages.getCsvKey("service.data.csv.ZsIfInvErpImportCsvSchema.4"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("stock_total",
            Messages.getCsvKey("service.data.csv.ZsIfInvErpImportCsvSchema.5"), CsvDataType.NUMBER)); 
    getColumns().add(new CsvColumnImpl("um",
            Messages.getCsvKey("service.data.csv.ZsIfInvErpImportCsvSchema.6"), CsvDataType.STRING)); 
  }
  public String getExportConfigureId() {
    return "";
  }

  public String getImportConfigureId() {
    return "ZsIfInvErpImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

}

