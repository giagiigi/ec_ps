package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.catalog.CommodityCsvExportQuery;

public class CCommodityStockExportDataSource extends SqlExportDataSource<CCommodityStockDataCsvSchema, CCommodityStockExportCondition> {

  public Query getExportQuery() {
    String skuCode = getCondition().getSkuCode();
    String sqlString = getCondition().getSqlString();
    CommodityCsvExportQuery q = null;
    if (!getCondition().isHeaderOnly()) {
      getSchema().setColumns(getCondition().getColumns());
      q = new CommodityCsvExportQuery(sqlString, skuCode, "3","0");
    } else {
//      getSchema().setColumns(getSchema().getAllSchema());
//      q = new CommodityCsvExportQuery(commodityCode, "0");
    }
    return q;

  }
  
  public boolean headerOnly() {
    getSchema().setColumns(getCondition().getColumns());
    return getCondition().isHeaderOnly();
  } 
}
