package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class JdOrderListCsvTwoSchema extends CsvSchemaImpl {

  public JdOrderListCsvTwoSchema() {
    getColumns().add(new CsvColumnImpl("customerCode", Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.13"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("childOrderNo", Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.1"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("childPaidPrice",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.18"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("orderNo",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.14"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("orderPayment", Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.20"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("shippingStatus",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.19"), CsvDataType.STRING));
     }

  /** serial verision UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "JdOrderListExportDataSourceTwo";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
