package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;


public class CCommodityStockDataCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public CCommodityStockDataCsvSchema() {
    
  }

  public String getExportConfigureId() {
    return "CCommodityStockExportDataSource";
  }

  public String getImportConfigureId() {
    return "CCommodityStockImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }
}
