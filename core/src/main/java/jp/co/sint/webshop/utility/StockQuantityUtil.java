package jp.co.sint.webshop.utility;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SimpleQuery;

public final class StockQuantityUtil {

  /**
   * 组合商品数量
   * @param commodityCode
   * @return
   */
  public static Long getTmallStockAllocationNum(String commodityCode, Long groupQuantity) {
    String sql = "SELECT SUM(STOCK_QUANTITY-ALLOCATED_QUANTITY) FROM TMALL_STOCK_ALLOCATION WHERE COMMODITY_CODE = ?";
    SimpleQuery query = new SimpleQuery(sql, commodityCode);
    Long tmallNum = 0L;
    try {
      tmallNum = DatabaseUtil.executeScalar(query, Long.class);
      if (tmallNum == null) {
        tmallNum = 0L;
      }
    } catch (Exception e) {
      tmallNum = 0L;
    }
    //库存数量 / 组合数
    tmallNum = tmallNum / groupQuantity;
    return tmallNum;
  }
  
  public static Long getJdStockAllocationNum(String commodityCode, Long groupQuantity) {
    String sql = "SELECT SUM(STOCK_QUANTITY-ALLOCATED_QUANTITY) FROM JD_STOCK_ALLOCATION WHERE COMMODITY_CODE = ?";
    SimpleQuery query = new SimpleQuery(sql, commodityCode);
    Long tmallNum = 0L;
    try {
      tmallNum = DatabaseUtil.executeScalar(query, Long.class);
      if (tmallNum == null) {
        tmallNum = 0L;
      }
    } catch (Exception e) {
      tmallNum = 0L;
    }
    //库存数量 / 组合数
    tmallNum = tmallNum / groupQuantity;
    return tmallNum;
  }
  

  /**
   * 原商品被分配库存数量
   * @param commodityCode
   * @return
   */
  public static Long getOriginalTmallStockAllocationNum(String commodityCode) {
    String sql = "SELECT SUM(STOCK_QUANTITY - ALLOCATED_QUANTITY) FROM TMALL_STOCK_ALLOCATION WHERE ORIGINAL_COMMODITY_CODE = ?";
    SimpleQuery query = new SimpleQuery(sql, commodityCode);
    Long tmallNum = 0L;
    try {
      tmallNum = DatabaseUtil.executeScalar(query, Long.class);
      if (tmallNum == null) {
        tmallNum = 0L;
      }
    } catch (Exception e) {
      tmallNum = 0L;
    }
    return tmallNum;
  }
  
  /**
   * 原商品库存数量
   * @param commodityCode
   * @return
   */
  public static Long queryStockNumByCommodityCode(String commodityCode) {
    String sql = "SELECT SUM(S.STOCK_TMALL-S.ALLOCATED_TMALL) FROM STOCK S WHERE S.COMMODITY_CODE = ?";
    SimpleQuery query = new SimpleQuery(sql, commodityCode);
    Long tmallNum = 0L;
    try {
      tmallNum = DatabaseUtil.executeScalar(query, Long.class);
      if (tmallNum == null) {
        tmallNum = 0L;
      }
    } catch (Exception e) {
      tmallNum = 0L;
    }
    return tmallNum;
  }
  
  /**
   * tmall单品库存数
   * @param commodityCode
   * @return
   */
  public static Long queryTmallNumsByCommodityCode(String commodityCode) {
    String sql = "SELECT S.STOCK_QUANTITY-S.ALLOCATED_QUANTITY FROM TMALL_STOCK S WHERE S.COMMODITY_CODE = ?";
    SimpleQuery query = new SimpleQuery(sql, commodityCode);
    Long tmallNum = 0L;
    try {
      tmallNum = DatabaseUtil.executeScalar(query, Long.class);
      if (tmallNum == null) {
        tmallNum = 0L;
      }
    } catch (Exception e) {
      tmallNum = 0L;
    }
    return tmallNum;
  }
  
  public static Long queryJdNumsByCommodityCode(String commodityCode) {
    String sql = "SELECT S.STOCK_QUANTITY-S.ALLOCATED_QUANTITY FROM JD_STOCK S WHERE S.COMMODITY_CODE = ?";
    SimpleQuery query = new SimpleQuery(sql, commodityCode);
    Long tmallNum = 0L;
    try {
      tmallNum = DatabaseUtil.executeScalar(query, Long.class);
      if (tmallNum == null) {
        tmallNum = 0L;
      }
    } catch (Exception e) {
      tmallNum = 0L;
    }
    return tmallNum;
  }
  
  /**
   * 原商品被分配TMALL库存引当数量
   * @param commodityCode
   * @return
   */
  public static Long getOriginalTmallStockAllocationQUANTITY(String commodityCode) {
    String sql = "SELECT SUM(ALLOCATED_QUANTITY) FROM TMALL_STOCK_ALLOCATION WHERE ORIGINAL_COMMODITY_CODE = ?";
    SimpleQuery query = new SimpleQuery(sql, commodityCode);
    Long tmallNum = 0L;
    try {
      tmallNum = DatabaseUtil.executeScalar(query, Long.class);
      if (tmallNum == null) {
        tmallNum = 0L;
      }
    } catch (Exception e) {
      tmallNum = 0L;
    }
    return tmallNum;
  }
  
  
  /**
   * 原商品被分配TMALL库存数量
   * @param commodityCode
   * @return
   */
  public static Long getOriginalTmallStockQUANTITY(String commodityCode) {
    String sql = "SELECT SUM(STOCK_QUANTITY) FROM TMALL_STOCK_ALLOCATION WHERE ORIGINAL_COMMODITY_CODE = ?";
    SimpleQuery query = new SimpleQuery(sql, commodityCode);
    Long tmallNum = 0L;
    try {
      tmallNum = DatabaseUtil.executeScalar(query, Long.class);
      if (tmallNum == null) {
        tmallNum = 0L;
      }
    } catch (Exception e) {
      tmallNum = 0L;
    }
    return tmallNum;
  }
  // 2014/06/10 库存更新对应 ob_卢 add start
  /**
   * 原商品被分配京东库存数量
   * @param commodityCode
   * @return
   */
  public static Long getOriginalJdStockQuantity(String commodityCode) {
    String sql = "SELECT SUM(STOCK_QUANTITY) FROM JD_STOCK_ALLOCATION WHERE ORIGINAL_COMMODITY_CODE = ?";
    SimpleQuery query = new SimpleQuery(sql, commodityCode);
    Long tmallNum = 0L;
    try {
      tmallNum = DatabaseUtil.executeScalar(query, Long.class);
      if (tmallNum == null) {
        tmallNum = 0L;
      }
    } catch (Exception e) {
      tmallNum = 0L;
    }
    return tmallNum;
  }
  // 2014/06/10 库存更新对应 ob_卢 add end
  
  
}
