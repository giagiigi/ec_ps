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
import jp.co.sint.webshop.data.dao.SetCommodityCompositionDao;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;

/**
 * 套餐商品明细
 * 
 * @author KS.
 * 
 */
public class SetCommodityCompositionDaoImpl implements SetCommodityCompositionDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<SetCommodityComposition, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public SetCommodityCompositionDaoImpl() {
    genericDao = new GenericDaoImpl<SetCommodityComposition, Long>(SetCommodityComposition.class);
  }

  /**
   * SessionFactoryを取得します
   * 
   * @return SessionFactoryのインスタンス
   */
  public SessionFactory getSessionFactory() {
    return this.sessionFactory;
  }

  /**
   * SessionFactoryを設定します
   * 
   * @param factory
   *          SessionFactoryのインスタンス
   */
  public void setSessionFactory(SessionFactory factory) {
    this.sessionFactory = factory;
    genericDao.setSessionFactory(factory);
  }

  /**
   * 指定されたorm_rowidを持つ套餐商品明细のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するSetCommodityCompositionのインスタンス
   */
  public SetCommodityComposition loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して套餐商品明细のインスタンスを取得します。
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param childCommodityCode
   *          子商品コード
   * @return 主キー列の値に対応するSetCommodityCompositionのインスタンス
   */
  public List<SetCommodityComposition> loadChild(String shopCode, String childCommodityCode) {
    Object[] params = new Object[] { shopCode, childCommodityCode };
    final String query = "SELECT * FROM SET_COMMODITY_COMPOSITION" 
      + " WHERE SHOP_CODE = ?" 
      + " AND CHILD_COMMODITY_CODE = ?"
      + " ORDER BY CHILD_COMMODITY_CODE";
    List<SetCommodityComposition> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result;
    } else {
      return null;
    }

  }
  
  public List<SetCommodityComposition> load(String shopCode, String commodityCode) {
    Object[] params = new Object[] { shopCode, commodityCode };
    final String query = "SELECT * FROM SET_COMMODITY_COMPOSITION" 
      + " WHERE SHOP_CODE = ?" 
      + " AND COMMODITY_CODE = ?"
      + " ORDER BY CHILD_COMMODITY_CODE";
    List<SetCommodityComposition> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result;
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して套餐商品明细のインスタンスを取得します。
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param childCommodityCode
   *          子商品コード
   * @return 主キー列の値に対応するSetCommodityCompositionのインスタンス
   */
  public SetCommodityComposition load(String shopCode, String commodityCode, String childCommodityCode) {
    Object[] params = new Object[] { shopCode, commodityCode, childCommodityCode };
    final String query = "SELECT * FROM SET_COMMODITY_COMPOSITION WHERE SHOP_CODE = ? AND COMMODITY_CODE = ?"
        + " AND CHILD_COMMODITY_CODE = ?";
    List<SetCommodityComposition> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して套餐商品明细が既に存在するかどうかを返します。
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param childCommodityCode
   *          子商品コード
   * @return 主キー列の値に対応するSetCommodityCompositionの行が存在すればtrue
   */
  public boolean exists(String shopCode, String commodityCode, String childCommodityCode) {
    Object[] params = new Object[] { shopCode, commodityCode, childCommodityCode };
    final String query = "SELECT COUNT(*) FROM SET_COMMODITY_COMPOSITION WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ? AND CHILD_COMMODITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規SetCommodityCompositionをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のSetCommodityComposition
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(SetCommodityComposition obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規SetCommodityCompositionをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のSetCommodityComposition
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(SetCommodityComposition obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 套餐商品明细を更新します。
   * 
   * @param obj
   *          更新対象のSetCommodityComposition
   */
  public void update(SetCommodityComposition obj) {
    genericDao.update(obj);
  }

  /**
   * 套餐商品明细を更新します。
   * 
   * @param obj
   *          更新対象のSetCommodityComposition
   */
  public void update(SetCommodityComposition obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 套餐商品明细を削除します。
   * 
   * @param obj
   *          削除対象のSetCommodityComposition
   */
  public void delete(SetCommodityComposition obj) {
    genericDao.delete(obj);
  }

  /**
   * 套餐商品明细を削除します。
   * 
   * @param obj
   *          削除対象のSetCommodityComposition
   */
  public void delete(SetCommodityComposition obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して套餐商品明细を削除します。
   * 
   * @param shopCode
   *          ショップコード
   * @param commodityCode
   *          商品コード
   * @param childCommodityCode
   *          商品コード
   */
  public void delete(String shopCode, String commodityCode, String childCommodityCode) {
    Object[] params = new Object[] { shopCode, commodityCode, childCommodityCode };
    final String query = "DELETE FROM SET_COMMODITY_COMPOSITION WHERE SHOP_CODE = ? AND COMMODITY_CODE = ?"
        + " AND CHILD_COMMODITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して套餐商品明细のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するSetCommodityCompositionのリスト
   */
  public List<SetCommodityComposition> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して套餐商品明细のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するSetCommodityCompositionのリスト
   */
  public List<SetCommodityComposition> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のSetCommodityCompositionのリスト
   */
  public List<SetCommodityComposition> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public long updateByQuery(String sql, Object... params) {
    return genericDao.updateByQuery(sql, params);
  }
}
