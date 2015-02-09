package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;


public class GiftCardUseLogCsvSchema  extends CsvSchemaImpl{

  public GiftCardUseLogCsvSchema() {
    
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("CUSTOMER_CODE", Messages.getCsvKey("service.data.csv.GiftCardUseLogCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("CARD_ID", Messages.getCsvKey("service.data.csv.GiftCardUseLogCsvSchema.1"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("RECHARGE_DATE", Messages.getCsvKey("service.data.csv.GiftCardUseLogCsvSchema.2"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("UNIT_PRICE", Messages.getCsvKey("service.data.csv.GiftCardUseLogCsvSchema.3"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("DENOMINATION", Messages.getCsvKey("service.data.csv.GiftCardUseLogCsvSchema.4"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("DISCOUNT_RATE", Messages.getCsvKey("service.data.csv.GiftCardUseLogCsvSchema.5"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("USE_AMOUNT", Messages.getCsvKey("service.data.csv.GiftCardUseLogCsvSchema.6"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("LEFT_AMOUNT", Messages.getCsvKey("service.data.csv.GiftCardUseLogCsvSchema.7"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("LINK_ORDER", Messages.getCsvKey("service.data.csv.GiftCardUseLogCsvSchema.8"), CsvDataType.STRING));
    
    setColumns(columns);
  }
  
  /** serial version UID */
  private static final long serialVersionUID = 1L;
  
  @Override
  public String getExportConfigureId() {
    return "GiftCardUseLogExportDateSource";
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
