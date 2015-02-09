package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.text.Messages;

public class CommodityHeaderCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public CommodityHeaderCsvSchema() {
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("commodity_code",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.1"), CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("commodity_name",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.2"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("represent_sku_code",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.3"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("stock_status_no",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.4"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("stock_management_type",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.5"), CsvDataType.NUMBER));
//  delete by V10-CH 170 start
//    getColumns().add(new CsvColumnImpl("commodity_tax_type",
//        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.6"), CsvDataType.NUMBER));
//  delete by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("commodity_description_pc",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.7"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("commodity_description_mobile",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.8"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("commodity_search_words",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.9"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("shadow_search_words",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.10"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("sale_start_datetime",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.11"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("sale_end_datetime",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.12"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("change_unit_price_datetime",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.13"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("discount_price_start_datetime",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.14"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("discount_price_end_datetime",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.15"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("reservation_start_datetime",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.16"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("reservation_end_datetime",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.17"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("delivery_type_no",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.18"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("link_url",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.19"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("recommend_commodity_rank",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.20"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("commodity_standard1_name",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.21"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("commodity_standard2_name",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.22"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("commodity_point_rate",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.23"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("commodity_point_start_datetime",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.24"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("commodity_point_end_datetime",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.25"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("sale_flg",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.26"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("display_client_type",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.27"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("arrival_goods_flg",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.28"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("unit_price",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.29"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("discount_price",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.30"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("reservation_price",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.31"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("change_unit_price",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.32"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("jan_code",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.33"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("reservation_limit",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.34"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("oneshot_reservation_limit",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.35"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("stock_threshold",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.36"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("orm_rowid",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.38"), CsvDataType.NUMBER, 
                false, true, false, getTargetTableName() + "_SEQ"));
    getColumns().add(new CsvColumnImpl("created_user",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.39"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("created_datetime",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.40"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("updated_user",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.41"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("updated_datetime",
        Messages.getCsvKey("service.data.csv.CommodityHeaderCsvSchema.42"), CsvDataType.DATETIME));
  }

  public String getExportConfigureId() {
    return "CommodityHeaderExportDataSource";
  }

  public String getImportConfigureId() {
    return "CommodityHeaderImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(CommodityHeader.class);
  }

}
