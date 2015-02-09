package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.Tag;
import jp.co.sint.webshop.text.Messages;

public class TagCsvSchema extends CsvSchemaImpl {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public TagCsvSchema() {
    getColumns().add(new CsvColumnImpl("SHOP_CODE",
        Messages.getCsvKey("service.data.csv.TagCsvSchema.0"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("TAG_CODE",
        Messages.getCsvKey("service.data.csv.TagCsvSchema.1"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("TAG_NAME",
        Messages.getCsvKey("service.data.csv.TagCsvSchema.2"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("DISPLAY_ORDER",
        Messages.getCsvKey("service.data.csv.TagCsvSchema.3"), CsvDataType.NUMBER)); 
    getColumns().add(
        new CsvColumnImpl("ORM_ROWID",
            Messages.getCsvKey("service.data.csv.TagCsvSchema.4"),
            CsvDataType.NUMBER, false, true, false,
            getTargetTableName() + "_SEQ"));
    getColumns().add(new CsvColumnImpl("CREATED_USER",
        Messages.getCsvKey("service.data.csv.TagCsvSchema.5"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("CREATED_DATETIME",
        Messages.getCsvKey("service.data.csv.TagCsvSchema.6"), CsvDataType.DATETIME)); 
    getColumns().add(new CsvColumnImpl("UPDATED_USER",
        Messages.getCsvKey("service.data.csv.TagCsvSchema.7"), CsvDataType.STRING)); 
    getColumns().add(new CsvColumnImpl("UPDATED_DATETIME",
        Messages.getCsvKey("service.data.csv.TagCsvSchema.8"), CsvDataType.DATETIME)); 
  }

  public String getExportConfigureId() {
    return "TagExportDataSource";
  }

  public String getImportConfigureId() {
    return "TagImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(Tag.class);
  }

}
