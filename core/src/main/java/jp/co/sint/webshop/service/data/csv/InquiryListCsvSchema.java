package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class InquiryListCsvSchema extends CsvSchemaImpl {

  public InquiryListCsvSchema() {
    List<CsvColumn> columns = new ArrayList<CsvColumn>();

    columns.add(new CsvColumnImpl("INQUIRY_HEADER_NO", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.0"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ACCEPT_UPDATETIME", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.1"),
        CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("CUSTOMER_NAME", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.2"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("CUSTOMER_CODE", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.3"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("MOBILE_NUMBER", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.4"),
        CsvDataType.STRING));
    columns
        .add(new CsvColumnImpl("IB_OB_TYPE", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.5"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("LARGE_CATEGORY", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.6"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("SMALL_CATEGORY", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.7"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("COMMODITY_CODE", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.8"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("INQUIRY_WAY", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.9"),
        CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("INQUIRY_SUBJECT", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.10"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("INQUIRY_STATUS_LAST", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.11"),
        CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("INQUIRY_DETAIL_NO", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.12"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("ACCEPT_UPDATETIME", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.13"),
        CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("PERSON_IN_CHARGE_NAME", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.14"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("PERSON_IN_CHARGE_NO", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.15"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("INQUIRY_STATUS", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.16"),
        CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("INQUIRY_CONTENTS", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.17"),
        CsvDataType.STRING));
    columns
        .add(new CsvColumnImpl("ORM_ROWID", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.18"), CsvDataType.NUMBER));
    columns.add(new CsvColumnImpl("CREATED_USER", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.19"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("CREATED_DATETIME", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.20"),
        CsvDataType.DATETIME));
    columns.add(new CsvColumnImpl("UPDATED_USER", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.21"),
        CsvDataType.STRING));
    columns.add(new CsvColumnImpl("UPDATED_DATETIME", Messages.getCsvKey("service.data.csv.InquiryListCsvSchema.22"),
        CsvDataType.DATETIME));
    setColumns(columns);
  }

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "InquiryListExportDataSource";
  }

  public String getImportConfigureId() {
    return null;
  }

  public String getTargetTableName() {
    return null;
  }

}
