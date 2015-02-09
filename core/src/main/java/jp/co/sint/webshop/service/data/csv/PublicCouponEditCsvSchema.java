package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.NewCouponRule;

public class PublicCouponEditCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public PublicCouponEditCsvSchema() {
    getColumns().add(new CsvColumnImpl("OBJECT_COMMODITIES", "对象商品集合字段", CsvDataType.STRING));
  }

  public String getExportConfigureId() {
    return "";
  }

  public String getImportConfigureId() {
    return "PublicCouponEditImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(NewCouponRule.class);
  }
}
