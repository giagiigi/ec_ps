//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.PropagandaActivityCommodityDao;
import jp.co.sint.webshop.data.dto.PropagandaActivityCommodity;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 宣传品活动关联商品
 *
 * @author System Integrator Corp.
 *
 */
public class PropagandaActivityCommodityDaoImpl implements PropagandaActivityCommodityDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<PropagandaActivityCommodity, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public PropagandaActivityCommodityDaoImpl() {
    genericDao = new GenericDaoImpl<PropagandaActivityCommodity, Long>(PropagandaActivityCommodity.class);
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
   * 指定されたorm_rowidを持つ宣传品活动关联商品のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するPropagandaActivityCommodityのインスタンス
   */
  public PropagandaActivityCommodity loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して宣传品活动关联商品のインスタンスを取得します。
   * @param activityCode 活动编号
   * @param commodityCode 宣传品活动关联商品コード
   * @return 主キー列の値に対応するPropagandaActivityCommodityのインスタンス
   */
  public PropagandaActivityCommodity load(String activityCode, String commodityCode) {
    Object[] params = new Object[]{activityCode, commodityCode};
    final String query = "SELECT * FROM PROPAGANDA_ACTIVITY_COMMODITY"
        + " WHERE ACTIVITY_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    List<PropagandaActivityCommodity> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して宣传品活动关联商品が既に存在するかどうかを返します。
   * @param activityCode 活动编号
   * @param commodityCode 宣传品活动关联商品コード
   * @return 主キー列の値に対応するPropagandaActivityCommodityの行が存在すればtrue
   */
  public boolean exists(String activityCode, String commodityCode) {
    Object[] params = new Object[]{activityCode, commodityCode};
    final String query = "SELECT COUNT(*) FROM PROPAGANDA_ACTIVITY_COMMODITY"
      + " WHERE ACTIVITY_CODE = ?"
      + " AND COMMODITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規PropagandaActivityCommodityをデータベースに追加します。
   * @param obj 追加対象のPropagandaActivityCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PropagandaActivityCommodity obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規PropagandaActivityCommodityをデータベースに追加します。
   * @param obj 追加対象のPropagandaActivityCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PropagandaActivityCommodity obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 宣传品活动关联商品を更新します。
   * @param obj 更新対象のPropagandaActivityCommodity
   */
  public void update(PropagandaActivityCommodity obj) {
    genericDao.update(obj);
  }

  /**
   * 宣传品活动关联商品を更新します。
   * @param obj 更新対象のPropagandaActivityCommodity
   */
  public void update(PropagandaActivityCommodity obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 宣传品活动关联商品を削除します。
   * @param obj 削除対象のPropagandaActivityCommodity
   */
  public void delete(PropagandaActivityCommodity obj) {
    genericDao.delete(obj);
  }

  /**
   * 宣传品活动关联商品を削除します。
   * @param obj 削除対象のPropagandaActivityCommodity
   */
  public void delete(PropagandaActivityCommodity obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して宣传品活动关联商品を削除します。
   * @param activityCode 活动编号
   * @param commodityCode 宣传品活动关联商品コード
   */
  public void delete(String activityCode, String commodityCode) {
    Object[] params = new Object[]{activityCode, commodityCode};
    final String query = "DELETE FROM PROPAGANDA_ACTIVITY_COMMODITY"
      + " WHERE ACTIVITY_CODE = ?"
      + " AND COMMODITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して宣传品活动关联商品のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPropagandaActivityCommodityのリスト
   */
  public List<PropagandaActivityCommodity> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して宣传品活动关联商品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPropagandaActivityCommodityのリスト
   */
  public List<PropagandaActivityCommodity> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のPropagandaActivityCommodityのリスト
   */
  public List<PropagandaActivityCommodity> loadAll() {
    return genericDao.loadAll();
  }

}
