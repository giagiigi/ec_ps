package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class NewPublicCouponCsvSchema extends CsvSchemaImpl {

  public NewPublicCouponCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("ORDER_DATE",Messages.getCsvKey("service.data.csv.NewPublicCouponCsvSchema.0"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("DISCOUNT_CODE",Messages.getCsvKey("service.data.csv.NewPublicCouponCsvSchema.1"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("TOTAL_CALL",Messages.getCsvKey("service.data.csv.NewPublicCouponCsvSchema.2"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("RETURN_CALL",Messages.getCsvKey("service.data.csv.NewPublicCouponCsvSchema.9"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("ZH_CALL",Messages.getCsvKey("service.data.csv.NewPublicCouponCsvSchema.3"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("JP_CALL",Messages.getCsvKey("service.data.csv.NewPublicCouponCsvSchema.4"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("EN_CALL",Messages.getCsvKey("service.data.csv.NewPublicCouponCsvSchema.5"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("DISCOUNT_PRICE",Messages.getCsvKey("service.data.csv.NewPublicCouponCsvSchema.6"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("PAID_PRICE",Messages.getCsvKey("service.data.csv.NewPublicCouponCsvSchema.7"), CsvDataType.NUMBER)); 
    columns.add(new CsvColumnImpl("SHIPPING_CHARGE",Messages.getCsvKey("service.data.csv.NewPublicCouponCsvSchema.8"), CsvDataType.NUMBER)); 

    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "NewPublicCouponExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
