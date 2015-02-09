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
import jp.co.sint.webshop.data.dao.RegionBlockDao;
import jp.co.sint.webshop.data.dto.RegionBlock;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 地域ブロック
 *
 * @author System Integrator Corp.
 *
 */
public class RegionBlockDaoImpl implements RegionBlockDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<RegionBlock, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public RegionBlockDaoImpl() {
    genericDao = new GenericDaoImpl<RegionBlock, Long>(RegionBlock.class);
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
   * 指定されたorm_rowidを持つ地域ブロックのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するRegionBlockのインスタンス
   */
  public RegionBlock loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して地域ブロックのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param regionBlockId 地域ブロックID
   * @return 主キー列の値に対応するRegionBlockのインスタンス
   */
  public RegionBlock load(String shopCode, Long regionBlockId) {
    Object[] params = new Object[]{shopCode, regionBlockId};
    final String query = "SELECT * FROM REGION_BLOCK"
        + " WHERE SHOP_CODE = ?"
        + " AND REGION_BLOCK_ID = ?";
    List<RegionBlock> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して地域ブロックが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param regionBlockId 地域ブロックID
   * @return 主キー列の値に対応するRegionBlockの行が存在すればtrue
   */
  public boolean exists(String shopCode, Long regionBlockId) {
    Object[] params = new Object[]{shopCode, regionBlockId};
    final String query = "SELECT COUNT(*) FROM REGION_BLOCK"
        + " WHERE SHOP_CODE = ?"
        + " AND REGION_BLOCK_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規RegionBlockをデータベースに追加します。
   * @param obj 追加対象のRegionBlock
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(RegionBlock obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規RegionBlockをデータベースに追加します。
   * @param obj 追加対象のRegionBlock
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(RegionBlock obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 地域ブロックを更新します。
   * @param obj 更新対象のRegionBlock
   */
  public void update(RegionBlock obj) {
    genericDao.update(obj);
  }

  /**
   * 地域ブロックを更新します。
   * @param obj 更新対象のRegionBlock
   */
  public void update(RegionBlock obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 地域ブロックを削除します。
   * @param obj 削除対象のRegionBlock
   */
  public void delete(RegionBlock obj) {
    genericDao.delete(obj);
  }

  /**
   * 地域ブロックを削除します。
   * @param obj 削除対象のRegionBlock
   */
  public void delete(RegionBlock obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して地域ブロックを削除します。
   * @param shopCode ショップコード
   * @param regionBlockId 地域ブロックID
   */
  public void delete(String shopCode, Long regionBlockId) {
    Object[] params = new Object[]{shopCode, regionBlockId};
    final String query = "DELETE FROM REGION_BLOCK"
        + " WHERE SHOP_CODE = ?"
        + " AND REGION_BLOCK_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して地域ブロックのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRegionBlockのリスト
   */
  public List<RegionBlock> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して地域ブロックのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRegionBlockのリスト
   */
  public List<RegionBlock> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のRegionBlockのリスト
   */
  public List<RegionBlock> loadAll() {
    return genericDao.loadAll();
  }

}
