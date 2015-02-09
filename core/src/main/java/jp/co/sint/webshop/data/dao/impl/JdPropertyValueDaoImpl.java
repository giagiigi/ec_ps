package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.JdPropertyValueDao;
import jp.co.sint.webshop.data.dto.JdPropertyValue;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * JD属性值
 * 
 * @author OB
 * 
 */
public class JdPropertyValueDaoImpl implements JdPropertyValueDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<JdPropertyValue, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public JdPropertyValueDaoImpl() {
    genericDao = new GenericDaoImpl<JdPropertyValue, Long>(JdPropertyValue.class);
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
   * 指定されたorm_rowidを持つJD属性值のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するjd_property_valueのインスタンス
   */
  public JdPropertyValue loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してJD属性值のインスタンスを取得します。
   * 
   * @param valueId
   *          属性值id
   * @param propertyId
   *          所属属性id
   * @return 主キー列の値に対応するjd_property_valueのインスタンス
   */
  public JdPropertyValue load(String valueId, String propertyId) {
    Object[] params = new Object[] { valueId, propertyId };
    final String query = "SELECT * FROM Jd_Property_Value WHERE value_id = ? and property_id = ?";
    List<JdPropertyValue> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してJD属性值が既に存在するかどうかを返します。
   * 
   * @param valueId
   *          属性值id
   * @param propertyId
   *          所属属性id
   * @return 主キー列の値に対応するjd_property_valueの行が存在すればtrue
   */
  public boolean exists(String valueId, String propertyId) {
    Object[] params = new Object[] { valueId, propertyId };
    final String query = "SELECT COUNT(*) FROM Jd_Property_Value WHERE value_id = ? and property_id = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規jd_property_valueをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のjd_property_value
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdPropertyValue obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規jd_property_valueをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のjd_property_value
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdPropertyValue obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * JD属性值を更新します。
   * 
   * @param obj
   *          更新対象のjd_property_value
   */
  public void update(JdPropertyValue obj) {
    genericDao.update(obj);
  }

  /**
   * JD属性值を更新します。
   * 
   * @param obj
   *          更新対象のjd_property_value
   */
  public void update(JdPropertyValue obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * JD属性值を削除します。
   * 
   * @param obj
   *          削除対象のjd_property_value
   */
  public void delete(JdPropertyValue obj) {
    genericDao.delete(obj);
  }

  /**
   * JD属性值を削除します。
   * 
   * @param obj
   *          削除対象のjd_property_value
   */
  public void delete(JdPropertyValue obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してJD属性值を削除します。
   * 
   * @param valueId
   *          属性值id
   * @param propertyId
   *          所属属性id
   */
  public void delete(String valueId, String propertyId) {
    Object[] params = new Object[] { valueId, propertyId };
    final String query = "DELETE FROM Jd_Property_Value WHERE value_id = ? and property_id = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してJD属性值のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するjd_property_valueのリスト
   */
  public List<JdPropertyValue> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してJD属性值のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するjd_property_valueのリスト
   */
  public List<JdPropertyValue> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のjd_property_valueのリスト
   */
  public List<JdPropertyValue> loadAll() {
    return genericDao.loadAll();
  }

}
