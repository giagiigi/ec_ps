package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.catalog.CommodityCsvExportQuery;
import jp.co.sint.webshop.utility.StringUtil;

public class CCommodityDetailExportDataSource extends SqlExportDataSource<CCommodityDetailCsvSchema, CCommodityDetailExportCondition> {

  public Query getExportQuery() {

    String skuCode = getCondition().getSkuCode();
    String commodityCode = getCondition().getCommodityCode();
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
      // 判断用户在“下载指定商品”栏输入的是商品编号还是SKU编号或者是没有输入
      if (StringUtil.hasValue(commodityCode)) {
        q = new CommodityCsvExportQuery(sqlString, code, "1","1" ,combineType);
      }else if (StringUtil.hasValue(skuCode)) {
        q = new CommodityCsvExportQuery(sqlString, code, "1","2" ,combineType);
      }else {
        q = new CommodityCsvExportQuery(sqlString, code, "1" ,combineType);
      }
    } else {
//      getSchema().setColumns(getSchema().getAllSchema());
//      q = new CommodityCsvExportQuery(skuCode, "1");
    }
    return q;

  }
  
  public boolean headerOnly() {
    getSchema().setColumns(getCondition().getColumns());
    return getCondition().isHeaderOnly();
  } 
}
