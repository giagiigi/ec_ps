package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.text.Messages;

public class CategoryCommodityCsvSchema extends CsvSchemaImpl {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public CategoryCommodityCsvSchema() {
    getColumns().add(new CsvColumnImpl("SHOP_CODE",
        Messages.getCsvKey("service.data.csv.CategoryCommodityCsvSchema.0"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("CATEGORY_CODE",
        Messages.getCsvKey("service.data.csv.CategoryCommodityCsvSchema.1"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("COMMODITY_CODE",
        Messages.getCsvKey("service.data.csv.CategoryCommodityCsvSchema.2"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("CATEGORY_SEARCH_PATH",
        Messages.getCsvKey("service.data.csv.CategoryCommodityCsvSchema.3"),
        CsvDataType.STRING, false, false, false, null));
    getColumns().add(
        new CsvColumnImpl("ORM_ROWID",
            Messages.getCsvKey("service.data.csv.CategoryCommodityCsvSchema.4"),
            CsvDataType.NUMBER, false, true, false, getTargetTableName() + "_SEQ"));
    getColumns().add(new CsvColumnImpl("CREATED_USER",
        Messages.getCsvKey("service.data.csv.CategoryCommodityCsvSchema.5"),
        CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("CREATED_DATETIME",
        Messages.getCsvKey("service.data.csv.CategoryCommodityCsvSchema.6"),
        CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("UPDATED_USER",
        Messages.getCsvKey("service.data.csv.CategoryCommodityCsvSchema.7"),
        CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("UPDATED_DATETIME",
        Messages.getCsvKey("service.data.csv.CategoryCommodityCsvSchema.8"),
        CsvDataType.DATETIME));
  }

  public String getExportConfigureId() {
    return "CategoryCommodityExportDataSource";
  }

  public String getImportConfigureId() {
    return "CategoryCommodityImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(CategoryCommodity.class);
  }

}
