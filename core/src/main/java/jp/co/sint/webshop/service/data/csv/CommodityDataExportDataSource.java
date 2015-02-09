package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.catalog.CommodityListExportQuery;

public class CommodityDataExportDataSource extends SqlExportDataSource<CommodityDataCsvSchema, CommodityDataExportCondition> {

  @Override
  public Query getExportQuery() {
    CommodityListExportQuery query = new CommodityListExportQuery(getCondition().getSearchCondition());

    query.setSqlString(query.getSqlString() + " ORDER BY SHOP_CODE, COMMODITY_CODE, DISPLAY_ORDER ");

    return query;
  }

}
