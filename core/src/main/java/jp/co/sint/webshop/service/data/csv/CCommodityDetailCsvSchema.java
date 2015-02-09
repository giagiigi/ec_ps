package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class CCommodityDetailCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public CCommodityDetailCsvSchema() {
    

  }

  public String getExportConfigureId() {
    return "CCommodityDetailExportDataSource";
  }

  public String getImportConfigureId() {
    return "CCommodityDetailImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

  public List<CsvColumn> getAllSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
    columns.add(new CsvColumnImpl("sku_code",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
    columns.add(new CsvColumnImpl("sku_name",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.1"), CsvDataType.STRING, false, false, true, null));
    columns.add(new CsvColumnImpl("commodity_code",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.2"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("purchase_price",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.3"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("unit_price",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.4"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("discount_price",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.5"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("display_order",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.6"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("standard_detail1_id",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.7"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("standard_detail1_name",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.8"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("standard_detail2_id",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.9"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("standard_detail2_name",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.10"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("weight",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.11"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("use_flg",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.12"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("minimum_order",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.13"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("maximum_order",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.14"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("order_multiple",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.15"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("stock_warning",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.16"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("tmall_sku_id",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.17"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("tmall_unit_price",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.18"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("tmall_discount_price",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.19"), CsvDataType.NUMBER));
    return columns;
  }
}
