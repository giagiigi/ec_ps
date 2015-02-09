package jp.co.sint.webshop.ext.tmall.getapi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.service.tmall.TmallCommoditySku;
import jp.co.sint.webshop.utility.DIContainer;

public class TmallConnection {

  private Connection connection;

  private List<PreparedStatement> statements = new ArrayList<PreparedStatement>();

  // 连接初始化
  public void init() {
    try {
      DataSource ds = DIContainer.getWebshopDataSource();
      connection = ds.getConnection();
      connection.setAutoCommit(false);
    } catch (SQLException e) {
      dispose();
      throw new DataAccessException(e);
    }
  }

  // 连接销毁
  public void dispose() {
    DatabaseUtil.closeResource(connection);
  }

  protected Connection getConnection() {
    return this.connection;
  }

  protected PreparedStatement createPreparedStatement(String sql) throws SQLException {
    PreparedStatement stmt = DatabaseUtil.prepareStatement(getConnection(), sql);
    statements.add(stmt);
    return stmt;
  }

  protected Object executeScalar(Query query) {
    Logger logger = Logger.getLogger(this.getClass());

    Object result = null;

    PreparedStatement stmt = null;
    ResultSet rset = null;
    try {
      stmt = DatabaseUtil.prepareStatement(getConnection(), query.getSqlString());
      DatabaseUtil.bindParameters(stmt, query.getParameters());
      rset = stmt.executeQuery();
      if (rset.next()) {
        result = rset.getObject(1);
      }
    } catch (Exception e) {
      logger.error(e);
      throw new DataAccessException(e);
    } finally {
      DatabaseUtil.closeResource(rset);
      DatabaseUtil.closeResource(stmt);
    }
    return result;
  }

  protected List<String> executeScalarGetList(Query query) {
    List<String> result = new ArrayList<String>();
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement stmt = null;
    ResultSet rset = null;
    try {
      stmt = DatabaseUtil.prepareStatement(getConnection(), query.getSqlString());
      DatabaseUtil.bindParameters(stmt, query.getParameters());
      rset = stmt.executeQuery();
      while (rset.next()) {
        result.add(rset.getString(1));
      }
    } catch (Exception e) {
      logger.error(e);
      throw new DataAccessException(e);
    } finally {
      DatabaseUtil.closeResource(rset);
      DatabaseUtil.closeResource(stmt);
    }
    return result;
  }

  protected Map<String, String> executeScalarGetListTmallStock(Query query) {
    Map<String, String> map = new HashMap<String, String>();
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement stmt = null;
    ResultSet rset = null;
    try {
      stmt = DatabaseUtil.prepareStatement(getConnection(), query.getSqlString());
      DatabaseUtil.bindParameters(stmt, query.getParameters());
      rset = stmt.executeQuery();
      while (rset.next()) {
        map.put(rset.getString(1), rset.getString(2));
      }
    } catch (Exception e) {
      logger.error(e);
      throw new DataAccessException(e);
    } finally {
      DatabaseUtil.closeResource(rset);
      DatabaseUtil.closeResource(stmt);
    }
    return map;
  }
  
  protected List<TmallCommoditySku> executeGetListTmallStock(Query query) {
    List<TmallCommoditySku> tcsList = null;
    Logger logger = Logger.getLogger(this.getClass());

    PreparedStatement stmt = null;
    ResultSet rset = null;

    try {
      tcsList = new ArrayList<TmallCommoditySku>();
      stmt = DatabaseUtil.prepareStatement(getConnection(), query.getSqlString());
      DatabaseUtil.bindParameters(stmt, query.getParameters());
      rset = stmt.executeQuery();
      while (rset.next()) {
        TmallCommoditySku tcs = new TmallCommoditySku();
        tcs.setNumiid(rset.getString("TMALL_COMMODITY_ID"));
        tcs.setSkuId(rset.getString("TMALL_SKU_ID"));
        tcs.setQuantity(rset.getString("NUM"));
        tcs.setOuterId(rset.getString("SKU_CODE"));
        // tmall使用标志临时使用字段
        tcs.setProperties(rset.getString("TMALL_USE_FLG"));
        // 对比库存专用start
        tcs.setStockTotal(rset.getString("STOCK_TOTAL"));
        tcs.setStockQuantity(rset.getString("STOCK_QUANTITY"));
        tcs.setStockTmall(rset.getString("STOCK_TMALL"));
        tcs.setAllocatedQuantity(rset.getString("ALLOCATED_QUANTITY"));
        tcs.setAllocatedTmall(rset.getString("ALLOCATED_TMALL"));
        tcs.setStockThreshold(rset.getString("STOCK_THRESHOLD"));
        tcs.setShareRatio(rset.getString("SHARE_RATIO"));
        // 对比库存专用end
        tcs.setUpdateType("1");
        tcsList.add(tcs);
      }
    } catch (Exception e) {
      logger.error(e);
      throw new DataAccessException(e);
    } finally {
      DatabaseUtil.closeResource(rset);
      DatabaseUtil.closeResource(stmt);
    }
    return tcsList;
  }

}
