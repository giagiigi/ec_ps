package jp.co.sint.webshop.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

public final class StockUtil {

  private static final int SUCCESSFULL = 0;

  private static final String STOCK_UTIL_LOG_FORMAT = "(shop={0}, sku={1}, qty={2}, iodate={3},user={4})";

  private StockUtil() {
  }

  public static boolean hasStock(StockUnit stockUnit) {
    return hasStock(toList(stockUnit));
  }

  public static boolean hasStock(List<StockUnit> stockUnitList) {
    return execStockOperation(StockOperation.QUERY, stockUnitList);
  }

  public static boolean hasReservingStock(StockUnit stockUnit) {
    return hasReservingStock(toList(stockUnit));
  }

  public static boolean hasReservingStock(List<StockUnit> stockUnitList) {
    return execStockOperation(StockOperation.QUERY_RESERVING, stockUnitList);
  }

  public static boolean allocate(StockUnit stockUnit) {
    return allocate(toList(stockUnit));
  }

  public static boolean allocate(List<StockUnit> stockUnitList) {
    return execStockOperation(StockOperation.ALLOCATE, stockUnitList);
  }

  public static boolean liquidatedAllocate(StockUnit stockUnit) {
    return liquidatedAllocate(toList(stockUnit));
  }

  public static boolean liquidatedAllocate(List<StockUnit> stockUnitList) {
    return execStockOperation(StockOperation.LIQUIDATED_ALLOCATE, stockUnitList);
  }

  public static boolean deallocate(StockUnit stockUnit) {
    return deallocate(toList(stockUnit));
  }

  public static boolean deallocate(List<StockUnit> stockUnitList) {
    return execStockOperation(StockOperation.DEALLOCATE, stockUnitList);
  }

  public static boolean entry(StockUnit stockUnit) {
    return entry(toList(stockUnit));
  }

  public static boolean entry(List<StockUnit> stockUnitList) {
    return execStockOperation(StockOperation.ENTRY, stockUnitList);
  }

  public static boolean deliver(StockUnit stockUnit) {
    return deliver(toList(stockUnit));
  }

  public static boolean deliver(List<StockUnit> stockUnitList) {
    return execStockOperation(StockOperation.DELIVER, stockUnitList);
  }

  public static boolean reserve(StockUnit stockUnit) {
    return reserve(toList(stockUnit));
  }

  public static boolean reserve(List<StockUnit> stockUnitList) {
    return execStockOperation(StockOperation.RESERVING, stockUnitList);
  }

  public static boolean liquidatedReserve(StockUnit stockUnit) {
    return liquidatedReserve(toList(stockUnit));
  }

  public static boolean liquidatedReserve(List<StockUnit> stockUnitList) {
    return execStockOperation(StockOperation.LIQUIDATED_RESERVING, stockUnitList);
  }

  public static boolean cancelReserve(StockUnit stockUnit) {
    return cancelReserve(toList(stockUnit));
  }

  public static boolean cancelReserve(List<StockUnit> stockUnitList) {
    return execStockOperation(StockOperation.CANCEL_RESERVING, stockUnitList);
  }

  public static boolean absolute(StockUnit stockUnit) {
    return absolute(toList(stockUnit));
  }

  public static boolean absolute(List<StockUnit> stockUnitList) {
    return execStockOperation(StockOperation.ABSOLUTE, stockUnitList);
  }

  private static boolean execStockOperation(StockOperation oper, List<StockUnit> stockUnitList) {
    Connection conn = null;
    boolean result = false;
    Logger logger = Logger.getLogger(StockUtil.class);
    try {
      DataSource ds = DIContainer.getWebshopDataSource();
      conn = ds.getConnection();
      conn.setAutoCommit(false);
      result = execStockOperation(oper, stockUnitList, conn);
      if (result) {
        conn.commit();
      } else {
        conn.rollback();
      }
    } catch (Exception e) {
      try {
        if (conn != null) {
          conn.rollback();
        }
      } catch (SQLException xx) {
        logger.error(xx);
      }
      logger.error(e);
      result = false;
    } finally {
      DatabaseUtil.closeResource(conn);
    }
    return result;
  }

  private static boolean execStockOperation(StockOperation oper, List<StockUnit> stockUnitList, Connection conn) {

    if (stockUnitList == null) {
      throw new IllegalArgumentException();
    }
    Collections.sort(stockUnitList);

    Logger logger = Logger.getLogger(StockUtil.class);
    logger.debug("Stock Operation has started: " + oper);
    CallableStatement cstmt = null;
    boolean result = false;
    try {
      cstmt = conn.prepareCall(oper.getCallableStatementString());

      for (StockUnit su : stockUnitList) {
        String message = getMessage(su);

        int i = 0;
        cstmt.setString(++i, su.getShopCode());
        cstmt.setString(++i, su.getSkuCode());
        cstmt.setInt(++i, su.getQuantity());
        cstmt.setString(++i, su.getLoginInfo().getRecordingFormat());

        // 入庫・出庫・絶対数更新のときは「入出庫日時」「注意事項」も必要
        if (oper == StockOperation.ENTRY || oper == StockOperation.ABSOLUTE || oper == StockOperation.DELIVER
            || oper == StockOperation.SHIPPING || oper == StockOperation.CANCEL_SHIPPING || oper == StockOperation.SHIFT ) {
          cstmt.setDate(++i, new java.sql.Date(su.getStockIODate().getTime()));
          cstmt.setString(++i, su.getMemo());
        }
        
        if( oper == StockOperation.DEALLOCATE) {
          if(!StringUtil.hasValue(su.getMemo()) ) {
            su.setMemo("EC");
          }
          cstmt.setString(++i, su.getMemo());
        }
        
        
        if(!DIContainer.getWebshopConfig().isPostgreSQL()){
        	cstmt.registerOutParameter(++i, Types.INTEGER);
        }
        
        cstmt.execute();
//      postgreSQL start         
        if(DIContainer.getWebshopConfig().isPostgreSQL()){
	        ResultSet resultset = cstmt.getResultSet();
	    	resultset.next();
	        if (resultset.getInt(1) != SUCCESSFULL) {
	            throw new StockOperationFailureException("Failed: " + oper + "/" + message);
	          } else {
	            logger.debug("OK : " + oper + message);
	          }
	    	resultset.close();
        } else {
//        	postgreSQL end         	
	        cstmt.clearParameters();
	
	        if (cstmt.getInt(i) != SUCCESSFULL) {
	          throw new StockOperationFailureException("Failed: " + oper + "/" + message);
	        } else {
	          logger.debug("OK : " + oper + message);
	        }
	      }
      }
      result = true;

    } catch (StockOperationFailureException e) {
      logger.info(e.getMessage());
      result = false;

    } catch (SQLException e) {
      logger.error(e);
      result = false;

    } catch (RuntimeException e) {
      logger.error(e);
      result = false;

    } finally {
      DatabaseUtil.closeResource(cstmt);
    }
    logger.debug("Stock Operation has finished: " + oper);
    return result;
  }

  private static String getMessage(StockUnit su) {
    Object[] args = new Object[] {
        su.getShopCode(), su.getSkuCode(), su.getQuantity(), su.getStockIODate(), su.getLoginInfo().getRecordingFormat()
    };
    return MessageFormat.format(STOCK_UTIL_LOG_FORMAT, args);
  }

  private static List<StockUnit> toList(StockUnit stockUnit) {
    List<StockUnit> list = new ArrayList<StockUnit>();
    list.add(stockUnit);
    return list;
  }
//postgreSQL start
  public static String getCallStart() {
	    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? "select * from " : "{call ";
	  }
  
  public static String getCallEnd() {
	    return DIContainer.getWebshopConfig().isPostgreSQL() == true ? ")" : ", ?)}";
	  }
  
  private static enum StockOperation {
	///** 問い合わせ(引当) */
	//QUERY("{CALL STOCK_QUERY(?, ?, ?, ?, ?)}"),
	///** 問い合わせ(予約) */
	//QUERY_RESERVING("{CALL STOCK_QUERY_RESERVING(?, ?, ?, ?, ?)}"),
	///** 入庫 */
	//ENTRY("{CALL STOCK_ENTRY(?, ?, ?, ?, ?, ?, ?)}"),
	///** 出庫 */
	//DELIVER("{CALL STOCK_DELIVER(?, ?, ?, ?, ?, ?, ?)}"),
	///** 引当 */
	//ALLOCATE("{CALL STOCK_ALLOCATE(?, ?, ?, ?, ?)}"),
	///** 引当(管理者調整) */
	//LIQUIDATED_ALLOCATE("{CALL STOCK_LIQUIDATED_ALLOCATE(?, ?, ?, ?, ?)}"),
	///** 予約 */
	//RESERVING("{CALL STOCK_RESERVING(?, ?, ?, ?, ?)}"),
	///** 予約(管理者調整) */
	//LIQUIDATED_RESERVING("{CALL STOCK_LIQUIDATED_RESERVING(?, ?, ?, ?, ?)}"),
	///** 予約キャンセル */
	//CANCEL_RESERVING("{CALL STOCK_CANCEL_RESERVING(?, ?, ?, ?, ?)}"),
	///** 絶対数更新 */
	//ABSOLUTE("{CALL STOCK_ABSOLUTE(?, ?, ?, ?, ?, ?, ?)}"),
	///** 引当取消 */
	//DEALLOCATE("{CALL STOCK_DEALLOCATE(?, ?, ?, ?, ?)}"),
	///** 受注への変更 */
	//SHIFT("{CALL STOCK_SHIFT(?, ?, ?, ?, ?, ?, ?)}"),
	///** 出荷 */
	//SHIPPING("{CALL STOCK_SHIPPING(?, ?, ?, ?, ?, ?, ?)}"),
	///** 出荷取消 */
	//CANCEL_SHIPPING("{CALL STOCK_CANCEL_SHIPPING(?, ?, ?, ?, ?, ?, ?)}");

    /** 問い合わせ(引当) */
    QUERY(getCallStart()+" STOCK_QUERY(?, ?, ?, ?"+getCallEnd()),
    /** 問い合わせ(予約) */
    QUERY_RESERVING(getCallStart()+" STOCK_QUERY_RESERVING(?, ?, ?, ?"+getCallEnd()),
    /** 入庫 */
    ENTRY(getCallStart()+" STOCK_ENTRY(?, ?, ?, ?, ?, ?"+getCallEnd()),
    /** 出庫 */
    DELIVER(getCallStart()+" STOCK_DELIVER(?, ?, ?, ?, ?, ?"+getCallEnd()),
    /** 引当 */
    ALLOCATE(getCallStart()+" STOCK_ALLOCATE(?, ?, ?, ?"+getCallEnd()),
    /** 引当(管理者調整) */
    LIQUIDATED_ALLOCATE(getCallStart()+" STOCK_LIQUIDATED_ALLOCATE(?, ?, ?, ?"+getCallEnd()),
    /** 予約 */
    RESERVING(getCallStart()+" STOCK_RESERVING(?, ?, ?, ?"+getCallEnd()),
    /** 予約(管理者調整) */
    LIQUIDATED_RESERVING(getCallStart()+" STOCK_LIQUIDATED_RESERVING(?, ?, ?, ?"+getCallEnd()),
    /** 予約キャンセル */
    CANCEL_RESERVING(getCallStart()+" STOCK_CANCEL_RESERVING(?, ?, ?, ?"+getCallEnd()),
    /** 絶対数更新 */
    ABSOLUTE(getCallStart()+" STOCK_ABSOLUTE(?, ?, ?, ?, ?, ?"+getCallEnd()),
    /** 引当取消 */
    DEALLOCATE(getCallStart()+" STOCK_DEALLOCATE(?, ?, ?,?, ?"+getCallEnd()),
    /** 受注への変更 */
    SHIFT(getCallStart()+" STOCK_SHIFT(?, ?, ?, ?, ?, ?"+getCallEnd()),
    /** 出荷 */
    SHIPPING(getCallStart()+" STOCK_SHIPPING(?, ?, ?, ?, ?, ?"+getCallEnd()),
    /** 出荷取消 */
    CANCEL_SHIPPING(getCallStart()+" STOCK_CANCEL_SHIPPING(?, ?, ?, ?, ?, ?"+getCallEnd());  
//  postgreSQL end    
    private String callableStatementString = "";

    private StockOperation(String callableStatementString) {
      this.callableStatementString = callableStatementString;
    }

    public String getCallableStatementString() {
      return callableStatementString;
    }
  }

  public static boolean hasStock(StockUnit stockUnit, Connection connection) {
    return hasStock(toList(stockUnit), connection);
  }

  public static boolean hasStock(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.QUERY, stockUnitList, connection);
  }

  public static boolean hasReservingStock(StockUnit stockUnit, Connection connection) {
    return hasReservingStock(toList(stockUnit), connection);
  }

  public static boolean hasReservingStock(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.QUERY_RESERVING, stockUnitList, connection);
  }

  public static boolean allocate(StockUnit stockUnit, Connection connection) {
    return allocate(toList(stockUnit), connection);
  }

  public static boolean allocate(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.ALLOCATE, stockUnitList, connection);
  }

  public static boolean liquidatedAllocate(StockUnit stockUnit, Connection connection) {
    return liquidatedAllocate(toList(stockUnit), connection);
  }

  public static boolean liquidatedAllocate(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.LIQUIDATED_ALLOCATE, stockUnitList, connection);
  }

  public static boolean deallocate(StockUnit stockUnit, Connection connection) {
    return deallocate(toList(stockUnit), connection);
  }

  public static boolean deallocate(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.DEALLOCATE, stockUnitList, connection);
  }

  public static boolean entry(StockUnit stockUnit, Connection connection) {
    return entry(toList(stockUnit), connection);
  }

  public static boolean entry(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.ENTRY, stockUnitList, connection);
  }

  public static boolean deliver(StockUnit stockUnit, Connection connection) {
    return deliver(toList(stockUnit), connection);
  }

  public static boolean deliver(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.DELIVER, stockUnitList, connection);
  }

  public static boolean reserve(StockUnit stockUnit, Connection connection) {
    return reserve(toList(stockUnit), connection);
  }

  public static boolean reserve(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.RESERVING, stockUnitList, connection);
  }

  public static boolean liquidatedReserve(StockUnit stockUnit, Connection connection) {
    return liquidatedReserve(toList(stockUnit), connection);
  }

  public static boolean liquidatedReserve(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.LIQUIDATED_RESERVING, stockUnitList, connection);
  }

  public static boolean cancelReserve(StockUnit stockUnit, Connection connection) {
    return cancelReserve(toList(stockUnit), connection);
  }

  public static boolean cancelReserve(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.CANCEL_RESERVING, stockUnitList, connection);
  }

  public static boolean absolute(StockUnit stockUnit, Connection connection) {
    return absolute(toList(stockUnit), connection);
  }

  public static boolean absolute(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.ABSOLUTE, stockUnitList, connection);
  }

  public static boolean changeToOrder(StockUnit stockUnit, Connection connection) {
    return changeToOrder(toList(stockUnit), connection);
  }

  public static boolean changeToOrder(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.SHIFT, stockUnitList, connection);
  }

  public static boolean shipping(StockUnit stockUnit, Connection connection) {
    return shipping(toList(stockUnit), connection);
  }

  public static boolean shipping(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.SHIPPING, stockUnitList, connection);
  }

  public static boolean cancelShipping(StockUnit stockUnit, Connection connection) {
    return cancelShipping(toList(stockUnit), connection);
  }

  public static boolean cancelShipping(List<StockUnit> stockUnitList, Connection connection) {
    return execStockOperation(StockOperation.CANCEL_SHIPPING, stockUnitList, connection);
  }

}
