package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class CouponStatusAllCsvSchema extends CsvSchemaImpl {

  public CouponStatusAllCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("CUSTOMER_COUPON_ID",
        Messages.getCsvKey("service.data.csv.CouponStatusAllCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COUPON_NAME",
        Messages.getCsvKey("service.data.csv.CouponStatusAllCsvSchema.1"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COUPON_PRICE",
        Messages.getCsvKey("service.data.csv.CouponStatusAllCsvSchema.2"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("ISSUE_DATE",
        Messages.getCsvKey("service.data.csv.CouponStatusAllCsvSchema.3"), CsvDataType.DATE));
    columns.add(new CsvColumnImpl("USE_COUPON_END_DATE",
        Messages.getCsvKey("service.data.csv.CouponStatusAllCsvSchema.4"), CsvDataType.DATE));
    columns.add(new CsvColumnImpl("USE_FLG",
        Messages.getCsvKey("service.data.csv.CouponStatusAllCsvSchema.5"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("CUSTOMER_CODE",
        Messages.getCsvKey("service.data.csv.CouponStatusAllCsvSchema.6"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("CUSTOMER_NAME",
        Messages.getCsvKey("service.data.csv.CouponStatusAllCsvSchema.7"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ORDER_NO",
        Messages.getCsvKey("service.data.csv.CouponStatusAllCsvSchema.8"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("USE_DATE",
        Messages.getCsvKey("service.data.csv.CouponStatusAllCsvSchema.9"), CsvDataType.DATE));
    setColumns(columns);
  }

  /** serial versionUID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "CouponStatusAllExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
