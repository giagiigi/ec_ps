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
import jp.co.sint.webshop.data.dao.PointRuleDao;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * ポイントルール
 *
 * @author System Integrator Corp.
 *
 */
public class PointRuleDaoImpl implements PointRuleDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<PointRule, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public PointRuleDaoImpl() {
    genericDao = new GenericDaoImpl<PointRule, Long>(PointRule.class);
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
   * 指定されたorm_rowidを持つポイントルールのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するPointRuleのインスタンス
   */
  public PointRule loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してポイントルールのインスタンスを取得します。
   * @param pointRuleNo ポイントルール番号
   * @return 主キー列の値に対応するPointRuleのインスタンス
   */
  public PointRule load(Long pointRuleNo) {
    Object[] params = new Object[]{pointRuleNo};
    final String query = "SELECT * FROM POINT_RULE"
        + " WHERE POINT_RULE_NO = ?";
    List<PointRule> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してポイントルールが既に存在するかどうかを返します。
   * @param pointRuleNo ポイントルール番号
   * @return 主キー列の値に対応するPointRuleの行が存在すればtrue
   */
  public boolean exists(Long pointRuleNo) {
    Object[] params = new Object[]{pointRuleNo};
    final String query = "SELECT COUNT(*) FROM POINT_RULE"
        + " WHERE POINT_RULE_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規PointRuleをデータベースに追加します。
   * @param obj 追加対象のPointRule
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PointRule obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規PointRuleをデータベースに追加します。
   * @param obj 追加対象のPointRule
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PointRule obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * ポイントルールを更新します。
   * @param obj 更新対象のPointRule
   */
  public void update(PointRule obj) {
    genericDao.update(obj);
  }

  /**
   * ポイントルールを更新します。
   * @param obj 更新対象のPointRule
   */
  public void update(PointRule obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * ポイントルールを削除します。
   * @param obj 削除対象のPointRule
   */
  public void delete(PointRule obj) {
    genericDao.delete(obj);
  }

  /**
   * ポイントルールを削除します。
   * @param obj 削除対象のPointRule
   */
  public void delete(PointRule obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してポイントルールを削除します。
   * @param pointRuleNo ポイントルール番号
   */
  public void delete(Long pointRuleNo) {
    Object[] params = new Object[]{pointRuleNo};
    final String query = "DELETE FROM POINT_RULE"
        + " WHERE POINT_RULE_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してポイントルールのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPointRuleのリスト
   */
  public List<PointRule> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してポイントルールのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPointRuleのリスト
   */
  public List<PointRule> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のPointRuleのリスト
   */
  public List<PointRule> loadAll() {
    return genericDao.loadAll();
  }

}
