package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class PointStatusAllCsvSchema extends CsvSchemaImpl {

  public PointStatusAllCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("SHOP_CODE",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("SHOP_NAME",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.1"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("CUSTOMER_CODE",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.2"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("CUSTOMER_NAME",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.3"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("POINT_ISSUE_TYPE",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.4"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ISSUED_POINT",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.5"),
        CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("POINT_ISSUE_STATUS",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.6"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("ORDER_NO",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.7"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ORDER_DATETIME",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.8"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("PAYMENT_DATE",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.9"), CsvDataType.DATE));
    columns.add(new CsvColumnImpl("REVIEW_ID",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.10"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ENQUETE_CODE",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.11"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("DESCRIPTION",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.12"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("POINT_ISSUE_DATETIME",
        Messages.getCsvKey("service.data.csv.PointStatusAllCsvSchema.13"), CsvDataType.DATETIME));
    setColumns(columns);
  }

  /** serial versionUID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "PointStatusAllExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
