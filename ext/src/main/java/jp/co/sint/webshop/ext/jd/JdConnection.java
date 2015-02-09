package jp.co.sint.webshop.ext.jd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.utility.DIContainer;

import org.apache.log4j.Logger;

public class JdConnection {

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

  public Connection getConnection() {
    return this.connection;
  }

  public PreparedStatement createPreparedStatement(String sql) throws SQLException {
    PreparedStatement stmt = DatabaseUtil.prepareStatement(getConnection(), sql);
    statements.add(stmt);
    return stmt;
  }

  public Object executeScalar(Query query) {
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

  public List<String> executeScalarGetList(Query query) {
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


}
