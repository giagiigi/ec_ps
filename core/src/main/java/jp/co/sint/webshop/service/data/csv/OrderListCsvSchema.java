package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class OrderListCsvSchema extends CsvSchemaImpl {

	private static final long serialVersionUID = 1L;

	public OrderListCsvSchema() {
		getColumns().add(new CsvColumnImpl("order_no", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.0"), CsvDataType.STRING));
		getColumns().add(new CsvColumnImpl("mobile_computer_type", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.76"), CsvDataType.STRING));
	  getColumns().add(new CsvColumnImpl("customer_code", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.19"), CsvDataType.STRING));
	  getColumns().add(new CsvColumnImpl("customer_le",Messages.getCsvKey("service.data.csv.OrderListCsvSchema.51"), CsvDataType.STRING));
	  getColumns().add(new CsvColumnImpl("use_language",Messages.getCsvKey("service.data.csv.OrderListCsvSchema.68"), CsvDataType.STRING));
	  getColumns().add(new CsvColumnImpl("payment_method", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.39"), CsvDataType.STRING));
	  getColumns().add(new CsvColumnImpl("address1", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.69"), CsvDataType.STRING));
	  getColumns().add(new CsvColumnImpl("retail_price", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.70"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("shipping_charge", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.71"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("payment_commission", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.77"), CsvDataType.BIGDECIMAL));
	  getColumns().add(new CsvColumnImpl("discount_price", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.72"), CsvDataType.BIGDECIMAL));
	  getColumns().add(new CsvColumnImpl("total_amount", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.73"), CsvDataType.BIGDECIMAL));
	  getColumns().add(new CsvColumnImpl("discount_code", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.74"), CsvDataType.BIGDECIMAL));
	  getColumns().add(new CsvColumnImpl("coupon_name", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.75"), CsvDataType.STRING));
	  getColumns().add(new CsvColumnImpl("use_agent", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.78"), CsvDataType.STRING));
	  getColumns().add(new CsvColumnImpl("order_client_type", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.79"), CsvDataType.STRING));
	  getColumns().add(new CsvColumnImpl("language_code", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.80"), CsvDataType.STRING));
	  getColumns().add(new CsvColumnImpl("gift_card_use_price", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.81"), CsvDataType.BIGDECIMAL));
	  getColumns().add(new CsvColumnImpl("outer_card_use_price", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.82"), CsvDataType.BIGDECIMAL));
	  getColumns().add(new CsvColumnImpl("address_last_name", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.83"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("addresssf2", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.84"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("addresssf3", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.85"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("addresssf4", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.86"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("shipping_mobile_number", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.87"), CsvDataType.STRING));
    // 20140424 why add start
    getColumns().add(new CsvColumnImpl("last_name", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.88"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("email", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.89"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("delivery_company_name", Messages.getCsvKey("service.data.csv.OrderListCsvSchema.90"), CsvDataType.STRING));
    // 20140424 why add end
	}

	public String getExportConfigureId() {
		return "OrderListExportDataSource";
	}

	public String getImportConfigureId() {
		return "OrderListImportDataSource";
	}

	public String getTargetTableName() {
		return "";
	}

}
