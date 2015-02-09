package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.text.Messages;

public class CategoryCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public CategoryCsvSchema() {
    getColumns().add(new CsvColumnImpl("category_code",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("category_name_pc",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.1"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("category_name_mobile",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.2"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("parent_category_code",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.3"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("path",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.4"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("depth",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.5"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("display_order",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.6"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("commodity_count_pc",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.7"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("commodity_count_mobile",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.8"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("orm_rowid",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.9"), CsvDataType.NUMBER, false, true, false,
        "CATEGORY_SEQ"));
    getColumns().add(new CsvColumnImpl("created_user",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.10"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("created_datetime",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.11"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("updated_user",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.12"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("updated_datetime",
        Messages.getCsvKey("service.data.csv.CategoryCsvSchema.13"), CsvDataType.DATETIME));

  }

  public String getExportConfigureId() {
    return "CategoryExportDataSource";
  }

  public String getImportConfigureId() {
    return "CategoryImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(Category.class);
  }
}
