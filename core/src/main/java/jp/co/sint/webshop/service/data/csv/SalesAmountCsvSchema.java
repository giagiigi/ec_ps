package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class SalesAmountCsvSchema extends CsvSchemaImpl {

  public SalesAmountCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("SHOP_CODE",
        Messages.getCsvKey("service.data.csv.SalesAmountCsvSchema.0"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("SHOP_NAME",
        Messages.getCsvKey("service.data.csv.SalesAmountCsvSchema.1"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("TOTAL_SALES_PRICE",
        Messages.getCsvKey("service.data.csv.SalesAmountCsvSchema.2"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("TOTAL_SALES_PRICE_TAX",
        Messages.getCsvKey("service.data.csv.SalesAmountCsvSchema.3"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("TOTAL_GIFT_PRICE",
        Messages.getCsvKey("service.data.csv.SalesAmountCsvSchema.4"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("TOTAL_GIFT_TAX",
        Messages.getCsvKey("service.data.csv.SalesAmountCsvSchema.5"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("TOTAL_SHIPPING_CHARGE",
        Messages.getCsvKey("service.data.csv.SalesAmountCsvSchema.6"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("TOTAL_SHIPPING_CHARGE_TAX",
        Messages.getCsvKey("service.data.csv.SalesAmountCsvSchema.7"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("TOTAL_DISCOUNT_AMOUNT",
        Messages.getCsvKey("service.data.csv.SalesAmountCsvSchema.8"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("TOTAL_REFUND",
        Messages.getCsvKey("service.data.csv.SalesAmountCsvSchema.9"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("TOTAL_RETURN_ITEM_LOSS_MONEY",
        Messages.getCsvKey("service.data.csv.SalesAmountCsvSchema.10"), CsvDataType.NUMBER)); 

    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "SalesAmountExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
