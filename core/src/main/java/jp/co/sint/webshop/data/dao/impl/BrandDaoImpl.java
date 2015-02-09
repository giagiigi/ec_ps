//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
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
import jp.co.sint.webshop.data.dao.BrandDao;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * タグ
 * 
 * @author System Integrator Corp.
 */
public class BrandDaoImpl implements BrandDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Brand, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public BrandDaoImpl() {
    genericDao = new GenericDaoImpl<Brand, Long>(Brand.class);
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
   * 指定されたorm_rowidを持つタグのインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するBrandのインスタンス
   */
  public Brand loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してタグのインスタンスを取得します。
   * 
   * @param shopCode
   *          ショップコード
   * @param BrandCode
   *          タグコード
   * @return 主キー列の値に対応するBrandのインスタンス
   */
  public Brand load(String shopCode, String BrandCode) {
    Object[] params = new Object[] {
        shopCode, BrandCode
    };
    final String query = "SELECT * FROM Brand" + " WHERE SHOP_CODE = ?" + " AND Brand_CODE = ?";
    List<Brand> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してタグが既に存在するかどうかを返します。
   * 
   * @param shopCode
   *          ショップコード
   * @param BrandCode
   *          タグコード
   * @return 主キー列の値に対応するBrandの行が存在すればtrue
   */
  public boolean exists(String shopCode, String BrandCode) {
    Object[] params = new Object[] {
        shopCode, BrandCode
    };
    final String query = "SELECT COUNT(*) FROM Brand" + " WHERE SHOP_CODE = ?" + " AND Brand_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Brandをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のBrand
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Brand obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Brandをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のBrand
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Brand obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * タグを更新します。
   * 
   * @param obj
   *          更新対象のBrand
   */
  public void update(Brand obj) {
    genericDao.update(obj);
  }

  /**
   * タグを更新します。
   * 
   * @param obj
   *          更新対象のBrand
   */
  public void update(Brand obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * タグを削除します。
   * 
   * @param obj
   *          削除対象のBrand
   */
  public void delete(Brand obj) {
    genericDao.delete(obj);
  }

  /**
   * タグを削除します。
   * 
   * @param obj
   *          削除対象のBrand
   */
  public void delete(Brand obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してタグを削除します。
   * 
   * @param shopCode
   *          ショップコード
   * @param BrandCode
   *          タグコード
   */
  public void delete(String shopCode, String BrandCode) {
    Object[] params = new Object[] {
        shopCode, BrandCode
    };
    final String query = "DELETE FROM Brand" + " WHERE SHOP_CODE = ?" + " AND Brand_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してタグのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するBrandのリスト
   */
  public List<Brand> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してタグのリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するBrandのリスト
   */
  public List<Brand> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のBrandのリスト
   */
  public List<Brand> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public Brand load(String BrandCode) {
    final String query = "SELECT * FROM Brand WHERE Brand_CODE = ?";
    List<Brand> result = genericDao.findByQuery(query, BrandCode);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

}
