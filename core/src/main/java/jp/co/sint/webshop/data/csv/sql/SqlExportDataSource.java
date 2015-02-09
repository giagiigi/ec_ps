package jp.co.sint.webshop.data.csv.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.csv.CsvData;
import jp.co.sint.webshop.data.csv.CsvExportException;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.data.csv.ExportDataSource;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.text.Messages;

import org.apache.log4j.Logger;

public class SqlExportDataSource<S extends CsvSchema, E extends CsvExportCondition<S>> extends SqlDataSource<S, E> implements
    ExportDataSource<S, E> {

  private PreparedStatement stmt;

  private ResultSet rset;

  private CsvData data;

  private Query exportQuery;

  protected Logger logger = Logger.getLogger(this.getClass());
  
  private List<PreparedStatement> statements = new ArrayList<PreparedStatement>();

  public void beforeExport() {

  }

  public void afterExport() {

  }

  public void onFetchData(List<String> csvLine) {
    
  }
  
  public boolean headerOnly() {
    return false;
  }
  
  public CsvData getData() {
    clearResources();
    try {
      Query query = getExportQuery();
      stmt = DatabaseUtil.prepareStatement(getConnection(), query.getSqlString());
      DatabaseUtil.bindParameters(stmt, query.getParameters());
      DatabaseUtil.logQuery(query);
      rset = stmt.executeQuery();
      data = new SqlCsvData(getSchema(), rset);
    } catch (SQLException ex) {
      throw new CsvExportException(ex);
    }
    logger.info(Messages.log("data.csv.sql.SqlExportDataSource.0"));
    return data;
  }

  protected void clearResources() {
    DatabaseUtil.closeResource(rset);
    DatabaseUtil.closeResource(stmt);
  }

  /**
   * exportQueryを返します。
   * 
   * @return the exportQuery
   */
  public Query getExportQuery() {
    return exportQuery;
  }
  //20120106 0S011 add start

  protected PreparedStatement createPreparedStatement(String sql) throws SQLException {
    PreparedStatement stmt = DatabaseUtil.prepareStatement(getConnection(), sql);
    statements.add(stmt);
    return stmt;
  }
  
  public void commit() {
    try {
      getConnection().commit();
    } catch (SQLException e) {
      throw new DataAccessException(e);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public void rollback() {
    try {
      getConnection().rollback();
    } catch (SQLException e) {
      throw new DataAccessException(e);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }
  //20120106 0S011 add end
  /**
   * exportQueryを設定します。
   * 
   * @param exportQuery
   *          設定する exportQuery
   */
  public void setExportQuery(Query exportQuery) {
    this.exportQuery = exportQuery;
  }

}
