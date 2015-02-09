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
import jp.co.sint.webshop.data.dao.HolidayDao;
import jp.co.sint.webshop.data.dto.Holiday;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 休日
 *
 * @author System Integrator Corp.
 *
 */
public class HolidayDaoImpl implements HolidayDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Holiday, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public HolidayDaoImpl() {
    genericDao = new GenericDaoImpl<Holiday, Long>(Holiday.class);
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
   * 指定されたorm_rowidを持つ休日のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するHolidayのインスタンス
   */
  public Holiday loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して休日のインスタンスを取得します。
   * @param holidayId 休日ID
   * @return 主キー列の値に対応するHolidayのインスタンス
   */
  public Holiday load(Long holidayId) {
    Object[] params = new Object[]{holidayId};
    final String query = "SELECT * FROM HOLIDAY"
        + " WHERE HOLIDAY_ID = ?";
    List<Holiday> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して休日が既に存在するかどうかを返します。
   * @param holidayId 休日ID
   * @return 主キー列の値に対応するHolidayの行が存在すればtrue
   */
  public boolean exists(Long holidayId) {
    Object[] params = new Object[]{holidayId};
    final String query = "SELECT COUNT(*) FROM HOLIDAY"
        + " WHERE HOLIDAY_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Holidayをデータベースに追加します。
   * @param obj 追加対象のHoliday
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Holiday obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Holidayをデータベースに追加します。
   * @param obj 追加対象のHoliday
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Holiday obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 休日を更新します。
   * @param obj 更新対象のHoliday
   */
  public void update(Holiday obj) {
    genericDao.update(obj);
  }

  /**
   * 休日を更新します。
   * @param obj 更新対象のHoliday
   */
  public void update(Holiday obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 休日を削除します。
   * @param obj 削除対象のHoliday
   */
  public void delete(Holiday obj) {
    genericDao.delete(obj);
  }

  /**
   * 休日を削除します。
   * @param obj 削除対象のHoliday
   */
  public void delete(Holiday obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して休日を削除します。
   * @param holidayId 休日ID
   */
  public void delete(Long holidayId) {
    Object[] params = new Object[]{holidayId};
    final String query = "DELETE FROM HOLIDAY"
        + " WHERE HOLIDAY_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して休日のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するHolidayのリスト
   */
  public List<Holiday> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して休日のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するHolidayのリスト
   */
  public List<Holiday> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のHolidayのリスト
   */
  public List<Holiday> loadAll() {
    return genericDao.loadAll();
  }

}
