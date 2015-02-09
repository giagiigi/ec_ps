package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class CustomerAddressCsvSchema extends CsvSchemaImpl {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public CustomerAddressCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns
        .add(new CsvColumnImpl(
            "CUSTOMER_CODE", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.0"),
            CsvDataType.STRING, false, false, true, null));
    columns
        .add(new CsvColumnImpl(
            "ADDRESS_NO", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.1"),
            CsvDataType.NUMBER, false, true, true, "ADDRESS_NO_SEQ"));
    columns.add(new CsvColumnImpl(
        "ADDRESS_ALIAS", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.2"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl(
        "ADDRESS_LAST_NAME", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.3"),
        CsvDataType.STRING));
//  delete by V10-CH 170 start
//    columns.add(new CsvColumnImpl(
//        "ADDRESS_FIRST_NAME", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.4"),
//        CsvDataType.STRING));
//    columns.add(new CsvColumnImpl(
//        "ADDRESS_LAST_NAME_KANA", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.5"),
//        CsvDataType.STRING));
//    columns.add(new CsvColumnImpl(
//        "ADDRESS_FIRST_NAME_KANA", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.6"),
//        CsvDataType.STRING));
//  delete by V10-CH 170 end
    columns.add(new CsvColumnImpl(
        "POSTAL_CODE", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.9"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl(
        "PREFECTURE_CODE", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.10"),
        CsvDataType.STRING));
//  add by V10-CH 170 start
    columns.add(new CsvColumnImpl(
        "CITY_CODE", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.21"),
        CsvDataType.STRING));
//  add by V10-CH 170 end
    columns.add(new CsvColumnImpl(
        "ADDRESS1", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.11"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl(
        "ADDRESS2", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.12"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl(
        "ADDRESS3", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.13"),
        CsvDataType.STRING));
//  delete by V10-CH start
//    columns.add(new CsvColumnImpl(
//        "ADDRESS4", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.14"),
//        CsvDataType.STRING));
//  delete by V10-CH end
    columns.add(new CsvColumnImpl(
        "PHONE_NUMBER", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.15"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl(
        "MOBILE_NUMBER", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.22"),
        CsvDataType.STRING));
    columns
        .add(new CsvColumnImpl(
            "ORM_ROWID", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.16"),
            CsvDataType.NUMBER, false, true, false,
            "CUSTOMER_ADDRESS_SEQ"));
    columns.add(new CsvColumnImpl(
        "CREATED_USER", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.17"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl(
        "CREATED_DATETIME", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.18"),
        CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl(
        "UPDATED_USER", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.19"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl(
        "UPDATED_DATETIME", Messages.getCsvKey("service.data.csv.CustomerAddressCsvSchema.20"),
        CsvDataType.DATETIME));

    setColumns(columns);
  }

  public String getExportConfigureId() {
    return "CustomerAddressExportDataSource";
  }

  public String getImportConfigureId() {
    return "CustomerAddressImportDataSource";
  }

  public String getTargetTableName() {
    return "CUSTOMER_ADDRESS";
  }

}
