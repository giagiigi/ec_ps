package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.text.Messages;

public class GiftCsvSchema extends CsvSchemaImpl {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public GiftCsvSchema() {
    getColumns().add(new CsvColumnImpl(
        "SHOP_CODE", Messages.getCsvKey("service.data.csv.GiftCsvSchema.0"),
            CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl(
        "GIFT_CODE", Messages.getCsvKey("service.data.csv.GiftCsvSchema.1"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl(
        "GIFT_NAME", Messages.getCsvKey("service.data.csv.GiftCsvSchema.2"),
        CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl(
        "GIFT_DESCRIPTION", Messages.getCsvKey("service.data.csv.GiftCsvSchema.3"),
        CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl(
        "GIFT_PRICE", Messages.getCsvKey("service.data.csv.GiftCsvSchema.4"),
        CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl(
        "DISPLAY_ORDER", Messages.getCsvKey("service.data.csv.GiftCsvSchema.5"),
        CsvDataType.NUMBER));
//  delete by V10-CH 170 START
//    getColumns().add(new CsvColumnImpl(
//        "GIFT_TAX_TYPE", Messages.getCsvKey("service.data.csv.GiftCsvSchema.6"),
//        CsvDataType.NUMBER));
//  delete by V10-CH 170 END
    getColumns().add(new CsvColumnImpl(
        "DISPLAY_FLG", Messages.getCsvKey("service.data.csv.GiftCsvSchema.7"),
        CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl(
        "ORM_ROWID", Messages.getCsvKey("service.data.csv.GiftCsvSchema.8"),
        CsvDataType.NUMBER, false, true, false, getTargetTableName() + "_SEQ"));
    getColumns().add(new CsvColumnImpl(
        "CREATED_USER", Messages.getCsvKey("service.data.csv.GiftCsvSchema.9"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl(
        "CREATED_DATETIME", Messages.getCsvKey("service.data.csv.GiftCsvSchema.10"),
        CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl(
        "UPDATED_USER", Messages.getCsvKey("service.data.csv.GiftCsvSchema.11"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl(
        "UPDATED_DATETIME", Messages.getCsvKey("service.data.csv.GiftCsvSchema.12"),
        CsvDataType.DATETIME));
  }

  public String getExportConfigureId() {
    return "GiftExportDataSource";
  }

  public String getImportConfigureId() {
    return "GiftImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(Gift.class);
  }

}
