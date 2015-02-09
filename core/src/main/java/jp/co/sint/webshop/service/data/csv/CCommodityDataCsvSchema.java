package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class CCommodityDataCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public CCommodityDataCsvSchema() {
    
  }

  public String getExportConfigureId() {
    return "CCommodityDataExportDataSource";
  }

  public String getImportConfigureId() {
    return "CCommodityDataImportDataSource";
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
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.11"), CsvDataType.BIGDECIMAL));
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
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.18"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("tmall_discount_price",
        Messages.getCsvKey("service.data.csv.CCommodityDetailCsvSchema.19"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("commodity_name",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.2"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("commodity_name_en",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.3"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("represent_sku_code",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.4"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("represent_sku_unit_price",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.5"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl("commodity_description_pc",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.6"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("commodity_description_mobile",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.7"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("commodity_search_words",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.8"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("sale_start_datetime",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.9"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("sale_end_datetime",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.10"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("discount_price_start_datetime",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.11"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("discount_price_end_datetime",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.12"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("standard1_id",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.13"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("standard1_name",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.14"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("standard2_id",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.15"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("standard2_name",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.16"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("sale_flg",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.17"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("return_flg",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.18"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("display_client_type",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.19"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("warning_flag",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.20"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("lead_time",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.21"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("sale_flag",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.22"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("spec_flag",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.23"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("brand_code",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.24"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("t_commodity_id",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.25"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("t_represent_sku_price",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.26"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("t_category_id",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.27"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("supplier_code",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.28"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("buyer_code",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.29"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("tax_code",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.30"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("original_place",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.31"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ingredient",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.32"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("material",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.33"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("big_flag",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.34"), CsvDataType.NUMBER));
    // 20130809 txw add start
    columns.add(new CsvColumnImpl("title",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.138"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("title_en",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.139"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("title_jp",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.140"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("description",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.141"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("description_en",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.142"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("description_jp",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.143"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("keyword",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.144"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("keyword_en",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.145"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("keyword_jp",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.146"), CsvDataType.STRING));
    // 20130809 txw add end
    return columns;
  }
}
