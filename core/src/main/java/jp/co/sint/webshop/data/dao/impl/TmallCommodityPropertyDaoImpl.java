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
import jp.co.sint.webshop.data.dao.TmallCommodityPropertyDao;
import jp.co.sint.webshop.data.dto.TmallCommodityProperty;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl; 
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 受注ヘッダ
 *
 * @author System Integrator Corp.
 *
 */
public class TmallCommodityPropertyDaoImpl implements TmallCommodityPropertyDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<TmallCommodityProperty, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public  TmallCommodityPropertyDaoImpl() {
    genericDao = new GenericDaoImpl<TmallCommodityProperty, Long>(TmallCommodityProperty.class);
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
   * @return idに対応するTmallCommodityPropertyのインスタンス
   */
  public TmallCommodityProperty loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して受注ヘッダのインスタンスを取得します。
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するTmallCommodityPropertyのインスタンス
   */
  public TmallCommodityProperty load(String commodityCode,String propertyId,String valueId) {
    Object[] params = new Object[]{commodityCode,propertyId,valueId};
    final String query = "SELECT * FROM Tmall_Commodity_Property"
        + " WHERE commodity_code = ? and property_id=? and value_id=?";
    List<TmallCommodityProperty> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }
  /**
   * 主キー列の値を指定して受注ヘッダが既に存在するかどうかを返します。
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するTmallCommodityPropertyの行が存在すればtrue
   */
  public boolean exists(String commodityCode,String propertyId,String valueId) {
    Object[] params = new Object[]{commodityCode,propertyId,valueId};
    final String query = "SELECT COUNT(*) FROM Tmall_Commodity_Property"
        + " WHERE commodity_code = ? and property_id=? and value_id=?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }
  /**
   * 新規TmallOrderHeaderをデータベースに追加します。
   * @param obj 追加対象のTmallCommodityProperty
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallCommodityProperty obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規TmallOrderHeaderをデータベースに追加します。
   * @param obj 追加対象のTmallCommodityProperty
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallCommodityProperty obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 受注ヘッダを更新します。
   * @param obj 更新対象のTmallCommodityProperty
   */
  public void update(TmallCommodityProperty obj) {
    genericDao.update(obj);
  }

  /**
   * 受注ヘッダを更新します。
   * @param obj 更新対象のTmallCommodityProperty
   */
  public void update(TmallCommodityProperty obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 受注ヘッダを削除します。
   * @param obj 削除対象のTmallCommodityProperty
   */
  public void delete(TmallCommodityProperty obj) {
    genericDao.delete(obj);
  }

  /**
   * 受注ヘッダを削除します。
   * @param obj 削除対象のTmallCommodityProperty
   */
  public void delete(TmallCommodityProperty obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して受注ヘッダを削除します。
   * @param orderNo 受注番号
   */
  public void delete(String commodityCode,String propertyId,String valueId) {
    Object[] params = new Object[]{commodityCode,propertyId,valueId};
    final String query = "DELETE FROM Tmall_Property_Value"
        + " WHERE commodity_code = ? and property_id=? and value_id=?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して受注ヘッダのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するTmallCommodityPropertyのリスト
   */
  public List<TmallCommodityProperty> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して受注ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するTmallCommodityPropertyのリスト
   */
  public List<TmallCommodityProperty> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のTmallCommodityPropertyのリスト
   */
  public List<TmallCommodityProperty> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public List<TmallCommodityProperty> loadByCommodityCode(String commodityId) {
    String sql = "select * from tmall_commodity_property where commodity_code = ? ";
    return this.findByQuery(sql, commodityId);
  }

  @Override
  public List<TmallCommodityProperty> loadInputProByCommodityCode(String commodityId) {
    String sql = "SELECT * FROM TMALL_COMMODITY_PROPERTY WHERE COMMODITY_CODE = ?" +
    		         " AND VALUE_ID = '0'";
    return this.findByQuery(sql, commodityId);
  }
}
