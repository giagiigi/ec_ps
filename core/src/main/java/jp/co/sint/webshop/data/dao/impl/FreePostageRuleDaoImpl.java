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
import jp.co.sint.webshop.data.dao.FreePostageRuleDao;
import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;

/**
 * 免邮促销
 * 
 * @author Kousen.
 */
public class FreePostageRuleDaoImpl implements FreePostageRuleDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<FreePostageRule, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public FreePostageRuleDaoImpl() {
    genericDao = new GenericDaoImpl<FreePostageRule, Long>(FreePostageRule.class);
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
   * 指定されたorm_rowidを持つ免邮促销のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するFreePostageRuleのインスタンス
   */
  public FreePostageRule loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して免邮促销のインスタンスを取得します。
   * 
   * @param freePostageRuleCode
   *          免邮促销编号
   * @return 主キー列の値に対応するFreePostageRuleのインスタンス
   */
  public FreePostageRule load(String freePostageRuleCode) {
    Object[] params = new Object[] {
      freePostageRuleCode
    };
    final String query = "SELECT * FROM FREE_POSTAGE_RULE WHERE FREE_POSTAGE_CODE = ?";
    List<FreePostageRule> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して免邮促销が既に存在するかどうかを返します。
   * 
   * @param freePostageRuleCode
   *          免邮促销编号
   * @return 主キー列の値に対応するFreePostageRuleの行が存在すればtrue
   */
  public boolean exists(String freePostageRuleCode) {
    Object[] params = new Object[] {
      freePostageRuleCode
    };
    final String query = "SELECT COUNT(*) FROM FREE_POSTAGE_RULE WHERE FREE_POSTAGE_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規FreePostageRuleをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のFreePostageRule
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(FreePostageRule obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規FreePostageRuleをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のFreePostageRule
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(FreePostageRule obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 免邮促销を更新します。
   * 
   * @param obj
   *          更新対象のFreePostageRule
   */
  public void update(FreePostageRule obj) {
    genericDao.update(obj);
  }

  /**
   * 免邮促销を更新します。
   * 
   * @param obj
   *          更新対象のFreePostageRule
   */
  public void update(FreePostageRule obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 免邮促销を削除します。
   * 
   * @param obj
   *          削除対象のFreePostageRule
   */
  public void delete(FreePostageRule obj) {
    genericDao.delete(obj);
  }

  /**
   * 免邮促销を削除します。
   * 
   * @param obj
   *          削除対象のFreePostageRule
   */
  public void delete(FreePostageRule obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して免邮促销を削除します。
   * 
   * @param freePostageRuleCode
   *          免邮促销编号
   */
  public void delete(String freePostageRuleCode) {
    Object[] params = new Object[] {
      freePostageRuleCode
    };
    final String query = "DELETE FROM FREE_POSTAGE_RULE WHERE FREE_POSTAGE_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して免邮促销のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するFreePostageRuleのリスト
   */
  public List<FreePostageRule> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して免邮促销のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するFreePostageRuleのリスト
   */
  public List<FreePostageRule> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のFreePostageRuleのリスト
   */
  public List<FreePostageRule> loadAll() {
    return genericDao.loadAll();
  }

}
