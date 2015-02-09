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
import jp.co.sint.webshop.data.dao.CouponRuleDao;
import jp.co.sint.webshop.data.dto.CouponRule;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * クーポンルール
 *
 * @author System Integrator Corp.
 *
 */
public class CouponRuleDaoImpl implements CouponRuleDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CouponRule, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CouponRuleDaoImpl() {
    genericDao = new GenericDaoImpl<CouponRule, Long>(CouponRule.class);
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
   * 指定されたorm_rowidを持つクーポンルールのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCouponRuleのインスタンス
   */
  public CouponRule loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してクーポンルールのインスタンスを取得します。
   * @param couponRuleNo クーポンルール番号
   * @return 主キー列の値に対応するCouponRuleのインスタンス
   */
  public CouponRule load(Long couponRuleNo) {
    Object[] params = new Object[]{couponRuleNo};
    final String query = "SELECT * FROM COUPON_RULE"
        + " WHERE COUPON_RULE_NO = ?";
    List<CouponRule> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してクーポンルールが既に存在するかどうかを返します。
   * @param couponRuleNo クーポンルール番号
   * @return 主キー列の値に対応するCouponRuleの行が存在すればtrue
   */
  public boolean exists(Long couponRuleNo) {
    Object[] params = new Object[]{couponRuleNo};
    final String query = "SELECT COUNT(*) FROM COUPON_RULE"
        + " WHERE COUPON_RULE_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CouponRuleをデータベースに追加します。
   * @param obj 追加対象のCouponRule
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CouponRule obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CouponRuleをデータベースに追加します。
   * @param obj 追加対象のCouponRule
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CouponRule obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * クーポンルールを更新します。
   * @param obj 更新対象のCouponRule
   */
  public void update(CouponRule obj) {
    genericDao.update(obj);
  }

  /**
   * クーポンルールを更新します。
   * @param obj 更新対象のCouponRule
   */
  public void update(CouponRule obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * クーポンルールを削除します。
   * @param obj 削除対象のCouponRule
   */
  public void delete(CouponRule obj) {
    genericDao.delete(obj);
  }

  /**
   * クーポンルールを削除します。
   * @param obj 削除対象のCouponRule
   */
  public void delete(CouponRule obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してクーポンルールを削除します。
   * @param couponRuleNo クーポンルール番号
   */
  public void delete(Long couponRuleNo) {
    Object[] params = new Object[]{couponRuleNo};
    final String query = "DELETE FROM COUPON_RULE"
        + " WHERE COUPON_RULE_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してクーポンルールのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCouponRuleのリスト
   */
  public List<CouponRule> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してクーポンルールのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCouponRuleのリスト
   */
  public List<CouponRule> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCouponRuleのリスト
   */
  public List<CouponRule> loadAll() {
    return genericDao.loadAll();
  }

}
