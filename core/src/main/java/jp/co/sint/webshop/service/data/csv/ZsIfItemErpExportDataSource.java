package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvColumn;
import jp.co.sint.webshop.data.csv.sql.SqlExportDataSource;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;

public class ZsIfItemErpExportDataSource extends SqlExportDataSource<ZsIfItemErpExportCsvSchema, ZsIfItemErpExportCondition> {

  WebshopConfig config = DIContainer.getWebshopConfig();

  private boolean headerOnly = false;

  // 导出过程中临时设定的exportFlagErp值
  private Long exportFlagErp = Long.parseLong("9");

  public Long getExportFlagErp() {
    return exportFlagErp;
  }

  private PreparedStatement updateStatement = null;

  private Logger logger = Logger.getLogger(this.getClass());

  String updateSql = "UPDATE C_COMMODITY_HEADER SET EXPORT_FLAG_ERP = ?, UPDATED_USER = ?, UPDATED_DATETIME = ?  WHERE EXPORT_FLAG_ERP = ? AND (SET_COMMODITY_FLG <> 1 OR SET_COMMODITY_FLG IS NULL) AND COMMODITY_CODE <> '9999999' AND COMMODITY_NAME NOT LIKE '%已删除%' ";

  /*
   * 导出前把数据库中所有的EXPORT_FLAG_ERP值为0的数据的EXPORT_FLAG_ERP值更新成9
   */
  public void beforeExport() {

    logger.debug("UPDATE C_COMMODITY_HEADER statement: " + updateSql);
    try {
      updateStatement = createPreparedStatement(updateSql);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    List<Object> updateShippingParams = new ArrayList<Object>();
    updateShippingParams.add(getExportFlagErp());
    updateShippingParams.add("BATCH:0:0:0");
    updateShippingParams.add(DateUtil.getSysdate());
    updateShippingParams.add(1L);
    logger.debug("Table:C_COMMODITY_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));
    try {
      DatabaseUtil.bindParameters(updateStatement, ArrayUtil.toArray(updateShippingParams, Object.class));
      int rows = updateStatement.executeUpdate();
      // 判断是不是无数据，只有标题行
      if (rows <= 0) {
        headerOnly = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // 判断是不是无数据，只有标题行实现方法
  public boolean headerOnly() {
    return headerOnly;
  }

  /*
   * 导出成功后把数据库中所有的EXPORT_FLAG_ERP值为9的数据的EXPORT_FLAG_ERP值更新成1
   */
  public void afterExport() {

    logger.debug("UPDATE C_COMMODITY_HEADER statement: " + updateSql);
    try {
      updateStatement = createPreparedStatement(updateSql);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
    List<Object> updateShippingParams = new ArrayList<Object>();
    updateShippingParams.add(0L);
    updateShippingParams.add("BATCH:0:0:0");
    updateShippingParams.add(DateUtil.getSysdate());
    updateShippingParams.add(getExportFlagErp());
    logger.debug("Table:C_COMMODITY_HEADER UPDATE Parameters:"
        + Arrays.toString(ArrayUtil.toArray(updateShippingParams, Object.class)));
    try {
      DatabaseUtil.bindParameters(updateStatement, ArrayUtil.toArray(updateShippingParams, Object.class));
      updateStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // 截取SKU_NAME赋值给Item Description 1和Item Description 2
  public void subStringSkuName(List<String> row, List<CsvColumn> columnList) {
    String oneStr = "";
    for (int i = 0; i < row.size(); i++) {
      String columnName = columnList.get(i).getPhysicalName();
      if (columnName.equals("COMMODITY_NAME_ONE") || columnName.equals("commodity_name_one")) {
        String columnData = row.get(i);
        oneStr = StringUtil.subStringByByte(columnData, 24);
        row.set(i, StringUtil.replaceCRLF(oneStr));
      }
      if (columnName.equals("COMMODITY_NAME_TWO") || columnName.equals("commodity_name_two")) {
        String columnData = row.get(i);
        String twoStr = StringUtil.subStringByByte(columnData.substring(oneStr.length()), 24);
        row.set(i, StringUtil.replaceCRLF(twoStr));
      }
    }
  }

  // 设定商品大中小分类
  public void getCategory(List<String> row, List<CsvColumn> columnList) {
    String bigCategory = "";
    String midCategory = "";
    String smallCategory = "";
    // 运费大分类固定编号
    String freeCate = "";
    for (int i = 0; i < row.size(); i++) {
      String columnName = columnList.get(i).getPhysicalName();
      if (columnName.equals("SKU_CODE") || columnName.equals("sku_code")) {
        String columnData = row.get(i);
        if (columnData.equals("9999999")) {
          freeCate = "H1";
        }
      }
      if (columnName.equals("PRODUCTLINE") || columnName.equals("productline")) {
        String columnData = row.get(i);
        String[] listStr = columnData.split("~");
        for (int j = 0; j < listStr.length; j++) {
          if (j == 2) {
            bigCategory = listStr[j];
          }
          if (listStr.length == 3) {
            int bigLen = bigCategory.length();
            midCategory = bigCategory;
            for (int k = 0; k < 5 - bigLen; k++) {
              midCategory += "0";
            }
            smallCategory = midCategory + "000";
          }
          if (listStr.length > 3) {
            if (j == 3) {
              midCategory = listStr[j];
              smallCategory = midCategory;
              int midLen = midCategory.length();
              for (int k = 0; k < 8 - midLen; k++) {
                smallCategory += "0";
              }
            }
          }
        }
        if (StringUtil.hasValue(freeCate)) {
          row.set(i, freeCate);
        } else {
          row.set(i, bigCategory);
        }
      }
      if (columnName.equals("ITEMTYPE") || columnName.equals("itemtype")) {
        row.set(i, "ITYPE");
      }
      if (columnName.equals("ITEMGROUP") || columnName.equals("itemgroup")) {
        row.set(i, "PT_GROUP");
      }
    }
  }

  public Query getExportQuery() {

    String sql = "" + " SELECT B.SKU_CODE " + " , " + "'" + config.getIfCmdtyUm() + "'" + " AS UM " + " , B.SKU_NAME "
        + " , B.SKU_NAME, "

        + " (CASE WHEN A.COMMODITY_TYPE = 1 THEN " + "'" + config.getGiftDefCategory() + "'"
        + "  ELSE (SELECT PATH||'~'||CATEGORY_CODE FROM CATEGORY WHERE CATEGORY_CODE = "
        + " (SELECT MIN(CC.CATEGORY_CODE) FROM CATEGORY_COMMODITY CC  INNER JOIN CATEGORY C ON C.CATEGORY_CODE = CC.CATEGORY_CODE  "
        + "  WHERE CC.COMMODITY_CODE=A.COMMODITY_CODE AND CC.CATEGORY_CODE != '0')) END) AS PRODUCTLINE "

        + " , TO_CHAR(A.CREATED_DATETIME,'yy/MM/dd') AS CREATED_DATETIME " + " ,'' AS ITEMTYPE"
        + " , (CASE WHEN B.USE_FLG = '1' THEN '0' WHEN B.USE_FLG = '0' THEN '1' ELSE '1' END) AS USE_FLG" + " ,'' AS ITEMGROUP"
        + " , A.SHELF_LIFE_FLAG AS DRAWING " + " , '' AS ABCCLASS " + " , '' AS LOTSERIALCONTROL " + " , " + "'"
        + config.getIfSite() + "'" + " AS SITE " + " , " + "'" + config.getIfLocationForEc() + "'" + " AS LOCATION "
        + " , '' AS SHELFLIFT " + " , 'Y' AS PLANORDER " + " , B.STOCK_WARNING " + " , '' AS REORDERPOINT " + " , A.BUYER_CODE "
        + " , A.SUPPLIER_CODE " + " , A.LEAD_TIME " + " , 'Y' AS INSPECTIONREQUIRED " + " , '0' AS INSPECTLT "
        + " , B.MINIMUM_ORDER " + " , B.MAXIMUM_ORDER " + " , B.ORDER_MULTIPLE "
        + " , (CASE WHEN CE.ON_STOCK_FLAG = 2 THEN 'TRANSHIP' ELSE 'NON-EMT' END) AS EMTTYPE "
        + " , 'N' AS AUTOMATICEMTPROCESSING " + " , B.WEIGHT " + " ,  '' AS NetWeight  " + " , B.UNIT_PRICE " + " , 'Y' AS TAX "
        + " , B.TAX_CLASS " + " , '' AS LENGTH " + " , '' AS WIDE " + " , '' AS HIGH " 
        +" FROM C_COMMODITY_HEADER A "
        + " INNER JOIN C_COMMODITY_DETAIL B " + " ON A.SHOP_CODE = B.SHOP_CODE " + " AND A.COMMODITY_CODE = B.COMMODITY_CODE "
        + " INNER JOIN STOCK C " + " ON B.SHOP_CODE = C.SHOP_CODE "
        + " AND B.SKU_CODE = C.SKU_CODE LEFT JOIN C_COMMODITY_EXT CE ON CE.COMMODITY_CODE = A.COMMODITY_CODE "
        + " WHERE (A.SET_COMMODITY_FLG <> 1 OR A.SET_COMMODITY_FLG IS NULL) AND A.COMMODITY_NAME NOT LIKE '%已删除%' AND A.COMMODITY_CODE <> '9999999'  AND (A.ORIGINAL_COMMODITY_CODE IS NULL OR A.ORIGINAL_COMMODITY_CODE = '') AND A.EXPORT_FLAG_ERP =" + getExportFlagErp();

    Query q = null;
    sql += "  ORDER BY A.SHOP_CODE, A.COMMODITY_CODE";
    q = new SimpleQuery(sql);
    return q;
  }
}
