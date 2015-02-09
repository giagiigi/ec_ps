package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.jd.order.JdCsvOrderSearchQueryTwo;
import jp.co.sint.webshop.service.jd.order.JdOrderSearchCondition;
public class JdOrderListExportDataSourceTwo extends SqlExportDataSource<JdOrderListCsvTwoSchema, JdOrderListExportConditionTwo> {

  @Override
  public Query getExportQuery() {
    JdOrderSearchCondition condition = getCondition().getCondition();
    
    JdCsvOrderSearchQueryTwo exportQuery = new  JdCsvOrderSearchQueryTwo(condition);

    Query q = new SimpleQuery(exportQuery.getSqlString(), exportQuery.getParameters());
    return q;

  }

}
