package jp.co.sint.webshop.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * データアクセスコントローラです。
 * 
 * @author System Integrator Corp.
 */
public final class DatabaseUtil {

  public static final String WEBSHOP_DATASOURCE = "webshopDataSource";

  public static final Long DEFAULT_ORM_ROWID = -1L;

  private DatabaseUtil() {
    // empty constructor
  }

  private static Connection getConnection() throws SQLException {
    DataSource ds = DIContainer.getWebshopDataSource();
    return ds.getConnection();
  }

  private static Connection getReadOnlyConnection() throws SQLException {
    Connection conn = getConnection();
    conn.setReadOnly(true);
    return conn;
  }

  private static Connection getTransactionalConnection() throws SQLException {
    Connection conn = getConnection();
    conn.setAutoCommit(false);
    return conn;
  }

  private static Connection getAutoCommitConnection() throws SQLException {
    Connection conn = getConnection();
    conn.setAutoCommit(true);
    return conn;
  }

  public static PreparedStatement prepareStatement(Connection conn, String sqlString) throws SQLException {
    // デフォルトのオプションでPreparedStatementを生成
    return prepareStatement(conn, sqlString, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
  }

  private static PreparedStatement prepareStatement(Connection conn, String sqlString, int resultSetType, int resultSetConcurrency)
      throws SQLException {
    // ここだけどうしてもFindBugs[L S SQL]のWarningが出てしまう
    return conn.prepareStatement(sqlString, resultSetType, resultSetConcurrency);
  }

  public static <T extends WebshopEntity>List<String> getColumnNames(Class<T> dtoClass) {
    return SqlUtil.getColumnNames(dtoClass);
  }

  public static <T extends WebshopEntity>String getTableName(Class<T> dtoClass) {
    return SqlUtil.getTableName(dtoClass);
  }

  public static <T extends WebshopEntity>String getColumnNamesCsv(Class<T> dtoClass) {
    return SqlUtil.getColumnNamesCsv(dtoClass);
  }

  public static <T extends WebshopEntity>String getSelectAllQuery(Class<T> dtoClass) {
    return SqlUtil.getSelectAllQuery(dtoClass);
  }

  public static <T extends WebshopEntity>String getInsertQuery(Class<T> dtoClass) {
    return SqlUtil.getInsertQuery(dtoClass);
  }

  public static <T extends WebshopEntity>String getUpdateQuery(Class<T> dtoClass) {
    return SqlUtil.getUpdateQuery(dtoClass);
  }

  public static <T extends WebshopEntity>String getDeleteQuery(Class<T> dtoClass) {
    return SqlUtil.getDeleteQuery(dtoClass);
  }

  /**
   * 更新者情報を設定します。
   * 
   * @param entity
   *          エンティティ
   * @param loginInfo
   *          ログインユーザ情報
   */
  public static void setUserStatus(WebshopEntity entity, LoginInfo loginInfo) {
    if (entity != null) {
      entity.setUpdatedUser(loginInfo.getRecordingFormat());
      if (entity.getOrmRowid().equals(DEFAULT_ORM_ROWID)) {
        entity.setCreatedUser(loginInfo.getRecordingFormat());
        entity.setCreatedDatetime(DateUtil.getSysdate());
        entity.setUpdatedDatetime(DateUtil.getSysdate());
      }
    }
  }

  /**
   * ストアドプロシージャを実行します。
   * 
   * @param procedure
   *          ストアドプロシージャ
   * @return 結果オブジェクト
   */
  public static StoredProcedureResult executeProcedure(StoredProcedure procedure) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    Connection conn = null;
    CallableStatement cstmt = null;
    StoredProcedureResult result = new StoredProcedureResult();

    try {
      conn = getTransactionalConnection();
      String procedureString = SqlDialect.getDefault().getProcedureString(procedure.getProcedureString());
      logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.0"), procedureString));
      cstmt = conn.prepareCall(procedureString);
      // 10.1.1 10020 削除 ここから
      // logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.1"), procedureString));
      // 10.1.1 10020 削除 ここまで

      StoredProcedureParameter<?>[] parameters = procedure.getParameters();

      for (int i = 0; i < parameters.length; i++) {
        StoredProcedureParameter<?> param = parameters[i];
        if (param.isOutput()) {
          //postgreSQL start ljb
          if (DIContainer.getWebshopConfig().isOracle()) {
              cstmt.registerOutParameter(i + 1, param.getSqlType());
          }
          //postgreSQL end ljb
        } else {
          Object obj = param.getValue();
          cstmt.setObject(i + 1, adjustValue(obj), getSqlType(param.getValue()));
        }
      }

      cstmt.execute();
      //postgreSQL start ljb
      if (DIContainer.getWebshopConfig().isPostgreSQL()) {
          ResultSet rs = cstmt.getResultSet();
          rs.next();
          result.getParameters().put("r_result", rs.getInt(1));
          rs.close();
      }
      //postgreSQL end ljb      
      // 10.1.1 10020 追加 ここから
      logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.1"), procedureString));
      // 10.1.1 10020 追加 ここまで

      for (int i = 0; i < parameters.length; i++) {
        StoredProcedureParameter<?> param = parameters[i];
        //postgreSQL start ljb
        if (param.isOutput() && DIContainer.getWebshopConfig().isOracle()) {
          result.getParameters().put(param.getName(), cstmt.getObject(i + 1));
        }
        //postgreSQL end ljb
      }
      //postgreSQL start ljb 
      if (DIContainer.getWebshopConfig().isPostgreSQL()) {
        if (result.getParameters().get("r_result").toString().equals(StoredProceduedResultType.SUCCESS.getValue())) {
          conn.commit();
        } else {
          rollbackOnFailure(conn);
        }
      } else {
        if (procedure.commitOnSuccess()) {
          conn.commit();
        }
      }
      //postgreSQL end ljb 
    } catch (SQLException ex) {
      logger.error(ex);

      //postgreSQL start ljb 
      if (DIContainer.getWebshopConfig().isPostgreSQL()) {
        rollbackOnFailure(conn);
      } else {
        if (procedure.commitOnSuccess()) {
          rollbackOnFailure(conn);
        }
      }
      //postgreSQL end ljb 

      result.setErrorCode(ex.getErrorCode());
      result.setErrorMessage(ex.getMessage());

    } catch (Exception ex) {

      logger.error(ex);
      throw new DataAccessException(ex);

    } finally {
      closeAll(null, cstmt, conn);
    }
    return result;
  }

  /**
   * データベースサーバのシステム時刻を取得します。
   * 
   * @return システム時刻(年月日、時分秒まで)
   */
  public static Date getSystemDatetime() {
    Query query = new SimpleQuery(SqlDialect.getDefault().getSystemDateQuery());
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    Date result = null;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rset = null;
    try {
      conn = getReadOnlyConnection();
      stmt = prepareStatement(conn, query.getSqlString());
      bindParameters(stmt, query.getParameters());
      logQuery(query);
      rset = stmt.executeQuery();
      if (rset.next()) {
        Timestamp ts = rset.getTimestamp(1);
        if (ts != null) {
          result = new Date(ts.getTime());
        }
      }
    } catch (Exception e) {
      logger.error(e);
      throw new DataAccessException(e);
    } finally {
      closeAll(rset, stmt, conn);
    }
    return result;
  }

  public static int getSqlType(Object obj) {
    int result = Types.OTHER;
    if (obj == null) {
      result = Types.NULL;
    } else if (obj instanceof Date) {
      result = Types.TIMESTAMP;
    } else if (obj instanceof Number) {
      result = Types.NUMERIC;
    } else if (obj instanceof String) {
      result = Types.VARCHAR;
    } else {
      result = Types.OTHER;
    }
    return result;
  }

  /**
   * 検索結果をMapのリストとして返します。 検索結果が0件のときは空のListを返します。
   * 
   * @param query
   *          SQL
   * @return 検索結果をMapのリストとして返します。
   */
  public static List<Map<String, String>> loadAsMapList(Query query) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rset = null;
    List<Map<String, String>> result = new ArrayList<Map<String, String>>();
    try {
      conn = getReadOnlyConnection();
      pstmt = prepareStatement(conn, query.getSqlString());
      bindParameters(pstmt, query.getParameters());
      logQuery(query);

      rset = pstmt.executeQuery();
      while (rset.next()) {
        Map<String, String> map = new HashMap<String, String>();
        copyResultToMap(rset, map);
        result.add(map);
      }

    } catch (Exception e) {
      logger.error(e);
      throw new DataAccessException(e);
    } finally {
      closeAll(rset, pstmt, conn);
    }

    return result;

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
  public static <R>R loadAsBean(Query query, Class<R> rowType) {
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
  public static <R>List<R> loadAsBeanList(Query query, Class<R> rowType) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rset = null;
    List<R> result = new ArrayList<R>();
    try {
      conn = getReadOnlyConnection();
      pstmt = prepareStatement(conn, query.getSqlString());
      bindParameters(pstmt, query.getParameters());
      logQuery(query);
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
      closeAll(rset, pstmt, conn);
    }

    return result;
  }
  //soukai add ob 2011/12/21 start
  /**
   * 検索結果をBeanのリストとして返します。 検索結果が0件のときは空のListを返します。
   *
   * @param <R>
   *          戻り1行分のデータとなるBeanの型
   * @param connection
   *          con
   * @param query
   *          SQL
   * @param rowType
   *          戻り1行分のデータとなるBean型のClassオブジェクト
   * @return 検索結果を表すBeanのリスト
   */
  public static <R>List<R> loadAsBeanList(Connection connection, Query query, Class<R> rowType) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    PreparedStatement pstmt = null;
    ResultSet rset = null;
    List<R> result = new ArrayList<R>();
    try {
      pstmt = prepareStatement(connection, query.getSqlString());
      bindParameters(pstmt, query.getParameters());
      logQuery(query);
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
      closeResource(rset);
      closeResource(pstmt);
    }

    return result;
  }
  //soukai add ob 2011/12/21 end
  /**
   * 更新クエリを発行します。 トランザクションは即時コミットされます。
   * 
   * @param query
   *          更新クエリ
   * @return 更新結果
   */
  public static UpdateResult executeUpdate(UpdateQuery<?> query) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    Connection conn = null;
    PreparedStatement pstmt = null;
    UpdateResult result = new UpdateResult();
    try {
      conn = getConnection();
      if (query.isTransacion()) {
        conn = getTransactionalConnection();
      } else {
        conn = getAutoCommitConnection();
      }
      pstmt = prepareStatement(conn, query.getSqlString());

      logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.2"), query.getSqlString()));
      for (Object[] params : query.getUpdateParameters()) {
        logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.3"), Arrays.toString(params)));
        bindParameters(pstmt, params);
        int updCount = pstmt.executeUpdate();
        logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.4"), updCount));
      }

      if (query.isTransacion()) {
        conn.commit();
        logger.debug(Messages.log("data.DatabaseUtil.5"));
      }
    } catch (Exception e) {
      logger.error(e);
      if (query.isTransacion()) {
        rollbackOnFailure(conn);
        logger.debug(Messages.log("data.DatabaseUtil.6"));
      }
      throw new DataAccessException(e);
    } finally {
      closeAll(null, pstmt, conn);
    }

    return result;
  }

  /**
   * ページ設定つきのSQLを発行して、検索結果を取得します。 <br>
   * ページ検索条件は「ページ番号」「１ページの件数」「最大取得件数」で構成されています。
   * このメソッドでは結果セットに含まれる全ての行を取得せず、先頭から「最大取得件数+1」件分のデータのみ取得します。 <h4>note:</h4>
   * <p>
   * 検索結果の件数が指定された閾値(最大取得件数)を超える場合、改めて"SELECT COUNT(*) FROM ( [元のSQL文]
   * )"を発行することで データ件数を取得しています。
   * </p>
   * 
   * @param <R>
   *          戻り1行分のデータとなるBeanの型
   * @param query
   *          検索に使用する問い合わせ
   * @return Beanのリストと検索ステータスの複合オブジェクト(SearchResult)で検索結果を返します。
   */
  public static <R>SearchResult<R> executeSearch(SearchQuery<R> query) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    logQuery(query);

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rset = null;
    List<R> result = new ArrayList<R>();
    SearchResult<R> searchResult = new SearchResult<R>();
    searchResult.setMaxFetchSize(query.getMaxFetchSize());

    long searchStarted = 0L;
    long searchFinished = 0L;
    try {
      conn = getReadOnlyConnection();

      pstmt = prepareStatement(conn, query.getSqlString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      int maxFetch = query.getMaxFetchSize();
      if (maxFetch > 1) {
        pstmt.setMaxRows(query.getMaxFetchSize() + 1);
      }
      logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.7"), pstmt.getMaxRows()));
      bindParameters(pstmt, query.getParameters());

      searchStarted = System.currentTimeMillis();
      rset = pstmt.executeQuery();
      rset.last();
      int countAmount = rset.getRow();
      if (countAmount > query.getMaxFetchSize() && query.getMaxFetchSize() > 0) {
        searchResult.setOverflow(true);
        logger.warn(MessageFormat.format(Messages.log("data.DatabaseUtil.8"), query.getMaxFetchSize()));
      }
      rset.beforeFirst();

      int pageNo = query.getPageNumber();
      if (pageNo < 1) {
        logger.warn(MessageFormat.format(Messages.log("data.DatabaseUtil.9"), pageNo));
        pageNo = 1;
      }
      int pageSize = query.getPageSize();
      if (pageSize < 1) {
        logger.warn(MessageFormat.format(Messages.log("data.DatabaseUtil.10"), pageSize));
        pageSize = 10;
      }
      int start = pageSize * (pageNo - 1);
      int rowCount = 0;

      logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.11"), countAmount));
      logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.12"), (start + 1)));
      logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.13"), pageSize));

      // e.g. amount=100 size=40,page=4+ のとき
      if (countAmount < start) {
        logger.debug(Messages.log("data.DatabaseUtil.14"));

        int adjustedPageNo = ((countAmount / pageSize) + 1);
        int adjustedStart = pageSize * (adjustedPageNo - 1);

        logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.15"), adjustedPageNo));
        logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.16"), adjustedStart));

        start = adjustedStart;
        pageNo = adjustedPageNo;

      } else {
        logger.trace(MessageFormat.format(Messages.log("data.DatabaseUtil.17"), countAmount, start, pageSize));
      }

      ResultSetMetaData meta = rset.getMetaData();
      String[] columns = new String[meta.getColumnCount()];
      for (int i = 0; i < columns.length; i++) {
        columns[i] = meta.getColumnName(i + 1);
      }
      logger.trace(MessageFormat.format(Messages.log("data.DatabaseUtil.18"), start, pageSize));

      // オーバーフロー時は最大値+1になっているので1減らす
      if (searchResult.isOverflow()) {
        countAmount--;
      }

      for (rowCount = 0; rset.next(); rowCount++) {

        if (rowCount == countAmount) {
          logger.debug(Messages.log("data.DatabaseUtil.19"));
          break;
        }

        if (rowCount < start) {
          logger.trace(MessageFormat.format(Messages.log("data.DatabaseUtil.20"), rowCount));
          continue;
        } else if (rowCount >= pageSize * pageNo) {
          logger.trace(MessageFormat.format(Messages.log("data.DatabaseUtil.21"), rowCount));
          continue;
        } else {
          logger.trace(MessageFormat.format(Messages.log("data.DatabaseUtil.22"), rowCount));
          logger.trace(MessageFormat.format("{0}:{1}", rset.getString(1), rset.getString(2)));
          R row = query.getRowType().newInstance();

          copyResultToBean(rset, row);

          result.add(row);
        }
      }
      logger.trace(MessageFormat.format(Messages.log("data.DatabaseUtil.23"), rowCount, pageNo, pageSize));
      searchResult.setRowCount(rowCount);
      searchResult.setPageSize(pageSize);
      searchResult.setCurrentPage(pageNo);
      searchResult.setRows(result);

      if (searchResult.isOverflow()) {
        rset.close();
        pstmt.close();
        String countQuery = MessageFormat.format("SELECT COUNT(*) FROM ({0}) PS", query.getSqlString());
        pstmt = prepareStatement(conn, countQuery);
        bindParameters(pstmt, query.getParameters());
        rset = pstmt.executeQuery();
        if (rset.next()) {
          int total = rset.getInt(1);
          searchResult.setRowCount(total);
        }
      }
      searchFinished = System.currentTimeMillis();
      logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.24"), searchFinished - searchStarted));
    } catch (SQLException ex) {
      logger.error(ex);
      throw new DataAccessException(ex);
    } catch (Exception ex) {
      logger.error(ex);
      throw new DataAccessException(ex);
    } finally {
      closeAll(rset, pstmt, conn);
    }

    return searchResult;
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
      Class<? extends Object> returnType = rowType.getMethod("get"
          + StringUtil.toPascalFormat(key), new Class[0]).getReturnType();
      Object value = rset.getObject(i + 1);

      // resultSetを2回見るのは無駄だが…

      if (value == null) {
        // オブジェクトがNULLの場合は処理を行わない
        Logger.getLogger(DatabaseUtil.class).trace("value is null");
      } else if (returnType.equals(Date.class)) {
        value = new Date(rset.getTimestamp(i + 1).getTime());
//      add by V10-CH start
      } else if (returnType.equals(BigDecimal.class)) {
        value = rset.getBigDecimal(i + 1);
//      add by V10-CH end
      } else if (returnType.equals(Long.class)) {
        value = rset.getLong(i + 1);
      } else if (returnType.equals(String.class)) {
        value = rset.getString(i + 1);
      } else if (returnType.equals(long.class)) {
        value = rset.getLong(i + 1);
      } else {
        throw new DataAccessException();
      }
      rowType.getMethod("set" + StringUtil.toPascalFormat(key), returnType).invoke(bean, value);
    }
  }

  /**
   * ResultSetのデータをMapへコピー
   * 
   * @param rset
   * @param bean
   * @throws Exception
   */
  private static void copyResultToMap(ResultSet rset, Map<String, String> map) throws Exception {
    ResultSetMetaData rsmd = rset.getMetaData();
    int cols = rsmd.getColumnCount();
    for (int i = 0; i < cols; i++) {
      String key = rsmd.getColumnName(i + 1);
      String value = rset.getString(i + 1);
      map.put(key, value);
    }
  }

  public static Object executeScalar(Query query) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    Object result = null;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rset = null;
    try {
      conn = getReadOnlyConnection();
      stmt = prepareStatement(conn, query.getSqlString());
      bindParameters(stmt, query.getParameters());
      logQuery(query);
      rset = stmt.executeQuery();
      if (rset.next()) {
        result = rset.getObject(1);
      } else {
        result = null;
      }

    } catch (Exception e) {
      logger.error(e);
      throw new DataAccessException(e);
    } finally {
      closeAll(rset, stmt, conn);
    }
    return result;
  }

  public static <T>T executeScalar(Query query, Class<T> returnType) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    T result = null;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rset = null;
    try {
      conn = getReadOnlyConnection();
      stmt = prepareStatement(conn, query.getSqlString());
      bindParameters(stmt, query.getParameters());
      logQuery(query);
      rset = stmt.executeQuery();
      if (rset.next()) {
        result = getTypedObject(rset, 1, returnType);
      } else {
        result = null;
      }
    } catch (Exception e) {
      logger.error(e);
      throw new DataAccessException(e);
    } finally {
      closeAll(rset, stmt, conn);
    }
    return result;
  }

  public static Long generateSequence(SequenceType sequenceType) {
//  postgreSQL start
    //String sqlString = MessageFormat.format("SELECT {0}.NEXTVAL FROM DUAL", sequenceType.getValue());
  String  sqlString = "SELECT " + SqlDialect.getDefault().getNextval(sequenceType,"{0}") + " FROM DUAL"; //$NON-NLS-1$
    //postgreSQL end    
//  postgreSQL end
    Query query = new SimpleQuery(sqlString);
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    Long result = null;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rset = null;
    try {
      conn = getReadOnlyConnection();
      stmt = prepareStatement(conn, query.getSqlString());
      bindParameters(stmt, query.getParameters());
      logQuery(query);
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
      closeAll(rset, stmt, conn);
    }
    return result;
  }

  public static void bindParameters(PreparedStatement pstmt, Object[] parameters) throws SQLException {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    for (int i = 0; i < parameters.length; i++) {
      logger.trace(MessageFormat.format(Messages.log("data.DatabaseUtil.25"), parameters[i]));
      if (parameters[i] != null) {
        logger.trace(MessageFormat.format(Messages.log("data.DatabaseUtil.26"), parameters[i].getClass().getName()));
      } else {
        logger.trace(Messages.log("data.DatabaseUtil.27"));
      }
      logger.trace(MessageFormat.format(Messages.log("data.DatabaseUtil.28"), getSqlType(parameters[i])));
      Object obj = parameters[i];
      //20101206 Modified by Kousen(ZhouDingqian). Start
      //Postgresql JDBC4対応
      //pstmt.setObject(i + 1, adjustValue(obj), getSqlType(parameters[i]));
      pstmt.setObject(i + 1, adjustValue(obj));
      //20101206 Modified by Kousen(ZhouDingqian). End
    }
  }

  private static void rollbackOnFailure(Connection conn) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    logger.trace(Messages.log("data.DatabaseUtil.29"));
    try {
      if (conn != null) {
        conn.rollback();
      }
    } catch (Exception e) {
      logger.warn(e);
    }
    logger.trace(Messages.log("data.DatabaseUtil.30"));
  }

  /**
   * JDBCデータリソースを開放します。 リソースはResultSet→Statement→Connectionの順に閉じられます。
   * 
   * @param rset
   *          ResultSetオブジェクト
   * @param stmt
   *          Statementオブジェクト
   * @param conn
   *          Connectionオブジェクト
   */
  public static void closeAll(ResultSet rset, Statement stmt, Connection conn) {
    closeResource(rset);
    closeResource(stmt);
    closeResource(conn);
  }

  /**
   * JDBCデータリソースを開放します。
   * 
   * @param rset
   *          ResultSetオブジェクト
   */
  public static void closeResource(ResultSet rset) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    try {
      if (rset != null) {
        rset.close();
      }
      logger.trace(Messages.log("data.DatabaseUtil.31"));
    } catch (SQLException e) {
      logger.warn(e);
    }
  }

  /**
   * JDBCデータリソースを開放します。
   * 
   * @param stmt
   *          Statementオブジェクト
   */
  public static void closeResource(Statement stmt) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    try {
      if (stmt != null) {
        stmt.close();
      }
      logger.trace(Messages.log("data.DatabaseUtil.32"));
    } catch (SQLException e) {
      logger.warn(e);
    }
  }

  /**
   * JDBCデータリソースを開放します。
   * 
   * @param conn
   *          Connectionオブジェクト
   */
  public static void closeResource(Connection conn) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    try {
      if (conn != null && !conn.isClosed()) {
        //20101206 Added by Kousen(ZhouDingqian). Start
        //JDBC4の対応
        conn.setReadOnly(false);
        //20101206 Added by Kousen(ZhouDingqian). End
        conn.close();
      }
      logger.trace(Messages.log("data.DatabaseUtil.33"));
    } catch (SQLException e) {
      logger.warn(e);
    }
  }

  /**
   * DTOのリストをRowIdの昇順にソートします。
   * 
   * @param list
   */
  public static <T extends WebshopEntity>void sort(List<T> list) {
    if (list != null) {
      Collections.sort(list, new WebshopEntityComparator());
    }
  }

  private static class WebshopEntityComparator implements Comparator<WebshopEntity>, Serializable {

    private static final long serialVersionUID = 1L;

    public int compare(WebshopEntity o1, WebshopEntity o2) {
      return o1.getOrmRowid().compareTo(o2.getOrmRowid());
    }
  }

  @SuppressWarnings("unchecked")
  private static <T>T getTypedObject(ResultSet rset, int columnIndex, Class<T> type) throws SQLException {
    if (type == null) {
      return (T) rset.getObject(columnIndex);
    } else if (type.equals(Date.class)) {
      Timestamp ts = rset.getTimestamp(columnIndex);
      if (ts != null) {
        return (T) new Date(ts.getTime());
      } else {
        return null;
      }
    } else if (type.equals(Long.class)) {
      BigDecimal bd = rset.getBigDecimal(columnIndex);
      if (bd != null) {
        return (T) Long.valueOf(bd.longValue());
      } else {
        return null;
      }
    } else if (type.equals(Long.class)) {
      return (T) rset.getString(columnIndex);
    } else {
      return (T) rset.getObject(columnIndex);
    }
  }

  private static Object adjustValue(Object obj) {
    if (obj instanceof Date) {
      obj = new Timestamp(((Date) obj).getTime());
    }
    return obj;
  }

  public static void logQuery(Query query) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    try {
      logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.34"),
          query.getSqlString()));
      logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.35"),
          Arrays.toString(query.getParameters())));
    } catch (RuntimeException e) {
      logger.warn(MessageFormat.format(Messages.log("data.DatabaseUtil.36"), e.getMessage()));
    }

  }

  public static void tryCommit(Connection conn) {
    try {
      conn.commit();
    } catch (SQLException e) {
      try {
        conn.rollback();
      } catch (SQLException ee) {
        throw new DataAccessException(ee);
      }
    }
  }

  public static void tryRollback(Connection conn) {
    try {
      conn.rollback();
    } catch (SQLException e) {
      throw new DataAccessException(e);
    }
  }

  //postgres start
  /**
   * 更新クエリを発行します。 トランザクションは即時コミットされます。
   * 
   * @param query
   *          更新クエリ
   * @return 更新結果
   */
  public static UpdateResult executeAnalysisTable(UpdateQuery<?> query) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    Connection conn = null;
    PreparedStatement pstmt = null;
    UpdateResult result = new UpdateResult();
    try {
      conn = getConnection();
      conn.setAutoCommit(true);
      pstmt = prepareStatement(conn, query.getSqlString());
      bindParameters(pstmt, query.getParameters());
      pstmt.execute();
      logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.2"), query.getSqlString()));
      for (Object[] params : query.getUpdateParameters()) {
        logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.3"), Arrays.toString(params)));
        bindParameters(pstmt, params);
        int updCount = pstmt.executeUpdate();
        logger.debug(MessageFormat.format(Messages.log("data.DatabaseUtil.4"), updCount));
      }
//
//      if (query.isTransacion()) {
//        conn.commit();
        logger.debug(Messages.log("data.DatabaseUtil.5"));
//      }
    } catch (Exception e) {
      logger.error(e);
//      if (query.isTransacion()) {
//        rollbackOnFailure(conn);
//        logger.debug(Messages.log("data.DatabaseUtil.6"));
//      }
      throw new DataAccessException(e);
    } finally {
      closeAll(null, pstmt, conn);
    }

    return result;
  }
  
  public static ArrayList<String> executeScalarForTableAnalysis(Query query) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    ArrayList<String> result = new ArrayList<String>();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rset = null;
    try {
      conn = getReadOnlyConnection();
      stmt = prepareStatement(conn, query.getSqlString());
      bindParameters(stmt, query.getParameters());
      logQuery(query);
      rset = stmt.executeQuery();
      while (rset.next()) {
        result.add(rset.getString(1));
      }
    } catch (Exception e) {
      logger.error(e);
      throw new DataAccessException(e);
    } finally {
      closeAll(rset, stmt, conn);
    }
    return result;
  }
  
  public static int getResultSet(CallableStatement statement, int resultIndex) throws SQLException {
    int result = -1;
    if (DIContainer.getWebshopConfig().isPostgreSQL()) {
      statement.execute();
      ResultSet resultset = statement.getResultSet();
      resultset.next();
      result = resultset.getInt(1);
      resultset.close();
    } else {
      statement.registerOutParameter(resultIndex, Types.INTEGER);
      statement.execute();
      result = statement.getInt(resultIndex);
    }
    return result;
  }
  
  public static String getDBEmptyString(String str) {
    if (DIContainer.getWebshopConfig().isPostgreSQL()) {
      str = null;
    }
    return str;
  }
  //postgres end
  
  // M17N 10361 追加 ここから
  /**
   * 検索結果をストリングのリストとして返します。 検索結果が0件のときは空のListを返します。
   * 
   * @param query
   *          SQL
   * @return 検索結果を表すストリングのリスト
   */
  public static List<String> loadAsStringList(Query query) {
    Logger logger = Logger.getLogger(DatabaseUtil.class);
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rset = null;
    List<String> result = new ArrayList<String>();
    try {
      conn = getReadOnlyConnection();
      pstmt = prepareStatement(conn, query.getSqlString());
      bindParameters(pstmt, query.getParameters());
      logQuery(query);
      rset = pstmt.executeQuery();
      while (rset.next()) {
        result.add(rset.getString(1));
      }
    } catch (Exception e) {
      logger.error(e);
      throw new DataAccessException(e);
    } finally {
      closeAll(rset, pstmt, conn);
    }

    return result;
  }
  // M17N 10361 追加 ここまで
}
