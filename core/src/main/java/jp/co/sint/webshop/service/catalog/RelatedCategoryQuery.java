package jp.co.sint.webshop.service.catalog;


import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.CategoryAttribute;
import jp.co.sint.webshop.data.dto.CategoryCommodity;

public class RelatedCategoryQuery extends AbstractQuery<RelatedCategory> {

  /**
   */
  private static final long serialVersionUID = 1L;

  /**
   */
  public static final String GET_CATEGORY_ATTRIBUTE_VALUE_LIST_QUERY =
        "SELECT A.CATEGORY_CODE, "
        + "                   A.SHOP_CODE, "
        + "                   A.CATEGORY_CODE, "
        + "                   A.COMMODITY_CODE, "
        + "                   A.CATEGORY_ATTRIBUTE_NO, "
        + "                   A.CATEGORY_ATTRIBUTE_VALUE, "
        //add by cs_yuli 20120608 start
        + " 				  A.CATEGORY_ATTRIBUTE_VALUE_EN, "
        + "					  A.CATEGORY_ATTRIBUTE_VALUE_JP, "
        //add by cs_yuli 20120608 end
        + "                   A.ORM_ROWID, "
        + "                   A.UPDATED_DATETIME, "
        + "                   B.COMMODITY_CODE, "
        + "                   B.COMMODITY_NAME "
        + "         FROM      CATEGORY_ATTRIBUTE_VALUE A "
        + "                     INNER JOIN COMMODITY_HEADER B "
        + "                       ON A.COMMODITY_CODE = B.COMMODITY_CODE AND "
        + "                          A.SHOP_CODE      = B.SHOP_CODE "
        + "         WHERE     A.SHOP_CODE      = ? AND "
        + "                   A.CATEGORY_CODE  = ? AND "
        + "                   A.COMMODITY_CODE = ? "
        + "         ORDER BY  A.CATEGORY_ATTRIBUTE_NO, B.COMMODITY_CODE ";

  public static final String GET_DELETE_CATEGORY_ATTRIBUTE_VALUE_QUERY = 
    " DELETE FROM CATEGORY_ATTRIBUTE_VALUE WHERE CATEGORY_CODE = ? AND SHOP_CODE = ? AND COMMODITY_CODE = ? ";

  public static final String GET_CATEGORY_ATTRIBUTE_LIST_QUERY = 
    DatabaseUtil.getSelectAllQuery(CategoryAttribute.class)
    + " WHERE CATEGORY_CODE = ? ORDER BY CATEGORY_ATTRIBUTE_NO";

  public static final String GET_DELETE_CATEGORY_COMMODITY_ALL_QUERY = 
    "DELETE FROM CATEGORY_COMMODITY WHERE SHOP_CODE = ? AND COMMODITY_CODE = ?";

  public static final String GET_CATEGORY_COMMODITY_LIST_QUERY = 
    DatabaseUtil.getSelectAllQuery(CategoryCommodity.class)
    + " WHERE SHOP_CODE = ? AND COMMODITY_CODE = ? ORDER BY COMMODITY_CODE";

  //20120216 os013 add start
  //修改C_COMMODITY_HEADER表的検索用カテゴリパス
  public static final String UPDATE_C_COMMODITY_HEADER_CATEGORY_SEARCH_PATH_QUERY="UPDATE C_COMMODITY_HEADER "
    + " SET CATEGORY_SEARCH_PATH = ? ,SYNC_FLAG_EC = ?,SYNC_FLAG_TMALL = ?,EXPORT_FLAG_ERP = ?,EXPORT_FLAG_WMS = ?,UPDATED_USER = ? ,UPDATED_DATETIME = ?  WHERE COMMODITY_CODE = ? ";
  //修改C_COMMODITY_HEADER表的 商品の分類属性値
  public static final String UPDATE_C_COMMODITY_HEADER_CATEGORY_ATTRIBUTE_VALUE_QUERY="UPDATE C_COMMODITY_HEADER "
    + " SET CATEGORY_ATTRIBUTE_VALUE = ? ,SYNC_FLAG_EC = ?, EXPORT_FLAG_ERP = ?,EXPORT_FLAG_WMS = ?,UPDATED_USER = ? ,UPDATED_DATETIME = ?  WHERE COMMODITY_CODE = ? ";
  
  //修改COMMODITY_HEADER表的商品目录路径
  public static final String UPDATE_COMMODITY_HEADER_CATEGORY_PATH_QUERY="UPDATE COMMODITY_HEADER "
    + " SET CATEGORY_PATH = ? ,UPDATED_USER = ? ,UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
  //修改COMMODITY_HEADER表的商品属性
  public static final String UPDATE_COMMODITY_HEADER_CATEGORY_ATTRIBUTE1_QUERY="UPDATE COMMODITY_HEADER "
    + " SET CATEGORY_ATTRIBUTE1 = ? ,UPDATED_USER = ? ,UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
  public static final String GET_CATEGORY_CODE_LIST_QUERY ="SELECT CATEGORY_CODE, COMMODITY_CODE, CATEGORY_ATTRIBUTE_NO,CATEGORY_ATTRIBUTE_VALUE,CATEGORY_ATTRIBUTE_VALUE_EN,CATEGORY_ATTRIBUTE_VALUE_JP " 
    +" FROM CATEGORY_ATTRIBUTE_VALUE WHERE COMMODITY_CODE = ? ORDER BY COMMODITY_CODE, CATEGORY_CODE,CATEGORY_ATTRIBUTE_NO ";
  //20120216 os013 add end
  public Class<RelatedCategory> getRowType() {
    return RelatedCategory.class;
  }
}
