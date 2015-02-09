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
import jp.co.sint.webshop.data.dao.BroadcastMailqueueHeaderDao;
import jp.co.sint.webshop.data.dto.BroadcastMailqueueHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 同報配信メールキューヘッダ
 *
 * @author System Integrator Corp.
 *
 */
public class BroadcastMailqueueHeaderDaoImpl implements BroadcastMailqueueHeaderDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<BroadcastMailqueueHeader, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public BroadcastMailqueueHeaderDaoImpl() {
    genericDao = new GenericDaoImpl<BroadcastMailqueueHeader, Long>(BroadcastMailqueueHeader.class);
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
   * 指定されたorm_rowidを持つ同報配信メールキューヘッダのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するBroadcastMailqueueHeaderのインスタンス
   */
  public BroadcastMailqueueHeader loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して同報配信メールキューヘッダのインスタンスを取得します。
   * @param mailQueueId メールキューID
   * @return 主キー列の値に対応するBroadcastMailqueueHeaderのインスタンス
   */
  public BroadcastMailqueueHeader load(Long mailQueueId) {
    Object[] params = new Object[]{mailQueueId};
    final String query = "SELECT * FROM BROADCAST_MAILQUEUE_HEADER"
        + " WHERE MAIL_QUEUE_ID = ?";
    List<BroadcastMailqueueHeader> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して同報配信メールキューヘッダが既に存在するかどうかを返します。
   * @param mailQueueId メールキューID
   * @return 主キー列の値に対応するBroadcastMailqueueHeaderの行が存在すればtrue
   */
  public boolean exists(Long mailQueueId) {
    Object[] params = new Object[]{mailQueueId};
    final String query = "SELECT COUNT(*) FROM BROADCAST_MAILQUEUE_HEADER"
        + " WHERE MAIL_QUEUE_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規BroadcastMailqueueHeaderをデータベースに追加します。
   * @param obj 追加対象のBroadcastMailqueueHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(BroadcastMailqueueHeader obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規BroadcastMailqueueHeaderをデータベースに追加します。
   * @param obj 追加対象のBroadcastMailqueueHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(BroadcastMailqueueHeader obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 同報配信メールキューヘッダを更新します。
   * @param obj 更新対象のBroadcastMailqueueHeader
   */
  public void update(BroadcastMailqueueHeader obj) {
    genericDao.update(obj);
  }

  /**
   * 同報配信メールキューヘッダを更新します。
   * @param obj 更新対象のBroadcastMailqueueHeader
   */
  public void update(BroadcastMailqueueHeader obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 同報配信メールキューヘッダを削除します。
   * @param obj 削除対象のBroadcastMailqueueHeader
   */
  public void delete(BroadcastMailqueueHeader obj) {
    genericDao.delete(obj);
  }

  /**
   * 同報配信メールキューヘッダを削除します。
   * @param obj 削除対象のBroadcastMailqueueHeader
   */
  public void delete(BroadcastMailqueueHeader obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して同報配信メールキューヘッダを削除します。
   * @param mailQueueId メールキューID
   */
  public void delete(Long mailQueueId) {
    Object[] params = new Object[]{mailQueueId};
    final String query = "DELETE FROM BROADCAST_MAILQUEUE_HEADER"
        + " WHERE MAIL_QUEUE_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して同報配信メールキューヘッダのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するBroadcastMailqueueHeaderのリスト
   */
  public List<BroadcastMailqueueHeader> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して同報配信メールキューヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するBroadcastMailqueueHeaderのリスト
   */
  public List<BroadcastMailqueueHeader> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のBroadcastMailqueueHeaderのリスト
   */
  public List<BroadcastMailqueueHeader> loadAll() {
    return genericDao.loadAll();
  }

}
