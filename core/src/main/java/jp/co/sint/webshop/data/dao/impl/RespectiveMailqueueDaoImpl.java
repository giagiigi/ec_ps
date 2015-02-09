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
import jp.co.sint.webshop.data.dao.RespectiveMailqueueDao;
import jp.co.sint.webshop.data.dto.RespectiveMailqueue;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 個別配信メールキュー
 *
 * @author System Integrator Corp.
 *
 */
public class RespectiveMailqueueDaoImpl implements RespectiveMailqueueDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<RespectiveMailqueue, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public RespectiveMailqueueDaoImpl() {
    genericDao = new GenericDaoImpl<RespectiveMailqueue, Long>(RespectiveMailqueue.class);
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
   * 指定されたorm_rowidを持つ個別配信メールキューのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するRespectiveMailqueueのインスタンス
   */
  public RespectiveMailqueue loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して個別配信メールキューのインスタンスを取得します。
   * @param mailQueueId メールキューID
   * @return 主キー列の値に対応するRespectiveMailqueueのインスタンス
   */
  public RespectiveMailqueue load(Long mailQueueId) {
    Object[] params = new Object[]{mailQueueId};
    final String query = "SELECT * FROM RESPECTIVE_MAILQUEUE"
        + " WHERE MAIL_QUEUE_ID = ?";
    List<RespectiveMailqueue> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して個別配信メールキューが既に存在するかどうかを返します。
   * @param mailQueueId メールキューID
   * @return 主キー列の値に対応するRespectiveMailqueueの行が存在すればtrue
   */
  public boolean exists(Long mailQueueId) {
    Object[] params = new Object[]{mailQueueId};
    final String query = "SELECT COUNT(*) FROM RESPECTIVE_MAILQUEUE"
        + " WHERE MAIL_QUEUE_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規RespectiveMailqueueをデータベースに追加します。
   * @param obj 追加対象のRespectiveMailqueue
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(RespectiveMailqueue obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規RespectiveMailqueueをデータベースに追加します。
   * @param obj 追加対象のRespectiveMailqueue
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(RespectiveMailqueue obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 個別配信メールキューを更新します。
   * @param obj 更新対象のRespectiveMailqueue
   */
  public void update(RespectiveMailqueue obj) {
    genericDao.update(obj);
  }

  /**
   * 個別配信メールキューを更新します。
   * @param obj 更新対象のRespectiveMailqueue
   */
  public void update(RespectiveMailqueue obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 個別配信メールキューを削除します。
   * @param obj 削除対象のRespectiveMailqueue
   */
  public void delete(RespectiveMailqueue obj) {
    genericDao.delete(obj);
  }

  /**
   * 個別配信メールキューを削除します。
   * @param obj 削除対象のRespectiveMailqueue
   */
  public void delete(RespectiveMailqueue obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して個別配信メールキューを削除します。
   * @param mailQueueId メールキューID
   */
  public void delete(Long mailQueueId) {
    Object[] params = new Object[]{mailQueueId};
    final String query = "DELETE FROM RESPECTIVE_MAILQUEUE"
        + " WHERE MAIL_QUEUE_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して個別配信メールキューのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRespectiveMailqueueのリスト
   */
  public List<RespectiveMailqueue> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して個別配信メールキューのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRespectiveMailqueueのリスト
   */
  public List<RespectiveMailqueue> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のRespectiveMailqueueのリスト
   */
  public List<RespectiveMailqueue> loadAll() {
    return genericDao.loadAll();
  }

}
