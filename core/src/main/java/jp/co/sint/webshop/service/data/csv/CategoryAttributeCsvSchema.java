package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.CategoryAttribute;
import jp.co.sint.webshop.text.Messages;

public class CategoryAttributeCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public CategoryAttributeCsvSchema() {
    getColumns().add(new CsvColumnImpl("category_code", Messages.getCsvKey("service.data.csv.CategoryAttributeCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("category_attribute_no", Messages.getCsvKey("service.data.csv.CategoryAttributeCsvSchema.1"), CsvDataType.NUMBER, false, false, true, null));
    getColumns().add(new CsvColumnImpl("category_attribute_name", Messages.getCsvKey("service.data.csv.CategoryAttributeCsvSchema.2"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("orm_rowid", Messages.getCsvKey("service.data.csv.CategoryAttributeCsvSchema.3"), CsvDataType.NUMBER, false, true, false, "CATEGORY_ATTRIBUTE_SEQ"));
    getColumns().add(new CsvColumnImpl("created_user", Messages.getCsvKey("service.data.csv.CategoryAttributeCsvSchema.4"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("created_datetime", Messages.getCsvKey("service.data.csv.CategoryAttributeCsvSchema.5"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("updated_user", Messages.getCsvKey("service.data.csv.CategoryAttributeCsvSchema.6"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("updated_datetime", Messages.getCsvKey("service.data.csv.CategoryAttributeCsvSchema.8"), CsvDataType.DATETIME));
  }

  public String getExportConfigureId() {
    return "CategoryAttributeExportDataSource";
  }

  public String getImportConfigureId() {
    return "CategoryAttributeImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(CategoryAttribute.class);
  }

}
