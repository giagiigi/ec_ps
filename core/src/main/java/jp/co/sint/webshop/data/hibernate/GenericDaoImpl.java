package jp.co.sint.webshop.data.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jp.co.sint.webshop.data.ConcurrencyFailureException;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SqlUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.text.Messages;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.type.AnyType;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;

/**
 * ジェネリックDAO(Data Access Object)のインタフェースの実装です。
 * 
 * @author System Integrator Corp.
 * @param <T>
 *          アクセス先テーブルに対応するDTOクラス
 * @param <PK>
 *          行IDクラス(通常はLongを用います)
 */
public class GenericDaoImpl<T extends WebshopEntity, PK extends Serializable> implements GenericDao<T, PK>, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private SessionFactory sessionFactory;

  private Class<? extends WebshopEntity> type;

  /**
   * 新しいGenericDaoImplのインスタンスを生成します。
   */
  public GenericDaoImpl() {

  }

  /**
   * 新しいGenericDaoImplのインスタンスを生成します。
   * 
   * @param tableType
   *          取り扱うDTOクラスのClassオブジェクト
   */
  public GenericDaoImpl(Class<? extends WebshopEntity> tableType) {
    setType(tableType);
  }

  /**
   * SessionFactoryを設定します。
   * 
   * @param factory
   *          SessionFactoryオブジェクト
   */
  public void setSessionFactory(SessionFactory factory) {
    this.sessionFactory = factory;
  }

  /**
   * SessionFactoryを取得します。
   * 
   * @return SessionFactoryオブジェクト
   */
  public SessionFactory getSessionFactory() {
    return this.sessionFactory;
  }

  /**
   * typeを返します。
   * 
   * @return the type
   */
  public Class<? extends WebshopEntity> getType() {
    return type;
  }

  /**
   * typeを設定します。
   * 
   * @param type
   *          設定する type
   */
  public void setType(Class<? extends WebshopEntity> type) {
    this.type = type;
  }

  /**
   * 指定されたidを持つDTOインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するDTOインスタンス
   */
  @SuppressWarnings("unchecked")
  public T loadByRowid(PK id) {
    Logger logger = Logger.getLogger(this.getClass());
    T t = null;
    Session session = getSession();
    try {
      t = (T) session.load(getType(), id);
    } catch (Exception e) {
      logger.warn(e);
    } finally {
      session.close();
    }
    return t;
  }

  /**
   * 新しいDTOインスタンスの内容をデータベースに追加します。
   * 
   * @param newInstance
   *          追加対象のDTOインスタンス
   * @return 追加が成功した場合のorm_rowid値
   */
  public PK insert(T newInstance) {
    return insert(newInstance, ServiceLoginInfo.getInstance());
  }

  /**
   * 新しいDTOインスタンスの内容をデータベースに追加します。
   * 
   * @param newInstance
   *          追加対象のDTOインスタンス
   * @return 追加が成功した場合のorm_rowid値
   */
  @SuppressWarnings("unchecked")
  public PK insert(T newInstance, LoginInfo loginInfo) {
    DatabaseUtil.setUserStatus(newInstance, loginInfo);
    Logger logger = Logger.getLogger(this.getClass());
    Session session = getSession();
    Transaction tx = session.beginTransaction();
    UUID txnId = UUID.randomUUID();
    logger.info(Messages.log("data.hibernate.GenericDaoImpl.0") + getFullTransactionId(txnId));
    PK key = null;
    try {
      key = (PK) session.save(newInstance);
      logger.info(MessageFormat.format(Messages.log("data.hibernate.GenericDaoImpl.1"),
          getShortTransactionId(txnId), SqlUtil.getTableName(type)));
      tx.commit();
      logger.info(Messages.log("data.hibernate.GenericDaoImpl.4")
          + getFullTransactionId(txnId));
    } catch (Exception e) {
      tx.rollback();
      logger.info(Messages.log("data.hibernate.GenericDaoImpl.5")
          + getFullTransactionId(txnId));
      throw new DataAccessException(e);
    } finally {
      session.close();
    }
    return key;
  }

  /**
   * DTOインスタンスの更新をデータベースへ反映します。
   * 
   * @param transactionObject
   *          更新対象のDTOインスタンス
   */
  public void update(T transactionObject) {
    update(transactionObject, ServiceLoginInfo.getInstance());
  }

  /**
   * DTOインスタンスの更新をデータベースへ反映します。
   * 
   * @param transactionObject
   *          更新対象のDTOインスタンス
   */
  public void update(T transactionObject, LoginInfo loginInfo) {
    Logger logger = Logger.getLogger(this.getClass());
    DatabaseUtil.setUserStatus(transactionObject, loginInfo);
    Session session = getSession();
    Transaction tx = session.beginTransaction();
    UUID txnId = UUID.randomUUID();
    logger.info(Messages.log("data.hibernate.GenericDaoImpl.0") + getFullTransactionId(txnId));
    try {
      session.update(transactionObject);
      logger.info(MessageFormat.format(Messages.log("data.hibernate.GenericDaoImpl.2"),
          getShortTransactionId(txnId), SqlUtil.getTableName(type)));
      tx.commit();
      logger.info(Messages.log("data.hibernate.GenericDaoImpl.4") + getFullTransactionId(txnId));
    } catch (StaleObjectStateException soEx) {
      tx.rollback();
      logger.info(Messages.log("data.hibernate.GenericDaoImpl.5") + getFullTransactionId(txnId));
      throw new ConcurrencyFailureException(soEx);
    } catch (Exception e) {
      tx.rollback();
      logger.info(Messages.log("data.hibernate.GenericDaoImpl.5") + getFullTransactionId(txnId));
      throw new DataAccessException(e);
    } finally {
      session.close();
    }
  }

  /**
   * DTOインスタンスの内容をデータベースから削除します。
   * 
   * @param transactionObject
   *          削除対象のDTOインスタンス
   */
  public void delete(T transactionObject) {
    delete(transactionObject, ServiceLoginInfo.getInstance());
  }

  /**
   * DTOインスタンスの内容をデータベースから削除します。
   * 
   * @param transactionObject
   *          削除対象のDTOインスタンス
   */
  public void delete(T transactionObject, LoginInfo loginInfo) {
    Logger logger = Logger.getLogger(this.getClass());
    DatabaseUtil.setUserStatus(transactionObject, loginInfo);
    Session session = getSession();
    Transaction tx = session.beginTransaction();
    UUID txnId = UUID.randomUUID();
    logger.info(Messages.log("data.hibernate.GenericDaoImpl.0") + getFullTransactionId(txnId));
    try {
      session.delete(transactionObject);
      logger.info(MessageFormat.format(Messages.log("data.hibernate.GenericDaoImpl.3"),
          getShortTransactionId(txnId), SqlUtil.getTableName(type)));

      tx.commit();
      logger.info(Messages.log("data.hibernate.GenericDaoImpl.4") + getFullTransactionId(txnId));
    } catch (StaleObjectStateException soEx) {
      tx.rollback();
      logger.info(Messages.log("data.hibernate.GenericDaoImpl.5") + getFullTransactionId(txnId));
      throw new ConcurrencyFailureException(soEx);
    } catch (Exception e) {
      tx.rollback();
      logger.info(Messages.log("data.hibernate.GenericDaoImpl.5") + getFullTransactionId(txnId));
      throw new DataAccessException(e);
    } finally {
      session.close();
    }

  }

  public Object executeScalar(String sqlString, Object... parameters) {
    Logger logger = Logger.getLogger(this.getClass());
    Object result = null;
    Session session = getSession();
    try {
      SQLQuery sqlQuery = session.createSQLQuery(sqlString);
      Type[] paramTypes = getParamTypes(parameters);
      sqlQuery.setParameters(parameters, paramTypes);
      result = sqlQuery.uniqueResult();
    } catch (Exception e) {
      logger.warn(e);
    } finally {
      session.close();
    }
    return result;
  }

  /**
   * バインド変数が埋め込まれたSQLクエリを指定して、DTOインスタンスのリストを取得します。
   * 
   * @param query
   *          SQLクラス
   * @return DTOインスタンスのリスト。結果がゼロ件の場合は空のリストを返す。
   */
  public List<T> findByQuery(Query query) {
    return findBySqlQuery0(query.getSqlString(), query.getParameters());
  }

  /**
   * バインド変数が埋め込まれたSQLクエリを指定して、DTOインスタンスのリストを取得します。
   * 
   * @param sqlString
   *          SQL文字列
   * @param parameters
   *          バインド変数に与えるパラメータの配列
   * @return DTOインスタンスのリスト。結果がゼロ件の場合は空のリストを返す。
   */
  public List<T> findByQuery(String sqlString, Object... parameters) {
    return findBySqlQuery0(sqlString, parameters);
  }

  /**
   * バインド変数が埋め込まれたSQLクエリを指定して、DTOインスタンスのリストを取得します。
   * 
   * @param sqlString
   *          SQL文字列
   * @param parameters
   *          バインド変数に与えるパラメータの配列
   * @return DTOインスタンスのリスト。結果がゼロ件の場合は空のリストを返す。
   */
  @SuppressWarnings("unchecked")
  private List<T> findBySqlQuery0(String sqlString, Object... parameters) {
    Logger logger = Logger.getLogger(this.getClass());
    Session session = getSession();
    List<T> list = null;
    try {
      SQLQuery sqlQuery = session.createSQLQuery(sqlString);
      sqlQuery.addEntity(getType());

      Type[] paramTypes = getParamTypes(parameters);
      sqlQuery.setParameters(parameters, paramTypes);

      list = (List<T>) sqlQuery.list();
    } catch (Exception e) {
      logger.warn(e);
      list = new ArrayList<T>();
    } finally {
      session.close();
    }
    return list;
  }

  /**
   * バインド変数が埋め込まれたSQLクエリを指定して、データベースを更新します。
   * 
   * @param query
   *          SQLクラス
   * @return 更新された件数。
   */
  public int updateByQuery(Query query) {
    return updateBySqlQuery0(query.getSqlString(), query.getParameters());
  }

  /**
   * バインド変数が埋め込まれたSQLクエリを指定して、データベースを更新します。
   * 
   * @param sqlString
   *          SQL文字列
   * @param parameters
   *          バインド変数に与えるパラメータの配列
   * @return 更新された件数。
   */
  public int updateByQuery(String sqlString, Object... parameters) {
    return updateBySqlQuery0(sqlString, parameters);
  }

  /**
   * バインド変数が埋め込まれたSQLクエリを指定して、データベースを更新します。
   * 
   * @param sqlString
   *          SQL文字列
   * @param parameters
   *          バインド変数に与えるパラメータの配列
   * @return 更新された件数。
   */
  private int updateBySqlQuery0(String sqlString, Object... parameters) {
    Logger logger = Logger.getLogger(this.getClass());
    Session session = getSession();
    int updateCount = -1;
    try {
      SQLQuery sqlQuery = session.createSQLQuery(sqlString);
      sqlQuery.addEntity(getType());

      Type[] paramTypes = getParamTypes(parameters);
      sqlQuery.setParameters(parameters, paramTypes);

      updateCount = sqlQuery.executeUpdate();
    } catch (RuntimeException e) {
      logger.warn(logger);
      updateCount = -1;
    } finally {
      session.close();
    }
    return updateCount;
  }

  public static Type[] getParamTypes(Object... parameters) {
    if (parameters == null) {
      return new Type[0];
    }
    Type[] paramTypes = new Type[parameters.length];
    for (int i = 0; i < parameters.length; i++) {
//modify by V10-CH start
//      if (parameters[i] instanceof Number) {
//        paramTypes[i] = new LongType();
//      } 
      if (parameters[i] instanceof Number) {
        if (parameters[i] instanceof BigDecimal) {
          paramTypes[i] = new BigDecimalType();
        }  else if (parameters[i] instanceof Long) {
          paramTypes[i] = new LongType();
        } else {
          Logger logger = Logger.getLogger(GenericDaoImpl.class);
          logger.warn(" type: " + parameters[i].getClass().getName());
        } 
//modify by V10-CH end
      }else if (parameters[i] instanceof Date) {
        paramTypes[i] = new TimestampType();
      } else if (parameters[i] instanceof String) {
        paramTypes[i] = new StringType();
      } else if (parameters[i] == null) {
        paramTypes[i] = new StringType();
      } else {
        paramTypes[i] = new AnyType();
      }
    }
    return paramTypes;
  }

  @SuppressWarnings("unchecked")
  public List<T> loadAll() {
    Logger logger = Logger.getLogger(this.getClass());
    List<T> list = null;
    Session session = getSession();
    try {
      list = (List<T>) session.createCriteria(getType()).list();
    } catch (Exception e) {
      logger.warn(e);
      list = new ArrayList<T>();
    } finally {
      session.close();
    }
    return list;
  }

  private Session getSession() {
    return sessionFactory.openSession();
  }

  private String getFullTransactionId(UUID uuid) {
    return MessageFormat.format("[TransacionId={0}]", uuid.toString());
  }

  private String getShortTransactionId(UUID uuid) {
    return MessageFormat.format("[Txn.id={0}...]", uuid.toString().substring(0, 8));
  }
}
