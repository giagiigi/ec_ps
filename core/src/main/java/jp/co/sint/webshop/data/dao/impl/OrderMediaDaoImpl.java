//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.OrderMediaDao;
import jp.co.sint.webshop.data.dto.OrderMedia;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;

/**
 * 订单媒体
 * 
 * @author Kousen.
 */
public class OrderMediaDaoImpl implements OrderMediaDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<OrderMedia, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public OrderMediaDaoImpl() {
    genericDao = new GenericDaoImpl<OrderMedia, Long>(OrderMedia.class);
  }

  /**
   * SessionFactoryを取得します
   * 
   * @return SessionFactoryのインスタンス
   */
  public SessionFactory getSessionFactory() {
    return this.sessionFactory;
  }

  /**
   * SessionFactoryを設定します
   * 
   * @param factory
   *          SessionFactoryのインスタンス
   */
  public void setSessionFactory(SessionFactory factory) {
    this.sessionFactory = factory;
    genericDao.setSessionFactory(factory);
  }

  /**
   * 指定されたorm_rowidを持つ订单媒体のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するOrderMediaのインスタンス
   */
  public OrderMedia loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して订单媒体のインスタンスを取得します。
   * 
   * @param orderNo
   *          订单号
   * @return 主キー列の値に対応するOrderMediaのインスタンス
   */
  public OrderMedia load(String orderNo) {
    Object[] params = new Object[] {
      orderNo
    };
    final String query = "SELECT * FROM ORDER_MEDIA WHERE ORDER_NO = ?";
    List<OrderMedia> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して订单媒体が既に存在するかどうかを返します。
   * 
   * @param orderNo
   *          订单号
   * @return 主キー列の値に対応するOrderMediaの行が存在すればtrue
   */
  public boolean exists(String orderNo) {
    Object[] params = new Object[] {
      orderNo
    };
    final String query = "SELECT COUNT(*) FROM ORDER_MEDIA WHERE ORDER_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規OrderMediaをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のOrderMedia
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(OrderMedia obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規OrderMediaをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のOrderMedia
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(OrderMedia obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 订单媒体を更新します。
   * 
   * @param obj
   *          更新対象のOrderMedia
   */
  public void update(OrderMedia obj) {
    genericDao.update(obj);
  }

  /**
   * 订单媒体を更新します。
   * 
   * @param obj
   *          更新対象のOrderMedia
   */
  public void update(OrderMedia obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 订单媒体を削除します。
   * 
   * @param obj
   *          削除対象のOrderMedia
   */
  public void delete(OrderMedia obj) {
    genericDao.delete(obj);
  }

  /**
   * 订单媒体を削除します。
   * 
   * @param obj
   *          削除対象のOrderMedia
   */
  public void delete(OrderMedia obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して订单媒体を削除します。
   * 
   * @param orderNo
   *          订单号
   */
  public void delete(String orderNo) {
    Object[] params = new Object[] {
      orderNo
    };
    final String query = "DELETE FROM ORDER_MEDIA WHERE ORDER_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して订单媒体のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するOrderMediaのリスト
   */
  public List<OrderMedia> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して订单媒体のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するOrderMediaのリスト
   */
  public List<OrderMedia> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のOrderMediaのリスト
   */
  public List<OrderMedia> loadAll() {
    return genericDao.loadAll();
  }

}
