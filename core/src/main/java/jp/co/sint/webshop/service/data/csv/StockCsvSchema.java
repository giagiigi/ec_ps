package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.text.Messages;

public class StockCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public StockCsvSchema() {
    getColumns().add(
        new CsvColumnImpl(
            "shop_code",
            Messages.getCsvKey("service.data.csv.StockCsvSchema.0"),
            CsvDataType.STRING, false, false, true, null));
    getColumns().add(
        new CsvColumnImpl(
            "sku_code",
            Messages.getCsvKey("service.data.csv.StockCsvSchema.1"),
            CsvDataType.STRING, false, false, true, null));
    getColumns()
        .add(
            new CsvColumnImpl(
                "commodity_code",
                Messages.getCsvKey("service.data.csv.StockCsvSchema.2"),
                CsvDataType.STRING, false, false, false, null));
    getColumns().add(
        new CsvColumnImpl("stock_quantity",
            Messages.getCsvKey("service.data.csv.StockCsvSchema.3"), CsvDataType.NUMBER)); 
    getColumns().add(
        new CsvColumnImpl("allocated_quantity",
            Messages.getCsvKey("service.data.csv.StockCsvSchema.4"), CsvDataType.NUMBER)); 
    getColumns().add(
        new CsvColumnImpl("reserved_quantity",
            Messages.getCsvKey("service.data.csv.StockCsvSchema.5"), CsvDataType.NUMBER)); 
    getColumns().add(
        new CsvColumnImpl("reservation_limit",
            Messages.getCsvKey("service.data.csv.StockCsvSchema.6"), CsvDataType.NUMBER)); 
    getColumns()
        .add(
            new CsvColumnImpl(
                "oneshot_reservation_limit",
                Messages.getCsvKey("service.data.csv.StockCsvSchema.7"), CsvDataType.NUMBER)); 
    getColumns().add(
        new CsvColumnImpl("stock_threshold",
            Messages.getCsvKey("service.data.csv.StockCsvSchema.8"), CsvDataType.NUMBER));
    getColumns()
        .add(
            new CsvColumnImpl(
                "orm_rowid",
                Messages.getCsvKey("service.data.csv.StockCsvSchema.10"),
                CsvDataType.NUMBER, false, true, false,
                getTargetTableName() + "_SEQ"));
    getColumns().add(
        new CsvColumnImpl("created_user",
            Messages.getCsvKey("service.data.csv.StockCsvSchema.11"), CsvDataType.STRING)); 
    getColumns().add(
        new CsvColumnImpl("created_datetime",
            Messages.getCsvKey("service.data.csv.StockCsvSchema.12"), CsvDataType.DATETIME)); 
    getColumns().add(
        new CsvColumnImpl("updated_user",
            Messages.getCsvKey("service.data.csv.StockCsvSchema.13"), CsvDataType.STRING)); 
    getColumns().add(
        new CsvColumnImpl("updated_datetime",
            Messages.getCsvKey("service.data.csv.StockCsvSchema.14"), CsvDataType.DATETIME)); 
  }

  public String getExportConfigureId() {
    return "StockExportDataSource";
  }

  public String getImportConfigureId() {
    return "StockImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(Stock.class);
  }

}
