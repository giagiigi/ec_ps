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
import jp.co.sint.webshop.data.dao.UserAccountDao;
import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 管理ユーザ
 *
 * @author System Integrator Corp.
 *
 */
public class UserAccountDaoImpl implements UserAccountDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<UserAccount, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public UserAccountDaoImpl() {
    genericDao = new GenericDaoImpl<UserAccount, Long>(UserAccount.class);
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
   * 指定されたorm_rowidを持つ管理ユーザのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するUserAccountのインスタンス
   */
  public UserAccount loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して管理ユーザのインスタンスを取得します。
   * @param userCode ユーザコード
   * @return 主キー列の値に対応するUserAccountのインスタンス
   */
  public UserAccount load(Long userCode) {
    Object[] params = new Object[]{userCode};
    final String query = "SELECT * FROM USER_ACCOUNT"
        + " WHERE USER_CODE = ?";
    List<UserAccount> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して管理ユーザが既に存在するかどうかを返します。
   * @param userCode ユーザコード
   * @return 主キー列の値に対応するUserAccountの行が存在すればtrue
   */
  public boolean exists(Long userCode) {
    Object[] params = new Object[]{userCode};
    final String query = "SELECT COUNT(*) FROM USER_ACCOUNT"
        + " WHERE USER_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規UserAccountをデータベースに追加します。
   * @param obj 追加対象のUserAccount
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(UserAccount obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規UserAccountをデータベースに追加します。
   * @param obj 追加対象のUserAccount
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(UserAccount obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 管理ユーザを更新します。
   * @param obj 更新対象のUserAccount
   */
  public void update(UserAccount obj) {
    genericDao.update(obj);
  }

  /**
   * 管理ユーザを更新します。
   * @param obj 更新対象のUserAccount
   */
  public void update(UserAccount obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 管理ユーザを削除します。
   * @param obj 削除対象のUserAccount
   */
  public void delete(UserAccount obj) {
    genericDao.delete(obj);
  }

  /**
   * 管理ユーザを削除します。
   * @param obj 削除対象のUserAccount
   */
  public void delete(UserAccount obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して管理ユーザを削除します。
   * @param userCode ユーザコード
   */
  public void delete(Long userCode) {
    Object[] params = new Object[]{userCode};
    final String query = "DELETE FROM USER_ACCOUNT"
        + " WHERE USER_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して管理ユーザのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するUserAccountのリスト
   */
  public List<UserAccount> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して管理ユーザのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するUserAccountのリスト
   */
  public List<UserAccount> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のUserAccountのリスト
   */
  public List<UserAccount> loadAll() {
    return genericDao.loadAll();
  }

}
