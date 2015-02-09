package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class TmallBrandListCsvSchema extends CsvSchemaImpl {

  public TmallBrandListCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("TMALL_BRAND_CODE", Messages.getCsvKey("service.data.csv.TmallBrandListCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("TMALL_BRAND_NAME", Messages.getCsvKey("service.data.csv.TmallBrandListCsvSchema.1"), CsvDataType.STRING));
    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "TmallBrandListExportDataSource";
  }

  public String getImportConfigureId() {
    return null;
  }

  public String getTargetTableName() {
    return null;
  }

}
