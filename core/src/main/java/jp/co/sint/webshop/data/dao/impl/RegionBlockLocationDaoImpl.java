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
import jp.co.sint.webshop.data.dao.RegionBlockLocationDao;
import jp.co.sint.webshop.data.dto.RegionBlockLocation;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 地域ブロック配置
 *
 * @author System Integrator Corp.
 *
 */
public class RegionBlockLocationDaoImpl implements RegionBlockLocationDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<RegionBlockLocation, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public RegionBlockLocationDaoImpl() {
    genericDao = new GenericDaoImpl<RegionBlockLocation, Long>(RegionBlockLocation.class);
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
   * 指定されたorm_rowidを持つ地域ブロック配置のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するRegionBlockLocationのインスタンス
   */
  public RegionBlockLocation loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して地域ブロック配置のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param regionBlockId 地域ブロックID
   * @param prefectureCode 都道府県コード
   * @return 主キー列の値に対応するRegionBlockLocationのインスタンス
   */
  public RegionBlockLocation load(String shopCode, Long regionBlockId, String prefectureCode) {
    Object[] params = new Object[]{shopCode, regionBlockId, prefectureCode};
    final String query = "SELECT * FROM REGION_BLOCK_LOCATION"
        + " WHERE SHOP_CODE = ?"
        + " AND REGION_BLOCK_ID = ?"
        + " AND PREFECTURE_CODE = ?";
    List<RegionBlockLocation> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して地域ブロック配置が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param regionBlockId 地域ブロックID
   * @param prefectureCode 都道府県コード
   * @return 主キー列の値に対応するRegionBlockLocationの行が存在すればtrue
   */
  public boolean exists(String shopCode, Long regionBlockId, String prefectureCode) {
    Object[] params = new Object[]{shopCode, regionBlockId, prefectureCode};
    final String query = "SELECT COUNT(*) FROM REGION_BLOCK_LOCATION"
        + " WHERE SHOP_CODE = ?"
        + " AND REGION_BLOCK_ID = ?"
        + " AND PREFECTURE_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規RegionBlockLocationをデータベースに追加します。
   * @param obj 追加対象のRegionBlockLocation
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(RegionBlockLocation obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規RegionBlockLocationをデータベースに追加します。
   * @param obj 追加対象のRegionBlockLocation
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(RegionBlockLocation obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 地域ブロック配置を更新します。
   * @param obj 更新対象のRegionBlockLocation
   */
  public void update(RegionBlockLocation obj) {
    genericDao.update(obj);
  }

  /**
   * 地域ブロック配置を更新します。
   * @param obj 更新対象のRegionBlockLocation
   */
  public void update(RegionBlockLocation obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 地域ブロック配置を削除します。
   * @param obj 削除対象のRegionBlockLocation
   */
  public void delete(RegionBlockLocation obj) {
    genericDao.delete(obj);
  }

  /**
   * 地域ブロック配置を削除します。
   * @param obj 削除対象のRegionBlockLocation
   */
  public void delete(RegionBlockLocation obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して地域ブロック配置を削除します。
   * @param shopCode ショップコード
   * @param regionBlockId 地域ブロックID
   * @param prefectureCode 都道府県コード
   */
  public void delete(String shopCode, Long regionBlockId, String prefectureCode) {
    Object[] params = new Object[]{shopCode, regionBlockId, prefectureCode};
    final String query = "DELETE FROM REGION_BLOCK_LOCATION"
        + " WHERE SHOP_CODE = ?"
        + " AND REGION_BLOCK_ID = ?"
        + " AND PREFECTURE_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して地域ブロック配置のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRegionBlockLocationのリスト
   */
  public List<RegionBlockLocation> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して地域ブロック配置のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRegionBlockLocationのリスト
   */
  public List<RegionBlockLocation> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のRegionBlockLocationのリスト
   */
  public List<RegionBlockLocation> loadAll() {
    return genericDao.loadAll();
  }

}
