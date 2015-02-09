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
import jp.co.sint.webshop.data.dao.BroadcastMailqueueDetailDao;
import jp.co.sint.webshop.data.dto.BroadcastMailqueueDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 同報配信メールキュー明細
 *
 * @author System Integrator Corp.
 *
 */
public class BroadcastMailqueueDetailDaoImpl implements BroadcastMailqueueDetailDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<BroadcastMailqueueDetail, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public BroadcastMailqueueDetailDaoImpl() {
    genericDao = new GenericDaoImpl<BroadcastMailqueueDetail, Long>(BroadcastMailqueueDetail.class);
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
   * 指定されたorm_rowidを持つ同報配信メールキュー明細のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するBroadcastMailqueueDetailのインスタンス
   */
  public BroadcastMailqueueDetail loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して同報配信メールキュー明細のインスタンスを取得します。
   * @param mailQueueId メールキューID
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するBroadcastMailqueueDetailのインスタンス
   */
  public BroadcastMailqueueDetail load(Long mailQueueId, String customerCode) {
    Object[] params = new Object[]{mailQueueId, customerCode};
    final String query = "SELECT * FROM BROADCAST_MAILQUEUE_DETAIL"
        + " WHERE MAIL_QUEUE_ID = ?"
        + " AND CUSTOMER_CODE = ?";
    List<BroadcastMailqueueDetail> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して同報配信メールキュー明細が既に存在するかどうかを返します。
   * @param mailQueueId メールキューID
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するBroadcastMailqueueDetailの行が存在すればtrue
   */
  public boolean exists(Long mailQueueId, String customerCode) {
    Object[] params = new Object[]{mailQueueId, customerCode};
    final String query = "SELECT COUNT(*) FROM BROADCAST_MAILQUEUE_DETAIL"
        + " WHERE MAIL_QUEUE_ID = ?"
        + " AND CUSTOMER_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規BroadcastMailqueueDetailをデータベースに追加します。
   * @param obj 追加対象のBroadcastMailqueueDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(BroadcastMailqueueDetail obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規BroadcastMailqueueDetailをデータベースに追加します。
   * @param obj 追加対象のBroadcastMailqueueDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(BroadcastMailqueueDetail obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 同報配信メールキュー明細を更新します。
   * @param obj 更新対象のBroadcastMailqueueDetail
   */
  public void update(BroadcastMailqueueDetail obj) {
    genericDao.update(obj);
  }

  /**
   * 同報配信メールキュー明細を更新します。
   * @param obj 更新対象のBroadcastMailqueueDetail
   */
  public void update(BroadcastMailqueueDetail obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 同報配信メールキュー明細を削除します。
   * @param obj 削除対象のBroadcastMailqueueDetail
   */
  public void delete(BroadcastMailqueueDetail obj) {
    genericDao.delete(obj);
  }

  /**
   * 同報配信メールキュー明細を削除します。
   * @param obj 削除対象のBroadcastMailqueueDetail
   */
  public void delete(BroadcastMailqueueDetail obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して同報配信メールキュー明細を削除します。
   * @param mailQueueId メールキューID
   * @param customerCode 顧客コード
   */
  public void delete(Long mailQueueId, String customerCode) {
    Object[] params = new Object[]{mailQueueId, customerCode};
    final String query = "DELETE FROM BROADCAST_MAILQUEUE_DETAIL"
        + " WHERE MAIL_QUEUE_ID = ?"
        + " AND CUSTOMER_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して同報配信メールキュー明細のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するBroadcastMailqueueDetailのリスト
   */
  public List<BroadcastMailqueueDetail> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して同報配信メールキュー明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するBroadcastMailqueueDetailのリスト
   */
  public List<BroadcastMailqueueDetail> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のBroadcastMailqueueDetailのリスト
   */
  public List<BroadcastMailqueueDetail> loadAll() {
    return genericDao.loadAll();
  }

}
