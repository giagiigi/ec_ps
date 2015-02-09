package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class ZsIfItemErpExportCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public ZsIfItemErpExportCsvSchema() {
    getColumns().add(new CsvColumnImpl("SKU_CODE",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.0"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("UM",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.1"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("COMMODITY_NAME_ONE",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.2"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("COMMODITY_NAME_TWO",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.3"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("PRODUCTLINE",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.4"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("CREATED_DATETIME",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.5"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("ITEMTYPE",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.6"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("USE_FLG",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.7"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("ITEMGROUP",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.8"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("DRAWING",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.9"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("ABCCLASS",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.10"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("LOTSERIALCONTROL",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.11"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("SITE",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.12"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("LOCATION",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.13"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("SHELFLIFT",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.14"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("PLANORDER",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.15"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("STOCK_THRESHOLD",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.16"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("STOCK_WARNING",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.17"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("BUYER_CODE",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.18"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("SUPPLIER_CODE",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.19"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("PURCHASELT",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.20"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("INSPECTIONREQUIRED",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.21"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("INSPECTLT",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.22"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("MINIMUM_ORDER",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.23"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("MAXIMUM_ORDER",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.24"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("ORDER_MULTIPLE",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.25"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("EMTTYPE",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.26"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("AUTOMATICEMTPROCESSING",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.27"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("WEIGHT",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.28"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("NETWEIGHT",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.29"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("UNIT_PRICE",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.30"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("TAX",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.31"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("TAXCLASS",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.32"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("LENGTH",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.33"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("WIDE",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.34"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("HIGH",
        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.35"), CsvDataType.STRING));
//    getColumns().add(new CsvColumnImpl("INNER_QUANTITY",
//        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.36"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("IN_BOUND_LIFE_DAYS",
//        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.37"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("OUT_BOUND_LIFE_DAYS",
//        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.38"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("SHELF_LIFE_DAYS",
//        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.39"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("SHELF_LIFE_ALERT_DAYS",
//        Messages.getCsvKey("service.data.csv.ZsIfItemErpExportCsvSchema.40"), CsvDataType.NUMBER));
   
  }

  public String getExportConfigureId() {
    return "ZsIfItemErpExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
