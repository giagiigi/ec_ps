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
import jp.co.sint.webshop.data.dao.CommodityProductionDateDao;
import jp.co.sint.webshop.data.dto.CommodityProductionDate;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;

/**
 * 商品生产日期
 * 
 * @author System Integrator Corp.
 */
public class CommodityProductionDateDaoImpl implements CommodityProductionDateDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CommodityProductionDate, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CommodityProductionDateDaoImpl() {
    genericDao = new GenericDaoImpl<CommodityProductionDate, Long>(CommodityProductionDate.class);
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
   * @return idに対応するCommodityProductionDateのインスタンス
   */
  public CommodityProductionDate loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してギフトのインスタンスを取得します。
   * 
   * @param skuCode
   *          ギフトコード
   * @return 主キー列の値に対応するCommodityProductionDateのインスタンス
   */
  public CommodityProductionDate load(String skuCode) {
    Object[] params = new Object[] {
      skuCode
    };
    final String query = "SELECT * FROM COMMODITY_PRODUCTION_DATE WHERE SKU_CODE = ?";
    List<CommodityProductionDate> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してギフトが既に存在するかどうかを返します。
   * 
   * @param skuCode
   *          ギフトコード
   * @return 主キー列の値に対応するCommodityProductionDateの行が存在すればtrue
   */
  public boolean exists(String skuCode) {
    Object[] params = new Object[] {
      skuCode
    };
    final String query = "SELECT COUNT(*) FROM COMMODITY_PRODUCTION_DATE WHERE SKU_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CommodityProductionDateをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のCommodityProductionDate
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CommodityProductionDate obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CommodityProductionDateをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のCommodityProductionDate
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CommodityProductionDate obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * ギフトを更新します。
   * 
   * @param obj
   *          更新対象のCommodityProductionDate
   */
  public void update(CommodityProductionDate obj) {
    genericDao.update(obj);
  }

  /**
   * ギフトを更新します。
   * 
   * @param obj
   *          更新対象のCommodityProductionDate
   */
  public void update(CommodityProductionDate obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * ギフトを削除します。
   * 
   * @param obj
   *          削除対象のCommodityProductionDate
   */
  public void delete(CommodityProductionDate obj) {
    genericDao.delete(obj);
  }

  /**
   * ギフトを削除します。
   * 
   * @param obj
   *          削除対象のCommodityProductionDate
   */
  public void delete(CommodityProductionDate obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してギフトを削除します。
   * 
   * @param skuCode
   *          ギフトコード
   */
  public void delete(String skuCode) {
    Object[] params = new Object[] {
      skuCode
    };
    final String query = "DELETE FROM COMMODITY_PRODUCTION_DATE WHERE SKU_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してギフトのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するCommodityProductionDateのリスト
   */
  public List<CommodityProductionDate> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してギフトのリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityProductionDateのリスト
   */
  public List<CommodityProductionDate> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のCommodityProductionDateのリスト
   */
  public List<CommodityProductionDate> loadAll() {
    return genericDao.loadAll();
  }

}
