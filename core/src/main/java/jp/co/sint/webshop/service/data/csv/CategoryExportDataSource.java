package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.dto.Category;

public class CategoryExportDataSource extends SqlExportDataSource<CategoryCsvSchema, CategoryExportCondition> {

  @Override
  public Query getExportQuery() {

    String sql = DatabaseUtil.getSelectAllQuery(Category.class) + " ORDER BY PATH, DISPLAY_ORDER ";
    Query q = new SimpleQuery(sql);

    return q;

  }
}
