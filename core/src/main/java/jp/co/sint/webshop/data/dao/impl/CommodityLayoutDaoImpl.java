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
import jp.co.sint.webshop.data.dao.CommodityLayoutDao;
import jp.co.sint.webshop.data.dto.CommodityLayout;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 商品レイアウト
 *
 * @author System Integrator Corp.
 *
 */
public class CommodityLayoutDaoImpl implements CommodityLayoutDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CommodityLayout, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CommodityLayoutDaoImpl() {
    genericDao = new GenericDaoImpl<CommodityLayout, Long>(CommodityLayout.class);
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
   * 指定されたorm_rowidを持つ商品レイアウトのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCommodityLayoutのインスタンス
   */
  public CommodityLayout loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して商品レイアウトのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param partsCode パーツコード
   * @return 主キー列の値に対応するCommodityLayoutのインスタンス
   */
  public CommodityLayout load(String shopCode, String partsCode) {
    Object[] params = new Object[]{shopCode, partsCode};
    final String query = "SELECT * FROM COMMODITY_LAYOUT"
        + " WHERE SHOP_CODE = ?"
        + " AND PARTS_CODE = ?";
    List<CommodityLayout> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して商品レイアウトが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param partsCode パーツコード
   * @return 主キー列の値に対応するCommodityLayoutの行が存在すればtrue
   */
  public boolean exists(String shopCode, String partsCode) {
    Object[] params = new Object[]{shopCode, partsCode};
    final String query = "SELECT COUNT(*) FROM COMMODITY_LAYOUT"
        + " WHERE SHOP_CODE = ?"
        + " AND PARTS_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CommodityLayoutをデータベースに追加します。
   * @param obj 追加対象のCommodityLayout
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CommodityLayout obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CommodityLayoutをデータベースに追加します。
   * @param obj 追加対象のCommodityLayout
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CommodityLayout obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 商品レイアウトを更新します。
   * @param obj 更新対象のCommodityLayout
   */
  public void update(CommodityLayout obj) {
    genericDao.update(obj);
  }

  /**
   * 商品レイアウトを更新します。
   * @param obj 更新対象のCommodityLayout
   */
  public void update(CommodityLayout obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 商品レイアウトを削除します。
   * @param obj 削除対象のCommodityLayout
   */
  public void delete(CommodityLayout obj) {
    genericDao.delete(obj);
  }

  /**
   * 商品レイアウトを削除します。
   * @param obj 削除対象のCommodityLayout
   */
  public void delete(CommodityLayout obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して商品レイアウトを削除します。
   * @param shopCode ショップコード
   * @param partsCode パーツコード
   */
  public void delete(String shopCode, String partsCode) {
    Object[] params = new Object[]{shopCode, partsCode};
    final String query = "DELETE FROM COMMODITY_LAYOUT"
        + " WHERE SHOP_CODE = ?"
        + " AND PARTS_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して商品レイアウトのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommodityLayoutのリスト
   */
  public List<CommodityLayout> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して商品レイアウトのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityLayoutのリスト
   */
  public List<CommodityLayout> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCommodityLayoutのリスト
   */
  public List<CommodityLayout> loadAll() {
    return genericDao.loadAll();
  }

}
