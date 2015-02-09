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
import jp.co.sint.webshop.data.dao.RelatedCommodityBDao;
import jp.co.sint.webshop.data.dto.RelatedCommodityB;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 自動リコメンド
 *
 * @author System Integrator Corp.
 *
 */
public class RelatedCommodityBDaoImpl implements RelatedCommodityBDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<RelatedCommodityB, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public RelatedCommodityBDaoImpl() {
    genericDao = new GenericDaoImpl<RelatedCommodityB, Long>(RelatedCommodityB.class);
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
   * 指定されたorm_rowidを持つ自動リコメンドのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するRelatedCommodityBのインスタンス
   */
  public RelatedCommodityB loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して自動リコメンドのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param linkShopCode リンクショップコード
   * @param linkCommodityCode リンク商品コード
   * @return 主キー列の値に対応するRelatedCommodityBのインスタンス
   */
  public RelatedCommodityB load(String shopCode, String commodityCode, String linkShopCode, String linkCommodityCode) {
    Object[] params = new Object[]{shopCode, commodityCode, linkShopCode, linkCommodityCode};
    final String query = "SELECT * FROM RELATED_COMMODITY_B"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?"
        + " AND LINK_SHOP_CODE = ?"
        + " AND LINK_COMMODITY_CODE = ?";
    List<RelatedCommodityB> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して自動リコメンドが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param linkShopCode リンクショップコード
   * @param linkCommodityCode リンク商品コード
   * @return 主キー列の値に対応するRelatedCommodityBの行が存在すればtrue
   */
  public boolean exists(String shopCode, String commodityCode, String linkShopCode, String linkCommodityCode) {
    Object[] params = new Object[]{shopCode, commodityCode, linkShopCode, linkCommodityCode};
    final String query = "SELECT COUNT(*) FROM RELATED_COMMODITY_B"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?"
        + " AND LINK_SHOP_CODE = ?"
        + " AND LINK_COMMODITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規RelatedCommodityBをデータベースに追加します。
   * @param obj 追加対象のRelatedCommodityB
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(RelatedCommodityB obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規RelatedCommodityBをデータベースに追加します。
   * @param obj 追加対象のRelatedCommodityB
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(RelatedCommodityB obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 自動リコメンドを更新します。
   * @param obj 更新対象のRelatedCommodityB
   */
  public void update(RelatedCommodityB obj) {
    genericDao.update(obj);
  }

  /**
   * 自動リコメンドを更新します。
   * @param obj 更新対象のRelatedCommodityB
   */
  public void update(RelatedCommodityB obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 自動リコメンドを削除します。
   * @param obj 削除対象のRelatedCommodityB
   */
  public void delete(RelatedCommodityB obj) {
    genericDao.delete(obj);
  }

  /**
   * 自動リコメンドを削除します。
   * @param obj 削除対象のRelatedCommodityB
   */
  public void delete(RelatedCommodityB obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して自動リコメンドを削除します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param linkShopCode リンクショップコード
   * @param linkCommodityCode リンク商品コード
   */
  public void delete(String shopCode, String commodityCode, String linkShopCode, String linkCommodityCode) {
    Object[] params = new Object[]{shopCode, commodityCode, linkShopCode, linkCommodityCode};
    final String query = "DELETE FROM RELATED_COMMODITY_B"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?"
        + " AND LINK_SHOP_CODE = ?"
        + " AND LINK_COMMODITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して自動リコメンドのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRelatedCommodityBのリスト
   */
  public List<RelatedCommodityB> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して自動リコメンドのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRelatedCommodityBのリスト
   */
  public List<RelatedCommodityB> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のRelatedCommodityBのリスト
   */
  public List<RelatedCommodityB> loadAll() {
    return genericDao.loadAll();
  }

}
