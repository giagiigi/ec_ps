//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.data.dao.RegionDao;
import jp.co.sint.webshop.data.dto.Region;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 地域
 *
 * @author System Integrator Corp.
 *
 */
public class RegionDaoImpl implements RegionDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Region, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public RegionDaoImpl() {
    genericDao = new GenericDaoImpl<Region, Long>(Region.class);
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
  public Region loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して地域のインスタンスを取得します。
   * @param countryCode 国コード
   * @param regionCode 地域コード
   * @return 主キー列の値に対応するRegionのインスタンス
   */
  public Region load(String countryCode, String regionCode) {
    Object[] params = new Object[]{countryCode, regionCode};
    final String query = "SELECT * FROM REGION"
        + " WHERE COUNTRY_CODE = ?"
        + " AND REGION_CODE = ?";
    List<Region> result = genericDao.findByQuery(query, params);
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
  public boolean exists(String countryCode, String regionCode) {
    Object[] params = new Object[]{countryCode, regionCode};
    final String query = "SELECT COUNT(*) FROM REGION"
        + " WHERE COUNTRY_CODE = ?"
        + " AND REGION_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Regionをデータベースに追加します。
   * @param obj 追加対象のRegion
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Region obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Regionをデータベースに追加します。
   * @param obj 追加対象のRegion
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Region obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 地域を更新します。
   * @param obj 更新対象のRegion
   */
  public void update(Region obj) {
    genericDao.update(obj);
  }

  /**
   * 地域を更新します。
   * @param obj 更新対象のRegion
   */
  public void update(Region obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 地域を削除します。
   * @param obj 削除対象のRegion
   */
  public void delete(Region obj) {
    genericDao.delete(obj);
  }

  /**
   * 地域を削除します。
   * @param obj 削除対象のRegion
   */
  public void delete(Region obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して地域を削除します。
   * @param countryCode 国コード
   * @param regionCode 地域コード
   */
  public void delete(String countryCode, String regionCode) {
    Object[] params = new Object[]{countryCode, regionCode};
    final String query = "DELETE FROM REGION"
        + " WHERE COUNTRY_CODE = ?"
        + " AND REGION_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して地域のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRegionのリスト
   */
  public List<Region> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して地域のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRegionのリスト
   */
  public List<Region> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のRegionのリスト
   */
  public List<Region> loadAll() {
    return genericDao.loadAll();
  }

}
