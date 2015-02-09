package jp.co.sint.webshop.data.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.UUID;

import javax.sql.DataSource;

import jp.co.sint.webshop.data.ConcurrencyFailureException;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.DuplicatedInsertException;
import jp.co.sint.webshop.data.ProcedureDelegate;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.StockManager;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.SqlDialect;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.type.Type;

public class TransactionManagerImpl implements TransactionManager {

  private DataSource dataSource;

  private SessionFactory sessionFactory;

  private Session session;

  private Transaction transaction;

  private Connection connection;

  private Logger logger = Logger.getLogger(this.getClass());

  private LoginInfo loginInfo;

  private UUID transactionId;

  private boolean disposed;

  private String getFullTransactionId() {
    return MessageFormat.format("[TransacionId={0}]", transactionId.toString());
  }

  private String getShortTransactionId() {
    return MessageFormat.format("[Txn.id={0}...]", transactionId.toString().substring(0, 8));
  }

  private String getOperationMessage(OperationType operType, String query) {
    return MessageFormat.format(Messages.getString("data.hibernate.TransactionManagerImpl.0"), query);
  }

  private String getOperationMessage(OperationType operType, WebshopEntity entity) {
    return MessageFormat.format(
        Messages.getString("data.hibernate.TransactionManagerImpl.1"),
        getShortTransactionId(), DatabaseUtil.getTableName(entity.getClass()), operType.toString());
  }

  private static enum OperationType {
    /** 追加b */
    INSERT(Messages.getString("data.hibernate.TransactionManagerImpl.2")),
    /** 更新 */
    UPDATE(Messages.getString("data.hibernate.TransactionManagerImpl.3")),
    /** 削除 */
    DELETE(Messages.getString("data.hibernate.TransactionManagerImpl.4")),
    /** クエリ */
    SQL(Messages.getString("data.hibernate.TransactionManagerImpl.5"));

    private String message;

    private OperationType(String msg) {
      this.message = msg;
    }

    public String toString() {
      return message;
    }
  }

  public void begin() {
    begin(ServiceLoginInfo.getInstance());
  }

  public void begin(LoginInfo login) {
    setLoginInfo(login);
    try {
      transactionId = UUID.randomUUID();
      connection = getDataSource().getConnection();
      logger.info(MessageFormat.format(
          Messages.log("data.hibernate.TransactionManagerImpl.6"), getFullTransactionId()));
      if (session == null) {
        session = getSessionFactory().openSession(connection);
      }
      transaction = session.beginTransaction();
      disposed = false;
    } catch (SQLException ex) {
      DatabaseUtil.closeResource(connection);
      throw new DataAccessException(ex);
    }
  }

  public void commit() {
    try {
      if (transaction != null) {
        transaction.commit();
        session.clear();
        session.close();
        session = null;
      }
      logger.info(MessageFormat.format(
          Messages.log("data.hibernate.TransactionManagerImpl.7"), getFullTransactionId()));
    } catch (Throwable t) {
      throw new DataAccessException(t);
    } finally {
      dispose();
    }

  }

  public void rollback() {
    try {
      if (transaction != null) {
        transaction.rollback();
        session.clear();
        session.close();
        session = null;
      }
      logger.info(MessageFormat.format(
          Messages.log("data.hibernate.TransactionManagerImpl.8"), getFullTransactionId()));
    } catch (Throwable t) {
      throw new DataAccessException(t);
    } finally {
      dispose();
    }
  }

  public void delete(WebshopEntity entity) {
    if (entity == null) {
      logger.warn(Messages.log("data.hibernate.TransactionManagerImpl.14"));
      return;
    }
    DatabaseUtil.setUserStatus(entity, getLoginInfo());
    try {
      session.delete(entity);
      session.flush();
      session.clear();
      logger.info(getOperationMessage(OperationType.DELETE, entity));
    } catch (StaleObjectStateException soEx) {
      // try {
      // if (transaction != null) {
      // transaction.rollback();
      // }
      // if (session != null) {
      // session.close();
      // }
      // } catch (Exception ee) {
      // logger.error(ee);
      // }
      // DatabaseUtil.closeResource(connection);
      throw new ConcurrencyFailureException(soEx);
    } catch (Throwable t) {
      // try {
      // if (transaction != null) {
      // transaction.rollback();
      // }
      // if (session != null) {
      // session.close();
      // }
      // } catch (Exception ee) {
      // logger.error(ee);
      // }
      // DatabaseUtil.closeResource(connection);
      throw new DataAccessException(t);
    }
  }

  public void insert(WebshopEntity entity) {
    if (entity == null) {
      logger.warn(Messages.log("data.hibernate.TransactionManagerImpl.14"));
      return;
    }
    DatabaseUtil.setUserStatus(entity, getLoginInfo());
    try {
      session.save(entity);
      session.flush();
      session.clear();
      logger.info(getOperationMessage(OperationType.INSERT, entity));
    } catch (ConstraintViolationException cvEx) {
      // try {
      // if (transaction != null) {
      // transaction.rollback();
      // }
      // if (session != null) {
      // session.close();
      // }
      // } catch (Exception ee) {
      // logger.error(ee);
      // }
      // DatabaseUtil.closeResource(connection);
      throw new DuplicatedInsertException(cvEx);
    } catch (Throwable t) {
      // try {
      // if (transaction != null) {
      // transaction.rollback();
      // }
      // if (session != null) {
      // session.close();
      // }
      // } catch (Exception ee) {
      // logger.error(ee);
      // }
      // DatabaseUtil.closeResource(connection);
      throw new DataAccessException(t);
    }
  }

  public void update(WebshopEntity entity) {
    if (entity == null) {
      logger.warn(Messages.log("data.hibernate.TransactionManagerImpl.14"));
      return;
    }
    DatabaseUtil.setUserStatus(entity, getLoginInfo());
    try {
      session.update(entity);
      session.flush();
      session.clear();
      logger.info(getOperationMessage(OperationType.UPDATE, entity));
    } catch (StaleObjectStateException soEx) {
      // try {
      // if (transaction != null) {
      // transaction.rollback();
      // }
      // if (session != null) {
      // session.close();
      // }
      // } catch (Exception ee) {
      // logger.error(ee);
      // }
      // DatabaseUtil.closeResource(connection);
      throw new ConcurrencyFailureException(soEx);
    } catch (Throwable t) {
      // try {
      // if (transaction != null) {
      // transaction.rollback();
      // }
      // if (session != null) {
      // session.close();
      // }
      // } catch (Exception ee) {
      // logger.error(ee);
      // }
      // DatabaseUtil.closeResource(connection);
      throw new DataAccessException(t);
    }
  }

  public int executeUpdate(Query query) {
    return executeUpdate0(query.getSqlString(), query.getParameters());
  }

  public int executeUpdate(String sql, Object... parameters) {
    return executeUpdate0(sql, parameters);
  }

  private int executeUpdate0(String sql, Object... parameters) {
    int result = -1;
    try {
      SQLQuery sqlQuery = session.createSQLQuery(sql);

      Type[] paramTypes = GenericDaoImpl.getParamTypes(parameters);
      sqlQuery.setParameters(parameters, paramTypes);

      result = sqlQuery.executeUpdate();
      logger.info(getOperationMessage(OperationType.SQL, sql));
    } catch (Throwable t) {
      // logger.error(t);
      // DatabaseUtil.closeResource(connection);
      throw new DataAccessException(t);
    }

    return result;
  }

  public void executeProcedure(ProcedureDelegate delegate) {
    CallableStatement statement = null;
    try {
        statement = getConnection().prepareCall(SqlDialect.getDefault().getProcedureString(delegate.getStatement()));
        delegate.execute(statement);

    } catch (Throwable t) {
      logger.error(t);
      throw new DataAccessException(t);
    } finally {
      DatabaseUtil.closeResource(statement);
    }
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public Connection getConnection() {
    return connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }

  public LoginInfo getLoginInfo() {
    return this.loginInfo;
  }

  public void setLoginInfo(LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }

  public StockManager getStockManager() {
    return new StockManagerImpl(getConnection());
  }

  public void close() {
    dispose();
  }

  public boolean isDisposed() {
    return disposed;
  }

  public void dispose() {
    if (isDisposed()) {
      return;
    }
    try {
      if (transaction != null) {
        if (!transaction.wasCommitted() && !transaction.wasRolledBack()) {
          logger.warn(MessageFormat.format(
              Messages.log("data.hibernate.TransactionManagerImpl.9"), getFullTransactionId()));
          logger.warn(Messages.log("data.hibernate.TransactionManagerImpl.10"));
          transaction.rollback();
          logger.warn(MessageFormat.format(
              Messages.log("data.hibernate.TransactionManagerImpl.11"), getFullTransactionId()));
        }
      }
      if (session != null) {
        session.clear();
        session.close();
      }
    } catch (Exception ee) {
      logger.error(ee);
    }
    DatabaseUtil.closeResource(connection);
    disposed = true;
    logger.info(Messages.log("data.hibernate.TransactionManagerImpl.12"));
    logger.info(MessageFormat.format(
        Messages.log("data.hibernate.TransactionManagerImpl.13"), getFullTransactionId()));
  }

}
