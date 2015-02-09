//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.CityDao;
import jp.co.sint.webshop.data.dto.City;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 地域
 *
 * @author System Integrator Corp.
 *
 */
public class CityDaoImpl implements CityDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<City, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CityDaoImpl() {
    genericDao = new GenericDaoImpl<City, Long>(City.class);
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
   * 指定されたorm_rowidを持つ地域のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するRegionのインスタンス
   */
  public City loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して地域のインスタンスを取得します。
   * @param countryCode 国コード
   * @param regionCode 地域コード
   * @return 主キー列の値に対応するRegionのインスタンス
   */
  public City load(String countryCode, String regionCode,String cityCode) {
    Object[] params = new Object[]{countryCode, regionCode,cityCode};
    final String query = "SELECT * FROM CITY"
        + " WHERE COUNTRY_CODE = ?"
        + " AND REGION_CODE = ?"
        + " AND CITY_CODE = ?";
    List<City> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して地域が既に存在するかどうかを返します。
   * @param countryCode 国コード
   * @param regionCode 地域コード
   * @return 主キー列の値に対応するRegionの行が存在すればtrue
   */
  public boolean exists(String countryCode, String regionCode,String cityCode) {
    Object[] params = new Object[]{countryCode, regionCode,cityCode};
    final String query = "SELECT COUNT(*) FROM REGION"
        + " WHERE COUNTRY_CODE = ?"
        + " AND REGION_CODE = ?"
        + " AND CITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Regionをデータベースに追加します。
   * @param obj 追加対象のRegion
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(City obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Regionをデータベースに追加します。
   * @param obj 追加対象のRegion
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(City obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 地域を更新します。
   * @param obj 更新対象のRegion
   */
  public void update(City obj) {
    genericDao.update(obj);
  }

  /**
   * 地域を更新します。
   * @param obj 更新対象のRegion
   */
  public void update(City obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 地域を削除します。
   * @param obj 削除対象のRegion
   */
  public void delete(City obj) {
    genericDao.delete(obj);
  }

  /**
   * 地域を削除します。
   * @param obj 削除対象のRegion
   */
  public void delete(City obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して地域を削除します。
   * @param countryCode 国コード
   * @param regionCode 地域コード
   */
  public void delete(String countryCode, String regionCode,String cityCode) {
    Object[] params = new Object[]{countryCode, regionCode,cityCode};
    final String query = "DELETE FROM CITY"
        + " WHERE COUNTRY_CODE = ?"
        + " AND REGION_CODE = ?"
        + " AND CITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して地域のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRegionのリスト
   */
  public List<City> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して地域のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRegionのリスト
   */
  public List<City> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のRegionのリスト
   */
  public List<City> loadAll() {
    return genericDao.loadAll();
  }
//2013/04/11 配送公司设定对应 ob add start
  /**
   * 按地域取得城市信息
   * @param regionCode 地域编号
   * @return 城市信息
   */
  public List<City> load(String regionCode){
    Object[] params = new Object[]{regionCode};
    final String query = "SELECT * FROM CITY"
        + " WHERE  REGION_CODE = ?";
    List<City> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result;
    } else {
      return null;
    }
  }
  //2013/04/11 配送公司设定对应 ob add end
}
