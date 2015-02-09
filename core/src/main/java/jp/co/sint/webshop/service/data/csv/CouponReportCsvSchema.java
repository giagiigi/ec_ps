package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;

public class CouponReportCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public CouponReportCsvSchema() {
    init1();
  }

  private void init1() {
   
  }

  public String getExportConfigureId() {
    return "CouponReportExportDataSource";
  }

  public String getImportConfigureId() {
    return "CouponReportImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

}
