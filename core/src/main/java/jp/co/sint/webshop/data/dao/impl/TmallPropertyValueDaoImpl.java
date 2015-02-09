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
import jp.co.sint.webshop.data.dao.TmallPropertyValueDao;
import jp.co.sint.webshop.data.dto.TmallPropertyValue;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl; 
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 受注ヘッダ
 *
 * @author System Integrator Corp.
 *
 */
public class TmallPropertyValueDaoImpl implements TmallPropertyValueDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<TmallPropertyValue, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public  TmallPropertyValueDaoImpl() {
    genericDao = new GenericDaoImpl<TmallPropertyValue, Long>(TmallPropertyValue.class);
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
   * 指定されたorm_rowidを持つ受注ヘッダのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するTmallOrderHeaderのインスタンス
   */
  public TmallPropertyValue loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して受注ヘッダのインスタンスを取得します。
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するTmallOrderHeaderのインスタンス
   */
  public TmallPropertyValue load(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "SELECT * FROM Tmall_Property_Value"
        + " WHERE value_id = ?";
    List<TmallPropertyValue> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }
  /**
   * 主キー列の値を指定して受注ヘッダが既に存在するかどうかを返します。
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するTmallOrderHeaderの行が存在すればtrue
   */
  public boolean exists(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "SELECT COUNT(*) FROM Tmall_Property_Value"
        + " WHERE value_id = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }
  /**
   * 新規TmallOrderHeaderをデータベースに追加します。
   * @param obj 追加対象のTmallOrderHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallPropertyValue obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規TmallOrderHeaderをデータベースに追加します。
   * @param obj 追加対象のTmallOrderHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallPropertyValue obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 受注ヘッダを更新します。
   * @param obj 更新対象のTmallOrderHeader
   */
  public void update(TmallPropertyValue obj) {
    genericDao.update(obj);
  }

  /**
   * 受注ヘッダを更新します。
   * @param obj 更新対象のTmallOrderHeader
   */
  public void update(TmallPropertyValue obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 受注ヘッダを削除します。
   * @param obj 削除対象のTmallOrderHeader
   */
  public void delete(TmallPropertyValue obj) {
    genericDao.delete(obj);
  }

  /**
   * 受注ヘッダを削除します。
   * @param obj 削除対象のTmallOrderHeader
   */
  public void delete(TmallPropertyValue obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して受注ヘッダを削除します。
   * @param orderNo 受注番号
   */
  public void delete(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "DELETE FROM Tmall_Property_Value"
        + " WHERE value_id = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して受注ヘッダのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するTmallOrderHeaderのリスト
   */
  public List<TmallPropertyValue> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して受注ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するTmallOrderHeaderのリスト
   */
  public List<TmallPropertyValue> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のTmallOrderHeaderのリスト
   */
  public List<TmallPropertyValue> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public List<TmallPropertyValue> loadByPropertyId(String propertyId,String categoryId) {
    String sql = "select * from tmall_property_value where property_id = ? and category_id=?";
    return findByQuery(sql, propertyId,categoryId);
  }

  @Override
  public TmallPropertyValue loadValue(String categoryId, String propertyId, String propertyValueId) {
    String sql = "SELECT value_id, value_name, property_id, category_id, alias_name,"
       +"delete_flag, orm_rowid, created_user, created_datetime, updated_user, "
       +" updated_datetime "
       +" FROM tmall_property_value where category_id=? and property_id=? and value_id=?";
    List<TmallPropertyValue> list = findByQuery(sql,categoryId,propertyId,propertyValueId);
    return list!=null&&list.size()>0?list.get(0):null;
  }

  /**
   * 查询手动输入的属性值对象是否以存在
   * @param categoryId 类目ID
   * @param propertyId  属性ID
   * @param propertyValueId 属性值ID
   * @param propertyValueName 手动输入的属性值名称
   * @return 
   */
  @Override
  public boolean exists(String categoryId, String propertyId, String propertyValueId, String propertyValueName) {
    String sql = "SELECT COUNT(*) "
      +" FROM tmall_property_value where category_id=? and property_id=? and value_id=? and value_name=?";
    Object result = genericDao.executeScalar(sql, categoryId,propertyId,propertyValueId,propertyValueName);
    return ((Number) result).intValue() > 0;
  }

  @Override
  public TmallPropertyValue loadValueByName(String categoryId, String propertyId, String propertyValueName) {
    String sql = "select * from tmall_property_value where property_id = ? and category_id=? and value_name=?";
    List<TmallPropertyValue> list = findByQuery(sql, propertyId,categoryId,propertyValueName);
    return list!=null&&list.size()>0?list.get(0):null;
  }

  @Override
  public TmallPropertyValue load(String categoryId, String propertyId, String valueId) {
    String sql = "SELECT *  FROM TMALL_PROPERTY_VALUE " +
    		" WHERE PROPERTY_ID = ? AND CATEGORY_ID = ? AND VALUE_ID = ?";
    List<TmallPropertyValue> list = findByQuery(sql, propertyId,categoryId,valueId);
    return list!=null&&list.size()>0?list.get(0):null;
  }
}
