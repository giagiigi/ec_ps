package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class NewPublicCouponDetailsCsvSchema extends CsvSchemaImpl {

  public NewPublicCouponDetailsCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("CUSTOMER_CODE",Messages.getCsvKey("service.data.csv.NewPublicCouponDetailsCsvSchema.0"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("DISCOUNT_CODE",Messages.getCsvKey("service.data.csv.NewPublicCouponDetailsCsvSchema.1"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("ORDER_NO",Messages.getCsvKey("service.data.csv.NewPublicCouponDetailsCsvSchema.2"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("MOBILE_COMPUTER_TYPE",Messages.getCsvKey("service.data.csv.NewPublicCouponDetailsCsvSchema.9"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("PAID_PRICE",Messages.getCsvKey("service.data.csv.NewPublicCouponDetailsCsvSchema.3"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("DISCOUNT_PRICE",Messages.getCsvKey("service.data.csv.NewPublicCouponDetailsCsvSchema.4"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("SHIPPING_CHARGE",Messages.getCsvKey("service.data.csv.NewPublicCouponDetailsCsvSchema.5"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("PAYMENT_PRICE",Messages.getCsvKey("service.data.csv.NewPublicCouponDetailsCsvSchema.6"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("LANGUAGE_CODE",Messages.getCsvKey("service.data.csv.NewPublicCouponDetailsCsvSchema.7"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("ADDRESS1",Messages.getCsvKey("service.data.csv.NewPublicCouponDetailsCsvSchema.8"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("use_agent", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.78"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("order_client_type", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.79"), CsvDataType.STRING));

    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "NewPublicCouponDetailsExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
