package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.GiftCommodity;
import jp.co.sint.webshop.text.Messages;

public class GiftCommodityCsvSchema extends CsvSchemaImpl {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public GiftCommodityCsvSchema() {
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.GiftCommodityCsvSchema.0"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("gift_code",
        Messages.getCsvKey("service.data.csv.GiftCommodityCsvSchema.1"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("commodity_code",
        Messages.getCsvKey("service.data.csv.GiftCommodityCsvSchema.2"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("orm_rowid",
        Messages.getCsvKey("service.data.csv.GiftCommodityCsvSchema.3"),
        CsvDataType.NUMBER, false, true, false, "GIFT_COMMODITY_SEQ"));
    getColumns().add(new CsvColumnImpl("created_user",
        Messages.getCsvKey("service.data.csv.GiftCommodityCsvSchema.4"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("created_datetime",
        Messages.getCsvKey("service.data.csv.GiftCommodityCsvSchema.5"),
        CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("updated_user",
        Messages.getCsvKey("service.data.csv.GiftCommodityCsvSchema.6"),
        CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("updated_datetime",
        Messages.getCsvKey("service.data.csv.GiftCommodityCsvSchema.7"),
        CsvDataType.DATETIME));

  }

  public String getExportConfigureId() {
    return "GiftCommodityExportDataSource";
  }

  public String getImportConfigureId() {
    return "GiftCommodityImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(GiftCommodity.class);
  }

}
