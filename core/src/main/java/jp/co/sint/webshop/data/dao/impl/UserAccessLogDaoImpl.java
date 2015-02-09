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
import jp.co.sint.webshop.data.dao.UserAccessLogDao;
import jp.co.sint.webshop.data.dto.UserAccessLog;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 管理側アクセスログ
 *
 * @author System Integrator Corp.
 *
 */
public class UserAccessLogDaoImpl implements UserAccessLogDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<UserAccessLog, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public UserAccessLogDaoImpl() {
    genericDao = new GenericDaoImpl<UserAccessLog, Long>(UserAccessLog.class);
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
   * 指定されたorm_rowidを持つ管理側アクセスログのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するUserAccessLogのインスタンス
   */
  public UserAccessLog loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して管理側アクセスログのインスタンスを取得します。
   * @param userAccessLogId 管理側アクセスログID
   * @return 主キー列の値に対応するUserAccessLogのインスタンス
   */
  public UserAccessLog load(Long userAccessLogId) {
    Object[] params = new Object[]{userAccessLogId};
    final String query = "SELECT * FROM USER_ACCESS_LOG"
        + " WHERE USER_ACCESS_LOG_ID = ?";
    List<UserAccessLog> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して管理側アクセスログが既に存在するかどうかを返します。
   * @param userAccessLogId 管理側アクセスログID
   * @return 主キー列の値に対応するUserAccessLogの行が存在すればtrue
   */
  public boolean exists(Long userAccessLogId) {
    Object[] params = new Object[]{userAccessLogId};
    final String query = "SELECT COUNT(*) FROM USER_ACCESS_LOG"
        + " WHERE USER_ACCESS_LOG_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規UserAccessLogをデータベースに追加します。
   * @param obj 追加対象のUserAccessLog
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(UserAccessLog obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規UserAccessLogをデータベースに追加します。
   * @param obj 追加対象のUserAccessLog
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(UserAccessLog obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 管理側アクセスログを更新します。
   * @param obj 更新対象のUserAccessLog
   */
  public void update(UserAccessLog obj) {
    genericDao.update(obj);
  }

  /**
   * 管理側アクセスログを更新します。
   * @param obj 更新対象のUserAccessLog
   */
  public void update(UserAccessLog obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 管理側アクセスログを削除します。
   * @param obj 削除対象のUserAccessLog
   */
  public void delete(UserAccessLog obj) {
    genericDao.delete(obj);
  }

  /**
   * 管理側アクセスログを削除します。
   * @param obj 削除対象のUserAccessLog
   */
  public void delete(UserAccessLog obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して管理側アクセスログを削除します。
   * @param userAccessLogId 管理側アクセスログID
   */
  public void delete(Long userAccessLogId) {
    Object[] params = new Object[]{userAccessLogId};
    final String query = "DELETE FROM USER_ACCESS_LOG"
        + " WHERE USER_ACCESS_LOG_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して管理側アクセスログのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するUserAccessLogのリスト
   */
  public List<UserAccessLog> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して管理側アクセスログのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するUserAccessLogのリスト
   */
  public List<UserAccessLog> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のUserAccessLogのリスト
   */
  public List<UserAccessLog> loadAll() {
    return genericDao.loadAll();
  }

}
