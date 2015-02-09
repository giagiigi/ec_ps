package jp.co.sint.webshop.service.data.csv;

import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.utility.WebUtil;

public class NoPicOrStopSellingDataSource extends SqlExportDataSource<NoPicOrStopSellingCsvSchema, NoPicOrStopSellingCondition> {

  public Query getExportQuery() {
    String sql = "SELECT '''' || CH.COMMODITY_CODE,"
        + " CH.COMMODITY_NAME,"
        + " (CASE WHEN CH.COMMODITY_TYPE = 0 THEN '普通商品' ELSE '礼品' END) AS COMMODITY_TYPE ,"
        + " CH.CATEGORY_SEARCH_PATH,"
        + " S.STOCK_QUANTITY,"
        + " S.STOCK_THRESHOLD,"
        + " (CASE WHEN CH.SALE_FLG_EC = 1 AND COUNT_CATEGORY_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) > 0 THEN '销售中' ELSE '停止销售' END) AS SALE_TYPE, "
        + " CH.SUPPLIER_CODE,"
        + " CH.COMMODITY_CODE AS COMMODITY_PIC"
        + " FROM C_COMMODITY_HEADER CH " + " INNER JOIN STOCK S ON S.COMMODITY_CODE = CH.COMMODITY_CODE "
        + " WHERE CH.COMMODITY_NAME NOT LIKE '%已删除%'";
    Query q = null;
    q = new SimpleQuery(sql);
    return q;
  }

  // 设定商品分类及判断是否有图片
  public void getCategoryAndNoPic(List<String> row, List<CsvColumn> columnList) {
    String oneLevCategory = "";
    String twoLevCategory = "";
    for (int i = 0; i < row.size(); i++) {
      String columnName = columnList.get(i).getPhysicalName();
      if (columnName.equals("CATEGORY_SEARCH_PATH") || columnName.equals("category_search_path")) {
        String columnData = row.get(i);
        String[] listStr = columnData.split("~");
        for (int j = 0; j < listStr.length; j++) {
          if (j == 2) {
            Category c = DatabaseUtil.loadAsBean(new SimpleQuery("SELECT * FROM CATEGORY WHERE CATEGORY_CODE = ?", listStr[j]),
                Category.class);
            if (c != null) {
              oneLevCategory = c.getCategoryNamePc();
            }
          }
          if (j == 3) {
            Category c = DatabaseUtil.loadAsBean(new SimpleQuery("SELECT * FROM CATEGORY WHERE CATEGORY_CODE = ?", listStr[j]),
                Category.class);
            if (c != null) {
              twoLevCategory = c.getCategoryNamePc();
            }
          }

        }
        row.set(i, oneLevCategory + "~" + twoLevCategory);
      }
      if (columnName.equals("COMMODITY_PIC") || columnName.equals("commodity_pic")) {
        String columnData = row.get(i);
        String filePath = "shop/" + "00000000" + "/commodity/";
        String fileName = columnData + ".jpg";
        boolean imgFlg = WebUtil.includeStaticExists(filePath, fileName);
        row.set(i, imgFlg ? "Yes" : "No");
      }
    }
  }
}
