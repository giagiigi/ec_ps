package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;

public class NewDeliverySlipNoCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public NewDeliverySlipNoCsvSchema() {
    getColumns().add(new CsvColumnImpl("ORDER_NO", "订单编号", CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("DELIVERY_SLIP_NO", "运单编号", CsvDataType.STRING));
  }

  public String getExportConfigureId() {
    return "";
  }

  public String getImportConfigureId() {
    return "NewDeliverySlipNoImportDataSource";
  }

  public String getTargetTableName() {
    return null;
  }
}
