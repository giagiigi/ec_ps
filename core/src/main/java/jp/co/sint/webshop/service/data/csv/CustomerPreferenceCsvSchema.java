package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class CustomerPreferenceCsvSchema extends CsvSchemaImpl {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public CustomerPreferenceCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("SHOP_CODE", Messages.getCsvKey("service.data.csv.CustomerPreferenceCsvSchema.0"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("SHOP_NAME", Messages.getCsvKey("service.data.csv.CustomerPreferenceCsvSchema.1"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_CODE", Messages.getCsvKey("service.data.csv.CustomerPreferenceCsvSchema.2"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_NAME", Messages.getCsvKey("service.data.csv.CustomerPreferenceCsvSchema.3"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("TOTAL_CUSTOMER_COUNT", Messages.getCsvKey("service.data.csv.CustomerPreferenceCsvSchema.4"),
        CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("TOTAL_ORDER_COUNT", Messages.getCsvKey("service.data.csv.CustomerPreferenceCsvSchema.5"),
        CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("PURCHASING_AMOUNT", Messages.getCsvKey("service.data.csv.CustomerPreferenceCsvSchema.6"),
        CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("TOTAL_ORDER_COUNT_RATIO", Messages.getCsvKey("service.data.csv.CustomerPreferenceCsvSchema.7"),
        CsvDataType.NUMBER));

    setColumns(columns);
  }

  public String getExportConfigureId() {
    return "CustomerPreferenceExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
