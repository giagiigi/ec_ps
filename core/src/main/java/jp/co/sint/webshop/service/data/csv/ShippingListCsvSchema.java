package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.csv.CsvDataType;
import jp.co.sint.webshop.data.csv.impl.CsvColumnImpl;
import jp.co.sint.webshop.data.csv.impl.CsvSchemaImpl;
import jp.co.sint.webshop.text.Messages;

public class ShippingListCsvSchema extends CsvSchemaImpl {

  private static final long serialVersionUID = 1L;

  public ShippingListCsvSchema() {
    init1();
    init2();
  }

  private void init1() {
    getColumns().add(new CsvColumnImpl("shipping_no",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.0"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("shipping_detail_no",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.1"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("sku_code",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.2"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("commodity_code",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.3"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("commodity_name",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.4"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("standard_detail1_name",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.5"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("standard_detail2_name",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.6"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("unit_price",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.7"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("discount_price",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.8"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("discount_amount",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.9"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("retail_price",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.10"), CsvDataType.BIGDECIMAL));
//  delete by V10-CH 170 start  
//    getColumns().add(new CsvColumnImpl("retail_tax",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.11"), CsvDataType.NUMBER));
//  delete by V10-CH 170 end 
    getColumns().add(new CsvColumnImpl("purchasing_amount",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.12"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("gift_code",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.13"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("gift_name",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.14"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("gift_price",

        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.15"), CsvDataType.BIGDECIMAL));
//  delete by V10-CH 170 start  
//    getColumns().add(new CsvColumnImpl("gift_tax_rate",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.16"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("gift_tax",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.17"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("gift_tax_type",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.18"), CsvDataType.NUMBER));
//  delete by V10-CH 170 end  
    getColumns().add(new CsvColumnImpl("order_no",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.19"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("order_datetime",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.20"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("shop_code",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.21"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("customer_code",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.22"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("last_name",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.23"), CsvDataType.STRING));
//  delete by V10-CH 170 start    
//    getColumns().add(new CsvColumnImpl("first_name",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.24"), CsvDataType.STRING));
//    getColumns().add(new CsvColumnImpl("last_name_kana",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.25"), CsvDataType.STRING));
//    getColumns().add(new CsvColumnImpl("first_name_kana",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.26"), CsvDataType.STRING));
//  delete by V10-CH 170 end    
    getColumns().add(new CsvColumnImpl("payment_method_type",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.27"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("payment_method_name",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.28"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("payment_commission",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.29"), CsvDataType.BIGDECIMAL));
//  delete by V10-CH 170 start     
//    getColumns().add(new CsvColumnImpl("payment_commission_tax_rate",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.30"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("payment_commission_tax",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.31"), CsvDataType.NUMBER));
//  delete by V10-CH 170 end  
  }

  private void init2() {
//  delete by V10-CH 170 start  
//    getColumns().add(new CsvColumnImpl("payment_commission_tax_type",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.32"), CsvDataType.NUMBER));
//  delete by V10-CH 170 end  
    getColumns().add(new CsvColumnImpl("address_no",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.33"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("address_last_name",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.34"), CsvDataType.STRING));
//  delete by V10-CH 170 start
//    getColumns().add(new CsvColumnImpl("address_first_name",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.35"), CsvDataType.STRING));
//    getColumns().add(new CsvColumnImpl("address_last_name_kana",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.36"), CsvDataType.STRING));
//    getColumns().add(new CsvColumnImpl("address_first_name_kana",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.37"), CsvDataType.STRING));
//  delete by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("postal_code",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.41"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("prefecture_code",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.42"), CsvDataType.STRING));
//  add by V10-CH 170 start
    getColumns().add(new CsvColumnImpl("city_code",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.78"), CsvDataType.STRING));
//  add by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("address1",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.43"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("address2",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.44"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("address3",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.45"), CsvDataType.STRING));
//  delete by V10-CH 170 start
//    getColumns().add(new CsvColumnImpl("address4",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.46"), CsvDataType.STRING));
//  delete by V10-CH 170 end
    getColumns().add(new CsvColumnImpl("phone_number",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.47"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("mobile_number",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.79"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("delivery_remark",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.48"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("acquired_point",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.49"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("delivery_slip_no",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.50"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("shipping_charge",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.51"), CsvDataType.BIGDECIMAL));
//  delete by V10-CH 170 start    
//    getColumns().add(new CsvColumnImpl("shipping_charge_tax_type",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.52"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("shipping_charge_tax_rate",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.53"), CsvDataType.NUMBER));
//    getColumns().add(new CsvColumnImpl("shipping_charge_tax",
//        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.54"), CsvDataType.NUMBER));
//  delete by V10-CH 170 end   
    getColumns().add(new CsvColumnImpl("delivery_type_no",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.55"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("delivery_type_name",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.56"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("delivery_appointed_date",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.57"), CsvDataType.DATE));
    getColumns().add(new CsvColumnImpl("delivery_appointed_time_start",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.58"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("delivery_appointed_time_end",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.59"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("arrival_date",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.60"), CsvDataType.DATE));
    getColumns().add(new CsvColumnImpl("arrival_time_start",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.61"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("arrival_time_end",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.62"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("shipping_status",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.63"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("shipping_status_name",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.64"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("shipping_direct_date",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.65"), CsvDataType.DATE));
    getColumns().add(new CsvColumnImpl("shipping_date",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.66"), CsvDataType.DATE));
    getColumns().add(new CsvColumnImpl("original_shipping_no",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.67"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("return_item_date",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.68"), CsvDataType.DATE));
    getColumns().add(new CsvColumnImpl("return_item_loss_money",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.69"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("return_item_type",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.70"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("all_payment_price",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.71"), CsvDataType.BIGDECIMAL));
    getColumns().add(new CsvColumnImpl("cash_on_delivery_commission",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.72"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("orm_rowid",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.73"), CsvDataType.NUMBER));
    getColumns().add(new CsvColumnImpl("created_user",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.74"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("created_datetime",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.75"), CsvDataType.DATETIME));
    getColumns().add(new CsvColumnImpl("updated_user",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.76"), CsvDataType.STRING));
    getColumns().add(new CsvColumnImpl("updated_datetime",
        Messages.getCsvKey("service.data.csv.ShippingListCsvSchema.77"), CsvDataType.DATETIME));
  }

  public String getExportConfigureId() {
    return "ShippingListExportDataSource";
  }

  public String getImportConfigureId() {
    return "ShippingListImportDataSource";
  }

  public String getTargetTableName() {
    return "";
  }

}
