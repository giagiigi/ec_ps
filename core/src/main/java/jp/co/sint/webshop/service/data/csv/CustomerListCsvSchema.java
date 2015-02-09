package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class CustomerListCsvSchema extends CsvSchemaImpl {

  public CustomerListCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("CUSTOMER_GROUP_CODE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.1"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("CUSTOMER_CODE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.0"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("LAST_NAME", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.2"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("EMAIL", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.7"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("PHONE_NUMBER", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.34"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("MOBILE_NUMBER", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.41"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("BIRTH_DATE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.9"), CsvDataType.DATE));
    columns.add(new CsvColumnImpl("SEX", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.10"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ADDRESS", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.42"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("LANGUAGE_CODE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.13"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("REQUEST_MAIL_TYPE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.43"), CsvDataType.STRING));
    // delete by lc 2012-03-12 strat
//    columns.add(new CsvColumnImpl("LAST_NAME", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.2"), CsvDataType.STRING));
////  delete by V10-CH 170 start
////    columns
////        .add(new CsvColumnImpl("FIRST_NAME", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.3"), CsvDataType.STRING));
////    columns.add(new CsvColumnImpl(
////        "LAST_NAME_KANA", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.4"), CsvDataType.STRING));
////    columns.add(new CsvColumnImpl(
////        "FIRST_NAME_KANA", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.5"), CsvDataType.STRING));
////  delete by V10-CH 170 end
//    columns.add(new CsvColumnImpl("LOGIN_ID", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.6"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl("PASSWORD", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.8"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl(
//        "REQUEST_MAIL_TYPE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.11"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl(
//        "CLIENT_MAIL_TYPE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.12"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl("CAUTION", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.14"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl(
//        "LOGIN_DATETIME", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.15"), CsvDataType.DATETIME));
//    columns.add(new CsvColumnImpl(
//        "LOGIN_ERROR_COUNT", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.16"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl(
//        "LOGIN_LOCKED_FLG", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.17"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl(
//        "CUSTOMER_STATUS", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.18"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl(
//        "CUSTOMER_ATTRIBUTE_REPLY_DATE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.19"), CsvDataType.DATE));
//    columns.add(new CsvColumnImpl(
//        "LATEST_POINT_ACQUIRED_DATE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.20"), CsvDataType.DATE));
//    columns.add(new CsvColumnImpl(
//        "REST_POINT", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.21"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl(
//        "TEMPORARY_POINT", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.22"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl(
//        "WITHDRAWAL_REQUEST_DATE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.23"), CsvDataType.DATE));
//    columns.add(new CsvColumnImpl(
//        "WITHDRAWAL_DATE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.24"), CsvDataType.DATE));
//    columns.add(new CsvColumnImpl(
//        "ADDRESS_ALIAS", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.25"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl(
//        "POSTAL_CODE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.28"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl(
//        "PREFECTURE_CODE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.29"), CsvDataType.STRING));
//    //add by V10-CH 170 start
//    columns.add(new CsvColumnImpl(
//        "CITY_CODE", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.40"), CsvDataType.STRING));
//    //add by V10-CH 170 end
//    columns.add(new CsvColumnImpl("ADDRESS1", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.30"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl("ADDRESS2", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.31"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl("ADDRESS3", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.32"), CsvDataType.STRING));
//    //delete by V10-CH 170 start
//    //columns.add(new CsvColumnImpl("ADDRESS4", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.33"), CsvDataType.STRING));
//    //delete by V10-CH 170 end
//    columns
//        .add(new CsvColumnImpl("ORM_ROWID", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.35"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl(
//        "CREATED_USER", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.36"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl(
//        "CREATED_DATETIME", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.37"), CsvDataType.DATETIME));
//    columns.add(new CsvColumnImpl(
//        "UPDATED_USER", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.38"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl(
//        "UPDATED_DATETIME", Messages.getCsvKey("service.data.csv.CustomerListCsvSchema.39"), CsvDataType.DATETIME));
    // delete by lc 2012-03-12 end
    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "CustomerListExportDataSource";
  }

  public String getImportConfigureId() {
    return null;
  }

  public String getTargetTableName() {
    return null;
  }

}
