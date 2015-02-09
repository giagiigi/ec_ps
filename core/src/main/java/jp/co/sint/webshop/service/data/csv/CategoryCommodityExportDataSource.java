package jp.co.sint.webshop.service.data.csv;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.utility.StringUtil;

public class CategoryCommodityExportDataSource extends
    SqlExportDataSource<CategoryCommodityCsvSchema, CategoryCommodityExportCondition> {

  public Query getExportQuery() {

    String shopCode = getCondition().getShopCode();
    String sql = DatabaseUtil.getSelectAllQuery(CategoryCommodity.class);

    List<Object> params = new ArrayList<Object>();

    if (StringUtil.hasValue(shopCode)) {
      sql += " WHERE SHOP_CODE = ? ";
      params.add(shopCode);
    }

    sql += " ORDER BY CATEGORY_CODE, SHOP_CODE, COMMODITY_CODE ";

    return new SimpleQuery(sql, params.toArray());

  }

}
