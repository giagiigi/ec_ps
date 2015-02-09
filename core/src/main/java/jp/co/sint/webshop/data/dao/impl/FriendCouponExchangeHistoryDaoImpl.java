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
import jp.co.sint.webshop.data.dao.FriendCouponExchangeHistoryDao;
import jp.co.sint.webshop.data.dto.FriendCouponExchangeHistory;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 *
 * @author System Integrator Corp.
 *
 */
public class FriendCouponExchangeHistoryDaoImpl implements FriendCouponExchangeHistoryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<FriendCouponExchangeHistory, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;
  
  /**
   * SessionFactoryを取得します
   * @return SessionFactoryのインスタンス
   */
  public SessionFactory getSessionFactory() {
    return this.sessionFactory;
  }
  
  /**
   * Constructor:
   */
  public FriendCouponExchangeHistoryDaoImpl() {
    genericDao = new GenericDaoImpl<FriendCouponExchangeHistory, Long>(FriendCouponExchangeHistory.class);
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
   * 顧客コードを指定して両替使うポイントを取得します 　　 
   * @param customerCode 顧客コード 
   * @return 両替使うポイント
   */
  public Long getAllExchangePoint(String customerCode) {
    Object[] params = new Object[]{customerCode};
    final String query = "SELECT SUM(EXCHANGE_POINT) FROM FRIEND_COUPON_EXCHANGE_HISTORY"
        + " WHERE CUSTOMER_CODE = ?"; 
    Object result = genericDao.executeScalar(query, params); 
    
    if(result != null){
      return ((Number)result).longValue();
    }
    return 0L;
  }

  /**
   * クーポンコードを指定してFriendCouponExchangeHistoryのリストを取得します。 　　
   * 
   * @param　couponIssueNo クーポンコード
   * @return 検索結果に相当するFriendCouponExchangeHistoryのリスト
   */
  public FriendCouponExchangeHistory load(String couponIssueNo) {
    String sqlString = "SELECT * FROM FRIEND_COUPON_EXCHANGE_HISTORY WHERE COUPON_ISSUE_NO = ?";
    List<FriendCouponExchangeHistory> list = this.findByQuery(sqlString, couponIssueNo);
    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  /**
   * FriendCouponExchangeHistoryを削除します。
   * @param obj 削除対象のFriendCouponExchangeHistory
   */
  public void delete(FriendCouponExchangeHistory obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);  
  }

  /**
   * 主キー列の値を指定してキャンペーンを削除します。
   * @param　couponIssueNo クーポンコード
   */
  public void delete(String couponIssueNo) {
    Object[] params = new Object[]{couponIssueNo};
    final String query = "DELETE FROM FRIEND_COUPON_EXCHANGE_HISTORY"
        + " WHERE COUPON_ISSUE_NO = ?";
    genericDao.updateByQuery(query, params); 
  }

  /**
   * 主キー列の値を指定してFriendCouponExchangeHistoryが既に存在するかどうかを返します。
   * @param　couponIssueNo クーポンコード
   * @return 主キー列の値に対応するFriendCouponExchangeHistoryの行が存在すればtrue
   */
  public boolean exists(String couponIssueNo) {
    Object[] params = new Object[]{couponIssueNo};
    final String query = "SELECT COUNT(*) FROM FRIEND_COUPON_EXCHANGE_HISTORY"
        + " WHERE COUPON_ISSUE_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;
  }

  /**
   * Queryオブジェクトを指定してキャンペーンのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するFriendCouponExchangeHistoryのリスト
   */
  public List<FriendCouponExchangeHistory> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してキャンペーンのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するFriendCouponExchangeHistoryのリスト
   */
  public List<FriendCouponExchangeHistory> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のFriendCouponExchangeHistoryのリスト
   */
  public List<FriendCouponExchangeHistory> loadAll() {
    return genericDao.loadAll();
  }

  /**
   * 指定されたorm_rowidを持つキャンペーンのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するFriendCouponExchangeHistoryのインスタンス
   */
  public FriendCouponExchangeHistory loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * FriendCouponExchangeHistoryを削除します。
   * @param obj 削除対象のFriendCouponExchangeHistory
   */
  public void delete(FriendCouponExchangeHistory transactionObject) {
    genericDao.delete(transactionObject);  
  }

  /**
   * 新規FriendCouponExchangeHistoryをデータベースに追加します。
   * @param obj 追加対象のFriendCouponExchangeHistory
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(FriendCouponExchangeHistory newInstance) {
    return genericDao.insert(newInstance);
  }

  /**
   * FriendCouponExchangeHistoryを更新します。
   * @param obj 更新対象のFriendCouponExchangeHistory
   */
  public void update(FriendCouponExchangeHistory transactionObject) {
    genericDao.update(transactionObject); 
  }

}
