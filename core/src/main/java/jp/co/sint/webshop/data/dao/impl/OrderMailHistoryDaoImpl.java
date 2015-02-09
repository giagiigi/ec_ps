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
import jp.co.sint.webshop.data.dao.OrderMailHistoryDao;
import jp.co.sint.webshop.data.dto.OrderMailHistory;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 受注メール送信履歴
 *
 * @author System Integrator Corp.
 *
 */
public class OrderMailHistoryDaoImpl implements OrderMailHistoryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<OrderMailHistory, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public OrderMailHistoryDaoImpl() {
    genericDao = new GenericDaoImpl<OrderMailHistory, Long>(OrderMailHistory.class);
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
   * 指定されたorm_rowidを持つ受注メール送信履歴のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するOrderMailHistoryのインスタンス
   */
  public OrderMailHistory loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して受注メール送信履歴のインスタンスを取得します。
   * @param orderMailHistoryId 受注メール送信履歴ID
   * @return 主キー列の値に対応するOrderMailHistoryのインスタンス
   */
  public OrderMailHistory load(Long orderMailHistoryId) {
    Object[] params = new Object[]{orderMailHistoryId};
    final String query = "SELECT * FROM ORDER_MAIL_HISTORY"
        + " WHERE ORDER_MAIL_HISTORY_ID = ?";
    List<OrderMailHistory> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して受注メール送信履歴が既に存在するかどうかを返します。
   * @param orderMailHistoryId 受注メール送信履歴ID
   * @return 主キー列の値に対応するOrderMailHistoryの行が存在すればtrue
   */
  public boolean exists(Long orderMailHistoryId) {
    Object[] params = new Object[]{orderMailHistoryId};
    final String query = "SELECT COUNT(*) FROM ORDER_MAIL_HISTORY"
        + " WHERE ORDER_MAIL_HISTORY_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規OrderMailHistoryをデータベースに追加します。
   * @param obj 追加対象のOrderMailHistory
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(OrderMailHistory obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規OrderMailHistoryをデータベースに追加します。
   * @param obj 追加対象のOrderMailHistory
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(OrderMailHistory obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 受注メール送信履歴を更新します。
   * @param obj 更新対象のOrderMailHistory
   */
  public void update(OrderMailHistory obj) {
    genericDao.update(obj);
  }

  /**
   * 受注メール送信履歴を更新します。
   * @param obj 更新対象のOrderMailHistory
   */
  public void update(OrderMailHistory obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 受注メール送信履歴を削除します。
   * @param obj 削除対象のOrderMailHistory
   */
  public void delete(OrderMailHistory obj) {
    genericDao.delete(obj);
  }

  /**
   * 受注メール送信履歴を削除します。
   * @param obj 削除対象のOrderMailHistory
   */
  public void delete(OrderMailHistory obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して受注メール送信履歴を削除します。
   * @param orderMailHistoryId 受注メール送信履歴ID
   */
  public void delete(Long orderMailHistoryId) {
    Object[] params = new Object[]{orderMailHistoryId};
    final String query = "DELETE FROM ORDER_MAIL_HISTORY"
        + " WHERE ORDER_MAIL_HISTORY_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して受注メール送信履歴のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するOrderMailHistoryのリスト
   */
  public List<OrderMailHistory> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して受注メール送信履歴のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するOrderMailHistoryのリスト
   */
  public List<OrderMailHistory> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のOrderMailHistoryのリスト
   */
  public List<OrderMailHistory> loadAll() {
    return genericDao.loadAll();
  }

}
