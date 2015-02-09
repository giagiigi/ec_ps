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
import jp.co.sint.webshop.data.dao.OrderInvoiceDao;
import jp.co.sint.webshop.data.dto.OrderInvoice;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;

/**
 * 受注ヘッダ
 * 
 * @author System Integrator Corp.
 */
public class OrderInvoiceDaoImpl implements OrderInvoiceDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<OrderInvoice, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public OrderInvoiceDaoImpl() {
    genericDao = new GenericDaoImpl<OrderInvoice, Long>(OrderInvoice.class);
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
   * 指定されたorm_rowidを持つ受注ヘッダのインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するOrderHeaderのインスタンス
   */
  public OrderInvoice loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して受注ヘッダのインスタンスを取得します。
   * 
   * @param orderNo
   *          受注番号
   * @return 主キー列の値に対応するOrderHeaderのインスタンス
   */
  public OrderInvoice load(String orderNo) {
    Object[] params = new Object[] {
      orderNo
    };
    final String query = "SELECT * FROM ORDER_INVOICE" + " WHERE ORDER_NO = ?";
    List<OrderInvoice> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して受注ヘッダが既に存在するかどうかを返します。
   * 
   * @param orderNo
   *          受注番号
   * @return 主キー列の値に対応するOrderHeaderの行が存在すればtrue
   */
  public boolean exists(String orderNo) {
    Object[] params = new Object[] {
      orderNo
    };
    final String query = "SELECT COUNT(*) FROM ORDER_INVOICE" + " WHERE ORDER_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規OrderHeaderをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のOrderHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(OrderInvoice obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規OrderHeaderをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のOrderHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(OrderInvoice obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 受注ヘッダを更新します。
   * 
   * @param obj
   *          更新対象のOrderHeader
   */
  public void update(OrderInvoice obj) {
    genericDao.update(obj);
  }

  /**
   * 受注ヘッダを更新します。
   * 
   * @param obj
   *          更新対象のOrderHeader
   */
  public void update(OrderInvoice obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 受注ヘッダを削除します。
   * 
   * @param obj
   *          削除対象のOrderHeader
   */
  public void delete(OrderInvoice obj) {
    genericDao.delete(obj);
  }

  /**
   * 受注ヘッダを削除します。
   * 
   * @param obj
   *          削除対象のOrderHeader
   */
  public void delete(OrderInvoice obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して受注ヘッダを削除します。
   * 
   * @param orderNo
   *          受注番号
   */
  public void delete(String orderNo) {
    Object[] params = new Object[] {
      orderNo
    };
    final String query = "DELETE FROM ORDER_INVOICE" + " WHERE ORDER_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して受注ヘッダのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するOrderHeaderのリスト
   */
  public List<OrderInvoice> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して受注ヘッダのリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するOrderHeaderのリスト
   */
  public List<OrderInvoice> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  public List<OrderInvoice> loadAll() {
    return genericDao.loadAll();
  }

}
