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
import jp.co.sint.webshop.data.dao.FriendCouponIssueHistoryDao;
import jp.co.sint.webshop.data.dto.FriendCouponIssueHistory;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * キャンペーン
 *
 * @author System Integrator Corp.
 *
 */
public class FriendCouponIssueHistoryDaoImpl implements FriendCouponIssueHistoryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<FriendCouponIssueHistory, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public FriendCouponIssueHistoryDaoImpl() {
    genericDao = new GenericDaoImpl<FriendCouponIssueHistory, Long>(FriendCouponIssueHistory.class);
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
   * 指定されたorm_rowidを持つキャンペーンのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCampaignのインスタンス
   */
  public FriendCouponIssueHistory loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してキャンペーンのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   * @return 主キー列の値に対応するCampaignのインスタンス
   */
  public FriendCouponIssueHistory load(String couponCode) {
    Object[] params = new Object[]{couponCode};
    final String query = "SELECT * FROM FRIEND_COUPON_ISSUE_HISTORY"
        + " WHERE COUPON_CODE = ?";
    List<FriendCouponIssueHistory> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してキャンペーンが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   * @return 主キー列の値に対応するCampaignの行が存在すればtrue
   */
  public boolean exists(String couponCode) {
    Object[] params = new Object[]{couponCode};
    final String query = "SELECT COUNT(*) FROM FRIEND_COUPON_ISSUE_HISTORY"
        + " WHERE COUPON_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 主キー列の値を指定してキャンペーンが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   * @return 主キー列の値に対応するCampaignの行が存在すればtrue
   */
  public boolean exists(String couponCode, String customerCode) {
    Object[] params = new Object[]{couponCode, customerCode};
    final String query = "SELECT COUNT(*) FROM FRIEND_COUPON_ISSUE_HISTORY"
        + " WHERE COUPON_CODE = ? AND customer_code = ? ";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }
  
  /**
   * 新規Campaignをデータベースに追加します。
   * @param obj 追加対象のCampaign
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(FriendCouponIssueHistory obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Campaignをデータベースに追加します。
   * @param obj 追加対象のCampaign
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(FriendCouponIssueHistory obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * キャンペーンを更新します。
   * @param obj 更新対象のCampaign
   */
  public void update(FriendCouponIssueHistory obj) {
    genericDao.update(obj);
  }

  /**
   * キャンペーンを更新します。
   * @param obj 更新対象のCampaign
   */
  public void update(FriendCouponIssueHistory obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * キャンペーンを削除します。
   * @param obj 削除対象のCampaign
   */
  public void delete(FriendCouponIssueHistory obj) {
    genericDao.delete(obj);
  }

  /**
   * キャンペーンを削除します。
   * @param obj 削除対象のCampaign
   */
  public void delete(FriendCouponIssueHistory obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してキャンペーンを削除します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   */
  public void delete(String couponCode) {
    Object[] params = new Object[]{couponCode};
    final String query = "DELETE FROM FRIEND_COUPON_ISSUE_HISTORY"
        + " WHERE COUPON_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してキャンペーンのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCampaignのリスト
   */
  public List<FriendCouponIssueHistory> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してキャンペーンのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCampaignのリスト
   */
  public List<FriendCouponIssueHistory> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCampaignのリスト
   */
  public List<FriendCouponIssueHistory> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public Long countUseNum(String couponCode) {
    Object[] params = new Object[]{couponCode};
    final String query = "SELECT COUNT(*) FROM FRIEND_COUPON_ISSUE_HISTORY"
        + " WHERE COUPON_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).longValue();
  }

}
