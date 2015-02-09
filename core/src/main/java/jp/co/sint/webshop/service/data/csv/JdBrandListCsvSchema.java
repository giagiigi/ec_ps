
package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;
/**
 * 2014/04/25 京东WBS对应 ob_卢 add 
 */
public class JdBrandListCsvSchema extends CsvSchemaImpl {

  public JdBrandListCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("JD_BRAND_CODE", Messages.getCsvKey("service.data.csv.JdBrandListCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("JD_BRAND_NAME", Messages.getCsvKey("service.data.csv.JdBrandListCsvSchema.1"), CsvDataType.STRING));
    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "JdBrandListExportDataSource";
  }

  public String getImportConfigureId() {
    return null;
  }

  public String getTargetTableName() {
    return null;
  }

}
