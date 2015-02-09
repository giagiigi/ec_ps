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
import jp.co.sint.webshop.data.dao.CategoryAttributeDao;
import jp.co.sint.webshop.data.dto.CategoryAttribute;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * カテゴリ属性名称
 *
 * @author System Integrator Corp.
 *
 */
public class CategoryAttributeDaoImpl implements CategoryAttributeDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CategoryAttribute, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CategoryAttributeDaoImpl() {
    genericDao = new GenericDaoImpl<CategoryAttribute, Long>(CategoryAttribute.class);
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
   * 指定されたorm_rowidを持つカテゴリ属性名称のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCategoryAttributeのインスタンス
   */
  public CategoryAttribute loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してカテゴリ属性名称のインスタンスを取得します。
   * @param categoryCode カテゴリコード
   * @param categoryAttributeNo カテゴリ属性番号
   * @return 主キー列の値に対応するCategoryAttributeのインスタンス
   */
  public CategoryAttribute load(String categoryCode, Long categoryAttributeNo) {
    Object[] params = new Object[]{categoryCode, categoryAttributeNo};
    final String query = "SELECT * FROM CATEGORY_ATTRIBUTE"
        + " WHERE CATEGORY_CODE = ?"
        + " AND CATEGORY_ATTRIBUTE_NO = ?";
    List<CategoryAttribute> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してカテゴリ属性名称が既に存在するかどうかを返します。
   * @param categoryCode カテゴリコード
   * @param categoryAttributeNo カテゴリ属性番号
   * @return 主キー列の値に対応するCategoryAttributeの行が存在すればtrue
   */
  public boolean exists(String categoryCode, Long categoryAttributeNo) {
    Object[] params = new Object[]{categoryCode, categoryAttributeNo};
    final String query = "SELECT COUNT(*) FROM CATEGORY_ATTRIBUTE"
        + " WHERE CATEGORY_CODE = ?"
        + " AND CATEGORY_ATTRIBUTE_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CategoryAttributeをデータベースに追加します。
   * @param obj 追加対象のCategoryAttribute
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CategoryAttribute obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CategoryAttributeをデータベースに追加します。
   * @param obj 追加対象のCategoryAttribute
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CategoryAttribute obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * カテゴリ属性名称を更新します。
   * @param obj 更新対象のCategoryAttribute
   */
  public void update(CategoryAttribute obj) {
    genericDao.update(obj);
  }

  /**
   * カテゴリ属性名称を更新します。
   * @param obj 更新対象のCategoryAttribute
   */
  public void update(CategoryAttribute obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * カテゴリ属性名称を削除します。
   * @param obj 削除対象のCategoryAttribute
   */
  public void delete(CategoryAttribute obj) {
    genericDao.delete(obj);
  }

  /**
   * カテゴリ属性名称を削除します。
   * @param obj 削除対象のCategoryAttribute
   */
  public void delete(CategoryAttribute obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してカテゴリ属性名称を削除します。
   * @param categoryCode カテゴリコード
   * @param categoryAttributeNo カテゴリ属性番号
   */
  public void delete(String categoryCode, Long categoryAttributeNo) {
    Object[] params = new Object[]{categoryCode, categoryAttributeNo};
    final String query = "DELETE FROM CATEGORY_ATTRIBUTE"
        + " WHERE CATEGORY_CODE = ?"
        + " AND CATEGORY_ATTRIBUTE_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してカテゴリ属性名称のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCategoryAttributeのリスト
   */
  public List<CategoryAttribute> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してカテゴリ属性名称のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCategoryAttributeのリスト
   */
  public List<CategoryAttribute> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCategoryAttributeのリスト
   */
  public List<CategoryAttribute> loadAll() {
    return genericDao.loadAll();
  }

}
