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

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.FriendCouponRuleDao;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * キャンペーン
 *
 * @author System Integrator Corp.
 *
 */
public class FriendCouponRuleDaoImpl implements FriendCouponRuleDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<FriendCouponRule, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public FriendCouponRuleDaoImpl() {
    genericDao = new GenericDaoImpl<FriendCouponRule, Long>(FriendCouponRule.class);
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
   * 指定されたorm_rowidを持つFriendCouponRuleのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するFriendCouponRuleのインスタンス
   */
  public FriendCouponRule loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してFriendCouponRuleのインスタンスを取得します。
   * @param friendCouponRuleNo 
   * @return 主キー列の値に対応するFriendCouponRuleのインスタンス
   */
  public FriendCouponRule load(String friendCouponRuleNo) {
    Object[] params = new Object[]{friendCouponRuleNo};
    final String query = "SELECT * FROM FRIEND_COUPON_RULE"
        + " WHERE FRIEND_COUPON_RULE_NO = ?";
    List<FriendCouponRule> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してFriendCouponRuleが既に存在するかどうかを返します。
   * @param friendCouponRuleNo 
   * @return 主キー列の値に対応するFriendCouponRuleの行が存在すればtrue
   */
  public boolean exists(String friendCouponRuleNo) {
    Object[] params = new Object[]{friendCouponRuleNo};
    final String query = "SELECT COUNT(*) FROM FRIEND_COUPON_RULE"
        + " WHERE FRIEND_COUPON_RULE_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規FriendCouponRuleをデータベースに追加します。
   * @param obj 追加対象のFriendCouponRule
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(FriendCouponRule obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規FriendCouponRuleをデータベースに追加します。
   * @param obj 追加対象のFriendCouponRule
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(FriendCouponRule obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * FriendCouponRuleを更新します。
   * @param obj 更新対象のFriendCouponRule
   */
  public void update(FriendCouponRule obj) {
    genericDao.update(obj);
  }

  /**
   * FriendCouponRuleを更新します。
   * @param obj 更新対象のFriendCouponRule
   */
  public void update(FriendCouponRule obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * FriendCouponRuleを削除します。
   * @param obj 削除対象のFriendCouponRule
   */
  public void delete(FriendCouponRule obj) {
    genericDao.delete(obj);
  }

  /**
   * FriendCouponRuleを削除します。
   * @param obj 削除対象のFriendCouponRule
   */
  public void delete(FriendCouponRule obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してFriendCouponRuleを削除します。
   * @param friendCouponRuleNo 
   */
  public void delete(String friendCouponRuleNo) {
    Object[] params = new Object[]{friendCouponRuleNo};
    final String query = "DELETE FROM FRIEND_COUPON_RULE"
        + " WHERE FRIEND_COUPON_RULE_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してFriendCouponRuleのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するFriendCouponRuleのリスト
   */
  public List<FriendCouponRule> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してFriendCouponRuleのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するFriendCouponRuleのリスト
   */
  public List<FriendCouponRule> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のFriendCouponRuleのリスト
   */
  public List<FriendCouponRule> loadAll() {
    return genericDao.loadAll();
  }

}
