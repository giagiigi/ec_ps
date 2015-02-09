package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.text.Messages;

public class DeliverySlipNoCsvSchema extends CsvSchemaImpl {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public DeliverySlipNoCsvSchema() {
    getColumns().add(new CsvColumnImpl("shipping_no",
        Messages.getCsvKey("service.data.csv.DeliverySlipNoCsvSchema.0"),
        CsvDataType.STRING, false, false, true, null));
    getColumns().add(new CsvColumnImpl("delivery_slip_no",
        Messages.getCsvKey("service.data.csv.DeliverySlipNoCsvSchema.1"), CsvDataType.STRING));
  }

  public String getExportConfigureId() {
    return "DeliverySlipNoExportDataSource";
  }

  public String getImportConfigureId() {
    return "DeliverySlipNoImportDataSource";
  }

  public String getTargetTableName() {
    return DatabaseUtil.getTableName(ShippingHeader.class);
  }

}
