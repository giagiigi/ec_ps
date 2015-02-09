package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.jd.order.JdCsvOrderSearchQuery;
import jp.co.sint.webshop.service.jd.order.JdOrderSearchCondition;
public class JdOrderListExportDataSource extends SqlExportDataSource<JdOrderListCsvSchema, JdOrderListExportCondition> {

  @Override
  public Query getExportQuery() {
    JdOrderSearchCondition condition = getCondition().getCondition();
    
    JdCsvOrderSearchQuery exportQuery = new JdCsvOrderSearchQuery(condition);
    
    Query q = new SimpleQuery(exportQuery.getSqlString(), exportQuery.getParameters());
    return q;

  }

}
