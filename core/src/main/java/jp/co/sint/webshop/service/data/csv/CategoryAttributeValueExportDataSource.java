package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.dto.CategoryAttributeValue;
import jp.co.sint.webshop.utility.StringUtil;

public class CategoryAttributeValueExportDataSource extends
    SqlExportDataSource<CategoryAttributeValueCsvSchema, CategoryAttributeValueExportCondition> {

  @Override
  public Query getExportQuery() {

    String sql = DatabaseUtil.getSelectAllQuery(CategoryAttributeValue.class);
    Query q = null;

    if (StringUtil.hasValue(getCondition().getShopCode())) {
      sql += " WHERE SHOP_CODE = ? ORDER BY CATEGORY_CODE, SHOP_CODE, COMMODITY_CODE, CATEGORY_ATTRIBUTE_NO ";
      q = new SimpleQuery(sql, getCondition().getShopCode());
    } else {
      sql += " ORDER BY CATEGORY_CODE, SHOP_CODE, COMMODITY_CODE, CATEGORY_ATTRIBUTE_NO";
      q = new SimpleQuery(sql);
    }

    return q;

  }
}
