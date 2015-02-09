package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class SalesAmountByShopDailyCsvSchema extends CsvSchemaImpl {

  public SalesAmountByShopDailyCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("COUNTED_DATE",
        Messages.getCsvKey("service.data.csv.SalesAmountByShopDailyCsvSchema.0"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("TOTAL_SALES_PRICE",
        Messages.getCsvKey("service.data.csv.SalesAmountByShopDailyCsvSchema.1"), CsvDataType.NUMBER)); 
//  delete by V10-CH 170 start
//    columns.add(new CsvColumnImpl("TOTAL_SALES_PRICE_TAX",
//        Messages.getCsvKey("service.data.csv.SalesAmountByShopDailyCsvSchema.2"), CsvDataType.NUMBER)); 
//  delete by V10-CH 170 end
    columns.add(new CsvColumnImpl("TOTAL_GIFT_PRICE",
        Messages.getCsvKey("service.data.csv.SalesAmountByShopDailyCsvSchema.3"), CsvDataType.NUMBER)); 
//  delete by V10-CH 170 start
//    columns.add(new CsvColumnImpl("TOTAL_GIFT_TAX",
//        Messages.getCsvKey("service.data.csv.SalesAmountByShopDailyCsvSchema.4"), CsvDataType.NUMBER)); 
//  delete by V10-CH 170 end
    columns.add(new CsvColumnImpl("TOTAL_SHIPPING_CHARGE",
        Messages.getCsvKey("service.data.csv.SalesAmountByShopDailyCsvSchema.5"), CsvDataType.NUMBER)); 
//  delete by V10-CH 170 start
//    columns.add(new CsvColumnImpl("TOTAL_SHIPPING_CHARGE_TAX",
//        Messages.getCsvKey("service.data.csv.SalesAmountByShopDailyCsvSchema.6"), CsvDataType.NUMBER)); 
//  delete by V10-CH 170 end
    columns.add(new CsvColumnImpl("TOTAL_DISCOUNT_AMOUNT",
        Messages.getCsvKey("service.data.csv.SalesAmountByShopDailyCsvSchema.7"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("TOTAL_REFUND",
        Messages.getCsvKey("service.data.csv.SalesAmountByShopDailyCsvSchema.8"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("TOTAL_RETURN_ITEM_LOSS_MONEY",
        Messages.getCsvKey("service.data.csv.SalesAmountByShopDailyCsvSchema.9"), CsvDataType.NUMBER)); 

    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "SalesAmountByShopDailyExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
