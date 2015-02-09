package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.service.catalog.CommodityCsvExportQuery;
import jp.co.sint.webshop.utility.StringUtil;


public class CategoryAttributeValueDataExportDataSource extends
    SqlExportDataSource<CategoryAttributeValueDataCsvSchema, CategoryAttributeValueDataExportCondition> {

  @Override
  public Query getExportQuery() {
    String commodityCode = getCondition().getCommodityCode();
    String skuCode = getCondition().getSkuCode();
    String code = "";
    if (StringUtil.hasValue(commodityCode)) {
      code = commodityCode;
    } else if (StringUtil.hasValue(skuCode)) {
      code = skuCode;
    }
    String sqlString = getCondition().getSqlString();
    CommodityCsvExportQuery q = null;
    if (!getCondition().isHeaderOnly()) {
      getSchema().setColumns(getCondition().getColumns());
      // 判断用户输入的是商品编号还是SKU编号或者是没有输入
      if (StringUtil.hasValue(commodityCode)) {
        q = new CommodityCsvExportQuery(sqlString, code, "3","1","0");
      } else if (StringUtil.hasValue(skuCode)) {
        q = new CommodityCsvExportQuery(sqlString, code, "3","2","0");
      } else {
        q = new CommodityCsvExportQuery(sqlString, code, "3","0");
      }
    }
    return q;
  }
  
  public boolean headerOnly() {
    getSchema().setColumns(getCondition().getColumns());
    return getCondition().isHeaderOnly();
  } 
}
