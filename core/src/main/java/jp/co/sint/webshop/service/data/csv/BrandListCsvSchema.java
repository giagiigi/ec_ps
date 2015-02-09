package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class BrandListCsvSchema extends CsvSchemaImpl {

  public BrandListCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("BRAND_CODE", Messages.getCsvKey("service.data.csv.BrandListCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("BRAND_NAME", Messages.getCsvKey("service.data.csv.BrandListCsvSchema.1"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("BRAND_ENGLISH_NAME", Messages.getCsvKey("service.data.csv.BrandListCsvSchema.2"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("TMALL_BRAND_CODE", Messages.getCsvKey("service.data.csv.BrandListCsvSchema.3"), CsvDataType.STRING));
    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "BrandListExportDataSource";
  }

  public String getImportConfigureId() {
    return null;
  }

  public String getTargetTableName() {
    return null;
  }

}
