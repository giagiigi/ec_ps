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
import jp.co.sint.webshop.data.dao.FriendCouponUseHistoryDao;
import jp.co.sint.webshop.data.dto.FriendCouponUseHistory;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 
 *
 * @author System Integrator Corp.
 *
 */
public class FriendCouponUseHistoryDaoImpl implements FriendCouponUseHistoryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<FriendCouponUseHistory, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;
  
  /**
   * Constructor:
   */
  public FriendCouponUseHistoryDaoImpl() {
    genericDao = new GenericDaoImpl<FriendCouponUseHistory, Long>(FriendCouponUseHistory.class);
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
   *  顧客コードを指定して有効コードを取得します。
   * @param customerCode 顧客コード
   * @return　有効コード
   */
  public Long getAllPoint(String customerCode) {
    Object[] params = new Object[]{customerCode};
    final String query =" SELECT  SUM(POINTS) FROM ("
       +"  SELECT CASE WHEN FCUH.USE_HISTORY_ID IN ("
       +"     SELECT USE_HISTORY_ID FROM FRIEND_COUPON_USE_HISTORY WHERE COUPON_CODE = FCUH.COUPON_CODE AND POINT_STATUS = '1' " 
       +" ORDER BY CREATED_DATETIME ASC LIMIT ("
       +"       SELECT COUPON_USE_NUM FROM FRIEND_COUPON_USE_HISTORY WHERE COUPON_CODE = FCUH.COUPON_CODE LIMIT 1"
       +"     )"
       +"   )  THEN FCUH.FORMER_USE_POINT ELSE POINT END AS POINTS"
       +"   FROM FRIEND_COUPON_USE_HISTORY FCUH INNER JOIN FRIEND_COUPON_ISSUE_HISTORY FCIH ON FCIH.COUPON_CODE = FCUH.COUPON_CODE "
       +"   AND FCIH.CUSTOMER_CODE = ? AND FCUH.POINT_STATUS = '1'"
       +") USER_POINT";
    Object result = genericDao.executeScalar(query, params);  
    
    if(result != null ){
      return ((Number)result).longValue();
    }
    return 0L;
  }

  /**
   * FriendCouponUseHistoryを削除します。
   * @param obj 削除対象のFriendCouponUseHistory
   */
  public void delete(FriendCouponUseHistory obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
    
  }

  /**
   * 主キー列の値を指定してギフトを削除します。
   * @param useHistoryId 履歴使うコード
   */
  public void delete(String useHistoryId) {
    Object[] params = new Object[]{useHistoryId};
    final String query = "DELETE FROM FRIEND_COUPON_USE_HISTORY"
        + " WHERE USE_HISTORY_ID = ?";
    genericDao.updateByQuery(query, params);
    
  }

  /**
   * 主キー列の値を指定してギフトが既に存在するかどうかを返します。
   * @param useHistoryId 履歴使うコード
   * @return 主キー列の値に対応するFRIEND_COUPON_USE_HISTORYの行が存在すればtrue
   */
  public boolean exists(String useHistoryId) {
    Object[] params = new Object[]{useHistoryId};
    final String query = "SELECT COUNT(*) FROM FRIEND_COUPON_USE_HISTORY"
        + " WHERE USE_HISTORY_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;
  }

  /**
   * Queryオブジェクトを指定してギフトのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するGiftのリスト
   */
  public List<FriendCouponUseHistory> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してギフトのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するGiftのリスト
   */
  public List<FriendCouponUseHistory> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * 主キー列の値を指定してキャンペーンのインスタンスを取得します。
   * @param useHistoryId 履歴使うコード
   * @return 主キー列の値に対応するFriendCouponUseHistoryのインスタンス
   */
  public FriendCouponUseHistory load(String useHistoryId) {
    Object[] params = new Object[]{useHistoryId};
    final String query = "SELECT * FROM FRIEND_COUPON_USE_HISTORY"
        + " WHERE USE_HISTORY_ID = ?";
    List<FriendCouponUseHistory> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のFriendCouponUseHistoryのリスト
   */
  public List<FriendCouponUseHistory> loadAll() {
    return genericDao.loadAll();
  }

  /**
   * 指定されたorm_rowidを持つキャンペーンのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するFriendCouponUseHistoryのインスタンス
   */
  public FriendCouponUseHistory loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * FriendCouponUseHistoryを削除します。
   * @param obj 削除対象のFriendCouponUseHistory
   */
  public void delete(FriendCouponUseHistory transactionObject) {
    genericDao.delete(transactionObject);   
  }

  /**
   * 新規FriendCouponUseHistoryをデータベースに追加します。
   * @param obj 追加対象のFriendCouponUseHistory
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(FriendCouponUseHistory newInstance) {
    return genericDao.insert(newInstance);
  }

  /**
   * FriendCouponUseHistoryを更新します。
   * @param obj 更新対象のFriendCouponUseHistory
   */
  public void update(FriendCouponUseHistory transactionObject) {
    genericDao.update(transactionObject);  
  }

  
  /**
   * クーポンコードを指定してFriendCouponUseHistoryを取得します。
   *　@param couponCode クーポンコード
   * @return　検索結果に相当するFriendCouponUseHistory　
   */
  public FriendCouponUseHistory loadByCouponCode(String couponCode) {
    Object[] params = new Object[]{couponCode};
    final String query = "SELECT * FROM FRIEND_COUPON_USE_HISTORY "
        + " WHERE COUPON_CODE = ?";
    List<FriendCouponUseHistory> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }
  }

  @Override
  public Long getIssueObtainPoint(String customerCode) {
    Object[] params = new Object[]{customerCode};
    final String query = "SELECT SUM(A.ISSUE_OBTAIN_POINT) " 
        + "FROM FRIEND_COUPON_ISSUE_HISTORY A "
        + "WHERE A.CUSTOMER_CODE = ? ";
    Object result = genericDao.executeScalar(query, params);  
    if(result != null ){
      return ((Number)result).longValue();
    }
    return 0L;
  }
  
  @Override
  public boolean existFriendCouponIssueHistory(String friendCouponRuleNo) {
    Object[] params = new Object[]{friendCouponRuleNo};
    final String query = "SELECT COUNT(*) " 
        + "FROM FRIEND_COUPON_ISSUE_HISTORY A "
        + "WHERE A.FRIEND_COUPON_RULE_NO = ? ";
    Object result =  genericDao.executeScalar(query, params);  
    return ((Number) result).intValue() > 0;
  }
}
