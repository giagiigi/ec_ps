package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.order.OrderListSearchCondition;
import jp.co.sint.webshop.service.order.OrderListSearchQuery;

public class OrderListExportDataSource extends SqlExportDataSource<OrderListCsvSchema, OrderListExportCondition> {

  public Query getExportQuery() {

    OrderListSearchCondition condition = getCondition().getCondition();
    condition.setSearchListSort("export");
    OrderListSearchQuery exportQuery = new OrderListSearchQuery(condition, true);
    Query q = new SimpleQuery(exportQuery.getSqlString(), exportQuery.getParameters());
    return q;

  }
}
