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

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.HotSaleCommodityDao;
import jp.co.sint.webshop.data.dto.HotSaleCommodity;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 商品詳細
 *
 * @author System Integrator Corp.
 *
 */
public class HotSaleCommodityDaoImpl implements HotSaleCommodityDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<HotSaleCommodity, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public HotSaleCommodityDaoImpl() {
    genericDao = new GenericDaoImpl<HotSaleCommodity, Long>(HotSaleCommodity.class);
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
   * 指定されたorm_rowidを持つ商品詳細のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCommodityDetailのインスタンス
   */
  public HotSaleCommodity loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して商品詳細のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するCommodityDetailのインスタンス
   */
  public HotSaleCommodity load(String commodityCode ,String languageCode) {
    Object[] params = new Object[]{commodityCode,languageCode};
    final String query = "SELECT * FROM HOT_SALE_COMMODITY"
        + " WHERE COMMODITY_CODE = ? and language_code = ? ";
    List<HotSaleCommodity> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }
  }
  
  public List<HotSaleCommodity> loadByLanguageCode(String languageCode) {
    Object[] params = new Object[]{languageCode};
    final String query = "SELECT * FROM HOT_SALE_COMMODITY"
        + " WHERE  language_code = ? order by sort_rank ";
    List<HotSaleCommodity> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result;
    } else {
      return null;
    }
  }

  /**
   * 主キー列の値を指定して商品詳細が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するCommodityDetailの行が存在すればtrue
   */
  public boolean exists(String commodityCode ,String languageCode) {
    Object[] params = new Object[]{commodityCode,languageCode};
    final String query = "SELECT COUNT(*) FROM HOT_SALE_COMMODITY"
      + " WHERE COMMODITY_CODE = ? and language_code = ? ";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CommodityDetailをデータベースに追加します。
   * @param obj 追加対象のCommodityDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(HotSaleCommodity obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CommodityDetailをデータベースに追加します。
   * @param obj 追加対象のCommodityDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(HotSaleCommodity obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 商品詳細を更新します。
   * @param obj 更新対象のCommodityDetail
   */
  public void update(HotSaleCommodity obj) {
    genericDao.update(obj);
  }

  /**
   * 商品詳細を更新します。
   * @param obj 更新対象のCommodityDetail
   */
  public void update(HotSaleCommodity obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 商品詳細を削除します。
   * @param obj 削除対象のCommodityDetail
   */
  public void delete(HotSaleCommodity obj) {
    genericDao.delete(obj);
  }

  /**
   * 商品詳細を削除します。
   * @param obj 削除対象のCommodityDetail
   */
  public void delete(HotSaleCommodity obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して商品詳細を削除します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   */
  public void delete(String commodityCode ,String languageCode) {
    Object[] params = new Object[]{commodityCode,languageCode};
    final String query = "DELETE FROM HOT_SALE_COMMODITY"
      + " WHERE COMMODITY_CODE = ? and language_code = ? ";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して商品詳細のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommodityDetailのリスト
   */
  public List<HotSaleCommodity> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して商品詳細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityDetailのリスト
   */
  public List<HotSaleCommodity> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCommodityDetailのリスト
   */
  public List<HotSaleCommodity> loadAll() {
    return genericDao.loadAll();
  }

}
