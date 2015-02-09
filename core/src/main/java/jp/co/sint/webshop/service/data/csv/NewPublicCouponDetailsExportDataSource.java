package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.analysis.NewPublicCouponDetailsQuery;

public class NewPublicCouponDetailsExportDataSource extends
    SqlExportDataSource<NewPublicCouponDetailsCsvSchema, NewPublicCouponDetailsExportCondition> {

  @Override
  public Query getExportQuery() {
    return new NewPublicCouponDetailsQuery(getCondition().getSearchCondition());
  }

}
