package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.domain.CustomerStatus;

public class CustomerAddressExportDataSource extends SqlExportDataSource<CustomerAddressCsvSchema, CustomerAddressExportCondition> {

  @Override
  public Query getExportQuery() {
//  modify by V10-CH 170 start
    String exportAllQuery = 
//      DatabaseUtil.getSelectAllQuery(CustomerAddress.class)
      " select  customer_code,  address_no,  address_alias,  address_last_name,"
      + "  postal_code,  prefecture_code,  city_code,  address1,"
      + " address2,  address3,  phone_number,  mobile_number, orm_rowid, "
      + " created_user,  created_datetime,  updated_user,  updated_datetime "
      + " from customer_address "
//add by V10-CH 170 end
        + " WHERE CUSTOMER_CODE IN (SELECT CUSTOMER_CODE FROM CUSTOMER WHERE CUSTOMER_STATUS = ?)"
        + " ORDER BY CUSTOMER_CODE, ADDRESS_NO ";

    return new SimpleQuery(exportAllQuery, CustomerStatus.MEMBER.getValue());
  }

}
