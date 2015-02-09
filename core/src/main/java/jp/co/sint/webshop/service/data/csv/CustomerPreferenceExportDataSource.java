package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.CustomerPreferenceSearchQuery;

public class CustomerPreferenceExportDataSource extends
    SqlExportDataSource<CustomerPreferenceCsvSchema, CustomerPreferenceExportCondition> {

  @Override
  public Query getExportQuery() {
    return new CustomerPreferenceSearchQuery(getCondition().getSearchCondition());
  }

}
