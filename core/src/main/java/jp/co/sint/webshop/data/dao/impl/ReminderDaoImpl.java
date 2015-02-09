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
import jp.co.sint.webshop.data.dao.ReminderDao;
import jp.co.sint.webshop.data.dto.Reminder;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * リマインダ
 *
 * @author System Integrator Corp.
 *
 */
public class ReminderDaoImpl implements ReminderDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Reminder, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public ReminderDaoImpl() {
    genericDao = new GenericDaoImpl<Reminder, Long>(Reminder.class);
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
   * 指定されたorm_rowidを持つリマインダのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するReminderのインスタンス
   */
  public Reminder loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してリマインダのインスタンスを取得します。
   * @param reissuanceKey 再発行キー
   * @return 主キー列の値に対応するReminderのインスタンス
   */
  public Reminder load(String reissuanceKey) {
    Object[] params = new Object[]{reissuanceKey};
    final String query = "SELECT * FROM REMINDER"
        + " WHERE REISSUANCE_KEY = ?";
    List<Reminder> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してリマインダが既に存在するかどうかを返します。
   * @param reissuanceKey 再発行キー
   * @return 主キー列の値に対応するReminderの行が存在すればtrue
   */
  public boolean exists(String reissuanceKey) {
    Object[] params = new Object[]{reissuanceKey};
    final String query = "SELECT COUNT(*) FROM REMINDER"
        + " WHERE REISSUANCE_KEY = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Reminderをデータベースに追加します。
   * @param obj 追加対象のReminder
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Reminder obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Reminderをデータベースに追加します。
   * @param obj 追加対象のReminder
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Reminder obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * リマインダを更新します。
   * @param obj 更新対象のReminder
   */
  public void update(Reminder obj) {
    genericDao.update(obj);
  }

  /**
   * リマインダを更新します。
   * @param obj 更新対象のReminder
   */
  public void update(Reminder obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * リマインダを削除します。
   * @param obj 削除対象のReminder
   */
  public void delete(Reminder obj) {
    genericDao.delete(obj);
  }

  /**
   * リマインダを削除します。
   * @param obj 削除対象のReminder
   */
  public void delete(Reminder obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してリマインダを削除します。
   * @param reissuanceKey 再発行キー
   */
  public void delete(String reissuanceKey) {
    Object[] params = new Object[]{reissuanceKey};
    final String query = "DELETE FROM REMINDER"
        + " WHERE REISSUANCE_KEY = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してリマインダのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するReminderのリスト
   */
  public List<Reminder> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してリマインダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するReminderのリスト
   */
  public List<Reminder> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のReminderのリスト
   */
  public List<Reminder> loadAll() {
    return genericDao.loadAll();
  }

}
