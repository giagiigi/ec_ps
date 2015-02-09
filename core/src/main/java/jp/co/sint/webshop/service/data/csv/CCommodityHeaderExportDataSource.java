package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.catalog.CommodityCsvExportQuery;
import jp.co.sint.webshop.utility.StringUtil;

public class CCommodityHeaderExportDataSource extends SqlExportDataSource<CCommodityHeaderCsvSchema, CCommodityHeaderExportCondition> {

  public Query getExportQuery() {
    String commodityCode = getCondition().getCommodityCode();
    String skuCode = getCondition().getSkuCode();
    String code = "";
    if (StringUtil.hasValue(commodityCode)) {
      code = commodityCode;
    } else if (StringUtil.hasValue(skuCode)) {
      code = skuCode;
    }
    String combineType = getCondition().getCombineType();
    String sqlString = getCondition().getSqlString();
    CommodityCsvExportQuery q = null;
    if (!getCondition().isHeaderOnly()) {
      getSchema().setColumns(getCondition().getColumns());
      if (StringUtil.hasValue(commodityCode)) {
        q = new CommodityCsvExportQuery(sqlString, code, "0", "1" ,combineType);
      }else if (StringUtil.hasValue(skuCode)) {
        q = new CommodityCsvExportQuery(sqlString, code, "0", "2" ,combineType);
      }else{
        q = new CommodityCsvExportQuery(sqlString, code, "0" ,combineType);
      }
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
