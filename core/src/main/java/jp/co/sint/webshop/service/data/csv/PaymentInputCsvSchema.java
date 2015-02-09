package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class PaymentInputCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public PaymentInputCsvSchema() {
    getColumns().add(new CsvColumnImpl("order_no",
        Messages.getCsvKey("service.data.csv.PaymentInputCsvSchema.0"),
        CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("payment_date",
        Messages.getCsvKey("service.data.csv.PaymentInputCsvSchema.1"),
        CsvDataType.DATE));
  }

  public String getExportConfigureId() {
    return "PaymentInputExportDataSource";
  }

  public String getImportConfigureId() {
    return "PaymentInputImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

}
