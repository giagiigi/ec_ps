package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.order.ShippingListSearchCondition;
import jp.co.sint.webshop.service.order.ShippingListSearchQuery;

public class ShippingListExportDataSource extends SqlExportDataSource<ShippingListCsvSchema, ShippingListExportCondition> {

  public Query getExportQuery() {

    ShippingListSearchCondition condition = getCondition().getCondition();
    condition.setSortItem(ShippingListSearchQuery.SORT_EXPORT);
    ShippingListSearchQuery exportQuery = new ShippingListSearchQuery(condition, true);
    Query q = new SimpleQuery(exportQuery.getSqlString(), exportQuery.getParameters());
    return q;

  }
}
