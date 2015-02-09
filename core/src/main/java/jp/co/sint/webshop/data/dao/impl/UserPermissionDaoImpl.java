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
import jp.co.sint.webshop.data.dao.UserPermissionDao;
import jp.co.sint.webshop.data.dto.UserPermission;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 管理ユーザ権限
 *
 * @author System Integrator Corp.
 *
 */
public class UserPermissionDaoImpl implements UserPermissionDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<UserPermission, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public UserPermissionDaoImpl() {
    genericDao = new GenericDaoImpl<UserPermission, Long>(UserPermission.class);
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
   * 指定されたorm_rowidを持つ管理ユーザ権限のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するUserPermissionのインスタンス
   */
  public UserPermission loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して管理ユーザ権限のインスタンスを取得します。
   * @param permissionCode 権限コード
   * @param userCode ユーザコード
   * @return 主キー列の値に対応するUserPermissionのインスタンス
   */
  public UserPermission load(String permissionCode, Long userCode) {
    Object[] params = new Object[]{permissionCode, userCode};
    final String query = "SELECT * FROM USER_PERMISSION"
        + " WHERE PERMISSION_CODE = ?"
        + " AND USER_CODE = ?";
    List<UserPermission> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して管理ユーザ権限が既に存在するかどうかを返します。
   * @param permissionCode 権限コード
   * @param userCode ユーザコード
   * @return 主キー列の値に対応するUserPermissionの行が存在すればtrue
   */
  public boolean exists(String permissionCode, Long userCode) {
    Object[] params = new Object[]{permissionCode, userCode};
    final String query = "SELECT COUNT(*) FROM USER_PERMISSION"
        + " WHERE PERMISSION_CODE = ?"
        + " AND USER_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規UserPermissionをデータベースに追加します。
   * @param obj 追加対象のUserPermission
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(UserPermission obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規UserPermissionをデータベースに追加します。
   * @param obj 追加対象のUserPermission
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(UserPermission obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 管理ユーザ権限を更新します。
   * @param obj 更新対象のUserPermission
   */
  public void update(UserPermission obj) {
    genericDao.update(obj);
  }

  /**
   * 管理ユーザ権限を更新します。
   * @param obj 更新対象のUserPermission
   */
  public void update(UserPermission obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 管理ユーザ権限を削除します。
   * @param obj 削除対象のUserPermission
   */
  public void delete(UserPermission obj) {
    genericDao.delete(obj);
  }

  /**
   * 管理ユーザ権限を削除します。
   * @param obj 削除対象のUserPermission
   */
  public void delete(UserPermission obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して管理ユーザ権限を削除します。
   * @param permissionCode 権限コード
   * @param userCode ユーザコード
   */
  public void delete(String permissionCode, Long userCode) {
    Object[] params = new Object[]{permissionCode, userCode};
    final String query = "DELETE FROM USER_PERMISSION"
        + " WHERE PERMISSION_CODE = ?"
        + " AND USER_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して管理ユーザ権限のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するUserPermissionのリスト
   */
  public List<UserPermission> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して管理ユーザ権限のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するUserPermissionのリスト
   */
  public List<UserPermission> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のUserPermissionのリスト
   */
  public List<UserPermission> loadAll() {
    return genericDao.loadAll();
  }

}
