package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.NewPublicCouponQuery;

public class NewPublicCouponExportDataSource extends
    SqlExportDataSource<NewPublicCouponCsvSchema, NewPublicCouponExportCondition> {

  @Override
  public Query getExportQuery() {
    return new NewPublicCouponQuery(getCondition().getSearchCondition());
  }

}
