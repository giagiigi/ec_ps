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

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.RelatedSiblingCategoryDao;
import jp.co.sint.webshop.data.dto.RelatedSiblingCategory;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;

/**
 * タグ
 * 
 * @author System Integrator Corp.
 */
public class RelatedSiblingCategoryDaoImpl implements RelatedSiblingCategoryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<RelatedSiblingCategory, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public RelatedSiblingCategoryDaoImpl() {
    genericDao = new GenericDaoImpl<RelatedSiblingCategory, Long>(RelatedSiblingCategory.class);
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
  public RelatedSiblingCategory loadByRowid(Long id) {
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
  public RelatedSiblingCategory load(String shopCode,String commodityCode,  String categoryCode) {
    Object[] params = new Object[] {
        shopCode,commodityCode, categoryCode
    };
    final String query = "SELECT * FROM RELATED_SIBLING_CATEGORY WHERE SHOP_CODE = ? AND COMMODITY_CODE=? AND CATEGORY_CODE = ?";
    List<RelatedSiblingCategory> result = genericDao.findByQuery(query, params);
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
  public boolean exists(String shopCode,String commodityCode,  String categoryCode) {
    Object[] params = new Object[] {
        shopCode,commodityCode, categoryCode
    };
    final String query = "SELECT COUNT(*) FROM RELATED_SIBLING_CATEGORY WHERE SHOP_CODE = ? AND COMMODITY_CODE=?  AND CATEGORY_CODE = ?";
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
  public Long insert(RelatedSiblingCategory obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Brandをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のBrand
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(RelatedSiblingCategory obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * タグを更新します。
   * 
   * @param obj
   *          更新対象のBrand
   */
  public void update(RelatedSiblingCategory obj) {
    genericDao.update(obj);
  }

  /**
   * タグを更新します。
   * 
   * @param obj
   *          更新対象のBrand
   */
  public void update(RelatedSiblingCategory obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * タグを削除します。
   * 
   * @param obj
   *          削除対象のBrand
   */
  public void delete(RelatedSiblingCategory obj) {
    genericDao.delete(obj);
  }

  /**
   * タグを削除します。
   * 
   * @param obj
   *          削除対象のBrand
   */
  public void delete(RelatedSiblingCategory obj, LoginInfo loginInfo) {
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
  public void delete(String shopCode,String commodityCode,  String categoryCode) {
    Object[] params = new Object[] {
        shopCode,commodityCode, categoryCode
    };
    final String query = "DELETE FROM RELATED_SIBLING_CATEGORY WHERE SHOP_CODE = ? AND COMMODITY_CODE=? AND CATEGORY_CODE = ? ";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してタグのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するBrandのリスト
   */
  public List<RelatedSiblingCategory> findByQuery(Query query) {
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
  public List<RelatedSiblingCategory> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のBrandのリスト
   */
  public List<RelatedSiblingCategory> loadAll() {
    return genericDao.loadAll();
  }


}
