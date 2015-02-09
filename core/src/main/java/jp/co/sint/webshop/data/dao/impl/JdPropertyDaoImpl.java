package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.data.dao.JdPropertyDao;
import jp.co.sint.webshop.data.dto.JdProperty;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 京东属性表
 * 
 * @author OB
 * 
 */
public class JdPropertyDaoImpl implements JdPropertyDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<JdProperty, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public JdPropertyDaoImpl() {
    genericDao = new GenericDaoImpl<JdProperty, Long>(JdProperty.class);
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
   * 指定されたorm_rowidを持つ京东属性表のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するCategoryのインスタンス
   */
  public JdProperty loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して京东属性表のインスタンスを取得します。
   * 
   * @param property_id
   *          JD属性id
   * @param categoryId
   *          JD类目ID
   * @return 主キー列の値に対応する京东属性表のインスタンス
   */
  public JdProperty load(String propertyId, String categoryId) {
    Object[] params = new Object[] { propertyId, categoryId };
    final String query = "SELECT * FROM jd_property WHERE property_id = ? AND category_id = ?";
    List<JdProperty> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して京东属性表が既に存在するかどうかを返します。
   * 
   * @param property_id
   *          JD属性id
   * @param categoryId
   *          JD类目ID
   * @return 主キー列の値に対応する京东属性表の行が存在すればtrue
   */
  public boolean exists(String propertyId, String categoryId) {
    Object[] params = new Object[] { propertyId, categoryId };
    final String query = "SELECT COUNT(*) FROM jd_category WHERE property_id = ? AND category_id = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規京东属性表をデータベースに追加します。
   * 
   * @param obj
   *          追加対象の京东属性表
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdProperty obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規京东属性表をデータベースに追加します。
   * 
   * @param obj
   *          追加対象の京东属性表
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdProperty obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 京东属性表を更新します。
   * 
   * @param obj
   *          更新対象の京东属性表
   */
  public void update(JdProperty obj) {
    genericDao.update(obj);
  }

  /**
   * 京东属性表を更新します。
   * 
   * @param obj
   *          更新対象の京东属性表
   */
  public void update(JdProperty obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 京东属性表を削除します。
   * 
   * @param obj
   *          削除対象の京东属性表
   */
  public void delete(JdProperty obj) {
    genericDao.delete(obj);
  }

  /**
   * 京东属性表を削除します。
   * 
   * @param obj
   *          削除対象の京东属性表
   */
  public void delete(JdProperty obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して京东属性表を削除します。
   * 
   * @param property_id
   *          JD属性id
   * @param categoryId
   *          JD类目ID
   */
  public void delete(String propertyId, String categoryId) {
    Object[] params = new Object[] { propertyId, categoryId };
    final String query = "DELETE FROM jd_category WHERE property_id = ? AND category_id = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して京东属性表のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当する京东属性表のリスト
   */
  public List<JdProperty> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して京东属性表のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当する京东属性表のリスト
   */
  public List<JdProperty> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分の京东属性表のリスト
   */
  public List<JdProperty> loadAll() {
    return genericDao.loadAll();
  }

}
