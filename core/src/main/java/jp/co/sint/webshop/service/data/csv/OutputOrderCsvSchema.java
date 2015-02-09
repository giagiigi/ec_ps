package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class OutputOrderCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public OutputOrderCsvSchema() {
    init1();
    init2();
  }
  private void init1() {
    getColumns().add(new CsvColumnImpl("order_no",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.0"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.1"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("order_datetime",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.2"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("customer_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.3"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("guest_flg",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.4"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("last_name",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.5"), CsvDataType.STRING));
//  delete by V10-CH 170 start
//    getColumns().add(new CsvColumnImpl("first_name",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.6"), CsvDataType.STRING));
//    getColumns().add(new CsvColumnImpl("last_name_kana",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.7"), CsvDataType.STRING));
//    getColumns().add(new CsvColumnImpl("first_name_kana",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.8"), CsvDataType.STRING));
//  delete by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("email",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.9"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("postal_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.10"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("prefecture_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.11"), CsvDataType.STRING));
//  add by V10-CH 170 start
    getColumns().add(new CsvColumnImpl("city_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.105"), CsvDataType.STRING));
//  add by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("address1",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.12"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("address2",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.13"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("address3",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.14"), CsvDataType.STRING));
//  delete by V10-CH 170 start
//    getColumns().add(new CsvColumnImpl("address4",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.15"), CsvDataType.STRING));
//  delete by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("phone_number",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.16"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("mobile_number",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.106"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("advance_later_flg",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.17"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("payment_method_no",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.18"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("payment_method_type",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.19"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("payment_method_name",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.20"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("payment_commission",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.21"), CsvDataType.NUMBER));
//  delete by V10-CH 170 start
//    getColumns().add(new CsvColumnImpl("payment_commission_tax_rate",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.22"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("payment_commission_tax",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.23"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("payment_commission_tax_type",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.24"), CsvDataType.NUMBER));
//  delete by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("used_point",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.25"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("coupon_price",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.107"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("payment_date",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.26"), CsvDataType.DATE));
    getColumns().add(new CsvColumnImpl("payment_limit_date",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.27"), CsvDataType.DATE));
    getColumns().add(new CsvColumnImpl("payment_status",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.28"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("customer_group_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.29"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("data_transport_status",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.30"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("order_status",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.31"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("client_group",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.32"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("caution",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.33"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("message",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.34"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("payment_order_id",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.35"), CsvDataType.NUMBER));
//  delete by V10-CH 170 start
//    getColumns().add(new CsvColumnImpl("cvs_code",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.36"), CsvDataType.STRING));
//    getColumns().add(new CsvColumnImpl("payment_receipt_no",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.37"), CsvDataType.STRING));
//    getColumns().add(new CsvColumnImpl("payment_receipt_url",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.38"), CsvDataType.STRING));
//    getColumns().add(new CsvColumnImpl("digital_cash_type",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.39"), CsvDataType.STRING));
//  delete by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("warning_message",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.40"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("orm_rowid",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.41"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("created_user",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.42"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("created_datetime",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.43"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("updated_user",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.44"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("updated_datetime",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.45"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("order_no",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.46"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.1"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("sku_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.47"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("commodity_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.48"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("commodity_name",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.49"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("standard_detail1_name",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.50"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("standard_detail2_name",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.51"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("purchasing_amount",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.52"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("unit_price",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.53"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("retail_price",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.54"), CsvDataType.NUMBER));
//  delete by V10-CH 170 start
//    getColumns().add(new CsvColumnImpl("retail_tax",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.55"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("commodity_tax_rate",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.56"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("commodity_tax",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.57"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("commodity_tax_type",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.58"), CsvDataType.NUMBER));
//  delete by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("campaign_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.59"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("campaign_name",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.60"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("campaign_discount_rate",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.61"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("applied_point_rate",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.62"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("orm_rowid",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.41"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("created_user",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.42"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("created_datetime",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.43"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("updated_user",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.44"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("updated_datetime",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.45"), CsvDataType.DATETIME));
  }
  private void init2() {
    getColumns().add(new CsvColumnImpl("shipping_no",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.63"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("order_no",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.46"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.1"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("customer_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.3"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("address_no",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.64"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("address_last_name",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.65"), CsvDataType.STRING));
//  delete by V10-CH 170 start
//    getColumns().add(new CsvColumnImpl("address_first_name",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.66"), CsvDataType.STRING));
//    getColumns().add(new CsvColumnImpl("address_last_name_kana",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.67"), CsvDataType.STRING));
//    getColumns().add(new CsvColumnImpl("address_first_name_kana",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.68"), CsvDataType.STRING));
//  delete by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("postal_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.10"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("prefecture_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.11"), CsvDataType.STRING));
//  add by V10-CH 170 start
    getColumns().add(new CsvColumnImpl("city_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.105"), CsvDataType.STRING));
//  add by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("address1",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.12"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("address2",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.13"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("address3",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.14"), CsvDataType.STRING));
//  delete by V10-CH 170 start
//    getColumns().add(new CsvColumnImpl("address4",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.15"), CsvDataType.STRING));
//  delete by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("phone_number",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.16"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("mobile_number",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.106"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("delivery_remark",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.69"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("acquired_point",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.70"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("delivery_slip_no",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.71"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("shipping_charge",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.72"), CsvDataType.NUMBER));
//  delete by V10-CH 170 start
//    getColumns().add(new CsvColumnImpl("shipping_charge_tax_type",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.73"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("shipping_charge_tax_rate",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.74"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("shipping_charge_tax",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.75"), CsvDataType.NUMBER));
//  delete by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("delivery_type_no",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.76"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("delivery_type_name",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.77"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("delivery_appointed_date",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.78"), CsvDataType.DATE));
    getColumns().add(new CsvColumnImpl("delivery_appointed_time_start",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.79"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("delivery_appointed_time_end",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.80"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("arrival_date",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.81"), CsvDataType.DATE));
    getColumns().add(new CsvColumnImpl("arrival_time_start",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.82"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("arrival_time_end",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.83"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("fixed_sales_status",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.84"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("shipping_status",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.85"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("shipping_direct_date",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.86"), CsvDataType.DATE));
    getColumns().add(new CsvColumnImpl("shipping_date",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.87"), CsvDataType.DATE));
    getColumns().add(new CsvColumnImpl("original_shipping_no",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.88"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("return_item_date",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.89"), CsvDataType.DATE));
    getColumns().add(new CsvColumnImpl("return_item_loss_money",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.90"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("return_item_type",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.91"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("orm_rowid",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.41"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("created_user",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.42"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("created_datetime",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.43"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("updated_user",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.44"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("updated_datetime",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.45"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("shipping_no",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.63"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("shipping_detail_no",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.92"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.1"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("sku_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.47"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("unit_price",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.53"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("discount_price",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.93"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("discount_amount",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.94"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("retail_price",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.54"), CsvDataType.NUMBER));
//  delete by V10-CH 170 start
//    getColumns().add(new CsvColumnImpl("retail_tax",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.95"), CsvDataType.NUMBER));
//  delete by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("purchasing_amount",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.52"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("gift_code",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.96"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("gift_name",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.97"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("gift_price",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.98"), CsvDataType.NUMBER));
//  delete by V10-CH 170 start
//    getColumns().add(new CsvColumnImpl("gift_tax_rate",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.99"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("gift_tax",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.100"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("gift_tax_type",
//        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.101"), CsvDataType.NUMBER));
//  delete by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("orm_rowid",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.41"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("created_user",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.42"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("created_datetime",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.43"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("updated_user",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.44"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("updated_datetime",
        Messages.getCsvKey("service.data.csv.OutputOrderCsvSchema.45"), CsvDataType.DATETIME));

  }

  public String getExportConfigureId() {
    return "OutputOrderExportDataSource";
  }

  public String getImportConfigureId() {
    return "OutputOrderImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

}
