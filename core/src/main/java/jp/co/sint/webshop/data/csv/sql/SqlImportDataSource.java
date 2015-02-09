package jp.co.sint.webshop.data.csv.sql;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.ProcedureDelegate;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CommitMode;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.data.csv.ImportDataSource;
import jp.co.sint.webshop.data.csv.TransactionMode;
import jp.co.sint.webshop.service.data.CsvImportCondition;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

public abstract class SqlImportDataSource<S extends CsvSchema, I extends CsvImportCondition<S>> extends SqlDataSource<S, I>
    implements ImportDataSource<S, I> {

  private int maxErrorCount = 100;

  private List<PreparedStatement> statements = new ArrayList<PreparedStatement>();
  
  private String message;
  
  public boolean setSchema(List<String> csvLine) {
    return false;
  }
  
  public void beforeImport() {

  }

  public TransactionMode afterImport() {
    return TransactionMode.DEPEND_ON_RESULT;
  }

  public final void update(List<String> csvLine) {
    try {
      executeUpdate(csvLine);
      clearParameters();
    } catch (SQLException ex) {
      throw new CsvImportException(ex);
    }
  }

  public abstract void executeUpdate(List<String> csvLine) throws SQLException;

  protected void clearParameters() throws SQLException {
    for (PreparedStatement stmt : statements) {
      stmt.clearParameters();
    }
  }

  public boolean hasHeader() {
    return getCondition().hasHeader();
  }

  @Override
  protected void initializeResources() {
  }

  @Override
  protected void clearResources() {
    super.clearResources();
    for (Statement stmt : statements) {
      DatabaseUtil.closeResource(stmt);
    }
  }

  protected PreparedStatement createPreparedStatement(String sql) throws SQLException {
    PreparedStatement stmt = DatabaseUtil.prepareStatement(getConnection(), sql);
    statements.add(stmt);
    return stmt;
  }

  protected CallableStatement createCallableStatement(String proc) throws SQLException {
    CallableStatement stmt = getConnection().prepareCall(proc);
    statements.add(stmt);
    return stmt;
  }

  public CommitMode getCommitMode() {
    return CommitMode.FOR_EACH;
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

  public int getMaxErrorCount() {
    return this.maxErrorCount;
  }

  public void setMaxErrorCount(int maxErrorCount) {
    this.maxErrorCount = maxErrorCount;
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

  protected boolean exists(PreparedStatement selectStatement, Object... args) {
    Logger logger = Logger.getLogger(this.getClass());
    int result = 0;
    ResultSet rset = null;
    try {
      DatabaseUtil.bindParameters(selectStatement, args);
      rset = selectStatement.executeQuery();
      if (rset.next()) {
        result = rset.getInt(1);
      } else {
        throw new CsvImportException("row not found");
      }
      selectStatement.clearParameters();
    } catch (RuntimeException e) {
      logger.error(e);
      throw new DataAccessException(e);
    } catch (Exception e) {
      logger.error(e);
      throw new DataAccessException(e);
    } finally {
      DatabaseUtil.closeResource(rset);
    }
    return result > 0;
  }

  /**
   * 検索結果の最初の1件をBeanにして返します。 結果が存在しないときはnullを返します。
   * 
   * @param <R>
   *          戻り1行分のデータとなるBeanの型
   * @param query
   *          SQL
   * @param rowType
   *          戻り1行分のデータとなるBean型のClassオブジェクト
   * @return 検索結果の最初の1件
   */
  public <R>R loadAsBean(Query query, Class<R> rowType) {
    List<R> rows = loadAsBeanList(query, rowType);
    if (rows.size() > 0) {
      return rows.get(0);
    }
    return null;
  }

  /**
   * 検索結果をBeanのリストとして返します。 検索結果が0件のときは空のListを返します。
   * 
   * @param <R>
   *          戻り1行分のデータとなるBeanの型
   * @param query
   *          SQL
   * @param rowType
   *          戻り1行分のデータとなるBean型のClassオブジェクト
   * @return 検索結果を表すBeanのリスト
   */
  public <R>List<R> loadAsBeanList(Query query, Class<R> rowType) {

    Logger logger = Logger.getLogger(this.getClass());
    PreparedStatement pstmt = null;
    ResultSet rset = null;
    List<R> result = new ArrayList<R>();

    try {
      getConnection().setReadOnly(true);
      pstmt = DatabaseUtil.prepareStatement(getConnection(), query.getSqlString());
      DatabaseUtil.bindParameters(pstmt, query.getParameters());

      logger.debug("SQL statement: " + query.getSqlString());
      logger.debug("Parameters   : " + Arrays.toString(query.getParameters()));

      rset = pstmt.executeQuery();

      while (rset.next()) {
        R row = rowType.newInstance();
        copyResultToBean(rset, row);
        result.add(row);
      }

    } catch (Exception e) {
      logger.error(e);
      throw new DataAccessException(e);
    } finally {
      DatabaseUtil.closeResource(rset);
      DatabaseUtil.closeResource(pstmt);
    }

    return result;

  }

  /**
   * ストアドプロシージャを実行します。
   * 
   * @param delegate
   *          実行するストアドプロシージャ情報を含むProcedureDelegateオブジェクト
   */
  public void executeProcedure(ProcedureDelegate delegate) {
    Logger logger = Logger.getLogger(this.getClass());
    CallableStatement cstmt = null;
    try {
      cstmt = getConnection().prepareCall(SqlDialect.getDefault().getProcedureString(delegate.getStatement()));
      logger.debug("PROCEDURE:" + delegate.getStatement() + " start");
      delegate.execute(cstmt);
      logger.debug("PROCEDURE:" + delegate.getStatement() + " end");
    } catch (Throwable t) {
      logger.error(t);
      throw new DataAccessException(t);
    } finally {
      DatabaseUtil.closeResource(cstmt);
    }
  }

  /**
   * ResultSetのデータをBeanへコピー
   * 
   * @param rset
   * @param bean
   * @throws Exception
   */
  private static void copyResultToBean(ResultSet rset, Object bean) throws Exception {
    Class<? extends Object> rowType = bean.getClass();
    ResultSetMetaData rsmd = rset.getMetaData();
    int cols = rsmd.getColumnCount();
    for (int i = 0; i < cols; i++) {
      String key = rsmd.getColumnName(i + 1);
      Object value = rset.getObject(i + 1);
      Class<? extends Object> returnType = rowType.getMethod("get" + StringUtil.toPascalFormat(key), new Class[0]).getReturnType();

      if (value == null) {
        // オブジェクトがNULLの場合は処理を行わない
        Logger.getLogger(SqlImportDataSource.class).trace("value is null");
      } else if (returnType.equals(Date.class)) {
        value = rset.getTimestamp(i + 1);
//      add by V10-CH start
      } else if (returnType.equals(BigDecimal.class)) {
        value = rset.getBigDecimal(i + 1);
//      add by V10-CH end
      } else if (returnType.equals(Long.class)) {
        value = rset.getLong(i + 1);
      } else if (returnType.equals(String.class)) {
        value = rset.getString(i + 1);
      } else {
        throw new DataAccessException();
      }
      rowType.getMethod("set" + StringUtil.toPascalFormat(key), returnType).invoke(bean, value);
    }
  }

  public Long generateSequence(SequenceType sequenceType) {
//	postgreSQL start
    //String sqlString = MessageFormat.format("SELECT {0}.NEXTVAL FROM DUAL", sequenceType.getValue());
    String  sqlString = "SELECT " + SqlDialect.getDefault().getNextval(sequenceType,"{0}") + " FROM DUAL"; //$NON-NLS-1$
//	postgreSQL end
    Query query = new SimpleQuery(sqlString);
    Logger logger = Logger.getLogger(this.getClass());
    Long result = null;
    PreparedStatement stmt = null;
    ResultSet rset = null;
    try {
      stmt = DatabaseUtil.prepareStatement(getConnection(), query.getSqlString());
      DatabaseUtil.bindParameters(stmt, query.getParameters());
      rset = stmt.executeQuery();
      if (rset.next()) {
        result = rset.getLong(1);
      } else {
        result = null;
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

  /**
   * messageを取得します。
   *
   * @return message
   */
  public String getMessage() {
    return message;
  }

  
  /**
   * messageを設定します。
   *
   * @param message 
   *          message
   */
  public void setMessage(String message) {
    this.message = message;
  }
}
