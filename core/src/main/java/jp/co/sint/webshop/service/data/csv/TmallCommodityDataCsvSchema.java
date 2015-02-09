package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class TmallCommodityDataCsvSchema extends CsvSchemaImpl {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public TmallCommodityDataCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("SHOP_CODE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_CODE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.1"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_NAME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.2"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("REPRESENT_SKU_CODE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.3"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("STOCK_STATUS_NO", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.4"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("STOCK_MANAGEMENT_TYPE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.5"), CsvDataType.NUMBER));
//  delete by V10-CH 170 start
//    columns.add(new CsvColumnImpl("COMMODITY_TAX_TYPE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.6"), CsvDataType.NUMBER));
//  delete by V10-CH 170 end
    columns.add(new CsvColumnImpl("COMMODITY_DESCRIPTION_PC", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.7"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_DESCRIPTION_MOBILE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.8"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_SEARCH_WORDS", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.9"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("SALE_START_DATETIME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.10"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("SALE_END_DATETIME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.11"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("CHANGE_UNIT_PRICE_DATETIME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.12"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("DISCOUNT_PRICE_START_DATETIME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.13"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("DISCOUNT_PRICE_END_DATETIME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.14"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("RESERVATION_START_DATETIME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.15"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("RESERVATION_END_DATETIME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.16"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("DELIVERY_TYPE_NO", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.17"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("LINK_URL", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.18"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("RECOMMEND_COMMODITY_RANK", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.19"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("COMMODITY_STANDARD1_NAME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.20"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_STANDARD2_NAME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.21"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_POINT_RATE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.22"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("COMMODITY_POINT_START_DATETIME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.23"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("COMMODITY_POINT_END_DATETIME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.24"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("SALE_FLG", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.25"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("DISPLAY_CLIENT_TYPE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.26"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("ARRIVAL_GOODS_FLG", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.27"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("SKU_CODE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.28"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("UNIT_PRICE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.29"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("DISCOUNT_PRICE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.30"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("RESERVATION_PRICE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.31"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("CHANGE_UNIT_PRICE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.32"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("JAN_CODE", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.33"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("DISPLAY_ORDER", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.34"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("STANDARD_DETAIL1_NAME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.35"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("STANDARD_DETAIL2_NAME", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.36"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("STOCK_QUANTITY", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.37"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("ALLOCATED_QUANTITY", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.38"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("RESERVED_QUANTITY", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.39"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("RESERVATION_LIMIT", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.40"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("ONESHOT_RESERVATION_LIMIT", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.41"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("STOCK_THRESHOLD", Messages.getCsvKey("service.data.csv.CommodityDataCsvSchema.42"), CsvDataType.NUMBER));
    setColumns(columns);
  }

  public String getExportConfigureId() {
    return "CommodityDataExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
