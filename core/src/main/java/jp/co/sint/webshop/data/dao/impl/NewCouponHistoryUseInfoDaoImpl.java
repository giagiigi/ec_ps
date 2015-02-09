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
import jp.co.sint.webshop.data.dao.NewCouponHistoryUseInfoDao;
import jp.co.sint.webshop.data.dto.NewCouponHistoryUseInfo;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 优惠券发行履历
 *
 * @author OB.
 *
 */
public class NewCouponHistoryUseInfoDaoImpl implements NewCouponHistoryUseInfoDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<NewCouponHistoryUseInfo, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public NewCouponHistoryUseInfoDaoImpl() {
    genericDao = new GenericDaoImpl<NewCouponHistoryUseInfo, Long>(NewCouponHistoryUseInfo.class);
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
   * 指定されたorm_rowidを持つNewCouponHistoryUseInfoのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するNewCouponHistoryUseInfoのインスタンス
   */
  public NewCouponHistoryUseInfo loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してNewCouponHistoryUseInfoのインスタンスを取得します。
   * @param couponIssueNo
   * @param couponUseNo
   * @return 主キー列の値に対応するNewCouponHistoryUseInfoのインスタンス
   */
  public NewCouponHistoryUseInfo load(String couponIssueNo, String couponUseNo) {
    Object[] params = new Object[]{couponIssueNo, couponUseNo};
    final String query = "SELECT * FROM NEW_COUPON_HISTORY_USE_INFO"
        + " WHERE COUPON_ISSUE_NO = ?"
        + " AND COUPON_USE_NO = ?";
    List<NewCouponHistoryUseInfo> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してNewCouponHistoryUseInfoが既に存在するかどうかを返します。
   * @param couponIssueNo
   * @param shopCode
   * @return 主キー列の値に対応するNewCouponHistoryUseInfoの行が存在すればtrue
   */
  public boolean exists(String couponIssueNo, String couponUseNo) {
    Object[] params = new Object[]{couponIssueNo, couponUseNo};
    final String query = "SELECT COUNT(*) FROM NEW_COUPON_HISTORY_USE_INFO"
        + " WHERE COUPON_ISSUE_NO = ?"
        + " AND COUPON_USE_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規NewCouponHistoryUseInfoをデータベースに追加します。
   * @param obj 追加対象のNewCouponHistoryUseInfo
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(NewCouponHistoryUseInfo obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規NewCouponHistoryUseInfoをデータベースに追加します。
   * @param obj 追加対象のNewCouponHistoryUseInfo
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(NewCouponHistoryUseInfo obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * NewCouponHistoryUseInfoを更新します。
   * @param obj 更新対象のNewCouponHistoryUseInfo
   */
  public void update(NewCouponHistoryUseInfo obj) {
    genericDao.update(obj);
  }

  /**
   * NewCouponHistoryUseInfoを更新します。
   * @param obj 更新対象のNewCouponHistoryUseInfo
   */
  public void update(NewCouponHistoryUseInfo obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * NewCouponHistoryUseInfoを削除します。
   * @param obj 削除対象のNewCouponHistoryUseInfo
   */
  public void delete(NewCouponHistoryUseInfo obj) {
    genericDao.delete(obj);
  }

  /**
   * NewCouponHistoryUseInfoを削除します。
   * @param obj 削除対象のNewCouponHistoryUseInfo
   */
  public void delete(NewCouponHistoryUseInfo obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してNewCouponHistoryUseInfoを削除します。
   * @param couponIssueNo
   * @param couponUseNo 
   */
  public void delete(String couponIssueNo, String couponUseNo) {
    Object[] params = new Object[]{couponIssueNo, couponUseNo};
    final String query = "DELETE FROM NEW_COUPON_HISTORY_USE_INFO"
      + " WHERE COUPON_ISSUE_NO = ?"
      + " AND COUPON_USE_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してNewCouponHistoryUseInfoのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するNewCouponHistoryUseInfoのリスト
   */
  public List<NewCouponHistoryUseInfo> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してNewCouponHistoryUseInfoのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するNewCouponHistoryUseInfoのリスト
   */
  public List<NewCouponHistoryUseInfo> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のNewCouponHistoryUseInfoのリスト
   */
  public List<NewCouponHistoryUseInfo> loadAll() {
    return genericDao.loadAll();
  }

}
