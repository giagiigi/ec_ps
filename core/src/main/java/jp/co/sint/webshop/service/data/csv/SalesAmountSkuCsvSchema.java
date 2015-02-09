package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class SalesAmountSkuCsvSchema extends CsvSchemaImpl {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public SalesAmountSkuCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("SHOP_CODE",
        Messages.getCsvKey("service.data.csv.SalesAmountSkuCsvSchema.0"), CsvDataType.STRING)); //$NON-NLS-2$
    columns.add(new CsvColumnImpl("SHOP_NAME",
        Messages.getCsvKey("service.data.csv.SalesAmountSkuCsvSchema.1"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("COMMODITY_CODE",
        Messages.getCsvKey("service.data.csv.SalesAmountSkuCsvSchema.2"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("SKU_CODE",
        Messages.getCsvKey("service.data.csv.SalesAmountSkuCsvSchema.3"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("COMMODITY_SKU_NAME",
        Messages.getCsvKey("service.data.csv.SalesAmountSkuCsvSchema.4"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("TOTAL_SALES_PRICE",
        Messages.getCsvKey("service.data.csv.SalesAmountSkuCsvSchema.5"), CsvDataType.NUMBER)); 
//  delete by V10-CH 170 start
//    columns.add(new CsvColumnImpl("TOTAL_SALES_PRICE_TAX",
//        Messages.getCsvKey("service.data.csv.SalesAmountSkuCsvSchema.6"), CsvDataType.NUMBER)); 
//  delete by V10-CH 170 end
    columns.add(new CsvColumnImpl("TOTAL_GIFT_PRICE",
        Messages.getCsvKey("service.data.csv.SalesAmountSkuCsvSchema.7"), CsvDataType.NUMBER)); 
//  delete by V10-CH 170 start
//    columns.add(new CsvColumnImpl("TOTAL_GIFT_TAX",
//        Messages.getCsvKey("service.data.csv.SalesAmountSkuCsvSchema.8"), CsvDataType.NUMBER)); 
//  delete by V10-CH 170 end
    columns.add(new CsvColumnImpl("TOTAL_ORDER_QUANTITY",
        Messages.getCsvKey("service.data.csv.SalesAmountSkuCsvSchema.9"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("TOTAL_RETURN_ITEM_QUANTITY",
        Messages.getCsvKey("service.data.csv.SalesAmountSkuCsvSchema.10"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("TOTAL_DISCOUNT_AMOUNT",
        Messages.getCsvKey("service.data.csv.SalesAmountSkuCsvSchema.11"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("TOTAL_REFUND",
        Messages.getCsvKey("service.data.csv.SalesAmountSkuCsvSchema.12"), CsvDataType.NUMBER)); 
    setColumns(columns);
  }

  public String getExportConfigureId() {
    return "SalesAmountSkuExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
