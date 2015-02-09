package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.text.Messages;

public class CommodityDetailCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public CommodityDetailCsvSchema() {
    getColumns()
        .add(
            new CsvColumnImpl(
                "shop_code", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.0"),
                CsvDataType.STRING, false, false, true, null));
    getColumns()
        .add(
            new CsvColumnImpl(
                "sku_code", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.1"),
                CsvDataType.STRING, false, false, true, null));
    getColumns().add(
        new CsvColumnImpl("commodity_code", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.2"),
            CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("unit_price", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.3"), CsvDataType.BIGDECIMAL));
    getColumns().add(
        new CsvColumnImpl("discount_price", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.4"),
            CsvDataType.BIGDECIMAL));
    getColumns().add(
        new CsvColumnImpl("reservation_price", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.5"),
            CsvDataType.BIGDECIMAL));
    getColumns().add(
        new CsvColumnImpl("change_unit_price", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.6"),
            CsvDataType.BIGDECIMAL));
    getColumns().add(
        new CsvColumnImpl("jan_code", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.7"),
            CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl("display_order", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.8"),
            CsvDataType.NUMBER));
    getColumns().add(
        new CsvColumnImpl(
            "standard_detail1_name", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.9"),
            CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl(
            "standard_detail2_name", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.10"),
            CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl(
            "reservation_limit", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.11"),
            CsvDataType.NUMBER));
    getColumns().add(
        new CsvColumnImpl(
            "oneshot_reservation_limit", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.12"),
            CsvDataType.NUMBER));
    getColumns()
        .add(
            new CsvColumnImpl(
                "stock_threshold", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.13"),
                CsvDataType.NUMBER));
    getColumns()
        .add(
            new CsvColumnImpl(
                "orm_rowid", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.15"),
                CsvDataType.NUMBER, false, true, false,
                getTargetTableName() + "_SEQ"));
    getColumns().add(
        new CsvColumnImpl("created_user", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.16"),
            CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl(
            "created_datetime", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.17"),
            CsvDataType.DATETIME));
    getColumns().add(
        new CsvColumnImpl("updated_user", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.18"),
            CsvDataType.STRING));
    getColumns().add(
        new CsvColumnImpl(
            "updated_datetime", Messages.getCsvKey("service.data.csv.CommodityDetailCsvSchema.19"),
            CsvDataType.DATETIME));

  }

  public String getExportConfigureId() {
    return "CommodityDetailExportDataSource";
  }

  public String getImportConfigureId() {
    return "CommodityDetailImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(CommodityDetail.class);
  }

}
