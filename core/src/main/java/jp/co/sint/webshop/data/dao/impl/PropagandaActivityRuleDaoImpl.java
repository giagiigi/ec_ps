//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.PropagandaActivityRuleDao;
import jp.co.sint.webshop.data.dto.PropagandaActivityRule;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 宣传品活动规则
 *
 * @author System Integrator Corp.
 *
 */
public class PropagandaActivityRuleDaoImpl implements PropagandaActivityRuleDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<PropagandaActivityRule, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public PropagandaActivityRuleDaoImpl() {
    genericDao = new GenericDaoImpl<PropagandaActivityRule, Long>(PropagandaActivityRule.class);
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
   * 指定されたorm_rowidを持つ宣传品活动规则のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するPropagandaActivityRuleのインスタンス
   */
  public PropagandaActivityRule loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して宣传品活动规则のインスタンスを取得します。
   * @param activityCode 活动编号
   * @return 主キー列の値に対応するPropagandaActivityRuleのインスタンス
   */
  public PropagandaActivityRule load(String activityCode) {
    Object[] params = new Object[]{activityCode};
    final String query = "SELECT * FROM PROPAGANDA_ACTIVITY_RULE"
        + " WHERE ACTIVITY_CODE = ?";
    List<PropagandaActivityRule> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して宣传品活动规则が既に存在するかどうかを返します。
   * @param activityCode 活动编号
   * @param PropagandaActivityRule 宣传品活动规则コード
   * @return 主キー列の値に対応するPropagandaActivityRuleの行が存在すればtrue
   */
  public boolean exists(String activityCode) {
    Object[] params = new Object[]{activityCode};
    final String query = "SELECT COUNT(*) FROM PROPAGANDA_ACTIVITY_RULE"
      + " WHERE ACTIVITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規PropagandaActivityRuleをデータベースに追加します。
   * @param obj 追加対象のPropagandaActivityRule
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PropagandaActivityRule obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規PropagandaActivityRuleをデータベースに追加します。
   * @param obj 追加対象のPropagandaActivityRule
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PropagandaActivityRule obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 宣传品活动规则を更新します。
   * @param obj 更新対象のPropagandaActivityRule
   */
  public void update(PropagandaActivityRule obj) {
    genericDao.update(obj);
  }

  /**
   * 宣传品活动规则を更新します。
   * @param obj 更新対象のPropagandaActivityRule
   */
  public void update(PropagandaActivityRule obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 宣传品活动规则を削除します。
   * @param obj 削除対象のPropagandaActivityRule
   */
  public void delete(PropagandaActivityRule obj) {
    genericDao.delete(obj);
  }

  /**
   * 宣传品活动规则を削除します。
   * @param obj 削除対象のPropagandaActivityRule
   */
  public void delete(PropagandaActivityRule obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して宣传品活动规则を削除します。
   * @param activityCode 活动编号
   * @param PropagandaActivityRuleCode 宣传品活动规则コード
   */
  public void delete(String activityCode) {
    Object[] params = new Object[]{activityCode};
    final String query = "DELETE FROM PROPAGANDA_ACTIVITY_RULE"
      + " WHERE ACTIVITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して宣传品活动规则のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPropagandaActivityRuleのリスト
   */
  public List<PropagandaActivityRule> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して宣传品活动规则のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPropagandaActivityRuleのリスト
   */
  public List<PropagandaActivityRule> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のPropagandaActivityRuleのリスト
   */
  public List<PropagandaActivityRule> loadAll() {
    return genericDao.loadAll();
  }

}
