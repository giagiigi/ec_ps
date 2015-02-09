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

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.AreaDao;
import jp.co.sint.webshop.data.dto.Area;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 区县
 *
 * @author Kousen.
 *
 */
public class AreaDaoImpl implements AreaDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Area, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public AreaDaoImpl() {
    genericDao = new GenericDaoImpl<Area, Long>(Area.class);
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
   * 指定されたorm_rowidを持つ区县のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するAreaのインスタンス
   */
  public Area loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して区县のインスタンスを取得します。
   * @param areaCode 区县编号
   * @return 主キー列の値に対応するAreaのインスタンス
   */
  public Area load(String areaCode) {
    Object[] params = new Object[]{areaCode};
    final String query = "SELECT * FROM AREA"
        + " WHERE AREA_CODE = ?"
        + " ORDER BY AREA_CODE";
    List<Area> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して区县が既に存在するかどうかを返します。
   * @param areaCode 区县编号
   * @return 主キー列の値に対応するAreaの行が存在すればtrue
   */
  public boolean exists(String areaCode) {
    Object[] params = new Object[]{areaCode};
    final String query = "SELECT COUNT(*) FROM AREA"
        + " WHERE AREA_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Areaをデータベースに追加します。
   * @param obj 追加対象のArea
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Area obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Areaをデータベースに追加します。
   * @param obj 追加対象のArea
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Area obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 区县を更新します。
   * @param obj 更新対象のArea
   */
  public void update(Area obj) {
    genericDao.update(obj);
  }

  /**
   * 区县を更新します。
   * @param obj 更新対象のArea
   */
  public void update(Area obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 区县を削除します。
   * @param obj 削除対象のArea
   */
  public void delete(Area obj) {
    genericDao.delete(obj);
  }

  /**
   * 区县を削除します。
   * @param obj 削除対象のArea
   */
  public void delete(Area obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して区县を削除します。
   * @param areaCode 区县编号
   */
  public void delete(String areaCode) {
    Object[] params = new Object[]{areaCode};
    final String query = "DELETE FROM AREA"
        + " WHERE AREA_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して区县のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するAreaのリスト
   */
  public List<Area> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して区县のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するAreaのリスト
   */
  public List<Area> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のAreaのリスト
   */
  public List<Area> loadAll() {
    return genericDao.loadAll();
  }
  /**
   * 按指定条件，取得区县信息
   *
   * @param prefectureCode 地域编号
   * @param cityCode 城市编号
   * @return 区县信息
   */
  public List<Area> load(String prefectureCode, String cityCode){
    Object[] params = new Object[]{prefectureCode, cityCode};
    final String query = "SELECT * FROM AREA"
        + " WHERE PREFECTURE_CODE = ?"
        + " AND CITY_CODE = ? ";
    List<Area> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result;
    } else {
      return null;
    }
  }
}
