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
import jp.co.sint.webshop.data.dao.FavoriteCommodityDao;
import jp.co.sint.webshop.data.dto.FavoriteCommodity;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * お気に入り商品
 *
 * @author System Integrator Corp.
 *
 */
public class FavoriteCommodityDaoImpl implements FavoriteCommodityDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<FavoriteCommodity, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public FavoriteCommodityDaoImpl() {
    genericDao = new GenericDaoImpl<FavoriteCommodity, Long>(FavoriteCommodity.class);
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
   * 指定されたorm_rowidを持つお気に入り商品のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するFavoriteCommodityのインスタンス
   */
  public FavoriteCommodity loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してお気に入り商品のインスタンスを取得します。
   * @param customerCode 顧客コード
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するFavoriteCommodityのインスタンス
   */
  public FavoriteCommodity load(String customerCode, String shopCode, String skuCode) {
    Object[] params = new Object[]{customerCode, shopCode, skuCode};
    final String query = "SELECT * FROM FAVORITE_COMMODITY"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND SHOP_CODE = ?"
        + " AND SKU_CODE = ?";
    List<FavoriteCommodity> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してお気に入り商品が既に存在するかどうかを返します。
   * @param customerCode 顧客コード
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するFavoriteCommodityの行が存在すればtrue
   */
  public boolean exists(String customerCode, String shopCode, String skuCode) {
    Object[] params = new Object[]{customerCode, shopCode, skuCode};
    final String query = "SELECT COUNT(*) FROM FAVORITE_COMMODITY"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND SHOP_CODE = ?"
        + " AND SKU_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規FavoriteCommodityをデータベースに追加します。
   * @param obj 追加対象のFavoriteCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(FavoriteCommodity obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規FavoriteCommodityをデータベースに追加します。
   * @param obj 追加対象のFavoriteCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(FavoriteCommodity obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * お気に入り商品を更新します。
   * @param obj 更新対象のFavoriteCommodity
   */
  public void update(FavoriteCommodity obj) {
    genericDao.update(obj);
  }

  /**
   * お気に入り商品を更新します。
   * @param obj 更新対象のFavoriteCommodity
   */
  public void update(FavoriteCommodity obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * お気に入り商品を削除します。
   * @param obj 削除対象のFavoriteCommodity
   */
  public void delete(FavoriteCommodity obj) {
    genericDao.delete(obj);
  }

  /**
   * お気に入り商品を削除します。
   * @param obj 削除対象のFavoriteCommodity
   */
  public void delete(FavoriteCommodity obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してお気に入り商品を削除します。
   * @param customerCode 顧客コード
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   */
  public void delete(String customerCode, String shopCode, String skuCode) {
    Object[] params = new Object[]{customerCode, shopCode, skuCode};
    final String query = "DELETE FROM FAVORITE_COMMODITY"
        + " WHERE CUSTOMER_CODE = ?"
        + " AND SHOP_CODE = ?"
        + " AND SKU_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してお気に入り商品のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するFavoriteCommodityのリスト
   */
  public List<FavoriteCommodity> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してお気に入り商品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するFavoriteCommodityのリスト
   */
  public List<FavoriteCommodity> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のFavoriteCommodityのリスト
   */
  public List<FavoriteCommodity> loadAll() {
    return genericDao.loadAll();
  }

}
