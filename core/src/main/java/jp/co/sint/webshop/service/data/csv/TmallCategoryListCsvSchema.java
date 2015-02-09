package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class TmallCategoryListCsvSchema extends CsvSchemaImpl {

  public TmallCategoryListCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("CATEGORY_CODE", Messages.getCsvKey("service.data.csv.TmallCategoryListCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("CATEGORY_NAME", Messages.getCsvKey("service.data.csv.TmallCategoryListCsvSchema.1"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("PARENT_NAME", Messages.getCsvKey("service.data.csv.TmallCategoryListCsvSchema.2"), CsvDataType.STRING));
    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "TmallCategoryListExportDataSource";
  }

  public String getImportConfigureId() {
    return null;
  }

  public String getTargetTableName() {
    return null;
  }

}
