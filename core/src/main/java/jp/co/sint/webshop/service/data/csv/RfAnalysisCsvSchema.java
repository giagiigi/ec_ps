package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class RfAnalysisCsvSchema extends CsvSchemaImpl {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public RfAnalysisCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();
    columns.add(new CsvColumnImpl("CUSTOMER_CODE",
        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.0"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl("CUSTOMER_GROUP_CODE",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.1"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl("LAST_NAME",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.2"), CsvDataType.STRING));
////  delete by V10-CH 170 start
////    columns.add(new CsvColumnImpl("FIRST_NAME",
////        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.3"), CsvDataType.STRING));
////    columns.add(new CsvColumnImpl("LAST_NAME_KANA",
////        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.4"), CsvDataType.STRING));
////    columns.add(new CsvColumnImpl("FIRST_NAME_KANA",
////        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.5"), CsvDataType.STRING));
////  delete by V10-CH 170 start
//    columns.add(new CsvColumnImpl("LOGIN_ID",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.6"), CsvDataType.STRING));
    columns.add(new CsvColumnImpl("EMAIL",
        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.7"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl("PASSWORD",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.8"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl("BIRTH_DATE",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.9"), CsvDataType.DATE));
//    columns.add(new CsvColumnImpl("SEX",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.10"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl("REQUEST_MAIL_TYPE",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.11"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl("CLIENT_MAIL_TYPE",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.12"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl("CAUTION",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.13"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl("LOGIN_DATETIME",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.14"), CsvDataType.DATETIME));
//    columns.add(new CsvColumnImpl("LOGIN_ERROR_COUNT",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.15"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl("LOGIN_LOCKED_FLG",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.16"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl("CUSTOMER_STATUS",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.17"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl("CUSTOMER_ATTRIBUTE_REPLY_DATE",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.18"), CsvDataType.DATE));
//    columns.add(new CsvColumnImpl("LATEST_POINT_ACQUIRED_DATE",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.19"), CsvDataType.DATE));
//    columns.add(new CsvColumnImpl("REST_POINT",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.20"), CsvDataType.BIGDECIMAL));
//    columns.add(new CsvColumnImpl("TEMPORARY_POINT",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.21"), CsvDataType.BIGDECIMAL));
//    columns.add(new CsvColumnImpl("WITHDRAWAL_REQUEST_DATE",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.22"), CsvDataType.DATE));
//    columns.add(new CsvColumnImpl("WITHDRAWAL_DATE",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.23"), CsvDataType.DATE));
//    columns.add(new CsvColumnImpl("ORM_ROWID",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.24"), CsvDataType.NUMBER));
//    columns.add(new CsvColumnImpl("CREATED_USER",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.25"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl("CREATED_DATETIME",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.26"), CsvDataType.DATETIME));
//    columns.add(new CsvColumnImpl("UPDATED_USER",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.27"), CsvDataType.STRING));
//    columns.add(new CsvColumnImpl("UPDATED_DATETIME",
//        Messages.getCsvKey("service.data.csv.RfAnalysisCsvSchema.28"), CsvDataType.DATETIME));

    setColumns(columns);
  }

  public String getExportConfigureId() {
    return "RfAnalysisExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
