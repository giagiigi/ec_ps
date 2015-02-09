package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.catalog.CommodityCsvExportQuery;
import jp.co.sint.webshop.utility.StringUtil;

public class CCommodityDataExportDataSource extends SqlExportDataSource<CCommodityDataCsvSchema, CCommodityDataExportCondition> {

  public Query getExportQuery() {

    String skuCode = getCondition().getSkuCode();
    // 20120201 os013 add start
    String commodityCode = getCondition().getCommodityCode();
    String code = "";
    if (StringUtil.hasValue(commodityCode)) {
      code = commodityCode;
    } else if (StringUtil.hasValue(skuCode)) {
      code = skuCode;
    }
    String combineType = getCondition().getCombineType();
    // 20120201 os013 add end
    String sqlString = getCondition().getSqlString();
    CommodityCsvExportQuery q = null;
    if (!getCondition().isHeaderOnly()) {
      getSchema().setColumns(getCondition().getColumns());
      // 20120201 os013 add start
      // 判断用户输入的是商品编号还是SKU编号或者是没有输入
      if (StringUtil.hasValue(commodityCode)) {
        q = new CommodityCsvExportQuery(sqlString, code, "2", "1",combineType);
      } else if (StringUtil.hasValue(skuCode)) {
        q = new CommodityCsvExportQuery(sqlString, code, "2", "2",combineType);
      } else {
        // 20120201 os013 add end
        q = new CommodityCsvExportQuery(sqlString, code, "2" ,combineType);
      }
    } else {
      // getSchema().setColumns(getSchema().getAllSchema());
      // q = new CommodityCsvExportQuery(skuCode, "2");
    }
    return q;

  }

  public boolean headerOnly() {
    getSchema().setColumns(getCondition().getColumns());
    return getCondition().isHeaderOnly();
  }
}
