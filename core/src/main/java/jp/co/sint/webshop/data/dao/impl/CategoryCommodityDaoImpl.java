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
import jp.co.sint.webshop.data.dao.CategoryCommodityDao;
import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * カテゴリ陳列商品
 *
 * @author System Integrator Corp.
 *
 */
public class CategoryCommodityDaoImpl implements CategoryCommodityDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CategoryCommodity, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CategoryCommodityDaoImpl() {
    genericDao = new GenericDaoImpl<CategoryCommodity, Long>(CategoryCommodity.class);
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
   * 指定されたorm_rowidを持つカテゴリ陳列商品のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCategoryCommodityのインスタンス
   */
  public CategoryCommodity loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してカテゴリ陳列商品のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param categoryCode カテゴリコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCategoryCommodityのインスタンス
   */
  public CategoryCommodity load(String shopCode, String categoryCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, categoryCode, commodityCode};
    final String query = "SELECT * FROM CATEGORY_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND CATEGORY_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    List<CategoryCommodity> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してカテゴリ陳列商品が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param categoryCode カテゴリコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCategoryCommodityの行が存在すればtrue
   */
  public boolean exists(String shopCode, String categoryCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, categoryCode, commodityCode};
    final String query = "SELECT COUNT(*) FROM CATEGORY_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND CATEGORY_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CategoryCommodityをデータベースに追加します。
   * @param obj 追加対象のCategoryCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CategoryCommodity obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CategoryCommodityをデータベースに追加します。
   * @param obj 追加対象のCategoryCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CategoryCommodity obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * カテゴリ陳列商品を更新します。
   * @param obj 更新対象のCategoryCommodity
   */
  public void update(CategoryCommodity obj) {
    genericDao.update(obj);
  }

  /**
   * カテゴリ陳列商品を更新します。
   * @param obj 更新対象のCategoryCommodity
   */
  public void update(CategoryCommodity obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * カテゴリ陳列商品を削除します。
   * @param obj 削除対象のCategoryCommodity
   */
  public void delete(CategoryCommodity obj) {
    genericDao.delete(obj);
  }

  /**
   * カテゴリ陳列商品を削除します。
   * @param obj 削除対象のCategoryCommodity
   */
  public void delete(CategoryCommodity obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してカテゴリ陳列商品を削除します。
   * @param shopCode ショップコード
   * @param categoryCode カテゴリコード
   * @param commodityCode 商品コード
   */
  public void delete(String shopCode, String categoryCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, categoryCode, commodityCode};
    final String query = "DELETE FROM CATEGORY_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND CATEGORY_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してカテゴリ陳列商品のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCategoryCommodityのリスト
   */
  public List<CategoryCommodity> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してカテゴリ陳列商品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCategoryCommodityのリスト
   */
  public List<CategoryCommodity> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCategoryCommodityのリスト
   */
  public List<CategoryCommodity> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public List<CategoryCommodity> loadAllByCommodityCode(String shopCode, String commodityCode) {
    Object[] params = new Object[]{shopCode,  commodityCode};
    final String query = "SELECT * FROM CATEGORY_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    List<CategoryCommodity> result = genericDao.findByQuery(query, params);
    return result;
  }

}
