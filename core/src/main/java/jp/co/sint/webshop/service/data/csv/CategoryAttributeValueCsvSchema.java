package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.CategoryAttributeValue;
import jp.co.sint.webshop.text.Messages;

public class CategoryAttributeValueCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public CategoryAttributeValueCsvSchema() {
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.CategoryAttributeValueCsvSchema.0"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("category_code",
        Messages.getCsvKey("service.data.csv.CategoryAttributeValueCsvSchema.1"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("category_attribute_no",
        Messages.getCsvKey("service.data.csv.CategoryAttributeValueCsvSchema.2"),
        CsvDataType.NUMBER, false, false, true, null));
    getColumns().add(new CsvColumnImpl("commodity_code",
        Messages.getCsvKey("service.data.csv.CategoryAttributeValueCsvSchema.3"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("category_attribute_value",
        Messages.getCsvKey("service.data.csv.CategoryAttributeValueCsvSchema.4"),
        CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("orm_rowid",
            Messages.getCsvKey("service.data.csv.CategoryAttributeValueCsvSchema.5"),
            CsvDataType.NUMBER, false, true, false,
        "CATEGORY_ATTRIBUTE_VALUE_SEQ"));
    getColumns().add(new CsvColumnImpl("created_user",
        Messages.getCsvKey("service.data.csv.CategoryAttributeValueCsvSchema.6"),
        CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("created_datetime",
        Messages.getCsvKey("service.data.csv.CategoryAttributeValueCsvSchema.7"),
        CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("updated_user",
        Messages.getCsvKey("service.data.csv.CategoryAttributeValueCsvSchema.8"),
        CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("updated_datetime",
        Messages.getCsvKey("service.data.csv.CategoryAttributeValueCsvSchema.9"),
        CsvDataType.DATETIME));
  }

  public String getExportConfigureId() {
    return "CategoryAttributeValueExportDataSource";
  }

  public String getImportConfigureId() {
    return "CategoryAttributeValueImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(CategoryAttributeValue.class);
  }

}
