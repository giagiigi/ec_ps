//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.data.dao.AccessLogDao;
import jp.co.sint.webshop.data.dto.AccessLog;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * アクセスログ
 *
 * @author System Integrator Corp.
 *
 */
public class AccessLogDaoImpl implements AccessLogDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<AccessLog, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public AccessLogDaoImpl() {
    genericDao = new GenericDaoImpl<AccessLog, Long>(AccessLog.class);
  }

  /**
   * SessionFactoryを取得します
   * @return SessionFactoryのインスタンス
   */
  public SessionFactory getSessionFactory() {
    return this.sessionFactory;
  }

  /**
   * SessionFactoryを設定します
   * @param factory SessionFactoryのインスタンス
   */
  public void setSessionFactory(SessionFactory factory) {
    this.sessionFactory = factory;
    genericDao.setSessionFactory(factory);
  }

  /**
   * 指定されたorm_rowidを持つアクセスログのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するAccessLogのインスタンス
   */
  public AccessLog loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してアクセスログのインスタンスを取得します。
   * @param accessLogId アクセスログID
   * @return 主キー列の値に対応するAccessLogのインスタンス
   */
  public AccessLog load(Long accessLogId) {
    Object[] params = new Object[]{accessLogId};
    final String query = "SELECT * FROM ACCESS_LOG"
        + " WHERE ACCESS_LOG_ID = ?";
    List<AccessLog> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してアクセスログが既に存在するかどうかを返します。
   * @param accessLogId アクセスログID
   * @return 主キー列の値に対応するAccessLogの行が存在すればtrue
   */
  public boolean exists(Long accessLogId) {
    Object[] params = new Object[]{accessLogId};
    final String query = "SELECT COUNT(*) FROM ACCESS_LOG"
        + " WHERE ACCESS_LOG_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規AccessLogをデータベースに追加します。
   * @param obj 追加対象のAccessLog
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(AccessLog obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規AccessLogをデータベースに追加します。
   * @param obj 追加対象のAccessLog
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(AccessLog obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * アクセスログを更新します。
   * @param obj 更新対象のAccessLog
   */
  public void update(AccessLog obj) {
    genericDao.update(obj);
  }

  /**
   * アクセスログを更新します。
   * @param obj 更新対象のAccessLog
   */
  public void update(AccessLog obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * アクセスログを削除します。
   * @param obj 削除対象のAccessLog
   */
  public void delete(AccessLog obj) {
    genericDao.delete(obj);
  }

  /**
   * アクセスログを削除します。
   * @param obj 削除対象のAccessLog
   */
  public void delete(AccessLog obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してアクセスログを削除します。
   * @param accessLogId アクセスログID
   */
  public void delete(Long accessLogId) {
    Object[] params = new Object[]{accessLogId};
    final String query = "DELETE FROM ACCESS_LOG"
        + " WHERE ACCESS_LOG_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してアクセスログのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するAccessLogのリスト
   */
  public List<AccessLog> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してアクセスログのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するAccessLogのリスト
   */
  public List<AccessLog> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のAccessLogのリスト
   */
  public List<AccessLog> loadAll() {
    return genericDao.loadAll();
  }

}
