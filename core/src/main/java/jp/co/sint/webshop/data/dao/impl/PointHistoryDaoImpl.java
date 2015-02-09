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
import jp.co.sint.webshop.data.dao.PointHistoryDao;
import jp.co.sint.webshop.data.dto.PointHistory;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * ポイント履歴
 *
 * @author System Integrator Corp.
 *
 */
public class PointHistoryDaoImpl implements PointHistoryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<PointHistory, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public PointHistoryDaoImpl() {
    genericDao = new GenericDaoImpl<PointHistory, Long>(PointHistory.class);
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
   * 指定されたorm_rowidを持つポイント履歴のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するPointHistoryのインスタンス
   */
  public PointHistory loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してポイント履歴のインスタンスを取得します。
   * @param pointHistoryId ポイント履歴ID
   * @return 主キー列の値に対応するPointHistoryのインスタンス
   */
  public PointHistory load(Long pointHistoryId) {
    Object[] params = new Object[]{pointHistoryId};
    final String query = "SELECT * FROM POINT_HISTORY"
        + " WHERE POINT_HISTORY_ID = ?";
    List<PointHistory> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してポイント履歴が既に存在するかどうかを返します。
   * @param pointHistoryId ポイント履歴ID
   * @return 主キー列の値に対応するPointHistoryの行が存在すればtrue
   */
  public boolean exists(Long pointHistoryId) {
    Object[] params = new Object[]{pointHistoryId};
    final String query = "SELECT COUNT(*) FROM POINT_HISTORY"
        + " WHERE POINT_HISTORY_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規PointHistoryをデータベースに追加します。
   * @param obj 追加対象のPointHistory
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PointHistory obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規PointHistoryをデータベースに追加します。
   * @param obj 追加対象のPointHistory
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PointHistory obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * ポイント履歴を更新します。
   * @param obj 更新対象のPointHistory
   */
  public void update(PointHistory obj) {
    genericDao.update(obj);
  }

  /**
   * ポイント履歴を更新します。
   * @param obj 更新対象のPointHistory
   */
  public void update(PointHistory obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * ポイント履歴を削除します。
   * @param obj 削除対象のPointHistory
   */
  public void delete(PointHistory obj) {
    genericDao.delete(obj);
  }

  /**
   * ポイント履歴を削除します。
   * @param obj 削除対象のPointHistory
   */
  public void delete(PointHistory obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してポイント履歴を削除します。
   * @param pointHistoryId ポイント履歴ID
   */
  public void delete(Long pointHistoryId) {
    Object[] params = new Object[]{pointHistoryId};
    final String query = "DELETE FROM POINT_HISTORY"
        + " WHERE POINT_HISTORY_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してポイント履歴のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPointHistoryのリスト
   */
  public List<PointHistory> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してポイント履歴のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPointHistoryのリスト
   */
  public List<PointHistory> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のPointHistoryのリスト
   */
  public List<PointHistory> loadAll() {
    return genericDao.loadAll();
  }

}
