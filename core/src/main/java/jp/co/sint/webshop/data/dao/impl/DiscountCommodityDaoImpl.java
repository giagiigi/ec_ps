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
import jp.co.sint.webshop.data.dao.DiscountCommodityDao;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;

/**
 * 限时限量折扣关联商品
 * 
 * @author System Integrator Corp.
 */
public class DiscountCommodityDaoImpl implements DiscountCommodityDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<DiscountCommodity, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public DiscountCommodityDaoImpl() {
    genericDao = new GenericDaoImpl<DiscountCommodity, Long>(DiscountCommodity.class);
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
   * 指定されたorm_rowidを持つキャンペーン対象商品のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するDiscountCommodityのインスタンス
   */
  public DiscountCommodity loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してキャンペーン対象商品のインスタンスを取得します。
   * 
   * @param discountCode
   *          折扣编号
   * @param commodityCode
   *          商品コード
   * @return 主キー列の値に対応するDiscountCommodityのインスタンス
   */
  public DiscountCommodity load(String discountCode, String commodityCode) {
    Object[] params = new Object[] {
        discountCode, commodityCode
    };
    final String query = "SELECT * FROM DISCOUNT_COMMODITY WHERE DISCOUNT_CODE = ? AND COMMODITY_CODE = ?";
    List<DiscountCommodity> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してキャンペーン対象商品が既に存在するかどうかを返します。
   * 
   * @param discountCode
   *          折扣编号
   * @param commodityCode
   *          商品コード
   * @return 主キー列の値に対応するDiscountCommodityの行が存在すればtrue
   */
  public boolean exists(String discountCode, String commodityCode) {
    Object[] params = new Object[] {
        discountCode, commodityCode
    };
    final String query = "SELECT COUNT(*) FROM DISCOUNT_COMMODITY WHERE DISCOUNT_CODE = ? AND COMMODITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規DiscountCommodityをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のDiscountCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(DiscountCommodity obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規DiscountCommodityをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のDiscountCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(DiscountCommodity obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * キャンペーン対象商品を更新します。
   * 
   * @param obj
   *          更新対象のDiscountCommodity
   */
  public void update(DiscountCommodity obj) {
    genericDao.update(obj);
  }

  /**
   * キャンペーン対象商品を更新します。
   * 
   * @param obj
   *          更新対象のDiscountCommodity
   */
  public void update(DiscountCommodity obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * キャンペーン対象商品を削除します。
   * 
   * @param obj
   *          削除対象のDiscountCommodity
   */
  public void delete(DiscountCommodity obj) {
    genericDao.delete(obj);
  }

  /**
   * キャンペーン対象商品を削除します。
   * 
   * @param obj
   *          削除対象のDiscountCommodity
   */
  public void delete(DiscountCommodity obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してキャンペーン対象商品を削除します。
   * 
   * @param discountCode
   *          折扣编号
   * @param commodityCode
   *          商品コード
   */
  public void delete(String discountCode, String commodityCode) {
    Object[] params = new Object[] {
        discountCode, commodityCode
    };
    final String query = "DELETE FROM DISCOUNT_COMMODITY WHERE DISCOUNT_CODE = ? AND COMMODITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してキャンペーン対象商品のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するDiscountCommodityのリスト
   */
  public List<DiscountCommodity> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してキャンペーン対象商品のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するDiscountCommodityのリスト
   */
  public List<DiscountCommodity> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のDiscountCommodityのリスト
   */
  public List<DiscountCommodity> loadAll() {
    return genericDao.loadAll();
  }

}
