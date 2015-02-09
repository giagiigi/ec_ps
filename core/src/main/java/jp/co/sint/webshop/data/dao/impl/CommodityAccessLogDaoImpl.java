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
import jp.co.sint.webshop.data.dao.CommodityAccessLogDao;
import jp.co.sint.webshop.data.dto.CommodityAccessLog;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 商品別アクセスログ
 *
 * @author System Integrator Corp.
 *
 */
public class CommodityAccessLogDaoImpl implements CommodityAccessLogDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CommodityAccessLog, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CommodityAccessLogDaoImpl() {
    genericDao = new GenericDaoImpl<CommodityAccessLog, Long>(CommodityAccessLog.class);
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
   * 指定されたorm_rowidを持つ商品別アクセスログのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCommodityAccessLogのインスタンス
   */
  public CommodityAccessLog loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して商品別アクセスログのインスタンスを取得します。
   * @param commodityAccessLogId 商品別アクセスログID
   * @return 主キー列の値に対応するCommodityAccessLogのインスタンス
   */
  public CommodityAccessLog load(Long commodityAccessLogId) {
    Object[] params = new Object[]{commodityAccessLogId};
    final String query = "SELECT * FROM COMMODITY_ACCESS_LOG"
        + " WHERE COMMODITY_ACCESS_LOG_ID = ?";
    List<CommodityAccessLog> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して商品別アクセスログが既に存在するかどうかを返します。
   * @param commodityAccessLogId 商品別アクセスログID
   * @return 主キー列の値に対応するCommodityAccessLogの行が存在すればtrue
   */
  public boolean exists(Long commodityAccessLogId) {
    Object[] params = new Object[]{commodityAccessLogId};
    final String query = "SELECT COUNT(*) FROM COMMODITY_ACCESS_LOG"
        + " WHERE COMMODITY_ACCESS_LOG_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CommodityAccessLogをデータベースに追加します。
   * @param obj 追加対象のCommodityAccessLog
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CommodityAccessLog obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CommodityAccessLogをデータベースに追加します。
   * @param obj 追加対象のCommodityAccessLog
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CommodityAccessLog obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 商品別アクセスログを更新します。
   * @param obj 更新対象のCommodityAccessLog
   */
  public void update(CommodityAccessLog obj) {
    genericDao.update(obj);
  }

  /**
   * 商品別アクセスログを更新します。
   * @param obj 更新対象のCommodityAccessLog
   */
  public void update(CommodityAccessLog obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 商品別アクセスログを削除します。
   * @param obj 削除対象のCommodityAccessLog
   */
  public void delete(CommodityAccessLog obj) {
    genericDao.delete(obj);
  }

  /**
   * 商品別アクセスログを削除します。
   * @param obj 削除対象のCommodityAccessLog
   */
  public void delete(CommodityAccessLog obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して商品別アクセスログを削除します。
   * @param commodityAccessLogId 商品別アクセスログID
   */
  public void delete(Long commodityAccessLogId) {
    Object[] params = new Object[]{commodityAccessLogId};
    final String query = "DELETE FROM COMMODITY_ACCESS_LOG"
        + " WHERE COMMODITY_ACCESS_LOG_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して商品別アクセスログのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommodityAccessLogのリスト
   */
  public List<CommodityAccessLog> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して商品別アクセスログのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityAccessLogのリスト
   */
  public List<CommodityAccessLog> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCommodityAccessLogのリスト
   */
  public List<CommodityAccessLog> loadAll() {
    return genericDao.loadAll();
  }

}
