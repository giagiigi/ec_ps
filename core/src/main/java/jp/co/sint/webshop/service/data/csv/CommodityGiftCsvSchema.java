package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.text.Messages;


public class CommodityGiftCsvSchema extends CsvSchemaImpl  {
  
  private static final long serialVersionUID = 1L;

  public CommodityGiftCsvSchema() {
    getColumns().add(new CsvColumnImpl("commodity_code",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("commodity_name",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.1"),  CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("commodity_name_en",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.2"),  CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("commodity_name_jp",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.3"),  CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("supplier_code",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.4"),  CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("buyer_code",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.5"),  CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("in_bound_life_days",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.6"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("out_bound_life_days",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.7"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("shelf_life_alert_days",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.8"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("weight",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.9"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("stock_warning",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.10"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("lead_time",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.11"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("minimum_order",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.12"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("maximum_order",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.13"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("order_multiple",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.14"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("shelf_life_flag",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.15"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("shelf_life_days",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.16"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("commodity_type",
        Messages.getCsvKey("service.data.csv.CommodityGiftCsvSchema.17"), CsvDataType.NUMBER));
  }

  @Override
  public String getExportConfigureId() {
    return null;
  }

  @Override
  public String getImportConfigureId() {
    return "CommodityGiftImportDataSource";
  }

  @Override
  public String getTargetTableName() {
    return DatabaseUtil.getTableName(CommodityHeader.class);
  }

}
