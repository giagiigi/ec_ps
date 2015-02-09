package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;


public class StockIODetailCsvSchema extends CsvSchemaImpl {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public StockIODetailCsvSchema() {
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.StockIODetailCsvSchema.0"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("sku_code",
        Messages.getCsvKey("service.data.csv.StockIODetailCsvSchema.1"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("stock_io_quantity",
        Messages.getCsvKey("service.data.csv.StockIODetailCsvSchema.2"), CsvDataType.NUMBER)); 
    getColumns().add(new CsvColumnImpl("stock_io_type",
        Messages.getCsvKey("service.data.csv.StockIODetailCsvSchema.3"), CsvDataType.NUMBER)); 
    getColumns().add(new CsvColumnImpl("memo",
        Messages.getCsvKey("service.data.csv.StockIODetailCsvSchema.4"), CsvDataType.STRING)); 
  }

  public String getExportConfigureId() {
    return "StockIODetailExportDataSource";
  }

  public String getImportConfigureId() {
    return "StockIODetailImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

}
