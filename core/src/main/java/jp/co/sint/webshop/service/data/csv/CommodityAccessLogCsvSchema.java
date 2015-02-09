package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class CommodityAccessLogCsvSchema extends CsvSchemaImpl {

  /** serial versionUID */
  private static final long serialVersionUID = 1L;

  public CommodityAccessLogCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("SHOP_CODE",
        Messages.getCsvKey("service.data.csv.CommodityAccessLogCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("SHOP_NAME",
        Messages.getCsvKey("service.data.csv.CommodityAccessLogCsvSchema.1"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_CODE",
        Messages.getCsvKey("service.data.csv.CommodityAccessLogCsvSchema.2"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_NAME",
        Messages.getCsvKey("service.data.csv.CommodityAccessLogCsvSchema.3"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ACCESS_COUNT",
        Messages.getCsvKey("service.data.csv.CommodityAccessLogCsvSchema.4"), CsvDataType.NUMBER));
    setColumns(columns);
  }

  public String getExportConfigureId() {
    return "CommodityAccessLogExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
