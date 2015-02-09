package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.customer.CouponStatusQuery;

public class CouponStatusAllExportDataSource extends SqlExportDataSource<CouponStatusAllCsvSchema, CouponStatusAllExportCondition> {

  @Override
  public Query getExportQuery() {
    return new CouponStatusQuery(getCondition().getSearchCondition(), CouponStatusQuery.LOAD_COUPON_STATUS_QUERY);
  }

}
