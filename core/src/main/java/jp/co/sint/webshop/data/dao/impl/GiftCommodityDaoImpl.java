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
import jp.co.sint.webshop.data.dao.GiftCommodityDao;
import jp.co.sint.webshop.data.dto.GiftCommodity;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * ギフト対象商品
 *
 * @author System Integrator Corp.
 *
 */
public class GiftCommodityDaoImpl implements GiftCommodityDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<GiftCommodity, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public GiftCommodityDaoImpl() {
    genericDao = new GenericDaoImpl<GiftCommodity, Long>(GiftCommodity.class);
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
   * 指定されたorm_rowidを持つギフト対象商品のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するGiftCommodityのインスタンス
   */
  public GiftCommodity loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してギフト対象商品のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param giftCode ギフトコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するGiftCommodityのインスタンス
   */
  public GiftCommodity load(String shopCode, String giftCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, giftCode, commodityCode};
    final String query = "SELECT * FROM GIFT_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND GIFT_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    List<GiftCommodity> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してギフト対象商品が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param giftCode ギフトコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するGiftCommodityの行が存在すればtrue
   */
  public boolean exists(String shopCode, String giftCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, giftCode, commodityCode};
    final String query = "SELECT COUNT(*) FROM GIFT_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND GIFT_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規GiftCommodityをデータベースに追加します。
   * @param obj 追加対象のGiftCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(GiftCommodity obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規GiftCommodityをデータベースに追加します。
   * @param obj 追加対象のGiftCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(GiftCommodity obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * ギフト対象商品を更新します。
   * @param obj 更新対象のGiftCommodity
   */
  public void update(GiftCommodity obj) {
    genericDao.update(obj);
  }

  /**
   * ギフト対象商品を更新します。
   * @param obj 更新対象のGiftCommodity
   */
  public void update(GiftCommodity obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * ギフト対象商品を削除します。
   * @param obj 削除対象のGiftCommodity
   */
  public void delete(GiftCommodity obj) {
    genericDao.delete(obj);
  }

  /**
   * ギフト対象商品を削除します。
   * @param obj 削除対象のGiftCommodity
   */
  public void delete(GiftCommodity obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してギフト対象商品を削除します。
   * @param shopCode ショップコード
   * @param giftCode ギフトコード
   * @param commodityCode 商品コード
   */
  public void delete(String shopCode, String giftCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, giftCode, commodityCode};
    final String query = "DELETE FROM GIFT_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND GIFT_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してギフト対象商品のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するGiftCommodityのリスト
   */
  public List<GiftCommodity> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してギフト対象商品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するGiftCommodityのリスト
   */
  public List<GiftCommodity> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のGiftCommodityのリスト
   */
  public List<GiftCommodity> loadAll() {
    return genericDao.loadAll();
  }

}
