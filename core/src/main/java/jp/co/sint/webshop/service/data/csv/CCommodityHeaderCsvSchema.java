package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class CCommodityHeaderCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public CCommodityHeaderCsvSchema() {
    
  }

  public String getExportConfigureId() {
    return "CCommodityHeaderExportDataSource";
  }

  public String getImportConfigureId() {
    return "CCommodityHeaderImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

  public List<CsvColumn> getAllSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.0"), CsvDataType.STRING, false, false, true, null));
    columns.add(new CsvColumnImpl("commodity_code",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.1"), CsvDataType.STRING, false, false, true, null));
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
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.26"), CsvDataType.BIGDECIMAL));
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
    columns.add(new CsvColumnImpl("food_security_prd_license_no",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.104"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("food_security_design_code",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.105"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("food_security_factory",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.106"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("food_security_factory_site",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.107"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("food_security_contact",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.108"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("food_security_mix",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.109"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("food_security_plan_storage",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.110"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("food_security_period",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.111"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("food_security_food_additive",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.112"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("food_security_supplier",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.113"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("food_security_product_date_start",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.114"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("food_security_product_date_end",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.115"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("food_security_stock_date_start",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.116"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("food_security_stock_date_end",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.117"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("commodity_type",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.120"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("import_commodity_type",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.125"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("clear_commodity_type",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.126"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("reserve_commodity_type1",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.127"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("reserve_commodity_type2",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.128"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("reserve_commodity_type3",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.129"), CsvDataType.NUMBER));
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
    // 2014/04/25 京东WBS对应 ob_卢 add start
    columns.add(new CsvColumnImpl("advert_content",
        Messages.getCsvKey("service.data.csv.CCommodityHeaderCsvSchema.152"), CsvDataType.STRING));
    // 2014/04/25 京东WBS对应 ob_卢 add end
    return columns;
  }
}
