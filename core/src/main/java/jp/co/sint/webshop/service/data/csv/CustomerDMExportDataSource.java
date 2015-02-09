package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.customer.CustomerDMExportQuery;

public class CustomerDMExportDataSource extends SqlExportDataSource<CustomerDMCsvSchema, CustomerDMExportCondition> {

  @Override
  public Query getExportQuery() {
    return new CustomerDMExportQuery(getCondition().getSearchCondition());
  }

}
