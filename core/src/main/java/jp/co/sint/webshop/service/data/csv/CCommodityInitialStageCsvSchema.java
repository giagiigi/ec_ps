package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.CategoryAttributeValue;


public class CCommodityInitialStageCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public CCommodityInitialStageCsvSchema() {
   
  }

  public String getExportConfigureId() {
    return "CCommodityInitialStageExportDataSource";
  }

  public String getImportConfigureId() {
    return "CCommodityInitialStageImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(CategoryAttributeValue.class);
  }

}
