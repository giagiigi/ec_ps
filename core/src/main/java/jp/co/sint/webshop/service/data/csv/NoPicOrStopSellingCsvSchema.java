package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class NoPicOrStopSellingCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public NoPicOrStopSellingCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("COMMODITY_CODE", Messages.getCsvKey("service.data.csv.NoPicOrStopSellingCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_NAME", Messages.getCsvKey("service.data.csv.NoPicOrStopSellingCsvSchema.1"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_TYPE", Messages.getCsvKey("service.data.csv.NoPicOrStopSellingCsvSchema.2"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("CATEGORY_SEARCH_PATH", Messages.getCsvKey("service.data.csv.NoPicOrStopSellingCsvSchema.3"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("STOCK_QUANTITY", Messages.getCsvKey("service.data.csv.NoPicOrStopSellingCsvSchema.4"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("STOCK_THRESHOLD", Messages.getCsvKey("service.data.csv.NoPicOrStopSellingCsvSchema.5"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("SALE_TYPE", Messages.getCsvKey("service.data.csv.NoPicOrStopSellingCsvSchema.6"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("SUPPLIER_CODE", Messages.getCsvKey("service.data.csv.NoPicOrStopSellingCsvSchema.7"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_PIC", Messages.getCsvKey("service.data.csv.NoPicOrStopSellingCsvSchema.8"), CsvDataType.STRING));
    setColumns(columns);
  }

  @Override
  public String getExportConfigureId() {
    return "NoPicOrStopSellingDataSource";
  }

  @Override
  public String getImportConfigureId() {
    return "NoPicOrStopSellingDataSource";
  }

  @Override
  public String getTargetTableName() {
    return null;
  }

}
