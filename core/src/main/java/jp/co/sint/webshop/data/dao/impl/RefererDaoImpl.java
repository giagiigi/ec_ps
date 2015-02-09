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
import jp.co.sint.webshop.data.dao.RefererDao;
import jp.co.sint.webshop.data.dto.Referer;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * リファラー
 *
 * @author System Integrator Corp.
 *
 */
public class RefererDaoImpl implements RefererDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Referer, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public RefererDaoImpl() {
    genericDao = new GenericDaoImpl<Referer, Long>(Referer.class);
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
   * 指定されたorm_rowidを持つリファラーのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するRefererのインスタンス
   */
  public Referer loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してリファラーのインスタンスを取得します。
   * @param refererId リファラーID
   * @return 主キー列の値に対応するRefererのインスタンス
   */
  public Referer load(Long refererId) {
    Object[] params = new Object[]{refererId};
    final String query = "SELECT * FROM REFERER"
        + " WHERE REFERER_ID = ?";
    List<Referer> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してリファラーが既に存在するかどうかを返します。
   * @param refererId リファラーID
   * @return 主キー列の値に対応するRefererの行が存在すればtrue
   */
  public boolean exists(Long refererId) {
    Object[] params = new Object[]{refererId};
    final String query = "SELECT COUNT(*) FROM REFERER"
        + " WHERE REFERER_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Refererをデータベースに追加します。
   * @param obj 追加対象のReferer
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Referer obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Refererをデータベースに追加します。
   * @param obj 追加対象のReferer
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Referer obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * リファラーを更新します。
   * @param obj 更新対象のReferer
   */
  public void update(Referer obj) {
    genericDao.update(obj);
  }

  /**
   * リファラーを更新します。
   * @param obj 更新対象のReferer
   */
  public void update(Referer obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * リファラーを削除します。
   * @param obj 削除対象のReferer
   */
  public void delete(Referer obj) {
    genericDao.delete(obj);
  }

  /**
   * リファラーを削除します。
   * @param obj 削除対象のReferer
   */
  public void delete(Referer obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してリファラーを削除します。
   * @param refererId リファラーID
   */
  public void delete(Long refererId) {
    Object[] params = new Object[]{refererId};
    final String query = "DELETE FROM REFERER"
        + " WHERE REFERER_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してリファラーのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRefererのリスト
   */
  public List<Referer> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してリファラーのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRefererのリスト
   */
  public List<Referer> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のRefererのリスト
   */
  public List<Referer> loadAll() {
    return genericDao.loadAll();
  }

}
