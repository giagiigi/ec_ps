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
import jp.co.sint.webshop.data.dao.CategoryDao;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * カテゴリ
 *
 * @author System Integrator Corp.
 *
 */
public class CategoryDaoImpl implements CategoryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Category, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CategoryDaoImpl() {
    genericDao = new GenericDaoImpl<Category, Long>(Category.class);
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
   * 指定されたorm_rowidを持つカテゴリのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCategoryのインスタンス
   */
  public Category loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してカテゴリのインスタンスを取得します。
   * @param categoryCode カテゴリコード
   * @return 主キー列の値に対応するCategoryのインスタンス
   */
  public Category load(String categoryCode) {
    Object[] params = new Object[]{categoryCode};
    final String query = "SELECT * FROM CATEGORY"
        + " WHERE CATEGORY_CODE = ?";
    List<Category> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してカテゴリが既に存在するかどうかを返します。
   * @param categoryCode カテゴリコード
   * @return 主キー列の値に対応するCategoryの行が存在すればtrue
   */
  public boolean exists(String categoryCode) {
    Object[] params = new Object[]{categoryCode};
    final String query = "SELECT COUNT(*) FROM CATEGORY"
        + " WHERE CATEGORY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Categoryをデータベースに追加します。
   * @param obj 追加対象のCategory
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Category obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Categoryをデータベースに追加します。
   * @param obj 追加対象のCategory
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Category obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * カテゴリを更新します。
   * @param obj 更新対象のCategory
   */
  public void update(Category obj) {
    genericDao.update(obj);
  }

  /**
   * カテゴリを更新します。
   * @param obj 更新対象のCategory
   */
  public void update(Category obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * カテゴリを削除します。
   * @param obj 削除対象のCategory
   */
  public void delete(Category obj) {
    genericDao.delete(obj);
  }

  /**
   * カテゴリを削除します。
   * @param obj 削除対象のCategory
   */
  public void delete(Category obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してカテゴリを削除します。
   * @param categoryCode カテゴリコード
   */
  public void delete(String categoryCode) {
    Object[] params = new Object[]{categoryCode};
    final String query = "DELETE FROM CATEGORY"
        + " WHERE CATEGORY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してカテゴリのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCategoryのリスト
   */
  public List<Category> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してカテゴリのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCategoryのリスト
   */
  public List<Category> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCategoryのリスト
   */
  public List<Category> loadAll() {
    return genericDao.loadAll();
  }

}
