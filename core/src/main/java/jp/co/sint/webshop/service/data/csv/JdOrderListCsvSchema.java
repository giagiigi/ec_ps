package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class JdOrderListCsvSchema extends CsvSchemaImpl {

  public JdOrderListCsvSchema() {
    
    getColumns().add(new CsvColumnImpl("customerCode", Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.13"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("orderNo", Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.14"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("childOrderNo", Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.1"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("skuCode", Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.15"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("commodityname",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.2"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("purchasingAmount",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.3"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("unitPrice",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.4"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("rowprice",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.5"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("childPaidPrice",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.6"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("deliveryCompanyName",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.7"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("isPart",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.8"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("address1",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.9"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("address2",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.10"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("address3",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.11"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("address4",Messages.getCsvKey("service.data.csv.JdOrderListCsvSchema.12"), CsvDataType.STRING));
  }

  /** serial verision UID */
  private static final long serialVersionUID = 1L;

  public String getExportConfigureId() {
    return "JdOrderListExportDataSource";
  }

  public String getImportConfigureId() {
    return "";
  }

  public String getTargetTableName() {
    return "";
  }

}
