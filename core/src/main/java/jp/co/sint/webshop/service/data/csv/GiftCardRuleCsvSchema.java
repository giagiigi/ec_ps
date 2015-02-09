package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class GiftCardRuleCsvSchema extends CsvSchemaImpl {

  public GiftCardRuleCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("card_code", Messages.getCsvKey("service.data.csv.GiftCardRuleCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("card_name", Messages.getCsvKey("service.data.csv.GiftCardRuleCsvSchema.1"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("effective_years", Messages.getCsvKey("service.data.csv.GiftCardRuleCsvSchema.2"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("weight", Messages.getCsvKey("service.data.csv.GiftCardRuleCsvSchema.3"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("unit_price", Messages.getCsvKey("service.data.csv.GiftCardRuleCsvSchema.4"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("denomination", Messages.getCsvKey("service.data.csv.GiftCardRuleCsvSchema.5"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("card_id", Messages.getCsvKey("service.data.csv.GiftCardRuleCsvSchema.6"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("pass_word", Messages.getCsvKey("service.data.csv.GiftCardRuleCsvSchema.7"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("issue_date", Messages.getCsvKey("service.data.csv.GiftCardRuleCsvSchema.8"), CsvDataType.DATETIME));
    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "GiftCardRuleExportDataSource";
  }

  public String getImportConfigureId() {
    return null;
  }

  public String getTargetTableName() {
    return null;
  }

}
