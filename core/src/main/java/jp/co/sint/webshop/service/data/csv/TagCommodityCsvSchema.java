package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.TagCommodity;
import jp.co.sint.webshop.text.Messages;

public class TagCommodityCsvSchema extends CsvSchemaImpl {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public TagCommodityCsvSchema() {
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.TagCommodityCsvSchema.0"),
        CsvDataType.STRING, false, false, true, null)); 
    getColumns().add(new CsvColumnImpl("tag_code",
        Messages.getCsvKey("service.data.csv.TagCommodityCsvSchema.1"),
        CsvDataType.STRING, false, false, true, null)); 
    getColumns().add(new CsvColumnImpl("commodity_code",
        Messages.getCsvKey("service.data.csv.TagCommodityCsvSchema.2"),
        CsvDataType.STRING, false, false, true, null)); 
    getColumns().add(new CsvColumnImpl("orm_rowid",
        Messages.getCsvKey("service.data.csv.TagCommodityCsvSchema.3"),
        CsvDataType.NUMBER, false, true, false,
        "TAG_COMMODITY_SEQ"));
    getColumns().add(new CsvColumnImpl("created_user",
        Messages.getCsvKey("service.data.csv.TagCommodityCsvSchema.4"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("created_datetime",
        Messages.getCsvKey("service.data.csv.TagCommodityCsvSchema.5"), CsvDataType.DATETIME)); 
    getColumns().add(new CsvColumnImpl("updated_user",
        Messages.getCsvKey("service.data.csv.TagCommodityCsvSchema.6"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("updated_datetime",
        Messages.getCsvKey("service.data.csv.TagCommodityCsvSchema.7"), CsvDataType.DATETIME)); 
  }

  public String getExportConfigureId() {
    return "TagCommodityExportDataSource";
  }

  public String getImportConfigureId() {
    return "TagCommodityImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(TagCommodity.class);
  }
}
