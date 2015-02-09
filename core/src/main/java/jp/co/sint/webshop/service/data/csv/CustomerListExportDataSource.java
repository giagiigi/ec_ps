package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.customer.CustomerExportQuery;

public class CustomerListExportDataSource extends SqlExportDataSource<CustomerListCsvSchema, CustomerListExportCondition> {

  @Override
  public Query getExportQuery() {
    return new CustomerExportQuery(getCondition().getSearchCondition());
  }

}
