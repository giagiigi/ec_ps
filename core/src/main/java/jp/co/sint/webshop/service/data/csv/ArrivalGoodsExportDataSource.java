package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.catalog.ArrivalGoodsExportQuery;

public class ArrivalGoodsExportDataSource extends SqlExportDataSource<ArrivalGoodsCsvSchema, ArrivalGoodsExportCondition> {

  public Query getExportQuery() {

    ArrivalGoodsExportQuery query = new ArrivalGoodsExportQuery(getCondition().getCondition());
    query.setSqlString(query.getSqlString());

    return query;
  }
}
