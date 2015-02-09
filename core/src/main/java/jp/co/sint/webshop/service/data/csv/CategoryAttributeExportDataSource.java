package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.dto.CategoryAttribute;

public class CategoryAttributeExportDataSource extends
    SqlExportDataSource<CategoryAttributeCsvSchema, CategoryAttributeExportCondition> {

  @Override
  public Query getExportQuery() {

    String sql = DatabaseUtil.getSelectAllQuery(CategoryAttribute.class) + " ORDER BY CATEGORY_CODE, CATEGORY_ATTRIBUTE_NO ";
    Query q = new SimpleQuery(sql);

    return q;

  }
}
