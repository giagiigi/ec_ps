package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class SalesAmountByShopMonthlyCsvSchema extends CsvSchemaImpl {

  public SalesAmountByShopMonthlyCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("COUNTED_DATE", Messages.getCsvKey("service.data.csv.SalesAmountByShopMonthlyCsvSchema.0"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("TOTAL_SALES_PRICE", Messages.getCsvKey("service.data.csv.SalesAmountByShopMonthlyCsvSchema.1"), CsvDataType.NUMBER));
    // delete by V10-CH 170 start
    // columns.add(new CsvColumnImpl("TOTAL_SALES_PRICE_TAX", "商品消費税額累計",
    // CsvDataType.NUMBER));
    // delete by V10-CH 170 end
    columns.add(new CsvColumnImpl("TOTAL_GIFT_PRICE", Messages.getCsvKey("service.data.csv.SalesAmountByShopMonthlyCsvSchema.3"), CsvDataType.NUMBER));
    // delete by V10-CH 170 start
    // columns.add(new CsvColumnImpl("TOTAL_GIFT_TAX", "ギフト消費税額累計",
    // CsvDataType.NUMBER));
    // delete by V10-CH 170 start
    columns.add(new CsvColumnImpl("TOTAL_SHIPPING_CHARGE", Messages.getCsvKey("service.data.csv.SalesAmountByShopMonthlyCsvSchema.5"), CsvDataType.NUMBER));
    // delete by V10-CH 170 start
    // columns.add(new CsvColumnImpl("TOTAL_SHIPPING_CHARGE_TAX", "送料消費税累計",
    // CsvDataType.NUMBER));
    // delete by V10-CH 170 start
    columns.add(new CsvColumnImpl("TOTAL_DISCOUNT_AMOUNT", Messages.getCsvKey("service.data.csv.SalesAmountByShopMonthlyCsvSchema.7"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("TOTAL_REFUND", Messages.getCsvKey("service.data.csv.SalesAmountByShopMonthlyCsvSchema.8"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("TOTAL_RETURN_ITEM_LOSS_MONEY", Messages.getCsvKey("service.data.csv.SalesAmountByShopMonthlyCsvSchema.9"), CsvDataType.NUMBER));

    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "SalesAmountByShopMonthlyExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
