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
import jp.co.sint.webshop.data.dao.DiscountHeaderDao;
import jp.co.sint.webshop.data.dto.DiscountHeader;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;

/**
 * 限时限量折扣
 * 
 * @author System Integrator Corp.
 */
public class DiscountHeaderDaoImpl implements DiscountHeaderDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<DiscountHeader, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public DiscountHeaderDaoImpl() {
    genericDao = new GenericDaoImpl<DiscountHeader, Long>(DiscountHeader.class);
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
   * 指定されたorm_rowidを持つギフトのインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するDiscountHeaderのインスタンス
   */
  public DiscountHeader loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してギフトのインスタンスを取得します。
   * 
   * @param discountCode
   *          折扣编号
   * @return 主キー列の値に対応するDiscountHeaderのインスタンス
   */
  public DiscountHeader load(String discountCode) {
    Object[] params = new Object[] {
      discountCode
    };
    final String query = "SELECT * FROM DISCOUNT_HEADER WHERE DISCOUNT_CODE = ?";
    List<DiscountHeader> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してギフトが既に存在するかどうかを返します。
   * 
   * @param discountCode
   *          折扣编号
   * @return 主キー列の値に対応するDiscountHeaderの行が存在すればtrue
   */
  public boolean exists(String discountCode) {
    Object[] params = new Object[] {
      discountCode
    };
    final String query = "SELECT COUNT(*) FROM DISCOUNT_HEADER WHERE DISCOUNT_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規DiscountHeaderをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のDiscountHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(DiscountHeader obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規DiscountHeaderをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のDiscountHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(DiscountHeader obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * ギフトを更新します。
   * 
   * @param obj
   *          更新対象のDiscountHeader
   */
  public void update(DiscountHeader obj) {
    genericDao.update(obj);
  }

  /**
   * ギフトを更新します。
   * 
   * @param obj
   *          更新対象のDiscountHeader
   */
  public void update(DiscountHeader obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * ギフトを削除します。
   * 
   * @param obj
   *          削除対象のDiscountHeader
   */
  public void delete(DiscountHeader obj) {
    genericDao.delete(obj);
  }

  /**
   * ギフトを削除します。
   * 
   * @param obj
   *          削除対象のDiscountHeader
   */
  public void delete(DiscountHeader obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してギフトを削除します。
   * 
   * @param discountCode
   *          ギフトコード
   */
  public void delete(String discountCode) {
    Object[] params = new Object[] {
      discountCode
    };
    final String query = "DELETE FROM DISCOUNT_HEADER WHERE DISCOUNT_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してギフトのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するDiscountHeaderのリスト
   */
  public List<DiscountHeader> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してギフトのリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するDiscountHeaderのリスト
   */
  public List<DiscountHeader> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のDiscountHeaderのリスト
   */
  public List<DiscountHeader> loadAll() {
    return genericDao.loadAll();
  }

}
