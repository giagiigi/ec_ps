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
import jp.co.sint.webshop.data.dao.CommodityPriceChangeHistoryDao;
import jp.co.sint.webshop.data.dto.CommodityPriceChangeHistory;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 商品詳細
 *
 * @author System Integrator Corp.
 *
 */
public class CommodityPriceChangeHistoryDaoImpl implements CommodityPriceChangeHistoryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CommodityPriceChangeHistory, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CommodityPriceChangeHistoryDaoImpl() {
    genericDao = new GenericDaoImpl<CommodityPriceChangeHistory, Long>(CommodityPriceChangeHistory.class);
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
   * 指定されたorm_rowidを持つ商品詳細のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCommodityDetailのインスタンス
   */
  public CommodityPriceChangeHistory loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して商品詳細のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するCommodityDetailのインスタンス
   */
  public CommodityPriceChangeHistory load(String commodityCode) {
    Object[] params = new Object[]{commodityCode};
    final String query = "SELECT * FROM commodity_price_change_history"
        + " WHERE commodity_code = ?";
    List<CommodityPriceChangeHistory> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して商品詳細が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するCommodityDetailの行が存在すればtrue
   */
  public boolean exists(String CommmodityCode) {
    Object[] params = new Object[]{CommmodityCode};
    final String query = "SELECT COUNT(*) FROM commodity_price_change_history"
        + " WHERE SHOP_CODE = ?"
        + " AND SKU_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CommodityDetailをデータベースに追加します。
   * @param obj 追加対象のCommodityDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CommodityPriceChangeHistory obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CommodityDetailをデータベースに追加します。
   * @param obj 追加対象のCommodityDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CommodityPriceChangeHistory obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 商品詳細を更新します。
   * @param obj 更新対象のCommodityDetail
   */
  public void update(CommodityPriceChangeHistory obj) {
    genericDao.update(obj);
  }

  /**
   * 商品詳細を更新します。
   * @param obj 更新対象のCommodityDetail
   */
  public void update(CommodityPriceChangeHistory obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 商品詳細を削除します。
   * @param obj 削除対象のCommodityDetail
   */
  public void delete(CommodityPriceChangeHistory obj) {
    genericDao.delete(obj);
  }

  /**
   * 商品詳細を削除します。
   * @param obj 削除対象のCommodityDetail
   */
  public void delete(CommodityPriceChangeHistory obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して商品詳細を削除します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   */
  public void delete(String commmodityCode) {
    Object[] params = new Object[]{commmodityCode};
    final String query = "DELETE FROM commodity_price_change_history"
        + " WHERE SHOP_CODE = ?"
        + " AND SKU_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して商品詳細のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommodityDetailのリスト
   */
  public List<CommodityPriceChangeHistory> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して商品詳細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityDetailのリスト
   */
  public List<CommodityPriceChangeHistory> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCommodityDetailのリスト
   */
  public List<CommodityPriceChangeHistory> loadAll() {
    return genericDao.loadAll();
  }

}
