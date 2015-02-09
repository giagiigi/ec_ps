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
import jp.co.sint.webshop.data.dao.ShopDao;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * ショップ
 *
 * @author System Integrator Corp.
 *
 */
public class ShopDaoImpl implements ShopDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Shop, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public ShopDaoImpl() {
    genericDao = new GenericDaoImpl<Shop, Long>(Shop.class);
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
   * 指定されたorm_rowidを持つショップのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するShopのインスタンス
   */
  public Shop loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してショップのインスタンスを取得します。
   * @param shopCode ショップコード
   * @return 主キー列の値に対応するShopのインスタンス
   */
  public Shop load(String shopCode) {
    Object[] params = new Object[]{shopCode};
    final String query = "SELECT * FROM SHOP"
        + " WHERE SHOP_CODE = ?";
    List<Shop> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してショップが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @return 主キー列の値に対応するShopの行が存在すればtrue
   */
  public boolean exists(String shopCode) {
    Object[] params = new Object[]{shopCode};
    final String query = "SELECT COUNT(*) FROM SHOP"
        + " WHERE SHOP_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Shopをデータベースに追加します。
   * @param obj 追加対象のShop
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Shop obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Shopをデータベースに追加します。
   * @param obj 追加対象のShop
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Shop obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * ショップを更新します。
   * @param obj 更新対象のShop
   */
  public void update(Shop obj) {
    genericDao.update(obj);
  }

  /**
   * ショップを更新します。
   * @param obj 更新対象のShop
   */
  public void update(Shop obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * ショップを削除します。
   * @param obj 削除対象のShop
   */
  public void delete(Shop obj) {
    genericDao.delete(obj);
  }

  /**
   * ショップを削除します。
   * @param obj 削除対象のShop
   */
  public void delete(Shop obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してショップを削除します。
   * @param shopCode ショップコード
   */
  public void delete(String shopCode) {
    Object[] params = new Object[]{shopCode};
    final String query = "DELETE FROM SHOP"
        + " WHERE SHOP_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してショップのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するShopのリスト
   */
  public List<Shop> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してショップのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するShopのリスト
   */
  public List<Shop> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のShopのリスト
   */
  public List<Shop> loadAll() {
    return genericDao.loadAll();
  }

}
