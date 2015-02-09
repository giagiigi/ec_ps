package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class CustomerCsvSchema extends CsvSchemaImpl {

  public CustomerCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("CUSTOMER_CODE", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.0"),
        CsvDataType.STRING, false, true, true, "CUSTOMER_CODE_SEQ"));
    columns.add(new CsvColumnImpl("CUSTOMER_GROUP_CODE",
        Messages.getCsvKey("service.data.csv.CustomerCsvSchema.1"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("LAST_NAME", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.2"), CsvDataType.STRING));
//  delete by V10-CH 170 start
//    columns.add(new CsvColumnImpl("FIRST_NAME", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.3"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl("LAST_NAME_KANA",
//        Messages.getCsvKey("service.data.csv.CustomerCsvSchema.4"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl("FIRST_NAME_KANA",
//        Messages.getCsvKey("service.data.csv.CustomerCsvSchema.5"), CsvDataType.STRING));
//  delete by V10-CH 170 end
    columns.add(new CsvColumnImpl("LOGIN_ID", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.6"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("EMAIL", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.7"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("PASSWORD", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.8"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("BIRTH_DATE", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.9"), CsvDataType.DATE));
    columns.add(new CsvColumnImpl("SEX", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.10"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("REQUEST_MAIL_TYPE",
        Messages.getCsvKey("service.data.csv.CustomerCsvSchema.11"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("CLIENT_MAIL_TYPE",
        Messages.getCsvKey("service.data.csv.CustomerCsvSchema.12"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("CAUTION", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.14"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("LOGIN_DATETIME",
        Messages.getCsvKey("service.data.csv.CustomerCsvSchema.15"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("LOGIN_ERROR_COUNT",
        Messages.getCsvKey("service.data.csv.CustomerCsvSchema.16"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl(
        "LOGIN_LOCKED_FLG", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.17"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl(
        "CUSTOMER_STATUS", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.18"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl(
        "CUSTOMER_ATTRIBUTE_REPLY_DATE", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.19"), CsvDataType.DATE));
    columns.add(new CsvColumnImpl(
        "LATEST_POINT_ACQUIRED_DATE", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.20"), CsvDataType.DATE));
    columns.add(new CsvColumnImpl("REST_POINT", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.21"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl(
        "TEMPORARY_POINT", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.22"), CsvDataType.BIGDECIMAL));
    columns.add(new CsvColumnImpl(
        "WITHDRAWAL_REQUEST_DATE", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.23"), CsvDataType.DATE));
    columns
        .add(new CsvColumnImpl("WITHDRAWAL_DATE", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.24"), CsvDataType.DATE));
    columns
        .add(new CsvColumnImpl("ADDRESS_ALIAS", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.25"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("POSTAL_CODE", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.28"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl(
        "PREFECTURE_CODE", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.29"), CsvDataType.STRING));
//  add by V10-CH 170 start
    columns.add(new CsvColumnImpl(
        "CITY_CODE", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.40"), CsvDataType.STRING));
//  add by V10-CH 170 end
    columns.add(new CsvColumnImpl("ADDRESS1", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.30"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ADDRESS2", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.31"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ADDRESS3", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.32"), CsvDataType.STRING));
//  delete by V10-CH 170 start
//    columns.add(new CsvColumnImpl("ADDRESS4", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.33"), CsvDataType.STRING));
//  delete by V10-CH 170 end
    columns.add(new CsvColumnImpl("PHONE_NUMBER", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.34"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("MOBILE_NUMBER", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.41"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ORM_ROWID", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.35"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("CREATED_USER", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.36"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl(
        "CREATED_DATETIME", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.37"), CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("UPDATED_USER", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.38"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl(
        "UPDATED_DATETIME", Messages.getCsvKey("service.data.csv.CustomerCsvSchema.39"), CsvDataType.DATETIME));
    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "CustomerExportDataSource";
  }

  public String getImportConfigureId() {
    return "CustomerImportDataSource";
  }

  public String getTargetTableName() {
    return "CUSTOMER";
  }

}
