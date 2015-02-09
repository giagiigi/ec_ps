package jp.co.sint.webshop.service.stock;

import jp.co.sint.webshop.data.domain.SyncFlagJd;

public final class StockServiceQuery {

  private static final long serialVersionUID = 1L;

  private StockServiceQuery() {
  }

  public static final String CHANGE_EC_ALLOCATED_QUANTITY =
    " SELECT SHOP_CODE, " +
    "        SKU_CODE, " +
    "        SUM(PURCHASING_AMOUNT) AS PURCHASING_AMOUNT " +
    "   FROM (SELECT OD.SHOP_CODE, " +
    "                CASE WHEN SCC.CHILD_COMMODITY_CODE IS NULL " +
    "                      AND CCH.ORIGINAL_COMMODITY_CODE IS NULL " +
    "                     THEN OD.COMMODITY_CODE " +
    "                     WHEN CCH.ORIGINAL_COMMODITY_CODE IS NULL " +
    "                     THEN SCC.CHILD_COMMODITY_CODE " +
    "                     ELSE CCH.ORIGINAL_COMMODITY_CODE " +
    "                 END AS SKU_CODE, " +
    "                CASE WHEN CCH.ORIGINAL_COMMODITY_CODE IS NULL " +
    "                     THEN OD.PURCHASING_AMOUNT " +
    "                     ELSE OD.PURCHASING_AMOUNT * CCH.COMBINATION_AMOUNT " +
    "                 END AS PURCHASING_AMOUNT " +
    "           FROM ORDER_DETAIL OD " +
    "          INNER JOIN C_COMMODITY_HEADER CCH " +
    "                  ON OD.SHOP_CODE = CCH.SHOP_CODE " +
    "                 AND OD.COMMODITY_CODE = CCH.COMMODITY_CODE " +
    "           LEFT JOIN SET_COMMODITY_COMPOSITION SCC " +
    "                  ON OD.SHOP_CODE = SCC.SHOP_CODE " +
    "                 AND OD.COMMODITY_CODE = SCC.COMMODITY_CODE " +
    "               WHERE OD.ORDER_NO = ? ) A " +
    "  GROUP BY SHOP_CODE, " +
    "           SKU_CODE " +
    "  ORDER BY SKU_CODE ";

  public static final String CHANGE_TM_ALLOCATED_QUANTITY =
    " SELECT SHOP_CODE, " +
    "        SKU_CODE, " +
    "        SUM(PURCHASING_AMOUNT) AS PURCHASING_AMOUNT " +
    "   FROM ( SELECT OD.SHOP_CODE, " +
    "                 CASE WHEN SCC.CHILD_COMMODITY_CODE IS NULL " +
    "                       AND CCH.ORIGINAL_COMMODITY_CODE IS NULL " +
    "                      THEN OD.COMMODITY_CODE " +
    "                      WHEN CCH.ORIGINAL_COMMODITY_CODE IS NULL " +
    "                      THEN SCC.CHILD_COMMODITY_CODE " +
    "                      ELSE CCH.ORIGINAL_COMMODITY_CODE " +
    "                  END AS SKU_CODE, " +
    "                 CASE WHEN CCH.ORIGINAL_COMMODITY_CODE IS NULL " +
    "                      THEN OD.PURCHASING_AMOUNT " +
    "                      ELSE OD.PURCHASING_AMOUNT * CCH.COMBINATION_AMOUNT " +
    "                  END AS PURCHASING_AMOUNT " +
    "            FROM TMALL_ORDER_DETAIL OD " +
    "           INNER JOIN C_COMMODITY_HEADER CCH " +
    "              ON OD.SHOP_CODE = CCH.SHOP_CODE " +
    "             AND OD.COMMODITY_CODE = CCH.COMMODITY_CODE " +
    "            LEFT JOIN SET_COMMODITY_COMPOSITION SCC " +
    "              ON OD.SHOP_CODE = SCC.SHOP_CODE " +
    "             AND OD.COMMODITY_CODE = SCC.COMMODITY_CODE " +
    "           WHERE OD.ORDER_NO = ? ) A " +
    "  GROUP BY SHOP_CODE, " +
    "           SKU_CODE " +
    "  ORDER BY SKU_CODE ";

  public static final String CHANGE_JD_ALLOCATED_QUANTITY =
    " SELECT SHOP_CODE, " +
    "        SKU_CODE, " +
    "        SUM(PURCHASING_AMOUNT) AS PURCHASING_AMOUNT " +
    "   FROM ( SELECT OD.SHOP_CODE, " +
    "                 CASE WHEN SCC.CHILD_COMMODITY_CODE IS NULL " +
    "                       AND CCH.ORIGINAL_COMMODITY_CODE IS NULL " +
    "                      THEN OD.COMMODITY_CODE " +
    "                      WHEN CCH.ORIGINAL_COMMODITY_CODE IS NULL " +
    "                      THEN SCC.CHILD_COMMODITY_CODE " +
    "                      ELSE CCH.ORIGINAL_COMMODITY_CODE " +
    "                  END AS SKU_CODE, " +
    "                 CASE WHEN CCH.ORIGINAL_COMMODITY_CODE IS NULL " +
    "                      THEN OD.PURCHASING_AMOUNT " +
    "                      ELSE OD.PURCHASING_AMOUNT * CCH.COMBINATION_AMOUNT " +
    "                  END AS PURCHASING_AMOUNT " +
    "            FROM JD_ORDER_DETAIL OD " +
    "           INNER JOIN C_COMMODITY_HEADER CCH " +
    "              ON OD.SHOP_CODE = CCH.SHOP_CODE " +
    "             AND OD.COMMODITY_CODE = CCH.COMMODITY_CODE " +
    "            LEFT JOIN SET_COMMODITY_COMPOSITION SCC " +
    "              ON OD.SHOP_CODE = SCC.SHOP_CODE " +
    "             AND OD.COMMODITY_CODE = SCC.COMMODITY_CODE " +
    "           WHERE OD.ORDER_NO = ? ) A " +
    "  GROUP BY SHOP_CODE, " +
    "           SKU_CODE " +
    "  ORDER BY SKU_CODE ";
  
  public static final String UPDATE_FLG_QUERY = "UPDATE STOCK SET API_SYNC_FLAG = ?, "
      + " UPDATED_USER = ? ,UPDATED_DATETIME = ? " 
      + " WHERE COMMODITY_CODE = ?  " 
      + " OR  COMMODITY_CODE IN "
      + " (SELECT SCC1.CHILD_COMMODITY_CODE "
      + " FROM SET_COMMODITY_COMPOSITION SCC1 INNER JOIN SET_COMMODITY_COMPOSITION SCC2 "
      + " ON SCC1.COMMODITY_CODE = SCC2.COMMODITY_CODE"
      + " WHERE SCC2.CHILD_COMMODITY_CODE = ? ) ";

  public static final String GET_STOCK = " SELECT * FROM STOCK WHERE COMMODITY_CODE = ? ";

  public static final String GET_TMALL_STOCK = " SELECT * FROM TMALL_STOCK WHERE COMMODITY_CODE = ? ";

  public static final String GET_TMALL_SUIT_COMMODITY = " SELECT * FROM TMALL_SUIT_COMMODITY "
      + " WHERE COMMODITY_CODE IN (SELECT COMMODITY_CODE FROM SET_COMMODITY_COMPOSITION "
      + " WHERE CHILD_COMMODITY_CODE = ? )" 
      + " ORDER BY SCALE_VALUE DESC, COMMODITY_CODE ";

  public static final String GET_TMALL_SUIT_DETAIL_COMMODITY = " SELECT * FROM TMALL_STOCK  "
      + " WHERE COMMODITY_CODE IN (SELECT CHILD_COMMODITY_CODE FROM SET_COMMODITY_COMPOSITION "
      + " WHERE COMMODITY_CODE = ? AND CHILD_COMMODITY_CODE <> ? )";

  public static final String GET_TMALL_STOCK_ALLOCATION = " SELECT * FROM TMALL_STOCK_ALLOCATION "
      + " WHERE ORIGINAL_COMMODITY_CODE = ? " 
      + " ORDER BY SCALE_VALUE DESC, COMMODITY_CODE ";

  public static final String GET_JD_STOCK = "SELECT * FROM JD_STOCK WHERE COMMODITY_CODE = ? ";

  public static final String GET_JD_SUIT_COMMODITY = " SELECT * FROM JD_SUIT_COMMODITY "
      + " WHERE COMMODITY_CODE IN (SELECT COMMODITY_CODE FROM SET_COMMODITY_COMPOSITION "
      + " WHERE CHILD_COMMODITY_CODE = ? )" 
      + " ORDER BY SCALE_VALUE DESC, COMMODITY_CODE ";

  public static final String GET_JD_SUIT_DETAIL_COMMODITY = " SELECT * FROM JD_STOCK  " 
      + " WHERE COMMODITY_CODE IN ("
      + " SELECT CHILD_COMMODITY_CODE FROM SET_COMMODITY_COMPOSITION "
      + " WHERE COMMODITY_CODE = ? AND CHILD_COMMODITY_CODE <> ? )";

  public static final String GET_JD_STOCK_ALLOCATION = " SELECT * FROM JD_STOCK_ALLOCATION "
      + " WHERE ORIGINAL_COMMODITY_CODE = ? " 
      + " ORDER BY SCALE_VALUE DESC, COMMODITY_CODE ";

  public static final String GET_STOCK_RATIO = "SELECT * FROM STOCK_RATIO WHERE COMMODITY_CODE = ? ";

  public static final String GET_C_COMMODITY_EXT = "SELECT * FROM C_COMMODITY_EXT WHERE COMMODITY_CODE = ? ";

  // 2014/06/05 库存更新对应 ob_李先超 add start
  public static final String GET_SYNC_COMMODITY_LIST = "SELECT * FROM STOCK WHERE API_SYNC_FLAG = " + SyncFlagJd.SYNCVISIBLE.getValue();
    
  //查找淘宝和京东商品ID
  public static final String GET_API_COMMODITY_ID = "SELECT * FROM C_COMMODITY_HEADER WHERE SHOP_CODE = ? AND REPRESENT_SKU_CODE = ?";
    
  //查找有效京东库存
  public static final String GET_JD_EFFECTIVE_STOCK = "SELECT * FROM JD_STOCK WHERE SHOP_CODE = ? AND SKU_CODE = ?";
    
  //查找有效京东库存
  public static final String GET_TMALL_EFFECTIVE_STOCK = "SELECT * FROM TMALL_STOCK WHERE SHOP_CODE = ? AND SKU_CODE = ?";
    
  //查找组合品List
  public static final String GET_JD_ALLOCATION_LIST = "SELECT CCH.COMMODITY_CODE, CCH.tmall_commodity_id, CCH.combination_amount, " 
    + "JSA.stock_quantity - JSA.allocated_quantity as effective_Quantity "
    + " FROM JD_STOCK_ALLOCATION JSA "
    + " INNER JOIN C_COMMODITY_HEADER CCH ON JSA.SHOP_CODE = CCH.SHOP_CODE AND JSA.ORIGINAL_COMMODITY_CODE = CCH.COMMODITY_CODE "
    + " WHERE JSA.ORIGINAL_COMMODITY_CODE = ? ";
    
  //查询套装品List
  public static final String GET_JD_COMPOSITION_LIST = "SELECT CCH.COMMODITY_CODE, CCH.tmall_commodity_id, JSC.stock_quantity-JSC.allocated_quantity as effective_Quantity" 
    + " FROM SET_COMMODITY_COMPOSITION SCC "
    + " INNER JOIN JD_SUIT_COMMODITY JSC ON SCC.COMMODITY_CODE = JSC.COMMODITY_CODE "
    + " INNER JOIN C_COMMODITY_HEADER CCH ON SCC.CHILD_COMMODITY_CODE = CCH.COMMODITY_CODE "
    + " WHERE SCC.CHILD_COMMODITY_CODE = ? ";
    
  //查询京东库存和引当数
    public static final String GET_ALL_JD_SUIT_COMMODITY = " SELECT * FROM JD_SUIT_COMMODITY WHERE COMMODITY_CODE = ? ";
    
  //查找淘宝组合品List
  public static final String GET_TMALL_ALLOCATION_LIST = "SELECT CCH.COMMODITY_CODE, CCH.tmall_commodity_id, CCH.combination_amount, " 
    + " TSA.stock_quantity - TSA.allocated_quantity as effective_Quantity " 
    + " FROM TMALL_STOCK_ALLOCATION TSA "
    + " INNER JOIN C_COMMODITY_HEADER CCH ON TSA.SHOP_CODE = CCH.SHOP_CODE AND TSA.ORIGINAL_COMMODITY_CODE = CCH.COMMODITY_CODE "
    + " WHERE TSA.ORIGINAL_COMMODITY_CODE = ? ";
    
  //查询TMALL套装品List
  public static final String GET_TMALL_COMPOSITION_LIST = "SELECT CCH.COMMODITY_CODE, CCH.tmall_commodity_id, TSC.stock_quantity-TSC.allocated_quantity as effective_Quantity" 
    + " FROM SET_COMMODITY_COMPOSITION SCC "
    + " INNER JOIN TMALL_SUIT_COMMODITY TSC ON SCC.COMMODITY_CODE = TSC.COMMODITY_CODE "
    + " INNER JOIN C_COMMODITY_HEADER CCH ON SCC.CHILD_COMMODITY_CODE = CCH.COMMODITY_CODE "
    + " WHERE SCC.CHILD_COMMODITY_CODE = ? ";
    
  //查询京东库存和引当数
  public static final String GET_ALL_TMALL_SUIT_COMMODITY = " SELECT * FROM TMALL_SUIT_COMMODITY WHERE COMMODITY_CODE = ? ";
    
  //套装明细商品List
  public static final String GET_COMMODITY_COMPOSITION_LIST = "SELECT * FROM SET_COMMODITY_COMPOSITION WHERE COMMODITY_CODE = ? ";
    
  //临时库存对象商品的取得
    public static final String GET_STOCK_TEMP_SKU_CODE = "SELECT DISTINCT SKU_CODE, SKU_CODE as commodity_CODE FROM STOCK_TEMP WHERE STOCK_CHANGE_TYPE IN (0,1,2,3,4)";
    
  //对象临时库存商品一览取得
  public static final String GET_STOCK_TEMP_LIST = "SELECT * FROM STOCK_TEMP WHERE SHOP_CODE = ? AND SKU_CODE = ? AND STOCK_CHANGE_TYPE IN (0,1,2,3,4)";
    
  public static final String UPDATE_STOCK_OF_CHILD_COMMODITY = "UPDATE STOCK SET stock_quantity = stock_quantity + stock_threshold, UPDATED_USER = ? , UPDATED_DATETIME = ? WHERE sku_code= ?";
  
  public static final String GET_STOCK_TEMP_BY_CODE="SELECT * FROM STOCK_TEMP WHERE SHOP_CODE = ? AND sku_code= ? AND STOCK_CHANGE_TYPE = ?";
  
  public static final String DELETE_STOCK_TEMP_NOT_SYNC="DELETE FROM STOCK_TEMP WHERE (STOCK_CHANGE_TYPE = 5 " +
  		" AND sku_code IN (SELECT COMMODITY_CODE FROM C_COMMODITY_HEADER WHERE TMALL_COMMODITY_ID IS NULL)" +
  		" AND SKU_CODE NOT IN(SELECT SKU_CODE FROM COMMODITY_SKU WHERE JD_USE_FLAG = 1) )" +
  		" OR (STOCK_CHANGE_TYPE = 6 AND sku_code IN (SELECT COMMODITY_CODE FROM C_COMMODITY_HEADER WHERE JD_COMMODITY_ID IS NULL)" +
  		" AND SKU_CODE NOT IN(SELECT SKU_CODE FROM COMMODITY_SKU WHERE JD_USE_FLAG = 1) )";
  
  public static final String SELECT_STOCK_TEMP_SYNC="SELECT *,SKU_CODE AS COMMODITY_CODE FROM STOCK_TEMP WHERE STOCK_CHANGE_TYPE = 5 OR STOCK_CHANGE_TYPE = 6 ORDER BY SKU_CODE";
  // 2014/06/05 库存更新对应 ob_李先超 add end
  
  public static final String GET_COMMODITY_MASTER_BY_SKU_CODE = "SELECT CM.*  FROM COMMODITY_SKU CS"+
      " INNER JOIN COMMODITY_MASTER CM ON CS.COMMODITY_CODE=CM.COMMODITY_CODE  "+
      " WHERE SKU_CODE=? ";
  public static final String GET_COMMODITY_SKU_BY_SKU_CODE = "SELECT *  FROM COMMODITY_SKU CS WHERE SKU_CODE=? ";
  
  
}