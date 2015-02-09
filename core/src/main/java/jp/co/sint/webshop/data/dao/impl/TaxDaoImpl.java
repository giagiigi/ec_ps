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
import jp.co.sint.webshop.data.dao.TaxDao;
import jp.co.sint.webshop.data.dto.Tax;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 消費税
 *
 * @author System Integrator Corp.
 *
 */
public class TaxDaoImpl implements TaxDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Tax, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public TaxDaoImpl() {
    genericDao = new GenericDaoImpl<Tax, Long>(Tax.class);
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
   * 指定されたorm_rowidを持つ消費税のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するTaxのインスタンス
   */
  public Tax loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して消費税のインスタンスを取得します。
   * @param taxNo 消費税番号
   * @return 主キー列の値に対応するTaxのインスタンス
   */
  public Tax load(Long taxNo) {
    Object[] params = new Object[]{taxNo};
    final String query = "SELECT * FROM TAX"
        + " WHERE TAX_NO = ?";
    List<Tax> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して消費税が既に存在するかどうかを返します。
   * @param taxNo 消費税番号
   * @return 主キー列の値に対応するTaxの行が存在すればtrue
   */
  public boolean exists(Long taxNo) {
    Object[] params = new Object[]{taxNo};
    final String query = "SELECT COUNT(*) FROM TAX"
        + " WHERE TAX_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Taxをデータベースに追加します。
   * @param obj 追加対象のTax
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Tax obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Taxをデータベースに追加します。
   * @param obj 追加対象のTax
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Tax obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 消費税を更新します。
   * @param obj 更新対象のTax
   */
  public void update(Tax obj) {
    genericDao.update(obj);
  }

  /**
   * 消費税を更新します。
   * @param obj 更新対象のTax
   */
  public void update(Tax obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 消費税を削除します。
   * @param obj 削除対象のTax
   */
  public void delete(Tax obj) {
    genericDao.delete(obj);
  }

  /**
   * 消費税を削除します。
   * @param obj 削除対象のTax
   */
  public void delete(Tax obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して消費税を削除します。
   * @param taxNo 消費税番号
   */
  public void delete(Long taxNo) {
    Object[] params = new Object[]{taxNo};
    final String query = "DELETE FROM TAX"
        + " WHERE TAX_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して消費税のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するTaxのリスト
   */
  public List<Tax> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して消費税のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するTaxのリスト
   */
  public List<Tax> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のTaxのリスト
   */
  public List<Tax> loadAll() {
    return genericDao.loadAll();
  }

}
