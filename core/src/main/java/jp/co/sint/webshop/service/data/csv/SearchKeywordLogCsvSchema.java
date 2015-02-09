package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class SearchKeywordLogCsvSchema extends CsvSchemaImpl {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public SearchKeywordLogCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("SEARCH_KEY",
        Messages.getCsvKey("service.data.csv.SearchKeywordLogCsvSchema.0"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("SEARCH_WORD",
        Messages.getCsvKey("service.data.csv.SearchKeywordLogCsvSchema.1"), CsvDataType.STRING)); 
    columns.add(new CsvColumnImpl("SEARCH_COUNT",
        Messages.getCsvKey("service.data.csv.SearchKeywordLogCsvSchema.2"), CsvDataType.STRING)); 
    setColumns(columns);
  }

  public String getExportConfigureId() {
    return "SearchKeywordLogExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
