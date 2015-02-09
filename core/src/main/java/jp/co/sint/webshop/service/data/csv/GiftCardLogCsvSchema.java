package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;


public class GiftCardLogCsvSchema extends CsvSchemaImpl{

  
  public GiftCardLogCsvSchema() {
  
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("CARD_CODE", Messages.getCsvKey("service.data.csv.GiftCardLogCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("CARD_NAME", Messages.getCsvKey("service.data.csv.GiftCardLogCsvSchema.1"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ISSUE_TIME", Messages.getCsvKey("service.data.csv.GiftCardLogCsvSchema.2"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("ISSUE_NUM", Messages.getCsvKey("service.data.csv.GiftCardLogCsvSchema.3"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("TOTAL_SALE_PRICE", Messages.getCsvKey("service.data.csv.GiftCardLogCsvSchema.4"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("TOTAL_DENOMINATION", Messages.getCsvKey("service.data.csv.GiftCardLogCsvSchema.5"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("ACTIVATE_AMOUNT", Messages.getCsvKey("service.data.csv.GiftCardLogCsvSchema.6"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("UNACT_AMOUNT", Messages.getCsvKey("service.data.csv.GiftCardLogCsvSchema.7"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("USE_AMOUNT", Messages.getCsvKey("service.data.csv.GiftCardLogCsvSchema.8"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("LEFT_AMOUNT", Messages.getCsvKey("service.data.csv.GiftCardLogCsvSchema.9"), CsvDataType.BIGDECIMAL));
    setColumns(columns);
  }
  /** serial version UID */
  private static final long serialVersionUID = 1L;
  
  @Override
  public String getExportConfigureId() {
    return "GiftCardLogExportDateSource";
  }

  @Override
  public String getImportConfigureId() {
    return null;
  }

  @Override
  public String getTargetTableName() {
    return null;
  }

}
