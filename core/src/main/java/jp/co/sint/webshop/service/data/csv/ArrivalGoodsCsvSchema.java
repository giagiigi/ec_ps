package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.ArrivalGoods;
import jp.co.sint.webshop.text.Messages;

public class ArrivalGoodsCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public ArrivalGoodsCsvSchema() {
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.ArrivalGoodsCsvSchema.0"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("sku_code",
        Messages.getCsvKey("service.data.csv.ArrivalGoodsCsvSchema.1"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("commodity_name",
        Messages.getCsvKey("service.data.csv.ArrivalGoodsCsvSchema.2"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("email",
        Messages.getCsvKey("service.data.csv.ArrivalGoodsCsvSchema.3"), CsvDataType.STRING));
  }

  public String getExportConfigureId() {
    return "ArrivalGoodsExportDataSource";
  }

  public String getImportConfigureId() {
    return "ArrivalGoodsImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(ArrivalGoods.class);
  }

}
