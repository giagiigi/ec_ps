package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class NearCommodityWarnCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public NearCommodityWarnCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("COMMODITY_CODE", Messages.getCsvKey("service.data.csv.NearCommodityWarnCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_NAME", Messages.getCsvKey("service.data.csv.NearCommodityWarnCsvSchema.1"), CsvDataType.STRING));
    setColumns(columns);
  }

  @Override
  public String getExportConfigureId() {
    return "NearCommodityWarnDataSource";
  }

  @Override
  public String getImportConfigureId() {
    return "";
  }

  @Override
  public String getTargetTableName() {
    return null;
  }

}
